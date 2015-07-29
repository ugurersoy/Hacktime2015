package com.logo.domain;

public class ResourceTypes {

	private Long id;
	private String title;
	
	
	public ResourceTypes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResourceTypes(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
