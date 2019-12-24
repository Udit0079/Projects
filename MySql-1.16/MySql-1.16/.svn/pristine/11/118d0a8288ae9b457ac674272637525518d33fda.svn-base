/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CPSMSCommonMgmtFacadeRemote {

//    public String getMessageWiseSeqNo(String messageType, String curDt) throws Exception;
    public String getOutwardFileName(String destination, String paymentProduct, String owMessageType,
            String dt, String iwMessageId) throws Exception;

    public List<String> getCpsmsHeaderDetail(String messageId) throws Exception;

    public String[] getCpsmsBankDetail() throws Exception;

    public String[] getOwSponsorBankName() throws Exception;

    public String getBankEmailId() throws Exception;
}
