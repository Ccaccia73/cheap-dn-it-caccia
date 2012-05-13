package com.cheapvacations.logic;

import java.util.*;

import com.cheapvacations.entities.*;

import javax.ejb.Stateless;
import javax.persistence.*;


/**
 * Session Bean implementation class HotelManager
 */
@Stateless(mappedName = "hotelmanagerJNDI")
public class HotelManager implements HotelManagerLocal {

	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

	
    public HotelManager() {

   }

	public Hotel addHotel(String name, String city, String address, int stars) throws LogicException {
				
		Query q = manager.createNamedQuery("Hotel.getHotelByNameAndCity");
		q.setParameter( "name", name );
		q.setParameter("city", city );

		@SuppressWarnings("unchecked")
		List<Hotel> hotels = (List<Hotel>)q.getResultList();		
		
		if(hotels.size() == 0){
			Hotel h = new Hotel();
			h.setName(name);
			h.setCity(city);
			h.setAddress(address);
			h.setStars(stars);
			h.setRating(0);
			
			manager.persist(h);
			
			// System.out.println("Hotel added");
			
			return h;
			
		}else{
			// System.out.println("Hotel exists");
			throw new LogicException("Hotel exists");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Hotel> queryHotels(String name, String city, int stars, float rating) {
		
		boolean valid = false;
		String qstring = "SELECT h FROM Hotel h";
		
		if(name != null){
			if(name.length() > 0){
				qstring = qstring + " WHERE h.name = :name ";
				valid = true;
			}
		}
		
		if(city != null){
			if(city.length() > 0){
				if(!valid){
					qstring = qstring +" WHERE h.city = :city ";
					valid = true;
				}else{
					qstring = qstring +"AND h.city = :city ";
				}
			}
		}
		
		if(stars > 0 && stars <5 ){
			if(!valid){
				qstring = qstring +" WHERE h.stars >= :stars ";
				valid = true;
			}else{
				qstring = qstring +"AND h.stars = :stars ";
			}
		}
		
		if(rating >= 0 ){
			if(!valid){
				qstring = qstring +" WHERE h.rating >= :rating ";
				valid = true;
			}else{
				qstring = qstring +"AND h.rating = :rating ";
			}
		}
		
		Query q1 = manager.createQuery(qstring);
		
		if(valid){
			// System.out.println(qstring);
			
			
			
			if(name != null){
				if(name.length() > 0){
					q1.setParameter("name", name);
				}
			}
			
			if(city != null){
				if(city.length() > 0){
					q1.setParameter("city", city);					
				}
			}
			
			if(stars > 0 && stars <5 ){
				q1.setParameter("stars", stars);
			}
			
			if(rating >= 0 ){
				q1.setParameter("rating", rating);
			}
						
			
			
		}else{
			// throw new LogicException("Query Hotel Failed");
		}
		return (List<Hotel>)q1.getResultList();
	}

	public Hotel updateHotel(String name, String city, String address, int stars, float rating) {
		
		Query q = manager.createNamedQuery("Hotel.getHotelByNameAndCity");
		q.setParameter( "name", name );
		q.setParameter("city", city );

		@SuppressWarnings("unchecked")
		List<Hotel> hotels = (List<Hotel>)q.getResultList();
		
		Hotel h = manager.find(Hotel.class, hotels.get(0).getId());
		
		h.setAddress(address);
		h.setStars(stars);
		h.setRating(rating);
		
		manager.merge(h);
		
		return h;
	}

	public void cancelHotel(long Id) throws LogicException {
		
		Hotel h = manager.find(Hotel.class, Id);
		
		if(h.getRelatedOffers().size() == 0){
			manager.remove(h);
		} else {
			throw new LogicException("Impossible to remove Hotel: Related offers present");
		}
	}

	public Hotel findHotelById(long Id) {
		return manager.find(Hotel.class, Id);
	}

}
