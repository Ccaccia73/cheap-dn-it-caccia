package com.cheapvacations.logic;

import java.util.List;

import javax.ejb.Remote;

import com.cheapvacations.utility.*;
import com.cheapvacations.entities.*;

@Remote
public interface CustomerManagerRemote {
	public void logout();
	public void unsubscribe();
	public void setCustomer(long Id) throws LogicException;
	public Customer getCustomer();
	public void update(String firstname, String lastname, String email, String address);
	// Hotel management
	public List<Hotel> manageHotel(String name, String city, String address, int stars, float rating, long Id, int operation) throws LogicException;
	// public List<HotelOffer> manageHotelOffer(Date startDate, Date endDate, int price, long operator_Id, long hotel_Id , long offer_Id, int operation) throws LogicException;
	// Flight management
	public List<Flight> manageFlight(String code, String airline, String departure, String arrival, float rating, long Id, int operation) throws LogicException;
	// public List<FlightOffer> manageFlightOffer(Date startDate, Date endDate, int price, long operator_Id, long flight_Id , long offer_Id, int operation) throws LogicException;
	// Package management
	public List<VacationPackage> getPackages();
	public Flight getFlight();
	public Hotel getHotel();
	public void manageVacationPackage(PackageData pack, int range, int operation) throws LogicException;
	// Cart management
	public void useCart(PackageData pack, int operation) throws LogicException;
	public List<VacationPackage> getOrderedPackages();
	public List<VacationPackage> getBookedPackages();
}
