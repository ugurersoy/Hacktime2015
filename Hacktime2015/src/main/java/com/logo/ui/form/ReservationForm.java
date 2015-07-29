package com.logo.ui.form;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.logo.domain.Reservation;
import com.logo.domain.Resource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

public class ReservationForm extends AbstractForm<Reservation>{

	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Adı");
	private TextField surname= new TextField("Soyadı");
	
	private TextField resourceName = new TextField("Kaynak Adı");
	private TextField resourceTypeName = new TextField("Kaynak Tipi");
	private DateField begDate = new DateField("Başlangıç Tarihi");
	private DateField endDate = new DateField("Bitiş Tarihi");
	
	

	@Override
	protected Component createContent()
	{
		begDate.setResolution(Resolution.SECOND);
		endDate.setResolution(Resolution.SECOND);
		return new MFormLayout(name, surname,resourceName,resourceTypeName,begDate,endDate).withMargin(true);
	}

	
}
