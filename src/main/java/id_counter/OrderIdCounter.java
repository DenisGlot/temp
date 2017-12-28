package id_counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * It is used first time in creating database
 * and then will be used in Scenario#buyFromBasket()
 * @author Denis
 *
 */
public class OrderIdCounter {
    
	public static AtomicInteger orderid = new AtomicInteger(0);
	
}
