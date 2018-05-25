package com.charan.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSendService {
    public void sendEmail(Email em) {
        sendFromGMail(em.getFrom(), em.getPwd(), new String[]{"charant.me.csa@gmail.com"}, em.getSubject(), em.getBody());
    }

    // editor@clinicalstudiesjournal.com
    // row123!@#
    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        System.out.println("Sending email : " + subject);
        Properties props = System.getProperties();
//        String host = "smtp.gmail.com";
        String host = "smtp.clinicalstudiesjournal.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
//            message.setText(body);
            message.setContent(body, "text/html; charset=utf-8");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            // Copy message to "Sent Items" folder as read
            Store store = session.getStore("imap");
            store.connect("imap.clinicalstudiesjournal.com", from, pass);

            Folder[] f = store.getDefaultFolder().list();
            for(Folder fd:f)
                System.out.println(">> "+fd.getName());

            Folder folder = store.getFolder("Sent Items");
            folder.open(Folder.READ_WRITE);
            message.setFlag(Flags.Flag.SEEN, false);
            folder.appendMessages(new Message[] {message});
            store.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
