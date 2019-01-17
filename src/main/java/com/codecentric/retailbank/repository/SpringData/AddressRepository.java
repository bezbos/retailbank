package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.AddressOLD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressOLD, Long> {
    AddressOLD findByLine1(String line1);
}
