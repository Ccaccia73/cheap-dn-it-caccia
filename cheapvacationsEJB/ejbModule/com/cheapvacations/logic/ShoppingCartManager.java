package com.cheapvacations.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cheapvacations.entities.VacationPackage;

/**
 * Session Bean implementation class ShoppingCartManager
 */
@Stateful(mappedName = "shoppingcartmanagerJNDI")
public class ShoppingCartManager implements ShoppingCartManagerLocal {

	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;

	private HashMap<Long, VacationPackage> mypackages;
	
    /**
     * Default constructor. 
     */
    public ShoppingCartManager() {
        mypackages = new HashMap<Long, VacationPackage>();
    }

	public boolean addPackageToCart(long idPackage) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean discardPackage(long idPackage) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean buyPackage(long idPackage) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	public boolean getPackage(long idPackage) {
		// TODO Auto-generated method stub
		return false;
	}
	*/
}
