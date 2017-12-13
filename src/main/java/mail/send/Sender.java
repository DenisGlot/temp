package mail.send;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exceptions.MailSenderException;

public class Sender {

	protected final String username = "denisglotov.1911@gmail.com";
	protected final String password = "123456asdzxcv";

	protected final Properties props;
	
	protected Message message;

	public Sender() {
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		message = new MimeMessage(session);
	}

	/**
	 * 
	 * @param toEmail
	 * @param passwordForClient
	 * @throws MailSenderException
	 */
	public void sendPassword(String toEmail, String passwordForClient) {

		try {
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Registration");
			message.setContent("<h2>Dear user! Your password :  " + passwordForClient
					+ "</h2><h2>Link to the website : </h2> <a href=\"https://calculator-netcracker.herokuapp.com/\">Click here!</a> ",
					"text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new MailSenderException();
		}
	}
	/**
	 * 
	 * @param feedback
	 * @throws MailSenderException
	 */
	public void sendFeedBack(String feedback) {
		try {
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("denisglotov98@mail.ru"));
			message.setSubject("FeedBack");
			message.setContent(feedback,
					"text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new MailSenderException();
		}
	}
}
