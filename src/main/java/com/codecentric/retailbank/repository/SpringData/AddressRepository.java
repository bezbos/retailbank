package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByLine1(String line1);
}
