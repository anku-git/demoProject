package com.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jwt.service.UserInfoDetailsService;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = false,prePostEnabled = false,jsr250Enabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,proxyTargetClass = true)
public class WebSecurityConfig {
	@Autowired
	private UserInfoDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationFilter JwtAuthenticationFilter;
	@Autowired
	private UserAuthenticationEntryPoint authenticationEntryPoint;

	// we can use the two types of authentication inMemoryAuthentication and
	// DaoBased based

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
		return http.csrf().disable().authorizeHttpRequests()
				// .requestMatchers("/api/admin/authenticate") .hasRole("ADMIN")
				.requestMatchers("/api/user/save","/api/admin/save","/api/admin/authenticate","/api/user/forget/password",
				"/api/user/verify/otp",	"/api/excel","/api/user/authenticate","/api/user/login","/auth-app/**","/swagger-ui/**", "/v3/api-docs",
								 "/v2/api-docs",
								 "/swagger-resources/**").permitAll().
				and().authorizeHttpRequests().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// .and().authenticationProvider(authenticationProvider())
				.and().addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	return config.getAuthenticationManager();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		 
	}

	
	
	
	
	
	
	
	
	
	
	
	/*
	 * .and() .userDetailsService(adminDetailsService)
	 * .passwordEncoder(passwordEncoder());
	 */

//	
//	  @Bean public AuthenticationProvider authenticationProvider() {
//	  DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
//	  authenticationProvider.setUserDetailsService(userDetailsService());
//	  authenticationProvider.setPasswordEncoder(passwordEncoder());
//	return  authenticationProvider; 
//	} 
//	  @Bean public UserDetailsService userDetailsService() { 
//		  return new  UserInfoDetailsService(); 
//	  }
	  
	/* 
	 * 
	 * 
	 * @Bean public UserDetailsService userDetailsService() { return new
	 * UserInfoDetailsService(); }
	 * 
	 * @Bean public UserDetailsService adminDetailsService() { return new
	 * UserInfoDetailsService(); }
	 */
}
