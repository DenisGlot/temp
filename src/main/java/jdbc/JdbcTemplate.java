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
	
	//ACCESS is the table where stored all information about users
	static final String FIND_WHERE = "select 'OK' from ACCESS where email = ? and password = ?";

	final Logger logger = Logger.getLogger(JdbcTemplate.class);

	private String driver = null;
	private String jdbc_url = null;

	protected Connection con = null;
	
	private ResultSetHandler<Object[][]> h;

	private static boolean tablesAreNotCreated = true;

	public JdbcTemplate() {
		initFields();
		initResultSetHandler();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
//		dropTables();
		createTables();
	}
	
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * Declares connection
	 * Be careful! Do not use it without method closeConnection
	 * I made it public because i need it in class TransaqMaker
	 * @return
	 * @throws RuneTime exception 
	 */
	protected void connectToDataBase() {
		try {
			con = DriverManager.getConnection(jdbc_url);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new NotDeclaredConnection();
		}
	}
	
	protected void closeConnection() {
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
			closeConnection();
		}
		return false;
	}
	
	public boolean executePreparedDDLwithTimestamp(String myQuery,Timestamp time ) {
		connectToDataBase();
		try(PreparedStatement ps = con.prepareStatement(myQuery)){
		      	ps.setObject(1, time);
		      	ps.executeUpdate();
		      	return true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		} finally {
			closeConnection();
		}
		
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
					}
				}
				return result;
			}
		};
	}


	/**
	 * It's table where stored passwords and emails
	 */
	private void createTables() {
		// Because i don't know how to check on existing tables. So i wrote down a try
		// catch
		// It will work only once on deployment
		connectToDataBase();
		DatabaseMetaData metaData = null;
		try {
			metaData = con.getMetaData();
			metaData.getTables(null, null, "ACCESS", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (tablesAreNotCreated) {
			
			// ACCESS for Users
			if(tableNotExist(metaData, "ACCESS")) {
			executeDDL(
					"create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),firstname varchar(45), lastname varchar(45), dateofbirth timestamp, address varchar(45), email varchar(64) not null, password varchar(64) not null, groupid integer not null, CONSTRAINT primary_key PRIMARY KEY (id))");
			executePreparedDDLwithTimestamp(
					"insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('admin','adminof',?,'Street','admin','"
							+ Hashing.sha1("admin") + "',1)",
					new Timestamp(123213412l));
			executePreparedDDLwithTimestamp(
					"insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('Iliya','hash',?,'Net','iliya','"
							+ Hashing.sha1("123456") + "',1)",
					new Timestamp(123213412l));
			executePreparedDDLwithTimestamp(
					"insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('Denis','hash',?,'Street','denis','"
							+ Hashing.sha1("123456") + "',1)",
					new Timestamp(123213412l));
			logger.debug("ACCESS was created");
			}
			// ROLES
			if(tableNotExist(metaData, "ROLES")) {
			executeDDL("create table ROLES (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), role varchar(64) not null)");
			executeDDL("insert into ROLES(role) values ('admin')");
			executeDDL("insert into ROLES(role) values ('user')");
			logger.debug("ROLES was created");
			}
			// PRODUCTS
			if(tableNotExist(metaData, "PRODUCTS")) {
			executeDDL(
					"create table PRODUCTS (productid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), categoryid integer, suplierid integer not null, price bigint, quantity integer, name varchar(45) not null, description varchar(255),urlofimg varchar(255))");
			executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,1,99,100,'JVM','the best assembly','https://i.ytimg.com/vi/G1ubVOl9IBw/maxresdefault.jpg')");
			executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,2,10000,50,'laptop HP190','The super fast processor Intel - 9213, RAM - 8192MB','https://cnet3.cbsistatic.com/img/ttt0dUBA6U9m043Fj1uEui4-6X8=/770x578/2014/09/07/30e56559-a526-47f0-9068-1ca1958ff73d/hp-streammodern-silver.jpg')");
			executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,1,100,100,'Windows 10','the best assembly','https://www.windowscentral.com/sites/wpcentral.com/files/styles/larger/public/field/image/2017/03/cloudwallpaper.jpg?itok=VC2ajDrI')");
			executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,2,2000,20,'iPhone X','It was announced on September 12, 2017, alongside the iPhone 8 and iPhone 8 Plus at the Steve Jobs Theater in the Apple Park campus. The phone was released on November 3, 2017','http://bm.img.com.ua/berlin/storage/orig/3/ae/1d734f3a21d07121c9c3996a18884ae3.jpg')");
			executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,2,99,100,'Fire TV Edition','Element 43-Inch 4K Ultra HD Smart LED TV - Fire TV Edition','https://assets.pcmag.com/media/images/560165-amazon-fire-tv.jpg?thumb=y&width=980&height=416')");
			logger.debug("PRODUCTS was created");
			}
			
			// ORDERS
			if(tableNotExist(metaData, "ORDERS")) {
			executeDDL(
					"create table ORDERS (orderid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userid integer not null,courierid integer, orderdate timestamp, shippereddate timestamp)");
			executeDDL("insert into ORDERS(userid,courierid,orderdate,shippereddate) values(1,1,'" + new Timestamp(1200000000l)
					+ "', '" + new Timestamp(435346453324l) + "')");
			OrderIdCounter.orderid.incrementAndGet();
			
			logger.debug("ORDERS was created");
			}
			
			// OrderDetails
			if(tableNotExist(metaData, "ORDERDETAILS")) {
			executeDDL(
					"create table ORDERDETAILS (orderdetailsid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),orderid integer ,productid integer, price bigint, quantity integer, discount decimal(3,2))");
			executeDDL("insert into ORDERDETAILS(orderid,productid,price,quantity,discount) values(1,1,100500,1,"
					+ new BigDecimal("0.25") + " )");
			logger.debug("ORDERDETAILS was created");
			} 
			// SUPLIERS
			if(tableNotExist(metaData, "SUPLIERS")) {
			executeDDL(
					"create table SUPLIERS(suplierid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
			executeDDL("insert into SUPLIERS(name,description) values('Oracle','The best vendor ever')");
			executeDDL("insert into SUPLIERS(name,description) values('HP','The internation vendor of hardware')");
			executeDDL("insert into SUPLIERS(name,description) values('Microsoft','The internation vendor of OS Windows and other stuff')");
			logger.debug("SUPLIERS was created");
			}
			// CATEGORIES
			if(tableNotExist(metaData, "CATEGORIES")) {
			executeDDL(
					"create table CATEGORIES(categoryid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
			executeDDL("insert into CATEGORIES(name,description) values('Software','OS,JVM and so on')");
			executeDDL("insert into CATEGORIES(name,description) values('Hardware','Laptops,smartphones and so on')");
			logger.debug("CATEGORIES was created");
			}
			//COURIERS
			if(tableNotExist(metaData, "COURIERS")) {
				executeDDL(
						"create table COURIERS (courierid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), firstname varchar(45) not null, lastname varchar(45) not null, hiredate timestamp, birth timestamp)");
				executeDDL("insert into COURIERS(firstname,lastname,hiredate,birth) values('Vasya','Pupkin','" + new Timestamp(1200000000l)
						+ "', '" + new Timestamp(435346453324l) + "')");
				logger.debug("COURIERS was created");
				}
			tablesAreNotCreated = false;
		}

	}
	
	
	/**
	 * Should be called just in createTables 
	 * Because con is declared;
	 * If you want to use then you must declare con by calling cannictToDataBase
	 * @return
	 */
	private boolean tableNotExist(DatabaseMetaData metaData, String tableName) {
		try {
			return !metaData.getTables(null, null, tableName, null).next();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
	}
 
	/**
	 * On case when i need to change database
	 * Because the database id embedded
	 */
	private void dropTables() {
		logger.debug("Droping Tables");
	    executeDDL("drop table ACCESS");
	    executeDDL("drop table ROLES");
	    executeDDL("drop table PRODUCTS");
	    executeDDL("drop table SUPLIERS");
	    executeDDL("drop table ORDERS");
	    executeDDL("drop table ORDERDETAILS");
	    executeDDL("drop table CATEGORIES");
	    executeDDL("drop table CoURIERS");
	}
}
