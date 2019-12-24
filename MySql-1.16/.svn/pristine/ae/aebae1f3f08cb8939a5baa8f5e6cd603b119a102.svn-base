package com.cbs.facade.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.CashierCashRecieveGridFile;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DenominitionTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import java.math.BigDecimal;
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

@Stateless(mappedName = "CashierCashRecievedFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CashierCashRecievedFacade implements CashierCashRecievedFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPost;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;

    public List gridLoad(String brCode) throws ApplicationException {
        List grid = new ArrayList();
        try {
            List tempBdLt = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG='N' AND BRNCODE='" + brCode + "'").getResultList();
            String tempBd = ((Vector) tempBdLt.get(0)).get(0).toString();
//            Query selectQuery = em.createNativeQuery("select t.tokenno,t.acno,t.custname,t.cramt,ifnull(ifnull(ifnull(r1.balance,r2.balance),r3.balance),0)as balance, "
//                    + "t.enterby,ifnull(t.tokenpaid,'N'),t.recno,t.details,t.org_brnid,t.dest_brnid from tokentable_credit t "
//                    + "left join reconbalan r1 on t.acno=r1.acno left join ca_reconbalan r2 on t.acno=r2.acno left join td_reconbalan r3 on t.acno=r3.acno "
//                    + "where t.org_brnid='" + brCode + "' and (t.tokenpaid = 'N' OR t.tokenpaid IS NULL) "
//                    + " and t.TokenNo is Not null AND t.DT='" + tempBd + "' order by tokenno");
            Query selectQuery = em.createNativeQuery("select t.tokenno,t.acno,t.custname,t.cramt,"
                    + " ifnull(ifnull(ifnull(r1.balance,r2.balance),r3.balance),0)as balance,t.enterby,ifnull(t.tokenpaid,'N'), "
                    + " t.recno,t.details,t.org_brnid,t.dest_brnid, "
                    + " ifnull(ifnull(concat(a.JtName1,' / ',a.JtName2,' / ',a.JtName3,' / ',a.JtName4),concat(b.JtName1,' / ',b.JtName2,' / ',b.JtName3,' / ',b.JtName4)),'') "
                    + " from tokentable_credit t "
                    + " left join reconbalan r1 on t.acno=r1.acno "
                    + " left join ca_reconbalan r2 on t.acno=r2.acno "
                    + " left join td_reconbalan r3 on t.acno=r3.acno "
                    + " left join accountmaster a on t.acno=a.acno "
                    + " left join td_accountmaster b on t.acno=b.acno "
                    + " left join gltable c on t.acno=c.acno "
                    + " where t.org_brnid='" + brCode + "' and (t.tokenpaid = 'N' OR t.tokenpaid IS NULL) and "
                    + " t.TokenNo is Not null AND t.DT='" + tempBd + "' and t.authby is null and (t.auth is null or t.auth='N') order by tokenno");
            grid = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return grid;
    }

//    public String cashRecievedProcess(List<CashierCashRecieveGridFile> gridDetail, String enterBy, String enterDt, DenominitionTable denominitionObj, boolean denominationRender, String brCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            int var1 = 0;
//            if (gridDetail.isEmpty() || gridDetail == null) {
//                ut.rollback();
//                return "Grid is empty !!!";
//            }
//            boolean chk = false;
//            for (int i = 0; i < gridDetail.size(); i++) {
//                if (gridDetail.get(i).getTokenPaid().equalsIgnoreCase("Y")) {
//                    chk = true;
//                    Query updateToken = em.createNativeQuery("update tokentable_credit set TokenPaid = '" + gridDetail.get(i).getTokenPaid() + "',tokenpaidby='" + enterBy + "' where acno = '" + gridDetail.get(i).getAcno() + "' and dt = '" + enterDt + "' and recno = " + gridDetail.get(i).getRecNo() + " and TokenNo = " + gridDetail.get(i).getTokenNo() + "");
//                    var1 = updateToken.executeUpdate();
//                    if (var1 <= 0) {
//                        ut.rollback();
//                        return "Update failed in TokenTable_Credit !!!";
//                    }
//                }
//            }
//            if (chk == false) {
//                ut.rollback();
//                return "Please select the record from grid by double clicking on cash recieved column to change from N to Y and then press Recieve button !!!";
//            }
//            /**Code for denomination**/
//            if (denominationRender == true) {
//                if (denominitionObj == null) {
//                    ut.rollback();
//                    return "Please enter denomination correctly !!!";
//                }                
//                
//                Query insertDenominationQuery = em.createNativeQuery("insert into denominition(Acno, Amount, "
//                        + "Ty, Dt, Rs1000, Rs500, Rs100, Rs50, Rs20, Rs10, Rs5, Rs2, Rs1, Rs05, "
//                        + "EnterBy, recno, Trantime ) values ('" + denominitionObj.getAcno() + "', "
//                        + denominitionObj.getAmount() + ", " + denominitionObj.getTy() + ", '"
//                        + denominitionObj.getDt() + "', " + denominitionObj.getRs1000() + ", "
//                        + denominitionObj.getRs500() + ", " + denominitionObj.getRs100() + ", "
//                        + denominitionObj.getRs50() + ", " + denominitionObj.getRs20() + ", "
//                        + denominitionObj.getRs10() + ", " + denominitionObj.getRs5() + ", "
//                        + denominitionObj.getRs2() + ", " + denominitionObj.getRs1() + ", "
//                        + denominitionObj.getRs05() + ", '" + denominitionObj.getEnterBy() + "', "
//                        + denominitionObj.getRecno() + ",CURRENT_TIMESTAMP)");
//                int var2 = insertDenominationQuery.executeUpdate();
//                if (var2 <= 0) {
//                    ut.rollback();
//                    return "Insertion failed in cash demonination !!!";
//                }
//
//                List chkCashierLt = em.createNativeQuery("SELECT Cashier FROM denominition_opening where Cashier= '" + enterBy + "' and dt= '" + enterDt + "' and brncode='" + brCode + "' and CLOSINGFLAG='N'").getResultList();
//                if (chkCashierLt.isEmpty() || chkCashierLt.size() <= 0) {
//                    ut.rollback();
//                    return "Cash demonination for this cashier is not present , first head cashier should allot cash for this cashier !!!";
//                }
//
//                Query updateCashierDenomination = em.createNativeQuery("update denominition_opening set Amount=Amount+" + denominitionObj.getAmount() + ", Rs1000=Rs1000+" + denominitionObj.getRs1000() + ",Rs500=Rs500+" + denominitionObj.getRs500() + ","
//                        + " Rs100=Rs100+" + denominitionObj.getRs100() + ",Rs50=Rs50+" + denominitionObj.getRs50() + ",Rs20=Rs20+" + denominitionObj.getRs20() + ",Rs10=Rs10+" + denominitionObj.getRs10() + ",Rs5=Rs5+" + denominitionObj.getRs5() + ",Rs2=Rs2+" + denominitionObj.getRs2() + ",Rs1=Rs1+" + denominitionObj.getRs1() + ",Rs05=Rs05+" + denominitionObj.getRs05() + " "
//                        + " where Cashier= '" + enterBy + "' and dt= '" + enterDt + "' and brncode='" + brCode + "' and CLOSINGFLAG='N'");
//                int var3 = updateCashierDenomination.executeUpdate();
//                if (var3 <= 0) {
//                    ut.rollback();
//                    return "Updation failed in cash denomination opening !!!";
//                }
//            }
//            /**End of denomination code**/
//            if (var1 <= 0) {
//                ut.rollback();
//                return "Cash recieving failed !!!";
//            } else {
//                ut.commit();
//                return "Cash recieved succesfully.";
//            }
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(e);
//            } catch (IllegalStateException ex) {
//                throw new ApplicationException(ex);
//            } catch (SecurityException ex) {
//                throw new ApplicationException(ex);
//            } catch (SystemException ex) {
//                throw new ApplicationException(ex);
//            }
//        }
//    }
    public String cashRecievedProcess(List<CashierCashRecieveGridFile> gridDetail, String enterBy, String enterDt, List<DDSDenominationGrid> denominationTable, boolean denominationRender, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if (gridDetail.isEmpty() || gridDetail == null) {
                ut.rollback();
                return "Grid is empty !!!";
            }
            boolean chk = false;
            
            for (int i = 0; i < gridDetail.size(); i++) {
                if (gridDetail.get(i).getTokenPaid().equalsIgnoreCase("Y")) {
                    chk = true;
                }
            }
            
            if (chk == false) {
                ut.rollback();
                return "Please select the record from grid by double clicking on cash recieved column to change from N to Y and then press Recieve button !!!";
            }
            
            for (int i = 0; i < gridDetail.size(); i++) {
                if (gridDetail.get(i).getTokenPaid().equalsIgnoreCase("Y")) {
                    //chk = true;
                    Query updateToken = em.createNativeQuery("update tokentable_credit set TokenPaid = '" + gridDetail.get(i).getTokenPaid() + "',tokenpaidby='" + enterBy + "' where acno = '" + gridDetail.get(i).getAcno() + "' and dt = '" + enterDt + "' and recno = " + gridDetail.get(i).getRecNo() + " and TokenNo = " + gridDetail.get(i).getTokenNo() + "");
                    var1 = updateToken.executeUpdate();
                    if (var1 <= 0) {
                        ut.rollback();
                        return "Update failed in TokenTable_Credit !!!";
                    }
                }

//                if (chk == false) {
//                    ut.rollback();
//                    return "Please select the record from grid by double clicking on cash recieved column to change from N to Y and then press Recieve button !!!";
//                }
                /**
                 * Code for denomination*
                 */
                if (denominationRender == true) {
                    if (denominationTable.isEmpty()) {
                        ut.rollback();
                        return "Please enter denomination correctly !!!";
                    }
                    if (gridDetail.get(i).getTokenPaid().equalsIgnoreCase("Y")) {
                        for (DDSDenominationGrid olObj : denominationTable) {
                            double denVal = olObj.getDenoValue();
                            int denNoCnt = olObj.getDenoNo();
                            String oNFlg = olObj.getFlag();                            
                            String denoMsg = interBranchFacade.insertDenominationDetail(gridDetail.get(i).acno, Float.parseFloat(gridDetail.get(i).recNo), enterDt, new BigDecimal(denVal),
                                    denNoCnt, Integer.parseInt(olObj.getTy()), brCode, enterBy, oNFlg);
                            if (!denoMsg.equalsIgnoreCase("true")) {
                                ut.rollback();
                                return "Insertion Problem in denomination_details !";
                            } else {
                                int cnVal = 0;
                                if(olObj.getTy().equalsIgnoreCase("0") || olObj.getTy().equalsIgnoreCase("4")){
                                    cnVal = denNoCnt;
                                }else if (olObj.getTy().equalsIgnoreCase("1") || olObj.getTy().equalsIgnoreCase("3")){
                                    cnVal = denNoCnt * -1;
                                }
                                
                                String denUpdateMsg = interBranchFacade.updateOpeningDenomination(brCode, new BigDecimal(denVal), cnVal, enterDt, oNFlg);
                                if (!denUpdateMsg.equalsIgnoreCase("true")) {
                                    ut.rollback();
                                    return "Updation Problem in denomination_details !";
                                }
                            }
                        }
                    }                    
                }
            }

            /**
             * End of denomination code*
             */
            if (var1 <= 0) {
                ut.rollback();
                return "Cash recieving failed !!!";
            } else {
                ut.commit();
                return "Cash recieved succesfully.";
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
            }
        }
    }

    /**
     * Below methods are for denominition*
     */
    public boolean cashAndDenominitionModChk() throws ApplicationException {
        try {
            List paramInfoRepDenoLt = em.createNativeQuery("SELECT ReportName FROM parameterinfo_report WHERE CODE=1 AND ReportName='CASH DENOMINITION MODULE'").getResultList();
            if (paramInfoRepDenoLt.isEmpty() || paramInfoRepDenoLt.size() <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    /**
     * ******************************CashierCurrencyCloseBean **************************
     */
    public List activeCashierCombo(String brCode) throws ApplicationException {
        List actCashier = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select a.userid from counterdetails a,securityinfo b where a.userid=b.userid and b.CASHIERST='Y' and  a.statusofcounter='Y' and a.brncode='" + brCode + "' and b.brncode='" + brCode + "'");
            actCashier = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return actCashier;
    }

    public String loadCashierData(String userId, String brCode, String enterDt) throws ApplicationException {
        String result = "";
        double openingBal = 0.0d;
        double cashRec = 0.0d;
        double cashSubTot = 0.0d;
        double cashPay = 0.0d;
        double drAmtThrTran = 0.0d;
        double drDemand = 0.0d;
        double crDemand = 0.0d;
        double closingBal = 0.0d;
        int counterAssigned = 0;
        String statusOfCounter = "";
        try {
            /**
             * Opening Balance*
             */
            List openBalLt = em.createNativeQuery("Select ifnull(sum(amount),0)  from denominition_opening where dt='" + enterDt + "' and cashier='" + userId + "' and BRNCODE='" + brCode + "'").getResultList();
            Vector openBalLtVec = (Vector) openBalLt.get(0);
            openingBal = Double.parseDouble(openBalLtVec.get(0).toString());

            /**
             * For Cash Recieved*
             */
            List cashRecLt = em.createNativeQuery("Select ifnull(sum(a.amt),0) as amount from (select ifnull(sum(cramt),0)  as amt from recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and ty=0  and trantype=0 "
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " Select ifnull(sum(cramt),0) as amt from loan_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and ty=0  and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " Select ifnull(sum(cramt),0) as amt from of_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and ty=0  and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " Select ifnull(sum(cramt),0) as amt from rdrecon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=0 and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select ifnull(sum(cramt),0) as amt from ca_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and ty=0  and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select ifnull(sum(cramt),0) as amt from gl_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=0 and trantype=0 and iy not in (5,6)"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select ifnull(sum(cramt),0) as amt from ddstransaction where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=0 and  trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select ifnull(sum(cramt),0) as amt from td_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and trantype<>27 and ty=0 and trantype=0 AND CLOSEFLAG IS NULL"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)) a").getResultList();
            Vector cashRecLtVec = (Vector) cashRecLt.get(0);
            cashRec = Double.parseDouble(cashRecLtVec.get(0).toString());

            /**
             * Sub Total*
             */
            cashSubTot = openingBal + cashRec;

            /**
             * Cash Payment*
             */
            List cashPayLt = em.createNativeQuery("select ifnull(sum(a.amt),0) as amount from (select sum(dramt) as amt from recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and ty=1  and trantype=0 and iy not in (5,6) "
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from loan_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and ty=1  and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from of_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=1 and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from ca_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=1 and trantype=0 and iy not in (5,6)"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from rdrecon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=1 and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from gl_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=1 and trantype=0 and iy not in (5,6)"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from ddstransaction where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y'  and ty=1 and trantype=0"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
                    + " union all"
                    + " select sum(dramt) as amt from td_recon where dt='" + enterDt + "' and tokenpaidby='" + userId + "' and org_brnid='" + brCode + "' and auth='Y' and trantype<>27  and ty=1 and trantype=0 AND CLOSEFLAG IS NULL"
                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE"
                    + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)) a").getResultList();
            Vector cashPayLtVec = (Vector) cashPayLt.get(0);
            cashPay = Double.parseDouble(cashPayLtVec.get(0).toString());

            /**
             * Closing balance*
             */
            closingBal = cashSubTot - cashPay;

            List denominationCloseLt = em.createNativeQuery("select ifnull(sum(amount),0) from denominition where enterby='" + userId + "'"
                    + " and dt='" + enterDt + "' and ty=1 and substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "000000'").getResultList();
            Vector denominationCloseLtVec = (Vector) denominationCloseLt.get(0);
            double tempCashP = Double.parseDouble(denominationCloseLtVec.get(0).toString());
            closingBal = closingBal - tempCashP;

            /**
             * Dr demand*
             */
            List drDemandLt = em.createNativeQuery("select ifnull(sum(amount),0) from denominition where enterby='" + userId + "'"
                    + " and dt='" + enterDt + "' and ty=1 and substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "100001'").getResultList();
            Vector drDemandLtVec = (Vector) drDemandLt.get(0);
            drDemand = Double.parseDouble(drDemandLtVec.get(0).toString());

            closingBal = closingBal - drDemand;

            /**
             * Cr Demand*
             */
            List crDemandLt = em.createNativeQuery("select ifnull(sum(amount),0) from denominition where enterby='" + userId + "'"
                    + " and dt='" + enterDt + "' and ty=0 and substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "100001'").getResultList();
            Vector crDemandLtVec = (Vector) crDemandLt.get(0);
            crDemand = Double.parseDouble(crDemandLtVec.get(0).toString());

            closingBal = closingBal + crDemand;

            List counterDtLt = em.createNativeQuery("select counterno,statusofcounter from counterdetails where userid='" + userId + "' and brncode='" + brCode + "'").getResultList();
            Vector counterDtLtVec = (Vector) counterDtLt.get(0);
            counterAssigned = Integer.parseInt(counterDtLtVec.get(0).toString());
            statusOfCounter = counterDtLtVec.get(1).toString();
            if (statusOfCounter.equalsIgnoreCase("Y")) {
                statusOfCounter = "Active";
            } else if (statusOfCounter.equalsIgnoreCase("N")) {
                statusOfCounter = "Inactive";
            } else {
                statusOfCounter = counterDtLtVec.get(1).toString();
            }

            List headCahierLt = em.createNativeQuery("SELECT HEADCASHIER FROM securityinfo WHERE UserId='" + userId + "' and brncode='" + brCode + "'").getResultList();
            Vector headCahierLtVec = (Vector) headCahierLt.get(0);
            String cashierType = headCahierLtVec.get(0).toString();
            if (cashierType.equalsIgnoreCase("N")) {
                cashierType = "Sub Cashier";
            } else if (cashierType.equalsIgnoreCase("Y")) {
                cashierType = "Head Cashier";
            }
            result = openingBal + ":" + cashRec + ":" + cashSubTot + ":" + cashPay + ":" + drAmtThrTran + ":" + drDemand + ":" + crDemand + ":" + closingBal + ":" + counterAssigned + ":" + statusOfCounter + ":" + cashierType;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List cashierDenominitionGridDetail(String userId, String brCode, String enterDt) throws ApplicationException {
        List griddetail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT CASHIER,AMOUNT,date_format(DT,'%d/%m/%Y'),RS1000,RS500,RS100,RS50,RS20,RS10,RS5,RS2,RS1,RS05 "
                    + " FROM denominition_opening WHERE Cashier='" + userId + "' and BRNCODE='" + brCode + "' AND AUTH='Y' AND DT='" + enterDt + "'");
            griddetail = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return griddetail;
    }

    public String cashCloseAction(String userId, String brCode, String enterDt, double totalAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            String headCshr = "";
            if (userId == null || userId.equalsIgnoreCase("") || userId.length() == 0) {
                ut.rollback();
                return "Cashier Id not found !!!";
            }
            if (brCode == null || brCode.equalsIgnoreCase("") || brCode.length() == 0) {
                ut.rollback();
                return "Branch code not found !!!";
            }
            List csrDenoDetailLt = em.createNativeQuery("SELECT AMOUNT,RS1000,RS500,RS100,RS50,RS20,RS10,RS5,RS2,RS1,RS05 "
                    + " FROM denominition_opening WHERE Cashier='" + userId + "' and BRNCODE='" + brCode + "' AND AUTH='Y' AND DT='" + enterDt + "'").getResultList();
            if (csrDenoDetailLt.isEmpty()) {
                ut.rollback();
                return "Cash not exists to close !!!";
            }
            Vector csrDenoDetailLtVec = (Vector) csrDenoDetailLt.get(0);
            double amount = Double.parseDouble(csrDenoDetailLtVec.get(0).toString());
            int rs1000 = Integer.parseInt(csrDenoDetailLtVec.get(1).toString());
            int rs500 = Integer.parseInt(csrDenoDetailLtVec.get(2).toString());
            int rs100 = Integer.parseInt(csrDenoDetailLtVec.get(3).toString());
            int rs50 = Integer.parseInt(csrDenoDetailLtVec.get(4).toString());
            int rs20 = Integer.parseInt(csrDenoDetailLtVec.get(5).toString());
            int rs10 = Integer.parseInt(csrDenoDetailLtVec.get(6).toString());
            int rs5 = Integer.parseInt(csrDenoDetailLtVec.get(7).toString());
            int rs2 = Integer.parseInt(csrDenoDetailLtVec.get(8).toString());
            int rs1 = Integer.parseInt(csrDenoDetailLtVec.get(9).toString());
            int rs05 = Integer.parseInt(csrDenoDetailLtVec.get(10).toString());

            List chkLt1 = em.createNativeQuery("select cashierst,HEADCASHIER from securityinfo where userid='" + userId + "' and levelid not in (5,6) and brncode='" + brCode + "'").getResultList();
            if (chkLt1.isEmpty() || chkLt1.size() <= 0) {
                ut.rollback();
                return "User ID does not exists !!!";
            } else {
                String cashierst = ((Vector) chkLt1.get(0)).get(0).toString();
                headCshr = ((Vector) chkLt1.get(0)).get(1).toString();
                if (cashierst.equalsIgnoreCase("N")) {
                    ut.rollback();
                    return "This user ID is not a valid cashier !!!";
                }
            }
            if (amount != totalAmt) {
                ut.rollback();
                return "Amount not matched !!!";
            }
            if (headCshr.equalsIgnoreCase("Y")) {
                List chkSubCashierCloseLt = em.createNativeQuery("SELECT Cashier,CLOSINGFLAG FROM denominition_opening WHERE DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND HEADCASHIER='N'").getResultList();
                if (!chkSubCashierCloseLt.isEmpty()) {
                    String tempCashierId = "";
                    String tempClosingFlag = "";
                    List cahierAddLt = new ArrayList();
                    for (int i = 0; i < chkSubCashierCloseLt.size(); i++) {
                        Vector ele = (Vector) chkSubCashierCloseLt.get(i);
                        tempCashierId = ele.get(0).toString();
                        tempClosingFlag = ele.get(1).toString();
                        if (tempClosingFlag.equalsIgnoreCase("N")) {
                            cahierAddLt.add(tempCashierId);
                        }
                    }
                    if (!cahierAddLt.isEmpty()) {
                        ut.rollback();
                        return "Head cashier cannot close cash because " + cahierAddLt + " still open !!!";
                    }
                }
                Query updateDen = em.createNativeQuery("update denominition_opening set ClosingFlag='Y',Closingbalance=" + totalAmt + " where cashier= '" + userId + "' and dt= '" + enterDt + "' and CLOSINGFLAG='N' and brncode='" + brCode + "'");
                var = updateDen.executeUpdate();
                if (var <= 0) {
                    ut.rollback();
                    return "Updation failed in denomination opening !!!";
                }
                Query updateCounter = em.createNativeQuery("update counterdetails set statusofcounter='N' where userid= '" + userId + "' AND brncode='" + brCode + "'");
                var1 = updateCounter.executeUpdate();
                if (var1 <= 0) {
                    ut.rollback();
                    return "Updation failed in counter details !!!";
                } else {
                    ut.commit();
                    return "Cash Close Successfully For : " + userId;
                }
            } else {
                Query updateDen = em.createNativeQuery("update denominition_opening set Amount=Amount+" + totalAmt + ", Rs1000=Rs1000+" + rs1000 + ",Rs500=Rs500+" + rs500 + ","
                        + " Rs100=Rs100+" + rs100 + ",Rs50=Rs50+" + rs50 + ",Rs20=Rs20+" + rs20 + ",Rs10=Rs10+" + rs10 + ",Rs5=Rs5+" + rs5 + ",Rs2=Rs2+" + rs2 + ",Rs1=Rs1+" + rs1 + ",Rs05=Rs05+" + rs05 + " "
                        + " where HEADCASHIER= 'Y' and dt= '" + enterDt + "' and brncode='" + brCode + "' and CLOSINGFLAG='N'");
                var = updateDen.executeUpdate();
                if (var <= 0) {
                    ut.rollback();
                    return "Updation failed in denomination opening !!!";
                }

                Query updateCounter = em.createNativeQuery("update counterdetails set statusofcounter='N' where userid= '" + userId + "' AND brncode='" + brCode + "'");
                var1 = updateCounter.executeUpdate();
                if (var1 <= 0) {
                    ut.rollback();
                    return "Updation failed in counter details !!!";
                }

                Query updateDenOfCashier = em.createNativeQuery("update denominition_opening set ClosingFlag='Y',Closingbalance=" + totalAmt + " WHERE cashier= '" + userId + "' and dt= '" + enterDt + "' and CLOSINGFLAG='N' and brncode='" + brCode + "'");
                var1 = updateDenOfCashier.executeUpdate();
                if (var1 <= 0) {
                    ut.rollback();
                    return "Updation failed in denomination opening !!!";
                } else {
                    ut.commit();
                    return "Cash Close Successfully For : " + userId;
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
            }
        }
    }

    /**
     * ************************************** End *************************************
     */
    /**
     * ****************************CashierOpeningBalanceRegisterBean *******************
     */
    public List gridLoadCashier(String brCode) throws ApplicationException {
        List grid = new ArrayList();
        try {
//            List tempBdLt = em.createNativeQuery("SELECT DATE FROM BANKDAYS WHERE DAYENDFLAG='N' AND BRNCODE='" + brCode + "'").getResultList();
//            String tempBd = ((Vector) tempBdLt.get(0)).get(0).toString();
            Query selectQuery = em.createNativeQuery("SELECT Name,UserId,CounterNo,StatusOfCounter FROM counterdetails WHERE BRNCODE='" + brCode + "'");
            grid = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return grid;
    }

    public String getUserNameMethod(String userId, String brCode) throws ApplicationException {
        String userName = "";
        try {
            List chkLt2 = em.createNativeQuery("select * from counterdetails where userid='" + userId + "' and brncode='" + brCode + "'").getResultList();
            if (!chkLt2.isEmpty() || chkLt2.size() > 0) {
                return "Counter has already assigned to this user ID !!!";
            }
            List chkLt1 = em.createNativeQuery("select cashierst,username from securityinfo where userid='" + userId + "' and levelid not in (5,6) and brncode='" + brCode + "'").getResultList();
            if (chkLt1.isEmpty() || chkLt1.size() <= 0) {
                return "User ID does not exists !!!";
            } else {
                String cashierst = ((Vector) chkLt1.get(0)).get(0).toString();
                userName = ((Vector) chkLt1.get(0)).get(1).toString().toUpperCase();
                if (cashierst.equalsIgnoreCase("N")) {
                    return "This user ID is not a valid cashier !!!";
                } else {
                    return userName;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String activateCashierId(String userId, String userName, String counterNo, String enterBy, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            List chkLt1 = em.createNativeQuery("select cashierst from securityinfo where userid='" + userId + "' and levelid not in (5,6) and brncode='" + brCode + "'").getResultList();
            if (chkLt1.isEmpty() || chkLt1.size() <= 0) {
                ut.rollback();
                return "User ID does not exists !!!";
            } else {
                String cashierst = ((Vector) chkLt1.get(0)).get(0).toString();
                if (cashierst.equalsIgnoreCase("N")) {
                    ut.rollback();
                    return "This user ID is not a valid cashier !!!";
                }
            }
            List chkLt2 = em.createNativeQuery("select * from counterdetails where userid='" + userId + "' and brncode='" + brCode + "'").getResultList();
            if (!chkLt2.isEmpty() || chkLt2.size() > 0) {
                ut.rollback();
                return "Counter has already assigned to this user ID !!!";
            }
            List chkLt3 = em.createNativeQuery("select * from counterdetails where counterno='" + counterNo + "' and brncode='" + brCode + "'").getResultList();
            if (!chkLt3.isEmpty() || chkLt3.size() > 0) {
                ut.rollback();
                return "This Counter number has already assigned !!!";
            }
            Query insertCounterDetQ = em.createNativeQuery("INSERT INTO counterdetails VALUES(" + counterNo + ",'" + userId + "','" + userName + "','Y','" + enterBy + "',CURRENT_TIMESTAMP,'" + brCode + "')");
            var = insertCounterDetQ.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Data not saved !!!";
            } else {
                ut.commit();
                return "Data saved succesfully.";
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
            }
        }
    }

    public String updateCashierCounter(String userId, String userName, String counterNo, String enterBy, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            List chkLt1 = em.createNativeQuery("select cashierst from securityinfo where userid='" + userId + "' and levelid not in (5,6) and brncode='" + brCode + "'").getResultList();
            if (chkLt1.isEmpty() || chkLt1.size() <= 0) {
                ut.rollback();
                return "User ID does not exists !!!";
            } else {
                String cashierst = ((Vector) chkLt1.get(0)).get(0).toString();
                if (cashierst.equalsIgnoreCase("N")) {
                    ut.rollback();
                    return "This user ID is not a valid cashier !!!";
                }
            }
            List chkLt3 = em.createNativeQuery("select * from counterdetails where counterno='" + counterNo + "' and brncode='" + brCode + "'").getResultList();
            if (!chkLt3.isEmpty() || chkLt3.size() > 0) {
                ut.rollback();
                return "This Counter number has already assigned !!!";
            }
            Query updateCounterDetQ = em.createNativeQuery("update counterdetails set counterno='" + counterNo + "' where userid='" + userId + "' and brncode='" + brCode + "'");
            var = updateCounterDetQ.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Data not updated !!!";
            } else {
                ut.commit();
                return "Data updated succesfully.";
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
            }
        }
    }

    public List safeDenominitionGridDetail(String brCode, String enterDt) throws ApplicationException {
        List griddetail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT CASHIER,AMOUNT,date_format(DT,'%d/%m/%Y'),RS1000,RS500,RS100,RS50,RS20,RS10,RS5,RS2,RS1,RS05 "
                    + " FROM denominition_opening WHERE BRNCODE='" + brCode + "' AND AUTH='Y' AND DT='" + enterDt + "'");
            griddetail = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return griddetail;
    }
    /**
     * *********************************** End *******************************************
     */
}
