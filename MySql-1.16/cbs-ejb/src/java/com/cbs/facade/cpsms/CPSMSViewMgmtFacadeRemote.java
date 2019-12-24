/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import com.cbs.dto.cpsms.CpsmsCommonDTO;
import com.cbs.dto.neftrtgs.NeftOwDetailsTO;
import com.cbs.dto.report.CPSMSBatchDetailPojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CPSMSViewMgmtFacadeRemote {

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileDetail(String dt, String brncode) throws Exception;

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchDetail(String dt, String messageId, String batchNo) throws Exception;

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchDrDetail(String dt, String messageId, String batchNo) throws Exception;

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchCrDetail(String dt, String messageId, String batchNo) throws Exception;

    public List isIntraBankAccountNo(String acno) throws Exception;

    public CpsmsCommonDTO getPrintAdviceBatchDetail(String dt, String messageId, String batchNo) throws Exception;

    public List<String> retrievePaymentReceivedDates(String fileType, String brnCode) throws Exception;

    public List<String> retrievePaymentReceivedMessageId(String fileType, String brnCode, String paymentReceivedDt) throws Exception;

    public List<String> retrievePaymentReceivedBatchNo(String fileType, String brnCode, String paymentReceivedDt, String messageId) throws Exception;

    public List<NeftOwDetailsTO> getInterBankOutwardDetail(String fileType, String paymentReceivedDt, String messageId, String batchNo, String brnCode) throws Exception;

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchDrDetailForIBResponse(String dt, String messageId, String batchNo) throws Exception;

    public List getcpsmsMassageId(String date) throws Exception;

    public List getcpsmsBatchNo(String date, String cpsmsMsgId) throws Exception;

    public String[] getcpsmsBatchNoStatusAndTxnNo(String date, String cpsmsMsgId, String cpsmsBatchNo) throws Exception;

    public List<CPSMSBatchDetailPojo> getCPSMSBatchDetailReport(String date, String cpsmsMsgId, String cpsmsBatchNo) throws Exception;
}
