package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.RefAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefAccountStatusRepository extends JpaRepository<RefAccountStatus, Long> {
    RefAccountStatus getByCode(String code);
}
