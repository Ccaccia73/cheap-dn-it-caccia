package com.cheapvacations.logic;

import javax.ejb.Local;

import com.cheapvacations.entities.*;

import java.util.*;

@Local
public interface FlightManagerLocal {
	public Flight addFlight(String code, String airline, String departure, String arrival) throws LogicException;
	public List<Flight> queryFligths(String code, String airline, String departure, String arrival, float rating);
	public Flight updateFlight(String code, String airline, String departure, String arrival, float rating);
	public void cancelFlight(long Id) throws LogicException;
	public Flight findFlightById(long Id);
}
