package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefAccountTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefAccountTypesRepository extends JpaRepository<RefAccountTypes, Long> {
}
