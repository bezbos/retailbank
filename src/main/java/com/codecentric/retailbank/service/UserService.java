package com.codecentric.retailbank.service;

import com.codecentric.retailbank.exception.runtime.UserAlreadyExistsException;
import com.codecentric.retailbank.model.dto.UserDto;
import com.codecentric.retailbank.model.security.PasswordResetToken;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.model.security.VerificationToken;
import com.codecentric.retailbank.repository.security.PasswordResetTokenRepository;
import com.codecentric.retailbank.repository.security.RoleRepository;
import com.codecentric.retailbank.repository.security.UserRepository;
import com.codecentric.retailbank.repository.security.VerificationTokenRepository;
import com.codecentric.retailbank.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserService implements IUserService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //endregion


    //region CONSTRUCTOR
    @Autowired public UserService(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  VerificationTokenRepository verificationTokenRepository,
                                  PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    //endregion


    //region USER
    @Override public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistsException {
        if (emailExists(accountDto.getEmail()))
            throw new UserAlreadyExistsException("An account with an email \"" + accountDto.getEmail() + "\" address already exists.");

        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(Arrays.asList(roleRepository.singleByName("ROLE_USER")));
        return userRepository.add(user);
    }

    @Override public void saveRegisteredUser(User user) {
        userRepository.update(user);
    }

    @Override public User getUser(String verificationToken) {
        User user = verificationTokenRepository.getSingleByToken(verificationToken).getUser();
        return user;
    }

    @Override public User findUserByMail(String userMail) {
        return userRepository.singleByUsername(userMail);
    }
    //endregion

    //region TOKENS
    @Override public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = verificationTokenRepository.getSingleByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken.setExpiryDate(getExpiryDate());
        vToken = verificationTokenRepository.add(vToken);
        return vToken;
    }

    @Override public void createVerificationTokenForUser(User user, String token) {
//        VerificationToken verificationToken = new VerificationToken(token, user, getExpiryDate());
//        verificationTokenRepository.save(verificationToken);
    }

    @Override public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.getSingleByToken(token);
    }
    //endregion

    //region PASSWORD
    @Override public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken myToken = new PasswordResetToken(user, token);
//        myToken.setExpiryDate(getExpiryDate());
//        passwordResetTokenRepository.save(myToken);
    }

    @Override public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.singleByToken(token);
    }

    @Override public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.update(user);
    }

    @Override public boolean checkIfValidOldPassword(User user, String password) {
        boolean result = passwordEncoder.matches(password, user.getPassword());

        return result;
    }
    //endregion

    //region HELPERS
    private boolean emailExists(String email) {
        return userRepository.singleByUsername(email) != null;
    }

    private Date getExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date expiryDate = calendar.getTime();

        return expiryDate;
    }
    //endregion

}
