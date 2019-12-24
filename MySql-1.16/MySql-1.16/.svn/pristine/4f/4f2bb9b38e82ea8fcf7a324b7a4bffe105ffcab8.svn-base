/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.dto.sms.SmsDetailsDto;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.sms.MessageDetailPojo;
import com.cbs.web.pojo.sms.AcSenderMsgPojo;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface MessageDetailBeanRemote {

    public List<MessageDetailPojo> getMessageDetail(String fromDate, String toDate) throws ApplicationException;

    public List<SmsDetailsDto> getAcWiseSmsDetails(String reportType, String acno,
            String frDt, String toDt) throws ApplicationException;

    public List getMobileDetail(String brCode, String msgType) throws ApplicationException;

    public List getProductWiseDetail(String brCode, String filterType, String acctnature, String acctcode) throws ApplicationException;

    public String getMobileNumberByAcno(String acno) throws ApplicationException;

    public List sendBirthDaySms(String birthDay, String birthMonth) throws ApplicationException;

    public String getCustomerIdByAccountNo(String acno) throws ApplicationException;

    public List<AcSenderMsgPojo> getAccountSmsDetail(String frdt, String todt, String acno,String detailType) throws ApplicationException;

    public List sendKycExpAlertSms(String date) throws ApplicationException;
}
