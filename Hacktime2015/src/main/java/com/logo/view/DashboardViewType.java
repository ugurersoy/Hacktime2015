package com.logo.view;

import com.logo.view.browsers.ReservationView;
import com.logo.view.browsers.ResourcesView;
import com.logo.view.dashboard.CharCustom;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
	DASHBOARD("çalışma Panosu", CharCustom.class, FontAwesome.HOME, true), 
    REZERVATION("rezervasyon",ReservationView.class,FontAwesome.LIST,false),
	RESOURCES("kaynaklar",ResourcesView.class,FontAwesome.TABLE,false),
	SCHEDULE("takvim", ScheduleView.class, FontAwesome.CALENDAR_O, false);
	

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
