package server;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;

import commands.Command;
import commands.SendEditedMapsDecisionCommand;
import commands.SendNewEditedMapsEmailCommand;
import commands.SendNewPricesDecisionEmailCommand;
import commands.SendNewPricesRequestEmailCommand;
import commands.SendNewVersionCommand;
import commands.SendRenewalEmailCommand;

public class Email {

	public static void sendEmail(Command command) throws MessagingException {
		Properties properties = new Properties();
	     properties.put("mail.smtp.auth", "true");
	     properties.put("mail.smtp.starttls.enable", "true");
	     properties.put("mail.smtp.host", "smtp.gmail.com");
	     properties.put("mail.smtp.port", "587");
	     
	     String myAccountEmail = "gcmmaps19@gmail.com";
	     String password = "ekhxgzhmlehzyzqn";
	     
	     
	     
	     //Get the session object  
	      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	    	  @Override
	    	  protected PasswordAuthentication getPasswordAuthentication() {
	    		  return new PasswordAuthentication(myAccountEmail, password);
	    	  }
	      });
	      Message message = null;
		 switch (command.getType()) {
			 case SendRenewalEmailCommand:
		    	  message = prepareRenewalMessage(session, myAccountEmail, 
		    			  command.getCommand(SendRenewalEmailCommand.class).getEmailAddress(), 
		    			  command.getCommand(SendRenewalEmailCommand.class).getCityName());
		    	  break;
		    	  
			 case SendNewVersionCommand:
		    	  message = prepareNewVersionMessage(session, myAccountEmail, 
		    			  command.getCommand(SendNewVersionCommand.class).getEmailAddress(),
		    			  command.getCommand(SendNewVersionCommand.class).getCityName()); 
		    	  break;
		    	  
			 case SendNewPricesRequestEmailCommand:
		    	  message = prepareNewPricesRequestMessage(session, myAccountEmail, 
		    			  command.getCommand(SendNewPricesRequestEmailCommand.class).getEmailAddress()); 
		    	  break;
		    	  
			 case SendNewPricesDecisionEmailCommand:
		    	  message = prepareNewPricesDecisionMessage(session, myAccountEmail, 
		    			  command.getCommand(SendNewPricesDecisionEmailCommand.class).getEmailAddress(),
		    			  command.getCommand(SendNewPricesDecisionEmailCommand.class).getDecision());
		    	  break;
		    	  
			 case SendNewEditedMapsEmailCommand:
		    	  message = prepareNewEditedMapsMessage(session, myAccountEmail, 
		    			  command.getCommand(SendNewEditedMapsEmailCommand.class).getEmailAddress());
		    	  break;
		    	  
			 case SendEditedMapsDecisionCommand:
		    	  message = prepareEditedMapsDecisionMessage(session, myAccountEmail, 
		    			  command.getCommand(SendEditedMapsDecisionCommand.class).getEmailAddress(),
		    			  command.getCommand(SendEditedMapsDecisionCommand.class).getDecision(), command.getCommand(SendEditedMapsDecisionCommand.class).getCityName());
		    	  break;
		 }
	      
	      Transport.send(message);
	      System.out.println("Messsage sent");
	   } 
	private static Message prepareRenewalMessage(Session session, String myAccountEmail, String recepient, String cityName) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("GCM - Renewal Subscriptions");
			message.setText("Three days until expiration date for " + cityName + " city.\n"
							+"You have 10% discount until the expiration date.");
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Message prepareNewVersionMessage(Session session, String myAccountEmail, String recepient, String cityName) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("GCM - New Version Released");
			message.setText("Check for " + cityName + " new version!");
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Message prepareNewPricesRequestMessage(Session session, String myAccountEmail, String recepient) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("GCM - New Prices Request");
			message.setText("Check for new prices!");
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Message prepareNewPricesDecisionMessage(Session session, String myAccountEmail, String recepient, String decision) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("GCM - Decision For New Prices");
			message.setText("Company Manager have decided to " + decision + " the 'Last New Prices Request'!");
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Message prepareNewEditedMapsMessage(Session session, String myAccountEmail, String recepient) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("GCM - New Edited Maps");
			message.setText("We have edited some maps please check them!");
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Message prepareEditedMapsDecisionMessage(Session session, String myAccountEmail, String recepient, String decision, String cityName) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("GCM - Decision For Updated Maps");
			message.setText("Company Manager have decided to " + decision + " the 'Last Update '" + cityName + "' Request'!");
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

}