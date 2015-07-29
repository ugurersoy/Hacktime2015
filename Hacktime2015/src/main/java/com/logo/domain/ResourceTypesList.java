package com.logo.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public final class ResourceTypesList
{
	public ResourceTypesList() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlElement(name="Data")
	private ArrayList<ResourceTypes> resourceTypes;

	public ArrayList<ResourceTypes> getResourceTypes() {
		return resourceTypes;
	}

	public void setResourceTypes(ArrayList<ResourceTypes> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}
	
}
