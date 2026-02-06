package com.zomato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zomato.dto.UserLogIn;
import com.zomato.dto.UserRegister;
import com.zomato.service.ZomatoUserService;

@RestController
public class ZomatoController {
	
	@Autowired
	ZomatoUserService service;
	
	
	@PostMapping("/create/user")
	public String registerUser(@RequestBody UserRegister request) {
		
		String result=service.registerUser(request);
		
		return result;
		
		
	}
	
	@PostMapping("/login/user")
	public String logInUser(@RequestBody UserLogIn request) {
		
		//TODO: we need to passs Credentials to the spring security layer
		//internally,spring security layer validate user creanditials 
		//internally security layer connects to data base and check for user name and password 
		//if user is valid and certified by security ,generate tokens and attach as a part of response object 
		
		String result=service.logInUser(request);
		return result;
	}
	
	
	

}
