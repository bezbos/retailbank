package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.events.OnRegistrationCompleteEvent;
import com.codecentric.retailbank.exception.runtime.InvalidOldPasswordException;
import com.codecentric.retailbank.exception.runtime.UserNotFoundException;
import com.codecentric.retailbank.model.dto.PasswordDto;
import com.codecentric.retailbank.model.dto.UserDto;
import com.codecentric.retailbank.model.security.PasswordResetToken;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.model.security.VerificationToken;
import com.codecentric.retailbank.service.UserService;
import com.codecentric.retailbank.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final String CONTROLLER_NAME = "account";
    //endregion

    //region CONSTRUCTORS
    public AccountController() {
    }

    public AccountController(Environment env, MessageSource messages, UserService userService) {
        super(env, messages, userService);
    }
    //endregion

    //region INDEX
    @GetMapping(value = {"", "/index"})
    public String getHomePage() {
        return CONTROLLER_NAME + "/index";
    }
    //endregion

    //region LOGIN / LOGOUT
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return CONTROLLER_NAME + "/login";
    }

    @GetMapping(value = "/logout-success")
    public String getLogoutPage() {
        return CONTROLLER_NAME + "/logout";
    }
    //endregion

    //region REGISTRATION
    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {
        UserDto dto = new UserDto();
        model.addAttribute("user", dto);
        return CONTROLLER_NAME + "/registration";
    }

    @PostMapping(value = "/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto,
                                            BindingResult result,
                                            WebRequest request) {
        User registered = null;

        if (!result.hasErrors()) {
            registered = new User();
            registered = createUserAccount(accountDto);
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
    //endregion

    //region PASSWORD
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String showForgotPasswordPage() {
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
                                         @RequestParam("id") long id,
                                         @RequestParam("token") String token) {

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
    public String showUpdatePasswordPage() {
        return CONTROLLER_NAME + "/updatePassword";
    }

    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    public String savePassword(@Valid PasswordDto passwordDto) {
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
    public String showChangePasswordPageForAuthenticatedUser() {
        return CONTROLLER_NAME + "/changePassword";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public GenericResponse updatePassword(Locale locale,
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
    //endregion

    //region OAUTH2
    private static String authorizationRequestBaseUri = "../oauth2/authorization";

    private Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @RequestMapping(value = "/oauth_login", method = RequestMethod.GET)
    public String getOauthLoginPage(Model model) {

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
    //endregion

}
