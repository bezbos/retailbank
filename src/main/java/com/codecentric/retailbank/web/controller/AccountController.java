package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.events.OnRegistrationCompleteEvent;
import com.codecentric.retailbank.persistence.model.PasswordResetToken;
import com.codecentric.retailbank.persistence.model.User;
import com.codecentric.retailbank.persistence.model.VerificationToken;
import com.codecentric.retailbank.services.UserService;
import com.codecentric.retailbank.web.dto.PasswordDto;
import com.codecentric.retailbank.web.dto.UserDto;
import com.codecentric.retailbank.web.error.InvalidOldPasswordException;
import com.codecentric.retailbank.web.error.UserAlreadyExistsException;
import com.codecentric.retailbank.web.error.UserNotFoundException;
import com.codecentric.retailbank.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "account";

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;


    public AccountController() {
        super();
    }


    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String getHome(Model model,
                          Principal principal) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return CONTROLLER_NAME + "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model,
                               Principal principal) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return CONTROLLER_NAME + "/login";
    }

    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String getLogoutPage(Model model,
                                Principal principal) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return CONTROLLER_NAME + "/logout";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request,
                                       Model model) {
        UserDto dto = new UserDto();
        model.addAttribute("user", dto);
        return CONTROLLER_NAME + "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto,
                                            BindingResult result,
                                            WebRequest request,
                                            Errors errors) {
        User registered = null;

        if (!result.hasErrors()) {
            registered = new User();
            registered = createUserAccount(accountDto, result);
        }

        if (registered == null)
            result.rejectValue("email", "message.regError");

        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (Exception ex) {
            return new ModelAndView(CONTROLLER_NAME + "/emailError", "user", accountDto);
        }

        return new ModelAndView(CONTROLLER_NAME + "/registrationConfirm", "user", accountDto);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request,
                                      Model model,
                                      @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);

        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/" + CONTROLLER_NAME + "/badUser?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            model.addAttribute("expired", true);
            model.addAttribute("token", token);

            return "redirect:/" + CONTROLLER_NAME + "/badUser?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);

        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/" + CONTROLLER_NAME + "/login?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/resendRegistrationToken", method = RequestMethod.GET)
    public String resendRegistrationToken(HttpServletRequest request,
                                          Model model,
                                          @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        Locale locale = request.getLocale();
        User user = userService.getUser(newToken.getToken());

        try {
            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
            mailSender.send(email);
        } catch (MailAuthenticationException e) {
            LOGGER.debug("MailAuthenticationException", e);
            return "redirect:/" + CONTROLLER_NAME + "emailError.html?lang=" + locale.getLanguage();
        } catch (Exception e) {
            LOGGER.debug(e.getLocalizedMessage(), e);
            model.addAttribute("message", e.getLocalizedMessage());
            return "redirect:/login.html?lang=" + locale.getLanguage();
        }

        model.addAttribute("message", messages.getMessage("message.resendToken", null, locale));
        return "redirect:/" + CONTROLLER_NAME + "/login.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String showForgotPasswordPage(Principal principal,
                                         Model model) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return CONTROLLER_NAME + "/forgotPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        User user = userService.findUserByMail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        try {
            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            mailSender.send(constructResetTokenEmail(appUrl, request.getLocale(), token, user));
        } catch (MailAuthenticationException ex) {
            LOGGER.debug("MailAuthenticationException", ex);
            return new GenericResponse(ex.getMessage(), "MailAuthenticationException");
        } catch (Exception ex) {
            LOGGER.debug("Exception", ex);
            return new GenericResponse(ex.getMessage(), "Exception");
        }

        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Locale locale,
                                         Model model,
                                         Principal principal,
                                         @RequestParam("id") long id,
                                         @RequestParam("token") String token) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        // Retrieve password reset token from DB, then retrieve token owner.
        PasswordResetToken passwordToken = userService.getPasswordResetToken(token);
        User user = passwordToken.getUser();

        // Check if password reset token is null or owner user doesn't exist.
        if ((passwordToken == null) || (user.getId() != id)) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/" + CONTROLLER_NAME + "/login";
        }

        // Check if password reset token has expired.
        Calendar cal = Calendar.getInstance();
        if ((passwordToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0)) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            return "redirect:/" + CONTROLLER_NAME + "/login";
        }

        // During reset phase, limit current session user to only being able to reset the password.
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/" + CONTROLLER_NAME + "/updateForgotPassword";
    }

    @RequestMapping(value = "/updateForgotPassword", method = RequestMethod.GET)
    public String showUpdatePasswordPage(Principal principal,
                                         Model model) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return CONTROLLER_NAME + "/updatePassword";
    }

    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    public String savePassword(Locale locale,
                               @Valid PasswordDto passwordDto) {
        // Retrieve the current session user
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        // Change their password
        try {
            LOGGER.info(user.getEmail() + " is changing password.");
            userService.changeUserPassword(user, passwordDto.getNewPassword());
        } catch (Exception ex) {
            LOGGER.error(user.getEmail() + " failed to change password.", ex);
        }

        // Logout the current session user.
        SecurityContextHolder.clearContext();

        return CONTROLLER_NAME + "/passwordResetSuccess";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public String showChangePasswordPageForAuthenticatedUser(Principal principal,
                                                             Model model) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return CONTROLLER_NAME + "/changePassword";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public GenericResponse updatePassword(Locale locale,
                                          HttpServletRequest request,
                                          @RequestParam("password") String password,
                                          @RequestParam("oldPassword") String oldPassword) {
        User user = userService.findUserByMail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }

        userService.changeUserPassword(user, password);

        sendEmailMessage(
                constructEmail("Bankcentric Password Change Notification",
                        "Your password has been successfully changed. If this was not your doing please contact support immidately!",
                        user), mailSender
        );

        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    @RequestMapping(value = "/passwordChangeSuccess", method = RequestMethod.GET)
    public String showPasswordChangeSuccessPage() {
        return CONTROLLER_NAME + "/passwordChangeSuccess";
    }


    // ************ OAUTH2 ************** //
    private static String authorizationRequestBaseUri = "../oauth2/authorization";

    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @RequestMapping(value = "/oauth_login", method = RequestMethod.GET)
    public String getLoginPage(Model model) {

        Iterable<ClientRegistration> clientRegistrations = null;

        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        for (ClientRegistration registration : clientRegistrations) {
            oauth2AuthenticationUrls.put(
                    registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId()
            );
        }

        model.addAttribute("urls", oauth2AuthenticationUrls);

        return CONTROLLER_NAME + "/oauth_login";
    }


    /**
     * Creates a new account in the DB.
     *
     * @param accountDto Account information.
     * @return the newly created account if successful. Otherwise returns <code>null</code>.
     */
    private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;

        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (UserAlreadyExistsException ex) {
            return null;
        }

        return registered;
    }

    /**
     * Used to construct a <code>SimpleMailMessage</code> object required to send an email. This one is used specifically for generating a email verification token resend.
     *
     * @param contextPath Application domain link.
     * @param locale      Language to use.
     * @param newToken    Verification token object.
     * @param user        User to send the email to.
     * @return a new instance of a <code>SimpleMailMessage</code> object.
     */
    private SimpleMailMessage constructResendVerificationTokenEmail(String contextPath,
                                                                    Locale locale,
                                                                    VerificationToken newToken,
                                                                    User user) {
        String confirmationUrl = contextPath + "/account/registrationConfirm.html?token=" + newToken.getToken();

        String message = messages.getMessage("message.resendToken", null, locale);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        email.setTo(user.getEmail());
        return email;
    }

    /**
     * Used to construct a <code>SimpleMailMessage</code> object required to send an email. This one is used specifically for generating a password reset email.
     *
     * @param contextPath Application domain link.
     * @param locale      Language to use.
     * @param token       Password reset token value.
     * @param user        User to send the email to.
     * @return a new instance of a <code>SimpleMailMessage</code> object.
     */
    private SimpleMailMessage constructResetTokenEmail(String contextPath,
                                                       Locale locale,
                                                       String token,
                                                       User user) {
        String url = contextPath + "/account/changePassword?id=" +
                user.getId() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    /**
     * Used to construct a <code>SimpleMailMessage</code> object required to send an email.
     *
     * @param subject Mail title.
     * @param body    Mail contents.
     * @param user    Mail receiver(takes the <code>email</code> property from the object).
     * @return a new instance of a <code>SimpleMailMessage</code> object.
     */
    private SimpleMailMessage constructEmail(String subject,
                                             String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    /**
     * Sends an email message.
     * <br/>
     * <br/>
     * CONFIGURE YOUR "application.properties" USING YOUR CREDENTIALS:
     * <pre>
     * support.email=jonhdoe@gmail.com
     * spring.mail.host=smtp.gmail.com
     * spring.mail.port=465
     * spring.mail.protocol=smtps
     * spring.mail.username=jonhdoe@gmail.com
     * spring.mail.password=YOUR_GOOGLE_APP_PASSWORD - "https://myaccount.google.com/apppasswords" Note: you must have 2FA activated to generate app passwords
     * spring.mail.properties.mail.transport.protocol=smtps
     * spring.mail.properties.mail.smtps.auth=true
     * spring.mail.properties.mail.smtps.starttls.enable=true
     * spring.mail.properties.mail.smtps.timeout=8000
     * </pre>
     *
     * @param mail       Represents an object containing mail information.
     * @param mailSender Represents the handler object that will be used to send the mail.
     */
    private void sendEmailMessage(SimpleMailMessage mail, JavaMailSender mailSender) {
        try {
            mailSender.send(mail);
        } catch (MailAuthenticationException ex) {
            LOGGER.debug("MailAuthenticationException", ex);
        } catch (Exception ex) {
            LOGGER.debug("Exception", ex);
        }
    }
}
