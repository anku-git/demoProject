package com.jwt.helper;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OtpHelper {
	private String otp;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String generateOtp() {
		Random random=new Random();
		Integer otp=100000+random.nextInt(900000);
		 
		return String.valueOf(otp);
		 
		
	}
	public String sendOtpToUser(String mobileNo) {
		otp=generateOtp();
		String key="4917de67-4756-11ea-9fa5-0200cd936042";
		String templeteName="FTOPL";
		String url="https://2factor.in/API/V1/"+key+"/SMS/"+mobileNo+"/"+otp+"/"+templeteName;
		ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,null,String.class);
		return otp;
	}

}
