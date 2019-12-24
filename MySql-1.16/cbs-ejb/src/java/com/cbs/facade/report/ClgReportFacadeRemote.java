package com.cbs.facade.report;

import com.cbs.dto.report.ClearingOperationReportPojo;
import com.cbs.dto.report.DemandDraftPayOrderPojo;
import com.cbs.dto.report.InwardChequeReturnPojo;
import com.cbs.dto.report.InwardClearingChequeReturnPojo;
import com.cbs.dto.report.InwardClearingPojo;
import com.cbs.dto.report.InwardClearingTodayDatePojo;
import com.cbs.dto.report.OutwardClearingBankwiseReportPojo;
import com.cbs.dto.report.OutwardClearingBatchReportPojo;
import com.cbs.dto.report.OutwardClearingEnteredReportPojo;
import com.cbs.dto.report.OutwardClearingSummaryReportPojo;
import com.cbs.dto.report.ho.InoutWardClearingPassReturnPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.CtsinwardinfoPojo;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ClgReportFacadeRemote {

    public List<InwardClearingTodayDatePojo> inwardClearingTodayDate(String emFlag, String date, String brnCode, String repName, String repType) throws ApplicationException;

    public List<InwardClearingPojo> inwardClearing(String emFlag, String date, String brnCode, String repName, String repType) throws ApplicationException;

    public List<OutwardClearingBankwiseReportPojo> getOutwardClearingBankwiseReport(String date, String reportName, String emFlag, String brCode) throws ApplicationException;

    public List<OutwardClearingSummaryReportPojo> getOutwardClearingSummaryReport(String date, String reportName, String emFlag, String brCode) throws ApplicationException;

    public List<OutwardClearingEnteredReportPojo> getOutwardClearingEnteredReport(String date, String reportName, String emFlag, String brCode, boolean hdfcFlag, String orderBy) throws ApplicationException;

    public List<OutwardClearingBatchReportPojo> getOutwardClearingBasedOnBatchReport(String date, String emFlag, String brCode, String orderBy) throws ApplicationException;

    public List circleType();

    public List<InwardClearingChequeReturnPojo> getInwardClearingCheque(String frmdt, String todt, String branchCode,String repoertType) throws ApplicationException;
    
    public int MergedRepType() throws ApplicationException;
    
    public List<InwardChequeReturnPojo> getInwardChequeReturn(String frmdt, String todt, String branchCode) throws ApplicationException;
    
    public List<DemandDraftPayOrderPojo> getDemandDraftPayOrderDetail(String frmdt, String todt, String branchCode,String modeType) throws ApplicationException;
        
    public List<ClearingOperationReportPojo> getClearingOperationData(String frmdt, String todt, String branchCode) throws ApplicationException;
    
    public List<InoutWardClearingPassReturnPojo> getInoutwardChkPassReturn(String repBased,String repType,String branchCode,String frmdt, String todt,String acWise,String acNo) throws ApplicationException;
    //Added by Manish Kumar
    public List getBranchList()throws ApplicationException;
    //------
    
    public List<CtsinwardinfoPojo> getctsinwardinfoData(String branchCode, String toDate ) throws ApplicationException;
}
