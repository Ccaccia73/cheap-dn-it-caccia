package com.cheapvacations.logic;


import com.cheapvacations.entities.*;
import com.cheapvacations.utility.PackageData;

import java.util.*;
import java.sql.Date;

import javax.ejb.*;
import javax.persistence.*;

/**
 * Session Bean implementation class OperatorManager
 */
@Stateful(mappedName = "operatormanagerJNDI")
public class OperatorManager implements OperatorManagerRemote {
	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;
	
	@EJB private HotelManagerLocal hm;
	@EJB private HotelOfferManagerLocal hmo;
	@EJB private FlightManagerLocal fm;
	@EJB private FlightOfferManagerLocal fmo;
	@EJB private PackageOfferManagerLocal pmo;

	private Operator operator;
	
	private List<VacationPackage> packages;
	
	private FlightOffer fo;
	private HotelOffer ho;
	
    /**
     * Default constructor. 
     */
    public OperatorManager() {
    	packages = null;
    	fo = null;
    	ho = null;
    }
    
    @Remove
	public void logout() {
		packages = null;
	}
    
    public void unsubscribe(){
		
		// TODO cancel offers
		operator.setStatus(0);
		manager.merge(operator);
		
	}

	public void setOperator(long Id) throws LogicException {
		try{
			operator = manager.find(Operator.class, Id);
		}catch (Exception e) {
			throw new LogicException("Operator not found");
		}
		
	}

	public Operator getOperator() {
		return operator;
	}
	
	public List<VacationPackage> getPackages() {
		return packages;
	}
	
	public FlightOffer getFlightOffer(){
		return fo;
	}
	
	public HotelOffer getHotelOffer(){
		return ho;
	}
	
	public void update(String firstname, String lastname, String email, String address){

		operator.setFirstName(firstname);
		operator.setLastName(lastname);
		operator.setEmail(email);
		operator.setAddress(address);
		
		manager.merge(operator);
	}

	public List<Hotel> manageHotel(String name, String city, String address, int stars, float rating, long Id ,int operation) throws LogicException {
		
		List<Hotel> h = new ArrayList<Hotel>();
		
		switch (operation) {
		case 1:
			h.add(hm.addHotel(name, city, address, stars));
			break;
		case 2:
			h = hm.queryHotels(name, city, stars, rating);
			break;
		case 3:
			h.add(hm.updateHotel(name, city, address, stars, rating));
			break;
		case 4:
			hm.cancelHotel(Id);
			break;
		case 5:
			h.add(hm.findHotelById(Id));
			break;
		default:
			break;
		}
		return h;
		
	}

	public List<HotelOffer> manageHotelOffer(Date startDate, Date endDate, int price, long operator_Id, long hotel_Id, long offer_Id, int operation) throws LogicException {
		List<HotelOffer> ho = new ArrayList<HotelOffer>();
		
		switch (operation) {
		case 1:
			ho.add(hmo.addHotelOffer(startDate, endDate, price, operator_Id, hotel_Id));
			break;
		case 2:
			ho = hmo.queryHotelOffer(startDate, endDate, price, operator_Id, hotel_Id);
			break;
		case 3:
			ho.add(hmo.updateHotelOffer(startDate, endDate, price, operator_Id, hotel_Id));
			break;
		case 4:
			hmo.cancelHotelOffer(offer_Id);
			break;
		case 5:
			ho.add(hmo.findHotelOfferById(offer_Id));
			break;
		case 6:
			ho = hmo.findHotelOfferByHotelIdAndOperatorId(hotel_Id, operator_Id);
			break;
		default:
			break;
		}
		return ho;
	}

	public List<Flight> manageFlight(String code, String airline, String departure, String arrival, float rating, long Id, int operation) throws LogicException {
		
		List<Flight> f = new ArrayList<Flight>();
		
		switch (operation) {
		case 1:
			f.add(fm.addFlight(code, airline, departure, arrival));
			break;
		case 2:
			f = fm.queryFligths(code, airline, departure, arrival, rating);
			break;
		case 3:
			f.add(fm.updateFlight(code, airline, departure, arrival, rating));
			break;
		case 4:
			fm.cancelFlight(Id);
			break;
		case 5:
			f.add(fm.findFlightById(Id));
			break;
		default:
			break;
		}
		return f;
		
	}

	public List<FlightOffer> manageFlightOffer(Date startDate, Date endDate, int price, long operator_Id, long flight_Id, long offer_Id, int operation) throws LogicException {
		List<FlightOffer> fo = new ArrayList<FlightOffer>();
		
		switch (operation) {
		case 1:
			fo.add(fmo.addFlightOffer(startDate, endDate, price, operator_Id, flight_Id));
			break;
		case 2:
			fo = fmo.queryFlightOffer(startDate, endDate, price, operator_Id, flight_Id);
			break;
		case 3:
			fo.add(fmo.updateFlightOffer(startDate, endDate, price, operator_Id, flight_Id));
			break;
		case 4:
			fmo.cancelFlightOffer(offer_Id);
			break;
		case 5:
			fo.add(fmo.findFlightOfferById(offer_Id));
			break;
		case 6:
			fo = fmo.findFlightOfferByFlightIdAndOperatorId(flight_Id, operator_Id);
			break;
		default:
			break;
		}
		return fo;
	}
	
	public void manageVacationPackage(PackageData pack , int range, int operation) throws LogicException {
		
		switch (operation) {
		// BUILD
		case 1:
			throw new LogicException("Operation Build Package not permitted in Operator");
		// BY HOTEL OFFER;
		case 2:
			ho = manager.find(HotelOffer.class, pack.idHotel);
			// System.out.println("HotelOffer ID"+ho.getId());
			packages = pmo.findVacationPackageOfferByHotelOfferId(pack.idHotel);
			break;
		// BY FLIGHT OFFER;
		case 3:
			fo = manager.find(FlightOffer.class, pack.idDeparture);
			packages = pmo.findVacationPackageOfferByFlightOfferId(pack.idDeparture);
			break;
		// QUERY WITH CRITERIA
		case 4:
			packages = pmo.queryVacationPackageOffer( pack, range);
			break;
			/*
			packages = pmo.queryVacationPackageOffer(startDate, endDate, price, -3, idAgency, idDeparture, idReturn, idHotel, idCustomer, range);
			
			Iterator<VacationPackage> i = packages.iterator();
			while(i.hasNext()){
				if( i.next().getDepartFlight().getReferenceFlightOperator().getId() != idOperator && 
					i.next().getReturnFlight().getReferenceFlightOperator().getId() != idOperator &&
					i.next().getStayHotel().getReferenceHotelOperator().getId() != idOperator){
					i.remove();
				}
			}
			*/
			
		// Clear Selection
		case 5:
			throw new LogicException("Operation Clear Packages not permitted in Operator");
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		default:
			break;
		}
	}

}
