package com.logo.domain;

import javax.xml.bind.annotation.XmlElement;
public final class User
{
	@XmlElement(name="Id")
	private int Id;
	@XmlElement(name="Name")
	private String name;
	@XmlElement(name="SurName")
	private String surName;
	@XmlElement(name="Password")
	private String password;
	@XmlElement(name="Email")
	private String email;
	@XmlElement(name="IsAdmin")
	private boolean isAdmin;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String name, String surName, String password, String phoneNr, String role, boolean isAdmin,
			Firms firms) {
		super();
		Id = id;
		this.name = name;
		this.surName = surName;
		this.password = password;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
