package com.logo.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.logo.HacktimeUI;
import com.logo.domain.Reservation;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class RezervatioStatusWait extends Table {

	
    @Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        
    	String result = super.formatPropertyValue(rowId, colId, property);
       
        
        return result;
    }

    public RezervatioStatusWait() {
        setCaption("Onay Bekleyenler");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setColumnAlignment("name", Align.RIGHT);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setSizeFull();

        List<Reservation> rezervationName = new ArrayList<Reservation>(
                HacktimeUI.getDataProvider().getReservationStatusWait());
      
        Collections.sort(rezervationName, new Comparator<Reservation>() {
            @Override
            public int compare(final Reservation o1, final Reservation o2) {
            	
                return o2.getName().compareTo(o1.getSurname());
            }
        });

        
        setContainerDataSource(new BeanItemContainer<Reservation>(Reservation.class, rezervationName));

        setVisibleColumns("name", "surname");
        setColumnHeaders("Onay Bekleyen Kişi", "");
        setColumnExpandRatio("name", 2);
        setColumnExpandRatio("surname", 1);

        setSortContainerPropertyId("name");
        setSortAscending(false);
    }

}
