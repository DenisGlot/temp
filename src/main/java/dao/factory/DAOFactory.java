package dao.factory;

import org.apache.log4j.Logger;

import dao.CategoryDAO;
import dao.OrderDAO;
import dao.OrderDetailsDAO;
import dao.ProductDAO;
import dao.RoleDAO;
import dao.SuplierDAO;
import dao.UserDAO;
import dao.superb.DAO;

public class DAOFactory {

	final Logger logger = Logger.getLogger(DAOFactory.class);
	
	public DAO getDAO(EntityName name) {
		switch(name) {
		case USER :
			return new UserDAO();
		case ROLE :
			return new RoleDAO();
		case ORDER:
			return new OrderDAO();
		case PRODUCT :
			return new ProductDAO();
		case CATEGORY:
			return new CategoryDAO();
		case SUPLIER :
			return new SuplierDAO();
		case ORDERDETAIL:
			return new OrderDetailsDAO();
		default:
			logger.error("There is not DAO found");
			return null;
		}
	}
}
