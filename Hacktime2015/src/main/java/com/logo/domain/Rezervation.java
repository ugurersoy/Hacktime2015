package com.logo.domain;

import java.util.Date;

public class Rezervation {
	
	private Long id;
	
	private int status;
	
	private Date begDate;
	private Date endDate;
	
	private User user;
	private Resource resource;

	
	
	public Rezervation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Rezervation(Long id, int status, Date begDate, Date endDate, User user, Resource resource) {
		super();
		this.id = id;
		this.status = status;
		this.begDate = begDate;
		this.endDate = endDate;
		this.user = user;
		this.resource = resource;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getBegDate() {
		return begDate;
	}
	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	

}
