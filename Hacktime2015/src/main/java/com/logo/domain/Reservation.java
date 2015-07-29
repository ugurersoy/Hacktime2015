package com.logo.domain;

import java.util.Date;

public class Reservation {
	
	private int id;
	
	private int status;
	
	private String name;
	private String surname;
	private String resourceName;
	
	private Date begDate;
	private Date endDate;
    
	


	
	
	public Reservation(int id, int status, String name, String surname, String resourceName, Date begDate,
			Date endDate) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.surname = surname;
		this.resourceName = resourceName;
		this.begDate = begDate;
		this.endDate = endDate;
	}



	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public String getResourceName() {
		return resourceName;
	}



	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
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

	

}
