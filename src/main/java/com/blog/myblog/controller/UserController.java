package com.blog.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.myblog.model.Users;
import com.blog.myblog.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
		
	@PostMapping("/register")
	public String register(@RequestBody Users user) {
		service.register(user);
		return "success";
	}
	
	@GetMapping("admin/users")
	public List<Users> getUsers(){
		return service.getAllUsers();
	}
	
	@GetMapping("/deleteprofile")
	public void deleteUser() {
		service.deleteUser();
	}
	
	
}
