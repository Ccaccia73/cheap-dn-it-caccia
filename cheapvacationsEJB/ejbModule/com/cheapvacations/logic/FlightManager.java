package com.cheapvacations.logic;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cheapvacations.entities.Flight;

/**
 * Session Bean implementation class FlightManager
 */
@Stateless(mappedName = "flightmanagerJNDI")
public class FlightManager implements FlightManagerLocal {
	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

	
    public FlightManager() {

   }

	public Flight addFlight(String code, String airline, String departure, String arrival) throws LogicException {

		Query q = manager.createNamedQuery("Flight.getFlightByDepartureAndArrivalAndAirline");
		q.setParameter( "departure", departure );
		q.setParameter("arrival", arrival );
		q.setParameter("airline", airline );

		@SuppressWarnings("unchecked")
		List<Flight> flights = (List<Flight>)q.getResultList();		

		if(flights.size() == 0){
			Flight f = new Flight();
			f.setCode(code);
			f.setAirline(airline);
			f.setDeparture(departure);
			f.setArrival(arrival);

			manager.persist(f);

			// System.out.println("Hotel added");

			return f;

		}else{
			// System.out.println("Hotel exists");
			throw new LogicException("Flight exists");
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Flight> queryFligths(String code, String airline, String departure, String arrival, float rating) {
				
		boolean valid = false;
		String qstring = "SELECT f FROM Flight f ";
		
		if(code != null){
			if(code.length() > 0){
				qstring = qstring + "WHERE f.code = :code ";
				valid = true;
			}
		}
		
		if(airline != null){
			if(airline.length() > 0){
				if(!valid){
					qstring = qstring +"WHERE f.airline = :airline ";
					valid = true;
				}else{
					qstring = qstring +"AND f.airline = :airline ";
				}
			}
		}
		
		if(departure != null){
			if(departure.length() > 0){
				if(!valid){
					qstring = qstring +"WHERE f.departure = :departure ";
					valid = true;
				}else{
					qstring = qstring +"AND f.departure = :departure ";
				}
			}
		}
		
		if(arrival != null){
			if(arrival.length() > 0){
				if(!valid){
					qstring = qstring +"WHERE f.arrival = :arrival ";
					valid = true;
				}else{
					qstring = qstring +"AND f.arrival = :arrival ";
				}
			}
		}

		if(rating >= 0 ){
			if(!valid){
				qstring = qstring +"f.rating >= :rating ";
				valid = true;
			}else{
				qstring = qstring +"AND f.rating = :rating ";
			}
		}
		
		// System.out.println(qstring);
		Query q1 = manager.createQuery(qstring);
		
		if(valid){
			
			if(code != null){
				if(code.length() > 0){
					q1.setParameter("code", code);
				}
			}
			
			if(airline != null){
				if(airline.length() > 0){
					q1.setParameter("airline", airline);					
				}
			}
			
			if(departure != null){
				if(departure.length() > 0){
					q1.setParameter("departure", departure);
				}
			}
			
			if(arrival != null){
				if(arrival.length() > 0){
					q1.setParameter("arrival", arrival);
				}
			}
			
			if(rating >= 0 ){
				q1.setParameter("rating", rating);
			}
						
		}
		return (List<Flight>)q1.getResultList();
		
	}

	public Flight updateFlight(String code, String airline, String departure, String arrival, float rating) {
		
		Query q = manager.createNamedQuery("Flight.getFlightByDepartureAndArrivalAndAirline");
		q.setParameter( "departure", departure );
		q.setParameter("arrival", arrival );
		q.setParameter("airline", airline );

		@SuppressWarnings("unchecked")
		List<Flight> flights = (List<Flight>)q.getResultList();
		
		Flight f = manager.find(Flight.class, flights.get(0).getId());
		
		f.setCode(code);
		f.setRating(rating);
		
		manager.merge(f);
		
		return f;
	}

	public void cancelFlight(long Id) throws LogicException {
		
		Flight f = manager.find(Flight.class, Id);
		
		if(f.getRelatedOffers().size() == 0){
			manager.remove(f);
		} else {
			throw new LogicException("Impossible to remove Flight: Related offers present");
		}
	}

	public Flight findFlightById(long Id) {
		return manager.find(Flight.class, Id);
	}

}
