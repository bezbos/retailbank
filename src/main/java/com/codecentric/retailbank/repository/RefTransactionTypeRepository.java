package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefTransactionTypeRepository extends JpaRepository<RefTransactionType, Long> {
    RefTransactionType findByCode(String code);
}
