package com.cheapvacations.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@NamedQueries({
	@NamedQuery(
		name="VacationPackage.getPackagesForGuestSession",
		query="SELECT p FROM VacationPackage p WHERE p.status = 2 AND p.startDate <= :date"),
	@NamedQuery(
		name="VacationPackage.getPackagesByDatesAndAgencyAndComponents",
		query="SELECT p FROM VacationPackage p WHERE" +
				" p.startDate = :startdate AND p.endDate = :enddate AND referenceAgency = :agency AND" + 
				" p.stayHotel = :hotel AND p.departFlight = :departf AND p.returnFlight = :returnf"),
	@NamedQuery(
		name="VacationPackage.getPackagesByFlightOfferId",
		query="SELECT p FROM VacationPackage p WHERE" +
				" p.departFlight = :departf OR p.returnFlight = :returnf"),
	@NamedQuery(
		name="VacationPackage.getPackagesByDepartFlightOfferId",
		query="SELECT p FROM VacationPackage p WHERE" +
				" p.departFlight = :departf"),
	@NamedQuery(
		name="VacationPackage.getPackagesByReturnFlightOfferId",
		query="SELECT p FROM VacationPackage p WHERE" +
				" p.returnFlight = :returnf"),
	@NamedQuery(
		name="VacationPackage.getPackagesByHotelOfferId",
		query="SELECT p FROM VacationPackage p WHERE" +
				" p.stayHotel = :hotel"),
	@NamedQuery(
		name="VacationPackage.getPackagesByHotelOfferIdAndAgency",
		query="SELECT p FROM VacationPackage p WHERE" +
				" p.stayHotel = :hotel AND p.referenceAgency = :agency"),
	@NamedQuery(
		name="VacationPackage.getPackagesByFlightOfferIdAndAgency",
		query="SELECT p FROM VacationPackage p WHERE" +
				" ( p.departFlight = :departf OR p.returnFlight = :returnf ) AND p.referenceAgency = :agency"),	
})


@Entity
@Table(name="VacationPackage")
public class VacationPackage implements Serializable {
	

	private static final long serialVersionUID = 13L;
	
	private long Id;
	private Date startDate;
	private Date endDate;
	private int price;
	private float markup;
	// AVAILABLE = 2
	// BOOKED = 1
	// ACQUIRED = 0
	// EXPIRED = -1
	private int status;
	private Integer version;
	
	private TravelAgency referenceAgency;
	
	private Customer buyer;
	
	private HotelOffer stayHotel;
	
	private FlightOffer departFlight;
	
	private FlightOffer returnFlight;

	
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
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
	}
	/**
	 * @return the startDate
	 */
	@Column(name="STARTDATE",nullable=false)
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * @return the endDate
	 */
	@Column(name="ENDDATE",nullable=false)
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the price
	 */
	@Column(name="PRICE",nullable=false)
	public int getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * @return the markup
	 */
	@Column(name="MARKUP",nullable=false)
	public float getMarkup() {
		return markup;
	}
	/**
	 * @param markup the markup to set
	 */
	public void setMarkup(float markup) {
		this.markup = markup;
	}
	
	/**
	 * @return the status
	 */
	@Column(name="STATUS")
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Version
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the referenceAgency
	 */
	@ManyToOne
	public TravelAgency getReferenceAgency() {
		return referenceAgency;
	}
	/**
	 * @param ref the referenceAgency to set
	 */
	public void setReferenceAgency(TravelAgency ref) {
		this.referenceAgency = ref;
	}
	/**
	 * @return the buyer
	 */
	@ManyToOne
	public Customer getBuyer() {
		return buyer;
	}
	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(Customer buyer) {
		this.buyer = buyer;
	}
	/**
	 * @return the stayHotel
	 */
	@ManyToOne
	public HotelOffer getStayHotel() {
		return stayHotel;
	}
	/**
	 * @param stayHotel the stayHotel to set
	 */
	public void setStayHotel(HotelOffer stayHotel) {
		this.stayHotel = stayHotel;
	}
	/**
	 * @return the departFlight
	 */
	@ManyToOne
	public FlightOffer getDepartFlight() {
		return departFlight;
	}
	/**
	 * @param departFlight the departFlight to set
	 */
	public void setDepartFlight(FlightOffer departFlight) {
		this.departFlight = departFlight;
	}
	/**
	 * @return the returnFlight
	 */
	@ManyToOne
	public FlightOffer getReturnFlight() {
		return returnFlight;
	}
	/**
	 * @param returnFlight the returnFlight to set
	 */
	public void setReturnFlight(FlightOffer returnFlight) {
		this.returnFlight = returnFlight;
	}	
}