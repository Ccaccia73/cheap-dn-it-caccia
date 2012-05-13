package manageServlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.entities.FlightOffer;
import com.cheapvacations.entities.HotelOffer;
import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.LogicException;
import com.cheapvacations.logic.OperatorManagerRemote;
import com.cheapvacations.logic.TravelAgencyManagerRemote;
import com.cheapvacations.utility.DateUtility;
import com.cheapvacations.utility.UserData;

/**
 * Servlet implementation class FlightOfferServlet
 */
public class FlightOfferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightOfferServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		String fo_start = request.getParameter("fo_start");
		String fo_end = request.getParameter("fo_end");
		String fo_price = request.getParameter("fo_price");
		String fid_str = request.getParameter("fid");
		String fo_id_str = request.getParameter("fo_id");
		
		int operation = Integer.parseInt(request.getParameter("op"));

		DateUtility du = new DateUtility();
		
		Date startDate = null;
		if(fo_start != null){
			if(fo_start.length()>0){
				startDate = du.getDateFromString(fo_start);
			}
		}

		Date endDate = null;
		if(fo_end != null){
			if(fo_end.length()>0){
				endDate = du.getDateFromString(fo_end);
			}
		}
		
		if(startDate != null && endDate != null){
			if(startDate.compareTo(endDate) >= 0){
				switch (user.type) {
				case 1:
					switch (operation) {
					case 1:
						response.sendRedirect("operator/flight/flight_offer_new.jsp?insert=fail&fid="+fid_str);
						return;
					default:
						break;
					}
					break;
				case 2:
					break;
				case 3:
					break;
				default:
					break;
				}
			}
		}
		
		
		Long flight_Id = -1L;
		if(fid_str != null){
			if(fid_str.length() > 0){
				flight_Id = Long.parseLong(fid_str);
			}
		}
		
		Long offer_Id = -1L;;
		if(fo_id_str != null){
			if(fo_id_str.length()>0){
				offer_Id = Long.parseLong(fo_id_str);
			}
		}
		
		int price = -1;
		if(fo_price != null){
			if(fo_price.length() != 0){
				price = Integer.parseInt(fo_price);
				if(price <= 0){
					response.sendRedirect("operator/flight/flight_offer_new.jsp?insert=fail&fid="+fid_str);
					return;
				}
			}
		}

		
		switch (user.type) {
		case 1:
			OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
			try {
				List<FlightOffer> fo = op.manageFlightOffer(startDate, endDate, price, user.Id, flight_Id, offer_Id, operation);
				request.getSession().setAttribute("flightoffer", fo);
				switch (operation) {
				// INSERT
				case 1:
					response.sendRedirect("operator/operator.jsp?flightofferinsert=ok");	
					break;
				// QUERY
				case 2:
					response.sendRedirect("operator/flight/flight_offer_search_res.jsp");	
					break;
				// UPDATE NON FATTO
				case 3:
					// response.sendRedirect("operator/operator.jsp?hotelupdate=ok");	
					break;
				// DELETE
				case 4:
					 response.sendRedirect("operator/operator.jsp?flightofferremove=ok");	
					break;
				// QUERY by ID
				case 5:
				// QUERY by FLIGHT ID
				case 6:
					response.sendRedirect("operator/flight/flight_offer_search_res.jsp");	
					break;
				default:
					response.sendRedirect("operator/operator.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 1:
					response.sendRedirect("operator/flight/flight_offer_new.jsp?insert=fail&fid="+fid_str);
					break;
				case 2:
					response.sendRedirect("operator/flight/flight_search.jsp?query=fail");	
					break;
				case 3:
					// response.sendRedirect("operator/operator.jsp?hotelupdate=fail");	
					break;
				case 4:
					response.sendRedirect("operator/operator.jsp?flightofferremove=fail");	
					break;
				case 6:
					response.sendRedirect("operator/flight/flight_search.jsp?query=fail");	
					break;
				default:
					// System.out.println("Hotel OFfer Servlet: Exception");
					response.sendRedirect("operator/operator.jsp");
					break;
				}	
			}
			break;
		case 2:
			TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
			try {
				List<FlightOffer> fo = ta.manageFlightOffer(startDate, endDate, price, user.Id, flight_Id, offer_Id, operation);
				request.getSession().setAttribute("flightoffer", fo);
				switch (operation) {
				// QUERY
				case 2:
					response.sendRedirect("agency/flight/flight_offer_search_res.jsp");	
					break;
				// QUERY by ID
				case 6:
					response.sendRedirect("agency/flight/flight_offer_search_res.jsp");	
					break;
				// ADD TO PACKAGE AS DEPARTURE FLIGHT
				case 7:
					ta.setPackageDepart(fo.get(0));
					response.sendRedirect("agency/agency.jsp?flightofferinsert=Dok");
					break;
				// ADD TO PACKAGE AS RETURN FLIGHT
				case 8:
					ta.setPackageReturn(fo.get(0));
					response.sendRedirect("agency/agency.jsp?flightofferinsert=Rok");
					break;
				default:
					// response.sendRedirect("operator/operator.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 2:
				case 6:
					response.sendRedirect("agency/hotel/hotel_search.jsp?query=fail");	
					break;
				default:
					// System.out.println("Hotel OFfer Servlet: Exception");
					response.sendRedirect("agency/agency.jsp");
					break;
				}	
			}
			break;
		case 3:
			// Customer does not need to see flight offers directly
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
