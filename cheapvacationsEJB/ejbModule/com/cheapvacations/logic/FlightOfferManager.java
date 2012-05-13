package com.cheapvacations.logic;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cheapvacations.entities.FlightOffer;
import com.cheapvacations.entities.Flight;
import com.cheapvacations.entities.Operator;
import com.cheapvacations.utility.DateUtility;

/**
 * Session Bean implementation class FlightOfferManager
 */
@Stateless(mappedName = "flightoffermanagerJNDI")
public class FlightOfferManager implements FlightOfferManagerLocal {

	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

	
    public FlightOfferManager() {
        
    }


	public FlightOffer addFlightOffer(Date startDate, Date endDate, int price, long idOperator, long idFlight) throws LogicException {
		
		Flight f = manager.find(Flight.class, idFlight);
		Operator o = manager.find(Operator.class, idOperator);
		
		Query q = manager.createNamedQuery("FlightOffer.getFlightOfferByIntersectingPeriod");
		q.setParameter("flight", f );
		q.setParameter("operator", o );
		q.setParameter("startD1", startDate );
		q.setParameter("endD1", endDate );
		q.setParameter("startD2", startDate );
		q.setParameter("endD2", endDate );
		
		@SuppressWarnings("unchecked")
		List<FlightOffer> offers = (List<FlightOffer>)q.getResultList();		
		
		if(offers.size() == 0){
			FlightOffer fo = new FlightOffer();
			fo.setReferenceFlightOperator(o);
			fo.setReferenceFlight(f);
			fo.setPrice(price);
			fo.setStartDate(startDate);
			fo.setEndDate(endDate);
			
			manager.persist(fo);
			
			// System.out.println("Hotel added");
			
			return fo;
			
		}else{
			// System.out.println("Hotel exists");
			throw new LogicException("Incompatible Flight Offer");
		}
	}

	@SuppressWarnings("unchecked")
	public List<FlightOffer> queryFlightOffer(Date startDate, Date endDate, int price, long idOperator, long idFlight) throws LogicException {
		
		DateUtility du = new DateUtility();
		
		boolean valid = false;
		String qstring = "SELECT fo FROM FlightOffer fo";
		
		if(startDate != null){
			qstring = qstring + " WHERE (fo.startDate BETWEEN :startD1 AND :endD1)";
			valid = true;
		}
		
		if(endDate != null){
			if(!valid){
				qstring = qstring +" WHERE (fo.endDate BETWEEN :startD2 AND :endD2)";
				valid = true;
			}else{
				qstring = qstring +"AND (fo.endDate BETWEEN :startD2 AND :endD2)";
			}
		}
		
		if(price > 0 ){
			if(!valid){
				qstring = qstring +" WHERE fo.price <= :price";
				valid = true;
			}else{
				qstring = qstring +" AND fo.price <= :price";
			}
		}
		
		if(idOperator > 0 ){
			if(!valid){
				qstring = qstring +" WHERE fo.referenceFlightOperator = :operator";
				valid = true;
			}else{
				qstring = qstring +" AND fo.referenceFlightOperator = :operator";
			}
		}

		if(idFlight > 0 ){
			if(!valid){
				qstring = qstring +" WHERE fo.referenceFlight = :flight";
				valid = true;
			}else{
				qstring = qstring +" AND fo.referenceFlight = :flight";
			}
		}
		
		Query q1 = manager.createQuery(qstring);
		
		if(valid){
			
			if(startDate != null){
				q1.setParameter("startD1", du.addDays(startDate, -3));
				q1.setParameter("endD1",  du.addDays(startDate, 3));
			}
			
			if(endDate != null){
				q1.setParameter("startD2", du.addDays(endDate, -3));
				q1.setParameter("endD2", du.addDays(endDate, 3));
			}
			
			if(price > 0 ){
				q1.setParameter("price", price);
			}
			
			if(idOperator != 0 ){
				q1.setParameter("operator", manager.find(Operator.class, idOperator));
			}
						
			if(idFlight != 0 ){
				q1.setParameter("hotel", manager.find(Flight.class, idFlight));
			}
						
		}
		
		return (List<FlightOffer>)q1.getResultList();
	}

	public FlightOffer updateFlightOffer(Date startDate, Date endDate, int price, long idOperator, long idFlight) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelFlightOffer(long id) throws LogicException {
		
		FlightOffer fo = manager.find(FlightOffer.class, id);
		
		if( fo.getRelatedDepartPackages().size() == 0 && fo.getRelatedReturnPackages().size() == 0 ){
			
			fo.getReferenceFlight().removeFromRelatedOffers(fo);
			manager.merge(fo.getReferenceFlight());
			
			fo.getReferenceFlightOperator().removeFromMyFlightOffers(fo);
			manager.merge(fo.getReferenceFlightOperator());
			
			fo.getReferenceFlight().removeFromRelatedOffers(fo);
			manager.merge(fo.getReferenceFlight());
			
			manager.remove(fo);
		}else{
			throw new LogicException("Flight Offer not removable: Packages rely on it");
		}
	}
	
	public FlightOffer findFlightOfferById(long Id) {
		return manager.find(FlightOffer.class, Id);
	}

	@SuppressWarnings("unchecked")
	public List<FlightOffer> findFlightOfferByFlightIdAndOperatorId(long flight_Id, long operator_Id) {
		Flight f = manager.find(Flight.class, flight_Id);
		Operator o = manager.find(Operator.class, operator_Id);
		
		Query q = manager.createNamedQuery("FlightOffer.getFlightOfferByFlightIdAndOperatorId");
		q.setParameter("flight", f );
		q.setParameter("operator", o );
		
		
		return (List<FlightOffer>)q.getResultList();		
	}


	@SuppressWarnings("unchecked")
	public List<FlightOffer> findFlightOfferByFlightId(long flight_Id) {
		Flight f = manager.find(Flight.class, flight_Id);
		
		Query q = manager.createNamedQuery("FlightOffer.getFlightOfferByFlightId");
		q.setParameter("flight", f );
		
		
		return (List<FlightOffer>)q.getResultList();		
	}
}
