package com.cbs.facade.common;

import com.cbs.dto.DividendTable;
import com.cbs.dto.ckycr.CKYCRDownloadDetail30;
import com.cbs.dto.ckycr.CKYCRDownloadDetail60;
import com.cbs.dto.other.DenominationDetailTo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.CKYCRDownloadPojo;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InterBranchTxnFacadeRemote {

    /**
     *
     * @param acNo
     * @param tranType
     * @param ty
     * @param amt
     * @param dt
     * @param valueDt
     * @param enterBy
     * @param orgBrCode
     * @param destBrCode
     * @param tranDesc
     * @param details
     * @param trsNo
     * @param recNo
     * @param tranId
     * @param termId
     * @param auth
     * @param authBy
     * @param subTokenNo
     * @param payBy
     * @param instNo
     * @param instDt
     * @param tdacNo
     * @param vchNo
     * @param intFlag
     * @param closeFlag
     * @param screenFlag
     * @param txnStatus
     * @param tokenNoDr
     * @param cxSxFlag
     * @return
     */
    public String ftsPosting43CBS(String acNo, Integer tranType, Integer ty, Double amt, String dt, String valueDt,
            String enterBy, String orgBrCode, String destBrCode, Integer tranDesc, String details, Float trsNo,
            Float recNo, Integer tranId, String termId, String auth, String authBy, String subTokenNo, Integer payBy,
            String instNo, String instDt, String tdacNo, Float vchNo, String intFlag, String closeFlag,
            String screenFlag, String txnStatus, Float tokenNoDr, String cxSxFlag, String adviceNo, String adviceBrnCode) throws ApplicationException;

    /**
     *
     * @param acctNo
     * @param amount
     * @param Userid
     * @return
     */
    public String checkBalance(String acctNo, double amount, String Userid) throws ApplicationException;

    /**
     *
     * @param acctNo
     * @return
     */
    public String ftsGetBalRemote(String acctNo) throws ApplicationException;

    /**
     *
     * @param acNo
     * @param ty
     * @param valueDt
     * @param amt
     * @param bal
     * @param tranType
     * @param details
     * @param tokenNo
     * @param subTokenNo
     * @param instNo
     * @param instDt
     * @param payBy
     * @param vchNo
     * @param recNo
     * @param tranDesc
     * @param destBrCode
     * @param orgnBrCode
     * @param enterBy
     * @param authBy
     * @param trSNo
     * @return
     */
    public String cbsPostingCx(String acNo, Integer ty, String valueDt, double amt, double bal, Integer tranType, String details,
            Float tokenNo, String subTokenNo, String instNo, String instDt, Integer payBy,
            Float vchNo, Float recNo, Integer tranDesc, String destBrCode, String orgnBrCode,
            String enterBy, String authBy, Float trSNo, String adviceNo, String adviceBrnCode) throws ApplicationException;

    /**
     *
     * @param acNo
     * @param ty
     * @param valueDt
     * @param amt
     * @param bal
     * @param tranType
     * @param details
     * @param tokenNo
     * @param subTokenNo
     * @param instNo
     * @param instDt
     * @param payBy
     * @param vchNo
     * @param recNo
     * @param tranDesc
     * @param destBrCode
     * @param orgnBrCode
     * @param enterBy
     * @param authBy
     * @param trSNo
     * @return
     */
    public String cbsPostingSx(String acNo, Integer ty, String valueDt, double amt, double bal, Integer tranType, String details,
            Float tokenNo, String subTokenNo, String instNo, String instDt, Integer payBy,
            Float vchNo, Float recNo, Integer tranDesc, String destBrCode, String orgnBrCode,
            String enterBy, String authBy, Float trSNo, String adviceNo, String adviceBrnCode) throws ApplicationException;

    /**
     *
     * @param acno
     * @param dt
     * @return
     * @throws ApplicationException
     */
    public String fnGetLoanStatus(String acno, String dt) throws ApplicationException;

    /**
     *
     * @param acno
     * @param dt
     * @return
     * @throws ApplicationException
     */
    public String fnGetLoanStatusTillDate(String acno, String dt) throws ApplicationException;

    /**
     *
     * @param acno
     * @param dt
     * @return
     * @throws ApplicationException
     */
    public String fnGetLoanStatusOfAccountTillDate(String acno, String dt) throws ApplicationException;

    /**
     *
     * @param acno
     * @param dt
     * @param crAmt
     * @param enterBy
     * @param orgnBrCode
     * @param destBrCode
     * @param valueDate
     * @return
     * @throws com.cbs.exception.ApplicationException
     */
    public String npaRecoveryAmtUpdation(String acno, String dt, double crAmt, String enterBy, String orgnBrCode, String destBrCode, String valueDate, float trsNo) throws com.cbs.exception.ApplicationException;

    /**
     *
     * @param acno
     * @param fromDt
     * @param toDt
     * @return
     * @throws ApplicationException
     */
    public String fnGetLoanStatusBetweenDate(String acno, String fromDt, String toDt) throws ApplicationException;

    /**
     *
     * @param acNo
     * @return
     * @throws ApplicationException
     */
    public String npaPOrdChk(String acNo) throws ApplicationException;

    public String insertIntoNeftStatusTable(String alertMsg, String process, String neftBankName,
            Float trsNo, String user, ExcelReaderPojo pojo) throws ApplicationException;

    public String insertNeftBulkTransaction(String process, String neftBankName,
            Float trsNo, String user, String orgnBrCode, ExcelReaderPojo pojo, String isoAccount, String orgnAlphaCode) throws ApplicationException;

    public String insertDividendTxn(Float trsNo, float recNo, String user, String orgnBrCode, DividendTable pojo, String isoAccount,
            String orgnAlphaCode, String details) throws ApplicationException;

    public Map<String, Double> getTaxComponent(Double chargeAmount, String applicableDt) throws ApplicationException;

    public Map<String, Double> getIgstTaxComponent(Double chargeAmount, String applicableDt) throws ApplicationException;

    public Map<String, Double> getTaxComponentSlab(String applicableDt) throws ApplicationException;

    public Map<String, Double> getIgstTaxComponentSlab(String applicableDt) throws ApplicationException;

    public String emiPaidMarkingDt(String acNo, String dt) throws Exception;

    public String insertBulkDenominationDetail(List<DenominationDetailTo> denominationList) throws ApplicationException;

    public String updateOpeningDenomination(String brnCode, BigDecimal denomination, int denoValue,
            String dt, String newOldFlag) throws ApplicationException;

    public String insertDenominationDetail(String acno, float recNo, String dt, BigDecimal denomination,
            int denoValue, int ty, String brnCode, String enteryBy, String newOldFlag) throws ApplicationException;

    public String deleteDenomination(float recNo, String dt) throws ApplicationException;

    public void insertCKYCRDownload(CKYCRDownloadPojo pojo) throws Exception;

    public void insertCKYCRDownloadDetail30(CKYCRDownloadDetail30 pojo) throws Exception;

    public void insertCKYCRDownloadDetail60(CKYCRDownloadDetail60 pojo) throws Exception;

    public String postACHDRreturnChargesAndTax(String accNo, String username, float trsno, String loginBrCode, String dt) throws ApplicationException;

    public String postACHDRSuccessEntry(String accNo, double creditAmount, String username, float trsno) throws ApplicationException;

    public String postECSChargesAndTax(String CBS_Umrn, String accNo, String username, float trsno, String loginBrCode, String dt) throws ApplicationException;

    public String postECSDRSuccessEntry(String CBS_Umrn, String accNo, double creditAmount, String username, float trsno) throws ApplicationException;
    
    public BigDecimal getCustomerCashWithdrawal(String custIds, String frDt, String toDt) throws ApplicationException;
    
    public BigDecimal getCustomerTdsOnCashWithdrawal(String custIds, String frDt, String toDt, BigDecimal curAmt, String auth) throws ApplicationException;
    
    public BigDecimal getTdsToBeDeductedForCustomer(String panNo, String custId, String fromDt, String toDt, BigDecimal curAmt, String auth) throws ApplicationException;
    
    public BigDecimal getTdsToBeDeductedForCustomer(String acNo, String fromDt, String toDt, BigDecimal curAmt, String auth) throws ApplicationException;
    
    public String tdsDeductionForCashWithdrawal(String partyNature, String partyAc, double tdsAmt, String enterBy, String orgnBrCode) throws ApplicationException;
}
