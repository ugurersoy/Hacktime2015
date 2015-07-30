package com.logo.domain;

import javax.xml.bind.annotation.XmlElement;

public class Resource
{
	@XmlElement(name="ResourceId")
	private int id;
	@XmlElement(name="ResourceTitle")
	private String title;
	@XmlElement(name="Capacity")
	private int capacity;
	@XmlElement(name="ResourceType")
	private int resourceType;
	@XmlElement(name="ResourceTypeTitle")
	private String resourceTypeName;


	public Resource()
	{
		super();
	}

	public Resource(int id, String title, int capacity, String resourceTypeName, ResourceTypes resourceTypes)
	{
		super();
		this.id = id;
		this.title = title;
		this.capacity = capacity;
		this.resourceTypeName = resourceTypeName;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getCapacity()
	{
		return capacity;
	}

	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	
	public int getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(int resourceType)
	{
		this.resourceType = resourceType;
	}

	public String getResourceTypeName()
	{
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName)
	{
		this.resourceTypeName = resourceTypeName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{\"title\":\""+title+"\",\"capacity\":"+capacity+",resourceType:"+resourceType+"}";
	}

}
