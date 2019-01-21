package com.codecentric.retailbank.repository.SpringData.security;

import com.codecentric.retailbank.model.security.OLD.User;
import com.codecentric.retailbank.model.security.OLD.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
