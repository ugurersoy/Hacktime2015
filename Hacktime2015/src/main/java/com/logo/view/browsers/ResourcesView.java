package com.logo.view.browsers;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.vaadin.viritin.FilterableListContainer;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.google.common.eventbus.Subscribe;
import com.logo.HacktimeUI;
import com.logo.domain.Resource;
import com.logo.event.DashboardEvent.BrowserResizeEvent;
import com.logo.event.DashboardEventBus;
import com.logo.ui.form.ResourcesForm;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings(
{ "serial", "unchecked" })
public final class ResourcesView extends VerticalLayout implements View
{

	private final Table table;
	private Button createButton;
	private Button deleteButton;
	private Button updateButton;

	private static final String[] DEFAULT_COLLAPSIBLE =
	{ "title", "capacity", "resourceTypeName" };

	public ResourcesView()
	{
		setSizeFull();
		addStyleName("transactions");
		DashboardEventBus.register(this);

		addComponent(buildToolbar());

		table = buildTable();

		addComponents(table, createButtonLayout());
		setExpandRatio(table, 1);
	}

	private GridLayout createButtonLayout()
	{
		createButton = new Button("Ekle");
		createButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		createButton.setIcon(FontAwesome.SAVE);
		deleteButton = new Button("Sil");
		deleteButton.setIcon(FontAwesome.TRASH_O);
		deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
		updateButton = new Button("Güncelle");
		updateButton.setIcon(FontAwesome.PENCIL);

		HorizontalLayout buttonLayout = new HorizontalLayout(createButton, deleteButton, updateButton);
		GridLayout grid = new GridLayout(3, 3);
		grid.setWidth("100%");

		grid.addComponent(buttonLayout, 2, 0);
		grid.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);

		createButton.addClickListener(new ClickListener()
		{

			@Override
			public void buttonClick(ClickEvent event)
			{
				ResourcesForm resourceForm = new ResourcesForm();
				resourceForm.setEntity(new Resource());
				final Window popup = resourceForm.openInModalPopup();
				resourceForm.setSavedHandler(new SavedHandler<Resource>()
				{

					@Override
					public void onSave(Resource entity)
					{
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
				Resource resource = (Resource) table.getValue();
				ResourcesForm resourcesForm = new ResourcesForm();
				resourcesForm.setEntity(resource);
				final Window popup = resourcesForm.openInModalPopup();
				resourcesForm.setSavedHandler(new SavedHandler<Resource>()
				{
					@Override
					public void onSave(Resource entity) {
						popup.close();
						table.sort();
						table.select(entity);
						table.setCurrentPageFirstItemId(entity);
					}
				});
			}
		});

		return grid;
	}

	@Override
	public void detach()
	{
		super.detach();
		// A new instance of TransactionsView is created every time it's
		// navigated to so we'll need to clean up references to it on detach.
		DashboardEventBus.unregister(this);
	}

	private Component buildToolbar()
	{
		HorizontalLayout header = new HorizontalLayout();
		header.setMargin(new MarginInfo(true, true, true, true));
		header.addStyleName("viewheader");
		header.setWidth("100%");
		header.setSpacing(true);
		Responsive.makeResponsive(header);

		Label title = new Label("Kaynak Listesi");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(title);

		HorizontalLayout tools = new HorizontalLayout(buildFilter());
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		
		GridLayout grid = new GridLayout(3, 3);
		grid.setWidth("100%");

		grid.addComponent(tools, 2, 0);
		grid.setComponentAlignment(tools, Alignment.TOP_RIGHT);
		
		header.addComponent(grid);

		return header;
	}

	private Component buildFilter()
	{
		final TextField filter = new TextField();
		filter.addTextChangeListener(new TextChangeListener()
		{
			@Override
			public void textChange(final TextChangeEvent event)
			{
				Filterable data = (Filterable) table.getContainerDataSource();
				data.removeAllContainerFilters();
				data.addContainerFilter(new Filter()
				{
					@Override
					public boolean passesFilter(final Object itemId, final Item item)
					{

						if (event.getText() == null || event.getText().equals(""))
						{
							return true;
						}

						return filterByProperty("title", item, event.getText())
								|| filterByProperty("capacity", item, event.getText())
								|| filterByProperty("resourceTypeName", item, event.getText());

					}

					@Override
					public boolean appliesToProperty(final Object propertyId)
					{
						if (propertyId.equals("title") || propertyId.equals("capacity")
								|| propertyId.equals("resourceTypeName"))
						{
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
		// filter.addShortcutListener(new ShortcutListener("Clear",
		// KeyCode.ESCAPE, null)
		// {
		// @Override
		// public void handleAction(final Object sender, final Object target)
		// {
		// filter.setValue("");
		// ((Filterable)
		// table.getContainerDataSource()).removeAllContainerFilters();
		// }
		// });
		return filter;
	}

	private Table buildTable()
	{
		final Table table = new Table();
		// {
		// @Override
		// protected String formatPropertyValue(final Object rowId, final Object
		// colId, final Property<?> property)
		// {
		// String result = super.formatPropertyValue(rowId, colId, property);
		// if (colId.equals("time"))
		// {
		// result = DATEFORMAT.format(((Date) property.getValue()));
		// }
		// else if (colId.equals("price"))
		// {
		// if (property != null && property.getValue() != null)
		// {
		// return "$" + DECIMALFORMAT.format(property.getValue());
		// }
		// else
		// {
		// return "";
		// }
		// }
		// return result;
		// }
		// };

		table.addContainerProperty("id", Integer.class, null);
		table.addContainerProperty("title", String.class, "");
		table.addContainerProperty("capacity", String.class, "");
		table.addContainerProperty("resourceTypeName", String.class, "");

		table.setSizeFull();
		table.addStyleName(ValoTheme.TABLE_BORDERLESS);
		table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		table.addStyleName(ValoTheme.TABLE_COMPACT);
		table.setSelectable(true);

		table.setColumnCollapsingAllowed(true);
		// table.setColumnCollapsible("time", false);
		// table.setColumnCollapsible("price", false);

		table.setColumnReorderingAllowed(true);
		table.setContainerDataSource(
				new TempTransactionsContainer((Collection<Resource>) HacktimeUI.getDataProvider().getResources(10)));
		table.setSortContainerPropertyId("title");
		table.setSortAscending(false);

		table.setColumnAlignment("capacity", Align.RIGHT);

		table.setVisibleColumns("title", "capacity", "resourceTypeName");
		table.setColumnHeaders("Kaynak Adı", "Kapasite", "Kaynak Tipi");

		table.setFooterVisible(true);
		// table.setColumnFooter("time", "Total");

		// table.setColumnFooter(
		// "price",
		// "$"
		// + DECIMALFORMAT.format(HacktimeUI.getDataProvider()
		// .getTotalSum()));

		// Allow dragging items to the reports menu
		table.setDragMode(TableDragMode.MULTIROW);

		table.addActionHandler(new TransactionsActionHandler());

		// table.addValueChangeListener(new ValueChangeListener() {
		// @Override
		// public void valueChange(final ValueChangeEvent event) {
		// if (table.getValue() instanceof Set) {
		// Set<Object> val = (Set<Object>) table.getValue();
		// createReport.setEnabled(val.size() > 0);
		// }
		// }
		// });
		table.setImmediate(true);

		return table;
	}

	private boolean defaultColumnsVisible()
	{
		boolean result = true;
		for (String propertyId : DEFAULT_COLLAPSIBLE)
		{
			if (table.isColumnCollapsed(propertyId) == Page.getCurrent().getBrowserWindowWidth() < 800)
			{
				result = false;
			}
		}
		return result;
	}

	@Subscribe
	public void browserResized(final BrowserResizeEvent event)
	{
		// Some columns are collapsed when browser window width gets small
		// enough to make the table fit better.
		if (defaultColumnsVisible())
		{
			for (String propertyId : DEFAULT_COLLAPSIBLE)
			{
				table.setColumnCollapsed(propertyId, Page.getCurrent().getBrowserWindowWidth() < 800);
			}
		}
	}

	private boolean filterByProperty(final String prop, final Item item, final String text)
	{
		if (item == null || item.getItemProperty(prop) == null || item.getItemProperty(prop).getValue() == null)
		{
			return false;
		}
		String val = item.getItemProperty(prop).getValue().toString().trim().toLowerCase();
		if (val.contains(text.toLowerCase().trim()))
		{
			return true;
		}
		return false;
	}

	// void createNewReportFromSelection() {
	// UI.getCurrent().getNavigator()
	// .navigateTo(DashboardViewType.REPORTS.getViewName());
	// DashboardEventBus.post(new TransactionReportEvent(
	// (Collection<Transaction>) table.getValue()));
	// }

	@Override
	public void enter(final ViewChangeEvent event)
	{
	}

	private class TransactionsActionHandler implements Handler
	{
		private final Action newAciton = new Action("Ekle");

		private final Action deleteAction = new Action("Sil");

		private final Action updateAction = new Action("Güncelle");

		@Override
		public void handleAction(final Action action, final Object sender, final Object target)
		{
			if (action == newAciton)
			{
				Notification.show("Not implemented in this demo");
			}
			else if (action == deleteAction)
			{
				Notification.show("Not implemented in this demo");
			}
			else if (action == updateAction)
			{
				Item item = ((Table) sender).getItem(target);
				if (item != null)
				{
					Long movieId = (Long) item.getItemProperty("id").getValue();
					// MovieDetailsWindow.open(HacktimeUI.getDataProvider()
					// .getMovie(movieId), null, null);
				}
			}
		}

		@Override
		public Action[] getActions(final Object target, final Object sender)
		{
			return new Action[]
			{ newAciton, deleteAction, updateAction };
		}
	}

	private class TempTransactionsContainer extends FilterableListContainer<Resource>
	{

		public TempTransactionsContainer(final Collection<Resource> collection)
		{
			super(collection);
		}

		// This is only temporarily overridden until issues with
		// BeanComparator get resolved.
		@Override
		public void sort(final Object[] propertyId, final boolean[] ascending)
		{
			final boolean sortAscending = ascending[0];
			final Object sortContainerPropertyId = propertyId[0];
			Collections.sort(getBackingList(), new Comparator<Resource>()
			{
				@Override
				public int compare(final Resource o1, final Resource o2)
				{
					int result = 0;
					if ("title".equals(sortContainerPropertyId))
					{
						result = o1.getTitle().compareTo(o2.getTitle());
					}
					else if ("capacity".equals(sortContainerPropertyId))
					{
						result = new Integer(o1.getCapacity()).compareTo(o2.getCapacity());
					}
					else if ("resourceTypeName".equals(sortContainerPropertyId))
					{
						result = o1.getResourceTypeName().compareTo(o2.getTitle());
					}
					if (!sortAscending)
					{
						result *= -1;
					}
					return result;
				}
			});
		}

	}

}
