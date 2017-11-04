import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthFilter implements Filter{

	@Override
	public void destroy() {System.out.println("***destroy AuthFilter");}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		
		String username =  request.getParameter("username");
		String password = request.getParameter("password");
		if(request.getParameter("action")!=null || username.equals("admin") && password.equals("admin")) {
			chain.doFilter(request, response);
		} else {
			out.println("<h1>username or password is not correct</h1>");
		}
		out.close();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {System.out.println("***intit AuthFilter");}

}
