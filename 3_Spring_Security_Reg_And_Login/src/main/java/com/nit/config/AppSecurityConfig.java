package com.nit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nit.service.CustomerService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private CustomerService customerservice;
	
	
	@Bean
	public PasswordEncoder pwdEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
	

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config)throws Exception {
		return config.getAuthenticationManager();
	}
	
	
	
	@Bean
	public AuthenticationProvider authProvide() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider(customerservice);
		
		authProvider.setPasswordEncoder(pwdEncoder());
		
		return authProvider;
		
		
	}
	
	
	
	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(req -> req
                .requestMatchers("/register", "/api/login")
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .formLogin(Customizer.withDefaults())
        .logout(Customizer.withDefaults());

    return http.build();
		
	}
	
	
	
	
	
	 
	
	

}
