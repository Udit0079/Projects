/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.transaction.UserTransaction;

@Stateless(mappedName = "StandingInstructionManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class StandingInstructionManagementFacade implements StandingInstructionManagementRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private BankProcessManagementFacadeRemote bankProcessManagement;

    public List accNoPass(String acno, int listIndex) throws ApplicationException {
        try {
            List resulList1 = new ArrayList();
            if (listIndex == 0) {
//                resulList1 = em.createNativeQuery("Select InStrNo,EffDate,Amount,Status,EntryDate,EnterBy,FromAcno,ToAcno From standins_transmaster Where FromAcno = '" + acno + "' order by InStrNo").getResultList();
                resulList1 = em.createNativeQuery("Select InStrNo,EffDate,Amount,Status,EntryDate,EnterBy,FromAcno,ToAcno,SNO From standins_transmaster Where FromAcno = '" + acno + "' "
                        + " UNION Select InStrNo,EffDate,Amount,'EXECUTED',ProcessDate,ProcessedBy,FromAcno,ToAcno,SNO From standins_transexecuted Where FromAcno = '" + acno + "' "
                        + " UNION Select InStrNo,EffDate,Amount,'PENDING',ProcessDate,ProcessedBy,FromAcno,ToAcno,SNO From standins_transpending Where FromAcno = '" + acno + "' order by FromAcno,ToAcno,SNO").getResultList();
                return resulList1;
            } else if ((listIndex == 1)) {
//                resulList1 = em.createNativeQuery("Select InStrNo,EffDate,Amount,Status,EntryDate,EnterBy,FromAcno,ToAcno From standins_transmaster Where ToAcno = '" + acno + "' order by InStrNo").getResultList();
                resulList1 = em.createNativeQuery("Select InStrNo,EffDate,Amount,Status,EntryDate,EnterBy,FromAcno,ToAcno,SNO From standins_transmaster Where ToAcno = '" + acno + "' "
                        + " UNION Select InStrNo,EffDate,Amount,'EXECUTED',ProcessDate,ProcessedBy,FromAcno,ToAcno,SNO From standins_transexecuted Where ToAcno = '" + acno + "' "
                        + " UNION Select InStrNo,EffDate,Amount,'PENDING',ProcessDate,ProcessedBy,FromAcno,ToAcno,SNO From standins_transpending Where ToAcno = '" + acno + "' order by ToAcno,FromAcno,SNO").getResultList();
                return resulList1;
            } else if ((listIndex == 2)) {
                resulList1 = em.createNativeQuery("Select InStrNo,EffDate,Amount,Status,EntryDate,EnterBy,FromAcno,ToAcno,SNO From standins_transmaster Where INSTRNO = '" + acno + "' "
                        + " UNION Select InStrNo,EffDate,Amount,'EXECUTED',ProcessDate,ProcessedBy,FromAcno,ToAcno,SNO From standins_transexecuted Where INSTRNO = '" + acno + "' "
                        + " UNION Select InStrNo,EffDate,Amount,'PENDING',ProcessDate,ProcessedBy,FromAcno,ToAcno,SNO From standins_transpending Where INSTRNO = '" + acno + "' order by ToAcno,FromAcno,SNO").getResultList();
                return resulList1;
            }
            return resulList1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List loadGridPending(String BRCODE) throws ApplicationException {
        try {
            List GrdPendingResult = new ArrayList();
            List lastUpdate = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "' ").getResultList();
            Vector ltUpdate = (Vector) lastUpdate.get(0);
            String currentDate = ltUpdate.get(0).toString();
            Query selectQuery = em.createNativeQuery("select INSTRNO,SNO,FROMACNO,TOACNO,AMOUNT,DATE_FORMAT(EFFDATE,'%d/%m/%Y') AS EFFDATE,REMARKS,"
                    + "DATE_FORMAT(Expirydt,'%d/%m/%Y') AS Expirydt from standins_transpending st,accountmaster ac where st.FROMACNO=ac.acno and "
                    + "ac.curbrcode='" + BRCODE + "' and processdate <= '" + currentDate + "' order by INSTRNO,SNO");
            GrdPendingResult = selectQuery.getResultList();
            return GrdPendingResult;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getCustomerName(String acno) throws ApplicationException {
        String custName = "";
        String nature = "";
        try {
            nature = fTSPosting43CBSBean.getAccountNature(acno);
            if ((nature.equals(CbsConstant.FIXED_AC)) || (nature.equals(CbsConstant.MS_AC))) {
                List status = em.createNativeQuery("select custname from td_accountmaster where  acno='" + acno + "'").getResultList();
                Vector accAllcustname = (Vector) status.get(0);
                custName = accAllcustname.get(0).toString();
            } else {
                List status1 = em.createNativeQuery("select custname from accountmaster where  acno='" + acno + "'").getResultList();
                Vector accAllcustname1 = (Vector) status1.get(0);
                custName = accAllcustname1.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return custName;
    }

    public String postButton(String user, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
        String msg1 = "Instructions Posted Successfully  And Confirm From Today's Error Report For Details ";
        List select1 = new ArrayList();
        try {
            float instrNo = 0;
            int sNo = 0;
            String fromAcnos = "";
            String toAcno = "";
            double amount = 0d;
            String effDate = "";
            String remarks = "";
            String expiryDt = "";
            String StnDetails = "";

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);

            List select = em.createNativeQuery("select instrno,sno,fromacno,toacno,amount,effdate,remarks,expirydt from "
                    + "standins_transpending st,accountmaster ac where st.fromacno=ac.acno and ac.curbrcode='" + BRCODE
                    + "' and processdate <= '" + Tempbd + "' order by instrno,sno").getResultList();
            if (select.isEmpty()) {
                throw new ApplicationException("Problem in data insertion");
            }

            List fromAcno = em.createNativeQuery("select fromacno from standins_transpending st,accountmaster ac where st.fromacno=ac.acno "
                    + "and ac.curbrcode='" + BRCODE + "' and processdate <= '" + Tempbd + "'").getResultList();
            if (fromAcno.isEmpty()) {
                throw new ApplicationException("No Instructions Pending For Today ");
            }
            List arraylist = new ArrayList();
            for (int i = 0; i < select.size(); i++) {
                Vector ele = (Vector) select.get(i);
                instrNo = (Float.parseFloat(ele.get(0).toString()));
                sNo = (Integer.parseInt(ele.get(1).toString()));
                fromAcnos = (ele.get(2).toString());
                toAcno = (ele.get(3).toString());
                amount = (Double.parseDouble(ele.get(4).toString()));
                String effDt = (ele.get(5).toString());
                String yy = effDt.substring(0, 4);
                String mm = effDt.substring(5, 7);
                String dd = effDt.substring(8, 10);
                effDate = yy + "" + mm + "" + dd;

                remarks = (ele.get(6).toString());
                String expiryDate = (ele.get(7).toString());
                String yy1 = expiryDate.substring(0, 4);
                String mm1 = expiryDate.substring(5, 7);
                String dd1 = expiryDate.substring(8, 10);
                expiryDt = yy1 + "" + mm1 + "" + dd1;
                int expDt = Integer.parseInt(expiryDt);
                arraylist.add(expDt);
                try {
                    ut.begin();
                    if (expDt >= Tempbds) {
                        String fromAccNature = fTSPosting43CBSBean.getAccountNature(fromAcnos);
                        String fromBrCode = fTSPosting43CBSBean.getCurrentBrnCode(fromAcnos);
                        String toAcctNature = fTSPosting43CBSBean.getAccountNature(toAcno);
                        String toBrCode = fTSPosting43CBSBean.getCurrentBrnCode(toAcno);

                        List accTypeFrom = bankProcessManagement.getAccountDetails(fromAccNature, fromAcnos);
                        if (accTypeFrom.isEmpty()) {
                            throw new ApplicationException("Data does not exist for " + fromAcnos);
                        }
                        String stnDetails = "";
                        Vector accAllValues = (Vector) accTypeFrom.get(0);
                        int fromAccSts = Integer.parseInt(accAllValues.get(1).toString());

                        String balanceValue = accAllValues.get(2).toString();
                        String limitValue = accAllValues.get(3).toString();

                        if (fromAccSts == 9) {
                            stnDetails = "Account Has been Closed";
                        }
                        if (fromAccSts == 10) {
                            stnDetails = "Lien Marked";
                        }
                        if (fromAccSts == 8) {
                            stnDetails = " Operation Stopped";
                        }
                        if (fromAccSts == 7) {
                            stnDetails = " Withdrawal Stopped";
                        }
                        double fromBalance = Double.parseDouble(balanceValue);
                        double fromOdlimit = Double.parseDouble(limitValue);

                        List accTypeTo = bankProcessManagement.getAccountDetails(toAcctNature, toAcno);
                        if (accTypeFrom.isEmpty()) {
                            throw new ApplicationException("Data does not exist for " + toAcno);
                        }
                        Vector accToValues = (Vector) accTypeTo.get(0);
                        int toAccSts = Integer.parseInt(accToValues.get(1).toString());

                        if ((fromAccSts == 9) && (toAccSts == 9)) {
                            throw new ApplicationException("Both A/C s  -> " + fromAcnos + " And " + toAcno + " Are Closed ");
                        }
                        if ((fromAccSts == 9) || (fromAccSts == 10) || (fromAccSts == 8) || (fromAccSts == 7)) {
                            throw new ApplicationException("Ac No :-> " + fromAcnos + " " + stnDetails);
                        }
                        if (toAccSts == 9) {
                            throw new ApplicationException("Ac No :-> " + toAcno + " is closed");
                        }
//                        if ((fromBalance + fromOdlimit) <= amount) {
//                            throw new ApplicationException("Insufficient Fund In : " + fromAcnos);
//                        }

                        float trsNumber = fTSPosting43CBSBean.getTrsNo();
                        float RECNO = 0;
                        float tokenNoDr = 0;

                        List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
                        Vector element = (Vector) list.get(0);
                        String branchName = element.get(0).toString();
                        String fromDetail = "SI trf to " + toAcno + " at branch " + branchName;
                        String toDetail = "SI trf from " + fromAcnos + " at branch " + branchName;
                        String crFtsPosting = "";
                        String drFtsPosting = "";

                        int crTranDesc = 0;
                        if ((toAcctNature.equals("RD")) || (toAcctNature.equals("DL")) || (toAcctNature.equals("TL"))) {
                            crTranDesc = 1;
                        }

                        String result = "";
                        if (BRCODE.equals(toBrCode)) {
                            result = fTSPosting43CBSBean.ftsPosting43CBS(fromAcnos, 2, 1, amount, Tempbd, Tempbd, user, fromBrCode, toBrCode, 0, fromDetail, trsNumber, RECNO, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "", "");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }

                            result = fTSPosting43CBSBean.ftsPosting43CBS(toAcno, 2, 0, amount, Tempbd, Tempbd, user, fromBrCode, toBrCode, crTranDesc, toDetail, trsNumber, RECNO, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "", "S");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                            if (toAcctNature.equals(CbsConstant.TERM_LOAN) || toAcctNature.equals(CbsConstant.DEMAND_LOAN) || toAcctNature.equals(CbsConstant.CURRENT_AC)) {
                                /* Dont remove this code and remove comment after confirmation from basti*/
                                result = fTSPosting43CBSBean.npaRecoveryUpdation(trsNumber, toAcctNature, toAcno, Tempbd, amount, BRCODE, toBrCode, user);
                                if (!result.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(result.substring(4));
                                }
                            }
                        } else {
                            result = interBranchFacade.cbsPostingCx(fromAcnos, 1, Tempbd, amount, 0f, 2, fromDetail, 0f, "20", "", null, 3, null, RECNO, 0, fromBrCode, BRCODE, user, "SYSTEM", trsNumber, "", "");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }

                            result = interBranchFacade.cbsPostingSx(toAcno, 0, Tempbd, amount, 0f, 2, toDetail, 0f, "20", "", null, 3, null, RECNO, 0, toBrCode, BRCODE, user, "SYSTEM", trsNumber, "", "");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }

                            result = updateBalances(fromAccNature, fromAcnos, amount, trsNumber, BRCODE);
                            if (!result.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                        }

                        Query insertQueryp = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,Effdate,"
                                + "processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcnos + "','" + toAcno + "','" + amount + "','"
                                + remarks + "','" + effDate + "','" + Tempbd + "','" + user + "')");
                        insertQueryp.executeUpdate();

                        Query deleteQuery1 = em.createNativeQuery("Delete from standins_transpending where FromAcno='" + fromAcnos + "' and InstrNo='"
                                + instrNo + "' and Sno='" + sNo + "'");
                        deleteQuery1.executeUpdate();
                        fTSPosting43CBSBean.lastTxnDateUpdation(fromAcnos);
                        fTSPosting43CBSBean.lastTxnDateUpdation(toAcno);
                        //Sms Object Creation--Dr
                        SmsToBatchTo to = new SmsToBatchTo();
                        to.setAcNo(fromAcnos);
                        to.setDrAmt(amount);
                        to.setCrAmt(0d);
                        to.setTranType(2);
                        to.setTy(1);
                        to.setTxnDt(dmy.format(ymd.parse(Tempbd)));
                        to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);

                        smsBatchList.add(to);
                        //Credit
                        to = new SmsToBatchTo();
                        to.setAcNo(toAcno);
                        to.setDrAmt(0d);
                        to.setCrAmt(amount);
                        to.setTranType(2);
                        to.setTy(0);
                        to.setTxnDt(dmy.format(ymd.parse(Tempbd)));
                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);

                        smsBatchList.add(to);
                        //End here                        
                    } else {
                        Query insertQuery = em.createNativeQuery("insert into standins_transcancel(instrno,sno,fromacno,toacno,Amount,remarks,"
                                + "Effdate,closingdate,closedby,status,Closed,expirydt)(select instrno,sno,fromacno,toacno,Amount,remarks,"
                                + "Effdate,'" + Tempbd + "','" + user + "',status,'Y',expirydt from standins_transpending st,accountmaster ac "
                                + "where st.FROMACNO=ac.acno and ac.curbrcode='" + BRCODE + "' and instrno='" + instrNo + "' and sno='" + sNo + "')");
                        int rs = insertQuery.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("SI : Problem in data insertion in standins_transcancel");
                        }
                        Query deleteQuery = em.createNativeQuery("delete from standins_transpending where fromacno='" + fromAcnos + "' and instrno='"
                                + instrNo + "' and sno='" + sNo + "'");
                        rs = deleteQuery.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("SI : Problem in data deletion from standins_transpending");
                        }
                    }
                    ut.commit();
                } catch (Exception e) {
                    ut.rollback();
                    try {
                        ut.begin();
                        Query updateQuery2 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='" + e.getMessage()
                                + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
                        int rs = updateQuery2.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("SI : Problem in data updation from standins_transpending");
                        }

                        List report = em.createNativeQuery("select INSTRNO,SNO,FROMACNO,TOACNO,AMOUNT,DATE_FORMAT(EFFDATE,'%d/%m/%Y') AS EFFDATE,REMARKS,ERRORMSG "
                                + "from standins_transpending where FROMACNO='" + fromAcnos + "' and TOACNO='" + toAcno + "' and SNO=" + sNo + " and INSTRNO='"
                                + instrNo + "' and REMARKS='" + remarks + "' and AMOUNT=" + amount + " and EFFDATE='" + effDate + "' and processdate <= '"
                                + Tempbd + "' order by INSTRNO,SNO").getResultList();
                        select1.add(report);

                        ut.commit();
                    } catch (Exception ex) {
                        ut.rollback();
                        System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + ex.getMessage());
                    }
                    System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
                }
            }
            //Sending Sms
            try {
                if (!smsBatchList.isEmpty()) {
                    System.out.println("SI Sms Size is--->" + smsBatchList.size());
                    smsFacade.sendSmsToBatch(smsBatchList);
                }
            } catch (Exception e) {
                System.out.println("Problem In Sending SMS To In SI Posting." + e.getMessage());
            }
            //End here            
        } catch (Exception e) {
            System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
        }
        return msg1 + "@ " + select1.toString();
    }

//    public String postButton(String user, String BRCODE) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
//        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            ut.begin();
//            float instrNo = 0;
//            int sNo = 0;
//            String fromAcnos = "";
//            String toAcno = "";
//            double amount = 0d;
//            String effDate = "";
//            String remarks = "";
//            String expiryDt = "";
//            String StnDetails = "";
//            List select1 = new ArrayList();
//            String msg1 = "Instructions Posted Successfully  And Confirm From Today's Error Report For Details ";
//
//            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "'").getResultList();
//            Vector tempCurrent = (Vector) tempBd.get(0);
//            String Tempbd = tempCurrent.get(0).toString();
//            int Tempbds = Integer.parseInt(Tempbd);
//
//            List select = em.createNativeQuery("select instrno,sno,fromacno,toacno,amount,effdate,remarks,expirydt from "
//                    + "standins_transpending st,accountmaster ac where st.fromacno=ac.acno and ac.curbrcode='" + BRCODE
//                    + "' and processdate <= '" + Tempbd + "' order by instrno,sno").getResultList();
//            if (select.isEmpty()) {
//                throw new ApplicationException("Problem in data insertion");
//            }
//
//            List fromAcno = em.createNativeQuery("select fromacno from standins_transpending st,accountmaster ac where st.fromacno=ac.acno "
//                    + "and ac.curbrcode='" + BRCODE + "' and processdate <= '" + Tempbd + "'").getResultList();
//            if (fromAcno.isEmpty()) {
//                throw new ApplicationException("No Instructions Pending For Today ");
//            }
//            List arraylist = new ArrayList();
//            for (int i = 0; i < select.size(); i++) {
//                Vector ele = (Vector) select.get(i);
//                instrNo = (Float.parseFloat(ele.get(0).toString()));
//                sNo = (Integer.parseInt(ele.get(1).toString()));
//                fromAcnos = (ele.get(2).toString());
//                toAcno = (ele.get(3).toString());
//                amount = (Double.parseDouble(ele.get(4).toString()));
//                String effDt = (ele.get(5).toString());
//                String yy = effDt.substring(0, 4);
//                String mm = effDt.substring(5, 7);
//                String dd = effDt.substring(8, 10);
//                effDate = yy + "" + mm + "" + dd;
//
//                remarks = (ele.get(6).toString());
//                String expiryDate = (ele.get(7).toString());
//                String yy1 = expiryDate.substring(0, 4);
//                String mm1 = expiryDate.substring(5, 7);
//                String dd1 = expiryDate.substring(8, 10);
//                expiryDt = yy1 + "" + mm1 + "" + dd1;
//                int expDt = Integer.parseInt(expiryDt);
//                arraylist.add(expDt);
//                if (expDt < Tempbds) {
//                    Query InsertQuery = em.createNativeQuery("insert into standins_transcancel(instrno,sno,fromacno,toacno,Amount,remarks,"
//                            + "Effdate,closingdate,closedby,status,Closed,expirydt)(select instrno,sno,fromacno,toacno,Amount,remarks,"
//                            + "Effdate,'" + Tempbd + "','" + user + "',status,'Y',expirydt from standins_transmaster st,accountmaster ac "
//                            + "where st.FROMACNO=ac.acno and ac.curbrcode='" + BRCODE + "' and instrno='" + instrNo + "' and sno='" + sNo + "')");
//                    InsertQuery.executeUpdate();
//
//                    Query deleteQuery = em.createNativeQuery("delete from standins_transmaster where fromacno='" + fromAcnos + "' and instrno='"
//                            + instrNo + "' and sno='" + sNo + "'");
//                    deleteQuery.executeUpdate();
//                }
//                int fromAccSts = 0;
//                int toAccSts = 0;
//                double fromOdlimit = 0d;
//                double fromBalance = 0d;
//                List accTypeFrom = null;
//                List accTypeTo = null;
//
//                String fromAccNature = fTSPosting43CBSBean.getAccountNature(fromAcnos);
//                String fromBrCode = fTSPosting43CBSBean.getCurrentBrnCode(fromAcnos);
//                String toAcctNature = fTSPosting43CBSBean.getAccountNature(toAcno);
//                String toBrCode = fTSPosting43CBSBean.getCurrentBrnCode(toAcno);
//
//                if ((fromAccNature.equals(CbsConstant.PAY_ORDER))) {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                } else if (fromAccNature.equals(CbsConstant.FIXED_AC) || (fromAccNature.equals(CbsConstant.MS_AC))) {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                } else if (fromAccNature.equals(CbsConstant.CURRENT_AC) || (fromAccNature.equals(CbsConstant.CC_AC))) {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,ca_reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                } else {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                }
//                if (!accTypeFrom.isEmpty()) {
//                    Vector accAllValues = (Vector) accTypeFrom.get(0);
//                    String statusValue = accAllValues.get(1).toString();
//                    String balanceValue = accAllValues.get(2).toString();
//                    String limitValue = accAllValues.get(3).toString();
//                    fromAccSts = Integer.parseInt(statusValue);
//                    if (fromAccSts == 9) {
//                        StnDetails = "Account Has been Closed";
//                    }
//                    if (fromAccSts == 10) {
//                        StnDetails = "Lien Marked";
//                    }
//                    if (fromAccSts == 8) {
//                        StnDetails = " Operation Stopped";
//                    }
//                    if (fromAccSts == 7) {
//                        StnDetails = " Withdrawal Stopped";
//                    }
//                    fromBalance = Double.parseDouble(balanceValue);
//                    fromOdlimit = Double.parseDouble(limitValue);
//                }
//                if ((toAcctNature.equals(CbsConstant.PAY_ORDER))) {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                } else if (toAcctNature.equals(CbsConstant.FIXED_AC) || (toAcctNature.equals(CbsConstant.MS_AC))) {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//
//                } else if (toAcctNature.equals(CbsConstant.CURRENT_AC)) {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,ca_reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                } else {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                }
//                if (!accTypeTo.isEmpty()) {
//                    Vector accToValues = (Vector) accTypeTo.get(0);
//                    String statusValue = accToValues.get(1).toString();
//                    toAccSts = Integer.parseInt(statusValue);
//                }
//                String a = "";
//                String FreezeAcFrom = "N";
//                String FreezeAcTo = "N";
//                if ((fromAccSts == 9) && (toAccSts == 9)) {
//                    a = "Both A/C s  -> " + fromAcnos + " And " + toAcno + " Are Closed ";
//                    Query updateQuery1 = em.createNativeQuery("Update standins_transpending  set processdate='" + Tempbd + "',errormsg='" + a + "',PROCESSEDBY='"
//                            + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                    updateQuery1.executeUpdate();
//                } else if ((fromAccSts == 9) || (toAccSts == 9) || (fromAccSts == 10) || (fromAccSts == 7) || (fromAccSts == 8)) {
//                    if ((fromAccSts == 9) || (fromAccSts == 10) || (fromAccSts == 7) || (fromAccSts == 8)) {
//                        a = "Ac No :-> " + fromAcnos + " " + StnDetails;
//                    } else if (toAccSts == 9) {
//                        a = "Ac No :-> " + toAcno + " " + StnDetails;
//                    }
//                    Query updateQuery2 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='" + a
//                            + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                    updateQuery2.executeUpdate();
//                } else if ((fromBalance + fromOdlimit) <= amount) {
//                    a = "Insuff. Fund In : " + fromAcnos;
//                    Query updateQuery3 = em.createNativeQuery("Update standins_transpending  set processdate='" + Tempbd + "',errormsg='"
//                            + a + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                    updateQuery3.executeUpdate();
//                } else if ((FreezeAcFrom.equals("Y")) && (FreezeAcTo.equals("Y"))) {
//                    a = "Both A/C s  -> " + fromAcnos + " And " + toAcno + " Are Freezed ";
//                    Query updateQuery4 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='"
//                            + a + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                    updateQuery4.executeUpdate();
//                } else if ((FreezeAcFrom.equals("Y")) || (FreezeAcTo.equals("Y"))) {
//                    if (FreezeAcFrom.equals("Y")) {
//                        a = "Ac No :-> " + fromAcnos + "  Freezed";
//                    } else if (FreezeAcTo.equals("Y")) {
//                        a = "Ac No :-> " + toAcno + "  Freezed";
//                    }
//                    Query updateQuery5 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='" + a
//                            + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                    updateQuery5.executeUpdate();
//                } else {
//                    float trsNumber = fTSPosting43CBSBean.getTrsNo();
//                    float RECNO = 0;
//                    float tokenNoDr = 0;
//
//                    List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
//                    Vector element = (Vector) list.get(0);
//                    String branchName = element.get(0).toString();
//                    String fromDetail = "SI trf to " + toAcno + " at branch " + branchName;
//                    String toDetail = "SI trf from " + fromAcnos + " at branch " + branchName;
//                    String crFtsPosting = "";
//                    String drFtsPosting = "";
//
//                    int crTranDesc = 0;
//                    if ((toAcctNature.equals("RD")) || (toAcctNature.equals("DL")) || (toAcctNature.equals("TL"))) {
//                        crTranDesc = 1;
//                    }
//                    if (BRCODE.equals(toBrCode)) {
//                        drFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(fromAcnos, 2, 1, amount, Tempbd, Tempbd, user, fromBrCode, toBrCode, 0, fromDetail, trsNumber, RECNO, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "");
//                        if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//
//                            crFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(toAcno, 2, 0, amount, Tempbd, Tempbd, user, fromBrCode, toBrCode, crTranDesc, toDetail, trsNumber, RECNO, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "");
//
//                            if (toAcctNature.equals(CbsConstant.TERM_LOAN) || toAcctNature.equals(CbsConstant.DEMAND_LOAN) || toAcctNature.equals(CbsConstant.CURRENT_AC)) {
//                                /* Dont remove this code and remove comment after confirmation from basti*/
//                                String msg = fTSPosting43CBSBean.npaRecoveryUpdation(trsNumber, toAcctNature, toAcno, Tempbd, amount, BRCODE, toBrCode, user);
//                                if (!msg.equalsIgnoreCase("True")) {
//                                    ut.rollback();
//                                    throw new ApplicationException(msg);
//                                }
//                            }
//                        }
//                    } else {
//                        drFtsPosting = interBranchFacade.cbsPostingCx(fromAcnos, 1, Tempbd, amount, 0f, 2, fromDetail, 0f, "20", "", null, 3, null, RECNO, 0, fromBrCode, BRCODE, user, "SYSTEM", trsNumber, "", "");
//                        if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                            crFtsPosting = interBranchFacade.cbsPostingSx(toAcno, 0, Tempbd, amount, 0f, 2, toDetail, 0f, "20", "", null, 3, null, RECNO, 0, toBrCode, BRCODE, user, "SYSTEM", trsNumber, "", "");
//                            if (crFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                                updateBalances(fromAccNature, fromAcnos, amount, trsNumber, BRCODE);
//                            }
//                        }
//                    }
//                    if ((drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) && (crFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE"))) {
//                        Query insertQueryp = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,Effdate,"
//                                + "processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcnos + "','" + toAcno + "','" + amount + "','"
//                                + remarks + "','" + effDate + "','" + Tempbd + "','" + user + "')");
//                        insertQueryp.executeUpdate();
//
//                        Query deleteQuery1 = em.createNativeQuery("Delete from standins_transpending where FromAcno='" + fromAcnos + "' and InstrNo='"
//                                + instrNo + "' and Sno='" + sNo + "'");
//                        deleteQuery1.executeUpdate();
//
//                        //Sms Object Creation--Dr
//                        SmsToBatchTo to = new SmsToBatchTo();
//                        to.setAcNo(fromAcnos);
//                        to.setDrAmt(amount);
//                        to.setCrAmt(0d);
//                        to.setTranType(2);
//                        to.setTy(1);
//                        to.setTxnDt(dmy.format(ymd.parse(Tempbd)));
//                        to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
//
//                        smsBatchList.add(to);
//                        //Credit
//                        to = new SmsToBatchTo();
//                        to.setAcNo(toAcno);
//                        to.setDrAmt(0d);
//                        to.setCrAmt(amount);
//                        to.setTranType(2);
//                        to.setTy(0);
//                        to.setTxnDt(dmy.format(ymd.parse(Tempbd)));
//                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);
//
//                        smsBatchList.add(to);
//                        //End here
//                    } else {
//                        List report = em.createNativeQuery("select INSTRNO,SNO,FROMACNO,TOACNO,AMOUNT,DATE_FORMAT(EFFDATE,'%d/%m/%Y') AS EFFDATE,REMARKS,"
//                                + "ERRORMSG from standins_transpending where FROMACNO='" + fromAcnos + "' and TOACNO='" + toAcno + "' and SNO=" + sNo
//                                + " and INSTRNO='" + instrNo + "' and REMARKS='" + remarks + "' and AMOUNT=" + amount + " and EFFDATE='" + effDate
//                                + "' and processdate <= '" + Tempbd + "' order by INSTRNO,SNO").getResultList();
//                        select1.add(report);
//                    }
//                }
//                List report = em.createNativeQuery("select INSTRNO,SNO,FROMACNO,TOACNO,AMOUNT,DATE_FORMAT(EFFDATE,'%d/%m/%Y') AS EFFDATE,REMARKS,ERRORMSG "
//                        + "from standins_transpending where FROMACNO='" + fromAcnos + "' and TOACNO='" + toAcno + "' and SNO=" + sNo + " and INSTRNO='"
//                        + instrNo + "' and REMARKS='" + remarks + "' and AMOUNT=" + amount + " and EFFDATE='" + effDate + "' and processdate <= '"
//                        + Tempbd + "' order by INSTRNO,SNO").getResultList();
//                select1.add(report);
//            }
//            ut.commit();
//            //Sending Sms
//            try {
//                if (!smsBatchList.isEmpty()) {
//                    System.out.println("SI Sms Size is--->" + smsBatchList.size());
//                    smsFacade.sendSmsToBatch(smsBatchList);
//                }
//            } catch (Exception e) {
//                System.out.println("Problem In Sending SMS To In SI Posting." + e.getMessage());
//            }
//            //End here
//            return msg1 + "@ " + select1.toString();
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(e.getMessage());
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//    }
    public String updateBalances(String acctNature, String acno, double amt, float trsNumber, String BRCODE) throws ApplicationException {
        try {
            Query deleteQuery6 = em.createNativeQuery("Delete From recon_trf_d where substring(ACNO,1,2)='" + BRCODE + "' and trsno=" + trsNumber + "");
            deleteQuery6.executeUpdate();

            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.SS_AC) || acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {

                Query updateQuery1A = em.createNativeQuery("UPDATE reconbalan rc,accountmaster ac SET rc.Balance =rc.Balance -" + amt + "  where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + BRCODE + "'");
                updateQuery1A.executeUpdate();

            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

//                Query updateQuery1A = em.createNativeQuery("UPDATE ca_reconbalan SET Balance =Balance -" + amt + " from ca_reconbalan rc,accountmaster ac where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + BRCODE + "'");
                Query updateQuery1A = em.createNativeQuery("UPDATE ca_reconbalan rc,accountmaster ac SET rc.Balance =rc.Balance -" + amt + "  where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + BRCODE + "'");
                updateQuery1A.executeUpdate();

            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {

//                Query updateQuery1A = em.createNativeQuery("UPDATE td_reconbalan SET Balance =Balance -" + amt + " from td_reconbalan rc,td_accountmaster ac where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + BRCODE + "'");
                Query updateQuery1A = em.createNativeQuery("UPDATE td_reconbalan rc,td_accountmaster ac SET rc.Balance =rc.Balance -" + amt + "  where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + BRCODE + "'");
                updateQuery1A.executeUpdate();
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List tableData(String insType, String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            if (insType.equals("TRANSACTION")) {
                List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
                Vector tempCurrent = (Vector) tempBd.get(0);
                String Tempbd = tempCurrent.get(0).toString();

                Query selectQuery = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,DATE_FORMAT(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,DATE_FORMAT(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,DATE_FORMAT(expirydt,'%d/%m/%Y') AS expirydt from standins_transmaster st,accountmaster ac where st.FROMACNO=acno and ac.curbrcode='" + brCode + "' and Effdate <= '" + Tempbd + "' and status='UNEXECUTED' order by INSTRNO,SNO");
                tableResult = selectQuery.getResultList();
            } else if (insType.equals("GENERAL")) {
                List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
                Vector tempCurrent = (Vector) tempBd.get(0);
                String Tempbd = tempCurrent.get(0).toString();

                Query selectQuery1 = em.createNativeQuery("select st.acno,sno,instrno,DATE_FORMAT(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,DATE_FORMAT(entrydate,'%d/%m/%Y') AS entrydate from standins_generalmaster st,accountmaster ac where st.ACNO=ac.acno and ac.curbrcode='" + brCode + "' and Effdate <= '" + Tempbd + "' and status='UNEXECUTED' order by INSTRNO,SNO");
                tableResult = selectQuery1.getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return tableResult;
    }

    public String postButton(String user, String brCode, String insType, List generalGrid) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
            List reportList = new ArrayList();
            String msg = "Instructions Posted Successfully  And Confirm From Today's Error Report For Details ";

            int thLmtParam = 0;
            List chParamThresh = fTSPosting43CBSBean.getThreshLmtParam();
            if (!(chParamThresh == null || chParamThresh.isEmpty())) {
                Vector verLst = (Vector) chParamThresh.get(0);
                thLmtParam = Integer.parseInt(verLst.get(0).toString());
            }

            List tempBdList = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBdList.get(0);
            String bankDt = tempCurrent.get(0).toString();
            if (insType.equals("TRANSACTION")) {
                List select = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,effdate,status,remarks,enterby,entrydate,chargeflag,"
                        + "expirydt from standins_transmaster ,accountmaster where fromacno=acno and curbrcode='" + brCode + "' and Effdate <='" + bankDt
                        + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
                if (select.isEmpty()) {
                    //ut.rollback();
                    return "table is empty ";
                }
                for (int i = 0; i < select.size(); i++) {
                    try {
                        ut.begin();
                        Vector ele = (Vector) select.get(i);
                        String fromAcNo = (ele.get(0).toString());
                        String toAcno = (ele.get(1).toString());
                        int sNo = (Integer.parseInt(ele.get(2).toString()));
                        float instrNo = (Float.parseFloat(ele.get(3).toString()));
                        double amount = (Double.parseDouble(ele.get(4).toString()));
                        String effDt = (ele.get(5).toString());
                        String effDate = effDt.substring(0, 4) + effDt.substring(5, 7) + effDt.substring(8, 10);

                        String remarks = (ele.get(7).toString());
                        String expiryDate = (ele.get(11).toString());
                        String expiryDt = expiryDate.substring(0, 4) + expiryDate.substring(5, 7) + expiryDate.substring(8, 10);

                        String fromAccNature = fTSPosting43CBSBean.getAccountNature(fromAcNo);
                        String fromBrCode = fTSPosting43CBSBean.getCurrentBrnCode(fromAcNo);
                        String toAcctNature = fTSPosting43CBSBean.getAccountNature(toAcno);
                        String toBrCode = fTSPosting43CBSBean.getCurrentBrnCode(toAcno);
                        List accTypeFrom = null;
                        if ((fromAccNature.equals(CbsConstant.PAY_ORDER))) {
                            accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  "
                                    + "a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
                        } else if (fromAccNature.equals(CbsConstant.FIXED_AC) || (fromAccNature.equals(CbsConstant.MS_AC))) {
                            accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r "
                                    + "where  a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
                        } else if (fromAccNature.equals(CbsConstant.CURRENT_AC)) {
                            accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,ca_reconbalan r "
                                    + "where  a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
                        } else {
                            accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,reconbalan r "
                                    + "where  a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
                        }
                        int fromAccSts = 0;
                        String StnDetails = "";
                        double fromBalance = 0d;
                        double fromOdlimit = 0d;
                        if (!accTypeFrom.isEmpty()) {
                            Vector accAllValues = (Vector) accTypeFrom.get(0);
                            String statusValue = accAllValues.get(1).toString();

                            String balanceValue = accAllValues.get(2).toString();
                            String limitValue = accAllValues.get(3).toString();
                            fromAccSts = Integer.parseInt(statusValue);

                            if (fromAccSts == 9) {
                                StnDetails = "Account Has been Closed";
                            }
                            if (fromAccSts == 10) {
                                StnDetails = "Lien Marked";
                            }
                            if (fromAccSts == 8) {
                                StnDetails = " Operation Stopped";
                            }
                            if (fromAccSts == 7) {
                                StnDetails = " Withdrawal Stopped";
                            }
                            fromBalance = Double.parseDouble(balanceValue);
                            fromOdlimit = Double.parseDouble(limitValue);
                        }
                        List accTypeTo = null;
                        if ((toAcctNature.equals(CbsConstant.PAY_ORDER))) {
                            accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='"
                                    + toAcno + "' and a.acno=r.acno").getResultList();
                        } else if (toAcctNature.equals(CbsConstant.FIXED_AC) || (toAcctNature.equals(CbsConstant.MS_AC))) {
                            accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r "
                                    + "where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();

                        } else if (toAcctNature.equals(CbsConstant.CURRENT_AC)) {
                            accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,ca_reconbalan r"
                                    + " where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
                        } else {
                            accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,reconbalan r where  "
                                    + "a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
                        }
                        int toAccSts = 0;
                        if (!accTypeTo.isEmpty()) {
                            Vector accToValues = (Vector) accTypeTo.get(0);
                            String statusValue = accToValues.get(1).toString();
                            toAccSts = Integer.parseInt(statusValue);
                        }
                        int ToAcFlag = 1;
                        ToAcFlag = toAccSts;
                        String a = null;
                        // String freezeAcFrom = "N";
                        // String freezeAcTo = "N";

                        /**
                         * Code Added For ThreshHold Limit Checking *
                         */
                        String chkThresh = "";
                        if (toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            if (thLmtParam == 1) {
                                chkThresh = fTSPosting43CBSBean.isThreshLmtExceed(toAcno, amount, ymmd.format(ymd.parse(bankDt)));
                            }
                        }

                        if ((fromAccSts == 9) && (ToAcFlag == 9)) {
                            a = "Both A/C s  -> " + fromAcNo + " And " + toAcno + " Are Closed ";
                            Query insertQuery = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
                                    + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + a + "'" + "," + "'" + expiryDt + "'" + ")");
                            insertQuery.executeUpdate();
                            Query deleteQuery = em.createNativeQuery("Delete From standins_transmaster where substring(FROMACNO,1,2)='" + brCode + "' and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                            deleteQuery.executeUpdate();
                            ut.commit();
                        } else if ((fromAccSts == 9) || (ToAcFlag == 9) || (fromAccSts == 10) || (fromAccSts == 8) || (fromAccSts == 7)) {
                            if ((fromAccSts == 9) || (fromAccSts == 10) || (fromAccSts == 8) || (fromAccSts == 7)) {
                                a = "Ac No :-> " + fromAcNo + "" + StnDetails;
                            } else if ((ToAcFlag == 9) || (ToAcFlag == 9)) {
                                a = "Ac No :-> " + toAcno + "" + StnDetails;
                            }
                            Query insertQuery1 = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
                                    + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + a + "'" + "," + "'" + expiryDt + "'" + ")");
                            insertQuery1.executeUpdate();
                            Query deleteQuery1 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                            deleteQuery1.executeUpdate();
                            ut.commit();
                        } //else if ((fromBalance + fromOdlimit) <= amount) {
                        else if (!fTSPosting43CBSBean.checkBalance(fromAcNo, amount, user).equalsIgnoreCase("True")){
                            a = "Insuff. Fund In : " + fromAcNo;
                            Query insertQuery2 = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
                                    + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + a + "'" + "," + "'" + expiryDt + "'" + ")");
                            insertQuery2.executeUpdate();

                            Query deleteQuery2 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                            deleteQuery2.executeUpdate();
                            ut.commit();
                        } else if ((toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && !chkThresh.equalsIgnoreCase("true")) {
                            Query insertQuery2 = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
                                    + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + chkThresh + "'" + "," + "'" + expiryDt + "'" + ")");
                            insertQuery2.executeUpdate();

                            Query deleteQuery2 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                            deleteQuery2.executeUpdate();
                            ut.commit();
                        } else {
                            float trsNo = fTSPosting43CBSBean.getTrsNo();
                            float recNo = 0;
                            float tokenNoDr = 0;
                            String crFtsPosting = "";
                            String drFtsPosting = "";

                            String branchName = "";
                            List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
                            Vector element = (Vector) list.get(0);
                            branchName = element.get(0).toString();

                            String fromDetail = "SI trf to " + toAcno + " at branch " + branchName;
                            String toDetail = "SI trf from " + fromAcNo + " at branch " + branchName;
                            int crTranDesc = 0;
                            if ((toAcctNature.equals("RD")) || (toAcctNature.equals("DL")) || (toAcctNature.equals("TL"))) {
                                crTranDesc = 1;
                            }

                            if (brCode.equals(toBrCode)) {
                                drFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(fromAcNo, 2, 1, amount, bankDt, bankDt, user, fromBrCode, fromBrCode, 0,
                                        fromDetail, trsNo, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr,
                                        "N", "", "", "S");
                                if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                    crFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(toAcno, 2, 0, amount, bankDt, bankDt, user, fromBrCode, fromBrCode,
                                            crTranDesc, toDetail, trsNo, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null,
                                            tokenNoDr, "N", "", "", "S");
                                    if (toAcctNature.equals(CbsConstant.TERM_LOAN) || toAcctNature.equals(CbsConstant.DEMAND_LOAN) || toAcctNature.equals(CbsConstant.CURRENT_AC)) {
                                        /* Dont remove this code and remove comment after confirmation from basti*/
                                        msg = fTSPosting43CBSBean.npaRecoveryUpdation(trsNo, toAcctNature, toAcno, bankDt, amount, brCode, toBrCode, user);
                                        if (!msg.equalsIgnoreCase("True")) {
                                            ut.rollback();
                                            throw new ApplicationException(msg);
                                        }
                                    }
                                }
                            } else {
                                drFtsPosting = interBranchFacade.cbsPostingCx(fromAcNo, 1, bankDt, amount, 0f, 2, fromDetail, 0f, "20", "", null,
                                        3, null, recNo, 0, fromBrCode, brCode, user, "SYSTEM", trsNo, "", "");
                                if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                    String rs = fTSPosting43CBSBean.updateBalance(fromAccNature, fromAcNo, 0, amount, "", "");
                                    if (rs.equalsIgnoreCase("True")) {
                                        crFtsPosting = interBranchFacade.cbsPostingSx(toAcno, 0, bankDt, amount, 0f, 2, toDetail, 0f, "20", "", null,
                                                3, null, recNo, crTranDesc, toBrCode, brCode, user, "SYSTEM", trsNo, "", "");
                                    }
                                }
                            }
                            if ((drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) && (crFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                                Query insertQuery6 = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,"
                                        + "Effdate,processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcNo + "','" + toAcno + "','"
                                        + amount + "','" + remarks + "','" + effDate + "','" + bankDt + "','" + user + "')");
                                insertQuery6.executeUpdate();

//                            Query deleteQuery6 = em.createNativeQuery("Delete standins_transmaster from standins_transmaster st,accountmaster ac where "
//                                    + "st.FROMacno=ac.acno and ac.curbrcode='"+brCode+"'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo 
//                                    + "' and Sno='" + sNo + "'");
                                Query deleteQuery6 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno "
                                        + "and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                                deleteQuery6.executeUpdate();
                                fTSPosting43CBSBean.lastTxnDateUpdation(fromAcNo);
                                fTSPosting43CBSBean.lastTxnDateUpdation(toAcno);
                                //Sms Object Creation--Dr
                                SmsToBatchTo to = new SmsToBatchTo();
                                to.setAcNo(fromAcNo);
                                to.setDrAmt(amount);
                                to.setCrAmt(0d);
                                to.setTranType(2);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(ymd.parse(bankDt)));
                                to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);

                                smsBatchList.add(to);
                                //Credit
                                to = new SmsToBatchTo();
                                to.setAcNo(toAcno);
                                to.setDrAmt(0d);
                                to.setCrAmt(amount);
                                to.setTranType(2);
                                to.setTy(0);
                                to.setTxnDt(dmy.format(ymd.parse(bankDt)));
                                to.setTemplate(SmsType.TRANSFER_DEPOSIT);

                                smsBatchList.add(to);
                                //End here
                                ut.commit();
                            } else if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                List report = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,date_format(effdate,'%d/%m/%Y') AS effdate,"
                                        + "status,remarks,enterby,DATE_FORMAT(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,date_format(expirydt,'%d/%m/%Y') AS expirydt "
                                        + "from standins_transmaster where FROMACNO='" + fromAcNo + "' and toacno='" + toAcno + "' and sno=" + sNo
                                        + " and Amount=" + amount + " and Effdate <= '" + bankDt + "' And instrno='" + instrNo + "' And remarks='"
                                        + remarks + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
                                reportList.add(report);
                                ut.rollback();
                            } else {
                                List report = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,date_format(effdate,'%d/%m/%Y') AS effdate,"
                                        + "status,remarks,enterby,date_format(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,date_format(expirydt,'%d/%m/%Y') AS expirydt "
                                        + "from standins_transmaster where fromacno='" + fromAcNo + "' and toacno='" + toAcno + "' and sno=" + sNo
                                        + " and Amount=" + amount + " and Effdate <= '" + bankDt + "' And instrno='" + instrNo + "' And remarks='"
                                        + remarks + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
                                reportList.add(report);
                                ut.rollback();
                            }
                        }
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            throw new ApplicationException(e.getMessage());
                        } catch (Exception ex) {
                            throw new ApplicationException(ex.getMessage());
                        }
                    }
                }
                //Sending Sms
                try {
                    if (!smsBatchList.isEmpty()) {
                        System.out.println("SI Sms Size is--->" + smsBatchList.size());
                        smsFacade.sendSmsToBatch(smsBatchList);
                    }
                } catch (Exception e) {
                    System.out.println("Problem In Sending SMS To In SI Posting." + e.getMessage());
                }
                //End here
            } else if (insType.equals("GENERAL")) {
                ut.begin();
                try {
                    List genSelect = em.createNativeQuery("select acno,sno,instrno,date_format(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,"
                            + "date_format(entrydate,'%d/%m/%Y') AS entrydate from standins_generalmaster where substring(ACNO,1,2)='" + brCode
                            + "' and Effdate <= '" + bankDt + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
                    if (genSelect.isEmpty()) {
                        ut.rollback();
                        return "";
                    }
                    List genAcNo = em.createNativeQuery("select acno from standins_generalmaster where substring(ACNO,1,2)='" + brCode + "' and Effdate <= '"
                            + bankDt + "' and status='UNEXECUTED'").getResultList();
                    if (genAcNo.isEmpty()) {
                        ut.rollback();
                        return " No General Instructions Pending For Today ";
                    }
                    for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7; a < generalGrid.size(); a = a + 8, b = b + 8, c = c + 8, d = d + 8, e = e + 8, f = f + 8, g = g + 8, h = h + 8) {
                        String acNo = (generalGrid.get(a).toString());
                        Integer sNumber = (Integer.parseInt(generalGrid.get(b).toString()));
                        Float instrNum = (Float.parseFloat(generalGrid.get(c).toString()));
                        String status = (generalGrid.get(e).toString());

                        if (status.equals("EXECUTED")) {
                            Query updateQuery0 = em.createNativeQuery("update standins_generalmaster set st.status='EXECUTED',PROCESSDATE='" + bankDt + "',PROCESSEDBY='" + user + "' from standins_generalmaster st,accountmaster ac where st.acno=ac.acno and ac.curbrcode ='" + brCode + "' and st.acno='" + acNo + "' and INSTRNO=" + instrNum + " and st.SNO=" + sNumber + "  and st.status='UNEXECUTED'");
                            updateQuery0.executeUpdate();
                            ut.commit();
                        }
                    }
                } catch (Exception e) {
                    try {
                        ut.rollback();
                        throw new ApplicationException(e.getMessage());
                    } catch (Exception ex) {
                        throw new ApplicationException(ex.getMessage());
                    }
                }
            }
            return msg + ": " + reportList.toString();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List loadGrdData(String acno, String instype) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (instype.equals("TRANSACTION")) {
                Query selectQuery = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,DATE_FORMAT(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,DATE_FORMAT(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,DATE_FORMAT(expirydt,'%d/%m/%Y') AS expirydt from standins_transmaster where fromacno='" + acno + "' order by InsTrNo,sno");
                list = selectQuery.getResultList();
            } else if (instype.equals("GENERAL")) {
                Query selectQuery = em.createNativeQuery("select acno,sno,instrno,DATE_FORMAT(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,DATE_FORMAT(entrydate,'%d/%m/%Y') AS entrydate from standins_generalmaster where acno='" + acno + "' order by InsTrNo,sno");
                list = selectQuery.getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public String acnoFind(String acno, String instype) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String custName = "";
        try {
            if (instype.equals("TRANSACTION") || instype.equals("GENERAL")) {
                ut.begin();
                List checkList;
                if ((acno == null) || (acno.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Please Enter Account Number !!!.";
                }
                String acctNature = fTSPosting43CBSBean.getAccountNature(acno);
                if ((acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (acctNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    checkList = em.createNativeQuery("select custname,accstatus from td_accountmaster where  acno='" + acno + "'").getResultList();
                    if (checkList.isEmpty()) {
                        ut.rollback();
                        return "Account Does Not Exist!!!.";
                    }
                    Vector secLst = (Vector) checkList.get(0);
                    String secListed = secLst.get(1).toString();
                    int secnum = Integer.parseInt(secListed);
                    if (secnum == 9) {
                        ut.rollback();
                        return "This Account Is Closed !!!.";
                    }
                    Vector secLst1 = (Vector) checkList.get(0);
                    custName = secLst1.get(0).toString();
                } else {
                    checkList = em.createNativeQuery("select custname,accstatus from accountmaster where  acno='" + acno + "'").getResultList();
                    if (checkList.isEmpty()) {
                        ut.rollback();
                        return "Account Does Not Exist!!!.";
                    }
                    Vector secLst = (Vector) checkList.get(0);
                    String secListed = secLst.get(1).toString();
                    int secnum = Integer.parseInt(secListed);
                    if (secnum == 9) {
                        ut.rollback();
                        return "This Account Is Closed !!!.";
                    }
                    Vector secLst1 = (Vector) checkList.get(0);
                    custName = secLst1.get(0).toString();
                }
            }
            ut.commit();
            return custName;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String deleteTransData(String fromAcno, int orgbrCode, float instrNo, String authby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            List list = new ArrayList();
            list = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode=" + orgbrCode + " ").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector recLst = (Vector) list.get(i);
                    String Tempbd = recLst.get(0).toString();
                    Query insertQuery = em.createNativeQuery("insert into standins_transcancel (instrno,sno,fromacno,toacno,Amount,remarks,Effdate,closingdate,closedby,status,Closed)(select instrno,sno,fromacno,toacno,Amount,remarks,Effdate,'" + Tempbd + "','" + authby + "',status,'Y' from standins_transmaster where fromacno = '" + fromAcno + "' and instrno=" + instrNo + " )");
                    int var = insertQuery.executeUpdate();

                    Query delete = em.createNativeQuery("delete from standins_transmaster where fromacno = '" + fromAcno + "' and instrno=" + instrNo + " ");
                    int var1 = delete.executeUpdate();

                    Query delete1 = em.createNativeQuery("delete from standins_transpending where fromacno = '" + fromAcno + "' and instrno=" + instrNo + " ");
                    int var2 = delete1.executeUpdate();
                    if (var > 0 && var1 > 0) {
                        msg = "Data Deleted Successfully All Instructions For -> " + fromAcno + "   Having Instruction No. " + instrNo;
                        ut.commit();
                        return msg;
                    } else if (var > 0 && var2 > 0) {
                        msg = "Data Deleted Successfully All Instructions For -> " + fromAcno + "   Having Instruction No. " + instrNo;
                        ut.commit();
                        return msg;
                    } else {
                        msg = "Problem In Deletion";
                        ut.rollback();
                        return msg;
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }
    int orgbrCode;

    public String deleteTransData1(String fromAcno, int orgbrCode, float instrNo, String authby, int sno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            List list = new ArrayList();
            list = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode=" + orgbrCode + " ").getResultList();
            if (!list.isEmpty()) {
                Vector recLst = (Vector) list.get(0);
                String Tempbd = recLst.get(0).toString();
                Query insertQuery = em.createNativeQuery("insert into standins_transcancel (instrno,sno,fromacno,toacno,Amount,remarks,Effdate,closingdate,closedby,status,Closed)(select instrno,sno,fromacno,toacno,Amount,remarks,Effdate,'" + Tempbd + "','" + authby + "',status,'Y' from standins_transmaster where fromacno = '" + fromAcno + "' and instrno=" + instrNo + " and sno=" + sno + " )");
                int var = insertQuery.executeUpdate();

                Query delete = em.createNativeQuery("delete from standins_transmaster where fromacno = '" + fromAcno + "' and instrno=" + instrNo + "  and sno=" + sno + " and cast(substring(FROMACNO,1,2) as unsigned) = '" + orgbrCode + "'");
                int var1 = delete.executeUpdate();
                if (var > 0 && var1 > 0) {
                    msg = "Data Deleted Successfully Single Instructions For -> " + fromAcno + "   Having Instruction No. " + instrNo + "Having Sno. " + sno;
                    ut.commit();
                    return msg;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public String deleteGenData(float instrno, String dt, String user, int orgbrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            List list = new ArrayList();
            list = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode=" + orgbrCode + " ").getResultList();
            Vector recLst = (Vector) list.get(0);
            String Tempbd = recLst.get(0).toString();
            Query delete = em.createNativeQuery("delete from standins_generalmaster where instrno=" + instrno + " ");
            int var = delete.executeUpdate();
            Query insertQuery = em.createNativeQuery("insert into standins_generalcancel(instrno,sno,acno,remarks,Effdate,closingdate,closedby,status,Closed)(select instrno,sno,acno,remarks,Effdate,'" + dt + "','" + user + "',status,'Y' from standins_generalmaster where instrno=" + instrno + ")");
            int var1 = insertQuery.executeUpdate();
            if (var > 0) {
                msg = "Data Deleted Successfully Having Instruction No." + instrno;
                ut.commit();
                return msg;
            } else {
                msg = "Problem In Deletion";
                ut.rollback();
                return msg;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
//    public String siAutoCoverPost(String dt) throws ApplicationException {
//        //UserTransaction ut = context.getUserTransaction();
//        String msg = "true";
//        List<List> resultLt = new ArrayList<List>();
//        List<SmsToBatchTo> smsBatchSiAutoList = new ArrayList<SmsToBatchTo>();
//        List<SmsToBatchTo> smsBatchSiPendingList = new ArrayList<SmsToBatchTo>();
//        try {
//            List autoSIList = em.createNativeQuery("select code from parameterinfo_report where reportname='SI-AUTO'").getResultList();
//            if (!autoSIList.isEmpty()) {
//                Vector autoSIVector = (Vector) autoSIList.get(0);
//                int code = Integer.parseInt(autoSIVector.get(0).toString());
//                if (code == 1) {
//                    List brnList = em.createNativeQuery("select BrnCode from branchmaster").getResultList();
//                    if (brnList.isEmpty()) {
//                        return "Branch code is not defined";
//                    }
//                    //ut.begin();
//                    for (int a = 0; a < brnList.size(); a++) {
//                        Vector brnListVector = (Vector) brnList.get(a);
//                        String brncode = brnListVector.get(0).toString();
//                        if (brncode.length() < 2) {
//                            brncode = "0" + brncode;
//                        }
//
//                        resultLt = siAutoPost("SYSTEM", brncode, dt);
//                        List<SmsToBatchTo> tempList = (List<SmsToBatchTo>) resultLt.get(1);
//                        System.out.println("Branch Code Is-->" + brncode + " And Sms Auto List Size-->" + tempList.size() + "\n");
//                        for (SmsToBatchTo to : tempList) {
//                            smsBatchSiAutoList.add(to);
//                        }
////                        if(!resultLt.isEmpty()){
////                            List<SIAutoPostingTxnGrid> instPost = new ArrayList<SIAutoPostingTxnGrid>();
////                            for (int i = 0; i < resultLt.size(); i++) {
////                                Vector ele = (Vector) resultLt.get(i);
////                                SIAutoPostingTxnGrid post = new SIAutoPostingTxnGrid();
////
////                                post.setInstrNo(Float.parseFloat(ele.get(3).toString()));
////                                post.setsNo(Integer.parseInt(ele.get(2).toString()));
////                                post.setFromAcno(ele.get(0).toString());
////                                post.setFromCustName(getCustomerName(ele.get(0).toString()));
////                                post.setToAcno(ele.get(1).toString());
////                                post.setToCustName(getCustomerName(ele.get(1).toString()));
////                                post.setAmount(Float.parseFloat(ele.get(4).toString()));
////                                post.setEffDate(ele.get(5).toString());
////                                post.setRemarks(ele.get(6).toString());
////                                post.setExpiryDt(ele.get(7).toString());
////                                    
////                                instPost.add(post);
////                            }                        
////                        }
//
//                        resultLt = siAutoPendingPost("SYSTEM", brncode, dt);
//                        tempList = (List<SmsToBatchTo>) resultLt.get(1);
//                        System.out.println("Branch Code Is-->" + brncode + " And Sms Auto Pending List Size-->" + tempList.size() + "\n");
//                        for (SmsToBatchTo to : tempList) {
//                            smsBatchSiPendingList.add(to);
//                        }
////                        if(!resultLt.isEmpty()){
////                            List<SIAutoPendingGrid> instPending = new ArrayList<SIAutoPendingGrid>();
////                            for (int i = 0; i < resultLt.size(); i++) {
////                                Vector ele = (Vector) resultLt.get(i);
////                                SIAutoPendingGrid pending = new SIAutoPendingGrid();
////                                        
////                                pending.setInstrNo(Float.parseFloat(ele.get(3).toString()));
////                                pending.setsNo(Integer.parseInt(ele.get(2).toString()));
////                                pending.setFromAcno(ele.get(0).toString());
////                                pending.setFromCustName(getCustomerName(ele.get(0).toString()));
////                                pending.setToAcno(ele.get(1).toString());
////                                pending.setToCustName(getCustomerName(ele.get(1).toString()));
////                                pending.setAmount(Float.parseFloat(ele.get(4).toString()));
////                                pending.setEffDate(ele.get(5).toString());
////                                pending.setRemarks(ele.get(6).toString());
////                                pending.setExpiryDt(ele.get(7).toString());
////                                    
////                                instPending.add(pending);
////                            }
////                        }
//                    }
//                    //ut.commit();
//                }
//            }
//            //Sending Sms
//            try {
//                if (!smsBatchSiAutoList.isEmpty()) {
//                    System.out.println("SI Auto Sms Size is--->" + smsBatchSiAutoList.size());
//                    smsFacade.sendSmsToBatch(smsBatchSiAutoList);
//                }
//                if (!smsBatchSiPendingList.isEmpty()) {
//                    System.out.println("SI Pending Sms Size is--->" + smsBatchSiPendingList.size());
//                    smsFacade.sendSmsToBatch(smsBatchSiPendingList);
//                }
//            } catch (Exception e) {
//                System.out.println("Problem In Sending SMS To In SI Posting." + e.getMessage());
//            }
//            //End here
//            return msg;
//        } catch (Exception e) {
//            try {
//                //ut.rollback();
//                throw new ApplicationException(e.getMessage());
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//    }
//
//    public List<List> siAutoPendingPost(String user, String BRCODE, String Tempbd) throws ApplicationException {
//        List<List> returnList = new ArrayList<List>();
//        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
//        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            float instrNo = 0;
//            int sNo = 0;
//            String fromAcnos = "";
//            String toAcno = "";
//            double amount = 0d;
//            String effDate = "";
//            String remarks = "";
//            String expiryDt = "";
//            String StnDetails = "";
//            List select1 = new ArrayList();
//            int Tempbds = Integer.parseInt(Tempbd);
//            int thLmtParam = 0;
//            List chParamThresh = fTSPosting43CBSBean.getThreshLmtParam();
//            if(!(chParamThresh==null || chParamThresh.isEmpty())){
//                Vector verLst = (Vector) chParamThresh.get(0);
//                thLmtParam = Integer.parseInt(verLst.get(0).toString());
//            }
//
//            List select = em.createNativeQuery("select instrno,sno,fromacno,toacno,amount,effdate,remarks,expirydt from "
//                    + "standins_transpending st,accountmaster ac where st.fromacno=ac.acno and ac.curbrcode='" + BRCODE
//                    + "' and processdate <= '" + Tempbd + "' order by instrno,sno").getResultList();
//
//            List arraylist = new ArrayList();
//            for (int i = 0; i < select.size(); i++) {
//                Vector ele = (Vector) select.get(i);
//                instrNo = (Float.parseFloat(ele.get(0).toString()));
//                sNo = (Integer.parseInt(ele.get(1).toString()));
//                fromAcnos = (ele.get(2).toString());
//                toAcno = (ele.get(3).toString());
//                amount = (Double.parseDouble(ele.get(4).toString()));
//                String effDt = (ele.get(5).toString());
//                String yy = effDt.substring(0, 4);
//                String mm = effDt.substring(5, 7);
//                String dd = effDt.substring(8, 10);
//                effDate = yy + "" + mm + "" + dd;
//
//                remarks = (ele.get(6).toString());
//                String expiryDate = (ele.get(7).toString());
//                String yy1 = expiryDate.substring(0, 4);
//                String mm1 = expiryDate.substring(5, 7);
//                String dd1 = expiryDate.substring(8, 10);
//                expiryDt = yy1 + "" + mm1 + "" + dd1;
//                int expDt = Integer.parseInt(expiryDt);
//                arraylist.add(expDt);
//                if (expDt < Tempbds) {
////                    Query InsertQuery = em.createNativeQuery("insert into standins_transcancel(instrno,sno,fromacno,toacno,Amount,remarks,"
////                            + "Effdate,closingdate,closedby,status,Closed,expirydt)(select instrno,sno,fromacno,toacno,Amount,remarks,"
////                            + "Effdate,'" + Tempbd + "','" + user + "',status,'Y',expirydt from standins_transmaster st,accountmaster ac "
////                            + "where st.FROMACNO=ac.acno and ac.curbrcode='" + BRCODE + "' and instrno='" + instrNo + "' and sno='" + sNo + "')");
////                    InsertQuery.executeUpdate();
////
////                    Query deleteQuery = em.createNativeQuery("delete from standins_transmaster where fromacno='" + fromAcnos + "' and instrno='"
////                            + instrNo + "' and sno='" + sNo + "'");
////                    deleteQuery.executeUpdate();
//                    Query InsertQuery = em.createNativeQuery("insert into standins_transcancel(instrno,sno,fromacno,toacno,Amount,remarks,"
//                            + "Effdate,closingdate,closedby,status,Closed,expirydt)(select instrno,sno,fromacno,toacno,Amount,remarks,"
//                            + "Effdate,'" + Tempbd + "','" + user + "',status,'Y',expirydt from standins_transpending st,accountmaster ac "
//                            + "where st.FROMACNO=ac.acno and ac.curbrcode='" + BRCODE + "' and instrno='" + instrNo + "' and sno='" + sNo + "')");
//                    InsertQuery.executeUpdate();
//
//                    Query deleteQuery = em.createNativeQuery("delete from standins_transpending where fromacno='" + fromAcnos + "' and instrno='"
//                            + instrNo + "' and sno='" + sNo + "'");
//                    deleteQuery.executeUpdate();
//                } else {
//                    int fromAccSts = 0;
//                    int toAccSts = 0;
//                    double fromOdlimit = 0d;
//                    double fromBalance = 0d;
//                    List accTypeFrom = null;
//                    List accTypeTo = null;
//                    
//                    String fromAccNature = fTSPosting43CBSBean.getAccountNature(fromAcnos);
//                    String fromBrCode = fTSPosting43CBSBean.getCurrentBrnCode(fromAcnos);
//                    String toAcctNature = fTSPosting43CBSBean.getAccountNature(toAcno);
//                    String toBrCode = fTSPosting43CBSBean.getCurrentBrnCode(toAcno);
//                    
//                    if ((fromAccNature.equals(CbsConstant.PAY_ORDER))) {
//                        accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                    } else if (fromAccNature.equals(CbsConstant.FIXED_AC) || (fromAccNature.equals(CbsConstant.MS_AC))) {
//                        accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                    } else if (fromAccNature.equals(CbsConstant.CURRENT_AC) || (fromAccNature.equals(CbsConstant.CC_AC))) {
//                        accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,ca_reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                    } else {
//                        accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,reconbalan r where  a.acno='" + fromAcnos + "' and a.acno=r.acno").getResultList();
//                    }
//                    if (!accTypeFrom.isEmpty()) {
//                        Vector accAllValues = (Vector) accTypeFrom.get(0);
//                        String statusValue = accAllValues.get(1).toString();
//                        String balanceValue = accAllValues.get(2).toString();
//                        String limitValue = accAllValues.get(3).toString();
//                        fromAccSts = Integer.parseInt(statusValue);
//                        if (fromAccSts == 9) {
//                            StnDetails = "Account Has been Closed";
//                        }
//                        if (fromAccSts == 10) {
//                            StnDetails = "Lien Marked";
//                        }
//                        if (fromAccSts == 8) {
//                            StnDetails = " Operation Stopped";
//                        }
//                        if (fromAccSts == 7) {
//                            StnDetails = " Withdrawal Stopped";
//                        }
//                        fromBalance = Double.parseDouble(balanceValue);
//                        fromOdlimit = Double.parseDouble(limitValue);
//                    }
//                    if ((toAcctNature.equals(CbsConstant.PAY_ORDER))) {
//                        accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                    } else if (toAcctNature.equals(CbsConstant.FIXED_AC) || (toAcctNature.equals(CbsConstant.MS_AC))) {
//                        accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                        
//                    } else if (toAcctNature.equals(CbsConstant.CURRENT_AC)) {
//                        accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,ca_reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                    } else {
//                        accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,reconbalan r where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                    }
//                    if (!accTypeTo.isEmpty()) {
//                        Vector accToValues = (Vector) accTypeTo.get(0);
//                        String statusValue = accToValues.get(1).toString();
//                        toAccSts = Integer.parseInt(statusValue);
//                    }
//                    String a = "";
//                    String FreezeAcFrom = "N";
//                    String FreezeAcTo = "N";
//                    /** Code Added For ThreshHold Limit Checking **/
//                    String chkThresh = "";
//                    if (toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                        if(thLmtParam==1){
//                            chkThresh = fTSPosting43CBSBean.isThreshLmtExceed(toAcno, amount,ymmd.format(ymd.parse(Tempbd)));
//                        }
//                    }
//                    if ((fromAccSts == 9) && (toAccSts == 9)) {
//                        a = "Both A/C s  -> " + fromAcnos + " And " + toAcno + " Are Closed ";
//                        Query updateQuery1 = em.createNativeQuery("Update standins_transpending  set processdate='" + Tempbd + "',errormsg='" + a + "',PROCESSEDBY='"
//                                + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                        updateQuery1.executeUpdate();
//                    } else if ((fromAccSts == 9) || (toAccSts == 9) || (fromAccSts == 10)|| (fromAccSts == 8)|| (fromAccSts == 7)) {
//                        if ((fromAccSts == 9) || (fromAccSts == 10)|| (fromAccSts == 8)|| (fromAccSts == 7)) {
//                            a = "Ac No :-> " + fromAcnos + " " + StnDetails;
//                        } else if (toAccSts == 9) {
//                            a = "Ac No :-> " + toAcno + " " + StnDetails;
//                        }
//                        Query updateQuery2 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='" + a
//                                + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                        updateQuery2.executeUpdate();
//                    } else if ((fromBalance + fromOdlimit) <= amount) {
//                        a = "Insuff. Fund In : " + fromAcnos;
//                        Query updateQuery3 = em.createNativeQuery("Update standins_transpending  set processdate='" + Tempbd + "',errormsg='"
//                                + a + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                        updateQuery3.executeUpdate();
//                    } else if ((FreezeAcFrom.equals("Y")) && (FreezeAcTo.equals("Y"))) {
//                        a = "Both A/C s  -> " + fromAcnos + " And " + toAcno + " Are Freezed ";
//                        Query updateQuery4 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='"
//                                + a + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                        updateQuery4.executeUpdate();
//                    } else if ((FreezeAcFrom.equals("Y")) || (FreezeAcTo.equals("Y"))) {
//                        if (FreezeAcFrom.equals("Y")) {
//                            a = "Ac No :-> " + fromAcnos + "  Freezed";
//                        } else if (FreezeAcTo.equals("Y")) {
//                            a = "Ac No :-> " + toAcno + "  Freezed";
//                        }
//                        Query updateQuery5 = em.createNativeQuery("Update standins_transpending set processdate='" + Tempbd + "',errormsg='" + a
//                                + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                        updateQuery5.executeUpdate();
//                    } else if ((toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && !chkThresh.equalsIgnoreCase("true")){
//                        Query updateQuery3 = em.createNativeQuery("Update standins_transpending  set processdate='" + Tempbd + "',errormsg='"
//                                + chkThresh + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcnos + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
//                        updateQuery3.executeUpdate();
//                    } else {
//                        float trsNumber = fTSPosting43CBSBean.getTrsNo();
//                        float RECNO = 0;
//                        float tokenNoDr = 0;
//                        
//                        List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
//                        Vector element = (Vector) list.get(0);
//                        String branchName = element.get(0).toString();
//                        String fromDetail = "SI trf to " + toAcno + " at branch " + branchName;
//                        String toDetail = "SI trf from " + fromAcnos + " at branch " + branchName;
//                        String crFtsPosting = "";
//                        String drFtsPosting = "";
//                        
//                        int crTranDesc = 0;
//                        if ((toAcctNature.equals("RD")) || (toAcctNature.equals("DL")) || (toAcctNature.equals("TL"))) {
//                            crTranDesc = 1;
//                        }
//                        if (BRCODE.equals(toBrCode)) {
//                            drFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(fromAcnos, 2, 1, amount, Tempbd, Tempbd, user, fromBrCode, toBrCode, 0, fromDetail, trsNumber, RECNO, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "");
//                            if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                                crFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(toAcno, 2, 0, amount, Tempbd, Tempbd, user, fromBrCode, toBrCode, crTranDesc, toDetail, trsNumber, RECNO, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "");
//                            }
//                        } else {
//                            drFtsPosting = interBranchFacade.cbsPostingCx(fromAcnos, 1, Tempbd, amount, 0f, 2, fromDetail, 0f, "20", "", null, 3, null, RECNO, 0, fromBrCode, BRCODE, user, "SYSTEM", trsNumber, "", "");
//                            if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                                crFtsPosting = interBranchFacade.cbsPostingSx(toAcno, 0, Tempbd, amount, 0f, 2, toDetail, 0f, "20", "", null, 3, null, RECNO, 0, toBrCode, BRCODE, user, "SYSTEM", trsNumber, "", "");
//                                if (crFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                                    updateBalances(fromAccNature, fromAcnos, amount, trsNumber, BRCODE);
//                                }
//                            }
//                        }
//                        if ((drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) && (crFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE"))) {
//                            Query insertQueryp = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,Effdate,"
//                                    + "processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcnos + "','" + toAcno + "','" + amount + "','"
//                                    + remarks + "','" + effDate + "','" + Tempbd + "','" + user + "')");
//                            insertQueryp.executeUpdate();
//                            
//                            Query deleteQuery1 = em.createNativeQuery("Delete from standins_transpending where FromAcno='" + fromAcnos + "' and InstrNo='"
//                                    + instrNo + "' and Sno='" + sNo + "'");
//                            deleteQuery1.executeUpdate();
//                            
//                            //Sms Object Creation--Dr
//                            SmsToBatchTo to = new SmsToBatchTo();
//                            to.setAcNo(fromAcnos);
//                            to.setDrAmt(amount);
//                            to.setCrAmt(0d);
//                            to.setTranType(2);
//                            to.setTy(1);
//                            to.setTxnDt(dmy.format(ymd.parse(Tempbd)));
//                            to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
//                            
//                            smsBatchList.add(to);
//                            //Credit
//                            to = new SmsToBatchTo();
//                            to.setAcNo(toAcno);
//                            to.setDrAmt(0d);
//                            to.setCrAmt(amount);
//                            to.setTranType(2);
//                            to.setTy(0);
//                            to.setTxnDt(dmy.format(ymd.parse(Tempbd)));
//                            to.setTemplate(SmsType.TRANSFER_DEPOSIT);
//                            
//                            smsBatchList.add(to);
//                            //End here
//                        } else {
//                            //updateBalances(toAcctNature, fromAcnos, amount, trsNumber, BRCODE);
//                            List report = em.createNativeQuery("select INSTRNO,SNO,FROMACNO,TOACNO,AMOUNT,DATE_FORMAT(EFFDATE,'%d/%m/%Y') AS EFFDATE,REMARKS,"
//                                    + "ERRORMSG from standins_transpending where FROMACNO='" + fromAcnos + "' and TOACNO='" + toAcno + "' and SNO=" + sNo
//                                    + " and INSTRNO='" + instrNo + "' and REMARKS='" + remarks + "' and AMOUNT=" + amount + " and EFFDATE='" + effDate
//                                    + "' and processdate <= '" + Tempbd + "' order by INSTRNO,SNO").getResultList();
//                            select1.add(report);
//                        }
//                    }
//                    
//                    List report = em.createNativeQuery("select INSTRNO,SNO,FROMACNO,TOACNO,AMOUNT,DATE_FORMAT(EFFDATE,'%d/%m/%Y') AS EFFDATE,REMARKS,ERRORMSG "
//                        + "from standins_transpending where FROMACNO='" + fromAcnos + "' and TOACNO='" + toAcno + "' and SNO=" + sNo + " and INSTRNO='"
//                        + instrNo + "' and REMARKS='" + remarks + "' and AMOUNT=" + amount + " and EFFDATE='" + effDate + "' and processdate <= '"
//                        + Tempbd + "' order by INSTRNO,SNO").getResultList();
//                    select1.add(report);
//                }                
//            }
////            return select1;
//            returnList.add(select1);
//            returnList.add(smsBatchList);
//            return returnList;
//        } catch (Exception e) {
//            try {
//                throw new ApplicationException(e.getMessage());
//            } catch (IllegalStateException ex) {
//                throw new ApplicationException(ex.getMessage());
//            } catch (SecurityException ex) {
//                throw new ApplicationException(ex.getMessage());
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//    }
//
//    public List<List> siAutoPost(String user, String brCode, String bankDt) throws ApplicationException {
//        List<List> returnList = new ArrayList<List>();
//        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
//        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            int thLmtParam = 0;
//            List chParamThresh = fTSPosting43CBSBean.getThreshLmtParam();
//            if(!(chParamThresh==null || chParamThresh.isEmpty())){
//                Vector verLst = (Vector) chParamThresh.get(0);
//                thLmtParam = Integer.parseInt(verLst.get(0).toString());
//            }
//            List reportList = new ArrayList();
//            //String msg = "Instructions Posted Successfully  And Confirm From Today's Error Report For Details ";
//            List select = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,effdate,status,remarks,enterby,entrydate,chargeflag,"
//                    + "expirydt from standins_transmaster ,accountmaster where fromacno=acno and curbrcode='" + brCode + "' and Effdate <='" + bankDt
//                    + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
//
//            for (int i = 0; i < select.size(); i++) {
//                Vector ele = (Vector) select.get(i);
//                String fromAcNo = (ele.get(0).toString());
//                String toAcno = (ele.get(1).toString());
//                int sNo = (Integer.parseInt(ele.get(2).toString()));
//                float instrNo = (Float.parseFloat(ele.get(3).toString()));
//                double amount = (Double.parseDouble(ele.get(4).toString()));
//                String effDt = (ele.get(5).toString());
//                String effDate = effDt.substring(0, 4) + effDt.substring(5, 7) + effDt.substring(8, 10);
//
//                String remarks = (ele.get(7).toString());
//                String expiryDate = (ele.get(11).toString());
//                String expiryDt = expiryDate.substring(0, 4) + expiryDate.substring(5, 7) + expiryDate.substring(8, 10);
//
//                String fromAccNature = fTSPosting43CBSBean.getAccountNature(fromAcNo);
//                String fromBrCode = fTSPosting43CBSBean.getCurrentBrnCode(fromAcNo);
//                String toAcctNature = fTSPosting43CBSBean.getAccountNature(toAcno);
//                String toBrCode = fTSPosting43CBSBean.getCurrentBrnCode(toAcno);
//                List accTypeFrom = null;
//                if ((fromAccNature.equals(CbsConstant.PAY_ORDER))) {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  "
//                            + "a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
//                } else if (fromAccNature.equals(CbsConstant.FIXED_AC) || (fromAccNature.equals(CbsConstant.MS_AC))) {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r "
//                            + "where  a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
//                } else if (fromAccNature.equals(CbsConstant.CURRENT_AC)) {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,ca_reconbalan r "
//                            + "where  a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
//                } else {
//                    accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,reconbalan r "
//                            + "where  a.acno='" + fromAcNo + "' and a.acno=r.acno").getResultList();
//                }
//                int fromAccSts = 0;
//                String StnDetails = "";
//                double fromBalance = 0d;
//                double fromOdlimit = 0d;
//                if (!accTypeFrom.isEmpty()) {
//                    Vector accAllValues = (Vector) accTypeFrom.get(0);
//                    String statusValue = accAllValues.get(1).toString();
//                    String balanceValue = accAllValues.get(2).toString();
//                    String limitValue = accAllValues.get(3).toString();
//                    fromAccSts = Integer.parseInt(statusValue);
//                    if (fromAccSts == 9) {
//                        StnDetails = "Account Has been Closed";
//                    }
//                    if (fromAccSts == 10) {
//                        StnDetails = "Lien Marked";
//                    }
//                    if (fromAccSts == 8) {
//                        StnDetails = " Operation Stopped";
//                    }
//                    if (fromAccSts == 7) {
//                        StnDetails = " Withdrawal Stopped";
//                    }
//                    fromBalance = Double.parseDouble(balanceValue);
//                    fromOdlimit = Double.parseDouble(limitValue);
//                }
//                List accTypeTo = null;
//                if ((toAcctNature.equals(CbsConstant.PAY_ORDER))) {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='"
//                            + toAcno + "' and a.acno=r.acno").getResultList();
//                } else if (toAcctNature.equals(CbsConstant.FIXED_AC) || (toAcctNature.equals(CbsConstant.MS_AC))) {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r "
//                            + "where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                } else if (toAcctNature.equals(CbsConstant.CURRENT_AC)) {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,ca_reconbalan r"
//                            + " where  a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                } else {
//                    accTypeTo = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,reconbalan r where  "
//                            + "a.acno='" + toAcno + "' and a.acno=r.acno").getResultList();
//                }
//                int toAccSts = 0;
//                if (!accTypeTo.isEmpty()) {
//                    Vector accToValues = (Vector) accTypeTo.get(0);
//                    String statusValue = accToValues.get(1).toString();
//                    toAccSts = Integer.parseInt(statusValue);
//                }
//                int ToAcFlag = 1;
//                ToAcFlag = toAccSts;
//                String a = null;
//                
//                /** Code Added For ThreshHold Limit Checking **/
//                String chkThresh = "";
//                if (toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                    if(thLmtParam==1){
//                        chkThresh = fTSPosting43CBSBean.isThreshLmtExceed(toAcno, amount,ymmd.format(ymd.parse(bankDt)));
//                    }
//                }                
//                
//                if ((fromAccSts == 9) && (ToAcFlag == 9)) {
//                    a = "Both A/C s  -> " + fromAcNo + " And " + toAcno + " Are Closed ";
//                    Query insertQuery = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
//                            + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + a + "'" + "," + "'" + expiryDt + "'" + ")");
//                    insertQuery.executeUpdate();
//                    Query deleteQuery = em.createNativeQuery("Delete From standins_transmaster where substring(FROMACNO,1,2)='" + brCode + "' and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
//                    deleteQuery.executeUpdate();
//                } else if ((fromAccSts == 9) || (ToAcFlag == 9) || (fromAccSts == 10)|| (fromAccSts == 8)|| (fromAccSts == 7)) {
//                    if ((fromAccSts == 9) || (fromAccSts == 10)|| (fromAccSts == 8)|| (fromAccSts == 7)) {
//                        a = "Ac No :-> " + fromAcNo + "" + StnDetails;
//                    } else if ((ToAcFlag == 9) || (ToAcFlag == 9)) {
//                        a = "Ac No :-> " + toAcno + "" + StnDetails;
//                    }
//                    Query insertQuery1 = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
//                            + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + a + "'" + "," + "'" + expiryDt + "'" + ")");
//                    insertQuery1.executeUpdate();
//                    Query deleteQuery1 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
//                    deleteQuery1.executeUpdate();
//                } else if ((fromBalance + fromOdlimit) <= amount) {
//                    a = "Insuff. Fund In : " + fromAcNo;
//                    Query insertQuery2 = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
//                            + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + a + "'" + "," + "'" + expiryDt + "'" + ")");
//                    insertQuery2.executeUpdate();
//
//                    Query deleteQuery2 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
//                    deleteQuery2.executeUpdate();
//                } else if ((toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && !chkThresh.equalsIgnoreCase("true")){
//                    Query insertQuery2 = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,ProcessDate, ProcessedBy, ErrorMsg,expirydt)"
//                        + "values (" + "'" + fromAcNo + "'" + "," + "'" + toAcno + "'" + "," + instrNo + "," + sNo + "," + "'" + remarks + "'" + "," + "'" + effDate + "'" + "," + amount + "," + "'" + bankDt + "'" + "," + "'" + user + "'" + "," + "'" + chkThresh + "'" + "," + "'" + expiryDt + "'" + ")");
//                    insertQuery2.executeUpdate();
//
//                    Query deleteQuery2 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
//                    deleteQuery2.executeUpdate();
//                } else {
//                    float trsNo = fTSPosting43CBSBean.getTrsNo();
//                    float recNo = 0;
//                    float tokenNoDr = 0;
//                    String crFtsPosting = "";
//                    String drFtsPosting = "";
//
//                    String branchName = "";
//                    List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
//                    Vector element = (Vector) list.get(0);
//                    branchName = element.get(0).toString();
//                    String fromDetail = "SI trf to " + toAcno + " at branch " + branchName;
//                    String toDetail = "SI trf from " + fromAcNo + " at branch " + branchName;
//                    int crTranDesc = 0;
//                    if ((toAcctNature.equals("RD")) || (toAcctNature.equals("DL")) || (toAcctNature.equals("TL"))) {
//                        crTranDesc = 1;
//                    }
//
//                    if (brCode.equals(toBrCode)) {
//                        drFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(fromAcNo, 2, 1, amount, bankDt, bankDt, user, fromBrCode, fromBrCode, 0,
//                                fromDetail, trsNo, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr,
//                                "N", "", "");
//                        if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                            //String rs = fTSPosting43CBSBean.updateBalance(fromAccNature, fromAcNo, 0, amount, "", "");
//                            //if (rs.equalsIgnoreCase("True")) {
//                            crFtsPosting = fTSPosting43CBSBean.ftsPosting43CBS(toAcno, 2, 0, amount, bankDt, bankDt, user, fromBrCode, fromBrCode,
//                                    crTranDesc, toDetail, trsNo, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null,
//                                    tokenNoDr, "N", "", "");
//                            //}
//                        }
//                    } else {
//                        drFtsPosting = interBranchFacade.cbsPostingCx(fromAcNo, 1, bankDt, amount, 0f, 2, fromDetail, 0f, "20", "", null,
//                                3, null, recNo, 0, fromBrCode, brCode, user, "SYSTEM", trsNo, "", "");
//                        if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                            String rs = fTSPosting43CBSBean.updateBalance(fromAccNature, fromAcNo, 0, amount, "", "");
//                            if (rs.equalsIgnoreCase("True")) {
//                                crFtsPosting = interBranchFacade.cbsPostingSx(toAcno, 0, bankDt, amount, 0f, 2, toDetail, 0f, "20", "", null,
//                                        3, null, recNo, 0, toBrCode, brCode, user, "SYSTEM", trsNo, "", "");
//                            }
//                        }
//                    }
//                    if ((drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) && (crFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE"))) {
//                        Query insertQuery6 = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,"
//                                + "Effdate,processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcNo + "','" + toAcno + "','"
//                                + amount + "','" + remarks + "','" + effDate + "','" + bankDt + "','" + user + "')");
//                        insertQuery6.executeUpdate();
//
//                        Query deleteQuery6 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno "
//                                + "and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
//                        deleteQuery6.executeUpdate();
//
//                        //Sms Object Creation--Dr
//                        SmsToBatchTo to = new SmsToBatchTo();
//                        to.setAcNo(fromAcNo);
//                        to.setDrAmt(amount);
//                        to.setCrAmt(0d);
//                        to.setTranType(2);
//                        to.setTy(1);
//                        to.setTxnDt(dmy.format(ymd.parse(bankDt)));
//                        to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
//
//                        smsBatchList.add(to);
//                        //Credit
//                        to = new SmsToBatchTo();
//                        to.setAcNo(toAcno);
//                        to.setDrAmt(0d);
//                        to.setCrAmt(amount);
//                        to.setTranType(2);
//                        to.setTy(0);
//                        to.setTxnDt(dmy.format(ymd.parse(bankDt)));
//                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);
//
//                        smsBatchList.add(to);
//                        //End here
//                    } else if (drFtsPosting.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                        List report = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,date_format(effdate,'%d/%m/%Y') AS effdate,"
//                                + "status,remarks,enterby,DATE_FORMAT(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,date_format(expirydt,'%d/%m/%Y') AS expirydt "
//                                + "from standins_transmaster where FROMACNO='" + fromAcNo + "' and toacno='" + toAcno + "' and sno=" + sNo
//                                + " and Amount=" + amount + " and Effdate <= '" + bankDt + "' And instrno='" + instrNo + "' And remarks='"
//                                + remarks + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
//                        reportList.add(report);
//                    } else {
//                        List report = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,date_format(effdate,'%d/%m/%Y') AS effdate,"
//                                + "status,remarks,enterby,date_format(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,date_format(expirydt,'%d/%m/%Y') AS expirydt "
//                                + "from standins_transmaster where fromacno='" + fromAcNo + "' and toacno='" + toAcno + "' and sno=" + sNo
//                                + " and Amount=" + amount + " and Effdate <= '" + bankDt + "' And instrno='" + instrNo + "' And remarks='"
//                                + remarks + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
//                        reportList.add(report);
//                    }
//                }
//            }
//
//            List genSelect = em.createNativeQuery("select acno,sno,instrno,date_format(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,"
//                    + "date_format(entrydate,'%d/%m/%Y') AS entrydate from standins_generalmaster where substring(ACNO,1,2)='" + brCode
//                    + "' and Effdate <= '" + bankDt + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();
//
//            for (int a = 0; a < genSelect.size(); a++) {
//                Vector ele1 = (Vector) genSelect.get(a);
//                String acNo = (ele1.get(0).toString());
//                Integer sNumber = (Integer.parseInt(ele1.get(1).toString()));
//                Float instrNum = (Float.parseFloat(ele1.get(2).toString()));
//                String status = (ele1.get(4).toString());
//
//                if (status.equals("EXECUTED")) {
//                    Query updateQuery0 = em.createNativeQuery("update standins_generalmaster set st.status='EXECUTED',PROCESSDATE='" + bankDt + "',PROCESSEDBY='" + user + "' from standins_generalmaster st,accountmaster ac where st.acno=ac.acno and ac.curbrcode ='" + brCode + "' and st.acno='" + acNo + "' and INSTRNO=" + instrNum + " and st.SNO=" + sNumber + "  and st.status='UNEXECUTED'");
//                    updateQuery0.executeUpdate();
//                }
//            }
////            return reportList;
//            returnList.add(reportList);
//            returnList.add(smsBatchList);
//            return returnList;
//        } catch (Exception e) {
//            try {
//                throw new ApplicationException(e);
//            } catch (IllegalStateException ex) {
//                throw new ApplicationException(ex);
//            } catch (SecurityException ex) {
//                throw new ApplicationException(ex);
//            }
//        }
//    }
}
