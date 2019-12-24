/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.loan;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface AdvancesInformationTrackingRemote {

    public List getSector() throws ApplicationException;

    public List getSubSector(String sector) throws ApplicationException;

    public List getListAsPerRequirement(String refRecNo, String gNo, String sGNo, String ssGNo, String sssGNo, String ssssGNo, String date,float parameter) throws ApplicationException;

    public List purposeOfAdvance(String refRecCode, String sector, String subSector, String modeOfAdvance) throws ApplicationException;

    public List modeOfAdvance() throws ApplicationException;

    public List typeOfAdvance() throws ApplicationException;

    public List secured() throws ApplicationException;

    public List guarnteeCover() throws ApplicationException;

    public List indusType() throws ApplicationException;

    public List sickness() throws ApplicationException;

    public List exposure() throws ApplicationException;

    public List exposureCategory() throws ApplicationException;

    public List exposureCategoryPurpose() throws ApplicationException;

    public List restructure() throws ApplicationException;

    public List sanctionLevel() throws ApplicationException;

    public List sanctionAuth() throws ApplicationException;

    public List npaClass() throws ApplicationException;

    public List courts() throws ApplicationException;

    public List modeOfSetlement() throws ApplicationException;

    public List getDWR() throws ApplicationException;

    public List assetClassReason() throws ApplicationException;

    public List populationGrp() throws ApplicationException;

    public List interestTable() throws ApplicationException;

    public List interestTableCode(String schemeCode) throws ApplicationException;

    public List interestType() throws ApplicationException;

    public List schemeCode() throws ApplicationException;

    public List schemeCodeDet(String schemeCode) throws ApplicationException;

    List chkAcNo(String accNo) throws ApplicationException;
    
    List custIdDetail(String customerId) throws ApplicationException;

    public List groupIdList() throws ApplicationException;
    
    public List getReferenceCode1(String refRecNo) throws ApplicationException;

    public List selectCustDetails(String accNo) throws ApplicationException;

    public List getCbsParameterinfoValue(String name) throws ApplicationException;

    public List getDetailsFromAccMaster(String accNo) throws ApplicationException;

    public String saveCustDetails(String accNo, String sector, String subSector, String purposeOfAdvance, String modeOfAdvance, String typeOfAdvance,
            String secured, String guarnteeCover, String industryType, String sickness, String exposure, String restructuring,
            String sanctionLevel, String sanctionAuth, String courts, String modeOfSettle, String debtWaiverReason, String assetClassReason, String popuGroup, String intTable, String intType, String schemeCode, String npaClass, String userName, String netWorth, String marginMoney, String documentDate, String renewDate, String loanDuration, String documentExprDate, String relation, String sancAmount, String drawingPwrInd, String monthlyIncome, String applicantCategory,
            String categoryOpt, String minorCommunity, String remarks, String relName, String relOwner, String expCategory, String expCatPurpose, String dirCustId, String retirement_age, String industry,String groupType,String groupDetail,String businessIndustryType) throws ApplicationException;

    public String modifyCustDetails(String accNo, String sector, String subSector, String purposeOfAdvance, String modeOfAdvance, String typeOfAdvance,
            String secured, String guarnteeCover, String industryType, String sickness, String exposure, String restructuring,
            String sanctionLevel, String sanctionAuth, String courts, String modeOfSettle, String debtWaiverReason, String assetClassReason, String popuGroup, String intTable, String intType, String schemeCode, String npaClass, String userName, String netWorth, String marginMoney, String documentDate, String renewDate, String loanDuration,
            String documentExprDate, String relation, String sancAmount, String drawingPwrInd, String monthlyIncome, String applicantCategory,
            String categoryOpt, String minorCommunity, String remarks, String relName, String relOwner, String expCategory, String expCatPurpose, String dirCustId, String retirement_age, String industry,String groupType,String groupDetail,String businessIndustryType) throws ApplicationException;

    /*FROM HERE THESE ARE SHIPRA EJB METHOD*/
    public List getReferenceCode(String refRecNo) throws ApplicationException;
    
    public List dirRelList(String custId,String dt) throws ApplicationException;
    
    public List isExist(String custId) throws ApplicationException;

    public List getappLoanDetails(String acno) throws ApplicationException;

    public List getDisbursementDetail(String acno) throws ApplicationException;

    public String saveDisbursementSchedule(List table, String orgnBrCode, String todayDate, String acno) throws ApplicationException;

    public List getDisbursementSchedule(String acno) throws ApplicationException;

    public String daybeginDate(java.lang.String orgnBrCode) throws ApplicationException;

    public List getInstallmentPlan(String acno) throws ApplicationException;

    public List getSanctionAmt(String acno) throws ApplicationException;

    public List getOsBalance(String acno) throws ApplicationException;

    public double repaymentOpeingPrinciple(String acNo);

    public String CalculateEMI(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException;

    public String CalculateEPRP(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException;

    public String CalculateEMIDailyBasis(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException;

    public String Calculate(String acno, Integer InstaltypeFlag, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, Float SancAmt, String Periodicity, String ManualFlag,String interestOption) throws ApplicationException;

    public String UpdateReShedule(String acno, List AmorList, String Flag, String user) throws ApplicationException;

    public List getIntCodeIntTypeSchmCode(String accNo) throws ApplicationException;
    /*END OF SHIPRA'S METHOD*/

    public List refRecCode(String refRecNo) throws ApplicationException;

    public List getDesignationPerson(String refRecNo, String designation) throws ApplicationException;

    public List getManagementDtl(String status) throws ApplicationException;

    public String SaveManagementDetail(String title, String name, String desg, String Gender, String edu_details, String addrLine1, String addrLine2, String village, String block, String CityCode, String StateCode, String PostalCode, String CountryCode, String PhoneNumber, String EmailID, String JoinDt, String expDt, String status, String enterby, String dsgRem, String custId,String dirRelation,String directorId) throws ApplicationException;

    public String UpdateManagementDetail(String title, String name, String desg, String Gender, String edu_details, String addrLine1, String addrLine2, String village, String block, String CityCode, String StateCode, String PostalCode, String CountryCode, String PhoneNumber, String EmailID, String JoinDt, String expDt, String status, String enterby, int sno, String dsgRem, String custId,String dirRelation,String directorId) throws ApplicationException;

    public List relNameDetail(String relCode) throws ApplicationException;

    public String getGuarantorIs(String schemeCode) throws ApplicationException;

    public String getGuarantorDob(String custId) throws ApplicationException;

    public String getGuarantorSalary(String custId) throws ApplicationException;

    public String getCustIdAsPerFolioNo(String folioNo) throws ApplicationException;

    public String getGuarantorLimit(String schemeCode) throws ApplicationException;

    public String getGuarantorNetworth(String custId) throws ApplicationException;

    public String getLoanPerid(String acNo) throws ApplicationException;

    public String getGuarantorNo(String schemeCode) throws ApplicationException;

    public String getMinGuarantorNo(String acNo) throws ApplicationException;

    public String guarNetWorthChk(String guarFlag, String guarAc, String schemeCode, String acno) throws ApplicationException;

    public String getGuarCustId(String acNo) throws ApplicationException;

    public List getGuarChk(String guarCustId) throws ApplicationException;

    public String SaveMoneyExchg(String tendName, String idProof, String idNo, double totVal, String place,
            String brCode, String enterBy, List<DDSDenominationGrid> cashDenoList, String authFlag) throws ApplicationException;

    public List gridLoadForMoneyExchg(String brCode, String authFlag) throws ApplicationException;

    public List getDenominationDetails(float recNo, String dt) throws ApplicationException;

    public String ModifyMoneyExchg(String tendName, String idProof, String idNo, double totVal, String place,
            String brCode, String authBy, int spNo, float recno, List<DDSDenominationGrid> cashDenoList) throws ApplicationException;

    public String verifyMoneyExchg(String authBy, int spNo) throws ApplicationException;

    public List getBankiinDetail();

    public List getBankNameList();

    public double getGuarantorPaticipateAmt(String custId) throws ApplicationException;

    public List getAadhaarDetail(String custId) throws ApplicationException;

    public List getCustomerAadhaarDetail(String custId) throws ApplicationException;

    public List refRecDesc(String refRecCode) throws ApplicationException;

    public String CalculateEmiAtSimpleInterest(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException;

    public List checkGroupId(String custId) throws ApplicationException;
    
    public List isEmirescheduled(String acno) throws ApplicationException;
    
    public List getOpeningBalByRescheduleDate(String acno) throws ApplicationException;

}
