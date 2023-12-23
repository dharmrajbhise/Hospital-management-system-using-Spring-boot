package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsImplementation implements UserDetailsService {
	
	@Autowired
	private UsersRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users u1 = repo.findByUsername(username);
		if(u1 == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		
		return new org.springframework.security.core.userdetails.User(u1.getUsername(), u1.getPassword(),
                getAuthorities(u1.getRole()));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
	}

}
