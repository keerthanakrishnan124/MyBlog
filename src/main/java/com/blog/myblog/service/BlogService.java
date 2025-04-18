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
	private Users getCurrentUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    return userRepo.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	private Users validateUserAccess(Blog blog, String allowedStatus) {
        Users user = getCurrentUser();
        if (!blog.getAuthor().equals(user)) {
            throw new EntityNotFoundException("You don't have permission to access this blog");
        }
        
        if (allowedStatus != null && !blog.getBlogStatus().equals(allowedStatus)) {
            throw new EntityNotFoundException("Blog must be in " + allowedStatus + " status");
        }
        
        return user;
    }

	public BlogDTO createBlog(BlogUpdateDTO blog) {
		Users author=getCurrentUser();
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
			Blog blog = blogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found"));
			
			if(blog.getBlogStatus().equals("PUBLISHED")) {
			blog.setViews(blog.getViews()+1);
			blogRepo.save(blog);
			return convertToDTO(blog);
			}
			throw new EntityNotFoundException("Blog is not published or does not exist.");
		
	}

	public BlogDTO updateBlog(BlogUpdateDTO blogDto,int id) {
		 Blog blog = blogRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Blog not found"));
	        
	        validateUserAccess(blog, "DRAFT");
	        
	        blog.setCategory(blogDto.getCategory());
	        blog.setTitle(blogDto.getTitle());
	        blog.setContent(blogDto.getContent());
	        blog.setUpdatedAt(LocalDateTime.now());
	        
	        return convertToDTO(blogRepo.save(blog));
		
	}

	 public BlogDTO publishBlog(int id) {
	        Blog blog = blogRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Blog not found"));
	        
	        validateUserAccess(blog, "DRAFT");
	        
	        blog.setBlogStatus("PUBLISHED");
	        blog.setUpdatedAt(LocalDateTime.now());
	        
	        return convertToDTO(blogRepo.save(blog));
	    }

	 public BlogDTO draftBlog(int id) {
	        Blog blog = blogRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Blog not found"));
	        
	        validateUserAccess(blog, "PUBLISHED");
	        
	        blog.setBlogStatus("DRAFT");
	        blog.setUpdatedAt(LocalDateTime.now());
	        
	        return convertToDTO(blogRepo.save(blog));
	    }

	 public void delete(int id) {
	        Blog blog = blogRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Blog not found"));
	        
	        validateUserAccess(blog, null);
	        blogRepo.delete(blog);
	    }

}
