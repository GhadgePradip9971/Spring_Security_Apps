package com.zomato.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Zomato_users")
@Data
public class UserEntity {
	@Id
	private String emailId;
	private String password;
	private String fullName;
	private String contactNumber;

}
