package exceptions;

public class NotDeclaredConnection extends RuntimeException{

	
	private static final long serialVersionUID = 1234234235354634L;
	
	private final static String error = "Your connection was nor declared";
	
	public NotDeclaredConnection() {
		super(error);
		
	}

}
