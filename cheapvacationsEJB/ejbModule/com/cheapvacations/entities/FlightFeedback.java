package com.cheapvacations.entities;


import javax.persistence.*;

/**
 * @author ccaccia
 *
 */
@Entity
@Table(name="FlightFeedback")
public class FlightFeedback extends Feedback {
	
	private static final long serialVersionUID = 4L;

	private Customer flightcustomer;
	
	private Flight flight;

	/**
	 * @return the customer
	 */
	
	@ManyToOne
	public Customer getFlightcustomer() {
		return flightcustomer;
	}

	public void setFlightcustomer(Customer flightcustomer) {
		this.flightcustomer = flightcustomer;
	}

	
	
	/**
	 * @return the flight
	 */
	@ManyToOne
	public Flight getFlight() {
		return flight;
	}

	/**
	 * @param flight the flight to set
	 */
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
}