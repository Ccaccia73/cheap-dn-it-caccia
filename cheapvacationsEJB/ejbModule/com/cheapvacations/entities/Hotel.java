package com.cheapvacations.entities;


import java.io.Serializable;
import javax.persistence.*;

import java.util.*;

@NamedQueries({
	@NamedQuery(
		name="Hotel.getHotelByNameAndCity",
		query="SELECT h FROM Hotel h WHERE h.name = :name AND h.city = :city"),
})

@Entity
@Table(name="Hotel")
public class Hotel implements Serializable {

	private static final long serialVersionUID = 6L;
	private long Id;
	private String name;
	private String city;
	private String address;
	private int stars;
	private float rating;
	
	private List<HotelFeedback> feedbacks;
	
	private List<HotelOffer> relatedOffers; 
	
	
	
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
	 * @return the name
	 */
	@Column(name="NAME",nullable=false,length=45)
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the city
	 */
	@Column(name="CITY",nullable=false,length=45)
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return the address
	 */
	@Column(name="ADDRESS",nullable=false,length=90)
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @return the stars
	 */
	@Column(name="STARS",nullable=true)
	public int getStars() {
		return stars;
	}
	/**
	 * @param stars the stars to set
	 */
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	/**
	 * @return the rating
	 */
	@Column(name="RATING",nullable=true)
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
	@OneToMany(mappedBy="hotel", cascade={CascadeType.ALL})
	public List<HotelFeedback> getFeedbacks() {
		return feedbacks;
	}
	/**
	 * @param feedbacks the feedbacks to set
	 */
	public void setFeedbacks(List<HotelFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	/**
	 * @return the relatedOffers
	 */
	@OneToMany(mappedBy="referenceHotel", cascade={CascadeType.ALL})
	public List<HotelOffer> getRelatedOffers() {
		return relatedOffers;
	}
	/**
	 * @param relatedOffers the relatedOffers to set
	 */
	public void setRelatedOffers(List<HotelOffer> relatedOffers) {
		this.relatedOffers = relatedOffers;
	}
	
	public void insertInRelatedOffers(HotelOffer ho){
		this.relatedOffers.add(ho);
	}
	
	public void removeFromRelatedOffers(HotelOffer ho){
		this.relatedOffers.remove(ho);
	}
}