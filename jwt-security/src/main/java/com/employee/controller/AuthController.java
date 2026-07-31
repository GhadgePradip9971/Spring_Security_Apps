package com.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RegistrationRequest;
import com.employee.dto.RegistrationResponse;
import com.employee.service.AuthService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthService authService;
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	
	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequesst){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(authService.register(registrationRequesst));
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(
	        @Valid @RequestBody LoginRequest request) {

	    return ResponseEntity.ok(
	        authService.login(request)
	    );
	}
	
	
	
	
}
