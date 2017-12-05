import dao.MyDAO;
import dao.annotation.MyColumn;
import dao.entity.User;

public class Test {

	public static void main(String[] args) {
		MyDAO<User, Integer> dao  = new MyDAO<>(User.class);
		dao.getALl();
	}

}
class Testic{
	@MyColumn(columnName="field" , clazz = String.class)
	public String field;
}
