package com.cheapvacations.logic;

import javax.ejb.Remote;
import com.cheapvacations.entities.*;
import java.util.*;
import com.cheapvacations.utility.*;

@Remote
public interface LoginManagerRemote {
	public UserData login(String username, String password) throws LogicException;
	public UserData register(String username, String password, String firstname, String lastname, String email, String address, int type )  throws LogicException;

	public List<VacationPackage> guestSession();
	
	public String getPassword(String username) throws LogicException;
	
	// user remove: used for testing
	public void removeUser(long Id, int type);
	
}
