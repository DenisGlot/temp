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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.derby.tools.sysinfo;
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
	
	private final ResultSetHandler<List<Object[]>> rsHandlerForList = initResultSetHandlerWithList();

	private final ResultSetHandler<Object[]> rsHandlerForOneRow = initResultSetHandlerWithOne();
	
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
	@Deprecated
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
	@Deprecated
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
	
	public List<Object[]> execSelect(String myQuery) {
		Connection con = connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, rsHandlerForList);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return null;

	}
	
	public List<Object[]> execPreparedSelect(String myQuery, Object ... param ) {
		Connection con = connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, rsHandlerForList, param);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return null;

	}
	
	/**
	 * It was created for method in NewDAO.findById 
	 * @param myQuery
	 * @return
	 */
	public Object[] execSelectForOneRow(String myQuery) {
		Connection con = connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, rsHandlerForOneRow);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return null;
	}
	
	/**
	 * It was created for method in NewDAO.findByCriteria 
	 * @param myQuery
	 * @return
	 */
	public Object[] execPreparedSelectForOneRow(String myQuery, Object ... param ) {
		Connection con = connectToDataBase();
		QueryRunner run = new QueryRunner();
		try {
			return run.query(con, myQuery, rsHandlerForOneRow, param);
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
	 * Do not use it!
	 * Create a ResultSetHandler implementation to convert the first row into an
	 * Object[]. ResultSet can not count rows so I used 100.
	 * @return 
	 */
	@Deprecated
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
	
	/**
	 * I found fabulous error of jdk or jvm  
	 * It works in methos below 
	 * All i needed to do is put Object[] row inside loop 
	 * @return
	 */
	private ResultSetHandler<List<Object[]>> initResultSetHandlerWithErrorOfJDK() {
		return new ResultSetHandler<List<Object[]>>() {
			public List<Object[]> handle(ResultSet rs) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				List<Object[]> result = new ArrayList<Object[]>();
				//This is a row array 
				Object[] row = new Object[cols];
				System.out.println("It's while");
				while(rs.next()) {
					for (int j = 0; j < cols; j++) {
						row[j] = rs.getObject(j + 1);
					}
					for(Object o : row) {
						System.out.print(o.toString() + " ");
					}
					System.out.println();
					result.add(row);
				}
				System.out.println("It's list");
				result.forEach((i)->{
					for(Object o : i) {
						System.out.print(o.toString() + " ");
					}
					System.out.println();
				});
				meta = null;
				row = null;
				return result;
			}
		};
	}
	
	/**
	 * That's the new way with ArrayList<Object[]>
	 * @return
	 */
	private ResultSetHandler<List<Object[]>> initResultSetHandlerWithList() {
		return new ResultSetHandler<List<Object[]>>() {
			public List<Object[]> handle(ResultSet rs) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				List<Object[]> result = new ArrayList<Object[]>();
				while(rs.next()) {
					//This is a row array
					Object[] row = new Object[cols];// have to do this 
					for (int j = 0; j < cols; j++) {
						row[j] = rs.getObject(j + 1);
					}
					result.add(row);
					row = null;
				}
				meta = null;
				return result;
			}
		};
	}
	
	/**
	 * It returns just one row for finById,and findByCriteria.
	 * WARNING!!! : And even if more than one row it returns the first one.
	 * @return
	 */
	private ResultSetHandler<Object[]> initResultSetHandlerWithOne() {
		return new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				//This is a row array 
				Object[] result = new Object[cols];
				rs.next();
				for (int j = 0; j < cols; j++) {
					result[j] = rs.getObject(j + 1);
				}
				meta = null;
				return result;
			}
		};
	}
}
