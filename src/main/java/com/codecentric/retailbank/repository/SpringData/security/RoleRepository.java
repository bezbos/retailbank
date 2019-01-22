package com.codecentric.retailbank.repository.SpringData.security;

import com.codecentric.retailbank.model.security.OLD.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Override
    void delete(Role role);
}
