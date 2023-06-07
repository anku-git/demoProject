package com.jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.ForgetPasswordDto;
import com.jwt.dto.UserDto;
import com.jwt.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.jwt.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;

	public String role;

//	@ApiOperation(value = "Upload a file", consumes = "multipart/form-data")

	@PostMapping(value = "/user/save")
	public ResponseEntity<String> save(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.OK);
	}

	@PostMapping("/admin/save")
	public ResponseEntity<String> adminsave(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.saveAdmin(userDto), HttpStatus.OK);
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/user/get/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		log.info("get user by id");
		return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
	}
	@PostMapping("/user/forget/password")
	public String forgetPassword(@RequestBody ForgetPasswordDto dto) {
		return userService.forgetPassword(dto);
	}
	@PostMapping("/user/verify/otp")
	public String verifyOtp(@RequestBody ForgetPasswordDto dto) {
		return userService.verifyUserOtp(dto);
	}
}

/*
 * Authentication authentication= authenticationManager.authenticate( new
 * UsernamePasswordAuthenticationToken(authRequest.getUsername(),
 * authRequest.getPassword())); if(authentication.isAuthenticated()) { return
 * jwtService.generateToken(authRequest.getUsername()); }else { throw new
 * UsernameNotFoundException("invalid user request"); }
 */