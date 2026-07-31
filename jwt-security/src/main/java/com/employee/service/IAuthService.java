package com.employee.service;

import com.employee.dto.RegistrationRequest;
import com.employee.dto.RegistrationResponse;

public interface IAuthService {
	
	public RegistrationResponse register(RegistrationRequest registrationRequest);
	

}
