package com.bliznyuk.springbootsecurity.controller;

import com.bliznyuk.springbootsecurity.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getUser(Model model, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("user", myUserDetails.getUser());
        model.addAttribute("roleDescription", "Пользователь");
        return "user/user";
    }
}
