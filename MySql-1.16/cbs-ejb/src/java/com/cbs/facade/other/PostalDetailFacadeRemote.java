/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.ChargesObject;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.BulkRecoveryPojo;
import com.cbs.pojo.PostalDemandPojo;
import com.cbs.pojo.PremiumDetailsPojo;
import java.util.List;
import java.util.TreeSet;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface PostalDetailFacadeRemote {

    public int getCodeByReportName(String reportName) throws ApplicationException;

    public List getPrimiumAndWellFareDetails(String txnType, String acno) throws ApplicationException;

    public List getPrimiumAmtDetails(String txnType, String acno) throws ApplicationException;

    public List getMemberRegistrationDt(String memberNo) throws ApplicationException;

    //public double getWellFareFund(String txnType) throws ApplicationException;
    public List getWellFareFund(String txnType) throws ApplicationException;

//    public String getMaxFinYearDate(String alphacode) throws ApplicationException;
    public String generateInsuranceAndWellFare(List<PremiumDetailsPojo> dataList, String acNo, String odLimit, String txnType) throws ApplicationException;

    public TreeSet<String> getBranchPlaceOfWorking(String brncode) throws ApplicationException;
    
    public List getBranchPlaceOfWorking1(String brncode) throws ApplicationException;

    public String generateExcelFileBasedOnPlaceOfWorking(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy) throws ApplicationException;

    public String processBulkRecovery(List<BulkRecoveryPojo> dataList, String orgnBrCode, String useName, String todayDate, String place) throws ApplicationException;

    public String isAccountofPostal(String acno) throws ApplicationException;

    public List<PostalDemandPojo> getAllDemandOfAccount(String acno, String todayDt) throws ApplicationException;

    public String processSingleRecovery(Float trsno, Float recno, String orgBrCode, String todayDt,
            Integer tranType, String authBy) throws ApplicationException;

    public String processTrfRecovery(Float trsno, Float recno, String orgBrCode, String todayDt,
            Integer tranType, String authBy) throws ApplicationException;

    public String getAccountByPurpose(String purpose) throws ApplicationException;

    public String clearingOperationOnPosting(Float trsNo, Integer fyear, Double seqNo, String originDt, String sundryHead,
            Double sundryDrAmt, String authBy, String orgBrCode) throws ApplicationException;

    public String checkNreFlagInBatch(Float trsno) throws ApplicationException;

    public String performGeneralTransaction(String acno, Integer ty, Integer tranType, String chqNo,
            Double amount, Float trsNo, String enterBy, String orgBrCode, String paidSno, String acAgainstPremiumAndWellGl, String chqDt, Integer payBy, String authBy, String ValueDt) throws ApplicationException;

    public String satisfyRecovery(String mode, String acno, Double receivedAmount, String authBy,String bnkCode) throws ApplicationException;

    public List getDesgType(String dt, String brCode) throws ApplicationException;

    public List getHeadFidility(String desg) throws ApplicationException;

    public List getSumFidility(String dt, String desg, String brCode) throws ApplicationException;

    public List<LoanIntCalcList> fidilityListRep(String toDt, String desg, String brnCode) throws ApplicationException;

    public String fidilityPost(String dt, String desg, String brncode, String crhead, String drhead, double amt, String authBy) throws ApplicationException;

    public String getTheftAmt(String acNo) throws ApplicationException;

    public List fileGenChecking(String brnCode, String placeOfWorking, String fromDt, String toDt) throws ApplicationException;

    public String getflag(String brnCode, String placeOfWorking, String toDt) throws ApplicationException;

    public List getFileGenDt(String place) throws ApplicationException;

    public String getsrNoByArea(String place) throws ApplicationException;

    public List getFrdtTodtByArea(String place) throws ApplicationException;

    public String armyInterestPostParameter() throws ApplicationException;

    public List interestPostChecking(String brnCode, String code, String frDt, String toDt) throws ApplicationException;

    public List<ChargesObject> calculateInsPremium(String brnCode, String acType, String dt) throws ApplicationException;

    public String postPremium(List<ChargesObject> lipLst, String crHead, double totAmt, String dt, String enterBy, String brnCode) throws ApplicationException;

    public String generateExcelFileBasedOnPlaceOfWorkingPostal(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy) throws ApplicationException;

    public String processBulkRecoveryPostal(List<BulkRecoveryPojo> dataList, String orgnBrCode, String useName, String todayDate, String place) throws ApplicationException;

    public String[] getFYearRangeForGenerationDate(String generationDt) throws ApplicationException;

    public String getCustNameByFolioNo(String folioNo) throws ApplicationException;

    public String isReoover(String brnCode, String place) throws ApplicationException;
    
    public String getBankCode() throws ApplicationException;
    
    public double getDemandByNature(String branch, String actNature, String tillDt) throws ApplicationException;
    
    public String generateExcelFileBasedOnPlaceOfWorkingOef(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy, String range) throws ApplicationException;
    
    public String processBulkRecoveryOef(List<BulkRecoveryPojo> dataList, String orgnBrCode, String useName, String todayDate, String place) throws ApplicationException;
}
