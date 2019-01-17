package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.AddressOLD;
import com.codecentric.retailbank.model.domain.OLD.BankOLD;
import com.codecentric.retailbank.model.domain.OLD.BranchOLD;
import com.codecentric.retailbank.model.domain.OLD.RefBranchTypeOLD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<BranchOLD, Long> {
    BranchOLD findByDetails(String details);

    List<BranchOLD> findByBank(BankOLD bank);

    List<BranchOLD> findByAddress(AddressOLD address);

    List<BranchOLD> findByType(RefBranchTypeOLD refBranchType);
}
