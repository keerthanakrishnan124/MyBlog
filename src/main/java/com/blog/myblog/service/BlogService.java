package com.blog.myblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.myblog.model.Blog;
import com.blog.myblog.model.BlogListDTO;
import com.blog.myblog.model.BlogUpdateDTO;
import com.blog.myblog.model.BlogDTO;
import com.blog.myblog.model.Users;
import com.blog.myblog.repository.BlogRepo;
import com.blog.myblog.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepo blogRepo;
	@Autowired
	private UserRepo userRepo;
	
	private BlogDTO convertToDTO(Blog blog) {
		return new BlogDTO(
				blog.getId(),
				blog.getCategory(),
				blog.getTitle(),
				blog.getContent(),
				blog.getAuthor().getFullname(),
				blog.getViews(),
				blog.getCreatedAt(),
				blog.getUpdatedAt());
	}

	public BlogDTO createBlog(BlogUpdateDTO blog) {
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		Optional<Users> user=userRepo.findByEmail(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		Users author=user.get();
		Blog blogobj=new Blog();
		blogobj.setTitle(blog.getTitle());
		blogobj.setContent(blog.getContent());
		blogobj.setAuthor(author);
		blogobj.setViews(0);
		blogobj.setCategory(blog.getCategory());
		blogobj.setBlogStatus("DRAFT");
		blogobj.setCreatedAt(LocalDateTime.now());
		blogobj.setUpdatedAt(LocalDateTime.now());
		blogRepo.save(blogobj);
		
		return convertToDTO(blogobj);
			
	}

	public List<BlogListDTO> view() {
		List<Blog> blogs= blogRepo.findByBlogStatus("PUBLISHED");
		List<BlogListDTO> response=blogs.stream().map(blog -> new BlogListDTO(
				blog.getId(),
				blog.getTitle(),
				blog.getAuthor().getFullname(),
				blog.getCategory(),
				blog.getCreatedAt(),
				blog.getViews()
				)).collect(Collectors.toList());
		return response;
	}

	public BlogDTO viewBlog(int id) {
		Optional<Blog> blog=blogRepo.findById(id);
		if(blog.isPresent()) {
			Blog b=blog.get();
			if(b.getBlogStatus().equals("PUBLISHED")) {
			b.setViews(b.getViews()+1);
			blogRepo.save(b);
			return convertToDTO(b);
			}
			System.out.println("Not accessible");
		}
		return null;
		
	}

	public BlogDTO updateBlog(BlogUpdateDTO blog,int id) {
		Optional<Blog> blogobj=blogRepo.findById(id);
		
		if(blogobj.isPresent()) {
			Blog b=blogobj.get();
			Users userobj=b.getAuthor();
			Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			String username=authentication.getName();
			Users user=userRepo.findByEmail(username).get();
			if(userobj.equals(user) && b.getBlogStatus().equals("DRAFT")) {
				b.setCategory(blog.getCategory());
				b.setTitle(blog.getTitle());
				b.setContent(blog.getContent());
				b.setUpdatedAt(LocalDateTime.now());
				
				blogRepo.save(b);
				
				return convertToDTO(b);
						
			}
			System.out.println("Not accessible");
			
		}
		System.out.println("Blog not present");
		return null;
	}

	public BlogDTO publishBlog(int id) {
		
		Optional<Blog> blog=blogRepo.findById(id);
		if(blog.isPresent()) {
			Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			String username=authentication.getName();
			Users user=userRepo.findByEmail(username).get();
			Blog blogobj=blog.get();
			if(blogobj.getAuthor().equals(user) && blogobj.getBlogStatus().equals("DRAFT")) {
				blogobj.setBlogStatus("PUBLISHED");
				blogobj.setUpdatedAt(LocalDateTime.now());
				blogRepo.save(blogobj);
				
				return convertToDTO(blogobj);
						
			}
			System.out.println("not accessible");
		}
		System.out.println("not present");
		return null;
	}

	public BlogDTO draftBlog(int id) {
		Optional<Blog> blog=blogRepo.findById(id);
		if(blog.isPresent()) {
			Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			String username=authentication.getName();
			Users user=userRepo.findByEmail(username).get();
			Blog blogobj=blog.get();
			if(blogobj.getAuthor().equals(user) && blogobj.getBlogStatus().equals("PUBLISHED")) {
				blogobj.setBlogStatus("DRAFT");
				blogobj.setUpdatedAt(LocalDateTime.now());
				blogRepo.save(blogobj);
				
				return convertToDTO(blogobj);
						
			}
			throw new EntityNotFoundException("Not accessible");
		}
		throw new EntityNotFoundException("Blog not found");
		
		
	}

}
