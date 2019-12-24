/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface AutoTermDepositRenewalRemote {

    public String autoRenewTermDeposit() throws ApplicationException;

    public String tdRenewalSave(String acctNo, String renewFdDate, String renewMatDate, double tdsDeduct, int periodYY, int periodMM, int periodDD,
            float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries, float recieptNo,
            String enteredBy, String rVoucherNo, double rAmt, String orgnBrCode, double tmpBalInt, double txtAmt, double matPre, String stYear,
            String endYear, double lblLastFtds, double tmpIntTds, double tdsAmt, float roiNew, double renewMatAmt, double clActTdsToBeDeducted,
            String autoManualFlag) throws ApplicationException;

    public String tdProvisionRenewal(String acno, String renewFDDt, String renewMatDT, double tdsDeduct, int periodYY, int periodMM,
            int periodDD, float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries,
            float tmpReciept, String enteredBy, String rVoucherNo, String rAcno, double rAmt, String orgBrCode, double tmpBalInt,
            double txtAmt, double matPre, String stYear, String endYear, float roiNew, double renewMatAmt, String autoManualFlag) throws ApplicationException;

    //public double closeActTdsPosting(List intCloseList, float tdsRate, String frmDT, String toDT, float trsNumber, float recNo,
     //       String tempBd, float rtNumber) throws ApplicationException;

    public String autoMonthlyIntPosting() throws ApplicationException;

    public String autoPendingChargesPosting(String todayDt, String userName) throws ApplicationException ;
            
    public String autoPaymentTermDeposit(String todayDt, String userName) throws ApplicationException;

    public String completeButton(String acno, float rtNumber, String intOption, String status,
            double prinAmt, double remainingInterest, double tdsToBeDeducted, double tdsDeducted,
            double interestPaid, String fdDate, String matDate, double netAmount, float penalty,
            float roi, String user, String brCode, double actualInterest, String auth, String authBy,
            double clActTdsToBeDeducted, String autoPayFlag, String centralDayBeginDt) throws ApplicationException;

    public String getFinYear(String brCode) throws ApplicationException;

    public List<Double> tdGlobal(String acNo, float rtNo, String maturePayment, float nROI, int tdcumuPrematureFlag, String year, String mon, String day,String tdType) throws ApplicationException;

    public String getTdsRateAndApplicableAmt(String acNo, String bussnesDt) throws ApplicationException;

    public double getCustomerFinYearTds(String acno, String fromDt, String toDt, String recover, String closeFlag) throws ApplicationException;

    public double getFinYearIntOfCustomer(String acno, String fromDt, String toDt, String closeFlag) throws ApplicationException;
    
   // public Map<String,List<SmsToBatchTo>> siAutoCoverPost(String dt) throws ApplicationException;
    
    public List<SmsToBatchTo> autoLockerRentPosting(String tempBd) throws ApplicationException;
    
    public double getMajorOrMinorInt(String acno, String fromDt, String toDt) throws ApplicationException ;
    
    public List getUnRecoverdTdsAccounts(String acno, String fromDt, String toDt, String recover, String closeFlag) throws ApplicationException ;
    
    public void closeActTdsPosting(List intCloseList, String frmDT, String toDT, float trsNumber, float recNo,
            String tempBd, float rtNumber, double calculatedTds, String acno, String intOpt) throws ApplicationException ;
    
    public double getClActFinYearTds(String acno, String fromDt, String toDt, String recover, float vchNo) throws ApplicationException;
    
     public String tdRenewalAuthBalInt(String acctNo, double tdsToBeDeducted, float tmpVchNo, String optInterest, String rVoucherNo, double tmpBalInt, 
            double matPre, double lastFinYearTds, double tmpIntTds, double tdsDeducted, double clActTdsToBeDeducted, String rStatus) throws ApplicationException;
     
     public String autoSeniorCitizenMarking(String dt, String userName) throws ApplicationException;
}
