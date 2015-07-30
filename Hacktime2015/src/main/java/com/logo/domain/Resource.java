package com.logo.domain;

import javax.xml.bind.annotation.XmlElement;

public class Resource
{
	@XmlElement(name="Id")
	private int id;
	@XmlElement(name="ResourceId")
	private int resourceId;
	@XmlElement(name="Title")
	private String title;
	@XmlElement(name="ResourceTitle")
	private String resourceTitle;
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
	
	public int getResourceId()
	{
		return resourceId;
	}

	public void setResourceId(int id)
	{
		this.resourceId = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getResourceTitle()
	{
		return resourceTitle;
	}

	public void setResourceTitle(String title)
	{
		this.resourceTitle = title;
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
		return "{\"title\":\""+title+"\",\"capacity\":"+capacity+",resourceTypeId:"+resourceType+"}";
	}

}
