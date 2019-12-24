package com.cbs.sms.service;

import javax.ejb.Remote;

@Remote
public interface SmsManager {

    public void enqueueMail(SmsMessage smsMessage);
}
