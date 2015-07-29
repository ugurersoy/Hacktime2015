package com.logo.ui.form;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.logo.component.ComboBoxBean;
import com.logo.domain.Reservation;
import com.logo.domain.Resource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

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
		name.setNullRepresentation("");
		surname.setNullRepresentation("");
		initComboSelect();
		return new MFormLayout(name, surname,resourceSelect, resourceTypeeSelect,begDate,endDate, getToolbar()).withMargin(true);
	}

	

	private void initComboSelect()
	{
		final BeanItemContainer<ComboBoxBean> container = new BeanItemContainer<ComboBoxBean>(ComboBoxBean.class);
		container.addItem(new ComboBoxBean(1, "Mercury"));
		container.addItem(new ComboBoxBean(2, "Venus"));
		container.addItem(new ComboBoxBean(3, "Earth"));
		container.addItem(new ComboBoxBean(4, "Mars"));
		resourceSelect = new ComboBox("Seçiniz", container);
		resourceSelect.setItemCaptionPropertyId("value");
		resourceSelect.setImmediate(true);
		
		final BeanItemContainer<ComboBoxBean> container2 = new BeanItemContainer<ComboBoxBean>(ComboBoxBean.class);
		container2.addItem(new ComboBoxBean(1, "Mercury"));
		container2.addItem(new ComboBoxBean(2, "Venus"));
		container2.addItem(new ComboBoxBean(3, "Earth"));
		container2.addItem(new ComboBoxBean(4, "Mars"));
		resourceTypeeSelect = new ComboBox("Seçiniz", container);
		resourceTypeeSelect.setItemCaptionPropertyId("value");
		resourceTypeeSelect.setImmediate(true);
	}
	
}
