package com.cheapvacations.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

@NamedQueries({
	@NamedQuery(
		name="Flight.getFlightByDepartureAndArrivalAndAirline",
		query="SELECT f FROM Flight f WHERE f.departure = :departure AND f.arrival = :arrival AND f.airline = :airline"),
})

@Entity
@Table(name="Flight")
public class Flight implements Serializable {
	
	private static final long serialVersionUID = 3L;
	private long Id;
	private String code;
	private String airline;
	private String departure;
	private String arrival;
	/*
	private Time time;
	private Time duration;
	*/
	private float rating;

	private List<FlightFeedback> feedbacks; 
	
	private List<FlightOffer> relatedOffers;
	
	/* getters and setters */
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	public long getId() {
		return Id;
	}
	/**
	 * @param pk the id to set
	 */
	public void setId(long pk) {
		Id = pk;
	}
	
	/**
	 * @return the code
	 */
	@Column(name="CODE",length=20,nullable=false)
	public String getCode() {
		return code;
	}
	/**
	 * @param cd the code to set
	 */
	public void setCode(String cd) {
		this.code = cd;
	}
	
	/**
	 * @return the airline
	 */
	@Column(name="AIRLINE",length=20,nullable=false)
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	/**
	 * @return the departure
	 */
	@Column(name="DEPARTURE",nullable=false,length=45)
	public String getDeparture() {
		return departure;
	}
	/**
	 * @param departure the departure to set
	 */
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	/**
	 * @return the arrival
	 */
	@Column(name="ARRIVAL",nullable=false,length=45)
	public String getArrival() {
		return arrival;
	}
	/**
	 * @param arrival the arrival to set
	 */
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	
	/*
	@Column(name="TIME",nullable=true)
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	@Column(name="DURATION",nullable=true)
	public Time getDuration() {
		return duration;
	}
	public void setDuration(Time duration) {
		this.duration = duration;
	}
	*/
	
	/**
	 * @return the rating
	 */
	@Column(name="RATING")
	public float getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}
	/**
	 * @return the feedbacks
	 */
	@OneToMany(mappedBy="flight", cascade={CascadeType.ALL})
	public List<FlightFeedback> getFeedbacks() {
		return feedbacks;
	}
	/**
	 * @param feedbacks the feedbacks to set
	 */
	public void setFeedbacks(List<FlightFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	/**
	 * @return the relatedOffers
	 */
	@OneToMany(mappedBy="referenceFlight", cascade={CascadeType.ALL})
	public List<FlightOffer> getRelatedOffers() {
		return relatedOffers;
	}
	/**
	 * @param relatedOffers the relatedOffers to set
	 */
	public void setRelatedOffers(List<FlightOffer> relatedOffers) {
		this.relatedOffers = relatedOffers;
	}
	
	public void insertInRelatedOffers(FlightOffer fo){
		this.relatedOffers.add(fo);
	}
	
	public void removeFromRelatedOffers(FlightOffer fo){
		this.relatedOffers.remove(fo);
	}
}