package com.logo.view.dashboard;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.logo.domain.Reservation;
import com.logo.event.DashboardEventBus;
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
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class CharCustom extends VerticalLayout implements View {

   private static Random rand = new Random(0);
   private static Color[] colors = new ValoLightTheme().getColors();

   
   public CharCustom() {
       setSizeFull();
       addStyleName("char");
       DashboardEventBus.register(this);

       addComponents(createChart(),createTabelRezervation());

   }

   public static Chart createChart() {
       rand = new Random(0);

       Chart chart = new Chart(ChartType.PIE);

        chart.setHeight("100%");
       Configuration conf = chart.getConfiguration();

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
       innerPieOptions.getDataLabels().setFormatter(
               "this.y > 5 ? this.point.name : null");
       innerPieOptions.getDataLabels().setColor(new SolidColor(255, 255, 255));
       innerPieOptions.getDataLabels().setDistance(-30);

       Color[] innerColors = Arrays.copyOf(colors, 5);
     
       
       innerSeries.setData(new String[] {"LOGO"}, new Number[] { 100 }, innerColors);

       DataSeries outerSeries = new DataSeries();
       outerSeries.setName("Versions");
       PlotOptionsPie outerSeriesOptions = new PlotOptionsPie();
       outerSeries.setPlotOptions(outerSeriesOptions);
       outerSeriesOptions.setInnerSize(237);
       outerSeriesOptions.setSize(318);
       outerSeriesOptions.setDataLabels(new Labels());
       outerSeriesOptions
               .getDataLabels()
               .setFormatter(
                       "this.y > 1 ? ''+ this.point.name +': '+ this.y +'%' : null");

       
       ArrayList<DataSeriesItem> dataS = new ArrayList<DataSeriesItem>();
       
       for(int i=0;i<10;i++)
	   {
    	   DataSeriesItem item=  new DataSeriesItem("MSIE 6.0"+i, 10.85+i, color(i));   
		   
		   dataS.add(item);
	   }
	   

       outerSeries.setData(dataS);
       conf.setSeries(innerSeries, outerSeries);
       chart.drawChart(conf);

       return chart;
   }
   
   private Table createTabelRezervation() {
	   
	   
	    List<Reservation> list = createListStatusWait();
	   
		 Table reservatinStatusWait= new Table("Onay Bekleyen Kaynakalar");
		 reservatinStatusWait.setSelectable(true);
		 reservatinStatusWait.setWidth(100, Unit.PERCENTAGE);
		 reservatinStatusWait.setPageLength(10);

		 reservatinStatusWait.addContainerProperty("name", String.class, "");
		 reservatinStatusWait.addContainerProperty("surname", String.class, "");
		 reservatinStatusWait.addContainerProperty("resourcename", String.class, "");

		 reservatinStatusWait.setVisibleColumns("name", "surname",  "resourcename");
		 
		 reservatinStatusWait.setColumnHeader("name", "Adı");
		 reservatinStatusWait.setColumnHeader("surname","Soyadı");
		 reservatinStatusWait.setColumnHeader("resourcename","Kaynak Adı");


		
      for (Reservation row: list) {
        Item newItem = reservatinStatusWait.getItem(reservatinStatusWait.addItem());
         newItem.getItemProperty("name").setValue(row.getName());
         newItem.getItemProperty("surname").setValue(row.getSurname());
         newItem.getItemProperty("resourcename").setValue(row.getResourceName());
         }
         
         

	
		return reservatinStatusWait;
	   
   }
   
   private List<Reservation> createListStatusWait()
   {
	   List<Reservation> list = new ArrayList<Reservation>();
	   

        for(int i=0;i<30;i++)
        {
        	Reservation res = new  Reservation();
        	
        	res.setName("name"+i);
        	res.setSurname("surname"+i);
        	res.setResourceName("resource"+i);
        	
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
   private static SolidColor color(int colorIndex) {
       SolidColor c = (SolidColor) colors[colorIndex];
       String cStr = c.toString().substring(1);

       int r = Integer.parseInt(cStr.substring(0, 2), 16);
       int g = Integer.parseInt(cStr.substring(2, 4), 16);
       int b = Integer.parseInt(cStr.substring(4, 6), 16);

       double opacity = (50 + rand.nextInt(95 - 50)) / 100.0;

       return new SolidColor(r, g, b, opacity);
   }

@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	
}
}


