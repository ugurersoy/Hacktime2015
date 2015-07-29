package com.logo.ui.form;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.logo.domain.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class ResourcesForm extends AbstractForm<Resource>
{

	private static final long serialVersionUID = 1L;
	private TextField title = new TextField("Kaynak Adı");
	private TextField capacity = new TextField("Kapasite");
	private TextField resourceTypeName = new TextField("Kaynak Tipi");

	@Override
	protected Component createContent()
	{
		return new MFormLayout(title, capacity, resourceTypeName, getToolbar()).withMargin(true);
	}

}
