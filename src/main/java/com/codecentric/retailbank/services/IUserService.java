package com.codecentric.retailbank.services;

import com.codecentric.retailbank.persistence.model.PasswordResetToken;
import com.codecentric.retailbank.persistence.model.User;
import com.codecentric.retailbank.persistence.model.VerificationToken;
import com.codecentric.retailbank.web.dto.UserDto;
import com.codecentric.retailbank.web.error.UserAlreadyExistsException;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistsException;

    User getUser(String verificationToken);

    VerificationToken getVerificationToken(String VerificationToken);

    void createVerificationTokenForUser(User user, String token);

    void saveRegisteredUser(User user);

    VerificationToken generateNewVerificationToken(String token);

    User findUserByMail(String userMail);

    void createPasswordResetTokenForUser(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);
}
