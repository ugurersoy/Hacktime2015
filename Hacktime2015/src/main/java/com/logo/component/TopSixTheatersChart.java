package com.logo.component;

import java.util.ArrayList;
import java.util.List;

import com.logo.HacktimeUI;
import com.logo.domain.Reservation;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;


@SuppressWarnings("serial")
public class TopSixTheatersChart extends Chart {

    public TopSixTheatersChart() {
        super(ChartType.PIE);

        setCaption("Popular Movies");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        getConfiguration().getChart().setAnimation(false);
        setWidth("100%");
        setHeight("90%");

        DataSeries series = new DataSeries();

     
        getConfiguration().setSeries(series);

        PlotOptionsPie opts = new PlotOptionsPie();
        opts.setBorderWidth(0);
        opts.setShadow(false);
        opts.setAnimation(false);
        getConfiguration().setPlotOptions(opts);

        Credits c = new Credits("");
        getConfiguration().setCredits(c);
    }

}
