import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthFilter implements Filter {

	public static String username;

	public static String password;

	@Override
	public void destroy() {
		System.out.println("***destroy AuthFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		try {
			declareForFirstTime(request, response);
			if(request.getParameter("action")==null) {
				declareForSecondTime(request, response);
			}
			if (username!=null && password!=null && username.equals("admin") && password.equals("admin")) {
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
		if(username==null) {
			username = request.getParameter("username");
			}
			if(password==null) {
			password = request.getParameter("password");
			}
	}
	private void declareForSecondTime(ServletRequest request, ServletResponse response) {
		if(request.getParameter("username")!=null) {
			username = request.getParameter("username");
			}
			if(request.getParameter("username")!=null) {
			password = request.getParameter("password");
			}
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("***intit AuthFilter");
	}

}
