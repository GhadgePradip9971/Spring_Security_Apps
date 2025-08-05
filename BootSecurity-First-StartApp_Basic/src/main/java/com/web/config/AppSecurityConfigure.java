package com.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfigure {
	//Securityfilterchain
	  @Bean
	    public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(auth -> 
	                auth
	                    .requestMatchers("/welcome","/about-us","contact-us").permitAll()
	                    .anyRequest().authenticated()
	            );
	            //.formLogin(withDefaults()  // Enables default login form
	        return http.build();
	    }
	

}
