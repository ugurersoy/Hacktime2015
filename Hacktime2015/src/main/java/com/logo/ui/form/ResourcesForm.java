package com.logo.ui.form;

import java.util.ArrayList;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.logo.component.ComboBoxBean;
import com.logo.domain.Resource;
import com.logo.domain.ResourceTypes;
import com.logo.rest.RestService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class ResourcesForm extends AbstractForm<Resource>
{

	private static final long serialVersionUID = 1L;
	private TextField title = new TextField("Kaynak Adı");
	private TextField capacity = new TextField("Kapasite");
	private ComboBox resourceSelect;

	@Override
	protected Component createContent()
	{
		initMovieSelect();
		// resourceSelect.setItemCaptionPropertyId("id");
		title.setNullRepresentation("");
		return new MFormLayout(title, capacity, resourceSelect, getToolbar()).withMargin(true);
	}

	private void initMovieSelect()
	{
		ArrayList<ResourceTypes> types = RestService.instance.getResourceTypes().getResourceTypes();
		final BeanItemContainer<ResourceTypes> container = new BeanItemContainer<ResourceTypes>(ResourceTypes.class);
		for(ResourceTypes item:types){
			container.addItem(item);
		}
		
		resourceSelect = new ComboBox("Kaynak Tipi", container);
		resourceSelect.setItemCaptionPropertyId("title");
		resourceSelect.setImmediate(true);
	}

}
