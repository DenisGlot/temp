package mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import mail.ejb.MailEJB;
import mail.validation.EmailValidation;

/**
 * This servlet is supposed to send mail with login and password . url-pattern =
 * '/register'
 */
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(MailServlet.class);

	private MailEJB mailEJB;

	public MailServlet() {
		super();
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
			mailEJB=new MailEJB();
			mailEJB.sendEmail(fromEmail, username, password, toEmail, passwordForClient);
			saveInDataBase(toEmail, passwordForClient);
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
		Properties prop = new Properties();
		InputStream input = null;
		String driver;
		String jdbc_url;
		Connection con = null;
		try {
			input = getClass().getResourceAsStream("/file.properties");
			prop.load(input);
			driver = prop.getProperty("driver_derby");
			jdbc_url = prop.getProperty("jdbc_url");
			try {
				Class.forName(driver);
				System.out.println(jdbc_url);
				con = DriverManager.getConnection(jdbc_url);
				con.createStatement().execute("insert into ACCESS values ('" + email + "','" + password + "')");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
}
