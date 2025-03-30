package com.blog.myblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.myblog.model.Users;
import com.blog.myblog.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> user=repo.findByEmail(username);
		if(user.isPresent()) {
			Users usrobj=user.get();
			return User.builder()
					.username(usrobj.getEmail())
					.password(usrobj.getPassword())
					.roles(usrobj.getRole())
					.build();
					
		}
		else {
			throw new UsernameNotFoundException("No user found");
		}
	}

}
