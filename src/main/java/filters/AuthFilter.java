package filters;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cache.Cache;
import cache.realization.RoleCache;
import cache.realization.UserCache;
import dao.DAO;
import dao.MyDAO;
import dao.entity.Role;
import dao.entity.User;
import hash.Hashing;
import prefix.Prefix;
import scenario.Scenario;

/**
 * This class is for authentication.
 * 
 * @author Denis
 *
 */
public class AuthFilter implements Filter {

	final Logger logger = Logger.getLogger(AuthFilter.class);

	
	private Scenario scenario;
	
	private UserCache userCache;
	
	private Cache<Integer, Role> roleCache;
	
	private String role = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		String email = null;
		String password = null;
		logger.debug("Session Atribute = " + session.getAttribute("login"));
		// Logic begins
		// if session has attribute login equals 'LOGIN' than user don't need to
		// authorize
		if (session.getAttribute("login") != null
				// It gives opportunity to sign in as another user
				&& request.getParameter("email") == null) {
			logger.debug("The filter did log in without email and password");
			chain.doFilter(request, response);
		} else {
			// Otherwise this logic checking in database email and password
			try (PrintWriter out = response.getWriter();) {
				email = request.getParameter("email");
				logger.debug("The email is " + email);
				password = request.getParameter("password");
				// if email and password is correct then we are going to page with calculator
				if (email != null && password != null && checkInDataBase(email, password)) {
					session.setAttribute("login", "LOGIN");
					session.setAttribute("email", email);
					session.setAttribute("role", role);
					logger.debug("The filter did log in with email and password");
					chain.doFilter(request, response);
				} else {
					// Or else we will see page with 'try again'
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>Error</title></head>");
					out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
					out.println("</head>");
					out.println("<body>");
					out.println("<h1>username or password is not correct</h1>");
					out.println("<a href=\"" + Prefix.prefix + "/signin.html\">Login?</a>");
					out.println("</body></html>");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("***initialize AuthFilter");
		userCache = new UserCache(User.class);
		roleCache = new RoleCache(Role.class);
		scenario = new Scenario();
	}

	@Override
	public void destroy() {
		logger.debug("***destroy AuthFilter");
	}
    /**
     * Checks in database on presence of user
     * @param email
     * @param password
     * @return
     */
	private boolean checkInDataBase(String email, String password) {
		User user = userCache.get(email);
		if (user == null) {
			return false;
		}
		if (scenario.authorization(user, userCache)) {
            declareRoleOfUser(user);
			return true;
		}
		return false;
	}
    /**
     * Declares field role 
     * @param user
     */
	private void declareRoleOfUser(User user) {
         this.role = roleCache.get(user.getGroupid()).getRole();
	}

}
