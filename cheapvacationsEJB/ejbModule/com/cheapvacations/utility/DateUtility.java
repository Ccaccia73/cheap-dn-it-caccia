package com.cheapvacations.utility;

import java.sql.Date;
import java.util.Calendar;

public class DateUtility {
	
	public final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

	
	public Date addDays(Date start, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.DAY_OF_YEAR,days);
		return new Date(cal.getTimeInMillis());
	}
	
	public Date getDateFromString(String s){
		String[] tokens1 = s.split("-");
		return Date.valueOf((tokens1[2]+"-"+tokens1[0]+"-"+tokens1[1]));
	}

	public String getStringFromDate(Date d){
		String[] tokens1 = d.toString().split("-");
		return (tokens1[1]+"-"+tokens1[2]+"-"+tokens1[0]);
	}
	
	public boolean isNotIntersecting(Date start1, Date end1, Date start2, Date end2){
		return ((end1.before(start2) || (end2.before(start1))));
	}
	
	public Date getIntersectingPeriodStart(Date start1, Date start2){
		if(start1.before(start2)){
			return start2;
		}else{
			return start1;
		}
	}
	
	public Date getIntersectingPeriodEnd(Date end1, Date end2){
		if(end1.before(end2)){
			return end1;
		}else{
			return end2;
		}
	}
	
	public int DifferenceInDays(Date from, Date to) {
	    return (int)((to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY);
	}

	
}
