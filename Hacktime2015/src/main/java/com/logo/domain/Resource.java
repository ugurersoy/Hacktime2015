package com.logo.domain;

public class Resource {
	
	private Long id;
	private String title;
	private int capacity;
	
	private ResourceTypes resourceTypes;

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(Long id, String title, int capacity, ResourceTypes resourceTypes) {
		super();
		this.id = id;
		this.title = title;
		this.capacity = capacity;
		this.resourceTypes = resourceTypes;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ResourceTypes getResourceTypes() {
		return resourceTypes;
	}

	public void setResourceTypes(ResourceTypes resourceTypes) {
		this.resourceTypes = resourceTypes;
	}
	
	
	

}
