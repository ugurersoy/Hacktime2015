package com.logo.domain;

import javax.xml.bind.annotation.XmlElement;

public class ResourceTypes {

	@XmlElement(name="Id")
	private Long id;
	@XmlElement(name="Title")
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
