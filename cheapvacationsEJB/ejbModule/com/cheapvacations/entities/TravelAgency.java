package com.cheapvacations.entities;

import javax.persistence.*;

import java.util.*;

@NamedQueries({
	@NamedQuery(
		name="TravelAgency.getTravelAgencyByUserAndPassword",
		query="SELECT ta FROM TravelAgency ta WHERE ta.username = :name AND ta.password = :pw"),
	@NamedQuery(
		name="TravelAgency.getTravelAgencyByUser",
		query="SELECT ta FROM TravelAgency ta WHERE ta.username = :name"),
})
@Entity
// caso single table
// @DiscriminatorValue(value="T")
// caso table x class
@Table(name="TravelAgency")
public class TravelAgency extends User {
	
	private static final long serialVersionUID = 11L;
	private List<VacationPackage> myPackages;

	/**
	 * @return the myPackages
	 */
	@OneToMany(mappedBy="referenceAgency", cascade={CascadeType.ALL})
	public List<VacationPackage> getMyPackages() {
		return myPackages;
	}

	/**
	 * @param packs the myPackages to set
	 */
	public void setMyPackages(List<VacationPackage> packs) {
		this.myPackages = packs;
	}
	
	public void removeFromMyPackages(VacationPackage pack){
		this.myPackages.remove(pack);
	}
	
	public void insertInMyPackages(VacationPackage pack){
		this.myPackages.add(pack);
	}
}