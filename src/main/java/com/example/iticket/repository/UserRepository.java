package com.example.iticket.repository;

import com.example.iticket.model.Role;
import com.example.iticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

     List<User> findAllByRoles_RoleName(String role);


}

