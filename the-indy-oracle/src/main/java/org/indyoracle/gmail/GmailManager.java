package org.indyoracle.gmail;

import static org.indyoracle.logging.LoggingHelper.log;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.indyoracle.beans.Mail;
import org.indyoracle.data.DataManager;

public class GmailManager {

	private static Map<String, String> getCredentials() {
		Map<String, String> credentials = new HashMap<String, String>();

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = GmailManager.class.getClass().getResourceAsStream(
					("/credentials.properties"));
			prop.load(input);

			credentials.put("username", prop.getProperty("username"));
			credentials.put("password", prop.getProperty("password"));

		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return credentials;
	}

	public static ArrayList<Mail> checkEmail(String username, String password) {
		ArrayList<Mail> email = new ArrayList<Mail>();

		try {
			Properties properties = new Properties();

			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");

			Session session = Session.getInstance(properties,
					new GMailAuthenticator(username, password));

			Store store = session.getStore("pop3s");
			store.connect("smtp.gmail.com", username, password);

			// Create the folder object and open it:
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve the messages from inbox:
			Message[] messages = emailFolder.getMessages();

			for (Message m : messages) {
				email.add(new Mail(m.getSubject(), m.getFrom()[0].toString(), m
						.getContent().toString()));
			}

			// Close the store and folder objects:
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			System.err.println(e);
		} catch (MessagingException e) {
			System.err.println(e);
		} catch (Exception e) {
			System.err.println(e);
		}

		return email;
	}

	public static ArrayList<Mail> getSmsMessages() {
		ArrayList<Mail> smsMessages = new ArrayList<Mail>();

		Map<String, String> creds = getCredentials();
		ArrayList<Mail> allEmails = checkEmail(creds.get("username"),
				creds.get("password"));
		for (Mail m : allEmails) {
			if (m.getSubject().startsWith("SMS from")) {
				smsMessages.add(m);
			}
		}

		return smsMessages;
	}

	public static String extractSmsNumber(Mail mail) {
		String phoneNumber = mail.getFrom();

		phoneNumber = phoneNumber.substring(phoneNumber.indexOf("\"") + 1);
		phoneNumber = phoneNumber.substring(0, phoneNumber.indexOf("\""));

		phoneNumber = phoneNumber.replace("(", "");
		phoneNumber = phoneNumber.replace(")", "");
		phoneNumber = phoneNumber.replace("-", "");
		phoneNumber = phoneNumber.replace(" ", "");

		return phoneNumber;
	}

	public static boolean sendHelpRequestEmail(String to, String toCarrier, String numberWhoNeedsHelp, String msg) {
		Map<String, String> creds = getCredentials();
		
		DataManager dataManager = DataManager.getInstance();
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new GMailAuthenticator(creds.get("username"), creds.get("password")));

		try {
			
			String carrierDomain = "";
			switch (toCarrier) {
			
				case "AT&T":
					carrierDomain = "txt.att.net";
					break;
				case "Boost Mobile":
					carrierDomain = "myboostmobile.com";
					break;
				case "Sprint":
					carrierDomain = "messaging.sprintpcs.com";
					break;
				case "T-Mobile":
					carrierDomain = "tmomail.net";
					break;
				case "Verizon":
					carrierDomain = "vtext.com";
					break;
				case "Virgin Mobile":
					carrierDomain = "vmobl.com";
					break;
			}
			
			Date now = new Date();
			String nowString = new SimpleDateFormat("hh:mm:ss").format(now);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("the-indy-oracle@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to + "@" + carrierDomain));
			message.setSubject("Someone Needs Help");
			String msgBody = "Hero, we've received an SOS from " + numberWhoNeedsHelp + ". Here is their full message: " + msg + ". Please coordinate an escort.";
			message.setText(msgBody);
			dataManager.addMessage(nowString + " MSG: " + msgBody + ", TO: " + to);
			log("MSG: " + msgBody + ", TO: " + to);

			Transport.send(message);

		} catch (MessagingException e) {
			System.err.println(e);
			return false;
		}
		
		return true;
	}
}
