package com.logo.ui.form;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.logo.component.ComboBoxBean;
import com.logo.domain.Resource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class ResourcesForm extends AbstractForm<Resource>
{

	private static final long serialVersionUID = 1L;
	private TextField title = new TextField("Kaynak Adı");
	private TextField capacity = new TextField("Kapasite");
	// private TextField resourceTypeName = new TextField("Kaynak Tipi");
	private ComboBox resourceSelect;

	@Override
	protected Component createContent()
	{
		initMovieSelect();
		// resourceSelect.setItemCaptionPropertyId("id");
		return new MFormLayout(title, capacity, resourceSelect, getToolbar()).withMargin(true);
	}

	private void initMovieSelect()
	{
		final BeanItemContainer<ComboBoxBean> container = new BeanItemContainer<ComboBoxBean>(ComboBoxBean.class);
		container.addItem(new ComboBoxBean(1, "Mercury"));
		container.addItem(new ComboBoxBean(2, "Venus"));
		container.addItem(new ComboBoxBean(3, "Earth"));
		container.addItem(new ComboBoxBean(4, "Mars"));
		resourceSelect = new ComboBox("Kaynak Tipi", container);
		resourceSelect.setItemCaptionPropertyId("value");
		resourceSelect.setImmediate(true);
	}

}
