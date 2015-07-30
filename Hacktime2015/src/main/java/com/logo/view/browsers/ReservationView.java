package com.logo.view.browsers;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.viritin.FilterableListContainer;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.google.common.eventbus.Subscribe;
import com.logo.HacktimeUI;
import com.logo.domain.Reservation;
import com.logo.domain.Resource;
import com.logo.domain.Transaction;
import com.logo.event.DashboardEvent.BrowserResizeEvent;
import com.logo.event.DashboardEvent.TransactionReportEvent;
import com.logo.ui.form.ReservationForm;
import com.logo.ui.form.ResourcesForm;
import com.logo.event.DashboardEventBus;
import com.logo.rest.RestService;
import com.logo.view.DashboardViewType;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "unchecked" })
public final class ReservationView extends VerticalLayout implements View {

    private final Table table;
    private Button createButton;
	private Button deleteButton;
	private Button updateButton;
    
//    private Button createReport;
    private static final DateFormat DATEFORMAT = new SimpleDateFormat(
            "MM/dd/yyyy hh:mm:ss a");
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    private static final String[] DEFAULT_COLLAPSIBLE = { "name", "surname",
            "resourcename", "status", "begdate", "enddate" };

    public ReservationView() {
    	
        setSizeFull();
        addStyleName("reservation");
        DashboardEventBus.register(this);

        addComponent(buildToolbar());

        table = buildTable();
        addComponents(table,createGridLayout());
        setExpandRatio(table, 1);
    }
    
    
    private GridLayout createGridLayout(){
    	createButton = new Button("Ekle");
		createButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		createButton.setIcon(FontAwesome.SAVE);
		deleteButton = new Button("Sil");
		deleteButton.setIcon(FontAwesome.TRASH_O);
		deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
		updateButton = new Button("Güncelle");
		updateButton.setIcon(FontAwesome.PENCIL);
		
		HorizontalLayout buttonLayout = new HorizontalLayout(createButton, deleteButton, updateButton);
		GridLayout grid= new GridLayout(3,3);
		grid.setWidth("100%");
		
		grid.addComponent(buttonLayout,2,0);
		grid.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);
		
		
		
		createButton.addClickListener(new ClickListener()
		{

			@Override
			public void buttonClick(ClickEvent event)
			{
				ReservationForm reservationForm = new ReservationForm();
				Reservation res = new Reservation();
				res.setName(HacktimeUI.currentUser.getName());
				res.setSurname(HacktimeUI.currentUser.getSurName());
				res.setUserId(HacktimeUI.currentUser.getId());
				reservationForm.setEntity(res);
				final Window popup = reservationForm.openInModalPopup();
				reservationForm.setSavedHandler(new SavedHandler<Reservation>()
				{

					@Override
					public void onSave(Reservation entity)
					{
						RestService.instance.persistReservation(entity);
						table.addItem(entity);
						popup.close();
						table.select(entity);
						table.setCurrentPageFirstItemId(entity);
					}
				});
			}
		});
		
		updateButton.addClickListener(new ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				Reservation reservation = (Reservation) table.getValue();
				ReservationForm reservationForm = new ReservationForm();
				reservationForm.setEntity(reservation);
				final Window popup = reservationForm.openInModalPopup();
				reservationForm.setSavedHandler(new SavedHandler<Reservation>()
				{
					@Override
					public void onSave(Reservation entity) {
						popup.close();
						table.sort();
						table.select(entity);
						table.setCurrentPageFirstItemId(entity);
					}
				});
			}
		});

		deleteButton.addClickListener(new ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				ConfirmDialog.show(UI.getCurrent(), "Confirm", "Emin misiniz?", "Tamam", "İptal",new ConfirmDialog.Listener() {
					
					@Override
					public void onClose(ConfirmDialog arg0) {
						if(arg0.isConfirmed()){
							RestService.instance.deleteReservation(((Reservation)table.getValue()).getId());  
							table.removeItem((Reservation)table.getValue());
						}
					}
				});
			}
		});
		
		return grid;
		
		
    }
    
   
    @Override
    public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        DashboardEventBus.unregister(this);
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        header.setWidth("100%");
        header.setMargin(new MarginInfo(true, true, true, true));
        Responsive.makeResponsive(header);

        Label title = new Label("Rezervasyon Listesi");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//        title.setIcon(icon);
        header.addComponent(title);

//        createReport = buildCreateReport();
        HorizontalLayout tools = new HorizontalLayout(buildFilter());
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
       
        GridLayout grid= new GridLayout(3,3);
		grid.setWidth("100%");
		
		grid.addComponent(tools,2,0);
		grid.setComponentAlignment(tools, Alignment.TOP_RIGHT);
        
        header.addComponent(grid);

        return header;
    }

//    private Button buildCreateReport() {
//        final Button createReport = new Button("Create Report");
//        createReport
//                .setDescription("Create a new report from the selected transactions");
//        createReport.addClickListener(new ClickListener() {
//            @Override
//            public void buttonClick(final ClickEvent event) {
//                createNewReportFromSelection();
//            }
//        });
//        createReport.setEnabled(false);
//        return createReport;
//    }

    private Component buildFilter() {
        final TextField filter = new TextField();
        filter.addTextChangeListener(new TextChangeListener() {
            @Override
            public void textChange(final TextChangeEvent event) {
                Filterable data = (Filterable) table.getContainerDataSource();
                data.removeAllContainerFilters();
                data.addContainerFilter(new Filter() {
                    @Override
                    public boolean passesFilter(final Object itemId,
                            final Item item) {

                        if (event.getText() == null
                                || event.getText().equals("")) {
                            return true;
                        }

                        return filterByProperty("name", item,
                                event.getText())
                                || filterByProperty("surname", item,
                                        event.getText())
                                || filterByProperty("resourceName", item,
                                        event.getText());

                    }

                    @Override
                    public boolean appliesToProperty(final Object propertyId) {
                        if (propertyId.equals("name")
                                || propertyId.equals("surname")
                                || propertyId.equals("resourceName")) {
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        filter.setInputPrompt("Filter");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        filter.addShortcutListener(new ShortcutListener("Clear",
                KeyCode.ESCAPE, null) {
            @Override
            public void handleAction(final Object sender, final Object target) {
                filter.setValue("");
                ((Filterable) table.getContainerDataSource())
                        .removeAllContainerFilters();
            }
        });
        return filter;
    }

    private Table buildTable() {
        final Table table = new Table() ;
//        {
      
//        	
//        	@Override
//            protected String formatPropertyValue(final Object rowId,
//                    final Object colId, final Property<?> property) {
//           
//            	String result = super.formatPropertyValue(rowId, colId,
//                        property);
//                
//                if (colId.equals("time")) {
//                
//                	result = DATEFORMAT.format(((Date) property.getValue()));
//                
//                }
//                else if (colId.equals("price")) {
//                    if (property != null && property.getValue() != null) {
//                        return "$" + DECIMALFORMAT.format(property.getValue());
//                    } else {
//                        return "";
//                    }
//                }
//                return result;
//            }
//        };
        
        table.addContainerProperty("id", Integer.class, null);
        table.addContainerProperty("name", String.class, "");
    	table.addContainerProperty("surname", String.class, "");
    	table.addContainerProperty("resourceName", String.class, "");
    	 table.addContainerProperty("begdate", Date.class, "");
     	table.addContainerProperty("enddate", Date.class, "");
     	table.addContainerProperty("status", int.class, "");
    		
        table.setSizeFull();
        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

        table.setColumnCollapsingAllowed(true);


        table.setColumnReorderingAllowed(true);
        ArrayList<Reservation> reservations ;
        if(HacktimeUI.currentUser.isAdmin())
        	reservations = RestService.instance.getReservationList().getReservations();
        else
        	reservations = RestService.instance.getReservationsByUser(HacktimeUI.currentUser.getId()).getReservations();
        boolean empty = reservations.isEmpty();
        if (empty) {
        	reservations.add(new Reservation());
        }
        table.setContainerDataSource(new TempReservationContainer(reservations));
        table.setSortContainerPropertyId("name");
        table.setSortAscending(false);



        table.setVisibleColumns("name", "surname", "resourceName", "begDate", "endDate",
                "status");
        table.setColumnHeaders("Adı", "Soyadı", "Kaynak Adı", "Başlangıç Tarhihi", "Bitiş Tarihi",
                "Drumu");

        table.setFooterVisible(true);
//        table.setColumnFooter("time", "Total");

//        table.setColumnFooter(
//                "price",
//                "$"
//                        + DECIMALFORMAT.format(HacktimeUI.getDataProvider()
//                                .getTotalSum()));

        // Allow dragging items to the reports menu
        table.setDragMode(TableDragMode.MULTIROW);

        table.addActionHandler(new TransactionsActionHandler());

//        table.addValueChangeListener(new ValueChangeListener() {
//            @Override
//            public void valueChange(final ValueChangeEvent event) {
//                if (table.getValue() instanceof Set) {
//                    Set<Object> val = (Set<Object>) table.getValue();
//                    createReport.setEnabled(val.size() > 0);
//                }
//            }
//        });
        table.setImmediate(true);
        if (empty) {
        	table.removeAllItems();
        }
        return table;
    }

    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (String propertyId : DEFAULT_COLLAPSIBLE) {
            if (table.isColumnCollapsed(propertyId) == Page.getCurrent()
                    .getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }

    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        // Some columns are collapsed when browser window width gets small
        // enough to make the table fit better.
        if (defaultColumnsVisible()) {
            for (String propertyId : DEFAULT_COLLAPSIBLE) {
                table.setColumnCollapsed(propertyId, Page.getCurrent()
                        .getBrowserWindowWidth() < 800);
            }
        }
        
    }

    private boolean filterByProperty(final String prop, final Item item,
            final String text) {
        if (item == null || item.getItemProperty(prop) == null
                || item.getItemProperty(prop).getValue() == null) {
            return false;
        }
        String val = item.getItemProperty(prop).getValue().toString().trim()
                .toLowerCase();
        if (val.contains(text.toLowerCase().trim())) {
            return true;
        }
        return false;
    }

//    void createNewReportFromSelection() {
//        UI.getCurrent().getNavigator()
//                .navigateTo(DashboardViewType.REPORTS.getViewName());
//        DashboardEventBus.post(new TransactionReportEvent(
//                (Collection<Transaction>) table.getValue()));
//    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }

    private class TransactionsActionHandler implements Handler {
        private final Action report = new Action("Ekle");

        private final Action discard = new Action("Sil");

        private final Action details = new Action("Güncelle");

        @Override
        public void handleAction(final Action action, final Object sender,
                final Object target) {
//            if (action == report) {
//                createNewReportFromSelection();
//            } else 
            if (action == discard) {
                Notification.show("Not implemented in this demo");
            } else if (action == details) {
                Item item = ((Table) sender).getItem(target);
                if (item != null) {
                    Long movieId = (Long) item.getItemProperty("movieId")
                            .getValue();
//                    MovieDetailsWindow.open(HacktimeUI.getDataProvider()
//                            .getMovie(movieId), null, null);
                }
            }
        }

        @Override
        public Action[] getActions(final Object target, final Object sender) {
            return new Action[] { details, report, discard };
        }
    }

    private class TempReservationContainer extends
            FilterableListContainer<Reservation> {

        public TempReservationContainer(
                final Collection<Reservation> collection) {
            super(collection);
        }

        // This is only temporarily overridden until issues with
        // BeanComparator get resolved.
        @Override
        public void sort(final Object[] propertyId, final boolean[] ascending) {
            final boolean sortAscending = ascending[0];
            final Object sortContainerPropertyId = propertyId[0];
            Collections.sort(getBackingList(), new Comparator<Reservation>() {
                @Override
                public int compare(final Reservation o1, final Reservation o2) {
                    int result = 0;
                    if ("name".equals(sortContainerPropertyId)) {
                        result = o1.getName().compareTo(o2.getName());
                    } else if ("surname".equals(sortContainerPropertyId)) {
                        result = o1.getSurname().compareTo(o2.getSurname());
                    } else if ("resourceName".equals(sortContainerPropertyId)) {
                        result = o1.getResourceName().compareTo(o2.getResourceName());
                    } else if ("begDate".equals(sortContainerPropertyId)) {
                        result = o1.getBegDate().compareTo(o2.getBegDate());
                    } else if ("endDate".equals(sortContainerPropertyId)) {
                        result = o1.getEndDate().compareTo(o2.getEndDate());
                    } else if ("status".equals(sortContainerPropertyId)) {
                    	
//                        result = o1.getStatus()=o2.getStatus();
                    }

                    if (!sortAscending) {
                        result *= -1;
                    }
                    return result;
                }
            });
        }

    }

}
