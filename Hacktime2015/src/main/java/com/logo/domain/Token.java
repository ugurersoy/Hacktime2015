package com.logo.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Token implements Serializable{
	
	@XmlElement(name="grant_type")
	private String grant_type;
	@XmlElement(name="username")
	private String username;
	@XmlElement(name="password")
	private String password;
	@XmlElement(name="branchcode")
	private int branchcode;
	
	public Token(){
		
	}
	
	public Token(String grant_type, String username, String password, int branchcode){
		this.grant_type = grant_type;
		this.username = username;
		this.password = password;
		this.branchcode = branchcode;
	}
	
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrantType(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getUsername() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getBranchcode() {
		return branchcode;
	}
	public void setBranchCode(int branchcode) {
		this.branchcode = branchcode;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "grant_type=" + grant_type + "&username=" + username + "&password=" + password +  "&branchcode=" + branchcode;
	}
	
	
}
