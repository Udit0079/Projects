/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.RdInterestDTO;
import com.cbs.dto.TdIntDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface RDIntCalFacadeRemote {

    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    List getAccountTypes() throws ApplicationException;

    /**
     * 
     * @param acType
     * @return
     * @throws ApplicationException 
     */
    List getInterestGLHeads(String acType) throws ApplicationException;

    /**
     * 
     * @param acType
     * @param fromDate
     * @param toDate
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    List<RdInterestDTO> interestCalculation(String acType, String fromDate, String toDate, String brCode, String intOption) throws ApplicationException;

    /**
     * 
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    String getCurrentDate(String brCode) throws ApplicationException;

    /**
     * 
     * @param acType
     * @param fromDate
     * @param toDate
     * @param user
     * @param brcode
     * @return
     * @throws ApplicationException 
     */
    String interestPosting(String acType, String fromDate, String toDate, String user, String brcode, String intOption) throws ApplicationException;

    /**
     * 
     * @param acType
     * @return
     * @throws ApplicationException 
     */
    //List acctTypeLostFocus(String acType) throws ApplicationException;
    /**
     * 
     * @param tmpPreDate
     * @param quarEndDt
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    List getFinYear(String tmpPreDate, String quarEndDt, String brCode) throws ApplicationException;

    /**
     * 
     * @param tmpPreDate
     * @param quarEndDt
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
//    String monthEndCheck(String tmpPreDate, String quarEndDt, String brCode) throws ApplicationException;
    /**
     * 
     * @param brCode
     * @param acType
     * @param tmpPreDate
     * @param quarEndDate
     * @return
     * @throws ApplicationException 
     */
    List<RdInterestDTO> quarterlyRdIntCal(String brCode, String acType, String tmpPreDate, String quarEndDate, String intOpt) throws ApplicationException;

    /**
     * 
     * @param userName
     * @param actype
     * @param quarEndDt
     * @param intOpt
     * @param tmpPreDt1
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    String postInterest(String userName, String actype, String quarEndDt, String intOpt, String tmpPreDt1, String brCode) throws ApplicationException;

    /**
     * 
     * @param acno
     * @param acType
     * @return
     * @throws ApplicationException 
     */
    public List getRdAccountDetail(String acno, String acType) throws ApplicationException;

    /**
     * @param acType
     * @return
     * @throws ApplicationException 
     */
    public String sngGetPrevailingRoi(String acType) throws ApplicationException;

    /**
     * 
     * @param rflag
     * @param totinstall
     * @param NetConRoi
     * @param toDt
     * @param fromDt
     * @param matDt
     * @param acno
     * @param install
     * @param penaltyAmt
     * @return
     * @throws ApplicationException 
     */
    public List rdInterestCalculation(String rflag, Float totinstall, Float NetConRoi, String toDt, String fromDt, String matDt,
            String acno, Float install, Float penaltyAmt) throws ApplicationException;

    /**
     * 
     * @param fdDate
     * @param inrange
     * @param inAmount
     * @return
     * @throws ApplicationException 
     */
    public List getRate(String fdDate, long inrange, Float inAmount) throws ApplicationException;

    /**
     * 
     * @param dint
     * @param dprovamt
     * @param datetmp
     * @param orgbrnid
     * @param destBrnid
     * @param rdSimplepostflag
     * @param enterby
     * @param acno
     * @param gpostcheck
     * @return
     * @throws ApplicationException 
     */
   public float rdIntPosting(double dint, double dprovamt, String datetmp, String orgbrnid,
            String destBrnid, Integer rdSimplepostflag, String enterby,String authBy, String acno, String gpostcheck, double tdsToBeDeducted, double closeActTdsToBeDeducted, String matDt) throws ApplicationException;

    /**
     * 
     * @param acNo
     * @return
     * @throws ApplicationException 
     */
    public List getInstallment(String acNo) throws ApplicationException;

    /**
     * 
     * @param acType
     * @param toDate
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public List<RdInterestDTO> rdIntProvision(String acType, String toDate, String brCode) throws ApplicationException;
    
    public int rdPostFlag() throws ApplicationException;
    
    public List getAcctNature() throws ApplicationException;
   
    public String saveRecordTargent(String accountType,String acctNature,String orgntype,String orgnCode,String targetAc,String tragetAmount,String frDt,String toDt,String monthlyYear,String actype,String userName,String todayDate)throws ApplicationException;

    public List gridDetailTargetMaster(String accountType)throws ApplicationException;
     
    public List Acctnature(String accountType) throws ApplicationException;
    
    public List<TdIntDetail> rdProjectedIntCalc(String acType, String fromDate, String toDate, String brCode) throws ApplicationException;
    
    public List<TdIntDetail> intCalcForTds(String acType, String fromDate, String toDate, String brCode) throws ApplicationException;
    
    public List<TdIntDetail> intCalcRDWithTds(String acType, String fromDate, String toDate, String brCode) throws ApplicationException;

    public List rdIntCal(String openingDate, String matDt, Float netConRoi, Float install) throws ApplicationException;
    
    public List<Double> getRdIntTds(String acNo, String frDt, String toDt, String brCode) throws ApplicationException;
    
    public String saveRdrecord(double dint, double dprovamt, String datetmp, String orgbrnid,
            String destBrnid, Integer intOption, String enterby, String acno, String gpostcheck,
            double tdsToBeDeducted, double tdsDeducted, String formDt, String matDt, 
            float netConRoi, double maturityAmt, float roi, float acROI, float penalty,
            double IntPaidCurrentYear,double closeActTdsToBeDeducted,double closeActTdsDeducted) throws ApplicationException;
    
    public String deleteRdrecord(String acNo) throws ApplicationException;
    
    public List getRdVerifyDeleteAccountDetail(String acno, String acType) throws ApplicationException;
    
    public List getUnAuthRdAcNo(String orgBranch) throws ApplicationException;
    
    public List isRdPaymentAcno(String acNo) throws ApplicationException;
    
    public List<RdInterestDTO> individualRdIntCal(String acNo, String toDate) throws ApplicationException;
    
    public List rdInterestCalculationProductWise(String rflag, Float totinstall, Float netConRoi, String toDt, String fromDt, String matDt,
            String acno, Float install, Float penaltyAmt, String rdMatDt, String provIntPaid) throws ApplicationException;
}
