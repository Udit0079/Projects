/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface TxnAuthorizationManagementFacadeRemote {

    public List getCashCrDataToAuthorize(String todayDate, String orgBranchCode) throws ApplicationException;

    public String cbsAuthCashCr(float recNo, String authBy, int msgNo, int msgBillStart, String status, int msgBillPo,
            int msgBillEnd, String date, float seqNo, String instNo, String billType, String acno, String custName,
            String payableAt, double amount, String dt1, String originDt, int timeLimit, double comm, int tranType, int ty,
            String inFavourOf, String enterBy, String lastUpdateBy, String orgnBrCode, String destBrCode, String valueDate)
            throws ApplicationException;

    public String cbsAuthCashCrDeletion(float recno, String instno, String acno, double amount,
            String dt, String custName, String subtokenno, float tokenno, String origindt, String enterby,
            String authby, String orgCode) throws ApplicationException;

    public String cbsCashCrDestAuth(float recno, String authBy, String enterBy, String orgCode) throws ApplicationException;

    public List getUnAuthCashdr(String orgnBrCode) throws ApplicationException;

    public String cbsAuthCashDr(float recno, String auth1, int msgNo, int msgBillStart, String status, int msgBillPO,
            int msgBillEnd, String date, float seqNo, String instno, String billType, String acno, String custname,
            String payableAt, double amount, String dt, String originDt, int timeLimit, double comm, int tranType, int ty,
            String inFavourOf, String enterBy, String lastUpdateBy, String orgnBrCode, String valueDate, String InstrDt, 
            BigDecimal tdsToBeDeducted )
            throws ApplicationException;

    public String CbsAuthCashDrDeletion(float recNo, String instNo, String acNo, double amount, String dt,
            String custName, String subTokenNo, float tokenNo, String originDt, String enterBy,
            String authBy, String orgCode) throws ApplicationException;

    public String cbsCashDrDestAuth(float recno, String authBy, String enterBy, String orgCode,BigDecimal tdsToBeDeducted)
            throws ApplicationException;

    public String getSignatureDetails(String acno);

    public List getDataForSignature(String accNo) throws ApplicationException;

    public List selectForOperationMode(String opmode);

    public List getUserAuthorizationLimitCash(String userName, String orgBrnCode);

    public List getUserAuthorizationLimitClearing(String userName, String orgBrnCode);

    public List tableAuthorize(String date, int ty, String orgnCode) throws ApplicationException;

    public String cbsAuthDeletion(float recNo, String instNo, String acNo, double CrAmt, double DrAmt, double Amount, String dt, String custName, String subtokenno,
            float tokenno, String originDt, String enterby, String deleteby, int tranType, String brCode, String option)
            throws ApplicationException;

    public String cbsAuthCleringDrCr(float recno, String Auth1, int MsgNo, int Msg_Bill_Start, String Status, int Msg_Bill_PO, int Msg_Bill_End, String date, float SeqNo, String instno, String BillType, String Acno,
            String custname, String PAYABLEAT, double Amount, String dt, String ORIGINDT, int TimeLimit, double comm, int TranType, int ty, String InFavourOf, String enterby, String LastUpdateBy, String option, String brCode)
            throws ApplicationException;

    public List getUnAuthXferDetails(String orgnBrCode) throws ApplicationException;
    
    public String getTranDescription(int tranDesc) throws ApplicationException;

    public String cbsAuthTransfer(String dt, Float lblBatch, String enterByPage, String orgnBrnCode) throws ApplicationException;

    public List getAcType() throws ApplicationException;

    public List getUnAuthPoDetails(String date, String orgnBrCode, String acType) throws ApplicationException;

    public String poAuthorization(Float recNo, String acNo, Float seqNo, String billType, double amt, String status, String dt, String auth1, String brCode)
            throws ApplicationException;

    public String cbsCoreTrfAuth(Float batchNo, String authBy) throws ApplicationException;

    public java.lang.String authLocalTransaction(int trSNo, java.lang.String AuthBy, java.lang.String dt, java.lang.String orgnBrnCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String cbsAuthTransferObcProcess(java.lang.String billNo, java.lang.String dt, java.lang.String instNo, java.lang.String enterBy, java.lang.Float trsno, java.lang.Float recno, double cramt, double dramt, java.lang.String orgnBrCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String cbsSecAuthVal(java.lang.String sAuthFlag, double sTranAmt, java.lang.Integer sty, java.lang.String sCheckBy, java.lang.String sMsg, java.lang.String orgnCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String authTransferTrans(java.lang.Float trsNo, java.lang.Integer ty, java.lang.Float recno, java.lang.String orgnBrCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String cbsDestEntryIdentification(java.lang.Float trSNo);

    public java.lang.String cbsAcStatus(java.lang.String acNo) throws com.cbs.exception.ApplicationException;

    public String cbsFdAcTranChk(String acNo) throws com.cbs.exception.ApplicationException;
    
    public String insertFidilityTran(String actNature, String acno, Integer ty, double amt, String dt, String valueDt, Integer tranType, String details,
            String enterBy, Float trsno, String tranTime, Float recno, String auth, String authBy, Integer tranDesc, Integer payBy,
            String instno, String instDt, Float tokenNo, String tokenPaidBy, String subTokenNo, Integer iy, String tdAcno, Float voucherNo,
            String intFlag, String closeFlag, String orgBrnId, String destBrnId, Integer tranId, String termId, String adviceNo, String adviceBrnCode) throws ApplicationException;
    
    public String fdFidilityChk(String acNo) throws com.cbs.exception.ApplicationException;
    
    public String idMergeSave(String orgCode, String mergCode, String enterBy, String brCode) throws ApplicationException;
    
    public List getUnAuthMergId(String orgCode) throws ApplicationException;
    
    public String mergeIdDeletion(String orgCode, String mergCode, String authBy) throws ApplicationException;
    
    public String mergeIdAuth(String orgCode, String mergCode, String enterBy, String authBy,String orgBrCode) throws ApplicationException;
    
    public String npaPOrdChk(String acNo) throws com.cbs.exception.ApplicationException;
    
    public void sendCCODExceedSms(String acno, double amount) throws ApplicationException;
    
    public List getDenoDateToShow(String acno, double recNo, String dt) throws ApplicationException;
}
