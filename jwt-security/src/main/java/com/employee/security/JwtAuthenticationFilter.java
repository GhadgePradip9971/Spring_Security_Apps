package com.employee.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employee.service.CustomUserDetailsService;
import com.employee.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	public JwtAuthenticationFilter(JwtService jwtService , CustomUserDetailsService userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		  String authHeader = request.getHeader("Authorization");

		    System.out.println("Authorization Header: " + authHeader);
		    if (authHeader == null ||
	                !authHeader.startsWith("Bearer ")) {

	            filterChain.doFilter(request, response);
	            return;
	        }

	        String token =
	                authHeader.substring(7);
	        
	        
	        String username = jwtService.extractUsername(token);

	        System.out.println("Username from JWT: " + username);
	        

	        // Make sure no authentication exists already
	        if (username != null &&
	                SecurityContextHolder.getContext()
	                        .getAuthentication() == null) {

	            // Load user from database
	            UserDetails userDetails =
	                    userDetailsService
	                            .loadUserByUsername(username);

	            // Create Authentication
	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(
	                            userDetails,
	                            null,
	                            userDetails.getAuthorities()
	                    );

	            // Store authentication
	            SecurityContextHolder.getContext()
	                    .setAuthentication(authentication);
	        }

		    
		    filterChain.doFilter(request, response);
		
		
		
		
		
	}
	
	


}
