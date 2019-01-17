package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.RefBranchTypeOLD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefBranchTypeRepository extends JpaRepository<RefBranchTypeOLD, Long> {
    RefBranchTypeOLD findByCode(String code);
}
