package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cache.factory.CacheType;
import dao.entity.User;
import jdbc.JdbcTemplate;
import prefix.Prefix;

/**
 * the path is \/registerDetails
 * Here I used jquery.validate library to validate fields
 * also I send the Object #User (could not be safe because of stored password there)  to the servlet MailServlet
 * @author Denis
 *
 */
public class RegisterDetails extends TemplateServlet {
	private static final long serialVersionUID = 14326457243354754L;
	
	private final Logger logger = Logger.getLogger(RegisterDetails.class);
   
    public RegisterDetails() {
        super();
    }

	@Override
	public String insertJs() {
		return "rulesForRegistration";
	}

	@Override
	public String insertCss() {
		return "styleForRequired";
	}

	@Override
	public String insertTitle() {
		return "Register Data";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		//Declares variables
		String firstName = (String) request.getParameter("fname");
		String lastName = (String) request.getParameter("lname");
		String phone = (String) request.getParameter("phone");
		String password = (String) request.getParameter("password");
		boolean isNotUnique = false; 
		
		logger.debug(firstName + " " + lastName + " " + phone + " " + password ) ; 
		
		/* Eventually uncomment
		if(password!=null) {
			isNotUnique=scenario.getById(CacheType.USER, phone)!=null;
		}
		*/
	    if(firstName!=null && lastName != null && phone !=null && password !=null /*Eventually uncomment && isNotUnique*/) {
	    	request.setAttribute("user", new User().newBuilder().setFirstName(firstName).setLastName(lastName).setGruopId(2).setPhone(phone).setPassword(password).build());
	    	//forward the request to MailServlet
	    	RequestDispatcher reqDispatcher = request.getRequestDispatcher("/register");// No need prefix here
	    	try {
				reqDispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
	    } else {
	    	out.println("<div class=\"container\">");
		     out.println("<form class =\"form-horizontal registrationForm\">\r\n" + 
				"  <div class=\"form-group\">\r\n" + 
				"    <label for=\"fname\">First Name</label>\r\n" + 
				"    <input id =\"fname\" name =\"fname\" type=\"text\" class=\"form-control\" id=\"first\" aria-describedby=\"emailHelp\" placeholder=\"Enter first name\">\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"form-group\">\r\n" + 
				"    <label for=\"lname\">Last Name</label>\r\n" + 
				"    <input id = \"lname\" name =\"lname\" type=\"text\" class=\"form-control\" id=\"last\" aria-describedby=\"emailHelp\" placeholder=\"Enter first name\">\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"form-group\">\r\n" + 
				"    <label for=\"phone\">Phone number(format XXXX-XXX-XXXX)</label>\r\n" + 
				"    <input id=\"phone\" name =\"phone\" type=\"text\" class=\"form-control input-medium bfh-phone\" type=\"tel\" pattern=\"^\\d{4}-\\d{3}-\\d{4}$\" required id=\"phone\">"); 
				if(isNotUnique) {
				out.println("    <label>This phone already exists</label>");
				}
				out.println("  </div>\r\n" + 
				"  <div class=\"form-group\">\r\n" + 
				"    <label for=\"password\">Password</label>\r\n" + 
				"    <input id = \"password\" name =\"password\" type=\"password\" class=\"form-control\" id=\"password\">\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"form-group\">\r\n" + 
				"    <label for=\"confirmPassword\">Confirm Password</label>\r\n" + 
				"    <input id = \"confirmPasswrod\" name = \"confirmPassword\" type=\"password\" class=\"form-control\" id=\"confirmPassword\">\r\n" + 
				"  </div>\r\n" + 
				"  \r\n" + 
				"  <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\r\n" + 
				"</form>");
		     out.println("</div>");
		     out.println("<script type=\"text/javascript\" src=\"https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.js\"></script>");
	    }
	}

}
