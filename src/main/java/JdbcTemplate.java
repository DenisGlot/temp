import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import hash.Hashing;
import mail.ejb.MailEJB;

public class JdbcTemplate {
	//Specific query for calculator
	private static final String FIND_WHERE = "select 'OK' from ACCESS where email = ? and password = ?";
	
	final Logger logger = Logger.getLogger(JdbcTemplate.class);
	
	private String driver = null;
	private String jdbc_url = null;
	
	private Connection con = null;
	private Statement statement=null;
    private ResultSetHandler<Object[][]> h;
    
    private static boolean tableIsCreated;
    
	public JdbcTemplate() {
		init();
		connectToDataBase();
	}

	public boolean executeDDL(String myQuery) {
	 	 try {
			return statement.execute(myQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	 	 return false;
	}
	/**
	 * This method returns first line yet
	 * @param string
	 * @return
	 */
	public Object[] executeSelect(String myQuery) {
		QueryRunner run = new QueryRunner();
		try {
		return	run.query(con, myQuery, h);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * Declaring String parameteres jdbc_url
	 * and driver from Property 
	 */
	private void init() {
		Properties prop = new Properties();
		InputStream input = getClass().getResourceAsStream("/file.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		driver = prop.getProperty("driver_derby");
		jdbc_url = prop.getProperty("jdbc_url");
		initResultSetHandler();
		
	}
	/**
	 * Create a ResultSetHandler implementation to convert the first row into an Object[].
	 * I don't know how to count rows so used 100
	 */
	private void initResultSetHandler() {
		h = new ResultSetHandler<Object[][]>() {
		    public Object[][] handle(ResultSet rs) throws SQLException {
		        ResultSetMetaData meta = rs.getMetaData();
		        int cols = meta.getColumnCount();
		        //Here
		        Object[][] result = new Object[cols][100];
		        //
                for(int i = 0;rs.next();i++) {
                	for(int j = 0;j<cols; j++) {
                		result[j][i]=rs.getObject(j+1);
                }
		    }
                return result;
		}
	};
	}
    /**
     * Making a working statement in constructor	
     * @return
     */
	private void connectToDataBase() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(jdbc_url);
			statement=con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		createTableACCESS();
	}
	/**
	 * It's table where stored passwords and emails 
	 */
	private void createTableACCESS() {
		// Because i don't know how to check on existing tables. So i wrote down a try
					// catch
					// It will work only once on deployment
					if (!tableIsCreated) {
	                        executeDDL("create table ACCESS (email varchar(64), password varchar(64))");						
							if (logger.isDebugEnabled()) {
								logger.debug("table ACCESS was created");
							}
							String admin = Hashing.sha1("admin");
							String iliya = Hashing.sha1("123456");
							executeDDL("insert into ACCESS values ('admin','" + admin + "')");
							executeDDL("insert into ACCESS values ('iliya','" + iliya + "')");
							executeDDL("insert into ACCESS values ('denis','" + iliya + "')");
							tableIsCreated = true;
					}
	}
	
	/**
	 * specific method for calculator
	 * @param email
	 * @param password
	 */
	public void saveInDataBase(String email, String password) {
		  try {
			statement.execute("insert into ACCESS values ('" + email + "','" + Hashing.sha1(password) + "')");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	} 
	/**
	 * specific method for calculator
	 * @param email
	 * @param password
	 */
	public boolean checkInDataBase(String email,String password) {
		String password2 = Hashing.sha1(password);
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(FIND_WHERE);
			ps.setString(1, email);
			ps.setString(2, password2);
			rs = ps.executeQuery();
			if(rs.next()==false) {
				return false;
			}
			String result = rs.getString(1);
			if(result!=null && result.equals("OK")) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
