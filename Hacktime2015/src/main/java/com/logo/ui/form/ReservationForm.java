package com.logo.ui.form;

import java.util.ArrayList;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.logo.HacktimeUI;
import com.logo.component.ComboBoxBean;
import com.logo.domain.Reservation;
import com.logo.domain.Resource;
import com.logo.domain.ResourceTypes;
import com.logo.rest.RestService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ReservationForm extends AbstractForm<Reservation>{

	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Adı");
	private TextField surname= new TextField("Soyadı");
	
	private ComboBox resourceSelect;
	private ComboBox resourceTypeeSelect;
	private DateField begDate = new DateField("Başlangıç Tarihi");
	private DateField endDate = new DateField("Bitiş Tarihi");
	
	

	@Override
	protected Component createContent()
	{
		begDate.setResolution(Resolution.SECOND);
		endDate.setResolution(Resolution.SECOND);
		begDate.setDateFormat("yyyy-MM-dd hh:mm:ss");
		endDate.setDateFormat("yyyy-MM-dd hh:mm:ss");
		name.setNullRepresentation("");
		surname.setNullRepresentation("");
		initComboSelect();
		return new MFormLayout(name, surname,resourceTypeeSelect, resourceSelect,begDate,endDate, getToolbar()).withMargin(true);
	}

	

	private void initComboSelect()
	{
		resourceSelect = new ComboBox("Kaynaklar");
		resourceSelect.setItemCaptionPropertyId("title");
		resourceSelect.setImmediate(true);
		
		ArrayList<ResourceTypes> types = RestService.instance.getResourceTypes().getResourceTypes();
		final BeanItemContainer<ResourceTypes> containertype = new BeanItemContainer<ResourceTypes>(ResourceTypes.class);
		for(ResourceTypes item:types){
			containertype.addItem(item);
		}
		resourceSelect.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Resource res = (Resource)event.getProperty().getValue();
				getEntity().setResourceId(res.getId());
				getEntity().setResourceName(res.getTitle());
				
			}
		});
		resourceTypeeSelect = new ComboBox("Kaynak Tipleri", containertype);
		resourceTypeeSelect.setItemCaptionPropertyId("title");
		resourceTypeeSelect.setImmediate(true);
		resourceTypeeSelect.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				ResourceTypes value = (ResourceTypes)event.getProperty().getValue();
				ArrayList<Resource> resources = RestService.instance.getResourceByType(value.getId()).getResources();
				final BeanItemContainer<Resource> container = new BeanItemContainer<Resource>(Resource.class);
				for(Resource item:resources){
					container.addItem(item);
				}
				resourceSelect.setContainerDataSource(container);
			}
		});
	}
	
	@Override
	public Window openInModalPopup() {
		getFieldGroup().getField("name").setEnabled(false);
		getFieldGroup().getField("surname").setEnabled(false);
		return super.openInModalPopup();
	}
	
}
