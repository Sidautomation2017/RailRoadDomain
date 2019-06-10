package com.pkg.utilities;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
		
	
	
	
	 public static void main (String[]arg) throws Exception{
		sendEmail();
		
		
	}
	
	
	public static void sendEmail () throws Exception {
		try{
			
			
		
			String host = "smtp.office365.com";
			final String fromEmail = "crtmnotifications@cloudmoyo.com"; 																		
			final String password = "h7%gDQNX$aau"; 													
			//final String[] toEmail = {"Sidheshwar.Tondare@cloudmoyo.com","Bhushan.Borse@cloudmoyo.com"}; // can be any email	
			//String totalTestCases=	String.valueOf(CommonMethods.passCount+CommonMethods.failCount);
			System.out.println("TLSEmail Start");
			Properties props = new Properties();
			props.put("mail.smtp.host", host); // SMTP Host
			props.put("mail.smtp.port", "587"); // TLS Port
			props.put("mail.smtp.auth", "true"); // enable authentication
			props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
			Authenticator auth = new Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			
			Session session = Session.getDefaultInstance(props, auth);			
			Message message = new MimeMessage(session); // email message
			message.setFrom(new InternetAddress(fromEmail));
			//InternetAddress[] recipients = InternetAddress.parse("Sidheshwar.Tondare@cloudmoyo.com,Preeti.Kale@cloudmoyo.com,Kaustubh.Vaze@cloudmoyo.com,Girwarsingh.Shekhawat@cloudmoyo.com,Sayeeda.Jahagirdar@cloudmoyo.com,Vaishali.Gadekar@cloudmoyo.com");
			InternetAddress[] recipients = InternetAddress.parse("Sidheshwar.Tondare@cloudmoyo.com");
			message.addRecipients(Message.RecipientType.TO, recipients);
			message.setSubject("MMCS ISS Automation Report");
			//BodyPart messageBodyPart = new MimeBodyPart();
			
			/*String emailBody="<table width='600'  style='color: #000000; border:1px solid #ccc; padding: 5px; font-family: Tahoma,  Geneva, sans-serif; font-size: 14px;' cellspacing='0' cellpadding='0' border='0' align='left'>";
			emailBody += "<tr>";
			emailBody += "<td style='border-bottom:5px solid #028ec3; background: #eaeff3;'>";
			emailBody += "<span style='display: block; font-size: 22px; padding: 10px;'> MMCS ISS EDI VALIDATION </span>";
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td>";
			emailBody += "<br>";
			emailBody += "Hello <strong>Team,</strong>";
			emailBody += "<br><br>";
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td style='font-family: Tahoma,  Geneva, sans-serif;'>";
			emailBody += "<strong style='border-bottom: 1px solid #ccc; padding-bottom: 2px;'>";
			emailBody += "Summary of Execution is as below :-";
			emailBody += "</strong>";
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td>";
			emailBody += "<br>";
			emailBody += "<table style='color: #000000; font-family: Tahoma,  Geneva, sans-serif; font-size: 14px; width: 70%; border: 1px solid #ccc; border-collapse: collapse;'  cellspacing='1' cellpadding='2' border='1' align='left'>";
			emailBody += "<tr>";
			emailBody += "<td width='150px'>";
			emailBody += "<strong>Environment</strong>";
			emailBody += "</td>";
			emailBody += "<td>";
			emailBody += "QA";
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";			
			emailBody += "<td width='150px'>";
			emailBody += "<strong>Total Test Cases</strong>";
			emailBody += "</td>";
			emailBody += "<td>";
			emailBody += totalTestCases;
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td>";
			emailBody += "<strong>Test Cases Passed</strong>";
			emailBody += "</td>";
			emailBody += "<td>";
			emailBody += CommonMethods.passCount;
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td>";
			emailBody += "<strong>Test Cases Failed</strong>";
			emailBody += "</td>";
			emailBody += "<td>";
			emailBody += CommonMethods.failCount;
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td>";	
			emailBody += "<strong>For Execution Report</strong>";
			emailBody += "</td>";
			emailBody += "<td>";
			emailBody+= "<a href="+reportpath+">";
			emailBody+="Click Here";
			emailBody+="</a>";
			emailBody += "</td>";
			emailBody += "</tr>";
			emailBody += "<tr>";
			emailBody += "<td>";	
			emailBody += "<strong>For ScreenShots</strong>";
			emailBody += "</td>";
			emailBody += "<td>";			
			//emailBody+= "<a href='file://CMLPT015/Users/Sidheshwar.Tondare/OneDrive%20-%20CloudMoyo%20India%20Private%20Limited/ISS%20Automation/EDI%20validation/ScreenShots/'>";
			emailBody+= "<a href=";
			emailBody+=screenshotPath+">";
			emailBody+="Click Here";
			emailBody+="</a>";
			emailBody += "</td>";
			emailBody += "</tr>";	
			emailBody += "</table>";
			emailBody +="<tr >";
			emailBody += "<td>";
			emailBody += "<strong>Regards,</strong><br>";
			emailBody += "<span style='color: #000000; font-weight: normal; font-family: Tahoma,  Geneva, sans-serif;'> MMCS Automation Team</span><br><br><br>";
			emailBody += "</tr>";
			emailBody += "</td>";
			emailBody += "<tr bgcolor='#ffffff'>";
			emailBody += "<td style='font-family:Tahoma,Helvetica,sans-serif; border-top: 1px solid #ccc; border-bottom:1px solid #ccc;  color:#000000;text-align:center; font-size:14px; padding: 15px 0; text-align:center;'>";
			emailBody += "This is an auto generated mail. Please do not reply";
			emailBody += "<br></td>";
				*/
			String emailBody="hello";
					
			//messageBodyPart.setText("this is automation driven");
			//Multipart multipart = new MimeMultipart();
			/*MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
			DataSource source = new FileDataSource(path);
			 messageBodyPart2.setDataHandler(new DataHandler(source));
		     messageBodyPart2.setFileName(path);		   
		    multipart.addBodyPart(messageBodyPart2);	*/
			//multipart.addBodyPart(messageBodyPart);
			
			/*StringWriter writer = new StringWriter();
			IOUtils.copy(new FileInputStream(new File(path)), writer);*/
			/*Attachment attachment = new Attachment(path);
            message.Attachments.Add(attachment);*/
			message.setContent(emailBody, "text/html");
			//message.setContent(multipart);
			Transport.send(message);
			System.out.println("Report Mail Sucessfully");

}
			
		catch (MessagingException e) {

			throw new RuntimeException(e);

		}
		
	}
	
}