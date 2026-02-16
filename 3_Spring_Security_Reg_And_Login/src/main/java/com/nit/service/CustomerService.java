package com.nit.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nit.entity.Customer;
import com.nit.repositiory.CustomerRepositiory;

@Service
public class CustomerService implements UserDetailsService {
	
	@Autowired
	private CustomerRepositiory customerrepo;

	@Override
	public UserDetails loadUserByUsername(String email)
	        throws UsernameNotFoundException {

	    Customer c = customerrepo.findByEmail(email);

	    if (c == null) {
	        throw new UsernameNotFoundException("User not found with email: " + email);
	    }

	    return new User(
	            c.getEmail(),
	            c.getPwd(),
	            Collections.emptyList()
	    );
	}

}
