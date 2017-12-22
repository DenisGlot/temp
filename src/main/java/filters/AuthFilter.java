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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cache.Cache;
import cache.factory.CacheType;
import cache.realization.RoleCache;
import cache.realization.UserCache;
import dao.DAO;
import dao.MyDAO;
import dao.entity.Role;
import dao.entity.User;
import hash.Hashing;
import prefix.Prefix;
import scenario.Scenario;
import servlets.SendHtml;

/**
 * This class is for authentication.
 * 
 * @author Denis
 *
 */
public class AuthFilter implements Filter,SendHtml {

	final Logger logger = Logger.getLogger(AuthFilter.class);

	
	private Scenario scenario;
	
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
				try (PrintWriter out = response.getWriter()) {
					sendHtmlToBrowser( (HttpServletRequest) request, (HttpServletResponse) response);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("***initialize AuthFilter");
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
		User user = (User) scenario.getById(CacheType.USER,email);
		if (user == null) {
			return false;
		}
		if (scenario.authorization(user)) {
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
         this.role = ((Role) scenario.getById(CacheType.ROLE,user.getGroupid())).getRole();
	}

	@Override
	public String insertJs() {
		return null;
	}

	@Override
	public String insertCss() {
		return "csForMenu";
	}

	@Override
	public String insertTitle() {
		return "Error";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		out.println("<div class=\"contain\">");
		if(request.getServletPath().equals("/feedback")) {
			out.println("<strong style = \"color:red; font-size: 1.5em;\">You must authorize to send a message</strong>");
		} else if(request.getParameter("email")==null){
			out.println("<strong style = \"color:red; font-size: 1.5em; margin-left: 30px;\">You must authorize to get in</strong>");
		} else {
			out.println("<strong style = \"color:red; font-size: 1.5em;\">Username or password is not correct</strong>");
		}
		out.println("<br/><br/><a style =\"font: bold 20px Arial;background-color: #ADFF2F;margin-left: 33%;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href=\"" + Prefix.prefix + "/signin\">Login?</a>");
        out.println("</div>"); 	 
	}

}
