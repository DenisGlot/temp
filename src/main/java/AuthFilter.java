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
			if(username==null) {
			username = request.getParameter("username");
			}
			if(password==null) {
			password = request.getParameter("password");
			}
			System.out.println(username + "  " + password);
			if (username!=null && password!=null && username.equals("admin") && password.equals("admin")) {
				chain.doFilter(request, response);
			} else {
				out.println("<h1>username or password is not correct</h1>");
				out.println("<a href=\"/\">Registation</a>");
			}
		} finally {
			out.close();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("***intit AuthFilter");
	}

}
