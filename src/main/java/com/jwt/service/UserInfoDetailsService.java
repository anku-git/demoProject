package com.jwt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.config.JwtTokenUtil;
import com.jwt.config.UserInfoUserDetails;
import com.jwt.dao.UserDao;
import com.jwt.dto.AuthRequest;
import com.jwt.entity.Role;
import com.jwt.entity.UserEntity;
import com.jwt.repository.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserInfoDetailsService implements UserDetailsService {
	@Autowired
	private UserDao userdao;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userInfo = userdao.findByUserEmail(username).orElseThrow();
		if (username.equals(userInfo.getEmail())) {
			log.info("user loadUserByUserName method");
			 return new UserInfoUserDetails(userInfo);
//			return new org.springframework.security.core.userdetails.User(userInfo.getName(), userInfo.getPassword(),
//					getAuthority(userInfo));

		}
		throw new UsernameNotFoundException("not found");
	}
	
	public UserDetails Login(String userName,String password) {
		return loadUserByUsername(userName);
		
	}
	public String  userLogin(AuthRequest authRequest) {
		UserEntity entity=userdao.findByUserEmail(authRequest.getUsername()).orElseThrow();
		UserDetails userDetails=this.Login(authRequest.getUsername(), authRequest.getPassword());
		String token=jwtTokenUtil.generateToken(userDetails);
		if(passwordEncoder.matches(authRequest.getPassword(), entity.getPassword()))
		{
			
		return token;
		}
		return token;
		
		
				
	}
	private Collection<? extends GrantedAuthority> getAuthority(UserEntity user) {
		//return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		String[]  userRoles=user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities=AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}	

}
