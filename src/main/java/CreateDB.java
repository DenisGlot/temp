import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

import hash.Hashing;
/**
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
			//ACCESS for Users
			statement.execute("create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),email varchar(64) not null, password varchar(64) not null, groupid integer not null, CONSTRAINT primary_key PRIMARY KEY (id))");
			statement.execute("insert into ACCESS(email,password,groupid) values ('admin','" + Hashing.sha1("admin") + "',1)");
            statement.execute("insert into ACCESS(email,password,groupid) values ('iliya','" + Hashing.sha1("123456") + "',1)");
            statement.execute("insert into ACCESS(email,password,groupid) values ('denis','" + Hashing.sha1("123456") + "',1)");
            //ROLES
            statement.execute("create table ROLES (id integer not null, role varchar(64) not null)");
            statement.execute("insert into ROLES(id, role) values (1,'admin')");
            statement.execute("insert into ROLES(id, role) values (2,'user')");
            //PRODUCTS
            statement.execute("create table PRODUCTS (productid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), categoryid integer, suplierid integer not null, price bigint, quantity integer, name varchar(45) not null, description varchar(255))");
            statement.execute("insert into PRODUCTS(categoryid, suplierid,price,quantity,name,description) values(1,1,100500,1,'machine','makes super clean house')");
            //ORDERS
            statement.execute("create table ORDERS (orderid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userid integer, orderdate timestamp, shippereddate timestamp)");
            statement.execute("insert into ORDERS(userid,orderdate,shippereddate) values(1,'" + new Timestamp(1200000000l) + "', '" + new Timestamp(435346453324l) + "')");
            //OrderDetails
            statement.execute("create table ORDERDETAILS (orderdetailsid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),orderid integer ,productid integer, price bigint, quantity integer, discount decimal(3,2))");
            statement.execute("insert into ORDERDETAILS(orderid,productid,price,quantity,discount) values(1,1,100500,1," + 5.24f +  " )");
            //SUPLIERS
            statement.execute("create table SUPLIERS(suplierid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
            statement.execute("insert into SUPLIERS(name,description) values('Oracle','The best vendor ever, ms sucks')");
            //CATEGORIES
            statement.execute("create table CATEGORIES(categoryid integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(45) not null, description varchar(255))");
            statement.execute("insert into SUPLIERS(name,description) values('Java','The best language ever, c# sucks')");
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
