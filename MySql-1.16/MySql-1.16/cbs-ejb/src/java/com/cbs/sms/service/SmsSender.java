/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.sms.service;

import com.cbs.dto.sms.BulkSmsTo;
import java.io.File;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SmsSender {

    public void postSms(SmsMessage sms);

    public void sendSmsBeyondCbs(SmsMessage sms);

    public String postBulkSmsByGupshup(List<File> files, List<BulkSmsTo> bulkSmsList, String bulkMode, File originalFile, String userName,
            String orgnCode, String todayDt) throws Exception;

    public String postBulkSmsByDigialaya(List<String> allXmlString, List<BulkSmsTo> bulkSmsList, String bulkMode, File originalFile,
            String userName, String orgnCode, String todayDt) throws Exception;
}
