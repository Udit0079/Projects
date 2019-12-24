/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.investment;

import com.cbs.entity.ho.investment.GoiRecon;
import com.cbs.entity.ho.investment.InvestmentAmortPostHistory;
import com.cbs.entity.ho.investment.InvestmentAmortizationDetails;
import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentCallMaster;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.ho.investment.InvestmentFrdDatesAndDetailsHistory;
import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.ho.investment.InvestmentSecurityAuthDetail;
import com.cbs.entity.ho.investment.InvestmentSecurityMaster;
import com.cbs.entity.ho.investment.InvestmentShare;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.transaction.GlRecon;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.FdrIntPostPojo;
import com.cbs.pojo.GoiIntReportPojo;
import com.cbs.pojo.InvestmentFDRCloseRenewAuthPojo;
import com.cbs.pojo.InvestmentFDRInterestPojo;
import com.cbs.pojo.SecurityAuthClosePojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface InvestmentMgmtFacadeRemote {

    /**
     * 
     * @param maturityYear
     * @return
     * @throws ApplicationException 
     */             
    public List<InvestmentSecurityMaster> getInvestmentSecurityToModify(String maturityYear) throws ApplicationException;

    public List<InvestmentSecurityMaster> getInvestmentSecurityToDelete(String maturityYear) throws ApplicationException;

    public String saveInvestmentSecurityMaster(InvestmentSecurityMaster entity) throws ApplicationException;

    public List<InvestmentSecurityMaster> checkDuplicate(String maturityYear, double roi, String secrityName) throws ApplicationException;

    public String deleteInvestmentSecurityMaster(Long sno, String lastUpdateBy, Date curDt) throws ApplicationException;

    public String modifyInvestmentSecurityMaster(InvestmentSecurityMaster entity) throws ApplicationException;

    public List<BranchMaster> getAllAlphaCode() throws ApplicationException;

    public List<GlDescRange> getGlRange(String glName) throws ApplicationException;

    public List<String> getSecurityType(String fromNo, String toNo) throws ApplicationException;

    public List<InvestmentGoidates> getControllNo(String secType) throws ApplicationException;

    public Object[] blurOnControllNo(int ctrlNo, String securityType) throws ApplicationException;

    public List<InvestmentAmortizationDetails> getAmortMode(Long ctrlNo) throws ApplicationException;

    public Long getTotalSumOfDays() throws ApplicationException;

    public BigDecimal getTotalSumOfAmortAmt(Date years) throws ApplicationException;

    public InvestmentSecurityMaster getInvestmentSecurityMaster(String securityName) throws ApplicationException;

    public Integer getMaxCtrlNoByInvestmentMaster() throws ApplicationException;

    public String updateInvestMasterByCtrlNo(Date curDt, int ctrlNo) throws ApplicationException;

    public String updateInvestGoiDatesByCtrlNo(Date closedt, int ctrlNo) throws ApplicationException;

    public List<Gltable> getGltable(String acname) throws ApplicationException;

    public String saveGlRecon(GlRecon entity) throws ApplicationException;

    public String saveGoiRecon(GoiRecon entity) throws ApplicationException;

    public String updateAmortDetailsByCtrlNo(BigInteger oldCtrlNo, Long ctrlNo, Date closedt) throws ApplicationException;

    public InvestmentMaster getInvestMaster(int ctrlNo, String secType) throws ApplicationException;

    public String saveInvestmentMaster(InvestmentMaster entity) throws ApplicationException;

    public InvestmentGoidates getInvestmentGoidates(Integer ctrlno) throws ApplicationException;

    public String saveInvestmentGoidates(InvestmentGoidates entity) throws ApplicationException;

    public List<InvestmentGoidates> getControllNoForAmortCalc(String type) throws ApplicationException;

    public Object[] getCtrlNoDetailsAmortcalc(Integer ctrlNo) throws ApplicationException;

    public String saveInvestmentAmortizationDetails(InvestmentAmortizationDetails entity) throws ApplicationException;

    public BranchMaster getBranchMasterByAlphacode(String alphacode) throws ApplicationException;

    public String saveFaceEqualSellAmtPart(Date todayDt, int ctrlNo, String securityType, double sellingAmt, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException;

    public String saveFaceGreaterSellAmtPart(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException;

    public List<InvestmentCallMaster> getUnAuhorizeCallMaster() throws ApplicationException;

    public Integer getMaxCtrlNo() throws ApplicationException;

    public String saveInvestmentCallMaster(InvestmentCallMaster entity) throws ApplicationException;

    public String updateInvestmentCallMaster(InvestmentCallMaster entity) throws ApplicationException;

    public InvestmentCallMaster getInvestCallMasterByCtrl(Long ctrlNo) throws ApplicationException;

    public String deleteInvestmentCallMaster(Long ctrlNo) throws ApplicationException;

    public String authorizeEntry(Long ctrlNo, String authBy, String orgnCode) throws ApplicationException;

    public Gltable getGltableByAcno(String acNo) throws ApplicationException;

    public Integer getMaxCtrlNoFromInvestmentFdrDetails() throws ApplicationException;

    public String fdrSaveProcess(Integer maxCtrlNo, Date purDate, Date matDate, String intOpt, String days, String months,
            String years, String details, String roi, String bank, double facevalue, double bookValue, String fdrNo,
            String branch, String user, String crHead, String orgnCode,String secTp, int txnid) throws ApplicationException;

    public Object[] getInvestmentDetailsAndDates(String status, Integer ctrlNo) throws ApplicationException;

    public String fdrUpdation(InvestmentFdrDates invFdrDates, InvestmentFdrDetails invFdrDetails) throws ApplicationException;

    public List<Object[]> getInvestmentDetailAndDatesForIntRecPostFdr(String status, Date purDt, String iOpt) throws ApplicationException;

    //public String postIntRecFdr(Date tillDt, String authBy, String orgncode) throws ApplicationException;
    
    public String postIntRecFdr(List<InvestmentFDRInterestPojo> tableList, double totAmt, Date tillDt, String authBy, String orgncode, String intOpt) throws ApplicationException;

    public Date getMaxToDateFromIntPostHistory(String param) throws ApplicationException;

    public Integer getmaxCtrlNoInvestmentShare() throws ApplicationException;

    public String saveShareCreation(Integer maxCtrlNo, String secType, Date purDate, String details, String bank, double facevalue, String shareCertificate,
            String branch, String user, String crHead, String orgnCode) throws ApplicationException;

    public List<InvestmentShare> getUnAuthorizeControlNo() throws ApplicationException;

    public InvestmentShare getControlNoDetails(Integer ctrlNo) throws ApplicationException;

    public String updateInvestmentShare(InvestmentShare entity) throws ApplicationException;

    public BranchMaster getAlphaCodeByBrnCode(String orgncode) throws ApplicationException;

    public String closeShare(Integer ctrlNo, double faceValue, String user, String bank, String drHead, String orgncode) throws ApplicationException;

    public InvestmentCallHead getInvestCallHeadByCode(String code) throws ApplicationException;

    public List<InvestmentSecurityMaster> getSecRoi(String securityName) throws ApplicationException;

    public List<InvestmentSecurityMaster> getAllSecName() throws ApplicationException;

    public GlDescRange getCodeFrmCodeBook(String sec) throws ApplicationException;

    public String saveGoiSecurity(int maxSec, String getCrGlVal, String getSecTpDd, String getFaceValue, String getOrgnBrCode, String getCrBranchDd, String getUserName, String getBookValue, String getAccrIntVal, String getIssueFrDd, String getCodeNoVal, double getRoiVal, Date getTransactionDate, String setFlag, Date getSettlementDate, String getSellerNmVal, String getConAcNoVal, String getBrokerAcVal, double getBrokerageAmtVal, int getNoOfShrVal, Date getMatDate, String getConSgAccountDd, double getPriceVal, String getPurchaseDate, String getPrnIssueDate, String getfInttPayDate, String getsInttPayDate, String getIssuePrVal, String dtl,double ytm, String enterBy, int seqNo, String marking) throws ApplicationException;

    public List<InvestmentSecurityMaster> getAllSecDtlBySecName(String securityName) throws ApplicationException;

    public String saveGlDataForAmort(String CrGlHead, String DrGlHead, String orgncode, double amount, String enterby, BigInteger ctrlNo, String monAmort, BigInteger yrAmort, String flag) throws ApplicationException;

    public String updateAmortDtl(String lastupdateby, Date lastupdatedt, BigInteger slno, Long ctrlno, Date years) throws ApplicationException;

    public List<InvestmentAmortizationDetails> getAmortCtrlNo(Date yearAmort, BigInteger ctrlNo) throws ApplicationException;

    public List<Long> getCtrlNo() throws ApplicationException;

    public List<InvestmentAmortPostHistory> getPostStatusCtrl(BigInteger yearAmort, String monthAmort, BigInteger ctrlNo) throws ApplicationException;

    public List<InvestmentAmortPostHistory> getPostStatusAll(BigInteger yearAmort, String monthAmort) throws ApplicationException;

    //public InvestmentCallHead getInvestmentCallHead(String code) throws ApplicationException;
    public BigDecimal getTotalAmortAmt(Date years, BigInteger ctrNo, String stat) throws ApplicationException;

    public Object[] getRepData(BigInteger ctrNo) throws ApplicationException;

    public String updateFdrDates(double amount, Integer ctrlno) throws ApplicationException;

    public Object[] getDetailDataOfCtrlNo(Integer ctrNo) throws ApplicationException;

    public List<Integer> getCtrlNoFromInvestmentFdrDetails() throws ApplicationException;

    public InvestmentFdrDates getInvestmentFdrDatesByCtrlNo(Integer ctrlNo) throws ApplicationException;

    public InvestmentFdrDetails getInvestmentFdrDetailsByCtrlNo(Integer ctrlNo) throws ApplicationException;

    public InvestmentShare getInvestmentShare(Integer ctrlNo) throws ApplicationException;

    public String fdrCloseProcess(Integer ctrlNo, String renCloseFlg, String closeFlg, String purchaseDate, Integer days, Integer months, Integer years,
            String matDate, float roi, String intOpt, double faceValue, double bookvalue, String intCrGlHead, double intCrGlHeadVal, String intRecCrGlHead,
            double intRecCrGlHeadVal, String faceValCrGlHead, double faceValCrGlHeadVal, String bookValDrGlHead, double bookValDrGlHeadVal, String flag,
            String enterBy, String brnCode, String sellerName, String vchDt, String intAccGlHead, double intAccGlHeadVal, String authBy) throws ApplicationException;

    public String updateFDRDetails(Integer ctrlNo, String status) throws ApplicationException;

    public String updateFDRDatesBySno(Integer ctrlNo, Date dt) throws ApplicationException;

    public List<String> getAllSellerName(String secTp) throws ApplicationException;

    public Integer getMaxCtrlNoByInvestmentMasterSec() throws ApplicationException;

    public List<GlDescRange> getGlRangeBySec(String glName) throws ApplicationException;

    public String updateInvestmentInterestAmount(InvestmentFrdDatesAndDetailsHistory historyObj, InvestmentFdrDates obj,String drAcno, String drAmt, String crAcno, String crAmt,String enterBy,String orgncode, BigDecimal curAmt, BigDecimal prevAmt) throws ApplicationException;
    
    public String postIntRecSec(List<GoiIntReportPojo> reportList, Date tillDt,String crAcno, String crAmt, String drAcno, String drAmt, String userName, String brCode,String remark,String accrAcno, String accrAmt) throws ApplicationException;
    
    public List<Object[]> getInvestmentDetailAndDatesForIntRecPostSec(String status, Date purDt) throws ApplicationException; 
    
    public String updateInvestmentSecInterestAmount(InvestmentFrdDatesAndDetailsHistory historyObj, InvestmentGoidates obj,String drAcno, String drAmt, String crAcno, String crAmt,String enterBy,String orgncode,String Flag) throws ApplicationException;
    
    public String getGLByCtrlNo(Integer ctrlNo) throws ApplicationException;
    
    public BigDecimal getTotalAmortAmtBal (Integer ctrlNo,String status) throws ApplicationException;
    
    public String updateInvestMasterAccdIntByCtrlNo(double acdint, int ctrlNo) throws ApplicationException;
    
    public List<InvestmentGoidates> getControllNoForAmortCalc(String status, String type) throws ApplicationException;
    
    public List<InvestmentCallMaster> getUnAuthorizeCallCtrlNo() throws ApplicationException;
    
    public InvestmentCallMaster getCallCtrlNoDetails(Integer ctrlNo) throws ApplicationException;
    
    public String closeCallMoney(Integer ctrlNo, String intDrHead, double intDrAmt, String intCrHead, double intCrAmt, String totDrHead, double totDrAmt, 
            String totCrHead, double totCrAmt, String user, String orgncode, String rem) throws ApplicationException;
    
    public List<InvestmentSecurityAuthDetail> getUnAuthSecCtrlNo(String brnCode) throws ApplicationException;
    
    public List<InvestmentSecurityAuthDetail> getUnAuthCtrlNoDtl(int seqNo) throws ApplicationException;
    
    public String saveInvestmentSecurityAuthDetail(InvestmentSecurityAuthDetail entity) throws ApplicationException;
    
    public String deleteInvestmentSecurityAuth(int seqNo) throws ApplicationException;
    
    public Integer getMaxSeqNoInvestmentSecAuth() throws ApplicationException;
    
    public String postSecAccrInt(List<GoiIntReportPojo> tableList, double totAmt, Date tillDt, String authBy, String orgncode) throws ApplicationException;
    
    public String saveFaceLessSellAmtPart(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt, double amortVal) throws ApplicationException;
    
    public List<InvestmentGoidates> getControllNoSecWise(String secType, String secDesc) throws ApplicationException;
    
    public List<String> getSecurityDesc(String secType) throws ApplicationException;
    
    public String saveTrBillValue(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException;

    public List getMarkingStatus(String CONTROLNO,String  SECTYPE) throws ApplicationException;   
    
    public String changeMark(String  SECTYPE, String CONTROLNO,String Marking,String MarkingType, String updateBy) throws ApplicationException;
    
    public List getControlNo(String SECTYPE) throws ApplicationException;
    
    public String saveMutualFundDetail(String buyDrAcNo, String buyCrAcNo,int days, double amt, String userName, String remark, String mark) throws ApplicationException;
    
    public List getUnAuthSeqList(List statusList, String rAcNo) throws ApplicationException;
    
    public List getUnAuthSeqDetailList(int seqNo) throws ApplicationException;
    
    public String verifyMutualFundDetail(int seqNo, String buyDrAcNo, String buyCrAcNo, int days, double amt, String gDate, String enterBy, String authBy, String remark, String orgBrnId, String mark) throws ApplicationException;

    public String deleteMutualFundDetail(int seqNo, String enterBy) throws ApplicationException;
    
    public List getRedeemBankList(String code) throws ApplicationException;
    
    public List<InvestmentFDRInterestPojo> getCtrlToRedeem(String drAcNo) throws ApplicationException;
    
    public String saveMFundRedeemDetail(List<InvestmentFDRInterestPojo> dataList, String reDrAcNo, String plAcNo, double partialAmt,
            double partialredeemAmt, String userName, String remark,double totalRemainingAmt) throws ApplicationException ;
    
    public List<InvestmentFDRInterestPojo> getSeqNoToVerifyDtl(String drAcNo, int seqNo) throws ApplicationException ;
    
    public String verifyMutualFundRedeemDetail(List<InvestmentFDRInterestPojo> dataList, int seqNo, String redeemDrAcNo, String redeemCrAcNo, String buyCrAcNo,
       String gDate, String authBy, String enterBy, String remark, String orgBrnId,Double partialAmt,Double totalPartialAmt,Double  partialRedeemAmt,
       Double totalRemainingAmt) throws ApplicationException;
 
    public List getUnAuthSecCloseList(String secType) throws ApplicationException;
    
    public List<SecurityAuthClosePojo> getUnAuthCtrlNoSecClose(int ctrlNo) throws ApplicationException;
    
    public String deleteSecCloseAuth(int seqNo, String ctrlNo) throws ApplicationException;
    
    public String saveSecurityCloseAuthDetail(String securityType, String securityName, String ctrlNo, String sellerName, String faceValue, String bookValue,
            String maturityDate, String accruedInt, String amortValue, String balanceInt, String issuePrice, String sellingStatus, String sellingAmt,
            String sellingDt, String debitGlHead, String branch, String enterBy, String orgBrnid) throws ApplicationException;
    
    public List getUnAuthFDRSeqNo() throws ApplicationException;
    
    public List<InvestmentFDRInterestPojo> getUnAuthSeqNoFDRList(int seqNo) throws ApplicationException;
    
    public String deleteFdrCreateAuth(int seqNo) throws ApplicationException;
    
    public String saveFDRCreateAuthDetail(String secType, String tdDetails, String fdrNo,String bank, String branch,String purDate,String passDays, String passMonth,String passYear,
                String matDate,String roi, String intOpt,String faceValue, String interest,String bookvalue, String crHead,String drAcno,String branchName,
                String userName,String orgnBrCode) throws ApplicationException;
    
    
    /** Ok After Testing **/
    
    
    public List getUnAuthFDRCloseCtrlNo() throws ApplicationException;
    
    public List<InvestmentFDRCloseRenewAuthPojo> getUnAuthFDRCloseRenew(String ctrlNo) throws ApplicationException;
    
    public String deleteFdrCloseRenewAuth(String ctrlNo) throws ApplicationException;
    
    public String saveFDRCloseRenewAuthDetail(String ctrlNo,String fdrNo,String sPurchaseDate,String sMaturityDate,String sFaceValue,
            String sBookValue, String sellerName, String sRoi,String sIntOpt,String sBranch,String closeRenew,String manualAuto,String renPurDate,
            String fPurchaseDate,String days,String months,String years,String matDate,String rRoi,String rIntOpt,String rFaceValue,String rInterest,
            String rMaturityValue,String intAccrGlHead,String intAccrValue,String intRecCrGlHead,String intRecCrValue,String intDrGlHead,String intDrValue,
            String faceValueCrGlHead,String faceValueCrValue,String matDrGlHead,String matDrValue,String enterBy,String orgBrnid) throws ApplicationException;
    
    public List getCtrlNoFromFdrDetails() throws ApplicationException;
    
    public List getControlList(String status) throws ApplicationException;
    
    public  List<InvestmentFdrDates> onblurCntrlNumber(int ctrlNo) throws ApplicationException;
    
    public List<InvestmentFdrDetails> controlNumberDeatils(int ctrlNo) throws ApplicationException;
  
    public String postIntRecievedRecFdr(List<FdrIntPostPojo> reportList,int controlNo,String frDate,String toDate,String authBy,String branch,String remark,String DrAcno,String DrAmt,String CrAcno,String CrAmt,String AccNo,String AccAmt,String intOpt) throws ApplicationException;
    
    public String saveSellAmtEqualBookAmt(Date todayDt, int ctrlNo, String securityType, double sellingAmt, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException;
    
    public String saveSellAmtGreaterBookAmt(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException;
    
    public String saveSellAmtLessBookAmt(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String ogrnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt, double amortVal) throws ApplicationException;
    
    public int getTotalSumOfDaysByCtrl(int ctrlNo) throws ApplicationException;
}
