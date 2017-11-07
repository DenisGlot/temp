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

/**
 * This class was for authentication. It's not for use
 * @author Denis
 *
 */
public class AuthFilter implements Filter {

	private String username;

	private String password;

	@Override
	public void destroy() {
		System.out.println("***destroy AuthFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		  HttpSession session = ((HttpServletRequest) request).getSession();
		  
	        
		PrintWriter out = response.getWriter();
		try {
			declareForFirstTime(request, response);
			if (session.getAttribute("email")!=null || username!=null && password!=null && username.equals("admin") && password.equals("admin")) {
				if(session.getAttribute("email")==null) {
				  session.setAttribute("email", "LOGIN");
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
		} finally {
			out.close();
		}
	}


	private void declareForFirstTime(ServletRequest request, ServletResponse response) {
			username = request.getParameter("username");
			password = request.getParameter("password");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("***intit AuthFilter");
	}

}
