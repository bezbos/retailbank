package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByPersonalDetails(String details);

    List<Customer> findByBranch(Branch relatedBranch);

    List<Customer> findByAddress(Address relatedAddress);
}
