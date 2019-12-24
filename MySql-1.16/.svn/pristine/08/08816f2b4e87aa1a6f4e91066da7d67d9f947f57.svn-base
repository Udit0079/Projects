/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.EquifaxComercialPoJo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SAMY
 */
@Stateless(mappedName = "EquifaxReportFacade")
public class EquifaxReportFacade implements EquifaxReportFacadeRemote {
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    DDSReportFacadeRemote ddsRemote;
    @EJB
    OverDueReportFacadeRemote overDueRemote;
    @EJB
    LoanReportFacadeRemote loanRemote;
    public List custIdListForGuarantor(String custid) throws ApplicationException {
        try {
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
                    + " where c.customerid = '" + custid + "'").getResultList();
            return l3;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException();
        }
    }
    
    public List<EquifaxComercialPoJo> comercialEquifaxReport(String memberCode, String fromDate, String toDate, String todaydate, String reportType) throws ApplicationException {
        List<EquifaxComercialPoJo> comercialDataList = new ArrayList<EquifaxComercialPoJo>();
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            List<EquifaxComercialPoJo> experionList = new ArrayList<EquifaxComercialPoJo>();
            Vector vtr1 = null;
            String lastPaymentDt = null, branchName = null, accountTypeTable = null, assetClass = "";
            String sanctionAmt = null, currentBal = "0", amtOverdue = null, writtrnOff = null;
            String overDueDays = "";
            EquifaxComercialPoJo pojo;
            List custIdList;
            boolean flag = false;
            List custNameList = new ArrayList();
            List<OverdueEmiReportPojo> OverdueTlList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", 1, 5000, toDate, "0A", "A", false,"","N");
            List<OverdueNonEmiResultList> OverdueDlList = overDueRemote.getOverDueNonEmi("DL", "ALL", toDate, "0A","N");
            List<OverdueNonEmiResultList> OverdueCaList = overDueRemote.getOverDueNonEmi("CA", "ALL", toDate, "0A","N");
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
            if (reportType.equalsIgnoreCase("1")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACNATURE'").getResultList();
            } else if (reportType.equalsIgnoreCase("2")) {
                acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_COMERCIAL'").getResultList();
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
            int isExcessEmi = common.isExceessEmi(toDate);
            new SimpleDateFormat("yyyyMMdd").format(new Date());
            String query = "select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                    + " when 'F' then '1'  when 'O' then '3' when '0' then '' when '' then '' when 'NULL' then '' end as gender, "
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
                    + " ifnull(c.CreditRatingAsOn,'') as creditRatingason,ifnull(c.tan,'') as tan, ifnull(c.cin,'') as cin,"
                    + " ifnull(c.other_identity,'') as otherId,ifnull(c.mobilenumber,'') as mobileNo, ifnull(c.CountryCode,'IN'),ifnull(Sanctionlimitdt,'1900-01-01 00:00:00'),ifnull(a.Relatioship,''),ifnull(FaxNumber,'') "
                    + " ,(select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature "
                    + " from accountmaster a, customerid b, loan_appparameter d , cbs_customer_master_detail c "
                    + " left outer join "
                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " where a.acno = b.acno and b.custid = cast(c.customerid as unsigned) and  a.acno = d.acno and a.accttype "
                    + " in ( select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag in ('D','B'))  "
                    + " and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + " ((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno";
            List l1 = em.createNativeQuery(query).getResultList();
            
            /*Main List start */
            for (int i = 0; i < l1.size(); i++) {
                Vector vtrmain = (Vector) l1.get(i);
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
                String shortNm = vtrmain.get(42).toString();
                String tinNo = vtrmain.get(43).toString();
                String sTax = vtrmain.get(44).toString();
                String voterID = vtrmain.get(45).toString();
                String dl = vtrmain.get(46).toString();
               // String crRating = vtrmain.get(47).toString() != null ? vtrmain.get(47).toString() : "19000101";
               // String creditRatingAsOn = crRating == "" ? dmy.format(ymdhms.parse(crRating)) : "";
                String crRating = vtrmain.get(47).toString() != null ? vtrmain.get(47).toString() : "1900-01-01 00:00:00";
                String creditRatingAsOn = crRating != "" ? dmy.format(ymdhms.parse(crRating)) : "01011900";
                
                String tanNo = vtrmain.get(48).toString();
                String cin = vtrmain.get(49).toString();
                String otheriId = vtrmain.get(50).toString();
                String mobile = vtrmain.get(51).toString();
                String country = "91", accStatusDt = "";
//                if (vtrmain.get(52).toString().equalsIgnoreCase("")) {
//                    country = "91";
//                } else {
//                    country = vtrmain.get(52).toString();
//                }
                String sanctionDt = dmy.format(ymdhms.parse(vtrmain.get(53).toString()));
//                String temp = dmy.format(ymdhms.parse(vtrmain.get(53).toString()));

                String relationShip = vtrmain.get(54).toString();
                String faxNo = vtrmain.get(55).toString();
                String acNture = vtrmain.get(56) != null ? vtrmain.get(56).toString() : "";
                String noOfEmp = "", telAreCode = "", faxAreaCode = "", relatedType = "", creditType = "", dp = "", amtRestruct = "", odBuc1 = "", odBuc2 = "", odBuc3 = "", odBuc4 = "", odBuc5 = "";
                String clsOfAct1 = "", clsOfAct2 = "", clsOfAct3 = "", sicCode = "", creditRating = "", authority = "", creditExpDt = "", locationType = "", dunsNo = "";
                
                pojo = new EquifaxComercialPoJo();
                custIdList = new ArrayList();
                String periodicity = "", repaymentTenure = "0", emiAmt = "0", highCredit = "0", lastCrAmt = "0", npaAmt = "0", guaranteeCover = "", renewalDt = "", assetClassDt = "";
                List npaList = em.createNativeQuery("select sum(cramt-dramt) from npa_recon where acno ='" + acNo + "' and dt<='" + toDate + "'").getResultList();
                if (!npaList.isEmpty()) {
                    Vector npaVect = (Vector) npaList.get(0);
                    npaAmt = npaVect.get(0) != null ? npaVect.get(0).toString() : "";
                }
                flag = true;
                if (title.equalsIgnoreCase("M/S") || title.equalsIgnoreCase("M/s")) {
                    if (Integer.parseInt(operMode) == 8) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                } else {
                    if (operMode.equalsIgnoreCase("1") || operMode.equalsIgnoreCase("7") || operMode.equalsIgnoreCase("9") || operMode.equalsIgnoreCase("10")) {
                        flag = true;
                    } else if (operMode.equalsIgnoreCase("2") || operMode.equalsIgnoreCase("3") || operMode.equalsIgnoreCase("4") || operMode.equalsIgnoreCase("5") || operMode.equalsIgnoreCase("6") || operMode.equalsIgnoreCase("8") || operMode.equalsIgnoreCase("11") || operMode.equalsIgnoreCase("12") || operMode.equalsIgnoreCase("13") || operMode.equalsIgnoreCase("14") || operMode.equalsIgnoreCase("15") || operMode.equalsIgnoreCase("16") || operMode.equalsIgnoreCase("17")) {
                        flag = true;
                    } else {
                        flag = true;
                    }
                }
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
                List detail = em.createNativeQuery("select ifnull(Commencement_Date,''),ifnull(Mis_Tin,''),ifnull(SalesTurnover,'') from cbs_cust_misinfo where customerid = (select CustId from customerid where acno ='" + acNo + "')").getResultList();
                String doi = "", tin = "", salesFigure = "", finYear = "";
                if (!detail.isEmpty()) {
                    Vector vect = (Vector) detail.get(0);
                    doi = vect.get(0).toString();
                    tin = vect.get(1).toString();
                    salesFigure = vect.get(2).toString();
                }
                String jtTitle = "", jtCustFullName = "", jtGender = "", jtDob = "", jtPAN = "", jtVoterID = "", jtPasspost = "", jtDlNo = "", jtAadhar = "", jtCin = "", jtTin = "", jtSTax = "",
                        jtOtherID = "", jtAdd1 = "", jtAdd2 = "", jtAdd3 = "", jtcity = "", jtState = "", jtPinCode = "", jtCountry = "91", jtMobile = "", jtTelArea = "", jtTelNo = "",
                        jtFaxArea = "", jtFaxNo = "", jtDin = "", jtRationCard = "", jtPerControl = "", jtBussEntity = "", jtBussCat = "", jtBussType = "", jtRegNo = "";
                String jtDoi = "", jtSalesFigure = "", jtFinYear = "";
                String guarantor_entity_name = "", guarantor_prefix = "", guarantor_name = "", guarantor_gender = "",
                        guarantor_comp_regi_no = "", guarantor_doi = "", guarantor_dob = "", guarantor_pan = "", guarantor_votrid = "", guarantor_passport = "",
                        guarantor_dl = "", guarantor_uid = "", guarantor_ration = "", guarantor_cin = "", guarantor_din = "", guarantor_tin = "", guarantor_stax = "", guarantor_otherid = "", guarantor_add1 = "", guarantor_add2 = "", guarantor_add3 = "", guarantor_city = "", guarantor_district = "", guarantor_state = "", guarantor_pincode = "",
                        guarantor_country = "", guarantor_mobile = "", guarantor_tel_area = "", guarantor_tel_no = "", guarantor_fax_area = "", guarantor_fax_no = "";
                String guarantorValueOfSec = "", guarantorCurrencyType = "", guarantorTypeOfSec = "", guarantorSecurityClass = "", guarantorDateOfValuation = "",
                        guarantorSegmentIdentifier = "", guarantorDateOfDishonour = "", guarantorAmt = "", guarantorChqNo = "", guarantorNoOfDisHonour = "",
                        guarantorChqIssueDt = "", guarantorReasonForDisHonour = "";
                /*Guarantor details Start from guarantor DUNS to Reason For Dishonour*/
                if (flag == true) {
                    List l8 = em.createNativeQuery("select phno,address,acno,name,ifnull(CUST_FLAG,'N'),ifnull(GAR_ACNO,''),ifnull(GAR_CUSTID,'') from loan_guarantordetails where acno ='" + acNo + "'").getResultList();
                    if (!l8.isEmpty()) {
                        /*If Guarantor Exists.*/
                        for (int k = 0; k < l8.size(); k++) {
                            Vector vtr = (Vector) l8.get(k);
                            String garCustFlag = vtr.get(4).toString();
                            String garCustAcNo = vtr.get(5).toString();
                            String garCustId = vtr.get(6).toString();
                            List loanSecurity = em.createNativeQuery("select lienvalue,ifnull(security,''),ifnull(SecurityType,''),ifnull(Issuedate,'') from loansecurity where acno ='" + acNo + "' and  status ='Active' ").getResultList();
                            if (!loanSecurity.isEmpty()) {
                                /*If  Loan Security Exist.*/
                                for (int l = 0; l < loanSecurity.size(); l++) {
                                    Vector vect = (Vector) loanSecurity.get(l);
                                    guarantorValueOfSec = vect.get(0).toString();
                                    guarantorCurrencyType = "INR";
                                    guarantorTypeOfSec = vect.get(1).toString();
                                    guarantorSecurityClass = vect.get(2).toString();
                                    guarantorDateOfValuation = vect.get(3).toString();
                                    if (custIdList.size() > 0) {
                                        for (int c = 0; c < custIdList.size(); c++) {
                                            pojo = new EquifaxComercialPoJo();
                                            String jtCustId = "";
                                            if (custIdList.size() == 1) {
                                                jtCustId = custIdList.get(0).toString();
                                            } else {
                                                String custId = (String) custIdList.get(c);
                                                jtCustId = custId.toString();
                                            }
                                            List jtCustdetail = em.createNativeQuery("select ifnull(c.title,''),ifnull(c.CustFullName,''),case c.gender when 'M' then '2'  "
                                                    + " when 'F' then '1' when 'O' then '3' when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
                                                    + " ifnull(c.PAN_GIRNumber,''),ifnull(c.VoterIDNo,''),ifnull(c.PassportNo,''),ifnull(c.DrivingLicenseNo,''),ifnull(aa.IdentityNo ,''),ifnull(c.cin,''),ifnull(c.TINNumber,''),ifnull(c.SalesTaxNo,''),"
                                                    + " ifnull(c.other_identity,''),ifnull(c.MailAddressLine1,''), ifnull(c.MailAddressLine2,'') ,"
                                                    + " ifnull((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode),'') as MailCityCode, "
                                                    + " ifnull((select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode),'') as mailstatecode, "
                                                    + " ifnull(c.MailPostalCode,''),ifnull(c.MailCountryCode,''),ifnull(c.MailPhoneNumber,''),ifnull(c.MailTelexNumber,''),ifnull(c.MailFaxNumber,'') "
                                                    + " from cbs_customer_master_detail c "
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
//                                                jtCountry = jtVect.get(18).toString();
                                                jtMobile = jtVect.get(19).toString();
                                                jtTelNo = jtVect.get(20).toString();
                                                jtFaxNo = jtVect.get(21).toString();
                                            }
                                            if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                                                /* If Custid Exist in Guarantor */
                                                List l3 = custIdListForGuarantor(garCustId);
                                                if (!l3.isEmpty()) {
                                                    pojo = new EquifaxComercialPoJo();
                                                    vtr = (Vector) l3.get(0);
                                                    guarantor_prefix = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                                    guarantor_name = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                                    guarantor_dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                                    guarantor_gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                                    guarantor_pan = vtr.get(4) != null ? vtr.get(4).toString() : "";
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
                                                    
                                                    pojo = new EquifaxComercialPoJo();
                                                    pojo.setMemberCode(memberCode);
                                                    pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                                    pojo.setMemberName(custName);
                                                    pojo.setShortName(shortNm);
                                                    pojo.setRegNo("");
                                                    pojo.setDoi(doi);
                                                    pojo.setPan(panGirNumber);
                                                    pojo.setCin(cin);
                                                    pojo.setTin(tin);
                                                    pojo.setsTax(sTax);
                                                    pojo.setOtherId(otheriId);
                                                    pojo.setConstNo("");
                                                    pojo.setBusinessCat("");
                                                    pojo.setBusinessType("");
                                                    pojo.setClassOfAct1(clsOfAct1);
                                                    pojo.setClassOfAct2(clsOfAct2);
                                                    pojo.setClassOfAct3(clsOfAct3);
                                                    pojo.setSicCode(sicCode);
                                                    pojo.setSalesFigure(salesFigure);
                                                    pojo.setFinYear(finYear);
                                                    pojo.setNoOfEmp(noOfEmp);
                                                    pojo.setCreditRating(creditRating);
                                                    pojo.setAuthority(authority);
                                                    pojo.setCreditRatingDt(creditRatingAsOn);
                                                    pojo.setCreditRatingExpDt(creditExpDt);
                                                    pojo.setLocationType(locationType);
                                                    pojo.setDunsNo(dunsNo);
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
                                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                    pojo.setCity(mailCityCode);
                                                    pojo.setDistrict(mailCityCode);
                                                    pojo.setState(mailStateCode);
                                                    pojo.setCountry(country);
                                                    pojo.setMobile(mobile);
                                                    pojo.setTelAreaCode(telAreCode);
                                                    pojo.setTelNo(telephoneNumber);
                                                    pojo.setFaxArea(faxAreaCode);
                                                    pojo.setFaxNo(faxNo);
                                                    pojo.setRelDunsNo(dunsNo);
                                                    pojo.setRelatedType(relatedType);
                                                    pojo.setRelationShip(relationShip);
                                                    pojo.setBusinessEntityName(jtBussEntity);
                                                    pojo.setBusinessCat1(jtBussCat);
                                                    pojo.setTypeOfBusiness(jtBussType);
                                                    pojo.setIndPreFix(jtTitle);
                                                    pojo.setFullName(jtCustFullName);
                                                    pojo.setGender(jtGender);
                                                    pojo.setRegNoCom(jtRegNo);
                                                    pojo.setDoi(jtDoi);
                                                    pojo.setDob(jtDob);
                                                    pojo.setPanNo(jtPAN);
                                                    pojo.setVoterID(jtVoterID);
                                                    pojo.setPassportNo(jtPasspost);
                                                    pojo.setDlID(jtDlNo);
                                                    pojo.setUid(jtAadhar);
                                                    pojo.setRationCardNo(jtRationCard);
                                                    pojo.setCinNo(jtCin);
                                                    pojo.setDinNo(jtDin);
                                                    pojo.setTinNo(jtTin);
                                                    pojo.setsTaxNo(jtSTax);
                                                    pojo.setOtherIdNo(jtOtherID);
                                                    pojo.setPercControl(jtPerControl);
                                                    pojo.setAdd1(jtAdd1);
                                                    pojo.setAdd2(jtAdd2);
                                                    pojo.setAdd3(jtAdd3);
                                                    pojo.setCityTown(jtcity);
                                                    pojo.setDistrict1(jtcity);
                                                    pojo.setState1(jtState);
                                                    pojo.setPin1(jtPinCode);
                                                    pojo.setCountry1(jtCountry);
                                                    pojo.setMobile1(jtMobile);
                                                    pojo.setTelAreaCode1(jtTelArea);
                                                    pojo.setTelNo1(jtTelNo);
                                                    pojo.setFaxArea1(jtFaxArea);
                                                    pojo.setFaxNo1(jtFaxNo);
                                                    pojo.setAcNo(acNo);
                                                    pojo.setPreAcno("");
                                                    pojo.setSanctDt(sanctionDt);
                                                    int overDueAmt=0;
                                                    if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                                + "where acno =  '" + acNo + "' and txnid = "
                                                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                        if (!sanctionLimitDtList.isEmpty()) {
                                                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                            odLimit = vist.get(1).toString();
                                                            pojo.setSancAmt(odLimit);
                                                        } else {
                                                            pojo.setSancAmt(odLimit);
                                                        }
//                                                        if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                            amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                        } else {
//                                                            amtOverdue = "0";
//                                                        }
//                                                        pojo.setOdBuct1(amtOverdue);
                                                        List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                        List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                        OverDueList = OverdueCaList;
                                                        if (!OverDueList.isEmpty()) {
                                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = (int) odVect.getOverDue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        pojo.setSancAmt(odLimit);
//                                                        List excessList = null;
//                                                        double excess = 0;
//                                                        if (isExcessEmi == 0) {
//                                                            excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                        } else {
//                                                            excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                                    + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                        }
//                                                        if (!excessList.isEmpty()) {
//                                                            if (!excessList.isEmpty()) {
//                                                                Vector ele = (Vector) excessList.get(0);
//                                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                    excess = Double.parseDouble(ele.get(0).toString());
//                                                                }
//                                                            }
//                                                        }
//                                                        List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                                + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                        if (!l6.isEmpty()) {
//                                                            vtr1 = (Vector) l6.get(0);
//                                                            amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                            if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                                amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                            }
//                                                            double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                            amtOverdue = String.valueOf(overDueAmt);
//                                                            pojo.setOdBuct1(amtOverdue);
//                                                        }
//                                                        List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                        if (!l7.isEmpty()) {
//                                                            vtr1 = (Vector) l7.get(0);
//                                                            overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                        }
                                                        List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                        List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                        if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                            OverDueList = OverdueDlList;
                                                            if (!OverDueList.isEmpty()) {
                                                                for (int s = 0; s < OverDueList.size(); s++) {
                                                                    OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                                    if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                        overDueAmt = (int) odVect.getOverDue();
                                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                            odBuc1 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                            odBuc2 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                            odBuc3 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                            odBuc4 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                            odBuc5 = String.valueOf(overDueAmt);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                            OverDueTLList = OverdueTlList;
                                                            if (!OverDueTLList.isEmpty()) {
                                                                for (int s = 0; s < OverDueTLList.size(); s++) {
                                                                    OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                                    if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                        overDueAmt = odVect.getAmountOverdue().intValue();
                                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                            odBuc1 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                            odBuc2 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                            odBuc3 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                            odBuc4 = String.valueOf(overDueAmt);
                                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                            odBuc5 = String.valueOf(overDueAmt);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    pojo.setCurrencyCode("INR");
                                                    pojo.setCreditType(creditType);
                                                    List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                                    if (!lemi.isEmpty()) {
                                                        Vector vtrEmi = (Vector) lemi.get(0);
                                                        repaymentTenure = vtrEmi.get(0).toString();
                                                        emiAmt = vtrEmi.get(1).toString();
                                                        periodicity = vtrEmi.get(2).toString();
                                                        pojo.setPeriod(repaymentTenure);
                                                        pojo.setEmiAmt(emiAmt);
                                                    } else {
                                                        pojo.setPeriod("0");
                                                        pojo.setEmiAmt("0");
                                                    }
                                                    if (periodicity.equalsIgnoreCase("W")) {
                                                        pojo.setRepaymentFreq("01");
                                                    } else if (periodicity.equalsIgnoreCase("M")) {
                                                        pojo.setRepaymentFreq("03");
                                                    } else if (periodicity.equalsIgnoreCase("Q")) {
                                                        pojo.setRepaymentFreq("04");
                                                    } else {
                                                        pojo.setRepaymentFreq("04");
                                                    }
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
                                                    pojo.setDrawingPower(dp);
                                                    List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                                    if (!l2.isEmpty()) {
                                                        vtr1 = (Vector) l2.get(0);
                                                        currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                                    }
                                                    pojo.setOutstanding(currentBal);
                                                    pojo.setAmtRestructured(amtRestruct);
                                                    pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                                    List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                            + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                            + " and effdt <='" + toDate + "'").getResultList();
                                                    if (!assetClsDt.isEmpty()) {
                                                        Vector vect1 = (Vector) assetClsDt.get(0);
                                                        accStatus = vect1.get(0).toString();
                                                    } else {
                                                        accStatus = accStatus;
                                                    }
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
//                                                        if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                            assetClass = "01";
//                                                        } else {
//                                                            assetClass = "05";
//                                                        }
                                                        assetClass = "01";
                                                    } else {
                                                        assetClass = "05";
                                                    }
                                                    pojo.setAssetClassif(assetClass);
                                                    pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                                    pojo.setOdBuct1(odBuc1);
                                                    pojo.setOdBuct2(odBuc2);
                                                    pojo.setOdBuct3(odBuc3);
                                                    pojo.setOdBuct4(odBuc4);
                                                    pojo.setOdBuct5(odBuc5);
                                                    pojo.setHighCredit(highCredit);
                                                    List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                                    if (!l4.isEmpty()) {
                                                        vtr1 = (Vector) l4.get(0);
                                                        lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                        List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                        if (!crAmt.isEmpty()) {
                                                            Vector crvect = (Vector) crAmt.get(0);
                                                            lastCrAmt = crvect.get(0).toString();
                                                        }
                                                    }
                                                    pojo.setLastCrAmt(lastCrAmt);
                                                    pojo.setAcStatus(accStatus);
                                                    pojo.setAcStatus(accStatusDt);
                                                    pojo.setRenewalDt(renewalDt);
                                                    pojo.setAssetClassDt(assetClassDt);
                                                    pojo.setAcStatus(accStatus);
                                                    pojo.setAcStatusDt(accStatus);
                                                    pojo.setWriteOffAmt("0");
                                                    pojo.setSettledAmt("0");
                                                    pojo.setRestureReason("");
                                                    pojo.setNpaAmt(npaAmt);
                                                    pojo.setAsstSecCover(sicCode);
                                                    pojo.setGuaranteeCover(guaranteeCover);
                                                    pojo.setBnkRemarkCode("");
                                                    pojo.setDefaultStatus("");
                                                    pojo.setDefaultStatusDt("");
                                                    pojo.setSuitStatus("");
                                                    pojo.setSuitAmt("0");
                                                    pojo.setSuitDt("");
                                                    pojo.setSuitRefNo("");
                                                    pojo.setDisputeIdNo("");
                                                    pojo.setTxnTypeCode("");
                                                    pojo.setGuarantorReasonForDisHonour("");
                                                    if (accStatus.equalsIgnoreCase("3")) {
                                                        writtrnOff = "1";
                                                    } else if (accStatus.equalsIgnoreCase("14")) {
                                                        writtrnOff = "7";
                                                    } else {
                                                        writtrnOff = "";
                                                    }

                                                    /*
                                                    01 =Standard
                                                    02 =Sub-Standard
                                                    03 =Doubtful
                                                    04 =Loss
                                                    05 =Special Mention Account
                                                     */
                                                    overDueDays = "";
                                                    
                                                    guarantorValueOfSec = vect.get(0).toString();
                                                    guarantorCurrencyType = "INR";
                                                    guarantorTypeOfSec = vect.get(1).toString();
                                                    guarantorSecurityClass = vect.get(2).toString();
                                                    guarantorDateOfValuation = vect.get(3).toString();
                                                    /*
                                                     * guarantorSegmentIdentifier="",
                                                     * guarantorDateOfDishonour="",
                                                     * guarantorAmt="",
                                                     * guarantorChqNo="",
                                                     * guarantorNoOfDisHonour="",
                                                     * guarantorChqIssueDt="",
                                                     * guarantorReasonForDisHonour=""
                                                     */
                                                    pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                                    pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                                    if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                        pojo.setGuarantorTypeOfSec("01");
                                                    } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                        pojo.setGuarantorTypeOfSec("02");
                                                    } else {
                                                        pojo.setGuarantorTypeOfSec("02");
                                                    }
                                                    pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                                    pojo.setGuarantorDateOfValuation(guarantorDateOfValuation == "" ? dmy.format(ymdhms.parse(guarantorDateOfValuation)) : "");
                                                    pojo.setGuarantorDUNs("");
                                                    pojo.setGuarantorType("");
                                                    pojo.setBusinessCatGuarantor("");
                                                    pojo.setBusinessTypeGuarantor("");
                                                    pojo.setGuarantorEntityName(guarantor_entity_name);
                                                    pojo.setGuarantorPrefix(guarantor_prefix);
                                                    pojo.setGuarantorFullName(guarantor_name);
                                                    pojo.setGuarantorGender(guarantor_gender);
                                                    pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                                    pojo.setGuarantorDOI(guarantor_doi);
                                                    pojo.setGuarantorDob(dmy.format(ymdhms.parse(guarantor_dob)));
                                                    pojo.setGuarantorPan(guarantor_pan);
                                                    pojo.setGuarantorVotedID(guarantor_votrid);
                                                    pojo.setGuarantorPassport(guarantor_passport);
                                                    pojo.setGuarantorDLId(guarantor_dl);
                                                    pojo.setGuarantorUID(guarantor_uid);
                                                    pojo.setRationCardNo(guarantor_ration);
                                                    pojo.setGuarantorCIN(guarantor_cin);
                                                    pojo.setGuarantorDIN(guarantor_din);
                                                    pojo.setGuarantorTIN(guarantor_tin);
                                                    pojo.setGuarantorSTax(guarantor_stax);
                                                    pojo.setGuarantorOthId(guarantor_otherid);
                                                    pojo.setGuarantorAdd1(guarantor_add1);
                                                    pojo.setGuarantorAdd2(guarantor_add2);
                                                    pojo.setGuarantorAdd3(guarantor_add3);
                                                    pojo.setGuarantorCity(guarantor_city);
                                                    pojo.setGuarantorDistrict(guarantor_district);
                                                    pojo.setGuarantorState(guarantor_state);
                                                    pojo.setGuarantorPin(guarantor_pincode);
                                                    pojo.setGuarantorCountry(guarantor_country);
                                                    pojo.setGuarantorMobile(guarantor_mobile);
                                                    pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                                    pojo.setGuarantorTelNo(guarantor_tel_no);
                                                    pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                                    pojo.setGuarantorFaxNo(guarantor_fax_no);
                                                    experionList.add(pojo);
                                                }
                                            } else {
                                                pojo = new EquifaxComercialPoJo();
                                                pojo.setMemberCode(memberCode);
                                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                                pojo.setMemberName(custName);
                                                pojo.setShortName(shortNm);
                                                pojo.setRegNo("");
                                                pojo.setDoi(doi);
                                                pojo.setPan(panGirNumber);
                                                pojo.setCin(cin);
                                                pojo.setTin(tin);
                                                pojo.setsTax(sTax);
                                                pojo.setOtherId(otheriId);
                                                pojo.setConstNo("");
                                                pojo.setBusinessCat("");
                                                pojo.setBusinessType("");
                                                pojo.setClassOfAct1(clsOfAct1);
                                                pojo.setClassOfAct2(clsOfAct2);
                                                pojo.setClassOfAct3(clsOfAct3);
                                                pojo.setSicCode(sicCode);
                                                pojo.setSalesFigure(salesFigure);
                                                pojo.setFinYear(finYear);
                                                pojo.setNoOfEmp(noOfEmp);
                                                pojo.setCreditRating(creditRating);
                                                pojo.setAuthority(authority);
                                                pojo.setCreditRatingDt(creditRatingAsOn);
                                                pojo.setCreditRatingExpDt(creditExpDt);
                                                pojo.setLocationType(locationType);
                                                pojo.setDunsNo(dunsNo);
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
                                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                pojo.setCity(mailCityCode);
                                                pojo.setDistrict(mailCityCode);
                                                pojo.setState(mailStateCode);
                                                pojo.setCountry(country);
                                                pojo.setMobile(mobile);
                                                pojo.setTelAreaCode(telAreCode);
                                                pojo.setTelNo(telephoneNumber);
                                                pojo.setFaxArea(faxAreaCode);
                                                pojo.setFaxNo(faxNo);
                                                pojo.setRelDunsNo(dunsNo);
                                                pojo.setRelatedType(relatedType);
                                                pojo.setRelationShip(relationShip);
                                                pojo.setBusinessEntityName(jtBussEntity);
                                                pojo.setBusinessCat1(jtBussCat);
                                                pojo.setTypeOfBusiness(jtBussType);
                                                pojo.setIndPreFix(jtTitle);
                                                pojo.setFullName(jtCustFullName);
                                                pojo.setGender(jtGender);
                                                pojo.setRegNoCom(jtRegNo);
                                                pojo.setDoi(jtDoi);
                                                pojo.setDob(jtDob);
                                                pojo.setPanNo(jtPAN);
                                                pojo.setVoterID(jtVoterID);
                                                pojo.setPassportNo(jtPasspost);
                                                pojo.setDlID(jtDlNo);
                                                pojo.setUid(jtAadhar);
                                                pojo.setRationCardNo(jtRationCard);
                                                pojo.setCinNo(jtCin);
                                                pojo.setDinNo(jtDin);
                                                pojo.setTinNo(jtTin);
                                                pojo.setsTaxNo(jtSTax);
                                                pojo.setOtherIdNo(jtOtherID);
                                                pojo.setPercControl(jtPerControl);
                                                pojo.setAdd1(jtAdd1);
                                                pojo.setAdd2(jtAdd2);
                                                pojo.setAdd3(jtAdd3);
                                                pojo.setCityTown(jtcity);
                                                pojo.setDistrict1(jtcity);
                                                pojo.setState1(jtState);
                                                pojo.setPin1(jtPinCode);
                                                pojo.setCountry1(jtCountry);
                                                pojo.setMobile1(jtMobile);
                                                pojo.setTelAreaCode1(jtTelArea);
                                                pojo.setTelNo1(jtTelNo);
                                                pojo.setFaxArea1(jtFaxArea);
                                                pojo.setFaxNo1(jtFaxNo);
                                                pojo.setAcNo(acNo);
                                                pojo.setPreAcno("");
                                                pojo.setSanctDt(sanctionDt);
                                                int overDueAmt=0;
                                                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                            + "where acno =  '" + acNo + "' and txnid = "
                                                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                    if (!sanctionLimitDtList.isEmpty()) {
                                                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                        odLimit = vist.get(1).toString();
                                                        pojo.setSancAmt(odLimit);
                                                    } else {
                                                        pojo.setSancAmt(odLimit);
                                                    }
//                                                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                        amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                    } else {
//                                                        amtOverdue = "0";
//                                                    }
                                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                    OverDueList = OverdueCaList;
                                                    if (!OverDueList.isEmpty()) {
                                                        for (int s = 0; s < OverDueList.size(); s++) {
                                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = (int) odVect.getOverDue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
//                                                    pojo.setOdBuct1(amtOverdue);
                                                } else {
                                                    List excessList = null;
//                                                    double excess = 0;
//                                                    if (isExcessEmi == 0) {
//                                                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                    } else {
//                                                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                                + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                    }
//                                                    if (!excessList.isEmpty()) {
//                                                        if (!excessList.isEmpty()) {
//                                                            Vector ele = (Vector) excessList.get(0);
//                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                excess = Double.parseDouble(ele.get(0).toString());
//                                                            }
//                                                        }
//                                                    }
//                                                    List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                    if (!l6.isEmpty()) {
//                                                        vtr1 = (Vector) l6.get(0);
//                                                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                            amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                        }
//                                                        double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                        amtOverdue = String.valueOf(overDueAmt);
//                                                        pojo.setOdBuct1(amtOverdue);
//                                                    }
//                                                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                    if (!l7.isEmpty()) {
//                                                        vtr1 = (Vector) l7.get(0);
//                                                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                    }
                                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                        OverDueList = OverdueDlList;
                                                        if (!OverDueList.isEmpty()) {
                                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = (int) odVect.getOverDue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                        OverDueTLList = OverdueTlList;
                                                        if (!OverDueTLList.isEmpty()) {
                                                            for (int s = 0; s < OverDueTLList.size(); s++) {
                                                                OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                                if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = odVect.getAmountOverdue().intValue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                pojo.setCurrencyCode("INR");
                                                pojo.setCreditType(creditType);
                                                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                                if (!lemi.isEmpty()) {
                                                    Vector vtrEmi = (Vector) lemi.get(0);
                                                    repaymentTenure = vtrEmi.get(0).toString();
                                                    emiAmt = vtrEmi.get(1).toString();
                                                    periodicity = vtrEmi.get(2).toString();
                                                    pojo.setPeriod(repaymentTenure);
                                                    pojo.setEmiAmt(emiAmt);
                                                } else {
                                                    pojo.setPeriod("0");
                                                    pojo.setEmiAmt("0");
                                                }
                                                if (periodicity.equalsIgnoreCase("W")) {
                                                    pojo.setRepaymentFreq("01");
                                                } else if (periodicity.equalsIgnoreCase("M")) {
                                                    pojo.setRepaymentFreq("03");
                                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                                    pojo.setRepaymentFreq("04");
                                                } else {
                                                    pojo.setRepaymentFreq("04");
                                                }
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
                                                pojo.setDrawingPower(dp);
                                                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                                if (!l2.isEmpty()) {
                                                    vtr1 = (Vector) l2.get(0);
                                                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                                }
                                                pojo.setOutstanding(currentBal);
                                                pojo.setAmtRestructured(amtRestruct);
                                                pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                                List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                        + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                        + " and effdt <='" + toDate + "'").getResultList();
                                                if (!assetClsDt.isEmpty()) {
                                                    Vector vect1 = (Vector) assetClsDt.get(0);
                                                    accStatus = vect1.get(0).toString();
                                                } else {
                                                    accStatus = accStatus;
                                                }
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
//                                                    if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                        assetClass = "01";
//                                                    } else {
//                                                        assetClass = "05";
//                                                    }
                                                    assetClass = "01";
                                                } else {
                                                    assetClass = "05";
                                                }
                                                pojo.setAssetClassif(assetClass);
                                                pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                                pojo.setOdBuct1(odBuc1);
                                                pojo.setOdBuct2(odBuc2);
                                                pojo.setOdBuct3(odBuc3);
                                                pojo.setOdBuct4(odBuc4);
                                                pojo.setOdBuct5(odBuc5);
                                                pojo.setHighCredit(highCredit);
                                                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                                if (!l4.isEmpty()) {
                                                    vtr1 = (Vector) l4.get(0);
                                                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                    List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                    if (!crAmt.isEmpty()) {
                                                        Vector crvect = (Vector) crAmt.get(0);
                                                        lastCrAmt = crvect.get(0).toString();
                                                    }
                                                }
                                                pojo.setLastCrAmt(lastCrAmt);
                                                pojo.setAcStatus(accStatus);
                                                pojo.setAcStatus(accStatusDt);
                                                pojo.setRenewalDt(renewalDt);
                                                pojo.setAssetClassDt(assetClassDt);
                                                pojo.setAcStatus(accStatus);
                                                pojo.setAcStatusDt(accStatus);
                                                pojo.setWriteOffAmt("0");
                                                pojo.setSettledAmt("0");
                                                pojo.setRestureReason("");
                                                pojo.setNpaAmt(npaAmt);
                                                pojo.setAsstSecCover(sicCode);
                                                pojo.setGuaranteeCover(guaranteeCover);
                                                pojo.setBnkRemarkCode("");
                                                pojo.setDefaultStatus("");
                                                pojo.setDefaultStatusDt("");
                                                pojo.setSuitStatus("");
                                                pojo.setSuitAmt("0");
                                                pojo.setSuitDt("");
                                                pojo.setSuitRefNo("");
                                                pojo.setDisputeIdNo("");
                                                pojo.setTxnTypeCode("");
                                                pojo.setGuarantorReasonForDisHonour("");
                                                if (accStatus.equalsIgnoreCase("3")) {
                                                    writtrnOff = "1";
                                                } else if (accStatus.equalsIgnoreCase("14")) {
                                                    writtrnOff = "7";
                                                } else {
                                                    writtrnOff = "";
                                                }

                                                /*
                                                01 =Standard
                                                02 =Sub-Standard
                                                03 =Doubtful
                                                04 =Loss
                                                05 =Special Mention Account
                                                 */
                                                overDueDays = "";
                                                /*
                                                 * guarantorSegmentIdentifier="",
                                                 * guarantorDateOfDishonour="",
                                                 * guarantorAmt="",
                                                 * guarantorChqNo="",
                                                 * guarantorNoOfDisHonour="",
                                                 * guarantorChqIssueDt="",
                                                 * guarantorReasonForDisHonour=""
                                                 */
                                                pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                                pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                                if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                    pojo.setGuarantorTypeOfSec("01");
                                                } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                    pojo.setGuarantorTypeOfSec("02");
                                                } else {
                                                    pojo.setGuarantorTypeOfSec("02");
                                                }
                                                pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                                pojo.setGuarantorDateOfValuation(guarantorDateOfValuation == "" ? dmy.format(ymdhms.parse(guarantorDateOfValuation)) : "");
                                                pojo.setGuarantorDUNs("");
                                                pojo.setGuarantorType("");
                                                pojo.setBusinessCatGuarantor("");
                                                pojo.setBusinessTypeGuarantor("");
                                                pojo.setGuarantorEntityName(guarantor_entity_name);
                                                pojo.setGuarantorPrefix(guarantor_prefix);
                                                pojo.setGuarantorFullName(vtr.get(3).toString());
                                                pojo.setGuarantorGender(guarantor_gender);
                                                pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                                pojo.setGuarantorDOI(guarantor_doi);
                                                /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                                pojo.setGuarantorDob(guarantor_dob);
                                                pojo.setGuarantorPan(guarantor_pan);
                                                pojo.setGuarantorVotedID(guarantor_votrid);
                                                pojo.setGuarantorPassport(guarantor_passport);
                                                pojo.setGuarantorDLId(guarantor_dl);
                                                pojo.setGuarantorUID(guarantor_uid);
                                                pojo.setRationCardNo(guarantor_ration);
                                                pojo.setGuarantorCIN(guarantor_cin);
                                                pojo.setGuarantorDIN(guarantor_din);
                                                pojo.setGuarantorTIN(guarantor_tin);
                                                pojo.setGuarantorSTax(guarantor_stax);
                                                pojo.setGuarantorOthId(guarantor_otherid);
                                                pojo.setGuarantorAdd1(vtr.get(1).toString());
                                                pojo.setGuarantorAdd2(guarantor_add2);
                                                pojo.setGuarantorAdd3(guarantor_add3);
                                                pojo.setGuarantorCity(guarantor_city);
                                                pojo.setGuarantorDistrict(guarantor_district);
                                                pojo.setGuarantorState(guarantor_state);
                                                pojo.setGuarantorPin(guarantor_pincode);
                                                pojo.setGuarantorCountry(guarantor_country);
                                                pojo.setGuarantorMobile(guarantor_mobile);
                                                pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                                pojo.setGuarantorTelNo(vtr.get(0).toString());
                                                pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                                pojo.setGuarantorFaxNo(guarantor_fax_no);
                                                experionList.add(pojo);
                                            }
                                        }
                                    } else {
                                        if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                                            /* If Custid Exist in Guarantor */
                                            List l3 = custIdListForGuarantor(garCustId);
                                            if (!l3.isEmpty()) {
                                                pojo = new EquifaxComercialPoJo();
                                                vtr = (Vector) l3.get(0);
                                                guarantor_prefix = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                                guarantor_name = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                                guarantor_dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                                guarantor_gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                                guarantor_pan = vtr.get(4) != null ? vtr.get(4).toString() : "";
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
                                                
                                                pojo = new EquifaxComercialPoJo();
                                                pojo.setMemberCode(memberCode);
                                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                                pojo.setMemberName(custName);
                                                pojo.setShortName(shortNm);
                                                pojo.setRegNo("");
                                                pojo.setDoi(doi);
                                                pojo.setPan(panGirNumber);
                                                pojo.setCin(cin);
                                                pojo.setTin(tin);
                                                pojo.setsTax(sTax);
                                                pojo.setOtherId(otheriId);
                                                pojo.setConstNo("");
                                                pojo.setBusinessCat("");
                                                pojo.setBusinessType("");
                                                pojo.setClassOfAct1(clsOfAct1);
                                                pojo.setClassOfAct2(clsOfAct2);
                                                pojo.setClassOfAct3(clsOfAct3);
                                                pojo.setSicCode(sicCode);
                                                pojo.setSalesFigure(salesFigure);
                                                pojo.setFinYear(finYear);
                                                pojo.setNoOfEmp(noOfEmp);
                                                pojo.setCreditRating(creditRating);
                                                pojo.setAuthority(authority);
                                                pojo.setCreditRatingDt(creditRatingAsOn);
                                                pojo.setCreditRatingExpDt(creditExpDt);
                                                pojo.setLocationType(locationType);
                                                pojo.setDunsNo(dunsNo);
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
                                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                pojo.setCity(mailCityCode);
                                                pojo.setDistrict(mailCityCode);
                                                pojo.setState(mailStateCode);
                                                pojo.setCountry(country);
                                                pojo.setMobile(mobile);
                                                pojo.setTelAreaCode(telAreCode);
                                                pojo.setTelNo(telephoneNumber);
                                                pojo.setFaxArea(faxAreaCode);
                                                pojo.setFaxNo(faxNo);
                                                pojo.setRelDunsNo(dunsNo);
                                                pojo.setRelatedType(relatedType);
                                                pojo.setRelationShip(relationShip);
                                                pojo.setBusinessEntityName(jtBussEntity);
                                                pojo.setBusinessCat1(jtBussCat);
                                                pojo.setTypeOfBusiness(jtBussType);
                                                pojo.setIndPreFix(jtTitle);
                                                pojo.setFullName(jtCustFullName);
                                                pojo.setGender(jtGender);
                                                pojo.setRegNoCom(jtRegNo);
                                                pojo.setDoi(jtDoi);
                                                pojo.setDob(jtDob);
                                                pojo.setPanNo(jtPAN);
                                                pojo.setVoterID(jtVoterID);
                                                pojo.setPassportNo(jtPasspost);
                                                pojo.setDlID(jtDlNo);
                                                pojo.setUid(jtAadhar);
                                                pojo.setRationCardNo(jtRationCard);
                                                pojo.setCinNo(jtCin);
                                                pojo.setDinNo(jtDin);
                                                pojo.setTinNo(jtTin);
                                                pojo.setsTaxNo(jtSTax);
                                                pojo.setOtherIdNo(jtOtherID);
                                                pojo.setPercControl(jtPerControl);
                                                pojo.setAdd1(jtAdd1);
                                                pojo.setAdd2(jtAdd2);
                                                pojo.setAdd3(jtAdd3);
                                                pojo.setCityTown(jtcity);
                                                pojo.setDistrict1(jtcity);
                                                pojo.setState1(jtState);
                                                pojo.setPin1(jtPinCode);
                                                pojo.setCountry1(jtCountry);
                                                pojo.setMobile1(jtMobile);
                                                pojo.setTelAreaCode1(jtTelArea);
                                                pojo.setTelNo1(jtTelNo);
                                                pojo.setFaxArea1(jtFaxArea);
                                                pojo.setFaxNo1(jtFaxNo);
                                                pojo.setAcNo(acNo);
                                                pojo.setPreAcno("");
                                                pojo.setSanctDt(sanctionDt);
                                                int overDueAmt =0;
                                                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                            + "where acno =  '" + acNo + "' and txnid = "
                                                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                    if (!sanctionLimitDtList.isEmpty()) {
                                                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                        odLimit = vist.get(1).toString();
                                                        pojo.setSancAmt(odLimit);
                                                    } else {
                                                        pojo.setSancAmt(odLimit);
                                                    }
//                                                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                        amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                    } else {
//                                                        amtOverdue = "0";
//                                                    }
//                                                    pojo.setOdBuct1(amtOverdue);
                                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                    OverDueList = OverdueCaList;
                                                    if (!OverDueList.isEmpty()) {
                                                        for (int s = 0; s < OverDueList.size(); s++) {
                                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = (int) odVect.getOverDue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    List excessList = null;
//                                                    double excess = 0;
//                                                    if (isExcessEmi == 0) {
//                                                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                    } else {
//                                                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                                + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                    }
//                                                    if (!excessList.isEmpty()) {
//                                                        if (!excessList.isEmpty()) {
//                                                            Vector ele = (Vector) excessList.get(0);
//                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                excess = Double.parseDouble(ele.get(0).toString());
//                                                            }
//                                                        }
//                                                    }
//                                                    List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                    if (!l6.isEmpty()) {
//                                                        vtr1 = (Vector) l6.get(0);
//                                                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                            amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                        }
//                                                        double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                        amtOverdue = String.valueOf(overDueAmt);
//                                                        pojo.setOdBuct1(amtOverdue);
//                                                    }
//                                                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                    if (!l7.isEmpty()) {
//                                                        vtr1 = (Vector) l7.get(0);
//                                                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                    }
                                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                        OverDueList = OverdueDlList;
                                                        if (!OverDueList.isEmpty()) {
                                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = (int) odVect.getOverDue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                        OverDueTLList = OverdueTlList;
                                                        if (!OverDueTLList.isEmpty()) {
                                                            for (int s = 0; s < OverDueTLList.size(); s++) {
                                                                OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                                if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = odVect.getAmountOverdue().intValue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                pojo.setCurrencyCode("INR");
                                                pojo.setCreditType(creditType);
                                                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                                if (!lemi.isEmpty()) {
                                                    Vector vtrEmi = (Vector) lemi.get(0);
                                                    repaymentTenure = vtrEmi.get(0).toString();
                                                    emiAmt = vtrEmi.get(1).toString();
                                                    periodicity = vtrEmi.get(2).toString();
                                                    pojo.setPeriod(repaymentTenure);
                                                    pojo.setEmiAmt(emiAmt);
                                                } else {
                                                    pojo.setPeriod("0");
                                                    pojo.setEmiAmt("0");
                                                }
                                                if (periodicity.equalsIgnoreCase("W")) {
                                                    pojo.setRepaymentFreq("01");
                                                } else if (periodicity.equalsIgnoreCase("M")) {
                                                    pojo.setRepaymentFreq("03");
                                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                                    pojo.setRepaymentFreq("04");
                                                } else {
                                                    pojo.setRepaymentFreq("04");
                                                }
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
                                                pojo.setDrawingPower(dp);
                                                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                                if (!l2.isEmpty()) {
                                                    vtr1 = (Vector) l2.get(0);
                                                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                                }
                                                pojo.setOutstanding(currentBal);
                                                pojo.setAmtRestructured(amtRestruct);
                                                pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                                List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                        + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                        + " and effdt <='" + toDate + "'").getResultList();
                                                if (!assetClsDt.isEmpty()) {
                                                    Vector vect1 = (Vector) assetClsDt.get(0);
                                                    accStatus = vect1.get(0).toString();
                                                } else {
                                                    accStatus = accStatus;
                                                }
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
//                                                    if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                        assetClass = "01";
//                                                    } else {
//                                                        assetClass = "05";
//                                                    }
                                                    assetClass = "01";
                                                } else {
                                                    assetClass = "05";
                                                }
                                                pojo.setAssetClassif(assetClass);
                                                pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                                pojo.setOdBuct1(odBuc1);
                                                pojo.setOdBuct2(odBuc2);
                                                pojo.setOdBuct3(odBuc3);
                                                pojo.setOdBuct4(odBuc4);
                                                pojo.setOdBuct5(odBuc5);
                                                pojo.setHighCredit(highCredit);
                                                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                                if (!l4.isEmpty()) {
                                                    vtr1 = (Vector) l4.get(0);
                                                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                    List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                    if (!crAmt.isEmpty()) {
                                                        Vector crvect = (Vector) crAmt.get(0);
                                                        lastCrAmt = crvect.get(0).toString();
                                                    }
                                                }
                                                pojo.setLastCrAmt(lastCrAmt);
                                                pojo.setAcStatus(accStatus);
                                                pojo.setAcStatus(accStatusDt);
                                                pojo.setRenewalDt(renewalDt);
                                                pojo.setAssetClassDt(assetClassDt);
                                                pojo.setAcStatus(accStatus);
                                                pojo.setAcStatusDt(accStatus);
                                                pojo.setWriteOffAmt("0");
                                                pojo.setSettledAmt("0");
                                                pojo.setRestureReason("");
                                                pojo.setNpaAmt(npaAmt);
                                                pojo.setAsstSecCover(sicCode);
                                                pojo.setGuaranteeCover(guaranteeCover);
                                                pojo.setBnkRemarkCode("");
                                                pojo.setDefaultStatus("");
                                                pojo.setDefaultStatusDt("");
                                                pojo.setSuitStatus("");
                                                pojo.setSuitAmt("0");
                                                pojo.setSuitDt("");
                                                pojo.setSuitRefNo("");
                                                pojo.setDisputeIdNo("");
                                                pojo.setTxnTypeCode("");
                                                pojo.setGuarantorReasonForDisHonour("");
                                                if (accStatus.equalsIgnoreCase("3")) {
                                                    writtrnOff = "1";
                                                } else if (accStatus.equalsIgnoreCase("14")) {
                                                    writtrnOff = "7";
                                                } else {
                                                    writtrnOff = "";
                                                }

                                                /*
                                                01 =Standard
                                                02 =Sub-Standard
                                                03 =Doubtful
                                                04 =Loss
                                                05 =Special Mention Account
                                                 */
                                                overDueDays = "";
                                                
                                                guarantorValueOfSec = vect.get(0).toString();
                                                guarantorCurrencyType = "INR";
                                                guarantorTypeOfSec = vect.get(1).toString();
                                                guarantorSecurityClass = vect.get(2).toString();
                                                guarantorDateOfValuation = vect.get(3).toString();
                                                /*
                                                 * guarantorSegmentIdentifier="",
                                                 * guarantorDateOfDishonour="",
                                                 * guarantorAmt="",
                                                 * guarantorChqNo="",
                                                 * guarantorNoOfDisHonour="",
                                                 * guarantorChqIssueDt="",
                                                 * guarantorReasonForDisHonour=""
                                                 */
                                                pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                                pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                                if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                    pojo.setGuarantorTypeOfSec("01");
                                                } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                    pojo.setGuarantorTypeOfSec("02");
                                                } else {
                                                    pojo.setGuarantorTypeOfSec("02");
                                                }
                                                pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                                pojo.setGuarantorDateOfValuation(guarantorDateOfValuation == "" ? dmy.format(ymdhms.parse(guarantorDateOfValuation)) : "");
                                                pojo.setGuarantorDUNs("");
                                                pojo.setGuarantorType("");
                                                pojo.setBusinessCatGuarantor("");
                                                pojo.setBusinessTypeGuarantor("");
                                                pojo.setGuarantorEntityName(guarantor_entity_name);
                                                pojo.setGuarantorPrefix(guarantor_prefix);
                                                pojo.setGuarantorFullName(guarantor_name);
                                                pojo.setGuarantorGender(guarantor_gender);
                                                pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                                pojo.setGuarantorDOI(guarantor_doi);
                                                pojo.setGuarantorDob(dmy.format(ymdhms.parse(guarantor_dob)));
                                                pojo.setGuarantorPan(guarantor_pan);
                                                pojo.setGuarantorVotedID(guarantor_votrid);
                                                pojo.setGuarantorPassport(guarantor_passport);
                                                pojo.setGuarantorDLId(guarantor_dl);
                                                pojo.setGuarantorUID(guarantor_uid);
                                                pojo.setRationCardNo(guarantor_ration);
                                                pojo.setGuarantorCIN(guarantor_cin);
                                                pojo.setGuarantorDIN(guarantor_din);
                                                pojo.setGuarantorTIN(guarantor_tin);
                                                pojo.setGuarantorSTax(guarantor_stax);
                                                pojo.setGuarantorOthId(guarantor_otherid);
                                                pojo.setGuarantorAdd1(guarantor_add1);
                                                pojo.setGuarantorAdd2(guarantor_add2);
                                                pojo.setGuarantorAdd3(guarantor_add3);
                                                pojo.setGuarantorCity(guarantor_city);
                                                pojo.setGuarantorDistrict(guarantor_district);
                                                pojo.setGuarantorState(guarantor_state);
                                                pojo.setGuarantorPin(guarantor_pincode);
                                                pojo.setGuarantorCountry(guarantor_country);
                                                pojo.setGuarantorMobile(guarantor_mobile);
                                                pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                                pojo.setGuarantorTelNo(guarantor_tel_no);
                                                pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                                pojo.setGuarantorFaxNo(guarantor_fax_no);
                                                experionList.add(pojo);
                                            }
                                        } else {
                                            pojo = new EquifaxComercialPoJo();
                                            pojo.setMemberCode(memberCode);
                                            pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                            pojo.setMemberName(custName);
                                            pojo.setShortName(shortNm);
                                            pojo.setRegNo("");
                                            pojo.setDoi(doi);
                                            pojo.setPan(panGirNumber);
                                            pojo.setCin(cin);
                                            pojo.setTin(tin);
                                            pojo.setsTax(sTax);
                                            pojo.setOtherId(otheriId);
                                            pojo.setConstNo("");
                                            pojo.setBusinessCat("");
                                            pojo.setBusinessType("");
                                            pojo.setClassOfAct1(clsOfAct1);
                                            pojo.setClassOfAct2(clsOfAct2);
                                            pojo.setClassOfAct3(clsOfAct3);
                                            pojo.setSicCode(sicCode);
                                            pojo.setSalesFigure(salesFigure);
                                            pojo.setFinYear(finYear);
                                            pojo.setNoOfEmp(noOfEmp);
                                            pojo.setCreditRating(creditRating);
                                            pojo.setAuthority(authority);
                                            pojo.setCreditRatingDt(creditRatingAsOn);
                                            pojo.setCreditRatingExpDt(creditExpDt);
                                            pojo.setLocationType(locationType);
                                            pojo.setDunsNo(dunsNo);
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
                                            pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            pojo.setCity(mailCityCode);
                                            pojo.setDistrict(mailCityCode);
                                            pojo.setState(mailStateCode);
                                            pojo.setCountry(country);
                                            pojo.setMobile(mobile);
                                            pojo.setTelAreaCode(telAreCode);
                                            pojo.setTelNo(telephoneNumber);
                                            pojo.setFaxArea(faxAreaCode);
                                            pojo.setFaxNo(faxNo);
                                            pojo.setRelDunsNo(dunsNo);
                                            pojo.setRelatedType(relatedType);
                                            pojo.setRelationShip(relationShip);
                                            pojo.setBusinessEntityName(jtBussEntity);
                                            pojo.setBusinessCat1(jtBussCat);
                                            pojo.setTypeOfBusiness(jtBussType);
                                            pojo.setIndPreFix(jtTitle);
                                            pojo.setFullName(jtCustFullName);
                                            pojo.setGender(jtGender);
                                            pojo.setRegNoCom(jtRegNo);
                                            pojo.setDoi(jtDoi);
                                            pojo.setDob(jtDob);
                                            pojo.setPanNo(jtPAN);
                                            pojo.setVoterID(jtVoterID);
                                            pojo.setPassportNo(jtPasspost);
                                            pojo.setDlID(jtDlNo);
                                            pojo.setUid(jtAadhar);
                                            pojo.setRationCardNo(jtRationCard);
                                            pojo.setCinNo(jtCin);
                                            pojo.setDinNo(jtDin);
                                            pojo.setTinNo(jtTin);
                                            pojo.setsTaxNo(jtSTax);
                                            pojo.setOtherIdNo(jtOtherID);
                                            pojo.setPercControl(jtPerControl);
                                            pojo.setAdd1(jtAdd1);
                                            pojo.setAdd2(jtAdd2);
                                            pojo.setAdd3(jtAdd3);
                                            pojo.setCityTown(jtcity);
                                            pojo.setDistrict1(jtcity);
                                            pojo.setState1(jtState);
                                            pojo.setPin1(jtPinCode);
                                            pojo.setCountry1(jtCountry);
                                            pojo.setMobile1(jtMobile);
                                            pojo.setTelAreaCode1(jtTelArea);
                                            pojo.setTelNo1(jtTelNo);
                                            pojo.setFaxArea1(jtFaxArea);
                                            pojo.setFaxNo1(jtFaxNo);
                                            pojo.setAcNo(acNo);
                                            pojo.setPreAcno("");
                                            pojo.setSanctDt(sanctionDt);
                                            int overDueAmt =0;
                                            if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                        + "where acno =  '" + acNo + "' and txnid = "
                                                        + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                if (!sanctionLimitDtList.isEmpty()) {
                                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                    odLimit = vist.get(1).toString();
                                                    pojo.setSancAmt(odLimit);
                                                } else {
                                                    pojo.setSancAmt(odLimit);
                                                }
//                                                if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                    amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                } else {
//                                                    amtOverdue = "0";
//                                                }
//                                                pojo.setOdBuct1(amtOverdue);
                                                List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                OverDueList = OverdueCaList;
                                                if (!OverDueList.isEmpty()) {
                                                    for (int s = 0; s < OverDueList.size(); s++) {
                                                        OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                        if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = (int) odVect.getOverDue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                                
                                            } else {
//                                                List excessList = null;
//                                                double excess = 0;
//                                                if (isExcessEmi == 0) {
//                                                    excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                } else {
//                                                    excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                            + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                }
//                                                if (!excessList.isEmpty()) {
//                                                    if (!excessList.isEmpty()) {
//                                                        Vector ele = (Vector) excessList.get(0);
//                                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                            excess = Double.parseDouble(ele.get(0).toString());
//                                                        }
//                                                    }
//                                                }
//                                                List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                        + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                if (!l6.isEmpty()) {
//                                                    vtr1 = (Vector) l6.get(0);
//                                                    amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                    if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                        amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                    }
//                                                    double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                    amtOverdue = String.valueOf(overDueAmt);
//                                                    pojo.setOdBuct1(amtOverdue);
//                                                }
//                                                List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                if (!l7.isEmpty()) {
//                                                    vtr1 = (Vector) l7.get(0);
//                                                    overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                }
                                                List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                    OverDueList = OverdueDlList;
                                                    if (!OverDueList.isEmpty()) {
                                                        for (int s = 0; s < OverDueList.size(); s++) {
                                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = (int) odVect.getOverDue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                    OverDueTLList = OverdueTlList;
                                                    if (!OverDueTLList.isEmpty()) {
                                                        for (int s = 0; s < OverDueTLList.size(); s++) {
                                                            OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                            if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = odVect.getAmountOverdue().intValue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            pojo.setCurrencyCode("INR");
                                            pojo.setCreditType(creditType);
                                            List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                            if (!lemi.isEmpty()) {
                                                Vector vtrEmi = (Vector) lemi.get(0);
                                                repaymentTenure = vtrEmi.get(0).toString();
                                                emiAmt = vtrEmi.get(1).toString();
                                                periodicity = vtrEmi.get(2).toString();
                                                pojo.setPeriod(repaymentTenure);
                                                pojo.setEmiAmt(emiAmt);
                                            } else {
                                                pojo.setPeriod("0");
                                                pojo.setEmiAmt("0");
                                            }
                                            if (periodicity.equalsIgnoreCase("W")) {
                                                pojo.setRepaymentFreq("01");
                                            } else if (periodicity.equalsIgnoreCase("M")) {
                                                pojo.setRepaymentFreq("03");
                                            } else if (periodicity.equalsIgnoreCase("Q")) {
                                                pojo.setRepaymentFreq("04");
                                            } else {
                                                pojo.setRepaymentFreq("04");
                                            }
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
                                            pojo.setDrawingPower(dp);
                                            List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                            if (!l2.isEmpty()) {
                                                vtr1 = (Vector) l2.get(0);
                                                currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                            }
                                            pojo.setOutstanding(currentBal);
                                            pojo.setAmtRestructured(amtRestruct);
                                            pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                            List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                    + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                    + " and effdt <='" + toDate + "'").getResultList();
                                            if (!assetClsDt.isEmpty()) {
                                                Vector vect1 = (Vector) assetClsDt.get(0);
                                                accStatus = vect1.get(0).toString();
                                            } else {
                                                accStatus = accStatus;
                                            }
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
//                                                if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                    assetClass = "01";
//                                                } else {
//                                                    assetClass = "05";
//                                                }
                                                assetClass = "01";
                                            } else {
                                                assetClass = "05";
                                            }
                                            pojo.setAssetClassif(assetClass);
                                            pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                            pojo.setOdBuct1(odBuc1);
                                            pojo.setOdBuct2(odBuc2);
                                            pojo.setOdBuct3(odBuc3);
                                            pojo.setOdBuct4(odBuc4);
                                            pojo.setOdBuct5(odBuc5);
                                            pojo.setHighCredit(highCredit);
                                            List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                            if (!l4.isEmpty()) {
                                                vtr1 = (Vector) l4.get(0);
                                                lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                if (!crAmt.isEmpty()) {
                                                    Vector crvect = (Vector) crAmt.get(0);
                                                    lastCrAmt = crvect.get(0).toString();
                                                }
                                            }
                                            pojo.setLastCrAmt(lastCrAmt);
                                            pojo.setAcStatus(accStatus);
                                            pojo.setAcStatus(accStatusDt);
                                            pojo.setRenewalDt(renewalDt);
                                            pojo.setAssetClassDt(assetClassDt);
                                            pojo.setAcStatus(accStatus);
                                            pojo.setAcStatusDt(accStatus);
                                            pojo.setWriteOffAmt("0");
                                            pojo.setSettledAmt("0");
                                            pojo.setRestureReason("");
                                            pojo.setNpaAmt(npaAmt);
                                            pojo.setAsstSecCover(sicCode);
                                            pojo.setGuaranteeCover(guaranteeCover);
                                            pojo.setBnkRemarkCode("");
                                            pojo.setDefaultStatus("");
                                            pojo.setDefaultStatusDt("");
                                            pojo.setSuitStatus("");
                                            pojo.setSuitAmt("0");
                                            pojo.setSuitDt("");
                                            pojo.setSuitRefNo("");
                                            pojo.setDisputeIdNo("");
                                            pojo.setTxnTypeCode("");
                                            pojo.setGuarantorReasonForDisHonour("");
                                            if (accStatus.equalsIgnoreCase("3")) {
                                                writtrnOff = "1";
                                            } else if (accStatus.equalsIgnoreCase("14")) {
                                                writtrnOff = "7";
                                            } else {
                                                writtrnOff = "";
                                            }

                                            /*
                                            01 =Standard
                                            02 =Sub-Standard
                                            03 =Doubtful
                                            04 =Loss
                                            05 =Special Mention Account
                                             */
                                            overDueDays = "";
                                            /*
                                             * guarantorSegmentIdentifier="",
                                             * guarantorDateOfDishonour="",
                                             * guarantorAmt="",
                                             * guarantorChqNo="",
                                             * guarantorNoOfDisHonour="",
                                             * guarantorChqIssueDt="",
                                             * guarantorReasonForDisHonour=""
                                             */
                                            pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                            pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                            if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                pojo.setGuarantorTypeOfSec("01");
                                            } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                pojo.setGuarantorTypeOfSec("02");
                                            } else {
                                                pojo.setGuarantorTypeOfSec("02");
                                            }
                                            pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                            pojo.setGuarantorDateOfValuation(guarantorDateOfValuation == "" ? dmy.format(ymdhms.parse(guarantorDateOfValuation)) : "");
                                            pojo.setGuarantorDUNs("");
                                            pojo.setGuarantorType("");
                                            pojo.setBusinessCatGuarantor("");
                                            pojo.setBusinessTypeGuarantor("");
                                            pojo.setGuarantorEntityName(guarantor_entity_name);
                                            pojo.setGuarantorPrefix(guarantor_prefix);
                                            pojo.setGuarantorFullName(vtr.get(3).toString());
                                            pojo.setGuarantorGender(guarantor_gender);
                                            pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                            pojo.setGuarantorDOI(guarantor_doi);
                                            /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                            pojo.setGuarantorDob(guarantor_dob);
                                            pojo.setGuarantorPan(guarantor_pan);
                                            pojo.setGuarantorVotedID(guarantor_votrid);
                                            pojo.setGuarantorPassport(guarantor_passport);
                                            pojo.setGuarantorDLId(guarantor_dl);
                                            pojo.setGuarantorUID(guarantor_uid);
                                            pojo.setRationCardNo(guarantor_ration);
                                            pojo.setGuarantorCIN(guarantor_cin);
                                            pojo.setGuarantorDIN(guarantor_din);
                                            pojo.setGuarantorTIN(guarantor_tin);
                                            pojo.setGuarantorSTax(guarantor_stax);
                                            pojo.setGuarantorOthId(guarantor_otherid);
                                            pojo.setGuarantorAdd1(vtr.get(1).toString());
                                            pojo.setGuarantorAdd2(guarantor_add2);
                                            pojo.setGuarantorAdd3(guarantor_add3);
                                            pojo.setGuarantorCity(guarantor_city);
                                            pojo.setGuarantorDistrict(guarantor_district);
                                            pojo.setGuarantorState(guarantor_state);
                                            pojo.setGuarantorPin(guarantor_pincode);
                                            pojo.setGuarantorCountry(guarantor_country);
                                            pojo.setGuarantorMobile(guarantor_mobile);
                                            pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                            pojo.setGuarantorTelNo(vtr.get(0).toString());
                                            pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                            pojo.setGuarantorFaxNo(guarantor_fax_no);
                                            experionList.add(pojo);
                                        }
                                    }
                                }
                            } else {
                                /*If Loan Security Does not Exist*/
                                if (custIdList.size() > 0) {
                                    /*If Joint Details Exist*/
                                    for (int c = 0; c < custIdList.size(); c++) {
                                        pojo = new EquifaxComercialPoJo();
                                        String jtCustId = "";
                                        if (custIdList.size() == 1) {
                                            jtCustId = custIdList.get(0).toString();
                                        } else {
                                            String custId = (String) custIdList.get(c);
                                            jtCustId = custId.toString();
                                        }
                                        List jtCustdetail = em.createNativeQuery("select ifnull(c.title,''),ifnull(c.CustFullName,''),case c.gender when 'M' then '2'  "
                                                + " when 'F' then '1'  when 'O' then '3' when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
                                                + " ifnull(c.PAN_GIRNumber,''),ifnull(c.VoterIDNo,''),ifnull(c.PassportNo,''),ifnull(c.DrivingLicenseNo,''),ifnull(aa.IdentityNo ,''),ifnull(c.cin,''),ifnull(c.TINNumber,''),ifnull(c.SalesTaxNo,''),"
                                                + " ifnull(c.other_identity,''),ifnull(c.MailAddressLine1,''), ifnull(c.MailAddressLine2,'') ,"
                                                + " ifnull((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode),'') as MailCityCode, "
                                                + " ifnull((select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode),'') as mailstatecode, "
                                                + " ifnull(c.MailPostalCode,''),ifnull(c.MailCountryCode,''),ifnull(c.MailPhoneNumber,''),ifnull(c.MailTelexNumber,''),ifnull(c.MailFaxNumber,'') "
                                                + " from cbs_customer_master_detail c "
                                                + " left outer join "
                                                + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                                                + " union "
                                                + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                                                + "  where c.customerid='" + jtCustId + "';").getResultList();
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
//                                            jtCountry = jtVect.get(18).toString();
                                            jtMobile = jtVect.get(19).toString();
                                            jtTelNo = jtVect.get(20).toString();
                                            jtFaxNo = jtVect.get(21).toString();
                                        }
                                        if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                                            /* If Custid Exist in Guarantor */
                                            List l3 = custIdListForGuarantor(garCustId);
                                            if (!l3.isEmpty()) {
                                                pojo = new EquifaxComercialPoJo();
                                                vtr = (Vector) l3.get(0);
                                                guarantor_prefix = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                                guarantor_name = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                                guarantor_dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                                guarantor_gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                                guarantor_pan = vtr.get(4) != null ? vtr.get(4).toString() : "";
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
                                                
                                                pojo = new EquifaxComercialPoJo();
                                                pojo.setMemberCode(memberCode);
                                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                                pojo.setMemberName(custName);
                                                pojo.setShortName(shortNm);
                                                pojo.setRegNo("");
                                                pojo.setDoi(doi);
                                                pojo.setPan(panGirNumber);
                                                pojo.setCin(cin);
                                                pojo.setTin(tin);
                                                pojo.setsTax(sTax);
                                                pojo.setOtherId(otheriId);
                                                pojo.setConstNo("");
                                                pojo.setBusinessCat("");
                                                pojo.setBusinessType("");
                                                pojo.setClassOfAct1(clsOfAct1);
                                                pojo.setClassOfAct2(clsOfAct2);
                                                pojo.setClassOfAct3(clsOfAct3);
                                                pojo.setSicCode(sicCode);
                                                pojo.setSalesFigure(salesFigure);
                                                pojo.setFinYear(finYear);
                                                pojo.setNoOfEmp(noOfEmp);
                                                pojo.setCreditRating(creditRating);
                                                pojo.setAuthority(authority);
                                                pojo.setCreditRatingDt(creditRatingAsOn);
                                                pojo.setCreditRatingExpDt(creditExpDt);
                                                pojo.setLocationType(locationType);
                                                pojo.setDunsNo(dunsNo);
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
                                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                                pojo.setCity(mailCityCode);
                                                pojo.setDistrict(mailCityCode);
                                                pojo.setState(mailStateCode);
                                                pojo.setCountry(country);
                                                pojo.setMobile(mobile);
                                                pojo.setTelAreaCode(telAreCode);
                                                pojo.setTelNo(telephoneNumber);
                                                pojo.setFaxArea(faxAreaCode);
                                                pojo.setFaxNo(faxNo);
                                                pojo.setRelDunsNo(dunsNo);
                                                pojo.setRelatedType(relatedType);
                                                pojo.setRelationShip(relationShip);
                                                pojo.setBusinessEntityName(jtBussEntity);
                                                pojo.setBusinessCat1(jtBussCat);
                                                pojo.setTypeOfBusiness(jtBussType);
                                                pojo.setIndPreFix(jtTitle);
                                                pojo.setFullName(jtCustFullName);
                                                pojo.setGender(jtGender);
                                                pojo.setRegNoCom(jtRegNo);
                                                pojo.setDoi(jtDoi);
                                                pojo.setDob(jtDob);
                                                pojo.setPanNo(jtPAN);
                                                pojo.setVoterID(jtVoterID);
                                                pojo.setPassportNo(jtPasspost);
                                                pojo.setDlID(jtDlNo);
                                                pojo.setUid(jtAadhar);
                                                pojo.setRationCardNo(jtRationCard);
                                                pojo.setCinNo(jtCin);
                                                pojo.setDinNo(jtDin);
                                                pojo.setTinNo(jtTin);
                                                pojo.setsTaxNo(jtSTax);
                                                pojo.setOtherIdNo(jtOtherID);
                                                pojo.setPercControl(jtPerControl);
                                                pojo.setAdd1(jtAdd1);
                                                pojo.setAdd2(jtAdd2);
                                                pojo.setAdd3(jtAdd3);
                                                pojo.setCityTown(jtcity);
                                                pojo.setDistrict1(jtcity);
                                                pojo.setState1(jtState);
                                                pojo.setPin1(jtPinCode);
                                                pojo.setCountry1(jtCountry);
                                                pojo.setMobile1(jtMobile);
                                                pojo.setTelAreaCode1(jtTelArea);
                                                pojo.setTelNo1(jtTelNo);
                                                pojo.setFaxArea1(jtFaxArea);
                                                pojo.setFaxNo1(jtFaxNo);
                                                pojo.setAcNo(acNo);
                                                pojo.setPreAcno("");
                                                pojo.setSanctDt(sanctionDt);
                                                int overDueAmt =0;
                                                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                            + "where acno =  '" + acNo + "' and txnid = "
                                                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                    if (!sanctionLimitDtList.isEmpty()) {
                                                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                        odLimit = vist.get(1).toString();
                                                        pojo.setSancAmt(odLimit);
                                                    } else {
                                                        pojo.setSancAmt(odLimit);
                                                    }
//                                                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                        amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                    } else {
//                                                        amtOverdue = "0";
//                                                    }
//                                                    pojo.setOdBuct1(amtOverdue);
                                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                    OverDueList = OverdueCaList;
                                                    if (!OverDueList.isEmpty()) {
                                                        for (int s = 0; s < OverDueList.size(); s++) {
                                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = (int) odVect.getOverDue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    
                                                } else {
//                                                    List excessList = null;
//                                                    double excess = 0;
//                                                    if (isExcessEmi == 0) {
//                                                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                    } else {
//                                                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                                + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                    }
//                                                    if (!excessList.isEmpty()) {
//                                                        if (!excessList.isEmpty()) {
//                                                            Vector ele = (Vector) excessList.get(0);
//                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                excess = Double.parseDouble(ele.get(0).toString());
//                                                            }
//                                                        }
//                                                    }
//                                                    List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                    if (!l6.isEmpty()) {
//                                                        vtr1 = (Vector) l6.get(0);
//                                                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                            amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                        }
//                                                        double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                        amtOverdue = String.valueOf(overDueAmt);
//                                                        pojo.setOdBuct1(amtOverdue);
//                                                    }
//                                                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                    if (!l7.isEmpty()) {
//                                                        vtr1 = (Vector) l7.get(0);
//                                                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                    }
                                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                        OverDueList = OverdueDlList;
                                                        if (!OverDueList.isEmpty()) {
                                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = (int) odVect.getOverDue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                        OverDueTLList = OverdueTlList;
                                                        if (!OverDueTLList.isEmpty()) {
                                                            for (int s = 0; s < OverDueTLList.size(); s++) {
                                                                OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                                if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                    overDueAmt = odVect.getAmountOverdue().intValue();
                                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                        odBuc1 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                        odBuc2 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                        odBuc3 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                        odBuc4 = String.valueOf(overDueAmt);
                                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                        odBuc5 = String.valueOf(overDueAmt);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                pojo.setCurrencyCode("INR");
                                                pojo.setCreditType(creditType);
                                                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                                if (!lemi.isEmpty()) {
                                                    Vector vtrEmi = (Vector) lemi.get(0);
                                                    repaymentTenure = vtrEmi.get(0).toString();
                                                    emiAmt = vtrEmi.get(1).toString();
                                                    periodicity = vtrEmi.get(2).toString();
                                                    pojo.setPeriod(repaymentTenure);
                                                    pojo.setEmiAmt(emiAmt);
                                                } else {
                                                    pojo.setPeriod("0");
                                                    pojo.setEmiAmt("0");
                                                }
                                                if (periodicity.equalsIgnoreCase("W")) {
                                                    pojo.setRepaymentFreq("01");
                                                } else if (periodicity.equalsIgnoreCase("M")) {
                                                    pojo.setRepaymentFreq("03");
                                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                                    pojo.setRepaymentFreq("04");
                                                } else {
                                                    pojo.setRepaymentFreq("04");
                                                }
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
                                                pojo.setDrawingPower(dp);
                                                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                                if (!l2.isEmpty()) {
                                                    vtr1 = (Vector) l2.get(0);
                                                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                                }
                                                pojo.setOutstanding(currentBal);
                                                pojo.setAmtRestructured(amtRestruct);
                                                pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                                List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                        + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                        + " and effdt <='" + toDate + "'").getResultList();
                                                if (!assetClsDt.isEmpty()) {
                                                    Vector vect1 = (Vector) assetClsDt.get(0);
                                                    accStatus = vect1.get(0).toString();
                                                } else {
                                                    accStatus = accStatus;
                                                }
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
//                                                    if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                        assetClass = "01";
//                                                    } else {
//                                                        assetClass = "05";
//                                                    }
                                                    assetClass = "01";
                                                } else {
                                                    assetClass = "05";
                                                }
                                                pojo.setAssetClassif(assetClass);
                                                pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                                pojo.setOdBuct1(odBuc1);
                                                pojo.setOdBuct2(odBuc2);
                                                pojo.setOdBuct3(odBuc3);
                                                pojo.setOdBuct4(odBuc4);
                                                pojo.setOdBuct5(odBuc5);
                                                pojo.setHighCredit(highCredit);
                                                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                                if (!l4.isEmpty()) {
                                                    vtr1 = (Vector) l4.get(0);
                                                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                    List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                    if (!crAmt.isEmpty()) {
                                                        Vector crvect = (Vector) crAmt.get(0);
                                                        lastCrAmt = crvect.get(0).toString();
                                                    }
                                                }
                                                pojo.setLastCrAmt(lastCrAmt);
                                                pojo.setAcStatus(accStatus);
                                                pojo.setAcStatus(accStatusDt);
                                                pojo.setRenewalDt(renewalDt);
                                                pojo.setAssetClassDt(assetClassDt);
                                                pojo.setAcStatus(accStatus);
                                                pojo.setAcStatusDt(accStatus);
                                                pojo.setWriteOffAmt("0");
                                                pojo.setSettledAmt("0");
                                                pojo.setRestureReason("");
                                                pojo.setNpaAmt(npaAmt);
                                                pojo.setAsstSecCover(sicCode);
                                                pojo.setGuaranteeCover(guaranteeCover);
                                                pojo.setBnkRemarkCode("");
                                                pojo.setDefaultStatus("");
                                                pojo.setDefaultStatusDt("");
                                                pojo.setSuitStatus("");
                                                pojo.setSuitAmt("0");
                                                pojo.setSuitDt("");
                                                pojo.setSuitRefNo("");
                                                pojo.setDisputeIdNo("");
                                                pojo.setTxnTypeCode("");
                                                pojo.setGuarantorReasonForDisHonour("");
                                                if (accStatus.equalsIgnoreCase("3")) {
                                                    writtrnOff = "1";
                                                } else if (accStatus.equalsIgnoreCase("14")) {
                                                    writtrnOff = "7";
                                                } else {
                                                    writtrnOff = "";
                                                }
                                                /*
                                                01 =Standard
                                                02 =Sub-Standard
                                                03 =Doubtful
                                                04 =Loss
                                                05 =Special Mention Account
                                                 */
//                                                guarantorValueOfSec = vect.get(0).toString();
//                                                guarantorCurrencyType = "INR";
//                                                guarantorTypeOfSec = vect.get(1).toString();
//                                                guarantorSecurityClass = vect.get(2).toString();
//                                                guarantorDateOfValuation = vect.get(3).toString();
                                                /*
                                                 * guarantorSegmentIdentifier="",
                                                 * guarantorDateOfDishonour="",
                                                 * guarantorAmt="",
                                                 * guarantorChqNo="",
                                                 * guarantorNoOfDisHonour="",
                                                 * guarantorChqIssueDt="",
                                                 * guarantorReasonForDisHonour=""
                                                 */
                                                pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                                pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                                if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                    pojo.setGuarantorTypeOfSec("01");
                                                } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                    pojo.setGuarantorTypeOfSec("02");
                                                } else {
                                                    pojo.setGuarantorTypeOfSec("02");
                                                }
                                                pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                                pojo.setGuarantorDateOfValuation(guarantorDateOfValuation);
                                                pojo.setGuarantorDUNs("");
                                                pojo.setGuarantorType("");
                                                pojo.setBusinessCatGuarantor("");
                                                pojo.setBusinessTypeGuarantor("");
                                                pojo.setGuarantorEntityName(guarantor_entity_name);
                                                pojo.setGuarantorPrefix(guarantor_prefix);
                                                pojo.setGuarantorFullName(guarantor_name);
                                                pojo.setGuarantorGender(guarantor_gender);
                                                pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                                pojo.setGuarantorDOI(guarantor_doi);
                                                pojo.setGuarantorDob(dmy.format(ymdhms.parse(guarantor_dob)));
                                                pojo.setGuarantorPan(guarantor_pan);
                                                pojo.setGuarantorVotedID(guarantor_votrid);
                                                pojo.setGuarantorPassport(guarantor_passport);
                                                pojo.setGuarantorDLId(guarantor_dl);
                                                pojo.setGuarantorUID(guarantor_uid);
                                                pojo.setRationCardNo(guarantor_ration);
                                                pojo.setGuarantorCIN(guarantor_cin);
                                                pojo.setGuarantorDIN(guarantor_din);
                                                pojo.setGuarantorTIN(guarantor_tin);
                                                pojo.setGuarantorSTax(guarantor_stax);
                                                pojo.setGuarantorOthId(guarantor_otherid);
                                                pojo.setGuarantorAdd1(guarantor_add1);
                                                pojo.setGuarantorAdd2(guarantor_add2);
                                                pojo.setGuarantorAdd3(guarantor_add3);
                                                pojo.setGuarantorCity(guarantor_city);
                                                pojo.setGuarantorDistrict(guarantor_district);
                                                pojo.setGuarantorState(guarantor_state);
                                                pojo.setGuarantorPin(guarantor_pincode);
                                                pojo.setGuarantorCountry(guarantor_country);
                                                pojo.setGuarantorMobile(guarantor_mobile);
                                                pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                                pojo.setGuarantorTelNo(guarantor_tel_no);
                                                pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                                pojo.setGuarantorFaxNo(guarantor_fax_no);
                                                experionList.add(pojo);
                                            }
                                        } else {
                                            pojo = new EquifaxComercialPoJo();
                                            pojo.setMemberCode(memberCode);
                                            pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                            pojo.setMemberName(custName);
                                            pojo.setShortName(shortNm);
                                            pojo.setRegNo("");
                                            pojo.setDoi(doi);
                                            pojo.setPan(panGirNumber);
                                            pojo.setCin(cin);
                                            pojo.setTin(tin);
                                            pojo.setsTax(sTax);
                                            pojo.setOtherId(otheriId);
                                            pojo.setConstNo("");
                                            pojo.setBusinessCat("");
                                            pojo.setBusinessType("");
                                            pojo.setClassOfAct1(clsOfAct1);
                                            pojo.setClassOfAct2(clsOfAct2);
                                            pojo.setClassOfAct3(clsOfAct3);
                                            pojo.setSicCode(sicCode);
                                            pojo.setSalesFigure(salesFigure);
                                            pojo.setFinYear(finYear);
                                            pojo.setNoOfEmp(noOfEmp);
                                            pojo.setCreditRating(creditRating);
                                            pojo.setAuthority(authority);
                                            pojo.setCreditRatingDt(creditRatingAsOn);
                                            pojo.setCreditRatingExpDt(creditExpDt);
                                            pojo.setLocationType(locationType);
                                            pojo.setDunsNo(dunsNo);
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
                                            pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            pojo.setCity(mailCityCode);
                                            pojo.setDistrict(mailCityCode);
                                            pojo.setState(mailStateCode);
                                            pojo.setCountry(country);
                                            pojo.setMobile(mobile);
                                            pojo.setTelAreaCode(telAreCode);
                                            pojo.setTelNo(telephoneNumber);
                                            pojo.setFaxArea(faxAreaCode);
                                            pojo.setFaxNo(faxNo);
                                            pojo.setRelDunsNo(dunsNo);
                                            pojo.setRelatedType(relatedType);
                                            pojo.setRelationShip(relationShip);
                                            pojo.setBusinessEntityName(jtBussEntity);
                                            pojo.setBusinessCat1(jtBussCat);
                                            pojo.setTypeOfBusiness(jtBussType);
                                            pojo.setIndPreFix(jtTitle);
                                            pojo.setFullName(jtCustFullName);
                                            pojo.setGender(jtGender);
                                            pojo.setRegNoCom(jtRegNo);
                                            pojo.setDoi(jtDoi);
                                            pojo.setDob(jtDob);
                                            pojo.setPanNo(jtPAN);
                                            pojo.setVoterID(jtVoterID);
                                            pojo.setPassportNo(jtPasspost);
                                            pojo.setDlID(jtDlNo);
                                            pojo.setUid(jtAadhar);
                                            pojo.setRationCardNo(jtRationCard);
                                            pojo.setCinNo(jtCin);
                                            pojo.setDinNo(jtDin);
                                            pojo.setTinNo(jtTin);
                                            pojo.setsTaxNo(jtSTax);
                                            pojo.setOtherIdNo(jtOtherID);
                                            pojo.setPercControl(jtPerControl);
                                            pojo.setAdd1(jtAdd1);
                                            pojo.setAdd2(jtAdd2);
                                            pojo.setAdd3(jtAdd3);
                                            pojo.setCityTown(jtcity);
                                            pojo.setDistrict1(jtcity);
                                            pojo.setState1(jtState);
                                            pojo.setPin1(jtPinCode);
                                            pojo.setCountry1(jtCountry);
                                            pojo.setMobile1(jtMobile);
                                            pojo.setTelAreaCode1(jtTelArea);
                                            pojo.setTelNo1(jtTelNo);
                                            pojo.setFaxArea1(jtFaxArea);
                                            pojo.setFaxNo1(jtFaxNo);
                                            pojo.setAcNo(acNo);
                                            pojo.setPreAcno("");
                                            pojo.setSanctDt(sanctionDt);
                                            int overDueAmt =0;
                                            if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                        + "where acno =  '" + acNo + "' and txnid = "
                                                        + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                if (!sanctionLimitDtList.isEmpty()) {
                                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                    odLimit = vist.get(1).toString();
                                                    pojo.setSancAmt(odLimit);
                                                } else {
                                                    pojo.setSancAmt(odLimit);
                                                }
//                                                if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                    amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                } else {
//                                                    amtOverdue = "0";
//                                                }
//                                                pojo.setOdBuct1(amtOverdue);
                                                List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                OverDueList = OverdueCaList;
                                                if (!OverDueList.isEmpty()) {
                                                    for (int s = 0; s < OverDueList.size(); s++) {
                                                        OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                        if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = (int) odVect.getOverDue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
//                                                List excessList = null;
//                                                double excess = 0;
//                                                if (isExcessEmi == 0) {
//                                                    excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                } else {
//                                                    excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                            + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                }
//                                                if (!excessList.isEmpty()) {
//                                                    if (!excessList.isEmpty()) {
//                                                        Vector ele = (Vector) excessList.get(0);
//                                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                            excess = Double.parseDouble(ele.get(0).toString());
//                                                        }
//                                                    }
//                                                }
//                                                List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                        + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                if (!l6.isEmpty()) {
//                                                    vtr1 = (Vector) l6.get(0);
//                                                    amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                    if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                        amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                    }
//                                                    double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                    amtOverdue = String.valueOf(overDueAmt);
//                                                    pojo.setOdBuct1(amtOverdue);
//                                                }
//                                                List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                if (!l7.isEmpty()) {
//                                                    vtr1 = (Vector) l7.get(0);
//                                                    overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                }
                                                List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                    OverDueList = OverdueDlList;
                                                    if (!OverDueList.isEmpty()) {
                                                        for (int s = 0; s < OverDueList.size(); s++) {
                                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = (int) odVect.getOverDue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                    OverDueTLList = OverdueTlList;
                                                    if (!OverDueTLList.isEmpty()) {
                                                        for (int s = 0; s < OverDueTLList.size(); s++) {
                                                            OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                            if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = odVect.getAmountOverdue().intValue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            pojo.setCurrencyCode("INR");
                                            pojo.setCreditType(creditType);
                                            List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                            if (!lemi.isEmpty()) {
                                                Vector vtrEmi = (Vector) lemi.get(0);
                                                repaymentTenure = vtrEmi.get(0).toString();
                                                emiAmt = vtrEmi.get(1).toString();
                                                periodicity = vtrEmi.get(2).toString();
                                                pojo.setPeriod(repaymentTenure);
                                                pojo.setEmiAmt(emiAmt);
                                            } else {
                                                pojo.setPeriod("0");
                                                pojo.setEmiAmt("0");
                                            }
                                            if (periodicity.equalsIgnoreCase("W")) {
                                                pojo.setRepaymentFreq("01");
                                            } else if (periodicity.equalsIgnoreCase("M")) {
                                                pojo.setRepaymentFreq("03");
                                            } else if (periodicity.equalsIgnoreCase("Q")) {
                                                pojo.setRepaymentFreq("04");
                                            } else {
                                                pojo.setRepaymentFreq("04");
                                            }
                                            /* Payment Frequency
                                             * 01 = Weekly 
                                             * 02 = Fortnightly 
                                             * 03 = Monthly 
                                             * 04 = Quarterly
                                             * 
                                             * in our System
                                            
                                             * W = weekly
                                             * M = Monthly
                                             * Q = Quarterly
                                             * HY/H= half-Yearly
                                             * Y = Yearly                                            
                                             */
                                            pojo.setDrawingPower(dp);
                                            List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                            if (!l2.isEmpty()) {
                                                vtr1 = (Vector) l2.get(0);
                                                currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                            }
                                            pojo.setOutstanding(currentBal);
                                            pojo.setAmtRestructured(amtRestruct);
                                            pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                            List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                    + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                    + " and effdt <='" + toDate + "'").getResultList();
                                            if (!assetClsDt.isEmpty()) {
                                                Vector vect = (Vector) assetClsDt.get(0);
                                                accStatus = vect.get(0).toString();
                                            } else {
                                                accStatus = accStatus;
                                            }
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
//                                                if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                    assetClass = "01";
//                                                } else {
//                                                    assetClass = "05";
//                                                }
                                                assetClass = "01";
                                            } else {
                                                assetClass = "05";
                                            }
                                            pojo.setAssetClassif(assetClass);
                                            pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                            pojo.setOdBuct1(odBuc1);
                                            pojo.setOdBuct2(odBuc2);
                                            pojo.setOdBuct3(odBuc3);
                                            pojo.setOdBuct4(odBuc4);
                                            pojo.setOdBuct5(odBuc5);
                                            pojo.setHighCredit(highCredit);
                                            List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                            if (!l4.isEmpty()) {
                                                vtr1 = (Vector) l4.get(0);
                                                lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                if (!crAmt.isEmpty()) {
                                                    Vector crvect = (Vector) crAmt.get(0);
                                                    lastCrAmt = crvect.get(0).toString();
                                                }
                                            }
                                            pojo.setLastCrAmt(lastCrAmt);
                                            pojo.setAcStatus(accStatus);
                                            pojo.setAcStatus(accStatusDt);
                                            pojo.setRenewalDt(renewalDt);
                                            pojo.setAssetClassDt(assetClassDt);
                                            pojo.setAcStatus(accStatus);
                                            pojo.setAcStatusDt(accStatus);
                                            pojo.setWriteOffAmt("0");
                                            pojo.setSettledAmt("0");
                                            pojo.setRestureReason("");
                                            pojo.setNpaAmt(npaAmt);
                                            pojo.setAsstSecCover(sicCode);
                                            pojo.setGuaranteeCover(guaranteeCover);
                                            pojo.setBnkRemarkCode("");
                                            pojo.setDefaultStatus("");
                                            pojo.setDefaultStatusDt("");
                                            pojo.setSuitStatus("");
                                            pojo.setSuitAmt("0");
                                            pojo.setSuitDt("");
                                            pojo.setSuitRefNo("");
                                            pojo.setDisputeIdNo("");
                                            pojo.setTxnTypeCode("");
                                            pojo.setGuarantorReasonForDisHonour("");
                                            if (accStatus.equalsIgnoreCase("3")) {
                                                writtrnOff = "1";
                                            } else if (accStatus.equalsIgnoreCase("14")) {
                                                writtrnOff = "7";
                                            } else {
                                                writtrnOff = "";
                                            }

                                            /*
                                            01 =Standard
                                            02 =Sub-Standard
                                            03 =Doubtful
                                            04 =Loss
                                            05 =Special Mention Account
                                             */
                                            /*
                                             * guarantorSegmentIdentifier="",
                                             * guarantorDateOfDishonour="",
                                             * guarantorAmt="",
                                             * guarantorChqNo="",
                                             * guarantorNoOfDisHonour="",
                                             * guarantorChqIssueDt="",
                                             * guarantorReasonForDisHonour=""
                                             */
                                            pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                            pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                            if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                pojo.setGuarantorTypeOfSec("01");
                                            } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                pojo.setGuarantorTypeOfSec("02");
                                            } else {
                                                pojo.setGuarantorTypeOfSec("02");
                                            }
                                            pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                            pojo.setGuarantorDateOfValuation(guarantorDateOfValuation);
                                            pojo.setGuarantorDUNs("");
                                            pojo.setGuarantorType("");
                                            pojo.setBusinessCatGuarantor("");
                                            pojo.setBusinessTypeGuarantor("");
                                            pojo.setGuarantorEntityName(guarantor_entity_name);
                                            pojo.setGuarantorPrefix(guarantor_prefix);
                                            pojo.setGuarantorFullName(vtr.get(3).toString());
                                            pojo.setGuarantorGender(guarantor_gender);
                                            pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                            pojo.setGuarantorDOI(guarantor_doi);
                                            /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                            pojo.setGuarantorDob(guarantor_dob);
                                            pojo.setGuarantorPan(guarantor_pan);
                                            pojo.setGuarantorVotedID(guarantor_votrid);
                                            pojo.setGuarantorPassport(guarantor_passport);
                                            pojo.setGuarantorDLId(guarantor_dl);
                                            pojo.setGuarantorUID(guarantor_uid);
                                            pojo.setRationCardNo(guarantor_ration);
                                            pojo.setGuarantorCIN(guarantor_cin);
                                            pojo.setGuarantorDIN(guarantor_din);
                                            pojo.setGuarantorTIN(guarantor_tin);
                                            pojo.setGuarantorSTax(guarantor_stax);
                                            pojo.setGuarantorOthId(guarantor_otherid);
                                            pojo.setGuarantorAdd1(vtr.get(1).toString());
                                            pojo.setGuarantorAdd2(guarantor_add2);
                                            pojo.setGuarantorAdd3(guarantor_add3);
                                            pojo.setGuarantorCity(guarantor_city);
                                            pojo.setGuarantorDistrict(guarantor_district);
                                            pojo.setGuarantorState(guarantor_state);
                                            pojo.setGuarantorPin(guarantor_pincode);
                                            pojo.setGuarantorCountry(guarantor_country);
                                            pojo.setGuarantorMobile(guarantor_mobile);
                                            pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                            pojo.setGuarantorTelNo(vtr.get(0).toString());
                                            pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                            pojo.setGuarantorFaxNo(guarantor_fax_no);
                                            experionList.add(pojo);
                                        }
                                    }
                                } else {
                                    if (garCustFlag.equalsIgnoreCase("YC") || garCustFlag.equalsIgnoreCase("YA")) {
                                        /* If Custid Exist in Guarantor */
                                        List l3 = custIdListForGuarantor(garCustId);
                                        if (!l3.isEmpty()) {
                                            pojo = new EquifaxComercialPoJo();
                                            vtr = (Vector) l3.get(0);
                                            guarantor_prefix = vtr.get(0) != null ? vtr.get(0).toString() : "";
                                            guarantor_name = vtr.get(1) != null ? vtr.get(1).toString() : "";
                                            guarantor_dob = vtr.get(2) != null ? vtr.get(2).toString() : "1900-01-01";
                                            guarantor_gender = vtr.get(3) != null ? vtr.get(3).toString() : "";
                                            guarantor_pan = vtr.get(4) != null ? vtr.get(4).toString() : "";
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
                                            
                                            pojo = new EquifaxComercialPoJo();
                                            pojo.setMemberCode(memberCode);
                                            pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                            pojo.setMemberName(custName);
                                            pojo.setShortName(shortNm);
                                            pojo.setRegNo("");
                                            pojo.setDoi(doi);
                                            pojo.setPan(panGirNumber);
                                            pojo.setCin(cin);
                                            pojo.setTin(tin);
                                            pojo.setsTax(sTax);
                                            pojo.setOtherId(otheriId);
                                            pojo.setConstNo("");
                                            pojo.setBusinessCat("");
                                            pojo.setBusinessType("");
                                            pojo.setClassOfAct1(clsOfAct1);
                                            pojo.setClassOfAct2(clsOfAct2);
                                            pojo.setClassOfAct3(clsOfAct3);
                                            pojo.setSicCode(sicCode);
                                            pojo.setSalesFigure(salesFigure);
                                            pojo.setFinYear(finYear);
                                            pojo.setNoOfEmp(noOfEmp);
                                            pojo.setCreditRating(creditRating);
                                            pojo.setAuthority(authority);
                                            pojo.setCreditRatingDt(creditRatingAsOn);
                                            pojo.setCreditRatingExpDt(creditExpDt);
                                            pojo.setLocationType(locationType);
                                            pojo.setDunsNo(dunsNo);
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
                                            pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            pojo.setCity(mailCityCode);
                                            pojo.setDistrict(mailCityCode);
                                            pojo.setState(mailStateCode);
                                            pojo.setCountry(country);
                                            pojo.setMobile(mobile);
                                            pojo.setTelAreaCode(telAreCode);
                                            pojo.setTelNo(telephoneNumber);
                                            pojo.setFaxArea(faxAreaCode);
                                            pojo.setFaxNo(faxNo);
                                            pojo.setRelDunsNo(dunsNo);
                                            pojo.setRelatedType(relatedType);
                                            pojo.setRelationShip(relationShip);
                                            pojo.setBusinessEntityName(jtBussEntity);
                                            pojo.setBusinessCat1(jtBussCat);
                                            pojo.setTypeOfBusiness(jtBussType);
                                            pojo.setIndPreFix(jtTitle);
                                            pojo.setFullName(jtCustFullName);
                                            pojo.setGender(jtGender);
                                            pojo.setRegNoCom(jtRegNo);
                                            pojo.setDoi(jtDoi);
                                            pojo.setDob(jtDob);
                                            pojo.setPanNo(jtPAN);
                                            pojo.setVoterID(jtVoterID);
                                            pojo.setPassportNo(jtPasspost);
                                            pojo.setDlID(jtDlNo);
                                            pojo.setUid(jtAadhar);
                                            pojo.setRationCardNo(jtRationCard);
                                            pojo.setCinNo(jtCin);
                                            pojo.setDinNo(jtDin);
                                            pojo.setTinNo(jtTin);
                                            pojo.setsTaxNo(jtSTax);
                                            pojo.setOtherIdNo(jtOtherID);
                                            pojo.setPercControl(jtPerControl);
                                            pojo.setAdd1(jtAdd1);
                                            pojo.setAdd2(jtAdd2);
                                            pojo.setAdd3(jtAdd3);
                                            pojo.setCityTown(jtcity);
                                            pojo.setDistrict1(jtcity);
                                            pojo.setState1(jtState);
                                            pojo.setPin1(jtPinCode);
                                            pojo.setCountry1(jtCountry);
                                            pojo.setMobile1(jtMobile);
                                            pojo.setTelAreaCode1(jtTelArea);
                                            pojo.setTelNo1(jtTelNo);
                                            pojo.setFaxArea1(jtFaxArea);
                                            pojo.setFaxNo1(jtFaxNo);
                                            pojo.setAcNo(acNo);
                                            pojo.setPreAcno("");
                                            pojo.setSanctDt(sanctionDt);
                                            int overDueAmt =0;
                                            if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                        + "where acno =  '" + acNo + "' and txnid = "
                                                        + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                                if (!sanctionLimitDtList.isEmpty()) {
                                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                    odLimit = vist.get(1).toString();
                                                    pojo.setSancAmt(odLimit);
                                                } else {
                                                    pojo.setSancAmt(odLimit);
                                                }
//                                                if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                    amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                                } else {
//                                                    amtOverdue = "0";
//                                                }
//                                                pojo.setOdBuct1(amtOverdue);
                                                List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                OverDueList = OverdueCaList;
                                                if (!OverDueList.isEmpty()) {
                                                    for (int s = 0; s < OverDueList.size(); s++) {
                                                        OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                        if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = (int) odVect.getOverDue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
//                                                List excessList = null;
//                                                double excess = 0;
//                                                if (isExcessEmi == 0) {
//                                                    excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                                } else {
//                                                    excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                            + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                                }
//                                                if (!excessList.isEmpty()) {
//                                                    if (!excessList.isEmpty()) {
//                                                        Vector ele = (Vector) excessList.get(0);
//                                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                            excess = Double.parseDouble(ele.get(0).toString());
//                                                        }
//                                                    }
//                                                }
//                                                List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                        + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                                if (!l6.isEmpty()) {
//                                                    vtr1 = (Vector) l6.get(0);
//                                                    amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                    if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                        amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                    }
//                                                    double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                    amtOverdue = String.valueOf(overDueAmt);
//                                                    pojo.setOdBuct1(amtOverdue);
//                                                }
//                                                List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                                if (!l7.isEmpty()) {
//                                                    vtr1 = (Vector) l7.get(0);
//                                                    overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                                }
                                                List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                                List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                    OverDueList = OverdueDlList;
                                                    if (!OverDueList.isEmpty()) {
                                                        for (int s = 0; s < OverDueList.size(); s++) {
                                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = (int) odVect.getOverDue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                    OverDueTLList = OverdueTlList;
                                                    if (!OverDueTLList.isEmpty()) {
                                                        for (int s = 0; s < OverDueTLList.size(); s++) {
                                                            OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                            if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                                overDueAmt = odVect.getAmountOverdue().intValue();
                                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                    odBuc1 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                    odBuc2 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                    odBuc3 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                    odBuc4 = String.valueOf(overDueAmt);
                                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                    odBuc5 = String.valueOf(overDueAmt);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            pojo.setCurrencyCode("INR");
                                            pojo.setCreditType(creditType);
                                            List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                            if (!lemi.isEmpty()) {
                                                Vector vtrEmi = (Vector) lemi.get(0);
                                                repaymentTenure = vtrEmi.get(0).toString();
                                                emiAmt = vtrEmi.get(1).toString();
                                                periodicity = vtrEmi.get(2).toString();
                                                pojo.setPeriod(repaymentTenure);
                                                pojo.setEmiAmt(emiAmt);
                                            } else {
                                                pojo.setPeriod("0");
                                                pojo.setEmiAmt("0");
                                            }
                                            if (periodicity.equalsIgnoreCase("W")) {
                                                pojo.setRepaymentFreq("01");
                                            } else if (periodicity.equalsIgnoreCase("M")) {
                                                pojo.setRepaymentFreq("03");
                                            } else if (periodicity.equalsIgnoreCase("Q")) {
                                                pojo.setRepaymentFreq("04");
                                            } else {
                                                pojo.setRepaymentFreq("04");
                                            }
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
                                            pojo.setDrawingPower(dp);
                                            List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                            if (!l2.isEmpty()) {
                                                vtr1 = (Vector) l2.get(0);
                                                currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                            }
                                            pojo.setOutstanding(currentBal);
                                            pojo.setAmtRestructured(amtRestruct);
                                            pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                            List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                    + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                    + " and effdt <='" + toDate + "'").getResultList();
                                            if (!assetClsDt.isEmpty()) {
                                                Vector vect = (Vector) assetClsDt.get(0);
                                                accStatus = vect.get(0).toString();
                                            } else {
                                                accStatus = accStatus;
                                            }
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
//                                                if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                    assetClass = "01";
//                                                } else {
//                                                    assetClass = "05";
//                                                }
                                                assetClass = "01";
                                            } else {
                                                assetClass = "05";
                                            }
                                            pojo.setAssetClassif(assetClass);
                                            pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                            pojo.setOdBuct1(odBuc1);
                                            pojo.setOdBuct2(odBuc2);
                                            pojo.setOdBuct3(odBuc3);
                                            pojo.setOdBuct4(odBuc4);
                                            pojo.setOdBuct5(odBuc5);
                                            pojo.setHighCredit(highCredit);
                                            List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                            if (!l4.isEmpty()) {
                                                vtr1 = (Vector) l4.get(0);
                                                lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                                List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                                if (!crAmt.isEmpty()) {
                                                    Vector crvect = (Vector) crAmt.get(0);
                                                    lastCrAmt = crvect.get(0).toString();
                                                }
                                            }
                                            pojo.setLastCrAmt(lastCrAmt);
                                            pojo.setAcStatus(accStatus);
                                            pojo.setAcStatus(accStatusDt);
                                            pojo.setRenewalDt(renewalDt);
                                            pojo.setAssetClassDt(assetClassDt);
                                            pojo.setAcStatus(accStatus);
                                            pojo.setAcStatusDt(accStatus);
                                            pojo.setWriteOffAmt("0");
                                            pojo.setSettledAmt("0");
                                            pojo.setRestureReason("");
                                            pojo.setNpaAmt(npaAmt);
                                            pojo.setAsstSecCover(sicCode);
                                            pojo.setGuaranteeCover(guaranteeCover);
                                            pojo.setBnkRemarkCode("");
                                            pojo.setDefaultStatus("");
                                            pojo.setDefaultStatusDt("");
                                            pojo.setSuitStatus("");
                                            pojo.setSuitAmt("0");
                                            pojo.setSuitDt("");
                                            pojo.setSuitRefNo("");
                                            pojo.setDisputeIdNo("");
                                            pojo.setTxnTypeCode("");
                                            pojo.setGuarantorReasonForDisHonour("");
                                            if (accStatus.equalsIgnoreCase("3")) {
                                                writtrnOff = "1";
                                            } else if (accStatus.equalsIgnoreCase("14")) {
                                                writtrnOff = "7";
                                            } else {
                                                writtrnOff = "";
                                            }

                                            /*
                                            01 =Standard
                                            02 =Sub-Standard
                                            03 =Doubtful
                                            04 =Loss
                                            05 =Special Mention Account
                                             */
                                            overDueDays = "";

//                                            guarantorValueOfSec = vect.get(0).toString();
//                                            guarantorCurrencyType = "INR";
//                                            guarantorTypeOfSec = vect.get(1).toString();
//                                            guarantorSecurityClass = vect.get(2).toString();
//                                            guarantorDateOfValuation = vect.get(3).toString();
                                            /*
                                             * guarantorSegmentIdentifier="",
                                             * guarantorDateOfDishonour="",
                                             * guarantorAmt="",
                                             * guarantorChqNo="",
                                             * guarantorNoOfDisHonour="",
                                             * guarantorChqIssueDt="",
                                             * guarantorReasonForDisHonour=""
                                             */
                                            pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                            pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                            if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                                pojo.setGuarantorTypeOfSec("01");
                                            } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                                pojo.setGuarantorTypeOfSec("02");
                                            } else {
                                                pojo.setGuarantorTypeOfSec("02");
                                            }
                                            pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                            pojo.setGuarantorDateOfValuation(guarantorDateOfValuation);
                                            pojo.setGuarantorDUNs("");
                                            pojo.setGuarantorType("");
                                            pojo.setBusinessCatGuarantor("");
                                            pojo.setBusinessTypeGuarantor("");
                                            pojo.setGuarantorEntityName(guarantor_entity_name);
                                            pojo.setGuarantorPrefix(guarantor_prefix);
                                            pojo.setGuarantorFullName(guarantor_name);
                                            pojo.setGuarantorGender(guarantor_gender);
                                            pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                            pojo.setGuarantorDOI(guarantor_doi);
                                            pojo.setGuarantorDob(dmy.format(ymdhms.parse(guarantor_dob)));
                                            pojo.setGuarantorPan(guarantor_pan);
                                            pojo.setGuarantorVotedID(guarantor_votrid);
                                            pojo.setGuarantorPassport(guarantor_passport);
                                            pojo.setGuarantorDLId(guarantor_dl);
                                            pojo.setGuarantorUID(guarantor_uid);
                                            pojo.setRationCardNo(guarantor_ration);
                                            pojo.setGuarantorCIN(guarantor_cin);
                                            pojo.setGuarantorDIN(guarantor_din);
                                            pojo.setGuarantorTIN(guarantor_tin);
                                            pojo.setGuarantorSTax(guarantor_stax);
                                            pojo.setGuarantorOthId(guarantor_otherid);
                                            pojo.setGuarantorAdd1(guarantor_add1);
                                            pojo.setGuarantorAdd2(guarantor_add2);
                                            pojo.setGuarantorAdd3(guarantor_add3);
                                            pojo.setGuarantorCity(guarantor_city);
                                            pojo.setGuarantorDistrict(guarantor_district);
                                            pojo.setGuarantorState(guarantor_state);
                                            pojo.setGuarantorPin(guarantor_pincode);
                                            pojo.setGuarantorCountry(guarantor_country);
                                            pojo.setGuarantorMobile(guarantor_mobile);
                                            pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                            pojo.setGuarantorTelNo(guarantor_tel_no);
                                            pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                            pojo.setGuarantorFaxNo(guarantor_fax_no);
                                            experionList.add(pojo);
                                        }
                                    } else {
                                        pojo = new EquifaxComercialPoJo();
                                        pojo.setMemberCode(memberCode);
                                        pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                        pojo.setMemberName(custName);
                                        pojo.setShortName(shortNm);
                                        pojo.setRegNo("");
                                        pojo.setDoi(doi);
                                        pojo.setPan(panGirNumber);
                                        pojo.setCin(cin);
                                        pojo.setTin(tin);
                                        pojo.setsTax(sTax);
                                        pojo.setOtherId(otheriId);
                                        pojo.setConstNo("");
                                        pojo.setBusinessCat("");
                                        pojo.setBusinessType("");
                                        pojo.setClassOfAct1(clsOfAct1);
                                        pojo.setClassOfAct2(clsOfAct2);
                                        pojo.setClassOfAct3(clsOfAct3);
                                        pojo.setSicCode(sicCode);
                                        pojo.setSalesFigure(salesFigure);
                                        pojo.setFinYear(finYear);
                                        pojo.setNoOfEmp(noOfEmp);
                                        pojo.setCreditRating(creditRating);
                                        pojo.setAuthority(authority);
                                        pojo.setCreditRatingDt(creditRatingAsOn);
                                        pojo.setCreditRatingExpDt(creditExpDt);
                                        pojo.setLocationType(locationType);
                                        pojo.setDunsNo(dunsNo);
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
                                        pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        pojo.setCity(mailCityCode);
                                        pojo.setDistrict(mailCityCode);
                                        pojo.setState(mailStateCode);
                                        pojo.setCountry(country);
                                        pojo.setMobile(mobile);
                                        pojo.setTelAreaCode(telAreCode);
                                        pojo.setTelNo(telephoneNumber);
                                        pojo.setFaxArea(faxAreaCode);
                                        pojo.setFaxNo(faxNo);
                                        pojo.setRelDunsNo(dunsNo);
                                        pojo.setRelatedType(relatedType);
                                        pojo.setRelationShip(relationShip);
                                        pojo.setBusinessEntityName(jtBussEntity);
                                        pojo.setBusinessCat1(jtBussCat);
                                        pojo.setTypeOfBusiness(jtBussType);
                                        pojo.setIndPreFix(jtTitle);
                                        pojo.setFullName(jtCustFullName);
                                        pojo.setGender(jtGender);
                                        pojo.setRegNoCom(jtRegNo);
                                        pojo.setDoi(jtDoi);
                                        pojo.setDob(jtDob);
                                        pojo.setPanNo(jtPAN);
                                        pojo.setVoterID(jtVoterID);
                                        pojo.setPassportNo(jtPasspost);
                                        pojo.setDlID(jtDlNo);
                                        pojo.setUid(jtAadhar);
                                        pojo.setRationCardNo(jtRationCard);
                                        pojo.setCinNo(jtCin);
                                        pojo.setDinNo(jtDin);
                                        pojo.setTinNo(jtTin);
                                        pojo.setsTaxNo(jtSTax);
                                        pojo.setOtherIdNo(jtOtherID);
                                        pojo.setPercControl(jtPerControl);
                                        pojo.setAdd1(jtAdd1);
                                        pojo.setAdd2(jtAdd2);
                                        pojo.setAdd3(jtAdd3);
                                        pojo.setCityTown(jtcity);
                                        pojo.setDistrict1(jtcity);
                                        pojo.setState1(jtState);
                                        pojo.setPin1(jtPinCode);
                                        pojo.setCountry1(jtCountry);
                                        pojo.setMobile1(jtMobile);
                                        pojo.setTelAreaCode1(jtTelArea);
                                        pojo.setTelNo1(jtTelNo);
                                        pojo.setFaxArea1(jtFaxArea);
                                        pojo.setFaxNo1(jtFaxNo);
                                        pojo.setAcNo(acNo);
                                        pojo.setPreAcno("");
                                        pojo.setSanctDt(sanctionDt);
                                        int overDueAmt =0;
                                        if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                    + "where acno =  '" + acNo + "' and txnid = "
                                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                            if (!sanctionLimitDtList.isEmpty()) {
                                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                odLimit = vist.get(1).toString();
                                                pojo.setSancAmt(odLimit);
                                            } else {
                                                pojo.setSancAmt(odLimit);
                                            }
//                                            if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                            } else {
//                                                amtOverdue = "0";
//                                            }
//                                            pojo.setOdBuct1(amtOverdue);
                                            List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                            OverDueList = OverdueCaList;
                                            if (!OverDueList.isEmpty()) {
                                                for (int s = 0; s < OverDueList.size(); s++) {
                                                    OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                    if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                        overDueAmt = (int) odVect.getOverDue();
                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                            odBuc1 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                            odBuc2 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                            odBuc3 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                            odBuc4 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                            odBuc5 = String.valueOf(overDueAmt);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
//                                            List excessList = null;
//                                            double excess = 0;
//                                            if (isExcessEmi == 0) {
//                                                excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                            } else {
//                                                excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                        + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                            }
//                                            if (!excessList.isEmpty()) {
//                                                if (!excessList.isEmpty()) {
//                                                    Vector ele = (Vector) excessList.get(0);
//                                                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                        excess = Double.parseDouble(ele.get(0).toString());
//                                                    }
//                                                }
//                                            }
//                                            List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                    + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                            if (!l6.isEmpty()) {
//                                                vtr1 = (Vector) l6.get(0);
//                                                amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                    amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                }
//                                                double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                amtOverdue = String.valueOf(overDueAmt);
//                                                pojo.setOdBuct1(amtOverdue);
//                                            }
//                                            List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                            if (!l7.isEmpty()) {
//                                                vtr1 = (Vector) l7.get(0);
//                                                overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                            }
                                            List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                            List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                            if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                OverDueList = OverdueDlList;
                                                if (!OverDueList.isEmpty()) {
                                                    for (int s = 0; s < OverDueList.size(); s++) {
                                                        OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                        if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = (int) odVect.getOverDue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                OverDueTLList = OverdueTlList;
                                                if (!OverDueTLList.isEmpty()) {
                                                    for (int s = 0; s < OverDueTLList.size(); s++) {
                                                        OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                        if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = odVect.getAmountOverdue().intValue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        pojo.setCurrencyCode("INR");
                                        pojo.setCreditType(creditType);
                                        List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                        if (!lemi.isEmpty()) {
                                            Vector vtrEmi = (Vector) lemi.get(0);
                                            repaymentTenure = vtrEmi.get(0).toString();
                                            emiAmt = vtrEmi.get(1).toString();
                                            periodicity = vtrEmi.get(2).toString();
                                            pojo.setPeriod(repaymentTenure);
                                            pojo.setEmiAmt(emiAmt);
                                        } else {
                                            pojo.setPeriod("0");
                                            pojo.setEmiAmt("0");
                                        }
                                        if (periodicity.equalsIgnoreCase("W")) {
                                            pojo.setRepaymentFreq("01");
                                        } else if (periodicity.equalsIgnoreCase("M")) {
                                            pojo.setRepaymentFreq("03");
                                        } else if (periodicity.equalsIgnoreCase("Q")) {
                                            pojo.setRepaymentFreq("04");
                                        } else {
                                            pojo.setRepaymentFreq("04");
                                        }
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
                                        pojo.setDrawingPower(dp);
                                        List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                        if (!l2.isEmpty()) {
                                            vtr1 = (Vector) l2.get(0);
                                            currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                        }
                                        pojo.setOutstanding(currentBal);
                                        pojo.setAmtRestructured(amtRestruct);
                                        pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                        List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                + " and effdt <='" + toDate + "'").getResultList();
                                        if (!assetClsDt.isEmpty()) {
                                            Vector vect = (Vector) assetClsDt.get(0);
                                            accStatus = vect.get(0).toString();
                                        } else {
                                            accStatus = accStatus;
                                        }
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
//                                            if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                assetClass = "01";
//                                            } else {
//                                                assetClass = "05";
//                                            }
                                            assetClass = "01";
                                        } else {
                                            assetClass = "05";
                                        }
                                        pojo.setAssetClassif(assetClass);
                                        pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                        pojo.setOdBuct1(odBuc1);
                                        pojo.setOdBuct2(odBuc2);
                                        pojo.setOdBuct3(odBuc3);
                                        pojo.setOdBuct4(odBuc4);
                                        pojo.setOdBuct5(odBuc5);
                                        pojo.setHighCredit(highCredit);
                                        List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                        if (!l4.isEmpty()) {
                                            vtr1 = (Vector) l4.get(0);
                                            lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                            List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                            if (!crAmt.isEmpty()) {
                                                Vector crvect = (Vector) crAmt.get(0);
                                                lastCrAmt = crvect.get(0).toString();
                                            }
                                        }
                                        pojo.setLastCrAmt(lastCrAmt);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setAcStatus(accStatusDt);
                                        pojo.setRenewalDt(renewalDt);
                                        pojo.setAssetClassDt(assetClassDt);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setAcStatusDt(accStatus);
                                        pojo.setWriteOffAmt("0");
                                        pojo.setSettledAmt("0");
                                        pojo.setRestureReason("");
                                        pojo.setNpaAmt(npaAmt);
                                        pojo.setAsstSecCover(sicCode);
                                        pojo.setGuaranteeCover(guaranteeCover);
                                        pojo.setBnkRemarkCode("");
                                        pojo.setDefaultStatus("");
                                        pojo.setDefaultStatusDt("");
                                        pojo.setSuitStatus("");
                                        pojo.setSuitAmt("0");
                                        pojo.setSuitDt("");
                                        pojo.setSuitRefNo("");
                                        pojo.setDisputeIdNo("");
                                        pojo.setTxnTypeCode("");
                                        pojo.setGuarantorReasonForDisHonour("");
                                        if (accStatus.equalsIgnoreCase("3")) {
                                            writtrnOff = "1";
                                        } else if (accStatus.equalsIgnoreCase("14")) {
                                            writtrnOff = "7";
                                        } else {
                                            writtrnOff = "";
                                        }

                                        /*
                                        01 =Standard
                                        02 =Sub-Standard
                                        03 =Doubtful
                                        04 =Loss
                                        05 =Special Mention Account
                                         */
                                        overDueDays = "";
                                        /*
                                         * guarantorSegmentIdentifier="",
                                         * guarantorDateOfDishonour="",
                                         * guarantorAmt="",
                                         * guarantorChqNo="",
                                         * guarantorNoOfDisHonour="",
                                         * guarantorChqIssueDt="",
                                         * guarantorReasonForDisHonour=""
                                         */
                                        pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                        pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                        if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                            pojo.setGuarantorTypeOfSec("01");
                                        } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                            pojo.setGuarantorTypeOfSec("02");
                                        } else {
                                            pojo.setGuarantorTypeOfSec("02");
                                        }
                                        pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                        pojo.setGuarantorDateOfValuation(guarantorDateOfValuation);
                                        pojo.setGuarantorDUNs("");
                                        pojo.setGuarantorType("");
                                        pojo.setBusinessCatGuarantor("");
                                        pojo.setBusinessTypeGuarantor("");
                                        pojo.setGuarantorEntityName(guarantor_entity_name);
                                        pojo.setGuarantorPrefix(guarantor_prefix);
                                        pojo.setGuarantorFullName(vtr.get(3).toString());
                                        pojo.setGuarantorGender(guarantor_gender);
                                        pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                        pojo.setGuarantorDOI(guarantor_doi);
                                        /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                        pojo.setGuarantorDob(guarantor_dob);
                                        pojo.setGuarantorPan(guarantor_pan);
                                        pojo.setGuarantorVotedID(guarantor_votrid);
                                        pojo.setGuarantorPassport(guarantor_passport);
                                        pojo.setGuarantorDLId(guarantor_dl);
                                        pojo.setGuarantorUID(guarantor_uid);
                                        pojo.setRationCardNo(guarantor_ration);
                                        pojo.setGuarantorCIN(guarantor_cin);
                                        pojo.setGuarantorDIN(guarantor_din);
                                        pojo.setGuarantorTIN(guarantor_tin);
                                        pojo.setGuarantorSTax(guarantor_stax);
                                        pojo.setGuarantorOthId(guarantor_otherid);
                                        pojo.setGuarantorAdd1(vtr.get(1).toString());
                                        pojo.setGuarantorAdd2(guarantor_add2);
                                        pojo.setGuarantorAdd3(guarantor_add3);
                                        pojo.setGuarantorCity(guarantor_city);
                                        pojo.setGuarantorDistrict(guarantor_district);
                                        pojo.setGuarantorState(guarantor_state);
                                        pojo.setGuarantorPin(guarantor_pincode);
                                        pojo.setGuarantorCountry(guarantor_country);
                                        pojo.setGuarantorMobile(guarantor_mobile);
                                        pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                        pojo.setGuarantorTelNo(vtr.get(0).toString());
                                        pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                        pojo.setGuarantorFaxNo(guarantor_fax_no);
                                        experionList.add(pojo);
                                    }
                                }
                            }
                        }
                    } else {
                        /*If Guarantor Details Does not Exist.*/
                        String garCustFlag = "";
                        String garCustAcNo = "";
                        String garCustId = "";
                        List loanSecurity = em.createNativeQuery("select lienvalue,ifnull(security,''),ifnull(SecurityType,''),ifnull(Issuedate,'') from loansecurity where acno ='" + acNo + "' and  status ='Active' ").getResultList();
                        if (!loanSecurity.isEmpty()) {
                            /*If Loan Security Exist*/
                            for (int l = 0; l < loanSecurity.size(); l++) {
                                Vector vect = (Vector) loanSecurity.get(l);
                                guarantorValueOfSec = vect.get(0).toString();
                                guarantorCurrencyType = "INR";
                                guarantorTypeOfSec = vect.get(1).toString();
                                guarantorSecurityClass = vect.get(2).toString();
                                guarantorDateOfValuation = vect.get(3).toString();
                                if (custIdList.size() > 0) {
                                    for (int c = 0; c < custIdList.size(); c++) {
                                        pojo = new EquifaxComercialPoJo();
                                        String jtCustId = "";
                                        String custId = (String) custIdList.get(c);
                                        jtCustId = custId.toString();
//                                        if (custIdList.size() == 1) {
//                                            jtCustId = custIdList.get(0).toString();
//                                        } else {
//                                            Vector custId = (Vector) custIdList.get(c);
//                                            jtCustId = custId.get(0).toString();
//                                        }
//                                        System.out.println("jtCust id" + jtCustId);
                                        List jtCustdetail = em.createNativeQuery("select ifnull(c.title,''),ifnull(c.CustFullName,''),case c.gender when 'M' then '2'  "
                                                + " when 'F' then '1' when 'O' then '3'  when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
                                                + " ifnull(c.PAN_GIRNumber,''),ifnull(c.VoterIDNo,''),ifnull(c.PassportNo,''),ifnull(c.DrivingLicenseNo,''),ifnull(aa.IdentityNo,''),ifnull(c.cin,''),ifnull(c.TINNumber,''),ifnull(c.SalesTaxNo,''),"
                                                + " ifnull(c.other_identity,''),ifnull(c.MailAddressLine1,''), ifnull(c.MailAddressLine2,'') ,"
                                                + " ifnull((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode),'') as MailCityCode, "
                                                + " ifnull((select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode),'') as mailstatecode, "
                                                + " ifnull(c.MailPostalCode,''),ifnull(c.MailCountryCode,''),ifnull(c.MailPhoneNumber,''),ifnull(c.MailTelexNumber,''),ifnull(c.MailFaxNumber,'') "
                                                + " from cbs_customer_master_detail c "
                                                + " left outer join "
                                                + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                                                + " union "
                                                + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                                                + " where c.customerid='" + jtCustId + "';").getResultList();
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
//                                            jtCountry = jtVect.get(18).toString();
                                            jtMobile = jtVect.get(19).toString();
                                            jtTelNo = jtVect.get(20).toString();
                                            jtFaxNo = jtVect.get(21).toString();
                                        }
                                        pojo = new EquifaxComercialPoJo();
                                        pojo.setMemberCode(memberCode);
                                        pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                        pojo.setMemberName(custName);
                                        pojo.setShortName(shortNm);
                                        pojo.setRegNo("");
                                        pojo.setDoi(doi);
                                        pojo.setPan(panGirNumber);
                                        pojo.setCin(cin);
                                        pojo.setTin(tin);
                                        pojo.setsTax(sTax);
                                        pojo.setOtherId(otheriId);
                                        pojo.setConstNo("");
                                        pojo.setBusinessCat("");
                                        pojo.setBusinessType("");
                                        pojo.setClassOfAct1(clsOfAct1);
                                        pojo.setClassOfAct2(clsOfAct2);
                                        pojo.setClassOfAct3(clsOfAct3);
                                        pojo.setSicCode(sicCode);
                                        pojo.setSalesFigure(salesFigure);
                                        pojo.setFinYear(finYear);
                                        pojo.setNoOfEmp(noOfEmp);
                                        pojo.setCreditRating(creditRating);
                                        pojo.setAuthority(authority);
                                        pojo.setCreditRatingDt(creditRatingAsOn);
                                        pojo.setCreditRatingExpDt(creditExpDt);
                                        pojo.setLocationType(locationType);
                                        pojo.setDunsNo(dunsNo);
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
                                        pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        pojo.setCity(mailCityCode);
                                        pojo.setDistrict(mailCityCode);
                                        pojo.setState(mailStateCode);
                                        pojo.setCountry(country);
                                        pojo.setMobile(mobile);
                                        pojo.setTelAreaCode(telAreCode);
                                        pojo.setTelNo(telephoneNumber);
                                        pojo.setFaxArea(faxAreaCode);
                                        pojo.setFaxNo(faxNo);
                                        pojo.setRelDunsNo(dunsNo);
                                        pojo.setRelatedType(relatedType);
                                        pojo.setRelationShip(relationShip);
                                        pojo.setBusinessEntityName(jtBussEntity);
                                        pojo.setBusinessCat1(jtBussCat);
                                        pojo.setTypeOfBusiness(jtBussType);
                                        pojo.setIndPreFix(jtTitle);
                                        pojo.setFullName(jtCustFullName);
                                        pojo.setGender(jtGender);
                                        pojo.setRegNoCom(jtRegNo);
                                        pojo.setDoi(jtDoi);
//                                        pojo.setDob(y_m_d.parse(jtDob).equals("01011900") ? "" : dmy.format(y_m_d.parse(jtDob)));
                                        pojo.setPanNo(jtPAN);
                                        pojo.setVoterID(jtVoterID);
                                        pojo.setPassportNo(jtPasspost);
                                        pojo.setDlID(jtDlNo);
                                        pojo.setUid(jtAadhar);
                                        pojo.setRationCardNo(jtRationCard);
                                        pojo.setCinNo(jtCin);
                                        pojo.setDinNo(jtDin);
                                        pojo.setTinNo(jtTin);
                                        pojo.setsTaxNo(jtSTax);
                                        pojo.setOtherIdNo(jtOtherID);
                                        pojo.setPercControl(jtPerControl);
                                        pojo.setAdd1(jtAdd1);
                                        pojo.setAdd2(jtAdd2);
                                        pojo.setAdd3(jtAdd3);
                                        pojo.setCityTown(jtcity);
                                        pojo.setDistrict1(jtcity);
                                        pojo.setState1(jtState);
                                        pojo.setPin1(jtPinCode);
                                        pojo.setCountry1(jtCountry);
                                        pojo.setMobile1(jtMobile);
                                        pojo.setTelAreaCode1(jtTelArea);
                                        pojo.setTelNo1(jtTelNo);
                                        pojo.setFaxArea1(jtFaxArea);
                                        pojo.setFaxNo1(jtFaxNo);
                                        pojo.setAcNo(acNo);
                                        pojo.setPreAcno("");
                                        pojo.setSanctDt(sanctionDt);
                                        int overDueAmt=0;
                                        if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                    + "where acno =  '" + acNo + "' and txnid = "
                                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                            if (!sanctionLimitDtList.isEmpty()) {
                                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                odLimit = vist.get(1).toString();
                                                pojo.setSancAmt(odLimit);
                                            } else {
                                                pojo.setSancAmt(odLimit);
                                            }
//                                            if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                                amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                            } else {
//                                                amtOverdue = "0";
//                                            }
//                                            pojo.setOdBuct1(amtOverdue);
                                            List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                            OverDueList = OverdueCaList;
                                            if (!OverDueList.isEmpty()) {
                                                for (int s = 0; s < OverDueList.size(); s++) {
                                                    OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                    if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                        overDueAmt = (int) odVect.getOverDue();
                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                            odBuc1 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                            odBuc2 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                            odBuc3 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                            odBuc4 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                            odBuc5 = String.valueOf(overDueAmt);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
//                                            List excessList = null;
//                                            double excess = 0;
//                                            if (isExcessEmi == 0) {
//                                                excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                            } else {
//                                                excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                        + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                            }
//                                            if (!excessList.isEmpty()) {
//                                                if (!excessList.isEmpty()) {
//                                                    Vector ele = (Vector) excessList.get(0);
//                                                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                        excess = Double.parseDouble(ele.get(0).toString());
//                                                    }
//                                                }
//                                            }
//                                            List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                    + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                            if (!l6.isEmpty()) {
//                                                vtr1 = (Vector) l6.get(0);
//                                                amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                                if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                    amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                                }
//                                                double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                                amtOverdue = String.valueOf(overDueAmt);
//                                                pojo.setOdBuct1(amtOverdue);
//                                            }
//                                            List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                            if (!l7.isEmpty()) {
//                                                vtr1 = (Vector) l7.get(0);
//                                                overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                            }
                                            List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                            List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                            if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                                OverDueList = OverdueDlList;
                                                if (!OverDueList.isEmpty()) {
                                                    for (int s = 0; s < OverDueList.size(); s++) {
                                                        OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                        if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = (int) odVect.getOverDue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                                OverDueTLList = OverdueTlList;
                                                if (!OverDueTLList.isEmpty()) {
                                                    for (int s = 0; s < OverDueTLList.size(); s++) {
                                                        OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                        if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                            overDueAmt = odVect.getAmountOverdue().intValue();
                                                            if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                                odBuc1 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                                odBuc2 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                                odBuc3 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                                odBuc4 = String.valueOf(overDueAmt);
                                                            } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                                odBuc5 = String.valueOf(overDueAmt);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        pojo.setCurrencyCode("INR");
                                        pojo.setCreditType(creditType);
                                        List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                        if (!lemi.isEmpty()) {
                                            Vector vtrEmi = (Vector) lemi.get(0);
                                            repaymentTenure = vtrEmi.get(0).toString();
                                            emiAmt = vtrEmi.get(1).toString();
                                            periodicity = vtrEmi.get(2).toString();
                                            pojo.setPeriod(repaymentTenure);
                                            pojo.setEmiAmt(emiAmt);
                                        } else {
                                            pojo.setPeriod("0");
                                            pojo.setEmiAmt("0");
                                        }
                                        if (periodicity.equalsIgnoreCase("W")) {
                                            pojo.setRepaymentFreq("01");
                                        } else if (periodicity.equalsIgnoreCase("M")) {
                                            pojo.setRepaymentFreq("03");
                                        } else if (periodicity.equalsIgnoreCase("Q")) {
                                            pojo.setRepaymentFreq("04");
                                        } else {
                                            pojo.setRepaymentFreq("04");
                                        }
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
                                        pojo.setDrawingPower(dp);
                                        List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                        if (!l2.isEmpty()) {
                                            vtr1 = (Vector) l2.get(0);
                                            currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                        }
                                        pojo.setOutstanding(currentBal);
                                        pojo.setAmtRestructured(amtRestruct);
                                        pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                        List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                                + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                                + " and effdt <='" + toDate + "'").getResultList();
                                        if (!assetClsDt.isEmpty()) {
                                            Vector vect1 = (Vector) assetClsDt.get(0);
                                            accStatus = vect1.get(0).toString();
                                        } else {
                                            accStatus = accStatus;
                                        }
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
//                                            if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                                assetClass = "01";
//                                            } else {
//                                                assetClass = "05";
//                                            }
                                            assetClass = "01";
                                        } else {
                                            assetClass = "05";
                                        }
                                        pojo.setAssetClassif(assetClass);
                                        pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                        pojo.setOdBuct1(odBuc1);
                                        pojo.setOdBuct2(odBuc2);
                                        pojo.setOdBuct3(odBuc3);
                                        pojo.setOdBuct4(odBuc4);
                                        pojo.setOdBuct5(odBuc5);
                                        pojo.setHighCredit(highCredit);
                                        List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                        if (!l4.isEmpty()) {
                                            vtr1 = (Vector) l4.get(0);
                                            lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                            List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                            if (!crAmt.isEmpty()) {
                                                Vector crvect = (Vector) crAmt.get(0);
                                                lastCrAmt = crvect.get(0).toString();
                                            }
                                        }
                                        pojo.setLastCrAmt(lastCrAmt);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setAcStatus(accStatusDt);
                                        pojo.setRenewalDt(renewalDt);
                                        pojo.setAssetClassDt(assetClassDt);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setAcStatusDt(accStatus);
                                        pojo.setWriteOffAmt("0");
                                        pojo.setSettledAmt("0");
                                        pojo.setRestureReason("");
                                        pojo.setNpaAmt(npaAmt);
                                        pojo.setAsstSecCover(sicCode);
                                        pojo.setGuaranteeCover(guaranteeCover);
                                        pojo.setBnkRemarkCode("");
                                        pojo.setDefaultStatus("");
                                        pojo.setDefaultStatusDt("");
                                        pojo.setSuitStatus("");
                                        pojo.setSuitAmt("0");
                                        pojo.setSuitDt("");
                                        pojo.setSuitRefNo("");
                                        pojo.setDisputeIdNo("");
                                        pojo.setTxnTypeCode("");
                                        pojo.setGuarantorReasonForDisHonour("");
                                        if (accStatus.equalsIgnoreCase("3")) {
                                            writtrnOff = "1";
                                        } else if (accStatus.equalsIgnoreCase("14")) {
                                            writtrnOff = "7";
                                        } else {
                                            writtrnOff = "";
                                        }

                                        /*
                                        01 =Standard
                                        02 =Sub-Standard
                                        03 =Doubtful
                                        04 =Loss
                                        05 =Special Mention Account
                                         */
                                        overDueDays = "";
                                        /*
                                         * guarantorSegmentIdentifier="",
                                         * guarantorDateOfDishonour="",
                                         * guarantorAmt="",
                                         * guarantorChqNo="",
                                         * guarantorNoOfDisHonour="",
                                         * guarantorChqIssueDt="",
                                         * guarantorReasonForDisHonour=""
                                         */
                                        pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                        pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                        if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                            pojo.setGuarantorTypeOfSec("01");
                                        } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                            pojo.setGuarantorTypeOfSec("02");
                                        } else {
                                            pojo.setGuarantorTypeOfSec("02");
                                        }
                                        pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                        pojo.setGuarantorDateOfValuation(guarantorDateOfValuation == "" ? dmy.format(ymdhms.parse(guarantorDateOfValuation)) : "");
                                        pojo.setGuarantorDUNs("");
                                        pojo.setGuarantorType("");
                                        pojo.setBusinessCatGuarantor("");
                                        pojo.setBusinessTypeGuarantor("");
                                        pojo.setGuarantorEntityName(guarantor_entity_name);
                                        pojo.setGuarantorPrefix(guarantor_prefix);
                                        pojo.setGuarantorFullName(guarantor_name);
                                        pojo.setGuarantorGender(guarantor_gender);
                                        pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                        pojo.setGuarantorDOI(guarantor_doi);
                                        /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                        pojo.setGuarantorDob(guarantor_dob);
                                        pojo.setGuarantorPan(guarantor_pan);
                                        pojo.setGuarantorVotedID(guarantor_votrid);
                                        pojo.setGuarantorPassport(guarantor_passport);
                                        pojo.setGuarantorDLId(guarantor_dl);
                                        pojo.setGuarantorUID(guarantor_uid);
                                        pojo.setRationCardNo(guarantor_ration);
                                        pojo.setGuarantorCIN(guarantor_cin);
                                        pojo.setGuarantorDIN(guarantor_din);
                                        pojo.setGuarantorTIN(guarantor_tin);
                                        pojo.setGuarantorSTax(guarantor_stax);
                                        pojo.setGuarantorOthId(guarantor_otherid);
                                        pojo.setGuarantorAdd1(guarantor_add1);
                                        pojo.setGuarantorAdd2(guarantor_add2);
                                        pojo.setGuarantorAdd3(guarantor_add3);
                                        pojo.setGuarantorCity(guarantor_city);
                                        pojo.setGuarantorDistrict(guarantor_district);
                                        pojo.setGuarantorState(guarantor_state);
                                        pojo.setGuarantorPin(guarantor_pincode);
                                        pojo.setGuarantorCountry(guarantor_country);
                                        pojo.setGuarantorMobile(guarantor_mobile);
                                        pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                        pojo.setGuarantorTelNo(guarantor_tel_no);
                                        pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                        pojo.setGuarantorFaxNo(guarantor_fax_no);
                                        experionList.add(pojo);
                                        
                                    }
                                } else {
                                    pojo = new EquifaxComercialPoJo();
                                    pojo.setMemberCode(memberCode);
                                    pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                    pojo.setMemberName(custName);
                                    pojo.setShortName(shortNm);
                                    pojo.setRegNo("");
                                    pojo.setDoi(doi);
                                    pojo.setPan(panGirNumber);
                                    pojo.setCin(cin);
                                    pojo.setTin(tin);
                                    pojo.setsTax(sTax);
                                    pojo.setOtherId(otheriId);
                                    pojo.setConstNo("");
                                    pojo.setBusinessCat("");
                                    pojo.setBusinessType("");
                                    pojo.setClassOfAct1(clsOfAct1);
                                    pojo.setClassOfAct2(clsOfAct2);
                                    pojo.setClassOfAct3(clsOfAct3);
                                    pojo.setSicCode(sicCode);
                                    pojo.setSalesFigure(salesFigure);
                                    pojo.setFinYear(finYear);
                                    pojo.setNoOfEmp(noOfEmp);
                                    pojo.setCreditRating(creditRating);
                                    pojo.setAuthority(authority);
                                    pojo.setCreditRatingDt(creditRatingAsOn);
                                    pojo.setCreditRatingExpDt(creditExpDt);
                                    pojo.setLocationType(locationType);
                                    pojo.setDunsNo(dunsNo);
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
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    pojo.setCity(mailCityCode);
                                    pojo.setDistrict(mailCityCode);
                                    pojo.setState(mailStateCode);
                                    pojo.setCountry(country);
                                    pojo.setMobile(mobile);
                                    pojo.setTelAreaCode(telAreCode);
                                    pojo.setTelNo(telephoneNumber);
                                    pojo.setFaxArea(faxAreaCode);
                                    pojo.setFaxNo(faxNo);
                                    pojo.setRelDunsNo(dunsNo);
                                    pojo.setRelatedType(relatedType);
                                    pojo.setRelationShip(relationShip);
                                    pojo.setBusinessEntityName(jtBussEntity);
                                    pojo.setBusinessCat1(jtBussCat);
                                    pojo.setTypeOfBusiness(jtBussType);
                                    pojo.setIndPreFix(jtTitle);
                                    pojo.setFullName(jtCustFullName);
                                    pojo.setGender(jtGender);
                                    pojo.setRegNoCom(jtRegNo);
                                    pojo.setDoi(jtDoi);
//                                    pojo.setDob(y_m_d.parse(jtDob).equals("01011900") ? "" : dmy.format(y_m_d.parse(jtDob)));
                                    pojo.setPanNo(jtPAN);
                                    pojo.setVoterID(jtVoterID);
                                    pojo.setPassportNo(jtPasspost);
                                    pojo.setDlID(jtDlNo);
                                    pojo.setUid(jtAadhar);
                                    pojo.setRationCardNo(jtRationCard);
                                    pojo.setCinNo(jtCin);
                                    pojo.setDinNo(jtDin);
                                    pojo.setTinNo(jtTin);
                                    pojo.setsTaxNo(jtSTax);
                                    pojo.setOtherIdNo(jtOtherID);
                                    pojo.setPercControl(jtPerControl);
                                    pojo.setAdd1(jtAdd1);
                                    pojo.setAdd2(jtAdd2);
                                    pojo.setAdd3(jtAdd3);
                                    pojo.setCityTown(jtcity);
                                    pojo.setDistrict1(jtcity);
                                    pojo.setState1(jtState);
                                    pojo.setPin1(jtPinCode);
                                    pojo.setCountry1(jtCountry);
                                    pojo.setMobile1(jtMobile);
                                    pojo.setTelAreaCode1(jtTelArea);
                                    pojo.setTelNo1(jtTelNo);
                                    pojo.setFaxArea1(jtFaxArea);
                                    pojo.setFaxNo1(jtFaxNo);
                                    pojo.setAcNo(acNo);
                                    pojo.setPreAcno("");
                                    pojo.setSanctDt(sanctionDt);
                                    int overDueAmt =0;
                                    if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                + "where acno =  '" + acNo + "' and txnid = "
                                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                        if (!sanctionLimitDtList.isEmpty()) {
                                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                                            odLimit = vist.get(1).toString();
                                            pojo.setSancAmt(odLimit);
                                        } else {
                                            pojo.setSancAmt(odLimit);
                                        }
//                                        if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                            amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                        } else {
//                                            amtOverdue = "0";
//                                        }
//                                        pojo.setOdBuct1(amtOverdue);
                                        List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                        OverDueList = OverdueCaList;
                                        if (!OverDueList.isEmpty()) {
                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                    overDueAmt = (int) odVect.getOverDue();
                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                        odBuc1 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                        odBuc2 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                        odBuc3 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                        odBuc4 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                        odBuc5 = String.valueOf(overDueAmt);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
//                                        List excessList = null;
//                                        double excess = 0;
//                                        if (isExcessEmi == 0) {
//                                            excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                        } else {
//                                            excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                    + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                        }
//                                        if (!excessList.isEmpty()) {
//                                            if (!excessList.isEmpty()) {
//                                                Vector ele = (Vector) excessList.get(0);
//                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                    excess = Double.parseDouble(ele.get(0).toString());
//                                                }
//                                            }
//                                        }
//                                        List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                        if (!l6.isEmpty()) {
//                                            vtr1 = (Vector) l6.get(0);
//                                            amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                            if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                            }
//                                            double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                            amtOverdue = String.valueOf(overDueAmt);
//                                            pojo.setOdBuct1(amtOverdue);
//                                        }
//                                        List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                        if (!l7.isEmpty()) {
//                                            vtr1 = (Vector) l7.get(0);
//                                            overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                        }
                                        List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                        List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                        if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                            OverDueList = OverdueDlList;
                                            if (!OverDueList.isEmpty()) {
                                                for (int s = 0; s < OverDueList.size(); s++) {
                                                    OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                    if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                        overDueAmt = (int) odVect.getOverDue();
                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                            odBuc1 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                            odBuc2 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                            odBuc3 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                            odBuc4 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                            odBuc5 = String.valueOf(overDueAmt);
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                            OverDueTLList = OverdueTlList;
                                            if (!OverDueTLList.isEmpty()) {
                                                for (int s = 0; s < OverDueTLList.size(); s++) {
                                                    OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                    if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                        overDueAmt = odVect.getAmountOverdue().intValue();
                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                            odBuc1 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                            odBuc2 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                            odBuc3 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                            odBuc4 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                            odBuc5 = String.valueOf(overDueAmt);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    pojo.setCurrencyCode("INR");
                                    pojo.setCreditType(creditType);
                                    List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                    if (!lemi.isEmpty()) {
                                        Vector vtrEmi = (Vector) lemi.get(0);
                                        repaymentTenure = vtrEmi.get(0).toString();
                                        emiAmt = vtrEmi.get(1).toString();
                                        periodicity = vtrEmi.get(2).toString();
                                        pojo.setPeriod(repaymentTenure);
                                        pojo.setEmiAmt(emiAmt);
                                    } else {
                                        pojo.setPeriod("0");
                                        pojo.setEmiAmt("0");
                                    }
                                    if (periodicity.equalsIgnoreCase("W")) {
                                        pojo.setRepaymentFreq("01");
                                    } else if (periodicity.equalsIgnoreCase("M")) {
                                        pojo.setRepaymentFreq("03");
                                    } else if (periodicity.equalsIgnoreCase("Q")) {
                                        pojo.setRepaymentFreq("04");
                                    } else {
                                        pojo.setRepaymentFreq("04");
                                    }
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
                                    pojo.setDrawingPower(dp);
                                    List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                    if (!l2.isEmpty()) {
                                        vtr1 = (Vector) l2.get(0);
                                        currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                    }
                                    pojo.setOutstanding(currentBal);
                                    pojo.setAmtRestructured(amtRestruct);
                                    pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                    List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                            + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                            + " and effdt <='" + toDate + "'").getResultList();
                                    if (!assetClsDt.isEmpty()) {
                                        Vector vect1 = (Vector) assetClsDt.get(0);
                                        accStatus = vect1.get(0).toString();
                                    } else {
                                        accStatus = accStatus;
                                    }
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
//                                        if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                            assetClass = "01";
//                                        } else {
//                                            assetClass = "05";
//                                        }
                                        assetClass = "01";
                                    } else {
                                        assetClass = "05";
                                    }
                                    pojo.setAssetClassif(assetClass);
                                    pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                    pojo.setOdBuct1(odBuc1);
                                    pojo.setOdBuct2(odBuc2);
                                    pojo.setOdBuct3(odBuc3);
                                    pojo.setOdBuct4(odBuc4);
                                    pojo.setOdBuct5(odBuc5);
                                    pojo.setHighCredit(highCredit);
                                    List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                    if (!l4.isEmpty()) {
                                        vtr1 = (Vector) l4.get(0);
                                        lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                        List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                        if (!crAmt.isEmpty()) {
                                            Vector crvect = (Vector) crAmt.get(0);
                                            lastCrAmt = crvect.get(0).toString();
                                        }
                                    }
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setAcStatus(accStatus);
                                    pojo.setAcStatus(accStatusDt);
                                    pojo.setRenewalDt(renewalDt);
                                    pojo.setAssetClassDt(assetClassDt);
                                    pojo.setAcStatus(accStatus);
                                    pojo.setAcStatusDt(accStatus);
                                    pojo.setWriteOffAmt("0");
                                    pojo.setSettledAmt("0");
                                    pojo.setRestureReason("");
                                    pojo.setNpaAmt(npaAmt);
                                    pojo.setAsstSecCover(sicCode);
                                    pojo.setGuaranteeCover(guaranteeCover);
                                    pojo.setBnkRemarkCode("");
                                    pojo.setDefaultStatus("");
                                    pojo.setDefaultStatusDt("");
                                    pojo.setSuitStatus("");
                                    pojo.setSuitAmt("0");
                                    pojo.setSuitDt("");
                                    pojo.setSuitRefNo("");
                                    pojo.setDisputeIdNo("");
                                    pojo.setTxnTypeCode("");
                                    pojo.setGuarantorReasonForDisHonour("");
                                    if (accStatus.equalsIgnoreCase("3")) {
                                        writtrnOff = "1";
                                    } else if (accStatus.equalsIgnoreCase("14")) {
                                        writtrnOff = "7";
                                    } else {
                                        writtrnOff = "";
                                    }

                                    /*
                                    01 =Standard
                                    02 =Sub-Standard
                                    03 =Doubtful
                                    04 =Loss
                                    05 =Special Mention Account
                                     */
                                    overDueDays = "";
                                    /*
                                     * guarantorSegmentIdentifier="",
                                     * guarantorDateOfDishonour="",
                                     * guarantorAmt="",
                                     * guarantorChqNo="",
                                     * guarantorNoOfDisHonour="",
                                     * guarantorChqIssueDt="",
                                     * guarantorReasonForDisHonour=""
                                     */
                                    pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                    pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                    if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                        pojo.setGuarantorTypeOfSec("01");
                                    } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                        pojo.setGuarantorTypeOfSec("02");
                                    } else {
                                        pojo.setGuarantorTypeOfSec("02");
                                    }
                                    pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                    pojo.setGuarantorDateOfValuation(guarantorDateOfValuation == "" ? dmy.format(ymdhms.parse(guarantorDateOfValuation)) : "");
                                    pojo.setGuarantorDUNs("");
                                    pojo.setGuarantorType("");
                                    pojo.setBusinessCatGuarantor("");
                                    pojo.setBusinessTypeGuarantor("");
                                    pojo.setGuarantorEntityName(guarantor_entity_name);
                                    pojo.setGuarantorPrefix(guarantor_prefix);
                                    pojo.setGuarantorFullName(guarantor_name);
                                    pojo.setGuarantorGender(guarantor_gender);
                                    pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                    pojo.setGuarantorDOI(guarantor_doi);
                                    /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                    pojo.setGuarantorDob(guarantor_dob);
                                    pojo.setGuarantorPan(guarantor_pan);
                                    pojo.setGuarantorVotedID(guarantor_votrid);
                                    pojo.setGuarantorPassport(guarantor_passport);
                                    pojo.setGuarantorDLId(guarantor_dl);
                                    pojo.setGuarantorUID(guarantor_uid);
                                    pojo.setRationCardNo(guarantor_ration);
                                    pojo.setGuarantorCIN(guarantor_cin);
                                    pojo.setGuarantorDIN(guarantor_din);
                                    pojo.setGuarantorTIN(guarantor_tin);
                                    pojo.setGuarantorSTax(guarantor_stax);
                                    pojo.setGuarantorOthId(guarantor_otherid);
                                    pojo.setGuarantorAdd1(guarantor_add1);
                                    pojo.setGuarantorAdd2(guarantor_add2);
                                    pojo.setGuarantorAdd3(guarantor_add3);
                                    pojo.setGuarantorCity(guarantor_city);
                                    pojo.setGuarantorDistrict(guarantor_district);
                                    pojo.setGuarantorState(guarantor_state);
                                    pojo.setGuarantorPin(guarantor_pincode);
                                    pojo.setGuarantorCountry(guarantor_country);
                                    pojo.setGuarantorMobile(guarantor_mobile);
                                    pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                    pojo.setGuarantorTelNo(guarantor_tel_no);
                                    pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                    pojo.setGuarantorFaxNo(guarantor_fax_no);
                                    experionList.add(pojo);
                                }
                            }
                        } else {
                            /*If Loan Security Does not Exist.*/
                            if (custIdList.size() > 0) {
                                for (int c = 0; c < custIdList.size(); c++) {
                                    pojo = new EquifaxComercialPoJo();
                                    String jtCustId = "";
//                                    if (custIdList.size() == 1) {
//                                        jtCustId = custIdList.get(0).toString();
//                                    } else {
//                                        Vector custId = (Vector) custIdList.get(c);
//                                        jtCustId = custId.get(0).toString();
//                                    }
                                    String custId = (String) custIdList.get(c);
                                    jtCustId = custId.toString();
                                    List jtCustdetail = em.createNativeQuery("select ifnull(c.title,''),ifnull(c.CustFullName,''),case c.gender when 'M' then '2'  "
                                            + " when 'F' then '1'  when 'O' then '3' when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
                                            + " ifnull(c.PAN_GIRNumber,''),ifnull(c.VoterIDNo,''),ifnull(c.PassportNo,''),ifnull(c.DrivingLicenseNo,''),ifnull(aa.IdentityNo,''),ifnull(c.cin,''),ifnull(c.TINNumber,''),ifnull(c.SalesTaxNo,''),"
                                            + " ifnull(c.other_identity,''),ifnull(c.MailAddressLine1,''), ifnull(c.MailAddressLine2,'') ,"
                                            + " ifnull((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode),'') as MailCityCode, "
                                            + " ifnull((select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode),'') as mailstatecode, "
                                            + " ifnull(c.MailPostalCode,''),ifnull(c.MailCountryCode,''),ifnull(c.MailPhoneNumber,''),ifnull(c.MailTelexNumber,''),ifnull(c.MailFaxNumber,'') "
                                            + " from cbs_customer_master_detail c "
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
//                                        jtCountry = jtVect.get(18).toString();
                                        jtMobile = jtVect.get(19).toString();
                                        jtTelNo = jtVect.get(20).toString();
                                        jtFaxNo = jtVect.get(21).toString();
                                    }
                                    pojo = new EquifaxComercialPoJo();
                                    pojo.setMemberCode(memberCode);
                                    pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                    pojo.setMemberName(custName);
                                    pojo.setShortName(shortNm);
                                    pojo.setRegNo("");
                                    pojo.setDoi(doi);
                                    pojo.setPan(panGirNumber);
                                    pojo.setCin(cin);
                                    pojo.setTin(tin);
                                    pojo.setsTax(sTax);
                                    pojo.setOtherId(otheriId);
                                    pojo.setConstNo("");
                                    pojo.setBusinessCat("");
                                    pojo.setBusinessType("");
                                    pojo.setClassOfAct1(clsOfAct1);
                                    pojo.setClassOfAct2(clsOfAct2);
                                    pojo.setClassOfAct3(clsOfAct3);
                                    pojo.setSicCode(sicCode);
                                    pojo.setSalesFigure(salesFigure);
                                    pojo.setFinYear(finYear);
                                    pojo.setNoOfEmp(noOfEmp);
                                    pojo.setCreditRating(creditRating);
                                    pojo.setAuthority(authority);
                                    pojo.setCreditRatingDt(creditRatingAsOn);
                                    pojo.setCreditRatingExpDt(creditExpDt);
                                    pojo.setLocationType(locationType);
                                    pojo.setDunsNo(dunsNo);
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
                                    pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    pojo.setCity(mailCityCode);
                                    pojo.setDistrict(mailCityCode);
                                    pojo.setState(mailStateCode);
                                    pojo.setCountry(country);
                                    pojo.setMobile(mobile);
                                    pojo.setTelAreaCode(telAreCode);
                                    pojo.setTelNo(telephoneNumber);
                                    pojo.setFaxArea(faxAreaCode);
                                    pojo.setFaxNo(faxNo);
                                    pojo.setRelDunsNo(dunsNo);
                                    pojo.setRelatedType(relatedType);
                                    pojo.setRelationShip(relationShip);
                                    pojo.setBusinessEntityName(jtBussEntity);
                                    pojo.setBusinessCat1(jtBussCat);
                                    pojo.setTypeOfBusiness(jtBussType);
                                    pojo.setIndPreFix(jtTitle);
                                    pojo.setFullName(jtCustFullName);
                                    pojo.setGender(jtGender);
                                    pojo.setRegNoCom(jtRegNo);
                                    pojo.setDoi(jtDoi);
                                    pojo.setDob(jtDob);
                                    pojo.setPanNo(jtPAN);
                                    pojo.setVoterID(jtVoterID);
                                    pojo.setPassportNo(jtPasspost);
                                    pojo.setDlID(jtDlNo);
                                    pojo.setUid(jtAadhar);
                                    pojo.setRationCardNo(jtRationCard);
                                    pojo.setCinNo(jtCin);
                                    pojo.setDinNo(jtDin);
                                    pojo.setTinNo(jtTin);
                                    pojo.setsTaxNo(jtSTax);
                                    pojo.setOtherIdNo(jtOtherID);
                                    pojo.setPercControl(jtPerControl);
                                    pojo.setAdd1(jtAdd1);
                                    pojo.setAdd2(jtAdd2);
                                    pojo.setAdd3(jtAdd3);
                                    pojo.setCityTown(jtcity);
                                    pojo.setDistrict1(jtcity);
                                    pojo.setState1(jtState);
                                    pojo.setPin1(jtPinCode);
                                    pojo.setCountry1(jtCountry);
                                    pojo.setMobile1(jtMobile);
                                    pojo.setTelAreaCode1(jtTelArea);
                                    pojo.setTelNo1(jtTelNo);
                                    pojo.setFaxArea1(jtFaxArea);
                                    pojo.setFaxNo1(jtFaxNo);
                                    pojo.setAcNo(acNo);
                                    pojo.setPreAcno("");
                                    pojo.setSanctDt(sanctionDt);
                                    int overDueAmt = 0;
                                    if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                                + "where acno =  '" + acNo + "' and txnid = "
                                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                        if (!sanctionLimitDtList.isEmpty()) {
                                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                                            odLimit = vist.get(1).toString();
                                            pojo.setSancAmt(odLimit);
                                        } else {
                                            pojo.setSancAmt(odLimit);
                                        }
//                                        if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                            amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                        } else {
//                                            amtOverdue = "0";
//                                        }
//                                        pojo.setOdBuct1(amtOverdue);
                                        List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                        OverDueList = OverdueCaList;
                                        if (!OverDueList.isEmpty()) {
                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                    overDueAmt = (int) odVect.getOverDue();
                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                        odBuc1 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                        odBuc2 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                        odBuc3 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                        odBuc4 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                        odBuc5 = String.valueOf(overDueAmt);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
//                                        List excessList = null;
//                                        double excess = 0;
//                                        if (isExcessEmi == 0) {
//                                            excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                        } else {
//                                            excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                    + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                        }
//                                        if (!excessList.isEmpty()) {
//                                            if (!excessList.isEmpty()) {
//                                                Vector ele = (Vector) excessList.get(0);
//                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                    excess = Double.parseDouble(ele.get(0).toString());
//                                                }
//                                            }
//                                        }
//                                        List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                                + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                        if (!l6.isEmpty()) {
//                                            vtr1 = (Vector) l6.get(0);
//                                            amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                            if (!amtOverdue.equalsIgnoreCase("0")) {
//                                                amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                            }
//                                            double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                            amtOverdue = String.valueOf(overDueAmt);
//                                            pojo.setOdBuct1(amtOverdue);
//                                        }
//                                        List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                        if (!l7.isEmpty()) {
//                                            vtr1 = (Vector) l7.get(0);
//                                            overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                        }
                                        List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                        List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                        if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                            OverDueList = OverdueDlList;
                                            if (!OverDueList.isEmpty()) {
                                                for (int s = 0; s < OverDueList.size(); s++) {
                                                    OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                    if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                        overDueAmt = (int) odVect.getOverDue();
                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                            odBuc1 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                            odBuc2 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                            odBuc3 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                            odBuc4 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                            odBuc5 = String.valueOf(overDueAmt);
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                            OverDueTLList = OverdueTlList;
                                            if (!OverDueTLList.isEmpty()) {
                                                for (int s = 0; s < OverDueTLList.size(); s++) {
                                                    OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                    if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                        overDueAmt = odVect.getAmountOverdue().intValue();
                                                        if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                            odBuc1 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                            odBuc2 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                            odBuc3 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                            odBuc4 = String.valueOf(overDueAmt);
                                                        } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                            odBuc5 = String.valueOf(overDueAmt);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    pojo.setCurrencyCode("INR");
                                    pojo.setCreditType(creditType);
                                    List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                    if (!lemi.isEmpty()) {
                                        Vector vtrEmi = (Vector) lemi.get(0);
                                        repaymentTenure = vtrEmi.get(0).toString();
                                        emiAmt = vtrEmi.get(1).toString();
                                        periodicity = vtrEmi.get(2).toString();
                                        pojo.setPeriod(repaymentTenure);
                                        pojo.setEmiAmt(emiAmt);
                                    } else {
                                        pojo.setPeriod("0");
                                        pojo.setEmiAmt("0");
                                    }
                                    if (periodicity.equalsIgnoreCase("W")) {
                                        pojo.setRepaymentFreq("01");
                                    } else if (periodicity.equalsIgnoreCase("M")) {
                                        pojo.setRepaymentFreq("03");
                                    } else if (periodicity.equalsIgnoreCase("Q")) {
                                        pojo.setRepaymentFreq("04");
                                    } else {
                                        pojo.setRepaymentFreq("04");
                                    }
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
                                    pojo.setDrawingPower(dp);
                                    List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                    if (!l2.isEmpty()) {
                                        vtr1 = (Vector) l2.get(0);
                                        currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                    }
                                    pojo.setOutstanding(currentBal);
                                    pojo.setAmtRestructured(amtRestruct);
                                    pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                    List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                            + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                            + " and effdt <='" + toDate + "'").getResultList();
                                    if (!assetClsDt.isEmpty()) {
                                        Vector vect = (Vector) assetClsDt.get(0);
                                        accStatus = vect.get(0).toString();
                                    } else {
                                        accStatus = accStatus;
                                    }
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
//                                        if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                            assetClass = "01";
//                                        } else {
//                                            assetClass = "05";
//                                        }
                                        assetClass = "01";
                                    } else {
                                        assetClass = "05";
                                    }
                                    pojo.setAssetClassif(assetClass);
                                    pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                    pojo.setOdBuct1(odBuc1);
                                    pojo.setOdBuct2(odBuc2);
                                    pojo.setOdBuct3(odBuc3);
                                    pojo.setOdBuct4(odBuc4);
                                    pojo.setOdBuct5(odBuc5);
                                    pojo.setHighCredit(highCredit);
                                    List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                    if (!l4.isEmpty()) {
                                        vtr1 = (Vector) l4.get(0);
                                        lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                        List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                        if (!crAmt.isEmpty()) {
                                            Vector crvect = (Vector) crAmt.get(0);
                                            lastCrAmt = crvect.get(0).toString();
                                        }
                                    }
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setAcStatus(accStatus);
                                    pojo.setAcStatus(accStatusDt);
                                    pojo.setRenewalDt(renewalDt);
                                    pojo.setAssetClassDt(assetClassDt);
                                    pojo.setAcStatus(accStatus);
                                    pojo.setAcStatusDt(accStatus);
                                    pojo.setWriteOffAmt("0");
                                    pojo.setSettledAmt("0");
                                    pojo.setRestureReason("");
                                    pojo.setNpaAmt(npaAmt);
                                    pojo.setAsstSecCover(sicCode);
                                    pojo.setGuaranteeCover(guaranteeCover);
                                    pojo.setBnkRemarkCode("");
                                    pojo.setDefaultStatus("");
                                    pojo.setDefaultStatusDt("");
                                    pojo.setSuitStatus("");
                                    pojo.setSuitAmt("0");
                                    pojo.setSuitDt("");
                                    pojo.setSuitRefNo("");
                                    pojo.setDisputeIdNo("");
                                    pojo.setTxnTypeCode("");
                                    pojo.setGuarantorReasonForDisHonour("");
                                    if (accStatus.equalsIgnoreCase("3")) {
                                        writtrnOff = "1";
                                    } else if (accStatus.equalsIgnoreCase("14")) {
                                        writtrnOff = "7";
                                    } else {
                                        writtrnOff = "";
                                    }
                                    /*
                                    01 =Standard
                                    02 =Sub-Standard
                                    03 =Doubtful
                                    04 =Loss
                                    05 =Special Mention Account
                                     */
                                    /*
                                     * guarantorSegmentIdentifier="",
                                     * guarantorDateOfDishonour="",
                                     * guarantorAmt="",
                                     * guarantorChqNo="",
                                     * guarantorNoOfDisHonour="",
                                     * guarantorChqIssueDt="",
                                     * guarantorReasonForDisHonour=""
                                     */
                                    pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                    pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                    if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                        pojo.setGuarantorTypeOfSec("01");
                                    } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                        pojo.setGuarantorTypeOfSec("02");
                                    } else {
                                        pojo.setGuarantorTypeOfSec("02");
                                    }
                                    pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                    pojo.setGuarantorDateOfValuation(guarantorDateOfValuation);
                                    pojo.setGuarantorDUNs("");
                                    pojo.setGuarantorType("");
                                    pojo.setBusinessCatGuarantor("");
                                    pojo.setBusinessTypeGuarantor("");
                                    pojo.setGuarantorEntityName(guarantor_entity_name);
                                    pojo.setGuarantorPrefix(guarantor_prefix);
                                    pojo.setGuarantorFullName(guarantor_name);
                                    pojo.setGuarantorGender(guarantor_gender);
                                    pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                    pojo.setGuarantorDOI(guarantor_doi);
                                    /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                    pojo.setGuarantorDob(guarantor_dob);
                                    pojo.setGuarantorPan(guarantor_pan);
                                    pojo.setGuarantorVotedID(guarantor_votrid);
                                    pojo.setGuarantorPassport(guarantor_passport);
                                    pojo.setGuarantorDLId(guarantor_dl);
                                    pojo.setGuarantorUID(guarantor_uid);
                                    pojo.setRationCardNo(guarantor_ration);
                                    pojo.setGuarantorCIN(guarantor_cin);
                                    pojo.setGuarantorDIN(guarantor_din);
                                    pojo.setGuarantorTIN(guarantor_tin);
                                    pojo.setGuarantorSTax(guarantor_stax);
                                    pojo.setGuarantorOthId(guarantor_otherid);
                                    pojo.setGuarantorAdd1(guarantor_add1);
                                    pojo.setGuarantorAdd2(guarantor_add2);
                                    pojo.setGuarantorAdd3(guarantor_add3);
                                    pojo.setGuarantorCity(guarantor_city);
                                    pojo.setGuarantorDistrict(guarantor_district);
                                    pojo.setGuarantorState(guarantor_state);
                                    pojo.setGuarantorPin(guarantor_pincode);
                                    pojo.setGuarantorCountry(guarantor_country);
                                    pojo.setGuarantorMobile(guarantor_mobile);
                                    pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                    pojo.setGuarantorTelNo(guarantor_tel_no);
                                    pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                    pojo.setGuarantorFaxNo(guarantor_fax_no);
                                    experionList.add(pojo);
                                }
                            } else {
                                pojo = new EquifaxComercialPoJo();
                                pojo.setMemberCode(memberCode);
                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                pojo.setMemberName(custName);
                                pojo.setShortName(shortNm);
                                pojo.setRegNo("");
                                pojo.setDoi(doi);
                                pojo.setPan(panGirNumber);
                                pojo.setCin(cin);
                                pojo.setTin(tin);
                                pojo.setsTax(sTax);
                                pojo.setOtherId(otheriId);
                                pojo.setConstNo("");
                                pojo.setBusinessCat("");
                                pojo.setBusinessType("");
                                pojo.setClassOfAct1(clsOfAct1);
                                pojo.setClassOfAct2(clsOfAct2);
                                pojo.setClassOfAct3(clsOfAct3);
                                pojo.setSicCode(sicCode);
                                pojo.setSalesFigure(salesFigure);
                                pojo.setFinYear(finYear);
                                pojo.setNoOfEmp(noOfEmp);
                                pojo.setCreditRating(creditRating);
                                pojo.setAuthority(authority);
                                pojo.setCreditRatingDt(creditRatingAsOn);
                                pojo.setCreditRatingExpDt(creditExpDt);
                                pojo.setLocationType(locationType);
                                pojo.setDunsNo(dunsNo);
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
                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                pojo.setCity(mailCityCode);
                                pojo.setDistrict(mailCityCode);
                                pojo.setState(mailStateCode);
                                pojo.setCountry(country);
                                pojo.setMobile(mobile);
                                pojo.setTelAreaCode(telAreCode);
                                pojo.setTelNo(telephoneNumber);
                                pojo.setFaxArea(faxAreaCode);
                                pojo.setFaxNo(faxNo);
                                pojo.setRelDunsNo(dunsNo);
                                pojo.setRelatedType(relatedType);
                                pojo.setRelationShip(relationShip);
                                pojo.setBusinessEntityName(jtBussEntity);
                                pojo.setBusinessCat1(jtBussCat);
                                pojo.setTypeOfBusiness(jtBussType);
                                pojo.setIndPreFix(jtTitle);
                                pojo.setFullName(jtCustFullName);
                                pojo.setGender(jtGender);
                                pojo.setRegNoCom(jtRegNo);
                                pojo.setDoi(jtDoi);
                                pojo.setDob(jtDob);
                                pojo.setPanNo(jtPAN);
                                pojo.setVoterID(jtVoterID);
                                pojo.setPassportNo(jtPasspost);
                                pojo.setDlID(jtDlNo);
                                pojo.setUid(jtAadhar);
                                pojo.setRationCardNo(jtRationCard);
                                pojo.setCinNo(jtCin);
                                pojo.setDinNo(jtDin);
                                pojo.setTinNo(jtTin);
                                pojo.setsTaxNo(jtSTax);
                                pojo.setOtherIdNo(jtOtherID);
                                pojo.setPercControl(jtPerControl);
                                pojo.setAdd1(jtAdd1);
                                pojo.setAdd2(jtAdd2);
                                pojo.setAdd3(jtAdd3);
                                pojo.setCityTown(jtcity);
                                pojo.setDistrict1(jtcity);
                                pojo.setState1(jtState);
                                pojo.setPin1(jtPinCode);
                                pojo.setCountry1(jtCountry);
                                pojo.setMobile1(jtMobile);
                                pojo.setTelAreaCode1(jtTelArea);
                                pojo.setTelNo1(jtTelNo);
                                pojo.setFaxArea1(jtFaxArea);
                                pojo.setFaxNo1(jtFaxNo);
                                pojo.setAcNo(acNo);
                                pojo.setPreAcno("");
                                pojo.setSanctDt(sanctionDt);
                                int overDueAmt=0;
                                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                                            + "where acno =  '" + acNo + "' and txnid = "
                                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                                    if (!sanctionLimitDtList.isEmpty()) {
                                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                                        odLimit = vist.get(1).toString();
                                        pojo.setSancAmt(odLimit);
                                    } else {
                                        pojo.setSancAmt(odLimit);
                                    }
//                                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                        amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                    } else {
//                                        amtOverdue = "0";
//                                    }
//                                    pojo.setOdBuct1(amtOverdue);
                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                    OverDueList = OverdueCaList;
                                    if (!OverDueList.isEmpty()) {
                                        for (int s = 0; s < OverDueList.size(); s++) {
                                            OverdueNonEmiResultList odVect = OverDueList.get(s);
                                            if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                overDueAmt = (int) odVect.getOverDue();
                                                if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                    odBuc1 = String.valueOf(overDueAmt);
                                                } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                    odBuc2 = String.valueOf(overDueAmt);
                                                } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                    odBuc3 = String.valueOf(overDueAmt);
                                                } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                    odBuc4 = String.valueOf(overDueAmt);
                                                } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                    odBuc5 = String.valueOf(overDueAmt);
                                                }
                                            }
                                        }
                                    }
                                } else {
//                                    List excessList = null;
//                                    double excess = 0;
//                                    if (isExcessEmi == 0) {
//                                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                    } else {
//                                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                                + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                    }
//                                    if (!excessList.isEmpty()) {
//                                        if (!excessList.isEmpty()) {
//                                            Vector ele = (Vector) excessList.get(0);
//                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                excess = Double.parseDouble(ele.get(0).toString());
//                                            }
//                                        }
//                                    }
//                                    List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                    if (!l6.isEmpty()) {
//                                        vtr1 = (Vector) l6.get(0);
//                                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                                            amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                        }
//                                        double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                        amtOverdue = String.valueOf(overDueAmt);
//                                        pojo.setOdBuct1(amtOverdue);
//                                    }
//                                    List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                    if (!l7.isEmpty()) {
//                                        vtr1 = (Vector) l7.get(0);
//                                        overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                    }
                                    List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();
                                    List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                    if (acNture.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                        OverDueList = OverdueDlList;
                                        if (!OverDueList.isEmpty()) {
                                            for (int s = 0; s < OverDueList.size(); s++) {
                                                OverdueNonEmiResultList odVect = OverDueList.get(s);
                                                if (odVect.getAccountNo().equalsIgnoreCase(acNo)) {
                                                    overDueAmt = (int) odVect.getOverDue();
                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                        odBuc1 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                        odBuc2 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                        odBuc3 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                        odBuc4 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                        odBuc5 = String.valueOf(overDueAmt);
                                                    }
                                                }
                                            }
                                        }
                                    } else if (acNture.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                        OverDueTLList = OverdueTlList;
                                        if (!OverDueTLList.isEmpty()) {
                                            for (int s = 0; s < OverDueTLList.size(); s++) {
                                                OverdueEmiReportPojo odVect = OverDueTLList.get(s);
                                                if (odVect.getAccountNumber().equalsIgnoreCase(acNo)) {
                                                    overDueAmt = odVect.getAmountOverdue().intValue();
                                                    if (odVect.getCurrentStatusNoOfDays() > 0 & odVect.getCurrentStatusNoOfDays() < 31) {
                                                        odBuc1 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 30 & odVect.getCurrentStatusNoOfDays() < 61) {
                                                        odBuc2 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 60 & odVect.getCurrentStatusNoOfDays() < 91) {
                                                        odBuc3 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 90 & odVect.getCurrentStatusNoOfDays() < 181) {
                                                        odBuc4 = String.valueOf(overDueAmt);
                                                    } else if (odVect.getCurrentStatusNoOfDays() > 180 & odVect.getCurrentStatusNoOfDays() < 999999) {
                                                        odBuc5 = String.valueOf(overDueAmt);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                pojo.setCurrencyCode("INR");
                                pojo.setCreditType(creditType);
                                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                                if (!lemi.isEmpty()) {
                                    Vector vtrEmi = (Vector) lemi.get(0);
                                    repaymentTenure = vtrEmi.get(0).toString();
                                    emiAmt = vtrEmi.get(1).toString();
                                    periodicity = vtrEmi.get(2).toString();
                                    pojo.setPeriod(repaymentTenure);
                                    pojo.setEmiAmt(emiAmt);
                                } else {
                                    pojo.setPeriod("0");
                                    pojo.setEmiAmt("0");
                                }
                                if (periodicity.equalsIgnoreCase("W")) {
                                    pojo.setRepaymentFreq("01");
                                } else if (periodicity.equalsIgnoreCase("M")) {
                                    pojo.setRepaymentFreq("03");
                                } else if (periodicity.equalsIgnoreCase("Q")) {
                                    pojo.setRepaymentFreq("04");
                                } else {
                                    pojo.setRepaymentFreq("04");
                                }
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
                                pojo.setDrawingPower(dp);
                                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                if (!l2.isEmpty()) {
                                    vtr1 = (Vector) l2.get(0);
                                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                }
                                pojo.setOutstanding(currentBal);
                                pojo.setAmtRestructured(amtRestruct);
                                pojo.setCloseDt(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                List assetClsDt = em.createNativeQuery("SELECT spflag FROM accountstatus a, codebook c "
                                        + " WHERE ACNO='" + acNo + "' AND a.spflag='" + accStatus + "' AND GROUPCODE = 3 and a.spflag = c.code "
                                        + " and effdt <='" + toDate + "'").getResultList();
                                if (!assetClsDt.isEmpty()) {
                                    Vector vect = (Vector) assetClsDt.get(0);
                                    accStatus = vect.get(0).toString();
                                } else {
                                    accStatus = accStatus;
                                }
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
//                                    if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                        assetClass = "01";
//                                    } else {
//                                        assetClass = "05";
//                                    }
                                    assetClass = "01";
                                } else {
                                    assetClass = "05";
                                }
                                pojo.setAssetClassif(assetClass);
                                pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                pojo.setOdBuct1(odBuc1);
                                pojo.setOdBuct2(odBuc2);
                                pojo.setOdBuct3(odBuc3);
                                pojo.setOdBuct4(odBuc4);
                                pojo.setOdBuct5(odBuc5);
                                pojo.setHighCredit(highCredit);
                                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                                if (!l4.isEmpty()) {
                                    vtr1 = (Vector) l4.get(0);
                                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                                    List crAmt = em.createNativeQuery("select ifnull(sum(cramt),0)  from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt = '" + lastPaymentDt + "'").getResultList();
                                    if (!crAmt.isEmpty()) {
                                        Vector crvect = (Vector) crAmt.get(0);
                                        lastCrAmt = crvect.get(0).toString();
                                    }
                                }
                                pojo.setLastCrAmt(lastCrAmt);
                                pojo.setAcStatus(accStatus);
                                pojo.setAcStatus(accStatusDt);
                                pojo.setRenewalDt(renewalDt);
                                pojo.setAssetClassDt(assetClassDt);
                                pojo.setAcStatus(accStatus);
                                pojo.setAcStatusDt(accStatus);
                                pojo.setWriteOffAmt("0");
                                pojo.setSettledAmt("0");
                                pojo.setRestureReason("");
                                pojo.setNpaAmt(npaAmt);
                                pojo.setAsstSecCover(sicCode);
                                pojo.setGuaranteeCover(guaranteeCover);
                                pojo.setBnkRemarkCode("");
                                pojo.setDefaultStatus("");
                                pojo.setDefaultStatusDt("");
                                pojo.setSuitStatus("");
                                pojo.setSuitAmt("0");
                                pojo.setSuitDt("");
                                pojo.setSuitRefNo("");
                                pojo.setDisputeIdNo("");
                                pojo.setTxnTypeCode("");
                                pojo.setGuarantorReasonForDisHonour("");
                                if (accStatus.equalsIgnoreCase("3")) {
                                    writtrnOff = "1";
                                } else if (accStatus.equalsIgnoreCase("14")) {
                                    writtrnOff = "7";
                                } else {
                                    writtrnOff = "";
                                }

                                /*
                                01 =Standard
                                02 =Sub-Standard
                                03 =Doubtful
                                04 =Loss
                                05 =Special Mention Account
                                 */
                                /*
                                 * guarantorSegmentIdentifier="",
                                 * guarantorDateOfDishonour="",
                                 * guarantorAmt="",
                                 * guarantorChqNo="",
                                 * guarantorNoOfDisHonour="",
                                 * guarantorChqIssueDt="",
                                 * guarantorReasonForDisHonour=""
                                 */
                                pojo.setGuarantorValueOfSec(guarantorValueOfSec);
                                pojo.setGuarantorCurrencyType(guarantorCurrencyType);
                                if (guarantorTypeOfSec.equalsIgnoreCase("P")) {
                                    pojo.setGuarantorTypeOfSec("01");
                                } else if (guarantorTypeOfSec.equalsIgnoreCase("C")) {
                                    pojo.setGuarantorTypeOfSec("02");
                                } else {
                                    pojo.setGuarantorTypeOfSec("02");
                                }
                                pojo.setGuarantorSecurityClass(guarantorSecurityClass);
                                pojo.setGuarantorDateOfValuation(guarantorDateOfValuation);
                                pojo.setGuarantorDUNs("");
                                pojo.setGuarantorType("");
                                pojo.setBusinessCatGuarantor("");
                                pojo.setBusinessTypeGuarantor("");
                                pojo.setGuarantorEntityName(guarantor_entity_name);
                                pojo.setGuarantorPrefix(guarantor_prefix);
                                pojo.setGuarantorFullName(guarantor_name);
                                pojo.setGuarantorGender(guarantor_gender);
                                pojo.setGuarantorComRegNo(guarantor_comp_regi_no);
                                pojo.setGuarantorDOI(guarantor_doi);
                                /* That means the guarantor is not exist in the bank so we have to print it blank*/
                                pojo.setGuarantorDob(guarantor_dob);
                                pojo.setGuarantorPan(guarantor_pan);
                                pojo.setGuarantorVotedID(guarantor_votrid);
                                pojo.setGuarantorPassport(guarantor_passport);
                                pojo.setGuarantorDLId(guarantor_dl);
                                pojo.setGuarantorUID(guarantor_uid);
                                pojo.setRationCardNo(guarantor_ration);
                                pojo.setGuarantorCIN(guarantor_cin);
                                pojo.setGuarantorDIN(guarantor_din);
                                pojo.setGuarantorTIN(guarantor_tin);
                                pojo.setGuarantorSTax(guarantor_stax);
                                pojo.setGuarantorOthId(guarantor_otherid);
                                pojo.setGuarantorAdd1(guarantor_add1);
                                pojo.setGuarantorAdd2(guarantor_add2);
                                pojo.setGuarantorAdd3(guarantor_add3);
                                pojo.setGuarantorCity(guarantor_city);
                                pojo.setGuarantorDistrict(guarantor_district);
                                pojo.setGuarantorState(guarantor_state);
                                pojo.setGuarantorPin(guarantor_pincode);
                                pojo.setGuarantorCountry(guarantor_country);
                                pojo.setGuarantorMobile(guarantor_mobile);
                                pojo.setGuarantorTelAreaCode(guarantor_tel_area);
                                pojo.setGuarantorTelNo(guarantor_tel_no);
                                pojo.setGuarantorFaxAreaCode(guarantor_fax_area);
                                pojo.setGuarantorFaxNo(guarantor_fax_no);
                                experionList.add(pojo);
                            }
                        }
                    }
                }
                /*Guarantor details End */
            }
            /*Main List End*/
            return experionList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }    
}
