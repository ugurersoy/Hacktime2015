package com.logo.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.logo.domain.Reservation;
import com.logo.domain.ResourceTypes;
import com.logo.event.DashboardEvent.BrowserResizeEvent;
import com.logo.rest.RestService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class ScheduleView extends CssLayout implements View
{
	private Calendar calendar;
	private final Component tray;
	private int resourceTypeIndex = 1; 
	public ScheduleView()
	{
		setSizeFull();
		addStyleName("schedule");
		
		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();
		tabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		tabs.addComponent(buildCalendarView());
		addComponent(tabs);
		tray = buildTray();
		addComponent(tray);
	}

	private Component buildCalendarView()
	{
		VerticalLayout calendarLayout = new VerticalLayout();
		calendarLayout.setCaption("Takvim");
		calendarLayout.setMargin(true);
		
		
		ArrayList<ResourceTypes> types = RestService.instance.getResourceTypes().getResourceTypes();
		final BeanItemContainer<ResourceTypes> containertype = new BeanItemContainer<ResourceTypes>(ResourceTypes.class);
		for(ResourceTypes item:types){
			containertype.addItem(item);
		}
		ComboBox resourceTypeSelect = new ComboBox("Kaynak Tipleri", containertype);
		resourceTypeSelect.setItemCaptionPropertyId("title");
		resourceTypeSelect.setValue(containertype.getIdByIndex(0));
		resourceTypeSelect.setImmediate(true);
		
		resourceTypeSelect.addValueChangeListener(new ValueChangeListener()
		{
			
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				ResourceTypes res = (ResourceTypes)event.getProperty().getValue();
				resourceTypeIndex = res.getId();
				MovieEventProvider mep = new MovieEventProvider();
				mep.getEvents(null, null);
				calendar.setImmediate(true);
			}
		});
		
		HorizontalLayout bottomLayout = new HorizontalLayout(resourceTypeSelect);
		GridLayout grid= new GridLayout(3,3);
		grid.setWidth("100%");
		grid.addComponent(bottomLayout,2,0);
		grid.setComponentAlignment(bottomLayout, Alignment.TOP_RIGHT);
		calendarLayout.addComponent(grid);
		
		calendar = new Calendar(new MovieEventProvider());
		calendar.setWidth(100.0f, Unit.PERCENTAGE);
		calendar.setHeight(1000.0f, Unit.PIXELS);

		calendarLayout.addComponent(calendar);

		calendar.setFirstVisibleHourOfDay(8);
		calendar.setLastVisibleHourOfDay(7);
		calendar.setLocale(new Locale("tr", "TR"));
		java.util.Calendar initialView = java.util.Calendar.getInstance();
		initialView.add(java.util.Calendar.DAY_OF_WEEK, -initialView.get(java.util.Calendar.DAY_OF_WEEK) + 1);
		calendar.setStartDate(initialView.getTime());

		initialView.add(java.util.Calendar.DAY_OF_WEEK, 6);
		calendar.setEndDate(initialView.getTime());
		
		return calendarLayout;
	}

	private Component buildTray()
	{
		final HorizontalLayout tray = new HorizontalLayout();
		tray.setWidth(100.0f, Unit.PERCENTAGE);
		tray.addStyleName("tray");
		tray.setSpacing(true);
		tray.setMargin(true);

		Label warning = new Label("You have unsaved changes made to the schedule");
		warning.addStyleName("warning");
		warning.addStyleName("icon-attention");
		tray.addComponent(warning);
		tray.setComponentAlignment(warning, Alignment.MIDDLE_LEFT);
		tray.setExpandRatio(warning, 1);

		return tray;
	}

	@Subscribe
	public void browserWindowResized(final BrowserResizeEvent event)
	{
		if (Page.getCurrent().getBrowserWindowWidth() < 800)
		{
			calendar.setEndDate(calendar.getStartDate());
		}
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
	}

	private class MovieEventProvider implements CalendarEventProvider
	{

		@Override
		public List<CalendarEvent> getEvents(final Date startDate, final Date endDate)
		{
			ArrayList<Reservation> reservations = RestService.instance.getScheduleList(resourceTypeIndex).getReservations();
			
			List<CalendarEvent> result = new ArrayList<CalendarEvent>();
			
			for (Reservation reservation : reservations)
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date begDate = null;
				Date endDate2 = null;
				try
				{
					begDate = formatter.parse(reservation.getBegDate());
					endDate2 = formatter.parse(reservation.getEndDate());
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.add(java.util.Calendar.HOUR, 1);
				result.add(new ReservationEvent(begDate, endDate2, reservation));
			}
			return result;
		}
	}

	public final class ReservationEvent implements CalendarEvent
	{

		private Date start;
		private Date end;
		private Reservation reservation;

		public ReservationEvent(final Date start, final Date end, final Reservation resource)
		{
			this.start = start;
			this.end = end;
			this.reservation = resource;
		}

		@Override
		public Date getStart()
		{
			return start;
		}

		@Override
		public Date getEnd()
		{
			return end;
		}

		@Override
		public String getDescription()
		{
			return "";
		}

		@Override
		public String getStyleName()
		{
			return String.valueOf(1);
		}

		@Override
		public boolean isAllDay()
		{
			return false;
		}

		public Reservation getResource()
		{
			return reservation;
		}

		public void setResource(final Reservation resource)
		{
			this.reservation = resource;
		}

		public void setStart(final Date start)
		{
			this.start = start;
		}

		public void setEnd(final Date end)
		{
			this.end = end;
		}

		@Override
		public String getCaption()
		{
			return reservation.getResourceName();
		}

	}

}
