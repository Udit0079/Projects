/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.ExperionPojo;
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
@Stateless(mappedName = "CiCReportFacade")
public class CicReportFacade implements CicReportFacadeRemote {

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

    public static String getMiddleName(String[] middleName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < middleName.length - 1; i++) {
            builder.append(middleName[i] + " ");
        }
        return builder.toString();
    }

    public List<ExperionPojo> cicReport(String memberCode, String fromDate, String toDate, String todaydate) throws ApplicationException {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            List<ExperionPojo> experionList = new ArrayList<ExperionPojo>();
            Vector vtr1 = null;
            String lastPaymentDt = null, branchName = null, accountTypeTable = null, assetClass = "";
            String sanctionAmt = null, currentBal = null, amtOverdue = null, writtrnOff = null;
            String overDueDays = "";
            ExperionPojo pojo;
            List custIdList;
            boolean flag = false;
            List custNameList = new ArrayList();
            List<OverdueEmiReportPojo> OverdueTlList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", 1, 5000, toDate, "0A", "A", false, "", "N");
            List<OverdueNonEmiResultList> OverdueDlList = overDueRemote.getOverDueNonEmi("DL", "ALL", toDate, "0A", "N");
            List<OverdueNonEmiResultList> OverdueCaList = overDueRemote.getOverDueNonEmi("CA", "ALL", toDate, "0A", "N");
            String title = "", custName = "", dob = "", gender = "", panGirNumber = "", passportNo = "", voterIdNo = "", telephoneNumber = "",
                    mailAddressLine1 = "", mailAddressLine2 = "", mailVillage = "", mailBlock = "", mailCityCode = "", mailStateCode = "", mailPostalCode = "",
                    customerid = "", shortName = "", acNo = "", operMode = "", openingDt = "", closingDate = "", accStatus = "",
                    custId1 = "", custId2 = "", custId3 = "", custId4 = "", address = "", space = " ",
                    perAddressLine1 = "", perAddressLine2 = "", perVillage = "", perBlock = "", perCityCode = "", perStateCode = "", perPostalCode = "",
                    issueDate = "", expirydate = "", drivingLicenseNo = "", uidNo = "",
                    telResident = "", telOffice = "", emailID = "", odLimit = "", address2 = "",
                    acNature = "", acctcode = "";
            List acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACNATURE'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Acnature data does not exit in required table");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }
            int isExcessEmi = common.isExceessEmi(toDate);

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
                    + " c.IssueDate, c.Expirydate, c.DrivingLicenseNo, aa.IdentityNo as aadhar_no  , "
                    + " ifnull(c.PerPhoneNumber, ifnull(c.PerTelexNumber, ifnull(c.PerFaxNumber, ''))) as telResident, "
                    + " ifnull(c.EmpPhoneNumber, ifnull(c.EmpTelexNumber, ifnull(c.EmpFaxNumber, ''))) as telOffice, "
                    + " ifnull(c.EmailID,''),"
                    + " c.PerAddressLine1, c.perAddressLine2, c.PerVillage, c.PerBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.PerCityCode) as PerCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.PerStateCode) as PerStateCode, "
                    + " c.PerPostalCode, cast(ifnull(a.ODLimit,0) as decimal) ,(select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature  "
                    + " from accountmaster a, customerid b, cbs_customer_master_detail c "
                    + " left outer join "
                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " where a.acno = b.acno and b.custid = cast(c.customerid as unsigned) and  a.accttype "
                    + "in ( select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag = 'D')  "
                    + "and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + "((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno").getResultList();
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
                String acNture = vtrmain.get(41) != null ? vtrmain.get(41).toString() : "";

                pojo = new ExperionPojo();
                custIdList = new ArrayList();
                pojo.setMemberCode(memberCode);
                List branchList = em.createNativeQuery("select bankname from bnkadd").getResultList();
                if (!branchList.isEmpty()) {
                    Vector vtrBranch = (Vector) branchList.get(0);
                    branchName = vtrBranch.get(0) != null ? vtrBranch.get(0).toString() : "";
                    pojo.setMemberName(branchName);
                }
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
                pojo.setIncomeTaxId(panGirNumber);
                pojo.setPassportNo(passportNo);
                pojo.setVoterIdNo(voterIdNo);
                pojo.setTelephone(telephoneNumber);
                address = !mailAddressLine1.equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.concat(!mailAddressLine2.equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2).concat(!mailVillage.equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage).concat(!mailBlock.equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock).concat(!mailCityCode.equalsIgnoreCase("") ? "".concat(mailCityCode.trim()) : mailCityCode);
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
//                System.out.println("acno>>>>"+acNo+": address:"+address);
                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");

                pojo.setStateCode(mailStateCode);
                pojo.setPinCode(mailPostalCode);
                pojo.setReportingMemCode(customerid);
                pojo.setMemberShortName(shortName);
                pojo.setCurrentAcno(acNo);

                /*Account Type*/
                List l9 = em.createNativeQuery("select ifnull(b.cic_acct_report_code,'00') AS accounttypetable from cbs_loan_acc_mast_sec a, cbs_scheme_currency_details b where a.scheme_code = b.scheme_code and a.acno = '" + acNo + "'").getResultList();
                if (!l9.isEmpty()) {
                    vtr1 = (Vector) l9.get(0);
                    accountTypeTable = vtr1.get(0) != null ? vtr1.get(0).toString() : "00";
                }
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
                I = Individual
                A = Authorized User (Refer to Supplementary credit card holder)
                G = Guarantor
                J = Joint
                If values reported in this field is 2 i.e. Authorized User, then
                the Account type field must contain the value 10 i.e. Credit
                Card.
                 */
                if (operMode.equalsIgnoreCase("1") || operMode.equalsIgnoreCase("7") || operMode.equalsIgnoreCase("9") || operMode.equalsIgnoreCase("10")) {
                    pojo.setAccountHolderTypeCode("I");
                } else if (operMode.equalsIgnoreCase("2") || operMode.equalsIgnoreCase("3") || operMode.equalsIgnoreCase("4") || operMode.equalsIgnoreCase("5") || operMode.equalsIgnoreCase("6") || operMode.equalsIgnoreCase("8") || operMode.equalsIgnoreCase("11") || operMode.equalsIgnoreCase("12") || operMode.equalsIgnoreCase("13") || operMode.equalsIgnoreCase("14") || operMode.equalsIgnoreCase("15") || operMode.equalsIgnoreCase("16") || operMode.equalsIgnoreCase("17")) {
                    pojo.setAccountHolderTypeCode("J");
                } else {
                    pojo.setAccountHolderTypeCode("I");
                }

                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                lastPaymentDt = "";
                List l4 = em.createNativeQuery("select ifnull(max(dt),'1900-01-01') from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "' and cramt != 0").getResultList();
                if (!l4.isEmpty()) {
                    vtr1 = (Vector) l4.get(0);
                    lastPaymentDt = vtr1.get(0) != null ? dmy.format(y_m_d.parse(vtr1.get(0).toString())) : "";
                    pojo.setLastPaymentdate(lastPaymentDt.equalsIgnoreCase("01011900") ? "" : lastPaymentDt);
                    lastPaymentDt = lastPaymentDt.equalsIgnoreCase("01011900") ? "" : lastPaymentDt;
                }
                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                pojo.setDateReported(dmy.format(ymd.parse(toDate)));

                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                if (!l2.isEmpty()) {
                    vtr1 = (Vector) l2.get(0);
                    currentBal = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
                    if (currentBal.equalsIgnoreCase("0")) {
                        currentBal = "0";
                    }
                    pojo.setCurrentBalance(currentBal);
                }

//                System.out.println("acno:"+acNo+"; amtOverdue:"+amtOverdue+"; currentBal:"+currentBal);
                overDueDays = "";
                amtOverdue = "0";
                int overDueAmt = 0;
                if (acNture.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, cast(acLimit as decimal) from loan_oldinterest "
                            + "where acno =  '" + acNo + "' and txnid = "
                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDate + "' )").getResultList();
                    if (!sanctionLimitDtList.isEmpty()) {
                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                        odLimit = vist.get(1).toString();
                        pojo.setSanctAmt(odLimit);
                    } else {
                        pojo.setSanctAmt(odLimit);
                    }
//                    if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                        amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
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
                                overDueDays = String.valueOf(vect.getCurrentStatusNoOfDays());
                            }
                        }
                    }
                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                } else {
                    pojo.setSanctAmt(odLimit);
//                    List excessList = null;
//                    double excess = 0;
//                    if (isExcessEmi == 0) {
//                        excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                    } else {
//                        excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                    }
//                    if (!excessList.isEmpty()) {
//                        if (!excessList.isEmpty()) {
//                            Vector ele = (Vector) excessList.get(0);
//                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                excess = Double.parseDouble(ele.get(0).toString());
//                            }
//                        }
//                    }
//                    List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                            + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                    if (!l6.isEmpty()) {
//                        vtr1 = (Vector) l6.get(0);
//                        amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                        if (!amtOverdue.equalsIgnoreCase("0")) {
//                            amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                        }
//                        double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                        amtOverdue = String.valueOf(overDueAmt);
//                        pojo.setAmountOverDue(amtOverdue);
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
                }
                pojo.setAmountOverDue(String.valueOf(overDueAmt));
                pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 999) ? "999" : overDueDays) : "");
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
                 * A:1 = Suit Filed
                 * B:2 = Wilful default
                 * C:3 = Suit filed(Wilful default)
                 * D:4 = Written off
                 * E:5 = Suit Filed & Written off
                 * F:6 = Wilful default & Written off
                 * G:7 = Suit Filed (Wilful default)& Written off
                 * H:  = Settled
                 * I:  = Post (WO) Settled
                 * Z:  = Restructured
                 */
                if (accStatus.equalsIgnoreCase("3")) {
                    writtrnOff = "A";
                } else if (accStatus.equalsIgnoreCase("14")) {
                    writtrnOff = "G";
                } else {
                    writtrnOff = "";
                }
                pojo.setWrittenOffStatus(writtrnOff);
                /*
                 * A:01 =Standard
                 * B:02 =Sub-Standard
                 * C:03 =Doubtful
                 * D:04 =Loss
                 * E:05 =Special Mention Account
                 */
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
                    assetClass = "A";
                } else if (accStatus.equalsIgnoreCase("11")) {
                    assetClass = "B";
                } else if (accStatus.equalsIgnoreCase("12")) {
                    assetClass = "C";
                } else if (accStatus.equalsIgnoreCase("13")) {
                    assetClass = "D";
                } else if (accStatus.equalsIgnoreCase("9")) {
                    if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
                        assetClass = "A";
                    } else {
                        assetClass = "E";
                    }
                } else {
                    assetClass = "E";
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
//                if (title.equalsIgnoreCase("M/S") || title.equalsIgnoreCase("M/s")) {
//                    if (Integer.parseInt(operMode) == 8) {
//                        flag = true;
//                    } else {
//                        flag = false;
//                    }
//                } else {
//                    if (operMode.equalsIgnoreCase("1") || operMode.equalsIgnoreCase("7") || operMode.equalsIgnoreCase("9") || operMode.equalsIgnoreCase("10")) {
//                        flag = true;
//                    } else if (operMode.equalsIgnoreCase("2") || operMode.equalsIgnoreCase("3") || operMode.equalsIgnoreCase("4") || operMode.equalsIgnoreCase("5") || operMode.equalsIgnoreCase("6") || operMode.equalsIgnoreCase("8") || operMode.equalsIgnoreCase("11") || operMode.equalsIgnoreCase("12") || operMode.equalsIgnoreCase("13") || operMode.equalsIgnoreCase("14") || operMode.equalsIgnoreCase("15") || operMode.equalsIgnoreCase("16") || operMode.equalsIgnoreCase("17")) {
//                        flag = true;
//                    } else {
//                        flag = true;
//                    }
//                }
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

                address2 = !perAddressLine1.equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.concat(!perAddressLine2.equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2).concat(!perVillage.equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage).concat(!perBlock.equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock).concat(!perCityCode.equalsIgnoreCase("") ? "".concat(perCityCode.trim()) : perCityCode);
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
//                System.out.println("acno:"+acNo+"; address:"+address2);
//                pojo.setAddress2(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
//                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
//                pojo.setaddress(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
//                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
//                pojo.setaddress2Line3(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
//                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
//                pojo.setaddress2Line4(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
//                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
//                pojo.setaddress2Line5(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");

                pojo.setAddress2("");
                pojo.setStateCode2("");
                pojo.setPinCode2("");
                /* Address Category
                 * 01 = Permanent Address 
                 * 02 = Residence Address 
                 * 03 = Office Address 
                 * 04 = Not Categorized */
                pojo.setAddressCategory2("04");
                /* Residence Code
                 * 01 = Owned 
                 * 02 = Rented */
                pojo.setResidenceCode2("");

                pojo.setOldAccType("");
                pojo.setOldOwnershipIndicator("");
                pojo.setWrittenSettledStatus("");

                double netWordth = 0d;
                String monthlyIncome = "0";

                List lColl = em.createNativeQuery("select ifnull(net_worth,0), ifnull(monthly_income,0) from cbs_loan_borrower_details where acc_no = '" + acNo + "'").getResultList();
                if (!lColl.isEmpty()) {
                    Vector vtrColl = (Vector) lColl.get(0);
                    netWordth = Double.parseDouble(vtrColl.get(0).toString());
                    monthlyIncome = vtrColl.get(1).toString();

                }
                pojo.setValueOfCollateral("00");
                pojo.setTypeOfCollateral("00");
                pojo.setCreditLimit(odLimit);
                pojo.setCashLimit("0");
//                System.out.println("acno:"+acNo);
                String roi = loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(odLimit), toDate, acNo);
                pojo.setRateOfInterest(roi);

                String periodicity = "", repaymentTenure = "0", emiAmt = "0";
                List lemi = em.createNativeQuery("select count(*), cast(ifnull(installamt,0) as decimal), ifnull(periodicity,'M') from emidetails where acno = '" + acNo + "'").getResultList();
                if (!lemi.isEmpty()) {
                    Vector vtrEmi = (Vector) lemi.get(0);
                    repaymentTenure = vtrEmi.get(0).toString();
                    emiAmt = vtrEmi.get(1).toString();
                    periodicity = vtrEmi.get(2).toString();
                    pojo.setRepaymentTenure(repaymentTenure);
                    pojo.setEmiAmt(emiAmt);
                } else {
                    pojo.setRepaymentTenure("0");
                    pojo.setEmiAmt("0");
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
                pojo.setIncome(monthlyIncome);
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
                                pojo.setMemberName(branchName);
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
                                pojo.setIncomeTaxId(panGirNumber);
                                pojo.setPassportNo(passportNo);
                                pojo.setVoterIdNo(voterIdNo);
                                pojo.setTelephone(telephoneNumber);

                                address = !mailAddressLine1.equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.concat(!mailAddressLine2.equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2).concat(!mailVillage.equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage).concat(!mailBlock.equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock).concat(!mailCityCode.equalsIgnoreCase("") ? "".concat(mailCityCode.trim()) : mailCityCode);
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

//                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");

                                pojo.setStateCode(mailStateCode);
                                pojo.setPinCode(mailPostalCode);
                                pojo.setReportingMemCode(customerid);
                                pojo.setMemberShortName(shortName);
                                pojo.setCurrentAcno(acNo);
                                pojo.setAccountType(accountTypeTable);
                                /*I = Individual
                                A = Authorized User (Refer to Supplementary credit card holder)
                                G = Guarantor
                                J = Joint*/
                                pojo.setAccountHolderTypeCode("J");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                pojo.setSanctAmt(odLimit);
                                pojo.setCurrentBalance(currentBal);
                                pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 999) ? "999" : overDueDays) : "");
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);

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

                                address2 = !perAddressLine1.equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.concat(!perAddressLine2.equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2).concat(!perVillage.equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage).concat(!perBlock.equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock).concat(!perCityCode.equalsIgnoreCase("") ? "".concat(perCityCode.trim()) : perCityCode);
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
//                                pojo.setAddress2(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line3(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line4(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line5(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");

                                pojo.setAddress2("");
                                pojo.setStateCode2("");
                                pojo.setPinCode2("");
                                /* Address Category
                                 * 01 = Permanent Address 
                                 * 02 = Residence Address 
                                 * 03 = Office Address 
                                 * 04 = Not Categorized */
                                pojo.setAddressCategory2("04");
                                /* Residence Code
                                 * 01 = Owned 
                                 * 02 = Rented */
                                pojo.setResidenceCode2("");

                                pojo.setOldAccType("");
                                pojo.setOldOwnershipIndicator("");
                                pojo.setWrittenSettledStatus("");

                                pojo.setValueOfCollateral("00");
                                pojo.setTypeOfCollateral("00");
                                pojo.setCreditLimit(odLimit);
                                pojo.setCashLimit("0");
                                pojo.setRateOfInterest(roi);


                                pojo.setRepaymentTenure(repaymentTenure);
                                pojo.setEmiAmt(emiAmt);

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
                                pojo.setIncome(monthlyIncome);
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
                                pojo.setMemberName(branchName);
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
                                pojo.setTelephone(telephoneNumber);

                                address = !mailAddressLine1.equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.concat(!mailAddressLine2.equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2).concat(!mailVillage.equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage).concat(!mailBlock.equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock).concat(!mailCityCode.equalsIgnoreCase("") ? "".concat(mailCityCode.trim()) : mailCityCode);
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

//                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
//                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
//                                pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");

                                pojo.setStateCode(mailStateCode);
                                pojo.setPinCode(mailPostalCode);
                                pojo.setReportingMemCode("");
                                pojo.setMemberShortName(shortName);
                                pojo.setCurrentAcno(acNo);
                                pojo.setAccountType(accountTypeTable);
                                /*
                                I = Individual
                                A = Authorized User (Refer to Supplementary credit card holder)
                                G = Guarantor
                                J = Joint
                                 */
                                pojo.setAccountHolderTypeCode("J");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                pojo.setSanctAmt(odLimit);
                                pojo.setCurrentBalance(currentBal);
                                pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 999) ? "999" : overDueDays) : "");
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);

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

                                address2 = !perAddressLine1.equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.concat(!perAddressLine2.equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2).concat(!perVillage.equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage).concat(!perBlock.equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock).concat(!perCityCode.equalsIgnoreCase("") ? "".concat(perCityCode.trim()) : perCityCode);
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
//                                pojo.setAddress2(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line3(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line4(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line5(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");

                                pojo.setAddress2("");
                                pojo.setStateCode2("");
                                pojo.setPinCode2("");
                                /* Address Category
                                 * 01 = Permanent Address 
                                 * 02 = Residence Address 
                                 * 03 = Office Address 
                                 * 04 = Not Categorized */
                                pojo.setAddressCategory2("04");
                                /* Residence Code
                                 * 01 = Owned 
                                 * 02 = Rented */
                                pojo.setResidenceCode2("");

                                pojo.setOldAccType("");
                                pojo.setOldOwnershipIndicator("");
                                pojo.setWrittenSettledStatus("");

                                pojo.setValueOfCollateral("00");
                                pojo.setTypeOfCollateral("00");
                                pojo.setCreditLimit(odLimit);
                                pojo.setCashLimit("0");
                                pojo.setRateOfInterest(roi);


                                pojo.setRepaymentTenure(repaymentTenure);
                                pojo.setEmiAmt(emiAmt);

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
                                pojo.setIncome(monthlyIncome);
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
                                experionList.add(pojo);
                            }
                        }
                    }
                }
                if (flag == true) {
//                    if (custIdList.size() <= 0) {
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
                                pojo.setMemberName(branchName);
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
                                pojo.setIncomeTaxId(panGirNumber);
                                pojo.setPassportNo(passportNo);
                                pojo.setVoterIdNo(voterIdNo);
                                pojo.setTelephone(vtr.get(0) != null ? vtr.get(0).toString() : "");

                                address = vtr.get(1) != null ? vtr.get(1).toString() : "";
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

                                //                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                //                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                //                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                //                                pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                //                                pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");

                                pojo.setStateCode(mailStateCode);
                                pojo.setPinCode(mailPostalCode);
                                pojo.setReportingMemCode(customerid);
                                pojo.setMemberShortName(shortName);
                                pojo.setCurrentAcno(acNo);
                                pojo.setAccountType(accountTypeTable);
                                /* I = Individual
                                A = Authorized User (Refer to Supplementary credit card holder)
                                G = Guarantor
                                J = Joint */
                                pojo.setAccountHolderTypeCode("G");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                pojo.setSanctAmt(odLimit);
                                pojo.setCurrentBalance(currentBal);
                                pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 999) ? "999" : overDueDays) : "");
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);
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

                                address2 = !perAddressLine1.equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.concat(!perAddressLine2.equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2).concat(!perVillage.equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage).concat(!perBlock.equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock).concat(!perCityCode.equalsIgnoreCase("") ? "".concat(perCityCode.trim()) : perCityCode);
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
                                //pojo.setAddress2(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line3(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line4(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                //                pojo.setaddress2Line5(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");

                                pojo.setAddress2("");
                                pojo.setStateCode2("");
                                pojo.setPinCode2("");
                                /* Address Category
                                 * 01 = Permanent Address 
                                 * 02 = Residence Address 
                                 * 03 = Office Address 
                                 * 04 = Not Categorized */
                                pojo.setAddressCategory2("04");
                                /* Residence Code
                                 * 01 = Owned 
                                 * 02 = Rented */
                                pojo.setResidenceCode2("");

                                pojo.setOldAccType("");
                                pojo.setOldOwnershipIndicator("");
                                pojo.setWrittenSettledStatus("");

                                pojo.setValueOfCollateral("00");
                                pojo.setTypeOfCollateral("00");
                                pojo.setCreditLimit(odLimit);
                                pojo.setCashLimit("0");
                                pojo.setRateOfInterest(roi);


                                pojo.setRepaymentTenure(repaymentTenure);
                                pojo.setEmiAmt(emiAmt);

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
                                pojo.setIncome(monthlyIncome);
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
                                    pojo.setMemberName(branchName);
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
                                    pojo.setIncomeTaxId(panGirNumber);
                                    pojo.setPassportNo(passportNo);
                                    pojo.setVoterIdNo(voterIdNo);
                                    pojo.setTelephone(telephoneNumber);

                                    address = !mailAddressLine1.equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.concat(!mailAddressLine2.equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2).concat(!mailVillage.equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage).concat(!mailBlock.equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock).concat(!mailCityCode.equalsIgnoreCase("") ? "".concat(mailCityCode.trim()) : mailCityCode);
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

                                    //                                pojo.setAddressLine1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                    //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                    //                                pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                    //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                    //                                pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                    //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                    //                                pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");
                                    //                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(" ") + 1) : "";
                                    //                                pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(" ")) : "");

                                    pojo.setStateCode(mailStateCode);
                                    pojo.setPinCode(mailPostalCode);
                                    pojo.setReportingMemCode(customerid);
                                    pojo.setMemberShortName(shortName);
                                    pojo.setCurrentAcno(acNo);
                                    pojo.setAccountType(accountTypeTable);
                                    /*  I = Individual
                                    A = Authorized User (Refer to Supplementary credit card holder)
                                    G = Guarantor
                                    J = Joint */
                                    pojo.setAccountHolderTypeCode("G");
                                    pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                    pojo.setLastPaymentdate(lastPaymentDt);
                                    pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                    pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                    pojo.setSanctAmt(odLimit);
                                    pojo.setCurrentBalance(currentBal);
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                    pojo.setNoOfDaysPast(!String.valueOf(overDueAmt).equalsIgnoreCase("") ? ((Integer.parseInt(overDueDays) > 999) ? "999" : overDueDays) : "");
                                    pojo.setWrittenOffStatus(writtrnOff);
                                    pojo.setAssetClassif(assetClass);

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

                                    address2 = !perAddressLine1.equalsIgnoreCase("") ? perAddressLine1.trim() : perAddressLine1.concat(!perAddressLine2.equalsIgnoreCase("") ? " ".concat(perAddressLine2.trim()) : perAddressLine2).concat(!perVillage.equalsIgnoreCase("") ? " ".concat(perVillage.trim()) : perVillage).concat(!perBlock.equalsIgnoreCase("") ? " ".concat(perBlock.trim()) : perBlock).concat(!perCityCode.equalsIgnoreCase("") ? "".concat(perCityCode.trim()) : perCityCode);
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
//                                        pojo.setAddress2(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                    //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    //                pojo.setaddress(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                    //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    //                pojo.setaddress2Line3(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                    //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    //                pojo.setaddress2Line4(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");
                                    //                address2 = (address2.length() > 40) ? address2.substring(address2.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    //                pojo.setaddress2Line5(address2.length() > 0 && address2.length() <= 40 ? address2.substring(0) : address2.length() > 40 ? address2.substring(0, 40).substring(0, address2.substring(0, 40).lastIndexOf(space)) : "");

                                    pojo.setAddress2("");
                                    pojo.setStateCode2("");
                                    pojo.setPinCode2("");
                                    /* Address Category
                                     * 01 = Permanent Address 
                                     * 02 = Residence Address 
                                     * 03 = Office Address 
                                     * 04 = Not Categorized */
                                    pojo.setAddressCategory2("04");
                                    /* Residence Code
                                     * 01 = Owned 
                                     * 02 = Rented */
                                    pojo.setResidenceCode2("");

                                    pojo.setOldAccType("");
                                    pojo.setOldOwnershipIndicator("");
                                    pojo.setWrittenSettledStatus("");

                                    pojo.setValueOfCollateral("00");
                                    pojo.setTypeOfCollateral("00");
                                    pojo.setCreditLimit(odLimit);
                                    pojo.setCashLimit("0");
                                    pojo.setRateOfInterest(roi);


                                    pojo.setRepaymentTenure(repaymentTenure);
                                    pojo.setEmiAmt(emiAmt);

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
                                    pojo.setIncome(monthlyIncome);
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
                                    experionList.add(pojo);
                                }
                            }
                        }
                    }
//                    }
                }
            }
            return experionList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
}
