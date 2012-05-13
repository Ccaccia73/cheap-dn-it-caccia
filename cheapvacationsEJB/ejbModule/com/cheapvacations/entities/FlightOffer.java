package com.cheapvacations.entities;


import java.util.List;

import javax.persistence.*;
/**
 * @author ccaccia
 *
 */

@NamedQueries({
	@NamedQuery(
		name="FlightOffer.getFlightOfferByIntersectingPeriod",
		query="SELECT fo FROM FlightOffer fo WHERE fo.referenceFlight = :flight AND" +
				" fo.referenceFlightOperator = :operator AND" +
				"( fo.startDate BETWEEN :startD1 AND :endD1 OR fo.endDate BETWEEN :startD2 AND :endD2)"),
	@NamedQuery(
		name="FlightOffer.getFlightOfferByFlightIdAndOperatorId",
		query="SELECT fo FROM FlightOffer fo WHERE fo.referenceFlight = :flight AND fo.referenceFlightOperator = :operator"),
		@NamedQuery(
				name="FlightOffer.getFlightOfferByFlightId",
				query="SELECT fo FROM FlightOffer fo WHERE fo.referenceFlight = :flight"),
})

@Entity
@Table(name="FlightOffer")
public class FlightOffer extends PackageOffer {
	
	private static final long serialVersionUID = 5L;

	private Operator referenceFlightOperator;
	
	private Flight referenceFlight;
	
	private List<VacationPackage> relatedDepartPackages;

	private List<VacationPackage> relatedReturnPackages;


	/**
	 * @return the referenceFlightOperator
	 */
	@ManyToOne
	public Operator getReferenceFlightOperator() {
		return referenceFlightOperator;
	}

	/**
	 * @param referenceFlightOperator the referenceFlightOperator to set
	 */
	public void setReferenceFlightOperator(Operator ref) {
		this.referenceFlightOperator = ref;
	}

	/**
	 * @return the referenceFlight
	 */
	@ManyToOne
	public Flight getReferenceFlight() {
		return referenceFlight;
	}

	/**
	 * @param referenceFlight the referenceFlight to set
	 */
	public void setReferenceFlight(Flight referenceFlight) {
		this.referenceFlight = referenceFlight;
	}

	/**
	 * @return the relatedDepartPackages
	 */
	@OneToMany(mappedBy="departFlight", cascade={CascadeType.ALL})
	public List<VacationPackage> getRelatedDepartPackages() {
		return relatedDepartPackages;
	}

	/**
	 * @param relatedDepartPackages the relatedDepartPackages to set
	 */
	public void setRelatedDepartPackages(List<VacationPackage> relatedDepartPackages) {
		this.relatedDepartPackages = relatedDepartPackages;
	}

	/**
	 * @return the relatedReturnPackages
	 */
	@OneToMany(mappedBy="returnFlight", cascade={CascadeType.ALL})
	public List<VacationPackage> getRelatedReturnPackages() {
		return relatedReturnPackages;
	}

	/**
	 * @param relatedReturnPackages the relatedReturnPackages to set
	 */
	public void setRelatedReturnPackages(List<VacationPackage> relatedReturnPackages) {
		this.relatedReturnPackages = relatedReturnPackages;
	}
	
	public void insertInRelatedDepartPackages(VacationPackage pack){
		this.relatedDepartPackages.add(pack);
	}
	
	public void insertInRelatedReturnPackages(VacationPackage pack){
		this.relatedReturnPackages.add(pack);
	}
	
	public void removeFromRelatedDepartPackages(VacationPackage pack){
		this.relatedDepartPackages.remove(pack);
	}
	
	public void removeFromRelatedReturnPackages(VacationPackage pack){
		this.relatedReturnPackages.remove(pack);
	}
}