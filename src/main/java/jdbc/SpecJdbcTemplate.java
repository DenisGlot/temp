package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.log4j.Logger;

import dao.entity.User;
import hash.Hashing;

/**
 * This class is extension for JdbcTemplate,
 * which consist the right method for authorization
 * @author Denis
 *
 */
public class SpecJdbcTemplate {
	
	private final Logger logger = Logger.getLogger(SpecJdbcTemplate.class);
	
	//ACCESS is the table where stored all information about users
	//For email
	private static final String FIND_WHERE_EMAIL = "select 'OK' from ACCESS where email = ? and password = ?";
	//For phone
	private static final String FIND_WHERE_PHONE = "select 'OK' from ACCESS where phone = ? and password = ?";
	
	private JdbcTemplate jt;

	public SpecJdbcTemplate(JdbcTemplate jt) {
		this.jt = jt;
	}

	/**
	 * Method for authorization.
	 * It's thread-safe because  here is just one outer field
	 * which is jt of class JdbcTemplate
	 * 
	 * @param email
	 * @param password
	 */
	public boolean checkInDataBase(User user) {
		String result;
		synchronized (jt) {
			jt.connectToDataBase();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				//For the phone authorization
				ps = jt.con.prepareStatement(FIND_WHERE_PHONE);
				ps.setString(1, user.getPhone());
				ps.setString(2, user.getPassword());
				rs = ps.executeQuery();
				if(rs.next()) {
					result = rs.getString(1);
					if(Objects.equals("OK", result)) { return true;}
				}
				//For the email authentication
				ps = jt.con.prepareStatement(FIND_WHERE_EMAIL);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getPassword());
				rs = ps.executeQuery();
				if (rs.next() == false) {
					logger.debug("resultset was null");
					return false;
				}
				result = rs.getString(1);
				if (Objects.equals("OK", result)) {
					return true;
				} else {
					logger.debug("user was not ok but was '" + result + "'");
					return false;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					jt.closeConnection();
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
}
