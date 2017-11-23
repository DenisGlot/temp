package mail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import dao.UserController;
import dao.entity.User;
import hash.Hashing;
import jdbc.JdbcTemplate;
import mail.ejb.MailEJB;
import mail.validation.EmailValidation;

/**
 * This servlet is supposed to send mail with login and password . url-pattern =
 * '/register'
 */
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserController uc = null;

    @EJB
	private MailEJB mailEJB;

	public MailServlet() {
		super();
		uc = new UserController();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String toEmail = request.getParameter("mail");
		String fromEmail = "denisglotov98@gmail.com";
		String username = "denisglotov98";
		String password = "strongdweeb2307";
		String passwordForClient = RandomStringUtils.randomAlphanumeric(4);
		boolean validation = EmailValidation.validate(toEmail==null?"":toEmail);
		if (validation) {
			mailEJB.sendEmail(fromEmail, username, password, toEmail, passwordForClient);
			saveInDataBase(toEmail, Hashing.sha1(passwordForClient));
		}
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<script type=\"text/javascript\" src=\"jquery-3.2.1.min.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"sign.js\"></script>");
			// For style
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
			//
			out.println("<title>Registration</title></head>");
			out.println("<body>");
			out.println("<div class=\"box\">");
			out.println("<form method=\"GET\">");
			if (!validation) {
				out.println("<h1>On this email will be sent the password</h1>");
				out.println("<input type=\"text\" name=\"mail\"" + "onFocus=\"field_focus(this, 'email');\"" 
						+ "onblur=\"field_blur(this, 'email');\" class=\"email\" />" + (validation ? "" : "<br/><em style=\"color:red;\">Please, write a valid email</em>"));
				out.println("<input type=\"submit\" value=\"Send\" id=\"btn2\"/>");
			} else {
				out.println("<h1>Message with password was sent on your email!</h1> <br/>");
				out.println("<a class=\"button\" href=\"/\">Go to Main page</a>");
			}
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void saveInDataBase(String email, String password) {
		if(email == null || password == null) {
			throw new IllegalArgumentException("email and password cannot be null!");
		}
		uc.save(new User(email,password));
	}
	
}
