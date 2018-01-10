package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import exceptions.NotDeclaredConnection;
import hash.Hashing;
import id_counter.OrderIdCounter;
/**
 * Never forget that here i used the array with 100 rows in initResultSetHandler
 * So if database will grow you must increase the amount of rows
 * @author Denis
 *
 */
public class JdbcTemplate {

	private final Logger logger = Logger.getLogger(JdbcTemplate.class);

	private final String driver = initDriver();
	private final String jdbc_url = initJdbcUrl();
	
	private final ResultSetHandler<Object[][]> h = initResultSetHandler();

	public JdbcTemplate() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Declares connection
	 * Be careful! Do not use it without method closeConnection
	 * It will be use in SpecJdbcTemplate which is in the same package
	 * @return
	 * @throws RuneTime exception 
	 */
	 Connection connectToDataBase() {
		try {
			return DriverManager.getConnection(jdbc_url);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new NotDeclaredConnection();
		}
	}
	/**
	 * It will be use in SpecJdbcTemplate which is in the same package
	 */
	void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean executeDDL(String myQuery) {
		Connection con = connectToDataBase();
		try(Statement statement = con.createStatement()) {
		     int res = statement.executeUpdate(myQuery);
		     if(res>0) {
		       return true;
		     } else {
		       return false; 
		     }
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			closeConnection(con);
		}
		return false;
	}
	
	public boolean executePreparedDDLwithTimestamp(String myQuery,Timestamp time ) {
		Connection con = connectToDataBase();
		try(PreparedStatement ps = con.prepareStatement(myQuery)){
		      	ps.setObject(1, time);
		      	ps.executeUpdate();
		      	return true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		} finally {
			closeConnection(con);
		}
		
	}

	/**
	 * This method returns array of arrays 
	 * 
	 * @param string
	 * @return
	 */
	public Object[][] executeSelect(String myQuery) {
		Connection con = connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, h);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(con);
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
		Connection con = connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, h, param);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return null;

	}

	/**
	 * Declaring String parameters jdbc_url and driver from Property
	 */
	private String initDriver() {
		Properties prop = new Properties();
		try(InputStream input = getClass().getResourceAsStream("/file.properties");) {
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return prop.getProperty("driver_derby");
	}
	
	private String initJdbcUrl() {
		Properties prop = new Properties();
		try(InputStream input = getClass().getResourceAsStream("/file.properties");) {
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return prop.getProperty("jdbc_url");
	}

	/**
	 * Create a ResultSetHandler implementation to convert the first row into an
	 * Object[]. I don't know how to count rows so I used 100
	 * @return 
	 */
	private ResultSetHandler<Object[][]> initResultSetHandler() {
		return new ResultSetHandler<Object[][]>() {
			public Object[][] handle(ResultSet rs) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				// Here
				Object[][] result = new Object[100][cols];
				//
				for (int i = 0; rs.next(); i++) {
					for (int j = 0; j < cols; j++) {
						result[i][j] = rs.getObject(j + 1);
					}
				}
				return result;
			}
		};
	}
}
