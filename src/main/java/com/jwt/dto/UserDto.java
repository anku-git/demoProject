package com.jwt.dto;

import lombok.Data;

@Data
public class UserDto {
	private String name;
	private String password;
	//private String roles;
	private String email;
	private String mobileNo;

}
