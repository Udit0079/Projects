/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.EquifaxComercialPoJo;
import com.cbs.dto.report.ExperionPojo;
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
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "ExperionLoanFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ExperionLoanFacade implements ExperionLoanFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    OverDueReportFacadeRemote overDueRemote;

    public static String getMiddleName(String[] middleName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < middleName.length - 1; i++) {
            builder.append(middleName[i] + " ");
        }
        return builder.toString();
    }

    public List<ExperionPojo> experionReport(String memberCode, String fromDate, String toDate, String todaydate, String cibilParameter) throws ApplicationException {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            List<ExperionPojo> experionList = new ArrayList<ExperionPojo>();
            Vector vtr1 = null;
            String lastPaymentDt = null, branchName = null, accountTypeTable = "00", assetClass = "";
            String sanctionAmt = null, currentBal = null, amtOverdue = null, writtrnOff = null;
            int overDueAmt = 0;
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
                    acNature = "", acctcode = "", odLimit = "";
            List codeQuery = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name ='CIBIL_IND_CATEGORY'").getResultList();
            String code = "", acctCat = "";
            if (!codeQuery.isEmpty()) {
                Vector codeVect = (Vector) codeQuery.get(0);
                code = codeVect.get(0).toString();
            } else {
                throw new ApplicationException("Please Fill define CIBIL_IND_CATEGORY in cbs_parameterinfo Table!!!");
            }
            List acNatureList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CIBIL_ACNATURE'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Acnature data does not exit in required table");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
                acctCat = " and a.acctcategory in (" + code + ")";
            }

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
            List l1 = em.createNativeQuery("select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
                    + " when 'F' then '1'  when '0' then '' when 'NULL' then '' end as gender, "
                    + " c.PAN_GIRNumber,c.PassportNo, c.VoterIDNo, ifnull(c.mobilenumber, ifnull(c.TelexNumber, ifnull(c.MailPhoneNumber, "
                    + " ifnull(c.MailTelexNumber, ifnull(c.MailFaxNumber, ''))))) as telephonenumber, "
                    + " c.PerAddressLine1, c.perAddressLine2, c.MailVillage, c.MailBlock , "
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = c.mailCityCode) as MailCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = c.mailstatecode) as mailstatecode, "
                    + " c.MailPostalCode, c.customerid, c.CustFullName, a.ACNo, a.OperMode, a.OpeningDt, a.ClosingDate, a.AccStatus, "
                    + " a.custid1, a.custid2, a.custid3, a.custid4, cast(ifnull(d.ODLimit,0) as decimal) , "
                    + " (select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature, "
                    + " ifnull(f.ACCT_REPORT_CODE,'00') as expCode, ifnull(f.cibil_acct_report_code,'00') as cibilCode, "
                    + " ifnull(f.cic_acct_report_code,'00') as equifaxCode, ifnull(f.crif_acct_report_code,'00') as crifCode "
                    + " from accountmaster a, customerid b, cbs_customer_master_detail c, loan_appparameter d, cbs_loan_acc_mast_sec e, "
                    + " cbs_scheme_currency_details f "
                    + " where a.acno = b.acno and a.acno = d.acno and e.acno = a.acno " + acctCat + "and e.scheme_code = f.scheme_code and b.custid = cast(c.customerid as unsigned)  and  a.accttype "
                    + "in ( select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag = 'D')  "
                    + "and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + "((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno").getResultList();
            for (int i = 0; i < l1.size(); i++) {
                pojo = new ExperionPojo();
                Vector vtrmain = (Vector) l1.get(i);
                title = vtrmain.get(0) != null ? vtrmain.get(0).toString() : "";
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

                if (acNo.equalsIgnoreCase("021200899001")) {

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
                odLimit = vtrmain.get(26) != null ? vtrmain.get(26).toString() : "0";
                String acNture = vtrmain.get(27) != null ? vtrmain.get(27).toString() : "";
                String expCode = vtrmain.get(28) != null ? vtrmain.get(28).toString() : "";
                String cibilCode = vtrmain.get(29) != null ? vtrmain.get(29).toString() : "";
                String equifaxCode = vtrmain.get(30) != null ? vtrmain.get(30).toString() : "";
                String crifCode = vtrmain.get(31) != null ? vtrmain.get(31).toString() : "";

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
                pojo.setAccountType(accountTypeTable);

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
                pojo.setGender(gender);            //1=MALE : 2=FEMALE
                pojo.setIncomeTaxId(panGirNumber);
                pojo.setPassportNo(passportNo);
                pojo.setVoterIdNo(voterIdNo);
                pojo.setTelephone(telephoneNumber);
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
//                System.out.println("acno<<<<"+acNo+": address:"+address);
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
                address = address.substring(pojo.getAddressLine1().length());
                if (address.trim().length() > 40 && !address.trim().substring(0, 40).contains(" ") && !address.trim().substring(0, 40).contains(",") && !address.substring(0, 40).contains("-")) {
                    pojo.setAddressLine2(address.substring(0, 40));
                    address = address.substring(40, address.length());
                } else {
                    pojo.setAddressLine2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";

                }
                if (address.trim().length() > 40 && !address.trim().substring(0, 40).contains(" ") && !address.trim().substring(0, 40).contains(",") && !address.trim().substring(0, 40).contains("-")) {
                    pojo.setAddressLine3(address.substring(0, 40));
                    address = address.substring(40, address.length());
                } else {
                    pojo.setAddressLine3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";

                }
                if (address.trim().length() > 40 && !address.trim().substring(0, 40).contains(" ") && !address.trim().substring(0, 40).contains(",") && !address.substring(0, 40).contains("-")) {
                    pojo.setAddressLine4(address.substring(0, 40));
                    address = address.substring(40, address.length());
                } else {
                    pojo.setAddressLine4(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";

                }
                if (address.trim().length() > 40 && !address.trim().substring(0, 40).contains(" ") && !address.trim().substring(0, 40).contains(",") && !address.trim().substring(0, 40).contains("-")) {
                    pojo.setAddressLine5(address.substring(0, 40));
                    address = address.substring(40, address.length());
                } else {
                    pojo.setAddressLine5(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                }
                pojo.setStateCode(mailStateCode);
                pojo.setPinCode(mailPostalCode);
                pojo.setReportingMemCode(customerid);
                pojo.setMemberShortName(shortName);
                pojo.setCurrentAcno(acNo);

                /*Account Type*/
//                List l9 = em.createNativeQuery("select ifnull(b.acct_report_code,'00') AS accounttypetable from cbs_loan_acc_mast_sec a, cbs_scheme_currency_details b where a.scheme_code = b.scheme_code and a.acno = '" + acNo + "'").getResultList();
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
                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                if (!l2.isEmpty()) {
                    vtr1 = (Vector) l2.get(0);
                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
//                    if (currentBal.equalsIgnoreCase("0")) {
//                        currentBal = "0";
//                    }
                    pojo.setCurrentBalance(currentBal);
                }
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
                overDueDays = "";
                amtOverdue = "0";
                overDueAmt = 0;
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
                    if (overDueAmt == 0) {
                        pojo.setAmountOverDue("");
                    } else {
                        pojo.setAmountOverDue(String.valueOf(overDueAmt));
                    }
                } else {
                    pojo.setSanctAmt(odLimit);
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
                }
                if (overDueAmt == 0) {
                    pojo.setAmountOverDue("");
                } else {
                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                }

//                System.out.println("acno:"+acNo+"; amtOverdue:"+amtOverdue+"; currentBal:"+currentBal);
                if (!overDueDays.equalsIgnoreCase("")) {
                    if (Long.parseLong(overDueDays) > 999) {
                        overDueDays = "999";
                    }
                }

                pojo.setNoOfDaysPast(!amtOverdue.equalsIgnoreCase("") ? overDueDays : "000");
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
                if (accStatus.equalsIgnoreCase("3")) {
                    writtrnOff = "1";
                } else if (accStatus.equalsIgnoreCase("14")) {
                    writtrnOff = "7";
                } else {
                    writtrnOff = "";
                }
                pojo.setWrittenOffStatus(writtrnOff);

                /*
                 01 =Standard
                 02 =Sub-Standard
                 03 =Doubtful
                 04 =Loss
                 05 =Special Mention Account
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
                                    + " c.MailPostalCode, c.customerid, c.CustFullName  from  cbs_customer_master_detail c "
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
//                System.out.println("acno<<<<"+acNo+": address:"+address);
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
                                pojo.setAccountHolderTypeCode("4");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                pojo.setSanctAmt(odLimit);
                                pojo.setCurrentBalance(currentBal);
                                if (overDueAmt == 0) {
                                    pojo.setAmountOverDue("");
                                } else {
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                }
                                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                    pojo.setNoOfDaysPast("000");
                                } else {
                                    pojo.setNoOfDaysPast(overDueDays);
                                }

                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);

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
//                System.out.println("acno<<<<"+acNo+": address:"+address);
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
                                pojo.setAccountHolderTypeCode("4");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                pojo.setSanctAmt(odLimit);
                                pojo.setCurrentBalance(currentBal);
                                if (overDueAmt == 0) {
                                    pojo.setAmountOverDue("");
                                } else {
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                }
                                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                    pojo.setNoOfDaysPast("000");
                                } else {
                                    pojo.setNoOfDaysPast(overDueDays);
                                }
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);

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
                                pojo.setAccountHolderTypeCode("3");
                                pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                pojo.setLastPaymentdate(lastPaymentDt);
                                pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                pojo.setSanctAmt(odLimit);
                                pojo.setCurrentBalance(currentBal);
                                if (overDueAmt == 0) {
                                    pojo.setAmountOverDue("");
                                } else {
                                    pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                }
                                if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                    pojo.setNoOfDaysPast("000");
                                } else {
                                    pojo.setNoOfDaysPast(overDueDays);
                                }
                                pojo.setWrittenOffStatus(writtrnOff);
                                pojo.setAssetClassif(assetClass);

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
                                        + " c.MailPostalCode, c.customerid, c.CustFullName  from  cbs_customer_master_detail c "
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
//                System.out.println("acno<<<<"+acNo+": address:"+address);
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
                                    pojo.setAccountHolderTypeCode("3");
                                    pojo.setDisbursDt(dmy.format(ymd.parse(openingDt)));
                                    pojo.setLastPaymentdate(lastPaymentDt);
                                    pojo.setDateClose(dmy.format(ymd.parse(closingDate)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd.parse(closingDate)));
                                    pojo.setDateReported(dmy.format(ymd.parse(toDate)));
                                    pojo.setSanctAmt(odLimit);
                                    pojo.setCurrentBalance(currentBal);
                                    if (overDueAmt == 0) {
                                        pojo.setAmountOverDue("");
                                    } else {
                                        pojo.setAmountOverDue(String.valueOf(overDueAmt));
                                    }
                                    if (pojo.getAmountOverDue().equalsIgnoreCase("")) {
                                        pojo.setNoOfDaysPast("000");
                                    } else {
                                        pojo.setNoOfDaysPast(overDueDays);
                                    }
                                    pojo.setWrittenOffStatus(writtrnOff);
                                    pojo.setAssetClassif(assetClass);

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

    public List<EquifaxComercialPoJo> comercialExperianReport(String memberCode, String fromDate, String toDate, String todaydate, String reportType) throws ApplicationException {
        List<EquifaxComercialPoJo> experionList = new ArrayList<EquifaxComercialPoJo>();
        String memberName = "", preMemberCode = "", reportDt = "", futureUse = "", brnCode = "", borName = "", shortName = "", borOfycLoc = "",
                borAdd1 = "", borAdd2 = "", borAdd3 = "", borCity = "", borDistrict = "", borPin = "", borTelNo = "", borTelAreaCode = "", borFaxNo = "", borFaxArea = "",
                roAdd1 = "", roAdd2 = "", roAdd3 = "", roCity = "", roDistrict = "", roState = "", roPin = "", roTelNo = "", roTelArea = "", roFaxNo = "", roFaxArea = "",
                borPan = "", borLglConst = "", classOFAct1 = "", classOfAct2 = "", classOfAct3 = "", filler1 = "", relationshipData = "", relType = "", relPan = "",
                relationShip = "", businessEntityName = "", jtPreFix = "", jtFullName = "", percOfContrl = "", jtAdd1 = "", jtAdd2 = "", jtAdd3 = "", jtCity = "", jtState = "",
                jtPin = "", jtcountry = "91", jtTelNo = "", jtTelArea = "", filler2 = "", crAcData = "", acno = "", preAcno = "", sanctDt = "", currencyCode = "",
                creditType = "", dp = "", assetClass = "", bnkRemarkCode = "", defalultStatus = "", defaultStatusDt = "", suitStatus = "", suitRefNo = "", suitDt = "",
                filler3 = "", guarantorData = "", guarantorType = "", guarantorPan = "", guarantorName = "", guarantoPreFix = "", guarantorFullName = "",
                guarantorAdd1 = "", guarantorAdd2 = "", guarantorAdd3 = "", guarantorCity = "", guarantorDistrict = "", guarantorState = "", guarantorPin = "",
                guarantorCountry = "", guarantorTelNo = "", guarantorTelArea = "", filler4 = "";
        BigDecimal sanctAmt = new BigDecimal("0"), outstanding = new BigDecimal("0"), suitAmt = new BigDecimal("0");
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Vector vtr1 = null;
            String sanctionAmt = null, currentBal = "0", amtOverdue = null, writtrnOff = null;
            String overDueDays = "";
            EquifaxComercialPoJo pojo;
            List custIdList;
            boolean flag = false;
            List custNameList = new ArrayList();
            String title = "", custName = "", dob = "", gender = "", panGirNumber = "", passportNo = "", voterIdNo = "", telephoneNumber = "",
                    mailAddressLine1 = "", mailAddressLine2 = "", mailVillage = "", mailBlock = "", mailCityCode = "", mailStateCode = "", mailPostalCode = "",
                    customerid = "", acNo = "", operMode = "", openingDt = "", closingDate = "", accStatus = "",
                    custId1 = "", custId2 = "", custId3 = "", custId4 = "", address = "", space = " ",
                    perAddressLine1 = "", perAddressLine2 = "", perVillage = "", perBlock = "", perCityCode = "", perStateCode = "", perPostalCode = "",
                    issueDate = "", expirydate = "", drivingLicenseNo = "", uidNo = "",
                    telResident = "", telOffice = "", emailID = "", odLimit = "", address2 = "",
                    acNature = "", acctcode = "", custNameAccMast = "";

            List acNatureList = new ArrayList();
            List<OverdueEmiReportPojo> OverdueTlList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", 1, 5000, toDate, "0A", "A", false, "", "N");
            List<OverdueNonEmiResultList> OverdueDlList = overDueRemote.getOverDueNonEmi("DL", "ALL", toDate, "0A", "N");
            List<OverdueNonEmiResultList> OverdueCaList = overDueRemote.getOverDueNonEmi("CA", "ALL", toDate, "0A", "N");
            /*1 For Consumer and parameter will be CIBIL_ACNATURE(DL & TL)
             * 2 For Comercial and parameter will be CIBIL_COMERCIAL(CA)
             */
            String branchName = "";
            List branchList = em.createNativeQuery("select bankname from bnkadd").getResultList();
            if (!branchList.isEmpty()) {
                Vector vtrBranch = (Vector) branchList.get(0);
                branchName = vtrBranch.get(0) != null ? vtrBranch.get(0).toString() : "";

            }
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
            List l1 = em.createNativeQuery("select  c.title, c.CustFullName, c.dateofbirth, case c.gender when 'M' then '2' "
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
                    + " ifnull(c.CreditRatingAsOn,'') as creditRatingason,ifnull(c.tan,'') as tan, ifnull(c.cin,'') as cin,"
                    + " ifnull(c.other_identity,'') as otherId,ifnull(c.mobilenumber,'') as mobileNo, ifnull(c.CountryCode,'IN'),ifnull(Sanctionlimitdt,'1900-01-01 00:00:00'),ifnull(a.Relatioship,''),ifnull(FaxNumber,'') "
                    + " ,(select acctnature from accounttypemaster where AcctCode=substring(a.acno,3,2)) as acnature  "
                    + " from accountmaster a, customerid b, loan_appparameter d , cbs_customer_master_detail c "
                    + " left outer join "
                    + " ( select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                    + " union "
                    + " select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E' ) aa on  aa.CustomerId = c.customerid "
                    + " where a.acno = b.acno and b.custid = cast(c.customerid as unsigned)  and  a.acno = d.acno and a.accttype "
                    + " in ( select acctcode from accounttypemaster where acctnature in (" + acNature + ") " + acctcode + " and crdbflag in ('D','B'))  "
                    + " and  a.openingdt <= '" + toDate + "'   and ((closingDate > '" + toDate + "')  or (closingDate between  '" + fromDate + "' and '" + toDate + "') or "
                    + " ((closingDate is null or closingDate = '') and accstatus <> 9 )) order by acno").getResultList();
            /*Main List start */
            for (int i = 0; i < l1.size(); i++) {
                Vector vtrmain = (Vector) l1.get(i);
                title = vtrmain.get(0) != null ? vtrmain.get(0).toString() : "N01";
                borName = vtrmain.get(1) != null ? vtrmain.get(1).toString() : "";
                dob = vtrmain.get(2) != null ? vtrmain.get(2).toString() : "1900-01-01";
                gender = vtrmain.get(3) != null ? vtrmain.get(3).toString() : "";
                borPan = vtrmain.get(4) != null ? vtrmain.get(4).toString() : "";
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
                String crRating = vtrmain.get(47).toString() != null ? vtrmain.get(47).toString() : "19000101";
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
                relationShip = vtrmain.get(54).toString();
                String faxNo = vtrmain.get(55).toString();
                String acNture = vtrmain.get(56) != null ? vtrmain.get(56).toString() : "";
                String noOfEmp = "", telAreCode = "", faxAreaCode = "", relatedType = "", amtRestruct = "", odBuc1 = "", odBuc2 = "", odBuc3 = "", odBuc4 = "", odBuc5 = "";
                String clsOfAct1 = "", clsOfAct2 = "", clsOfAct3 = "", sicCode = "", creditRating = "", authority = "", creditExpDt = "", locationType = "", dunsNo = "";

                String guarantor_entity_name = "", guarantor_prefix = "", guarantor_name = "", guarantor_gender = "",
                        guarantor_comp_regi_no = "", guarantor_doi = "", guarantor_dob = "", guarantor_pan = "", guarantor_votrid = "", guarantor_passport = "",
                        guarantor_dl = "", guarantor_uid = "", guarantor_ration = "", guarantor_cin = "", guarantor_din = "", guarantor_tin = "", guarantor_stax = "", guarantor_otherid = "", guarantor_add1 = "", guarantor_add2 = "", guarantor_add3 = "", guarantor_city = "", guarantor_district = "", guarantor_state = "", guarantor_pincode = "",
                        guarantor_country = "", guarantor_mobile = "", guarantor_tel_area = "", guarantor_tel_no = "", guarantor_fax_area = "", guarantor_fax_no = "";
                String guarantorValueOfSec = "", guarantorCurrencyType = "", guarantorTypeOfSec = "", guarantorSecurityClass = "", guarantorDateOfValuation = "",
                        guarantorSegmentIdentifier = "", guarantorDateOfDishonour = "", guarantorAmt = "", guarantorChqNo = "", guarantorNoOfDisHonour = "",
                        guarantorChqIssueDt = "", guarantorReasonForDisHonour = "";

                pojo = new EquifaxComercialPoJo();
                custIdList = new ArrayList();
                String periodicity = "", repaymentTenure = "0", emiAmt = "0", highCredit = "0", lastCrAmt = "0", npaAmt = "0", guaranteeCover = "", renewalDt = "", assetClassDt = "";
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
                /*
                 * Code	Description For Credit Type
                 0100	Cash Credit
                 0200	Overdraft
                 0300	Demand Loan
                 0310	Loan Extended Through Credit Cards 
                 0410	Medium Term Loan (period above 1 year and up to 3 years)
                 0420	Long Term Loan (period above 3 years)
                 0500	Packing Credit (all export pre-shipment finance)
                 0610	Export Bills Purchased
                 0620	Export Bills Discounted
                 0630	Export Bills Advanced Against
                 0640	Advances Against Export Cash Incentives and Duty Draw-back Claims
                 0710	Inland Bills Purchased
                 0720	Inland Bills Discounted
                 0800	Advances Against Import Bills
                 0900	Foreign Currency Cheques, TCs/DDs/TTs/MTs Purchased
                 1000	Lease Finance
                 1100	Hire Purchase
                 2000	Bank Guarantee
                 2100	Deferred Payment Guarantee
                 3000	Letters of Credit
                 9000	Aggregation of all borrowings due to Filing of Suit
                 9999	Other
                 In Our System Only CC/OD/DL/TL Loans are defined.
                 */
                String crType = common.getAcctNature(acNo.substring(2, 4));
                if (crType.equalsIgnoreCase(CbsConstant.CC_AC)) {
                    creditType = "0100";
                } else if (crType.equalsIgnoreCase(CbsConstant.OD_AC)) {
                    creditType = "0200";
                } else if (crType.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    creditType = "0300";
                } else if (crType.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List lb = em.createNativeQuery("select ifnull(LOAN_DURATION,0) from cbs_loan_borrower_details where acc_no =" + acNo + " ").getResultList();
                    if (!lb.isEmpty()) {
                        Vector lbvect = (Vector) lb.get(0);
                        int period = Integer.parseInt(lbvect.get(0).toString());
                        if (period > 12 && period <= 36) {
                            creditType = "0410";
                        } else if (period > 36) {
                            creditType = "0420";
                        } else {
                            creditType = "9999";
                        }
                    } else {
                        creditType = "9999";
                    }
                } else {
                    creditType = "9999";
                }
                List npaList = em.createNativeQuery("select sum(cramt-dramt) from npa_recon where acno ='" + acNo + "' and dt<='" + toDate + "'").getResultList();
                if (!npaList.isEmpty()) {
                    Vector npaVect = (Vector) npaList.get(0);
                    npaAmt = npaVect.get(0) != null ? npaVect.get(0).toString() : "";
                }

                String jtTitle = "", jtCustFullName = "", jtGender = "", jtDob = "", jtPAN = "", jtVoterID = "", jtPasspost = "", jtDlNo = "", jtAadhar = "", jtCin = "", jtTin = "", jtSTax = "",
                        jtOtherID = "", jtcity = "", jtPinCode = "", jtCountry = "91", jtMobile = "",
                        jtFaxArea = "", jtFaxNo = "", jtDin = "", jtRationCard = "", jtPerControl = "", jtBussEntity = "", jtBussCat = "", jtBussType = "", jtRegNo = "";
                String jtDoi = "", jtSalesFigure = "", jtFinYear = "", finYear = "";

                if (flag == true) {
                    brnCode = acNo.substring(0, 2);
//                    System.out.println("brn" + brnCode);
                    List l8 = em.createNativeQuery("select phno,address,acno,name,ifnull(CUST_FLAG,'N'),ifnull(GAR_ACNO,''),ifnull(GAR_CUSTID,'') from loan_guarantordetails where acno ='" + acNo + "'").getResultList();
                    if (!l8.isEmpty()) { /*If Guarantor Exists.*/
                        for (int k = 0; k < l8.size(); k++) {
                            Vector vtr = (Vector) l8.get(k);
                            String garCustFlag = vtr.get(4).toString();
                            String garCustAcNo = vtr.get(5).toString();
                            String garCustId = vtr.get(6).toString();
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
                                            + " when 'F' then '1' when 'O' then '3' when '0' then '' when '' then '' when 'NULL' then '' end as gender, ifnull(c.DateOfBirth,''),"
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
//                                        jtCountry = jtVect.get(18).toString();
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
                                            pojo.setPreMemberCode(preMemberCode);
                                            pojo.setMemberName(branchName);
                                            pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                            pojo.setFutureUse(futureUse);
                                            pojo.setBrnCode(brnCode);
                                            pojo.setBorName(borName);
                                            pojo.setShortName(shortName);
                                            pojo.setBorOfficeLocation(borOfycLoc);
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
                                            pojo.setBorAdd1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setBorAdd2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                            pojo.setBorAdd3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                            pojo.setBorCity(mailCityCode);
                                            pojo.setBorDistrict(mailCityCode);
                                            pojo.setBorPin(borPin);
                                            pojo.setBorTelAreaCode(telAreCode);
                                            pojo.setBorTelNo(telephoneNumber);
                                            pojo.setBorFaxAreaCode(faxAreaCode);
                                            pojo.setBorFaxNo(faxNo);
                                            pojo.setRoAdd1(roAdd1);
                                            pojo.setRoAdd2(roAdd2);
                                            pojo.setRoAdd3(roAdd3);
                                            pojo.setRoCity(roCity);
                                            pojo.setRoDistrict(roDistrict);
                                            pojo.setRoState(roState);
                                            pojo.setRoPin(roPin);
                                            pojo.setRoTelNo(roTelNo);
                                            pojo.setRoTelAreaCode(roTelArea);
                                            pojo.setRoFaxNo(roFaxNo);
                                            pojo.setRoFaxAreaCode(roFaxArea);
                                            pojo.setBorPan(borPan);
                                            pojo.setBorLglConstitution(borLglConst);
                                            pojo.setClassOfAct1(classOFAct1);
                                            pojo.setClassOfAct2(classOfAct2);
                                            pojo.setClassOfAct3(classOfAct3);
                                            pojo.setFiller1(filler1);
                                            pojo.setRelationshipData(relationshipData);
                                            pojo.setRelType(relType);
                                            pojo.setRelPan(relPan);
                                            pojo.setRelationship(relationShip);
                                            pojo.setBusinessEntityName(businessEntityName);
                                            pojo.setJtCustPrefix(jtPreFix);
                                            pojo.setJtCustName(jtFullName);
                                            pojo.setPercControl(percOfContrl);
                                            pojo.setJtAdd1(jtAdd1);
                                            pojo.setJtAdd2(jtAdd2);
                                            pojo.setJtAdd3(jtAdd3);
                                            pojo.setJtCity(jtCity);
                                            pojo.setJtState(jtState);
                                            pojo.setJtPin(jtPin);
                                            pojo.setJtCountry(jtcountry);
                                            pojo.setJtTelNo(jtTelNo);
                                            pojo.setJtTelAreaCode(jtTelArea);
                                            pojo.setFiller2(filler2);
                                            pojo.setCrAcData(crAcData);
                                            pojo.setAcNo(acNo);
                                            pojo.setPreAcno(preAcno);
                                            pojo.setSanctDt(sanctDt);
                                            pojo.setSancAmt(sanctAmt.toString());
                                            pojo.setCurrencyCode("INR");
                                            pojo.setCreditType(creditType);
                                            pojo.setDrawingPower(dp);
                                            List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                            if (!l2.isEmpty()) {
                                                vtr1 = (Vector) l2.get(0);
                                                currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                            }
                                            pojo.setOutstanding(currentBal);
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
                                            pojo.setBnkRemarkCode(bnkRemarkCode);
                                            pojo.setDefaultStatus(defalultStatus);
                                            pojo.setDefaultStatusDt(defaultStatusDt);
                                            pojo.setSuitStatus(suitStatus);
                                            pojo.setSuitRefNo(suitRefNo);
                                            pojo.setSuitAmt(suitAmt.toString());
                                            /*	Filler*/
                                            pojo.setSuitDt(suitDt);
                                            pojo.setFiller3(filler3);
                                            pojo.setGuarantorData(guarantorData);
                                            pojo.setGuarantorType(guarantorType);
                                            pojo.setPanNo(jtPAN);
                                            pojo.setGuarantorName(guarantorName);
                                            pojo.setGuarantorPrefix(guarantoPreFix);
                                            pojo.setGuarantorFullName(guarantorFullName);
                                            pojo.setGuarantorAdd1(guarantorAdd1);
                                            pojo.setGuarantorAdd2(guarantorAdd2);
                                            pojo.setGuarantorAdd3(guarantorAdd3);
                                            pojo.setGuarantorCity(guarantorCity);
                                            pojo.setGuarantorDistrict(guarantorDistrict);
                                            pojo.setGuarantorState(guarantorState);
                                            pojo.setGuarantorPin(guarantorPin);
                                            pojo.setGuarantorCountry(guarantorCountry);
                                            pojo.setGuarantorTelNo(guarantorTelNo);
                                            pojo.setGuarantorTelAreaCode(guarantorTelArea);
                                            pojo.setFiller4(filler4);

                                            pojo.setSanctDt(sanctionDt);
                                            amtOverdue = "";
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
                                            pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                            pojo.setOdBuct1(odBuc1);
                                            pojo.setOdBuct2(odBuc2);
                                            pojo.setOdBuct3(odBuc3);
                                            pojo.setOdBuct4(odBuc4);
                                            pojo.setOdBuct5(odBuc5);
                                            experionList.add(pojo);
                                        }
                                    } else {

                                        pojo = new EquifaxComercialPoJo();

                                        pojo.setMemberCode(memberCode);
                                        pojo.setPreMemberCode(preMemberCode);
                                        pojo.setMemberName(branchName);
                                        pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                        pojo.setFutureUse(futureUse);
                                        pojo.setBrnCode(brnCode);
                                        pojo.setBorName(borName);
                                        pojo.setShortName(shortName);
                                        pojo.setBorOfficeLocation(borOfycLoc);
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
                                        pojo.setBorAdd1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setBorAdd2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setBorAdd3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        pojo.setBorCity(mailCityCode);
                                        pojo.setBorDistrict(mailCityCode);
                                        pojo.setBorPin(borPin);
                                        pojo.setBorTelAreaCode(telAreCode);
                                        pojo.setBorTelNo(telephoneNumber);
                                        pojo.setBorFaxAreaCode(faxAreaCode);
                                        pojo.setBorFaxNo(faxNo);
                                        pojo.setRoAdd1(roAdd1);
                                        pojo.setRoAdd2(roAdd2);
                                        pojo.setRoAdd3(roAdd3);
                                        pojo.setRoCity(roCity);
                                        pojo.setRoDistrict(roDistrict);
                                        pojo.setRoState(roState);
                                        pojo.setRoPin(roPin);
                                        pojo.setRoTelNo(roTelNo);
                                        pojo.setRoTelAreaCode(roTelArea);
                                        pojo.setRoFaxNo(roFaxNo);
                                        pojo.setRoFaxAreaCode(roFaxArea);
                                        pojo.setBorPan(borPan);
                                        pojo.setBorLglConstitution(borLglConst);
                                        pojo.setClassOfAct1(classOFAct1);
                                        pojo.setClassOfAct2(classOfAct2);
                                        pojo.setClassOfAct3(classOfAct3);
                                        pojo.setFiller1(filler1);
                                        pojo.setRelationshipData(relationshipData);
                                        pojo.setRelType(relType);
                                        pojo.setRelPan(relPan);
                                        pojo.setRelationship(relationShip);
                                        pojo.setBusinessEntityName(businessEntityName);
                                        pojo.setJtCustPrefix(jtPreFix);
                                        pojo.setJtCustName(jtFullName);
                                        pojo.setPercControl(percOfContrl);
                                        pojo.setJtAdd1(jtAdd1);
                                        pojo.setJtAdd2(jtAdd2);
                                        pojo.setJtAdd3(jtAdd3);
                                        pojo.setJtCity(jtCity);
                                        pojo.setJtState(jtState);
                                        pojo.setJtPin(jtPin);
                                        pojo.setJtCountry(jtcountry);
                                        pojo.setJtTelNo(jtTelNo);
                                        pojo.setJtTelAreaCode(jtTelArea);
                                        pojo.setFiller2(filler2);
                                        pojo.setCrAcData(crAcData);
                                        pojo.setAcNo(acNo);
                                        pojo.setPreAcno(preAcno);
                                        pojo.setSanctDt(sanctDt);
                                        pojo.setSancAmt(sanctAmt.toString());
                                        pojo.setCurrencyCode("INR");
                                        pojo.setCreditType(creditType);
                                        pojo.setDrawingPower(dp);
                                        List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                        if (!l2.isEmpty()) {
                                            vtr1 = (Vector) l2.get(0);
                                            currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                        }
                                        pojo.setOutstanding(currentBal);
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
                                        pojo.setBnkRemarkCode(bnkRemarkCode);
                                        pojo.setDefaultStatus(defalultStatus);
                                        pojo.setDefaultStatusDt(defaultStatusDt);
                                        pojo.setSuitStatus(suitStatus);
                                        pojo.setSuitRefNo(suitRefNo);
                                        pojo.setSuitAmt(suitAmt.toString());
                                        /*	Filler*/
                                        pojo.setSuitDt(suitDt);
                                        pojo.setFiller3(filler3);
                                        pojo.setGuarantorData(guarantorData);
                                        pojo.setGuarantorType(guarantorType);
                                        pojo.setPanNo(jtPAN);
                                        pojo.setGuarantorName(guarantorName);
                                        pojo.setGuarantorPrefix(guarantoPreFix);
                                        pojo.setGuarantorFullName(guarantorFullName);
                                        pojo.setGuarantorAdd1(guarantorAdd1);
                                        pojo.setGuarantorAdd2(guarantorAdd2);
                                        pojo.setGuarantorAdd3(guarantorAdd3);
                                        pojo.setGuarantorCity(guarantorCity);
                                        pojo.setGuarantorDistrict(guarantorDistrict);
                                        pojo.setGuarantorState(guarantorState);
                                        pojo.setGuarantorPin(guarantorPin);
                                        pojo.setGuarantorCountry(guarantorCountry);
                                        pojo.setGuarantorTelNo(guarantorTelNo);
                                        pojo.setGuarantorTelAreaCode(guarantorTelArea);
                                        pojo.setFiller4(filler4);

                                        pojo.setSanctDt(sanctionDt);
                                        amtOverdue = "";
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
                                        pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                        pojo.setOdBuct1(odBuc1);
                                        pojo.setOdBuct2(odBuc2);
                                        pojo.setOdBuct3(odBuc3);
                                        pojo.setOdBuct4(odBuc4);
                                        pojo.setOdBuct5(odBuc5);
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
                                        pojo.setPreMemberCode(preMemberCode);
                                        pojo.setMemberName(branchName);
                                        pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                        pojo.setFutureUse(futureUse);
                                        pojo.setBrnCode(brnCode);
                                        pojo.setBorName(borName);
                                        pojo.setShortName(shortName);
                                        pojo.setBorOfficeLocation(borOfycLoc);
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
                                        pojo.setBorAdd1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setBorAdd2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                        pojo.setBorAdd3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                        pojo.setBorCity(mailCityCode);
                                        pojo.setBorDistrict(mailCityCode);
                                        pojo.setBorPin(borPin);
                                        pojo.setBorTelAreaCode(telAreCode);
                                        pojo.setBorTelNo(telephoneNumber);
                                        pojo.setBorFaxAreaCode(faxAreaCode);
                                        pojo.setBorFaxNo(faxNo);
                                        pojo.setRoAdd1(roAdd1);
                                        pojo.setRoAdd2(roAdd2);
                                        pojo.setRoAdd3(roAdd3);
                                        pojo.setRoCity(roCity);
                                        pojo.setRoDistrict(roDistrict);
                                        pojo.setRoState(roState);
                                        pojo.setRoPin(roPin);
                                        pojo.setRoTelNo(roTelNo);
                                        pojo.setRoTelAreaCode(roTelArea);
                                        pojo.setRoFaxNo(roFaxNo);
                                        pojo.setRoFaxAreaCode(roFaxArea);
                                        pojo.setBorPan(borPan);
                                        pojo.setBorLglConstitution(borLglConst);
                                        pojo.setClassOfAct1(classOFAct1);
                                        pojo.setClassOfAct2(classOfAct2);
                                        pojo.setClassOfAct3(classOfAct3);
                                        pojo.setFiller1(filler1);
                                        pojo.setRelationshipData(relationshipData);
                                        pojo.setRelType(relType);
                                        pojo.setRelPan(relPan);
                                        pojo.setRelationship(relationShip);
                                        pojo.setBusinessEntityName(businessEntityName);
                                        pojo.setJtCustPrefix(jtPreFix);
                                        pojo.setJtCustName(jtFullName);
                                        pojo.setPercControl(percOfContrl);
                                        pojo.setJtAdd1(jtAdd1);
                                        pojo.setJtAdd2(jtAdd2);
                                        pojo.setJtAdd3(jtAdd3);
                                        pojo.setJtCity(jtCity);
                                        pojo.setJtState(jtState);
                                        pojo.setJtPin(jtPin);
                                        pojo.setJtCountry(jtcountry);
                                        pojo.setJtTelNo(jtTelNo);
                                        pojo.setJtTelAreaCode(jtTelArea);
                                        pojo.setFiller2(filler2);
                                        pojo.setCrAcData(crAcData);
                                        pojo.setAcNo(acNo);
                                        pojo.setPreAcno(preAcno);
                                        pojo.setSanctDt(sanctDt);
                                        pojo.setSancAmt(sanctAmt.toString());
                                        pojo.setCurrencyCode("INR");
                                        pojo.setCreditType(creditType);
                                        pojo.setDrawingPower(dp);
                                        List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                        if (!l2.isEmpty()) {
                                            vtr1 = (Vector) l2.get(0);
                                            currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                        }
                                        pojo.setOutstanding(currentBal);
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
                                        pojo.setBnkRemarkCode(bnkRemarkCode);
                                        pojo.setDefaultStatus(defalultStatus);
                                        pojo.setDefaultStatusDt(defaultStatusDt);
                                        pojo.setSuitStatus(suitStatus);
                                        pojo.setSuitRefNo(suitRefNo);
                                        pojo.setSuitAmt(suitAmt.toString());
                                        /*	Filler*/
                                        pojo.setSuitDt(suitDt);
                                        pojo.setFiller3(filler3);
                                        pojo.setGuarantorData(guarantorData);
                                        pojo.setGuarantorType(guarantorType);
                                        pojo.setPanNo(jtPAN);
                                        pojo.setGuarantorName(guarantorName);
                                        pojo.setGuarantorPrefix(guarantoPreFix);
                                        pojo.setGuarantorFullName(guarantorFullName);
                                        pojo.setGuarantorAdd1(guarantorAdd1);
                                        pojo.setGuarantorAdd2(guarantorAdd2);
                                        pojo.setGuarantorAdd3(guarantorAdd3);
                                        pojo.setGuarantorCity(guarantorCity);
                                        pojo.setGuarantorDistrict(guarantorDistrict);
                                        pojo.setGuarantorState(guarantorState);
                                        pojo.setGuarantorPin(guarantorPin);
                                        pojo.setGuarantorCountry(guarantorCountry);
                                        pojo.setGuarantorTelNo(guarantorTelNo);
                                        pojo.setGuarantorTelAreaCode(guarantorTelArea);
                                        pojo.setFiller4(filler4);

                                        pojo.setSanctDt(sanctionDt);
                                        amtOverdue = "";
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
                                        pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                        pojo.setOdBuct1(odBuc1);
                                        pojo.setOdBuct2(odBuc2);
                                        pojo.setOdBuct3(odBuc3);
                                        pojo.setOdBuct4(odBuc4);
                                        pojo.setOdBuct5(odBuc5);
                                        experionList.add(pojo);
                                    }
                                } else {
                                    pojo = new EquifaxComercialPoJo();
                                    pojo.setMemberCode(memberCode);
                                    pojo.setPreMemberCode(preMemberCode);
                                    pojo.setMemberName(branchName);
                                    pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                    pojo.setFutureUse(futureUse);
                                    pojo.setBrnCode(brnCode);
                                    pojo.setBorName(borName);
                                    pojo.setShortName(shortName);
                                    pojo.setBorOfficeLocation(borOfycLoc);
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
                                    pojo.setBorAdd1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setBorAdd2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                    pojo.setBorAdd3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                    pojo.setBorCity(mailCityCode);
                                    pojo.setBorDistrict(mailCityCode);
                                    pojo.setBorPin(borPin);
                                    pojo.setBorTelAreaCode(telAreCode);
                                    pojo.setBorTelNo(telephoneNumber);
                                    pojo.setBorFaxAreaCode(faxAreaCode);
                                    pojo.setBorFaxNo(faxNo);
                                    pojo.setRoAdd1(roAdd1);
                                    pojo.setRoAdd2(roAdd2);
                                    pojo.setRoAdd3(roAdd3);
                                    pojo.setRoCity(roCity);
                                    pojo.setRoDistrict(roDistrict);
                                    pojo.setRoState(roState);
                                    pojo.setRoPin(roPin);
                                    pojo.setRoTelNo(roTelNo);
                                    pojo.setRoTelAreaCode(roTelArea);
                                    pojo.setRoFaxNo(roFaxNo);
                                    pojo.setRoFaxAreaCode(roFaxArea);
                                    pojo.setBorPan(borPan);
                                    pojo.setBorLglConstitution(borLglConst);
                                    pojo.setClassOfAct1(classOFAct1);
                                    pojo.setClassOfAct2(classOfAct2);
                                    pojo.setClassOfAct3(classOfAct3);
                                    pojo.setFiller1(filler1);
                                    pojo.setRelationshipData(relationshipData);
                                    pojo.setRelType(relType);
                                    pojo.setRelPan(relPan);
                                    pojo.setRelationship(relationShip);
                                    pojo.setBusinessEntityName(businessEntityName);
                                    pojo.setJtCustPrefix(jtPreFix);
                                    pojo.setJtCustName(jtFullName);
                                    pojo.setPercControl(percOfContrl);
                                    pojo.setJtAdd1(jtAdd1);
                                    pojo.setJtAdd2(jtAdd2);
                                    pojo.setJtAdd3(jtAdd3);
                                    pojo.setJtCity(jtCity);
                                    pojo.setJtState(jtState);
                                    pojo.setJtPin(jtPin);
                                    pojo.setJtCountry(jtcountry);
                                    pojo.setJtTelNo(jtTelNo);
                                    pojo.setJtTelAreaCode(jtTelArea);
                                    pojo.setFiller2(filler2);
                                    pojo.setCrAcData(crAcData);
                                    pojo.setAcNo(acNo);
                                    pojo.setPreAcno(preAcno);
                                    pojo.setSanctDt(sanctDt);
                                    pojo.setSancAmt(sanctAmt.toString());
                                    pojo.setCurrencyCode("INR");
                                    pojo.setCreditType(creditType);
                                    pojo.setDrawingPower(dp);
                                    List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                    if (!l2.isEmpty()) {
                                        vtr1 = (Vector) l2.get(0);
                                        currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                    }
                                    pojo.setOutstanding(currentBal);
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
                                    pojo.setBnkRemarkCode(bnkRemarkCode);
                                    pojo.setDefaultStatus(defalultStatus);
                                    pojo.setDefaultStatusDt(defaultStatusDt);
                                    pojo.setSuitStatus(suitStatus);
                                    pojo.setSuitRefNo(suitRefNo);
                                    pojo.setSuitAmt(suitAmt.toString());
                                    /*	Filler*/
                                    pojo.setSuitDt(suitDt);
                                    pojo.setFiller3(filler3);
                                    pojo.setGuarantorData(guarantorData);
                                    pojo.setGuarantorType(guarantorType);
                                    pojo.setPanNo(jtPAN);
                                    pojo.setGuarantorName(guarantorName);
                                    pojo.setGuarantorPrefix(guarantoPreFix);
                                    pojo.setGuarantorFullName(guarantorFullName);
                                    pojo.setGuarantorAdd1(guarantorAdd1);
                                    pojo.setGuarantorAdd2(guarantorAdd2);
                                    pojo.setGuarantorAdd3(guarantorAdd3);
                                    pojo.setGuarantorCity(guarantorCity);
                                    pojo.setGuarantorDistrict(guarantorDistrict);
                                    pojo.setGuarantorState(guarantorState);
                                    pojo.setGuarantorPin(guarantorPin);
                                    pojo.setGuarantorCountry(guarantorCountry);
                                    pojo.setGuarantorTelNo(guarantorTelNo);
                                    pojo.setGuarantorTelAreaCode(guarantorTelArea);
                                    pojo.setFiller4(filler4);

                                    pojo.setSanctDt(sanctionDt);
                                    amtOverdue = "";
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
                                    pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                    pojo.setOdBuct1(odBuc1);
                                    pojo.setOdBuct2(odBuc2);
                                    pojo.setOdBuct3(odBuc3);
                                    pojo.setOdBuct4(odBuc4);
                                    pojo.setOdBuct5(odBuc5);
                                    if (accStatus.equalsIgnoreCase("3")) {
                                        writtrnOff = "1";
                                    } else if (accStatus.equalsIgnoreCase("14")) {
                                        writtrnOff = "7";
                                    } else {
                                        writtrnOff = "";
                                    }
                                    experionList.add(pojo);
                                }
                            }
                        }
                    } else {
                        /*If Guarantor Details Does not Exist.*/
                        String garCustFlag = "";
                        String garCustAcNo = "";
                        String garCustId = "";
                        guarantorCurrencyType = "INR";
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

                                pojo = new EquifaxComercialPoJo();

                                pojo.setMemberCode(memberCode);
                                pojo.setPreMemberCode(preMemberCode);
                                pojo.setMemberName(branchName);
                                pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                                pojo.setFutureUse(futureUse);
                                pojo.setBrnCode(brnCode);
                                pojo.setBorName(borName);
                                pojo.setShortName(shortName);
                                pojo.setBorOfficeLocation(borOfycLoc);
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
                                pojo.setBorAdd1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                pojo.setBorAdd2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                                pojo.setBorAdd3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                                pojo.setBorCity(mailCityCode);
                                pojo.setBorDistrict(mailCityCode);
                                pojo.setBorPin(borPin);
                                pojo.setBorTelAreaCode(telAreCode);
                                pojo.setBorTelNo(telephoneNumber);
                                pojo.setBorFaxAreaCode(faxAreaCode);
                                pojo.setBorFaxNo(faxNo);
                                pojo.setRoAdd1(roAdd1);
                                pojo.setRoAdd2(roAdd2);
                                pojo.setRoAdd3(roAdd3);
                                pojo.setRoCity(roCity);
                                pojo.setRoDistrict(roDistrict);
                                pojo.setRoState(roState);
                                pojo.setRoPin(roPin);
                                pojo.setRoTelNo(roTelNo);
                                pojo.setRoTelAreaCode(roTelArea);
                                pojo.setRoFaxNo(roFaxNo);
                                pojo.setRoFaxAreaCode(roFaxArea);
                                pojo.setBorPan(borPan);
                                pojo.setBorLglConstitution(borLglConst);
                                pojo.setClassOfAct1(classOFAct1);
                                pojo.setClassOfAct2(classOfAct2);
                                pojo.setClassOfAct3(classOfAct3);
                                pojo.setFiller1(filler1);
                                pojo.setRelationshipData(relationshipData);
                                pojo.setRelType(relType);
                                pojo.setRelPan(relPan);
                                pojo.setRelationship(relationShip);
                                pojo.setBusinessEntityName(businessEntityName);
                                pojo.setJtCustPrefix(jtPreFix);
                                pojo.setJtCustName(jtFullName);
                                pojo.setPercControl(percOfContrl);
                                pojo.setJtAdd1(jtAdd1);
                                pojo.setJtAdd2(jtAdd2);
                                pojo.setJtAdd3(jtAdd3);
                                pojo.setJtCity(jtCity);
                                pojo.setJtState(jtState);
                                pojo.setJtPin(jtPin);
                                pojo.setJtCountry(jtcountry);
                                pojo.setJtTelNo(jtTelNo);
                                pojo.setJtTelAreaCode(jtTelArea);
                                pojo.setFiller2(filler2);
                                pojo.setCrAcData(crAcData);
                                pojo.setAcNo(acNo);
                                pojo.setPreAcno(preAcno);
                                pojo.setSanctDt(sanctDt);
                                pojo.setSancAmt(sanctAmt.toString());
                                pojo.setCurrencyCode("INR");
                                pojo.setCreditType(creditType);
                                pojo.setDrawingPower(dp);
                                List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                                if (!l2.isEmpty()) {
                                    vtr1 = (Vector) l2.get(0);
                                    currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                                }
                                pojo.setOutstanding(currentBal);
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
                                pojo.setBnkRemarkCode(bnkRemarkCode);
                                pojo.setDefaultStatus(defalultStatus);
                                pojo.setDefaultStatusDt(defaultStatusDt);
                                pojo.setSuitStatus(suitStatus);
                                pojo.setSuitRefNo(suitRefNo);
                                pojo.setSuitAmt(suitAmt.toString());
                                /*	Filler*/
                                pojo.setSuitDt(suitDt);
                                pojo.setFiller3(filler3);
                                pojo.setGuarantorData(guarantorData);
                                pojo.setGuarantorType(guarantorType);
                                pojo.setPanNo(jtPAN);
                                pojo.setGuarantorName(guarantorName);
                                pojo.setGuarantorPrefix(guarantoPreFix);
                                pojo.setGuarantorFullName(guarantorFullName);
                                pojo.setGuarantorAdd1(guarantorAdd1);
                                pojo.setGuarantorAdd2(guarantorAdd2);
                                pojo.setGuarantorAdd3(guarantorAdd3);
                                pojo.setGuarantorCity(guarantorCity);
                                pojo.setGuarantorDistrict(guarantorDistrict);
                                pojo.setGuarantorState(guarantorState);
                                pojo.setGuarantorPin(guarantorPin);
                                pojo.setGuarantorCountry(guarantorCountry);
                                pojo.setGuarantorTelNo(guarantorTelNo);
                                pojo.setGuarantorTelAreaCode(guarantorTelArea);
                                pojo.setFiller4(filler4);

                                pojo.setSanctDt(sanctionDt);
                                amtOverdue = "";
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
                                pojo.setAmtOverDue(String.valueOf(overDueAmt));
                                pojo.setOdBuct1(odBuc1);
                                pojo.setOdBuct2(odBuc2);
                                pojo.setOdBuct3(odBuc3);
                                pojo.setOdBuct4(odBuc4);
                                pojo.setOdBuct5(odBuc5);
                                if (accStatus.equalsIgnoreCase("3")) {
                                    writtrnOff = "1";
                                } else if (accStatus.equalsIgnoreCase("14")) {
                                    writtrnOff = "7";
                                } else {
                                    writtrnOff = "";
                                }
                                experionList.add(pojo);
                            }
                        } else {
                            pojo = new EquifaxComercialPoJo();
                            pojo.setMemberCode(memberCode);
                            pojo.setPreMemberCode(preMemberCode);
                            pojo.setMemberName(branchName);
                            pojo.setReportDate(dmy.format(ymd.parse(toDate)));
                            pojo.setFutureUse(futureUse);
                            pojo.setBrnCode(brnCode);
                            pojo.setBorName(borName);
                            pojo.setShortName(shortName);
                            pojo.setBorOfficeLocation(borOfycLoc);
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
                            pojo.setBorAdd1(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                            pojo.setBorAdd2(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                            address = (address.length() > 40) ? address.substring(address.substring(0, 40).lastIndexOf(space) + 1) : "";
                            pojo.setBorAdd3(address.length() > 0 && address.length() <= 40 ? address.substring(0) : address.length() > 40 ? address.substring(0, 40).substring(0, address.substring(0, 40).lastIndexOf(space)) : "");
                            pojo.setBorCity(mailCityCode);
                            pojo.setBorDistrict(mailCityCode);
                            pojo.setBorPin(borPin);
                            pojo.setBorTelAreaCode(telAreCode);
                            pojo.setBorTelNo(telephoneNumber);
                            pojo.setBorFaxAreaCode(faxAreaCode);
                            pojo.setBorFaxNo(faxNo);
                            pojo.setRoAdd1(roAdd1);
                            pojo.setRoAdd2(roAdd2);
                            pojo.setRoAdd3(roAdd3);
                            pojo.setRoCity(roCity);
                            pojo.setRoDistrict(roDistrict);
                            pojo.setRoState(roState);
                            pojo.setRoPin(roPin);
                            pojo.setRoTelNo(roTelNo);
                            pojo.setRoTelAreaCode(roTelArea);
                            pojo.setRoFaxNo(roFaxNo);
                            pojo.setRoFaxAreaCode(roFaxArea);
                            pojo.setBorPan(borPan);
                            pojo.setBorLglConstitution(borLglConst);
                            pojo.setClassOfAct1(classOFAct1);
                            pojo.setClassOfAct2(classOfAct2);
                            pojo.setClassOfAct3(classOfAct3);
                            pojo.setFiller1(filler1);
                            pojo.setRelationshipData(relationshipData);
                            pojo.setRelType(relType);
                            pojo.setRelPan(relPan);
                            pojo.setRelationship(relationShip);
                            pojo.setBusinessEntityName(businessEntityName);
                            pojo.setJtCustPrefix(jtPreFix);
                            pojo.setJtCustName(jtFullName);
                            pojo.setPercControl(percOfContrl);
                            pojo.setJtAdd1(jtAdd1);
                            pojo.setJtAdd2(jtAdd2);
                            pojo.setJtAdd3(jtAdd3);
                            pojo.setJtCity(jtCity);
                            pojo.setJtState(jtState);
                            pojo.setJtPin(jtPin);
                            pojo.setJtCountry(jtcountry);
                            pojo.setJtTelNo(jtTelNo);
                            pojo.setJtTelAreaCode(jtTelArea);
                            pojo.setFiller2(filler2);
                            pojo.setCrAcData(crAcData);
                            pojo.setAcNo(acNo);
                            pojo.setPreAcno(preAcno);
                            pojo.setSanctDt(sanctDt);
                            pojo.setSancAmt(sanctAmt.toString());
                            pojo.setCurrencyCode("INR");
                            pojo.setCreditType(creditType);
                            pojo.setDrawingPower(dp);
                            List l2 = em.createNativeQuery("select  cast((sum(dramt) - sum(cramt)) as decimal) from " + CbsUtil.getReconTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno = '" + acNo + "' and dt <= '" + toDate + "'").getResultList();
                            if (!l2.isEmpty()) {
                                vtr1 = (Vector) l2.get(0);
                                currentBal = vtr1.get(0) != null ? (Double.parseDouble(vtr1.get(0).toString()) <= 0 ? "0" : vtr1.get(0).toString()) : "0";
                            }
                            pojo.setOutstanding(currentBal);
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
//                                if (dmy.parse(closingDate).after(dmy.parse(toDate))) {
//                                    assetClass = "01";
//                                } else {
//                                    assetClass = "05";
//                                }
                                assetClass = "01";
                            } else {
                                assetClass = "05";
                            }
                            pojo.setAssetClassif(assetClass);
                            pojo.setBnkRemarkCode(bnkRemarkCode);
                            pojo.setDefaultStatus(defalultStatus);
                            pojo.setDefaultStatusDt(defaultStatusDt);
                            pojo.setSuitStatus(suitStatus);
                            pojo.setSuitRefNo(suitRefNo);
                            pojo.setSuitAmt(suitAmt.toString());
                            /*	Filler*/
                            pojo.setSuitDt(suitDt);
                            pojo.setFiller3(filler3);
                            pojo.setGuarantorData(guarantorData);
                            pojo.setGuarantorType(guarantorType);
                            pojo.setPanNo(jtPAN);
                            pojo.setGuarantorName(guarantorName);
                            pojo.setGuarantorPrefix(guarantoPreFix);
                            pojo.setGuarantorFullName(guarantorFullName);
                            pojo.setGuarantorAdd1(guarantorAdd1);
                            pojo.setGuarantorAdd2(guarantorAdd2);
                            pojo.setGuarantorAdd3(guarantorAdd3);
                            pojo.setGuarantorCity(guarantorCity);
                            pojo.setGuarantorDistrict(guarantorDistrict);
                            pojo.setGuarantorState(guarantorState);
                            pojo.setGuarantorPin(guarantorPin);
                            pojo.setGuarantorCountry(guarantorCountry);
                            pojo.setGuarantorTelNo(guarantorTelNo);
                            pojo.setGuarantorTelAreaCode(guarantorTelArea);
                            pojo.setFiller4(filler4);

                            pojo.setSanctDt(sanctionDt);
                            amtOverdue = "";
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
//                                if (Double.parseDouble(odLimit) < Double.parseDouble(currentBal)) {
//                                    amtOverdue = String.valueOf(Integer.parseInt(currentBal) - Integer.parseInt(odLimit));
//                                } else {
//                                    amtOverdue = "0";
//                                }
//                                pojo.setOdBuct1(amtOverdue);
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
//                                List excessList = null;
//                                double excess = 0;
//                                if (isExcessEmi == 0) {
//                                    excessList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + toDate + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
//                                } else {
//                                    excessList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
//                                            + " (select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + toDate + "')").getResultList();
//                                }
//                                if (!excessList.isEmpty()) {
//                                    if (!excessList.isEmpty()) {
//                                        Vector ele = (Vector) excessList.get(0);
//                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                            excess = Double.parseDouble(ele.get(0).toString());
//                                        }
//                                    }
//                                }
//                                List l6 = em.createNativeQuery(" select cast(ifnull(sum(installamt),0) as decimal) as emiamt from emidetails where acno ='" + acNo + "' "
//                                        + " and (PAYMENTDT>'" + toDate + "'  or PAYMENTDT ='' or PAYMENTDT is null ) and duedt<'" + toDate + "'").getResultList();
//                                if (!l6.isEmpty()) {
//                                    vtr1 = (Vector) l6.get(0);
//                                    amtOverdue = vtr1.get(0) != null ? vtr1.get(0).toString() : "0";
//                                    if (!amtOverdue.equalsIgnoreCase("0")) {
//                                        amtOverdue = Integer.parseInt(amtOverdue) > Integer.parseInt(currentBal) ? currentBal : amtOverdue;
//                                    }
//                                    double overDueAmt = Double.parseDouble(amtOverdue) - excess;
//                                    amtOverdue = String.valueOf(overDueAmt);
//                                    pojo.setOdBuct1(amtOverdue);
//                                }
//                                List l7 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,duedt,'" + toDate + "') from emidetails where status = 'Unpaid' and  acno = '" + acNo + "' and duedt < '" + toDate + "'").getResultList();
//                                if (!l7.isEmpty()) {
//                                    vtr1 = (Vector) l7.get(0);
//                                    overDueDays = vtr1.get(0) != null ? (Double.parseDouble(amtOverdue) > 0 ? (Double.parseDouble(vtr1.get(0).toString()) <= 900 ? vtr1.get(0).toString() : "900") : overDueDays) : "";
//                                }
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
                            pojo.setAmtOverDue(String.valueOf(overDueAmt));
                            pojo.setOdBuct1(odBuc1);
                            pojo.setOdBuct2(odBuc2);
                            pojo.setOdBuct3(odBuc3);
                            pojo.setOdBuct4(odBuc4);
                            pojo.setOdBuct5(odBuc5);
                            experionList.add(pojo);
                        }
                    }
                }/*Guarantor details End */
            }/*Main List End*/
            return experionList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
}
