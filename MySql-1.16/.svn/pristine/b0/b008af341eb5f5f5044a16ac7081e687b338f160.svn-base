/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.investment;

import com.cbs.dto.report.InvestmentMutualFundPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.GoiReportPojo;
import com.cbs.pojo.BucketWiseInvestmentPojo;
import com.cbs.pojo.CallMoneydetailPojo;
import com.cbs.pojo.DailyCallMoneyPojo;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface InvestmentReportMgmtFacadeRemote {

    /**
     * *
     *
     * @param reportType
     * @param fromDt
     * @param toDt
     * @return
     * @throws ApplicationException
     */
    public List<InvestmentMaster> getDueReportBetweenRange(String reportType, Date fromDt, Date toDt) throws ApplicationException;

    public List<InvestmentMaster> getDueReportTillDate(String reportType, Date tilldate) throws ApplicationException;

    public List<String> getSellarName() throws ApplicationException;

    public List<InvestmentMaster> getAllReportTypeAllSellarName(Date frDt,Date asOnDt, String sType,String status) throws ApplicationException;

    public List<InvestmentMaster> getAllReportTypeIndivisualSellarName(String sellar,Date frDt,Date asOnDt,String status) throws ApplicationException;

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarName(String repType, Date asOnDt) throws ApplicationException;
    
    public List<InvestmentMaster> getIndivisualReportTypeAllSellarNameGoi(String repType,Date frDt,Date asOnDt,String status) throws ApplicationException;

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarNameAndMArking(String repType, String marking, Date asOnDt) throws ApplicationException;

    public List<GoiReportPojo> getIndivisualReportTypeAllSellarNameAndMArking1(String repType, String marking,String asOnDt) throws ApplicationException;
    
    
    
    
    public List<InvestmentMaster> getIndivisualReportTypeReportTypeIndivisualSellarName(String repType, String sellar,Date frDt, Date asOnDt,String status) throws ApplicationException;

    public List<String> getSName(String repType) throws ApplicationException;

    public List<Object[]> getAllInvestmentMasterSecurityMaster(String repType, Date maxTillDt) throws ApplicationException;
    
    public List<Object[]> getInvestmentMasterSecurityMaster(String repType, String sellerName,Date frDt, Date maxTillDt,String status) throws ApplicationException;
    
    public List<Object[]> getAllInvestmentMasterSecurityMasterGoiInt(String repType,Date frDt,Date maxTillDt,String status) throws ApplicationException;

    public List<Object[]> getDatesAndDetailsForInterestRecreport() throws ApplicationException;

    public List<Object[]> getDueDateReportActive(Date frDt) throws ApplicationException;
    
    public List<Object[]> getDueDateReportActive1(Date frDt) throws ApplicationException; 

    public List<Object[]> getDueDateReportRenewed() throws ApplicationException;

    public List<Object[]> getDueDateReportClose(Date frDt, Date toDt) throws ApplicationException;

    public List<Object[]> getDueDateReportActiveSellarName(String sellerName) throws ApplicationException;

    public List<InvestmentMaster> getAllDueReportBetweenRange(String reportType, Date fromDt, Date toDt) throws ApplicationException;

    public List<InvestmentMaster> getAllDueReportTillDate(String reportType, Date tilldate) throws ApplicationException;

    public List<Object[]> getInvestMastSecAll(String sellerName, Date frDt,Date maxTillDt,String status) throws ApplicationException;

    public List<Object[]> getAllInvestMastSec(Date frDt,Date maxTillDt,String status) throws ApplicationException;

    public List<Object[]> getAllInvestmentMasterSecurityMasterAccrPost(String repType, Date maxTillDt) throws ApplicationException;

    public List<BucketWiseInvestmentPojo> getBucketWiseInvestmentdata(String report, String toDt) throws ApplicationException;

    public List<CallMoneydetailPojo> getCallMoneydata(String fromDt, String toDt) throws ApplicationException;

    public List<String> getBankName() throws ApplicationException;

    public List<Object[]> getDatesAndDetailsForInterestRecreportBankWise(String bnkName, String status) throws ApplicationException;

    public List<Object[]> getDatesAndDetailsForInterestRecreportAll(String status) throws ApplicationException;

    public List<Object[]> getDatesAndDetailsForInterestRecreportBankAll(String bnkName) throws ApplicationException;

    public List<DailyCallMoneyPojo> getDailyCallMoneydata(String firstAltDt, String secondAltDt, String reportIns) throws ApplicationException;

    public List<DailyCallMoneyPojo> getDailyCallMoneyAvgData(String firstAltDt, String secondAltDt, String reportIns) throws ApplicationException;

    public double getAmortAmt(Integer ctrlNo, String tillDt) throws ApplicationException;

    public double getAmortAmtBetweenDate(Integer ctrlNo, String fromDt, String tillDt) throws ApplicationException;

    public String getMarkByControlNo(Integer ctrlNo, String secType, String tillDt) throws ApplicationException;

    public List getBankingType() throws ApplicationException;
    
    public List<InvestmentMutualFundPojo> getInvestmentFundData(String reportStaus, String bankType, String frDate, String toDate) throws ApplicationException;

    public List<Object[]> getDueDateReportObtained(Date frDt, Date toDt) throws ApplicationException;
    
    public double getReceviableAmt(Integer ctrlNo, String tillDt) throws ApplicationException;
    
    public double getReceGoiAmt(Integer ctrlNo, String tillDt) throws ApplicationException;

    public List getRecivableDate(Integer ctrlNo) throws ApplicationException;

    
}
