package com.example.iticket.repository;

import com.example.iticket.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
