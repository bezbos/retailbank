package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefBranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefBranchTypeRepository extends JpaRepository<RefBranchType, String> {
}
