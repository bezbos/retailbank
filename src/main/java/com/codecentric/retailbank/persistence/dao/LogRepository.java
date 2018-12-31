package com.codecentric.retailbank.persistence.dao;

import com.codecentric.retailbank.persistence.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
}
