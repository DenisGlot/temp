package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	private final Logger logger = Logger.getLogger(JdbcTemplate.class);
	
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
		synchronized (jt) {
			jt.connectToDataBase();
			String password2 = Hashing.sha1(user.getPassword());
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = jt.con.prepareStatement(JdbcTemplate.FIND_WHERE);
				ps.setString(1, user.getEmail());
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
					jt.con.close();
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
