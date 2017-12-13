package daotest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import dao.MyDAO;
import dao.entity.Category;
import dao.entity.Order;
import dao.entity.OrderDetails;
import dao.entity.Product;
import dao.entity.Role;
import dao.entity.Suplier;
import dao.entity.User;
import hash.Hashing;

/**
 * Does not matter which id you declare as object
 * becuase MyDAO does not consider id as parameter
 * @author Denis
 *
 */
public class MyDAOTest {

	MyDAO<User, Integer> daoUser;
	MyDAO<Role, Integer> daoRole;
	MyDAO<Product,Integer> daoProduct;
	MyDAO<Order,Integer> daoOrder;
	MyDAO<OrderDetails,Integer> daoOrderDetails;
	MyDAO<Suplier,Integer> daoSuplier;
	MyDAO<Category,Integer> daoCategory;
	
	@Before
	public void init() {
		daoUser  = new MyDAO<>(User.class);
		daoRole = new MyDAO<>(Role.class);
		daoProduct = new MyDAO<>(Product.class);
		daoOrder = new MyDAO<>(Order.class);
		daoOrderDetails = new MyDAO<>(OrderDetails.class);
		daoSuplier = new MyDAO<>(Suplier.class);
		daoCategory = new MyDAO<>(Category.class);
 	}
	
	@Test
	public void user() {
		assertEquals(daoUser.findById(1), new User(1,"admin","adminof",new Timestamp(123213412l),"Street","admin",Hashing.sha1("admin"),1));
		assertEquals(daoUser.findByCriteria("email", "admin"), new User(1,"admin","adminof",new Timestamp(123213412l),"Street","admin",Hashing.sha1("admin"),1));
		assertTrue(daoUser.save( new User(1,"sergey","donnow",new Timestamp(1232132312l),"Moscow","sergey",Hashing.sha1("123456"),1)));
		User sergey = daoUser.findByCriteria("email", "sergey");
		assertEquals(sergey, new User(sergey.getId(),"sergey","donnow",new Timestamp(1232132312l),"Moscow","sergey",Hashing.sha1("123456"),1));
		sergey.setAddress("London");
		assertTrue(daoUser.update(sergey));
		assertEquals(daoUser.findById(sergey.getId()),sergey);
		assertTrue(daoUser.delete(sergey));
		
	}

	@Test
	public void role() {
		assertEquals(daoRole.findById(1), new Role(1,"admin"));
		assertEquals(daoRole.findByCriteria("role", "admin"), new Role(1,"admin"));
		assertTrue(daoRole.save(new Role(1,"employee")));
		Role employee = daoRole.findByCriteria("role", "employee");
		assertEquals(employee,new Role(employee.getId(),"employee"));
		employee.setRole("director");
		assertTrue(daoRole.update(employee));
		assertEquals(daoRole.findById(employee.getId()),employee);
		assertTrue(daoRole.delete(employee));
	}
	
	@Test
	public void product() {
		assertEquals(daoProduct.findById(1), new Product(1,1,1,100500L,1,"machine","makes super clean house"));
		assertEquals(daoProduct.findByCriteria("price", 100500), new Product(1,1,1,100500L,1,"machine","makes super clean house"));
		assertTrue(daoProduct.save( new Product(1,1,1,100500L,1,"notebook","Super cheap notebook")));
		Product notebook = daoProduct.findByCriteria("name", "notebook");
		assertEquals(notebook, new Product(notebook.getProductid(),1,1,100500L,1,"notebook","Super cheap notebook"));
		notebook.setDescription("Not cheap anymore");
		assertTrue(daoProduct.update(notebook));
		assertEquals(daoProduct.findById(notebook.getProductid()),notebook);
		assertTrue(daoProduct.delete(notebook));
	}
	
	@Test
	public void order() {
		assertEquals(daoOrder.findById(1), new Order(1,1,1,new Timestamp(1200000000l), new Timestamp(435346453324l)));
		assertEquals(daoOrder.findByCriteria("userid", 1),new Order(1,1,1,new Timestamp(1200000000l), new Timestamp(435346453324l)));
		assertTrue(daoOrder.save( new Order(1,1,1,new Timestamp(120000000000l), new Timestamp(435346453324l))));
		Order order = daoOrder.findByCriteria("orderDate", new Timestamp(120000000000l));
		assertEquals(order, new Order(order.getOrderid(),1,1,new Timestamp(120000000000l), new Timestamp(435346453324l)));
		order.setOrderDate(new Timestamp(213423423498324l));
		assertTrue(daoOrder.update(order));
		assertEquals(daoOrder.findById(order.getOrderid()),order);
		assertTrue(daoOrder.delete(order));
	}
	
	@Test
	public void orderdetails() {
		assertEquals(daoOrderDetails.findById(1), new OrderDetails(1,1,1,100500L,1, new BigDecimal("0.25")));
		assertEquals(daoOrderDetails.findByCriteria("price", 100500L),new OrderDetails(1,1,1,100500L,1,new BigDecimal("0.25")));
		assertTrue(daoOrderDetails.save( new OrderDetails(1,1,1,100500L,123456, new BigDecimal("0.25"))));
		OrderDetails detail = daoOrderDetails.findByCriteria("quantity", 123456);
		assertEquals(detail, new OrderDetails(detail.getOrderdetailsid(),1,1,100500L,123456, new BigDecimal("0.25")));
		detail.setQuantity(1234567);
		assertTrue(daoOrderDetails.update(detail));
		assertEquals(daoOrderDetails.findById(detail.getOrderdetailsid()),detail);
		assertTrue(daoOrderDetails.delete(detail));
	}
	
	@Test
	public void suplier() {
		assertEquals(daoSuplier.findById(1), new Suplier(1,"Oracle","The best vendor ever, ms sucks"));
		assertEquals(daoSuplier.findByCriteria("name", "Oracle"), new Suplier(1,"Oracle","The best vendor ever, ms sucks"));
		assertTrue(daoSuplier.save(new Suplier(1,"SAP","German vendor")));
		Suplier sap = daoSuplier.findByCriteria("name", "SAP");
		assertEquals(sap,new Suplier(sap.getSuplierid(),"SAP","German vendor"));
		sap.setDescription("Third vendor");
		assertTrue(daoSuplier.update(sap));
		assertEquals(daoSuplier.findById(sap.getSuplierid()),sap);
		assertTrue(daoSuplier.delete(sap));
	}
	
	@Test
	public void category() {
		assertEquals(daoCategory.findById(1), new Category(1,"Java","The best language ever, c# sucks"));
		assertEquals(daoCategory.findByCriteria("name", "Java"), new Category(1,"Java","The best language ever, c# sucks"));
		assertTrue(daoCategory.save(new Category(1,"Scala","kind of java")));
		Category scala = daoCategory.findByCriteria("name", "Scala");
		assertEquals(scala,new Category(scala.getCategoryid(),"Scala","kind of java"));
		scala.setDescription("Third vendor");
		assertTrue(daoCategory.update(scala));
		assertEquals(daoCategory.findById(scala.getCategoryid()),scala);
		assertTrue(daoCategory.delete(scala));
	}
}
