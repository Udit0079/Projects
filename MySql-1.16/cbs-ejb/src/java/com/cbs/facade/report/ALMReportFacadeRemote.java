package com.cbs.facade.report;

import com.cbs.dto.report.AlmAnnexture1Table;
import com.cbs.dto.report.AlmFddetailPojo;
import com.cbs.dto.report.AlmTlRoiWiseReportPojo;
import com.cbs.dto.report.TlAccountWisePojo;
import com.cbs.dto.report.AlmTdAccountWiseReportPojo;
import com.cbs.dto.report.AlmTdRoiWiseReportPojo;
import com.cbs.dto.report.AlmTdRoiwiseOverdueReportPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface ALMReportFacadeRemote {

    public List<TlAccountWisePojo> tlAcWise(String date, String brCode) throws ApplicationException;

    public List<AlmTlRoiWiseReportPojo> getTlRoiwiseReport(String date, String brCode) throws ApplicationException;

    public List<AlmTdRoiwiseOverdueReportPojo> getAlmTdRoiWiseOverdueReport(String date, String brCode) throws ApplicationException;

    public List<AlmTdAccountWiseReportPojo> getAlmTdAccountWiseReport(String date, String brCode, boolean overdue) throws ApplicationException;

    public List<AlmTdRoiWiseReportPojo> getAlmTdRoiWiseReport(String date, String brCode) throws ApplicationException;

    /********************************************************************
    FOLLOWING THREE FUNCTIONS ARE FOR ALM ANNEXTURE 1 
    /********************************************************************/
    /** SELECT *FROM CBS_REP_ALM ('20040101','06','BRANCH')
     *  if the List return blank then either the REPTYPE is not 'BRANCH' or ACCOUNTYPEMASTER TABLE DO NOT
     *  CONTAIN APPROPRIATE DATA
     *  CONVERSION OF CBS_REP_ALM
     */
    public List<AlmAnnexture1Table> cbsRepAlm(String dt, String brCode, String repType) throws ApplicationException;

    /**
     * IMPLEMENTATIONS OF CBS_REP_GLB_DEBIT_BAL FUNCTION
     */
    public List cbsRepGlbDebitBal(String accode, String dt, String brncode) throws ApplicationException;

    public List hoAlm(String date, String brCode, String repOpt) throws ApplicationException;
    
  //  public List revenueStatement(String fromDt, String todt, String brCode, String repOpt) throws ApplicationException;
    
 //   public List revenueStatementOfPL(String fromDt, String todt, String brCode, String repOpt) throws ApplicationException;
    /********************************************************************
    END OF FUNCTIONS FOR ALM ANNEXTURE 1 
    /********************************************************************/
    public List hoAlmInterestSensitivty(String date, String brCode, String repOpt) throws ApplicationException;

    public double cashInHandDr(String fromDt, String toDt, String brnCode) throws ApplicationException;
    
    public double cashInHandCr(String fromDt, String toDt, String brnCode) throws ApplicationException;
    
    public List caRevenueBalance(String glHead, String classType, String acType, String fromDt, String toDt, String brnCode) throws ApplicationException;
    
    public List<AlmFddetailPojo> getAlmFdDetail(String brCode,String nature,String bucketNo,String tillDt,String bucketBase,String acType,String frDt,String toDt, String dlCase) throws ApplicationException;
}
