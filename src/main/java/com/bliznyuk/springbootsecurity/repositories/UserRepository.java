package com.bliznyuk.springbootsecurity.repositories;

import com.bliznyuk.springbootsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByRolesName(String roleName);
}
