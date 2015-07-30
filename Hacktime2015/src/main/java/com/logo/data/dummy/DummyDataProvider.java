package com.logo.data.dummy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.logo.HacktimeUI;
import com.logo.data.DataProvider;
import com.logo.domain.DashboardNotification;
import com.logo.domain.Resource;
import com.logo.domain.Reservation;
import com.logo.domain.User;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.CurrentInstance;

/**
 * A dummy implementation for the backend API.
 */
public class DummyDataProvider implements DataProvider
{

	// TODO: Get API key from http://developer.rottentomatoes.com
	private static final String ROTTEN_TOMATOES_API_KEY = null;

	/* List of countries and cities for them */
	// private static Multimap<String, String> countryToCities;
	private static Date lastDataUpdate;
	// private static Collection<Movie> movies;
	private static Multimap<Long, Resource> resources;
	private static Multimap<Long, Reservation> reservations;
	

	private static Random rand = new Random();



	@Override
	public User authenticate(String userName, String password)
	{
		
		User user = new User();
		user.setName(HacktimeUI.currentUser.getName());
		user.setSurName(HacktimeUI.currentUser.getSurName());
		//user.setRole("admin");
		String email = user.getName().toLowerCase() + "." + user.getSurName().toLowerCase() + "@xyz"
				 + ".com";
//		user.setEmail(email.replaceAll(" ", ""));
//		user.setLocation("Çanakkale");
		return user;
	}

	

	public Multimap<Long, Resource> generateResources(int count)
	{
		/*Multimap<Long, Resource>*/ resources = MultimapBuilder.hashKeys().arrayListValues().build();
		//
		for (int i = 0; i < count; i++)
		{
			resources.putAll((long) i, new ArrayList<Resource>());
			resources.get((long) i).add(new Resource(i, "Title " + i, i, "resourceTypeName", null));
		}
		return resources;
	}
	
//	/**
//	 * Rezervasyon bilgilerini listeliyor
//	 *
//	 * @return
//	 */
//	private Multimap<Long, Reservation> generateRezervationData(int num)
//	{
//		reservations = MultimapBuilder.hashKeys().arrayListValues().build();
//		Calendar cal = Calendar.getInstance();
//		for (int i = 0; i < num; i++)
//		{
//			reservations.putAll((long) i, new ArrayList<Reservation>());
//			
//			Label statusLabel = new Label(i+"");
//			
//			if(i==0)
//			{
//				statusLabel.setStyleName(ValoTheme.LABEL_FAILURE);
//			}else if(i==1)
//			{
//				statusLabel.setStyleName(ValoTheme.LABEL_SUCCESS);
//			}else
//			{
//				statusLabel.setStyleName(ValoTheme.LABEL_COLORED);
//			}
//			
//			reservations.get((long) i).add(new Reservation(i,i,"name"+i,"surname"+i,"resourcename"+i,"",""));
//
//		}
//
//		return reservations;
//
//	}

	@Override
	public Collection<Resource> getResources(int count)
	{
		List<Resource> orderedTransactions = Lists.newArrayList(resources.values());
//		Collections.sort(orderedTransactions, new Comparator<Resource>()
//		{
//			@Override
//			public int compare(Resource o1, Resource o2)
//			{
//				return o2.getTime().compareTo(o1.getTime());
//			}
//		});
		return orderedTransactions.subList(0, Math.min(count, resources.values().size() - 1));
	}




	@Override
	public Collection<Reservation> getReservationStatusWait() {
	
	   ArrayList<Reservation> list = new ArrayList<Reservation>();
		 for(int i=0;i<10;i++)
		 {
			 Reservation item = new Reservation();
			 item.setName("deneme"+1);
			 item.setSurname("surname"+1);
			 list.add(item);
		 }
		
		return	list;
	}


}
