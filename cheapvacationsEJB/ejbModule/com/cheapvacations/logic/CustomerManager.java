package com.cheapvacations.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.cheapvacations.entities.*;
import com.cheapvacations.utility.*;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class CustomerManager
 */
@Stateful(mappedName = "customermanagerJNDI")
public class CustomerManager implements CustomerManagerRemote {

	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;
	
	@EJB private HotelManagerLocal hm;
	// @EJB private HotelOfferManagerLocal hmo;
	@EJB private FlightManagerLocal fm;
	// @EJB private FlightOfferManagerLocal fmo;
	@EJB private PackageOfferManagerLocal pmo;
	// @EJB private ShoppingCartManagerLocal scm;
	
	private Customer customer;

	
	private List<VacationPackage> packages;
	
	private List<VacationPackage> orderedPackages;
	private List<VacationPackage> bookedPackages;
	
	private Flight tempflight;
	private Hotel temphotel;
    /**
     * Default constructor. 
     */
    public CustomerManager() {
    	tempflight = null;
    	temphotel = null;
    	bookedPackages = new ArrayList<VacationPackage>();
    	orderedPackages = new ArrayList<VacationPackage>();
    	
    }
    
	
	@Remove
	public void logout() {
		Iterator<VacationPackage> i = bookedPackages.iterator();
		while(i.hasNext()){
			VacationPackage tvp = i.next();
			tvp.setStatus(2);
			tvp.setBuyer(null);
			manager.merge(tvp);
		}
		
	}
	
	public void unsubscribe(){
		customer.setStatus(0);
		manager.merge(customer);
	}
	
	@SuppressWarnings("unchecked")
	public void setCustomer(long Id) throws LogicException {
		try{
			customer = manager.find(Customer.class, Id);
		}catch (Exception e) {
			throw new LogicException("Customer not found");
		}
		Query q = manager.createQuery("SELECT vp FROM VacationPackage vp WHERE vp.status = 0 AND vp.buyer = :customer");
		q.setParameter("customer", customer);
		
		this.orderedPackages = q.getResultList();
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public Flight getFlight(){
		return tempflight;
	}
	
	public Hotel getHotel(){
		return temphotel;
	}
	public void update(String firstname, String lastname, String email,	String address) {
		customer.setFirstName(firstname);
		customer.setLastName(lastname);
		customer.setEmail(email);
		customer.setAddress(address);
		
		manager.merge(customer);
	}


	public List<Hotel> manageHotel(String name, String city, String address, int stars, float rating, long Id, int operation) throws LogicException {
		
		List<Hotel> h = new ArrayList<Hotel>();
		
		switch (operation) {
		case 2:
			h = hm.queryHotels(name, city, stars, rating);
			break;
		default:
			break;
		}
		return h;
		
	}

	/*
	public List<HotelOffer> manageHotelOffer(Date startDate, Date endDate, int price, long operator_Id, long hotel_Id, long offer_Id, int operation) throws LogicException {
		List<HotelOffer> ho = new ArrayList<HotelOffer>();
		
		switch (operation) {
		case 2:
			ho = hmo.queryHotelOffer(startDate, endDate, price, 0, hotel_Id);
			break;
		case 5:
		case 7:
			ho.add(hmo.findHotelOfferById(offer_Id));
			break;
		case 6:
			ho = hmo.findHotelOfferByHotelId(hotel_Id);
			break;
		default:
			break;
		}
		return ho;
	}
	*/


	public List<Flight> manageFlight(String code, String airline, String departure, String arrival, float rating, long Id, int operation) throws LogicException {
		
		List<Flight> f = new ArrayList<Flight>();
		
		switch (operation) {
		case 2:
			f = fm.queryFligths(code, airline, departure, arrival, rating);
			break;
		default:
			break;
		}
		return f;
		
	}

	/*
	public List<FlightOffer> manageFlightOffer(Date startDate, Date endDate, int price, long operator_Id, long flight_Id, long offer_Id, int operation) throws LogicException {
		List<FlightOffer> fo = new ArrayList<FlightOffer>();
		
		switch (operation) {
		case 2:
			fo = fmo.queryFlightOffer(startDate, endDate, price, 0, flight_Id);
			break;
		case 5:
		case 7:
		case 8:
			fo.add(fmo.findFlightOfferById(offer_Id));
			break;
		case 6:
			fo = fmo.findFlightOfferByFlightId(flight_Id);
		default:
			break;
		}
		return fo;
	}
	*/
	
	public List<VacationPackage> getPackages() {
		return packages;
	}

	
	public void manageVacationPackage(PackageData pack, int range, int operation)
			throws LogicException {
		
		
		switch (operation) {
		// Generic Query
		case 4:
			packages = pmo.queryVacationPackageOffer( pack, range);
			break;
		// query by hotel id
		case 6:
			temphotel = manager.find(Hotel.class, pack.hotel_Id);
			packages = pmo.findVacationPackageOfferByHotelId(pack.hotel_Id);
			break;
		// query by flight id
		case 7:
			tempflight = manager.find(Flight.class, pack.flight_Id);
			packages = pmo.findVacationPackageOfferByFlightId(pack.flight_Id);
			break;
		default:
			break;
		}
	}
	
	public void useCart(PackageData pack, int operation) throws LogicException {
		
		VacationPackage vp1 = manager.find(VacationPackage.class, pack.Id);
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		
		switch (operation) {
		// Add to Cart
		case 1:
			
			if(sqlToday.before(vp1.getStartDate())){
				vp1.setBuyer(manager.find(Customer.class,pack.idCustomer));
				// Package Booked
				vp1.setStatus(1);
				bookedPackages.add(vp1);
				manager.merge(vp1);
			}else{
				// package is expired
				vp1.setStatus(-1);
				manager.merge(vp1);
				throw new LogicException("Found an Expired Package in Booking");
			}
			
			break;
		// Buy
		case 2:
			if(sqlToday.before(vp1.getStartDate())){
				vp1.setBuyer(manager.find(Customer.class,pack.idCustomer));
				// Package Booked
				vp1.setStatus(0);
				bookedPackages.remove(vp1);
				orderedPackages.add(vp1);
				customer.insertInMyPackages(vp1);
				manager.merge(vp1);
				manager.merge(customer);
			}else{
				// package is expired
				vp1.setStatus(-1);
				bookedPackages.remove(vp1);
				manager.merge(vp1);
				throw new LogicException("Found an Expired Package to Buy");
			}
			break;
		// Discard
		case 3:
			vp1.setStatus(2);
			bookedPackages.remove(vp1);
			manager.merge(vp1);
			break;
		// discard from orders 
		case 4:
			Calendar dateCal = Calendar.getInstance();
			// make it now
			dateCal.setTime(new java.util.Date());
			// make it 3 days
			dateCal.add(Calendar.DAY_OF_YEAR, 3);
			java.sql.Date sqlMaxDate = new java.sql.Date(dateCal.getTime().getTime());
			if(sqlMaxDate.before(vp1.getStartDate())){
				vp1.setBuyer(null);
				// Package Booked
				vp1.setStatus(2);
				orderedPackages.remove(vp1);
				customer.removeFromMyPackages(vp1);
				
				manager.merge(vp1);
				manager.merge(customer);
			}else{
				throw new LogicException("Too late to remove Package");
			}
			break;
		default:
			break;
		}
		
	}


	public List<VacationPackage> getOrderedPackages() {
		return orderedPackages;
	}
	
	public List<VacationPackage> getBookedPackages() {
		return bookedPackages;
	}
	
	
}
