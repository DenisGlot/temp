import dao.MyDAO;
import dao.annotation.MyColumn;
import dao.entity.User;

public class Test {

	public static void main(String[] args) {
		MyDAO<User, Integer> dao  = new MyDAO<>(User.class);
		System.out.println(dao.getALl());
		System.out.println(dao.findById(1));
		System.out.println(dao.findByCriteria("email", "denis"));
	}

}
