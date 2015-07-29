package com.logo.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public final class ResourceList
{
	public ResourceList() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlElement(name="Data")
	private ArrayList<Resource> resources;

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}
	
}
