package com.logo.data;

import java.util.Collection;
import java.util.Date;

import com.google.common.collect.Multimap;
import com.logo.domain.DashboardNotification;
import com.logo.domain.Reservation;
import com.logo.domain.Resource;
import com.logo.domain.User;

/**
 * QuickTickets Daasdasdasdasddasdshboard backend API.
 */
public interface DataProvider {
    /**
     * @param count
     *            Number of transactions to fetch.
     * @return A Collection of most recent transactions.
     */
    Collection<Resource> getResources(int count);
    Collection<Reservation> getReservationStatusWait();

   

    /**
     * @param userName
     * @param password
     * @return Authenticated used.
     */
    User authenticate(String userName, String password);

  
  
}
