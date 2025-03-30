package com.blog.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.myblog.model.Users;
import com.blog.myblog.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Users register(Users user) {
		
		user.setRole("USER");
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}

	public List<Users> getAllUsers() {
		return repo.findAll();
	}
}
