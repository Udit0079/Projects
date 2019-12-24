/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.other.venderMasterPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "OtherMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OtherMgmtFacade implements OtherMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    CommonReportMethodsRemote common;
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void activityAlertCheck(String todayDt, String orgBrnCode) throws Exception {
        try {
            String monthLastDt = yyyyMMdd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(todayDt.substring(4, 6).trim()),
                    Integer.parseInt(todayDt.substring(0, 4).trim())));
            String monthLastWorkingDt = calculateLastWorkingDtOfMonth(todayDt, monthLastDt, orgBrnCode);
            System.out.println("Month Last Working Date-->" + monthLastWorkingDt);
            if (yyyyMMdd.parse(todayDt).compareTo(yyyyMMdd.parse(monthLastWorkingDt)) == 0) {
                List list = em.createNativeQuery("select min(mindate),min(maxdate) from cbs_yearend where yearendflag='N'").getResultList();
                if (list.isEmpty()) {
                    throw new Exception("Please define CBS year end data.");
                }
                Vector ele = (Vector) list.get(0);
                String startFinYearDt = ele.get(0).toString().trim();

                List<String> monthTypeList = whichTypeOfMonthInFinYear(todayDt, startFinYearDt);
                String monthTypes = "";
                for (String monthType : monthTypeList) {
                    if (monthTypes.equals("")) {
                        monthTypes = "'" + monthType + "'";
                    } else {
                        monthTypes = monthTypes + "," + "'" + monthType + "'";
                    }
                }
                System.out.println("monthType---->" + monthTypes);
                String monthStartDt = monthLastDt.substring(0, 6) + "01";

                List activityList = em.createNativeQuery("select distinct activity_code from cbs_activity_alert where "
                        + "frequency in(" + monthTypes + ") and branch_code='" + orgBrnCode + "' order by activity_code").getResultList();
                for (int i = 0; i < activityList.size(); i++) {
                    Vector actVec = (Vector) activityList.get(i);
                    String activityCode = actVec.get(0).toString().trim();

                    List activityBulkList = em.createNativeQuery("select activity_code,activity_name,branch_code,frequency,"
                            + "ac_type,flag from cbs_activity_alert where frequency in(" + monthTypes + ") and "
                            + "branch_code='" + orgBrnCode + "' and activity_code='" + activityCode + "'").getResultList();

                    for (int j = 0; j < activityBulkList.size(); j++) { //Check here, if it is empty and also not
                        Vector bulkEle = (Vector) activityBulkList.get(j);
                        String acType = bulkEle.get(4).toString().trim();
                        String flag = bulkEle.get(5).toString().trim();

                        List checkList;
                        //ATM Charge Alert
                        if (activityCode.equalsIgnoreCase("ATM")) { //It can be at branch as well as on HO(only posting from Ho for branches).
                            String alphacode = common.getAlphacodeByBrncode(orgBrnCode);
                            List branchList;
                            if (alphacode.equalsIgnoreCase("HO")) {
                                branchList = em.createNativeQuery("select brncode,alphacode from branchmaster where alphacode "
                                        + "not in('CELL','HO') order by brncode").getResultList();
                            } else {
                                branchList = em.createNativeQuery("select brncode,alphacode from branchmaster where "
                                        + "brncode=cast('" + orgBrnCode + "' as unsigned)").getResultList();
                            }
                            for (int k = 0; k < branchList.size(); k++) {
                                Vector brVec = (Vector) branchList.get(k);
                                String branchCode = brVec.get(0).toString().trim().length() < 2 ? "0" + brVec.get(0).toString().trim()
                                        : brVec.get(0).toString().trim();

                                checkList = em.createNativeQuery("select post_flag from cbs_loan_acctype_interest_parameter "
                                        + "where flag='AT' and brncode='" + branchCode + "' and to_dt "
                                        + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList(); //It can be single row or multiple. For each it should be posted.
                                if (!checkList.isEmpty()) {
                                    Vector checkEle = (Vector) checkList.get(0);
                                    if (checkEle.get(0).toString().trim().equalsIgnoreCase("N")) {
                                        throw new Exception("Branch's ATM Charge is not posted. So please confirm it. Branch Is: " + branchCode);
                                    }
                                }
                            }
                        }
                        //SMS Charge Alert
                        if (activityCode.equalsIgnoreCase("SMS")) { //It will be only at branch level.
                            checkList = em.createNativeQuery("select post_flag from cbs_loan_acctype_interest_parameter "
                                    + "where flag='MB' and brncode='" + orgBrnCode + "' and ac_type='" + acType + "' and to_dt "
                                    + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList(); //It can be single row or multiple. For each it should be posted.
                            if (!checkList.isEmpty()) {
                                Vector checkEle = (Vector) checkList.get(0);
                                if (checkEle.get(0).toString().trim().equalsIgnoreCase("N")) {
                                    String accountType = acType.equalsIgnoreCase("A") ? "ALL" : acType;
                                    throw new Exception("Branch's SMS Charge is not posted. So please confirm it. For Account Type: " + accountType);
                                }
                            }
                        }
                        //Cash Handling Charge Alert
                        if (activityCode.equalsIgnoreCase("CHC")) {
                            checkList = em.createNativeQuery("select post_flag from cbs_loan_acctype_interest_parameter "
                                    + "where flag='CH' and brncode='" + orgBrnCode + "' and ac_type='" + acType + "' and to_dt "
                                    + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList(); //It can be single row or multiple. For each it should be posted.
                            if (!checkList.isEmpty()) {
                                Vector checkEle = (Vector) checkList.get(0);
                                if (checkEle.get(0).toString().trim().equalsIgnoreCase("N")) {
                                    throw new Exception("Branch's Cash Handling Charge is not posted. So please confirm it. Account Type Is: " + acType);
                                }
                            }
                        }
                        //Manimum Balance Charge Alert
                        if (activityCode.equalsIgnoreCase("MBC")) {
                            checkList = em.createNativeQuery("select auth from post_history where postflag='MB' and "
                                    + "brncode='" + orgBrnCode + "' and actype='" + acType + "' and todate "
                                    + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList();
                            if (checkList.isEmpty()) {
                                throw new Exception("Branch's Minimum Balance Charges is not posted. So please confirm it. Account Type Is: " + acType);
                            }
                        }
                        //Incidental Charge Alert
                        if (activityCode.equalsIgnoreCase("INC")) {
                            checkList = em.createNativeQuery("select auth from post_history where postflag='" + flag + "' and "
                                    + "brncode='" + orgBrnCode + "' and actype='" + acType + "' and todate "
                                    + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList();
                            if (checkList.isEmpty()) {
                                String opFlag = flag.equalsIgnoreCase("3") ? "Operative" : "Inoperative";
                                throw new Exception("Branch's Incidental Charges is not posted. So please confirm it. " + opFlag + " Account Type Is: " + acType);
                            }
                        }
                        //Inoperative Charge Alert
                        if (activityCode.equalsIgnoreCase("INP")) {
                            checkList = em.createNativeQuery("select auth from post_history where postflag='IO' and "
                                    + "brncode='" + orgBrnCode + "' and actype='" + acType + "' and todate "
                                    + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList();
                            if (checkList.isEmpty()) {
                                throw new Exception("Branch's Inoperative Charges is not posted. So please confirm it. Account Type Is: " + acType);
                            }
                        }
                        //Loan Inspection Charge Alert
                        if (activityCode.equalsIgnoreCase("LIC")) {
                            checkList = em.createNativeQuery("select enterby from parameterinfo_posthistory where status=1 and "
                                    + "purpose like'%Inspection Charges%' and trandesc=117 and brncode='" + orgBrnCode + "' and "
                                    + "actype='" + acType + "' and todt between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList();
                            if (checkList.isEmpty()) {
                                throw new Exception("Branch's Loan Inspection Charges is not posted. So please confirm it. Account Type Is: " + acType);
                            }
                        }
                        //Loan Interest Alert
                        if (activityCode.equalsIgnoreCase("LNI")) {
                            checkList = em.createNativeQuery("select post_flag from cbs_loan_acctype_interest_parameter "
                                    + "where flag='" + flag + "' and brncode='" + orgBrnCode + "' and ac_type='" + acType + "' and to_dt "
                                    + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList(); //It can be single row or multiple. For each it should be posted.
                            if (!checkList.isEmpty()) {
                                Vector checkEle = (Vector) checkList.get(0);
                                if (checkEle.get(0).toString().trim().equalsIgnoreCase("N")) {
                                    String opFlag = flag.equalsIgnoreCase("I") ? "Interest" : "Penal";
                                    throw new Exception("Branch's Loan Interest is not posted. So please confirm it. " + opFlag + " Account Type Is: " + acType);
                                }
                            }
                        }
                        //Loan Interest Alert
                        if (activityCode.equalsIgnoreCase("FDI")) {
                            String alphacode = common.getAlphacodeByBrncode(orgBrnCode);
                            List branchList;
                            if (alphacode.equalsIgnoreCase("HO")) {
                                branchList = em.createNativeQuery("select brncode,alphacode from branchmaster where alphacode "
                                        + "not in('CELL','HO') order by brncode").getResultList();
                            } else {
                                branchList = em.createNativeQuery("select brncode,alphacode from branchmaster where "
                                        + "brncode=cast('" + orgBrnCode + "' as unsigned)").getResultList();
                            }
                            for (int k = 0; k < branchList.size(); k++) {
                                Vector brVec = (Vector) branchList.get(k);
                                String branchCode = brVec.get(0).toString().trim().length() < 2 ? "0" + brVec.get(0).toString().trim()
                                        : brVec.get(0).toString().trim();

                                String branchAndAccoutType = branchCode + acType;
                                List accountCheckList = em.createNativeQuery("select acno from td_accountmaster where accstatus=1 and "
                                        + "substring(acno,1,4)='" + branchAndAccoutType + "'").getResultList();
                                if (!accountCheckList.isEmpty()) {
                                    checkList = em.createNativeQuery("select intopt from td_intflag where brncode='" + branchCode + "' and "
                                            + "actype='" + acType + "' and intopt='" + flag + "' and td_tilldate "
                                            + "between '" + monthStartDt + "' and '" + monthLastDt + "'").getResultList(); //It can be single row or multiple. For each it should be posted.
                                    if (checkList.isEmpty()) {
                                        throw new Exception("Branch's FD Interest is not posted. So please confirm it. "
                                                + "Branch Is: " + branchCode + ": Account Type: " + acType);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<String> whichTypeOfMonthInFinYear(String todayDt, String startFinYearDt) throws Exception {
        List<String> monthTypeInFinYear = new ArrayList<>();
        try {
            int monthDiff = CbsUtil.monthDiff(yyyyMMdd.parse(startFinYearDt), yyyyMMdd.parse(todayDt)) + 1;
            if (monthDiff == 3 || monthDiff == 6 || monthDiff == 9 || monthDiff == 12) {
                monthTypeInFinYear.add("Q");
            }
            if (monthDiff == 6 || monthDiff == 12) {
                monthTypeInFinYear.add("H");
            }
            if (monthDiff == 12) {
                monthTypeInFinYear.add("Y");
            }
            monthTypeInFinYear.add("M");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return monthTypeInFinYear;
    }

    public String calculateLastWorkingDtOfMonth(String todayDt, String monthEndDt, String orgBrnCode) throws Exception {
        String monthLastWorkingDt = "";
        try {
            List list = em.createNativeQuery("select ifnull(dayendflag,''),ifnull(daybeginflag,''),ifnull(dayendflag1,'') from "
                    + "bankdays where date='" + monthEndDt + "' and brncode='" + orgBrnCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data in bankdays for the branch: " + orgBrnCode);
            }
            Vector ele = (Vector) list.get(0);
            String dayEndFlag = ele.get(0).toString().trim();
            String dayBeginFlag = ele.get(1).toString().trim();
            String dayEndFlagOne = ele.get(2).toString().trim();
            if (dayEndFlag.equalsIgnoreCase("Y") && dayBeginFlag.equalsIgnoreCase("H") && dayEndFlagOne.equalsIgnoreCase("N")) { //Holiday
                list = em.createNativeQuery("select max(date) from bankdays where date <'" + monthEndDt + "' and "
                        + "(daybeginflag = 'Y' or daybeginflag = 'N') and daybeginflag <> 'H' and "
                        + "brncode = '" + orgBrnCode + "'").getResultList();
                ele = (Vector) list.get(0);
                monthLastWorkingDt = ele.get(0).toString().trim();
            } else {
                monthLastWorkingDt = monthEndDt;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return monthLastWorkingDt;
    }

    public boolean isNonPosted(List postFlagList) throws Exception {
        boolean isPosted = true;
        try {
            for (int i = 0; i < postFlagList.size(); i++) {
                Vector ele = (Vector) postFlagList.get(i);
                String postFlag = ele.get(0).toString().trim();
                if (postFlag.equalsIgnoreCase("N")) {
                    isPosted = false;
                    return isPosted;
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return isPosted;
    }

    public String saveVenderMasterDetail(List<venderMasterPojo> list, String username, String date, String funtion) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (funtion.equalsIgnoreCase("A")) {
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        int j = em.createNativeQuery("insert into vender_master(gst_in_no,vender_name,vender_state,enter_by,enter_date) values('" + list.get(i).getGstno() + "',"
                                + "'" + list.get(i).getName() + "', '" + list.get(i).getState() + "', '" + username + "', '" + ymd.format(dmy.parse(date)) + "')").executeUpdate();
                        if (j < 0) {
                            throw new Exception("Problem In vender_master Insertion.");
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    public String updateVenderMasterDetail(String gstIn, String Name, String state, String user) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int i = em.createNativeQuery("update vender_master set vender_name='" + Name + "',vender_state='" + state + "' ,update_by='" + user + "', "
                    + "update_date=now() where gst_in_no = '" + gstIn + "'").executeUpdate();
            if (i < 0) {
                throw new Exception("Problem In vender_master updation.");
            }
            ut.commit();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    public List getVenderDetail(String gstNo) throws Exception {
        List resultList = new ArrayList<>();
        try {
            resultList = em.createNativeQuery("select gst_in_no,vender_name,vender_state from vender_master where gst_in_no ='" + gstNo + "'").getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return resultList;
    }

    public List isgstexits(String gstNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select gst_in_no from vender_invoice_master where gst_in_no = '" + gstNo + "' and auth_flag = 'Y'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List cbsAutoNeftDetailFieldListBasesOnModulePrirorty(String gNo, String module) throws ApplicationException {
        try {
            return em.createNativeQuery("select m.sss_gno,c.ref_desc from cbs_ref_rec_mapping m,cbs_ref_rec_type c where m.gno='" + gNo + "'  "
                    + " and m.s_gno='" + module + "' and m.ss_gno=c.ref_rec_no and m.sss_gno=c.ref_code;").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List cbsNeftBankNameList() throws ApplicationException {
        try {
            return em.createNativeQuery("select DISTINCT neft_bank_name from cbs_auto_neft_details").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getVenderInvoiceUnauthorizeDetail() throws ApplicationException {
        List resultList = new ArrayList<>();
        try {
            resultList = em.createNativeQuery("select vi.gst_in_no,vi.invoice_no,vi.invoice_amt,vi.cgst,vi.sgst,vi.igst,"
                    + "vd.vender_name,vd.vender_state,vi.enter_by from vender_invoice_master vi,vender_master vd where vi.auth_flag='N' and vi.gst_in_no = vd.gst_in_no").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public String saveVenderInvoiceDetail(String gstin, String invoiceNo, String invcAmt, String cgst, String sgst, String igst,
            String user, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select gst_in_no from vender_invoice_master where gst_in_no = '" + gstin + "' and invoice_no='" + invoiceNo + "'").getResultList();
            if (!list.isEmpty()) {
                ut.rollback();
                return "This invoice already exits.";
            }
            int n = em.createNativeQuery("insert into vender_invoice_master (gst_in_no,invoice_no,invoice_amt,cgst,sgst,igst,enter_by,"
                    + "enter_dt,auth_flag)values('" + gstin + "','" + invoiceNo + "','" + Double.valueOf(invcAmt) + "','" + Double.valueOf(cgst) + "',"
                    + "'" + Double.valueOf(sgst) + "','" + Double.valueOf(igst) + "','" + user + "','" + date + "','N')").executeUpdate();
            if (n < 0) {
                throw new ApplicationException("Problem In vender_invoice_master insertion");
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public String updateH2hParameter(String field, String value, String neftBankName, String process) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select HOST_NAME from cbs_auto_neft_details  where  "
                    + "PROCESS ='" + process + "' and NEFT_BANK_NAME ='" + neftBankName + "'").getResultList();
            if (list.isEmpty()) {
                return "Your detail does not exits.";
            }

            int n = em.createNativeQuery("update cbs_auto_neft_details set  " + field + " = '" + value + "'  where  "
                    + "PROCESS ='" + process + "' and NEFT_BANK_NAME ='" + neftBankName + "'").executeUpdate();
            if (n < 0) {
                throw new ApplicationException("Problem In cbs_auto_neft_details updation");
            }
            ut.commit();
        } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "true";
    }

    public String authorizeDetail(venderMasterPojo obj, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = em.createNativeQuery("update vender_invoice_master set auth_flag ='Y' , auth_by='" + user + "' , auth_dt=now() where "
                    + " gst_in_no ='" + obj.getGstno() + "' and invoice_no='" + obj.getInvoiceNo() + "'").executeUpdate();
            if (n < 0) {
                throw new ApplicationException("Problem In vender_invoice_master updation.");
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String deleteDetail(venderMasterPojo obj) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int m = em.createNativeQuery("delete from vender_invoice_master where gst_in_no ='" + obj.getGstno() + "' and invoice_no ='" + obj.getInvoiceNo() + "'").executeUpdate();
            if (m < 0) {
                throw new ApplicationException("Problem In vender_invoice_master deletion.");
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

}
