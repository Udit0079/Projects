/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.RbiAlmPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.RevenueReportDrCrPojo;
import com.cbs.dto.report.RevenueStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import java.math.BigDecimal;
import javax.ejb.Remote;
import java.util.List;

/**
 *
 * @author root
 */
@Remote
public interface RbiMonthlyReportFacadeRemote {

//    public List<RbiSossPojo> getMonetaryAggregateDetails(String reportDt, BigDecimal repOpt,
//            String reportName, String orgnCode) throws ApplicationException;
    public GlHeadPojo getOSBalance(List<GlHeadPojo> balanceList, String acno) throws ApplicationException;

    public GlHeadPojo getOSBalanceWithClassification(List<GlHeadPojo> balanceList, String acno, String cls, String date) throws ApplicationException;

    public String getBranchWiseQuery(String brCode, String fromDt, String todt) throws ApplicationException;

    public List<RevenueStatementPojo> revenueStatement(String fromDt, String todt, String brCode, BigDecimal repOpt, String repType) throws ApplicationException;

    public RevenueReportDrCrPojo caRevenueBalance(String glHead, String classType, String acType, String fromDt, String toDt, String brnCode) throws ApplicationException;

    public String getAcctCodeWiseBal(AdditionalStmtPojo params) throws ApplicationException;

    public String getGLHeadBal(String brCode, String head, String date, String cl) throws ApplicationException;

    public String getForm1GLHeadBal(String brCode, String head, String date, String cl, String flag) throws ApplicationException;

    public String getGLHeadDrCr(String brCode, String head, String frDt, String todt, String cl) throws ApplicationException;

    public String cashInHandDrCr(String fromDt, String toDt, String brnCode) throws ApplicationException;

    public List<RbiSossPojo> getMonetaryAggregateDetails(String reportDt, BigDecimal repOpt,
            String reportName, String orgnCode) throws ApplicationException;

    public BigDecimal getMonetaryAggregatesFdValue(String acNature, String acType, String date, String rangeBaseOn,
            String fromRange, String toRange) throws ApplicationException;

    public List getStmtValue(String repOpt) throws ApplicationException;
    
    public List<RbiSossPojo> getForm1Details(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,
            String reportIn, String ReportFormat) throws ApplicationException;

    public List<GlHeadPojo> getAsOnDateBalanceList(String brCode, List<String> dates) throws ApplicationException ;
    
    public List<GlHeadPojo> getInsertedGlbList(List<String> dates) throws ApplicationException;
            
    public List<GlHeadPojo> getAsOnDateBalanceBetweenDateList(String brCode, String frDt, String toDt) throws ApplicationException;
    
    public List<RbiSossPojo> getForm1DetailsAsPerGlb(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,
            String reportIn, String reportFormat) throws ApplicationException;
    
    public GlHeadPojo setGlReportAmount(GlHeadPojo pojo, int i, BigDecimal bal) throws ApplicationException ;
    
    public RbiSossPojo setReportAmount(RbiSossPojo pojo, int i, BigDecimal bal) throws ApplicationException ;
    
    public BigDecimal[] getValueFromFormulaForFormOne(List<RbiSossPojo> dataList, String formula, List<String> dates)
            throws ApplicationException;
    
    public BigDecimal getOperandBalanceForFormOne(List<RbiSossPojo> dataList, String csvExp,
            int amtIndex) throws ApplicationException;
    
    public BigDecimal setOperandBalance(RbiSossPojo pojo, BigDecimal balance, int k) throws ApplicationException;
    
    public List<RbiSossPojo> getMonetaryReportDetails(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
      
    public Double getValueFromFormula(List<RbiAlmPojo> dataList, String formula) throws ApplicationException;

    public Double getOperandBalanceDouble(List<RbiAlmPojo> dataList, String csvExp) throws ApplicationException;
    
}
