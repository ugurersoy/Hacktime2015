package com.logo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class Reservation {
	
	private static String[] stats = {"Bekliyor", "Onaylandı", "Reddedildi"};
	
	@XmlElement(name="Id")
	private int id;
	@XmlElement(name="Status")
	private int status;
	@XmlElement(name="Name")
	private String name;
	@XmlElement(name="SurName")
	private String surname;
	@XmlElement(name="ResourceId")
	private int resourceId;
	@XmlElement(name="ResourceTitle")
	private String resourceName;
	@XmlElement(name="BeginDate")
	private String begDate;
	@XmlElement(name="EndDate")
	private String endDate;
	private int userId;
    
	


	
	
	public Reservation(int id, int status, String name, String surname, String resourceName, String begDate,
			String endDate) {
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
	
	public String getStatuss() {
		return stats[status];
	}
	
	public String getBegDate() {
		return begDate;
	}
	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public int getResourceId() {
		return resourceId;
	}



	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
	


	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		/*String beg="";
		String end="";
		try {
			beg = dt.format(new SimpleDateFormat().parse(begDate));
			end = dt.format(new SimpleDateFormat().parse(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "{\"status\":\""+status+"\",\"name\":\""+name+"\",\"surname\":\""+surname+"\",\"resourceId\":"+resourceId
				+ ",\"beginDate\":\""+begDate+"\",\"endDate\":\""+ endDate +"\",\"userid\":"+userId+"}";
	}

	

}
