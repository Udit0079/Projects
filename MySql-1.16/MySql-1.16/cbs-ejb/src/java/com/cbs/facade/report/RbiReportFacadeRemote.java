/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.Soss3PortFolioPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaConsolidateAndAcWisePojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RbiReportFacadeRemote {
    
    public String getMinFinYear(String dt) throws ApplicationException;

    public AcctBalPojo getAcctsAndBal(AdditionalStmtPojo paramPojo) throws ApplicationException;

    public AcctBalPojo getAcctsAndBalAmtRangeWise(AdditionalStmtPojo params) throws ApplicationException;

    public AcctBalPojo getAcctsAndBalTimeRangeWise(AdditionalStmtPojo params) throws ApplicationException;
    
    public long getAccountStatusReport(String date, String brnCode, String acctCode, String acNat, String classification) throws ApplicationException ;

    public List<GlHeadPojo> getGLHeadAndBal(AdditionalStmtPojo params) throws ApplicationException;

    public AcctBalPojo getNpaAcctsAndBal(AdditionalStmtPojo params) throws ApplicationException;    
    
    public YearEndDatePojo getYearEndData(String brncode);

    public YearEndDatePojo getYearEndDataAccordingToDate(String brncode, String date);
    
    public YearEndDatePojo getYearEndDataAccordingFinYear(String brncode, String year);

    public List<GlHeadPojo> getOverDueAccountInBetweenDateAndTheirOutStandingBal(String frDt, String toDt, String acNature, String acType, String absParam, String tillDate, int rangeFrom, int rangeTo) throws ApplicationException;

    public List<GlHeadPojo> accountHavingSanctionAsPerAmount(String reportDt, String acNature, String acType, Double amount, String remarks) throws ApplicationException;

    public BigDecimal getValueFromFormula(List<RbiSossPojo> dataList, String formula) throws ApplicationException;
    
    public AcctBalPojo getNpaBasedOnAllNpaStatus(AdditionalStmtPojo params) throws ApplicationException ;
    
    public List<ProvDetailOfLoanAccounts> getProvisionAccordingToLoan(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;    
    
    public NpaConsolidateAndAcWisePojo getAmountBasedOnNpaClassification(AdditionalStmtPojo params) throws ApplicationException;
    
    public BigDecimal provisionAccordingToLoan(AdditionalStmtPojo params) throws ApplicationException;
}
