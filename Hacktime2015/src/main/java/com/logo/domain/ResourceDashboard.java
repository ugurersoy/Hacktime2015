package com.logo.domain;

import javax.xml.bind.annotation.XmlElement;

public class ResourceDashboard {

	
	 @XmlElement(name="ResourceId")
	 private int id;
	 
	 @XmlElement(name="ResourceCount")
	 private int resourceCount;
	 
	 @XmlElement(name="Title")
	 private String title;
	 
	 @XmlElement(name="Capacity")
	 private int capasity;

	 
	 public ResourceDashboard() {
		// TODO Auto-generated constructor stub
	}
 
	public ResourceDashboard(int id, int resourceCount, String title, int capasity) {
		super();
		this.id = id;
		this.resourceCount = resourceCount;
		this.title = title;
		this.capasity = capasity;
	}
	
	public int getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

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
	public int getCapasity() {
		return capasity;
	}
	public void setCapasity(int capasity) {
		this.capasity = capasity;
	}
}
