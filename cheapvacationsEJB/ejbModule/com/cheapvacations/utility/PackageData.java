package com.cheapvacations.utility;

import java.sql.Date;
import java.io.*;

public class PackageData implements Serializable {
	private static final long serialVersionUID = 1L;
	public Date startDate;
	public Date endDate;
	public int price;
	public float markup;
	public int status;
	public long Id;
	public long idAgency;
	public long idCustomer;
	// Used in queries with Offers
	public long idDeparture;
	public long idReturn;
	public long idHotel;
	public String departurecity;
	public String arrivalcity;
	public String hotelname;
	// Used in queries with flights and hotels
	public long flight_Id;
	public long hotel_Id;
	
	public PackageData(){
		startDate = null;
		endDate = null;
		price = 0;
		markup = 0;
		status = -3;
		Id = 0;
		idAgency = 0;
		idCustomer = 0;
		idDeparture = 0;
		idReturn = 0;
		idHotel = 0;
		departurecity = null;
		arrivalcity = null;
		hotelname = null;
		flight_Id = 0;
		hotel_Id = 0;
	}
}
