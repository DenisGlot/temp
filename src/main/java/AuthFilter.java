import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private static final String FIND_ALL = "select * from ACCESS";
	private static final String FIND_WHERE = "select email, password from ACCESS where email = ? and password = ?";

	@Override
	public void destroy() {
		System.out.println("***destroy AuthFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		  HttpSession session = ((HttpServletRequest) request).getSession();
		  System.out.println("*******Session Atribute!! " + session.getAttribute("login"));
		  if(session.getAttribute("login")!=null && session.getAttribute("login").equals("LOGIN")) {
			  System.out.println("I'm in first chain.doFilter()");
			  chain.doFilter(request, response);
		  }
	    String email = null;
	    String password = null;
		PrintWriter out = response.getWriter();
		try {
			email = request.getParameter("email");
			password = request.getParameter("password");
			if (email!=null && password!=null && checkInDB(email,password)) {
				  session.setAttribute("login", "LOGIN");
				  System.out.println("I'm in second chain.doFilter()");
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
		Connection con = null;
		try {
		    input = getClass().getResourceAsStream("file.properties");
			prop.load(input);
			driver = prop.getProperty("driver_derby");
			jdbc_url = prop.getProperty("jdbc_url");
			// Because i don't know how to check on existing tables. So i wrote down a try catch
		    // It will work only once on deployment
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(jdbc_url);
				System.out.println("*******Creating Table******");
			    con.createStatement().execute("create table ACCESS (email varchar(45), password varchar(45))");
			    con.createStatement().execute("insert into ACCESS values ('admin','admin')");
			    con.createStatement().execute("insert into ACCESS values ('iliya','123456')");
			    con.createStatement().execute("insert into ACCESS values ('denis','123456')");
		    } catch (SQLException e){
			    e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean checkInDB(String email,String password) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(jdbc_url);
			ps = con.prepareStatement(FIND_WHERE);
			//set the parameter
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			String result1 = null;
			String result2 = null;
			while(rs.next()) {
				result1 = rs.getString(1);
				result2 = rs.getString(2);
				System.out.println(result1 + "  " + result2);
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
					if (rs != null)
					rs.close();
					if (ps != null)
					ps.close();
					if (con != null)
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

}
