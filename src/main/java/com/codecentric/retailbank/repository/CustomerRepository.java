package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByPersonalDetails(String details);

    Customer findByBranch(Branch relatedBranch);
}
