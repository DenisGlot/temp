import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import hash.Hashing;
/**
 * It's the base for creating database
 * For real place where database will be creating look for jdbc.JDBCTemplate#createTables()
 * This class was for creating Derby DataBase myDB
 * Currently, Application doesn't need it
 * @author Denis
 *
 */
public class CreateDB {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:myDB;create=true";

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(JDBC_URL);
			statement=connection.createStatement();
//			//ACCESS for Users
//			executeDDL("create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),firstname varchar(45), lastname varchar(45), dateofbirth timestamp, address varchar(45), email varchar(64) not null, password varchar(64) not null, groupid integer not null, CONSTRAINT primary_key PRIMARY KEY (id))");
//			executeDDL("insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('admin','adminof','" + new Date(12312325325l) + "','Street','admin','" + Hashing.sha1("admin") + "',1)");
//			executeDDL("insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('Iliya','hash','" + new Date(123123253252342342l) + "','Net','iliya','" + Hashing.sha1("123456") + "',1)");
//			executeDDL("insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('Denis','hash','" + new Date(12312325325234l) + "','Street','denis','" + Hashing.sha1("123456") + "',1)");
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
		} catch (ClassNotFoundException e) {
			System.err.println("Driver for derby not found!!!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Could not get connected to derby or insert into table!");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
					statement.close();
				} catch (SQLException e) {
					System.err.println("Could not close the connection!");
					e.printStackTrace();
				}
			}
		}

	}
}
