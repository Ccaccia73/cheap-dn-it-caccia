package com.cheapvacations.entities;


import javax.persistence.*;

/**
 * @author ccaccia
 *
 */
@Entity
@Table(name="HotelFeedback")
public class HotelFeedback extends Feedback {
	
	private static final long serialVersionUID = 7L;

	private Customer hotelcustomer;
	
	private Hotel hotel;

	/**
	 * @return the customer
	 */
	
	@ManyToOne
	public Customer getHotelcustomer() {
		return hotelcustomer;
	}

	public void setHotelcustomer(Customer hotelcustomer) {
		this.hotelcustomer = hotelcustomer;
	}

	/**
	 * @return the hotel
	 */
	@ManyToOne
	public Hotel getHotel() {
		return hotel;
	}

	/**
	 * @param hotel the hotel to set
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	

}