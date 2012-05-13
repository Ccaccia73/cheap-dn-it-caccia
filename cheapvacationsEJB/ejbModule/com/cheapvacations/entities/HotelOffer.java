package com.cheapvacations.entities;

import javax.persistence.*;

import java.util.*;

/**
 * @author ccaccia
 *
 */

@NamedQueries({
	@NamedQuery(
		name="HotelOffer.getHotelOfferByIntersectingPeriod",
		query="SELECT ho FROM HotelOffer ho WHERE ho.referenceHotel = :hotel AND" +
				" ho.referenceHotelOperator = :operator AND" +
				"( ho.startDate BETWEEN :startD1 AND :endD1 OR ho.endDate BETWEEN :startD2 AND :endD2)"),
	@NamedQuery(
		name="HotelOffer.getHotelOfferByHotelIdAndOperatorId",
		query="SELECT ho FROM HotelOffer ho WHERE ho.referenceHotel = :hotel AND ho.referenceHotelOperator = :operator"),
	@NamedQuery(
		name="HotelOffer.getHotelOfferByHotelId",
		query="SELECT ho FROM HotelOffer ho WHERE ho.referenceHotel = :hotel"),
})

@Entity
@Table(name="HotelOffer")
public class HotelOffer extends PackageOffer {
	
	private static final long serialVersionUID = 8L;

	private Operator referenceHotelOperator;
	
	private Hotel referenceHotel;
	
	private List<VacationPackage> relatedPackages;

	/**
	 * @return the referenceHotelOperator
	 */
	@ManyToOne
	public Operator getReferenceHotelOperator() {
		return referenceHotelOperator;
	}

	/**
	 * @param referenceHotelOperator the referenceHotelOperator to set
	 */
	public void setReferenceHotelOperator(Operator ref) {
		this.referenceHotelOperator = ref;
	}

	/**
	 * @return the referenceHotel
	 */
	@ManyToOne
	public Hotel getReferenceHotel() {
		return referenceHotel;
	}

	/**
	 * @param referenceHotel the referenceHotel to set
	 */
	public void setReferenceHotel(Hotel referenceHotel) {
		this.referenceHotel = referenceHotel;
	}

	/**
	 * @return the relatedPackages
	 */
	@OneToMany(mappedBy="stayHotel", cascade={CascadeType.ALL} )
	public List<VacationPackage> getRelatedPackages() {
		return relatedPackages;
	}

	/**
	 * @param relatedPackages the relatedPackages to set
	 */
	public void setRelatedPackages(List<VacationPackage> relatedPackages) {
		this.relatedPackages = relatedPackages;
	}
	
	public void insertInRelatedPackages(VacationPackage pack){
		this.relatedPackages.add(pack);
	}
	
	public void removeFromRelatedPAckages(VacationPackage pack){
		this.relatedPackages.remove(pack);
	}
}