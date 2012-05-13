package loginServlet;

import com.cheapvacations.logic.*;
import com.cheapvacations.utility.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateServlet
 */
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		String first = request.getParameter("firstname");
		String last = request.getParameter("lastname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		switch (user.type) {
		case 1:
			OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
			op.update(first, last, email, address);
			response.sendRedirect("operator/operator.jsp");
			break;
		case 2:
			TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
			ta.update(first, last, email, address);
			response.sendRedirect("agency/agency.jsp");
			break;
		case 3:
			CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
			cust.update(first, last, email, address);
			response.sendRedirect("customer/customer.jsp");
			break;
		default:
			response.sendRedirect("home.jsp");
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
