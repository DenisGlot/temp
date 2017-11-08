import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class is for authentication.
 * @author Denis
 *
 */
public class AuthFilter implements Filter {
	private Properties prop = null;
	private InputStream input=null;
	private String driver = null;
	private String jdbc_url=null;
	private static final String FIND_ALL = "select * from access";

	@Override
	public void destroy() {
		System.out.println("***destroy AuthFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		  HttpSession session = ((HttpServletRequest) request).getSession();
		  
	    String email = null;
	    String password = null;
		PrintWriter out = response.getWriter();
		try {
			email = request.getParameter("username");
			password = request.getParameter("password");
			if (session.getAttribute("login")!=null || email!=null && password!=null && checkInDB(email,password)) {
				if(session.getAttribute("login")==null) {
				  session.setAttribute("login", "LOGIN");
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

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("***intit AuthFilter");
		prop = new Properties();
		try {
		    input = getClass().getResourceAsStream("file.properties");
			prop.load(input);
			driver = prop.getProperty("driver_derby");
			jdbc_url = prop.getProperty("jdbc_url");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean checkInDB(String email,String password) {
		Connection connection = null;
		Statement statement =null;
			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(jdbc_url);
				statement = connection.createStatement();
				ResultSet resultSet= statement.executeQuery(FIND_ALL);
				String result1 = null;
				String result2 = null;
				while(resultSet.next()) {
					result1 = resultSet.getString(1);
					result2 = resultSet.getString(2);
					if(result1!=null && result2!=null && result1.equals(email) && result2.equals(password)) {
						return true;
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		return false;
	}

}
