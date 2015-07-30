package com.logo.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class ResourceDashboardList {

	public ResourceDashboardList() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlElement(name="Data")
	private ArrayList<ResourceDashboard> resourceDashboards;

	public ArrayList<ResourceDashboard> getResourceDashboards() {
		return resourceDashboards;
	}

	public void setResourceDashboards(ArrayList<ResourceDashboard> resourceDashboards) {
		this.resourceDashboards = resourceDashboards;
	}


}
