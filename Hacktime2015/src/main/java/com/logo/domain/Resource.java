package com.logo.domain;

public class Resource
{

	private int id;
	private String title;
	private int capacity;
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

	public String getResourceTypeName()
	{
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName)
	{
		this.resourceTypeName = resourceTypeName;
	}

}
