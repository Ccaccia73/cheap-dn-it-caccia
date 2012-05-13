package com.cheapvacations.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cheapvacations.entities.*;
import com.cheapvacations.utility.*;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class TravelAgencyManager
 */
@Stateful(mappedName = "travelagencymanagerJNDI")
public class TravelAgencyManager implements TravelAgencyManagerRemote {
	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

	@EJB private HotelManagerLocal hm;
	@EJB private HotelOfferManagerLocal hmo;
	@EJB private FlightManagerLocal fm;
	@EJB private FlightOfferManagerLocal fmo;
	@EJB private PackageOfferManagerLocal pmo;
	
	private TravelAgency agency;
	
	private HotelOffer packageHotel;
	private FlightOffer packageDepart;
	private FlightOffer packageReturn;
	
	private List<VacationPackage> packages;
	
	private FlightOffer fo;
	private HotelOffer ho;
	
    /**
     * Default constructor. 
     */
    public TravelAgencyManager() {
    	packageHotel = null;
    	packageDepart = null;
    	packageReturn = null;
    	fo = null;
    	ho = null;
    }


	public void buildPackage() {
		// TODO Auto-generated method stub
		
	}
	
	@Remove
	public void logout() {

	}

	public void unsubscribe(){
		// TODO cancel packages
		agency.setStatus(0);
		manager.merge(agency);
	}

	public void setTravelAgency(long Id) throws LogicException {
		try{
			agency = manager.find(TravelAgency.class, Id);
		}catch (Exception e) {
			throw new LogicException("Agency not found");
		}		
	}

	public TravelAgency getTravelAgency() {
		return agency;
	}
	
	public FlightOffer getFlightOffer(){
		return fo;
	}
	
	public HotelOffer getHotelOffer(){
		return ho;
	}
	public void update(String firstname, String lastname, String email,String address) {
		agency.setFirstName(firstname);
		agency.setLastName(lastname);
		agency.setEmail(email);
		agency.setAddress(address);
		
		manager.merge(agency);
	}


	public List<Hotel> manageHotel(String name, String city, String address, int stars, float rating, long Id, int operation) throws LogicException {
		
		List<Hotel> h = new ArrayList<Hotel>();
		
		switch (operation) {
		case 2:
			h = hm.queryHotels(name, city, stars, rating);
			break;
		case 5:
			h.add(hm.findHotelById(Id));
		default:
			break;
		}
		return h;
		
	}


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


	public List<Flight> manageFlight(String code, String airline, String departure, String arrival, float rating, long Id, int operation) throws LogicException {
		
		List<Flight> f = new ArrayList<Flight>();
		
		switch (operation) {
		case 2:
			f = fm.queryFligths(code, airline, departure, arrival, rating);
			break;
		case 5:
			f.add(fm.findFlightById(Id));
		default:
			break;
		}
		return f;
		
	}


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


	public HotelOffer getPackageHotel() {
		return packageHotel;
	}


	public void setPackageHotel(HotelOffer packageHotel) {
		this.packageHotel = packageHotel;
	}


	public FlightOffer getPackageDepart() {
		return packageDepart;
	}


	public void setPackageDepart(FlightOffer packageDepart) {
		this.packageDepart = packageDepart;
	}


	public FlightOffer getPackageReturn() {
		return packageReturn;
	}


	public void setPackageReturn(FlightOffer packageReturn) {
		this.packageReturn = packageReturn;
	}

	
	public List<VacationPackage> getPackages() {
		return packages;
	}

	
	public void manageVacationPackage(PackageData pack, int range, int operation)
			throws LogicException {
		
		
		switch (operation) {
		// build
		case 1:
			if(packages != null){
				packages.clear();
			}else{
				packages = new ArrayList<VacationPackage>();
			}
			packages.add(pmo.addVacationPackageOffer(pack));
			
			break;
		// QUERY BY HOTEL OFFER ID 
		case 2:
			ho = manager.find(HotelOffer.class, pack.idHotel);
			// System.out.println("HotelOffer ID"+ho.getId());
			packages = pmo.findVacationPackageOfferByHotelOfferIdAndAgency(pack.idHotel, pack.idAgency);
			break;
		// QUERY BY FLIGHT OFFER ID
		case 3:
			fo = manager.find(FlightOffer.class, pack.idDeparture);
			packages = pmo.findVacationPackageOfferByFlightOfferIdAndAgency(pack.idDeparture, pack.idAgency);
			break;
		case 4:
			packages = pmo.queryVacationPackageOffer( pack, range);
			break;
		// Clear Selection
		case 5:
			pmo.cancelVacationPackageOffer(pack.Id);
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			packageHotel = null;
			packageDepart = null;
			packageReturn = null;
			break;
		default:
			break;
		}
	}
}
