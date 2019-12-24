/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.EquifaxComercialPoJo;
import com.cbs.dto.report.ExperionPojo;
import com.cbs.dto.report.CibilBSPoJo;
import com.cbs.dto.report.CibilHDPoJo;
import com.cbs.dto.report.CibilASPoJo;
import com.cbs.dto.report.CibilCDPoJo;
import com.cbs.dto.report.CibilSSPoJo;
import com.cbs.dto.report.CibilTSPoJo;
import com.cbs.dto.report.CibilRSPoJo;
import com.cbs.dto.report.CibilGSPoJo;
import com.cbs.dto.report.CibilCRPoJo;
import com.cbs.dto.report.CibilComPoJo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author SAMY
 */
@Stateless(mappedName = "CibilReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CibilReportFacade implements CibilReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    DDSReportFacadeRemote ddsRemote;
    @EJB
    OverDueReportFacadeRemote overDueRemote;
    @EJB
    LoanReportFacadeRemote loanRemote;
    NumberFormat formatter2 = new DecimalFormat("####");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("ddMMyyyy");
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public static String getMiddleName(String[] middleName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < middleName.length - 1; i++) {
            builder.append(middleName[i] + " ");
        }
        return builder.toString();
    }

    public String cibilReportConsumerText(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String path, String cibilfileName, String cibilParameter) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String fileName = "";
        BufferedWriter bw = null;
        FileWriter fw = null;
        File file = null;
        try {
            ut.begin();
            //fileName = "CibilConsumerFile.txt";
            fileName = cibilfileName + ".txt";
            System.out.println("File path : " + path + "" + fileName);
            file = new File(path + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            if (file.createNewFile()) {
                fw = new FileWriter(file.getCanonicalFile());
                bw = new BufferedWriter(fw);

//                String SegmentTag = "TUDF", Version = "12", UserId = "BF91110001                    ", shortname = "REP             ", CycleIdentification = "  ", DateReported = "30092016", ReportingPassword = "XXXXX                         ", AuthenticationMethod = "L", FutureUse = "00000", MemberData = "                                                ";
//                String header = SegmentTag + Version + UserId + shortname + CycleIdentification + DateReported + ReportingPassword + AuthenticationMethod + FutureUse + MemberData;
//                bw.write(header);

                String SegmentTag = "TUDF", Version = "12", UserId = "", shortname = "", CycleIdentification = "  ", DateReported = "30092016", ReportingPassword = "XXXXX                         ", AuthenticationMethod = "L", FutureUse = "00000", MemberData = "                                                ";
                List list = em.createNativeQuery("select distinct cibil_member_code_new,cibil_member_name_new from branchmaster").getResultList();
                if (!list.isEmpty()) {
                    Vector vector = (Vector) list.get(0);
                    UserId = vector.get(0).toString();
                    shortname = vector.get(1).toString();
                }

                int useridlength = UserId.length();
                int addspace = 32 - useridlength;
                String spaceUserid = "";
                if (addspace > 0) {
                    for (int i = 0; i < addspace; i++) {
                        spaceUserid += " ";
                    }
                }
                int shortnamelength = UserId.length();
                int addspac = 16 - shortnamelength;
                String spaceshortname = "";
                if (addspac > 0) {
                    for (int i = 0; i < addspac; i++) {
                        spaceshortname += " ";
                    }
                }

                DateReported = dmyFormat.format(ymdFormat.parse(toDate));
                String header = SegmentTag + Version + UserId + spaceUserid + shortname + spaceshortname + CycleIdentification
                        + DateReported + ReportingPassword + AuthenticationMethod + FutureUse + MemberData;
                bw.write(header);
            }
            String body = "", SegmentPN = "PN03N01", SegmentID = "ID03", SegmentPT = "PT03", SegmentEC = "EC03", SegmentPA = "PA03", SegmentTL = "TL04T001", SegmentTH = "TH03", SegmentES = "ES02**", SegmentTRLR = "TRLR";

            List<ExperionPojo> cibilList = cibilReport(memberCode, fromDate, toDate, todaydate, reportType, cibilParameter);
            for (ExperionPojo obj : cibilList) {
                // Name Segment (PN)
                // add on name 0+variable length as per Hcbl
                String ConsumerName1 = SegmentPN + (obj.getCustNameF1().equalsIgnoreCase("") ? "" : "01" + (obj.getCustNameF1().length() < 10 ? "0" + obj.getCustNameF1().length() : obj.getCustNameF1().length()) + obj.getCustNameF1());
                String ConsumerName2 = obj.getCustNameF2().equalsIgnoreCase("") ? "" : "02" + (obj.getCustNameF2().length() < 10 ? "0" + obj.getCustNameF2().length() : obj.getCustNameF2().length()) + obj.getCustNameF2();
                String ConsumerName3 = obj.getCustNameF3().equalsIgnoreCase("") ? "" : "03" + (obj.getCustNameF3().length() < 10 ? "0" + obj.getCustNameF3().length() : obj.getCustNameF3().length()) + obj.getCustNameF3();
                String ConsumerName4 = obj.getCustNameF4().equalsIgnoreCase("") ? "" : "04" + (obj.getCustNameF4().length() < 10 ? "0" + obj.getCustNameF4().length() : obj.getCustNameF4().length()) + obj.getCustNameF4();
                String ConsumerName5 = obj.getCustNameF5().equalsIgnoreCase("") ? "" : "05" + (obj.getCustNameF5().length() < 10 ? "0" + obj.getCustNameF5().length() : obj.getCustNameF5().length()) + obj.getCustNameF5();
                String DateofBirth = obj.getDob().equalsIgnoreCase("") ? "" : "0708" + obj.getDob();
                String gender = obj.getGender().equalsIgnoreCase("") ? "" : "0801" + obj.getGender();
                // Identification Segment (ID)
                String IncomeTaxIDNumber = obj.getIncomeTaxId().equalsIgnoreCase("") ? "" : SegmentID + "I01" + "01020102" + (obj.getIncomeTaxId().length() < 10 ? "0" + obj.getIncomeTaxId().length() : obj.getIncomeTaxId().length()) + obj.getIncomeTaxId();
                String PassportNumber = (obj.getPassportNo().equalsIgnoreCase("") ? "" : SegmentID + "I02" + "01020202" + (obj.getPassportNo().length() < 10 ? "0" + obj.getPassportNo().length() : obj.getPassportNo().length()) + obj.getPassportNo());
                String PassportIssueDate = ((obj.getPassportIssueDt() == null || obj.getPassportIssueDt().equalsIgnoreCase("")) ? "" : "0308" + obj.getPassportIssueDt());
                String PassportExpiryDate = ((obj.getPassportExpDt() == null || obj.getPassportExpDt().equalsIgnoreCase("")) ? "" : "0408" + obj.getPassportExpDt());
                String VoterIDNumber = (obj.getVoterIdNo().equalsIgnoreCase("") ? "" : SegmentID + "I03" + "01020203" + (obj.getVoterIdNo().length() < 10 ? "0" + obj.getVoterIdNo().length() : obj.getVoterIdNo().length()) + obj.getVoterIdNo());
                String DrivingLicenseNumber = (obj.getDriLicenseNo().equalsIgnoreCase("") ? "" : SegmentID + "I04" + "01020204" + (obj.getDriLicenseNo().length() < 10 ? "0" + obj.getDriLicenseNo().length() : obj.getDriLicenseNo().length()) + obj.getDriLicenseNo());
                String DrivingLicenseIssueDate = ((obj.getDriLicenseIssueDt() == null || obj.getDriLicenseIssueDt().equalsIgnoreCase("")) ? "" : "0308" + obj.getDriLicenseIssueDt());
                String DrivingLicenseExpiryDate = ((obj.getDriLicenseExpDt() == null || obj.getDriLicenseExpDt().equalsIgnoreCase("")) ? "" : "0408" + obj.getDriLicenseExpDt());
                String RationCardNumber = (obj.getRationCardNo().equalsIgnoreCase("") ? "" : SegmentID + "I05" + "01020205" + (obj.getRationCardNo().length() < 10 ? "0" + obj.getRationCardNo().length() : obj.getRationCardNo().length()) + obj.getRationCardNo());
                String UniversalIDNumber = (obj.getUniversalIdNo().equalsIgnoreCase("") ? "" : SegmentID + "I06" + "01020206" + (obj.getUniversalIdNo().length() < 10 ? "0" + obj.getUniversalIdNo().length() : obj.getUniversalIdNo().length()) + obj.getUniversalIdNo());
                String AdditionalID1 = (obj.getAddId1().equalsIgnoreCase("") ? "" : SegmentID + "I07" + "01020207" + (obj.getAddId1().length() < 10 ? "0" + obj.getAddId1().length() : obj.getAddId1().length()) + obj.getAddId1());
                String AdditionalID2 = (obj.getAddId2().equalsIgnoreCase("") ? "" : SegmentID + "I08" + "01020208" + (obj.getAddId2().length() < 10 ? "0" + obj.getAddId2().length() : obj.getAddId2().length()) + obj.getAddId2());

                // Telephone Segment (PT)
                String TelephoneNoMobile = obj.getTelephone().equalsIgnoreCase("") ? "" : SegmentPT + "T0101" + (obj.getTelephone().length() < 10 ? "0" + obj.getTelephone().length() : obj.getTelephone().length()) + obj.getTelephone();
                String TelephoneNoResidence = obj.getTelNoResidence().equalsIgnoreCase("") ? "" : SegmentPT + "T0201" + (obj.getTelNoResidence().length() < 10 ? "0" + obj.getTelNoResidence().length() : obj.getTelNoResidence().length()) + obj.getTelNoResidence();
                String TelephoneNoOffice = obj.getTelNoOffice().equalsIgnoreCase("") ? "" : SegmentPT + "T0301" + (obj.getTelNoOffice().length() < 10 ? "0" + obj.getTelNoOffice().length() : obj.getTelNoOffice().length()) + obj.getTelNoOffice();
                String ExtensionOffice = obj.getExtOffice().equalsIgnoreCase("") ? "" : SegmentPT + "T0402" + (obj.getExtOffice().length() < 10 ? "0" + obj.getExtOffice().length() : obj.getExtOffice().length()) + obj.getExtOffice();
                String TelephoneNoOther = obj.getTelNoOther().equalsIgnoreCase("") ? "" : SegmentPT + "T0501" + (obj.getTelNoOther().length() < 10 ? "0" + obj.getTelNoOther().length() : obj.getTelNoOther().length()) + obj.getTelNoOther();
                String ExtensionOther = obj.getExtOther().equalsIgnoreCase("") ? "" : SegmentPT + "T0602" + (obj.getExtOther().length() < 10 ? "0" + obj.getExtOther().length() : obj.getExtOther().length()) + obj.getExtOther();

                // Email Contact Segment (EC)
                String EmailID1 = obj.getEmailId1().equalsIgnoreCase("") ? "" : SegmentEC + "C0101" + (obj.getEmailId1().length() < 10 ? "0" + obj.getEmailId1().length() : obj.getEmailId1().length()) + obj.getEmailId1();
                String EmailID2 = obj.getEmailId2().equalsIgnoreCase("") ? "" : SegmentEC + "C0201" + (obj.getEmailId2().length() < 10 ? "0" + obj.getEmailId2().length() : obj.getEmailId2().length()) + obj.getEmailId2();

                // Address Segment (PA)
                String Address1 = obj.getAddressLine1().equalsIgnoreCase("") ? "" : SegmentPA + "A0101" + (obj.getAddressLine1().length() < 10 ? "0" + obj.getAddressLine1().length() : obj.getAddressLine1().length()) + obj.getAddressLine1();
                String StateCode1 = obj.getStateCode().equalsIgnoreCase("") ? "" : "0602" + obj.getStateCode();
                String PINCode1 = obj.getPinCode().equalsIgnoreCase("") ? "" : "07" + (obj.getPinCode().length() < 10 ? "0" + obj.getPinCode().length() : obj.getPinCode().length()) + obj.getPinCode();
                String AddressCategory1 = obj.getAddressCategory1().equalsIgnoreCase("") ? "" : "0802" + obj.getAddressCategory1();
                String ResidenceCode1 = obj.getResidenceCode1().equalsIgnoreCase("") ? "" : "0902" + obj.getResidenceCode1();

                String Address2 = obj.getAddressLine2().equalsIgnoreCase("") ? "" : SegmentPA + "A0201" + (obj.getAddressLine2().length() < 10 ? "0" + obj.getAddressLine2().length() : obj.getAddressLine2().length()) + obj.getAddressLine2();
                String StateCode2 = obj.getStateCode2().equalsIgnoreCase("") ? "" : "0602" + obj.getStateCode2();
                String PINCode2 = obj.getPinCode2().equalsIgnoreCase("") ? "" : "07" + (obj.getPinCode2().length() < 10 ? "0" + obj.getPinCode2().length() : obj.getPinCode2().length()) + obj.getPinCode2();
                String AddressCategory2 = obj.getAddressCategory2().equalsIgnoreCase("") ? "" : "0802" + obj.getAddressCategory2();
                String ResidenceCode2 = obj.getResidenceCode2().equalsIgnoreCase("") ? "" : "0902" + obj.getResidenceCode2();

                // Account Segment (TL)
                String CurrentNewMemberCode = obj.getReportingMemCode().equalsIgnoreCase("") ? "" : SegmentTL + "0110" + obj.getReportingMemCode();
                String CurrentNewMemberShortName = obj.getMemberShortName().equalsIgnoreCase("") ? "" : "02" + (obj.getMemberShortName().length() < 10 ? "0" + obj.getMemberShortName().length() : obj.getMemberShortName().length()) + obj.getMemberShortName();
                String CurrNewAccountNo = obj.getCurrentAcno().equalsIgnoreCase("") ? "" : "03" + (obj.getCurrentAcno().length() < 10 ? "0" + obj.getCurrentAcno().length() : obj.getCurrentAcno().length()) + obj.getCurrentAcno();
                String AccountType = obj.getAccountType().equalsIgnoreCase("") ? "" : "0402" + obj.getAccountType();
                String OwnershipIndicator = obj.getAccountHolderTypeCode().equalsIgnoreCase("") ? "" : "0501" + obj.getAccountHolderTypeCode();
                String DateOpenedDisbursed = obj.getDisbursDt().equalsIgnoreCase("") ? "" : "0808" + obj.getDisbursDt();
                String DateofLastPayment = obj.getLastPaymentdate().equalsIgnoreCase("") ? "" : "0908" + obj.getLastPaymentdate();
                String DateClosed = obj.getDateClose().equalsIgnoreCase("") ? "" : "1008" + obj.getDateClose();
                String DateReported = obj.getDateReported().equalsIgnoreCase("") ? "" : "1108" + obj.getDateReported();
                String HighCreditSanctionedAmt = obj.getSanctAmt().equalsIgnoreCase("") ? "" : "12" + (obj.getSanctAmt().length() < 10 ? "0" + obj.getSanctAmt().length() : obj.getSanctAmt().length()) + obj.getSanctAmt();
                String CurrentBalance = obj.getCurrentBalance().equalsIgnoreCase("") ? "" : "13" + (obj.getCurrentBalance().length() < 10 ? "0" + obj.getCurrentBalance().length() : obj.getCurrentBalance().length()) + obj.getCurrentBalance();
                String AmtOverdue = obj.getAmountOverDue().equalsIgnoreCase("") ? "" : "14" + (obj.getAmountOverDue().length() < 10 ? "0" + obj.getAmountOverDue().length() : obj.getAmountOverDue().length()) + obj.getAmountOverDue();
                String NoofDaysPastDue = obj.getNoOfDaysPast().equalsIgnoreCase("") ? "" : "15" + (obj.getNoOfDaysPast().length() < 10 ? "0" + obj.getNoOfDaysPast().length() : obj.getNoOfDaysPast().length()) + obj.getNoOfDaysPast();
                String OldMbrCode = obj.getOldReportingMemCode().equalsIgnoreCase("") ? "" : "1610" + obj.getOldReportingMemCode();
                String OldMbrShortName = obj.getOldMemShortName().equalsIgnoreCase("") ? "" : "17" + (obj.getOldMemShortName().length() < 10 ? "0" + obj.getOldMemShortName().length() : obj.getOldMemShortName().length()) + obj.getOldMemShortName();
                String OldAccNo = obj.getOldAccountNo() == null || obj.getOldAccountNo().equalsIgnoreCase("") ? "" : "18" + (obj.getOldAccountNo().length() < 10 ? "0" + obj.getOldAccountNo().length() : obj.getOldAccountNo().length()) + obj.getOldAccountNo();
                String OldAccType = obj.getOldAccType().equalsIgnoreCase("") ? "" : "1902" + obj.getOldAccType();
                String OldOwnershipIndicator = obj.getOldOwnershipIndicator().equalsIgnoreCase("") ? "" : "2001" + obj.getOldOwnershipIndicator();
                String SuitFiledWilfulDefault = (obj.getWrittenOffStatus() == null || obj.getWrittenOffStatus().equalsIgnoreCase("")) ? "" : "2102" + obj.getWrittenOffStatus();
                String WrittenoffandSettledStatus = obj.getWrittenSettledStatus().equalsIgnoreCase("") ? "" : "2202" + obj.getWrittenSettledStatus();
                String AssetClassification = obj.getAssetClassif().equalsIgnoreCase("") ? "" : "2602" + obj.getAssetClassif();
                String ValueofCollateral = obj.getValueOfCollateral().equalsIgnoreCase("") ? "" : "34" + (obj.getValueOfCollateral().length() < 10 ? "0" + obj.getValueOfCollateral().length() : obj.getValueOfCollateral().length()) + obj.getValueOfCollateral();
                String TypeofCollateral = obj.getTypeOfCollateral().equalsIgnoreCase("") ? "" : "3502" + obj.getTypeOfCollateral();
                String CreditLimit = obj.getCreditLimit().equalsIgnoreCase("") ? "" : "36" + (obj.getCreditLimit().length() < 10 ? "0" + obj.getCreditLimit().length() : obj.getCreditLimit().length()) + obj.getCreditLimit();
                String CashLimit = obj.getCashLimit().equalsIgnoreCase("") ? "" : "37" + (obj.getCashLimit().length() < 10 ? "0" + obj.getCashLimit().length() : obj.getCashLimit().length()) + obj.getCashLimit();
                String RateofInterest = obj.getRateOfInterest().equalsIgnoreCase("") ? "" : "38" + (obj.getRateOfInterest().length() < 10 ? "0" + obj.getRateOfInterest().length() : obj.getRateOfInterest().length()) + obj.getRateOfInterest();
                String RepaymentTenure = obj.getRepaymentTenure().equalsIgnoreCase("") ? "" : "39" + (obj.getRepaymentTenure().length() < 10 ? "0" + obj.getRepaymentTenure().length() : obj.getRepaymentTenure().length()) + obj.getRepaymentTenure();
                String EMIAmount = obj.getEmiAmt().equalsIgnoreCase("") ? "" : "40" + (obj.getEmiAmt().length() < 10 ? "0" + obj.getEmiAmt().length() : obj.getEmiAmt().length()) + obj.getEmiAmt();
                String WrittenoffAmountTotal = obj.getWrittenOffAmountTotal().equalsIgnoreCase("") ? "" : "41" + (obj.getWrittenOffAmountTotal().length() < 10 ? "0" + obj.getWrittenOffAmountTotal().length() : obj.getWrittenOffAmountTotal().length()) + obj.getWrittenOffAmountTotal();
                String WrittenoffPrincipalAmount = obj.getWrittenOffPrincipalAmount().equalsIgnoreCase("") ? "" : "42" + (obj.getWrittenOffPrincipalAmount().length() < 10 ? "0" + obj.getWrittenOffPrincipalAmount().length() : obj.getWrittenOffPrincipalAmount().length()) + obj.getWrittenOffPrincipalAmount();
                String SettlementAmt = obj.getSettlementAmt().equalsIgnoreCase("") ? "" : "43" + (obj.getSettlementAmt().length() < 10 ? "0" + obj.getSettlementAmt().length() : obj.getSettlementAmt().length()) + obj.getSettlementAmt();
                String PaymentFrequency = obj.getPaymentFrequency().equalsIgnoreCase("") ? "" : "4402" + obj.getPaymentFrequency();
                String ActualPaymentAmt = obj.getActualPaymentAmt().equalsIgnoreCase("") ? "" : "45" + (obj.getActualPaymentAmt().length() < 10 ? "0" + obj.getActualPaymentAmt().length() : obj.getActualPaymentAmt().length()) + obj.getActualPaymentAmt();
                String OccupationCode = obj.getOccupationCode().equalsIgnoreCase("") ? "" : "4602" + obj.getOccupationCode();
                String Income = obj.getIncome().equalsIgnoreCase("") ? "" : "47" + (obj.getIncome().length() < 10 ? "0" + obj.getIncome().length() : obj.getIncome().length()) + obj.getIncome();
                String NetGrossIncomeIndicator = obj.getNetGrossIncomeIndicator().equalsIgnoreCase("") ? "" : "4801" + obj.getNetGrossIncomeIndicator();
                String MonthlyAnnualIncomeIndicator = obj.getMonthlyAnnualIncomeIndicator().equalsIgnoreCase("") ? "" : "4901" + obj.getMonthlyAnnualIncomeIndicator();

                // Account History Segment (TH)
                // End of Subject Segment (ES)
                // Trailer Segment (TRLR)
                bw.write(ConsumerName1 + ConsumerName2 + ConsumerName3 + ConsumerName4 + ConsumerName5 + DateofBirth + gender
                        + IncomeTaxIDNumber + PassportNumber + PassportIssueDate + PassportExpiryDate + VoterIDNumber + DrivingLicenseNumber + DrivingLicenseIssueDate + DrivingLicenseExpiryDate + RationCardNumber + UniversalIDNumber + AdditionalID1 + AdditionalID2
                        + TelephoneNoMobile + TelephoneNoResidence + TelephoneNoOffice + ExtensionOffice + TelephoneNoOther + ExtensionOther
                        + EmailID1 + EmailID2
                        + Address1 + StateCode1 + PINCode1 + AddressCategory1 + ResidenceCode1 + Address2 + StateCode2 + PINCode2 + AddressCategory2 + ResidenceCode2
                        + CurrentNewMemberCode + CurrentNewMemberShortName + CurrNewAccountNo + AccountType + OwnershipIndicator + DateOpenedDisbursed + DateofLastPayment + DateClosed + DateReported
                        + HighCreditSanctionedAmt + CurrentBalance + AmtOverdue + NoofDaysPastDue + OldMbrCode + OldMbrShortName + OldAccNo + OldAccType + OldOwnershipIndicator
                        + SuitFiledWilfulDefault + WrittenoffandSettledStatus + AssetClassification + ValueofCollateral + TypeofCollateral + CreditLimit + CashLimit + RateofInterest + RepaymentTenure
                        + EMIAmount + WrittenoffAmountTotal + WrittenoffPrincipalAmount + SettlementAmt + PaymentFrequency + ActualPaymentAmt + OccupationCode + Income + NetGrossIncomeIndicator + MonthlyAnnualIncomeIndicator + SegmentES);

            }
            bw.write(SegmentTRLR);

            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                file.delete();
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;

    }

    public String cibilComercialDelimitedFormat(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String cibilParameter, String path, String cibilfileName, String repType) throws ApplicationException {

        List<EquifaxComercialPoJo> experionList = new ArrayList<EquifaxComercialPoJo>();
        String preMemberCode = "", reportDt = "", futureUse = "", brnCode = "", borName = "", shortName = "", borOfycLoc = "",
                borAdd1 = "", borAdd2 = "", borAdd3 = "", borCity = "", borDistrict = "", borPin = "", borTelNo = "", borTelAreaCode = "", borFaxNo = "", borFaxArea = "",
                borPan = "", borLglConst = "", classOFAct1 = "", classOfAct2 = "", classOfAct3 = "", relationshipData = "", relType = "", relPan = "",
                relationShip = "", businessEntityName = "", jtPreFix = "", jtFullName = "", percOfContrl = "", jtAdd1 = "", jtAdd2 = "", jtAdd3 = "", jtCity = "", jtState = "",
                jtPin = "", jtcountry = "91", jtTelNo = "", jtTelArea = "", crAcData = "", acno = "", preAcno = "", sanctDt = "", currencyCode = "",
                creditType = "", dp = "", assetClass = "", bnkRemarkCode = "", defalultStatus = "", defaultStatusDt = "", suitStatus = "", suitRefNo = "", suitDt = "";
        BigDecimal sanctAmt = new BigDecimal("0"), outstanding = new BigDecimal("0"), suitAmt = new BigDecimal("0");
        String suitAmount = "";
        String writeoff = "";
        String fileName = "";
        BufferedWriter bw = null;
        FileWriter fw = null;
        File file = null;

        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Vector vtr1 = null;
            String sanctionAmt = null, currentBal = "0", amtOverdue = null, writtrnOff = null;
            String overDueDays = "";
            List custIdList;
            List custNameList = new ArrayList();
            List<OverdueEmiReportPojo> OverdueTlList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", 1, 5000, toDate, "0A", "A", false, "", "N");
            List<OverdueNonEmiResultList> OverdueDlList = overDueRemote.getOverDueNonEmi("DL", "ALL", toDate, "0A", "N");
            List<OverdueNonEmiResultList> OverdueCaList = overDueRemote.getOverDueNonEmi("CA", "ALL", toDate, "0A", "N");
            String title = "", custName = "", dob = "", gender = "", panGirNumber = "", passportNo = "", voterIdNo = "", telephoneNumber = "",
                    mailAddressLine1 = "", mailAddressLine2 = "", mailVillage = "", mailBlock = "", mailCityCode = "", mailStateCode = "", mailPostalCode = "",
                    customerid = "", acNo = "", operMode = "", openingDt = "", closingDate = "", accStatus = "",
                    custId1 = "", custId2 = "", custId3 = "", custId4 = "", address = "", space = " ",
                    perAddressLine1 = "", perAddressLine2 = "", perVillage = "", perBlock = "", perCityCode = "", perStateCode = "", perPostalCode = "",
                    issueDate = "", expirydate = "", drivingLicenseNo = "", uidNo = "",
                    telResident = "", telOffice = "", emailID = "", odLimit = "", address2 = "",
                    acNature = "", acctcode = "", custNameAccMast = "", bussCat = "", bussType = "";

            List acNatureList = new ArrayList();
            /*1 For Consumer and parameter will be CIBIL_ACNATURE(DL & TL)
             * 2 For Comercial and parameter will be CIBIL_COMERCIAL(CA)
             */
            String branchName = "";
            List branchList = em.createNativeQuery("select bankname from bnkadd").getResultList();
            if (!branchList.isEmpty()) {
                Vector vtrBranch = (Vector) branchList.get(0);
                branchName = vtrBranch.get(0) != null ? vtrBranch.get(0).toString() : "";

            }
            String cibil_old_acno = "N";
            List parameterOld = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name ='CIBIL_OLD_ACNO'").getResultList();
            if (!parameterOld.isEmpty()) {
                Vector vect = (Vector) parameterOld.get(0);
                cibil_old_acno = vect.get(0).toString();
            }
            List codeQuery = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name ='CIBIL_IND_CATEGORY'").getResultList();
            String code = "";
            if (!codeQuery.isEmpty()) {
                Vector codeVect = (Vector) codeQuery.get(0);
                code = codeVect.get(0).toString();
            } else {
                throw new ApplicationException("Please Fill define CIBIL_IND_CATEGORY in cbs_parameterinfo Table!!!");
            }
            String acctCat = "";
            if (reportType.equalsIgnoreCase("1")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACNATURE'").getResultList();
                acctCat = " and a.acctcategory in (" + code + ")";
            } else if (reportType.equalsIgnoreCase("2")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_COMERCIAL'").getResultList();
                acctCat = " and a.acctcategory  not in (" + code + ")";
            }
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Acnature data does not exit in required table");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }
            String cibilVerNo = common.getCibilVerNo();
            List acctCodeList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACCTCODE_NOT'").getResultList();
            if (acctCodeList.isEmpty()) {
                acctcode = " and acctcode not in ('') ";
            } else {
                Vector acctCodeVect = (Vector) acctCodeList.get(0);
                if (acctCodeVect.get(0).toString().equalsIgnoreCase("")) {
                    acctcode = " and acctcode not in ('') ";
                } else {
                    acctcode = " and acctcode not in (" + acctCodeVect.get(0).toString() + ") ";
                }
            }
            String oldMemberCode = "", newMemberCode = "", oldMemberName = "", newMemberName = "";
            List oldAcNoList = em.createNativeQuery("select ifnull(cibil_member_code_old,''),ifnull(cibil_member_code_new,''),ifnull(cibil_member_name_old,''),ifnull(cibil_member_name_new,'') from branchmaster").getResultList();
            if (!oldAcNoList.isEmpty()) {
                Vector vtrBranch = (Vector) oldAcNoList.get(0);
                oldMemberCode = vtrBranch.get(0).toString();
                memberCode = vtrBranch.get(1).toString().equalsIgnoreCase("") ? memberCode : vtrBranch.get(1).toString();
                oldMemberName = vtrBranch.get(2).toString();
                newMemberName = vtrBranch.get(3).toString();
            }
            int isExcessEmi = common.isExceessEmi(toDate);
            String header = "HD" + "|" + memberCode + "|" + oldMemberCode + "|" + dmy.format(sdf.parse(todaydate)) + "|" + dmy.format(ymd.parse(toDate)) + "|" + "01" + "|" + "" + "|";
            ut.begin();
            fileName = cibilfileName + ".tap";
            file = new File(path + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            if (file.createNewFile()) {
                fw = new FileWriter(file.getCanonicalFile());
                bw = new BufferedWriter(fw);
                bw.write(header + "\n");
            }

            List<CibilCRPoJo> cr = new ArrayList<>();
            // End Of HD Pojo.It will be reported only once.
            new SimpleDateFormat("yyyyMMdd").format(new Date());
            List l1 = em.createNativeQuery("select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                    + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                    + " c.PAN_GIRNumber,c.PassportNo, c.VoterIDNo, ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                    + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                    + " c.MailAddressLine1, c.MailAddressLine2, c.MailVillage, c.MailBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = c.mailCityCode) as MailCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                    + " c.MailPostalCode, c.customerid, c.CustFullName, a.ACNo, a.OperMode, a.OpeningDt, a.ClosingDate, a.AccStatus, "
                    + " a.custid1, a.custid2, a.custid3, a.custid4,"
                    + " c.IssueDate, c.Expirydate, c.DrivingLicenseNo, aa.IdentityNo as aadhar_no , "
                    + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                    + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                    + " ifnull(c.EmailID,''),"
                    + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = c.PerCityCode) as PerCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                    + " c.PerPostalCode, cast(cast((case cast(ifnull(d.ODLimit,0) as decimal(25,2)) when 0 then d.Sanctionlimit else cast(ifnull(d.ODLimit,0) as decimal(25,2)) end) as decimal(25,2)) as unsigned) as odLimit , c.CustFullName,ifnull(c.shortname,''),ifnull(c.TINNumber,'') as tin ,"
                    + " ifnull(c.SalesTaxNo,'')as saletax,ifnull(c.VoterIDNo,'') as voterId,ifnull(c.DrivingLicenseNo,'') as dl,"
                    + " ifnull(c.CreditRatingAsOn,'1900-01-01 00:00:00') as creditRatingason,ifnull(c.tan,'') as tan, ifnull(c.cin,'') as cin,"
                    + " ifnull(c.other_identity,'') as otherId,ifnull(c.mobilenumber,'') as mobileNo, ifnull(c.CountryCode,'IN'),ifnull(Sanctionlimitdt,'1900-01-01 00:00:00'),ifnull(a.Relatioship,''),ifnull(FaxNumber,'') "
                    + " ,(select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature,a.acctCategory ,c.OperationalRiskRating,"
                    + " (select ifnull(b.cibil_acct_report_code,'00') AS accounttypetable from cbs_loan_acc_mast_sec clm, cbs_scheme_currency_details b where clm.scheme_code = b.scheme_code and clm.acno = a.acno) as accounttypetable,"
                    + " d.old_ac_no,d.old_ac_type,d.old_branch_code,"
                    + " d.old_ownership_indicator,CustEntityType,bb.Commencement_Date,bd.NPA_CLASSIFICATION,bd.ASSET_CLASS_REASON ,bd.mode_of_settlement ,bd.SICKNESS,bd.BUSINESS_INDUSTRY_TYPE  "
                    + " from accountmaster a, customerid b, loan_appparameter d ,cbs_loan_borrower_details bd, cbs_customer_master_detail c "
                    + " left outer join "
                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " left join "
                    + " (select CustomerId,Commencement_Date from cbs_cust_misinfo) bb on bb.CustomerId = c.customerid"
                    + " where a.acno = b.acno and b.custid = c.customerid and  a.acno = d.acno and  a.acno = bd.ACC_NO " + acctCat + " and a.accttype "
                    + " in ( select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag in ('D','B')) "
                    + " and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + " ((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno").getResultList();
            /*Main List start */
            String hdTab = "", bsTab = "", asTab = "", rsTab = "", crTab = "", gsTab = "", ssTab = "", cdTab = "", tsTab = "";
            for (int i = 0; i < l1.size(); i++) {
                Vector vtrmain = (Vector) l1.get(i);
                title = vtrmain.get(0) != null ? vtrmain.get(0).toString() : "N01";
                borName = vtrmain.get(1) != null ? vtrmain.get(1).toString() : "";
                dob = dmy.format(ymdhms.parse(vtrmain.get(2) != null ? vtrmain.get(2).toString() : "1900-01-01"));
                gender = vtrmain.get(3) != null ? vtrmain.get(3).toString() : "";
                borPan = vtrmain.get(4) != null ? vtrmain.get(4).toString() : "";
                if (!borPan.equalsIgnoreCase("") && borPan.length() == 10) {
                    if (ParseFileUtil.isAlphabet(borPan.substring(0, 5)) && ParseFileUtil.isNumeric(borPan.substring(5, 9)) && ParseFileUtil.isAlphabet(borPan.substring(9))) {
                        borPan = borPan;
                    } else {
                        borPan = "";
                    }
                } else {
                    borPan = "";
                }
                passportNo = vtrmain.get(5) != null ? vtrmain.get(5).toString() : "";
                voterIdNo = vtrmain.get(6) != null ? vtrmain.get(6).toString() : "";
                telephoneNumber = vtrmain.get(7) != null ? vtrmain.get(7).toString() : "";
                mailAddressLine1 = vtrmain.get(8) != null ? vtrmain.get(8).toString() : "";
                mailAddressLine2 = vtrmain.get(9) != null ? vtrmain.get(9).toString() : "";
                mailVillage = vtrmain.get(10) != null ? vtrmain.get(10).toString() : "";
                mailBlock = vtrmain.get(11) != null ? vtrmain.get(11).toString() : "";
                mailCityCode = vtrmain.get(12) != null ? vtrmain.get(12).toString() : "";
                mailStateCode = vtrmain.get(13) != null ? vtrmain.get(13).toString() : "";
                mailPostalCode = vtrmain.get(14) != null ? vtrmain.get(14).toString() : "";
                customerid = vtrmain.get(15) != null ? vtrmain.get(15).toString() : "";
                shortName = vtrmain.get(16) != null ? vtrmain.get(16).toString() : "";
                acNo = vtrmain.get(17) != null ? vtrmain.get(17).toString() : "";
                operMode = vtrmain.get(18) != null ? vtrmain.get(18).toString() : "";
                openingDt = vtrmain.get(19) != null ? vtrmain.get(19).toString() : "19000101";
                closingDate = vtrmain.get(20) != null ? vtrmain.get(20).toString() : "";
                accStatus = vtrmain.get(21) != null ? vtrmain.get(21).toString() : "";
                custId1 = vtrmain.get(22) != null ? vtrmain.get(22).toString() : "";
                custId2 = vtrmain.get(23) != null ? vtrmain.get(23).toString() : "";
                custId3 = vtrmain.get(24) != null ? vtrmain.get(24).toString() : "";
                custId4 = vtrmain.get(25) != null ? vtrmain.get(25).toString() : "";

                issueDate = vtrmain.get(26) != null ? vtrmain.get(26).toString() : "1900-01-01";
                expirydate = vtrmain.get(27) != null ? vtrmain.get(27).toString() : "1900-01-01";
                drivingLicenseNo = vtrmain.get(28) != null ? vtrmain.get(28).toString() : "";
                uidNo = vtrmain.get(29) != null ? vtrmain.get(29).toString() : "";
                telResident = vtrmain.get(30) != null ? vtrmain.get(30).toString() : "";
                telOffice = vtrmain.get(31) != null ? vtrmain.get(31).toString() : "";
                emailID = vtrmain.get(32) != null ? vtrmain.get(32).toString() : "";

                perAddressLine1 = vtrmain.get(33) != null ? vtrmain.get(33).toString() : "";
                perAddressLine2 = vtrmain.get(34) != null ? vtrmain.get(34).toString() : "";
                perVillage = vtrmain.get(35) != null ? vtrmain.get(35).toString() : "";
                perBlock = vtrmain.get(36) != null ? vtrmain.get(36).toString() : "";
                perCityCode = vtrmain.get(37) != null ? vtrmain.get(37).toString() : "";
                perStateCode = vtrmain.get(38) != null ? vtrmain.get(38).toString() : "";
                perPostalCode = vtrmain.get(39) != null ? vtrmain.get(39).toString() : "";
                odLimit = vtrmain.get(40) != null ? vtrmain.get(40).toString() : "";
                custNameAccMast = vtrmain.get(41) != null ? vtrmain.get(41).toString() : "";
                String shortNm = vtrmain.get(42).toString();
                String tinNo = vtrmain.get(43).toString();
                String sTax = vtrmain.get(44).toString();
                String voterID = vtrmain.get(45).toString();
                String dl = vtrmain.get(46).toString();
                String crRating = vtrmain.get(47).toString() != null ? vtrmain.get(47).toString() : "1900-01-01 00:00:00";
                String creditRatingAsOn = crRating != "" ? dmy.format(ymdhms.parse(crRating)) : "01011900";
                String tanNo = vtrmain.get(48).toString();
                String cin = vtrmain.get(49).toString();
                String otheriId = vtrmain.get(50).toString();
                String mobile = vtrmain.get(51).toString();
                String country = "91", accStatusDt = "";
                String sanctionDt = dmy.format(ymdhms.parse(vtrmain.get(53).toString()));
                relationShip = vtrmain.get(54).toString();
                String faxNo = vtrmain.get(55).toString();
                String acNture = vtrmain.get(56) != null ? vtrmain.get(56).toString() : "";
                borLglConst = vtrmain.get(57) != null ? vtrmain.get(57).toString() : "UN";

                String operationalRiskRating = vtrmain.get(58) != null ? vtrmain.get(58).toString() : "";
                String accounttypetable = vtrmain.get(59) != null ? vtrmain.get(59).toString() : "";

                String OldAcno = vtrmain.get(60) != null ? vtrmain.get(60).toString() : "";
                String oldAcType = vtrmain.get(61) != null ? vtrmain.get(61).toString() : "";
                String oldBranchCode = vtrmain.get(62) != null ? vtrmain.get(62).toString() : "";
                String oldOwnershipIndicator = vtrmain.get(63) != null ? vtrmain.get(63).toString() : "";
                String CustEntityType = vtrmain.get(64) != null ? vtrmain.get(64).toString() : "";
                String DateOfIncorporation = vtrmain.get(65) != null ? vtrmain.get(65).toString() : "";
                String suitFiledStatus = vtrmain.get(66) != null ? vtrmain.get(66).toString() : "";
                String WilfulDefaultStatus = vtrmain.get(67) != null ? vtrmain.get(67).toString() : "";
                writeoff = vtrmain.get(68) != null ? vtrmain.get(68).toString() : "";
                bussCat = vtrmain.get(69) != null ? vtrmain.get(69).toString() : "";
                bussType = vtrmain.get(70) != null ? vtrmain.get(70).toString() : "";

                if (cibil_old_acno.equalsIgnoreCase("N")) {
                    OldAcno = "";
                    oldAcType = "";
                    oldBranchCode = "";
                    oldOwnershipIndicator = "";
                }
                String noOfEmp = "", telAreCode = "", faxAreaCode = "", relatedType = "", amtRestruct = "", odBuc1 = "", odBuc2 = "", odBuc3 = "", odBuc4 = "", odBuc5 = "";
                String clsOfAct1 = "", clsOfAct2 = "", clsOfAct3 = "", sicCode = "", creditRating = "", authority = "", creditExpDt = "", locationType = "", dunsNo = "";

                String guarantor_entity_name = "", guarantor_prefix = "", guarantor_name = "", guarantor_gender = "",
                        guarantor_comp_regi_no = "", guarantor_doi = "", guarantor_dob = "", guarantor_pan = "", guarantor_votrid = "", guarantor_passport = "",
                        guarantor_dl = "", guarantor_uid = "", guarantor_ration = "", guarantor_cin = "", guarantor_din = "", guarantor_tin = "", guarantor_stax = "", guarantor_otherid = "", guarantor_add1 = "", guarantor_add2 = "", guarantor_add3 = "", guarantor_city = "", guarantor_district = "", guarantor_state = "", guarantor_pincode = "",
                        guarantor_country = "", guarantor_mobile = "", guarantor_tel_area = "", guarantor_tel_no = "", guarantor_fax_area = "", guarantor_fax_no = "", guarantorCustEntityType = "", guarantorDateOfIncor;
                creditRating = vtrmain.get(58) != null ? vtrmain.get(58).toString() : "";

                //BSPOJO
                /* In Our System It is in the ref_rec 325*/
                String borLglConstitution = "", assAuthority = "";
                if (borLglConst.equalsIgnoreCase("PL")) {
                    borLglConstitution = "11";
                } else if (borLglConst.equalsIgnoreCase("LC")) {
                    borLglConstitution = "12";
                } else if (borLglConst.equalsIgnoreCase("SB")) {
                    borLglConstitution = "20";
                } else if (borLglConst.equalsIgnoreCase("PC")) {
                    borLglConstitution = "30";
                } else if (borLglConst.equalsIgnoreCase("PF")) {
                    borLglConstitution = "40";
                } else if (borLglConst.equalsIgnoreCase("TG")) {
                    borLglConstitution = "50";
                } else if (borLglConst.equalsIgnoreCase("HUF")) {
                    borLglConstitution = "55";
                } else if (borLglConst.equalsIgnoreCase("COS")) {
                    borLglConstitution = "60";
                } else if (borLglConst.equalsIgnoreCase("IND")) {
                    borLglConstitution = "70";
                } else if (borLglConst.equalsIgnoreCase("CGP") || borLglConst.equalsIgnoreCase("CGD")) {
                    borLglConstitution = "80";
                } else if (borLglConst.equalsIgnoreCase("TG")) {
                    borLglConstitution = "85";
                } else {
                    borLglConstitution = "99";
                }
                if (bussCat.equalsIgnoreCase("KVIENT")) {
                    bussCat = "01";
                } else if (bussCat.equalsIgnoreCase("MICR")) {
                    bussCat = "02";
                } else if (bussCat.equalsIgnoreCase("MICE")) {
                    bussCat = "03";
                } else if (bussCat.equalsIgnoreCase("MNFEN5")) {
                    bussCat = "04";
                } else if (bussCat.equalsIgnoreCase("MEDEME")) {
                    bussCat = "05";
                } else if (bussCat.equalsIgnoreCase("MEDESE")) {
                    bussCat = "06";
                } else {
                    bussCat = "07";

                }
//                if (bussType.equalsIgnoreCase("MFG")) {
//                    bussType = "01";
//                } else if (bussType.equalsIgnoreCase("DIST")) {
//                    bussType = "02";
//                } else if (bussType.equalsIgnoreCase("WS")) {
//                    bussType = "03";
//                } else if (bussType.equalsIgnoreCase("TRD")) {
//                    bussType = "04";
//                } else if (bussType.equalsIgnoreCase("BROK")) {
//                    bussType = "05";
//                } else if (bussType.equalsIgnoreCase("SERP")) {
//                    bussType = "06";
//                } else if (bussType.equalsIgnoreCase("IMPRT")) {
//                    bussType = "07";
//                } else if (bussType.equalsIgnoreCase("EXPRT")) {
//                    bussType = "08";
//                } else if (bussType.equalsIgnoreCase("AGR")) {
//                    bussType = "09";
//                } else if (bussType.equalsIgnoreCase("DEALR")) {
//                    bussType = "10";
//                } else {
//                    bussType = "11";
//                }
//                if (authority.equalsIgnoreCase("CARE")) {
//                    assAuthority = "01";
//                } else if (authority.equalsIgnoreCase("CRISIL")) {
//                    assAuthority = "02";
//                } else if (authority.equalsIgnoreCase("ICRA")) {
//                    assAuthority = "03";
//                } else if (authority.equalsIgnoreCase("ONICRA")) {
//                    assAuthority = "04";
//                } else if (authority.equalsIgnoreCase("SMERA")) {
//                    assAuthority = "05";
//                } else if (authority.equalsIgnoreCase("FITCH")) {
//                    assAuthority = "06";
//                } else {
//                    assAuthority = "07";
//                }
                if (repType.equalsIgnoreCase("1")) {
                    bsTab = "BS|" + acNo.substring(0, 2) + "|" + oldBranchCode + "|" + borName + "|" + shortNm + "|" + cin + "|"
                            + DateOfIncorporation + "|" + borPan + "|" + cin + "|" + tinNo + "|" + sTax + "|" + otheriId + "|" + borLglConstitution
                            + "|" + bussCat + "|" + bussType + "|" + classOFAct1 + "|"
                            + classOfAct2 + "|" + classOfAct3 + "|" + sicCode + "|" + "" + "|" + getFinYear(toDate) + "|" + noOfEmp + "|"
                            + creditRating + "|" + assAuthority + "|" + creditRatingAsOn + "|" + "" + "|" + "" + "|";
                } else {
                    bsTab = "BS|" + acNo.substring(0, 2) + "|" + oldBranchCode + "|" + borName + "|" + shortNm + "|" + cin + "|"
                            + DateOfIncorporation + "|" + borPan + "|" + cin + "|" + tinNo + "|" + sTax + "|" + otheriId + "|" + borLglConstitution
                            + "|" + bussCat + "|" + bussType + "|" + classOFAct1 + "|"
                            + classOfAct2 + "|" + classOfAct3 + "|" + sicCode + "|" + "" + "|" + getFinYear(toDate) + "|" + noOfEmp + "|"
                            + creditRating + "|" + assAuthority + "|" + creditRatingAsOn + "|" + "" + "|" + "" + "|";
                }
                bw.write(bsTab + "\n");
                //End Of BS Pojo.

                //ASPOJO
                address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                        !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                        !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                        !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                        !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                if (address.contains(" ")) {
                    space = " ";
                } else if (address.contains(",")) {
                    space = ",";
                } else {
                    space = "-";
                }
                if (address.length() > 40) {
                    if (!address.substring(40).contains(space)) {
                        StringBuilder sb = new StringBuilder();
                        String[] pairs = address.split(",");
                        sb.append(pairs[0]);
                        for (int t = 1; t < pairs.length; ++t) {
                            String pair = pairs[t];
                            sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                            sb.append(pair);
                        }
                        address = sb.toString();
                    }
                }

                borAdd1 = address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "";
                //address = (address.length() > 200) ? address.substring(address.substring(0, 200).lastIndexOf(space) + 1) : "";
                borAdd2 = address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "";
                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                borAdd3 = address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "";
                if (repType.equalsIgnoreCase("1")) {
                    asTab = "AS" + "|" + "01" + "|" + "999999999" + "|" + borAdd1 + "|" + borAdd2 + "|" + borAdd3 + "|" + mailCityCode + "|"
                            + mailCityCode + "|" + mailStateCode + "|" + mailPostalCode + "|" + "079" + "|"
                            + mobile + "|" + borTelAreaCode + "|" + borTelNo + "|" + borFaxArea + "|" + borFaxNo + "|" + "" + "|";
                } else {
                    asTab = "AS" + "|" + "01" + "|" + "999999999" + "|" + borAdd1 + "|" + borAdd2 + "|" + borAdd3 + "|" + mailCityCode + "|"
                            + mailCityCode + "|" + mailStateCode + "|" + mailPostalCode + "|" + "079" + "|"
                            + mobile + "|" + borTelAreaCode + "|" + borTelNo + "|" + borFaxArea + "|" + borFaxNo + "|" + "" + "|";
                }

                bw.write(asTab + "\n");
                //End OF ASPojo.This can be multiple Times for an Account.


                custIdList = new ArrayList();
                if (!custId1.equalsIgnoreCase("")) {
                    custIdList.add(custId1);
                }
                if (!custId2.equalsIgnoreCase("")) {
                    custIdList.add(custId2);
                }
                if (!custId3.equalsIgnoreCase("")) {
                    custIdList.add(custId3);
                }
                if (!custId4.equalsIgnoreCase("")) {
                    custIdList.add(custId4);
                }
                String jtTitle = "", jtCustFullName = "", jtGender = "", jtDob = "", jtPAN = "", jtVoterID = "", jtPasspost = "", jtDlNo = "", jtAadhar = "", jtCin = "", jtTin = "", jtSTax = "",
                        jtOtherID = "", jtcity = "", jtPinCode = "", jtCountry = "91", jtMobile = "",
                        jtFaxArea = "", jtFaxNo = "", jtDin = "", jtRationCard = "", jtPerControl = "", jtBussEntity = "", jtBussCat = "", jtBussType = "", jtRegNo = "";
                String jtDoi = "", jtSalesFigure = "", jtFinYear = "", finYear = "";


                // End Of GS Pojo.
                //RS Pojo
                if (custIdList.size() > 0) {
                    for (int c = 0; c < custIdList.size(); c++) {
                        String jtCustId = "";
                        if (custIdList.size() == 1) {
                            jtCustId = custIdList.get(0).toString();
                        } else {
                            String custId = (String) custIdList.get(c);
                            jtCustId = custId.toString();
                        }
                        List jtCustdetail = em.createNativeQuery("select ifnull(c.title,''),ifnull(c.CustFullName,''),case c.gender when 'M' then '01'  "
                                + " when 'F' then '02' when 'O' then '03' when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
                                + " ifnull(c.PAN_GIRNumber,''),ifnull(c.VoterIDNo,''),ifnull(c.PassportNo,''),ifnull(c.DrivingLicenseNo,''),ifnull(aa.IdentityNo,''),ifnull(c.cin,''),ifnull(c.TINNumber,''),ifnull(c.SalesTaxNo,''),"
                                + " ifnull(c.other_identity,''),ifnull(c.MailAddressLine1,''), ifnull(c.MailAddressLine2,'') ,"
                                + " ifnull((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = c.mailCityCode),'') as MailCityCode, "
                                + " ifnull((select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode),'') as mailstatecode, "
                                + " ifnull(c.MailPostalCode,''),ifnull(c.MailCountryCode,''),ifnull(c.MailPhoneNumber,''),ifnull(c.MailTelexNumber,''),ifnull(c.MailFaxNumber,'') "
                                + " from cbs_customer_master_detail  c "
                                + " left outer join "
                                + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                                + " union "
                                + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                                + "where c.customerid='" + jtCustId + "';").getResultList();
                        List jtDetail = em.createNativeQuery("select ifnull(Commencement_Date,''),ifnull(Mis_Tin,''),ifnull(SalesTurnover,'') from cbs_cust_misinfo where customerid = '" + jtCustId + "'").getResultList();
                        if (!jtDetail.isEmpty()) {
                            Vector vect1 = (Vector) jtDetail.get(0);
                            jtDoi = vect1.get(0).toString();
                            jtSalesFigure = vect1.get(2).toString();
                        }
                        if (!jtCustdetail.isEmpty()) {
                            Vector jtVect = (Vector) jtCustdetail.get(0);
                            jtTitle = jtVect.get(0).toString();
                            jtCustFullName = jtVect.get(1).toString();
                            jtGender = jtVect.get(2).toString();
                            jtDob = jtVect.get(3).toString();
                            jtPAN = jtVect.get(4).toString();
                            if (!jtPAN.equalsIgnoreCase("") && jtPAN.length() == 10) {
                                if (ParseFileUtil.isAlphabet(jtPAN.substring(0, 5)) && ParseFileUtil.isNumeric(jtPAN.substring(5, 9)) && ParseFileUtil.isAlphabet(jtPAN.substring(9))) {
                                    jtPAN = jtPAN;
                                } else {
                                    jtPAN = "";
                                }
                            } else {
                                jtPAN = "";
                            }
                            jtVoterID = jtVect.get(5).toString();
                            jtPasspost = jtVect.get(6).toString();
                            jtDlNo = jtVect.get(7).toString();
                            jtAadhar = jtVect.get(8).toString();
                            jtCin = jtVect.get(9).toString();
                            jtTin = jtVect.get(10).toString();
                            jtSTax = jtVect.get(11).toString();
                            jtOtherID = jtVect.get(12).toString();
                            jtAdd1 = jtVect.get(13).toString();
                            jtAdd2 = jtVect.get(14).toString();
                            jtcity = jtVect.get(15).toString();
                            jtState = jtVect.get(16).toString();
                            jtPinCode = jtVect.get(17).toString();
//                                    jtCountry = jtVect.get(18).toString();
                            jtMobile = jtVect.get(19).toString();
                            jtTelNo = jtVect.get(20).toString();
                            jtFaxNo = jtVect.get(21).toString();
                        }

                        //RSPOJO
                        CibilRSPoJo rsPojo;
                        rsPojo = new CibilRSPoJo();
                        rsPojo.setAcno(acNo);
                        rsPojo.setFlag("3");
                        /*01 MSME 
                         * 02 SME 
                         * 03 Micro 
                         * 04 Small 
                         * 05 Medium 
                         * 06 Large 
                         * 07 Others */
//                        if (bussCat.equalsIgnoreCase("KVIENT")) {
//                            bussCat = "01";
//                        } else if (bussCat.equalsIgnoreCase("MICR")) {
//                            bussCat = "02";
//                        } else if (bussCat.equalsIgnoreCase("MICE")) {
//                            bussCat = "03";
//                        } else if (bussCat.equalsIgnoreCase("MNFEN5")) {
//                            bussCat = "04";
//                        } else if (bussCat.equalsIgnoreCase("MEDEME")) {
//                            bussCat = "05";
//                        } else if (bussCat.equalsIgnoreCase("MEDESE")) {
//                            bussCat = "06";
//                        } else {
//                            bussCat = "07";
//
//                        }
//                        if (CustEntityType.equalsIgnoreCase("02")) {
//                            bussCat = "";
//                        }
                        /*
                         * 01 Manufacturing 
                         * 02 Distribution 
                         * 03 Wholesale 
                         * 04 Trading 
                         * 05 Broking
                         * 06 Service Provider 
                         * 07 Importing 
                         * 08 Exporting 
                         * 09 Agriculture 
                         * 10 Dealers 
                         * 11 Others
                         */
                        /* 
                         10 Shareholder                 
                         11 Holding Company
                         12 Subsidiary Company
                         20 Proprietor                  PC	PROPRIETARY CONCERNS
                         30 Partner                     PF	PARTNERSHIP FIRM 
                         40 Trustee                     TG	TRUST GROUP 
                         51 Promoter Director
                         52 Nominee Director
                         53 Independent Director
                         54 Director - Since Resigned
                         55 Individual Member of SHG
                         56 Other Director
                         60 Others                       UN	UNCLASSIFIED
                         */
                        String relation = "60";
                        if (borLglConst.equalsIgnoreCase("PC")) {
                            relation = "20";
                        } else if (borLglConst.equalsIgnoreCase("PF")) {
                            relation = "30";
                        } else if (borLglConst.equalsIgnoreCase("TG")) {
                            relation = "40";
                        }


//                        if (bussType.equalsIgnoreCase("MFG")) {
//                            bussType = "01";
//                        } else if (bussType.equalsIgnoreCase("DIST")) {
//                            bussType = "02";
//                        } else if (bussType.equalsIgnoreCase("WS")) {
//                            bussType = "03";
//                        } else if (bussType.equalsIgnoreCase("TRD")) {
//                            bussType = "04";
//                        } else if (bussType.equalsIgnoreCase("BROK")) {
//                            bussType = "05";
//                        } else if (bussType.equalsIgnoreCase("SERP")) {
//                            bussType = "06";
//                        } else if (bussType.equalsIgnoreCase("IMPRT")) {
//                            bussType = "07";
//                        } else if (bussType.equalsIgnoreCase("EXPRT")) {
//                            bussType = "08";
//                        } else if (bussType.equalsIgnoreCase("AGR")) {
//                            bussType = "09";
//                        } else if (bussType.equalsIgnoreCase("DEALR")) {
//                            bussType = "10";
//                        } else {
//                            bussType = "11";
//                        }

                        //  1= Business Entity Registered in India my cbs code 02
                        //   2=Resident Indian Individual my cbs code 01
                        if (CustEntityType.equalsIgnoreCase("01")) {
                            bussType = "";
                            bussCat = "";
                            jtBussEntity = "";
                            CustEntityType = "02";
                        } else {
                            jtBussEntity = jtCustFullName;
                            CustEntityType = "01";
                        }
                        if (!jtDob.equalsIgnoreCase("")) {
                            jtDob = dmy.format(ymdhms.parse(jtDob));
                        }

//                        if (Integer.parseInt(CustEntityType) == 2) {
//                            jtBussEntity = jtCustFullName;
//                            CustEntityType = "01";
//                        }

                        if (repType.equalsIgnoreCase("1")) {
                            rsTab = "RS" + "|" + "999999999" + "|" + Integer.parseInt(CustEntityType) + "|" + relation + "|" + jtBussEntity + "|" + bussCat + "|" + bussType
                                    + "|" + jtPreFix + "|" + jtCustFullName + "|" + jtGender + "|" + "" + "|" + jtDoi + "|" + jtDob + "|" + jtPAN
                                    + "|" + jtVoterID + "|" + jtPasspost + "|" + jtDlNo + "|" + jtAadhar + "|" + jtRationCard + "|" + jtCin + "|" + jtDin + "|" + jtTin
                                    + "|" + jtSTax + "|" + jtOtherID + "|" + "" + "|" + jtAdd1 + "|" + jtAdd2 + "|" + jtAdd3 + "|" + jtcity + "|" + jtcity + "|" + jtState + "|" + jtPinCode
                                    + "|" + "079" + "|" + mobile + "|" + jtTelNo + "|" + jtTelArea + "|" + jtFaxNo + "|" + jtFaxArea + "|" + "" + "|";
                        } else {
                            rsTab = "RS" + "|" + "999999999" + "|" + Integer.parseInt(CustEntityType) + "|" + relation + "|" + jtBussEntity + "|" + bussCat + "|" + bussType
                                    + "|" + jtPreFix + "|" + jtCustFullName + "|" + jtGender + "|" + "" + "|" + jtDoi + "|" + jtDob + "|" + jtPAN
                                    + "|" + jtVoterID + "|" + jtPasspost + "|" + jtDlNo + "|" + jtAadhar + "|" + jtRationCard + "|" + jtCin + "|" + jtDin + "|" + jtTin
                                    + "|" + jtSTax + "|" + jtOtherID + "|" + "" + "|" + jtAdd1 + "|" + jtAdd2 + "|" + jtAdd3 + "|" + jtcity + "|" + jtcity + "|" + jtState + "|" + jtPinCode
                                    + "|" + "079" + "|" + mobile + "|" + jtTelNo + "|" + jtTelArea + "|" + jtFaxNo + "|" + jtFaxArea + "|" + "" + "|";
                        }
                        bw.write(rsTab + "\n");
                    }
                }
                //rs tab end


                //CRPOJO
                String lastPaymentDt = "", lastCrAmt = "0";
                String periodicity = "", repaymentTenure = "0", emiAmt = "0", highCredit = "0", npaAmt = "0", guaranteeCover = "", renewalDt = "", assetClassDt = "";
                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01'),ifnull(cast(cramt as decimal(25)),0) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                if (!l4.isEmpty()) {
                    vtr1 = (Vector) l4.get(0);
                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                    lastCrAmt = vtr1.get(1) != null ? vtr1.get(1).toString() : "0";
                    lastCrAmt = String.valueOf(Integer.parseInt(lastCrAmt));
                }
                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                if (!lemi.isEmpty()) {
                    Vector vtrEmi = (Vector) lemi.get(0);
                    repaymentTenure = vtrEmi.get(0).toString();
                    if (vtrEmi.get(1).toString().equalsIgnoreCase("0")) {
                        emiAmt = "";
                    } else {
                        emiAmt = vtrEmi.get(1).toString();
                    }
                    periodicity = vtrEmi.get(2).toString();
                }
                int period = 0;
                String securityCoverage = "", securityCoverageCode = "";
                String crType = common.getAcctNature(acNo.substring(2, 4));
                List lb = em.createNativeQuery("select cast(ifnull(LOAN_DURATION,0) as unsigned),SECURED from cbs_loan_borrower_details where acc_no =" + acNo + " ").getResultList();
                if (!lb.isEmpty()) {
                    Vector lbvect = (Vector) lb.get(0);
                    period = Integer.parseInt(lbvect.get(0).toString());
                    securityCoverage = lbvect.get(1).toString();
                }
                if (securityCoverage.equalsIgnoreCase("FLSEC")) {
                    securityCoverageCode = "01";
                } else if (securityCoverage.equalsIgnoreCase("UNSEC")) {
                    securityCoverageCode = "03";
                } else {
                    securityCoverageCode = "02";
                }
                String repaymntFreq = "";
                /*01 Monthly 
                 * 02 Quarterly 
                 * 03 Half yearly 
                 * 04 Annual 
                 * 05 On Demand 
                 * 06 Bullet 
                 * 07 Rolling 
                 * 08 Others */
                if (periodicity.equalsIgnoreCase("M")) {
                    repaymntFreq = "01";
                } else if (periodicity.equalsIgnoreCase("Q")) {
                    repaymntFreq = "02";
                } else if (periodicity.equalsIgnoreCase("H")) {
                    repaymntFreq = "03";
                } else if (periodicity.equalsIgnoreCase("Y")) {
                    repaymntFreq = "04";
                } else {
                    repaymntFreq = "08";
                }
                if (crType.equalsIgnoreCase(CbsConstant.CC_AC)) {
                    creditType = "0100";
                } else if (crType.equalsIgnoreCase(CbsConstant.OD_AC)) {
                    creditType = "0200";
                } else if (crType.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    creditType = "0300";
                } else if (crType.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    if (period < 12) {
                        creditType = "5100";
                    } else if (period > 12 && period <= 36) {
                        creditType = "0410";
                    } else if (period > 36) {
                        creditType = "0420";
                    } else {
                        creditType = "6000";
                    }
                } else {
                    creditType = "6000";
                }
                List npaList = em.createNativeQuery("select ifnull(cast(sum(dramt-cramt)as decimal(25)),0) from npa_recon where acno ='" + acNo + "' and dt<='" + toDate + "'").getResultList();
                if (!npaList.isEmpty()) {
                    Vector npaVect = (Vector) npaList.get(0);
                    npaAmt = npaVect.get(0) != null ? npaVect.get(0).toString() : "";
                    npaAmt = String.valueOf(Integer.parseInt(npaAmt));
                }
                List currBal = em.createNativeQuery("select cast(sum(dramt-cramt)as decimal(25)) from " + common.getTableName(acNture) + " where acno ='" + acNo + "' and dt<='" + toDate + "'").getResultList();
                if (!currBal.isEmpty()) {
                    Vector currVect = (Vector) currBal.get(0);
                    currentBal = currVect.get(0) != null ? currVect.get(0).toString() : "0";
                    currentBal = String.valueOf(Integer.parseInt(currentBal));
                }
                CibilCRPoJo crPojo = new CibilCRPoJo();
                crPojo.setAcno(acNo);
                crPojo.setFlag("4");
                crPojo.setSegId("CR");
                cr.add(crPojo);
                if (closingDate.equalsIgnoreCase("") || (ymd.parse(closingDate).after(ymd.parse(toDate)))) {
                    if (ymd.parse(CbsUtil.monthAdd(ymd.format(ymdhms.parse(vtrmain.get(53).toString())), period)).after(ymd.parse(toDate))) {
                        closingDate = dmy.format(ymd.parse(CbsUtil.monthAdd(ymd.format(ymdhms.parse(vtrmain.get(53).toString())), period)));
                    } else {
                        closingDate = "";
                    }
                } else {
                    closingDate = dmy.format(ymd.parse(closingDate));
                }
                renewalDt = CbsUtil.dateAdd(CbsUtil.monthAdd(ymd.format(ymdhms.parse(vtrmain.get(53).toString())), period), 1);
                String presentStatus = "";
                if (accStatus.equalsIgnoreCase("1")) {
                    presentStatus = "STD";
                    WilfulDefaultStatus = "0";
                } else if (accStatus.equalsIgnoreCase("11")) {
                    presentStatus = "SUB";
                } else if (accStatus.equalsIgnoreCase("12")) {
                    presentStatus = "DOU";
                } else if (accStatus.equalsIgnoreCase("13")) {
                    presentStatus = "LOS";
                }
                if (!accStatus.equalsIgnoreCase("9")) {
                    List assetClsDt = em.createNativeQuery("SELECT ifnull(date_format(MAX(EFFDT),'%Y%m%d'),'19000101') FROM accountstatus a, codebook c "
                            + " WHERE ACNO='" + acNo + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                            + " and effdt <='" + toDate + "'").getResultList();
                    if (!assetClsDt.isEmpty()) {
                        Vector vect = (Vector) assetClsDt.get(0);
                        assetClassDt = vect.get(0).toString();
                    }
                }
                if (accStatus.equalsIgnoreCase("1")) {
                    assetClass = "0001";
                    //crPojo.setAssetClassDt(assetClassDt.equalsIgnoreCase("19000101") ? dmy.format(ymd.parse(openingDt)) : dmy.format(ymd.parse(assetClassDt)));
                    assetClassDt = assetClassDt.equalsIgnoreCase("19000101") ? dmy.format(ymd.parse(openingDt)) : dmy.format(ymd.parse(assetClassDt));
                } else if (accStatus.equalsIgnoreCase("11")) {
                    assetClass = "0002";
                    assetClassDt = assetClassDt.equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(assetClassDt));
                } else if (accStatus.equalsIgnoreCase("12")) {
                    assetClass = "0003";
                    assetClassDt = assetClassDt.equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(assetClassDt));
                } else if (accStatus.equalsIgnoreCase("13")) {
                    assetClass = "0004";
                    assetClassDt = assetClassDt.equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(assetClassDt));
                } else if (accStatus.equalsIgnoreCase("9")) {
//                    assetClass = "0005";
//                    if (!closingDate.equalsIgnoreCase("")) {
//                        assetClassDt = dmy.format(ymd.parse(closingDate));
//                    }
                    assetClass = "0001";
                } else {
                    assetClass = "0005";
                    assetClassDt = assetClassDt.equalsIgnoreCase("19000101") ? dmy.format(ymd.parse(openingDt)) : dmy.format(ymd.parse(assetClassDt));
                }
                /*0001	standard
                 * 0002	substandard
                 * 0003	doub
                 * 0004	loss
                 * 0005	special
                 * 1001	1day past due
                 * 1002	2 day past due*/
                String odAmt = "0";
                String dpLimit = "0";
                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                    OverDueList = OverdueCaList;
                    if (!OverDueList.isEmpty()) {
                        for (int k = 0; k < OverDueList.size(); k++) {
                            OverdueNonEmiResultList vect = OverDueList.get(k);
                            if (vect.getAccountNo().equalsIgnoreCase(acNo)) {
                                odAmt = String.valueOf((int) vect.getOverDue());
                                if (vect.getCurrentStatusNoOfDays() > 0 & vect.getCurrentStatusNoOfDays() < 31) {
                                    odBuc1 = odAmt;
                                } else if (vect.getCurrentStatusNoOfDays() > 30 & vect.getCurrentStatusNoOfDays() < 61) {
                                    odBuc2 = odAmt;
                                } else if (vect.getCurrentStatusNoOfDays() > 60 & vect.getCurrentStatusNoOfDays() < 91) {
                                    odBuc3 = odAmt;
                                } else if (vect.getCurrentStatusNoOfDays() > 90 & vect.getCurrentStatusNoOfDays() < 181) {
                                    odBuc4 = odAmt;
                                } else if (vect.getCurrentStatusNoOfDays() > 180 & vect.getCurrentStatusNoOfDays() < 999999) {
                                    odBuc5 = odAmt;
                                }
                            }
                        }
                    }
//                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' and txnid = (select max(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "')").getResultList();
//                    if (!sanctionLimitDtList.isEmpty()) {
//                        Vector vist = (Vector) sanctionLimitDtList.get(0);
//                        odLimit = vist.get(1).toString();
//                    } else {
//                        odLimit = odLimit;
//                        dpLimit = odLimit;
//                    }
//                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                        double overdue = Double.parseDouble(currentBal) - Double.parseDouble(odLimit);
//                        amtOverdue = String.valueOf(overdue);
//                    } else {
//                        amtOverdue = "0";
//                    }                    
                    // crPojo.setOdAmt(amtOverdue);
//                    odAmt = amtOverdue;
//                    odBuc1 = amtOverdue;
//                    crPojo.setOdBucket2("0");
//                    crPojo.setOdBucket3("0");
//                    crPojo.setOdBucket4("0");
//                    crPojo.setOdBucket5("0");
                } else {
                    List excessList = null;
                    double excess = 0;
                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        OverDueList = OverdueDlList;
                        if (!OverDueList.isEmpty()) {
                            for (int k = 0; k < OverDueList.size(); k++) {
                                OverdueNonEmiResultList vect = OverDueList.get(k);
                                if (vect.getAccountNo().equalsIgnoreCase(acNo)) {
                                    odAmt = String.valueOf((int) vect.getOverDue());
                                    if (vect.getCurrentStatusNoOfDays() > 0 & vect.getCurrentStatusNoOfDays() < 31) {
                                        odBuc1 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 30 & vect.getCurrentStatusNoOfDays() < 61) {
                                        odBuc2 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 60 & vect.getCurrentStatusNoOfDays() < 91) {
                                        odBuc3 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 90 & vect.getCurrentStatusNoOfDays() < 181) {
                                        odBuc4 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 180 & vect.getCurrentStatusNoOfDays() < 999999) {
                                        odBuc5 = odAmt;
                                    }
                                }
                            }
                        }
                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        OverDueTLList = OverdueTlList;
                        if (!OverDueTLList.isEmpty()) {
                            for (int k = 0; k < OverDueTLList.size(); k++) {
                                OverdueEmiReportPojo vect = OverDueTLList.get(k);
                                if (vect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                    odAmt = String.valueOf(vect.getAmountOverdue().intValue());
                                    if (vect.getCurrentStatusNoOfDays() > 0 & vect.getCurrentStatusNoOfDays() < 31) {
                                        odBuc1 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 30 & vect.getCurrentStatusNoOfDays() < 61) {
                                        odBuc2 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 60 & vect.getCurrentStatusNoOfDays() < 91) {
                                        odBuc3 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 90 & vect.getCurrentStatusNoOfDays() < 181) {
                                        odBuc4 = odAmt;
                                    } else if (vect.getCurrentStatusNoOfDays() > 180 & vect.getCurrentStatusNoOfDays() < 999999) {
                                        odBuc5 = odAmt;
                                    }
                                }
                            }
                        }
                    }
//                    if (isExcessEmi == 0) {
//                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                    } else {
//                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                + "(select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                    }
//                    if (!excessList.isEmpty()) {
//                        if (!excessList.isEmpty()) {
//                            Vector ele = (Vector) excessList.get(0);
//                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                excess = Double.parseDouble(ele.get(0).toString());
//                            }
//                        }
//                    }
//                    List l6 = em.createNativeQuery("select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null )  and duedt<'" + toDate + "' ").getResultList();
//                    if (!l6.isEmpty()) {
//                        vtr1 = (Vector) l6.get(0);
//                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                            if (Double.parseDouble(amtOverdue) > Double.parseDouble(currentBal)) {
//                                amtOverdue = currentBal;
//                            } else {
//                                amtOverdue = amtOverdue;
//                            }
//                            double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                            amtOverdue = String.valueOf(overDueAmt);
//                        }
//                        odAmt = amtOverdue;
//                    }
//                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                    overDueDays = "0";
//                    if (!l7.isEmpty()) {
//                        vtr1 = (Vector) l7.get(0);
//                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                    }
//
//                    odBuc1 = "0";
//                    odBuc2 = "0";
//                    odBuc3 = "0";
//                    odBuc4 = "0";
//                    odBuc5 = "0";
//
//                    if (!overDueDays.equalsIgnoreCase("")) {
//                        if (Integer.parseInt(overDueDays) <= 30) {
//                            odBuc1 = amtOverdue;
//                        } else if (Integer.parseInt(overDueDays) > 30 && Integer.parseInt(overDueDays) <= 60) {
//                            odBuc2 = amtOverdue;
//                        } else if (Integer.parseInt(overDueDays) < 60 && Integer.parseInt(overDueDays) <= 90) {
//                            odBuc3 = amtOverdue;
//                        } else if (Integer.parseInt(overDueDays) < 90 && Integer.parseInt(overDueDays) <= 180) {
//                            odBuc4 = amtOverdue;
//                        } else if (Integer.parseInt(overDueDays) < 180) {
//                            odBuc5 = amtOverdue;
//                        }
//                    } else {
//                        odBuc1 = amtOverdue;
//                        odBuc2 = "0";
//                        odBuc3 = "0";
//                        odBuc4 = "0";
//                        odBuc5 = "0";
//                    }
                }
                if (accStatus.equalsIgnoreCase("9")) {
                    accStatus = "03";
                    if (!closingDate.equalsIgnoreCase("")) {
                        accStatusDt = dmy.format(ymd.parse(closingDate));
                    }
                } else {
                    accStatus = "01";
                    accStatusDt = dmy.format(ymd.parse(openingDt));
                }
                if (suitAmt.doubleValue() == 0) {
                    suitAmount = "0";
                } else {
                    suitAmount = suitAmt.toString();
                }
                if (WilfulDefaultStatus.equalsIgnoreCase("0")) {
                    WilfulDefaultStatus = "0";
                }
                if (highCredit.equalsIgnoreCase("0")) {
                    highCredit = "";
                }
                if (suitFiledStatus.equalsIgnoreCase("NPA")) {
                    suitFiledStatus = "00";
                } else if (suitFiledStatus.equalsIgnoreCase("SF")) {
                    suitFiledStatus = "01";
                } else if (suitFiledStatus.equalsIgnoreCase("DECRE")) {
                    suitFiledStatus = "03";
                }
                if (odAmt.equalsIgnoreCase("0")) {
                    odAmt = "0";
                }
                if (npaAmt.equalsIgnoreCase("0")) {
                    npaAmt = "";
                }

                if (writeoff.equalsIgnoreCase("WROFF")) {
                    writtrnOff = currentBal;
                } else {
                    writtrnOff = "";
                }

                if (repType.equalsIgnoreCase("1")) {

                    crTab = "CR" + "|" + acNo + "|" + OldAcno + "|" + sanctionDt + "|" + String.valueOf(Integer.parseInt(odLimit)) + "|" + "INR" + "|"
                            + creditType + "|" + String.valueOf(period) + "|" + repaymntFreq + "|" + String.valueOf(Integer.parseInt(odLimit)) + "|" + currentBal + "|" + "" + "|" + closingDate + "|"
                            + dmy.format(ymd.parse(renewalDt)) + "|" + assetClass + "|" + assetClassDt + "|" + odAmt + "|" + odBuc1 + "|" + odBuc2 + "|"
                            + odBuc3 + "|" + odBuc4 + "|" + odBuc5 + "|" + highCredit + "|" + emiAmt + "|"
                            + lastCrAmt + "|" + accStatus + "|" + accStatusDt + "|" + writtrnOff + "|" + "" + "|" + "99" + "|"
                            + npaAmt + "|" + securityCoverageCode + "|" + guaranteeCover + "|" + bnkRemarkCode + "|" + WilfulDefaultStatus + "|" + "czc" + "|" + suitFiledStatus + "|" + suitRefNo + "|" + suitAmount + "|"
                            + suitDt + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|";
                } else {

                    crTab = "CR" + "|" + acNo + "|" + OldAcno + "|" + sanctionDt + "|" + String.valueOf(Integer.parseInt(odLimit)) + "|" + "INR" + "|"
                            + creditType + "|" + String.valueOf(period) + "|" + repaymntFreq + "|" + String.valueOf(Integer.parseInt(odLimit)) + "|" + currentBal + "|" + "" + "|" + closingDate + "|"
                            + dmy.format(ymd.parse(renewalDt)) + "|" + assetClass + "|" + assetClassDt + "|" + odAmt + "|" + odBuc1 + "|" + odBuc2 + "|"
                            + odBuc3 + "|" + odBuc4 + "|" + odBuc5 + "|" + highCredit + "|" + emiAmt + "|"
                            + lastCrAmt + "|" + accStatus + "|" + accStatusDt + "|" + writtrnOff + "|" + "" + "|" + "99" + "|"
                            + npaAmt + "|" + securityCoverageCode + "|" + guaranteeCover + "|" + bnkRemarkCode + "|" + WilfulDefaultStatus + "|" + "czc" + "|" + suitFiledStatus + "|" + suitRefNo + "|" + suitAmount + "|"
                            + suitDt + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|";
                }
                bw.write(crTab + "\n");
                // End OF CR Pojo.This can be multiple Times for an Account.





                //gstab start
                List l8 = em.createNativeQuery("select phno,address,acno,name,ifnull(CUST_FLAG,'N'),ifnull(GAR_ACNO,''),ifnull(GAR_CUSTID,'') from loan_guarantordetails where acno ='" + acNo + "'").getResultList();

                if (!l8.isEmpty()) { /*If Guarantor Exists.*/

                    for (int k = 0; k < l8.size(); k++) {
                        Vector vtr = (Vector) l8.get(k);
                        String garCustFlag = vtr.get(4).toString();
                        String garCustAcNo = vtr.get(5).toString();
                        String garCustId = vtr.get(6).toString();
                        List l3 = custIdListForGuarantor(garCustId);
                        //GSPOJO                        
                        // CibilGSPoJo gsPojo = new CibilGSPoJo();

                        if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                            /* If Custid Exist in Guarantor */
                            if (!l3.isEmpty()) {
                                vtr = (Vector) l3.get(0);
//                                Vector gsVect = (Vector) l3.get(0);
                                guarantor_prefix = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                guarantor_name = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                guarantor_dob = dmy.format(ymdhms.parse(vtr.get(2) != null ? vtr.get(2).toString() : "19000101"));
                                guarantor_gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                guarantor_pan = vtr.get(4) != null ? vtr.get(4).toString() : "";
                                if (!guarantor_pan.equalsIgnoreCase("") && guarantor_pan.length() == 10) {
                                    if (ParseFileUtil.isAlphabet(guarantor_pan.substring(0, 5)) && ParseFileUtil.isNumeric(guarantor_pan.substring(5, 9)) && ParseFileUtil.isAlphabet(guarantor_pan.substring(9))) {
                                        guarantor_pan = guarantor_pan;
                                    } else {
                                        guarantor_pan = "";
                                    }
                                } else {
                                    guarantor_pan = "";
                                }
                                guarantor_passport = vtr.get(5) != null ? vtr.get(5).toString() : "";
                                guarantor_votrid = vtr.get(6) != null ? vtr.get(6).toString() : "";
                                guarantor_tel_no = vtr.get(7) != null ? vtr.get(7).toString() : "";
                                guarantor_add1 = vtr.get(8) != null ? vtr.get(8).toString() : "";
                                guarantor_add2 = vtr.get(9) != null ? vtr.get(9).toString() : "";
                                guarantor_add3 = vtr.get(10) != null ? vtr.get(10).toString() : "".concat(vtr.get(11) != null ? vtr.get(11).toString() : "");
                                guarantor_city = vtr.get(12) != null ? vtr.get(12).toString() : "";
                                guarantor_state = vtr.get(13) != null ? vtr.get(13).toString() : "";
                                guarantor_pincode = vtr.get(14) != null ? vtr.get(14).toString() : "";
                                customerid = vtr.get(15) != null ? vtr.get(15).toString() : "";
                                shortName = vtr.get(16) != null ? vtr.get(16).toString() : "";
                                guarantor_dl = vtr.get(19) != null ? vtr.get(19).toString() : "";
                                guarantor_uid = vtr.get(20) != null ? vtr.get(20).toString() : "";
                                telResident = vtr.get(21) != null ? vtr.get(21).toString() : "";
                                guarantorCustEntityType = vtr.get(31) != null ? vtr.get(31).toString() : "";
                                guarantorDateOfIncor = vtr.get(32) != null ? vtr.get(32).toString() : "";
                                String busiCust = "", busiType = "";
                                if (guarantorCustEntityType.equalsIgnoreCase("01")) {
                                    busiCust = "07";
                                    busiType = "11";
                                }
                                //RSPOJO
                                // gsPojo = new CibilGSPoJo();
                                String guarRationCard = "";
                                String guarCIN = "";
                                String guarDIN = "";
                                String guarTIN = "";
                                String guarServiceTAx = "";
                                String guarOtherID = "";
                                String guarMob = "";
                                String guarTelAreaCode = "";
                                String guarFaxAreaCode = "";
                                String guarFaxNo = "";
                                String guarFiller = "";
                                if (repType.equalsIgnoreCase("1")) {
                                    gsTab = "GS" + "|" + "999999999" + "|" + Integer.parseInt(guarantorCustEntityType) + "|" + busiCust + "|" + busiType + "|" + ""
                                            + "|" + guarantor_prefix + "|" + guarantor_name + "|" + guarantor_gender + "|" + "" + "|" + guarantorDateOfIncor + "|" + guarantor_dob
                                            + "|" + guarantor_pan + "|" + guarantor_votrid + "|" + guarantor_passport + "|" + guarantor_dl + "|" + guarantor_uid
                                            + "|" + guarRationCard + "|" + guarCIN + "|" + guarDIN + "|" + guarTIN + "|" + guarServiceTAx + "|" + guarOtherID
                                            + "|" + guarantor_add1 + "|" + guarantor_add2 + "|" + guarantor_add3 + "|" + guarantor_city + "|" + guarantor_city + "|" + guarantor_state + "|" + guarantor_pincode
                                            + "|" + "079" + "|" + guarMob + "|" + guarTelAreaCode + "|" + telResident + "|" + guarFaxAreaCode + "|" + guarFaxNo + "|" + guarFiller + "|";
                                } else {
                                    gsTab = "GS" + "|" + "999999999" + "|" + Integer.parseInt(guarantorCustEntityType) + "|" + busiCust + "|" + busiType + "|" + ""
                                            + "|" + guarantor_prefix + "|" + guarantor_name + "|" + guarantor_gender + "|" + "" + "|" + guarantorDateOfIncor + "|" + guarantor_dob
                                            + "|" + guarantor_pan + "|" + guarantor_votrid + "|" + guarantor_passport + "|" + guarantor_dl + "|" + guarantor_uid
                                            + "|" + guarRationCard + "|" + guarCIN + "|" + guarDIN + "|" + guarTIN + "|" + guarServiceTAx + "|" + guarOtherID
                                            + "|" + guarantor_add1 + "|" + guarantor_add2 + "|" + guarantor_add3 + "|" + guarantor_city + "|" + guarantor_city + "|" + guarantor_state + "|" + guarantor_pincode
                                            + "|" + "079" + "|" + guarMob + "|" + guarTelAreaCode + "|" + telResident + "|" + guarFaxAreaCode + "|" + guarFaxNo + "|" + guarFiller + "|";
                                }



                                bw.write(gsTab + "\n");
                            }
                        } else {
                            //RSPOJO
                            if (repType.equalsIgnoreCase("1")) {
                                gsTab = "GS" + "|" + "999999999" + "|" + "2" + "|" + "07" + "|" + "11" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "2" + "|" + ""
                                        + "|" + "079" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|";
                            } else {
                                gsTab = "GS" + "|" + "999999999" + "|" + "2" + "|" + "07" + "|" + "11" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""
                                        + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "2" + "|" + ""
                                        + "|" + "079" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|";
                            }


                            bw.write(gsTab + "\n");
                        }
                    }
                }
                //gs Tab end




                //SSPOJO
                String secClassification = "", typeOfSec = "";
                List loanSecurity = em.createNativeQuery("select ifnull(cast(lienvalue as decimal(25)),0),ifnull(security,''),ifnull(SecurityType,''),ifnull(Issuedate,'') from loansecurity where acno ='" + acNo + "' "
                        + " and  status ='Active' and Entrydate<='" + toDate + "'  "
                        + " union  "
                        + " select ifnull(cast(lienvalue as decimal(25)),0),ifnull(security,''),ifnull(SecurityType,''),ifnull(Issuedate,'') from loansecurity where acno ='" + acNo + "' "
                        + " and status ='EXPIRED' and Entrydate<='" + toDate + "' and ExpiryDate >'" + toDate + "' ").getResultList();
                if (!loanSecurity.isEmpty()) {
                    for (int ls = 0; ls < loanSecurity.size(); ls++) {
                        Vector vtr = (Vector) loanSecurity.get(ls);
                        String lienvalue = vtr.get(0).toString();
                        if (vtr.get(2).toString().equalsIgnoreCase("P")) {
                            secClassification = "001";
                            typeOfSec = "01";
                        } else {
                            secClassification = "009";
                            typeOfSec = "21";
                        }
                        String dateOfValue = vtr.get(3).toString().equalsIgnoreCase("") ? "" : dmy.format(y_m_d.parse(vtr.get(3).toString()));
                        if (repType.equalsIgnoreCase("1")) {
                            ssTab = "SS" + "|" + String.valueOf(Integer.parseInt(lienvalue)) + "|" + "INR" + "|" + typeOfSec + "|" + secClassification + "|" + dateOfValue + "|" + "" + "|";

                        } else {
                            ssTab = "SS" + "|" + String.valueOf(Integer.parseInt(lienvalue)) + "|" + "INR" + "|" + secClassification + "|" + typeOfSec + "|" + dateOfValue + "|" + "" + "|";

                        }


                        bw.write(ssTab + "\n");
                    }
                }
                // End Of the SS Pojo.This can be multiple times for an Account.


                //CDPOJO
                List cdList = em.createNativeQuery("select acno,DT,cast(INST_AMT as unsigned),INST_NO,1 as noOFDisH,INST_DT from  cts_clg_in_entry_history where ACNO= '" + acNo + "' and status ='4' and userdetails in ('INSUFFICIENT FUNDS','EXCEEDS ARRANGEMENTS','1','2') and dt between '" + fromDate + "' and '" + toDate + "' ;").getResultList();
                //This Query is for Only Insufficient Funds. For any Other Reason change in the Query.
                String segId = "";
                if (!cdList.isEmpty()) {
                    for (int c = 0; c < cdList.size(); c++) {
                        Vector cdVect = (Vector) cdList.get(c);
                        if (cibilParameter.equalsIgnoreCase("CRIF")) {
                            segId = "DS";
                        } else {
                            segId = "CD";
                        }
                        if (repType.equalsIgnoreCase("1")) {
                            cdTab = "CD" + "|" + cdVect.get(1).toString() + "|" + cdVect.get(2).toString() + "|" + cdVect.get(3).toString() + "|" + cdVect.get(4).toString()
                                    + "|" + cdVect.get(5).toString() + "|" + "01" + "|" + "" + "|";
                        } else {
                            cdTab = "CD" + "|" + cdVect.get(1).toString() + "|" + cdVect.get(2).toString() + "|" + cdVect.get(3).toString() + "|" + cdVect.get(4).toString()
                                    + "|" + cdVect.get(5).toString() + "|" + "01" + "|" + "" + "|";
                        }

                        bw.write(cdTab + "\n");


                    }
                }
                //End Of CD Pojo.This can be multiple Times for an Account.

            }/*Main List End*/

            //TSPOJO
            tsTab = "TS" + "|" + String.valueOf(l1.size()) + "|" + String.valueOf(cr.size()) + "|" + "" + "|";
            bw.write(tsTab + "\n");
            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                file.delete();
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public List<ExperionPojo> cibilReport(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String cibilParameter) throws ApplicationException {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            List<ExperionPojo> experionList = new ArrayList<ExperionPojo>();
            Vector vtr1 = null;
            String lastPaymentDt = null, branchName = null, accountTypeTable = null, assetClass = "";
            String sanctionAmt = null, currentBal = null, amtOverdue = null, writtrnOff = null;
            String overDueDays = "";
            int overDueAmt = 0;
            ExperionPojo pojo;

            List custIdList;
            String bankCode = fts.getBankCode();
            boolean flag = false;
            List custNameList = new ArrayList();
            List<OverdueEmiReportPojo> OverdueTlList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", 1, 5000, toDate, "0A", "A", false, "", "N");
            List<OverdueNonEmiResultList> OverdueDlList = overDueRemote.getOverDueNonEmi("DL", "ALL", toDate, "0A", "N");
            List<OverdueNonEmiResultList> OverdueCaList = overDueRemote.getOverDueNonEmi("CA", "ALL", toDate, "0A", "N");
            List<LoanMisCellaniousPojo> misList = loanRemote.cbsLoanMisReport("0A", "0", "0", toDate, "A", 0, 9999999999.00, "S", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ACTIVE", "0", "0", "Y", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            String title = "", custName = "", dob = "", gender = "", panGirNumber = "", passportNo = "", voterIdNo = "", telephoneNumber = "",
                    mailAddressLine1 = "", mailAddressLine2 = "", mailVillage = "", mailBlock = "", mailCityCode = "", mailStateCode = "", mailPostalCode = "",
                    customerid = "", shortName = "", acNo = "", operMode = "", openingDt = "", closingDate = "", accStatus = "",
                    custId1 = "", custId2 = "", custId3 = "", custId4 = "", address = "", space = " ",
                    perAddressLine1 = "", perAddressLine2 = "", perVillage = "", perBlock = "", perCityCode = "", perStateCode = "", perPostalCode = "",
                    issueDate = "", expirydate = "", drivingLicenseNo = "", uidNo = "",
                    telResident = "", telOffice = "", emailID = "", odLimit = "", address2 = "",
                    acNature = "", acctcode = "", custNameAccMast = "";
            List acNatureList = new ArrayList();
            /*1 For Consumer and parameter will be CIBIL_ACNATURE(DL & TL)
             * 2 For Comercial and parameter will be CIBIL_COMERCIAL(CA)
             */
            List codeQuery = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name ='CIBIL_IND_CATEGORY'").getResultList();
            String code = "";
            if (!codeQuery.isEmpty()) {
                Vector codeVect = (Vector) codeQuery.get(0);
                code = codeVect.get(0).toString();
            } else {
                throw new ApplicationException("Please Fill define CIBIL_IND_CATEGORY in cbs_parameterinfo Table!!!");
            }
            String acctCat = "";
            if (reportType.equalsIgnoreCase("1")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACNATURE'").getResultList();
                acctCat = " and a.acctcategory in (" + code + ")";
            } else if (reportType.equalsIgnoreCase("2")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_COMERCIAL'").getResultList();
                acctCat = " and a.acctcategory  not in (" + code + ")";
            }
            String oldMemberCode = "", newMemberCode = "", oldMemberName = "", newMemberName = "";
            List oldAcNoList = em.createNativeQuery("select ifnull(cibil_member_code_old,''),ifnull(cibil_member_code_new,''),ifnull(cibil_member_name_old,''),ifnull(cibil_member_name_new,'') from branchmaster").getResultList();
            if (!oldAcNoList.isEmpty()) {
                Vector vtrBranch = (Vector) oldAcNoList.get(0);
                oldMemberCode = vtrBranch.get(0).toString();
                memberCode = vtrBranch.get(1).toString().equalsIgnoreCase("") ? memberCode : vtrBranch.get(1).toString();
                oldMemberName = vtrBranch.get(2).toString();
                newMemberName = vtrBranch.get(3).toString();
            }
            String cibil_old_acno = "N";
            List parameterOld = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name ='CIBIL_OLD_ACNO'").getResultList();
            if (!parameterOld.isEmpty()) {
                Vector vect = (Vector) parameterOld.get(0);
                cibil_old_acno = vect.get(0).toString();
            }
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Acnature data does not exit in required table");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }
            String cibilVerNo = common.getCibilVerNo();
            List acctCodeList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACCTCODE_NOT'").getResultList();
            if (acctCodeList.isEmpty()) {
                acctcode = " and acctcode not in ('') ";
            } else {
                Vector acctCodeVect = (Vector) acctCodeList.get(0);
                if (acctCodeVect.get(0).toString().equalsIgnoreCase("")) {
                    acctcode = " and acctcode not in ('') ";
                } else {
                    acctcode = " and acctcode not in (" + acctCodeVect.get(0).toString() + ") ";
                }
            }
            String cbsLiveDt = "19000101";
            List liveDtCBS = em.createNativeQuery("select ifnull(code,'19000101') from cbs_parameterinfo where name ='CBS_LIVE_DATE'").getResultList();
            if (!liveDtCBS.isEmpty()) {
                Vector liveDt = (Vector) liveDtCBS.get(0);
                cbsLiveDt = liveDt.get(0).toString();
            }

            new SimpleDateFormat("yyyyMMdd").format(new Date());
            List l1 = em.createNativeQuery("select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                    + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                    + " c.PAN_GIRNumber,c.PassportNo, c.VoterIDNo, ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                    + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                    + " c.MailAddressLine1, c.MailAddressLine2, c.MailVillage, c.MailBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode) as MailCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                    + " c.MailPostalCode, c.customerid, c.CustFullName, a.ACNo, a.OperMode, a.OpeningDt, a.ClosingDate, a.AccStatus, "
                    + " a.custid1, a.custid2, a.custid3, a.custid4,"
                    + " c.IssueDate, c.Expirydate, c.DrivingLicenseNo,  aa.IdentityNo as aadhar_no , "
                    + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                    + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                    + " ifnull(c.EmailID,''),"
                    + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.PerCityCode) as PerCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                    + " c.PerPostalCode, case cast(ifnull(d.ODLimit,0) as decimal) when 0 then d.Sanctionlimit else cast(ifnull(d.ODLimit,0) as decimal) end as sancAmt, c.CustFullName ,(select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature ,"
                    + " ifnull(f.cibil_acct_report_code,'00') AS accounttypetable , "
                    + " ifnull((SELECT max(spflag) FROM accountstatus a, codebook c WHERE  a.spflag=a.accstatus AND GROUPCODE = '3' and a.spflag = c.code  AND effdt <='" + toDate + "' and ACNO=a.ACNo),a.accstatus) as accountstatus ,"
                    + "  (select ifnull(max(dt),(select ifnull(max(dt),'1900-01-01 00:00:00') from loan_recon where acno = a.acno and dt <= '" + toDate + "' and cramt != 0)) from ca_recon where acno = a.acno and dt <= '" + toDate + "' and cramt != 0) as lastPymtDt,"
                    + " d.old_ac_no,d.old_ac_type,d.old_branch_code,d.old_ownership_indicator, "
                    + " f.ACCT_REPORT_CODE as expCode, f.cibil_acct_report_code as cibilCode, "
                    + " f.cic_acct_report_code as cicCode, f.crif_acct_report_code as crifCode ,clb.MODE_OF_SETTLEMENT "
                    + " from accountmaster a, customerid b, loan_appparameter d , "
                    + " cbs_loan_acc_mast_sec e, "
                    + " cbs_scheme_currency_details f, cbs_loan_borrower_details clb,"
                    + " cbs_customer_master_detail c "
                    + " left outer join "
                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " where a.acno = b.acno and b.custid = cast(c.customerid as unsigned)  and e.acno = a.acno and e.scheme_code = f.scheme_code "
                    + " and  a.acno = d.acno and a.acno = clb.ACC_NO" + acctCat + " and a.accttype "
                    + " in ( select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag = 'D') "
                    + " and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + " ((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno").getResultList();

            for (int i = 0; i < l1.size(); i++) {
                pojo = new ExperionPojo();
                custIdList = new ArrayList();
                Vector vtrmain = (Vector) l1.get(i);
                accountTypeTable = "";
                currentBal = "0";
                title = vtrmain.get(0) != null ? vtrmain.get(0).toString() : "N01";
                custName = vtrmain.get(1) != null ? vtrmain.get(1).toString() : "";
                dob = vtrmain.get(2) != null ? vtrmain.get(2).toString() : "1900-01-01";
                gender = vtrmain.get(3) != null ? vtrmain.get(3).toString() : "";
                panGirNumber = vtrmain.get(4) != null ? vtrmain.get(4).toString() : "";
                passportNo = vtrmain.get(5) != null ? vtrmain.get(5).toString() : "";
                voterIdNo = vtrmain.get(6) != null ? vtrmain.get(6).toString() : "";
                telephoneNumber = vtrmain.get(7) != null ? vtrmain.get(7).toString() : "";
                mailAddressLine1 = vtrmain.get(8) != null ? vtrmain.get(8).toString() : "";
                mailAddressLine2 = vtrmain.get(9) != null ? vtrmain.get(9).toString() : "";
                mailVillage = vtrmain.get(10) != null ? vtrmain.get(10).toString() : "";
                mailBlock = vtrmain.get(11) != null ? vtrmain.get(11).toString() : "";
                mailCityCode = vtrmain.get(12) != null ? vtrmain.get(12).toString() : "";
                mailStateCode = vtrmain.get(13) != null ? vtrmain.get(13).toString() : "";
                mailPostalCode = vtrmain.get(14) != null ? vtrmain.get(14).toString() : "";
                customerid = vtrmain.get(15) != null ? vtrmain.get(15).toString() : "";
                shortName = vtrmain.get(16) != null ? vtrmain.get(16).toString() : "";
                acNo = vtrmain.get(17) != null ? vtrmain.get(17).toString() : "";
                if (acNo.equalsIgnoreCase("023305014301")) {
                    System.out.println(acNo);
                }

                operMode = vtrmain.get(18) != null ? vtrmain.get(18).toString() : "";
                openingDt = vtrmain.get(19) != null ? vtrmain.get(19).toString() : "19000101";
                closingDate = vtrmain.get(20) != null ? vtrmain.get(20).toString().equalsIgnoreCase("") ? "19000101" : vtrmain.get(20).toString() : "19000101";
                accStatus = vtrmain.get(21) != null ? vtrmain.get(21).toString() : "";
                custId1 = vtrmain.get(22) != null ? vtrmain.get(22).toString() : "";
                custId2 = vtrmain.get(23) != null ? vtrmain.get(23).toString() : "";
                custId3 = vtrmain.get(24) != null ? vtrmain.get(24).toString() : "";
                custId4 = vtrmain.get(25) != null ? vtrmain.get(25).toString() : "";

                issueDate = vtrmain.get(26) != null ? vtrmain.get(26).toString() : "1900-01-01";
                expirydate = vtrmain.get(27) != null ? vtrmain.get(27).toString() : "1900-01-01";
                drivingLicenseNo = vtrmain.get(28) != null ? vtrmain.get(28).toString() : "";
                uidNo = vtrmain.get(29) != null ? vtrmain.get(29).toString() : "";
                telResident = vtrmain.get(30) != null ? vtrmain.get(30).toString() : "";
                telOffice = vtrmain.get(31) != null ? vtrmain.get(31).toString() : "";
                emailID = vtrmain.get(32) != null ? vtrmain.get(32).toString() : "";

                perAddressLine1 = vtrmain.get(33) != null ? vtrmain.get(33).toString() : "";
                perAddressLine2 = vtrmain.get(34) != null ? vtrmain.get(34).toString() : "";
                perVillage = vtrmain.get(35) != null ? vtrmain.get(35).toString() : "";
                perBlock = vtrmain.get(36) != null ? vtrmain.get(36).toString() : "";
                perCityCode = vtrmain.get(37) != null ? vtrmain.get(37).toString() : "";
                perStateCode = vtrmain.get(38) != null ? vtrmain.get(38).toString() : "";
                perPostalCode = vtrmain.get(39) != null ? vtrmain.get(39).toString() : "";
                odLimit = vtrmain.get(40) != null ? vtrmain.get(40).toString() : "0";
                custNameAccMast = vtrmain.get(41) != null ? vtrmain.get(41).toString() : "0";
                String acNture = vtrmain.get(42) != null ? vtrmain.get(42).toString() : "";
                //   String OldAcnoMap = vtrmain.get(43) != null ? vtrmain.get(43).toString() : "";
                accountTypeTable = vtrmain.get(43) != null ? vtrmain.get(43).toString() : "";
                String acStatusFinal = vtrmain.get(44) != null ? vtrmain.get(44).toString() : "";
                lastPaymentDt = vtrmain.get(45) != null ? dmy.format(y_m_d.parse(vtrmain.get(45).toString())) : "";
                lastPaymentDt = lastPaymentDt.equalsIgnoreCase("01011900") ? "" : lastPaymentDt;

                String OldAcnoMap = vtrmain.get(46) != null ? vtrmain.get(46).toString() : "";
                String oldAcType = vtrmain.get(47) != null ? vtrmain.get(47).toString() : "";
                String oldBranchCode = vtrmain.get(48) != null ? vtrmain.get(48).toString() : "";
                String oldOwnershipIndicator = vtrmain.get(49) != null ? vtrmain.get(49).toString() : "";

                String expCode = vtrmain.get(50) != null ? vtrmain.get(50).toString() : "";
                String cibilCode = vtrmain.get(51) != null ? vtrmain.get(51).toString() : "";
                String equifaxCode = vtrmain.get(52) != null ? vtrmain.get(52).toString() : "";
                String crifCode = vtrmain.get(53) != null ? vtrmain.get(53).toString() : "";
                String writeoff = vtrmain.get(54) != null ? vtrmain.get(54).toString() : "";
                if (cibilParameter.equalsIgnoreCase("Experian")) {
                    accountTypeTable = expCode;
                } else if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                    accountTypeTable = cibilCode;
                } else if (cibilParameter.equalsIgnoreCase("Equifax")) {
                    accountTypeTable = equifaxCode;
                } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
                    accountTypeTable = crifCode;
                } else {
                    accountTypeTable = "00";
                }

                if (accountTypeTable.equalsIgnoreCase(oldAcType)) {
                    oldAcType = ""; // As per Hcbl mail
                }
                pojo.setMemberCode(memberCode);
                pojo.setMemberName(newMemberName);

                pojo.setCycleIdent("00");
                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                pojo.setReportPassword("");
                pojo.setFutureUse("");
                pojo.setMemberData("");

                String[] st = custName.split(" ");
                String lastName = "", firstName = "", middleName = "";
                if (st.length > 0) {
                    firstName = st[0];
                    middleName = st.length > 2 ? getMiddleName(st) : "";
                    if (st.length > 1) {
                        lastName = st[st.length - 1];
                    }
                }

                pojo.setCustNameF1(lastName);
                pojo.setCustNameF2(firstName);
                firstName = "";
                lastName = "";
                if (!middleName.equalsIgnoreCase("")) {
                    st = middleName.split(" ");
                    if (st.length > 1) {
                        firstName = st[0];
                        if (st.length > 1) {
                            middleName = st[st.length - 1];
                        }
                        lastName = st.length > 2 ? getMiddleName(st) : "";
                        pojo.setCustNameF3(firstName);
                        pojo.setCustNameF4(middleName);
                        pojo.setCustNameF5(lastName);
                    } else {
                        pojo.setCustNameF3(middleName);
                        pojo.setCustNameF4("");
                        pojo.setCustNameF5("");
                    }
                } else {
                    pojo.setCustNameF3(middleName);
                    pojo.setCustNameF4("");
                    pojo.setCustNameF5("");
                }
                pojo.setDob(dmy.format(y_m_d.parse(dob)).equalsIgnoreCase("01011900") ? "" : dmy.format(y_m_d.parse(dob)));
                pojo.setGender(gender);            //1=Female : 2=male
                // changed due to Software Bug #36025
                if (!panGirNumber.equalsIgnoreCase("") && panGirNumber.length() == 10) {
                    if (ParseFileUtil.isAlphabet(panGirNumber.substring(0, 5)) && ParseFileUtil.isNumeric(panGirNumber.substring(5, 9)) && ParseFileUtil.isAlphabet(panGirNumber.substring(9))) {
                        pojo.setIncomeTaxId(panGirNumber);
                    } else {
                        pojo.setIncomeTaxId("");
                    }
                } else {
                    pojo.setIncomeTaxId("");
                }
                pojo.setPassportNo(passportNo);
                pojo.setVoterIdNo(voterIdNo);
                pojo.setTelephone(telephoneNumber.length() > 20 ? telephoneNumber.substring(0, 20) : telephoneNumber);
                address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                        !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                        !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                        !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                        !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                if (cibilVerNo.equalsIgnoreCase("3.0")) {
                    pojo.setCustNameF1((custName.length() >= custNameAccMast.length()) ? custName : custNameAccMast);
                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                    pojo.setAddressLine2("");
                    pojo.setAddressLine3("");
                    pojo.setAddressLine4("");
                    pojo.setAddressLine5("");
                } else {
                    if (address.contains(" ")) {
                        space = " ";
                    } else if (address.contains(",")) {
                        space = ",";
                    } else {
                        space = "-";
                    }
                    //                   System.out.println("acno<<<<"+acNo+": address:"+address);
                    if (address.length() > 40) {
                        if (!address.substring(40).contains(space)) {
                            StringBuilder sb = new StringBuilder();
                            String[] pairs = address.split(",");
                            sb.append(pairs[0]);
                            for (int t = 1; t < pairs.length; ++t) {
                                String pair = pairs[t];
                                sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                sb.append(pair);
                            }
                            address = sb.toString();
                        }
                    }
                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                    pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                    pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");

                }
//                System.out.println("acno>>>>"+acNo+": address:"+address);

                pojo.setStateCode(mailStateCode);
                pojo.setPinCode(mailPostalCode);
//                pojo.setReportingMemCode(memberCode);
//                pojo.setMemberShortName(branchName);
                pojo.setCurrentAcno(acNo);
                int a1 = Integer.parseInt(acNo.substring(0, 2));
                int a2 = Integer.parseInt(acNo.substring(2, 4));
                int a3 = Integer.parseInt(acNo.substring(4, 10));
                String oldAcNo = String.valueOf(a1).concat("/").concat(String.valueOf(a2)).concat("/").concat(String.valueOf(a3));
                if (cibil_old_acno.equalsIgnoreCase("Y")) { //For Khattri
                    if (bankCode.equalsIgnoreCase("KHAT")) {
                        pojo.setOldAccountNo(oldAcNo);
                    } else {
                        pojo.setOldAccountNo(OldAcnoMap);
                    }
                    pojo.setOldReportingMemCode(oldMemberCode);
                    pojo.setOldMemShortName(oldMemberName);
                } else if (cibil_old_acno.equalsIgnoreCase("N")) {
                    pojo.setOldAccountNo("");
                    pojo.setOldReportingMemCode("");
                    pojo.setOldMemShortName("");
                } else {
                    pojo.setOldAccountNo("");
                    pojo.setOldReportingMemCode("");
                    pojo.setOldMemShortName("");
                }
                pojo.setReportingMemCode(memberCode);
                pojo.setMemberShortName(newMemberName);

                /*Account Type*/
//                List l9 = em.createNativeQuery("select ifnull(b.cibil_acct_report_code,'00') AS accounttypetable from cbs_loan_acc_mast_sec a, cbs_scheme_currency_details b where a.scheme_code = b.scheme_code and a.acno = '" + acNo + "'").getResultList();
//                if (!l9.isEmpty()) {
//                    vtr1 = (Vector) l9.get(0);
//                    accountTypeTable = vtr1.get(0) != null ? vtr1.get(0).toString() : "00";
//                }
                pojo.setAccountType(accountTypeTable);

                /* *************************************** *
                 0	MODE OF OPERATION
                 1	SELF                        1
                 2	EITHER OR SURVIVOR          4
                 3	FORMER OR SURVIVOR          4
                 4	ANY ONE OR SURVIVOR         4
                 5	ANY TWO JOINTLY             4
                 6	ANY THREE JOINTLY           4
                 7	UNDER POWER OF ATTOR        1  
                 8	PROPRIETOR                  4
                 9	AUTHORIZED SIGNATORY        1
                 10	M.D.                        1
                 11	UNDER GUARDIANSHIP          4
                 12	BOTH OF TWO JOINTLY         4
                 13	MINOR                       4      
                 14	ANY FOUR JOINTLY            4
                 15	ANY FIVE JOINTLY            4
                 16	ALL JOINTLY                 4
                 17	JOINTLY                     4
                 * *************************************** *
                 1 = Individual
                 2 = Authorized User (Refer to Supplementary credit card holder)
                 3 = Guarantor
                 4 = Joint
                 If values reported in this field is 2 i.e. Authorized User, then
                 the Account type field must contain the value 10 i.e. Credit
                 Card.
                 */
                if (operMode.equalsIgnoreCase("1") || operMode.equalsIgnoreCase("7") || operMode.equalsIgnoreCase("9") || operMode.equalsIgnoreCase("10")) {
                    pojo.setAccountHolderTypeCode("1");
                } else if (operMode.equalsIgnoreCase("2") || operMode.equalsIgnoreCase("3") || operMode.equalsIgnoreCase("4") || operMode.equalsIgnoreCase("5") || operMode.equalsIgnoreCase("6") || operMode.equalsIgnoreCase("8") || operMode.equalsIgnoreCase("11") || operMode.equalsIgnoreCase("12") || operMode.equalsIgnoreCase("13") || operMode.equalsIgnoreCase("14") || operMode.equalsIgnoreCase("15") || operMode.equalsIgnoreCase("16") || operMode.equalsIgnoreCase("17")) {
                    pojo.setAccountHolderTypeCode("4");
                } else {
                    pojo.setAccountHolderTypeCode("1");
                }

                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));

//                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + table_name + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
//                if (!l4.isEmpty()) {
//                    vtr1 = (Vector) l4.get(0);
//                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
//                    pojo.setLastPaymentdate(lastPaymentDt.equalsIgnoreCase("01011900") ? "" : lastPaymentDt);
//                    lastPaymentDt = lastPaymentDt.equalsIgnoreCase("01011900") ? "" : lastPaymentDt;
//                }                
                pojo.setLastPaymentdate(lastPaymentDt.equalsIgnoreCase("01011900") ? "" : lastPaymentDt);
                if (ymd.parse(closingDate).after(ymd.parse(toDate))) {
                    pojo.setDateClose("");
                } else {
                    pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                }
                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
//                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + table_name + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
//                if (!l2.isEmpty()) {
//                    vtr1 = (Vector) l2.get(0);
//                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
//                    pojo.setCurrentBalance(currentBal);
//                }
                double netWordth = 0d;
                String monthlyIncome = "0";
                String roi = "";
                for (int t = 0; t < misList.size(); t++) {
                    if (acNo.equalsIgnoreCase(misList.get(t).getAcNo())) {
                        currentBal = misList.get(t).getOutstanding() == null ? "0" : misList.get(t).getOutstanding().abs().toString();
                        odLimit = misList.get(t).getSanctionAmt() == null ? odLimit : misList.get(t).getSanctionAmt().toString();
                        netWordth = misList.get(t).getNetWorth() == null ? 0 : misList.get(t).getNetWorth().doubleValue();
                        monthlyIncome = misList.get(t).getMonthlyIncome() == null ? "0" : misList.get(t).getMonthlyIncome().toString();
                        roi = misList.get(t).getRoi() == null ? "" : misList.get(t).getRoi().toString();
                    }
                }

                pojo.setCurrentBalance(formatter2.format(Double.parseDouble(currentBal)));
                if (Double.parseDouble(odLimit) == 0) {
                    pojo.setSanctAmt("");
                } else {
                    pojo.setSanctAmt(formatter2.format(Double.parseDouble(odLimit)));
                }

                overDueDays = "0";
                amtOverdue = "0";
                overDueAmt = 0;
                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' and txnid = (select max(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "')").getResultList();
//                    if (!sanctionLimitDtList.isEmpty()) {
//                        Vector vist = (Vector) sanctionLimitDtList.get(0);
//                        odLimit = vist.get(1).toString();
//                        pojo.setSanctAmt(odLimit);
//                    } else {
//                        pojo.setSanctAmt(odLimit);
//                    }
                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                    OverDueList = OverdueCaList;
                    if (!OverDueList.isEmpty()) {
                        for (int k = 0; k < OverDueList.size(); k++) {
                            OverdueNonEmiResultList vect = OverDueList.get(k);
                            if (vect.getAccountNo().equalsIgnoreCase(acNo)) {
                                overDueAmt = (int) vect.getOverDue();
                                overDueDays = String.valueOf(vect.getCurrentStatusNoOfDays());
                            }
                        }
                    }
//                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                        amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                    } else {
//                        amtOverdue = "0";
//                    }
                    if (overDueAmt == 0) {
                        pojo.setAmountOverDue("");
                    } else {
                        pojo.setAmountOverDue(String.valueOf(overDueAmt));
                    }

                } else {
//                    pojo.setSanctAmt(odLimit);
//                    List excessList = null;
//                    int excess = 0;
//                    if (!bankCode.equalsIgnoreCase("army")) {
//                        if (isExcessEmi == 0) {
//                            excessList = em.createNativeQuery("Select cast(ifnull(e.excessamt,0) as decimal) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                        } else {
//                            excessList = em.createNativeQuery("select cast(ifnull(sum(excessamt),0) as decimal) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                    + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                        }
//                        if (!excessList.isEmpty()) {
//                            if (!excessList.isEmpty()) {
//                                Vector ele = (Vector) excessList.get(0);
//                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                    excess = Integer.parseInt(ele.get(0).toString());
//                                }
//                            }
//                        }
//                        List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                        if (!l6.isEmpty()) {
//                            vtr1 = (Vector) l6.get(0);
//                            amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                            if (!amtOverdue.equalsIgnoreCase("0")) {
//                                amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                            }
//                            if (Integer.parseInt(amtOverdue) == 0) {
//                                excess = 0;
//                            }
//                            int overDueAmt = Integer.parseInt(amtOverdue) - excess;
//                            amtOverdue = String.valueOf(overDueAmt);
//                            if (Integer.parseInt(currentBal) == 0) {
//                                amtOverdue = "0";
//                            }
//                            pojo.setAmountOverDue(amtOverdue);
//                        }
//                    } else {
//                        //For Over Due Amount new code
//                        double overDueAmt = 0d;
//                        amtOverdue = "0";
//                        List<OverDueListPojo> list = ddsRemote.getOverDueListData(acNo.substring(0, 2), acNo.substring(2, 4), toDate, acNo);
//                        if (!list.isEmpty()) {
//                            overDueAmt = list.get(0).getOveDue().doubleValue();
//                            amtOverdue = String.valueOf(overDueAmt);
//                            pojo.setAmountOverDue(amtOverdue);
//                        } else {
//                            pojo.setAmountOverDue(amtOverdue);
//                        }
//                        //END For Over Due Amount new code 
//                    }
//                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                    if (!l7.isEmpty()) {
//                        vtr1 = (Vector) l7.get(0);
//                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                    }
                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        OverDueList = OverdueDlList;
                        if (!OverDueList.isEmpty()) {
                            for (int k = 0; k < OverDueList.size(); k++) {
                                OverdueNonEmiResultList vect = OverDueList.get(k);
                                if (vect.getAccountNo().equalsIgnoreCase(acNo)) {
                                    overDueAmt = (int) vect.getOverDue();
                                    overDueDays = String.valueOf(vect.getCurrentStatusNoOfDays());
                                }
                            }
                        }
                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        OverDueTLList = OverdueTlList;
                        if (!OverDueTLList.isEmpty()) {
                            for (int k = 0; k < OverDueTLList.size(); k++) {
                                OverdueEmiReportPojo vect = OverDueTLList.get(k);
                                if (vect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                    overDueAmt = vect.getAmountOverdue().intValue();
                                    overDueDays = String.valueOf(vect.getCurrentStatusNoOfDays());
                                }
                            }
                        }
                    }
                    if (overDueAmt == 0) {
                        pojo.setAmountOverDue("");
                    } else {
                        pojo.setAmountOverDue(String.valueOf(overDueAmt));
                    }
                }
                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                    pojo.setNoOfDaysPast("000");
                } else {
                    pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 900) ? "900" : overDueDays) : "");
                }

                /*
                 0	ACCOUNT STATUS
                 1	OPERATIVE
                 2	INOPERATIVE
                 3	SUIT FIELDS                 1
                 4	FROZEN                      
                 5	RECALLED
                 6	DECREED
                 7	WITHDRAWAL STOPPED
                 8	OPERATION STOPPED
                 9	CLOSED
                 10	LIEN MARKED
                 11	SUB STANDARD
                 12	DOUBTFUL
                 13	LOSS
                 14	PROTESTED
                 ======================
                 Valid Value are:
                 1 = Suit Filed
                 2 = Wilful default
                 3 = Suit filed(Wilful default)
                 4 = Written off
                 5 = Suit Filed & Written off
                 6 = Wilful default & Written off
                 7 = Suit Filed (Wilful default)& Written off
                 */
//                if (accStatus.equalsIgnoreCase("3")) {
//                    writtrnOff = "1";
//                } else if (accStatus.equalsIgnoreCase("14")) {
//                    writtrnOff = "7";
//                } else {
//                    writtrnOff = "";
//                }
                if (writeoff.equalsIgnoreCase("WROFF")) {
                    pojo.setWrittenOffStatus("Write Off");
                } else {
                    pojo.setWrittenOffStatus("");
                }



                /*
                 01 =Standard
                 02 =Sub-Standard
                 03 =Doubtful
                 04 =Loss
                 05 =Special Mention Account
                 */
//                List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
//                        + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
//                        + " and effdt <='" + toDate + "'").getResultList();
//                if (!assetClsDt.isEmpty()) {
//                    Vector vect = (Vector) assetClsDt.get(0);
//                    accStatus = vect.get(0).toString();
//                } else {
//                    accStatus = accStatus;
//                }
                accStatus = acStatusFinal;
                if (accStatus.equalsIgnoreCase("1")) {
                    assetClass = "01";
                } else if (accStatus.equalsIgnoreCase("11")) {
                    assetClass = "02";
                } else if (accStatus.equalsIgnoreCase("12")) {
                    assetClass = "03";
                } else if (accStatus.equalsIgnoreCase("13")) {
                    assetClass = "04";
                } else if (accStatus.equalsIgnoreCase("9")) {
                    /*This code was removed as per the Khattri Requirement (Discussion with Alok Sir).*/
//                    if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                        assetClass = "01";
//                    } else {
//                        assetClass = "05";
//                    }
                    assetClass = "01";
                } else {
                    assetClass = "05";
                }
                pojo.setAssetClassif(assetClass);

                if (!custId1.equalsIgnoreCase("")) {
                    custIdList.add(custId1);
                }
                if (!custId2.equalsIgnoreCase("")) {
                    custIdList.add(custId2);
                }
                if (!custId3.equalsIgnoreCase("")) {
                    custIdList.add(custId3);
                }
                if (!custId4.equalsIgnoreCase("")) {
                    custIdList.add(custId4);
                }

                experionList.add(pojo);
                flag = true;
                /**
                 * CIBIL Start
                 */
                if (!passportNo.equalsIgnoreCase("")) {
                    pojo.setPassportIssueDt(issueDate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(issueDate)));
                    pojo.setPassportExpDt(expirydate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(expirydate)));
                }

                pojo.setDriLicenseNo(drivingLicenseNo);
                pojo.setDriLicenseIssueDt("");
                pojo.setDriLicenseExpDt("");
                pojo.setRationCardNo("");
                pojo.setUniversalIdNo(uidNo);

                pojo.setAddId1("");
                pojo.setAddId2("");

                pojo.setTelNoResidence(telResident);
                pojo.setTelNoOffice(telOffice);
                pojo.setExtOffice("");
                pojo.setTelNoOther("");
                pojo.setExtOther("");
                pojo.setEmailId1(emailID);
                pojo.setEmailId2("");

                /* Address Category
                 * 01 = Permanent Address 
                 * 02 = Residence Address 
                 * 03 = Office Address 
                 * 04 = Not Categorized */
                pojo.setAddressCategory1(mailAddressLine1.equalsIgnoreCase("") ? "04" : "02");
                /* Residence Code
                 * 01 = Owned 
                 * 02 = Rented */
                pojo.setResidenceCode1(mailAddressLine1.equalsIgnoreCase("") ? perAddressLine1.equalsIgnoreCase("") ? "" : "01" : "02");

                address2 = (!perAddressLine1.trim().equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.trim()).concat(
                        !perAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2.trim()).concat(
                        !perVillage.trim().equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage.trim()).concat(
                        !perBlock.trim().equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock.trim()).concat(
                        !perCityCode.trim().equalsIgnoreCase("") ? " ".concat(perCityCode.trim()) : perCityCode.trim());
                if (address.contains(" ")) {
                    space = " ";
                } else if (address.contains(",")) {
                    space = ",";
                } else {
                    space = "-";
                }
                if (address2.length() > 40) {
                    if (!address2.substring(40).contains(space)) {
                        StringBuilder sb = new StringBuilder();
                        String[] pairs = address.split(",");
                        sb.append(pairs[0]);
                        for (int t = 1; t < pairs.length; ++t) {
                            String pair = pairs[t];
                            sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                            sb.append(pair);
                        }
                        address2 = sb.toString();
                    }
                }
                pojo.setAddress2("");
                pojo.setBorcity2("");
                pojo.setBorDistrict2("");
                pojo.setStateCode2("");
                pojo.setPinCode2("");
                pojo.setAddressCategory2("");
                pojo.setResidenceCode2("");
                pojo.setOldAccType(oldAcType);
                pojo.setOldOwnershipIndicator(oldOwnershipIndicator);
                pojo.setWrittenSettledStatus("");

//                List lColl = em.createNativeQuery("select ifnull(net_worth,0), ifnull(monthly_income,0) from cbs_loan_borrower_details where acc_no = '" + acNo + "'").getResultList();
//                if (!lColl.isEmpty()) {
//                    Vector vtrColl = (Vector) lColl.get(0);
//                    netWordth = Double.parseDouble(vtrColl.get(0).toString());
//                    monthlyIncome = vtrColl.get(1).toString();
//                }
                pojo.setValueOfCollateral("00");
                pojo.setTypeOfCollateral("00");
                pojo.setCreditLimit(accountTypeTable.equalsIgnoreCase("10") ? odLimit : "");
                pojo.setCashLimit("");
//                String roi = loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(odLimit), toDate, acNo);
                pojo.setRateOfInterest(roi);

                String periodicity = "", repaymentTenure = "0", emiAmt = "0";
                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                if (!lemi.isEmpty()) {
                    Vector vtrEmi = (Vector) lemi.get(0);
                    repaymentTenure = vtrEmi.get(0).toString();
                    emiAmt = vtrEmi.get(1).toString();
                    periodicity = vtrEmi.get(2).toString();
                    pojo.setRepaymentTenure(repaymentTenure.equalsIgnoreCase("0") ? "" : repaymentTenure);
                    pojo.setEmiAmt(emiAmt.equalsIgnoreCase("0") ? "" : emiAmt);
                } else {
                    pojo.setRepaymentTenure("");
                    pojo.setEmiAmt("");
                }

                pojo.setWrittenOffAmountTotal("");
                pojo.setWrittenOffPrincipalAmount("");
                pojo.setSettlementAmt("");

                /* Payment Frequency
                 * 01 = Weekly 
                 * 02 = Fortnightly 
                 * 03 = Monthly 
                 * 04 = Quarterly
                
                 * in our System
                
                 * W = weekly
                 * M = Monthly
                 * Q = Quarterly
                 * HY/H= half-Yearly
                 * Y = Yearly
                
                 */
                if (periodicity.equalsIgnoreCase("W")) {
                    pojo.setPaymentFrequency("01");
                } else if (periodicity.equalsIgnoreCase("M")) {
                    pojo.setPaymentFrequency("03");
                } else if (periodicity.equalsIgnoreCase("Q")) {
                    pojo.setPaymentFrequency("04");
                } else {
                    pojo.setPaymentFrequency("04");
                }

                pojo.setActualPaymentAmt("");
                pojo.setOccupationCode("");
                pojo.setIncome(monthlyIncome.replace(".0", ""));
                if (Double.parseDouble(pojo.getIncome()) == 0) {
                    pojo.setIncome("");
                }
                /*
                 * G = Gross Income 
                 * N = Net Income */
                pojo.setNetGrossIncomeIndicator(netWordth > 0 ? "N" : "G");
                /*
                 * M = Monthly 
                 * A = Annual */
                pojo.setMonthlyAnnualIncomeIndicator(monthlyIncome.equalsIgnoreCase("0") ? "A" : "M");

                /**
                 * CIBIL END*
                 */
                if (flag == true) {
                    if (!custIdList.isEmpty()) {
                        for (int j = 0; j < custIdList.size(); j++) {
                            List l3 = em.createNativeQuery("select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                                    + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                                    + " c.PAN_GIRNumber,c.PassportNo, c.VoterIDNo, ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                                    + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                                    + " c.MailAddressLine1, c.MailAddressLine2, c.MailVillage, c.MailBlock , "
                                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode) as MailCityCode, "
                                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                                    + " c.MailPostalCode, c.customerid, c.CustFullName, "
                                    + " c.IssueDate, c.Expirydate, c.DrivingLicenseNo, aa.IdentityNo as aadhar_no , "
                                    + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                                    + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                                    + " ifnull(c.EmailID,''),"
                                    + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.PerCityCode) as PerCityCode, "
                                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                                    + " c.PerPostalCode "
                                    + "  from  cbs_customer_master_detail c "
                                    + " left outer join "
                                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                                    + " union "
                                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                                    + " where c.customerid = '" + custIdList.get(j).toString() + "'").getResultList();
                            if (!l3.isEmpty()) {
                                pojo = new ExperionPojo();
                                Vector vtr = (Vector) l3.get(0);
                                title = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                custName = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                panGirNumber = vtr.get(4) != null ? vtr.get(4).toString() : "";
                                passportNo = vtr.get(5) != null ? vtr.get(5).toString() : "";
                                voterIdNo = vtr.get(6) != null ? vtr.get(6).toString() : "";
                                telephoneNumber = vtr.get(7) != null ? vtr.get(7).toString() : "";
                                mailAddressLine1 = vtr.get(8) != null ? vtr.get(8).toString() : "";
                                mailAddressLine2 = vtr.get(9) != null ? vtr.get(9).toString() : "";
                                mailVillage = vtr.get(10) != null ? vtr.get(10).toString() : "";
                                mailBlock = vtr.get(11) != null ? vtr.get(11).toString() : "";
                                mailCityCode = vtr.get(12) != null ? vtr.get(12).toString() : "";
                                mailStateCode = vtr.get(13) != null ? vtr.get(13).toString() : "";
                                mailPostalCode = vtr.get(14) != null ? vtr.get(14).toString() : "";
                                customerid = vtr.get(15) != null ? vtr.get(15).toString() : "";
                                shortName = vtr.get(16) != null ? vtr.get(16).toString() : "";

                                issueDate = vtr.get(17) != null ? vtr.get(17).toString() : "1900-01-01";
                                expirydate = vtr.get(18) != null ? vtr.get(18).toString() : "1900-01-01";
                                drivingLicenseNo = vtr.get(19) != null ? vtr.get(19).toString() : "";
                                uidNo = vtr.get(20) != null ? vtr.get(20).toString() : "";
                                telResident = vtr.get(21) != null ? vtr.get(21).toString() : "";
                                telOffice = vtr.get(22) != null ? vtr.get(22).toString() : "";
                                emailID = vtr.get(23) != null ? vtr.get(23).toString() : "";

                                perAddressLine1 = vtr.get(24) != null ? vtr.get(24).toString() : "";
                                perAddressLine2 = vtr.get(25) != null ? vtr.get(25).toString() : "";
                                perVillage = vtr.get(26) != null ? vtr.get(26).toString() : "";
                                perBlock = vtr.get(27) != null ? vtr.get(27).toString() : "";
                                perCityCode = vtr.get(28) != null ? vtr.get(28).toString() : "";
                                perStateCode = vtr.get(29) != null ? vtr.get(29).toString() : "";
                                perPostalCode = vtr.get(30) != null ? vtr.get(30).toString() : "";

                                pojo = new ExperionPojo();
                                pojo.setMemberCode(memberCode);
                                pojo.setMemberName(newMemberName);
                                pojo.setCycleIdent("00");
                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                pojo.setReportPassword("");
                                pojo.setFutureUse("");
                                pojo.setMemberData("");
                                st = custName.split(" ");
                                lastName = "";
                                firstName = "";
                                middleName = "";
                                if (st.length > 0) {
                                    firstName = st[0];
                                    middleName = st.length > 2 ? getMiddleName(st) : "";
                                    if (st.length > 1) {
                                        lastName = st[st.length - 1];
                                    }
                                }
                                pojo.setCustNameF1(lastName);
                                pojo.setCustNameF2(firstName);
                                if (!middleName.equalsIgnoreCase("")) {
                                    lastName = "";
                                    firstName = "";
                                    st = middleName.split(" ");
                                    if (st.length > 1) {
                                        firstName = st[0];
                                        if (st.length > 1) {
                                            middleName = st[st.length - 1];
                                        }
                                        lastName = st.length > 2 ? getMiddleName(st) : "";
                                        pojo.setCustNameF3(firstName);
                                        pojo.setCustNameF4(middleName);
                                        pojo.setCustNameF5(lastName);
                                    } else {
                                        pojo.setCustNameF3(middleName);
                                        pojo.setCustNameF4("");
                                        pojo.setCustNameF5("");
                                    }
                                } else {
                                    pojo.setCustNameF3(middleName);
                                    pojo.setCustNameF4("");
                                    pojo.setCustNameF5("");
                                }
                                pojo.setDob(dmy.format(y_m_d.parse(dob)).equalsIgnoreCase("01011900") ? "" : dmy.format(y_m_d.parse(dob)));
                                pojo.setGender(gender);            //1=MALE : 2=FEMALE
                                // changed due to Software Bug #36025
                                if (!panGirNumber.equalsIgnoreCase("") && panGirNumber.length() == 10) {
                                    if (ParseFileUtil.isAlphabet(panGirNumber.substring(0, 5)) && ParseFileUtil.isNumeric(panGirNumber.substring(5, 9)) && ParseFileUtil.isAlphabet(panGirNumber.substring(9))) {
                                        pojo.setIncomeTaxId(panGirNumber);
                                    } else {
                                        pojo.setIncomeTaxId("");
                                    }
                                } else {
                                    pojo.setIncomeTaxId("");
                                }
                                pojo.setPassportNo(passportNo);
                                pojo.setVoterIdNo(voterIdNo);
                                pojo.setTelephone(telephoneNumber.length() > 20 ? telephoneNumber.substring(0, 20) : telephoneNumber);

                                address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                                        !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                                        !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                                        !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                                        !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                                if (address.contains(" ")) {
                                    space = " ";
                                } else if (address.contains(",")) {
                                    space = ",";
                                } else {
                                    space = "-";
                                }
                                if (cibilVerNo.equalsIgnoreCase("3.0")) {
                                    pojo.setCustNameF1(custName);
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                                    pojo.setAddressLine2("");
                                    pojo.setAddressLine3("");
                                    pojo.setAddressLine4("");
                                    pojo.setAddressLine5("");
                                } else {
                                    if (address.contains(" ")) {
                                        space = " ";
                                    } else if (address.contains(",")) {
                                        space = ",";
                                    } else {
                                        space = "-";
                                    }
                                    if (address.length() > 40) {
                                        if (!address.substring(40).contains(space)) {
                                            StringBuilder sb = new StringBuilder();
                                            String[] pairs = address.split(",");
                                            sb.append(pairs[0]);
                                            for (int t = 1; t < pairs.length; ++t) {
                                                String pair = pairs[t];
                                                sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                                sb.append(pair);
                                            }
                                            address = sb.toString();
                                        }
                                    }
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");

                                }
                                pojo.setStateCode(mailStateCode);
                                pojo.setPinCode(mailPostalCode);
                                pojo.setReportingMemCode(memberCode);
                                pojo.setMemberShortName(newMemberName);

                                a1 = Integer.parseInt(acNo.substring(0, 2));
                                a2 = Integer.parseInt(acNo.substring(2, 4));
                                a3 = Integer.parseInt(acNo.substring(4, 10));
                                oldAcNo = String.valueOf(a1).concat("/").concat(String.valueOf(a2)).concat("/").concat(String.valueOf(a3));
                                if (cibil_old_acno.equalsIgnoreCase("Y")) {
                                    if (bankCode.equalsIgnoreCase("KHAT")) {
                                        pojo.setOldAccountNo(oldAcNo);
                                    } else {
                                        pojo.setOldAccountNo(OldAcnoMap);
                                    }
                                    pojo.setOldReportingMemCode(oldMemberCode);
                                    pojo.setOldMemShortName(oldMemberName);
                                } else if (cibil_old_acno.equalsIgnoreCase("N")) {
                                    pojo.setOldAccountNo("");
                                    pojo.setOldReportingMemCode("");
                                    pojo.setOldMemShortName("");
                                } else {
                                    pojo.setOldAccountNo("");
                                    pojo.setOldReportingMemCode("");
                                    pojo.setOldMemShortName("");
                                }
                                pojo.setCurrentAcno(acNo);
                                pojo.setAccountType(accountTypeTable);
                                pojo.setAccountHolderTypeCode("4");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                if (ymd.parse(closingDate).after(ymd.parse(toDate))) {
                                    pojo.setDateClose("");
                                } else {
                                    pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                }
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                if (Double.parseDouble(odLimit) == 0) {
                                    pojo.setSanctAmt("");
                                } else {
                                    pojo.setSanctAmt(formatter2.format(Double.parseDouble(odLimit)));
                                }
                                pojo.setCurrentBalance(formatter2.format(Double.parseDouble(currentBal)));
                                if (overDueAmt == 0) {
                                    pojo.setAmountOverDue("");
                                } else {
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                }
                                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                    pojo.setNoOfDaysPast("000");
                                } else {
                                    pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 900) ? "900" : overDueDays) : "");
                                }
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);
                                if (!passportNo.equalsIgnoreCase("")) {
                                    pojo.setPassportIssueDt(issueDate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(issueDate)));
                                    pojo.setPassportExpDt(expirydate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(expirydate)));
                                }
                                pojo.setDriLicenseNo(drivingLicenseNo);
                                pojo.setDriLicenseIssueDt("");
                                pojo.setDriLicenseExpDt("");
                                pojo.setRationCardNo("");
                                pojo.setUniversalIdNo(uidNo);
                                pojo.setAddId1("");
                                pojo.setAddId2("");
                                pojo.setTelNoResidence(telResident);
                                pojo.setTelNoOffice(telOffice);
                                pojo.setExtOffice("");
                                pojo.setTelNoOther("");
                                pojo.setExtOther("");
                                pojo.setEmailId1(emailID);
                                pojo.setEmailId2("");
                                pojo.setAddressCategory1(mailAddressLine1.equalsIgnoreCase("") ? "04" : "02");
                                pojo.setResidenceCode1(mailAddressLine1.equalsIgnoreCase("") ? perAddressLine1.equalsIgnoreCase("") ? "" : "01" : "02");
                                address2 = (!perAddressLine1.trim().equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.trim()).concat(
                                        !perAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2.trim()).concat(
                                        !perVillage.trim().equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage.trim()).concat(
                                        !perBlock.trim().equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock.trim()).concat(
                                        !perCityCode.trim().equalsIgnoreCase("") ? " ".concat(perCityCode.trim()) : perCityCode.trim());
                                if (address.contains(" ")) {
                                    space = " ";
                                } else if (address.contains(",")) {
                                    space = ",";
                                } else {
                                    space = "-";
                                }
                                if (address2.length() > 40) {
                                    if (!address2.substring(40).contains(space)) {
                                        StringBuilder sb = new StringBuilder();
                                        String[] pairs = address.split(",");
                                        sb.append(pairs[0]);
                                        for (int t = 1; t < pairs.length; ++t) {
                                            String pair = pairs[t];
                                            sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                            sb.append(pair);
                                        }
                                        address2 = sb.toString();
                                    }
                                }
                                pojo.setAddress2("");
                                pojo.setBorcity2("");
                                pojo.setBorDistrict2("");
                                pojo.setStateCode2("");
                                pojo.setPinCode2("");
                                pojo.setAddressCategory2("");
                                pojo.setResidenceCode2("");
                                pojo.setOldAccType(oldAcType);
                                pojo.setOldOwnershipIndicator(oldOwnershipIndicator);
                                pojo.setWrittenSettledStatus("");
                                pojo.setValueOfCollateral("00");
                                pojo.setTypeOfCollateral("00");
                                pojo.setCreditLimit(accountTypeTable.equalsIgnoreCase("10") ? odLimit : "");
                                pojo.setCashLimit("");
                                pojo.setRateOfInterest(roi);
                                //  pojo.setRepaymentTenure(repaymentTenure);
                                pojo.setRepaymentTenure(repaymentTenure.equalsIgnoreCase("0") ? "" : repaymentTenure);
                                //pojo.setEmiAmt(emiAmt);
                                pojo.setEmiAmt(emiAmt.equalsIgnoreCase("0") ? "" : emiAmt);
                                pojo.setWrittenOffAmountTotal("");
                                pojo.setWrittenOffPrincipalAmount("");
                                pojo.setSettlementAmt("");
                                if (periodicity.equalsIgnoreCase("W")) {
                                    pojo.setPaymentFrequency("01");
                                } else if (periodicity.equalsIgnoreCase("M")) {
                                    pojo.setPaymentFrequency("03");
                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                    pojo.setPaymentFrequency("04");
                                } else {
                                    pojo.setPaymentFrequency("04");
                                }
                                pojo.setActualPaymentAmt("");
                                pojo.setOccupationCode("");
                                pojo.setIncome(monthlyIncome.replace(".0", ""));
                                if (Double.parseDouble(pojo.getIncome()) == 0) {
                                    pojo.setIncome("");
                                }
                                /*
                                 * G = Gross Income 
                                 * N = Net Income */
                                pojo.setNetGrossIncomeIndicator(netWordth > 0 ? "N" : "G");
                                /*
                                 * M = Monthly 
                                 * A = Annual */
                                pojo.setMonthlyAnnualIncomeIndicator(monthlyIncome.equalsIgnoreCase("0") ? "A" : "M");
                                experionList.add(pojo);
                            }
                        }
                    } else {
                        List custJtList = em.createNativeQuery("select ifnull(jtName1,''),ifnull(jtName2,''),ifnull(jtName3,''),ifnull(jtName4,'') from accountmaster where acno "
                                + "= '" + acNo + "'").getResultList();
                        if (!custJtList.isEmpty()) {
                            custNameList = new ArrayList();
                            Vector vtrList = (Vector) custJtList.get(0);
                            if (vtrList.get(0) != null) {
                                if (!vtrList.get(0).toString().equals("")) {
                                    if (!vtrList.get(0).toString().equals(" ")) {
                                        custNameList.add(vtrList.get(0).toString());
                                    }
                                }
                            }
                            if (vtrList.get(1) != null) {
                                if (!vtrList.get(1).toString().equals("")) {
                                    if (!vtrList.get(1).toString().equals(" ")) {
                                        custNameList.add(vtrList.get(1).toString());
                                    }
                                }
                            }
                            if (vtrList.get(2) != null) {
                                if (!vtrList.get(2).toString().equals("")) {
                                    if (!vtrList.get(2).toString().equals(" ")) {
                                        custNameList.add(vtrList.get(2).toString());
                                    }
                                }
                            }
                            if (vtrList.get(3) != null) {
                                if (!vtrList.get(3).toString().equals("")) {
                                    if (!vtrList.get(3).toString().equals(" ")) {
                                        custNameList.add(vtrList.get(3).toString());
                                    }
                                }
                            }
                        }
                        for (int j = 0; j < custNameList.size(); j++) {
                            if (!custNameList.isEmpty()) {
                                pojo = new ExperionPojo();
                                custName = custNameList.get(j) != null ? custNameList.get(j).toString() : "";
                                pojo.setMemberCode(memberCode);
                                pojo.setMemberName(newMemberName);
                                pojo.setCycleIdent("00");
                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                pojo.setReportPassword("");
                                pojo.setFutureUse("");
                                pojo.setMemberData("");

                                st = custName.split(" ");
                                lastName = "";
                                firstName = "";
                                middleName = "";

                                if (st.length > 0) {
                                    firstName = st[0];
                                    middleName = st.length > 2 ? getMiddleName(st) : "";
                                    if (st.length > 1) {
                                        lastName = st[st.length - 1];
                                    }
                                }
                                pojo.setCustNameF1(lastName);
                                pojo.setCustNameF2(firstName);
                                if (!middleName.equalsIgnoreCase("")) {
                                    lastName = "";
                                    firstName = "";
                                    st = middleName.split(" ");
                                    if (st.length > 1) {
                                        firstName = st[0];
                                        if (st.length > 1) {
                                            middleName = st[st.length - 1];
                                        }
                                        lastName = st.length > 2 ? getMiddleName(st) : "";
                                        pojo.setCustNameF3(firstName);
                                        pojo.setCustNameF4(middleName);
                                        pojo.setCustNameF5(lastName);
                                    } else {
                                        pojo.setCustNameF3(middleName);
                                        pojo.setCustNameF4("");
                                        pojo.setCustNameF5("");
                                    }
                                } else {
                                    pojo.setCustNameF3(middleName);
                                    pojo.setCustNameF4("");
                                    pojo.setCustNameF5("");
                                }
                                pojo.setDob(dmy.format(y_m_d.parse(dob)).equalsIgnoreCase("01011900") ? "" : dmy.format(y_m_d.parse(dob)));
                                pojo.setGender(gender);            //1=MALE : 2=FEMALE
                                pojo.setIncomeTaxId("");
                                pojo.setPassportNo("");
                                pojo.setVoterIdNo("");
                                pojo.setTelephone(telephoneNumber.length() > 20 ? telephoneNumber.substring(0, 20) : telephoneNumber);

                                address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                                        !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                                        !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                                        !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                                        !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                                if (cibilVerNo.equalsIgnoreCase("3.0")) {
                                    pojo.setCustNameF1(custName);
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                                    pojo.setAddressLine2("");
                                    pojo.setAddressLine3("");
                                    pojo.setAddressLine4("");
                                    pojo.setAddressLine5("");
                                } else {
                                    if (address.contains(" ")) {
                                        space = " ";
                                    } else if (address.contains(",")) {
                                        space = ",";
                                    } else {
                                        space = "-";
                                    }
                                    if (address.length() > 40) {
                                        if (!address.substring(40).contains(space)) {
                                            StringBuilder sb = new StringBuilder();
                                            String[] pairs = address.split(",");
                                            sb.append(pairs[0]);
                                            for (int t = 1; t < pairs.length; ++t) {
                                                String pair = pairs[t];
                                                sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                                sb.append(pair);
                                            }
                                            address = sb.toString();
                                        }
                                    }
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");

                                }
                                pojo.setStateCode(mailStateCode);
                                pojo.setPinCode(mailPostalCode);
                                pojo.setReportingMemCode(memberCode);
                                pojo.setMemberShortName(newMemberName);
                                a1 = Integer.parseInt(acNo.substring(0, 2));
                                a2 = Integer.parseInt(acNo.substring(2, 4));
                                a3 = Integer.parseInt(acNo.substring(4, 10));
                                oldAcNo = String.valueOf(a1).concat("/").concat(String.valueOf(a2)).concat("/").concat(String.valueOf(a3));
                                if (cibil_old_acno.equalsIgnoreCase("Y")) {
                                    if (bankCode.equalsIgnoreCase("KHAT")) {
                                        pojo.setOldAccountNo(oldAcNo);
                                    } else {
                                        pojo.setOldAccountNo(OldAcnoMap);
                                    }
                                    pojo.setOldReportingMemCode(oldMemberCode);
                                    pojo.setOldMemShortName(oldMemberName);
                                } else if (cibil_old_acno.equalsIgnoreCase("N")) {
                                    pojo.setOldAccountNo("");
                                    pojo.setOldReportingMemCode("");
                                    pojo.setOldMemShortName("");
                                } else {
                                    pojo.setOldAccountNo("");
                                    pojo.setOldReportingMemCode("");
                                    pojo.setOldMemShortName("");
                                }
                                pojo.setCurrentAcno(acNo);
                                pojo.setAccountType(accountTypeTable);
                                pojo.setAccountHolderTypeCode("4");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                if (ymd.parse(closingDate).after(ymd.parse(toDate))) {
                                    pojo.setDateClose("");
                                } else {
                                    pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                }
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                if (Double.parseDouble(odLimit) == 0) {
                                    pojo.setSanctAmt("");
                                } else {
                                    pojo.setSanctAmt(formatter2.format(Double.parseDouble(odLimit)));
                                }
                                pojo.setCurrentBalance(formatter2.format(Double.parseDouble(currentBal)));
                                if (overDueAmt == 0) {
                                    pojo.setAmountOverDue("");
                                } else {
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                }
                                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                    pojo.setNoOfDaysPast("000");
                                } else {
                                    pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 900) ? "900" : overDueDays) : "");
                                }
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);
                                if (!passportNo.equalsIgnoreCase("")) {
                                    pojo.setPassportIssueDt(issueDate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(issueDate)));
                                    pojo.setPassportExpDt(expirydate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(expirydate)));
                                }

                                pojo.setDriLicenseNo(drivingLicenseNo);
                                pojo.setDriLicenseIssueDt("");
                                pojo.setDriLicenseExpDt("");
                                pojo.setRationCardNo("");
                                pojo.setUniversalIdNo(uidNo);

                                pojo.setAddId1("");
                                pojo.setAddId2("");

                                pojo.setTelNoResidence(telResident);
                                pojo.setTelNoOffice(telOffice);
                                pojo.setExtOffice("");
                                pojo.setTelNoOther("");
                                pojo.setExtOther("");
                                pojo.setEmailId1(emailID);
                                pojo.setEmailId2("");
                                /* Address Category
                                 * 01 = Permanent Address 
                                 * 02 = Residence Address 
                                 * 03 = Office Address 
                                 * 04 = Not Categorized */
                                pojo.setAddressCategory1(mailAddressLine1.equalsIgnoreCase("") ? "04" : "02");
                                /* Residence Code
                                 * 01 = Owned 
                                 * 02 = Rented */
                                pojo.setResidenceCode1(mailAddressLine1.equalsIgnoreCase("") ? perAddressLine1.equalsIgnoreCase("") ? "" : "01" : "02");
                                address2 = (!perAddressLine1.trim().equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.trim()).concat(
                                        !perAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2.trim()).concat(
                                        !perVillage.trim().equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage.trim()).concat(
                                        !perBlock.trim().equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock.trim()).concat(
                                        !perCityCode.trim().equalsIgnoreCase("") ? " ".concat(perCityCode.trim()) : perCityCode.trim());
                                if (address.contains(" ")) {
                                    space = " ";
                                } else if (address.contains(",")) {
                                    space = ",";
                                } else {
                                    space = "-";
                                }
                                if (address2.length() > 40) {
                                    if (!address2.substring(40).contains(space)) {
                                        StringBuilder sb = new StringBuilder();
                                        String[] pairs = address.split(",");
                                        sb.append(pairs[0]);
                                        for (int t = 1; t < pairs.length; ++t) {
                                            String pair = pairs[t];
                                            sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                            sb.append(pair);
                                        }
                                        address2 = sb.toString();
                                    }
                                }
                                pojo.setAddress2("");
                                pojo.setBorcity2("");
                                pojo.setBorDistrict2("");
                                pojo.setStateCode2("");
                                pojo.setPinCode2("");
                                /* Address Category
                                 * 01 = Permanent Address 
                                 * 02 = Residence Address 
                                 * 03 = Office Address 
                                 * 04 = Not Categorized */
                                pojo.setAddressCategory2("");
                                /* Residence Code
                                 * 01 = Owned 
                                 * 02 = Rented */
                                pojo.setResidenceCode2("");

                                pojo.setOldAccType(oldAcType);
                                pojo.setOldOwnershipIndicator(oldOwnershipIndicator);
                                pojo.setWrittenSettledStatus("");

                                pojo.setValueOfCollateral("00");
                                pojo.setTypeOfCollateral("00");
                                pojo.setCreditLimit(accountTypeTable.equalsIgnoreCase("10") ? odLimit : "");
                                pojo.setCashLimit("");
                                pojo.setRateOfInterest(roi);
                                // pojo.setRepaymentTenure(repaymentTenure);
                                pojo.setRepaymentTenure(repaymentTenure.equalsIgnoreCase("0") ? "" : repaymentTenure);
                                // pojo.setEmiAmt(emiAmt);
                                pojo.setEmiAmt(emiAmt.equalsIgnoreCase("0") ? "" : emiAmt);
                                pojo.setWrittenOffAmountTotal("");
                                pojo.setWrittenOffPrincipalAmount("");
                                pojo.setSettlementAmt("");
                                if (periodicity.equalsIgnoreCase("W")) {
                                    pojo.setPaymentFrequency("01");
                                } else if (periodicity.equalsIgnoreCase("M")) {
                                    pojo.setPaymentFrequency("03");
                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                    pojo.setPaymentFrequency("04");
                                } else {
                                    pojo.setPaymentFrequency("04");
                                }

                                pojo.setActualPaymentAmt("");
                                pojo.setOccupationCode("");
                                pojo.setIncome(monthlyIncome.replace(".0", ""));
                                if (Double.parseDouble(pojo.getIncome()) == 0) {
                                    pojo.setIncome("");
                                }
                                pojo.setNetGrossIncomeIndicator(netWordth > 0 ? "N" : "G");
                                pojo.setMonthlyAnnualIncomeIndicator(monthlyIncome.equalsIgnoreCase("0") ? "A" : "M");
                                experionList.add(pojo);
                            }
                        }
                    }
                }
                if (flag == true) {
                    List l8 = em.createNativeQuery("select phno,address,acno,name,ifnull(CUST_FLAG,'N'),ifnull(GAR_ACNO,''),ifnull(GAR_CUSTID,'') from loan_guarantordetails where acno ='" + acNo + "'").getResultList();
                    if (!l8.isEmpty()) {
                        for (int k = 0; k < l8.size(); k++) {
                            pojo = new ExperionPojo();
                            Vector vtr = (Vector) l8.get(k);
                            String garCustFlag = vtr.get(4).toString();
                            String garCustAcNo = vtr.get(5).toString();
                            String garCustId = vtr.get(6).toString();
                            /* If Customer ID/ Account No doesn't exist */
                            if (garCustFlag.equalsIgnoreCase("N")) {
                                custName = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                pojo.setMemberCode(memberCode);
                                pojo.setMemberName(newMemberName);
                                pojo.setCycleIdent("00");
                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                pojo.setReportPassword("");
                                pojo.setFutureUse("");
                                pojo.setMemberData("");

                                st = custName.split(" ");
                                lastName = "";
                                firstName = "";
                                middleName = "";

                                if (st.length > 0) {
                                    firstName = st[0];
                                    middleName = st.length > 2 ? getMiddleName(st) : "";
                                    if (st.length > 1) {
                                        lastName = st[st.length - 1];
                                    }
                                }
                                pojo.setCustNameF1(lastName);
                                pojo.setCustNameF2(firstName);
                                if (!middleName.equalsIgnoreCase("")) {
                                    lastName = "";
                                    firstName = "";
                                    st = middleName.split(" ");
                                    if (st.length > 1) {
                                        firstName = st[0];
                                        if (st.length > 1) {
                                            middleName = st[st.length - 1];
                                        }
                                        lastName = st.length > 2 ? getMiddleName(st) : "";
                                        pojo.setCustNameF3(firstName);
                                        pojo.setCustNameF4(middleName);
                                        pojo.setCustNameF5(lastName);
                                    } else {
                                        pojo.setCustNameF3(middleName);
                                        pojo.setCustNameF4("");
                                        pojo.setCustNameF5("");
                                    }
                                } else {
                                    pojo.setCustNameF3(middleName);
                                    pojo.setCustNameF4("");
                                    pojo.setCustNameF5("");
                                }
                                pojo.setDob(dmy.format(y_m_d.parse(dob)).equalsIgnoreCase("01011900") ? "" : dmy.format(y_m_d.parse(dob)));
                                pojo.setGender(gender);            //1=MALE : 2=FEMALE
                                // changed due to Software Bug #36025
                                if (!panGirNumber.equalsIgnoreCase("") && panGirNumber.length() == 10) {
                                    if (ParseFileUtil.isAlphabet(panGirNumber.substring(0, 5)) && ParseFileUtil.isNumeric(panGirNumber.substring(5, 9)) && ParseFileUtil.isAlphabet(panGirNumber.substring(9))) {
                                        pojo.setIncomeTaxId(panGirNumber);
                                    } else {
                                        pojo.setIncomeTaxId("");
                                    }
                                } else {
                                    pojo.setIncomeTaxId("");
                                }
                                pojo.setPassportNo(passportNo);
                                pojo.setVoterIdNo(voterIdNo);
                                pojo.setTelephone(vtr.get(0) != null ? vtr.get(0).toString() : "");

                                address = vtr.get(1) != null ? (vtr.get(1).toString().length() > 20 ? vtr.get(1).toString().substring(0, 20) : vtr.get(1).toString()) : "";
                                if (cibilVerNo.equalsIgnoreCase("3.0")) {
                                    pojo.setCustNameF1(custName);
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                                    pojo.setAddressLine2("");
                                    pojo.setAddressLine3("");
                                    pojo.setAddressLine4("");
                                    pojo.setAddressLine5("");
                                } else {
                                    if (address.contains(" ")) {
                                        space = " ";
                                    } else if (address.contains(",")) {
                                        space = ",";
                                    } else {
                                        space = "-";
                                    }
                                    if (address.length() > 40) {
                                        if (!address.substring(40).contains(space)) {
                                            StringBuilder sb = new StringBuilder();
                                            String[] pairs = address.split(",");
                                            sb.append(pairs[0]);
                                            for (int t = 1; t < pairs.length; ++t) {
                                                String pair = pairs[t];
                                                sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                                sb.append(pair);
                                            }
                                            address = sb.toString();
                                        }
                                    }
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");

                                }
                                pojo.setStateCode(mailStateCode);
                                pojo.setPinCode(mailPostalCode);
                                pojo.setReportingMemCode(memberCode);
                                pojo.setMemberShortName(newMemberName);
                                a1 = Integer.parseInt(acNo.substring(0, 2));
                                a2 = Integer.parseInt(acNo.substring(2, 4));
                                a3 = Integer.parseInt(acNo.substring(4, 10));
                                oldAcNo = String.valueOf(a1).concat("/").concat(String.valueOf(a2)).concat("/").concat(String.valueOf(a3));
                                if (cibil_old_acno.equalsIgnoreCase("Y")) {
                                    if (bankCode.equalsIgnoreCase("KHAT")) {
                                        pojo.setOldAccountNo(oldAcNo);
                                    } else {
                                        pojo.setOldAccountNo(OldAcnoMap);
                                    }
                                    pojo.setOldReportingMemCode(oldMemberCode);
                                    pojo.setOldMemShortName(oldMemberName);
                                } else if (cibil_old_acno.equalsIgnoreCase("N")) {
                                    pojo.setOldAccountNo("");
                                    pojo.setOldReportingMemCode("");
                                    pojo.setOldMemShortName("");
                                } else {
                                    pojo.setOldAccountNo("");
                                    pojo.setOldReportingMemCode("");
                                    pojo.setOldMemShortName("");
                                }
                                pojo.setCurrentAcno(acNo);
                                pojo.setAccountType(accountTypeTable);
                                pojo.setAccountHolderTypeCode("3");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                if (ymd.parse(closingDate).after(ymd.parse(toDate))) {
                                    pojo.setDateClose("");
                                } else {
                                    pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                }
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                if (Double.parseDouble(odLimit) == 0) {
                                    pojo.setSanctAmt("");
                                } else {
                                    pojo.setSanctAmt(formatter2.format(Double.parseDouble(odLimit)));
                                }
                                pojo.setCurrentBalance(formatter2.format(Double.parseDouble(currentBal)));
                                if (overDueAmt == 0) {
                                    pojo.setAmountOverDue("");
                                } else {
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                }
                                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                    pojo.setNoOfDaysPast("000");
                                } else {
                                    pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 900) ? "900" : overDueDays) : "");
                                }
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);
                                if (!passportNo.equalsIgnoreCase("")) {
                                    pojo.setPassportIssueDt(issueDate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(issueDate)));
                                    pojo.setPassportExpDt(expirydate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(expirydate)));
                                }

                                pojo.setDriLicenseNo(drivingLicenseNo);
                                pojo.setDriLicenseIssueDt("");
                                pojo.setDriLicenseExpDt("");
                                pojo.setRationCardNo("");
                                pojo.setUniversalIdNo(uidNo);
                                pojo.setAddId1("");
                                pojo.setAddId2("");
                                pojo.setTelNoResidence(telResident);
                                pojo.setTelNoOffice(telOffice);
                                pojo.setExtOffice("");
                                pojo.setTelNoOther("");
                                pojo.setExtOther("");
                                pojo.setEmailId1(emailID);
                                pojo.setEmailId2("");
                                pojo.setAddressCategory1(mailAddressLine1.equalsIgnoreCase("") ? "04" : "02");
                                pojo.setResidenceCode1(mailAddressLine1.equalsIgnoreCase("") ? perAddressLine1.equalsIgnoreCase("") ? "" : "01" : "02");

                                address2 = (!perAddressLine1.trim().equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.trim()).concat(
                                        !perAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2.trim()).concat(
                                        !perVillage.trim().equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage.trim()).concat(
                                        !perBlock.trim().equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock.trim()).concat(
                                        !perCityCode.trim().equalsIgnoreCase("") ? " ".concat(perCityCode.trim()) : perCityCode.trim());
                                if (address.contains(" ")) {
                                    space = " ";
                                } else if (address.contains(",")) {
                                    space = ",";
                                } else {
                                    space = "-";
                                }
                                if (address2.length() > 40) {
                                    if (!address2.substring(40).contains(space)) {
                                        StringBuilder sb = new StringBuilder();
                                        String[] pairs = address.split(",");
                                        sb.append(pairs[0]);
                                        for (int t = 1; t < pairs.length; ++t) {
                                            String pair = pairs[t];
                                            sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                            sb.append(pair);
                                        }
                                        address2 = sb.toString();
                                    }
                                }
                                pojo.setAddress2("");
                                pojo.setBorcity2("");
                                pojo.setBorDistrict2("");
                                pojo.setStateCode2("");
                                pojo.setPinCode2("");
                                pojo.setAddressCategory2("");
                                pojo.setResidenceCode2("");
                                pojo.setOldAccType(oldAcType);
                                pojo.setOldOwnershipIndicator(oldOwnershipIndicator);
                                pojo.setWrittenSettledStatus("");
                                pojo.setValueOfCollateral("00");
                                pojo.setTypeOfCollateral("00");
                                pojo.setCreditLimit(accountTypeTable.equalsIgnoreCase("10") ? odLimit : "");
                                pojo.setCashLimit("");
                                pojo.setRateOfInterest(roi);
                                // pojo.setRepaymentTenure(repaymentTenure);
                                pojo.setRepaymentTenure(repaymentTenure.equalsIgnoreCase("0") ? "" : repaymentTenure);
                                // pojo.setEmiAmt(emiAmt);
                                pojo.setEmiAmt(emiAmt.equalsIgnoreCase("0") ? "" : emiAmt);
                                pojo.setWrittenOffAmountTotal("");
                                pojo.setWrittenOffPrincipalAmount("");
                                pojo.setSettlementAmt("");
                                if (periodicity.equalsIgnoreCase("W")) {
                                    pojo.setPaymentFrequency("01");
                                } else if (periodicity.equalsIgnoreCase("M")) {
                                    pojo.setPaymentFrequency("03");
                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                    pojo.setPaymentFrequency("04");
                                } else {
                                    pojo.setPaymentFrequency("04");
                                }

                                pojo.setActualPaymentAmt("");
                                pojo.setOccupationCode("");
                                pojo.setIncome(monthlyIncome.replace(".0", ""));
                                if (Double.parseDouble(pojo.getIncome()) == 0) {
                                    pojo.setIncome("");
                                }
                                pojo.setNetGrossIncomeIndicator(netWordth > 0 ? "N" : "G");
                                pojo.setMonthlyAnnualIncomeIndicator(monthlyIncome.equalsIgnoreCase("0") ? "A" : "M");
                                experionList.add(pojo);
                            } else if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                                /* If Custid Exist in Guarantor */
                                List l3 = em.createNativeQuery("select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                                        + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                                        + " c.PAN_GIRNumber,c.PassportNo, c.VoterIDNo, ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                                        + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                                        + " c.MailAddressLine1, c.MailAddressLine2, c.MailVillage, c.MailBlock , "
                                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode) as MailCityCode, "
                                        + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                                        + " c.MailPostalCode, c.customerid, c.CustFullName, "
                                        + " c.IssueDate, c.Expirydate, c.DrivingLicenseNo, aa.IdentityNo as aadhar_no , "
                                        + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                                        + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                                        + " ifnull(c.EmailID,''),"
                                        + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.PerCityCode) as PerCityCode, "
                                        + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                                        + " c.PerPostalCode "
                                        + " from  cbs_customer_master_detail c "
                                        + " left outer join "
                                        + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                                        + " union "
                                        + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                                        + " where c.customerid = '" + garCustId + "'").getResultList();
                                if (!l3.isEmpty()) {
                                    pojo = new ExperionPojo();
                                    vtr = (Vector) l3.get(0);
                                    title = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                    custName = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                    dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                    gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                    panGirNumber = vtr.get(4) != null ? vtr.get(4).toString() : "";
                                    passportNo = vtr.get(5) != null ? vtr.get(5).toString() : "";
                                    voterIdNo = vtr.get(6) != null ? vtr.get(6).toString() : "";
                                    telephoneNumber = vtr.get(7) != null ? vtr.get(7).toString() : "";
                                    mailAddressLine1 = vtr.get(8) != null ? vtr.get(8).toString() : "";
                                    mailAddressLine2 = vtr.get(9) != null ? vtr.get(9).toString() : "";
                                    mailVillage = vtr.get(10) != null ? vtr.get(10).toString() : "";
                                    mailBlock = vtr.get(11) != null ? vtr.get(11).toString() : "";
                                    mailCityCode = vtr.get(12) != null ? vtr.get(12).toString() : "";
                                    mailStateCode = vtr.get(13) != null ? vtr.get(13).toString() : "";
                                    mailPostalCode = vtr.get(14) != null ? vtr.get(14).toString() : "";
                                    customerid = vtr.get(15) != null ? vtr.get(15).toString() : "";
                                    shortName = vtr.get(16) != null ? vtr.get(16).toString() : "";
                                    issueDate = vtr.get(17) != null ? vtr.get(17).toString() : "1900-01-01";
                                    expirydate = vtr.get(18) != null ? vtr.get(18).toString() : "1900-01-01";
                                    drivingLicenseNo = vtr.get(19) != null ? vtr.get(19).toString() : "";
                                    uidNo = vtr.get(20) != null ? vtr.get(20).toString() : "";
                                    telResident = vtr.get(21) != null ? vtr.get(21).toString() : "";
                                    telOffice = vtr.get(22) != null ? vtr.get(22).toString() : "";
                                    emailID = vtr.get(23) != null ? vtr.get(23).toString() : "";
                                    perAddressLine1 = vtr.get(24) != null ? vtr.get(24).toString() : "";
                                    perAddressLine2 = vtr.get(25) != null ? vtr.get(25).toString() : "";
                                    perVillage = vtr.get(26) != null ? vtr.get(26).toString() : "";
                                    perBlock = vtr.get(27) != null ? vtr.get(27).toString() : "";
                                    perCityCode = vtr.get(28) != null ? vtr.get(28).toString() : "";
                                    perStateCode = vtr.get(29) != null ? vtr.get(29).toString() : "";
                                    perPostalCode = vtr.get(30) != null ? vtr.get(30).toString() : "";
                                    pojo = new ExperionPojo();
                                    pojo.setMemberCode(memberCode);
                                    pojo.setMemberName(newMemberName);
                                    pojo.setCycleIdent("00");
                                    pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                    pojo.setReportPassword("");
                                    pojo.setFutureUse("");
                                    pojo.setMemberData("");
                                    st = custName.split(" ");
                                    lastName = "";
                                    firstName = "";
                                    middleName = "";
                                    if (st.length > 0) {
                                        firstName = st[0];
                                        middleName = st.length > 2 ? getMiddleName(st) : "";
                                        if (st.length > 1) {
                                            lastName = st[st.length - 1];
                                        }
                                    }
                                    pojo.setCustNameF1(lastName);
                                    pojo.setCustNameF2(firstName);
                                    if (!middleName.equalsIgnoreCase("")) {
                                        lastName = "";
                                        firstName = "";
                                        st = middleName.split(" ");
                                        if (st.length > 1) {
                                            firstName = st[0];
                                            if (st.length > 1) {
                                                middleName = st[st.length - 1];
                                            }
                                            lastName = st.length > 2 ? getMiddleName(st) : "";
                                            pojo.setCustNameF3(firstName);
                                            pojo.setCustNameF4(middleName);
                                            pojo.setCustNameF5(lastName);
                                        } else {
                                            pojo.setCustNameF3(middleName);
                                            pojo.setCustNameF4("");
                                            pojo.setCustNameF5("");
                                        }
                                    } else {
                                        pojo.setCustNameF3(middleName);
                                        pojo.setCustNameF4("");
                                        pojo.setCustNameF5("");
                                    }
                                    pojo.setDob(dmy.format(y_m_d.parse(dob)).equalsIgnoreCase("01011900") ? "" : dmy.format(y_m_d.parse(dob)));
                                    pojo.setGender(gender);            //1=MALE : 2=FEMALE
                                    // changed due to Software Bug #36025
                                    if (!panGirNumber.equalsIgnoreCase("") && panGirNumber.length() == 10) {
                                        if (ParseFileUtil.isAlphabet(panGirNumber.substring(0, 5)) && ParseFileUtil.isNumeric(panGirNumber.substring(5, 9)) && ParseFileUtil.isAlphabet(panGirNumber.substring(9))) {
                                            pojo.setIncomeTaxId(panGirNumber);
                                        } else {
                                            pojo.setIncomeTaxId("");
                                        }
                                    } else {
                                        pojo.setIncomeTaxId("");
                                    }
                                    pojo.setPassportNo(passportNo);
                                    pojo.setVoterIdNo(voterIdNo);
                                    pojo.setTelephone(telephoneNumber.length() > 20 ? telephoneNumber.substring(0, 20) : telephoneNumber);

                                    address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                                            !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                                            !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                                            !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                                            !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                                    if (cibilVerNo.equalsIgnoreCase("3.0")) {
                                        pojo.setCustNameF1(custName);
                                        pojo.setAddressLine1(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                                        pojo.setAddressLine2("");
                                        pojo.setAddressLine3("");
                                        pojo.setAddressLine4("");
                                        pojo.setAddressLine5("");
                                    } else {
                                        if (address.contains(" ")) {
                                            space = " ";
                                        } else if (address.contains(",")) {
                                            space = ",";
                                        } else {
                                            space = "-";
                                        }
                                        if (address.length() > 40) {
                                            if (!address.substring(40).contains(space)) {
                                                StringBuilder sb = new StringBuilder();
                                                String[] pairs = address.split(",");
                                                sb.append(pairs[0]);
                                                for (int t = 1; t < pairs.length; ++t) {
                                                    String pair = pairs[t];
                                                    sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                                    sb.append(pair);
                                                }
                                                address = sb.toString();
                                            }
                                        }
                                        pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");

                                    }
                                    pojo.setStateCode(mailStateCode);
                                    pojo.setPinCode(mailPostalCode);
                                    pojo.setReportingMemCode(memberCode);
                                    pojo.setMemberShortName(newMemberName);
                                    a1 = Integer.parseInt(acNo.substring(0, 2));
                                    a2 = Integer.parseInt(acNo.substring(2, 4));
                                    a3 = Integer.parseInt(acNo.substring(4, 10));
                                    oldAcNo = String.valueOf(a1).concat("/").concat(String.valueOf(a2)).concat("/").concat(String.valueOf(a3));
                                    if (cibil_old_acno.equalsIgnoreCase("Y")) {
                                        if (bankCode.equalsIgnoreCase("KHAT")) {
                                            pojo.setOldAccountNo(oldAcNo);
                                        } else {
                                            pojo.setOldAccountNo(OldAcnoMap);
                                        }
                                        pojo.setOldReportingMemCode(oldMemberCode);
                                        pojo.setOldMemShortName(oldMemberName);
                                    } else if (cibil_old_acno.equalsIgnoreCase("N")) {
                                        pojo.setOldAccountNo("");
                                        pojo.setOldReportingMemCode("");
                                        pojo.setOldMemShortName("");
                                    } else {
                                        pojo.setOldAccountNo("");
                                        pojo.setOldReportingMemCode("");
                                        pojo.setOldMemShortName("");
                                    }
                                    pojo.setCurrentAcno(acNo);
                                    pojo.setAccountType(accountTypeTable);
                                    pojo.setAccountHolderTypeCode("3");
                                    pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                    pojo.setLastPaymentdate(lastPaymentDt);
                                    if (ymd.parse(closingDate).after(ymd.parse(toDate))) {
                                        pojo.setDateClose("");
                                    } else {
                                        pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                    }
                                    pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                    if (Double.parseDouble(odLimit) == 0) {
                                        pojo.setSanctAmt("");
                                    } else {
                                        pojo.setSanctAmt(formatter2.format(Double.parseDouble(odLimit)));
                                    }
                                    pojo.setCurrentBalance(formatter2.format(Double.parseDouble(currentBal)));
                                    if (overDueAmt == 0) {
                                        pojo.setAmountOverDue("");
                                    } else {
                                        pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                    }
                                    if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                        pojo.setNoOfDaysPast("000");
                                    } else {
                                        pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 900) ? "900" : overDueDays) : "");
                                    }
                                    pojo.setWrittenOffStatus(writtrnOff);
                                    pojo.setAssetClassif(assetClass);
                                    if (!passportNo.equalsIgnoreCase("")) {
                                        pojo.setPassportIssueDt(issueDate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(issueDate)));
                                        pojo.setPassportExpDt(expirydate.contains("1900-01-01") ? "" : dmy.format(y_m_d.parse(expirydate)));
                                    }
                                    pojo.setDriLicenseNo(drivingLicenseNo);
                                    pojo.setDriLicenseIssueDt("");
                                    pojo.setDriLicenseExpDt("");
                                    pojo.setRationCardNo("");
                                    pojo.setUniversalIdNo(uidNo);
                                    pojo.setAddId1("");
                                    pojo.setAddId2("");
                                    pojo.setTelNoResidence(telResident);
                                    pojo.setTelNoOffice(telOffice);
                                    pojo.setExtOffice("");
                                    pojo.setTelNoOther("");
                                    pojo.setExtOther("");
                                    pojo.setEmailId1(emailID);
                                    pojo.setEmailId2("");
                                    pojo.setAddressCategory1(mailAddressLine1.equalsIgnoreCase("") ? "04" : "02");
                                    pojo.setResidenceCode1(mailAddressLine1.equalsIgnoreCase("") ? perAddressLine1.equalsIgnoreCase("") ? "" : "01" : "02");

                                    address2 = (!perAddressLine1.trim().equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.trim()).concat(
                                            !perAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2.trim()).concat(
                                            !perVillage.trim().equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage.trim()).concat(
                                            !perBlock.trim().equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock.trim()).concat(
                                            !perCityCode.trim().equalsIgnoreCase("") ? " ".concat(perCityCode.trim()) : perCityCode.trim());
                                    if (address.contains(" ")) {
                                        space = " ";
                                    } else if (address.contains(",")) {
                                        space = ",";
                                    } else {
                                        space = "-";
                                    }
                                    if (address2.length() > 40) {
                                        if (!address2.substring(40).contains(space)) {
                                            StringBuilder sb = new StringBuilder();
                                            String[] pairs = address.split(",");
                                            sb.append(pairs[0]);
                                            for (int t = 1; t < pairs.length; ++t) {
                                                String pair = pairs[t];
                                                sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                                                sb.append(pair);
                                            }
                                            address2 = sb.toString();
                                        }
                                    }
                                    pojo.setAddress2("");
                                    pojo.setBorcity2("");
                                    pojo.setBorDistrict2("");
                                    pojo.setStateCode2("");
                                    pojo.setPinCode2("");
                                    pojo.setAddressCategory2("");
                                    pojo.setResidenceCode2("");
                                    pojo.setOldAccType(oldAcType);
                                    pojo.setOldOwnershipIndicator(oldOwnershipIndicator);
                                    pojo.setWrittenSettledStatus("");
                                    pojo.setValueOfCollateral("00");
                                    pojo.setTypeOfCollateral("00");
                                    pojo.setCreditLimit(accountTypeTable.equalsIgnoreCase("10") ? odLimit : "");
                                    pojo.setCashLimit("");
                                    pojo.setRateOfInterest(roi);
                                    // pojo.setRepaymentTenure(repaymentTenure);
                                    pojo.setRepaymentTenure(repaymentTenure.equalsIgnoreCase("0") ? "" : repaymentTenure);
                                    //pojo.setEmiAmt(emiAmt);
                                    pojo.setEmiAmt(emiAmt.equalsIgnoreCase("0") ? "" : emiAmt);
                                    pojo.setWrittenOffAmountTotal("");
                                    pojo.setWrittenOffPrincipalAmount("");
                                    pojo.setSettlementAmt("");
                                    if (periodicity.equalsIgnoreCase("W")) {
                                        pojo.setPaymentFrequency("01");
                                    } else if (periodicity.equalsIgnoreCase("M")) {
                                        pojo.setPaymentFrequency("03");
                                    } else if (periodicity.equalsIgnoreCase("Q")) {
                                        pojo.setPaymentFrequency("04");
                                    } else {
                                        pojo.setPaymentFrequency("04");
                                    }
                                    pojo.setActualPaymentAmt("");
                                    pojo.setOccupationCode("");
                                    pojo.setIncome(monthlyIncome.replace(".0", ""));
                                    if (Double.parseDouble(pojo.getIncome()) == 0) {
                                        pojo.setIncome("");
                                    }
                                    pojo.setNetGrossIncomeIndicator(netWordth > 0 ? "N" : "G");
                                    pojo.setMonthlyAnnualIncomeIndicator(monthlyIncome.equalsIgnoreCase("0") ? "A" : "M");
                                    experionList.add(pojo);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("End Time" + new Date());
            return experionList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public List custIdListForGuarantor(String custid) throws ApplicationException {
        try {
            List l3 = em.createNativeQuery("select  ifnull(c.title,''), c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                    + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                    + " ifnull(c.PAN_GIRNumber,''),ifnull(c.PassportNo,''), ifnull(c.VoterIDNo,''), ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                    + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                    + " c.MailAddressLine1, c.MailAddressLine2, c.MailVillage, c.MailBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode) as MailCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                    + " ifnull(c.MailPostalCode,''), c.customerid, c.CustFullName, "
                    + " c.IssueDate, c.Expirydate, ifnull(c.DrivingLicenseNo,''), ifnull(aa.IdentityNo,'') as aadhar_no , "
                    + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                    + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                    + " ifnull(c.EmailID,''),"
                    + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.PerCityCode) as PerCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                    + " ifnull(c.PerPostalCode,''),CustEntityType,bb.Commencement_Date "
                    + " from  cbs_customer_master_detail c "
                    + " left outer join "
                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " left join "
                    + " (select CustomerId,Commencement_Date from cbs_cust_misinfo) bb on bb.CustomerId = c.customerid"
                    + " where c.customerid = '" + custid + "'").getResultList();
            return l3;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException();
        }
    }

    public String getFinYear(String dt) throws ApplicationException {
        try {
            List yearEndList = em.createNativeQuery("select f_year from cbs_yearend where '" + dt + "' between mindate and maxdate").getResultList();
            Vector vec = (Vector) yearEndList.get(0);
            return vec.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public CibilComPoJo cibilComercial(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String cibilParameter) throws ApplicationException {
        List<EquifaxComercialPoJo> experionList = new ArrayList<EquifaxComercialPoJo>();
        String preMemberCode = "", reportDt = "", futureUse = "", brnCode = "", borName = "", shortName = "", borOfycLoc = "",
                borAdd1 = "", borAdd2 = "", borAdd3 = "", borCity = "", borDistrict = "", borPin = "", borTelNo = "", borTelAreaCode = "", borFaxNo = "", borFaxArea = "",
                borPan = "", borLglConst = "", classOFAct1 = "", classOfAct2 = "", classOfAct3 = "", relationshipData = "", relType = "", relPan = "",
                relationShip = "", businessEntityName = "", jtPreFix = "", jtFullName = "", percOfContrl = "", jtAdd1 = "", jtAdd2 = "", jtAdd3 = "", jtCity = "", jtState = "",
                jtPin = "", jtcountry = "91", jtTelNo = "", jtTelArea = "", crAcData = "", acno = "", preAcno = "", sanctDt = "", currencyCode = "",
                creditType = "", dp = "", assetClass = "", bnkRemarkCode = "", defalultStatus = "", defaultStatusDt = "", suitStatus = "", suitRefNo = "", suitDt = "";
        BigDecimal sanctAmt = new BigDecimal("0"), outstanding = new BigDecimal("0"), suitAmt = new BigDecimal("0");
        try {
            System.out.println("Start Time" + new Date());
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Vector vtr1 = null;
            String sanctionAmt = null, currentBal = "0", amtOverdue = null, writtrnOff = null;
            String overDueDays = "";
            List custIdList;
            List custNameList = new ArrayList();
            List<OverdueEmiReportPojo> OverdueTlList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", 1, 5000, toDate, "0A", "A", false, "", "N");
            List<OverdueNonEmiResultList> OverdueDlList = overDueRemote.getOverDueNonEmi("DL", "ALL", toDate, "0A", "N");
            List<OverdueNonEmiResultList> OverdueCaList = overDueRemote.getOverDueNonEmi("CA", "ALL", toDate, "0A", "N");
            String title = "", custName = "", dob = "", gender = "", panGirNumber = "", passportNo = "", voterIdNo = "", telephoneNumber = "",
                    mailAddressLine1 = "", mailAddressLine2 = "", mailVillage = "", mailBlock = "", mailCityCode = "", mailStateCode = "", mailPostalCode = "",
                    customerid = "", acNo = "", operMode = "", openingDt = "", closingDate = "", accStatus = "",
                    custId1 = "", custId2 = "", custId3 = "", custId4 = "", address = "", space = " ",
                    perAddressLine1 = "", perAddressLine2 = "", perVillage = "", perBlock = "", perCityCode = "", perStateCode = "", perPostalCode = "",
                    issueDate = "", expirydate = "", drivingLicenseNo = "", uidNo = "",
                    telResident = "", telOffice = "", emailID = "", odLimit = "", address2 = "",
                    acNature = "", acctcode = "", custNameAccMast = "", bussCat = "", bussType = "";

            List acNatureList = new ArrayList();
            /*1 For Consumer and parameter will be CIBIL_ACNATURE(DL & TL)
             * 2 For Comercial and parameter will be CIBIL_COMERCIAL(CA)
             */
            String branchName = "";
            List branchList = em.createNativeQuery("select bankname from bnkadd").getResultList();
            if (!branchList.isEmpty()) {
                Vector vtrBranch = (Vector) branchList.get(0);
                branchName = vtrBranch.get(0) != null ? vtrBranch.get(0).toString() : "";

            }

            String cibil_old_acno = "N";
            List parameterOld = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name ='CIBIL_OLD_ACNO'").getResultList();
            if (!parameterOld.isEmpty()) {
                Vector vect = (Vector) parameterOld.get(0);
                cibil_old_acno = vect.get(0).toString();
            }
            List codeQuery = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name ='CIBIL_IND_CATEGORY'").getResultList();
            String code = "";
            if (!codeQuery.isEmpty()) {
                Vector codeVect = (Vector) codeQuery.get(0);
                code = codeVect.get(0).toString();
            } else {
                throw new ApplicationException("Please Fill define CIBIL_IND_CATEGORY in cbs_parameterinfo Table!!!");
            }
            String acctCat = "";
            if (reportType.equalsIgnoreCase("1")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACNATURE'").getResultList();
                acctCat = " and a.acctcategory in (" + code + ")";
            } else if (reportType.equalsIgnoreCase("2")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_COMERCIAL'").getResultList();
                acctCat = " and a.acctcategory  not in (" + code + ")";
            }
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Acnature data does not exit in required table");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }
            String cibilVerNo = common.getCibilVerNo();
            List acctCodeList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACCTCODE_NOT'").getResultList();
            if (acctCodeList.isEmpty()) {
                acctcode = " and acctcode not in ('') ";
            } else {
                Vector acctCodeVect = (Vector) acctCodeList.get(0);
                if (acctCodeVect.get(0).toString().equalsIgnoreCase("")) {
                    acctcode = " and acctcode not in ('') ";
                } else {
                    acctcode = " and acctcode not in (" + acctCodeVect.get(0).toString() + ") ";
                }
            }
            String oldMemberCode = "", newMemberCode = "", oldMemberName = "", newMemberName = "";
            List oldAcNoList = em.createNativeQuery("select ifnull(cibil_member_code_old,''),ifnull(cibil_member_code_new,''),ifnull(cibil_member_name_old,''),ifnull(cibil_member_name_new,'') from branchmaster").getResultList();
            if (!oldAcNoList.isEmpty()) {
                Vector vtrBranch = (Vector) oldAcNoList.get(0);
                oldMemberCode = vtrBranch.get(0).toString();
                memberCode = vtrBranch.get(1).toString().equalsIgnoreCase("") ? memberCode : vtrBranch.get(1).toString();
                oldMemberName = vtrBranch.get(2).toString();
                newMemberName = vtrBranch.get(3).toString();
            }
//            int isExcessEmi = common.isExceessEmi(toDate);
            CibilComPoJo finalPojo = new CibilComPoJo();
            //HDPOJO
            CibilHDPoJo hdPojo = new CibilHDPoJo();
            hdPojo.setSegmentID("HD");
            hdPojo.setMemberID(memberCode);
            hdPojo.setPreMembId(oldMemberCode);
            hdPojo.setReportGenDt(dmy.format(sdf.parse(todaydate)));
            hdPojo.setReportDt(dmy.format(ymd.parse(toDate)));
            hdPojo.setTypeOfInfo("01");
            hdPojo.setFiller("");
            List<CibilHDPoJo> hd = new ArrayList<>();
            List<CibilBSPoJo> bs = new ArrayList<>();
            List<CibilASPoJo> as = new ArrayList<>();
            List<CibilRSPoJo> rs = new ArrayList<>();
            List<CibilCRPoJo> cr = new ArrayList<>();
            List<CibilGSPoJo> gs = new ArrayList<>();
            List<CibilSSPoJo> ss = new ArrayList<>();
            List<CibilCDPoJo> cd = new ArrayList<>();
            List<CibilTSPoJo> ts = new ArrayList<>();
            hd.add(hdPojo);
            // End Of HD Pojo.It will be reported only once.

            new SimpleDateFormat("yyyyMMdd").format(new Date());
            String query = "select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                    + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                    + " c.PAN_GIRNumber,c.PassportNo, c.VoterIDNo, ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                    + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                    + " c.MailAddressLine1, c.MailAddressLine2, c.MailVillage, c.MailBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode) as MailCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                    + " c.MailPostalCode, c.customerid, c.CustFullName, a.ACNo, a.OperMode, a.OpeningDt, a.ClosingDate, a.AccStatus, "
                    + " a.custid1, a.custid2, a.custid3, a.custid4,"
                    + " c.IssueDate, c.Expirydate, c.DrivingLicenseNo, aa.IdentityNo as aadhar_no , "
                    + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                    + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                    + " ifnull(c.EmailID,''),"
                    + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.PerCityCode) as PerCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                    + " c.PerPostalCode, cast(ifnull(d.ODLimit,0) as decimal), c.CustFullName,ifnull(c.shortname,''),ifnull(c.TINNumber,'') as tin ,"
                    + " ifnull(c.SalesTaxNo,'')as saletax,ifnull(c.VoterIDNo,'') as voterId,ifnull(c.DrivingLicenseNo,'') as dl,"
                    + " ifnull(c.CreditRatingAsOn,'1900-01-01 00:00:00') as creditRatingason,ifnull(c.tan,'') as tan, ifnull(c.cin,'') as cin,"
                    + " ifnull(c.other_identity,'') as otherId,ifnull(c.mobilenumber,'') as mobileNo, ifnull(c.CountryCode,'IN'),ifnull(Sanctionlimitdt,'1900-01-01 00:00:00'),ifnull(a.Relatioship,''),ifnull(FaxNumber,'') "
                    + " ,(select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature,a.acctCategory ,c.OperationalRiskRating , "
                    + " (select ifnull(b.cibil_acct_report_code,'00') AS accounttypetable from cbs_loan_acc_mast_sec clm, cbs_scheme_currency_details b where clm.scheme_code = b.scheme_code and clm.acno = a.acno) as accounttypetable,"
                    + " d.old_ac_no,d.old_ac_type,d.old_branch_code,"
                    + " d.old_ownership_indicator,CustEntityType,bb.Commencement_Date,bd.NPA_CLASSIFICATION,bd.ASSET_CLASS_REASON,bd.mode_of_settlement ,bd.SICKNESS  "
                    + " from accountmaster a, customerid b, loan_appparameter d,cbs_loan_borrower_details bd ,cbs_customer_master_detail c "
                    + " left outer join "
                    + " (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " left join "
                    + " (select CustomerId,Commencement_Date from cbs_cust_misinfo) bb on bb.CustomerId = c.customerid "
                    + " where a.acno = b.acno and b.custid = cast(c.customerid as unsigned) and  a.acno = d.acno and  a.acno = bd.ACC_NO " + acctCat + " and a.accttype "
                    + " in (select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag in ('D','B')) "
                    + " and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + " ((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno";
            List l1 = em.createNativeQuery(query).getResultList();
            /*Main List start */
            for (int i = 0; i < l1.size(); i++) {
                Vector vtrmain = (Vector) l1.get(i);
                title = vtrmain.get(0) != null ? vtrmain.get(0).toString() : "N01";
                borName = vtrmain.get(1) != null ? vtrmain.get(1).toString() : "";
                dob = vtrmain.get(2) != null ? vtrmain.get(2).toString() : "1900-01-01";
                gender = vtrmain.get(3) != null ? vtrmain.get(3).toString() : "";
                borPan = vtrmain.get(4) != null ? vtrmain.get(4).toString() : "";
                if (!borPan.equalsIgnoreCase("") && borPan.length() == 10) {
                    if (ParseFileUtil.isAlphabet(borPan.substring(0, 5)) && ParseFileUtil.isNumeric(borPan.substring(5, 9)) && ParseFileUtil.isAlphabet(borPan.substring(9))) {
                        borPan = borPan;
                    } else {
                        borPan = "";
                    }
                } else {
                    borPan = "";
                }

                passportNo = vtrmain.get(5) != null ? vtrmain.get(5).toString() : "";
                voterIdNo = vtrmain.get(6) != null ? vtrmain.get(6).toString() : "";
                telephoneNumber = vtrmain.get(7) != null ? vtrmain.get(7).toString() : "";
                mailAddressLine1 = vtrmain.get(8) != null ? vtrmain.get(8).toString() : "";
                mailAddressLine2 = vtrmain.get(9) != null ? vtrmain.get(9).toString() : "";
                mailVillage = vtrmain.get(10) != null ? vtrmain.get(10).toString() : "";
                mailBlock = vtrmain.get(11) != null ? vtrmain.get(11).toString() : "";
                mailCityCode = vtrmain.get(12) != null ? vtrmain.get(12).toString() : "";
                mailStateCode = vtrmain.get(13) != null ? vtrmain.get(13).toString() : "";
                mailPostalCode = vtrmain.get(14) != null ? vtrmain.get(14).toString() : "";
                customerid = vtrmain.get(15) != null ? vtrmain.get(15).toString() : "";
                shortName = vtrmain.get(16) != null ? vtrmain.get(16).toString() : "";
                acNo = vtrmain.get(17) != null ? vtrmain.get(17).toString() : "";
                if (acNo.equalsIgnoreCase("041200248001")) {
                    System.out.println(acNo);
                }

                operMode = vtrmain.get(18) != null ? vtrmain.get(18).toString() : "";
                openingDt = vtrmain.get(19) != null ? vtrmain.get(19).toString() : "19000101";
                closingDate = vtrmain.get(20) != null ? vtrmain.get(20).toString() : "";
                accStatus = vtrmain.get(21) != null ? vtrmain.get(21).toString() : "";
                custId1 = vtrmain.get(22) != null ? vtrmain.get(22).toString() : "";
                custId2 = vtrmain.get(23) != null ? vtrmain.get(23).toString() : "";
                custId3 = vtrmain.get(24) != null ? vtrmain.get(24).toString() : "";
                custId4 = vtrmain.get(25) != null ? vtrmain.get(25).toString() : "";

                issueDate = vtrmain.get(26) != null ? vtrmain.get(26).toString() : "1900-01-01";
                expirydate = vtrmain.get(27) != null ? vtrmain.get(27).toString() : "1900-01-01";
                drivingLicenseNo = vtrmain.get(28) != null ? vtrmain.get(28).toString() : "";
                uidNo = vtrmain.get(29) != null ? vtrmain.get(29).toString() : "";
                telResident = vtrmain.get(30) != null ? vtrmain.get(30).toString() : "";
                telOffice = vtrmain.get(31) != null ? vtrmain.get(31).toString() : "";
                emailID = vtrmain.get(32) != null ? vtrmain.get(32).toString() : "";

                perAddressLine1 = vtrmain.get(33) != null ? vtrmain.get(33).toString() : "";
                perAddressLine2 = vtrmain.get(34) != null ? vtrmain.get(34).toString() : "";
                perVillage = vtrmain.get(35) != null ? vtrmain.get(35).toString() : "";
                perBlock = vtrmain.get(36) != null ? vtrmain.get(36).toString() : "";
                perCityCode = vtrmain.get(37) != null ? vtrmain.get(37).toString() : "";
                perStateCode = vtrmain.get(38) != null ? vtrmain.get(38).toString() : "";
                perPostalCode = vtrmain.get(39) != null ? vtrmain.get(39).toString() : "";
                odLimit = vtrmain.get(40) != null ? vtrmain.get(40).toString() : "0";
                custNameAccMast = vtrmain.get(41) != null ? vtrmain.get(41).toString() : "0";
                String shortNm = vtrmain.get(42).toString();
                String tinNo = vtrmain.get(43).toString();
                String sTax = vtrmain.get(44).toString();
                String voterID = vtrmain.get(45).toString();
                String dl = vtrmain.get(46).toString();
                String crRating = vtrmain.get(47).toString() != null ? vtrmain.get(47).toString() : "1900-01-01 00:00:00";
                String creditRatingAsOn = crRating != "" ? dmy.format(ymdhms.parse(crRating)) : "01011900";
                String tanNo = vtrmain.get(48).toString();
                String cin = vtrmain.get(49).toString();
                String otheriId = vtrmain.get(50).toString();
                String mobile = vtrmain.get(51).toString();
                String country = "91", accStatusDt = "";
                String sanctionDt = dmy.format(ymdhms.parse(vtrmain.get(53).toString()));
                relationShip = vtrmain.get(54).toString();
                String faxNo = vtrmain.get(55).toString();
                String acNture = vtrmain.get(56) != null ? vtrmain.get(56).toString() : "";
                borLglConst = vtrmain.get(57) != null ? vtrmain.get(57).toString() : "UN";
                String operationalRiskRating = vtrmain.get(58) != null ? vtrmain.get(58).toString() : "";
                String accounttypetable = vtrmain.get(59) != null ? vtrmain.get(59).toString() : "";

                String OldAcno = vtrmain.get(60) != null ? vtrmain.get(60).toString() : "";
                String oldAcType = vtrmain.get(61) != null ? vtrmain.get(61).toString() : "";
                String oldBranchCode = vtrmain.get(62) != null ? vtrmain.get(62).toString() : "";
                String oldOwnershipIndicator = vtrmain.get(63) != null ? vtrmain.get(63).toString() : "";
                String custEntityType = vtrmain.get(64) != null ? vtrmain.get(64).toString() : "";
                String DateOfIncorporation = vtrmain.get(65) != null ? vtrmain.get(65).toString() : "";
                String suitFiledStatus = vtrmain.get(66) != null ? vtrmain.get(66).toString() : "";
                String wilfulDefaultStatus = vtrmain.get(67) != null ? vtrmain.get(67).toString() : "";
                String writeoffac = vtrmain.get(68) != null ? vtrmain.get(68).toString() : "";
                bussCat = vtrmain.get(69) != null ? vtrmain.get(69).toString() : "";
                if (cibil_old_acno.equalsIgnoreCase("N")) {
                    OldAcno = "";
                    oldAcType = "";
                    oldBranchCode = "";
                    oldOwnershipIndicator = "";
                }
                String noOfEmp = "", telAreCode = "", faxAreaCode = "", relatedType = "", amtRestruct = "", odBuc1 = "", odBuc2 = "", odBuc3 = "", odBuc4 = "", odBuc5 = "";
                String clsOfAct1 = "", clsOfAct2 = "", clsOfAct3 = "", sicCode = "", creditRating = "", authority = "", creditExpDt = "", locationType = "", dunsNo = "";

                String guarantor_entity_name = "", guarantor_prefix = "", guarantor_name = "", guarantor_gender = "",
                        guarantor_comp_regi_no = "", guarantor_doi = "", guarantor_dob = "", guarantor_pan = "", guarantor_votrid = "", guarantor_passport = "",
                        guarantor_dl = "", guarantor_uid = "", guarantor_ration = "", guarantor_cin = "", guarantor_din = "", guarantor_tin = "", guarantor_stax = "", guarantor_otherid = "", guarantor_add1 = "", guarantor_add2 = "", guarantor_add3 = "", guarantor_city = "", guarantor_district = "", guarantor_state = "", guarantor_pincode = "",
                        guarantor_country = "", guarantor_mobile = "", guarantor_tel_area = "", guarantor_tel_no = "", guarantor_fax_area = "", guarantor_fax_no = "", gaurantorCustEntityType = "", gaurantorDateOfIncor;
                creditRating = vtrmain.get(58) != null ? vtrmain.get(58).toString() : "";

                //BSPOJO
                CibilBSPoJo bsPojo = new CibilBSPoJo();
                bsPojo.setAcno(acNo);
                bsPojo.setFlag("1");
                bsPojo.setSegId("BS");
                bsPojo.setMemberBrnCode(acNo.substring(0, 2));
                bsPojo.setPrevMemBrnCode(oldBranchCode);
                bsPojo.setBorName(borName);
                bsPojo.setBorShortName(shortNm);
                bsPojo.setComRegNo("");
                bsPojo.setDateOfIncorp(DateOfIncorporation);
                bsPojo.setPan(borPan);
                bsPojo.setCin(cin);
                bsPojo.setTin(tinNo);
                bsPojo.setServiceTax(sTax);
                bsPojo.setOtherId(otheriId);
                /*
                 * 11 Private Limited
                 * 12 Public Limited
                 * 20 Business Entities Created by Statute
                 * 30 Proprietorship
                 * 40 Partnership
                 * 50 Trust
                 * 55 Hindu Undivided Family
                 * 60 Co-operative Society
                 * 70 Association of Persons
                 * 80 Government
                 * 85 Self Help Group
                 * 99 Not classified
                 * 
                 /* In Our System It is in the ref_rec 325
                 AB	AUTONOMOUS BODIES               99
                 CGD	CENTRAL GOVT DEPT.              80
                 CGP	CENTRAL GOVT PSUS               12
                 COS	CO-OP. SOCIETY                  60
                 FI	FINANCIAL INCLUSION ACCOUNT     99
                 HUF	HUF                             55
                 IND	INDIVIDUAL                      
                 JLG	JOINT LIABILITIES GROUP         70
                 LB	LOCAL BODIES                    20
                 LC	PUBLIC LIMITED COMPANY          12
                 MI	MICROFINANCE INST.              85
                 NGO	NGOS                            50
                 PC	PROPRIETARY CONCERNS            40
                 PCS	PRIVATE/CORPORATE SECTOR        11
                 PF	PARTNERSHIP FIRM                30
                 PL	PRIVATE LIMITED COMPANY         12
                 RS	REGISTERED SOCIETIES            60
                 SB	STATUTORY BODIES                20
                 SGD	STATE GOVT. DEPT.               80
                 SGP	STATE GOVT. PSUS                12
                 TG	TRUST GROUP                     50
                 UN	UNCLASSIFIED                    99*/
                if (borLglConst.equalsIgnoreCase("PL")) {
                    bsPojo.setBorLglConstitution("11");
                } else if (borLglConst.equalsIgnoreCase("LC")) {
                    bsPojo.setBorLglConstitution("12");
                } else if (borLglConst.equalsIgnoreCase("SB")) {
                    bsPojo.setBorLglConstitution("20");
                } else if (borLglConst.equalsIgnoreCase("PC")) {
                    bsPojo.setBorLglConstitution("30");
                } else if (borLglConst.equalsIgnoreCase("PF")) {
                    bsPojo.setBorLglConstitution("40");
                } else if (borLglConst.equalsIgnoreCase("TG")) {
                    bsPojo.setBorLglConstitution("50");
                } else if (borLglConst.equalsIgnoreCase("HUF")) {
                    bsPojo.setBorLglConstitution("55");
                } else if (borLglConst.equalsIgnoreCase("COS")) {
                    bsPojo.setBorLglConstitution("60");
                } else if (borLglConst.equalsIgnoreCase("IND")) {
                    bsPojo.setBorLglConstitution("70");
                } else if (borLglConst.equalsIgnoreCase("CGP") || borLglConst.equalsIgnoreCase("CGD")) {
                    bsPojo.setBorLglConstitution("80");
                } else if (borLglConst.equalsIgnoreCase("TG")) {
                    bsPojo.setBorLglConstitution("85");
                } else {
                    bsPojo.setBorLglConstitution("99");
                }
                /*01 MSME 
                 * 02 SME 
                 * 03 Micro 
                 * 04 Small 
                 * 05 Medium 
                 * 06 Large 
                 * 07 Others */
                if (bussCat.equalsIgnoreCase("KVIENT")) {
                    bsPojo.setBussCat("01");
                } else if (bussCat.equalsIgnoreCase("MICR")) {
                    bsPojo.setBussCat("02");
                } else if (bussCat.equalsIgnoreCase("MICE")) {
                    bsPojo.setBussCat("03");
                } else if (bussCat.equalsIgnoreCase("MNFEN5")) {
                    bsPojo.setBussCat("04");
                } else if (bussCat.equalsIgnoreCase("MEDEME")) {
                    bsPojo.setBussCat("05");
                } else if (bussCat.equalsIgnoreCase("MEDESE")) {
                    bsPojo.setBussCat("06");
                } else {
                    bsPojo.setBussCat("07");
                }
                /*
                 * 01 Manufacturing 
                 * 02 Distribution 
                 * 03 Wholesale 
                 * 04 Trading 
                 * 05 Broking
                 * 06 Service Provider 
                 * 07 Importing 
                 * 08 Exporting 
                 * 09 Agriculture 
                 * 10 Dealers 
                 * 11 Others
                 */
                if (bussType.equalsIgnoreCase("MFG")) {
                    bsPojo.setBussType("01");
                } else if (bussType.equalsIgnoreCase("DIST")) {
                    bsPojo.setBussType("02");
                } else if (bussType.equalsIgnoreCase("WS")) {
                    bsPojo.setBussType("03");
                } else if (bussType.equalsIgnoreCase("TRD")) {
                    bsPojo.setBussType("04");
                } else if (bussType.equalsIgnoreCase("BROK")) {
                    bsPojo.setBussType("05");
                } else if (bussType.equalsIgnoreCase("SERP")) {
                    bsPojo.setBussType("06");
                } else if (bussType.equalsIgnoreCase("IMPRT")) {
                    bsPojo.setBussType("07");
                } else if (bussType.equalsIgnoreCase("EXPRT")) {
                    bsPojo.setBussType("08");
                } else if (bussType.equalsIgnoreCase("AGR")) {
                    bsPojo.setBussType("09");
                } else if (bussType.equalsIgnoreCase("DEALR")) {
                    bsPojo.setBussType("10");
                } else {
                    bsPojo.setBussType("11");
                }
                bsPojo.setClsOfAct1(classOFAct1);
                bsPojo.setClsOfAct2(classOfAct2);
                bsPojo.setClsOfAct3(classOfAct3);
                bsPojo.setSicCode(sicCode);
                bsPojo.setSalesFigure("");
                bsPojo.setFinYear(getFinYear(toDate));
                bsPojo.setNumberOfEmp(noOfEmp);
                bsPojo.setCrRating(creditRating);
                /*
                 * 01 CARE
                 * 02 CRISIL
                 * 03 ICRA
                 * 04 ONICRA
                 * 05 SMERA
                 * 06 Fitch
                 * 07 Others
                 */
                if (authority.equalsIgnoreCase("CARE")) {
                    bsPojo.setAssAuthority("01");
                } else if (authority.equalsIgnoreCase("CRISIL")) {
                    bsPojo.setAssAuthority("02");
                } else if (authority.equalsIgnoreCase("ICRA")) {
                    bsPojo.setAssAuthority("03");
                } else if (authority.equalsIgnoreCase("ONICRA")) {
                    bsPojo.setAssAuthority("04");
                } else if (authority.equalsIgnoreCase("SMERA")) {
                    bsPojo.setAssAuthority("05");
                } else if (authority.equalsIgnoreCase("FITCH")) {
                    bsPojo.setAssAuthority("06");
                } else {
                    bsPojo.setAssAuthority("07");
                }
                bsPojo.setCrRatingAsOn(creditRatingAsOn);
                bsPojo.setCrRatingExpDt("");
                bsPojo.setBorDunsNo("");
                bsPojo.setFiller("");
                int outsBal = 0;
                List currBal = em.createNativeQuery("select ifnull(cast(sum(dramt-Cramt)as decimal(25)),0) from " + common.getTableName(acNture) + " where acno ='" + acNo + "' and dt<='" + toDate + "'").getResultList();
                if (!currBal.isEmpty()) {
                    Vector currVect = (Vector) currBal.get(0);
                    currentBal = currVect.get(0) != null ? currVect.get(0).toString() : "0";
                    outsBal = Integer.parseInt(currentBal);
                }
                if (0 <= outsBal) {
                    bs.add(bsPojo);
                }
                //End Of BS Pojo.

                //CRPOJO
                String lastPaymentDt = "", lastCrAmt = "0";
                int lastCrAmount = 0;
                String periodicity = "", repaymentTenure = "0", emiAmt = "0", highCredit = "", npaAmt = "0", guaranteeCover = "", renewalDt = "", assetClassDt = "";
                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01'),ifnull(cast(cramt as decimal(25)),0) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                if (!l4.isEmpty()) {
                    vtr1 = (Vector) l4.get(0);
                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                    lastCrAmt = vtr1.get(1) != null ? vtr1.get(1).toString() : "0";
                    lastCrAmount = Integer.parseInt(lastCrAmt);
                }
                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                if (!lemi.isEmpty()) {
                    Vector vtrEmi = (Vector) lemi.get(0);
                    repaymentTenure = vtrEmi.get(0).toString();
                    emiAmt = String.valueOf(Integer.parseInt(vtrEmi.get(1).toString()));
                    periodicity = vtrEmi.get(2).toString();
                }
                int period = 0;
                String crType = common.getAcctNature(acNo.substring(2, 4));
                List lb = em.createNativeQuery("select ifnull(LOAN_DURATION,0) from cbs_loan_borrower_details where acc_no =" + acNo + " ").getResultList();
                if (!lb.isEmpty()) {
                    Vector lbvect = (Vector) lb.get(0);

                    period = Integer.parseInt(lbvect.get(0).toString().trim());

                }
                String repaymntFreq = "";
                /*01 Monthly 
                 * 02 Quarterly 
                 * 03 Half yearly 
                 * 04 Annual 
                 * 05 On Demand 
                 * 06 Bullet 
                 * 07 Rolling 
                 * 08 Others */
                if (periodicity.equalsIgnoreCase("M")) {
                    repaymntFreq = "01";
                } else if (periodicity.equalsIgnoreCase("Q")) {
                    repaymntFreq = "02";
                } else if (periodicity.equalsIgnoreCase("H")) {
                    repaymntFreq = "03";
                } else if (periodicity.equalsIgnoreCase("Y")) {
                    repaymntFreq = "04";
                } else {
                    repaymntFreq = "08";
                }
                if (crType.equalsIgnoreCase(CbsConstant.CC_AC)) {
                    creditType = "0100";
                } else if (crType.equalsIgnoreCase(CbsConstant.OD_AC)) {
                    creditType = "0200";
                } else if (crType.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    creditType = "0300";
                } else if (crType.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    if (period < 12) {
                        creditType = "5100";
                    } else if (period > 12 && period <= 36) {
                        creditType = "0410";
                    } else if (period > 36) {
                        creditType = "0420";
                    } else {
                        creditType = "6000";
                    }
                } else {
                    creditType = "6000";
                }
                List npaList = em.createNativeQuery("select ifnull(cast(sum(dramt-cramt)as decimal(25)),0) from npa_recon where acno ='" + acNo + "' and dt<='" + toDate + "'").getResultList();
                if (!npaList.isEmpty()) {
                    Vector npaVect = (Vector) npaList.get(0);
                    npaAmt = npaVect.get(0) != null ? npaVect.get(0).toString() : "0";
                    npaAmt = String.valueOf(Integer.parseInt(npaAmt));
                }
                CibilCRPoJo crPojo = new CibilCRPoJo();
                crPojo.setAcno(acNo);
                crPojo.setFlag("4");
                crPojo.setSegId("CR");
                crPojo.setAccountNumber(acNo);
                crPojo.setPreAcNo(OldAcno);
                crPojo.setSancDt(sanctionDt);
                crPojo.setSancAmt(odLimit);
                crPojo.setCurrencyCode("INR");
                crPojo.setCrType(creditType);
                if (period == 0) {
                    crPojo.setPeriod(String.valueOf(""));
                } else {
                    crPojo.setPeriod(String.valueOf(period));
                }

                crPojo.setRepaymentFreq(repaymntFreq);
                crPojo.setDpLimit(odLimit);
                crPojo.setOutstanding(String.valueOf(outsBal));
                crPojo.setOutstandingRestructured("");
                if (closingDate.equalsIgnoreCase("") || (ymd.parse(closingDate).after(ymd.parse(toDate)))) {
                    if (ymd.parse(CbsUtil.monthAdd(ymd.format(ymdhms.parse(vtrmain.get(53).toString())), period)).after(ymd.parse(toDate))) {
                        crPojo.setClosingDt(dmy.format(ymd.parse(CbsUtil.monthAdd(ymd.format(ymdhms.parse(vtrmain.get(53).toString())), period))));
                    } else {
                        crPojo.setClosingDt("");
                    }
                } else {
                    crPojo.setClosingDt(dmy.format(ymd.parse(closingDate)));
                }
                renewalDt = CbsUtil.dateAdd(CbsUtil.monthAdd(ymd.format(ymdhms.parse(vtrmain.get(53).toString())), period), 1);
                crPojo.setRenewalDt(dmy.format(ymd.parse(renewalDt)));
                if (npaAmt.equalsIgnoreCase("0")) {
                    crPojo.setNpaAmount(new BigDecimal("0"));
                } else {
                    crPojo.setNpaAmount(new BigDecimal(npaAmt));
                }

                String presentStatus = "";
                if (accStatus.equalsIgnoreCase("1")) {
                    presentStatus = "STD";
                    wilfulDefaultStatus = "0";
                } else if (accStatus.equalsIgnoreCase("11")) {
                    presentStatus = "SUB";
                } else if (accStatus.equalsIgnoreCase("12")) {
                    presentStatus = "DOU";
                } else if (accStatus.equalsIgnoreCase("13")) {
                    presentStatus = "LOS";
                }
                if (!accStatus.equalsIgnoreCase("9")) {
                    List assetClsDt = em.createNativeQuery("SELECT ifnull(date_format(MAX(EFFDT),'%Y%m%d'),'19000101') FROM accountstatus a, codebook c "
                            + " WHERE ACNO='" + acNo + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                            + " and effdt <='" + toDate + "'").getResultList();
                    if (!assetClsDt.isEmpty()) {
                        Vector vect = (Vector) assetClsDt.get(0);
                        assetClassDt = vect.get(0).toString();
                    }
                }
                if (accStatus.equalsIgnoreCase("1")) {
                    crPojo.setAssetClass("0001");
                    crPojo.setAssetClassDt(assetClassDt.equalsIgnoreCase("19000101") ? dmy.format(ymd.parse(openingDt)) : dmy.format(ymd.parse(assetClassDt)));
                } else if (accStatus.equalsIgnoreCase("11")) {
                    crPojo.setAssetClass("0002");
                    crPojo.setAssetClassDt(assetClassDt.equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(assetClassDt)));
                } else if (accStatus.equalsIgnoreCase("12")) {
                    crPojo.setAssetClass("0003");
                    crPojo.setAssetClassDt(assetClassDt.equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(assetClassDt)));
                } else if (accStatus.equalsIgnoreCase("13")) {
                    crPojo.setAssetClass("0004");
                    crPojo.setAssetClassDt(assetClassDt.equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(assetClassDt)));
                } else if (accStatus.equalsIgnoreCase("9")) {
                    /*This code was removed as per the Khattri Requirement (Discussion with Alok Sir).*/
                    crPojo.setAssetClass("0001");
                    crPojo.setAssetClassDt(dmy.format(ymd.parse(closingDate)));
                } else {
                    crPojo.setAssetClass("0005");
                    crPojo.setAssetClassDt(assetClassDt.equalsIgnoreCase("19000101") ? dmy.format(ymd.parse(openingDt)) : dmy.format(ymd.parse(assetClassDt)));
                }
                /*0001	standard
                 * 0002	substandard
                 * 0003	doub
                 * 0004	loss
                 * 0005	special
                 * 1001	1day past due
                 * 1002	2 day past due*/
                crPojo.setOdAmt("");
                amtOverdue = "0";
                int overDueAmt = 0;
                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                            + "where acno =  '" + acNo + "' and txnid = "
                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                    if (!sanctionLimitDtList.isEmpty()) {
                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                        odLimit = vist.get(1).toString();
                        crPojo.setSancAmt(odLimit);
                        crPojo.setDpLimit(odLimit);
                    } else {
                        crPojo.setSancAmt(odLimit);
                        crPojo.setDpLimit(odLimit);
                    }
//                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                        double overdue = Double.parseDouble(currentBal) - Double.parseDouble(odLimit);
//                        amtOverdue = String.valueOf(overdue);
//                    } else {
//                        amtOverdue = "0";
//                    }
                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                    OverDueList = OverdueCaList;
                    if (!OverDueList.isEmpty()) {
                        for (int k = 0; k < OverDueList.size(); k++) {
                            OverdueNonEmiResultList vect = OverDueList.get(k);
                            if (vect.getAccountNo().equalsIgnoreCase(acNo)) {
                                overDueAmt = (int) vect.getOverDue();
                                if (vect.getCurrentStatusNoOfDays() > 0 & vect.getCurrentStatusNoOfDays() < 31) {
                                    odBuc1 = String.valueOf(overDueAmt);
                                } else if (vect.getCurrentStatusNoOfDays() > 30 & vect.getCurrentStatusNoOfDays() < 61) {
                                    odBuc2 = String.valueOf(overDueAmt);
                                } else if (vect.getCurrentStatusNoOfDays() > 60 & vect.getCurrentStatusNoOfDays() < 91) {
                                    odBuc3 = String.valueOf(overDueAmt);
                                } else if (vect.getCurrentStatusNoOfDays() > 90 & vect.getCurrentStatusNoOfDays() < 181) {
                                    odBuc4 = String.valueOf(overDueAmt);
                                } else if (vect.getCurrentStatusNoOfDays() > 180 & vect.getCurrentStatusNoOfDays() < 999999) {
                                    odBuc5 = String.valueOf(overDueAmt);
                                }
                            }
                        }
                    }
                    if (String.valueOf(overDueAmt).equalsIgnoreCase("0")) {
                        crPojo.setOdAmt(String.valueOf("0"));
                    } else {
                        crPojo.setOdAmt(String.valueOf(overDueAmt));
                    }

                    crPojo.setOdBucket1(odBuc1);
                    crPojo.setOdBucket2(odBuc2);
                    crPojo.setOdBucket3(odBuc3);
                    crPojo.setOdBucket4(odBuc4);
                    crPojo.setOdBucket5(odBuc5);
                } else {
                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        OverDueList = OverdueDlList;
                        if (!OverDueList.isEmpty()) {
                            for (int k = 0; k < OverDueList.size(); k++) {
                                OverdueNonEmiResultList vect = OverDueList.get(k);
                                if (vect.getAccountNo().equalsIgnoreCase(acNo)) {
                                    overDueAmt = (int) vect.getOverDue();
                                    if (vect.getCurrentStatusNoOfDays() > 0 & vect.getCurrentStatusNoOfDays() < 31) {
                                        odBuc1 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 30 & vect.getCurrentStatusNoOfDays() < 61) {
                                        odBuc2 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 60 & vect.getCurrentStatusNoOfDays() < 91) {
                                        odBuc3 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 90 & vect.getCurrentStatusNoOfDays() < 181) {
                                        odBuc4 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 180 & vect.getCurrentStatusNoOfDays() < 999999) {
                                        odBuc5 = String.valueOf(overDueAmt);
                                    }
                                }
                            }
                        }
                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        OverDueTLList = OverdueTlList;
                        if (!OverDueTLList.isEmpty()) {
                            for (int k = 0; k < OverDueTLList.size(); k++) {
                                OverdueEmiReportPojo vect = OverDueTLList.get(k);
                                if (vect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                    overDueAmt = vect.getAmountOverdue().intValue();
                                    if (vect.getCurrentStatusNoOfDays() > 0 & vect.getCurrentStatusNoOfDays() < 31) {
                                        odBuc1 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 30 & vect.getCurrentStatusNoOfDays() < 61) {
                                        odBuc2 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 60 & vect.getCurrentStatusNoOfDays() < 91) {
                                        odBuc3 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 90 & vect.getCurrentStatusNoOfDays() < 181) {
                                        odBuc4 = String.valueOf(overDueAmt);
                                    } else if (vect.getCurrentStatusNoOfDays() > 180 & vect.getCurrentStatusNoOfDays() < 999999) {
                                        odBuc5 = String.valueOf(overDueAmt);
                                    }
                                }
                            }
                        }
                    }
//                    List excessList = null;
//                    double excess = 0;
//                    if (isExcessEmi == 0) {
//                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                    } else {
//                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                + "(select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                    }
//                    if (!excessList.isEmpty()) {
//                        if (!excessList.isEmpty()) {
//                            Vector ele = (Vector) excessList.get(0);
//                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                excess = Double.parseDouble(ele.get(0).toString());
//                            }
//                        }
//                    }
//                    List l6 = em.createNativeQuery("select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null )  and duedt<'" + toDate + "' ").getResultList();
//                    if (!l6.isEmpty()) {
//                        vtr1 = (Vector) l6.get(0);
//                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                            if (Double.parseDouble(amtOverdue) > Double.parseDouble(currentBal)) {
//                                amtOverdue = currentBal;
//                            } else {
//                                amtOverdue = amtOverdue;
//                            }
//                            double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                            amtOverdue = String.valueOf(overDueAmt);
//                        }
                    crPojo.setOdAmt(String.valueOf(overDueAmt));
//                    }
//                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                    overDueDays = "0";
//                    if (!l7.isEmpty()) {
//                        vtr1 = (Vector) l7.get(0);
//                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                    }
//                    crPojo.setOdBucket1("0");
//                    crPojo.setOdBucket2("0");
//                    crPojo.setOdBucket3("0");
//                    crPojo.setOdBucket4("0");
//                    crPojo.setOdBucket5("0");
//                    if (!overDueDays.equalsIgnoreCase("")) {
//                        if (Integer.parseInt(overDueDays) <= 30) {
//                            crPojo.setOdBucket1(amtOverdue);
//                        } else if (Integer.parseInt(overDueDays) > 30 && Integer.parseInt(overDueDays) <= 60) {
//                            crPojo.setOdBucket2(amtOverdue);
//                        } else if (Integer.parseInt(overDueDays) < 60 && Integer.parseInt(overDueDays) <= 90) {
//                            crPojo.setOdBucket3(amtOverdue);
//                        } else if (Integer.parseInt(overDueDays) < 90 && Integer.parseInt(overDueDays) <= 180) {
//                            crPojo.setOdBucket4(amtOverdue);
//                        } else if (Integer.parseInt(overDueDays) < 180) {
//                            crPojo.setOdBucket5(amtOverdue);
//                        }
//                    } else {
                    crPojo.setOdBucket1(odBuc1);
                    crPojo.setOdBucket2(odBuc2);
                    crPojo.setOdBucket3(odBuc3);
                    crPojo.setOdBucket4(odBuc4);
                    crPojo.setOdBucket5(odBuc5);
//                    }
                }
                if (highCredit.equalsIgnoreCase("0")) {
                    crPojo.setHighCredit("");
                } else {
                    crPojo.setHighCredit(highCredit);
                }
                if (emiAmt.equalsIgnoreCase("0")) {
                    crPojo.setEmiAmt("");
                } else {
                    crPojo.setEmiAmt(emiAmt);
                }
                crPojo.setLastCrAmt(String.valueOf(lastCrAmount));
                if (accStatus.equalsIgnoreCase("9")) {
                    crPojo.setAcStatus("03");
                    crPojo.setAcStatusDt(dmy.format(ymd.parse(closingDate)));
                } else {
                    crPojo.setAcStatus("01");
                    crPojo.setAcStatusDt(dmy.format(ymd.parse(openingDt)));
                }
                if (writeoffac.equalsIgnoreCase("WROFF")) {
                    crPojo.setWriteOffAmt(crPojo.getOutstanding());
                } else {
                    crPojo.setWriteOffAmt("");
                }

                crPojo.setSettledAmt("");
                crPojo.setRestructuredReason("99");
                crPojo.setAssetSecCover("");
                crPojo.setGuaranteeCover(guaranteeCover);
                crPojo.setBnkRemarkCode(bnkRemarkCode);
//                if (wilfulDefaultStatus.equalsIgnoreCase("0")) {
//                    crPojo.setWillFullStatus("");
//                } else {
//                    crPojo.setWillFullStatus(wilfulDefaultStatus);
//                }
//                
                if (wilfulDefaultStatus.equalsIgnoreCase("SST16")) {
                    crPojo.setWillFullStatus("1");
                } else {
                    crPojo.setWillFullStatus("0");
                }

                crPojo.setWillFullDt("");
                if (suitFiledStatus.equalsIgnoreCase("NPA")) {
                    crPojo.setSuitFiledStatus("00");
                } else if (suitFiledStatus.equalsIgnoreCase("SF")) {
                    crPojo.setSuitFiledStatus("01");
                } else if (suitFiledStatus.equalsIgnoreCase("DECRE")) {
                    crPojo.setSuitFiledStatus("03");
                }

                crPojo.setSuitRefNo(suitRefNo);
                if (suitAmt.toString().equalsIgnoreCase("0")) {
                    crPojo.setSuitAmt("");
                } else {
                    crPojo.setSuitAmt(suitAmt.toString());
                }
                crPojo.setSuitDt(suitDt);
                crPojo.setDisputeIdNo("");
                crPojo.setTxnTypeCode("");
                crPojo.setOtherBk("");
                crPojo.setuFCEAmt("");
                crPojo.setuFCEDt("");
                crPojo.setFiller("");
                if (0 <= outsBal) {
                    cr.add(crPojo);
                }
                // End OF CR Pojo.This can be multiple Times for an Account.

                //ASPOJO
                CibilASPoJo asPojo = new CibilASPoJo();
                asPojo.setAcno(acNo);
                asPojo.setSegId("AS");
                asPojo.setFlag("2");
                asPojo.setBorOfycLocType("01");
                asPojo.setBorOfycDUNNo("999999999");
                address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                        !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                        !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                        !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                        !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                if (address.contains(" ")) {
                    space = " ";
                } else if (address.contains(",")) {
                    space = ",";
                } else {
                    space = "-";
                }
                if (address.length() > 40) {
                    if (!address.substring(40).contains(space)) {
                        StringBuilder sb = new StringBuilder();
                        String[] pairs = address.split(",");
                        sb.append(pairs[0]);
                        for (int t = 1; t < pairs.length; ++t) {
                            String pair = pairs[t];
                            sb.append(pair.indexOf(',') < 0 ? ", " : ",");
                            sb.append(pair);
                        }
                        address = sb.toString();
                    }
                }
                asPojo.setBorAdd1(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                address = (address.length() > 200) ? address.substring(address.substring(0, 200).lastIndexOf(space) + 1) : "";
                asPojo.setBorAdd2(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                asPojo.setBorAdd3(address.length() > 0 && address.length() <= 200 ? address.substring(0) : address.length() > 200 ? address.substring(0, 200).substring(0, address.substring(0, 200).lastIndexOf(space)) : "");
                asPojo.setBorCity(mailCityCode);
                asPojo.setBorDistrict(mailCityCode);
                asPojo.setBorPinCode(borPin);
                asPojo.setBorTelAreaCode(telAreCode);
                asPojo.setBorTelNo(telephoneNumber);
                asPojo.setBorFaxAreaCode(faxAreaCode);
                asPojo.setBorFaxNo(faxNo);
                asPojo.setBorState(mailStateCode);
                asPojo.setBorCountry("079");
                asPojo.setBorMob(mobile);
                asPojo.setBorTelAreaCode(borTelAreaCode);
                asPojo.setBorTelNo(borTelNo);
                asPojo.setBorFaxAreaCode(borFaxArea);
                asPojo.setBorFaxNo(borFaxNo);
                asPojo.setBorFiller("");
                if (0 <= outsBal) {
                    as.add(asPojo);
                }
                //End OF ASPojo.This can be multiple Times for an Account.

                //CDPOJO
                List cdList = em.createNativeQuery("select acno,DT,cast(INST_AMT as unsigned),INST_NO,1 as noOFDisH,INST_DT from  cts_clg_in_entry_history where ACNO= '" + acNo + "' and status ='4' and userdetails in ('INSUFFICIENT FUNDS','EXCEEDS ARRANGEMENTS','1','2') and dt between '" + fromDate + "' and '" + toDate + "' ;").getResultList();
                //This Query is for Only Insufficient Funds. For any Other Reason change in the Query.
                if (!cdList.isEmpty()) {
                    for (int c = 0; c < cdList.size(); c++) {
                        Vector cdVect = (Vector) cdList.get(c);
                        CibilCDPoJo cdPojo = new CibilCDPoJo();
                        cdPojo.setAcno(cdVect.get(0).toString());
                        cdPojo.setFlag("7");
                        if (cibilParameter.equalsIgnoreCase("CRIF")) {
                            cdPojo.setSegId("DS");
                        } else {
                            cdPojo.setSegId("CD");
                        }
                        cdPojo.setDateOfDishonour(dmy.format(ymdhms.parse(cdVect.get(1).toString())));
                        cdPojo.setAmt(cdVect.get(2).toString());
                        cdPojo.setInstNo(cdVect.get(3).toString());
                        cdPojo.setNoOfTimeDisH(cdVect.get(4).toString());
                        cdPojo.setChqIssueDt(dmy.format(ymdhms.parse(cdVect.get(5).toString())));
                        cdPojo.setReasonForDisH("01");//This is the code for the "Insufficient Funds" defined in the Circular.
                        cdPojo.setFiller("");
                        if (0 <= outsBal) {
                            cd.add(cdPojo);
                        }
                    }
                }
                //End Of CD Pojo.This can be multiple Times for an Account.
                //SSPOJO
                List loanSecurity = em.createNativeQuery("select ifnull(lienvalue,0),ifnull(security,''),ifnull(SecurityType,''),ifnull(Issuedate,'') from loansecurity where acno ='" + acNo + "' "
                        + " and  status ='Active' and Entrydate<='" + toDate + "'  "
                        + " union  "
                        + " select lienvalue,ifnull(security,''),ifnull(SecurityType,''),ifnull(Issuedate,'') from loansecurity where acno ='" + acNo + "' "
                        + " and status ='EXPIRED' and Entrydate<='" + toDate + "' and ExpiryDate >'" + toDate + "' ").getResultList();
                if (!loanSecurity.isEmpty()) {
                    for (int ls = 0; ls < loanSecurity.size(); ls++) {
                        Vector vtr = (Vector) loanSecurity.get(ls);
                        String lienvalue = vtr.get(0).toString();
                        CibilSSPoJo ssPojo = new CibilSSPoJo();
                        ssPojo.setAcno(acNo);
                        ssPojo.setFlag("6");
                        ssPojo.setSegId("SS");
                        ssPojo.setValOfSec(new BigDecimal(lienvalue));
                        ssPojo.setCurrencyType("INR");
                        if (vtr.get(2).toString().equalsIgnoreCase("P")) {
                            ssPojo.setSecClassification("001");
                            ssPojo.setTypeOfSec("01");
                        } else {
                            ssPojo.setTypeOfSec("21");
                            ssPojo.setSecClassification("009");
                        }
                        ssPojo.setDateOfValue(vtr.get(3).toString().equalsIgnoreCase("") ? "" : dmy.format(y_m_d.parse(vtr.get(3).toString())));
                        ssPojo.setFiller("");
                        if (0 <= outsBal) {
                            ss.add(ssPojo);
                        }
                    }
                }
                // End Of the SS Pojo.This can be multiple times for an Account.
                custIdList = new ArrayList();
                if (!custId1.equalsIgnoreCase("")) {
                    custIdList.add(custId1);
                }
                if (!custId2.equalsIgnoreCase("")) {
                    custIdList.add(custId2);
                }
                if (!custId3.equalsIgnoreCase("")) {
                    custIdList.add(custId3);
                }
                if (!custId4.equalsIgnoreCase("")) {
                    custIdList.add(custId4);
                }
                String jtTitle = "", jtCustFullName = "", jtGender = "", jtDob = "", jtPAN = "", jtVoterID = "", jtPasspost = "", jtDlNo = "", jtAadhar = "", jtCin = "", jtTin = "", jtSTax = "",
                        jtOtherID = "", jtcity = "", jtPinCode = "", jtCountry = "91", jtMobile = "",
                        jtFaxArea = "", jtFaxNo = "", jtDin = "", jtRationCard = "", jtPerControl = "", jtBussEntity = "", jtBussCat = "", jtBussType = "", jtRegNo = "";
                String jtDoi = "", jtSalesFigure = "", jtFinYear = "", finYear = "";

                List l8 = em.createNativeQuery("select phno,address,acno,name,ifnull(CUST_FLAG,'N'),ifnull(GAR_ACNO,''),ifnull(GAR_CUSTID,'') from loan_guarantordetails where acno ='" + acNo + "'").getResultList();
                if (!l8.isEmpty()) { /*If Guarantor Exists.*/

                    for (int k = 0; k < l8.size(); k++) {
                        Vector vtr = (Vector) l8.get(k);
                        String garCustFlag = vtr.get(4).toString();
                        String garCustAcNo = vtr.get(5).toString();
                        String garCustId = vtr.get(6).toString();
                        List l3 = custIdListForGuarantor(garCustId);
                        //GSPOJO                        
                        CibilGSPoJo gsPojo = new CibilGSPoJo();

                        if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                            /* If Custid Exist in Guarantor */
                            if (!l3.isEmpty()) {
                                vtr = (Vector) l3.get(0);
//                                Vector gsVect = (Vector) l3.get(0);
                                guarantor_prefix = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                guarantor_name = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                guarantor_dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                guarantor_gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                guarantor_pan = vtr.get(4) != null ? vtr.get(4).toString() : "";
                                if (!guarantor_pan.equalsIgnoreCase("") && guarantor_pan.length() == 10) {
                                    if (ParseFileUtil.isAlphabet(guarantor_pan.substring(0, 5)) && ParseFileUtil.isNumeric(guarantor_pan.substring(5, 9)) && ParseFileUtil.isAlphabet(guarantor_pan.substring(9))) {
                                        guarantor_pan = guarantor_pan;
                                    } else {
                                        guarantor_pan = "";
                                    }
                                } else {
                                    guarantor_pan = "";
                                }

                                guarantor_passport = vtr.get(5) != null ? vtr.get(5).toString() : "";
                                guarantor_votrid = vtr.get(6) != null ? vtr.get(6).toString() : "";
                                guarantor_tel_no = vtr.get(7) != null ? vtr.get(7).toString() : "";
                                guarantor_add1 = vtr.get(8) != null ? vtr.get(8).toString() : "";
                                guarantor_add2 = vtr.get(9) != null ? vtr.get(9).toString() : "";
                                guarantor_add3 = vtr.get(10) != null ? vtr.get(10).toString() : "".concat(vtr.get(11) != null ? vtr.get(11).toString() : "");
                                guarantor_city = vtr.get(12) != null ? vtr.get(12).toString() : "";
                                guarantor_state = vtr.get(13) != null ? vtr.get(13).toString() : "";
                                guarantor_pincode = vtr.get(14) != null ? vtr.get(14).toString() : "";
                                customerid = vtr.get(15) != null ? vtr.get(15).toString() : "";
                                shortName = vtr.get(16) != null ? vtr.get(16).toString() : "";
                                guarantor_dl = vtr.get(19) != null ? vtr.get(19).toString() : "";
                                guarantor_uid = vtr.get(20) != null ? vtr.get(20).toString() : "";
                                telResident = vtr.get(21) != null ? vtr.get(21).toString() : "";
                                gaurantorCustEntityType = vtr.get(31) != null ? vtr.get(31).toString() : "";
                                gaurantorDateOfIncor = vtr.get(32) != null ? vtr.get(32).toString() : "";

                                //RSPOJO
                                gsPojo = new CibilGSPoJo();
                                gsPojo.setAcno(acNo);
                                gsPojo.setFlag("5");
                                gsPojo.setSegId("GS");
                                gsPojo.setGuarantorDUNSNo("999999999");
                                gsPojo.setGuarantorType(String.valueOf(Integer.parseInt(gaurantorCustEntityType)));
                                if (gaurantorCustEntityType.equalsIgnoreCase("02")) {
                                    gsPojo.setBussCat("");
                                    gsPojo.setBussType("");
                                } else {
                                    gsPojo.setBussCat("07");
                                    gsPojo.setBussType("11");
                                }
                                gsPojo.setGuarEntityName("");
                                gsPojo.setGuarNamePrefix(guarantor_prefix);
                                gsPojo.setGuarFullName(guarantor_name);
                                gsPojo.setGuarGender(guarantor_gender);
                                gsPojo.setGuarCompRegNo("");
                                gsPojo.setGuarDateOfIncorp(gaurantorDateOfIncor);
                                gsPojo.setGuarDOB(guarantor_dob);
                                gsPojo.setGuarPan(guarantor_pan);
                                gsPojo.setGuarVoterID(guarantor_votrid);
                                gsPojo.setGuarPassport(guarantor_passport);
                                gsPojo.setGuarDL(guarantor_dl);
                                gsPojo.setGuarUID(guarantor_uid);
                                gsPojo.setGuarRationCard("");
                                gsPojo.setGuarCIN("");
                                gsPojo.setGuarDIN("");
                                gsPojo.setGuarTIN("");
                                gsPojo.setGuarServiceTAx("");
                                gsPojo.setGuarOtherID("");
                                gsPojo.setGuarAdd1(guarantor_add1);
                                gsPojo.setGuarAdd2(guarantor_add2);
                                gsPojo.setGuarAdd3(guarantor_add3);
                                gsPojo.setGuarCity(guarantor_city);
                                gsPojo.setGuarDistrict(guarantor_city);
                                gsPojo.setGuarState(guarantor_state);
                                gsPojo.setGuarPinCode(guarantor_pincode);
                                gsPojo.setGuarCountry("079");
                                gsPojo.setGuarMob("");
                                gsPojo.setGuarTelAreaCode("");
                                gsPojo.setGuarTelNo(telResident);
                                gsPojo.setGuarFaxAreaCode("");
                                gsPojo.setGuarFaxNo("");
                                gsPojo.setGuarFiller("");
                                if (0 <= outsBal) {
                                    gs.add(gsPojo);
                                }
                            }
                        } else {
                            //RSPOJO
                            gsPojo = new CibilGSPoJo();
                            gsPojo.setAcno(acNo);
                            gsPojo.setFlag("5");
                            gsPojo.setSegId("GS");
                            gsPojo.setGuarantorDUNSNo("999999999");
                            gsPojo.setGuarantorType("2");
                            gsPojo.setBussCat("07");
                            gsPojo.setBussType("11");
                            gsPojo.setGuarEntityName("");
                            gsPojo.setGuarNamePrefix("");
                            gsPojo.setGuarFullName("");
                            gsPojo.setGuarGender("");
                            gsPojo.setGuarCompRegNo("");
                            gsPojo.setGuarDateOfIncorp("");
                            gsPojo.setGuarDOB("");
                            gsPojo.setGuarPan("");
                            gsPojo.setGuarVoterID("");
                            gsPojo.setGuarPassport("");
                            gsPojo.setGuarDL("");
                            gsPojo.setGuarUID("");
                            gsPojo.setGuarRationCard("");
                            gsPojo.setGuarCIN("");
                            gsPojo.setGuarDIN("");
                            gsPojo.setGuarTIN("");
                            gsPojo.setGuarServiceTAx("");
                            gsPojo.setGuarOtherID("");
                            gsPojo.setGuarAdd1("");
                            gsPojo.setGuarAdd2("");
                            gsPojo.setGuarAdd3("");
                            gsPojo.setGuarCity("");
                            gsPojo.setGuarDistrict("");
                            gsPojo.setGuarState("2");
                            gsPojo.setGuarPinCode("");
                            gsPojo.setGuarCountry("079");
                            gsPojo.setGuarMob("");
                            gsPojo.setGuarTelAreaCode("");
                            gsPojo.setGuarTelNo("");
                            gsPojo.setGuarFaxAreaCode("");
                            gsPojo.setGuarFaxNo("");
                            gsPojo.setGuarFiller("");
                            if (0 <= outsBal) {
                                gs.add(gsPojo);
                            }
                        }
                    }
                }
                // End Of GS Pojo.
                //RS Pojo
                if (custIdList.size() > 0) {
                    for (int c = 0; c < custIdList.size(); c++) {
                        String jtCustId = "";
                        if (custIdList.size() == 1) {
                            jtCustId = custIdList.get(0).toString();
                        } else {
                            String custId = (String) custIdList.get(c);
                            jtCustId = custId.toString();
                        }
                        List jtCustdetail = em.createNativeQuery("select ifnull(c.title,''),ifnull(c.CustFullName,''),case c.gender when 'M' then '01'  "
                                + " when 'F' then '02' when 'O' then '3' when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
                                + " ifnull(c.PAN_GIRNumber,''),ifnull(c.VoterIDNo,''),ifnull(c.PassportNo,''),ifnull(c.DrivingLicenseNo,''),ifnull(aa.IdentityNo,''),ifnull(c.cin,''),ifnull(c.TINNumber,''),ifnull(c.SalesTaxNo,''),"
                                + " ifnull(c.other_identity,''),ifnull(c.MailAddressLine1,''), ifnull(c.MailAddressLine2,'') ,"
                                + " ifnull((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode),'') as MailCityCode, "
                                + " ifnull((select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode),'') as mailstatecode, "
                                + " ifnull(c.MailPostalCode,''),ifnull(c.MailCountryCode,''),ifnull(c.MailPhoneNumber,''),ifnull(c.MailTelexNumber,''),ifnull(c.MailFaxNumber,'') "
                                + " from cbs_customer_master_detail  c "
                                + " left outer join "
                                + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                                + " union "
                                + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                                + "where c.customerid='" + jtCustId + "';").getResultList();
                        List jtDetail = em.createNativeQuery("select ifnull(Commencement_Date,''),ifnull(Mis_Tin,''),ifnull(SalesTurnover,'') from cbs_cust_misinfo where customerid = '" + jtCustId + "'").getResultList();
                        if (!jtDetail.isEmpty()) {
                            Vector vect1 = (Vector) jtDetail.get(0);
                            jtDoi = vect1.get(0).toString();
                            jtSalesFigure = vect1.get(2).toString();
                        }
                        if (!jtCustdetail.isEmpty()) {
                            Vector jtVect = (Vector) jtCustdetail.get(0);
                            jtTitle = jtVect.get(0).toString();
                            jtCustFullName = jtVect.get(1).toString();
                            jtGender = jtVect.get(2).toString();
                            jtDob = jtVect.get(3).toString();
                            jtPAN = jtVect.get(4).toString();
                            if (!jtPAN.equalsIgnoreCase("") && jtPAN.length() == 10) {
                                if (ParseFileUtil.isAlphabet(jtPAN.substring(0, 5)) && ParseFileUtil.isNumeric(jtPAN.substring(5, 9)) && ParseFileUtil.isAlphabet(jtPAN.substring(9))) {
                                    jtPAN = jtPAN;
                                } else {
                                    jtPAN = "";
                                }
                            } else {
                                jtPAN = "";
                            }


                            jtVoterID = jtVect.get(5).toString();
                            jtPasspost = jtVect.get(6).toString();
                            jtDlNo = jtVect.get(7).toString();
                            jtAadhar = jtVect.get(8).toString();
                            jtCin = jtVect.get(9).toString();
                            jtTin = jtVect.get(10).toString();
                            jtSTax = jtVect.get(11).toString();
                            jtOtherID = jtVect.get(12).toString();
                            jtAdd1 = jtVect.get(13).toString();
                            jtAdd2 = jtVect.get(14).toString();
                            jtcity = jtVect.get(15).toString();
                            jtState = jtVect.get(16).toString();
                            jtPinCode = jtVect.get(17).toString();
//                                    jtCountry = jtVect.get(18).toString();
                            jtMobile = jtVect.get(19).toString();
                            jtTelNo = jtVect.get(20).toString();
                            jtFaxNo = jtVect.get(21).toString();
                        }

                        //RSPOJO

                        /* 
                         10 Shareholder                 
                         11 Holding Company
                         12 Subsidiary Company
                         20 Proprietor                  PC	PROPRIETARY CONCERNS
                         30 Partner                     PF	PARTNERSHIP FIRM 
                         40 Trustee                     TG	TRUST GROUP 
                         51 Promoter Director
                         52 Nominee Director
                         53 Independent Director
                         54 Director - Since Resigned
                         55 Individual Member of SHG
                         56 Other Director
                         60 Others                       UN	UNCLASSIFIED
                         */
                        String relation = "60";
                        if (borLglConst.equalsIgnoreCase("PC")) {
                            relation = "20";
                        } else if (borLglConst.equalsIgnoreCase("PF")) {
                            relation = "30";
                        } else if (borLglConst.equalsIgnoreCase("TG")) {
                            relation = "40";
                        }

                        CibilRSPoJo rsPojo;
                        rsPojo = new CibilRSPoJo();
                        rsPojo.setAcno(acNo);
                        rsPojo.setFlag("3");
                        rsPojo.setSegId("RS");
                        rsPojo.setRelationDunNo("999999999");
                        rsPojo.setRelatedType(String.valueOf(Integer.parseInt(custEntityType)));
                        rsPojo.setRelationShip(relation);
                        if (rsPojo.getRelatedType().equalsIgnoreCase("1")) {
                            rsPojo.setBusinessEntityName(jtCustFullName);
                        } else {
                            rsPojo.setBusinessEntityName(jtBussEntity);
                        }
                        /*01 MSME 
                         * 02 SME 
                         * 03 Micro 
                         * 04 Small 
                         * 05 Medium 
                         * 06 Large 
                         * 07 Others */
                        if (bussCat.equalsIgnoreCase("KVIENT")) {
                            rsPojo.setBussCat("01");
                        } else if (bussCat.equalsIgnoreCase("MICR")) {
                            rsPojo.setBussCat("02");
                        } else if (bussCat.equalsIgnoreCase("MICE")) {
                            rsPojo.setBussCat("03");
                        } else if (bussCat.equalsIgnoreCase("MNFEN5")) {
                            rsPojo.setBussCat("04");
                        } else if (bussCat.equalsIgnoreCase("MEDEME")) {
                            rsPojo.setBussCat("05");
                        } else if (bussCat.equalsIgnoreCase("MEDESE")) {
                            rsPojo.setBussCat("06");
                        } else {
                            rsPojo.setBussCat("07");
                        }
                        if (custEntityType.equalsIgnoreCase("02")) {
                            rsPojo.setBussCat("");
                        }
                        /*
                         * 01 Manufacturing 
                         * 02 Distribution 
                         * 03 Wholesale 
                         * 04 Trading 
                         * 05 Broking
                         * 06 Service Provider 
                         * 07 Importing 
                         * 08 Exporting 
                         * 09 Agriculture 
                         * 10 Dealers 
                         * 11 Others
                         */
                        if (bussType.equalsIgnoreCase("MFG")) {
                            rsPojo.setBussType("01");
                        } else if (bussType.equalsIgnoreCase("DIST")) {
                            rsPojo.setBussType("02");
                        } else if (bussType.equalsIgnoreCase("WS")) {
                            rsPojo.setBussType("03");
                        } else if (bussType.equalsIgnoreCase("TRD")) {
                            rsPojo.setBussType("04");
                        } else if (bussType.equalsIgnoreCase("BROK")) {
                            rsPojo.setBussType("05");
                        } else if (bussType.equalsIgnoreCase("SERP")) {
                            rsPojo.setBussType("06");
                        } else if (bussType.equalsIgnoreCase("IMPRT")) {
                            rsPojo.setBussType("07");
                        } else if (bussType.equalsIgnoreCase("EXPRT")) {
                            rsPojo.setBussType("08");
                        } else if (bussType.equalsIgnoreCase("AGR")) {
                            rsPojo.setBussType("09");
                        } else if (bussType.equalsIgnoreCase("DEALR")) {
                            rsPojo.setBussType("10");
                        } else {
                            rsPojo.setBussType("11");
                        }
                        if (custEntityType.equalsIgnoreCase("02")) {
                            rsPojo.setBussType("");
                        }
                        rsPojo.setPrefix(jtPreFix);
                        rsPojo.setFullName(jtCustFullName);
                        rsPojo.setGender(jtGender);
                        rsPojo.setCompRegNo("");
                        rsPojo.setDateOfIncorp(jtDoi);
                        rsPojo.setDob(dmy.format(ymdhms.parse(jtDob)));
                        rsPojo.setPan(jtPAN);
                        rsPojo.setVoterId(jtVoterID);
                        rsPojo.setPassportNo(jtPasspost);
                        rsPojo.setDlNo(jtDlNo);
                        rsPojo.setUid(jtAadhar);
                        rsPojo.setRationCardNo(jtRationCard);
                        rsPojo.setCin(jtCin);
                        rsPojo.setDin(jtDin);
                        rsPojo.setTin(jtTin);
                        rsPojo.setServiceTax(jtSTax);
                        rsPojo.setOtherId(jtOtherID);
                        rsPojo.setPercOfCntrl("");
                        rsPojo.setAdd1(jtAdd1);
                        rsPojo.setAdd2(jtAdd2);
                        rsPojo.setAdd3(jtAdd3);
                        rsPojo.setCity(jtcity);
                        rsPojo.setDistrict(jtcity);
                        rsPojo.setState(jtState);
                        rsPojo.setPinCode(jtPinCode);
                        rsPojo.setCountry("079");
                        rsPojo.setMobNo(jtMobile);
                        rsPojo.setTelNo(jtTelNo);
                        rsPojo.setTelAreaCode(jtTelArea);
                        rsPojo.setFaxNo(jtFaxNo);
                        rsPojo.setFaxAreaCode(jtFaxArea);
                        rsPojo.setFiller("");
                        if (0 <= outsBal) {
                            rs.add(rsPojo);
                        }
                    }
                }
            }/*Main List End*/
            //TSPOJO
            CibilTSPoJo tsPojo = new CibilTSPoJo();
            tsPojo.setSegId("TS");
            tsPojo.setNoOfBor(String.valueOf(bs.size()));
            tsPojo.setNoOfCrFacility(String.valueOf(cr.size()));
            tsPojo.setFiller("");
            ts.add(tsPojo);
            if (bs.isEmpty()) {
                CibilBSPoJo bsPojo = new CibilBSPoJo();
                bsPojo.setAcno("");
                bs.add(bsPojo);
            }
            if (as.isEmpty()) {
                CibilASPoJo asPojo = new CibilASPoJo();
                asPojo.setAcno("");
                as.add(asPojo);
            }
            if (rs.isEmpty()) {
                CibilRSPoJo rsPojo = new CibilRSPoJo();
                rsPojo.setAcno("");
                rs.add(rsPojo);
            }
            if (cr.isEmpty()) {
                CibilCRPoJo crPojo = new CibilCRPoJo();
                crPojo.setAcno("");
                cr.add(crPojo);
            }
            if (gs.isEmpty()) {
                CibilGSPoJo gsPojo = new CibilGSPoJo();
                gsPojo = new CibilGSPoJo();
                gsPojo.setAcno("");
                gs.add(gsPojo);
            }
            if (ss.isEmpty()) {
                CibilSSPoJo ssPojo = new CibilSSPoJo();
                ssPojo.setAcno("");
                ss.add(ssPojo);
            }
            if (cd.isEmpty()) {
                CibilCDPoJo cdPojo = new CibilCDPoJo();
                cdPojo.setAcno("");
                cd.add(cdPojo);
            }
            finalPojo.setHdPojo(hd);
            finalPojo.setAsPojo(as);
            finalPojo.setBsPojo(bs);
            finalPojo.setCdPojo(cd);
            finalPojo.setCrPojo(cr);
            finalPojo.setGsPojo(gs);
            finalPojo.setRsPojo(rs);
            finalPojo.setSsPojo(ss);
            finalPojo.setTsPojo(ts);
            //End of TS Pojo. It will be reported only Once.
//            return experionList;
            return finalPojo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
}
