package com.bliznyuk.springbootsecurity.util;

import com.bliznyuk.springbootsecurity.model.Role;
import com.bliznyuk.springbootsecurity.model.User;
import com.bliznyuk.springbootsecurity.repositories.RoleRepository;
import com.bliznyuk.springbootsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminRoleInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRoleInitializer(UserRepository userRepository, RoleRepository roleRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        addRoleIfNotExists("ROLE_USER");
        addRoleIfNotExists("ROLE_ADMIN");

        if (!userRepository.existsByRolesName("ROLE_ADMIN")) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();

            User defaultAdminUser = new User();
            defaultAdminUser.setName("admin");
            defaultAdminUser.setLastName("admin");
            defaultAdminUser.setEmail("admin@gmail.com");
            defaultAdminUser.setPassword(passwordEncoder.encode("admin"));
            defaultAdminUser.setUsername("admin");
            defaultAdminUser.setRoles(Set.of(userRole, adminRole));
            userRepository.save(defaultAdminUser);
        }
    }

    private void addRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }
}