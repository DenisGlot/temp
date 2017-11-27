package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hash.Hashing;

/**
 * Utility class
 * @author Denis
 *
 */
public class SpecJdbcTemplate extends JdbcTemplate {

	public SpecJdbcTemplate() {
		super();
	}
	/**
	 * specific method for calculator
	 * 
	 * @param email
	 * @param password
	 */
	public boolean saveInDataBase(String email, String password) {
        connectToDataBase();
		try(Statement statement = con.createStatement()) {
			statement.execute(
					"insert into ACCESS(email,password) values ('" + email + "','" + Hashing.sha1(password) + "')");
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * specific method for calculator
	 * 
	 * @param email
	 * @param password
	 */
	public boolean checkInDataBase(String email, String password) {
        connectToDataBase();
		String password2 = Hashing.sha1(password);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(FIND_WHERE);
			ps.setString(1, email);
			ps.setString(2, password2);
			rs = ps.executeQuery();
			if (rs.next() == false) {
				return false;
			}
			String result = rs.getString(1);
			if (result != null && result.equals("OK")) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}
}
