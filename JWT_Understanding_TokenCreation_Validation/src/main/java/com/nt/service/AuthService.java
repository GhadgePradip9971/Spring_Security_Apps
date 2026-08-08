package com.nt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.dto.LoginRequest;
import com.nt.dto.LoginResponse;
import com.nt.dto.RegisterRequest;
import com.nt.entity.User;
import com.nt.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public String register(RegisterRequest request) {

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		user.setEnabled(true);
		userRepository.save(user);
		return "User registered successfully";
		
		
	}
	

    // - Login + JWT Generation
	public LoginResponse login(LoginRequest request) {
		
		
		//Authenticate UserName and Password using AuthenticationManager
		authenticationManager.authenticate(
				new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
						request.getUsername(), request.getPassword()));
		
		//generate JWT token using JwtService after successful authentication
		
		String token = jwtService.generateToken(request.getUsername());
		//return username with jwt token to the client
		
		return new LoginResponse(
				request.getUsername(),
				token);
		
	}
	
	

}
