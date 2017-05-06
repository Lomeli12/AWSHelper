package net.lomeli.awshelper.util;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    public static void sendMail(Session session, MimeMessage msg, String host, String user, String pass) {
        try {
            Transport transport = session.getTransport();
            try {
                transport.connect(host, user, pass);
                transport.sendMessage(msg, msg.getAllRecipients());
            } catch (MessagingException ex) {
                ex.printStackTrace();
            } finally {
                transport.close();
            }
        } catch (NoSuchProviderException ex) {
            System.out.println("Email Provider does not exist!");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Failed to close connection!");
            ex.printStackTrace();
        }
    }

    public static Session createSMTPSession(int port) {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        return Session.getDefaultInstance(props);
    }

    public static MimeMessage createMessage(String to, String from, String subject,
            String body, Session session) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(from);
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        msg.setContent(body, "text/plain");
        return msg;
    }
}
