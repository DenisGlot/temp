package jdbc;

import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import hash.Hashing;
import id_counter.OrderIdCounter;

/**
 * This class is created just for creating database
 * @author Denis
 *
 */
public class DataBaseCreator {
	
    private final Logger logger = Logger.getLogger(DataBaseCreator.class);
	
	private JdbcTemplate jt;
	
	private static boolean tablesAreNotCreated = true;
	
	public DataBaseCreator(JdbcTemplate jt) {
		this.jt= jt;
//		dropTables();
		createTables();
	}
	
	/**
	 * It is method which creates whole database
	 * derby note: after type you can write either "not null" or nothing
	 */
	private void createTables() {
		// Because i don't know how to check on existing tables. So i wrote down a try
		// catch
		// It will work only once on deployment
		jt.connectToDataBase();
		DatabaseMetaData metaData = null;
		//Declares metaData
		try {
			metaData = jt.getConnection().getMetaData();
			metaData.getTables(null, null, "ACCESS", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (tablesAreNotCreated) {
			
			// ACCESS for Users
			if(tableNotExist(metaData, "ACCESS")) {
				jt.executeDDL(
					"create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),firstname varchar(45), lastname varchar(45), dateofbirth timestamp, address varchar(45),phone varchar(45) not null, email varchar(64), password varchar(64) not null, groupid integer not null, CONSTRAINT primary_key PRIMARY KEY (id))");
				jt.executePreparedDDLwithTimestamp(
					"insert into ACCESS(firstname,lastname,dateofbirth,address,phone,email,password,groupid) values ('admin','adminof',?,'Street','+7-777-777-77-77','admin','"
							+ Hashing.sha1("admin") + "',1)",
					new Timestamp(123213412l));
				jt.executePreparedDDLwithTimestamp(
					"insert into ACCESS(firstname,lastname,dateofbirth,address,phone,email,password,groupid) values ('Iliya','hash',?,'Net','+7-777-777-77-77','iliya','"
							+ Hashing.sha1("123456") + "',1)",
					new Timestamp(123213412l));
				jt.executePreparedDDLwithTimestamp(
					"insert into ACCESS(firstname,lastname,dateofbirth,address,phone,email,password,groupid) values ('Denis','hash',?,'Street','+7-777-777-77-77','denis','"
							+ Hashing.sha1("123456") + "',1)",
					new Timestamp(123213412l));
			logger.debug("ACCESS was created");
			}
			// ROLES
			if(tableNotExist(metaData, "ROLES")) {
				jt.executeDDL("create table ROLES (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), role varchar(64) not null)");
				jt.executeDDL("insert into ROLES(role) values ('admin')");
				jt.executeDDL("insert into ROLES(role) values ('user')");
			logger.debug("ROLES was created");
			}
			// PRODUCTS
			if(tableNotExist(metaData, "PRODUCTS")) {
				jt.executeDDL(
					"create table PRODUCTS (productid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), categoryid integer, suplierid integer not null, price bigint, quantity integer, name varchar(45) not null, description varchar(255),urlofimg varchar(255))");
				jt.executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,1,99,100,'JVM','the best assembly','https://i.ytimg.com/vi/G1ubVOl9IBw/maxresdefault.jpg')");
				jt.executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,2,10000,50,'laptop HP190','The super fast processor Intel - 9213, RAM - 8192MB','https://cnet3.cbsistatic.com/img/ttt0dUBA6U9m043Fj1uEui4-6X8=/770x578/2014/09/07/30e56559-a526-47f0-9068-1ca1958ff73d/hp-streammodern-silver.jpg')");
				jt.executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,1,100,100,'Windows 10','the best assembly','https://www.windowscentral.com/sites/wpcentral.com/files/styles/larger/public/field/image/2017/03/cloudwallpaper.jpg?itok=VC2ajDrI')");
				jt.executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,2,2000,20,'iPhone X','It was announced on September 12, 2017, alongside the iPhone 8 and iPhone 8 Plus at the Steve Jobs Theater in the Apple Park campus. The phone was released on November 3, 2017','http://bm.img.com.ua/berlin/storage/orig/3/ae/1d734f3a21d07121c9c3996a18884ae3.jpg')");
				jt.executeDDL(
					"insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description,urlofimg) values(1,2,99,100,'Fire TV Edition','Element 43-Inch 4K Ultra HD Smart LED TV - Fire TV Edition','https://assets.pcmag.com/media/images/560165-amazon-fire-tv.jpg?thumb=y&width=980&height=416')");
			logger.debug("PRODUCTS was created");
			}
			
			// ORDERS
			if(tableNotExist(metaData, "ORDERS")) {
				jt.executeDDL(
					"create table ORDERS (orderid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userid integer not null,courierid integer, orderdate timestamp, shippereddate timestamp)");
				jt.executeDDL("insert into ORDERS(userid,courierid,orderdate,shippereddate) values(1,1,'" + new Timestamp(1200000000l)
					+ "', '" + new Timestamp(435346453324l) + "')");
			OrderIdCounter.orderid.incrementAndGet();
			
			logger.debug("ORDERS was created");
			}
			
			// OrderDetails
			if(tableNotExist(metaData, "ORDERDETAILS")) {
				jt.executeDDL(
					"create table ORDERDETAILS (orderdetailsid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),orderid integer ,productid integer, price bigint, quantity integer, discount decimal(3,2))");
				jt.executeDDL("insert into ORDERDETAILS(orderid,productid,price,quantity,discount) values(1,1,100500,1,"
					+ new BigDecimal("0.25") + " )");
			logger.debug("ORDERDETAILS was created");
			} 
			// SUPLIERS
			if(tableNotExist(metaData, "SUPLIERS")) {
				jt.executeDDL(
					"create table SUPLIERS(suplierid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
				jt.executeDDL("insert into SUPLIERS(name,description) values('Oracle','The best vendor ever')");
				jt.executeDDL("insert into SUPLIERS(name,description) values('HP','The internation vendor of hardware')");
				jt.executeDDL("insert into SUPLIERS(name,description) values('Microsoft','The internation vendor of OS Windows and other stuff')");
			logger.debug("SUPLIERS was created");
			}
			// CATEGORIES
			if(tableNotExist(metaData, "CATEGORIES")) {
				jt.executeDDL(
					"create table CATEGORIES(categoryid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
				jt.executeDDL("insert into CATEGORIES(name,description) values('Software','OS,JVM and so on')");
				jt.executeDDL("insert into CATEGORIES(name,description) values('Hardware','Laptops,smartphones and so on')");
			logger.debug("CATEGORIES was created");
			}
			//COURIERS
			if(tableNotExist(metaData, "COURIERS")) {
				jt.executeDDL(
						"create table COURIERS (courierid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), firstname varchar(45) not null, lastname varchar(45) not null, hiredate timestamp, birth timestamp)");
				jt.executeDDL("insert into COURIERS(firstname,lastname,hiredate,birth) values('Vasya','Pupkin','" + new Timestamp(1200000000l)
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
		jt.executeDDL("drop table ACCESS");
		jt.executeDDL("drop table ROLES");
		jt.executeDDL("drop table PRODUCTS");
		jt.executeDDL("drop table SUPLIERS");
		jt.executeDDL("drop table ORDERS");
		jt.executeDDL("drop table ORDERDETAILS");
		jt.executeDDL("drop table CATEGORIES");
		jt.executeDDL("drop table COURIERS");
	}

}
