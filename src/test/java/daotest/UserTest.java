package daotest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.entity.User;
import hash.Hashing;

public class UserTest {
	
//	DAOImpl<User, Integer> uc = null;
	
	@Before
	public void init() {
//		uc = new DAOImpl<>(User.class);
	}

	@Test
	
	public void test() {
//		 User userNull = uc.findByCriteria("email", "nieufhiewa");
//		 assertEquals(null, userNull);
//		 List<User> list  = uc.getALl();
//		 System.out.println(list);
//		 User newUser = new User(666,"delete","remove");
//		 User check = new User(1,"admin",Hashing.sha1("admin"));
//         User user = uc.findById(1);
//         assertEquals(user, check);
//         user = uc.findByCriteria("email", "admin");
//         assertEquals(user, check);
//         // Primary Key in DataBase is generated by DataBase
//         // So it's hard to make nice check
//         uc.save(newUser);
//         newUser = uc.findByCriteria("email", "delete");
//         newUser.setPassword("kick");
//         uc.update(newUser);
//         assertEquals(uc.findByCriteria("email", "delete"), new User(newUser.getId(),"delete",Hashing.sha1("kick")));
//         uc.delete(newUser);
	}

}
