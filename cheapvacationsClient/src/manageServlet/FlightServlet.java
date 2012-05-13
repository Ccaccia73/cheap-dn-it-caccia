package manageServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.entities.Flight;
import com.cheapvacations.entities.Hotel;
import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.LogicException;
import com.cheapvacations.logic.OperatorManagerRemote;
import com.cheapvacations.logic.TravelAgencyManagerRemote;
import com.cheapvacations.utility.UserData;

/**
 * Servlet implementation class FlightServlet
 */
public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		String code = request.getParameter("fcode");
		String airline = request.getParameter("fairline");
		String departure = request.getParameter("fdeparture");
		String arrival = request.getParameter("farrival");
				
		String rating_str = request.getParameter("frating");
		float rating;
		if(rating_str != null){
			rating = Float.parseFloat(rating_str);
		}else{
			rating = -1;
		}

		String id_str = request.getParameter("fid");
		Long Id;
		if(id_str != null){
			Id = Long.parseLong(id_str);
		}else{
			Id = -1L;
		}
		
		int operation = Integer.parseInt(request.getParameter("op"));
		
		
		switch (user.type) {
		case 1:
			OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
			try {
				List<Flight> f = op.manageFlight(code, airline, departure, arrival, rating, Id, operation);
				request.getSession().setAttribute("flight", f);
				switch (operation) {
				// INSERT
				case 1:
					response.sendRedirect("operator/flight/flight_new_res.jsp");	
					break;
				// QUERY
				case 2:
					response.sendRedirect("operator/flight/flight_search_res.jsp");	
					break;
				// UPDATE
				case 3:
					response.sendRedirect("operator/operator.jsp?flightupdate=ok");	
					break;
				// DELETE
				case 4:
					response.sendRedirect("operator/operator.jsp?flightremove=ok");	
					break;
				default:
					response.sendRedirect("operator/operator.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 1:
					response.sendRedirect("operator/flight/flight_new.jsp?insert=fail");
					break;
				case 2:
					response.sendRedirect("operator/flight/flight_search.jsp?query=fail");	
					break;
				case 3:
					response.sendRedirect("operator/operator.jsp?flightupdate=fail");	
					break;
				case 4:
					response.sendRedirect("operator/operator.jsp?flightremove=fail");	
					break;
				default:
					response.sendRedirect("operator/operator.jsp");
					break;
				}	
			}
			break;
		case 2:
			TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
			try {
				List<Flight> f = ta.manageFlight(code, airline, departure, arrival, rating, Id, operation);
				request.getSession().setAttribute("flight", f);
				switch (operation) {
				// QUERY
				case 2:
					response.sendRedirect("agency/flight/flight_search_res.jsp");	
					break;
				default:
					response.sendRedirect("agency/agency.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 2:
					response.sendRedirect("agency/flight/flight_search.jsp?query=fail");	
					break;
				default:
					response.sendRedirect("agency/agency.jsp");
					break;
				}	
			}
			break;
		case 3:
			CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
			try {
				List<Flight> f = cust.manageFlight(code, airline, departure, arrival, rating, Id, operation);
				request.getSession().setAttribute("flight", f);
				switch (operation) {
				// QUERY
				case 2:
					response.sendRedirect("customer/flight/flight_search_res.jsp");	
					break;
				default:
					response.sendRedirect("customer/customer.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 2:
					response.sendRedirect("customer/flight/flight_search.jsp?query=fail");	
					break;
				default:
					response.sendRedirect("customer/customer.jsp");
					break;
				}	
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
