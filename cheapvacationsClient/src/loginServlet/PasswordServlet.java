package loginServlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheapvacations.logic.*;

/**
 * Servlet implementation class PasswordServlet
 */
public class PasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			
			Context ctx = getInitialContext();
			String username = request.getParameter("username");
			
			LoginManagerRemote lmRemote = (LoginManagerRemote)ctx.lookup("loginmanagerJNDI");

			try{
				String pw = lmRemote.getPassword(username);
				response.sendRedirect("password.jsp"+"?password="+pw);
			}catch (LogicException e) {
				response.sendRedirect("password.jsp"+"?recovery=fail");
			}
		}catch (Exception e) {
			response.sendRedirect("password.jsp"+"?recovery=fail");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	static public Context getInitialContext() throws Exception{
		
		Hashtable<String,String> env = new Hashtable<String,String>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");
	
		return new InitialContext(env);
		
	}

}
