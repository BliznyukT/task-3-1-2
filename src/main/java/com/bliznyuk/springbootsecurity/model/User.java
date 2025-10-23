package com.bliznyuk.springbootsecurity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Component
@Table
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(name = "lastname")
    private String lastName;
    private String email;
    private String password;
    private String username;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String lastName, String email, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public User(String name, String lastName, String email, String password, String username, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public String getRoleDescription() {
        return getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"))
                ? "Администратор" : "Пользователь";
    }
}
