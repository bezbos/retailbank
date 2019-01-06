package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefAccountTypeRepository extends JpaRepository<RefAccountType, Long> {
    RefAccountType findByCode(String code);
}
