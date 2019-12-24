package com.cbs.email.service;

import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import org.apache.log4j.Logger;

@Stateless
public class MailManagerImpl implements MailManager {

    private static final Logger logger = Logger.getLogger(MailManagerImpl.class);
    static Set<MailTemplate> templates;
    @Resource(mappedName = "jms/emailQueue")
    private Queue queue;
    @Resource(mappedName = "jms/emailConnectionFactory")
    private QueueConnectionFactory connectionFactory;

    @Override
    public void enqueueMail(MailMessage mailMessage) {
        try {
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage message = session.createObjectMessage(mailMessage);
            QueueSender sender = session.createSender(queue);
            sender.send(message);

            connection.close();
        } catch (Exception e) {
            logger.error("Error Sending mail: " + mailMessage.getMimeType() + "\nFrom: " + mailMessage.getFromAddress()
                    + "\nTo: " + mailMessage.getToAddress() + "\nSubject: " + mailMessage.getSubject()
                    + "\nCcAddress: " + mailMessage.getCcAddress(), e);
        }
    }
}
