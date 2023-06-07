package com.jwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.service.UserInfoDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtService;
	@Autowired
	private UserInfoDetailsService userInfoDetailsService;

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		log.info("inside doFilterInternal method");
//		String authHeader = request.getHeader("Authorization");
//		String token = null;
//		String username = null;
//		if (authHeader != null && authHeader.startsWith("Bearer")) {
//			token = authHeader.substring(7);
//			username = jwtService.extractUsername(token);
//		}
//		// error in this method
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = this.userInfoDetailsService.loadUserByUsername(username);
//
//			
//			if (jwtService.validateToken(token, userDetails)) {
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				usernamePasswordAuthenticationToken
//						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//			}
//		
//	}
//		filterChain.doFilter(request, response);
//	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtService.extractUsername(jwtToken);
			} catch (IllegalArgumentException e) {
			} catch (ExpiredJwtException e) {
			}
		} else {
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userInfoDetailsService.loadUserByUsername(username);
			if (jwtService.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}


/*
 * SecurityContextHolder.getContext().setAuthentication(adminAuth);
 * 
 * UserDetails userDetails=userInfoDetailsService.loadUserByUsername(username);
 * // UserDetails userDetails=adminDetailsService.loadUserByUsername(username);
 * // if(jwtService.validateToken(token, userDetails)) {
 * UsernamePasswordAuthenticationToken authToken=new
 * UsernamePasswordAuthenticationToken(userDetails,null,userDetails.
 * getAuthorities()); authToken.setDetails(new
 * WebAuthenticationDetailsSource().buildDetails(request));
 * SecurityContextHolder.getContext().setAuthentication(authToken); }
 */

/*
 * Authentication adminAuth = new UsernamePasswordAuthenticationToken(username,
 * adminDetailsService.loadUserByUsername(username).getAuthorities());
 */
