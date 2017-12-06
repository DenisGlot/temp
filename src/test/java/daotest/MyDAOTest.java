package daotest;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import dao.MyDAO;
import dao.entity.Product;
import dao.entity.Role;
import dao.entity.User;
import hash.Hashing;

public class MyDAOTest {

	MyDAO<User, Integer> daoUser;
	MyDAO<Role, Integer> daoRole;
	MyDAO<Product,Integer> daoProduct;
	@Before
	public void init() {
		daoUser  = new MyDAO<>(User.class);
		daoRole = new MyDAO<>(Role.class);
		daoProduct = new MyDAO<>(Product.class);
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
}
