package manageServlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.entities.Hotel;
import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.LogicException;
import com.cheapvacations.logic.OperatorManagerRemote;
import com.cheapvacations.logic.TravelAgencyManagerRemote;
import com.cheapvacations.utility.*;

/**
 * Servlet implementation class PackageServlet
 */
public class PackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PackageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		int operation = Integer.parseInt(request.getParameter("op"));

		PackageData pack = new PackageData();
		
		DateUtility du = new DateUtility();
		
		switch (user.type) {
		case 1:
			OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
			try {
				switch (operation) {
				// FIND BY HOTEL OFFER ID
				case 2:
					long idHotel = Long.parseLong(request.getParameter("ho_id"));
					pack.idHotel = idHotel;
					op.manageVacationPackage(pack, -1, operation);
					response.sendRedirect("operator/package/package_search_res.jsp?ho_id="+idHotel);
					return;
				// FIND BY FLIGHT OFFER ID
				case 3:
					long idDepart = Long.parseLong(request.getParameter("fo_id"));
					pack.idDeparture = idDepart;
					op.manageVacationPackage(pack, -1, operation);
					response.sendRedirect("operator/package/package_search_res.jsp?fo_id="+idDepart);
					return;
				//  GENERIC QUERY
				case 4:
					
					String departure = request.getParameter("pdepart");
					String arrival = request.getParameter("parrive");
					String hotelname = request.getParameter("photel");
					String str_price = request.getParameter("pmaxprice");
					
					int max_price = -1;
					if(str_price != null){
						if(str_price.length() > 0){
							max_price = Integer.parseInt(str_price); 
						}
					}
					
					String pstartdate = request.getParameter("pstartdate");
					String penddate = request.getParameter("penddate");
					
					
					Date startDate = null;
					if(pstartdate != null){
						if(pstartdate.length()>0){
							startDate = du.getDateFromString(pstartdate);
						}
					}

					Date endDate = null;
					if(penddate != null){
						if(penddate.length()>0){
							endDate = du.getDateFromString(penddate);
						}
					}
					
					if(startDate != null && endDate != null){
						if(startDate.compareTo(endDate) >= 0){
							response.sendRedirect("operator/package/package_search.jsp?query=fail");
						}
					}
					pack.startDate = startDate;
					pack.endDate = endDate;
					pack.price = max_price;
					pack.departurecity = departure;
					pack.arrivalcity = arrival;
					pack.hotelname = hotelname;
					
					op.manageVacationPackage(pack, 7, operation);
					
					response.sendRedirect("operator/package/package_search_res.jsp"+
					"?pdepart="+departure+"&parrive="+arrival+"&photel="+hotelname+"&pmaxprice="+str_price+
					"&pstartdate="+pstartdate+"&penddate="+penddate);
					return;
				default:
					break;
				}
				
			} catch (LogicException e) {
				switch (operation) {
				case 2:
				case 3:
				case 4:
					response.sendRedirect("operator/operator.jsp?packquery=fail");
					return;
				default:
					break;
				}	
			}
			break;
		case 2:
			TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
			try {
				switch (operation) {
				case 1:
					
					String str_markup = request.getParameter("pmarkup");
					
					float markup = 0;
					boolean validmarkup = false;
					int tmp_markup;
					if(str_markup != null){
						if(str_markup.length() > 0){
							tmp_markup = Integer.parseInt(str_markup);
							if(tmp_markup > 0){
								validmarkup = true;
								markup = (float)tmp_markup/100;
							}
						}
					}
					
					if(!validmarkup){
						response.sendRedirect("agency/package/package_new.jsp?insert=fail");
						return;
					}
					
					String pstartdate = request.getParameter("pstartd");
					String penddate = request.getParameter("pendd");
					
					
					Date startDate = null;
					if(pstartdate != null){
						if(pstartdate.length()>0){
							startDate = du.getDateFromString(pstartdate);
						}
					}

					Date endDate = null;
					if(penddate != null){
						if(penddate.length()>0){
							endDate = du.getDateFromString(penddate);
						}
					}
					
					if(startDate == null || endDate == null || !(endDate.after(startDate)  ) ){
						response.sendRedirect("agency/package/package_new.jsp?insert=fail");
						return;
					}
					
					Date d1 = du.getIntersectingPeriodStart(ta.getPackageDepart().getStartDate(), ta.getPackageHotel().getStartDate());
					Date d2 = du.getIntersectingPeriodEnd(ta.getPackageDepart().getEndDate(), ta.getPackageHotel().getEndDate());
					Date d3 = du.getIntersectingPeriodStart(ta.getPackageReturn().getStartDate(), ta.getPackageHotel().getStartDate());
					Date d4 = du.getIntersectingPeriodEnd(ta.getPackageReturn().getEndDate(), ta.getPackageHotel().getEndDate());
					
					if(startDate.before(d1) || startDate.after(d2) || endDate.before(d3) || endDate.after(d4)){
						response.sendRedirect("agency/package/package_new.jsp?insert=fail");
						return;
					}
					
					int price = ta.getPackageDepart().getPrice() + ta.getPackageReturn().getPrice() + 
							ta.getPackageHotel().getPrice()*( du.DifferenceInDays(startDate, endDate) );
					
					price = Math.round((float)price * (1+markup) / 10) * 10;
					
					pack.startDate = startDate;
					pack.endDate = endDate;
					pack.price = price;
					pack.markup = markup;
					pack.status = 2;
					pack.idAgency = user.Id;
					pack.idDeparture = ta.getPackageDepart().getId();
					pack.idReturn = ta.getPackageReturn().getId();
					pack.idHotel = ta.getPackageHotel().getId();
					
					ta.manageVacationPackage(pack, -1, operation);
					
					response.sendRedirect("agency/agency.jsp?packinsert=ok");
					return;
				// FIND BY HOTEL OFFER ID
				case 2:
					long idHotel = Long.parseLong(request.getParameter("ho_id"));
					pack.idHotel = idHotel;
					pack.idAgency = user.Id;
					ta.manageVacationPackage(pack, -1, operation);
					response.sendRedirect("agency/package/package_search_res.jsp?ho_id="+idHotel);
					return;
				// FIND BY FLIGHT OFFER ID
				case 3:
					long idDepart = Long.parseLong(request.getParameter("fo_id"));
					pack.idDeparture = idDepart;
					pack.idAgency = user.Id;
					ta.manageVacationPackage(pack, -1, operation);
					response.sendRedirect("agency/package/package_search_res.jsp?fo_id="+idDepart);
					return;
				//  GENERIC QUERY
				case 4:
					
					String departure = request.getParameter("pdepart");
					String arrival = request.getParameter("parrive");
					String hotelname = request.getParameter("photel");
					String str_price = request.getParameter("pmaxprice");
					
					int max_price = -1;
					if(str_price != null){
						if(str_price.length() > 0){
							max_price = Integer.parseInt(str_price); 
						}
					}
					
					pstartdate = request.getParameter("pstartdate");
					penddate = request.getParameter("penddate");
					
					
					startDate = null;
					if(pstartdate != null){
						if(pstartdate.length()>0){
							startDate = du.getDateFromString(pstartdate);
						}
					}

					endDate = null;
					if(penddate != null){
						if(penddate.length()>0){
							endDate = du.getDateFromString(penddate);
						}
					}
					
					if(startDate != null && endDate != null){
						if(startDate.compareTo(endDate) >= 0){
							response.sendRedirect("agency/package/package_search.jsp?query=fail");
						}
					}
					pack.startDate = startDate;
					pack.endDate = endDate;
					pack.price = max_price;
					pack.departurecity = departure;
					pack.arrivalcity = arrival;
					pack.hotelname = hotelname;
					pack.idAgency = user.Id;
					
					ta.manageVacationPackage(pack, 7, operation);
					
					response.sendRedirect("agency/package/package_search_res.jsp"+
					"?pdepart="+departure+"&parrive="+arrival+"&photel="+hotelname+"&pmaxprice="+str_price+
					"&pstartdate="+pstartdate+"&penddate="+penddate);
					return;
				// CANCEL
				case 5:
					String str_Id = request.getParameter("p_id");
					pack.Id = Long.parseLong(str_Id);
					ta.manageVacationPackage(pack, -1, operation);
					response.sendRedirect("agency/agency.jsp?packcancel=ok");	
					return;
				case 8:
					ta.manageVacationPackage(pack, -1, operation);
					response.sendRedirect("agency/agency.jsp?compcancel=ok");
					return;
				default:
					response.sendRedirect("agency/agency.jsp");
					return;
				}
			} catch (LogicException e) {
				switch (operation) {
				case 1:
					response.sendRedirect("agency/package/package_new.jsp?insert=fail");
					return;
				case 2:
				case 3:
				case 4:
					response.sendRedirect("agency/agency.jsp?packquery=fail");
					return;
				case 5:
					response.sendRedirect("agency/agency.jsp?packcancel=fail");	
					return;
				case 8:
					response.sendRedirect("agency/agency.jsp?compcancel=fail");
				default:
					response.sendRedirect("agency/agency.jsp");
					break;
				}	
			}
			break;
		case 3:
			CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
			try {
				switch (operation) {
				//  GENERIC QUERY
				case 4:
					
					String departure = request.getParameter("pdepart");
					String arrival = request.getParameter("parrive");
					String hotelname = request.getParameter("photel");
					String str_price = request.getParameter("pmaxprice");
					
					int max_price = -1;
					if(str_price != null){
						if(str_price.length() > 0){
							max_price = Integer.parseInt(str_price); 
						}
					}
					
					String pstartdate = request.getParameter("pstartdate");
					String penddate = request.getParameter("penddate");
					
					
					Date startDate = null;
					if(pstartdate != null){
						if(pstartdate.length()>0){
							startDate = du.getDateFromString(pstartdate);
						}
					}

					Date endDate = null;
					if(penddate != null){
						if(penddate.length()>0){
							endDate = du.getDateFromString(penddate);
						}
					}
					
					if(startDate != null && endDate != null){
						if(startDate.compareTo(endDate) >= 0){
							response.sendRedirect("customer/package/package_search.jsp?query=fail");
						}
					}
					pack.startDate = startDate;
					pack.endDate = endDate;
					pack.price = max_price;
					pack.departurecity = departure;
					pack.arrivalcity = arrival;
					pack.hotelname = hotelname;
					
					// available packages only
					pack.status = 2;
					
					cust.manageVacationPackage(pack, 7, operation);
					
					response.sendRedirect("customer/package/package_search_res.jsp"+
					"?pdepart="+departure+"&parrive="+arrival+"&photel="+hotelname+"&pmaxprice="+str_price+
					"&pstartdate="+pstartdate+"&penddate="+penddate);
					return;
				// Query by Hotel ID
				case 6:
					String hid_str = request.getParameter("hid");
					pack.hotel_Id = Long.parseLong(hid_str);
					
					// available packages only
					pack.status = 2;
					
					cust.manageVacationPackage(pack, 0, operation);
					response.sendRedirect("customer/package/package_search_res.jsp?hid="+hid_str);
					return;
				// Query by Flight ID
				case 7:
					String fid_str = request.getParameter("fid");
					pack.flight_Id = Long.parseLong(fid_str);
					
					// available packages only
					pack.status = 2;

					cust.manageVacationPackage(pack, 0, operation);
					response.sendRedirect("customer/package/package_search_res.jsp?fid="+fid_str);
					return;
				default:
					response.sendRedirect("customer/customer.jsp");
					return;
				}
			} catch (LogicException e) {
				switch (operation) {
				case 4:
				case 6:
				case 7:
					response.sendRedirect("customer/customer.jsp?packquery=fail");
					return;
				default:
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
