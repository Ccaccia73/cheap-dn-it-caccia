package com.cheapvacations.tests;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cheapvacations.entities.*;
import com.cheapvacations.logic.*;
import com.cheapvacations.utility.*;


public class OperatorManagerTest {
	
	private static Context ctx;
	
	// operator data
	private static OperatorManagerRemote omRemote;
	
	// login data
	private static LoginManagerRemote lmRemote;
	private static UserData ud1;
	
	// Date utility
	private static DateUtility du;
	
	// Hotel and Flight Operations
	public static final int ADD = 1;
	public static final int QUERY = 2;
	public static final int UPDATE = 3;
	public static final int CANCEL = 4;
	public static final int FINDbyID = 5;
	public static final int FINDby2ID = 6;
	
	
	// structures to keep data
	private static List<Hotel> hl1;
	private static List<Flight> fl1;
	private static List<HotelOffer> hol1;
	private static List<FlightOffer> fol1;
	
	// Id of objects to be removed  at the end
	// test hotel and flight
	private static long h1id;
	private static long f1id;
	// test offers
	private static long ho1id;
	private  static long fo1id;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = getInitialContext();
		omRemote = (OperatorManagerRemote) ctx.lookup("operatormanagerJNDI");
		lmRemote = (LoginManagerRemote) ctx.lookup("loginmanagerJNDI");
		// insert new operator
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "a@b.it", "address", 1);
		omRemote.setOperator(ud1.Id);
		
		// Date utility
		du = new DateUtility();

		// Insert test hotel
		hl1 = omRemote.manageHotel("Sheraton", "London", "Piccadilly Circus", 4, 0, 0, ADD);
		h1id = hl1.get(0).getId();
		
		// Insert test flight
		fl1 = omRemote.manageFlight("Q73", "Quantas", "Milano", "Adelaide", 0, 0, ADD);
		f1id = fl1.get(0).getId();
		
		// Insert test hotel offer
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("07-20-2012"), du.getDateFromString("07-30-2012"), 100, ud1.Id, h1id, 0, ADD);
		ho1id = hol1.get(0).getId();
		
		// Insert test flight offer
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("07-20-2012"), du.getDateFromString("07-30-2012"), 100, ud1.Id, f1id, 0, ADD);
		fo1id = fol1.get(0).getId();
		

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		omRemote.manageHotelOffer(null, null, 0, 0, 0, ho1id, CANCEL);
		// cancel test flight offer
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo1id, CANCEL);
		// cancel test hotel
		omRemote.manageHotel(null, null, null, 0, 0, h1id, CANCEL);
		// cancel test flight
		omRemote.manageFlight(null, null, null, null, 0, f1id, CANCEL);
		// cancel test hotel offer
		// remove test user
		lmRemote.removeUser(ud1.Id, ud1.type);
	}

	
	@Test(expected=LogicException.class)
	public final void testUnsubscribe() throws LogicException {
		assertEquals(ud1.name,omRemote.getOperator().getUsername());
		omRemote.unsubscribe();
		lmRemote.login(ud1.name, "1234");
	}


	@Test
	public final void testUpdateOperator() throws LogicException {
		UserData ud2 = lmRemote.register("test_operator02", "1234", "op", "op", "a@b.com", null, 1);
		omRemote.setOperator(ud2.Id);
		omRemote.update("aa", "bb", "a@b.com", "via");
		assertEquals("aa",omRemote.getOperator().getFirstName());
		assertEquals("bb",omRemote.getOperator().getLastName());
		assertEquals("a@b.com",omRemote.getOperator().getEmail());
		assertEquals("via",omRemote.getOperator().getAddress());
		lmRemote.removeUser(ud2.Id, ud2.type);
	}
	
	// HOTEL TESTS
	@Test
	public final void testManageHotelADD() throws LogicException {
		hl1 = omRemote.manageHotel("Hilton", "Dubai", "street", 4, 0, 0, ADD);
		assertEquals("Hilton",hl1.get(0).getName());
		assertEquals("Dubai",hl1.get(0).getCity());
		assertEquals("street",hl1.get(0).getAddress());
		assertEquals(4,hl1.get(0).getStars());
		omRemote.manageHotel(null, null, null, 0, 0, hl1.get(0).getId(), CANCEL);
	}

	@Test(expected=LogicException.class)
	public final void testManageHotelADDSame() throws LogicException {
		hl1 = omRemote.manageHotel("Sheraton", "London", "street", 4, 0, 0, ADD);
	}
	
	@Test
	public final void testManageHotelQuery() throws LogicException {
		long tmpId1;
		long tmpId2;
		// insert second hotel
		hl1 = omRemote.manageHotel("Hilton", "Dubai", "street", 3, 0, 0, ADD);
		tmpId1 = hl1.get(0).getId();
		// insert third hotel
		hl1 = omRemote.manageHotel("Hilton", "London", "street", 2, 0, 0, ADD);
		tmpId2 = hl1.get(0).getId();
		
		// query by name
		hl1 = omRemote.manageHotel("Hilton", null, null, 0, 0, 0, QUERY);
		assertEquals(2,hl1.size());
		// query by city
		hl1 = omRemote.manageHotel(null, "London", null, 0, 0, 0, QUERY);
		assertEquals(2,hl1.size());
		// query by name & city
		hl1 = omRemote.manageHotel("Hilton", "London", null, 0, 0, 0, QUERY);
		assertEquals(1,hl1.size());
		
		omRemote.manageHotel(null, null, null, 0, 0, tmpId1, CANCEL);
		omRemote.manageHotel(null, null, null, 0, 0, tmpId2, CANCEL);
	}


	@Test
	public final void testManageHotelUpdate() throws LogicException {
		hl1 = omRemote.manageHotel("Sheraton", "London", "Trafalgar", 2, 0, 0, UPDATE);
		assertEquals("Sheraton",hl1.get(0).getName());
		assertEquals("London",hl1.get(0).getCity());
		assertEquals("Trafalgar",hl1.get(0).getAddress());
		assertEquals(2,hl1.get(0).getStars());
		hl1 = omRemote.manageHotel("Sheraton", "London", "Piccadilly Circus", 4, 0, 0, UPDATE);
		assertEquals("Piccadilly Circus",hl1.get(0).getAddress());
		assertEquals(4,hl1.get(0).getStars());
	}
	

	@Test
	public final void testManageHotelFind() throws LogicException {
		
		hl1 = omRemote.manageHotel(null, null, null, 0, 0, h1id, FINDbyID);
		assertEquals("Sheraton",hl1.get(0).getName());
		assertEquals("London",hl1.get(0).getCity());
		assertEquals("Piccadilly Circus",hl1.get(0).getAddress());
		assertEquals(4,hl1.get(0).getStars());
	}
	
	// FLIGHT TESTS
	@Test
	public final void testManageFlightADD() throws LogicException {
		fl1 = omRemote.manageFlight("U01","United","New York","London",0,0,ADD);
		assertEquals("U01",fl1.get(0).getCode());
		assertEquals("United",fl1.get(0).getAirline());
		assertEquals("New York",fl1.get(0).getDeparture());
		assertEquals("London",fl1.get(0).getArrival());
		omRemote.manageFlight(null, null, null, null, 0, fl1.get(0).getId(), CANCEL);
	}

	@Test(expected=LogicException.class)
	public final void testManageFlightADDSame() throws LogicException {
		fl1 = omRemote.manageFlight("Q73", "Quantas", "Milano", "Adelaide", 0, 0, ADD);
	}
	
	@Test
	public final void testManageFlightQuery() throws LogicException {
		long tmpId1;
		long tmpId2;
		// insert second hotel
		fl1 = omRemote.manageFlight("AZ33", "Alitalia", "Roma", "Los Angeles", 0, 0, ADD);
		tmpId1 = fl1.get(0).getId();
		// insert third hotel
		fl1 = omRemote.manageFlight("Q83", "Quantas", "Adelaide", "Los Angeles", 0, 0, ADD);
		tmpId2 = fl1.get(0).getId();
		
		// query by airline
		fl1 = omRemote.manageFlight(null, "Quantas", null, null, 0, 0, QUERY);
		assertEquals(2,fl1.size());
		// query by arrival
		fl1 = omRemote.manageFlight(null, null, null, "Los Angeles", 0, 0, QUERY );
		assertEquals(2,fl1.size());
		// query by airline & departure
		fl1 = omRemote.manageFlight(null, "Quantas", "Adelaide", null, 0, 0, QUERY);
		assertEquals(1,fl1.size());
		
		omRemote.manageFlight(null, null, null, null, 0, tmpId1, CANCEL);
		omRemote.manageFlight(null, null, null, null, 0, tmpId2, CANCEL);
	}


	@Test
	public final void testManageFlightUpdate() throws LogicException {
		fl1 = omRemote.manageFlight("Q93", "Quantas", "Milano", "Adelaide", 0, 0, UPDATE);
		assertEquals("Q93",fl1.get(0).getCode());
		assertEquals("Quantas",fl1.get(0).getAirline());
		assertEquals("Milano",fl1.get(0).getDeparture());
		assertEquals("Adelaide",fl1.get(0).getArrival());
		fl1 = omRemote.manageFlight("Q73", "Quantas", "Milano", "Adelaide", 0, 0, UPDATE);
		assertEquals("Q73",fl1.get(0).getCode());
	}
	

	@Test
	public final void testManageFlightFind() throws LogicException {
		
		fl1 = omRemote.manageFlight(null, null, null, null, 0, f1id, FINDbyID);
		assertEquals("Q73",fl1.get(0).getCode());
		assertEquals("Quantas",fl1.get(0).getAirline());
		assertEquals("Milano",fl1.get(0).getDeparture());
		assertEquals("Adelaide",fl1.get(0).getArrival());
	}
	
	// HOTEL OFFER TESTS
	@Test
	public final void testManageHotelOfferADD() throws LogicException {
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("09-01-2012"), du.getDateFromString("09-20-2012"), 50, ud1.Id, h1id, 0, ADD);
		assertEquals("09-01-2012",du.getStringFromDate(hol1.get(0).getStartDate()));
		assertEquals("09-20-2012",du.getStringFromDate(hol1.get(0).getEndDate()));
		assertEquals(50,hol1.get(0).getPrice());
		assertEquals("Sheraton",hol1.get(0).getReferenceHotel().getName());
		omRemote.manageHotelOffer(null, null, 0, 0, 0, hol1.get(0).getId(), CANCEL);
	}
	
	@Test(expected=LogicException.class)
	public final void testManageHotelOfferAddIntersect() throws LogicException{
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("07-15-2012"), du.getDateFromString("07-25-2012"), 50, ud1.Id, h1id, 0, ADD);
	}

	@Test(expected=LogicException.class)
	public final void testManageHotelOfferHotelDelete() throws LogicException{
		// no hotel cancel with offers present
		omRemote.manageHotel(null, null, null, 0, 0, h1id, CANCEL);
	}
	
	@Test
	public final void testManageHotelOfferQuery() throws LogicException {
		long tmpId1;
		long tmpId2;
		// insert second hotel offer
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("01-01-2013"),du.getDateFromString( "01-31-2013"), 60, ud1.Id, h1id, 0, ADD);
		tmpId1 = hol1.get(0).getId();
		// insert third hotel offer
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("02-15-2013"),du.getDateFromString( "02-28-2013"), 40, ud1.Id, h1id, 0, ADD);
		tmpId2 = hol1.get(0).getId();
		
		// Query by start date
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("01-02-2013"),null, 0, 0, 0, 0, QUERY);
		assertEquals(1,hol1.size());
		// Query by end date
		hol1 = omRemote.manageHotelOffer(null, du.getDateFromString("02-27-2013"), 0, 0, 0, 0, QUERY);
		assertEquals(1,hol1.size());
		
		omRemote.manageHotelOffer(null, null, 0, 0, 0, tmpId1, CANCEL);
		omRemote.manageHotelOffer(null, null, 0, 0, 0, tmpId2, CANCEL);
	}
	
	@Test
	public final void testManageHotelOfferFind() throws LogicException {
		hol1 = omRemote.manageHotelOffer(null, null, 0, 0, 0, ho1id, FINDbyID);
		assertEquals("Sheraton",hol1.get(0).getReferenceHotel().getName());
		assertEquals("London",hol1.get(0).getReferenceHotel().getCity());
	}
	
	@Test
	public final void testManageHotelOfferFind2() throws LogicException {
		hol1 = omRemote.manageHotelOffer(null, null, 0, ud1.Id, h1id, 0, FINDby2ID);
		assertEquals("Sheraton",hol1.get(0).getReferenceHotel().getName());
		assertEquals("London",hol1.get(0).getReferenceHotel().getCity());
		assertEquals(ud1.Id,hol1.get(0).getReferenceHotelOperator().getId());
	}

	
	// FLIGHT OFFER TESTS 
	@Test
	public final void testManageFlightOfferADD() throws LogicException{
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("09-01-2012"), du.getDateFromString("09-20-2012"), 75, ud1.Id, f1id, 0, ADD);
		assertEquals("09-01-2012",du.getStringFromDate(fol1.get(0).getStartDate()));
		assertEquals("09-20-2012",du.getStringFromDate(fol1.get(0).getEndDate()));
		assertEquals(75,fol1.get(0).getPrice());
		assertEquals("Quantas",fol1.get(0).getReferenceFlight().getAirline());
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fol1.get(0).getId(), CANCEL);
	}
	
	@Test(expected=LogicException.class)
	public final void testManageFlightOfferAddIntersect() throws LogicException{
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("07-25-2012"), du.getDateFromString("08-05-2012"), 50, ud1.Id, f1id, 0, ADD);
	}
	
	@Test(expected=LogicException.class)
	public final void testManageFlightOfferFlightDelete() throws LogicException{
		omRemote.manageFlight(null, null, null, null, 0, f1id, CANCEL);
	}
	
	@Test
	public final void testManageFlightOfferQuery() throws LogicException {
		long tmpId1;
		long tmpId2;
		// insert second flight offer
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("01-01-2013"),du.getDateFromString( "01-31-2013"), 60, ud1.Id, f1id, 0, ADD);
		tmpId1 = fol1.get(0).getId();
		// insert third flight offer
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("02-15-2013"),du.getDateFromString( "02-28-2013"), 40, ud1.Id, f1id, 0, ADD);
		tmpId2 = fol1.get(0).getId();
		
		// Query by start date
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("01-02-2013"),null, 0, 0, 0, 0, QUERY);
		assertEquals(1,fol1.size());
		// Query by end date
		fol1 = omRemote.manageFlightOffer(null, du.getDateFromString("03-01-2013"), 0, 0, 0, 0, QUERY);
		assertEquals(1,fol1.size());
		
		omRemote.manageFlightOffer(null, null, 0, 0, 0, tmpId1, CANCEL);
		omRemote.manageFlightOffer(null, null, 0, 0, 0, tmpId2, CANCEL);
	}
	
	@Test
	public final void testManageFlightOfferFind() throws LogicException {
		fol1 = omRemote.manageFlightOffer(null, null, 0, 0, 0, fo1id, FINDbyID);
		assertEquals("Quantas",fol1.get(0).getReferenceFlight().getAirline());
		assertEquals("Adelaide",fol1.get(0).getReferenceFlight().getArrival());
	}
	
	@Test
	public final void testManageFlightOfferFind2() throws LogicException {
		fol1 = omRemote.manageFlightOffer(null, null, 0, ud1.Id, f1id, 0, FINDby2ID);
		assertEquals("Quantas",fol1.get(0).getReferenceFlight().getAirline());
		assertEquals("Adelaide",fol1.get(0).getReferenceFlight().getArrival());
		assertEquals(ud1.Id,fol1.get(0).getReferenceFlightOperator().getId());
	}
	
	
	static public Context getInitialContext() throws Exception{
		
		Hashtable<String,String> env = new Hashtable<String,String>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");
	
		return new InitialContext(env);
		
	}	
}
