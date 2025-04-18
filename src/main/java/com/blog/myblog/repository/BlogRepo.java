package com.blog.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.myblog.model.Blog;
import com.blog.myblog.model.Users;

public interface BlogRepo extends JpaRepository<Blog, Integer>{

	List<Blog> findByBlogStatus(String status);

	List<Blog> findByAuthor(Users user);

}
