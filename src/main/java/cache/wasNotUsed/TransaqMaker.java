package cache.wasNotUsed;

import java.lang.reflect.Method;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import jdbc.JdbcTemplate;

public class TransaqMaker implements Transaction{
	
	private final Logger logger = Logger.getLogger(TransaqMaker.class);
	
	private JdbcTemplate jt = null;
	
	Method connect;
	
	public TransaqMaker() {
		jt = new JdbcTemplate();
	} 

	@Override
	public void begin() {
		jt.connectToDataBase();
		try {
			jt.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public void commit() {
		try {
			jt.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			jt.closeConnection();
		}
		
	}

	@Override
	public void rollback() {
		try {
			jt.getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			jt.closeConnection();
		}
	}
	
	

}
