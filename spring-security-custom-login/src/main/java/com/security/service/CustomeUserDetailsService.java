package com.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.config.CustomUserDetails;
import com.security.entity.User;
import com.security.repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
	
	 private final UserRepository userRepository;

	    public CustomeUserDetailsService(
	            UserRepository userRepository) {

	        this.userRepository = userRepository;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {

	        User user = userRepository
	                .findByUsername(username)
	                .orElseThrow(() ->
	                        new UsernameNotFoundException(
	                                "User not found"));

	        return new CustomUserDetails(user);
	    }
	}
