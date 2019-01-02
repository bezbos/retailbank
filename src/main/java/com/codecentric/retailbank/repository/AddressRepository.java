package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
