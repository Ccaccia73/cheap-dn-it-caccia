package com.cheapvacations.logic;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cheapvacations.entities.*;
import com.cheapvacations.utility.*;

/**
 * Session Bean implementation class HotelOfferManager
 */
@Stateless(mappedName = "hoteloffermanagerJNDI")
public class HotelOfferManager implements HotelOfferManagerLocal {

	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

	
    public HotelOfferManager() {

    }	
    
	public HotelOffer addHotelOffer(Date startDate, Date endDate, int price, long idOperator, long idHotel) throws LogicException {
		
		Hotel h = manager.find(Hotel.class, idHotel);
		Operator o = manager.find(Operator.class, idOperator);
		
		Query q = manager.createNamedQuery("HotelOffer.getHotelOfferByIntersectingPeriod");
		q.setParameter("hotel", h );
		q.setParameter("operator", o );
		q.setParameter("startD1", startDate );
		q.setParameter("endD1", endDate );
		q.setParameter("startD2", startDate );
		q.setParameter("endD2", endDate );
		
		@SuppressWarnings("unchecked")
		List<HotelOffer> offers = (List<HotelOffer>)q.getResultList();		
		
		if(offers.size() == 0){
			HotelOffer ho = new HotelOffer();
			ho.setReferenceHotelOperator(o);
			ho.setReferenceHotel(h);
			ho.setPrice(price);
			ho.setStartDate(startDate);
			ho.setEndDate(endDate);
			
			manager.persist(ho);
			
			// System.out.println("Hotel added");
			
			return ho;
			
		}else{
			// System.out.println("Hotel exists");
			throw new LogicException("Incompatible Hotel Offer");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<HotelOffer> queryHotelOffer(Date startDate, Date endDate, int price, long idOperator, long idHotel) throws LogicException {
		
		DateUtility du = new DateUtility();
		
		boolean valid = false;
		String qstring = "SELECT ho FROM HotelOffer ho";
		
		if(startDate != null){
			qstring = qstring + " WHERE (ho.startDate BETWEEN :startD1 AND :endD1)";
			valid = true;
		}
		
		if(endDate != null){
			if(!valid){
				qstring = qstring +" WHERE (ho.endDate BETWEEN :startD2 AND :endD2)";
				valid = true;
			}else{
				qstring = qstring +"AND (ho.endDate BETWEEN :startD2 AND :endD2)";
			}
		}
		
		if(price > 0 ){
			if(!valid){
				qstring = qstring +" WHERE ho.price <= :price";
				valid = true;
			}else{
				qstring = qstring +" AND ho.price <= :price";
			}
		}
		
		if(idOperator > 0 ){
			if(!valid){
				qstring = qstring +" WHERE ho.referenceHotel = :operator";
				valid = true;
			}else{
				qstring = qstring +" AND ho.referenceHotelOperator = :operator";
			}
		}

		if(idHotel > 0 ){
			if(!valid){
				qstring = qstring +" WHERE ho.referenceHotel = :hotel";
				valid = true;
			}else{
				qstring = qstring +" AND ho.referenceHotel = :hotel";
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
						
			if(idHotel != 0 ){
				q1.setParameter("hotel", manager.find(Hotel.class, idHotel));
			}
						
		}
		
		return (List<HotelOffer>)q1.getResultList();
	}

	public HotelOffer updateHotelOffer(Date startDate, Date endDate, int price, long idOperator, long idHotel) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelHotelOffer(long id) throws LogicException {
		
		
		HotelOffer ho = manager.find(HotelOffer.class, id);
		
		if(ho.getRelatedPackages().size() == 0){
			
			ho.getReferenceHotel().removeFromRelatedOffers(ho);
			manager.merge(ho.getReferenceHotel());
			
			ho.getReferenceHotelOperator().removeFromMyHotelOffers(ho);
			manager.merge(ho.getReferenceHotelOperator());
			
			ho.getReferenceHotel().removeFromRelatedOffers(ho);
			manager.merge(ho.getReferenceHotel());
			
			manager.remove(ho);
		}else{
			throw new LogicException("Impossible to remove Hotel Offer");
		}
		
		
	}

	public HotelOffer findHotelOfferById(long Id) {
		return manager.find(HotelOffer.class, Id);
	}

	@SuppressWarnings("unchecked")
	public List<HotelOffer> findHotelOfferByHotelIdAndOperatorId(long hotel_Id, long operator_Id) {
		
		Hotel h = manager.find(Hotel.class, hotel_Id);
		Operator o = manager.find(Operator.class, operator_Id);
		
		Query q = manager.createNamedQuery("HotelOffer.getHotelOfferByHotelIdAndOperatorId");
		q.setParameter("hotel", h );
		q.setParameter("operator", o );
		
		return (List<HotelOffer>)q.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<HotelOffer> findHotelOfferByHotelId(long hotel_Id) {
		Hotel h = manager.find(Hotel.class, hotel_Id);
		
		Query q = manager.createNamedQuery("HotelOffer.getHotelOfferByHotelId");
		q.setParameter("hotel", h );
		
		return (List<HotelOffer>)q.getResultList();		
	}
}
