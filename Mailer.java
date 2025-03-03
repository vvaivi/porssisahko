import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Mailer {
    private String messageTitle; 
    private String messageContent;
    
    public Mailer(String messageTitle, String messageContent) {
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
    }

    public void sendEmail() {
        final String senderEmail = System.getenv("SENDER_EMAIL");
        final String senderPassword = System.getenv("SENDER_PASSWORD");
        String receiverEmailString = System.getenv("RECEIVER_EMAIL").replace("[", "").replace("]", "");
        String[] receiverEmails = receiverEmailString.split(",");
    
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
    
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });
    
        try {
            for (String recipientEmail : receiverEmails) {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(MimeUtility.encodeText(messageTitle, "UTF-8", "B"));

                byte[] messageBytes = messageContent.getBytes(StandardCharsets.UTF_8);
                String utf8EncodedMessage = new String(messageBytes, StandardCharsets.UTF_8);
                message.setContent(utf8EncodedMessage, "text/html; charset=utf-8");
                
                Transport.send(message);
    
                System.out.println("Email sent successfully to " + recipientEmail);
            
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding", e);
        }
    }

    public static void main(String[] args) {
        EmailMessage message = new EmailMessage(5.5, 12);
        Mailer mailer = new Mailer(message.getTitle(), message.getMessage());
        mailer.sendEmail();
    }
}