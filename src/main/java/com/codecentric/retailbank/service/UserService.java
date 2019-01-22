package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.dto.UserDto;
import com.codecentric.retailbank.model.security.PasswordResetToken;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.model.security.VerificationToken;
import com.codecentric.retailbank.repository.JDBC.security.PasswordResetTokenRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.security.RoleRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.security.UserRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.security.VerificationTokenRepositoryJDBC;
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
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepositoryJDBC userRepositoryJDBC;

    @Autowired
    private RoleRepositoryJDBC roleRepositoryJDBC;

    @Autowired
    private VerificationTokenRepositoryJDBC verificationTokenRepositoryJDBC;

    @Autowired
    private PasswordResetTokenRepositoryJDBC passwordResetTokenRepositoryJDBC;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        user.setRoles(Arrays.asList(roleRepositoryJDBC.getSingleByName("ROLE_USER")));
        return userRepositoryJDBC.add(user);
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
//        VerificationToken verificationToken = new VerificationToken(token, user, getExpiryDate());
//        verificationTokenRepositoryJDBC.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepositoryJDBC.getSingleByToken(token);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepositoryJDBC.update(user);
    }

    @Override
    public User getUser(String verificationToken) {
        User user = verificationTokenRepositoryJDBC.getSingleByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = verificationTokenRepositoryJDBC.getSingleByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken.setExpiryDate(getExpiryDate());
        vToken = verificationTokenRepositoryJDBC.add(vToken);
        return vToken;
    }

    @Override
    public User findUserByMail(String userMail) {
        return userRepositoryJDBC.getSingleByUsername(userMail);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken myToken = new PasswordResetToken(user, token);
//        myToken.setExpiryDate(getExpiryDate());
//        passwordResetTokenRepositoryJDBC.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepositoryJDBC.getSingleByToken(token);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepositoryJDBC.update(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        boolean result = passwordEncoder.matches(password, user.getPassword());

        return result;
    }


    private boolean emailExists(String email) {
        return userRepositoryJDBC.getSingleByUsername(email) != null;
    }

    private Date getExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date expiryDate = calendar.getTime();

        return expiryDate;
    }

}
