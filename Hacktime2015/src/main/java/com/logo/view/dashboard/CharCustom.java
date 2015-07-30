package com.logo.view.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.vaadin.viritin.FilterableListContainer;

import com.logo.HacktimeUI;
import com.logo.domain.Reservation;
import com.logo.domain.ResourceDashboard;
import com.logo.event.DashboardEventBus;
import com.logo.rest.RestService;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.themes.ValoLightTheme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class CharCustom extends VerticalLayout implements View
{

	private static Random rand = new Random(0);
	private static Color[] colors = new ValoLightTheme().getColors();

	public CharCustom()
	{
		setSizeFull();
		addStyleName("char");
		DashboardEventBus.register(this);
		VerticalLayout hl = new VerticalLayout();
		hl.setImmediate(true);
		hl.addComponents(createChart(),createTabelRezervation());
		addComponents(hl);

	}

	public static HorizontalLayout createChart()
	{
		rand = new Random(0);
		HorizontalLayout hl = new HorizontalLayout();
		Chart chart = new Chart(ChartType.PIE);
		hl.addComponent(chart);
		hl.setWidth("100%");
//		chart.setHeight("100%");
		hl.setMargin(new MarginInfo(false, false, false, false));
		Configuration conf = chart.getConfiguration();
		chart.setImmediate(true);
		conf.setTitle("");

		YAxis yaxis = new YAxis();
		yaxis.setTitle("Total percent market share");

		PlotOptionsPie pie = new PlotOptionsPie();
		pie.setShadow(false);
		conf.setPlotOptions(pie);

		conf.getTooltip().setValueSuffix("%");

		DataSeries innerSeries = new DataSeries();
		innerSeries.setName("Browsers");
		PlotOptionsPie innerPieOptions = new PlotOptionsPie();
		innerSeries.setPlotOptions(innerPieOptions);
		innerPieOptions.setSize(237);
		innerPieOptions.setDataLabels(new Labels());
		innerPieOptions.getDataLabels().setFormatter("this.y > 5 ? this.point.name : null");
		innerPieOptions.getDataLabels().setColor(new SolidColor(255, 255, 255));
		innerPieOptions.getDataLabels().setDistance(-30);

		Color[] innerColors = Arrays.copyOf(colors, 5);

		innerSeries.setData(new String[]
		{ "LOGO" }, new Number[]
		{ 100 }, innerColors);

		DataSeries outerSeries = new DataSeries();
		outerSeries.setName("Versions");
		PlotOptionsPie outerSeriesOptions = new PlotOptionsPie();
		outerSeries.setPlotOptions(outerSeriesOptions);
		outerSeriesOptions.setInnerSize(237);
		outerSeriesOptions.setSize(318);
		outerSeriesOptions.setDataLabels(new Labels());
		outerSeriesOptions.getDataLabels().setFormatter("this.y > 1 ? ''+ this.point.name +': '+ this.y +'%' : null");

		ArrayList<DataSeriesItem> dataS = new ArrayList<DataSeriesItem>();

		ArrayList<ResourceDashboard> resourceDashboards= RestService.instance.getResourceDashboardList().getResourceDashboards();
		
		for (int i=0;i<resourceDashboards.size();i++)
		{
			DataSeriesItem item = new DataSeriesItem( resourceDashboards.get(i).getTitle(),resourceDashboards.get(i).getResourceCount(), color(i));

			dataS.add(item);
		}

		outerSeries.setData(dataS);
		conf.setSeries(innerSeries, outerSeries);
		chart.drawChart(conf);

		return hl;
	}

	private Table createTabelRezervation()
	{
		final Table table = new Table();

		table.addContainerProperty("id", Integer.class, null);
		table.addContainerProperty("name", String.class, "");
		table.addContainerProperty("surname", String.class, "");
		table.addContainerProperty("resourceName", String.class, "");
		table.addContainerProperty("begdate", Date.class, "");
		table.addContainerProperty("enddate", Date.class, "");
		table.addContainerProperty("statuss", String.class, "");

		table.setSizeFull();
		table.addStyleName(ValoTheme.TABLE_BORDERLESS);
		table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		table.addStyleName(ValoTheme.TABLE_COMPACT);
		table.setSelectable(true);

		table.setColumnCollapsingAllowed(true);

		table.setColumnReorderingAllowed(true);
		ArrayList<Reservation> reservations;
//		if (HacktimeUI.currentUser.isAdmin())
//			reservations = RestService.instance.getReservationList().getReservations();
//		else 
			reservations = RestService.instance.getReservationsByUserAndStatus(HacktimeUI.currentUser.getId(), 0)
				.getReservations();
		boolean empty = reservations.isEmpty();
		if (empty)
		{
			reservations.add(new Reservation());
		}
		table.setContainerDataSource(new TempReservationContainer(reservations));
		table.setSortContainerPropertyId("name");
		table.setSortAscending(false);

		table.setVisibleColumns("name", "surname", "resourceName", "begDate", "endDate", "statuss");
		table.setColumnHeaders("Adı", "Soyadı", "Kaynak Adı", "Başlangıç Tarhihi", "Bitiş Tarihi", "Durumu");

		table.setFooterVisible(true);
		table.setDragMode(TableDragMode.MULTIROW);
//		table.addActionHandler(new TransactionsActionHandler());
		table.setImmediate(true);
		if (empty)
		{
			table.removeAllItems();
		}
		return table;

		/*
		 * List<Reservation> list = createListStatusWait();
		 * 
		 * Table reservatinStatusWait= new Table("Onay Bekleyen Kaynakalar");
		 * reservatinStatusWait.setSelectable(true);
		 * reservatinStatusWait.setWidth(100, Unit.PERCENTAGE);
		 * reservatinStatusWait.setPageLength(10);
		 * 
		 * reservatinStatusWait.addContainerProperty("name", String.class, "");
		 * reservatinStatusWait.addContainerProperty("surname", String.class,
		 * ""); reservatinStatusWait.addContainerProperty("resourcename",
		 * String.class, "");
		 * 
		 * reservatinStatusWait.setVisibleColumns("name", "surname",
		 * "resourcename");
		 * 
		 * reservatinStatusWait.setColumnHeader("name", "Adı");
		 * reservatinStatusWait.setColumnHeader("surname","Soyadı");
		 * reservatinStatusWait.setColumnHeader("resourcename","Kaynak Adı");
		 * 
		 * 
		 * 
		 * 
		 * for (Reservation row: list) { Item newItem =
		 * reservatinStatusWait.getItem(reservatinStatusWait.addItem());
		 * newItem.getItemProperty("name").setValue(row.getName());
		 * newItem.getItemProperty("surname").setValue(row.getSurname());
		 * newItem.getItemProperty("resourcename").setValue(row.getResourceName(
		 * )); }
		 * 
		 * 
		 * 
		 * 
		 * return reservatinStatusWait;
		 */

	}

	private class TempReservationContainer extends FilterableListContainer<Reservation>
	{

		public TempReservationContainer(final Collection<Reservation> collection)
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
			Collections.sort(getBackingList(), new Comparator<Reservation>()
			{
				@Override
				public int compare(final Reservation o1, final Reservation o2)
				{
					int result = 0;
					if ("name".equals(sortContainerPropertyId))
					{
						result = o1.getName().compareTo(o2.getName());
					}
					else if ("surname".equals(sortContainerPropertyId))
					{
						result = o1.getSurname().compareTo(o2.getSurname());
					}
					else if ("resourceName".equals(sortContainerPropertyId))
					{
						result = o1.getResourceName().compareTo(o2.getResourceName());
					}
					else if ("begDate".equals(sortContainerPropertyId))
					{
						result = o1.getBegDate().compareTo(o2.getBegDate());
					}
					else if ("endDate".equals(sortContainerPropertyId))
					{
						result = o1.getEndDate().compareTo(o2.getEndDate());
					}
					else if ("status".equals(sortContainerPropertyId))
					{

						// result = o1.getStatus()=o2.getStatus();
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

	private List<Reservation> createListStatusWait()
	{
		List<Reservation> list = new ArrayList<Reservation>();

		for (int i = 0; i < 30; i++)
		{
			Reservation res = new Reservation();

			res.setName("name" + i);
			res.setSurname("surname" + i);
			res.setResourceName("resource" + i);

			list.add(res);
		}

		return list;
	}

	/**
	 * Add a small random factor to a color form the vaadin theme.
	 *
	 * @param colorIndex
	 *            the index of the color in the colors array.
	 * @return the new color
	 */
	private static SolidColor color(int colorIndex)
	{
		SolidColor c = (SolidColor) colors[colorIndex];
		String cStr = c.toString().substring(1);

		int r = Integer.parseInt(cStr.substring(0, 2), 16);
		int g = Integer.parseInt(cStr.substring(2, 4), 16);
		int b = Integer.parseInt(cStr.substring(4, 6), 16);

		double opacity = (50 + rand.nextInt(95 - 50)) / 100.0;

		return new SolidColor(r, g, b, opacity);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		// TODO Auto-generated method stub

	}
}
