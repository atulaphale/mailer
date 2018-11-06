package com.cvs.mailer.util;

import com.cvs.mailer.representation.Mailer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailCreation {

    private AppProperties mailProperties;

    private Properties mailServerProperties;
    private Session mailSession;
    private MimeMessage mailMessage;

    private Mailer mail;

    public static void main(String args[]){
        AppProperties testProperties = new AppProperties();
        testProperties.setServerHost("smtp.gmail.com");
        testProperties.setServerPort("587");
        testProperties.setUserName("XXX@gmail.com");
        testProperties.setPassword(""); //check password is filled
        testProperties.setEnableTLS("true");
        testProperties.setEnableAuth("true");
        MailCreation m = new MailCreation(new Mailer(100, "XXX@gmail.com", "TEST", "<html><body><p>Hello World</p></body></html>"), testProperties);
        m.prepareAndSendMail();
    }

    public MailCreation(Mailer mail, AppProperties mailProperties) {
        this.mail = mail;
        this.mailProperties = mailProperties;
    }

    public void setMailServerProperties() {
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", mailProperties.getServerPort());
        mailServerProperties.put("mail.smtp.auth", mailProperties.getEnableAuth());
        mailServerProperties.put("mail.smtp.starttls.enable", mailProperties.getEnableTLS());
    }

    private void createEmailMessage() throws AddressException,
            MessagingException {
        mailSession = Session.getDefaultInstance(mailServerProperties, null);
        mailMessage = new MimeMessage(mailSession);
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToAddress()));
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setContent(mail.getBody(), "text/html");//for a html email
        //emailMessage.setText(mail.getBody());// for a text email
    }

    private void sendEmail() throws AddressException, MessagingException {

        String emailHost = mailProperties.getServerHost();
        String fromUser = mailProperties.getUserName();
        String fromUserEmailPassword = mailProperties.getPassword();
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }

    public String prepareAndSendMail() {
        String response = "Mail sent successfully";
        try {
            this.setMailServerProperties();
            this.createEmailMessage();
            this.sendEmail();
        }
        catch(AddressException e){
            System.out.println("Error in email address : " + e.getMessage());
            response = "Could not send the mail. Error in TO mail address";
        }
        catch (MessagingException e){
            System.out.println("Error in email address" + e.getMessage());
            response = "Could not send the mail. Error in message of the mail";
        }
        catch (Exception e){
            System.out.println("Unknown error");
            response = "Could not send the mail. Unknown error";
            e.printStackTrace();
        }
        return response;
    }

}
