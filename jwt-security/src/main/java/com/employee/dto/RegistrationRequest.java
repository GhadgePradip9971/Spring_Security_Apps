package com.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must contain at least 6 characters")
	private String password;

}
