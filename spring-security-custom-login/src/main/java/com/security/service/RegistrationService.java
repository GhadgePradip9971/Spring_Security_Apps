package com.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dto.RegisterRequest;
import com.security.entity.Role;
import com.security.entity.User;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;

@Service
public class RegistrationService implements IRegistrationService{
	
	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public void register(RegisterRequest request) {
	
		
		if(userRepository.existsByUsername(request.getUsername())) {
		    throw new RuntimeException(
                    "Username already exists");
        }

		   Role role = roleRepository
	                .findByRoleName("ROLE_USER")
	                .orElseThrow(() ->
	                    new RuntimeException(
	                        "Default role not found"));
		   
		   User user = new User();

	        user.setUsername(request.getUsername());
	        
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setEnabled(true);
	        user.setRole(role);

	        userRepository.save(user);
		
		
		
		
		}
		
	}
    
    
    
    
    
    
    
    


