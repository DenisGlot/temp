package exceptions;

public class NotAdminException extends RuntimeException{

	
	private static final long serialVersionUID = 1234234235354634L;
	
	private final static String error = "You are not admin";
	
	public NotAdminException() {
		super(error);
		
	}

}
