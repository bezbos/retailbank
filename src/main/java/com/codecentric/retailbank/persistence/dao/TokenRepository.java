package com.codecentric.retailbank.persistence.dao;

import com.codecentric.retailbank.persistence.model.User;
import com.codecentric.retailbank.persistence.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
