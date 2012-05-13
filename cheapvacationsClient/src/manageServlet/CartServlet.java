package manageServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.logic.CustomerManagerRemote;
import com.cheapvacations.logic.LogicException;
import com.cheapvacations.utility.DateUtility;
import com.cheapvacations.utility.PackageData;
import com.cheapvacations.utility.UserData;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserData user = (UserData)request.getSession().getAttribute("user");
		CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
		
		PackageData pack = new PackageData();
		
		int operation = Integer.parseInt(request.getParameter("op"));
		long pack_id = Long.parseLong(request.getParameter("p_id"));
		
		pack.idCustomer = user.Id;
		pack.Id = pack_id;
		
		try{
			cust.useCart(pack, operation);
		}catch (LogicException e) {
			switch (operation) {
			// Add to Cart
			case 1:
				response.sendRedirect("customer/package/package_search_res.jsp?addpack=fail");
				return;
			// Buy
			case 2:
				response.sendRedirect("customer/package/orders.jsp");
				return;
			// Discard from cart
			case 3:
				response.sendRedirect("customer/package/orders.jsp");
				return;
			// Discard from past orders
			case 4:
				response.sendRedirect("customer/package/cart.jsp");
				return;
			// view orders
			case 5:
				response.sendRedirect("customer/package/orders.jsp");
				return;
			default:
				break;
			}
		}
		
		switch (operation) {
		// Add to Cart
		case 1:
			response.sendRedirect("customer/package/cart.jsp");
			break;
		// Buy
		case 2:
			response.sendRedirect("customer/package/orders.jsp");
			break;
		// Discard
		case 3:
			response.sendRedirect("customer/package/orders.jsp");
			break;
		// view cart
		case 4:
			response.sendRedirect("customer/package/cart.jsp");
			break;
		// view orders
		case 5:
			response.sendRedirect("customer/package/orders.jsp");
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
