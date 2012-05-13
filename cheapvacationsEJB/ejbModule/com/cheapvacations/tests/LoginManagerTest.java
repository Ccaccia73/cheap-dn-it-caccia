package com.cheapvacations.tests;

import com.cheapvacations.logic.*;
import com.cheapvacations.utility.*;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class LoginManagerTest {
	
	private static Context ctx;
	
	private static LoginManagerRemote lmRemote;
	
	private static UserData ud1;
	private static UserData ud2;
	
	public LoginManagerTest() throws Exception{
		ctx = getInitialContext();
		lmRemote = (LoginManagerRemote) ctx.lookup("loginmanagerJNDI");
	}
	
	@BeforeClass
	public static void setupMethod(){
		ud1 = new UserData();
		ud2 = new UserData();
	}
	
	@Test
	public void UserRegister() throws Exception {
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "a@b.it", "address", 1);

		// Operator
		assertEquals("operator_test01",ud1.name);
		assertEquals(1,ud1.type);
		
	}
	
	@Test(expected=LogicException.class)
	public void DoubleRegister() throws LogicException{
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "a@b.it", "address", 1);
		assertEquals("operator_test01",ud1.name);
		assertEquals(1,ud1.type);
		ud2 = lmRemote.register("operator_test01", "5678", "SecondOperator", "test", "a@b.it", "address", 1);
	}
	
	@Test(expected=LogicException.class)
	public void DoubleRegisterDifferentUserType() throws LogicException{
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "a@b.it", "address", 1);
		assertEquals("operator_test01",ud1.name);
		assertEquals(1,ud1.type);
		ud2 = lmRemote.register("operator_test01", "5678", "SecondOperator", "test", "a@b.it", "address", 2);
	}
	
	@Test
	public void recoverPassword() throws LogicException {
		ud1 = lmRemote.register("operator_test01", "1234", "Operator", "test", "a@b.it", "address", 1);
		assertEquals("1234",lmRemote.getPassword("operator_test01"));
	}
	
	
	@After
	public void tearDown(){
		lmRemote.removeUser(ud1.Id, ud1.type);
	}
	
	static public Context getInitialContext() throws Exception{
		
		Hashtable<String,String> env = new Hashtable<String,String>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");
	
		return new InitialContext(env);
		
	}
}
