/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.FdRoiDetail;
import com.cbs.pojo.IntCalTable;
import com.cbs.pojo.SavingIntRateChangePojo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface LoanInterestCalculationFacadeRemote {

    public List getAcctType() throws ApplicationException;

    public List getCaAcctType() throws ApplicationException;

    public List getTheftAcType() throws ApplicationException;

    //public List getTableList();
    String getROI(String intTableCode, double amt, String date) throws ApplicationException;

    String getRoiLoanAccount(double amt, String date, String acno) throws ApplicationException;

    String outStandingAsOnDate(String acNo, String tillDate) throws ApplicationException;

    public ArrayList getSecurityAddExpDtChangeSlab(String acNo, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException;

    ArrayList getRoiChangeSlab(String intTableCode, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException;

    ArrayList getAdhocDateChangeSlab(String acNo, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException;

    public String dateAdd(String dt, int noOfDays) throws ApplicationException;

    String getNextToDt(String brnCode, String acNo, String fromDt, String freq, int moratoriumPd) throws ApplicationException;

    List<LoanIntCalcList> cbsLoanIntCalc(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> cbsCaAccountIntCalc(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;

    public String outStandingCaAccountAsOnDate(String acNo, String tillDate) throws ApplicationException;

    List<LoanIntCalcList> cbsTheftIntCalcNew(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException;

    // List<LoanMultiList> cbsTheftIntCalc(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException;
    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException;

    public String caAccountWiseFromDt(String acNo, String brnCode) throws ApplicationException;

    public String allFromDt(String acType, String brnCode, String want) throws ApplicationException;

    public String allDepositAccountFromDt(String acType, String brnCode, String want) throws ApplicationException;

    public String getGlHeads(String acctCode) throws ApplicationException;

    public String getCaAccountCreditGlHeads(String acctCode) throws ApplicationException;

    public String loanInterestPosting(List<IntCalTable> intCalc, String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;

    public String caAccountInterestPosting(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;

    public String theftInterestPosting(List<LoanIntCalcList> intProductDetail, String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;

    public String[][] createFromDtArray(String acNo, String fromDt, String toDt, String intAppFreq, String acNature,
            String intTableCode, String SecurityAddExpFlag, String odPenalFlag) throws ApplicationException;

    public List getOnlyLoanAcctType() throws ApplicationException;

    public LoanIntCalcList accWiseLoanIntCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException;

    public double[] loanIntAsPerSecurity(String acNo, String fromDt, String toDt, double noOfDays, double outstanding, String calDt, String orderBy) throws ApplicationException;

    public LoanIntCalcList accWiseCaAccountIntCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException;

    public LoanIntCalcList txnLoanIntCalc(String toDt, String acNo, String brnCode) throws ApplicationException;

    public LoanIntCalcList accWiseLoanIntCalcTheftNew(List<SavingIntRateChangePojo> roiList, List<FdRoiDetail> FdRoiList, String fromDt, String toDt, String acNo, String brnCode, String acNature) throws ApplicationException;

    public List acWiseFromDtToDt(String year, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> threftIntCalcPostal(String intOpt, String acType, String acNo, float roi, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException;

    public String theftInterestPostalPosting(List<LoanIntCalcList> intProductDetail, String acType, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;

    public String allFromDtForCharge(String acType, String brnCode, String want, String flag) throws ApplicationException;
}
