package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findByDetails(String details);

    Branch findByBank(Bank bank);
}
