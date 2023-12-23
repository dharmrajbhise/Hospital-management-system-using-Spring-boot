package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.service.UserDetailsImplementation;

import com.example.demo.service.UserDetailsImplementation;

@Configuration
@Order(1)
public class UserSecurity {
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsImplementation();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable().authorizeHttpRequests().antMatchers("/","/register","/success").permitAll();
		
							httpSecurity.csrf().disable()
							.authorizeHttpRequests()
								.antMatchers("/admin/**")
								.hasAuthority("DOCTOR")
								.antMatchers("/user/**")
								.hasAuthority("PATIENT")
								.antMatchers("/reception/**")
								.hasAuthority("RECEPTION")
								.antMatchers("/medical/**")
								.hasAuthority("MEDICAL")
							.and()
							.formLogin()
								.loginPage("/login")
								.loginProcessingUrl("/login")
							.and()
								.logout()
								.logoutUrl("/logout")
								.logoutSuccessUrl("/")
							.permitAll()
							.and()
								.exceptionHandling()
								.accessDeniedPage("/error");
		
		return httpSecurity.build();	
	}

}
