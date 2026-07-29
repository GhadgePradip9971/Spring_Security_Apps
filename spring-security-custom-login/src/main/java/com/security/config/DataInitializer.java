package com.security.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.security.entity.Role;
import com.security.repository.RoleRepository;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initializeRoles(RoleRepository roleRepository) {

		return args -> {

			if (roleRepository.findByRoleName("ROLE_USER").isEmpty()) {

				Role role = new Role();
				role.setRoleName("ROLE_USER");

				roleRepository.save(role);
			}

			if (roleRepository.findByRoleName("ROLE_ADMIN").isEmpty()) {

				Role role = new Role();
				role.setRoleName("ROLE_ADMIN");

				roleRepository.save(role);
			}
		};
	}
}
