package com.cheapvacations.entities;

import javax.persistence.*;

import java.util.*;


@NamedQueries({
	@NamedQuery(
		name="Operator.getOperatorByUserAndPassword",
		query="SELECT o FROM Operator o WHERE o.username = :name AND o.password = :pw"),
	@NamedQuery(
		name="Operator.getOperatorByUser",
		query="SELECT o FROM Operator o WHERE o.username = :name"),
})
@Entity
// caso single table
// @DiscriminatorValue(value="O")
// caso table per class
@Table(name="Operator")
public class Operator extends User {
	
	private static final long serialVersionUID = 9L;

	private List<HotelOffer> myHotelOffers;
	
	private List<FlightOffer> myFlightOffers;

	/**
	 * @return the myHotelOffers
	 */
	@OneToMany(mappedBy="referenceHotelOperator", cascade={CascadeType.ALL})
	public List<HotelOffer> getMyHotelOffers() {
		return myHotelOffers;
	}

	/**
	 * @param hotels the myHotelOffers to set
	 */
	public void setMyHotelOffers(List<HotelOffer> hotels) {
		this.myHotelOffers = hotels;
	}

	/**
	 * @return the myFlightOffer
	 */
	@OneToMany(mappedBy="referenceFlightOperator", cascade={CascadeType.ALL})
	public List<FlightOffer> getMyFlightOffers() {
		return myFlightOffers;
	}

	/**
	 * @param flights the myFlightOffers to set
	 */
	public void setMyFlightOffers(List<FlightOffer> flights) {
		this.myFlightOffers = flights;
	}
	
	public void insertInMyHotelOffers(HotelOffer ho){
		this.myHotelOffers.add(ho);
	}
	
	public void removeFromMyHotelOffers(HotelOffer ho){
		this.myHotelOffers.remove(ho);
	}
	
	public void insertInMyFlightOffers(FlightOffer fo){
		this.myFlightOffers.add(fo);
	}
	
	public void removeFromMyFlightOffers(FlightOffer fo){
		this.myFlightOffers.remove(fo);
	}
}