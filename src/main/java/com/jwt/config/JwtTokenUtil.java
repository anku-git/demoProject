package com.jwt.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenUtil {
	@Value("${jwt.secret}")
	private String secret;
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;


	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		//claims.put("name", userDetails.getUsername());
		claims.put("authorities",
				userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		return createToken(claims, userDetails.getUsername());
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))

				 .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
				 .signWith(getSignKey(),SignatureAlgorithm.HS256).
				setHeaderParam("type", "jwt").compact();
				
		
	}

	private Key getSignKey() {
		byte[] keyByte = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyByte);
	}

}
