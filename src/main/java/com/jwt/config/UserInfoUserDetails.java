package com.jwt.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jwt.entity.Role;
import com.jwt.entity.UserEntity;
public class UserInfoUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private Set<GrantedAuthority> authorities;
	private UserEntity entity;
	
	public UserInfoUserDetails(UserEntity entity){
		this.entity=entity;
	
		
	}
	
	@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return entity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
			.collect(Collectors.toList());
			
			
//		List<Role> roles=entity.getRoles();
//		List<SimpleGrantedAuthority> authorities=new ArrayList<>();
//		for(Role role :roles) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
		//}
		
		//return authorities;
	}

	@Override
	public String getPassword() {
		
		return entity.getPassword();
	}

	@Override
	public String getUsername() {
		return entity.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
