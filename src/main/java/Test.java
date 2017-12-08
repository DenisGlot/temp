import dao.MyDAO;
import dao.annotation.MyColumn;
import dao.entity.User;
import exceptions.NotDeclaredConnection;

public class Test {

	public static void main(String[] args) {
		throw new NotDeclaredConnection();
	}

}
