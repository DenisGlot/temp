package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import hash.Hashing;

public class JdbcTemplate {
	// Specific query for calculator
	private static final String FIND_WHERE = "select 'OK' from ACCESS where email = ? and password = ?";

	final Logger logger = Logger.getLogger(JdbcTemplate.class);

	private String driver = null;
	private String jdbc_url = null;

	private Connection con = null;
	private ResultSetHandler<Object[][]> h;

	private static boolean tableIsCreated;

	public JdbcTemplate() {
		initFields();
		initResultSetHandler();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
//		createTableACCESS();
	}
	
	/**
	 * Declares connection
	 * 
	 * @return
	 */
	private void connectToDataBase() {
		try {
			con = DriverManager.getConnection(jdbc_url);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean executeDDL(String myQuery) {
		connectToDataBase();
		try(Statement statement = con.createStatement()) {
			return statement.execute(myQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return false;
	}

	/**
	 * This method returns array of arrays 
	 * 
	 * @param string
	 * @return
	 */
	public Object[][] executeSelect(String myQuery) {
		connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, h);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;

	}
	/**
	 * It executes query with bind parameters
	 * @param myQuery
	 * @param param
	 * @return
	 */
	public Object[][] executePreparedSelect(String myQuery, Object ... param ) {
		connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, h, param);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;

	}

	/**
	 * Declaring String parameters jdbc_url and driver from Property
	 */
	private void initFields() {
		Properties prop = new Properties();
		try(InputStream input = getClass().getResourceAsStream("/file.properties");) {
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		driver = prop.getProperty("driver_derby");
		jdbc_url = prop.getProperty("jdbc_url");
	}

	/**
	 * Create a ResultSetHandler implementation to convert the first row into an
	 * Object[]. I don't know how to count rows so I used 100
	 */
	private void initResultSetHandler() {
		h = new ResultSetHandler<Object[][]>() {
			public Object[][] handle(ResultSet rs) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				// Here
				Object[][] result = new Object[100][cols];
				//
				for (int i = 0; rs.next(); i++) {
					for (int j = 0; j < cols; j++) {
						result[i][j] = rs.getObject(j + 1);
						// If first element equals null then whole array is null
						if(i==0 && (j==0 || j == 1 || j==2) ) {
							if(result[i][j]==null) {
								return null;
							}
						}
						
					}
				}
				return result;
			}
		};
	}


	/**
	 * It's table where stored passwords and emails
	 */
	private void createTableACCESS() {
		// Because i don't know how to check on existing tables. So i wrote down a try
		// catch
		// It will work only once on deployment
		if (!tableIsCreated) {
			executeDDL(
					"create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),email varchar(64), password varchar(64), CONSTRAINT primary_key PRIMARY KEY (id))");
			String admin = Hashing.sha1("admin");
			String iliya = Hashing.sha1("123456");
			executeDDL("insert into ACCESS(email,password) values ('admin','" + admin + "')");
			executeDDL("insert into ACCESS(email,password) values ('iliya','" + iliya + "')");
			executeDDL("insert into ACCESS(email,password) values ('denis','" + iliya + "')");
			tableIsCreated = true;
		}
	}
	
	
	// ********These methods below i don't use since DAO********
	
	
	/**
	 * specific method for calculator
	 * 
	 * @param email
	 * @param password
	 */
	public boolean saveInDataBase(String email, String password) {
        connectToDataBase();
		try(Statement statement = con.createStatement()) {
			statement.execute(
					"insert into ACCESS(email,password) values ('" + email + "','" + Hashing.sha1(password) + "')");
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * specific method for calculator
	 * 
	 * @param email
	 * @param password
	 */
	public boolean checkInDataBase(String email, String password) {
        connectToDataBase();
		String password2 = Hashing.sha1(password);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(FIND_WHERE);
			ps.setString(1, email);
			ps.setString(2, password2);
			rs = ps.executeQuery();
			if (rs.next() == false) {
				return false;
			}
			String result = rs.getString(1);
			if (result != null && result.equals("OK")) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}
}
