package com.logo.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;
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
//		DashboardEventBus.register(this);
		HorizontalLayout hz = new HorizontalLayout();
		
		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();
		tabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		tabs.addComponent(buildCalendarView());
		
		
		
		addComponent(tabs);

		tray = buildTray();
		addComponent(tray);
//		addComponent(grid);

//		injectMovieCoverStyles();
	}

//	@Override
//	public void detach()
//	{
//		super.detach();
//		// A new instance of ScheduleView is created every time it's navigated
//		// to so we'll need to clean up references to it on detach.
//		DashboardEventBus.unregister(this);
//	}

//	private void injectMovieCoverStyles()
//	{
//		// Add all movie cover images as classes to CSSInject
//		String styles = "";
//		for (int i = 0; i < 10; i++)
//		{
//            WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
//            String bg = "url(VAADIN/themes/" + UI.getCurrent().getTheme()
//                    + "/img/event-title-bg.png), url(" + "" + ")";
//            // IE8 doesn't support multiple background images
//            if (webBrowser.isIE() && webBrowser.getBrowserMajorVersion() == 8) {
//                bg = "url(" + "ajksdh" + ")";
//            }
//            styles += ".v-calendar-event-" + i
//                    + " .v-calendar-event-content {background-image:" + bg
//                    + ";}";
//		}
//		Page.getCurrent().getStyles().add(styles);
//	}

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

		// calendar.setHandler(new EventClickHandler() {
		// @Override
		// public void eventClick(final EventClick event) {
		// setTrayVisible(false);
		// MovieEvent movieEvent = (MovieEvent) event.getCalendarEvent();
		// MovieDetailsWindow.open(movieEvent.getMovie(),
		// movieEvent.getStart(), movieEvent.getEnd());
		// }
		// });
		calendarLayout.addComponent(calendar);

		calendar.setFirstVisibleHourOfDay(8);
		calendar.setLastVisibleHourOfDay(7);
//		calendar.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
//		TimeFormat tf = calendar.getTimeFormat();
//		calendar.setTimeFormat(tf);
		
//		calendar.setHandler(new BasicEventMoveHandler()
//		{
//			@Override
//			public void eventMove(final MoveEvent event)
//			{
//				CalendarEvent calendarEvent = event.getCalendarEvent();
//				if (calendarEvent instanceof ReservationEvent)
//				{
//					ReservationEvent editableEvent = (ReservationEvent) calendarEvent;
//
//					Date newFromTime = event.getNewStart();
//
//					// Update event dates
//					long length = editableEvent.getEnd().getTime() - editableEvent.getStart().getTime();
//					setDates(editableEvent, newFromTime, new Date(newFromTime.getTime() + length));
////					setTrayVisible(true);
//				}
//			}
//
//			protected void setDates(final ReservationEvent event, final Date start, final Date end)
//			{
//				event.start = start;
//				event.end = end;
//			}
//		});
//		calendar.setHandler(new BasicEventResizeHandler()
//		{
//			@Override
//			public void eventResize(final EventResize event)
//			{
//				Notification.show("You're not allowed to change the movie duration");
//			}
//		});

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

//		ClickListener close = new ClickListener()
//		{
//			@Override
//			public void buttonClick(final ClickEvent event)
//			{
//				setTrayVisible(false);
//			}
//		};

//		Button confirm = new Button("Confirm");
//		confirm.addStyleName(ValoTheme.BUTTON_PRIMARY);
//		confirm.addClickListener(close);
//		tray.addComponent(confirm);
//		tray.setComponentAlignment(confirm, Alignment.MIDDLE_LEFT);
//
//		Button discard = new Button("Discard");
//		discard.addClickListener(close);
//		discard.addClickListener(new ClickListener()
//		{
//			@Override
//			public void buttonClick(final ClickEvent event)
//			{
//				calendar.markAsDirty();
//			}
//		});
//		tray.addComponent(discard);
//		tray.setComponentAlignment(discard, Alignment.MIDDLE_LEFT);
		return tray;
	}

//	private void setTrayVisible(final boolean visible)
//	{
//		final String styleReveal = "v-animate-reveal";
//		if (visible)
//		{
//			tray.addStyleName(styleReveal);
//		}
//		else
//		{
//			tray.removeStyleName(styleReveal);
//		}
//	}

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
//				result.add(new ReservationEvent(java.util.Calendar.getInstance().getTime(), cal.getTime(), reservation));
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
