import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * This class was for creating Derby DataBase myDB
 * @author Denis
 *
 */
public class CreateDB {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:myDB;create=true";

	public static void main(String[] args) {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(JDBC_URL);
			connection.createStatement().execute("create table access(email varchar(45), password varchar(45))");
			connection.createStatement().execute("insert into access values ('admin','admin')");
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
				} catch (SQLException e) {
					System.err.println("Could not close the connection!");
					e.printStackTrace();
				}
			}
		}

	}
}
