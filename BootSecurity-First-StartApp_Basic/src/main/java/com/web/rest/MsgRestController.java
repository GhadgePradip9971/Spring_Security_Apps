package com.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgRestController {
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome to spring security";
	}
	
	@GetMapping("/greet")
	public String greet() {
		return"welcome to java";
	}
 
	
}
