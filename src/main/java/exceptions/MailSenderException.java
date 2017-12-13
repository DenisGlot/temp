package exceptions;

public class MailSenderException extends RuntimeException{

	private static final long serialVersionUID = 11234215423L;

private static String error = "Could not send a message";
	
	public MailSenderException() {
		super(error);
	}
	
}
