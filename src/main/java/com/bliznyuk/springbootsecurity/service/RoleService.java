package com.bliznyuk.springbootsecurity.service;

import com.bliznyuk.springbootsecurity.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
    void save(Role role);
}
