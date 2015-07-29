package com.logo.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TokenResponse {
	
	@XmlElement(name="access_token")
	private String access_token;
	@XmlElement(name="token_type")
	private String token_type;
	@XmlElement(name="expires_in")
	private String expires_in;
	@XmlElement(name="refresh_token")
	private String refresh_token;
	@XmlElement(name="userName")
	private String userName;
	@XmlElement(name="branchcode")
	private int branchcode;
	
	public static TokenResponse instance;
	
	public TokenResponse(){
		
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(int branchcode) {
		this.branchcode = branchcode;
	}
	
	
}