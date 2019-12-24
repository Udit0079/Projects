/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.BalanceSheetDTO;
import com.cbs.dto.report.CrrSlrPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.AdvncAgnstShareDebtPojo;
import com.cbs.dto.report.ho.AnnualNPAStmtConsolidate;
import com.cbs.dto.report.ho.DeducteeChallanPojo;
import com.cbs.dto.report.ho.DtlRegisterPojo;
import com.cbs.dto.report.ho.DuplicateCustIdPojo;
import com.cbs.dto.report.ho.StmtOfOpenCloseOfficePojo;
import com.cbs.dto.report.ho.UCBInvstmntInOtherUCBPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.ExpenditureBalDayWisePojo;
import com.cbs.pojo.GlHeadPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface HoReportFacadeRemote {

    /**
     *
     * @param repOpt
     * @param frDt
     * @param toDt
     * @return
     * @throws ApplicationException
     */
    public List<CrrSlrPojo> getCrrDetails(String brCode, String repOpt, String frDt, String toDt,String parameter) throws ApplicationException;

    /**
     *
     * @param repOpt
     * @param frDt
     * @param toDt
     * @return
     * @throws ApplicationException
     */
    public List<CrrSlrPojo> getSlrDetails(String brCode, String repOpt, String frDt, String toDt,String parameter) throws ApplicationException;
    public List<CrrSlrPojo> getSlrDetailsMis(String brCode, String repOpt, String frDt, String toDt,String parameter) throws ApplicationException;

    /**
     *
     * @param brCode
     * @param dt
     * @return
     * @throws ApplicationException
     */
    public BigDecimal getNewNdtl(String brCode, String dt) throws ApplicationException;
    
    public BigDecimal getTotalNdtl(String brCode, String dt) throws ApplicationException;
   
    /**
     *
     * @param dt
     * @return
     * @throws ApplicationException
     */
    public List<BalanceSheetDTO> getBalanceSheetData(String dt) throws ApplicationException;

    /**
     *
     * @param fridayDt
     * @return
     * @throws ApplicationException
     */
    public boolean isFridayDate(String fridayDt) throws ApplicationException;

    /**
     *
     * @param firstAltFriDt
     * @param monthLastDt
     * @return
     * @throws ApplicationException
     */
    public List getAllAlternateFriday(String firstAltFriDt, String monthLastDt) throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public List getFormOneStructure() throws ApplicationException;

    /**
     *
     * @param accode
     * @param dt
     * @return
     * @throws ApplicationException
     */
    public BigDecimal getAcCodeAmount(String accode, String dt) throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public List getFormNineStructure() throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public List getMonetaryAggregatesStructure() throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public Double getMonetaryAggregatesFdValue(String acType, String formula, String date) throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public List getCrrSlrParameter() throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public List getAnnextureStructure() throws ApplicationException;

    /**
     *
     * @param brCode
     * @param date
     * @return
     * @throws ApplicationException
     */
    public List getCrrSlrPercentage(String brCode, String date) throws ApplicationException;

    /**
     *
     * @param brCode
     * @param date
     * @param repOpt
     * @return
     * @throws ApplicationException
     */
    public List<DtlRegisterPojo> getDtlRegisterData(String brCode, String date, String repOpt) throws ApplicationException;

    public List maxReportFriday() throws ApplicationException;
    
    public List<GlHeadPojo> getAsOnDateBalanceList(String brCode, String date) throws ApplicationException;

    public GlHeadPojo getOSBalance(List<GlHeadPojo> balanceList, String acno, String cls) throws ApplicationException;

    public List<RbiSossPojo> getBalanceSheet(String reptName, List<String> dates, String orgnCode, BigDecimal repOpt, String summaryOpt,int staffNo) throws ApplicationException;

    public List<AnnualNPAStmtConsolidate> getAnnualNPAStmt(String brCode, String date, BigDecimal repOpt,String reportName) throws ApplicationException;

    public List<StmtOfOpenCloseOfficePojo> getStmtOfOpenCloseOffice(String brCode, String fromDt, String toDate) throws ApplicationException;

    public List<UCBInvstmntInOtherUCBPojo> getUCBsInvestmentInOtherUCB(String reportName, BigDecimal reptOpt, String brnCode, String asOnDate) throws ApplicationException;

    public List<AdvncAgnstShareDebtPojo> getAdvncAgnstShareAndDebt(String reportName, BigDecimal reptOpt, String brnCode, String asOnDate) throws ApplicationException;

    public List<DuplicateCustIdPojo> getDuplicateCustIdData(String reportType, String orderBy,String branchCode,String duplicateBy) throws ApplicationException;

    public List<RbiSossPojo> getMisDataToMgmt(String reptName, List<String> dates, String orgnCode, BigDecimal repOpt, String summaryOpt, int noOfEmpPreDate, int noOfEmpAsOnDate) throws ApplicationException;

    public List<DtlRegisterPojo> getDtlForInvestment(String brCode, String date, String repOpt) throws ApplicationException;
    
    public DeducteeChallanPojo getDeducteeChallanReturnData(String tdsOption, String frDt,String toDt, String bsrCode,String totalAmount,String submissionDate,String srNo,String voucherNo,String custType,String surCharge,String eduCess,String interest,String fee,String penaltyOthers,String minQuarterDt,String finYear,String branch) throws ApplicationException;
    
    public List<ExpenditureBalDayWisePojo> getLoanDepositAnnextureData(String branchCode,String reportType, String tillDate) throws ApplicationException;

    public List<ExpenditureBalDayWisePojo> getLoanDepositAnnextureDataSummary(String branchCode, String reportType, String tillDate) throws ApplicationException;
}
