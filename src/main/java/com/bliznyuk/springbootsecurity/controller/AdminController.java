package com.bliznyuk.springbootsecurity.controller;


import com.bliznyuk.springbootsecurity.model.Role;
import com.bliznyuk.springbootsecurity.model.User;
import com.bliznyuk.springbootsecurity.repositories.RoleRepository;
import com.bliznyuk.springbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String showAdmin(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("roleDescription", "Администратор");
        return "admin/admin";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("listUsers", userService.getUsers());
        return "admin/listUsers";
    }

    @GetMapping("/show_user")
    public String showUserById(@RequestParam("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleDescription", user.getRoleDescription());
        return "admin/showUser";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin/newUser";
    }

    @PostMapping
    public String saveUser(User newUser, @RequestParam(value = "roleIds", required = false) Set<Long> roleIds) {
        Set<Role> roles;
        if (roleIds != null && !roleIds.isEmpty()) {
            roles = new HashSet<>(roleRepository.findAllById(roleIds));
        } else {
            Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow();
            roles = Set.of(defaultRole);
        }
        newUser.setRoles(roles);
        userService.addUser(newUser);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/update")
    public String editUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin/editUser";
    }

    @PostMapping("/savechanges")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }
}