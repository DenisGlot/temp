package dao.factory;

import org.apache.log4j.Logger;

import dao.DAO;
import dao.RoleDAO;
import dao.UserDAO;

public class DAOFactory {

	final Logger logger = Logger.getLogger(DAOFactory.class);
	
	public DAO getDAO(EntityName name) {
		switch(name) {
		case USER :
			return new UserDAO();
		case ROLE :
			return new RoleDAO();
		default:
			logger.error("There is not DAO found");
			return null;
		}
	}
}
