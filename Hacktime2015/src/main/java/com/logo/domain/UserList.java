package com.logo.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public final class UserList
{
	public UserList() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlElement(name="Data")
	private ArrayList<User> users;

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
}
