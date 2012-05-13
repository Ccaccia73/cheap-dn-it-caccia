package loginServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.OperatorManagerRemote;
import com.cheapvacations.logic.TravelAgencyManagerRemote;
import com.cheapvacations.utility.*;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		String unsub_value = request.getParameter("unsubscribe");
		
		boolean unsub = false;
		
		if(unsub_value != null){
			if(unsub_value.equals("true")){
				unsub = true;
			}
		}
		
		String name = null;
		
		switch (user.type) {
		case 1:
			OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
			name = op.getOperator().getUsername();
			if(unsub){
				op.unsubscribe();
			}
			op.logout();
			break;
		case 2:
			TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
			name = ta.getTravelAgency().getUsername();
			if(unsub){
				ta.unsubscribe();
			}
			ta.logout();
			break;
		case 3:
			CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
			name = cust.getCustomer().getUsername();
			if(unsub){
				cust.unsubscribe();
			}
			cust.logout();
			break;
		default:
			break;
		}
		response.sendRedirect("home.jsp?logout="+name);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
