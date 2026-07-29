package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.security.service.RegistrationService;

@Controller
public class RegistrationPageController {

    private final RegistrationService registrationService;

    public RegistrationPageController(
            RegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        try {

            registrationService.register(
                    username,
                    password);

            return "redirect:/login?registered=true";

        } catch (RuntimeException ex) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    ex.getMessage());

            return "redirect:/register";
        }
    }
}