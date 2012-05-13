package com.cheapvacations.logic;

import java.util.List;
import javax.ejb.Local;

import com.cheapvacations.entities.*;

import java.sql.Date;

@Local
public interface HotelOfferManagerLocal {
	public HotelOffer addHotelOffer(Date startDate, Date endDate, int price, long idOperator, long idHotel)  throws LogicException;
	public List<HotelOffer> queryHotelOffer(Date startDate, Date endDate, int price, long idOperator, long idHotel)  throws LogicException;
	public HotelOffer updateHotelOffer(Date startDate, Date endDate, int price, long idOperator, long idHotel);
	public void cancelHotelOffer(long id) throws LogicException;
	public HotelOffer findHotelOfferById(long Id);
	public List<HotelOffer> findHotelOfferByHotelIdAndOperatorId(long hotel_Id, long operator_Id);
	public List<HotelOffer> findHotelOfferByHotelId(long hotel_Id);
}



