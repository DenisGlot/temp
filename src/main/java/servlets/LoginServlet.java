package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prefix.Prefix;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends TemplateServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	@Override
	public String insertJs() {
		return "signin";
	}

	@Override
	public String insertCss() {
		return "style";
	}

	@Override
	public String insertTitle() {
		return "Authorization";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		  out.println("<form method=\"POST\" action=\"item\">\r\n" + 
		  		"		<div class=\"box\">\r\n" + 
		  		"			<h1>Calculator Pro</h1> Your email address\r\n" + 
		  		"			<input id=\"email\" type=\"text\" name=\"email\"\r\n" + 
		  		"				onFocus=\"field_focus(this, 'email');\"\r\n" + 
		  		"				onblur=\"field_blur(this, 'email');\" class=\"email\" />\r\n" + 
		  		"				Password <input\r\n" + 
		  		"				id=\"password\" type=\"password\" name=\"password\"\r\n" + 
		  		"				onFocus=\"field_focus(this, 'email');\"\r\n" + 
		  		"				onblur=\"field_blur(this, 'email');\" class=\"email\" />\r\n" + 
		  		"              <input onclick = \"redirect()\" type=\"submit\" value=\"Log in\" id=\"btn2\"/>\r\n" + 
		  		"              <h1><a href =\""+ Prefix.prefix +"/registerDetails\">Registration</a></h1>\r\n" + 
		  		"		</div>\r\n" + 
		  		"	</form>");
	}

}
