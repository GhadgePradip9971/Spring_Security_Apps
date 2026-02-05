package com.zomato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zomato.dto.UserLogIn;
import com.zomato.dto.UserRegister;
import com.zomato.entity.UserEntity;
import com.zomato.repositiory.ZomatoUserRepositiory;

@Service
public class ZomatoUserService {

	@Autowired
	ZomatoUserRepositiory userrepo;

	public String registerUser(UserRegister request) {

		UserEntity entity = new UserEntity();
		entity.setEmailId(request.getEmailId());
		entity.setContactNumber(request.getContactNumber());
		entity.setFullName(request.getFullName());
		entity.setPassword(request.getPassword());

		userrepo.save(entity);

		return "User Created Succesfully ! Please Login";
	}

	public String logInUser(UserLogIn request) {

		UserEntity entity = userrepo.findByEmailIdAndPassword(request.getEmailId(), request.getPassword());

		System.out.println(entity);
		return "User Log In suceesss!!!!!!";
	}

}
