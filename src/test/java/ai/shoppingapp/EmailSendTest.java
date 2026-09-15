package ai.shoppingapp;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailSendTest {

    public static void main(String[] args) {
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost("smtp.gmail.com");
    	mailSender.setPort(465); // Changed to 465
    	mailSender.setUsername("naybhonekhant115@gmail.com");
    	mailSender.setPassword("dunihsmtdqrqzhwy");

    	Properties props = mailSender.getJavaMailProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.auth", "true");

    	// Configure SSL directly for port 465
    	props.put("mail.smtp.ssl.enable", "true");
    	props.put("mail.smtp.socketFactory.port", "465");
    	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	props.put("mail.smtp.socketFactory.fallback", "false");

    	props.put("mail.smtp.connectiontimeout", "10000");
    	props.put("mail.smtp.timeout", "10000");
    	props.put("mail.debug", "true");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("naybhonekhant115@gmail.com");
        message.setTo("levilyonin@gmail.com"); // Your receiving email
        message.setSubject("Pure Java SMTP Test");
        message.setText("SMTP connection works independently of the web application!");

        System.out.println("Connecting to Google SMTP...");
        mailSender.send(message);
        System.out.println("Email sent successfully!");
    }
}