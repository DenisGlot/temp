package cache.wasNotUsed;

public interface Transaction {

	void begin();
	
	void commit();
	
	void rollback();
}
