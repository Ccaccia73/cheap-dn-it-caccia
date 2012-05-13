package com.cheapvacations.logic;

import com.cheapvacations.utility.*;
import java.util.List;

import javax.ejb.Local;

import com.cheapvacations.entities.VacationPackage;

@Local
public interface PackageOfferManagerLocal {
	public VacationPackage addVacationPackageOffer(PackageData pack)  throws LogicException;
	public List<VacationPackage> queryVacationPackageOffer(PackageData pack, int range);
	public VacationPackage updateVacationPackageOffer(PackageData pack);
	public void cancelVacationPackageOffer(long id) throws LogicException;
	public VacationPackage findVacationPackageOfferByID(long Id);
	
	public List<VacationPackage> findVacationPackageOfferByFlightId(long flight_Id);
	public List<VacationPackage> findVacationPackageOfferByHotelId(long hotel_Id);

	public List<VacationPackage> findVacationPackageOfferByFlightOfferId(long flightoffer_Id);
	public List<VacationPackage> findVacationPackageOfferByDepartFlightOfferId(long flightoffer_Id);
	public List<VacationPackage> findVacationPackageOfferByReturnFlightOfferId(long flightoffer_Id);
	public List<VacationPackage> findVacationPackageOfferByHotelOfferId(long hoteloffer_Id);
	
	public List<VacationPackage> findVacationPackageOfferByFlightOfferIdAndAgency(long flightoffer_Id, long idAgency);
	public List<VacationPackage> findVacationPackageOfferByHotelOfferIdAndAgency(long hoteloffer_Id, long idAgency);	

}
