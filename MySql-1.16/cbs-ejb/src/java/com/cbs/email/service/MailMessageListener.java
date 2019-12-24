package com.cbs.email.service;

import com.cbs.utils.ServiceLocator;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.Logger;

@MessageDriven(mappedName = "jms/emailQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailMessageListener implements MessageListener {

    private static final Logger logger = Logger.getLogger(MailMessageListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            MailMessage mailMessage = (MailMessage) ((ObjectMessage) message).getObject();
            logger.debug("Received message from emailQueue. Send mail to : " + mailMessage.getToAddress());
            sendMail(mailMessage);
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    private void sendMail(MailMessage mailMessage) throws Exception {
        try {
            MailSender mailSender = (MailSender) ServiceLocator.getInstance().lookup("MailSenderImpl");
            mailSender.postEmail(mailMessage);
        } catch (Exception ex) {
            logger.error("Error processing message", ex);
        }
    }
//    private InternetAddress[] createAddress(String[] stringAddList) {
//        try {
//            InternetAddress[] addressList = new InternetAddress[stringAddList.length];
//            for (int i = 0; i < stringAddList.length; i++) {
//                addressList[i] = new InternetAddress(stringAddList[i]);
//            }
//            return addressList;
//        } catch (Exception e) {
//            logger.error("createAddress(String[])", e);
//            return null;
//        }
//    }
//	private static class SMTPAuthenticator extends javax.mail.Authenticator {
//
//		SMTPAuthenticator() {
//		}
//
//		@Override
//		public PasswordAuthentication getPasswordAuthentication() {
//			String username = MailConstants.MAIL_AUTH_USERNAME;
//			String password = MailConstants.MAIL_AUTH_PASSWORD;
//			return new PasswordAuthentication(username, password);
//		}
//	}
}
