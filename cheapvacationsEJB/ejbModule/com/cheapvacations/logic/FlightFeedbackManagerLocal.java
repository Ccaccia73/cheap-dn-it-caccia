package com.cheapvacations.logic;

import java.util.List;

import javax.ejb.Local;

@Local
public interface FlightFeedbackManagerLocal {
	public List<Long> queryFlightFeedbacks(int mark, String Comment, long idCustomer, long idFlight);
	public long insertFlightFeedback(int mark, String Comment, long idCustomer, long idFlight);
}
