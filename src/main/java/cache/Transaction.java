package cache;

public interface Transaction {

	void begin();
	
	void commit();
	
	void rollback();
}
