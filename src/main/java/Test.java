import java.util.concurrent.ConcurrentHashMap;

import dao.MyDAO;
import dao.annotation.MyColumn;
import dao.entity.Product;
import dao.entity.User;
import exceptions.NotDeclaredConnection;

public class Test {

	public static void main(String[] args) {
		ConcurrentHashMap<Product, Integer> products = new ConcurrentHashMap<>();
		products.put(new Product(), 4);
		System.out.println(products);
		products.put(new Product(), 10);
		System.out.println(products);
		products.remove(new Product());
		System.out.println(products);
	}

}
