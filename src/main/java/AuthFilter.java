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
import dao.MyDAO;
import dao.entity.Role;
import dao.entity.User;
import dao.superb.DAO;
import hash.Hashing;

/**
 * This class is for authentication.
 * 
 * @author Denis
 *
 */
public class AuthFilter implements Filter {

	final Logger logger = Logger.getLogger(AuthFilter.class);

	private MyDAO<User, Integer> userDAO;
	
	private Cache cache = null;
	
	private DAO<Role,Integer> roleDAO;
	
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
					out.println("<a href=\"/\">Try again?</a>");
					out.println("</body></html>");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("***initialize AuthFilter");
		userDAO = new MyDAO<>(User.class);
		roleDAO = new MyDAO<>(Role.class);
		cache = new Cache();
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
		User user = cache.loadUser(email);
		if (user == null) {
			return false;
		}
		if (user.getEmail().equals(email) && user.getPassword().equals(Hashing.sha1(password))) {
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
         Role role = roleDAO.findById(user.getGroupid());
         this.role = role.getRole();
	}

}
