package com.cheapvacations.tests;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cheapvacations.entities.Flight;
import com.cheapvacations.entities.FlightOffer;
import com.cheapvacations.entities.Hotel;
import com.cheapvacations.entities.HotelOffer;
import com.cheapvacations.entities.VacationPackage;
import com.cheapvacations.logic.*;
import com.cheapvacations.utility.*;

public class CustomerManagerTest {
	
	public static final int ADD = 1;
	public static final int CANCEL = 4;
	
	public static final int PACK_ADD = 1;
	public static final int PACK_DELETE = 5;
	
	public static final int ADD_TO_CART = 1;
	public static final int BUY = 2;
	public static final int REMOVE_FROM_CART = 3;
	public static final int REMOVE_FROM_ORDERS = 4;
	
	
	private static Context ctx;
	
	// operator
	private static OperatorManagerRemote omRemote;
	// travel agency
	private static TravelAgencyManagerRemote taRemote;
	// customer 
	private static CustomerManagerRemote custRemote;
	
	// login data
	private static LoginManagerRemote lmRemote;
	private static UserData ud1;
	private static UserData ud2;
	private static UserData ud3;
	
	// Date utility
	private static DateUtility du;
	// Package Data
	private static PackageData pack;

	
	// structures to keep data
	private static List<Hotel> hl1;
	private static List<Flight> fl1;
	private static List<HotelOffer> hol1;
	private static List<FlightOffer> fol1;
	private static List<VacationPackage> vpl1;
	
	// Id of objects to be removed  at the end
	// test hotel and flight
	private static long h1id;
	private static long f1id;
	private static long f2id;
	// test offers
	private static long ho1id;
	private  static long fo1id;
	private  static long fo2id;
	
	// test package
	private static long vp1id;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = getInitialContext();
		omRemote = (OperatorManagerRemote) ctx.lookup("operatormanagerJNDI");
		taRemote = (TravelAgencyManagerRemote) ctx.lookup("travelagencymanagerJNDI");
		custRemote = (CustomerManagerRemote) ctx.lookup("customermanagerJNDI");
		lmRemote = (LoginManagerRemote) ctx.lookup("loginmanagerJNDI");
		// insert new operator
		ud2 = lmRemote.register("agency_test01", "1234", "Agency", "Agency", "ta@ta.it", "address", 2);
		ud3 = lmRemote.register("customer_test01", "1234", "Customer", "Test", "a@b.c", "address", 3);
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "op@op.it", "address", 1);
		
		taRemote.setTravelAgency(ud2.Id);
		custRemote.setCustomer(ud3.Id);
		omRemote.setOperator(ud1.Id);
		
		
		// Date utility
		du = new DateUtility();
		// Package Data
		pack = new PackageData();

		// Insert test hotel #1
		hl1 = omRemote.manageHotel("Sheraton", "Boston", "BackBay", 4, 0, 0, ADD);
		h1id = hl1.get(0).getId();
			
		// Insert test flight #1
		fl1 = omRemote.manageFlight("UA01", "United Airlines", "Milano", "Boston", 0, 0, ADD);
		f1id = fl1.get(0).getId();
		// Insert test flight #2
		fl1 = omRemote.manageFlight("UA02", "United Airlines", "Boston", "Milano", 0, 0, ADD);
		f2id = fl1.get(0).getId();
		
		// Insert test hotel offer #1
		hol1 = omRemote.manageHotelOffer(du.getDateFromString("06-05-2013"), du.getDateFromString("06-25-2013"), 100, ud1.Id, h1id, 0, ADD);
		ho1id = hol1.get(0).getId();
		
		// Insert test flight offer #1
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("06-01-2013"), du.getDateFromString("06-10-2013"), 100, ud1.Id, f1id, 0, ADD);
		fo1id = fol1.get(0).getId();
		// Insert test flight offer #2
		fol1 = omRemote.manageFlightOffer(du.getDateFromString("06-20-2013"), du.getDateFromString("06-30-2013"), 100, ud1.Id, f2id, 0, ADD);
		fo2id = fol1.get(0).getId();
		
		// insert correct package
		pack.idAgency = ud2.Id;
		pack.idHotel = ho1id;
		pack.idDeparture = fo1id;
		pack.idReturn = fo2id;
		pack.startDate = du.getDateFromString("06-08-2013");
		pack.endDate = du.getDateFromString("06-28-2013");
		pack.price = 1200;
		pack.markup = (float)0.08;
		
		taRemote.manageVacationPackage(pack, -1, PACK_ADD);
		vpl1 = taRemote.getPackages();
		vp1id = vpl1.get(0).getId();
		
	}
	
	@Test(expected=LogicException.class)
	public final void testUnsubscribe() throws LogicException {
		assertEquals(ud1.name,custRemote.getCustomer().getUsername());
		custRemote.unsubscribe();
		lmRemote.login(ud2.name, "1234");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// cancel test package
		PackageData pack1 = new PackageData();
		pack1.Id = vp1id;
		taRemote.manageVacationPackage(pack1, -1, PACK_DELETE);
		// cancel test hotel offers
		omRemote.manageHotelOffer(null, null, 0, 0, 0, ho1id, CANCEL);
		// cancel test flight offer
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo1id, CANCEL);
		omRemote.manageFlightOffer(null, null, 0, 0, 0, fo2id, CANCEL);
		// cancel test hotel
		omRemote.manageHotel(null, null, null, 0, 0, h1id, CANCEL);
		// cancel test flight
		omRemote.manageFlight(null, null, null, null, 0, f1id, CANCEL);
		omRemote.manageFlight(null, null, null, null, 0, f2id, CANCEL);
		// remove test user
		lmRemote.removeUser(ud1.Id, ud1.type);
		lmRemote.removeUser(ud2.Id, ud2.type);
		lmRemote.removeUser(ud3.Id, ud3.type);

	}
	
	@Test
	public void addPackageAndRemove() throws LogicException {
		custRemote.useCart(pack, ADD_TO_CART);
		assertEquals(1,custRemote.getBookedPackages().size());
		assertEquals(1,custRemote.getBookedPackages().get(0).getStatus());
		assertEquals(ud3.Id,custRemote.getBookedPackages().get(0).getBuyer().getId());
		
		custRemote.useCart(pack, REMOVE_FROM_CART);
		assertEquals(0,custRemote.getBookedPackages().size());
	}

	@Test
	public void addPackageBuyAndRemove() throws LogicException {
		custRemote.useCart(pack, ADD_TO_CART);
		assertEquals(1,custRemote.getBookedPackages().size());
		assertEquals(1,custRemote.getBookedPackages().get(0).getStatus());
		assertEquals(ud3.Id,custRemote.getBookedPackages().get(0).getBuyer().getId());
		
		custRemote.useCart(pack, BUY);
		assertEquals(0,custRemote.getBookedPackages().size());
		assertEquals(1,custRemote.getOrderedPackages().size());
		assertEquals(ud3.Id,custRemote.getOrderedPackages().get(0).getBuyer().getId());
		assertEquals(0,custRemote.getBookedPackages().get(0).getStatus());
		
		custRemote.useCart(pack, REMOVE_FROM_ORDERS);
		assertEquals(0,custRemote.getBookedPackages().size());
		assertEquals(0,custRemote.getOrderedPackages().size());
	}
	
	static public Context getInitialContext() throws Exception{
		
		Hashtable<String,String> env = new Hashtable<String,String>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");
	
		return new InitialContext(env);
		
	}	
}
