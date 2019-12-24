/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.td;

import com.cbs.dto.TdIntDetail;
import com.cbs.dto.TdsDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.GridData;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface TermDepositeCalculationManagementFacadeRemote {

    public List getTableDetail(String acno, String brCode) throws ApplicationException;

    public String cbsTdPaymentAccInfo(String acno, String brCode) throws ApplicationException;

    public String completeButton(String acno, float rtNumber, String lblIntOpt, String mintStatus,
            double lblPAmt, double lblBalInt, double txtTds1, double txtTds, double lblInterest, String fdDate, String lblMdate,
            double lblNetAmt, float penaltyApplication, float stxtRoi, String user, String BRCODE,
            double lblInterest1, String auth, String authBy, double clActTdsToBeDeducted) throws ApplicationException;

    public String cbsTdPaymentRtno(String AccountNo, float txtReceiptNo,
            float NetROI, String fdDate, String matDate, String user, double lblPAmt,
            String tmpCustCat, String brCode, String autoPayFlag, String centralDayBeginDt) throws ApplicationException;

    public String cbsPenalityApply(String AccountNo, float txtReceiptNo,
            String fdDate, String matDate, String user, double lblPAmt, String applicableRate,
            String penalityApplicable, String lblintopt, String brCode,
            String tmpCustCat, String autoPayFlag, String centralDayBeginDt) throws ApplicationException;

    public String getFirstDate(int months, int years);

    public String getLastdate(String firstDate);

    public List<TdsDetail> tdsCalculationBeforeIntCalc(String firstdate, String lastdate, String branchCode)
            throws ApplicationException;
    
    public double getInterestSum(List<TdIntDetail> txnList, String custId);
    
    public double getTotalIntPaid(List<TdIntDetail> txnList, String custId);
    
    public String getGlHead(String lastDate, String branchCode);

    public List getAcctDetail(String tmpAcc, String brCode) throws ApplicationException;

    public List getTableDetailForTdSingleEntry(String tmpAcc, String brCode) throws ApplicationException;

    public List getReceiptNo(String ac, String rt, String brCode) throws ApplicationException;

    public String save(List<GridData> generalGrid, float lblTotAmt, String brCode, String user) throws ApplicationException;

    public String displayField(String tmpAcno, String txtRTNo, String brcode) throws ApplicationException;

    public String cmdLoadClick(String tmpAcc, String intOpt, float tdsAmount, String user, String brcode)
            throws ApplicationException;

    public List tableAccountWise(String accountNo, String date, String orgnCode) throws ApplicationException;

    public List getGlobalFdCondition() throws ApplicationException;

    public List getTdRecieptSeq() throws ApplicationException;

    public List getBookSeries(String acctNature, String receipt, String brCode) throws ApplicationException;

    public String tdRenewalSave(String acno, String renewFDDt, String renewMatDT, double tdsDeduct, int periodYY, int periodMM,
            int periodDD, float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries,
            float tmpReciept, String enteredBy, String rVoucherNo, double rAmt, String orgncode, double tmpBalInt, double txtAmt, double matPre,
            String stYear, String endYear, double lblLastFtds, double tmpIntTds, double tdsAmt, float roi, double renewMatAmt, double clActTdsToBeDeducted) throws ApplicationException;

    public String cbsTdRenewalNewAcct(String oldAcNo, String enteredBy, String orgnCode, String acType) throws ApplicationException;

    public Float tdApplicableROIFdRenewal(String acno, String applicableRoi, double tOTAMT, String matDt, String wefDt, String presentDt) throws ApplicationException;

    public String LienAcno(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public String provisionCompleteAction(String acno, float rtNumber, String intOpt, String status, double prinAmt, double remainingInterest,
            double tdsToBeDeducted, double interestPaid, String fdDate, String matDate, double netAmt, float penalty, float roi, String user,
            String brCode, double actualInterest, String auth, String authBy) throws ApplicationException;

    public String tdProvisionRenewal(String acno, String renewFDDt, String renewMatDT, double tdsDeduct, int periodYY, int periodMM,
            int periodDD, float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries,
            float tmpReciept, String enteredBy, String rVoucherNo, String rAcno, double rAmt, String orgBrCode, double tmpBalInt,
            double txtAmt, double matPre, String stYear, String endYear, float roiNew, double renewMatAmt) throws ApplicationException;

    public String saveTdPaymentDetails(String acno, float rtNo, float receiptNo, String intOption, String status, double prinAmt, double remInt,
            double tdsToBeDeducted, double tdsDeducted, double interestPaid, String fdDate, String matDate, double netAmount, float penalty,
            float roi, String user, String brCode, double actualInterest, float acROI, float netConRoi, double clActTdsToBeDeducted,
            double clActTdsDeducted, double clActIntFinYear) throws ApplicationException;

    public List getUnAuthAcNo(String orgnBrCode) throws ApplicationException;

    public List getUnAuthRtNo(String acNo) throws ApplicationException;

    public List getRtDetails(String acNo, float rtNo) throws ApplicationException;

    public String deleteTdPaymentDetails(String acNo, float rtNo) throws ApplicationException;

    public String getContractedRoi(String acNo, float rtNo) throws ApplicationException;

    public String tdsPostBeforeIntCalc(List<TdsDetail> tdsCalculation, String glHead, String brnCode, String frDt, String toDt, String authBy) throws ApplicationException;

    public List<TdsDetail> projectedTdsCalculation(String firstdate, String lastdate, String branchCode) throws ApplicationException;
}
