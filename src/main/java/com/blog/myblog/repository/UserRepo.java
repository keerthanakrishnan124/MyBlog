package com.blog.myblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.myblog.model.Users;

public interface UserRepo extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByEmail(String username);
}
