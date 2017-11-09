package calculator;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

public class ChekcDB {
	
	private static final String FIND_ALL = "select * from ACCESS";

	@Test
	public void test() {
		Properties prop = null;
	    InputStream input=null;
		prop = new Properties();
		String driver = null;
		String jdbc_url=null;
		try {
			input = getClass().getResourceAsStream("file.properties");
			prop.load(input);
			driver = prop.getProperty("driver_derby");
			jdbc_url = prop.getProperty("jdbc_url");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		Statement statement =null;
		ResultSet resultSet=null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(jdbc_url);
			statement = connection.createStatement();
     		statement.execute("insert into access values ('iliya','123456')");
     		statement.execute("insert into access values ('denis','123456')");
			resultSet= statement.executeQuery(FIND_ALL);
			while(resultSet.next()) {
			System.out.println(resultSet.getString(1) + "   " + resultSet.getString(2));	
			}
		} catch (ClassNotFoundException | SQLException e) {
			assertThat(e.getMessage(), is("Index: 0, Size: 0"));
			e.printStackTrace();
	
		} finally {
			
				try {
					if (connection != null)
					connection.close();
					if (statement != null)
					statement.close();
					if (resultSet != null)
				    resultSet.close();
				} catch (SQLException e) {
					System.err.println("Could not close the connection!");
					e.printStackTrace();
				}
			
		}
	}
	
	

}
