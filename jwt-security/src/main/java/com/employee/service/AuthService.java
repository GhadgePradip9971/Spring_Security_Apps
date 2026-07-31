package com.employee.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RegistrationRequest;
import com.employee.dto.RegistrationResponse;
import com.employee.entity.Role;
import com.employee.entity.User;
import com.employee.repository.RoleRepository;
import com.employee.repository.UserRepository;

@Service
public class AuthService  implements IAuthService{
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager ;

	public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		if(userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
			throw new RuntimeException("UserName Alreday Exist");
		}
		  Role userRole = roleRepository.findByRoleName("ROLE_USER")
	                .orElseThrow(() ->
	                        new RuntimeException("ROLE_USER not found"));
		  
		  
		  

	        User user = new User();

	        user.setUsername(registrationRequest.getUsername());
	        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
	        
	        user.setEnabled(true);
	        user.setRole(userRole);
	        userRepository.save(user);


	        return new RegistrationResponse(
	                "User registered successfully",
	                user.getUsername());
	}
	
	
	public LoginResponse login(LoginRequest request) {

	    Authentication authentication =
	            authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                    request.getUsername(),
	                    request.getPassword()
	                )
	            );

	    return new LoginResponse(
	            "Login successful",
	            authentication.getName()
	    );
	}
	
	
	
	

}
