package com.cheapvacations.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;


@MappedSuperclass
@Entity
@Table(name="Offers")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class PackageOffer implements Serializable {
	
	private static final long serialVersionUID = 10L;
	protected long Id;
	protected Date startDate;
	protected Date endDate;
	protected int price;
	
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
	 * @return the startDate
	 */
	@Column(name="STARTDATE",nullable=false)
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date date) {
		this.startDate = date;
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
	public void setEndDate(Date date) {
		this.endDate = date;
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
}