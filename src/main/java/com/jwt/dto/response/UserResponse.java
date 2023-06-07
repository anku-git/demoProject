package com.jwt.dto.response;

import lombok.Data;

@Data
public class UserResponse {
	private Long id;
	private String name;
	private String password;
	private String email;
	private String role;
}
