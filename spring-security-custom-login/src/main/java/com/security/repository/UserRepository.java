package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User>findByUsername(String username);//for log in
	boolean existsByUsername(String username);//it is regstration

}
