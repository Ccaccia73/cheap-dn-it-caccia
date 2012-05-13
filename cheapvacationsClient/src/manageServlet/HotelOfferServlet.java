package manageServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.entities.*;
import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.LogicException;
import com.cheapvacations.logic.OperatorManagerRemote;
import com.cheapvacations.logic.TravelAgencyManagerRemote;
import com.cheapvacations.utility.*;
import java.sql.Date;

/**
 * Servlet implementation class HotelOfferServlet
 */
public class HotelOfferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelOfferServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		String ho_start = request.getParameter("ho_start");
		String ho_end = request.getParameter("ho_end");
		String ho_price = request.getParameter("ho_price");
		String hid_str = request.getParameter("hid");
		String ho_id_str = request.getParameter("ho_id");

		int operation = Integer.parseInt(request.getParameter("op"));
		
		
		DateUtility du = new DateUtility();
		
		Date startDate = null;
		if(ho_start != null){
			if(ho_start.length()>0){
				startDate = du.getDateFromString(ho_start);
			}
		}

		Date endDate = null;
		if(ho_end != null){
			if(ho_end.length()>0){
				endDate = du.getDateFromString(ho_end);
			}
		}
		
		if(startDate != null && endDate != null){
			if(startDate.compareTo(endDate) >= 0){
				switch (user.type) {
				case 1:
					switch (operation) {
					case 1:
						response.sendRedirect("operator/hotel/hotel_offer_new.jsp?insert=fail&hid="+hid_str);						
						return;
					default:
						break;
					}
					break;
				case 2:
					// response.sendRedirect("agency/hotel/hotel_offer_new.jsp?insert=fail&hid="+hid_str);
					break;
				default:
					break;
				}
			}
		}
		
		
		Long hotel_Id = -1L;
		if(hid_str != null){
			if(hid_str.length() > 0){
				hotel_Id = Long.parseLong(hid_str);
			}
		}
		
		Long offer_Id = -1L;;
		if(ho_id_str != null){
			if(ho_id_str.length()>0){
				offer_Id = Long.parseLong(ho_id_str);
			}
		}
		
		int price = -1;
		if(ho_price != null){
			if(ho_price.length() != 0){
				price = Integer.parseInt(ho_price);
				if(price <= 0){
					response.sendRedirect("operator/hotel/hotel_offer_new.jsp?insert=fail&hid="+hid_str);
					return;
				}
			}
		}

		/*
		if(startDate != null){
			System.out.println("Start Date: "+startDate.toString());			
		}else{
			System.out.println("Start Date: NULL");
		}
		
		if(endDate != null){
			System.out.println("Ende Date: "+endDate.toString());
		}else{
			System.out.println("Ende Date: NULL");
		}
		System.out.println("Price: "+price);
		System.out.println("Operator ID: "+user.Id);
		System.out.println("Hotel Id: "+hotel_Id);
		System.out.println("Offer Id: "+offer_Id);
		System.out.println("operation: "+operation);
		*/
		
		switch (user.type) {
		case 1:
			OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
			try {
				List<HotelOffer> ho = op.manageHotelOffer(startDate, endDate, price, user.Id, hotel_Id, offer_Id, operation);
				request.getSession().setAttribute("hoteloffer", ho);
				switch (operation) {
				// INSERT
				case 1:
					response.sendRedirect("operator/operator.jsp?hotelofferinsert=ok");	
					break;
				// QUERY
				case 2:
					response.sendRedirect("operator/hotel/hotel_offer_search_res.jsp");	
					break;
				// UPDATE NON GESTITO
				case 3:
					// response.sendRedirect("operator/operator.jsp?hotelupdate=ok");	
					break;
				// DELETE
				case 4:
					response.sendRedirect("operator/operator.jsp?hotelofferremove=ok");	
					break;
				// QUERY by ID
				case 5:
				// QUERY by HOTEL ID
				case 6:
					response.sendRedirect("operator/hotel/hotel_offer_search_res.jsp");	
					break;
				default:
					response.sendRedirect("operator/operator.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 1:
					response.sendRedirect("operator/operator.jsp?hotelofferinsert=fail");
					break;
				case 2:
					response.sendRedirect("operator/hotel/hotel_search.jsp?query=fail");	
					break;
				case 3:
					// response.sendRedirect("operator/operator.jsp?hotelupdate=fail");	
					break;
				case 4:
					response.sendRedirect("operator/operator.jsp?hotelofferremove=fail");	
					break;
				case 6:
					response.sendRedirect("operator/hotel/hotel_search.jsp?query=fail");	
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
				List<HotelOffer> ho = ta.manageHotelOffer(startDate, endDate, price, user.Id, hotel_Id, offer_Id, operation);
				request.getSession().setAttribute("hoteloffer", ho);
				switch (operation) {
				// QUERY
				case 2:
					response.sendRedirect("agency/hotel/hotel_offer_search_res.jsp");	
					break;
				// QUERY by ID
				case 6:
					response.sendRedirect("agency/hotel/hotel_offer_search_res.jsp");
					break;
				// ADD TO PACKAGE
				case 7:
					ta.setPackageHotel(ho.get(0));
					response.sendRedirect("agency/agency.jsp?hotelofferinsert=ok");
					break;
				default:
					response.sendRedirect("agency/agency.jsp");
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 2:
				case 6:
					response.sendRedirect("agency/hotel/hotel_search.jsp?query=fail");	
					break;
				case 7:
					response.sendRedirect("agency/agency.jsp?hotelofferinsert=fail");
					break;
				default:
					// System.out.println("Hotel OFfer Servlet: Exception");
					response.sendRedirect("agency/agency.jsp");
					break;
				}	
			}
			break;
		case 3:
			// customer does not need to see Hotel Offers Directly
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
