package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import cache.realization.UserCache;
import dao.entity.User;
import mail.send.Sender;
import mail.validation.EmailValidation;
import scenario.Scenario;

/**
 * This servlet is supposed to send mail with login and password . url-pattern =
 * '/register'
 */
public class MailServlet extends TemplateServlet {
	private static final long serialVersionUID = 12342352L;

	private UserCache userCache;
	private Scenario scenario;
	private String toEmail;
	private String passwordForClient;
	private boolean validation;
	private boolean checkOnUnique;

	public MailServlet() {
		super();
		userCache = new UserCache(User.class);
		scenario = new Scenario();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		toEmail = request.getParameter("mail");
		passwordForClient = RandomStringUtils.randomAlphanumeric(6);
		validation = EmailValidation.validate(toEmail == null ? "" : toEmail);
		checkOnUnique = false;
		if (validation) {
			if(userCache.get(toEmail)==null) {
				checkOnUnique=true;
				new Sender().sendPassword(toEmail, passwordForClient);
				saveInDataBase(toEmail, passwordForClient);
			}
		}

		sendHtmlToBrowser(request, response);
		
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
		out.println("<form method=\"GET\">");
		if (!validation || !checkOnUnique) {
			out.println("<h1>On this email will be sent the password</h1>");
			out.println("<input value =\"" + toEmail + "\" type=\"text\" name=\"mail\""
					+ "onFocus=\"field_focus(this, 'email');\""
					+ "onblur=\"field_blur(this, 'email');\" class=\"email\" />"
					+ (validation ? (checkOnUnique ? "" : "<br/><em style=\"color:red;\">This email already exists</em>") 
							                       : "<br/><em style=\"color:red;\">Please, write a valid email</em>"));
			out.println("<input type=\"submit\" value=\"Send\" id=\"btn2\"/>");
		} else {
			out.println("<h1>Message with password was sent on your email!</h1> <br/>");
			out.println("<a class=\"button\" href=\"/\">Go to Main page</a>");
		}
		out.println("</form>");
		out.println("</div>");
		
	}
	
	 /**
     * Saves a user in database
     * @param email
     * @param password
     * @return
     */
	private boolean saveInDataBase(String email, String password) {
		return scenario.registerUser(User.newBuilder().setEmail(email).setPassword(password).setGruopId(2).build(), userCache);
	}

	

}
