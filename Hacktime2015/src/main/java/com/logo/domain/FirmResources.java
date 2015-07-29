package com.logo.domain;

public class FirmResources {

	private Long id;
	private Firms firms;
	private Resource resource;
	
	
	
	public FirmResources() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FirmResources(Long id, Firms firms, Resource resource) {
		super();
		this.id = id;
		this.firms = firms;
		this.resource = resource;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Firms getFirms() {
		return firms;
	}
	public void setFirms(Firms firms) {
		this.firms = firms;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	
	
	
}
