package com.codecentric.retailbank.repository.SpringData.security;

import com.codecentric.retailbank.model.security.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {
}
