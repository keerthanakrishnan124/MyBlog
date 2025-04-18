package com.blog.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.myblog.model.Blog;
import com.blog.myblog.model.Users;
import com.blog.myblog.repository.BlogRepo;
import com.blog.myblog.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private BlogRepo blogrepo;
	
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

	public Users deleteUser() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		Users user=repo.findByEmail(username).orElseThrow( () -> new UsernameNotFoundException(username));
		 List<Blog> blogs = blogrepo.findByAuthor(user);
		    for (Blog blog : blogs) {
		    	Users deleteduser=repo.findByEmail("deletedusers@system.com").orElseThrow( () -> new UsernameNotFoundException("deletedusers@system.com"));
		        blog.setAuthor(deleteduser);
		    }
		    blogrepo.saveAll(blogs);
		repo.delete(user);
		return user;
	}
}
