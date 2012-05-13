package com.cheapvacations.logic;

import com.cheapvacations.entities.*;

import javax.ejb.Local;
import java.util.*;

@Local
public interface HotelManagerLocal {
	public Hotel addHotel(String name, String city, String address, int stars) throws LogicException;
	public List<Hotel> queryHotels(String name, String city, int stars, float rating);
	public Hotel updateHotel(String name, String city, String address, int stars, float rating);
	public void cancelHotel(long Id) throws LogicException;
	public Hotel findHotelById(long Id);
}
