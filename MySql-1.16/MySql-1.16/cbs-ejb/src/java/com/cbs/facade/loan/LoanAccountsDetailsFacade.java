/*
 * Created By    :   ROHIT KRISHNA GUPTA
 * Creation Date :   12 Oct 2010
 */
package com.cbs.facade.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Admin
 */
@Stateless(mappedName = "LoanAccountsDetailsFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanAccountsDetailsFacade implements LoanAccountsDetailsRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    AdvancesInformationTrackingRemote aitr;

    public List actTypeCombo() throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('TL','CA','DL','SS') order by acctcode").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List accountDetail(String acctNo) throws ApplicationException {
        List accountInfo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select a.acno,a.custname,Date_format(a.openingdt,'%d/%m/%Y'),a.accstatus, "
                    + " a.odlimit,b.LOAN_PD_MONTH,a.acctCategory, b.SCHEME_CODE, b.INTEREST_TABLE_CODE, "
                    + " c.CustId, ifnull(a.custid1,''), ifnull(a.custid2,''), ifnull(a.custid3,''), ifnull(a.custid4,'')  "
                    + " from accountmaster a, cbs_loan_acc_mast_sec b, customerid c where a.acno = c.Acno and "
                    + " a.acno = b.ACNO and a.acno='" + acctNo + "'");
            accountInfo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accountInfo;
    }

    public List accountDetailForArmy(String acctNo) throws ApplicationException {
        List accountInfo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select a.acno,a.custname,Date_format(a.openingdt,'%d/%m/%Y'),a.accstatus, a.odlimit  from accountmaster a where a.acno='" + acctNo + "'");
            accountInfo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accountInfo;
    }

    public List gridDetail(String acctNo) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select firmname,firmadd,firmphno,date_format(fromdt,'%d/%m/%Y'),date_format(todt,'%d/%m/%Y'),designation,monthlyincome,reasontoquit,coalesce(empid,''),sno from loan_employmenthistory where acno = '" + acctNo + "' order by sno");
            gridList = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridList;
    }

    public String saveEmploymentDetail(String brCode, String acNo, String firmName, String firmAdd, String firmPhoneNo, String designation, String monthlyIncome, String fromDt, String toDt, String reasonToQuit, String empId, String enterBy, String lastUpdate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if ((brCode == null) || (acNo == null) || (firmName == null) || (firmPhoneNo == null) || (designation == null) || (monthlyIncome == null) || (fromDt == null) || (toDt == null) || (reasonToQuit == null) || (empId == null) || (enterBy == null) || (lastUpdate == null)) {
                ut.rollback();
                return "Please Check All Fields!!!.";
            }
            List dtChk = em.createNativeQuery("select TIMESTAMPDIFF(DAY" + ",'" + fromDt + "','" + toDt + "')").getResultList();
            Vector Lst2 = (Vector) dtChk.get(0);
            int dtDif = Integer.parseInt(Lst2.get(0).toString());
            if (dtDif < 0) {
                ut.rollback();
                return "From Date Must Be Less Than To date !!!.";
            }
            if (firmName.length() > 40) {
                firmName = firmName.substring(0, 40);
            }
            if (firmAdd.length() > 60) {
                firmAdd = firmAdd.substring(0, 60);
            }
            if (firmPhoneNo.length() > 20) {
                firmPhoneNo = firmPhoneNo.substring(0, 20);
            }
            if (designation.length() > 25) {
                designation = designation.substring(0, 25);
            }
            if (reasonToQuit.length() > 60) {
                reasonToQuit = reasonToQuit.substring(0, 60);
            }

//            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
//            Vector Lst = (Vector) tempBdList.get(0);
//            String tempBd = Lst.get(0).toString();

            List maxNo = em.createNativeQuery("select coalesce(max(sno),'0') from loan_employmenthistory where acno = '" + acNo + "'").getResultList();
            Vector Lst1 = (Vector) maxNo.get(0);
            int sno = Integer.parseInt(Lst1.get(0).toString()) + 1;

            Query insertQuery = em.createNativeQuery("insert into loan_employmenthistory(acno,sno,firmname,firmadd,firmphno,designation, monthlyincome,fromdt,todt,reasontoquit,enterby,lastupdate,empid)"
                    + "values('" + acNo + "'," + sno + ",'" + firmName + "','" + firmAdd + "'," + " '" + firmPhoneNo + "', " + " '" + designation + "', " + " '" + Float.parseFloat(monthlyIncome) + "', '" + fromDt + "', " + " '" + toDt + "', '" + reasonToQuit + "', " + " '" + enterBy + "','" + lastUpdate + "','" + empId + "')");
            var1 = insertQuery.executeUpdate();

            if ((var1 > 0)) {
                ut.commit();
                return "Record Saved Successfully";
            } else {
                ut.rollback();
                return "Not Saved.";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }

    }

    public String deleteRecord(String brCode, String acno, String sNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if ((brCode == null) || (acno == null) || (sNo == null)) {
                ut.rollback();
                return "Please Check Values Of Row !!!";
            }
            List acNoChk = em.createNativeQuery("Select * From loan_employmenthistory  WHERE ACNO='" + acno + "' AND SNO=" + sNo).getResultList();
            if (acNoChk.isEmpty() || acNoChk.isEmpty()) {
                ut.rollback();
                return "Data could not be Deleted";
            }
            Query deleteQuery = em.createNativeQuery("Delete From loan_employmenthistory  WHERE ACNO='" + acno + "' AND SNO=" + Integer.parseInt(sNo));
            var = deleteQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List docListCombo(String acType) throws ApplicationException {
        try {
            List docList;
            docList = em.createNativeQuery("SELECT legaldocument FROM legaldocuments_actype_master WHERE ACTYPE = '" + acType + "'").getResultList();
            return docList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List legalDocsGridDetail(String acctNo) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select ACNO,DOCUMENT_CODE,date_format(DUE_DATE,'%d/%m/%Y'),date_format(RECEIVED_DATE,'%d/%m/%Y'),date_format(EXPIRY_DATE,'%d/%m/%Y'),RMKS,DEL_FLG,SCAN_DETAILS_FLG from cbs_loan_acc_doc where acno = '" + acctNo + "'");
            gridList = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridList;
    }

    public List legalDocsNameDetail(String code) throws ApplicationException {
        List docname = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select legaldocument from legaldocuments_actype_master where code=" + Integer.parseInt(code) + "");
            docname = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return docname;
    }

    public String saveDocumentDetails(String brCode, String acno, String docName, String dueDt, String expDt, String recDt, String remarks, String delFlag, String scanFlag, String freeText1, String freeText2, String freeText3, String freeText4, String freeText5, String freeText6, String freeText7, String freeText8, String freeText9, String freeText10) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            String code;
            String accountCode = facadeRemote.getAccountNature(acno);
            if ((brCode == null) || (acno == null) || (docName == null) || (dueDt == null) || (expDt == null) || (recDt == null)) {
                ut.rollback();
                return "Please Check All Fields !!!";
            }
            List codeList = em.createNativeQuery("select code from legaldocuments_actype_master where legaldocument='" + docName + "' and actype='" + accountCode + "'").getResultList();
            if (codeList.isEmpty() || codeList.isEmpty()) {
                ut.rollback();
                return "Data could not be Saved";
            } else {
                Vector Lst = (Vector) codeList.get(0);
                code = Lst.get(0).toString();
            }
            List chk = em.createNativeQuery("select document_code from cbs_loan_acc_doc where acno='" + acno + "' and document_code='" + code + "'").getResultList();
            if (chk.isEmpty() || chk.isEmpty()) {
            } else {
                ut.rollback();
                return "This Document is Already Added , Select Another Document.";
            }
            List dtChk = em.createNativeQuery("select TIMESTAMPDIFF(DAY" + ",'" + recDt + "',curdate())").getResultList();
            Vector Lst2 = (Vector) dtChk.get(0);
            int dtDif = Integer.parseInt(Lst2.get(0).toString());
            if (dtDif > 0) {
                ut.rollback();
                return "Recieve Date Cannot be Less Than Today Date !!!.";
            }
            List dtChk1 = em.createNativeQuery("select TIMESTAMPDIFF(DAY" + ",'" + dueDt + "','" + expDt + "')").getResultList();
            Vector Lst3 = (Vector) dtChk1.get(0);
            int dtDif1 = Integer.parseInt(Lst3.get(0).toString());
            if (dtDif1 < 0) {
                ut.rollback();
                return "Expiry Date Cannot be Less Than Due Date !!!.";
            }
            Query insertQuery = em.createNativeQuery("insert into cbs_loan_acc_doc(ACNO,DOCUMENT_CODE,DUE_DATE,RECEIVED_DATE,EXPIRY_DATE,RMKS,DEL_FLG,SCAN_DETAILS_FLG,FREE_TEXT_1,FREE_TEXT_2,FREE_TEXT_3,FREE_TEXT_4,FREE_TEXT_5,FREE_TEXT_6,FREE_TEXT_7,FREE_TEXT_8,FREE_TEXT_9,FREE_TEXT_10)"
                    + " values ('" + acno + "','" + code + "','" + dueDt + "','" + recDt + "','" + expDt + "','" + remarks + "','" + delFlag.substring(0, 1) + "','" + scanFlag.substring(0, 1) + "','" + freeText1 + "','" + freeText2 + "','" + freeText3 + "','" + freeText4 + "','" + freeText5 + "','" + freeText6 + "','" + freeText7 + "','" + freeText8 + "','" + freeText9 + "','" + freeText10 + "')");
            var = insertQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Saved Succesfully";
            } else {
                ut.rollback();
                return "Document Detail Not Saved.";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String deleteRecordFromLoanAcDoctable(String brCode, String acno, String docName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0, var1 = 0;
            String code;
            if ((brCode == null) || (acno == null) || (docName == null)) {
                ut.rollback();
                return "Please Check Values Of Row !!!";
            }
            String accountCode = facadeRemote.getAccountCode(acno);
            List codeList = em.createNativeQuery("select code from legaldocuments_actype_master where legaldocument='" + docName + "' and actype='" + accountCode + "'").getResultList();
            if (codeList.isEmpty() || codeList.isEmpty()) {
                ut.rollback();
                return "Data could not be Deleted";
            } else {
                Vector Lst = (Vector) codeList.get(0);
                code = Lst.get(0).toString();
            }

            Query insertQuery = em.createNativeQuery("insert into cbs_loan_acc_doc_his select * from cbs_loan_acc_doc where acno='" + acno + "' and DOCUMENT_CODE='" + code + "'");
            var = insertQuery.executeUpdate();
            Query deleteQuery = em.createNativeQuery("Delete From cbs_loan_acc_doc where acno='" + acno + "' and DOCUMENT_CODE='" + code + "'");
            var1 = deleteQuery.executeUpdate();
            if ((var > 0) && (var1 > 0)) {
                ut.commit();
                return "Document Record Deleted Successfully";
            } else {
                ut.rollback();
                return "Data could not be Deleted.";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String loanShareDtOnLoad(String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = null;
            checkList = em.createNativeQuery("select ShareMoney,NomineeName,FShareType,FNominalMem,MemNo,date_format(MembershipDt,'%d/%m/%Y') from loan_share where acno='" + acno + "'").getResultList();
            if (checkList.isEmpty()) {
                ut.rollback();
                return "";
            }
            Vector secLst = (Vector) checkList.get(0);
            String ShareMoney = secLst.get(0).toString();
            String NomineeName = secLst.get(1).toString();
            String FShareType = secLst.get(2).toString();
            if (FShareType.equalsIgnoreCase("R")) {
                FShareType = "REGULAR";
            } else {
                FShareType = "NOMINAL";
            }
            String FNominalMem = secLst.get(3).toString();
            if (FNominalMem.equalsIgnoreCase("0")) {
                FNominalMem = "No";
            } else {
                FNominalMem = "Yes";
            }
            String MemNo = secLst.get(4).toString();
            String MembershipDt = secLst.get(5).toString();

            ut.commit();
            return ShareMoney + ":" + NomineeName + ":" + FShareType + ":" + FNominalMem + ":" + MemNo + ":" + MembershipDt;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String loanShareDtSave(String acno, String ShareType, Float ShareMoney, String NomineeName, String MembershipDt, String NominalMem, String MemNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = null;
            String YN = "";
            if ((ShareMoney.isNaN() == true) || (ShareMoney < 0)) {
                ut.rollback();
                return "Please Enter a valid Share Money.";
            }
            if (NomineeName.equalsIgnoreCase("")) {
                ut.rollback();
                return "Please enter the Nominee Name.";
            }
            if (MemNo.equalsIgnoreCase("")) {
                ut.rollback();
                return "Please enter valid Nominal No.";
            }
            checkList = em.createNativeQuery("select * from loan_share where memno='" + MemNo + "'").getResultList();
            if (checkList.size() > 0) {
                ut.rollback();
                return "Member No. already exists";
            }
            if (NominalMem.equalsIgnoreCase("Y")) {
                YN = "1";
            } else {
                YN = "0";
            }
            List dtDiffLt = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,CURDATE(),'" + MembershipDt + "')").getResultList();
            Vector dtDiffLtVec = (Vector) dtDiffLt.get(0);
            Integer diff = Integer.parseInt(dtDiffLtVec.get(0).toString());
            if (diff > 0) {
                ut.rollback();
                return "MEMBERSHIP DATE CANNOT BE GREATER THAN CURRENT DATE !!!";
            }
            List checkList1 = em.createNativeQuery("select * from loan_share where acno='" + acno + "'").getResultList();
            if (checkList1.isEmpty()) {
                Query insertQuery = em.createNativeQuery("insert into loan_share(Acno,ShareMoney,NomineeName,FShareType,FNominalMem,MemNo,MembershipDt)"
                        + "values (" + "'" + acno + "'" + "," + "'" + ShareMoney + "'" + "," + "'" + NomineeName + "'" + "," + "'" + ShareType + "'" + "," + "'" + YN + "'" + "," + "'" + MemNo + "'" + "," + "date_format('" + MembershipDt + "','%Y-%m-%d'))");
                int var = insertQuery.executeUpdate();
                if (var > 0) {
                    ut.commit();
                    return "DATA SAVE SUCCESSFULLY";
                } else {
                    ut.rollback();
                    return "PROBLEM IN SAVING";
                }
            } else {
                Query updateQuery = em.createNativeQuery("update loan_share set ShareMoney =" + ShareMoney + " ,NomineeName = '" + NomineeName + "',FShareType ='" + ShareType + "',FNominalMem ='" + NominalMem + "',MemNo ='" + MemNo + "',MembershipDt = '" + MembershipDt + "' where acno='" + acno + "'");
                int exec = updateQuery.executeUpdate();
                if (exec > 0) {
                    ut.commit();
                    return "DATA UPDATED SUCSESFULLY";
                } else {
                    ut.rollback();
                    return "PROBLEM IN UPDATING";
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String acNoCheckForGuar(String acno) throws ApplicationException {
        String message = "";
        String accNature = "";
        String custId = "";
        try {
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                accNature = recLst.get(0).toString();
            } else {
                message = "ACCOUNT NATURE NOT FOUND !!!";
                return message;
            }
            if (accNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || accNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk1 = em.createNativeQuery("SELECT * FROM td_accountmaster WHERE ACNO='" + acno + "'").getResultList();
                if (chk1.isEmpty()) {
                    message = "THIS ACCOUNT NO. IS NOT VALID !!!";
                    return message;
                }
            } else {
                List chk1 = em.createNativeQuery("SELECT * FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
                if (chk1.isEmpty()) {
                    message = "THIS ACCOUNT NO. IS NOT VALID !!!";
                    return message;
                }
            }
            List chk2 = em.createNativeQuery("SELECT CUSTID FROM customerid WHERE ACNO='" + acno + "'").getResultList();
            if (!chk2.isEmpty()) {
                Vector chk2Vec = (Vector) chk2.get(0);
                custId = chk2Vec.get(0).toString();
                List chk3 = em.createNativeQuery("SELECT * FROM loan_guarantordetails WHERE GAR_CUSTID='" + custId + "'").getResultList();
                if (!chk3.isEmpty()) {
                    message = "THIS ACCOUNT HOLDER HAS ALREADY GIVE THE GUARANTEE FOR ANOTHER ACCOUNT !!!";
                    return message;
                } else {
                    message = "true" + custId;
                    return message;
                }
            } else {
                message = "false";
                return message;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List guarontorDetailIfBankCust(String custId) throws ApplicationException {
        List checkList = null;
        try {
            checkList = em.createNativeQuery("select custname,concat(mailaddressline1,mailaddressline2),fathername,mobilenumber,TIMESTAMPDIFF(YEAR,dateofbirth,CURDATE()) from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public List loanGuarantorDtOnLoad(String acno) throws ApplicationException {
        List checkList = null;
        try {
            checkList = em.createNativeQuery("select name,Fathersname,age,cast(ifnull(retirementage, 0) as unsigned),Address,PhNo,occupation,firmname,firmaddress,firmphno,designation,networth,coalesce(gar_acno,''),coalesce(cust_flag,'') from loan_guarantordetails where acno = '" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public String gurnatorDtSave(String actionFlag, String authby, String custFlag, String custidacno, String acno, String name, String Fathersname, Integer age, String retirementage, String Address, String PhNo, String occupation, String firmname, String firmaddress, String firmphno, String designation, Float networth) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = null;
            String YN = "";
            String tmpCustId = "";
            if (name.equalsIgnoreCase("")) {
                ut.rollback();
                return "Please enter the Name.";
            }
            if ((networth == null) || (networth < 0) || (networth.isNaN() == true)) {
                ut.rollback();
                return "Please enter Valid the Net Worth.";
            }

            if (acno.equalsIgnoreCase(custidacno)) {
                ut.rollback();
                return "ACCOUNT HOLDER CANNOT GIVE GUARANTEE FOR SELF !!!.";
            }

            if (actionFlag.equalsIgnoreCase("UPDATE")) {
                Query insertQuery = em.createNativeQuery("insert into loan_guarantordetails_his(ACNO,NAME,ADDRESS,PHNO,OCCUPATION,FIRMNAME,FIRMADDRESS,FIRMPHNO,DESIGNATION,FATHERSNAME,age,RETIREMENTAGE,OFFICEADDRESS,NETWORTH,AUTHBY,ENTERBY,TRANTIME,CUST_FLAG,GAR_ACNO) select ACNO,NAME,ADDRESS,PHNO,OCCUPATION,FIRMNAME,FIRMADDRESS,FIRMPHNO,DESIGNATION,FATHERSNAME,age,RETIREMENTAGE,OFFICEADDRESS,NETWORTH,AUTHBY,'" + authby + "',now(),CUST_FLAG,GAR_ACNO from loan_guarantordetails where acno='" + acno + "' and   name='" + name + "'  and fathersname='" + Fathersname + "' and ifnull(phno,'') =  '" + PhNo + "'");
                int exec = insertQuery.executeUpdate();
                Query updateQuery = em.createNativeQuery("update loan_guarantordetails set address='" + Address + "' ,phno='" + PhNo + "' where acno='" + acno + "' and name='" + name + "' and fathersname='" + Fathersname + "'");
                int exec1 = updateQuery.executeUpdate();
                if ((exec > 0) || (exec1 > 0)) {
                    ut.commit();
                    return "DATA UPDATED SUCCESSFULLY";
                } else {
                    ut.rollback();
                    return "PROBLEM IN UPDATION";
                }
            } else {
                if (custFlag.equalsIgnoreCase("YA")) {
                    List checkList1 = em.createNativeQuery("select * from accountmaster where acno = '" + custidacno + "'").getResultList();
                    List checkList2 = em.createNativeQuery("select * from td_accountmaster where acno = '" + custidacno + "'").getResultList();
                    if (checkList1.isEmpty()) {
                        if (checkList2.isEmpty()) {
                            ut.rollback();
                            return "Gurantor is not our Customer";
                        }
                    }
                    if (checkList2.isEmpty()) {
                        if (checkList1.isEmpty()) {
                            ut.rollback();
                            return "Gurantor is not our Customer";
                        }
                    }
                    List checkList3 = em.createNativeQuery("select CUSTID from customerid WHERE ACNo='" + custidacno + "'").getResultList();
                    if (!checkList3.isEmpty()) {
                        Vector checkList3Vec = (Vector) checkList3.get(0);
                        tmpCustId = checkList3Vec.get(0).toString();
                    }
                    String accSeq = common.getAccseq();
                    if (!accSeq.equalsIgnoreCase("M")) {
                        List guarCkh = em.createNativeQuery("select * from loan_guarantordetails where gar_custid='" + tmpCustId + "'").getResultList();
                        if (guarCkh.isEmpty()) {
                        } else {
                            ut.rollback();
                            return "This Guarantor Is Already Give The Gaurantee For Another Account.";
                        }
                    }
                    if (facadeRemote.getBankCode().equalsIgnoreCase("INDR")) {
                        List guarCkh = em.createNativeQuery("select * from loan_guarantordetails where gar_custid='" + tmpCustId + "'").getResultList();
                        if (guarCkh.isEmpty()) {
                        } else {
                            ut.rollback();
                            return "This Guarantor Is Already Give The Gaurantee For Another Account.";
                        }
                    }
                }
                if (custFlag.equalsIgnoreCase("YC") || custFlag.equalsIgnoreCase("YF")) {
                    tmpCustId = custidacno;
                }

                String gChk = gurnatorCompare(tmpCustId, acno, networth);
                if (!gChk.equalsIgnoreCase("true")) {
                    ut.rollback();
                    return gChk;
                }

                Query insertQuery = em.createNativeQuery("insert into loan_guarantordetails(acno,name,address,phno,occupation,firmname,firmaddress,firmphno,designation,fathersname,age,retirementage,networth,CUST_FLAG,GAR_ACNO,GAR_CUSTID,ENTERBY,TRANTIME,AUTHBY)"
                        + " values (" + "'" + acno + "'" + "," + "'" + name + "'" + "," + "'" + Address + "'" + "," + "'" + PhNo + "'" + "," + "'" + occupation + "'" + "," + "'" + firmname + "'" + "," + "'" + firmaddress + "'" + "," + "'" + firmphno + "'" + "," + "'" + designation + "'" + "," + "'" + Fathersname + "'" + "," + age + "," + retirementage + "," + networth + "," + "'" + custFlag + "'" + "," + "'" + custidacno + "','" + tmpCustId + "','" + authby + "',now(),'System')");
                int exec2 = insertQuery.executeUpdate();
                if ((exec2 > 0)) {
                    ut.commit();
                    return "DATA SAVED SUCCESSFULLY";
                } else {
                    ut.rollback();
                    return "PROBLEM IN SAVING";
                }

            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String loanGuarantorDtDelete(String authby, String acno, String name, String Fathersname, String Address, String PhNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertQuery = em.createNativeQuery("insert into loan_guarantordetails_his(ACNO,NAME,ADDRESS,PHNO,OCCUPATION,FIRMNAME,FIRMADDRESS,FIRMPHNO,DESIGNATION,FATHERSNAME,age,RETIREMENTAGE,OFFICEADDRESS,NETWORTH,AUTHBY,ENTERBY,TRANTIME,CUST_FLAG,GAR_ACNO) select ACNO,NAME,ADDRESS,PHNO,OCCUPATION,FIRMNAME,FIRMADDRESS,FIRMPHNO,DESIGNATION,FATHERSNAME,age,RETIREMENTAGE,OFFICEADDRESS,NETWORTH,AUTHBY,'" + authby + "',now(),CUST_FLAG,GAR_ACNO from loan_guarantordetails where acno='" + acno + "' and   name='" + name + "'  and fathersname='" + Fathersname + "' and ifnull(phno,'') =  '" + PhNo + "'");
            int exec5 = insertQuery.executeUpdate();
            Query delQuery = em.createNativeQuery("Delete From loan_guarantordetails  WHERE ACNO='" + acno + "' AND name='" + name + "' and fathersname='" + Fathersname + "' and address='" + Address + "'");
            int exec6 = delQuery.executeUpdate();
            if ((exec5 > 0) || (exec6 > 0)) {
                ut.commit();
                return "DATA DELETED SUCCESSFULLY";
            } else {
                ut.rollback();
                return "PROBLEM IN DELETION";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List insuranceStatusCombo() throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select description from codebook where groupcode=33 and code <> 0").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List insuranceGridLoad(String acno) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select sno,acno,covernoteno,date_format(fromdt,'%d/%m/%Y'),date_format(todt,'%d/%m/%Y'),premiumpaid,particulars,status,enterby,date_format(dt,'%d/%m/%Y'),coalesce(assetdesc,''),coalesce(assetval,''),coalesce(inscomname,''),coalesce(instype,'') from loan_insurance where acno='" + acno + "' order by  sno").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List insuranceTypeCombo() throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where REF_REC_NO='083'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List insuranceCompanyCombo() throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where REF_REC_NO='053'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List setInsuranceCompany(String insComName) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + insComName + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List setInsuranceType(String insType) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + insType + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveInsuranceDetail(String acno, String assetDesc, Float assetval, String insComName, String insType, String coverNoteNo, String status, String issueDt, String expDt, Float insAmt, String insDetail, String userName, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if ((acno == null) || (assetDesc == null) || (insComName == null) || (insType == null) || (coverNoteNo == null) || (status == null) || (issueDt == null) || (expDt == null) || (insDetail == null)) {
                ut.rollback();
                return "Please Check All Fields !!!";
            }
            List dtChk1 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + issueDt + "','" + expDt + "')").getResultList();
            Vector Lst3 = (Vector) dtChk1.get(0);
            int dtDif1 = Integer.parseInt(Lst3.get(0).toString());
            if (dtDif1 < 0) {
                ut.rollback();
                return "Expiry Date Cannot be Less Than Issue Date !!!.";
            }
            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector Lst = (Vector) tempBdList.get(0);
            String tempBd = Lst.get(0).toString();
            List dateDiffChkLt = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,'" + tempBd + "','" + issueDt + "')").getResultList();
            Vector dateDiffChkLtVec = (Vector) dateDiffChkLt.get(0);
            Integer diffTmp = Integer.parseInt(dateDiffChkLtVec.get(0).toString());
            if (diffTmp > 0) {
                ut.rollback();
                return "Isssue Date Should Be Less Than Current Date !!!.";
            }
            List insComList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_desc='" + insComName + "'").getResultList();
            Vector Lst1 = (Vector) insComList.get(0);
            String insCom = Lst1.get(0).toString();

            List insTypeList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_desc='" + insType + "'").getResultList();
            Vector Lst2 = (Vector) insTypeList.get(0);
            String insTy = Lst2.get(0).toString();
            Query insertQuery = em.createNativeQuery("insert into loan_insurance(acno,covernoteno,fromdt,todt,premiumpaid,particulars,status,enterby,dt,assetdesc,assetval,inscomname,instype) values('" + acno + "','" + coverNoteNo + "','" + issueDt + "','" + expDt + "'," + insAmt + ",'" + insDetail + "','" + status + "','" + userName + "','" + tempBd + "','" + assetDesc + "'," + assetval + ",'" + insCom + "','" + insTy + "')");
            var = insertQuery.executeUpdate();
            if ((var > 0)) {
                ut.commit();
                return "DATA SAVED SUCCESSFULLY";
            } else {
                ut.rollback();
                return "NOT SAVED";
            }


        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String updateInsuranceDetail(String acno, int sNo, String assetDesc, Float assetval, String insComName, String insType, String coverNoteNo, String status, String issueDt, String expDt, Float insAmt, String insDetail, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if ((acno == null) || (assetDesc == null) || (insComName == null) || (insType == null) || (coverNoteNo == null) || (status == null) || (issueDt == null) || (expDt == null) || (insDetail == null)) {
                ut.rollback();
                return "Please Check All Fields !!!";
            }
            List dtChk1 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + issueDt + "','" + expDt + "')").getResultList();
            Vector Lst3 = (Vector) dtChk1.get(0);
            int dtDif1 = Integer.parseInt(Lst3.get(0).toString());
            if (dtDif1 < 0) {
                ut.rollback();
                return "Expiry Date Cannot be Less Than Issue Date !!!.";
            }

            List insComList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_desc='" + insComName + "'").getResultList();
            Vector Lst1 = (Vector) insComList.get(0);
            String insCom = Lst1.get(0).toString();

            List insTypeList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_desc='" + insType + "'").getResultList();
            Vector Lst2 = (Vector) insTypeList.get(0);
            String insTy = Lst2.get(0).toString();


            Query updateQuery = em.createNativeQuery("Update loan_insurance set assetdesc='" + assetDesc + "', assetval=" + assetval + " , inscomname='" + insCom + "',instype='" + insTy + "',covernoteno='" + coverNoteNo + "'," + " fromdt='" + issueDt + "'," + " Todt='" + expDt + "'," + " PremiumPaid=" + insAmt + "," + " status='" + status + "'," + " particulars='" + insDetail + "'" + " where sno=" + sNo + " and " + " acno='" + acno + "'");
            var1 = updateQuery.executeUpdate();
            if ((var1 > 0)) {
                ut.commit();
                return "DATA UPDATED SUCCESSFULLY";
            } else {
                ut.rollback();
                return "PROBLEM IN UPDATION";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List groupCompanyGridLoad(String acno) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select firmname,address,phno from loan_groupcompanydetails where acno = '" + acno + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List companyDetailOnLoad(String acno) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select name,regDoffadd,Location,date_format(incorpdt,'%d/%m/%Y'),authcapital,subscapital,networth,dir1,dir2,dir3,dir4,dir5,dir6,dir7 from loan_companydetails where acno = '" + acno + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveCompanyDetail(String acno, String cmpnyName, String regdoffadd, String location, String incorpdt, String authcapital, String subscapital, String networth, String dir1, String dir2, String dir3, String dir4, String dir5, String dir6, String dir7, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if ((acno == null) || (cmpnyName == null) || (regdoffadd == null) || (location == null) || (incorpdt == null) || (authcapital == null) || (subscapital == null) || (networth == null) || (enterBy == null)) {
                ut.rollback();
                return "Please Check Company Detail Fields !!!";
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (authcapital.equalsIgnoreCase("") || authcapital.length() == 0) {
                ut.rollback();
                return "Please Enter Authorised Capital !!!";
            }
            Matcher authcapitalCheck = p.matcher(authcapital);
            if (!authcapitalCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Authorised Capital !!!";
            }
            if (Float.parseFloat(authcapital) < 0) {
                ut.rollback();
                return "Authorised Capital Cannot Be Less Then Zero !!!";
            }

            if (subscapital.equalsIgnoreCase("") || subscapital.length() == 0) {
                ut.rollback();
                return "Please Enter Subscribed Capital !!!";
            }
            Matcher subscapitalCheck = p.matcher(subscapital);
            if (!subscapitalCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Subscribed Capital !!!";
            }
            if (Float.parseFloat(subscapital) < 0) {
                ut.rollback();
                return "Subscribed Capital Cannot Be Less Then Zero !!!";
            }

            if (networth.equalsIgnoreCase("") || networth.length() == 0) {
                ut.rollback();
                return "Please Enter Net Worth !!!";
            }
            Matcher networthCheck = p.matcher(networth);
            if (!networthCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Net Worth !!!";
            }
            if (Float.parseFloat(networth) < 0) {
                ut.rollback();
                return "Net Worth Cannot Be Less Then Zero !!!";
            }

            if (cmpnyName.length() > 40) {
                cmpnyName = cmpnyName.substring(0, 40);
            }
            if (regdoffadd.length() > 40) {
                regdoffadd = regdoffadd.substring(0, 60);
            }
            if (location.length() > 40) {
                location = location.substring(0, 60);
            }

            List dtChk1 = em.createNativeQuery("select TIMESTAMPDIFF(DAY, curDate(),'" + incorpdt + "')").getResultList();
            Vector Lst3 = (Vector) dtChk1.get(0);
            int dtDif1 = Integer.parseInt(Lst3.get(0).toString());
            if (dtDif1 > 0) {
                ut.rollback();
                return "Incorporation Date Cannot be Greater Than Today Date !!!.";
            }

            Query insertQuery = em.createNativeQuery("insert into loan_companydetails(acno,name,regdoffadd,location, " + " incorpdt,authcapital,subscapital, networth,dir1,dir2,dir3,dir4,dir5,dir6,dir7,ENTERBY,AUTHBY) values " + " ('" + acno + "', " + " '" + cmpnyName + "', " + " '" + regdoffadd + "', " + " '" + location + "', " + " '" + incorpdt + "', " + " " + Double.parseDouble(authcapital) + ", " + " " + Double.parseDouble(subscapital) + ", " + " " + Double.parseDouble(networth) + ", " + " '" + dir1 + "', " + " '" + dir2 + "', " + " '" + dir3 + "', " + " '" + dir4 + "', " + " '" + dir5 + "', " + " '" + dir6 + "', " + " '" + dir7 + "', " + " '" + enterBy + "', " + " 'SYSTEM')");
            var = insertQuery.executeUpdate();
            if ((var > 0)) {
                ut.commit();
                return "COMPANY DETAIL SAVED SUCCESSFULLY";
            } else {
                ut.rollback();
                return "NOT SAVED";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String updateCompanyDetail(String acno, String cmpnyName, String regdoffadd, String location, String incorpdt, String authcapital, String subscapital, String networth, String dir1, String dir2, String dir3, String dir4, String dir5, String dir6, String dir7, String enterBy, String oldCmpnyname, String oldRegOffice, String oldLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if ((acno == null) || (cmpnyName == null) || (regdoffadd == null) || (location == null) || (incorpdt == null) || (authcapital == null) || (subscapital == null) || (networth == null) || (enterBy == null)) {
                ut.rollback();
                return "Please Check Company Details Fields !!!";
            }
            if ((oldCmpnyname == null) || (oldRegOffice == null) || (oldLocation == null)) {
                ut.rollback();
                return "Please Select Proper Row !!!";
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (authcapital.equalsIgnoreCase("") || authcapital.length() == 0) {
                ut.rollback();
                return "Please Enter Authorised Capital !!!";
            }
            Matcher authcapitalCheck = p.matcher(authcapital);
            if (!authcapitalCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Authorised Capital !!!";
            }
            if (Float.parseFloat(authcapital) < 0) {
                ut.rollback();
                return "Authorised Capital Cannot Be Less Then Zero !!!";
            }

            if (subscapital.equalsIgnoreCase("") || subscapital.length() == 0) {
                ut.rollback();
                return "Please Enter Subscribed Capital !!!";
            }
            Matcher subscapitalCheck = p.matcher(subscapital);
            if (!subscapitalCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Subscribed Capital !!!";
            }
            if (Float.parseFloat(subscapital) < 0) {
                ut.rollback();
                return "Subscribed Capital Cannot Be Less Then Zero !!!";
            }

            if (networth.equalsIgnoreCase("") || networth.length() == 0) {
                ut.rollback();
                return "Please Enter Net Worth !!!";
            }
            Matcher networthCheck = p.matcher(networth);
            if (!networthCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Net Worth !!!";
            }
            if (Float.parseFloat(networth) < 0) {
                ut.rollback();
                return "Net Worth Cannot Be Less Then Zero !!!";
            }

            if (cmpnyName.length() > 40) {
                cmpnyName = cmpnyName.substring(0, 40);
            }
            if (regdoffadd.length() > 40) {
                regdoffadd = regdoffadd.substring(0, 60);
            }
            if (location.length() > 40) {
                location = location.substring(0, 60);
            }

            List dtChk1 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,curDate(),'" + incorpdt + "')").getResultList();
            Vector Lst3 = (Vector) dtChk1.get(0);
            int dtDif1 = Integer.parseInt(Lst3.get(0).toString());
            if (dtDif1 > 0) {
                ut.rollback();
                return "Incorporation Date Cannot be Greater Than Today Date !!!.";
            }

            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N'").getResultList();
            Vector Lst = (Vector) tempBdList.get(0);
            String tempBd = Lst.get(0).toString();

            Query updateQuery = em.createNativeQuery("update loan_companydetails set " + " acno='" + acno + "', " + " name='" + cmpnyName + "', " + " regDoffadd='" + regdoffadd + "', " + " location='" + location + "', " + " incorpdt='" + incorpdt + "', " + " authcapital=" + Double.parseDouble(authcapital) + ", " + " subscapital=" + Double.parseDouble(subscapital) + ", " + " networth=" + Double.parseDouble(networth) + ", " + " dir1='" + dir1 + "', " + " dir2='" + dir2 + "', " + " dir3='" + dir3 + "', " + " dir4='" + dir4 + "', " + " dir5='" + dir5 + "', " + " dir6='" + dir6 + "', " + " dir7='" + dir7 + "', " + " LASTUPDATE='" + tempBd + "' where acno='" + acno + "' and " + " name='" + oldCmpnyname + "' and location= '" + oldLocation + "' and regDoffadd= '" + oldRegOffice + "'");
            var = updateQuery.executeUpdate();
            if ((var > 0)) {
                ut.commit();
                return "COMPANY DETAIL UPDATED SUCCESSFULLY";
            } else {
                ut.rollback();
                return "NOT UPDATED";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String saveGroupCompanyDetail(String acno, String grComName, String grCompAdd, String grCompPhNo, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if ((acno == null) || (grComName == null) || (grCompAdd == null) || (grCompPhNo == null) || (enterBy == null)) {
                ut.rollback();
                return "Please Check Group Company Details Fields !!!";
            }
            if (grComName.length() > 50) {
                grComName = grComName.substring(0, 50);
            }
            if (grCompAdd.length() > 45) {
                grCompAdd = grCompAdd.substring(0, 45);
            }
            if (grCompPhNo.length() > 12) {
                grCompPhNo = grCompPhNo.substring(0, 12);
            }
            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N'").getResultList();
            Vector Lst = (Vector) tempBdList.get(0);
            String tempBd = Lst.get(0).toString();

            Query insertQuery = em.createNativeQuery("insert into loan_groupcompanydetails(acno,firmname,address,phno,lastupdatedt,enterby,authby) values " + " ('" + acno + "','" + grComName + "', " + " '" + grCompAdd + "', " + " '" + grCompPhNo + "','" + tempBd + "','" + enterBy + "','SYSTEM')");
            var = insertQuery.executeUpdate();
            if ((var > 0)) {
                ut.commit();
                return "GROUP COMPANY DETAIL SAVED SUCCESSFULLY";
            } else {
                ut.rollback();
                return "NOT SAVED";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String deleteFromGroupCompDetailGrid(String acno, String name, String address, String phNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if ((acno == null) || (address == null) || (phNo == null)) {
                ut.rollback();
                return "Please Select Proper Row !!!";
            }
            Query deleteQuery = em.createNativeQuery("Delete From loan_groupcompanydetails  WHERE ACNO='" + acno + "' AND firmname='" + name + "' and address='" + address + "' and phno='" + phNo + "'");
            var = deleteQuery.executeUpdate();
            if ((var > 0)) {
                ut.commit();
                return "Group Company Record Deleted Successfully";
            } else {
                ut.rollback();
                return "NOT DELETED";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String gurnatorCompare(String custId, String acno, float netWordth) throws ApplicationException {
        try {
            String msg = "true";
            String shNo = "", gurFlg = "N";
            int gurNo, gurCount;
            if (custId.equalsIgnoreCase("") || custId == null) {
            } else {
                List sChkList = em.createNativeQuery("select scheme_code from cbs_loan_acc_mast_sec where acno = '" + acno + "'").getResultList();
                if (sChkList.isEmpty()) {
                    msg = "No Details Exist In cbs_loan_acc_mast_sec";
                } else {
                    Vector sNoLst = (Vector) sChkList.get(0);
                    shNo = sNoLst.get(0).toString();

                    List gurChk = em.createNativeQuery("select ifnull(ledger_folio_calc_event,'N'),ifnull(adhoc_pass_sheet_print_event,0) from cbs_scheme_currency_details "
                            + " where scheme_code ='" + shNo + "'").getResultList();
                    Vector gurLst = (Vector) gurChk.get(0);
                    gurFlg = gurLst.get(0).toString();

//                    String gVal = gurLst.get(1).toString();
//                    if (gVal.equalsIgnoreCase("")) {
//                        gurNo = 0;
//                    } else {
//                        gurNo = Integer.parseInt(gVal);
//                    }

                    if (gurFlg.equalsIgnoreCase("Y")) {
                        Vector tempVector = (Vector) em.createNativeQuery("select * from share_holder where custid = '" + custId + "'").getResultList().get(0);
                        if (tempVector.isEmpty()) {
                            msg = "Customer Id Is not Of The Member";
                        } else {
                            List guarCkh = em.createNativeQuery(" select count(ag.acno), ifnull(sum(ifnull(NETWORTH,0)),0) from loan_guarantordetails ag, accountmaster a where ag.gar_custid = '" + custId + "' and ag.acno = a.acno and a.accstatus <>9 ").getResultList();
                            if (!guarCkh.isEmpty()) {
                                Vector gurCnt = (Vector) guarCkh.get(0);
                                gurCount = Integer.parseInt(gurCnt.get(0).toString());
                                float netWordthGiven = Float.parseFloat(gurCnt.get(1).toString());
                                float guNetworth = Float.parseFloat(aitr.getGuarantorLimit(shNo)) * Float.parseFloat(aitr.getGuarantorSalary(custId));
                                double remaingNetWorth = guNetworth - netWordthGiven;
                                double installmentPaidAmt = aitr.getGuarantorPaticipateAmt(custId);
                                double leftTotalNetWorth = remaingNetWorth + installmentPaidAmt;
//                                if ((netWordthGiven) > guNetworth) {
//                                    msg = "Gurnator has already taken the gurnatee more than defined limit";
//                                } 
                                if (netWordth > leftTotalNetWorth) {
                                    msg = "Networth and total given networth are more than defined limit";
                                }
                            }
                        }
                    }
                }
            }
            return msg;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
