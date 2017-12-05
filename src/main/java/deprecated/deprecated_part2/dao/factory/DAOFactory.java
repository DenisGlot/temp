package deprecated.deprecated_part2.dao.factory;

import org.apache.log4j.Logger;

import dao.superb.DAO;
import deprecated.deprecated_part2.dao.CategoryDAO;
import deprecated.deprecated_part2.dao.OrderDAO;
import deprecated.deprecated_part2.dao.OrderDetailsDAO;
import deprecated.deprecated_part2.dao.ProductDAO;
import deprecated.deprecated_part2.dao.RoleDAO;
import deprecated.deprecated_part2.dao.SuplierDAO;
import deprecated.deprecated_part2.dao.UserDAO;

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
