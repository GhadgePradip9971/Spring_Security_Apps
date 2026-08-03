package com.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "JWT Security Application is running";
    }
    
    @GetMapping("/profile")
    public String profile() {
		return "This is a protected profile endpoint";
	}
}
