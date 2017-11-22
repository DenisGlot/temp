import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * This class is for authentication.
 * 
 * @author Denis
 *
 */
public class AuthFilter implements Filter {

	final Logger logger = Logger.getLogger(AuthFilter.class);
 
    JdbcTemplate jt;
 
	@Override
	public void destroy() {
		System.out.println("***destroy AuthFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		String email = null;
		String password = null;
		System.out.println("*******Session Atribute!! = " + session.getAttribute("login"));
		if (logger.isDebugEnabled()) {
			logger.debug("Session Atribute!! = " + session.getAttribute("login"));
		}
		if (session.getAttribute("login") != null && session.getAttribute("login").equals("LOGIN")
				&& request.getParameter("email") == null) {
			System.out.println("I'm in first chain.doFilter()");
			if (logger.isDebugEnabled()) {
				logger.debug("The filter did log in without email and password");
			}
			chain.doFilter(request, response);
		} else {
			
			try(PrintWriter out = response.getWriter();) {
				email = request.getParameter("email");
				password = request.getParameter("password");
				if (email != null && password != null && jt.checkInDataBase(email, password)) {
					session.setAttribute("login", "LOGIN");
					session.setAttribute("email", email);
					System.out.println("I'm in second chain.doFilter()");
					if (logger.isDebugEnabled()) {
						logger.debug("The filter did log in with email and password");
					}
					chain.doFilter(request, response);
				} else {
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>Error</title></head>");
					out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
					out.println("</head>");
					out.println("<body>");
					out.println("<h1>username or password is not correct</h1>");
					out.println("<a href=\"/\">Try again?</a>");
					out.println("</body></html>");
				}
			} 
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("***intit AuthFilter");
		jt = new JdbcTemplate();
	}

}
