package mail.send;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import exceptions.MailSenderException;

public class Sender {
	
	private final Logger logger = Logger.getLogger(Sender.class);

	protected final static String username = "denisglotov.1911@gmail.com";
	protected final static String password = "123456asdzxcv";
	
	static Session session;
	
	static {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
	
	/**
	 * The Content must be text/html type 
	 * @param toEmail
	 * @param Subject 
	 * @param Content must be text/html type 
	 * @throws MailSenderException
	 */
	private void sendMessage(String toEmail,String Subject,String Content) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(Subject);
			message.setContent(Content,"text/html");
			Transport.send(message);
		} catch(MessagingException e) {
			throw new MailSenderException();
		}
	}

	/**
	 * 
	 * @param toEmail
	 * @param passwordForClient
	 * @throws MailSenderException from sedMessage method
	 */
	public void sendPassword(String toEmail, String passwordForClient) {
		
		String content = "<h2>Dear user! Your password :  " + passwordForClient + "</h2><h2>Link to the website : </h2> <a href=\"https://calculator-netcracker.herokuapp.com/\">Click here!</a> ";
		
		sendMessage(toEmail,"Registration",content);
		
		logger.debug("The message was sent");
	}
	
	/**
	 * 
	 * @param feedback the body of the message
	 * @param subject 
	 * @param email From whom?
	 * @throws MailSenderException from sedMessage method
	 */
	public void sendFeedBack(String feedback,String subject,String email) {
		sendMessage("denisglotov98@mail.ru",subject + " from " + email,feedback);
	}
}
