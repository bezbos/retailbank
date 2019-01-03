package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}
