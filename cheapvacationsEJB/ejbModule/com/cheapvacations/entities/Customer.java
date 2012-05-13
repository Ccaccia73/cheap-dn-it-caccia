package com.cheapvacations.entities;


import javax.persistence.*;

import java.util.*;

/**
 * @ejb.bean
 *   name=Customer
 *   represents the Customer class
 */
@NamedQueries({
	@NamedQuery(
		name="Customer.getCustomerByUserAndPassword",
		query="SELECT c FROM Customer c WHERE c.username = :name AND c.password = :pw"),
	@NamedQuery(
		name="Customer.getCustomerByUser",
		query="SELECT c FROM Customer c WHERE c.username = :name"),
})

@Entity
// caso single table
// @DiscriminatorValue(value="C")
// caso table per class
@Table(name="Customer")
public class Customer extends User {

	private static final long serialVersionUID = 1L;

	
	private List<VacationPackage> myPackages;
	
	
	private List<HotelFeedback> myHotelFeedbacks;
	

	private List<FlightFeedback> myFlightFeedbacks;

	/**
	 * @return the myPackages
	 */
	@OneToMany(mappedBy="buyer", cascade={CascadeType.ALL})
	public List<VacationPackage> getMyPackages() {
		return myPackages;
	}

	/**
	 * @param myPackages the myPackages to set
	 */
	public void setMyPackages(List<VacationPackage> myPackages) {
		this.myPackages = myPackages;
	}

	/**
	 * @return the myHotelFeedbacks
	 */
	
	@OneToMany(mappedBy="hotelcustomer",targetEntity=com.cheapvacations.entities.HotelFeedback.class, cascade={CascadeType.ALL})
	public List<HotelFeedback> getMyHotelFeedbacks() {
		return myHotelFeedbacks;
	}
	
	/**
	 * @param myHotelFeedbacks the myHotelFeedbacks to set
	 */
	
	public void setMyHotelFeedbacks(List<HotelFeedback> myHotelFeedbacks) {
		this.myHotelFeedbacks = myHotelFeedbacks;
	}
	
	/**
	 * @return the myFlightFeedbacks
	 */
	
	@OneToMany(mappedBy="flightcustomer",targetEntity=com.cheapvacations.entities.FlightFeedback.class,cascade={CascadeType.ALL})
	public List<FlightFeedback> getMyFlightFeedbacks() {
		return myFlightFeedbacks;
	}
	
	/**
	 * @param myFlightFeedbacks the myFlightFeedbacks to set
	 */
	
	public void setMyFlightFeedbacks(List<FlightFeedback> myFlightFeedbacks) {
		this.myFlightFeedbacks = myFlightFeedbacks;
	}
	
	public void insertInMyPackages(VacationPackage pack){
		this.myPackages.add(pack);
	}
	
	public void removeFromMyPackages(VacationPackage pack){
		this.myPackages.remove(pack);
	}
	
	
}