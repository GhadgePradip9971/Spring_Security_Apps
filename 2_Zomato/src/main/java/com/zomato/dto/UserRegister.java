package com.zomato.dto;

import lombok.Data;

@Data
public class UserRegister {
	
	private String emailId;
	private String password;
	private String fullName;
	private String contactNumber;

}
