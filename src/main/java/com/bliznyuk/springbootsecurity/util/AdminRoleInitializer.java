package com.bliznyuk.springbootsecurity.util;

import com.bliznyuk.springbootsecurity.model.Role;
import com.bliznyuk.springbootsecurity.model.User;
import com.bliznyuk.springbootsecurity.service.RoleServiceImpl;
import com.bliznyuk.springbootsecurity.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminRoleInitializer implements CommandLineRunner {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminRoleInitializer(
            UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        addRoleIfNotExists("ROLE_USER");
        addRoleIfNotExists("ROLE_ADMIN");

        if (!userService.existsByRolesName("ROLE_ADMIN")) {
            Role adminRole = roleService.findByName("ROLE_ADMIN").orElseThrow();
            Role userRole = roleService.findByName("ROLE_USER").orElseThrow();

            User defaultAdminUser = new User("admin", "admin", "admin@mail.ru",
                    "admin", "admin", Set.of(userRole, adminRole));
            userService.save(defaultAdminUser);
        }
    }

    private void addRoleIfNotExists(String roleName) {
        if (roleService.findByName(roleName).isEmpty()) {
            roleService.save(new Role(roleName));
        }
    }
}