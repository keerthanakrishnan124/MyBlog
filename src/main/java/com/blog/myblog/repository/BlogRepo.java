package com.blog.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.myblog.model.Blog;
import com.blog.myblog.model.Users;

public interface BlogRepo extends JpaRepository<Blog, Integer>{

	List<Blog> findByBlogStatus(String status);

	List<Blog> findByAuthor(Users user);

	@Query("SELECT b FROM Blog b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Blog> searchbyTitleorCategory(@Param("keyword") String keyword);
}
