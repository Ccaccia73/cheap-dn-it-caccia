package loginServlet;


// import java.io.PrintWriter;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cheapvacations.logic.LoginManagerRemote;
import com.cheapvacations.logic.OperatorManagerRemote;
import com.cheapvacations.logic.TravelAgencyManagerRemote;
import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.LogicException;
import com.cheapvacations.utility.*;



/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 200L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = null;
		
		try{
			
			Context ctx = getInitialContext();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			
			LoginManagerRemote lmRemote = (LoginManagerRemote)ctx.lookup("loginmanagerJNDI");
			
			try{
				user = lmRemote.login(username, password);
			}catch (LogicException e) {
				response.sendRedirect("home.jsp?login=fail");
				return;
			}
			
			request.getSession().setAttribute("user", user);
			
			
			if(user.type == 1){
				
				OperatorManagerRemote opRemote = (OperatorManagerRemote) ctx.lookup("operatormanagerJNDI");
				
				opRemote.setOperator(user.Id);
				
				request.getSession().setAttribute("operatorREF", opRemote);
				response.sendRedirect("operator/operator.jsp");
				
			}else if(user.type == 2){
				TravelAgencyManagerRemote taRemote = (TravelAgencyManagerRemote) ctx.lookup("travelagencymanagerJNDI");
				
				taRemote.setTravelAgency(user.Id);
				
				request.getSession().setAttribute("agencyREF", taRemote);
				response.sendRedirect("agency/agency.jsp");
				
			}else if(user.type == 3){
				CustomerManagerRemote cRemote = (CustomerManagerRemote) ctx.lookup("customermanagerJNDI");
				
				cRemote.setCustomer(user.Id);
				
				request.getSession().setAttribute("customerREF", cRemote);
				response.sendRedirect("customer/customer.jsp");
			}
			
		}
		catch (Exception e) {
			response.sendRedirect("home.jsp?login=fail");
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet( request, response);
	}
	
	static public Context getInitialContext() throws Exception{
		
		Hashtable<String,String> env = new Hashtable<String,String>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");
	
		return new InitialContext(env);
		
	}


}
