package com.jwt.dto;

import lombok.Data;

@Data
public class ForgetPasswordDto {
	private String mobileNo;
//	private String email;
	private String otp;

}
