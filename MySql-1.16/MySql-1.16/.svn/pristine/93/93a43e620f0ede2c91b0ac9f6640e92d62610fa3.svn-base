/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  30 OCT 2010
 */
package com.cbs.facade.inventory;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.util.ArrayList;
import java.util.List;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "ChequeMaintinanceRegisterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ChequeMaintinanceRegisterFacade implements ChequeMaintinanceRegisterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;

    public List chkGLHead(String acctType) throws ApplicationException {
        List glhead = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select charges,glheadmisc,charges1 From parameterinfo_miscincome Where acctCode='" + acctType
                    + "' and Purpose like '%Stop Payment Charges%'");
            glhead = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return glhead;
    }

    public List custChqDetailForFreshChq(String acno) throws ApplicationException {
        List detail = new ArrayList();
        String acNat = "";
        try {
            acNat = ftsPosting.getAccountNature(acno);
            String tableName = getChequeBookTable(acNat);
            detail = em.createNativeQuery("select cast(chqno as unsigned),coalesce(DATE_FORMAT(issuedt,'%d/%m/%Y'),'') from " 
                    + tableName+" where acno='" + acno + "' and statusflag='F'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return detail;
    }

    public List custChqDetailForStopChq(String acno) throws ApplicationException {
        List detail = new ArrayList();
        try {
            String acNat = ftsPosting.getAccountNature(acno);
            String tableName = getChequeBookTable(acNat);
            detail = em.createNativeQuery("select cast(chqno as unsigned),coalesce(DATE_FORMAT(issuedt,'%d/%m/%Y'),'') from " 
                    + tableName+" where acno='" + acno + "' and statusflag='S'").getResultList();
            
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return detail;
    }

    public String custStatus(String acno) throws ApplicationException {
        String result = "";
        try {
            List chk = em.createNativeQuery("select * from accountmaster where acno='" + acno + "'").getResultList();
            if (chk.isEmpty()) {
                result = "This Account No Does Not Exist";
            } else {
                List chk1 = em.createNativeQuery("select acno from accountmaster where acno='" + acno + "' and accstatus=9").getResultList();
                if (!chk1.isEmpty()) {
                    result = "This Account Has Been Closed";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List custDetail(String acno) throws ApplicationException {
        List detail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select am.custname,am.optstatus,am.nomination,"
                    + " coalesce(am.jtname1,''),coalesce(am.jtname2,''),cb.description From accountmaster am, codebook cb "
                    + " where am.acno='" + acno + "' and am.operMode=cb.code and cb.Groupcode=4 and am.accstatus<>9");
            detail = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return detail;
    }

    public List getAcBalance(String acno) throws ApplicationException {
        List balanceList = new ArrayList();
        String acNat = "";
        try {
            acNat = ftsPosting.getAccountNature(acno);
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM ca_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.TD_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM loan_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM loan_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM rdrecon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM gl_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM of_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM nparecon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return balanceList;
    }

    public String saveChqMaintinanceDetail(String acno, long chqFrom, String option, long chqTo, String status, String enterBy, String favoring,
            String chqDt, Float tAmt, String chrgOpt, Float partyBalance, Float tmpCharges, String glHead,
            String orgBrnCode, String destBrnCode, String saveUpdateFlag) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            String message = "", tempbd = "", chequeBookTable = "", statusflag = "", tempStatus = "";
            Integer tmpTy;

            if (chqFrom <= 0) {
                throw new ApplicationException("Please Enter From Cheque No.");
            }
            
            if (option.equalsIgnoreCase("SERIES")) {
                if (chqTo <= 0) {
                    throw new ApplicationException("Please Enter To Cheque No.");
                }
                if ((chqTo < chqFrom)) {
                    throw new ApplicationException("From Cheque No. Can not Be Greater Than Cheque No To.");
                }
            } else {
                chqTo = chqFrom;
            }
            if (status.equalsIgnoreCase("SP")) {
                tempStatus = "S";
                tmpTy = 9;
            } else {
                tempStatus = "F";
                tmpTy = 1;
            }

            long tmpchqFrom = chqFrom;
            
            List dayList = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode='" + orgBrnCode + "'").getResultList();
            if (!dayList.isEmpty()) {
                Vector element = (Vector) dayList.get(0);
                tempbd = element.get(0).toString();
            }

            chequeBookTable = getChequeBookTable(ftsPosting.getAccountNature(acno));
            while (tmpchqFrom <= chqTo) {
                List acnoChqList = em.createNativeQuery("select statusflag from " + chequeBookTable + " where acno = '" + acno + "' and chqno = " + tmpchqFrom).getResultList();
                if (acnoChqList.isEmpty()) {
                    throw new ApplicationException("Instrument number does not exist. Cheque Number is " + tmpchqFrom);
                }
                Vector acnoChqVector = (Vector) acnoChqList.get(0);
                statusflag = acnoChqVector.get(0).toString();
                if(statusflag.equalsIgnoreCase("U")){
                    throw new ApplicationException("Cheque Number " + tmpchqFrom + " is already used.");
                }
                if (statusflag.equalsIgnoreCase(tempStatus)) {
                    if (statusflag.equalsIgnoreCase("F")) {
                        throw new ApplicationException("Cheque Number " + tmpchqFrom + " is already operative.");
                    } else if (statusflag.equalsIgnoreCase("S")) {
                        throw new ApplicationException("Cheque Number " + tmpchqFrom + " is already stopped.");
                    }
                }
                tmpchqFrom = tmpchqFrom + 1;
            }
            
            int insertResult = em.createNativeQuery("insert into chbookdetail(acno,ChBookNo,ChequeNo,status,amount,favoring,chequedt,"
                    + "enterydate,enteredby)"
                    + " values ('" + acno + "'," + chqFrom + "," + chqTo + "," + tmpTy + "," + tAmt + ",'" + favoring + "','" + chqDt + "','"
                    + tempbd + "','" + enterBy + "')").executeUpdate();

            if (insertResult <= 0) {
                throw new ApplicationException("Cheque book detail insertion problem. Cheque Number is " + chqFrom);
            } else {
                if (saveUpdateFlag.equalsIgnoreCase("S")) {
                    message = "Data has been saved successfully.";
                } else {
                    message = "Data has been updated successfully.";
                }
            }

            ut.commit();
            return message;
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (ApplicationException e) {
                throw new ApplicationException(e);
            } catch (SystemException e) {
                throw new ApplicationException(e);
            }
        }
    }

//    public double findTax(double commAmt,String dt) throws ApplicationException {
//        Integer roundNo = 0;
//        String appType = "";
//        double ttlTaxAmt = 0;
//        try {
//            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
//            String currentDate = ymd.format(new Date());
//            List resultTaxMaster = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= '"+dt+"' LIMIT 1").getResultList();
//            if (resultTaxMaster.size() <= 0) {
//                throw new ApplicationException("TAX NOT DEFINED");
//            } else {
//                List resultTax = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= '"+dt+"' LIMIT 1").getResultList();
//                if (resultTax.size() <= 0) {
//                } else {
//                    Vector resultVect = (Vector) resultTax.get(0);
//                    roundNo = Integer.parseInt(resultVect.get(0).toString());
//                }
//            }
//            List taxApplicableROTList = fnTaxApplicableROT(currentDate);
//            if (taxApplicableROTList.size() <= 0) {
//                throw new ApplicationException("Rate Of Tax not Found");
//            } else {
//                Vector resultVect = (Vector) taxApplicableROTList.get(0);
//                appType = resultVect.get(0).toString();
//            }
//            String taxamount = taxAmount(commAmt, appType);
//            ttlTaxAmt = CbsUtil.round(Double.parseDouble(taxamount), roundNo);
//            return ttlTaxAmt;
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }

//    public String taxAmount(double amt, String type) throws ApplicationException {
//        double minAmt;
//        double maxAmt;
//        double balance = 0;
//        try {
//            List resultlist = em.createNativeQuery("select minAmt,maxamt from taxmaster where type='" + type + "' and"
//                    + " ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' "
//                    + "and ApplicableFlag='Y')").getResultList();
//            if (resultlist.size() <= 0) {
//                return "No Data in TaxMaster";
//            } else {
//                Vector resultVect = (Vector) resultlist.get(0);
//                minAmt = Float.parseFloat(resultVect.get(0).toString());
//                maxAmt = Float.parseFloat(resultVect.get(1).toString());
//            }
//            List resultlistTaxMaster = em.createNativeQuery("Select ('" + amt + "'* ROT)/100  from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y')").getResultList();
//            if (resultlistTaxMaster.size() <= 0) {
//                return "No Data in TaxMaster";
//            } else {
//                Vector resultVect = (Vector) resultlistTaxMaster.get(0);
//                balance = Float.parseFloat(resultVect.get(0).toString());
//            }
//
//            if (balance < minAmt) {
//                balance = minAmt;
//            } else if (balance > maxAmt) {
//                balance = maxAmt;
//            }
//            return String.valueOf(balance);
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }

    /**
     * ***************************************************************************************************************************************
     */
//    public List fnTaxApplicableROT(String appDT) throws ApplicationException {
//        List resultList = null;
//        try {
//            resultList = em.createNativeQuery("select TYPE,ROT,ROTApplyOn,glhead from taxmaster where ApplicableDt<='" + appDT + "' and applicableFlag='Y' and Auth='Y'").getResultList();
//            if (resultList.size() <= 0) {
//            }
//            return resultList;
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }

    public String getChequeBookTable(String acNature) {
        String chqBookTable = "";
        if (acNature.equals(CbsConstant.CURRENT_AC)) {
            chqBookTable = "chbook_ca";
        } else if (acNature.equals(CbsConstant.SAVING_AC)) {
            chqBookTable = "chbook_sb";
        } else if (acNature.equals(CbsConstant.PAY_ORDER)) {
            chqBookTable = "chbook_po";
        }
        return chqBookTable;
    }
}