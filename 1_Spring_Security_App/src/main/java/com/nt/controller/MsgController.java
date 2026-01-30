package com.nt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
	
	
	@GetMapping("/welcome")
	public String welcome() {
		
		return "welcome to sathya tech";
		
	}
	
	@GetMapping("/greet")
	public String greeting() {
		return"Happy birthday to you";
	}

}
