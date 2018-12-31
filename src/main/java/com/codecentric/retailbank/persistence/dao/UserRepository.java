package com.codecentric.retailbank.persistence.dao;

import com.codecentric.retailbank.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
