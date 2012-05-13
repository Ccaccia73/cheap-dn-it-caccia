package loginServlet;

import java.io.IOException;
// import java.io.PrintWriter;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
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
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 201L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// PrintWriter out = response.getWriter();
		
		UserData user = new UserData();
		
		try{
			
			Context ctx = getInitialContext();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			int type = Integer.parseInt(request.getParameter("category"));
			
			
			LoginManagerRemote lmRemote = (LoginManagerRemote)ctx.lookup("loginmanagerJNDI");
			
			try{
				user = lmRemote.register(username, password, firstname, lastname, email, address, type);
			}catch (LogicException e) {
				response.sendRedirect("registration.jsp?register=fail");
				return;
			}
			
			request.getSession().setAttribute("user", user);
			
			if(type == 1){
				OperatorManagerRemote opRemote = (OperatorManagerRemote) ctx.lookup("operatormanagerJNDI");
				
				opRemote.setOperator(user.Id);

				request.getSession().setAttribute("operatorREF", opRemote);
				response.sendRedirect("operator/operator.jsp");
			}else if(type == 2){
				TravelAgencyManagerRemote taRemote = (TravelAgencyManagerRemote) ctx.lookup("travelagencymanagerJNDI");
				
				taRemote.setTravelAgency(user.Id);

				request.getSession().setAttribute("agencyREF", taRemote);
				response.sendRedirect("agency/agency.jsp");
			}else if(type == 3){
				CustomerManagerRemote cRemote = (CustomerManagerRemote) ctx.lookup("customermanagerJNDI");
				
				cRemote.setCustomer(user.Id);
				
				request.getSession().setAttribute("customerREF", cRemote);
				response.sendRedirect("customer/customer.jsp");
			}
			
		}catch (Exception e) {
			response.sendRedirect("registration.jsp?register=fail");
			return;
		}

	}

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
