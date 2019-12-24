/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.sms.service;

import com.cbs.utils.ServiceLocator;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(mappedName = "jms/smsQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class SmsMessageListener implements MessageListener {

    public SmsMessageListener() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            SmsMessage sendMessage = (SmsMessage) ((ObjectMessage) message).getObject();
            System.out.println("Received Message In MDB-->Acno is::" + sendMessage.getActualAcNo()
                    + " And Mobile is::" + sendMessage.getMobileNo());
            sendSms(sendMessage);
        } catch (Exception e) {
            System.out.println("Error In MDB onMessage() method-->" + e.getMessage());
        }
    }

    private void sendSms(SmsMessage sendMessage) throws Exception {
        try {
            SmsSender smsSender = (SmsSender) ServiceLocator.getInstance().lookup("SmsSenderImpl");
            smsSender.postSms(sendMessage);
        } catch (Exception e) {
            System.out.println("Error In MDB sendSms() method-->" + e.getMessage());
        }
    }
}
