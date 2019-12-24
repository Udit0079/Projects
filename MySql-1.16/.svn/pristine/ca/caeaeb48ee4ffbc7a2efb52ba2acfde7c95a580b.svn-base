package com.cbs.facade.ho.bankGuarantee;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.BankGuaranteePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.dto.TdLienMarkingGrid;
import com.cbs.dto.report.ExceptionReportPojo;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;
import org.omg.CORBA.SystemException;

@Stateless(mappedName = "BankGuaranteeFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class BankGuaranteeFacade implements BankGuaranteeFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private CustomerManagementFacadeRemote customerRemote;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    FtsPostingMgmtFacadeRemote ftsMethods;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public double getTaxCharges(double totalCommissionAmt, String dt) throws ApplicationException {
        double sPerc = 0;
        int rUpTo = 0;
        double sPercIgst = 0, taxAmtIgst = 0;
        int rUpToIgst = 0;
        try {
            Map<String, Double> map = new HashMap<String, Double>();
            List list = em.createNativeQuery("Select ifnull(code,0),reportname from parameterinfo_report where "
                    + "reportname in ('STAXMODULE_ACTIVE')").getResultList();
            Vector v8 = (Vector) list.get(0);
            String staxModuleActive = "N";
            if (Integer.parseInt(v8.get(0).toString()) == 0) {
                staxModuleActive = "N";
            } else {
                staxModuleActive = "Y";
                map = interFts.getTaxComponentSlab(dt);
                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                    rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
                map = interFts.getIgstTaxComponentSlab(dt);
                Set<Map.Entry<String, Double>> set1 = map.entrySet();
                Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                while (it1.hasNext()) {
                    Map.Entry entry = it1.next();
                    sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                    rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    taxAmtIgst = CbsUtil.round(((totalCommissionAmt * sPercIgst) / 100), rUpToIgst);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return taxAmtIgst;
    }

    @Override
    public String getAccountNumber(String userAccountNumber) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select am.AcNo from accountmaster am , accounttypemaster amt  where  substring(am.AcNo,3,2 ) = amt.AcctCode and amt.acctNature in ('SB','CA')").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account Number Does Not Exist.");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public String getAccountValidation(String userAccountNumber) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select a.acno from td_accountmaster a , td_vouchmst b where a.acno = b.acno and a.acno ='" + userAccountNumber + "'and a.AccStatus <> '9' and b.status <>'C'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("This Account Number is not Active.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getAccountDetail(String acno) throws ApplicationException {
        try {
            List<ExceptionReportPojo> finalList = new ArrayList<ExceptionReportPojo>();
            String custName = "", expiryDate = "";
            double odLimit = 0.0d;
            List list;
            list = em.createNativeQuery("select  a.ACNo,a.custname ,max(date_format (ifnull(c.RENEWAL_DATE,'%d/%m/%Y'),'' ))ExpiryDate, a.ODLimit from accountmaster a, "
                    + "cbs_loan_borrower_details c where c.ACC_NO = '" + acno + "' and a.ACNo=c.ACC_NO ").getResultList();
            if (!list.isEmpty()) {
                ExceptionReportPojo row = new ExceptionReportPojo();
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    custName = ele.get(1).toString();
                    odLimit = Double.parseDouble(ele.get(3).toString());
                }
                row.setCustName(custName);
                row.setOdLimit(odLimit);
                finalList.add(row);
            }
            return (finalList);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String deleteEntry(int GuaranteeNo, Double guaranteeAmt, String action, List<TdLienMarkingGrid> currentItem, String orgnBrCode, String entryDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int delete = 0;

            delete = em.createNativeQuery("Delete from cbs_bank_guarantee_details "
                    + " where GuaranteeNo ='" + GuaranteeNo + "' and Action='" + action + "' and  auth ='N'").executeUpdate();
            if (delete <= 0) {
                ut.rollback();
                return "Problem in Deletion Lien Entry.";
            }
            for (TdLienMarkingGrid dataItem : currentItem) {
                int delete1 = 0;
                dataItem.getSno();
                delete1 = em.createNativeQuery("Delete from loansecurity "
                        + "where Acno='" + GuaranteeNo + "' and Sno = '" + dataItem.getSno() + "'").executeUpdate();
                if (delete1 < 0) {
                    return "Problem in Deletion Security Details Entry.";
                }
                String msg = DeleteSecurityTableEntry(enterBy, entryDt, dataItem.getAcNo(), guaranteeAmt, dataItem.getSno(), orgnBrCode);
                if (!msg.contains("TRUE")) {
                    ut.rollback();
                    return (msg);
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();

            } catch (Exception e) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public String updateRenewEntry(int GuaranteeNo, double guaranteeAmt, String updateDt, String entryDt,
            String enterBy, String action, List<TdLienMarkingGrid> currentItem, String authBy,
            String orgnBrCode, String guaranteeIssuedBy, double comissionAmt, String acNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int update = 0;
            update = em.createNativeQuery("UPDATE cbs_bank_guarantee_details set auth='Y',AuthBy ='" + authBy + "', UpdateDt ='" + updateDt + "' "
                    + "where GuaranteeNo ='" + GuaranteeNo + "' and Action='" + action + "'").executeUpdate();
            if (update <= 0) {
                ut.rollback();
                return "Problem in Updation Lien Entry.";
            }
            double lienTotalAmt = 0d;
            for (TdLienMarkingGrid dataItem : currentItem) {
                if (dataItem.getGuarSecStatus().equalsIgnoreCase("Active")) {
                    lienTotalAmt += Double.parseDouble(dataItem.getLienValue());
                }
            }

            if (lienTotalAmt < guaranteeAmt) {
                ut.rollback();
                return "Please AddOn Proper Security. After then you can verify the security.";
            }
            Date todayDate = new Date();
            String tDate = ymmd.format(todayDate);
            List resultList1 = em.createNativeQuery("select sno from loansecurity where acno ='" + GuaranteeNo + "' and GuaranteeSecStatus='E' and GuaranteeSecStatus <> Status and GuaranteeSecExpDt ='" + tDate + "'").getResultList();
            if (!resultList1.isEmpty()) {
                for (int c = 0; c < resultList1.size(); c++) {
                    Vector ele1 = (Vector) resultList1.get(c);
                    Integer SrNo = Integer.parseInt(ele1.get(0).toString());
                    String msg = UpdateSecurityTable(enterBy, entryDt, String.valueOf(GuaranteeNo), guaranteeAmt, SrNo, orgnBrCode);
                    if (!msg.contains("TRUE")) {
                        ut.rollback();
                        return msg;
                    }
                }
            }
            List parameterList = em.createNativeQuery("SELECT glheadmisc, trandesc FROM parameterinfo_miscincome WHERE "
                    + "acctCode='" + guaranteeIssuedBy + "' AND Purpose='BG Commission'").getResultList();
            if (parameterList.isEmpty()) {
                ut.rollback();
                return "There is no GLHead for BG Commission";
//                throw new ApplicationException("There is no GLHead for BG Commission");
            }
            Vector parameterVect = (Vector) parameterList.get(0);
            float tmpCharges = 0f;
            Integer tranDescComm = null;
            String glHeadCommission = "";

            if (parameterVect.get(0) != null) {
                glHeadCommission = orgnBrCode + parameterVect.get(0).toString() + "01";
            }
            if (parameterVect.get(1) != null) {
                tranDescComm = Integer.parseInt(parameterVect.get(1).toString());
            }
            String details = "Commision Txn for Bank Guarantee Renewal of " + GuaranteeNo;
            Float tmpTrsno = ftsMethods.getTrsNo();
            String msg = insertCommisionAndTax(glHeadCommission, comissionAmt, acNo, entryDt, enterBy, tmpTrsno,
                    tranDescComm, orgnBrCode, details, authBy);
            if (!msg.equalsIgnoreCase("True")) {
                ut.rollback();
                return msg;
//                throw new ApplicationException(msg);
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();

            } catch (Exception e) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";

    }

    private String insertCommisionAndTax(String glHeadCommission, double comissionAmt, String acNo, String entryDt, String enterBy, float tmpTrsno,
            int tranDescComm, String orgnBrCode, String details, String authBy) throws ApplicationException {
        try {
            Map<String, Double> map = new HashMap<>();
            String msg = "True";
            String branchState = "";
            String acNature = ftsMethods.getAccountNature(acNo);
            if (comissionAmt > 0) {
                // float recon = ftsMethods.getRecNo();
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    msg = ftsMethods.checkBalForOdLimit(acNo, comissionAmt, enterBy);
                    if (msg.equalsIgnoreCase("99")) {
                        return "Limit Exceede for : " + acNo;
//                        throw new ApplicationException("Limit Exceede for : " + acNo);
                    } else if (!msg.equalsIgnoreCase("1")) {
                        return "Balance Exceeds.";

//                        throw new ApplicationException("Balance Exceeds.");
                    }
                } else {
                    msg = ftsMethods.checkBalance(acNo, comissionAmt, enterBy);
                    if (!msg.equalsIgnoreCase("True")) {
                        return acNo + " Does not have sufficient balance to deduct charges";
//                        throw new ApplicationException(acNo + " Does not have sufficient balance to deduct charges");
                    }
                }

                msg = ftsRemote.insertRecons(acNature, acNo, 1, comissionAmt, entryDt, entryDt,
                        2, "Bank Guarantee Issue", enterBy, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                        authBy, tranDescComm, 3, "", null, 0f, "", "", 1, "", 0f, "", "", orgnBrCode,
                        orgnBrCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return "Problem in Updation Lien Entry.";
//                    throw new ApplicationException("Problem in Updation Lien Entry.");
                }

                msg = ftsMethods.updateBalance(acNature, acNo, 0, comissionAmt, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    return "Problem in Updation Lien Entry.";
//                    throw new ApplicationException("Problem in Updation Lien Entry.");
                }
                List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + glHeadCommission + "'").getResultList();
                String glHeadName = "NOT AVAIL";
                if (glTableList.size() > 0) {
                    Vector findTaxVect = (Vector) glTableList.get(0);
                    glHeadName = findTaxVect.get(0).toString();
                }

                msg = ftsRemote.insertRecons("PO", glHeadCommission, 0, comissionAmt, entryDt, entryDt,
                        2, details, enterBy, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                        authBy, tranDescComm, 3, "", null, 0f, "", "", 1, "", 0f, "", "", orgnBrCode,
                        orgnBrCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return "Problem in Updation Lien Entry.";
//                    throw new ApplicationException("Problem in Updation Lien Entry.");
                }
                msg = ftsRemote.updateBalance("PO", glHeadCommission, comissionAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    return "Problem in Updation Lien Entry.";
//                    throw new ApplicationException("Problem in Updation Lien Entry.");
                }
                /*Service Tax Entry*/
                List parameterinfoList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
                if (parameterinfoList.isEmpty()) {
                    return "Data does not exist in parameterinfo_report for tax module.";
//                    throw new ApplicationException("Data does not exist in parameterinfo_report for tax module.");
                }
                Vector parameterVect = (Vector) parameterinfoList.get(0);
                String staxModuleActive = parameterVect.get(0).toString();

                if (staxModuleActive.equalsIgnoreCase("1")) {
//                    double ttlTax = cmrFacade.findTax(commAmt, todayDt);
                    String mainDetails = null;
                    double sTax = 0d;
                    if (branchState.equalsIgnoreCase(branchState)) {
                        map = ibRemote.getTaxComponent(comissionAmt, entryDt);
                    } else {
                        map = ibRemote.getIgstTaxComponent(comissionAmt, entryDt);
                    }
                    Set<Map.Entry<String, Double>> set = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                    }

                    msg = ftsRemote.insertRecons(acNature, acNo, 1, sTax, entryDt, entryDt,
                            2, "GST For Bank Guarantee Issue", enterBy, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                            authBy, 71, 3, "", null, 0f, "", "", 1, "", 0f, "", "", orgnBrCode,
                            orgnBrCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return "Problem in Updation Lien Entry.";

//                        throw new ApplicationException("Problem in Updation Lien Entry.");
                    }
                    msg = ftsRemote.updateBalance(acNature, acNo, 0, sTax, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        return "Problem in Updation Lien Entry.";
//                        throw new ApplicationException("Problem in Updation Lien Entry.");
                    }

                    Set<Map.Entry<String, Double>> set1 = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                    while (it1.hasNext()) {
                        Map.Entry entry = it1.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        String description = keyArray[0];
                        String taxHead = orgnBrCode + keyArray[1];
                        mainDetails = description.toUpperCase() + " for Bank Guarantee Issue against. " + acNo;
                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                        msg = ftsRemote.insertRecons("PO", taxHead, 0, taxAmount, entryDt, entryDt,
                                2, mainDetails, enterBy, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                                authBy, 71, 3, "", null, 0f, "", "", 1, "", 0f, "", "", orgnBrCode,
                                orgnBrCode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            return "Problem in Updation Lien Entry.";
//                            throw new ApplicationException("Problem in Updation Lien Entry.");
                        }
                        msg = ftsRemote.updateBalance("PO", taxHead, taxAmount, 0, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            return "Problem in Updation Lien Entry.";
//                            throw new ApplicationException("Problem in Updation Lien Entry.");
                        }
                    }
                }
            }
            return "True";
        } catch (ApplicationException | NumberFormatException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String updateLienEntry(int guaranteeNo, double guaranteeAmt, double comissionAmt, String acNo, String entryDt, String enterBy, String action, String authBy, List<TdLienMarkingGrid> currentItem, String orgnBrCode, String updateDt, String guaranteeIssuedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int update = 0;
            Integer tyCr = 0;
            Integer tyDr = 1;
            Map<String, Double> map = new HashMap<String, Double>();
            String auth = "", details = "";
            if (action.equalsIgnoreCase("I")) {
                auth = "Y";
                tyCr = 0;
                tyDr = 1;
                details = "Bank Guarantee Issued for: " + acNo;
            } else if (action.equalsIgnoreCase("V")) {
                auth = "Y";
                tyCr = 1;
                tyDr = 0;
                details = "Bank Guarantee invoked against: " + acNo;
            } else if (action.equalsIgnoreCase("C")) {
                auth = "Y";
                tyCr = 1;
                tyDr = 0;
                details = "Bank Guarantee Close against: " + acNo;
            }
            update = em.createNativeQuery("UPDATE cbs_bank_guarantee_details set auth='" + auth + "',AuthBy ='" + authBy + "', UpdateDt ='" + updateDt + "' "
                    + "where GuaranteeNo ='" + guaranteeNo + "' and Action='" + action + "'").executeUpdate();
            if (update <= 0) {
                ut.rollback();
                return "Problem in Updation Bank Guarantee Entry.";
            }


            double lienTotalAmt = 0d;
            for (TdLienMarkingGrid dataItem : currentItem) {
                if (dataItem.getGuarSecStatus().equalsIgnoreCase("Active")) {
                    lienTotalAmt += Double.parseDouble(dataItem.getLienValue());
                }
            }

            if (!action.equalsIgnoreCase("C")) {
                if (lienTotalAmt < guaranteeAmt) {
                    ut.rollback();
                    return "Please AddOn Proper Security. After then you can verify the security.";
                }
            }
            if (action.equalsIgnoreCase("I")) {
                for (TdLienMarkingGrid dataItem : currentItem) {
                    if (dataItem.getLienAcNo() == null || dataItem.getLienAcNo().equalsIgnoreCase("")) {
                        dataItem.setTypeOfSec("O");
                    } else {
                        dataItem.setTypeOfSec(("F"));
                    }
//                    int i = em.createNativeQuery("update loansecurity set Auth='" + auth + "',AuthBy='" + authBy + "'  where  Acno ='" + guaranteeNo + "' ").executeUpdate();
                }
                Date todayDate = new Date();
                String tdate = ymmd.format(todayDate);

                List resultList1 = em.createNativeQuery("select sno from loansecurity where acno ='" + guaranteeNo + "' and GuaranteeSecStatus='E' and  GuaranteeSecStatus <> substring(Status,1,1) and GuaranteeSecExpDt ='" + tdate + "'").getResultList();
                if (!resultList1.isEmpty()) {
                    for (int c = 0; c < resultList1.size(); c++) {
                        Vector ele1 = (Vector) resultList1.get(c);
                        Integer SrNo = Integer.parseInt(ele1.get(0).toString());
                        String msg = UpdateSecurityTable(enterBy, entryDt, String.valueOf(guaranteeNo), guaranteeAmt, SrNo, orgnBrCode);
                        if (!msg.contains("TRUE")) {
                            ut.rollback();
                            return msg;
                        }
                    }
                }
            } else {
                for (TdLienMarkingGrid dataItem : currentItem) {
//                    if (dataItem.getLienAcNo() == null || dataItem.getLienAcNo().equalsIgnoreCase("")) {
//                        dataItem.setTypeOfSec("O");
//                    } else {
                    String msg = UpdateSecurityTable(enterBy, entryDt, dataItem.getAcNo(), guaranteeAmt, dataItem.getSno(), orgnBrCode);
                    if (!msg.contains("TRUE")) {
                        ut.rollback();
//                        throw new ApplicationException(msg);
                        return msg;
                    }

//                    }
//                    int i = em.createNativeQuery("update loansecurity set Auth='" + auth + "',AuthBy='" + authBy + "'  where  Acno ='" + guaranteeNo + "' ").executeUpdate();
                }
            }
            String custState = "", branchState = "";
            String brCode = orgnBrCode;


            List parameterList = em.createNativeQuery("SELECT glheadmisc, glheadmisc1, trandesc FROM parameterinfo_miscincome WHERE "
                    + "acctCode='" + guaranteeIssuedBy + "' AND Purpose='Bank Guarantee Issue'").getResultList();
            if (parameterList.isEmpty()) {
                ut.rollback();
                return "There is no GLHead for Bank Guarantee Issue";
//                throw new ApplicationException("There is no GLHead for Bank Guarantee Issue");
            }
            Vector parameterVect = (Vector) parameterList.get(0);

            String glHeadLiab = "", glHeadAsset = "";

            if (parameterVect.get(0) != null) {
                glHeadLiab = brCode + parameterVect.get(0).toString() + "01";
            }
            if (parameterVect.get(1) != null) {
                glHeadAsset = brCode + parameterVect.get(1).toString() + "01";
            }
            if (parameterVect.get(2) != null) {
                Integer trandescBG = Integer.parseInt(parameterVect.get(2).toString());
            }

            /*Commission and Service Tax entry from account and going in GL Haed*/
            parameterList = em.createNativeQuery("SELECT glheadmisc, trandesc FROM parameterinfo_miscincome WHERE "
                    + "acctCode='" + guaranteeIssuedBy + "' AND Purpose='BG Commission'").getResultList();
            if (parameterList.isEmpty()) {
                ut.rollback();
                return "There is no GLHead for BG Commission";
            }
            parameterVect = (Vector) parameterList.get(0);
            float tmpCharges = 0f;
            Integer tranDescComm = null;
            String glHeadCommission = "";

            if (parameterVect.get(0) != null) {
                glHeadCommission = brCode + parameterVect.get(0).toString() + "01";
            }
            if (parameterVect.get(1) != null) {
                tranDescComm = Integer.parseInt(parameterVect.get(1).toString());
            }

            Float tmpTrsno = ftsMethods.getTrsNo();
            String msg = ftsRemote.insertRecons("PO", glHeadLiab, tyCr, guaranteeAmt, entryDt, entryDt,
                    2, details, enterBy, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                    authBy, tranDescComm, 3, "", null, 0f, "", "", 1, "", 0f, "", "", orgnBrCode,
                    orgnBrCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                return "Problem in Updation Lien Entry.";
            }
            if (action.equalsIgnoreCase("I")) {
                msg = ftsRemote.updateBalance("PO", glHeadLiab, guaranteeAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    ut.rollback();
                    return "Problem in Updation Lien Entry.";
                }
            } else {
                msg = ftsRemote.updateBalance("PO", glHeadLiab, 0, guaranteeAmt, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    ut.rollback();
                    return "Problem in Updation Lien Entry.";
                }
            }

            msg = ftsRemote.insertRecons("PO", glHeadAsset, tyDr, guaranteeAmt, entryDt, entryDt,
                    2, details, enterBy, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                    authBy, tranDescComm, 3, "", null, 0f, "", "", 1, "", 0f, "", "", orgnBrCode,
                    orgnBrCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                return "Problem in Updation Lien Entry.";
            }
            if (action.equalsIgnoreCase("I")) {
                msg = ftsRemote.updateBalance("PO", glHeadAsset, 0, guaranteeAmt, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    ut.rollback();
                    return "Problem in Updation Lien Entry.";
                }
            } else {
                msg = ftsRemote.updateBalance("PO", glHeadAsset, guaranteeAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    ut.rollback();
                    return "Problem in Updation Lien Entry.";
                }
            }
//            int retiy = 1;

            if (action.equalsIgnoreCase("I")) {
                msg = insertCommisionAndTax(glHeadCommission, comissionAmt, acNo, entryDt, enterBy, tmpTrsno,
                        tranDescComm, orgnBrCode, details, authBy);
                if (!msg.equalsIgnoreCase("True")) {
                    ut.rollback();
                    return msg;
                }
            }
            ut.commit();
        } catch (NotSupportedException | javax.transaction.SystemException | ApplicationException | NumberFormatException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | javax.transaction.SystemException e) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public String saveIssueEntry(int guaranteeNo, String acNo, String action, String benfiName, String benfiAddress, String city, String state,
            String pinCode, String classification, String guaranteeIssuedBy, String purpose, String validityIn, int period,
            Double guaranteeAmt, String mode, Double comissionAmt, Double totalcomissionAmt, Double taxCharges,
            String guaranteeExpiryDate, String guaranteeInvokingDueDt, String enterBy, String authBy, String entryDt,
            String updateDt, int txnId, String orgnBrCode, List<TdLienMarkingGrid> currentItem) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String res = "";
        try {
            ut.begin();
            int save = 0;
            double amt = 0.0d;
            double sub = 0.0d;
            String auth = "N";
            int update = 0;
            if (action.equalsIgnoreCase("R") || action.equalsIgnoreCase("I")) {
                save = em.createNativeQuery("insert into cbs_bank_guarantee_details ( GuaranteeNo,AcNo,Action,"
                        + "BeneficiaryName,BenAddress,BenCity,"
                        + "BenState,BenPin,Classification,Purpose,GuaranteeValidityBasedOn,"
                        + "GuaranteeValidityPeriod,GuaranteeAmt,CommissionAmtBaseOn,CommissionAmtPerPeriod,"
                        + "CommissionAmtTotal,ChargesTotal,GuaranteeExpDt,GuaranteeInvokingDueDt,EnterBy,auth,AuthBy,EntryDt,brnCode,GuaranteeIssuedBy )"
                        + " values ('" + guaranteeNo + "','" + acNo + "','" + action + "','" + benfiName + "',"
                        + "'" + benfiAddress + "','" + city + "','" + state + "','" + pinCode + "',"
                        + "'" + classification + "','" + purpose + "','" + validityIn + "','" + period + "',"
                        + "'" + guaranteeAmt + "','" + mode + "',0," + comissionAmt + ","
                        + "'" + taxCharges + "','" + guaranteeExpiryDate + "',"
                        + "'" + guaranteeInvokingDueDt + "','" + enterBy + "','" + auth + "','" + authBy + "',"
                        + "'" + entryDt + "', '" + orgnBrCode + "','" + guaranteeIssuedBy + "')").executeUpdate();

                if (save <= 0) {
                    ut.rollback();
                    return "Problem in insertion.";
                }
            }
//            else if (action.equalsIgnoreCase("I")) {
//                update = em.createNativeQuery("UPDATE cbs_bank_guarantee_details set auth='N' ,EntryDt='" + entryDt + "', EnterBy='" + enterBy + "'"
//                        + "where GuaranteeNo ='" + guaranteeNo + "' and action ='I'").executeUpdate();
//                if (update <= 0) {
//                    return "Problem in Updation Lien Entry.";
//                }
//            }
            for (TdLienMarkingGrid dataItem : currentItem) {
                amt += Double.parseDouble(dataItem.getPrintAmt());
            }
            sub = amt - guaranteeAmt;
            if (sub < 0) {
                ut.rollback();
                return "Guarantee Amt is less than security Amt";
            }
            Integer AutoSno = 1;
            for (TdLienMarkingGrid dataItem : currentItem) {
                if (dataItem.getSno() == 0) {
                    if (dataItem.getLienAcNo() == null || dataItem.getLienAcNo().equalsIgnoreCase("")) {
                        dataItem.setTypeOfSec("O");
                    } else {
                        dataItem.setTypeOfSec(("F"));
                    }
                    List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + guaranteeNo + "'").getResultList();
                    if (!chk5.isEmpty()) {
                        Vector ele = (Vector) chk5.get(0);
                        AutoSno = Integer.parseInt(ele.get(0).toString());
                        AutoSno = AutoSno + 1;
                    }
//             String matDate =ymd.format(dmy.parse(dataItem.getMatDt()));
                    auth = "Y";
//                int i = em.createNativeQuery("insert into loansecurity(Acno,Sno,Remarks,IntTableCode,matdate)values ('" + dataItem.getAcNo() + "','" + AutoSno + "','" + dataItem.getLien() + "' ,'" + dataItem.getIntTable() + "','" + ymd.format(dmy.parse(dataItem.getMatDt())) + "' )").executeUpdate();
                    if (dataItem.getTypeOfSec().equalsIgnoreCase("O")) {
                        String msg = saveSecurityDetail(String.valueOf(guaranteeNo), dataItem.getSecurity(), dataItem.getSecurityType(), dataItem.getStatus(),
                                dataItem.getSecurityDesc1(), dataItem.getSecurityDesc2(), dataItem.getSecurityDesc3(), dataItem.getParticulars(), dataItem.getOtherAc(),
                                Float.parseFloat(dataItem.getMatValue()), dataItem.getMatDate(), dataItem.getEstimationDt(), Float.parseFloat(dataItem.getLienValue()), enterBy, dataItem.getDetails(),
                                entryDt, Float.parseFloat(dataItem.getMargin()), "", "", "");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            return msg;
                        }
                    } else if (dataItem.getTypeOfSec().equalsIgnoreCase("F")) {
                        String msg = saveLienMarkingDetail(Float.parseFloat(dataItem.getReciept()), Float.parseFloat(dataItem.getVoucherNo()), dataItem.getLienAcNo().substring(2, 4), dataItem.getLienAcNo(), String.valueOf(guaranteeNo), dataItem.getChkLien(), auth, enterBy, dataItem.getDetails(), dataItem.getSecurity(), orgnBrCode);
                        //Float ReceiptNo,                      Float VchNo,                                String Actype,                      String lienAcNo,    String guaranteeNo,         String chkLien,      AUTH, enteredby,  remark,              String security,         brnCode
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            return msg;
                        }
                    }
                }
            }
            ut.commit();

        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApplicationException(e.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        res = "true: " + guaranteeNo;
        return res;

    }

    @Override
    public String saveLienEntry(int guaranteeNo, String acNo, String action, String benfiName, String benfiAddress, String city, String state,
            String pinCode, String classification, String guaranteeIssuedBy, String purpose, String validityIn, int period,
            Double guaranteeAmt, String mode, Double comissionAmt, Double totalcomissionAmt, Double taxCharges,
            String guaranteeExpiryDate, String guaranteeInvokingDueDt, String enterBy, String authBy, String entryDt,
            String updateDt, int txnId, String orgnBrCode, List<TdLienMarkingGrid> currentItem) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String res = "";
        try {
            ut.begin();
            int save = 0;
            double amt = 0.0d;
            double lienAmt = 0.0d;
            double sub = 0.0d;
            String auth = "";
            if (action.equalsIgnoreCase("I")) {
                guaranteeNo = 1;
                auth = "N";
                List list = em.createNativeQuery("select max(GuaranteeNo) as GuraNo from cbs_bank_guarantee_details").getResultList();
                Vector v5 = (Vector) list.get(0);
                if (v5.get(0) != null) {
                    String Numb = v5.get(0).toString();
                    int Guarante = Integer.parseInt(Numb);
                    guaranteeNo = Guarante + 1;
                } else {
                    guaranteeNo = 1;
                }
                for (TdLienMarkingGrid dataItem : currentItem) {
                    amt += Double.parseDouble(dataItem.getPrintAmt());
                    if (dataItem.getGuarSecStatus().equalsIgnoreCase("Active")) {
                        lienAmt += Double.parseDouble(dataItem.getLienValue());
                    }
                }
                if (lienAmt < guaranteeAmt) {
                    ut.rollback();
                    return "Please AddOn Proper Security.";
                }
                sub = amt - guaranteeAmt;
                if (sub < 0) {
                    ut.rollback();
                    return "Guarantee Amt is less than security Amt";
                }

            } else if (action.equalsIgnoreCase("V")) {
                auth = "N";
            } else if (action.equalsIgnoreCase("C")) {
                auth = "N";
            }

            save = em.createNativeQuery("insert into cbs_bank_guarantee_details ( GuaranteeNo,AcNo,Action,"
                    + "BeneficiaryName,BenAddress,BenCity,"
                    + "BenState,BenPin,Classification,Purpose,GuaranteeValidityBasedOn,"
                    + "GuaranteeValidityPeriod,GuaranteeAmt,CommissionAmtBaseOn,CommissionAmtPerPeriod,"
                    + "CommissionAmtTotal,ChargesTotal,GuaranteeExpDt,GuaranteeInvokingDueDt,EnterBy,auth,AuthBy,EntryDt,brnCode,GuaranteeIssuedBy )"
                    + " values ('" + guaranteeNo + "','" + acNo + "','" + action + "','" + benfiName + "',"
                    + "'" + benfiAddress + "','" + city + "','" + state + "','" + pinCode + "',"
                    + "'" + classification + "','" + purpose + "','" + validityIn + "','" + period + "',"
                    + "'" + guaranteeAmt + "','" + mode + "',0," + comissionAmt + ","
                    + "'" + taxCharges + "','" + guaranteeExpiryDate + "',"
                    + "'" + guaranteeInvokingDueDt + "','" + enterBy + "','" + auth + "','" + authBy + "',"
                    + "'" + entryDt + "', '" + orgnBrCode + "','" + guaranteeIssuedBy + "')").executeUpdate();

            if (save <= 0) {
                ut.rollback();
                return "Problem in insertion.";
            }
            if (action.equalsIgnoreCase("I")) {
                Integer AutoSno = 1;
                for (TdLienMarkingGrid dataItem : currentItem) {
                    if (dataItem.getLienAcNo() == null || dataItem.getLienAcNo().equalsIgnoreCase("")) {
                        dataItem.setTypeOfSec("O");
                    } else {
                        dataItem.setTypeOfSec(("F"));
                    }
                    List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + guaranteeNo + "'").getResultList();
                    if (!chk5.isEmpty()) {
                        Vector ele = (Vector) chk5.get(0);
                        AutoSno = Integer.parseInt(ele.get(0).toString());
                        AutoSno = AutoSno + 1;
                    }
//             String matDate =ymd.format(dmy.parse(dataItem.getMatDt()));
                    auth = "Y";
//                int i = em.createNativeQuery("insert into loansecurity(Acno,Sno,Remarks,IntTableCode,matdate)values ('" + dataItem.getAcNo() + "','" + AutoSno + "','" + dataItem.getLien() + "' ,'" + dataItem.getIntTable() + "','" + ymd.format(dmy.parse(dataItem.getMatDt())) + "' )").executeUpdate();
                    if (dataItem.getTypeOfSec().equalsIgnoreCase("O")) {
                        String msg = saveSecurityDetail(String.valueOf(guaranteeNo), dataItem.getSecurity(), dataItem.getSecurityType(), dataItem.getStatus(),
                                dataItem.getSecurityDesc1(), dataItem.getSecurityDesc2(), dataItem.getSecurityDesc3(), dataItem.getParticulars(), dataItem.getOtherAc(),
                                Float.parseFloat(dataItem.getMatValue()), dataItem.getMatDate(), dataItem.getEstimationDt(), Float.parseFloat(dataItem.getLienValue()), enterBy, dataItem.getDetails(),
                                entryDt, Float.parseFloat(dataItem.getMargin()), "", "", "");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            return msg;
                        }
                    } else if (dataItem.getTypeOfSec().equalsIgnoreCase("F")) {
                        String msg = saveLienMarkingDetail(Float.parseFloat(dataItem.getReciept()), Float.parseFloat(dataItem.getVoucherNo()), dataItem.getLienAcNo().substring(2, 4), dataItem.getLienAcNo(), String.valueOf(guaranteeNo), dataItem.getChkLien(), auth, enterBy, dataItem.getDetails(), dataItem.getSecurity(), orgnBrCode);
                        //Float ReceiptNo,                      Float VchNo,                                String Actype,                      String lienAcNo,    String guaranteeNo,         String chkLien,      AUTH, enteredby,  remark,              String security,         brnCode
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            return msg;
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        res = "true: " + guaranteeNo;
        return res;
    }

    @Override
    public String verifyCheckAmt(String acno, Double guarAmt) throws ApplicationException {
        try {
            double totalLienAmt = 0d;
            String lienacno = "";
            String secStatus;
            List activeList = em.createNativeQuery("select lienvalue,matvalue, lienacno,Status, GuaranteeSecStatus from loansecurity where Acno='" + acno + "' and Status ='Active'").getResultList();
            if (!activeList.isEmpty()) {
                for (int i = 0; i < activeList.size(); i++) {
                    Vector ele = (Vector) activeList.get(0);
                    lienacno = ele.get(2).toString();
                    secStatus = ele.get(4).toString();
                    if (secStatus.equalsIgnoreCase("A")) {
                        totalLienAmt = Double.parseDouble(ele.get(0).toString());
                    }
                }
            }
            if (totalLienAmt < guarAmt) {
                return "Please AddOn Proper Security.";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    @Override
    public String updateSecuritySecStatus(String ExpiryDate, String acno, Integer sno, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer updateMasterList = em.createNativeQuery("Update loansecurity Set GuaranteeSecStatus = 'E',"
                    + "GuaranteeSecExpDt='" + ExpiryDate + "' Where Acno= '" + acno + "' and Sno =" + sno + "").executeUpdate();
            if (updateMasterList <= 0) {
                ut.rollback();
                return " New Status Has Not Been Changed As EXPIRED";
            }
            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "TRUE:New Status Has Been Changed As EXPIRED;";
    }

    @Override
    public String DeleteSecurityTableEntry(String expiredBy, String ExpiryDate, String acno, Double guarAmt, Integer sno, String brnCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
        try {
//            ut.begin();
            String vchNo = "", lienAcNo = "", acctNo = "";
            float voucherNo = 0f;
            float receiptNo = 0f;

            if (!lienAcNo.equalsIgnoreCase("")) {
                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno ='" + vchNo + "' and acno='" + lienAcNo + "'").getResultList();
                if (!chk2.isEmpty()) {
                    for (int i = 0; i < chk2.size(); i++) {
                        Vector ele = (Vector) chk2.get(i);
                        acctNo = ele.get(0).toString();
                        voucherNo = Float.parseFloat(ele.get(1).toString());
                        receiptNo = Float.parseFloat(ele.get(2).toString());
                    }

                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + lienAcNo + "' and voucherno = " + vchNo + " and receiptno = " + receiptNo + " "
                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + lienAcNo + "' and voucherno = " + vchNo + " and receiptno = " + receiptNo + ")").getResultList();

                    String lStat = "", mBrn = "";
                    if (!chLienBrn.isEmpty()) {
                        Vector ele = (Vector) chLienBrn.get(0);
                        lStat = ele.get(0).toString();
                        mBrn = ele.get(1).toString();
                    }

                    if (lStat.equalsIgnoreCase("Y")) {
                        if (mBrn.equalsIgnoreCase(brnCode)) {
                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                    + " VALUES('" + acctNo + "'," + voucherNo + "," + receiptNo + ",'" + expiredBy + "',NOW(),'N',SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','')");
                            int var1 = insertQuery.executeUpdate();
                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE acno='" + lienAcNo + "' and voucherno=" + vchNo + "");
                            int var2 = updateQuery.executeUpdate();
                        }
//                        else {
////                            ut.rollback();
//                            return "Lien Can Be Removed Only From Marked Branch";
//                        }
                    }
//                    else {
////                        ut.rollback();
//                        return "Lien Status Is Removed In Td_lien_update";
//                    }
                }
            }
//            ut.commit();
            return "TRUE: Status Has Been Changed As EXPIRED; Lien Mark Is Removed Against- " + lienAcNo + " / " + vchNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String UpdateSecurityTable(String expiredBy, String ExpiryDate, String acno, Double guarAmt, Integer sno, String brnCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
        try {
//            ut.begin();
            String vchNo = "", lienAcNo = "", acctNo = "";
            float voucherNo = 0f;
            float receiptNo = 0f;

            List unAuthList = em.createNativeQuery("select * from loansecurity Where Acno= '" + acno + "' and auth = 'N' and Sno =" + sno).getResultList();
            if (!unAuthList.isEmpty()) {
//                ut.rollback();
                return "Please authorize the security";
            } else {
                unAuthList = em.createNativeQuery("select ifnull(lienacno,''), VoucherNo  from loansecurity Where Acno= '" + acno + "' and auth = 'Y' and Sno =" + sno + "").getResultList();
                if (!unAuthList.isEmpty()) {
                    Vector ele = (Vector) unAuthList.get(0);
                    if (ele.get(0).toString() == null || ele.get(0).toString().equalsIgnoreCase("")) {
                        lienAcNo = "";
                    } else {
                        lienAcNo = ele.get(0).toString();
                    }
                    vchNo = ele.get(1).toString();
                }
            }

            Integer upadteMasterList = em.createNativeQuery("Update loansecurity Set Status = 'EXPIRED',"
                    + "ExpiredBy='" + expiredBy + "',ExpiryDate='" + ExpiryDate + "' Where Acno= '" + acno + "' and Sno =" + sno + "").executeUpdate();
            if (upadteMasterList <= 0) {
//                ut.rollback();
                return "Status Has Not Been Changed As EXPIRED";
            }
            if (!lienAcNo.equalsIgnoreCase("")) {
                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + vchNo + " and acno='" + lienAcNo + "'").getResultList();
                if (!chk2.isEmpty()) {
                    for (int i = 0; i < chk2.size(); i++) {
                        Vector ele = (Vector) chk2.get(i);
                        acctNo = ele.get(0).toString();
                        voucherNo = Float.parseFloat(ele.get(1).toString());
                        receiptNo = Float.parseFloat(ele.get(2).toString());
                    }

                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + lienAcNo + "' and voucherno = " + vchNo + " and receiptno = " + receiptNo + " "
                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + lienAcNo + "' and voucherno = " + vchNo + " and receiptno = " + receiptNo + ")").getResultList();

                    String lStat = "", mBrn = "";
                    if (!chLienBrn.isEmpty()) {
                        Vector ele = (Vector) chLienBrn.get(0);
                        lStat = ele.get(0).toString();
                        mBrn = ele.get(1).toString();
                    }

                    if (lStat.equalsIgnoreCase("Y")) {
                        if (mBrn.equalsIgnoreCase(brnCode)) {
                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                    + " VALUES('" + acctNo + "'," + voucherNo + "," + receiptNo + ",'" + expiredBy + "',NOW(),'N',SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','')");
                            int var1 = insertQuery.executeUpdate();
                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = 'N' WHERE acno='" + lienAcNo + "' and voucherno=" + vchNo + "");
                            int var2 = updateQuery.executeUpdate();
                        }
//                        else {
////                            ut.rollback();
//                            return "Lien Can Be Removed Only From Marked Branch";
//                        }
                    }
//                    else {
////                        ut.rollback();
//                        return "Lien Status Is Removed In Td_lien_update";
//                    }
                }
            }
//            ut.commit();
            return "TRUE: Status Has Been Changed As EXPIRED; Lien Mark Is Removed Against- " + lienAcNo + " / " + vchNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//
//    public String getCityCode(String city) throws ApplicationException {
//        try {
//            List<CbsRefRecTypeTO> cityList = customerRemote.getCurrencyCode("001");
//            for (CbsRefRecTypeTO obj : cityList) {
//                if (obj.getRefDesc().equalsIgnoreCase(city)) {
//                    return obj.getCbsRefRecTypePKTO().getRefCode();
//                }
//            }
//            return "";
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//    }
    @Override
    public List verifyGNo(String function, String action, String authValue, String brnCode) throws ApplicationException {
        List list1 = new ArrayList();
        try {
            String Tempbd = ymmd.format(new Date());
            List dtList = em.createNativeQuery("SELECT date_format(DATE,'%Y-%m-%d') FROM bankdays WHERE DAYENDFLAG = 'N' and brncode = '" + brnCode + "'").getResultList();
            if (!dtList.isEmpty()) {
                Vector dtv = (Vector) dtList.get(0);
                Tempbd = dtv.get(0).toString();
            } else {
                throw new ApplicationException("SERVER DATE NOT FOUND !!!");
            }
            String GuarQuery = "";
            String statusQuery = "";
            String dtQuery = " and a.EntryDt = '" + Tempbd + "' ";
            String dtQuery1 = " and EntryDt = '" + Tempbd + "' ";
            String query = "";
            if (action.equalsIgnoreCase("R")) {
                action = "R";
                if (function.equalsIgnoreCase("M")) {
                    dtQuery = "  and a.EntryDt <= '" + Tempbd + "'  ";
                    dtQuery1 = "  and EntryDt <= '" + Tempbd + "'  ";
                    GuarQuery = " and a.GuaranteeNo not in (Select distinct GuaranteeNo from cbs_bank_guarantee_details where auth ='N')";

                    query = " select aa.GuaranteeNo, aa.BeneficiaryName, aa.GuaranteeAmt, max(aa.txnid) as txn, aa.auth, aa.action, aa.EntryDt, aa.brnCode from "
                            + " (select a.GuaranteeNo, a.BeneficiaryName, a.GuaranteeAmt, b.txnid, a.auth, a.action, a.EntryDt, a.brnCode from  "
                            + " (Select * from cbs_bank_guarantee_details)a,  (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b  where a.GuaranteeNo = b.GuaranteeNo "
                            + "  " + GuarQuery + " and a.txnid = b.txnid  " + dtQuery
                            + " and a.brnCode ='" + brnCode + "'  and a.action in ('" + action + "','I')  group by  a.GuaranteeNo "
                            + " union "
                            + " select a.GuaranteeNo,a.BeneficiaryName, a.GuaranteeAmt, a.txnid, a.auth, a.action, a.EntryDt, a.brnCode from  (Select * from cbs_bank_guarantee_details)a, "
                            + " (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b,"
                            + " (select * from loansecurity where GuaranteeSecStatus = substring(Status,1,1)) c   where c.Acno= a.GuaranteeNo "
                            + " " + GuarQuery + " and a.brnCode ='" + brnCode + "' "
                            + " and a.action in ('" + action + "','I') " + dtQuery + " and  a.GuaranteeNo = b.GuaranteeNo  group by  a.GuaranteeNo) aa, "
                            + "(Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)bb  where aa.GuaranteeNo = bb.GuaranteeNo "
                            + " and aa.GuaranteeNo not in (Select distinct GuaranteeNo from cbs_bank_guarantee_details where auth ='N') and aa.txnid = bb.txnid "
                            + "and aa.EntryDt <= '" + Tempbd + "' and aa.brnCode ='" + brnCode + "'  and aa.action in ('" + action + "','I')   group by  aa.GuaranteeNo";

                } else if (function.equalsIgnoreCase("VR")) {
                    query = " select aa.GuaranteeNo, aa.BeneficiaryName, aa.GuaranteeAmt, max(aa.txnid) as txn, aa.auth, aa.action, aa.EntryDt, aa.brnCode from "
                            + " (select a.GuaranteeNo, a.BeneficiaryName, a.GuaranteeAmt, b.txnid, a.auth, a.action, a.EntryDt, a.brnCode from  "
                            + " (Select * from cbs_bank_guarantee_details)a,  (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b  where a.GuaranteeNo = b.GuaranteeNo "
                            + "  " + GuarQuery + " and a.txnid = b.txnid  " + dtQuery
                            + " and a.brnCode ='" + brnCode + "'  and a.action in ('" + action + "')  group by  a.GuaranteeNo "
                            + " union "
                            + " select a.GuaranteeNo,a.BeneficiaryName, a.GuaranteeAmt, a.txnid, a.auth, a.action, a.EntryDt, a.brnCode from  (Select * from cbs_bank_guarantee_details)a, "
                            + " (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b,"
                            + " (select * from loansecurity where GuaranteeSecStatus='E' and GuaranteeSecStatus <> substring(Status,1,1) and GuaranteeSecExpDt ='" + Tempbd + "')c   where c.Acno= a.GuaranteeNo "
                            + " " + GuarQuery + " and a.brnCode ='" + brnCode + "' "
                            + " and a.action in ('" + action + "') " + dtQuery + " and  a.GuaranteeNo = b.GuaranteeNo  group by  a.GuaranteeNo) aa, "
                            + " (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)bb  where aa.GuaranteeNo = bb.GuaranteeNo "
                            + " " + GuarQuery + " and aa.txnid = bb.txnid "
                            + " and aa.EntryDt = '" + Tempbd + "' and aa.brnCode ='" + brnCode + "'  and aa.action in ('" + action + "')   group by  aa.GuaranteeNo";
                }
                System.out.println("Query:" + query);
                list1 = em.createNativeQuery(query).getResultList();

            } else {
                if (function.equalsIgnoreCase("M")) {
                    dtQuery = "  and a.EntryDt <= '" + Tempbd + "'  ";
                    dtQuery1 = "  and EntryDt <= '" + Tempbd + "'  ";

                    if (action.equalsIgnoreCase("V") || action.equalsIgnoreCase("C")) {
                        action = "I','R";
                    }

                    GuarQuery = " and a.GuaranteeNo not in (Select distinct GuaranteeNo from cbs_bank_guarantee_details where auth ='N')";
                    statusQuery = "union "
                            + " select a.GuaranteeNo, a.BeneficiaryName, a.GuaranteeAmt, a.txnid, a.auth, a.action from "
                            + " (Select * from cbs_bank_guarantee_details)a, (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b ,"
                            + " (select * from loansecurity where GuaranteeSecStatus = substring(Status,1,1)) c  "
                            + " where c.Acno= a.GuaranteeNo and a.GuaranteeNo = b.GuaranteeNo " + GuarQuery + " and a.txnid = b.txnid " + dtQuery
                            + " and a.GuaranteeNo not in "
                            + " (Select distinct GuaranteeNo from cbs_bank_guarantee_details where auth ='N') and a.brnCode ='" + brnCode + "' "
                            + " and a.action in ('" + action + "') and a.EntryDt <= '" + Tempbd + "'   group by  a.GuaranteeNo";

                } else if (function.equalsIgnoreCase("VR")) {
                    statusQuery = "union "
                            + " select a.GuaranteeNo, a.BeneficiaryName, a.GuaranteeAmt, a.txnid, a.auth, a.action from "
                            + " (Select * from cbs_bank_guarantee_details)a,"
                            + " (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                            + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b, "
                            + " (select * from loansecurity where GuaranteeSecStatus='E' and GuaranteeSecStatus <> substring(Status,1,1) and GuaranteeSecExpDt ='" + Tempbd + "')c "
                            + " where c.Acno= a.GuaranteeNo and a.GuaranteeNo = b.GuaranteeNo " + GuarQuery + " and a.txnid = b.txnid " + dtQuery + " group by  a.GuaranteeNo ";
                } else if (function.equalsIgnoreCase("D")) {
                    statusQuery = "union "
                            + " select a.GuaranteeNo, a.BeneficiaryName, a.GuaranteeAmt, a.txnid, a.auth, a.action from "
                            + " (Select * from cbs_bank_guarantee_details)a "
                            + ",(select * from loansecurity where GuaranteeSecStatus='E' and GuaranteeSecStatus <> substring(Status,1,1) and GuaranteeSecExpDt ='" + Tempbd + "')c "
                            + " where c.Acno= a.GuaranteeNo and a.action in ('" + action + "') and a.auth = '" + authValue + "'  group by  a.GuaranteeNo ";
                }

                query = "select a.GuaranteeNo, a.BeneficiaryName, a.GuaranteeAmt, b.txnid, a.auth, a.action from "
                        + " (Select * from cbs_bank_guarantee_details)a, "
                        + " (Select GuaranteeNo, max(TxnId) as txnid from cbs_bank_guarantee_details "
                        + " where auth = '" + authValue + "' " + dtQuery1 + " /*and action = '" + action + "'*/ group by GuaranteeNo)b "
                        + " where a.GuaranteeNo = b.GuaranteeNo " + GuarQuery + " and a.txnid = b.txnid " + dtQuery
                        + " and a.brnCode ='" + brnCode + "'  and a.action in ('" + action + "')  group by  a.GuaranteeNo " + statusQuery + "";
                System.out.println("Query:" + query);
                list1 = em.createNativeQuery(query).getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list1;
    }

    @Override
    public List verifyCase(String function, int GuaranteeNo, String action) throws ApplicationException {
        List list2 = new ArrayList();
        try {
            String authQuery = "N", txnQuery = "";
            int txnId = 0;
            if (function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("D")) {
                if (action.equalsIgnoreCase("I") || action.equalsIgnoreCase("R")) {
//                    authQuery = "Y";
                    List list1 = new ArrayList();
                    list1 = em.createNativeQuery("select max(a.TxnId),a.auth , ifnull(b.AuthBy,'') as authBy , b.sno from cbs_bank_guarantee_details a ,"
                            + " loansecurity b  where GuaranteeNo = '" + GuaranteeNo + "' and a.GuaranteeNo = b.acno"
                            + " and b.Enteredby <>  ifnull(b.AuthBy,'') and  a.TxnId = (select max(TxnId) from cbs_bank_guarantee_details where GuaranteeNo = '" + GuaranteeNo + "' )").getResultList();
                    if (!list1.isEmpty()) {
                        Vector ele = (Vector) list1.get(0);
                        txnId = Integer.parseInt(ele.get(0).toString());
                        authQuery = ele.get(1).toString();
                    }
                }
                if (txnId != 0) {
                    txnQuery = " and a.TxnId = " + txnId;
                }

                list2 = em.createNativeQuery(" Select a.AcNo,a.BeneficiaryName,a.BenAddress,a.BenCity,a.BenState,a.BenPin,a.Classification,"
                        + " a.Purpose,a.GuaranteeValidityBasedOn,a.GuaranteeValidityPeriod,a.GuaranteeAmt, "
                        + " a.CommissionAmtBaseOn,a.CommissionAmtPerPeriod,a.CommissionAmtTotal,a.ChargesTotal,"
                        + " a.GuaranteeExpDt,a.GuaranteeInvokingDueDt,a.EnterBy,ifnull(a.AuthBy,''),a.EntryDt,ifnull(a.UpdateDt,''), max(a.TxnId),a.GuaranteeIssuedBy "
                        + " from cbs_bank_guarantee_details a ,loansecurity b where a.GuaranteeNo = '" + GuaranteeNo + "' and "
                        + " a.auth ='" + authQuery + "' and a.GuaranteeNo = b.acno "  + txnQuery
                        + " and b.Enteredby <> ifnull(b.AuthBy,'') ").getResultList();

            } else {
                list2 = em.createNativeQuery("Select AcNo,BeneficiaryName,BenAddress,BenCity,BenState,BenPin,Classification,"
                        + "Purpose,GuaranteeValidityBasedOn,GuaranteeValidityPeriod,GuaranteeAmt,"
                        + "CommissionAmtBaseOn,CommissionAmtPerPeriod,CommissionAmtTotal,ChargesTotal,GuaranteeExpDt,GuaranteeInvokingDueDt,"
                        + "EnterBy,AuthBy,EntryDt,ifnull(UpdateDt,''),max(TxnId),GuaranteeIssuedBy"
                        + " from cbs_bank_guarantee_details where GuaranteeNo = '" + GuaranteeNo + "'").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list2;
    }

    @Override
    public List verifyGridCase(String acno) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("Select Acno,Sno,lienvalue,Issuedate,matdate,Status,ifnull(lienacno,''),Remarks,Particulars,matvalue,GuaranteeSecStatus from loansecurity where Acno ='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }

    public String saveLienMarkingDetail(Float ReceiptNo, Float VchNo, String Actype, String lienAcNo, String guaranteeNo, String chkLien, String AUTH, String enteredby, String remark, String security, String brnCode) throws ApplicationException {
        String msg = "";                //                                                      
        try {
            int var1 = 0, var2 = 0, var3 = 0, var4 = 0;
            String LienD = null;
            String Tempbd = null;
            String acctNo = null;
            Float VoucherNo = 0.0f;
            Float Vreceiptno = 0.0f;
            Float prinAmt = 0.0f;
            String FDDt = null;
            String TDCodeDesc = "BANK GUARANTEE";
            String MatDt = null;
            Integer AutoSno = null;
            Float ROI = 0.0f;
            String TmpMsg = null;
            remark = "";

            if (ReceiptNo == null) {
                msg = "PLEASE SELECT RECEIPT NO FROM GRID !!!";
                return msg;
            }
            if (VchNo == null) {
                msg = "VOUCHER NO PASSED BLANK !!!";
                return msg;
            }

            if (Actype == null) {
                msg = "ACCOUNT TYPE PASSED BLANK !!!";
                return msg;
            }

            if (lienAcNo == null) {
                msg = "ACCOUNT NO. PASSED BLANK !!!";
                return msg;
            }
            String brcode = ftsMethods.getCurrentBrnCode(lienAcNo);
            List dtList = em.createNativeQuery("SELECT date_format(DATE,'%Y-%m-%d') FROM bankdays WHERE DAYENDFLAG = 'N' and brncode = '" + brcode + "'").getResultList();
            if (!dtList.isEmpty()) {
                Vector dtv = (Vector) dtList.get(0);
                Tempbd = dtv.get(0).toString();
            } else {
                msg = "SERVER DATE NOT FOUND !!!";
                return msg;
            }

//            
            List chk1 = em.createNativeQuery("Select coalesce(Lien,''),prinAmt,FdDt,MatDt,Roi,IntOpt"
                    + " From td_vouchmst Where Voucherno=" + VchNo + " and acno='" + lienAcNo + "'").getResultList();
            if (!chk1.isEmpty()) {
                for (int i = 0; i < chk1.size(); i++) {
                    Vector ele = (Vector) chk1.get(i);
                    LienD = ele.get(0).toString();
                    prinAmt = Float.parseFloat(ele.get(1).toString());
                    //  FdDtD = ele.get(2).toString();
                    //  MatDtD = ele.get(3).toString();
                    ROI = Float.parseFloat(ele.get(4).toString());
                    //  IntOptD = ele.get(5).toString();
                }
                if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("Yes")) {
                    msg = "Lien Is Already Marked Against- " + lienAcNo + " / " + VchNo;
                    return msg;
                }
                if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("Yes")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("Yes"))) {
                    List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + lienAcNo + "'").getResultList();
                    for (int i = 0; i < chk2.size(); i++) {
                        Vector ele = (Vector) chk2.get(i);
                        acctNo = ele.get(0).toString();
                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                    }
                    Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                            + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                    var1 = insertQuery.executeUpdate();
                    Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien =SUBSTRING('" + chkLien + "',1,1) WHERE acno='" + lienAcNo + "' and voucherno=" + VchNo + "");
                    var2 = updateQuery.executeUpdate();
                    msg = "Lien Is Marked Against- " + lienAcNo + " / " + VchNo;
                }
                if (chkLien.equalsIgnoreCase("Yes")) {
//                    if (Loan_Lien_Call.equalsIgnoreCase("True")) {
                    List chk2 = em.createNativeQuery("Select a.prinAmt,a.FDDt,a.MatDt,a.ROI From td_vouchmst a,td_accountmaster b Where a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + lienAcNo + "' and a.acno=b.acno").getResultList();

                    if (chk2.isEmpty()) {
                        msg = "Please check the TD data of AcType " + Actype + "; AcNo " + lienAcNo + "; VouchNo " + VoucherNo + "";
                        return msg;
                    } else {
                        List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a,td_accountmaster b"
                                + " WHERE a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + lienAcNo + "' and a.acno=b.acno").getResultList();
                        if (!chk3.isEmpty()) {
                            for (int i = 0; i < chk3.size(); i++) {
                                Vector ele = (Vector) chk3.get(i);
                                prinAmt = Float.parseFloat(ele.get(0).toString());
                                FDDt = ele.get(1).toString();
                                MatDt = ele.get(2).toString();
                                // ROI = Float.parseFloat(ele.get(3).toString());
                            }
                        }
//                        List chk4 = em.createNativeQuery("SELECT ifnull(Description,'') FROM codebook WHERE GroupCode=51 and code=61").getResultList();
//                        if (!chk4.isEmpty()) {
//                            Vector ele = (Vector) chk4.get(0);
//                            TDCodeDesc = ele.get(0).toString();
//                        }
                        List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + guaranteeNo + "'").getResultList();
                        if (!chk5.isEmpty()) {
                            Vector ele = (Vector) chk5.get(0);
                            AutoSno = Integer.parseInt(ele.get(0).toString());
                            AutoSno = AutoSno + 1;
                        }
                        TmpMsg = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY): ";
                        Query insertQuery = em.createNativeQuery("INSERT INTO loansecurity (acno,sno,security,particulars,"
                                + " matdate,lienvalue,matValue,issuedate,"
                                + " status,Remarks,enteredby,entrydate,SecurityChg,lienacno, auth, AuthBy, securitysub, "
                                + " SecurityType, SecurityOption,GuaranteeSecStatus,VoucherNo)"
                                + " VALUES('" + guaranteeNo + "'," + AutoSno + ",'" + security + "','" + VchNo + "',"
                                + "'" + ymd.format(ymd.parse(MatDt)) + "'," + prinAmt + "," + prinAmt + ","
                                + "'" + ymd.format(ymd.parse(FDDt)) + "','Active',"
                                + " CONCAT('DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):', "
                                + "'" + lienAcNo + "','; VchNo:" + VchNo + "', '; ROI:' "
                                + ", cast(" + ROI + " as char(10)) , '; Present Amt:' , "
                                + "cast(" + prinAmt + " as char(20))),'" + enteredby + "',"
                                + "'" + Tempbd + "','Lien',"
                                + "'" + lienAcNo + "','Y','system','BANK GUARANTEE ISSUE', "
                                + "'DATED', 'FIXED AND OTHER DEPOSITS(SPECIFY)','A','"+ VchNo +"')");
                        var3 = insertQuery.executeUpdate();
                        if (var3 > 0) {
                            return "TRUE";
                        }
                    }
                }
            }
            if (var1 > 0 && var2 > 0) {
                return msg;
            } else {
                return "RECORD NOT SAVED !!!";
            }

        } catch (Exception ex) {
            try {
                ex.printStackTrace();
            } catch (SystemException syex) {
                throw new ApplicationException(ex);
            }
            throw new ApplicationException(ex);
        }
        // return msg;
    }

    public String saveSecurityDetail(String acno, String security, String securityType, String status,
            String securityDesc1, String securityDesc2, String securityDesc3, String particulars, String otherAc,
            Float matValue, String matDate, String estimationDt, Float lienValue, String enteredBy, String Remarks,
            String entryDate, Float Margin, String groupCode, String secODRoi, String secODScheme) throws ApplicationException {
        try {
            Integer code = 0;
            Integer sno = 0;
            String statusV = "";
            String remarkV = "";
            List resultlist = em.createNativeQuery("Select ifnull(max(sno),0) From loansecurity Where Acno ='" + acno + "'").getResultList();
            Vector dateVect = (Vector) resultlist.get(0);
            code = Integer.parseInt(dateVect.get(0).toString());
            if (code == 0) {
                sno = 1;
            } else {
                sno = code + 1;
            }
            if (status.equalsIgnoreCase("1") || status.equalsIgnoreCase("ACTIVE")) {
                statusV = "Active";
            }
            if (securityDesc3.equalsIgnoreCase("")) {
                remarkV = securityType + ":" + securityDesc1 + ":" + securityDesc2 + ":" + particulars + ":" + otherAc;
            } else {
                remarkV = securityType + ":" + securityDesc1 + ":" + securityDesc2 + ":" + securityDesc3 + ":" + particulars + ":" + otherAc;
            }
            double STMFrequency = 0d;
            if (securityType.equalsIgnoreCase("NON-DATED")) {
                STMFrequency = 7d;
            }
            if (secODRoi.equalsIgnoreCase("") || secODRoi.equalsIgnoreCase(null)) {
                secODRoi = "0";
            }
            securityDesc1 = "BANK GUARANTEE";
            securityDesc3 = "BANK GUARANTEE ISSUE";
            Integer upadteMasterList = em.createNativeQuery("Insert Into loansecurity(Acno,Sno,security,SecurityOption,"
                    + "Particulars,MatValue,MatDate,issueDate,LienValue,Status,EnteredBy,Remarks,EntryDate,SecurityType,"
                    + "securityChg,Margin,Auth,securitysub,securitycode, AppRoi,IntTableCode, STMFrequency,GuaranteeSecStatus)"
                    + "values('" + acno + "'," + sno + ",'" + security + "','" + securityDesc2 + "','" + particulars + "',"
                    + "" + matValue + ",'" + matDate + "','" + estimationDt + "'," + lienValue + ",'" + statusV + "',"
                    + "'" + enteredBy + "','" + remarkV + "','" + entryDate + "','" + securityType + "',"
                    + "'" + securityDesc1 + "'," + Margin + ",'Y','" + securityDesc3 + "','" + groupCode + "',"
                    + "'" + secODRoi + "','" + secODScheme + "'," + STMFrequency + ",'A')").executeUpdate();

            if (upadteMasterList <= 0) {
                return "Data is does not Saved in gltable";
            } else {
                return "TRUE";
            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
            } catch (SystemException syex) {
                throw new ApplicationException(e);
            }
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getCityList(String state) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct a.REF_CODE,a.REF_DESC from cbs_ref_rec_type a,"
                    + "cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and "
                    + "a.ref_rec_no = '011' and b.GNO = '" + state + "' and b.S_GNO = 'DIST' "
                    + "order by b.order_by").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    
     @Override
     public List verifyIssueCase(String action, int GuaranteeNo) throws ApplicationException{
         try {
            List list = em.createNativeQuery(" select * from cbs_bank_guarantee_details where GuaranteeNo = '"+ GuaranteeNo +"' and action ='"+ action +"'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        } 
     }

    @Override
    public List<BankGuaranteePojo> getReportData(String action, String fromDate, String toDate, String orgbrn, String reportType, String issueType, String tillDate) throws ApplicationException {
        List<BankGuaranteePojo> resultList = new ArrayList<BankGuaranteePojo>();
        List list;
        List list1;
        String validity = "";
        try {
            if (action.equalsIgnoreCase("V") || action.equalsIgnoreCase("C")) {
                issueType = "";
            }
            String ToDate = ymmd.format(dmy.parse(toDate));
            String FromDate = ymmd.format(dmy.parse(fromDate));
            String branchQuery = "";
            if (orgbrn.equalsIgnoreCase("90")) {
                branchQuery = "";
            } else {
                branchQuery = " and a.brncode='" + orgbrn + "' ";
            }

            String typeQuery = "";
            if ((action.equalsIgnoreCase("I") || action.equalsIgnoreCase("R")) && issueType.equalsIgnoreCase("All")) {
                typeQuery = "";
            } else if ((action.equalsIgnoreCase("I") || action.equalsIgnoreCase("R")) && issueType.equalsIgnoreCase("AA")) {
                FromDate = "1900-01-01";
                String TillDate = ymmd.format(dmy.parse(tillDate));
                ToDate = TillDate;
                typeQuery = " where a.GuaranteeInvokingDueDt <='" + ToDate + "' and a.GuaranteeExpDt > '" + ToDate + "' ";
            } else if ((action.equalsIgnoreCase("I") || action.equalsIgnoreCase("R")) && issueType.equalsIgnoreCase("EI")) {
                typeQuery = " where a.GuaranteeExpDt BETWEEN '" + FromDate + "' and '" + ToDate + "' and a.GuaranteeInvokingDueDt <='" + ToDate + "' ";
            }

            if (reportType.equalsIgnoreCase("S")) {
                String query = "select a.GuaranteeNo,a.AcNo,a.BeneficiaryName,a.BenAddress, a.BenCity,a.BenState,a.GuaranteeInvokingDueDt, a.GuaranteeExpDt,\n"
                        + " a.GuaranteeValidityPeriod,a.GuaranteeValidityBasedOn,\n"
                        + " a.CommissionAmtTotal,a.GuaranteeAmt,a.securitysub, sum(a.lienvalue ) as security ,a.classification as classifications, a.purpose as purposes,a.guaranteeIssuedBy as guaranteeIssue, \n"
                        + " a.status as statuss ,a.particulars as particular ,a.remarks as remark  from \n"
                        + " (select distinct( ifnull(a.GuaranteeNo,'')) as GuaranteeNo,ifnull(a.AcNo,'') as AcNo, \n"
                        + " ifnull(a.beneficiaryname,'') as beneficiaryname,ifnull(a.benaddress,'') as benaddress, \n"
                        + " ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '011' and ref_code = a.bencity),'') as bencity, \n"
                        + " ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '002' and ref_code = a.benstate),'') as benstate,\n"
                        + " (a.EntryDt) as guaranteeinvokingduedt, (a.guaranteeexpdt) as guaranteeexpdt, \n"
                        + " ifnull(a.guaranteevalidityperiod,0) as guaranteevalidityperiod,ifnull(a.guaranteevaliditybasedon,'') as guaranteevaliditybasedon ,ifnull(a.commissionamttotal,0.0) as commissionamttotal, \n"
                        + " ifnull(a.guaranteeamt,0.0) as guaranteeamt,ifnull(b.securitysub,'') as securitysub, \n"
                        + " sum(b.lienvalue) as lienvalue, sno ,  ifnull((Select ref_desc from cbs_ref_rec_type where ref_rec_no ='418' and ref_code =a.Classification),'')  as classification , \n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='419' and ref_code =a.purpose ),'') as purpose,\n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='420' and ref_code =a.GuaranteeIssuedBy ),'') as guaranteeIssuedBy, \n"
                        + " (b.status) as status ,(b.particulars) as particulars , (b.remarks) as remarks   from loansecurity b,cbs_bank_guarantee_details a \n"
                        + " where b.acno = a.guaranteeno and (a.EntryDt BETWEEN '" + FromDate + "'  and '" + ToDate + "') \n"
                        + " and a.action='" + action + "' and  b.status='ACTIVE' " + branchQuery + " group by b.acno ,b.sno,a.guaranteeno \n"
                        + " union all \n"
                        + " select distinct (ifnull(a.guaranteeno,'')) as guaranteeno,ifnull(a.acno,'') as acno,ifnull(a.beneficiaryname,'') as beneficiaryname,ifnull(a.benaddress,'') as benaddress,\n"
                        + " ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '011' and ref_code = a.bencity),'') as bencity,\n"
                        + " ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '002' and ref_code = a.benstate),'') as benstate, \n"
                        + " (a.EntryDt) as guaranteeinvokingduedt, (a.guaranteeexpdt) as guaranteeexpdt \n"
                        + " ,ifnull(a.guaranteevalidityperiod,0) as guaranteevalidityperiod, ifnull(a.guaranteevaliditybasedon,'') as guaranteevaliditybasedon ,ifnull(a.commissionamttotal,0.0) as commissionamttotal, \n"
                        + " ifnull(a.guaranteeamt,0.0) as guaranteeamt,ifnull(b.securitysub,'') as securitysub, \n"
                        + " sum(b.lienvalue) as lienvalue, sno ,  ifnull((Select ref_desc from cbs_ref_rec_type where ref_rec_no ='418' and ref_code =a.Classification),'')  as classification ,\n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='419' and ref_code =a.purpose ),'') as purpose,\n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='420' and ref_code =a.GuaranteeIssuedBy ),'') as guaranteeIssuedBy ,\n"
                        + " (b.status) as status ,(b.particulars) as particulars , (b.remarks) as remarks  from loansecurity b,cbs_bank_guarantee_details a \n"
                        + " where b.acno = a.guaranteeno and (a.EntryDt BETWEEN '" + FromDate + "'  and '" + ToDate + "') \n"
                        + " and ((b.ExpiryDate)>= '" + FromDate + "')  and ((b.ExpiryDate)<'" + ToDate + "') \n"
                        + " and  b.status='EXPIRED' and a.action='" + action + "' " + branchQuery + " group by b.acno ,b.sno,a.guaranteeno) a\n"
                        + " " + typeQuery + "group by a.guaranteeno  order by cast(a.guaranteeno as decimal)";
                list = em.createNativeQuery(query).getResultList();                
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vec = (Vector) list.get(i);
                        BankGuaranteePojo bgp = new BankGuaranteePojo();
                        bgp.setGuaranteeNo(Integer.parseInt(vec.get(0).toString()));
                        bgp.setAcno(vec.get(1).toString());
                        bgp.setBenfiName(vec.get(2).toString());
                        bgp.setBenfiAddress(vec.get(3).toString() + ", " + vec.get(4).toString() + ", " + vec.get(5).toString());
                        bgp.setIssueDate(dmy.format(ymmd.parse(vec.get(6).toString())));
                        bgp.setGuaranteeExpiryDate(dmy.format(ymmd.parse(vec.get(7).toString())));
                        bgp.setPeriod(Integer.parseInt(vec.get(8).toString()));
                        if (vec.get(9).toString().equalsIgnoreCase("D")) {
                            validity = "Days";
                        } else if (vec.get(9).toString().equalsIgnoreCase("M")) {
                            validity = "Month";
                        } else {
                            validity = "Year";
                        }
                        bgp.setValidityIn(vec.get(8).toString() + " " + validity);
                        bgp.setTotalcomissionAmt(Double.valueOf(vec.get(10).toString()));
                        bgp.setGuaranteeAmt(Double.valueOf(vec.get(11).toString()));
                        bgp.setSecuritysub(vec.get(12).toString());
                        bgp.setLienValue(Float.valueOf(vec.get(13).toString()));
                        bgp.setClassification(vec.get(14).toString());
                        bgp.setPurpose(vec.get(15).toString());
                        bgp.setGuaranteeIssuedBy(vec.get(16).toString());
                        bgp.setStatus(vec.get(17).toString());
                        bgp.setParticular(vec.get(18).toString());
                        bgp.setRemark(vec.get(19).toString());
//                        bgp.setLienAcno(vec.get(20).toString());
                        bgp.setType(reportType);
                        resultList.add(bgp);
                    }
                }
            } else {
                int firstPrint = 0;
                Integer guarNo = 0;
                Integer oldGuarNo = 0;
                String query = "select aa.GuaranteeNo, aa.AcNo, aa.BeneficiaryName, aa.BenAddress, aa.BenCity, aa.BenState, aa.GuaranteeInvokingDueDt, \n"
                        + " aa.GuaranteeExpDt, aa.GuaranteeValidityPeriod, aa.GuaranteeValidityBasedOn, aa.CommissionAmtTotal ,aa.GuaranteeAmt, aa.securitysub, aa.security , \n"
                        + " aa.classifications, aa.purposes,aa.guaranteeIssue,  \n"
                        + " bb.Particulars, bb.lienvalue, aa.Status, group_concat('Security Status: ',bb.Status, '; Details: ',bb.Remarks,'\n') as Remarks,bb.Entrydate, bb.lienacno from \n"
                        + " (select a.GuaranteeNo, a.AcNo, a.BeneficiaryName, a.BenAddress, a.BenCity, a.BenState, a.GuaranteeInvokingDueDt, a.GuaranteeExpDt,  \n"
                        + " a.GuaranteeValidityPeriod, a.GuaranteeValidityBasedOn, a.CommissionAmtTotal, a.GuaranteeAmt, a.securitysub, sum(a.lienvalue ) as security , \n"
                        + " a.classification as classifications, a.purpose as purposes,a.guaranteeIssuedBy as guaranteeIssue , a.status  \n"
                        + " from (select distinct( ifnull(a.GuaranteeNo,'')) as GuaranteeNo,ifnull(a.AcNo,'') as AcNo,  ifnull(a.beneficiaryname,'') as beneficiaryname,  \n"
                        + " ifnull(a.benaddress,'') as benaddress, ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '011' and ref_code = a.bencity),'') as bencity, \n"
                        + " ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '002' and ref_code = a.benstate),'') as benstate,  \n"
                        + " (a.EntryDt) as guaranteeinvokingduedt, (a.guaranteeexpdt) as guaranteeexpdt, ifnull(a.guaranteevalidityperiod,0) as guaranteevalidityperiod, \n"
                        + " ifnull(a.guaranteevaliditybasedon,'') as guaranteevaliditybasedon , ifnull(a.commissionamttotal,0.0) as commissionamttotal,  \n"
                        + " ifnull(a.guaranteeamt,0.0) as guaranteeamt,ifnull(b.securitysub,'') as securitysub, sum(b.lienvalue) as lienvalue, sno, \n"
                        + "  ifnull((Select ref_desc from cbs_ref_rec_type where ref_rec_no ='418' and ref_code =a.Classification),'')  as classification , \n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='419' and ref_code =a.purpose ),'') as purpose, \n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='420' and ref_code =a.GuaranteeIssuedBy ),'') as guaranteeIssuedBy , b.status  \n"
                        + " from loansecurity b,cbs_bank_guarantee_details a where b.acno = a.guaranteeno and (a.EntryDt BETWEEN '" + FromDate + "'  and '" + ToDate + "')   \n"
                        + " and a.action='" + action + "' and  b.status='ACTIVE' " + branchQuery + " and ((b.Entrydate)<='" + ToDate + "') group by b.acno ,b.sno,a.guaranteeno  \n "
                        + " union all  \n"
                        + " select distinct (ifnull(a.guaranteeno,'')) as guaranteeno,ifnull(a.acno,'') as acno,ifnull(a.beneficiaryname,'') as beneficiaryname,  \n"
                        + " ifnull(a.benaddress,'') as benaddress, ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '011' and ref_code = a.bencity),'') as bencity,  \n"
                        + " ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = '002' and ref_code = a.benstate),'') as benstate,  \n"
                        + " (a.EntryDt) as guaranteeinvokingduedt, (a.guaranteeexpdt) as guaranteeexpdt  ,ifnull(a.guaranteevalidityperiod,0) as guaranteevalidityperiod,  \n"
                        + " ifnull(a.guaranteevaliditybasedon,'') as guaranteevaliditybasedon ,ifnull(a.commissionamttotal,0.0) as commissionamttotal,  \n"
                        + " ifnull(a.guaranteeamt,0.0) as guaranteeamt,ifnull(b.securitysub,'') as securitysub,  sum(b.lienvalue) as lienvalue, sno,  \n"
                        + " ifnull((Select ref_desc from cbs_ref_rec_type where ref_rec_no ='418' and ref_code =a.Classification),'')  as classification , \n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='419' and ref_code =a.purpose ),'') as purpose, \n"
                        + " ifnull((Select ref_desc from  cbs_ref_rec_type where ref_rec_no ='420' and ref_code =a.GuaranteeIssuedBy ),'') as guaranteeIssuedBy , b.status  \n"
                        + " from loansecurity b,cbs_bank_guarantee_details a where b.acno = a.guaranteeno and (a.EntryDt BETWEEN '" + FromDate + "'  and '" + ToDate + "')    \n"
                        + " and ((b.ExpiryDate)>= '" + FromDate + "')  and ((b.ExpiryDate)<'" + ToDate + "') " + branchQuery + " and  b.status='EXPIRED' and a.action='" + action + "' \n"
                        + " group by b.acno ,b.sno,a.guaranteeno) a  " + typeQuery + "group by a.guaranteeno) aa  \n"
                        + " left join  \n"
                        + " (select Acno as guaranteeno,Sno,Particulars,lienvalue,Status,Issuedate,Remarks,Entrydate,ifnull(lienacno,'') as lienacno  from loansecurity b  \n"
                        + " where b.status='ACTIVE' and securitysub = 'BANK GUARANTEE ISSUE' and ((b.Entrydate)<='" + ToDate + "')  \n"
                        + " union all  \n"
                        + " select Acno as guaranteeno,Sno,Particulars,lienvalue,Status,Issuedate,Remarks,Entrydate,ifnull(lienacno,'') as lienacno from loansecurity b  \n"
                        + " where ((b.ExpiryDate)>= '" + FromDate + "')  and ((b.ExpiryDate)<'" + ToDate + "')  and  b.status='EXPIRED'  \n"
                        + " and securitysub = 'BANK GUARANTEE ISSUE' ) bb on cast(aa.GuaranteeNo as decimal) = cast(bb.guaranteeno as decimal)   \n"
                        + " group by aa.GuaranteeNo order by cast(aa.GuaranteeNo as decimal) ";
                list1 = em.createNativeQuery(query).getResultList();                
                if (!list1.isEmpty()) {
                    for (int i = 0; i < list1.size(); i++) {
                        Vector vec = (Vector) list1.get(i);
                        BankGuaranteePojo bgp = new BankGuaranteePojo();
                        if (firstPrint == 0) {
                            guarNo = Integer.parseInt(vec.get(0).toString());
                            bgp.setGuaranteeNo(Integer.parseInt(vec.get(0).toString()));
                            bgp.setAcno(vec.get(1).toString());
                            bgp.setBenfiName(vec.get(2).toString());
                            bgp.setBenfiAddress(vec.get(3).toString() + ", " + vec.get(4).toString() + ", " + vec.get(5).toString());
                            bgp.setIssueDate(dmy.format(ymmd.parse(vec.get(6).toString())));
                            bgp.setGuaranteeExpiryDate(dmy.format(ymmd.parse(vec.get(7).toString())));
                            bgp.setPeriod(Integer.parseInt(vec.get(8).toString()));
                            if (vec.get(9).toString().equalsIgnoreCase("D")) {
                                validity = "Days";
                            } else if (vec.get(9).toString().equalsIgnoreCase("M")) {
                                validity = "Month";
                            } else {
                                validity = "Year";
                            }
                            bgp.setValidityIn(vec.get(8).toString() + " " + validity);
                            bgp.setTotalcomissionAmt(Double.valueOf(vec.get(10).toString()));
                            bgp.setGuaranteeAmt(Double.valueOf(vec.get(11).toString()));
                            bgp.setSecuritysub(vec.get(12).toString());
                            bgp.setLienValue(Float.valueOf(vec.get(18).toString()));
                            bgp.setClassification(vec.get(14).toString());
                            bgp.setPurpose(vec.get(15).toString());
                            bgp.setGuaranteeIssuedBy(vec.get(16).toString());
                            bgp.setParticular(vec.get(17).toString());
                            bgp.setStatus(vec.get(19).toString());
                            bgp.setRemark(vec.get(20).toString());
                            bgp.setLienAcno(vec.get(22).toString());
                            bgp.setType(reportType);
                            oldGuarNo = guarNo;
                            firstPrint = 1;
                            resultList.add(bgp);

                        } else {
                            guarNo = Integer.parseInt(vec.get(0).toString());
                            if (guarNo.equals(oldGuarNo)) {
                                bgp.setGuaranteeNo(Integer.parseInt(vec.get(0).toString()));
                                bgp.setAcno(vec.get(1).toString());
                                bgp.setBenfiName(vec.get(2).toString());
                                bgp.setBenfiAddress(vec.get(3).toString() + ", " + vec.get(4).toString() + ", " + vec.get(5).toString());
                                bgp.setIssueDate(dmy.format(ymmd.parse(vec.get(6).toString())));
                                bgp.setGuaranteeExpiryDate(dmy.format(ymmd.parse(vec.get(7).toString())));
                                bgp.setPeriod(Integer.parseInt(vec.get(8).toString()));
                                if (vec.get(9).toString().equalsIgnoreCase("D")) {
                                    validity = "Days";
                                } else if (vec.get(9).toString().equalsIgnoreCase("M")) {
                                    validity = "Month";
                                } else {
                                    validity = "Year";
                                }
                                bgp.setValidityIn(vec.get(8).toString() + " " + validity);
                                bgp.setTotalcomissionAmt(Double.valueOf(vec.get(10).toString()));
                                bgp.setGuaranteeAmt(0.00);
                                bgp.setSecuritysub(vec.get(12).toString());
                                bgp.setLienValue(Float.valueOf(vec.get(18).toString()));
                                bgp.setClassification(vec.get(14).toString());
                                bgp.setPurpose(vec.get(15).toString());
                                bgp.setGuaranteeIssuedBy(vec.get(16).toString());
                                bgp.setParticular(vec.get(17).toString());
                                bgp.setStatus(vec.get(19).toString());
                                bgp.setRemark(vec.get(20).toString());
                                bgp.setLienAcno(vec.get(22).toString());
                                bgp.setType(reportType);
                                oldGuarNo = guarNo;
                                resultList.add(bgp);
                            } else {
                                bgp.setGuaranteeNo(Integer.parseInt(vec.get(0).toString()));
                                guarNo = Integer.parseInt(vec.get(0).toString());
                                bgp.setAcno(vec.get(1).toString());
                                bgp.setBenfiName(vec.get(2).toString());
                                bgp.setBenfiAddress(vec.get(3).toString() + ", " + vec.get(4).toString() + ", " + vec.get(5).toString());
                                bgp.setIssueDate(dmy.format(ymmd.parse(vec.get(6).toString())));
                                bgp.setGuaranteeExpiryDate(dmy.format(ymmd.parse(vec.get(7).toString())));
                                bgp.setPeriod(Integer.parseInt(vec.get(8).toString()));
                                if (vec.get(9).toString().equalsIgnoreCase("D")) {
                                    validity = "Days";
                                } else if (vec.get(9).toString().equalsIgnoreCase("M")) {
                                    validity = "Month";
                                } else {
                                    validity = "Year";
                                }
                                bgp.setValidityIn(vec.get(8).toString() + " " + validity);
                                bgp.setTotalcomissionAmt(Double.valueOf(vec.get(10).toString()));
                                bgp.setGuaranteeAmt(Double.valueOf(vec.get(11).toString()));
                                bgp.setSecuritysub(vec.get(12).toString());
                                bgp.setLienValue(Float.valueOf(vec.get(18).toString()));
                                bgp.setClassification(vec.get(14).toString());
                                bgp.setPurpose(vec.get(15).toString());
                                bgp.setGuaranteeIssuedBy(vec.get(16).toString());
                                bgp.setParticular(vec.get(17).toString());
                                bgp.setStatus(vec.get(19).toString());
                                bgp.setRemark(vec.get(20).toString());
                                bgp.setLienAcno(vec.get(22).toString());
                                bgp.setType(reportType);
                                oldGuarNo = guarNo;
                                resultList.add(bgp);
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }
}
