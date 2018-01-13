package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prefix.Prefix;

/**
 * On this servlet will be sent user after trying to login and this servlet will be intercepted by AuthFilter, which make a real authorization
 */
@WebServlet("gohome")
public class NothingServlet extends HttpServlet {
	private static final long serialVersionUID = 16756467L;
       
   
    public NothingServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(Prefix.prefix);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
