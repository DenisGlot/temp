import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
			statement.execute("create table ACCESS (id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),email varchar(64) not null, password varchar(64) not null, groupid integer not null, CONSTRAINT primary_key PRIMARY KEY (id))");
			statement.execute("insert into ACCESS(email,password,groupid) values ('admin','" + Hashing.sha1("admin") + "',1)");
            statement.execute("insert into ACCESS(email,password,groupid) values ('iliya','" + Hashing.sha1("123456") + "',1)");
            statement.execute("insert into ACCESS(email,password,groupid) values ('denis','" + Hashing.sha1("123456") + "',1)");
            statement.execute("create table ROLES (id integer not null, role varchar(64) not null)");
            statement.execute("insert into ROLES(id, role) values (1,'admin')");
            statement.execute("insert into ROLES(id, role) values (2,'user')");
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
