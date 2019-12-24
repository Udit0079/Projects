/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import com.cbs.dto.neftrtgs.NeftOwDetailsTO;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CPSMSMgmtFacadeRemote {

    public boolean isCPSMSModuleOn() throws ApplicationException;

//    public void cpsmsProcess() throws ApplicationException;
    public String printAdviceBatchReturnProcessing(String dt, String messageId, String batchNo) throws Exception;

    public String printAdviceBatchProcessing(String dt, String messageId, String batchNo, String userName,
            String orgnBrCode) throws Exception;

    public String generateInterBankResponse(String fileType, String paymentReceivedDt, String messageId, String batchNo,
            List<NeftOwDetailsTO> batchDetailList, String userName, String brnCode) throws Exception;
    
    public void accValQuickResponse() throws Exception;
    
    public void accValRegularResponse() throws Exception;
    
    public void generateDailyTransactionDetail() throws Exception;
    
    public void generateTransactionHistoryDetail() throws Exception;
}
