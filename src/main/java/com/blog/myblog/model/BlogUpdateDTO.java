package com.blog.myblog.model;

public class BlogUpdateDTO {
	String category;
	String title;
	String Content;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public BlogUpdateDTO() {
		super();
	}
	public BlogUpdateDTO(String category, String title, String content) {
		super();
		this.category = category;
		this.title = title;
		Content = content;
	}
	
	
}
