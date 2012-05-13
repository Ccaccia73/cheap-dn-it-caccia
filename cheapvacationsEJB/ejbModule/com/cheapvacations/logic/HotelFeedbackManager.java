package com.cheapvacations.logic;

import java.util.List;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class HotelFeedbackManager
 */
@Stateless(mappedName = "hotelfeedbackmanagerJNDI")
public class HotelFeedbackManager implements HotelFeedbackManagerLocal {

    /**
     * Default constructor. 
     */
    public HotelFeedbackManager() {
        // TODO Auto-generated constructor stub
    }

	public List<Long> queryHotelFeedbacks(int mark, String Comment,
			long idCustomer, long idHotel) {
		// TODO Auto-generated method stub
		return null;
	}

	public long insertHotelFeedback(int mark, String Comment, long idCustomer,
			long idHotel) {
		// TODO Auto-generated method stub
		return 0;
	}

}
