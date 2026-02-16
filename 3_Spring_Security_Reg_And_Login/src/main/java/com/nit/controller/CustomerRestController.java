package com.nit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nit.entity.Customer;
import com.nit.repositiory.CustomerRepositiory;

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerRepositiory customerrepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	
	@Autowired
	private AuthenticationManager authmanager;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> saveCustomer(@RequestBody Customer customer){
		
	String encodedPassword	=pwdEncoder.encode(customer.getPwd());
	
	customer.setPwd(encodedPassword);
		
		customerrepo.save(customer);
		return new ResponseEntity<String>("Customer Registerd",HttpStatus.CREATED);
		
		
		
	}
	
	
	@PostMapping("/api/login")
	public ResponseEntity<String> loginChek(@RequestBody Customer customer){
		
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPwd());
		
		
		try {
			Authentication authenticate=authmanager.authenticate(token);
			
			if(authenticate.isAuthenticated()) {
				return new ResponseEntity<String>("welcome to sathya technology",HttpStatus.OK);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Invalid Credentials",HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	

}
