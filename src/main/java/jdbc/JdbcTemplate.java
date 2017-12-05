package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import hash.Hashing;

public class JdbcTemplate {
	// Specific query for calculator
	protected static final String FIND_WHERE = "select 'OK' from ACCESS where email = ? and password = ?";

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
		createTables();
	}
	
	/**
	 * Declares connection
	 * 
	 * @return
	 */
	protected void connectToDataBase() {
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
		     statement.execute(myQuery);
		     return true;
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
		if (tablesAreNotCreated) {
            //ACCESS for Users
			executeDDL("create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),firstname varchar(45), lastname varchar(45), dateofbirth timestamp, address varchar(45), email varchar(64) not null, password varchar(64) not null, groupid integer not null, CONSTRAINT primary_key PRIMARY KEY (id))");
			executeDDL("insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('admin','adminof','" + new Timestamp(12312325325l) + "','Street','admin','" + Hashing.sha1("admin") + "',1)");
			executeDDL("insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('Iliya','hash','" + new Timestamp(123123253252342342l) + "','Net','iliya','" + Hashing.sha1("123456") + "',1)");
			executeDDL("insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('Denis','hash','" + new Timestamp(12312325325234l) + "','Street','denis','" + Hashing.sha1("123456") + "',1)");
//            //ROLES
//            executeDDL("create table ROLES (id integer not null, role varchar(64) not null)");
//            executeDDL("insert into ROLES(id, role) values (1,'admin')");
//            executeDDL("insert into ROLES(id, role) values (2,'user')");
//            //PRODUCTS
//            executeDDL("create table PRODUCTS (productid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), categoryid integer, suplierid integer not null, price bigint, quantity integer, name varchar(45) not null, description varchar(255))");
//            executeDDL("insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description) values(1,1,100500,1,'machine','makes super clean house')");
//            //ORDERS
//            executeDDL("create table ORDERS (orderid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userid integer, orderdate timestamp, shippereddate timestamp)");
//            executeDDL("insert into ORDERS(userid,orderdate,shippereddate) values(1,'" + new Timestamp(1200000000l) + "', '" + new Timestamp(435346453324l) + "')");
//            //OrderDetails
//            executeDDL("create table ORDERDETAILS (orderdetailsid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),orderid integer ,productid integer, price bigint, quantity integer, discount decimal(3,2))");
//            executeDDL("insert into ORDERDETAILS(orderid,productid,price,quantity,discount) values(1,1,100500,1," + 5.24f +  " )");
//            //SUPLIERS
//            executeDDL("create table SUPLIERS(suplierid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
//            executeDDL("insert into SUPLIERS(name,description) values('Oracle','The best vendor ever, ms sucks')");
//            //CATEGORIES
//            executeDDL("create table CATEGORIES(categoryid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
//            executeDDL("insert into SUPLIERS(name,description) values('Java','The best language ever, c# sucks')");
			tablesAreNotCreated = false;
		}
	}
	
	
	
}
