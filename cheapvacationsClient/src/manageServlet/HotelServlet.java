package manageServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.logic.*;
import com.cheapvacations.entities.*;
import com.cheapvacations.utility.UserData;

import java.util.*;

/**
 * Servlet implementation class HotelServlet
 */
public class HotelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		String name = request.getParameter("hname");
		String city = request.getParameter("hcity");
		String address = request.getParameter("haddress");
		
		String star_str = request.getParameter("hstars");
		int stars;
		if(star_str != null){
			stars = Integer.parseInt(star_str);
		}else{
			stars = 0;
		}
		
		String rating_str = request.getParameter("hrating");
		float rating;
		if(rating_str != null){
			rating = Float.parseFloat(rating_str);
		}else{
			rating = -1;
		}

		String id_str = request.getParameter("hid");
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
				List<Hotel> h = op.manageHotel(name, city, address, stars, rating, Id, operation);
				request.getSession().setAttribute("hotel", h);
				switch (operation) {
				// INSERT
				case 1:
					response.sendRedirect("operator/hotel/hotel_new_res.jsp");	
					break;
				// QUERY
				case 2:
					response.sendRedirect("operator/hotel/hotel_search_res.jsp");	
					break;
				// UPDATE
				case 3:
					response.sendRedirect("operator/operator.jsp?hotelupdate=ok");	
					break;
				// DELETE
				case 4:
					response.sendRedirect("operator/operator.jsp?hotelremove=ok");	
					break;
				default:
					response.sendRedirect("operator/operator.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 1:
					response.sendRedirect("operator/hotel/hotel_new.jsp?insert=fail");
					break;
				case 2:
					response.sendRedirect("operator/hotel/hotel_search.jsp?query=fail");	
					break;
				case 3:
					response.sendRedirect("operator/operator.jsp?hotelupdate=fail");	
					break;
				case 4:
					response.sendRedirect("operator/operator.jsp?hotelremove=fail");	
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
				List<Hotel> h = ta.manageHotel(name, city, address, stars, rating, Id, operation);
				request.getSession().setAttribute("hotel", h);
				switch (operation) {
				// QUERY
				case 2:
					response.sendRedirect("agency/hotel/hotel_search_res.jsp");	
					break;
				default:
					response.sendRedirect("agency/agency.jsp");
					break;
				}
			} catch (LogicException e) {
				switch (operation) {
				case 2:
					response.sendRedirect("agency/hotel/hotel_search.jsp?query=fail");	
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
				List<Hotel> h = cust.manageHotel(name, city, address, stars, rating, Id, operation);
				request.getSession().setAttribute("hotel", h);
				switch (operation) {
				// QUERY
				case 2:
					response.sendRedirect("customer/hotel/hotel_search_res.jsp");	
					break;
				default:
					response.sendRedirect("customer/customer.jsp");
					break;
				}
			} catch (LogicException e) {
				switch (operation) {
				case 2:
					response.sendRedirect("customer/hotel/hotel_search.jsp?query=fail");	
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
