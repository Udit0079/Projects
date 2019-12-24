package com.cbs.sms.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;


@Stateless
public class SmsManagerImpl implements SmsManager {

    @Resource(mappedName = "jms/smsQueue")
    private Queue queue;
    @Resource(mappedName = "jms/smsConnectionFactory")
    private QueueConnectionFactory connectionFactory;

    @Override
    public void enqueueMail(SmsMessage smsMessage) {
        try {
            System.out.println("Going to send sms to queue In SmsManagerImpl,Acno is-->" + smsMessage.getActualAcNo()
                    + " And Mobile is-->" + smsMessage.getMobileNo());
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage message = session.createObjectMessage(smsMessage);
            QueueSender sender = session.createSender(queue);
            sender.send(message);

            System.out.println("Sms has been send to queue,Acno is-->" + smsMessage.getActualAcNo()
                    + " And Mobile is-->" + smsMessage.getMobileNo());
            connection.close();
        } catch (Exception e) {
            System.out.println("Error Sending SMS In SmsManagerImpl:: Acno is-->" + smsMessage.getActualAcNo()
                    + " And Mobile is-->" + smsMessage.getMobileNo() + " And Error is-->" + e.getMessage());
        }
    }
}
