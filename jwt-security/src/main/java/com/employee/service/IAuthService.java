package com.employee.service;

import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RegistrationRequest;
import com.employee.dto.RegistrationResponse;

public interface IAuthService {
	
	public RegistrationResponse register(RegistrationRequest registrationRequest);
	public LoginResponse login(LoginRequest request);

}
