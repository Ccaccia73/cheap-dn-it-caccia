package com.cheapvacations.logic;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import com.cheapvacations.entities.FlightOffer;

@Local
public interface FlightOfferManagerLocal {
	public FlightOffer addFlightOffer(Date startDate, Date endDate, int price, long idOperator, long idFlight)  throws LogicException;
	public List<FlightOffer> queryFlightOffer(Date startDate, Date endDate, int price, long idOperator, long idFlight)  throws LogicException;
	public FlightOffer updateFlightOffer(Date startDate, Date endDate, int price, long idOperator, long idFlight);
	public void cancelFlightOffer(long id) throws LogicException;
	public FlightOffer findFlightOfferById(long Id);
	public List<FlightOffer> findFlightOfferByFlightIdAndOperatorId(long flight_Id, long operator_Id);
	public List<FlightOffer> findFlightOfferByFlightId(long flight_Id);
}
