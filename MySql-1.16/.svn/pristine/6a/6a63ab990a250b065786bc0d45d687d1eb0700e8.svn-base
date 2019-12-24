/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.dto.AtmCardMappGrid;
import com.cbs.dto.AtmSecondryAccountDetail;
import com.cbs.dto.CustomerDetail;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.dto.report.CardFillingReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface MiscMgmtFacadeS1Remote {

    public List<AtmCardMappGrid> getAtmAccountDetail(String accountNo, String cardNo) throws Exception;

    public CustomerDetail getCustomerDetailByAccountNo(String accountNo) throws Exception;

    public String processAddAndModifyFirstSeqFile(String fileType, String delFlag, String primaryAccountNo, String txnLimitType,
            String encodingName, String embossingName, String cardType,String txnLimitCardType, String cardRelationship, String serviceCode, String kccEmvType,
            List<AtmSecondryAccountDetail> secondaryTable, String userName,String wdrlLimitAmount,String wdrlLimitCount,
             String purLimitAmount,String purLimitCount,String minLimit,String secondaryacNo) throws Exception;

    public String processDeleteFirstSeqFile(String fileType, String delFlag, String primaryAccountNo,
            List<AtmSecondryAccountDetail> secondaryTable, String userName) throws Exception;

    public String changeCardStatus(String fileType, String delFlag, String primaryAccountNo, String chnNo, String cardStatus,
            String userName) throws Exception;

    public String changeNames(String fileType, String delFlag, String primaryAccountNo, String chnNo, String encodingName,
            String embossingName, String userName) throws Exception;

    public String detailModification(String primaryAccountNo, String chnNo,String oldCardNo, String userName) throws Exception;

    public boolean isPrimaryTypeAccount(String accountNo) throws Exception;

    public String generateNewAddOnCard(String fileType, String dt, String todayDt, String userName, String orgCode,
            String filesLocation) throws Exception;

    public String generateBulkCardStatusAndNameChange(String fileType, String dt, String todayDt, String userName,
            String orgCode, String filesLocation) throws Exception;

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fileShowDt) throws Exception;

    public List<ExcelReaderPojo> getIdbiNeftReturnData(String txnType, String returnDate) throws Exception;

    public String generateIdbiIwReturn(String fileType, List<ExcelReaderPojo> returnData, String todayDt, String userName,
            String orgCode, String filesLocation) throws Exception;

    public List<ExcelReaderPojo> getIdbiOutwardData(String paymentType, String txnDt) throws Exception;

    public String generateIdbiOutward(String fileType, List<ExcelReaderPojo> returnData, String todayDt, String userName,
            String orgCode, String filesLocation) throws Exception;

    public List<CbsMandateDetailPojo> getOwMandateFeedReport(String brnCode, String txnType,String option ,String frDt,String toDt) throws Exception;

//    public List<AtmSecondryAccountDetail> getAtmSecondaryAccountDetail(String accountNo, String cardNo) throws Exception;

    public List getImpsOutwardDetailReport(String function, String frDt, String toDt) throws ApplicationException;
    
    public List<CardFillingReportPojo> getCadFillingReportDetail(String branchCode, String fromDate, String toDate) throws ApplicationException;
    
    
}
