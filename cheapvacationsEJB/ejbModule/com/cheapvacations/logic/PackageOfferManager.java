package com.cheapvacations.logic;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cheapvacations.entities.Customer;
import com.cheapvacations.entities.Flight;
import com.cheapvacations.entities.FlightOffer;
import com.cheapvacations.entities.Hotel;
import com.cheapvacations.entities.HotelOffer;
import com.cheapvacations.entities.TravelAgency;
import com.cheapvacations.entities.VacationPackage;
import com.cheapvacations.utility.*;

/**
 * Session Bean implementation class PackageOfferManager
 */
@Stateless(mappedName = "packageoffermanagerJNDI")
public class PackageOfferManager implements PackageOfferManagerLocal {
	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

    /**
     * Default constructor. 
     */
    public PackageOfferManager() {
    	
    }

	public VacationPackage addVacationPackageOffer(PackageData pack) throws LogicException {
		
		FlightOffer foDep = manager.find(FlightOffer.class, pack.idDeparture);
		FlightOffer foRet = manager.find(FlightOffer.class, pack.idReturn);
		HotelOffer ho = manager.find(HotelOffer.class, pack.idHotel);
		
		TravelAgency ta = manager.find(TravelAgency.class, pack.idAgency);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByDatesAndAgencyAndComponents");
		q.setParameter("startdate", pack.startDate );
		q.setParameter("enddate", pack.endDate );
		q.setParameter("agency", ta );
		q.setParameter("hotel", ho );
		q.setParameter("departf", foDep );
		q.setParameter("returnf", foRet );
		
		@SuppressWarnings("unchecked")
		List<VacationPackage> offers = (List<VacationPackage>)q.getResultList();		
		
		if(offers.size() == 0){
			VacationPackage po = new VacationPackage();
			
			po.setDepartFlight(foDep);
			po.setReturnFlight(foRet);
			po.setStayHotel(ho);
			po.setStartDate(pack.startDate);
			po.setEndDate(pack.endDate);
			po.setReferenceAgency(ta);
			po.setBuyer(null);
			po.setMarkup(pack.markup);
			po.setPrice(pack.price);
			po.setStatus(2);
						
			manager.persist(po);
			
			ho.insertInRelatedPackages(po);
			manager.persist(ho);
			
			foDep.insertInRelatedDepartPackages(po);
			manager.persist(foDep);
			
			foRet.insertInRelatedReturnPackages(po);
			manager.persist(foRet);
			
			ta.insertInMyPackages(po);
			manager.persist(ta);
			
			// System.out.println("Package added");
			
			return po;
			
		}else{
			// System.out.println("Hotel exists");
			throw new LogicException("Incompatible Vacation Package Offer");
		}
	}

	@SuppressWarnings("unchecked")
	public List<VacationPackage> queryVacationPackageOffer(PackageData pack, int range) {
		
		DateUtility du = new DateUtility();
		
		boolean valid = false;
		
		String qstring = "SELECT po FROM VacationPackage po";
		
		if(pack.startDate != null){
			qstring = qstring + " WHERE (po.startDate BETWEEN :startD1 AND :endD1)";
			valid = true;
		}
		
		if(pack.endDate != null){
			if(!valid){
				qstring = qstring +" WHERE (po.endDate BETWEEN :startD2 AND :endD2)";
				valid = true;
			}else{
				qstring = qstring +" AND (po.endDate BETWEEN :startD2 AND :endD2)";
			}
		}
		
		if(pack.price > 0 ){
			if(!valid){
				qstring = qstring +" WHERE po.price <= :price";
				valid = true;
			}else{
				qstring = qstring +" AND po.price <= :price";
			}
		}
		
		if(pack.status >= -1 && pack.status <= 2 ){
			if(!valid){
				qstring = qstring +" WHERE po.status = :status";
				valid = true;
			}else{
				qstring = qstring +" AND po.status = :status";
			}
		}

		if(pack.idAgency > 0 ){
			if(!valid){
				qstring = qstring +" WHERE po.referenceAgency = :agency";
				valid = true;
			}else{
				qstring = qstring +" AND po.referenceAgency = :agency";
			}
		}

		if(pack.departurecity != null ){
			if(pack.departurecity.length() > 0){
				if(!valid){
					qstring = qstring +" WHERE po.departFlight.referenceFlight.departure = :departure";
					valid = true;
				}else{
					qstring = qstring +" AND po.departFlight.referenceFlight.departure = :departure";
				}
			}
		}
		
		if(pack.arrivalcity != null ){
			if(pack.arrivalcity.length() >0){
				if(!valid){
					qstring = qstring +" WHERE po.departFlight.referenceFlight.arrival = :arrival";
					valid = true;
				}else{
					qstring = qstring +" AND po.departFlight.referenceFlight.arrival = :arrival";
				}
			}
		}
		
		if(pack.hotelname != null){
			if(pack.hotelname.length()>0){
				if(!valid){
					qstring = qstring +" WHERE po.stayHotel.referenceHotel.name = :hotelname";
					valid = true;
				}else{
					qstring = qstring +" AND po.stayHotel.referenceHotel.name = :hotelname";
				}
			}
		}
		

		if(pack.idCustomer > 0 ){
			if(!valid){
				qstring = qstring +" WHERE po.buyer = :buyer";
				valid = true;
			}else{
				qstring = qstring +" AND po.buyer = :buyer";
			}
		}
		
		// System.out.println(qstring);
		
		Query q1 = manager.createQuery(qstring);
		
		if(valid){
			
			if(pack.startDate != null){
				q1.setParameter("startD1", du.addDays(pack.startDate, -range));
				q1.setParameter("endD1",  du.addDays(pack.startDate, range));
			}
			
			if(pack.endDate != null){
				q1.setParameter("startD2", du.addDays(pack.endDate, -range));
				q1.setParameter("endD2", du.addDays(pack.endDate, range));
			}
			
			if(pack.price > 0 ){
				q1.setParameter("price", pack.price);
			}
			
			if(pack.status >= -1 && pack.status <= 2 ){
				q1.setParameter("status", pack.status);
			}

			if(pack.idAgency > 0 ){
				q1.setParameter("agency", manager.find(TravelAgency.class, pack.idAgency));
			}

			if(pack.departurecity != null ){
				if(pack.departurecity.length()>0){
					q1.setParameter("departure",pack.departurecity);
				}
			}
			
			if(pack.arrivalcity != null ){
				if(pack.arrivalcity.length()>0){
					q1.setParameter("arrival",pack.arrivalcity);
				}
			}
			
			if(pack.hotelname != null ){
				if(pack.hotelname.length()>0){
					q1.setParameter("hotelname",pack.hotelname);
				}
			}
			
			if(pack.idCustomer > 0 ){
				q1.setParameter("buyer",manager.find(Customer.class, pack.idCustomer));
			}
						
		}
		
		// List<VacationPackage> tmp = (List<VacationPackage>)q1.getResultList();
		
		// System.out.print("Query return size: "+tmp.size());
		
		return (List<VacationPackage>)q1.getResultList();
	}

	public VacationPackage updateVacationPackageOffer(PackageData pack) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelVacationPackageOffer(long id) throws LogicException {
		
		VacationPackage vp = manager.find(VacationPackage.class, id);
		
		if(vp.getStatus() == 2 || vp.getStatus() == -1){
			
			vp.getDepartFlight().removeFromRelatedDepartPackages(vp);
			manager.merge(vp.getDepartFlight());
			
			vp.getReturnFlight().removeFromRelatedReturnPackages(vp);
			manager.merge(vp.getReturnFlight());
			
			vp.getStayHotel().removeFromRelatedPAckages(vp);
			manager.merge(vp.getStayHotel());
			
			vp.getReferenceAgency().removeFromMyPackages(vp);
			manager.remove(vp.getReferenceAgency());
			
			
			manager.remove(vp);
			
		}else{
			throw new LogicException("Impossible to remove Package");
		}
	}

	public VacationPackage findVacationPackageOfferByID(long Id) {
		return manager.find(VacationPackage.class, Id);
	}

	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByFlightId( long flight_Id) {
		Flight f = manager.find(Flight.class, flight_Id);
				
		Query q1 = manager.createQuery("SELECT po FROM VacationPackage po "+
		"WHERE (po.departFlight.referenceFlight = :flight1 OR po.returnFlight.referenceFlight = :flight2)");

		q1.setParameter("flight1", f );
		q1.setParameter("flight2", f );
		
		return (List<VacationPackage>)q1.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByHotelId(long hotel_Id) {
		
		Hotel h = manager.find(Hotel.class, hotel_Id);
		
		Query q1 = manager.createQuery("SELECT po FROM VacationPackage po "+
		"WHERE po.stayHotel.referenceHotel = :hotel");
		q1.setParameter("hotel", h );
		
		return (List<VacationPackage>)q1.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByFlightOfferId(
			long flightoffer_Id) {
		
		FlightOffer fo = manager.find(FlightOffer.class, flightoffer_Id);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByFlightOfferId");
		q.setParameter("departf", fo );
		q.setParameter("returnf", fo);
		
		return (List<VacationPackage>)q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByDepartFlightOfferId(
			long flightoffer_Id) {
		
		FlightOffer fo = manager.find(FlightOffer.class, flightoffer_Id);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByDepartFlightOfferId");
		q.setParameter("departf", fo );
		
		return (List<VacationPackage>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByReturnFlightOfferId(
			long flightoffer_Id) {
		
		FlightOffer fo = manager.find(FlightOffer.class, flightoffer_Id);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByReturnFlightOfferId");
		q.setParameter("returnf", fo);
		
		return (List<VacationPackage>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByHotelOfferId(
			long hoteloffer_Id) {

		HotelOffer ho = manager.find(HotelOffer.class, hoteloffer_Id);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByHotelOfferId");
		q.setParameter("hotel", ho );
		
		return (List<VacationPackage>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByFlightOfferIdAndAgency(
			long flightoffer_Id, long idAgency) {
		
		FlightOffer fo = manager.find(FlightOffer.class, flightoffer_Id);
		TravelAgency a = manager.find(TravelAgency.class, idAgency);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByFlightOfferIdAndAgency");
		q.setParameter("departf", fo );
		q.setParameter("returnf", fo);
		q.setParameter("agency", a);
		
		return (List<VacationPackage>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<VacationPackage> findVacationPackageOfferByHotelOfferIdAndAgency(
			long hoteloffer_Id, long idAgency) {
		
		HotelOffer ho = manager.find(HotelOffer.class, hoteloffer_Id);
		TravelAgency a = manager.find(TravelAgency.class, idAgency);
		
		Query q = manager.createNamedQuery("VacationPackage.getPackagesByHotelOfferId");
		q.setParameter("hotel", ho );
		q.setParameter("agency", a);
		
		return (List<VacationPackage>)q.getResultList();
	}
}
