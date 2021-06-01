package utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {
	public static boolean sendMail(String to, String subject, String content, String user, String pass) {
		Properties pro = System.getProperties();
		pro.put("mail.smtp.host", "smtp.gmail.com");
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.port", "587");
		pro.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(pro, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});

		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			message.setContent(content, "text/html; charset=UTF-8");
			Transport.send(message);
			System.out.println("Send Email Success");
			return true;
		} catch (AddressException e) {
			System.err.println("Send Email Failed");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.err.println("Send Email Failed");
			e.printStackTrace();
		}
		return false;

	}
}
