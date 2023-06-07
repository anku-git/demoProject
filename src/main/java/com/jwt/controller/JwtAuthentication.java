package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.config.JwtTokenUtil;
import com.jwt.dto.AuthRequest;
import com.jwt.service.UserInfoDetailsService;
import com.jwt.service.UserService;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
@RequestMapping("/api")
public class JwtAuthentication {
	@Autowired
	private UserInfoDetailsService infoUserDetails;
	@Autowired
	private JwtTokenUtil jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String role;

	@PostMapping("/user/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
		log.info("user authenticate");
		authenticate(authRequest.getUsername(), authRequest.getPassword());
		// final UserDetails userDetails =
		// infoUserDetails.loadUserByUsername(authRequest.getUsername());
		String token = infoUserDetails.userLogin(authRequest);
		return token;

		// return jwtService.generateToken(userDetails);

	}

	@PostMapping("/admin/authenticate")
	// @PreAuthorize("hasRole('ADMIN')")
	// @Secured("ROLE_ADMIN")
	public String authenticateAdminAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
		authenticate(authRequest.getUsername(), authRequest.getPassword());

		final UserDetails userDetails = infoUserDetails.loadUserByUsername(authRequest.getUsername());
		return jwtService.generateToken(userDetails);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);

		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);

		}
	}

}
