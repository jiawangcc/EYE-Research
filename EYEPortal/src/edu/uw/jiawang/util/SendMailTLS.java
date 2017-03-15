package edu.uw.jiawang.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class SendMailTLS {

	private static final String USERNAME = "jiawang@uw.edu";
	private static final String PASSWORD = ""; //enter password here

	private static final Properties PROPS = new Properties();
	static {
		PROPS.put("mail.smtp.auth", "true");
		PROPS.put("mail.smtp.starttls.enable", "true");
		PROPS.put("mail.smtp.host", "smtp.uw.edu");
		PROPS.put("mail.smtp.port", "587");
	}

	public static boolean sendMsgToEmailRecipient(String subject, String msg, String email) {

		Session session = Session.getInstance(PROPS, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eyedb@uw.edu"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}
