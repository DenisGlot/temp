package mail.ejb;

import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;


public class MailEJB {
	
	final Logger logger = Logger.getLogger(MailEJB.class);

	public void sendEmail(String fromEmail,String username,String password,String toEmail,String passwordForClient) {
		try {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.fallback", "false");
		
		Session mailSession = Session.getDefaultInstance(prop, null);
		//mailSession.setDebug(true);
		
		Message message = new MimeMessage(mailSession);
		
		message.setFrom(new InternetAddress(fromEmail));
		
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		
		message.setContent("<h2>Dear user! Your password :  " + passwordForClient + "<h2><h2>Link to the website : </h2> <a href=\"https://calculator-netcracker.herokuapp.com/\">Click here!</a> ", "text/html");
		
		message.setSubject("Registration");
		
		Transport transport = mailSession.getTransport("smtp");
		
		transport.connect("smtp.gmail.com", username, password);
		
		transport.sendMessage(message, message.getAllRecipients());
		} catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
