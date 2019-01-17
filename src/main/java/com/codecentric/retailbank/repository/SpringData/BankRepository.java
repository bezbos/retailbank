package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.BankOLD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankOLD, Long> {
    BankOLD findByDetails(String details);
}
