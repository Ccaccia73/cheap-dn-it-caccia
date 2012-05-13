package com.cheapvacations.logic;

import com.cheapvacations.utility.*;
import com.cheapvacations.entities.*;
import javax.ejb.*;
import javax.persistence.*;
import java.util.*;

import org.jboss.ejb3.annotation.RemoteBinding;

/**
 * Session Bean implementation class LoginManager
 */
@Stateless
@RemoteBinding(jndiBinding = "loginmanagerJNDI")
public class LoginManager implements LoginManagerRemote {
	
	@PersistenceContext(unitName="dataCheapvacations")
	private EntityManager manager;
		
    /**
     * Default constructor. 
     */
    public LoginManager() {

    }

	public UserData login(String username, String password) throws LogicException {
		
		UserData user = new UserData();
		
		// Query q = manager.createQuery( "select c from CUSTOMER c where c.username = :u and c.password = :p" );
		Query q = manager.createNamedQuery("Customer.getCustomerByUserAndPassword");
		q.setParameter( "name", username );
		q.setParameter("pw", password);
		
		@SuppressWarnings("unchecked")
		List<Customer> lc = (List<Customer>)q.getResultList();
		
		if(lc.size() > 0){
			if(lc.size() > 1){
				throw new LogicException("More than one Customer with same credentials");
			}else{
				if(lc.get(0).getStatus() == 1){
					user.Id = lc.get(0).getId();
					user.name = lc.get(0).getUsername();
					user.type = 3;
					return user;
				}else{
					throw new LogicException("User unsubscribed");
				}
			}
		}else{
			q = manager.createNamedQuery("TravelAgency.getTravelAgencyByUserAndPassword");
			q.setParameter( "name", username );
			q.setParameter("pw", password);
			
			@SuppressWarnings("unchecked")
			List<TravelAgency> lta = (List<TravelAgency>)q.getResultList();
			
			if(lta.size() > 0){
				if(lta.size() > 1){
					throw new LogicException("More than one Travel Agency with same credentials");
				}else{
					if(lta.get(0).getStatus() == 1){
						user.Id = lta.get(0).getId();
						user.name = lta.get(0).getUsername();
						user.type = 2;
						return user;
					}else{
						throw new LogicException("User unsubscribed");
					}
				}
			}else{
				q = manager.createNamedQuery("Operator.getOperatorByUserAndPassword");
				q.setParameter( "name", username );
				q.setParameter("pw", password);
				
				@SuppressWarnings("unchecked")
				List<Operator> lo = (List<Operator>)q.getResultList();
				
				if(lo.size() > 0){
					if(lo.size() > 1){
						throw new  LogicException("More than one Operator with same credentials");
					}else{
						if(lo.get(0).getStatus() == 1 ){
							user.Id = lo.get(0).getId();
							user.name = lo.get(0).getUsername();
							user.type = 1;
							
							return user;
						}else{
							throw new LogicException("User unsubscribed");
						}
					}
				}else{
					throw new LogicException("No User found");
				}
			}
		}
	}

	public UserData register(String username, String password, String firstname, String lastname, String email,String address, int type) throws LogicException {
		
		if(username.length() == 0 || password.length() == 0 || firstname.length()== 0 || lastname.length() == 0){
			// System.out.println("Username o password nulli");
			throw new LogicException("You must define username and password");
		}
		
		// System.out.println("Dopo Username o password");
		
		if(type < 1 || type > 3){
			// System.out.println("Usertype sbagliato");
			throw new LogicException("Wrong User Type");
		}
		
		
		UserData user = new UserData();
		
		Query q1 = manager.createNamedQuery("Customer.getCustomerByUser");
		q1.setParameter( "name", username );
		
		Query q2 = manager.createNamedQuery("TravelAgency.getTravelAgencyByUser");
		q2.setParameter( "name", username );
		
		Query q3 = manager.createNamedQuery("Operator.getOperatorByUser" );
		q3.setParameter( "name", username );
		
		if(q1.getResultList().size() + q2.getResultList().size() + q3.getResultList().size() > 0) {
			throw new LogicException("Username already in use");
		}

		switch (type) {
		case 1:

			Operator o = new Operator();
			o.setUsername(username);
			o.setPassword(password);
			o.setFirstName(firstname);
			o.setLastName(lastname);
			o.setEmail(email);
			o.setAddress(address);
			o.setStatus(1);

			manager.persist(o);
			
			user.Id = o.getId();
			user.name = o.getUsername();
			user.type = type;
			
			return user;

		case 2:

			TravelAgency ta = new TravelAgency();
			ta.setUsername(username);
			ta.setPassword(password);
			ta.setFirstName(firstname);
			ta.setLastName(lastname);
			ta.setEmail(email);
			ta.setAddress(address);
			ta.setStatus(1);

			manager.persist(ta);

			user.Id = ta.getId();
			user.name = ta.getUsername();
			user.type = type;
			
			return user;

		case 3:

			Customer c = new Customer();
			c.setUsername(username);
			c.setPassword(password);
			c.setFirstName(firstname);
			c.setLastName(lastname);
			c.setEmail(email);
			c.setAddress(address);
			c.setStatus(1);

			manager.persist(c);

			user.Id = c.getId();
			user.name = c.getUsername();
			user.type = type;
			
			return user;

		default:
			throw new LogicException("Wrong user type");
		}
	}
	/*
	public boolean update(long id, String username, String password, String firstname, String lastname, String email, String address, int usertype) {
		// TODO Auto-generated method stub
		return false;
	}

	public void performActivity(long id, int usertype) {
		// TODO Auto-generated method stub
		
	}
	*/

	public List<VacationPackage> guestSession() {
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(sqlToday);
		cal.add(Calendar.DAY_OF_YEAR,7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlMaxDay = new java.sql.Date(cal.getTimeInMillis());
		
		Query q1 = manager.createNamedQuery("VacationPackage.getPackagesForGuestSession");
		q1.setParameter( "date", sqlMaxDay );
		
		@SuppressWarnings("unchecked")
		List<VacationPackage> pack = (List<VacationPackage>)q1.getResultList();
		
		return pack;

	}
	
	
	public String getPassword(String username) throws LogicException {
		Query q1 = manager.createNamedQuery("Customer.getCustomerByUser");
		q1.setParameter( "name", username );
		
		if(q1.getResultList().size() > 0){
			if(((Customer)q1.getResultList().get(0)).getStatus() != 0){
				return ((Customer)q1.getResultList().get(0)).getPassword();
			}
		}
		
		Query q2 = manager.createNamedQuery("TravelAgency.getTravelAgencyByUser");
		q2.setParameter( "name", username );
		
		if(q2.getResultList().size() > 0){
			if(((TravelAgency)q2.getResultList().get(0)).getStatus() != 0){
				return ((TravelAgency)q2.getResultList().get(0)).getPassword();
			}
		}
		
		Query q3 = manager.createNamedQuery("Operator.getOperatorByUser" );
		q3.setParameter( "name", username );
		
		if(q3.getResultList().size() > 0){
			if(((Operator)q3.getResultList().get(0)).getStatus() != 0){
				return ((Operator)q3.getResultList().get(0)).getPassword();
			}
		}
		
		throw new LogicException("Username not found");
	}

	public void removeUser(long Id, int type) {
		
		switch (type) {
		case 1:
			Operator op = manager.find(Operator.class,Id);
			manager.remove(op);
			break;
		case 2:
			TravelAgency ta = manager.find(TravelAgency.class,Id);
			manager.remove(ta);
			break;
		case 3:
			Customer cust = manager.find(Customer.class,Id);
			manager.remove(cust);
			break;

		default:
			break;
		}
	}
	
}
