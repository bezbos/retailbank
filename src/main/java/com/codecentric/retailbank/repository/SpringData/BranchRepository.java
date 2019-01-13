package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.RefBranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findByDetails(String details);

    List<Branch> findByBank(Bank bank);

    List<Branch> findByAddress(Address address);

    List<Branch> findByType(RefBranchType refBranchType);
}
