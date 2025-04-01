package com.blog.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.myblog.model.BlogListDTO;
import com.blog.myblog.model.BlogUpdateDTO;
import com.blog.myblog.model.BlogDTO;
import com.blog.myblog.service.BlogService;

@RestController
//@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogService service;
	
	@PostMapping("/createDraft")
	public ResponseEntity<BlogDTO> createBlog(@RequestBody BlogUpdateDTO blog) {
		BlogDTO blogDTO=service.createBlog(blog);
		return ResponseEntity.ok(blogDTO);
	}
	
	
	@PutMapping("/updateDraft/{id}")
	public ResponseEntity<BlogDTO> updateBlog(@RequestBody BlogUpdateDTO blog,@PathVariable int id) {
		BlogDTO blogDTO=service.updateBlog(blog,id);
		return ResponseEntity.ok(blogDTO);
	}
	
	@GetMapping("/publishBlog/{id}")
	public ResponseEntity<BlogDTO> publishBlog(@PathVariable int id) {
		BlogDTO blogDTO=service.publishBlog(id);
		return ResponseEntity.ok(blogDTO);
	}
	
	@GetMapping("/draftBlog/{id}")
	public ResponseEntity<BlogDTO> draftBlog(@PathVariable int id) {
		BlogDTO blogDTO=service.draftBlog(id);
		return ResponseEntity.ok(blogDTO);
	}
	
	@GetMapping("/blogs")
	public List<BlogListDTO> view(){
		return service.view();
	}
	
	@GetMapping("/blogs/{id}")
	public BlogDTO response(@PathVariable int id) {
		return service.viewBlog(id);
	}
}
