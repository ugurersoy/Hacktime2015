package com.logo.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public final class ReservationList
{
	public ReservationList() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlElement(name="Data")
	private ArrayList<Reservation> reservations;

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}
	
}
