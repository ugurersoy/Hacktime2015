package com.logo.domain;

public final class User
{
	private Long Id;
	private String name;
	private String surName;
	private String password;
	private String phoneNr;
	private String role;
	
	private boolean isAdmin;
	
	private Firms firms;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Long id, String name, String surName, String password, String phoneNr, String role, boolean isAdmin,
			Firms firms) {
		super();
		Id = id;
		this.name = name;
		this.surName = surName;
		this.password = password;
		this.phoneNr = phoneNr;
		this.role = role;
		this.isAdmin = isAdmin;
		this.firms = firms;
	}
	public Firms getFirms() {
		return firms;
	}
	public void setFirms(Firms firms) {
		this.firms = firms;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
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
	public String getPhoneNr() {
		return phoneNr;
	}
	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
