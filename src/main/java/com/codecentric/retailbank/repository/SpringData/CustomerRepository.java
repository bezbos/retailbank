package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.AddressOLD;
import com.codecentric.retailbank.model.domain.OLD.BranchOLD;
import com.codecentric.retailbank.model.domain.OLD.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByPersonalDetails(String details);

    List<Customer> findByBranch(BranchOLD relatedBranch);

    List<Customer> findByAddress(AddressOLD relatedAddress);
}
