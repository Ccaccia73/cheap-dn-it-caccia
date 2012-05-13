package com.cheapvacations.logic;

import com.cheapvacations.entities.*;

import javax.ejb.Remote;
import java.util.*;
import java.sql.Date;
import com.cheapvacations.utility.*;

@Remote
public interface TravelAgencyManagerRemote {
	public void logout();
	public void unsubscribe();
	public void buildPackage();
	public void setTravelAgency(long Id) throws LogicException;
	public TravelAgency getTravelAgency();
	public void update(String firstname, String lastname, String email, String address);
	// Hotel management
	public List<Hotel> manageHotel(String name, String city, String address, int stars, float rating, long Id, int operation) throws LogicException;
	public List<HotelOffer> manageHotelOffer(Date startDate, Date endDate, int price, long operator_Id, long hotel_Id , long offer_Id, int operation) throws LogicException;
	// Flight management
	public List<Flight> manageFlight(String code, String airline, String departure, String arrival, float rating, long Id, int operation) throws LogicException;
	public List<FlightOffer> manageFlightOffer(Date startDate, Date endDate, int price, long operator_Id, long flight_Id , long offer_Id, int operation) throws LogicException;
	// Package Management
	public HotelOffer getPackageHotel();
	public void setPackageHotel(HotelOffer packageHotel);
	public FlightOffer getPackageDepart();
	public void setPackageDepart(FlightOffer packageDepart);
	public FlightOffer getPackageReturn();
	public void setPackageReturn(FlightOffer packageReturn);
	public List<VacationPackage> getPackages();
	public FlightOffer getFlightOffer();
	public HotelOffer getHotelOffer();
	public void manageVacationPackage(PackageData pack, int range, int operation) throws LogicException;
}
