package com.cheapvacations.logic;

import java.util.List;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class FlightFeedbackManager
 */
@Stateless(mappedName = "flightfeedbackJNDI")
public class FlightFeedbackManager implements FlightFeedbackManagerLocal {

    /**
     * Default constructor. 
     */
    public FlightFeedbackManager() {
        // TODO Auto-generated constructor stub
    }

	public List<Long> queryFlightFeedbacks(int mark, String Comment,long idCustomer, long idFlight) {
		// TODO Auto-generated method stub
		return null;
	}

	public long insertFlightFeedback(int mark, String Comment, long idCustomer, long idFlight) {
		// TODO Auto-generated method stub
		return 0;
	}

}
