package com.logo;

import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.logo.data.DataProvider;
import com.logo.data.dummy.DummyDataProvider;
import com.logo.domain.User;
import com.logo.event.DashboardEvent.BrowserResizeEvent;
import com.logo.event.DashboardEvent.CloseOpenWindowsEvent;
import com.logo.event.DashboardEvent.UserLoggedOutEvent;
import com.logo.event.DashboardEvent.UserLoginRequestedEvent;
import com.logo.event.DashboardEventBus;
import com.logo.view.LoginView;
import com.logo.view.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("valo")
@Title("Hacktime 2015")
@Widgetset("com.logo.LogoWidgetSet")
@SpringUI(path = "logo")
public class HacktimeUI extends UI
{

	private final DataProvider dataProvider = new DummyDataProvider();
	private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

	public HacktimeUI()
	{
		UI.getCurrent();
	}
//sad
	@Override
	protected void init(VaadinRequest request)
	{
		setLocale(new Locale("tr-TR"));

		DashboardEventBus.register(this);
		Responsive.makeResponsive(this);
		addStyleName(ValoTheme.UI_WITH_MENU);

		updateContent();

		// Some views need to be aware of browser resize events so a
		// BrowserResizeEvent gets fired to the event bus on every occasion.
		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener()
		{
			@Override
			public void browserWindowResized(final BrowserWindowResizeEvent event)
			{
				DashboardEventBus.post(new BrowserResizeEvent());
			}
		});
	}

	private void updateContent()
	{
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		if (user != null && "admin".equals(user.getRole()))
		{
			// Authenticated user
			setContent(new MainView());
			removeStyleName("loginview");
			getNavigator().navigateTo(getNavigator().getState());
		}
		else
		{
			setContent(new LoginView());
			addStyleName("loginview");
		}
	}

	@Subscribe
	public void userLoginRequested(final UserLoginRequestedEvent event)
	{
		DataProvider dataProvider = getDataProvider();
		User user = dataProvider.authenticate(event.getUserName(), event.getPassword());
		VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
		updateContent();
	}

	@Subscribe
	public void userLoggedOut(final UserLoggedOutEvent event)
	{
		// When the user logs out, current VaadinSession gets closed and the
		// page gets reloaded on the login screen. Do notice the this doesn't
		// invalidate the current HttpSession.
		VaadinSession.getCurrent().close();
		Page.getCurrent().reload();
	}

	@Subscribe
	public void closeOpenWindows(final CloseOpenWindowsEvent event)
	{
		for (Window window : getWindows())
		{
			window.close();
		}
	}

	/**
	 * @return An instance for accessing the (dummy) services layer.
	 */
	public static DataProvider getDataProvider()
	{
		return ((HacktimeUI) getCurrent()).dataProvider;
	}

	public static DashboardEventBus getDashboardEventbus()
	{
		return ((HacktimeUI) getCurrent()).dashboardEventbus;
	}

}
