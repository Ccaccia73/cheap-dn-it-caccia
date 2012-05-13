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

public class TravelAgencyManagerTest {

	
	private static Context ctx;
	
	// operator data
	private static OperatorManagerRemote omRemote;
	// travel agency
	private static TravelAgencyManagerRemote taRemote;
	
	// login data
	private static LoginManagerRemote lmRemote;
	private static UserData ud1;
	private static UserData ud2;
	
	// Date utility
	private static DateUtility du;
	// Package Data
	private static PackageData pack;
	
	// Hotel and Flight Operations
	public static final int ADD = 1;
	public static final int QUERY = 2;
	public static final int UPDATE = 3;
	public static final int CANCEL = 4;
	public static final int FINDbyID = 5;
	public static final int FINDby2ID = 6;
	
	public static final int PACK_ADD = 1;
	public static final int PACK_FINDbyHOid = 2;
	public static final int PACK_FINDbyFOid = 3;
	public static final int PACK_QUERY = 4;
	public static final int PACK_DELETE = 5;
	
	// structures to keep data
	private static List<Hotel> hl1;
	private static List<Flight> fl1;
	private static List<HotelOffer> hol1;
	private static List<FlightOffer> fol1;
	private static List<VacationPackage> vpl1;
	
	// Id of objects to be removed  at the end
	// test hotel and flight
	private static long h1id;
	private static long h2id;
	private static long f1id;
	private static long f2id;
	private static long f3id;
	// test offers
	private static long ho1id;
	private static long ho2id;
	private static long ho3id;
	
	private  static long fo1id;
	private  static long fo2id;
	private  static long fo3id;
	private  static long fo4id;
	
	// test package
	private static long vp1id;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = getInitialContext();
		omRemote = (OperatorManagerRemote) ctx.lookup("operatormanagerJNDI");
		taRemote = (TravelAgencyManagerRemote) ctx.lookup("travelagencymanagerJNDI");
		lmRemote = (LoginManagerRemote) ctx.lookup("loginmanagerJNDI");
		// insert new operator
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "op@op.it", "address", 1);
		ud2 = lmRemote.register("agency_test01", "1234", "Agency", "Agency", "ta@ta.it", "address", 2);
		omRemote.setOperator(ud1.Id);
		taRemote.setTravelAgency(ud2.Id);
		
		// Date utility
		du = new DateUtility();
		// Package Data
		pack = new PackageData();

		// Insert test hotel #1
		hl1 = omRemote.manageHotel("Sheraton", "Los Angeles", "Beverly Hills", 4, 0, 0, ADD);
		h1id = hl1.get(0).getId();
		// Insert test hotel #2
		hl1 = omRemote.manageHotel("Sheraton", "Boston", "BackBay", 4, 0, 0, ADD);
		h2id = hl1.get(0).getId();
			
		// Insert test flight #1
		fl1 = omRemote.manageFlight("UA01", "United Airlines", "Milano", "Boston", 0, 0, ADD);
		f1id = fl1.get(0).getId();
		// Insert test flight #2
		fl1 = omRemote.manageFlight("UA02", "United Airlines", "Roma", "Boston", 0, 0, ADD);
		f2id = fl1.get(0).getId();
		// Insert test flight #3
		fl1 = omRemote.manageFlight("UA02", "United Airlines", "Boston", "Milano", 0, 0, ADD);
		f3id = fl1.get(0).getId();
		
		// Insert test hotel offer #1
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("06-05-2013"), du.getDateFromString("06-25-2013"), 100, ud1.Id, h1id, 0, ADD);
		ho1id = hol1.get(0).getId();
		// Insert test hotel offer #2
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("06-05-2013"), du.getDateFromString("06-25-2013"), 100, ud1.Id, h2id, 0, ADD);
		ho2id = hol1.get(0).getId();
		// Insert test hotel offer #3
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("07-15-2013"), du.getDateFromString("07-25-2013"), 100, ud1.Id, h1id, 0, ADD);
		ho3id = hol1.get(0).getId();
		
		// Insert test flight offer #1
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("06-01-2013"), du.getDateFromString("06-10-2013"), 100, ud1.Id, f1id, 0, ADD);
		fo1id = fol1.get(0).getId();
		// Insert test flight offer #2
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("06-01-2013"), du.getDateFromString("06-10-2013"), 100, ud1.Id, f2id, 0, ADD);
		fo2id = fol1.get(0).getId();
		// Insert test flight offer #3
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("06-20-2013"), du.getDateFromString("06-30-2013"), 100, ud1.Id, f3id, 0, ADD);
		fo3id = fol1.get(0).getId();
		// Insert test flight offer #4
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("07-03-2013"), du.getDateFromString("07-30-2013"), 100, ud1.Id, f3id, 0, ADD);
		fo4id = fol1.get(0).getId();
		
		// insert correct package
		pack.idAgency = ud2.Id;
		pack.idHotel = ho1id;
		pack.idDeparture = fo1id;
		pack.idReturn = fo3id;
		pack.startDate = du.getDateFromString("06-08-2013");
		pack.endDate = du.getDateFromString("06-28-2013");
		pack.price = 1200;
		pack.markup = (float)0.08;
		
		taRemote.manageVacationPackage(pack, -1, PACK_ADD);
		vpl1 = taRemote.getPackages();
		vp1id = vpl1.get(0).getId();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// cancel test package
		PackageData pack1 = new PackageData();
		pack1.Id = vp1id;
		taRemote.manageVacationPackage(pack1, -1, PACK_DELETE);
		// cancel test hotel offers
		omRemote.manageHotelOffer(null, null, 0, 0, 0, ho1id, CANCEL);
		omRemote.manageHotelOffer(null, null, 0, 0, 0, ho2id, CANCEL);
		omRemote.manageHotelOffer(null, null, 0, 0, 0, ho3id, CANCEL);
		// cancel test flight offer
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo1id, CANCEL);
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo2id, CANCEL);
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo3id, CANCEL);
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo4id, CANCEL);
		// cancel test hotel
		omRemote.manageHotel(null, null, null, 0, 0, h1id, CANCEL);
		omRemote.manageHotel(null, null, null, 0, 0, h2id, CANCEL);
		// cancel test flight
		omRemote.manageFlight(null, null, null, null, 0, f1id, CANCEL);
		omRemote.manageFlight(null, null, null, null, 0, f2id, CANCEL);
		omRemote.manageFlight(null, null, null, null, 0, f3id, CANCEL);
		// remove test user
		lmRemote.removeUser(ud1.Id, ud1.type);
		// lmRemote.removeUser(ud2.Id, ud2.type);
	}
	
	@Test(expected=LogicException.class)
	public final void testUnsubscribe() throws LogicException {
		assertEquals(ud2.name,taRemote.getTravelAgency().getUsername());
		taRemote.unsubscribe();
		lmRemote.login(ud2.name, "1234");
	}

	@Test(expected=LogicException.class)
	public final void testManageVacationPackageAddSame() throws LogicException {
		// insert correct package
		pack.idAgency = ud2.Id;
		pack.idHotel = ho1id;
		pack.idDeparture = fo1id;
		pack.idReturn = fo3id;
		pack.startDate = du.getDateFromString("06-08-2013");
		pack.endDate = du.getDateFromString("06-28-2013");
		pack.price = 1200;
		pack.markup = (float)0.08;
		
		taRemote.manageVacationPackage(pack, -1, PACK_ADD);
	}
	
	@Test
	public final void testManageVacationPackageQuery() throws LogicException {
		PackageData pack2 = new PackageData();
		pack2.endDate = du.getDateFromString("07-03-2013");
		taRemote.manageVacationPackage(pack2, 7, PACK_QUERY);
		assertEquals(1,taRemote.getPackages().size());
	}
	/*
	@Test
	public final void testManageVacationPackageFindByHotelOfferId() throws LogicException {
		PackageData pack2 = new PackageData();
		pack2.idAgency = ud2.Id;
		pack2.idHotel = ho1id;
		taRemote.manageVacationPackage(pack2, 0, PACK_FINDbyHOid);
		assertEquals("Boston",taRemote.getPackages().get(0).getDepartFlight().getReferenceFlight().getArrival());
	}
	
	@Test
	public final void testManageVacationPackageFindByFlightOfferId() throws LogicException {
		PackageData pack2 = new PackageData();
		pack2.idAgency = ud2.Id;
		pack2.idDeparture = fo1id;
		taRemote.manageVacationPackage(pack2, 0, PACK_FINDbyFOid);
		assertEquals("Milano",taRemote.getPackages().get(0).getDepartFlight().getReferenceFlight().getDeparture());
	}
	*/
	
	static public Context getInitialContext() throws Exception{
		
		Hashtable<String,String> env = new Hashtable<String,String>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");
	
		return new InitialContext(env);
		
	}	
}
