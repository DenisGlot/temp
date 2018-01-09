package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import cache.factory.CacheType;
import cache.realization.UserCache;
import dao.entity.User;
import mail.send.Sender;
import mail.validation.EmailValidation;
import prefix.Prefix;
import scenario.Scenario;

/**
 * This servlet is supposed to send mail with login and password . url-pattern =
 * '/register'
 */
public class MailServlet extends TemplateServlet {
	private static final long serialVersionUID = 12342352L;
	
	private final Logger logger = Logger.getLogger(MailServlet.class);

	private User user;
	private String toEmail;
	private Long id;
	private boolean validation;
	private boolean checkOnUnique;
	private boolean noThanks;
	
	private static ConcurrentHashMap<Long, User> userCache;
	private static AtomicLong userId;

	public MailServlet() {
		super();
		userCache = new ConcurrentHashMap<>();
		userId = new AtomicLong(0);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		toEmail = request.getParameter("mail");
		//Make possible for update
		//It's working just from RegisterDetails
		if (request.getAttribute("user") != null) {
			user = (User) request.getAttribute("user");
		    id = userId.incrementAndGet();
			userCache.put(id, user);
		} 
		else {
			String idString = request.getParameter("userId");
			if(idString == null) {
				throw new NullPointerException("userId parameter equals null(MailServlet)");
			}
			id = Long.parseLong(idString);
			user = userCache.remove(id);
		}
		//
		logger.debug(user);
 		// On case if user will try get this page through writing http-request by himself
 		if(user == null) {
 			response.sendRedirect("home");
 			logger.debug("User was null");
 		} else {
			validation = EmailValidation.validate(toEmail == null ? "" : toEmail);
			checkOnUnique = false;
			noThanks = toEmail == null ? false : toEmail.equals("a@mail.ru");
			if (validation) {
				if (noThanks || scenario.getById(CacheType.USER, toEmail) == null) {
					// Needs checkOnUnique equals true further
					checkOnUnique = true;
					if (!noThanks) {
						if (toEmail != null) {
							user.setEmail(toEmail);

						}
						new Sender().sendPassword(toEmail, user.getPassword());
					}
					saveInDataBase(user);
				}
			}
			user = null; // for GC
			sendHtmlToBrowser(request, response);
 		}
	}
	
	@Override
	public String insertJs() {
		return "sign";
	}

	@Override
	public String insertCss() {
		return "style";
	}

	@Override
	public String insertTitle() {
		return "Registration";
	}

	
	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		out.println("<div class=\"box\">");
		out.println("<form action=\"register\" method=\"GET\">");
		if (!validation || !checkOnUnique) {
			out.println("<h1>Do you want to receive information from us?</h1>");
			out.println("<input type=\"hidden\" name=\"userId\" value=\"" + id + "\" />");
			out.println("<input placeholder=\"your email\" value =\"" + (toEmail==null?"":toEmail) + "\" type=\"text\" name=\"mail\""
					+ "onFocus=\"field_focus(this, 'email');\""
					+ "onblur=\"field_blur(this, 'email');\" class=\"email\" />"
					+ (validation ? (checkOnUnique ? "" : "<br/><em style=\"color:red;\">This email already exists</em>") 
							                       : "<br/><em style=\"color:red;\">Please, write a valid email</em>"));
			out.println("<input type=\"submit\" value=\"Send\" id=\"btn2\"/><br/><br/><br/><br/><br/>");
			out.println("<a href=\"" + Prefix.prefix + "/register?mail=a%40mail.ru&userId=" + id + "\">No, thank you. Continue</a>");
		} else {
			out.println("<h1>You were successfully signed up!</h1> <br/>");
			out.println("<a class=\"button\" href=\"" + Prefix.prefix + "/\">Go to Main page</a>");
		}
		out.println("</form>");
		out.println("</div>");
		toEmail = null; //for GC
		
	}
	
	 /**
     * Saves a user in database
     * @param email
     * @param password
     * @return
     */
	private boolean saveInDataBase(User user) {
		return scenario.registerUser(user);
	}
	
	private void clearCacheHere() {
		userCache.clear();
	}
}
