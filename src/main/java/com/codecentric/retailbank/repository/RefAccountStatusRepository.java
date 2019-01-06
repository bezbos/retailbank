package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefAccountStatusRepository extends JpaRepository<RefAccountStatus, Long> {
    RefAccountStatus getByCode(String code);
}
