package com.nit.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nit.entity.Customer;


public interface CustomerRepositiory extends JpaRepository<Customer, Integer> {
	
	public Customer findByEmail(String email);

}
