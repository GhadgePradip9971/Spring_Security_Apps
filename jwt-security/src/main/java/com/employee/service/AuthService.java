package com.employee.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
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
	
	
	
	
	
	
	

}
