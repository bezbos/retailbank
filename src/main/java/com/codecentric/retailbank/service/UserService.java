package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.dto.UserDto;
import com.codecentric.retailbank.model.security.PasswordResetToken;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.model.security.VerificationToken;
import com.codecentric.retailbank.repository.SpringData.security.PasswordResetTokenRepository;
import com.codecentric.retailbank.repository.SpringData.security.RoleRepository;
import com.codecentric.retailbank.repository.SpringData.security.TokenRepository;
import com.codecentric.retailbank.repository.SpringData.security.UserRepository;
import com.codecentric.retailbank.service.interfaces.IUserService;
import com.codecentric.retailbank.web.error.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistsException {
        if (emailExists(accountDto.getEmail()))
            throw new UserAlreadyExistsException("An account with an email \"" + accountDto.getEmail() + "\" address already exists.");

        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }


    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user, getExpiryDate());
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken.setExpiryDate(getExpiryDate());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public User findUserByMail(String userMail) {
        return userRepository.findByEmail(userMail);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(user, token);
        myToken.setExpiryDate(getExpiryDate());
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        boolean result = passwordEncoder.matches(password, user.getPassword());

        return result;
    }


    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private Date getExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date expiryDate = calendar.getTime();

        return expiryDate;
    }

}
