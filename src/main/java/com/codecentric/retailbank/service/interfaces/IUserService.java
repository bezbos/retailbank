package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.exception.runtime.UserAlreadyExistsException;
import com.codecentric.retailbank.model.dto.UserDto;
import com.codecentric.retailbank.model.security.PasswordResetToken;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.model.security.VerificationToken;

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
