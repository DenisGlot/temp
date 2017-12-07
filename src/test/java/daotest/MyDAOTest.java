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
	}

	@Test
	public void role() {
		assertEquals(daoRole.findById(1), new Role(1,"admin"));
		assertEquals(daoRole.findByCriteria("role", "admin"), new Role(1,"admin"));
	}
	
	@Test
	public void product() {
		assertEquals(daoProduct.findById(1), new Product(1,1,1,100500L,1,"machine","makes super clean house"));
		assertEquals(daoProduct.findByCriteria("price", 100500), new Product(1,1,1,100500L,1,"machine","makes super clean house"));
	}
	
	@Test
	public void order() {
		assertEquals(daoOrder.findById(1), new Order(1,1,new Timestamp(1200000000l), new Timestamp(435346453324l)));
		assertEquals(daoOrder.findByCriteria("userid", 1),new Order(1,1,new Timestamp(1200000000l), new Timestamp(435346453324l)));
	}
	
	@Test
	public void orderdetails() {
		assertEquals(daoOrderDetails.findById(1), new OrderDetails(1,1,1,100500L,1, new BigDecimal("0,25")));
		assertEquals(daoOrderDetails.findByCriteria("price", 100500L),new OrderDetails(1,1,1,100500L,1,new BigDecimal("0,25")));
	}
	
	@Test
	public void suplier() {
		assertEquals(daoSuplier.findById(1), new Suplier(1,"Oracle","The best vendor ever, ms sucks"));
		assertEquals(daoSuplier.findByCriteria("name", "Oracle"), new Suplier(1,"Oracle","The best vendor ever, ms sucks"));
	}
	
	@Test
	public void category() {
		assertEquals(daoCategory.findById(1), new Category(1,"Java","The best language ever, c# sucks"));
		assertEquals(daoCategory.findByCriteria("name", "Java"), new Category(1,"Java","The best language ever, c# sucks"));
	}
}
