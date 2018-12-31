package com.codecentric.retailbank.services;

import com.codecentric.retailbank.persistence.dao.PasswordResetTokenRepository;
import com.codecentric.retailbank.persistence.dao.RoleRepository;
import com.codecentric.retailbank.persistence.dao.TokenRepository;
import com.codecentric.retailbank.persistence.dao.UserRepository;
import com.codecentric.retailbank.persistence.model.PasswordResetToken;
import com.codecentric.retailbank.persistence.model.User;
import com.codecentric.retailbank.persistence.model.VerificationToken;
import com.codecentric.retailbank.web.dto.UserDto;
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
    public User registerNewUserAccount(final UserDto accountDto) throws UserAlreadyExistsException {
        if (emailExists(accountDto.getEmail()))
            throw new UserAlreadyExistsException("An account with an email \"" + accountDto.getEmail() + "\" address already exists.");

        final User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken verificationToken = new VerificationToken(token, user, getExpiryDate());
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(final String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(final String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken.setExpiryDate(getExpiryDate());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public User findUserByMail(final String userMail) {
        return userRepository.findByEmail(userMail);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(user, token);
        myToken.setExpiryDate(getExpiryDate());
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String password) {
        boolean result = passwordEncoder.matches(password, user.getPassword());

        return result;
    }


    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    private Date getExpiryDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        final Date expiryDate = calendar.getTime();

        return expiryDate;
    }

}
