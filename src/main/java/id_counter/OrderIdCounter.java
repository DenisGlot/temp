package id_counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * It was used first time in creating datbase
 * and then will be used in Scenario#buyFromBasket()
 * @author Denis
 *
 */
public class OrderIdCounter {

	public static AtomicInteger orderid = new AtomicInteger(0);
}
