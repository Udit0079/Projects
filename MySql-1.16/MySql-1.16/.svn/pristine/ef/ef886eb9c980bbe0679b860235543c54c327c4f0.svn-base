/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.loan;

import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface LoanGenralFacadeRemote {

    public java.util.List getCustname(java.lang.String accNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveGridData(java.util.List arraylist) throws com.cbs.exception.ApplicationException;

    public java.util.List selectDataForChkAuthClick(java.lang.String accNo) throws com.cbs.exception.ApplicationException;

    public java.util.List selectDemandDt(java.lang.String accNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String FnOtgChg(java.lang.String accNo, java.lang.String entryDate) throws com.cbs.exception.ApplicationException;

    public java.lang.String fnOtgInst(java.lang.String accNo, java.lang.String entryDate) throws com.cbs.exception.ApplicationException;

    public java.lang.String fnOtgPrn(java.lang.String accNo, java.lang.String entryDate) throws com.cbs.exception.ApplicationException;

    public java.lang.String finYear(java.lang.String currentDate, java.lang.String brncode) throws com.cbs.exception.ApplicationException;

    public java.lang.String acNature(java.lang.String acType) throws com.cbs.exception.ApplicationException;

    public java.util.List dateDiff(java.lang.String fromDt, java.lang.String toDt) throws com.cbs.exception.ApplicationException;

    public java.lang.String interestSaveUpdate(java.lang.String acno, java.lang.String inttabcode, double accPrefCr, double minIntRateCr, double maxIntRateCr, double accPrefDr, double minIntRateDr, double maxIntRateDr, java.lang.String intPegflag, int pegFreqMon, int pegFreqDays, java.lang.String fromDate, java.lang.String toDate, int effNoOfDays, java.lang.String user, java.lang.String curDate, java.lang.String createBy, java.lang.String createdt, String auth,String penalApply) throws com.cbs.exception.ApplicationException;

    public java.lang.String interestVerify(java.lang.String acno, String auth,String userName) throws com.cbs.exception.ApplicationException;
    
    public java.util.List interestCodeCombo() throws com.cbs.exception.ApplicationException;

    public java.util.List acctTypeCombo() throws com.cbs.exception.ApplicationException;

    public java.util.List tableData(java.lang.String acno) throws com.cbs.exception.ApplicationException;
    
    public java.util.List tableDataUnverified(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.util.List post(java.lang.String acctType, java.lang.String acno, java.lang.String user, java.lang.String date, java.lang.String frDt, java.lang.String toDt, java.lang.String orgnCode) throws com.cbs.exception.ApplicationException;

    public java.util.List accountDetail(java.lang.String acctNo) throws com.cbs.exception.ApplicationException;

    public java.util.List detailRoi(java.lang.String acctNo, java.lang.String brnCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String odEnhancementSave(java.lang.String ACNO, java.lang.String actype, float roi, float newroi, float penalroi, float penalty, float sanctionlimit, float newsanctionlimit, float maxlimit, float newmaxlimit, float adhoclimit, float newadhoclimit, float adhocroi, float newadhocroi, java.lang.String adhocappdt, java.lang.String newadhocappdt, java.lang.String adhoctilldt, java.lang.String newadhoctilldt, float subsidyamt, float newsubsidyamt, java.lang.String subsidyexpdt, java.lang.String newsubsidyexpdt, java.lang.String intopt, java.lang.String enterby, java.lang.String date) throws com.cbs.exception.ApplicationException;

    public java.util.List accountTypeDropDown() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveLienMarkingDetail(java.lang.Float ReceiptNo, java.lang.Float VchNo, java.lang.String Actype, java.lang.String AcNO, java.lang.String LAcNO, java.lang.String chkLien, java.lang.String AUTH, java.lang.String enteredby, java.lang.String remark, java.lang.String Loan_Lien_Call, java.lang.String tmpSecType, java.lang.String DLAccOpen_Lien, java.lang.String BillLcBg_Lien, String brnCode, String roiOnSecurity, String secODScheme, String margin, String additionalRoi) throws com.cbs.exception.ApplicationException;

    public java.lang.String tdLienPresentAmount(java.lang.String acno, java.lang.Float voucherno, java.lang.Float prinamount) throws com.cbs.exception.ApplicationException;

    public java.util.List gridDetailLoad(java.lang.String acno, java.lang.String acType) throws com.cbs.exception.ApplicationException;

    public java.util.List customerDetail(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.lang.String auth(java.lang.String userName, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String dlAcOpen() throws com.cbs.exception.ApplicationException;

    public java.util.List acctTypeComboLI() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveLoanRepaymentDetails(com.cbs.dto.loan.RepaymentShedulePojo pojo) throws com.cbs.exception.ApplicationException;

    public boolean checkAcno(java.lang.String acno, String brncode) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.loan.RepaymentShedulePojo> getSheduleList(java.lang.String acno) throws com.cbs.exception.ApplicationException;
    
    public java.util.List getUnVerifiedAcNo(String branchCode) throws com.cbs.exception.ApplicationException;
}
