package com.cheapvacations.logic;

import java.util.List;
import javax.ejb.Local;

@Local
public interface HotelFeedbackManagerLocal {
	public List<Long> queryHotelFeedbacks(int mark, String Comment, long idCustomer, long idHotel);
	public long insertHotelFeedback(int mark, String Comment, long idCustomer, long idHotel);
}
