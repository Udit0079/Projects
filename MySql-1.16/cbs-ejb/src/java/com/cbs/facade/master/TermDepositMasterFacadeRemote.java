/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.master;

import com.cbs.dto.TdIntDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.TdEntry;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface TermDepositMasterFacadeRemote {
    //TdConditionBean Methods

    public List tableDataTdCondition() throws ApplicationException;

    public String gridClick(String custType, float sNum) throws ApplicationException;

    public String upDate(String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, float sNum, String userName) throws ApplicationException;

    public String save(String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, String authBy) throws ApplicationException;

    //TdsSlabBean Methods
    public List tableDataTdsSlab() throws ApplicationException;

    public String save(String glAcno, String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, String authBy,String tdsRatePan,String srctznTdsAmt) throws ApplicationException;

    public String gridClick(String custType, int sNum) throws ApplicationException;

    public String upDate(String glAcno, String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, int sNum,String tdsRatePan,String srctznTdsAmt) throws ApplicationException;

    public String checkGlTds(String glAcno) throws ApplicationException;

    //Td15hEntryBean Methods
    //public List accountDetails(String accountNo) throws ApplicationException;
    public List accountDetails(String custid, String opt) throws ApplicationException;

    public List fYearData(String brCode) throws ApplicationException;

    //public List tableData(String accountNo) throws ApplicationException;
    //public List tableData(String accountNo, String fYr) throws ApplicationException;
    public List tableData(String custId, String brnId, String Opt) throws ApplicationException;

    //public String upDateData(String date, String details, int seqnum, String brcode, String fYears) throws ApplicationException;
//    public String upDateData(String date, String details, int seqnum, String brcode, String fYears, String tdsOpt, String acno) throws ApplicationException;
    public String upDateData(String date, List<TdEntry> details, String fYears, String custId, String user) throws ApplicationException;

    //public String saveData(String date, String details, String acno, String userName, String brcode, String fYears) throws ApplicationException;
    public String saveData(String date, List<TdEntry> details, String custId, String userName, String brcode, String fYears, String tdsOpt, double totalNoForm, double aggregateAmount, double otherIncome, double deductionAmt, double estimatedIncome, String assessmentYear,String taxOption) throws ApplicationException;

    // public List dateDiffStatementFreqDate(String statementFreqDate) throws ApplicationException;
    //TermDepositSlabBean Methods
    public List getTableDetails(String acNat) throws ApplicationException;

    public List getTableHistry(String acNat) throws ApplicationException;

    public List returnCurrentItem() throws ApplicationException;

    public List returnCurrentDateItem(String acNat) throws ApplicationException;

    public String deleteData(int seqNo) throws ApplicationException;

    public List dateDiff(String ds) throws ApplicationException;

    public String saveRecord(String dateOfEffect, String roi, String fromDays, String toDays, String fromAmt, String toAmt, String scn, String stn, String otn, String fromDaysDetails, String toDaysDetails, String enterBy, boolean value, String SeqenceNo, String acNat, String mgn) throws ApplicationException;

    public List customerData(String custId,String finMinDt) throws ApplicationException;

    public String deleteData(String date, String fYears, String custId) throws ApplicationException;

    public String verifyData(List<TdEntry> details, String custId, String userName, String tdsDoc) throws ApplicationException;

    public String authFlagChk(String custId) throws ApplicationException;

    public List getDetailsList(String brCode) throws ApplicationException;

    public List customerValidateData(String custId) throws ApplicationException;

    public List tdsAcno(String custId) throws ApplicationException;

    public String getTotalIntAmtDuringFinYear(String acno, String frDt, String toDt, float voucherNo) throws ApplicationException;

    public List<TdIntDetail> rdAcWiseIntCalc(String acNo, String fromDate, String toDate, String brCode) throws ApplicationException;

    public List form15G15HChecking(String custId) throws ApplicationException;

    public List minorIdData(String custId) throws ApplicationException;

    public List getcustEntityType(String custId) throws ApplicationException;
    
    public double getRdProjectedInt(String acNo, String fromDate, String toDate) throws ApplicationException;
    
    public String getAfterHolyDate() throws ApplicationException;
    
    public List isHolyDays(String date) throws ApplicationException;
}
