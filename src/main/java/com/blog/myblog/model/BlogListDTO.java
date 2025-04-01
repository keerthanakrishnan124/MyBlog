package com.blog.myblog.model;

import java.time.LocalDateTime;

public class BlogListDTO {
	
	private int id;
	private String title;
	private String author;
	private String category;
	private LocalDateTime createdAt;
	private int views;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public BlogListDTO(int id, String title, String author, String category, LocalDateTime createdAt, int views) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.createdAt = createdAt;
		this.views = views;
	}
	public BlogListDTO() {
		super();
	}
	
	
}
