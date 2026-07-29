package com.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.RegisterRequest;
import com.security.service.RegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class RegistrationController {
	
	
	public RegistrationController(RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}

	private final RegistrationService registrationService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(
	        @Valid @RequestBody RegisterRequest request) {

	    registrationService.register(
	            request.getUsername(),
	            request.getPassword());

	    return ResponseEntity.ok(
	            "User registered successfully");
	}
	
	
	
	
	
	
	
	
	
	

}
