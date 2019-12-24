/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.report.InterestReportPojo;
import com.cbs.dto.report.RbiInspectionPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface FdRdFacadeRemote {

    public List getFdRdNonEmi(String acNature, String acType, String transDt, String brCode) throws ApplicationException;

    public List getDistinctAcCodeByAcNature() throws ApplicationException;

    public List getAcNaturetldlcaList() throws ApplicationException;

    public List<RbiInspectionPojo> rbiInspectionData(String brnCode, String acNatue, String acType, String asOnDt) throws ApplicationException;

    public List<AccountStatementReportPojo> getAccountStatementRecieptWiseList(String acNo, String frDt, String toDt, String status, String voucherNo) throws ApplicationException;

    public List getVoucherNoByAcNo(String acNo, String status,String toDt) throws ApplicationException;

    public List<TdReceiptIntDetailPojo> getProjectedInterestTdsCalData(String brCode, String frDt, String toDt, double frAmt, double toAmt, String reportOption, String custId,String isSenior) throws ApplicationException;

    public List getVoucherPrintinALLgData(String branch, String reportOption, String date) throws ApplicationException;
    
    public double getSeniorCitizenAmt(String date) throws ApplicationException;
    
    public List<InterestReportPojo> getThriftInterestData(String brnCode, String area, String frDt, String toDt) throws ApplicationException;
             
}
