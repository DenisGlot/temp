package dao.factory;

import org.apache.log4j.Logger;

import dao.DAO;
import dao.UserDAO;

public class DAOFactory {

	final Logger logger = Logger.getLogger(DAOFactory.class);
	
	public DAO getDAO(EntityName name) {
		if(name == EntityName.USER) {
		return new UserDAO();
		} else {
			logger.error("There is not DAO found");
			return null;
		}
	}
}
