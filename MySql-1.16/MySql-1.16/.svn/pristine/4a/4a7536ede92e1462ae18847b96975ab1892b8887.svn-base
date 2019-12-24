/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.deaf.DeafForm1Pojo;
import com.cbs.pojo.deaf.DeafMarkPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

@Stateless(mappedName = "DeafMgmtFacade")
//@TransactionManagement(value = TransactionManagementType.BEAN)
public class DeafMgmtFacade implements DeafMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    DDSReportFacadeRemote ddsRepRemote;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    DDSManagementFacadeRemote ddsMgmt;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    AccountOpeningFacadeRemote acOpenRemote;
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<DeafForm1Pojo> getForm1IOpeningData(String inputBr, String inputfrDt, String inputToDt, String repType, String prevEndDt) throws ApplicationException {
        List<DeafForm1Pojo> dataList = new ArrayList<DeafForm1Pojo>();
        List list = null;
        String effectDt = inputToDt;
        String subquery = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(acno,1,2) = '" + inputBr + "'";
        String subQueryv = inputBr.equalsIgnoreCase("0A") ? "" : "and curbrcode = '" + inputBr + "'";
        try {
            String bnkCode = fts.getBankCode();
            /*
             For Interest Bearing Deposit for Opening balance at the beginning of the month.
             * 
             */
            // substring(acno,1,2)= '" + inputBr + "'and
            String subquery1 = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(acno,1,2) = '" + inputBr + "'";
            subquery = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(a.acno,1,2) = '" + inputBr + "'";
            int noOfAcNo1 = 0;
            BigDecimal bal = new BigDecimal("0");
            String monthBeginDt = effectDt.substring(0, 6) + "01";
            List intBearList1;
            if (bnkCode.equalsIgnoreCase("INDR")) {
                String rddt = fts.getCodeFromCbsParameterInfo("DEAF-RD-INT-DT");
                if (ymd.parse(rddt).after(ymd.parse(prevEndDt))) {
                    rddt = prevEndDt;
                }
                intBearList1 = em.createNativeQuery("select sum(NoOfAno),sum(Amt) from ( "
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' "
                        + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno and a.acno not in"
                        + "(select distinct a.acno from accountstatus a,accountmaster b where a.dt <='" + rddt + "' "
                        + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno "
                        + "and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))) "
                        + "and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "')) "
                        + "union all "
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' "
                        + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "')) "
                        + "union all "
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,td_accountmaster b where a.dt <='" + prevEndDt + "' "
                        + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                        + "('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')))f").getResultList();
            } else {
                intBearList1 = em.createNativeQuery("select sum(NoOfAno),sum(Amt) from ("
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' "
                        + "and a.spflag='15' " + subquery + " and b.accstatus='15' and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                        + "('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "')) "
                        + "union all "
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,td_accountmaster b where a.dt <='" + prevEndDt + "' "
                        + "and a.spflag='15' " + subquery + " and b.accstatus='15' and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                        + "('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')))f").getResultList();
            }
            double bearingbal = 0d;
            int aa = 0;
            DeafForm1Pojo pojo1 = new DeafForm1Pojo();
            if (!intBearList1.isEmpty()) {
                for (int i = 0; i < intBearList1.size(); i++) {
                    Vector vtr = (Vector) intBearList1.get(i);

                    aa = intBearList1.size();
                    String acno = vtr.get(0).toString();
                    aa = Integer.parseInt(acno);
                    double balance = Double.parseDouble(vtr.get(1).toString());
                    bearingbal = bearingbal + balance;
                }
            }
            pojo1.setsNo("1");
            String mm = effectDt.substring(4, 6);
            String yyyy = effectDt.substring(0, 4);
            pojo1.setMonth(mm);
            pojo1.setYear(yyyy);
            pojo1.setParticular("Opening balance at the beginning of the month.");
            pojo1.setIbdNoOfAc(aa);
            pojo1.setIbdAmount(new BigDecimal(formatter.format(bearingbal)));
            /*
             For Non - interest bearing Deposit
             * 
             */
            double bearingbal2 = 0d;
            int rdb = 0;
            if (bnkCode.equalsIgnoreCase("INDR")) {
                String rddt = fts.getCodeFromCbsParameterInfo("DEAF-RD-INT-DT");
                if (ymd.parse(rddt).after(ymd.parse(prevEndDt))) {
                    rddt = prevEndDt;
                }
                List intBearRd2 = em.createNativeQuery("select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + rddt + "' "
                        + "and a.spflag='15' " + subquery + " and b.accstatus='15' and a.acno=b.acno "
                        + "and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "'))").getResultList();
                Vector rdv = (Vector) intBearRd2.get(0);
                rdb = Integer.parseInt(rdv.get(0).toString());
                bearingbal2 = Double.parseDouble(rdv.get(1).toString());
            }
            List intBearingList2 = em.createNativeQuery("select count(a.acno),ifnull(sum(a.amount),0) from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' and a.spflag='15' "
                    + "" + subquery + " and b.accstatus='15' and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag in('C','B'))").getResultList();
            int bb = 0;
            if (!intBearingList2.isEmpty()) {
                for (int i = 0; i < intBearingList2.size(); i++) {
                    Vector vtr = (Vector) intBearingList2.get(i);
                    String acno = vtr.get(0).toString();
                    bb = Integer.parseInt(acno);
                    bb = bb + rdb;
                    double balance = Double.parseDouble(vtr.get(1).toString());
                    bearingbal2 = bearingbal2 + balance;
                }
            }
            pojo1.setNibdNoOfAc(bb);
            pojo1.setNibdAmount(new BigDecimal(formatter.format(bearingbal2)));
            /*
             For Other Credits (Non -  interest bearing)
             * 
             */
            List intBearingList3 = em.createNativeQuery("select count(acno),ifnull(sum(amount),0) from accountstatus where dt<= '" + prevEndDt + "' and spflag='15' " + subquery1 + " "
                    + "and substring(acno,3,2)in('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "')").getResultList();

            double bearingbal3 = 0d;

            int cc = 0;
            double glBal = 0d;
            if (!intBearingList3.isEmpty()) {
                String acno = "", acType = "";
                for (int i = 0; i < intBearingList3.size(); i++) {
                    Vector vtr = (Vector) intBearingList3.get(i);
                    cc = intBearingList3.size();
                    acno = vtr.get(0).toString();
                    cc = Integer.parseInt(acno);
                    bearingbal3 = Double.parseDouble(vtr.get(1).toString());
                }
            }
            pojo1.setOtcNoOfAc(cc);
            pojo1.setOtcAmount(new BigDecimal(formatter.format(bearingbal3 + glBal)));

            noOfAcNo1 = aa + bb + cc;
            bal = new BigDecimal(bearingbal + bearingbal2 + bearingbal3);
            pojo1.setNoOfAcNo(noOfAcNo1);
            pojo1.setBal(bal);
            dataList.add(pojo1);

            /*
             Interest Bearing Deposit for Number of Accounts and amount, if any, inadvertently omitted in the return for the previous month and transferred during this month.
             * 
             */
            DeafForm1Pojo pojo2 = new DeafForm1Pojo();
            pojo2.setsNo("2");
            pojo2.setParticular("Number of Accounts and amount, if any, inadvertently omitted in the return for the previous month and transferred during this month.");
            pojo2.setIbdNoOfAc(0);
            pojo2.setIbdAmount(new BigDecimal(0));
            pojo2.setNibdNoOfAc(0);
            pojo2.setNibdAmount(new BigDecimal(0));
            pojo2.setOtcNoOfAc(0);
            pojo2.setOtcAmount(new BigDecimal(0));
            pojo2.setNoOfAcNo(0);
            pojo2.setBal(new BigDecimal(0));
            dataList.add(pojo2);

            /*
             For Interest Bearing Deposit for  Number of Accounts and amount due and transferred to the Fund during this month.
             * 
             */
            int noOfAcNo3 = 0;
            BigDecimal bal3 = new BigDecimal("0");
            List acfdList;
            acfdList = em.createNativeQuery("select sum(NoOfAno),sum(Amt) from ("
                    + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b "
                    + "where a.dt > '" + inputfrDt + "' and a.dt <= '" + inputToDt + "' "
                    + "and a.spflag='15' " + subquery + " and b.accstatus = 15 and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "')) "
                    + "union All "
                    + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,td_accountmaster b "
                    + "where a.dt > '" + inputfrDt + "' and a.dt <= '" + inputToDt + "' "
                    + "and a.spflag='15' " + subquery + " and b.accstatus = 15 and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                    + ")f").getResultList();


            Vector vtr1 = (Vector) acfdList.get(0);
            DeafForm1Pojo pojo3 = new DeafForm1Pojo();
            int a1 = Integer.parseInt(vtr1.get(0).toString());
            BigDecimal b1 = new BigDecimal(vtr1.get(1).toString());
            pojo3.setsNo("3");
            pojo3.setParticular("Number of Accounts and amount due and transferred to the Fund during this month.");
            pojo3.setIbdNoOfAc(a1);
            pojo3.setIbdAmount(b1);
            //dataList.add(pojo3);
                 /*
             For Non - interest bearing Deposit
             * 
             */
            int a22 = 0;
            BigDecimal b22 = new BigDecimal("0");
            List accaList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,accountmaster b where a.dt > '" + inputfrDt + "' and a.dt <= '" + inputToDt + "' and a.spflag='15' "
                    + "" + subquery + " and b.accstatus = 15 and a.acno = b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag in('C','B'))").getResultList();
            Vector vtr2 = (Vector) accaList.get(0);
            int a2 = Integer.parseInt(vtr2.get(0).toString());
            a2 = a2 + a22;
            BigDecimal b2 = new BigDecimal(vtr2.get(1).toString());
            b2 = b2.add(b22);
            pojo3.setNibdNoOfAc(a2);
            pojo3.setNibdAmount(b2);
            //dataList.add(pojo3);
                 /*
             For Other Credits (Non -  interest bearing)
             * 
             */
            List acccList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt > '" + inputfrDt + "' and dt <= '" + inputToDt + "' and spflag='15' "
                    + "" + subquery1 + " and substring(acno,3,2)in('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "')").getResultList();
            Vector vtr3 = (Vector) acccList.get(0);
            int a3 = Integer.parseInt(vtr3.get(0).toString());
            BigDecimal b3 = new BigDecimal(vtr3.get(1).toString());
            pojo3.setOtcNoOfAc(a3);
            pojo3.setOtcAmount(b3);
            noOfAcNo3 = a1 + a2 + a3;
            bal3 = bal3.add(b1).add(b2).add(b3);
            pojo3.setNoOfAcNo(noOfAcNo3);
            pojo3.setBal(bal3);
            dataList.add(pojo3);

            /*
             For Interest Bearing Deposit for Accounts and amount claimed during the previous month and paid from the Fund during this month.
             * 
             */

            int noOfAcNo4 = 0;
            BigDecimal bal4 = new BigDecimal("0");
            List claimedList1;
            claimedList1 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                    + "" + subquery1 + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
            DeafForm1Pojo pojo4 = new DeafForm1Pojo();
            Vector cvt1 = (Vector) claimedList1.get(0);
            int intBearingClaimed = Integer.parseInt(cvt1.get(0).toString());
            BigDecimal intBearingClaimedBal = new BigDecimal(cvt1.get(1).toString());
            pojo4.setsNo("4");
            pojo4.setParticular("Accounts and amount claimed during the previous month and paid from the Fund during this month.");
            pojo4.setIbdNoOfAc(intBearingClaimed);
            pojo4.setIbdAmount(intBearingClaimedBal);
            //dataList.add(pojo4);
                 /*
             For Non - interest bearing Deposit
             * 
             */
            int nonIntBearingClaimed1 = 0;
            BigDecimal nonIntBearingClaimedBal1 = new BigDecimal("0");

            List claimedList2 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                    + "" + subquery1 + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag in('C','B'))").getResultList();
            Vector cvt2 = (Vector) claimedList2.get(0);
            int nonIntBearingClaimed = Integer.parseInt(cvt2.get(0).toString());
            nonIntBearingClaimed = nonIntBearingClaimed + nonIntBearingClaimed1;
            BigDecimal nonIntBearingClaimedBal = new BigDecimal(cvt2.get(1).toString());
            nonIntBearingClaimedBal = nonIntBearingClaimedBal.add(nonIntBearingClaimedBal1);
            pojo4.setNibdNoOfAc(nonIntBearingClaimed);
            pojo4.setNibdAmount(nonIntBearingClaimedBal);
            // dataList.add(pojo4);
                   /*
             Other Credits (Non -  interest bearing)
             * 
             */

            List claimedList3 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                    + "" + subquery1 + " and substring(acno,3,2)in('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "')").getResultList();
            Vector cvt3 = (Vector) claimedList3.get(0);
            int otherBearingClaimed = Integer.parseInt(cvt3.get(0).toString());
            BigDecimal otherBearingClaimedBal = new BigDecimal(cvt3.get(1).toString());
            pojo4.setOtcNoOfAc(otherBearingClaimed);
            pojo4.setOtcAmount(otherBearingClaimedBal);
            // dataList.add(pojo4);

            noOfAcNo4 = intBearingClaimed + nonIntBearingClaimed + otherBearingClaimed;
            bal4 = bal4.add(intBearingClaimedBal).add(nonIntBearingClaimedBal).add(otherBearingClaimedBal);
            pojo4.setNoOfAcNo(noOfAcNo4);
            pojo4.setBal(bal4);
            dataList.add(pojo4);

            /*
             Total amount transferred less amount paid from the Fund during the month.(2+3-4)
             * 
             */
            DeafForm1Pojo pojo5 = new DeafForm1Pojo();
            pojo5.setsNo("5");
            pojo5.setParticular("Total amount transferred less amount paid from the Fund during the month.(2+3-4)");
            int netIbdAcno = (0 + a1) - intBearingClaimed;
            pojo5.setIbdNoOfAc((0 + a1) - intBearingClaimed);
            double netIbdAmt = (0 + b1.doubleValue()) - intBearingClaimedBal.doubleValue();
            pojo5.setIbdAmount(new BigDecimal(formatter.format((0 + b1.doubleValue()) - intBearingClaimedBal.doubleValue())));
            int netNibdAcno = (0 + a2) - nonIntBearingClaimed;
            pojo5.setNibdNoOfAc((0 + a2) - nonIntBearingClaimed);
            double netNibdAmt = (0 + b2.doubleValue()) - nonIntBearingClaimedBal.doubleValue();
            pojo5.setNibdAmount(new BigDecimal(formatter.format((0 + b2.doubleValue()) - nonIntBearingClaimedBal.doubleValue())));
            int netOtherAcno = (0 + a3) - otherBearingClaimed;
            pojo5.setOtcNoOfAc((0 + a3) - otherBearingClaimed);
            double netOtherAmt = (0 + b3.doubleValue()) - otherBearingClaimedBal.doubleValue();
            pojo5.setOtcAmount(new BigDecimal(formatter.format((0 + b3.doubleValue()) - otherBearingClaimedBal.doubleValue())));
            int netAcno = (0 + noOfAcNo3) - noOfAcNo4;
            pojo5.setNoOfAcNo((0 + noOfAcNo3) - noOfAcNo4);
            double netBal = (0 + bal3.doubleValue()) - bal4.doubleValue();
            pojo5.setBal(new BigDecimal(formatter.format((0 + bal3.doubleValue()) - bal4.doubleValue())));
            dataList.add(pojo5);

            /*
             Total amount with the Fund at the end of the ….…(month) 20…..
             * 
             */

            DeafForm1Pojo pojo6 = new DeafForm1Pojo();
            pojo6.setsNo("6");
            pojo6.setParticular("Total amount with the Fund at the end of the ….…(month) 20…..");
            pojo6.setIbdNoOfAc(aa + netIbdAcno);
            pojo6.setIbdAmount(new BigDecimal(formatter.format(bearingbal + netIbdAmt)));

            pojo6.setNibdNoOfAc(bb + netNibdAcno);
            pojo6.setNibdAmount(new BigDecimal(formatter.format(bearingbal2 + netNibdAmt)));

            pojo6.setOtcNoOfAc(cc + netOtherAcno);
            pojo6.setOtcAmount(new BigDecimal(formatter.format(bearingbal3 + netOtherAmt)));

            pojo6.setNoOfAcNo(noOfAcNo1 + netAcno);
            pojo6.setBal(new BigDecimal(formatter.format(bal.doubleValue() + netBal)));
            dataList.add(pojo6);

            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<DeafForm1Pojo> getForm1Data(String inputBr, String inputfrDt, String inputToDt, String repType, String prevEndDt) throws ApplicationException {
        List<DeafForm1Pojo> dataList = new ArrayList<DeafForm1Pojo>();
        try {
            List list = null;
            String effectDt = inputToDt;
            String subquery = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(acno,1,2) = '" + inputBr + "'";
            String subQueryv = inputBr.equalsIgnoreCase("0A") ? "" : "and curbrcode = '" + inputBr + "'";

//            if (inputBr.equalsIgnoreCase("0A")) {
//                list = em.createNativeQuery("select ifnull(max(date_format(to_dt,'%Y%m%d')),'') as to_dt "
//                        + "from cbs_loan_acctype_interest_parameter where date_format(to_dt,'%Y%m%d')<='" + effectDt + "' "
//                        + " and flag='UC' and post_flag='Y'").getResultList();
//            } else {
//                list = em.createNativeQuery("select ifnull(max(date_format(to_dt,'%Y%m%d')),'') as to_dt "
//                        + "from cbs_loan_acctype_interest_parameter where date_format(to_dt,'%Y%m%d')<='" + effectDt + "' and "
//                        + "brncode='" + inputBr + "' and flag='UC' and post_flag='Y'").getResultList();
//            }
//
//            if (list == null || list.isEmpty()) {
//                throw new ApplicationException("There is no data.");
//            }
//            Vector ele = (Vector) list.get(0);
//            String postDt = ele.get(0).toString();

            //Retrieve All Un-Claimed Nature.
            list = ddsRepRemote.getAccountNatureClassification("'C'");
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Please define credit/debit acctcode in accounttypemaster.");
            }
            //List<String> acTypeList = new ArrayList<String>();
            if (repType.equalsIgnoreCase("Form I")) {
                int noOfAcNo = 0;
                BigDecimal bal = new BigDecimal("0");
                List acfdList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                        + "and spflag='15' " + subquery + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                        + "('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
                Vector vtr1 = (Vector) acfdList.get(0);
                DeafForm1Pojo pojo1 = new DeafForm1Pojo();
                int a1 = Integer.parseInt(vtr1.get(0).toString());
                BigDecimal b1 = new BigDecimal(vtr1.get(1).toString());
                pojo1.setIbdNoOfAc(a1);
                pojo1.setIbdAmount(b1);
                dataList.add(pojo1);

                List accaList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + inputfrDt + "' and '" + inputToDt + "' and spflag='15' "
                        + "" + subquery + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where AcctCode = '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "')").getResultList();
                Vector vtr2 = (Vector) accaList.get(0);
                int a2 = Integer.parseInt(vtr2.get(0).toString());
                BigDecimal b2 = new BigDecimal(vtr2.get(1).toString());
                pojo1.setNibdNoOfAc(a2);
                pojo1.setNibdAmount(b2);
                dataList.add(pojo1);

                List acccList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + inputfrDt + "' and '" + inputToDt + "' and spflag='15' "
                        + "" + subquery + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.PAY_ORDER + "') and AcctCode <> '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "')").getResultList();
                Vector vtr3 = (Vector) acccList.get(0);
                int a3 = Integer.parseInt(vtr3.get(0).toString());
                BigDecimal b3 = new BigDecimal(vtr3.get(1).toString());
                pojo1.setOtcNoOfAc(a3);
                pojo1.setOtcAmount(b3);
                noOfAcNo = a1 + a2 + a3;
                bal = bal.add(b1).add(b2).add(b3);
                String mm = effectDt.substring(4, 6);
                String yyyy = effectDt.substring(0, 4);
                pojo1.setMonth(mm);
                pojo1.setYear(yyyy);
                pojo1.setNoOfAcNo(noOfAcNo);
                pojo1.setBal(bal);
                dataList.add(pojo1);
            } else if (repType.equalsIgnoreCase("Form II")) {
                String bnkCode = fts.getBankCode();

                // New Code Opening Data as per Indraprastha Bank first month closing data = 2nd month Opening data

                String monthFirstDt = CbsUtil.monthAdd(inputfrDt, -1);
                String monthEndDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(inputToDt.substring(4, 6)) - 1, Integer.parseInt(inputToDt.substring(0, 4))));
                String prevMonthEndDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(prevEndDt.substring(4, 6)) - 1, Integer.parseInt(prevEndDt.substring(0, 4))));

                List<DeafForm1Pojo> openingDataList = getForm1IOpeningData(inputBr, monthFirstDt, monthEndDt, repType, prevMonthEndDt);
                DeafForm1Pojo getEleData = openingDataList.get(openingDataList.size() - 1);

//                System.out.println(getEleData.getIbdNoOfAc());
//                System.out.println(getEleData.getIbdAmount());
//                System.out.println(getEleData.getNibdNoOfAc());
//                System.out.println(getEleData.getNibdAmount());
//                System.out.println(getEleData.getOtcNoOfAc());
//                System.out.println(getEleData.getOtcAmount());
//                System.out.println(getEleData.getNoOfAcNo());
//                System.out.println(getEleData.getBal());

                DeafForm1Pojo pojo1 = new DeafForm1Pojo();
                pojo1.setsNo("1");
                String mm1 = effectDt.substring(4, 6);
                String yyyy1 = effectDt.substring(0, 4);
                pojo1.setMonth(mm1);
                pojo1.setYear(yyyy1);
                pojo1.setParticular("Opening balance at the beginning of the month.");
                pojo1.setIbdNoOfAc(getEleData.getIbdNoOfAc());
                pojo1.setIbdAmount(new BigDecimal(formatter.format(getEleData.getIbdAmount())));

                pojo1.setNibdNoOfAc(getEleData.getNibdNoOfAc());
                pojo1.setNibdAmount(new BigDecimal(formatter.format(getEleData.getNibdAmount())));

                pojo1.setOtcNoOfAc(getEleData.getOtcNoOfAc());
                pojo1.setOtcAmount(getEleData.getOtcAmount());

                pojo1.setNoOfAcNo(getEleData.getNoOfAcNo());
                pojo1.setBal(getEleData.getBal());
                dataList.add(pojo1);
                // End New Code Opening Data
                /*
                 For Interest Bearing Deposit for Opening balance at the beginning of the month.
                 * 
                 */

                // substring(acno,1,2)= '" + inputBr + "'and
                String subquery1 = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(acno,1,2) = '" + inputBr + "'";
                subquery = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(a.acno,1,2) = '" + inputBr + "'";
                int noOfAcNo1 = 0;
                BigDecimal bal = new BigDecimal("0");
                String monthBeginDt = effectDt.substring(0, 6) + "01";
                List intBearList1;
                if (bnkCode.equalsIgnoreCase("INDR")) {
                    String rddt = fts.getCodeFromCbsParameterInfo("DEAF-RD-INT-DT");
                    if (ymd.parse(rddt).after(ymd.parse(prevEndDt))) {
                        rddt = prevEndDt;
                    }
                    intBearList1 = em.createNativeQuery("select sum(NoOfAno),sum(Amt) from ( "
                            + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' "
                            + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno and a.acno not in"
                            + "(select distinct a.acno from accountstatus a,accountmaster b where a.dt <='" + rddt + "' "
                            + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno "
                            + "and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))) "
                            + "and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "')) "
                            + "union all "
                            + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' "
                            + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "')) "
                            + "union all "
                            + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,td_accountmaster b where a.dt <='" + prevEndDt + "' "
                            + "and a.spflag='15' and b.accstatus='15' " + subquery + " and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                            + "('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')))f").getResultList();
                } else {
                    intBearList1 = em.createNativeQuery("select sum(NoOfAno),sum(Amt) from ("
                            + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' "
                            + "and a.spflag='15' " + subquery + " and b.accstatus='15' and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                            + "('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "')) "
                            + "union all "
                            + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,td_accountmaster b where a.dt <='" + prevEndDt + "' "
                            + "and a.spflag='15' " + subquery + " and b.accstatus='15' and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                            + "('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')))f").getResultList();
                }
                double bearingbal = 0d;
                int aa = 0;
                //DeafForm1Pojo pojo1 = new DeafForm1Pojo();
                if (!intBearList1.isEmpty()) {
                    for (int i = 0; i < intBearList1.size(); i++) {
                        Vector vtr = (Vector) intBearList1.get(i);

                        aa = intBearList1.size();
                        String acno = vtr.get(0).toString();
                        aa = Integer.parseInt(acno);
                        double balance = Double.parseDouble(vtr.get(1).toString());
                        bearingbal = bearingbal + balance;
                    }
                }
                // pojo1.setsNo("1");
                String mm = effectDt.substring(4, 6);
                String yyyy = effectDt.substring(0, 4);
                //pojo1.setMonth(mm);
                // pojo1.setYear(yyyy);
                // pojo1.setParticular("Opening balance at the beginning of the month.");
                //pojo1.setIbdNoOfAc(aa);
                // pojo1.setIbdAmount(new BigDecimal(formatter.format(bearingbal)));
                /*
                 For Non - interest bearing Deposit
                 * 
                 */
                double bearingbal2 = 0d;
                int rdb = 0;
                if (bnkCode.equalsIgnoreCase("INDR")) {
                    String rddt = fts.getCodeFromCbsParameterInfo("DEAF-RD-INT-DT");
                    if (ymd.parse(rddt).after(ymd.parse(prevEndDt))) {
                        rddt = prevEndDt;
                    }
                    List intBearRd2 = em.createNativeQuery("select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b where a.dt <='" + rddt + "' "
                            + "and a.spflag='15' " + subquery + " and b.accstatus='15' and a.acno=b.acno "
                            + "and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "'))").getResultList();
                    Vector rdv = (Vector) intBearRd2.get(0);
                    rdb = Integer.parseInt(rdv.get(0).toString());
                    bearingbal2 = Double.parseDouble(rdv.get(1).toString());
                }
                List intBearingList2 = em.createNativeQuery("select count(a.acno),ifnull(sum(a.amount),0) from accountstatus a,accountmaster b where a.dt <='" + prevEndDt + "' and a.spflag='15' "
                        + "" + subquery + " and b.accstatus='15' and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag in('C','B'))").getResultList();
                int bb = 0;
                if (!intBearingList2.isEmpty()) {
                    for (int i = 0; i < intBearingList2.size(); i++) {
                        Vector vtr = (Vector) intBearingList2.get(i);
                        String acno = vtr.get(0).toString();
                        bb = Integer.parseInt(acno);
                        bb = bb + rdb;
                        double balance = Double.parseDouble(vtr.get(1).toString());
                        bearingbal2 = bearingbal2 + balance;
                    }
                }
                // pojo1.setNibdNoOfAc(bb);
                // pojo1.setNibdAmount(new BigDecimal(formatter.format(bearingbal2)));
                /*
                 For Other Credits (Non -  interest bearing)
                 * 
                 */
                List intBearingList3 = em.createNativeQuery("select count(acno),ifnull(sum(amount),0) from accountstatus where dt<= '" + prevEndDt + "' and spflag='15' " + subquery1 + " "
                        + "and substring(acno,3,2)in('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "')").getResultList();

                double bearingbal3 = 0d;

                int cc = 0;
                double glBal = 0d;
                if (!intBearingList3.isEmpty()) {
                    String acno = "", acType = "";
                    for (int i = 0; i < intBearingList3.size(); i++) {
                        Vector vtr = (Vector) intBearingList3.get(i);
                        cc = intBearingList3.size();
                        acno = vtr.get(0).toString();
                        cc = Integer.parseInt(acno);
                        bearingbal3 = Double.parseDouble(vtr.get(1).toString());
                    }
                }
                // pojo1.setOtcNoOfAc(cc);
                // pojo1.setOtcAmount(new BigDecimal(formatter.format(bearingbal3 + glBal)));

                noOfAcNo1 = aa + bb + cc;
                bal = new BigDecimal(bearingbal + bearingbal2 + bearingbal3);
                // pojo1.setNoOfAcNo(noOfAcNo1);
                // pojo1.setBal(bal);
                // dataList.add(pojo1);

                /*
                 Interest Bearing Deposit for Number of Accounts and amount, if any, inadvertently omitted in the return for the previous month and transferred during this month.
                 * 
                 */
                DeafForm1Pojo pojo2 = new DeafForm1Pojo();
                pojo2.setsNo("2");
                pojo2.setParticular("Number of Accounts and amount, if any, inadvertently omitted in the return for the previous month and transferred during this month.");
                pojo2.setIbdNoOfAc(0);
                pojo2.setIbdAmount(new BigDecimal(0));
                pojo2.setNibdNoOfAc(0);
                pojo2.setNibdAmount(new BigDecimal(0));
                pojo2.setOtcNoOfAc(0);
                pojo2.setOtcAmount(new BigDecimal(0));
                pojo2.setNoOfAcNo(0);
                pojo2.setBal(new BigDecimal(0));
                dataList.add(pojo2);

                /*
                 For Interest Bearing Deposit for  Number of Accounts and amount due and transferred to the Fund during this month.
                 * 
                 */
                int noOfAcNo3 = 0;
                BigDecimal bal3 = new BigDecimal("0");
                List acfdList;
                acfdList = em.createNativeQuery("select sum(NoOfAno),sum(Amt) from ("
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,accountmaster b "
                        + "where a.dt > '" + inputfrDt + "' and a.dt <= '" + inputToDt + "' "
                        + "and a.spflag='15' " + subquery + " and b.accstatus = 15 and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "')) "
                        + "union All "
                        + "select count(*) NoOfAno,cast(ifnull(sum(amount),0) as decimal(14,2)) Amt from accountstatus a,td_accountmaster b "
                        + "where a.dt > '" + inputfrDt + "' and a.dt <= '" + inputToDt + "' "
                        + "and a.spflag='15' " + subquery + " and b.accstatus = 15 and a.acno=b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                        + ")f").getResultList();


                Vector vtr1 = (Vector) acfdList.get(0);
                DeafForm1Pojo pojo3 = new DeafForm1Pojo();
                int a1 = Integer.parseInt(vtr1.get(0).toString());
                BigDecimal b1 = new BigDecimal(vtr1.get(1).toString());
                pojo3.setsNo("3");
                pojo3.setParticular("Number of Accounts and amount due and transferred to the Fund during this month.");
                pojo3.setIbdNoOfAc(a1);
                pojo3.setIbdAmount(b1);
                //dataList.add(pojo3);
                 /*
                 For Non - interest bearing Deposit
                 * 
                 */
                int a22 = 0;
                BigDecimal b22 = new BigDecimal("0");
                List accaList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,accountmaster b where a.dt > '" + inputfrDt + "' and a.dt <= '" + inputToDt + "' and a.spflag='15' "
                        + "" + subquery + " and b.accstatus = 15 and a.acno = b.acno and substring(a.acno,3,2)in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag in('C','B'))").getResultList();
                Vector vtr2 = (Vector) accaList.get(0);
                int a2 = Integer.parseInt(vtr2.get(0).toString());
                a2 = a2 + a22;
                BigDecimal b2 = new BigDecimal(vtr2.get(1).toString());
                b2 = b2.add(b22);
                pojo3.setNibdNoOfAc(a2);
                pojo3.setNibdAmount(b2);
                //dataList.add(pojo3);
                 /*
                 For Other Credits (Non -  interest bearing)
                 * 
                 */
                List acccList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt > '" + inputfrDt + "' and dt <= '" + inputToDt + "' and spflag='15' "
                        + "" + subquery1 + " and substring(acno,3,2)in('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "')").getResultList();
                Vector vtr3 = (Vector) acccList.get(0);
                int a3 = Integer.parseInt(vtr3.get(0).toString());
                BigDecimal b3 = new BigDecimal(vtr3.get(1).toString());
                pojo3.setOtcNoOfAc(a3);
                pojo3.setOtcAmount(b3);
                noOfAcNo3 = a1 + a2 + a3;
                bal3 = bal3.add(b1).add(b2).add(b3);
                pojo3.setNoOfAcNo(noOfAcNo3);
                pojo3.setBal(bal3);
                dataList.add(pojo3);

                /*
                 For Interest Bearing Deposit for Accounts and amount claimed during the previous month and paid from the Fund during this month.
                 * 
                 */

                int noOfAcNo4 = 0;
                BigDecimal bal4 = new BigDecimal("0");
                List claimedList1;
                claimedList1 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                        + "" + subquery1 + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
                DeafForm1Pojo pojo4 = new DeafForm1Pojo();
                Vector cvt1 = (Vector) claimedList1.get(0);
                int intBearingClaimed = Integer.parseInt(cvt1.get(0).toString());
                BigDecimal intBearingClaimedBal = new BigDecimal(cvt1.get(1).toString());
                pojo4.setsNo("4");
                pojo4.setParticular("Accounts and amount claimed during the previous month and paid from the Fund during this month.");
                pojo4.setIbdNoOfAc(intBearingClaimed);
                pojo4.setIbdAmount(intBearingClaimedBal);
                //dataList.add(pojo4);
                 /*
                 For Non - interest bearing Deposit
                 * 
                 */
                int nonIntBearingClaimed1 = 0;
                BigDecimal nonIntBearingClaimedBal1 = new BigDecimal("0");

                List claimedList2 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                        + "" + subquery1 + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag in('C','B'))").getResultList();
                Vector cvt2 = (Vector) claimedList2.get(0);
                int nonIntBearingClaimed = Integer.parseInt(cvt2.get(0).toString());
                nonIntBearingClaimed = nonIntBearingClaimed + nonIntBearingClaimed1;
                BigDecimal nonIntBearingClaimedBal = new BigDecimal(cvt2.get(1).toString());
                nonIntBearingClaimedBal = nonIntBearingClaimedBal.add(nonIntBearingClaimedBal1);
                pojo4.setNibdNoOfAc(nonIntBearingClaimed);
                pojo4.setNibdAmount(nonIntBearingClaimedBal);
                // dataList.add(pojo4);
                   /*
                 Other Credits (Non -  interest bearing)
                 * 
                 */

                List claimedList3 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' "
                        + "" + subquery1 + " and substring(acno,3,2)in('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "')").getResultList();
                Vector cvt3 = (Vector) claimedList3.get(0);
                int otherBearingClaimed = Integer.parseInt(cvt3.get(0).toString());
                BigDecimal otherBearingClaimedBal = new BigDecimal(cvt3.get(1).toString());
                pojo4.setOtcNoOfAc(otherBearingClaimed);
                pojo4.setOtcAmount(otherBearingClaimedBal);
                // dataList.add(pojo4);

                noOfAcNo4 = intBearingClaimed + nonIntBearingClaimed + otherBearingClaimed;
                bal4 = bal4.add(intBearingClaimedBal).add(nonIntBearingClaimedBal).add(otherBearingClaimedBal);
                pojo4.setNoOfAcNo(noOfAcNo4);
                pojo4.setBal(bal4);
                dataList.add(pojo4);

                /*
                 Total amount transferred less amount paid from the Fund during the month.(2+3-4)
                 * 
                 */
                DeafForm1Pojo pojo5 = new DeafForm1Pojo();
                pojo5.setsNo("5");
                pojo5.setParticular("Total amount transferred less amount paid from the Fund during the month.(2+3-4)");
                int netIbdAcno = (0 + a1) - intBearingClaimed;
                pojo5.setIbdNoOfAc((0 + a1) - intBearingClaimed);
                double netIbdAmt = (0 + b1.doubleValue()) - intBearingClaimedBal.doubleValue();
                pojo5.setIbdAmount(new BigDecimal(formatter.format((0 + b1.doubleValue()) - intBearingClaimedBal.doubleValue())));
                int netNibdAcno = (0 + a2) - nonIntBearingClaimed;
                pojo5.setNibdNoOfAc((0 + a2) - nonIntBearingClaimed);
                double netNibdAmt = (0 + b2.doubleValue()) - nonIntBearingClaimedBal.doubleValue();
                pojo5.setNibdAmount(new BigDecimal(formatter.format((0 + b2.doubleValue()) - nonIntBearingClaimedBal.doubleValue())));
                int netOtherAcno = (0 + a3) - otherBearingClaimed;
                pojo5.setOtcNoOfAc((0 + a3) - otherBearingClaimed);
                double netOtherAmt = (0 + b3.doubleValue()) - otherBearingClaimedBal.doubleValue();
                pojo5.setOtcAmount(new BigDecimal(formatter.format((0 + b3.doubleValue()) - otherBearingClaimedBal.doubleValue())));
                int netAcno = (0 + noOfAcNo3) - noOfAcNo4;
                pojo5.setNoOfAcNo((0 + noOfAcNo3) - noOfAcNo4);
                double netBal = (0 + bal3.doubleValue()) - bal4.doubleValue();
                pojo5.setBal(new BigDecimal(formatter.format((0 + bal3.doubleValue()) - bal4.doubleValue())));
                dataList.add(pojo5);

                /*
                 Total amount with the Fund at the end of the ….…(month) 20…..
                 * 
                 */

                DeafForm1Pojo pojo6 = new DeafForm1Pojo();
                pojo6.setsNo("6");
                pojo6.setParticular("Total amount with the Fund at the end of the ….…(month) 20…..");
                pojo6.setIbdNoOfAc(aa + netIbdAcno);
                pojo6.setIbdAmount(new BigDecimal(formatter.format(bearingbal + netIbdAmt)));

                pojo6.setNibdNoOfAc(bb + netNibdAcno);
                pojo6.setNibdAmount(new BigDecimal(formatter.format(bearingbal2 + netNibdAmt)));

                pojo6.setOtcNoOfAc(cc + netOtherAcno);
                pojo6.setOtcAmount(new BigDecimal(formatter.format(bearingbal3 + netOtherAmt)));

                pojo6.setNoOfAcNo(noOfAcNo1 + netAcno);
                pojo6.setBal(new BigDecimal(formatter.format(bal.doubleValue() + netBal)));
                dataList.add(pojo6);
            } else if (repType.equalsIgnoreCase("Form III")) {
                String subquery2 = inputBr.equalsIgnoreCase("0A") ? "" : "and substring(b.acno,1,2) = '" + inputBr + "'";
                //As on dt change to month first dt to last dt
                List form3List = em.createNativeQuery("select a.acno,date_format(a.dt,'%Y%m%d'),a.amount,b.claimed_amount,date_format(b.paid_dt,'%Y%m%d'),"
                        + "c.custname,date_format(b.deaf_dt,'%d/%m/%Y'),ifnull(Deaf_Roi,0) from accountstatus a, cbs_claimed_details b,accountmaster c where b.acno = a.acno " + subquery2 + " "
                        + "and b.acno = c.acno and b.paid_dt between '" + inputfrDt + "' and '" + inputToDt + "'and a.dt >='" + inputfrDt + "' "
                        + "union "
                        + "select a.acno,date_format(a.dt,'%Y%m%d'),a.amount,b.claimed_amount,date_format(b.paid_dt,'%Y%m%d'),c.custname,date_format(b.deaf_dt,'%d/%m/%Y'),ifnull(Deaf_Roi,0) "
                        + "from accountstatus a, cbs_claimed_details b,td_accountmaster c where b.acno = a.acno " + subquery2 + " "
                        + "and b.acno = c.acno and b.paid_dt between '" + inputfrDt + "' and '" + inputToDt + "'and a.dt >='" + inputfrDt + "'").getResultList();

                if (!form3List.isEmpty()) {
                    for (int i = 0; i < form3List.size(); i++) {
                        Vector vtr = (Vector) form3List.get(i);
                        DeafForm1Pojo pojo = new DeafForm1Pojo();
                        String acNo = vtr.get(0).toString();
                        String acNature = fts.getAcNatureByCode(acNo.substring(2, 4));
                        String effDt = vtr.get(1).toString();
                        String acRoi = "0";
                        if (!(acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC))) {
                            String schemeCode = loanReportFacade.getSchemeCodeAcNoWise(acNo);
                            acRoi = acOpenRemote.getROI(schemeCode, Double.parseDouble(vtr.get(2).toString()), effDt);
                        }
                        String claimDt = vtr.get(4).toString();
                        String name = vtr.get(5).toString();
                        String deafDt = vtr.get(6).toString();
                        List noOfYmdList = CbsUtil.getYrMonDayDiff(ymd.format(dmyy.parse(deafDt)), claimDt);
                        String noOfYmd = noOfYmdList.get(0).toString() + "/" + noOfYmdList.get(1).toString() + "/" + noOfYmdList.get(2).toString();
                        String deafRoi = vtr.get(7).toString();
                        if (deafRoi.equalsIgnoreCase("0")) {
                            deafRoi = acRoi;
                        }
                        List deafAmtList = em.createNativeQuery("select ifnull(AMOUNT,0)from accountstatus where acno = '" + acNo + "' and spflag = 15 and dt = \n"
                                + "(select date_format(max(dt),'%Y%m%d') from accountstatus where acno = '" + acNo + "' and spflag = 15)\n"
                                + "").getResultList();
                        Vector dfVector = (Vector) deafAmtList.get(0);
                        double deafAmt = Double.parseDouble(dfVector.get(0).toString());
                        //double balance = common.getBalanceOnDate(acNo, effectDt);
                        pojo.setCustName(name + " / " + acNo);
                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.MS_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.TD_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            pojo.setAcType("Interest Bearing");
                        } else {
                            pojo.setAcType("Non-Interest Bearing");
                        }
                        //pojo.setAcType(acNo);
                        pojo.setPeriod(noOfYmd);
                        pojo.setIntRate(Double.parseDouble(deafRoi));
                        // pojo.setDeafDate(vtr.get(1).toString().substring(8, 10) + "/" + vtr.get(1).toString().substring(5, 7) + "/" + vtr.get(1).toString().substring(0, 4));
                        pojo.setDeafDate(deafDt);
                        pojo.setDeafAmt(deafAmt);
                        pojo.setClaimAmt(Double.parseDouble(vtr.get(3).toString()));
                        pojo.setPaidDate(claimDt.substring(6, 8) + "/" + claimDt.substring(4, 6) + "/" + claimDt.substring(0, 4));
                        pojo.setDiffclaimAmtDeafAmt(Double.parseDouble(vtr.get(3).toString()) - deafAmt);
                        dataList.add(pojo);

                    }
                }
            } else if (repType.equalsIgnoreCase("Form IV")) {
                DeafForm1Pojo pojo1 = new DeafForm1Pojo();
                pojo1.setsNo("2");
                pojo1.setParticular("Details of claim made during the month");
                dataList.add(pojo1);

                DeafForm1Pojo pojo2 = new DeafForm1Pojo();
                pojo2.setParticular("a) Interest bearing Claim");
                dataList.add(pojo2);

                List acnoClaimList = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where  deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' " + subquery + " and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in "
                        + "('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();

                Vector vtr = (Vector) acnoClaimList.get(0);
                DeafForm1Pojo pojo3 = new DeafForm1Pojo();
                int calimIntBeAcno = Integer.parseInt(vtr.get(0).toString());
                BigDecimal claimPriAmt = new BigDecimal(vtr.get(1).toString());
                pojo3.setParticular("  (i) From the principal amount transferred to DEAF Account");
                pojo3.setIbdNoOfAc(calimIntBeAcno);
                pojo3.setClaimPriAmt(claimPriAmt);
                dataList.add(pojo3);

//                List intBearingList = em.createNativeQuery("select count(*),ifnull(sum(CrAmt),0) from recon where acno in(select acno from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' " + subquery + " and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in "
//                        + "('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))) and dt >= '" + inputToDt + "' and trandesc = 8 and details like '%in claimed%'").getResultList();             
                List intBearingList = em.createNativeQuery("select sum(acNo),sum(intAmt) from(\n"
                        + "select count(*) acNo,ifnull(sum(CrAmt),0) intAmt from recon where acno in(select acno from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' " + subquery + " "
                        + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "'))) \n"
                        + "and dt >= '" + inputToDt + "' and trandesc = 8 and details like '%in claimed%'\n"
                        + "union all\n"
                        + "select count(*) acNo,ifnull(sum(CrAmt),0) intAmt from td_recon where acno in(select acno from cbs_claimed_details where deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' " + subquery + " "
                        + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))) \n"
                        + "and dt >= '" + inputToDt + "' and trandesc = 8 and details like '%in claimed%'\n"
                        + ")f").getResultList();

                Vector vtr1 = (Vector) intBearingList.get(0);
                DeafForm1Pojo pojo4 = new DeafForm1Pojo();
                int claimIntAcno = Integer.parseInt(vtr1.get(0).toString());
                BigDecimal claimIntAmt = new BigDecimal(vtr1.get(1).toString());
                pojo4.setParticular("  (ii) Interest claimed from the Fund");
                pojo4.setIbdNoOfAc(claimIntAcno);
                pojo4.setClaimPriAmt(claimIntAmt);
                dataList.add(pojo4);

                DeafForm1Pojo pojo5 = new DeafForm1Pojo();
                pojo5.setParticular("  (iii) Gross Claim ( i + ii)");
                pojo5.setIbdNoOfAc(calimIntBeAcno + claimIntAcno);
                pojo5.setClaimPriAmt(claimPriAmt.add(claimIntAmt));
                dataList.add(pojo5);

                DeafForm1Pojo pojo6 = new DeafForm1Pojo();
                pojo6.setParticular("b) Non-Interest bearing Claim");
                dataList.add(pojo6);

                List nonIntBearingList = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where  deaf_dt between '" + inputfrDt + "' and '" + inputToDt + "' " + subquery + " and substring(acno,3,2)= '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "'").getResultList();
                Vector vtr2 = (Vector) nonIntBearingList.get(0);
                DeafForm1Pojo pojo7 = new DeafForm1Pojo();
                int calimNonIntBeAcno = Integer.parseInt(vtr2.get(0).toString());
                BigDecimal claimNonIntPriAmt = new BigDecimal(vtr2.get(1).toString());
                pojo7.setParticular("  (i) From the Principal amount transferred to DEAF Account");
                pojo7.setIbdNoOfAc(calimNonIntBeAcno);
                pojo7.setClaimPriAmt(claimNonIntPriAmt);
                dataList.add(pojo7);

                DeafForm1Pojo pojo8 = new DeafForm1Pojo();
                pojo8.setsNo("3");
                pojo8.setParticular("Total claim from Fund(2.(a) (iii) +2 (b) (i))");
                pojo8.setIbdNoOfAc(calimIntBeAcno + claimIntAcno + calimNonIntBeAcno);
                pojo8.setClaimPriAmt(claimPriAmt.add(claimIntAmt).add(claimNonIntPriAmt));
                dataList.add(pojo8);

            } else if (repType.equalsIgnoreCase("Form V")) {

                String prevStartingYear = "", prevEndingYear = "";
                String curStartingYear = "", curEndingYear = "";

                List prevYearList = em.createNativeQuery("SELECT YEAR('" + inputToDt + "') - 1 ").getResultList();
                Vector elePer = (Vector) prevYearList.get(0);
                List curYearList = em.createNativeQuery("SELECT YEAR('" + inputToDt + "')").getResultList();
                Vector ele2Cur = (Vector) curYearList.get(0);

                prevStartingYear = elePer.get(0).toString() + "0101";
                prevEndingYear = elePer.get(0).toString() + "1231";

                curStartingYear = ele2Cur.get(0).toString() + "0101";
                curEndingYear = ele2Cur.get(0).toString() + "1231";

//                List finPrvList = em.createNativeQuery("select distinct MINDATE as finFirstDt,MAXDATE as finLastDt from yearend where f_year = 2014").getResultList();
//                Vector finPrvVector = (Vector) finPrvList.get(0);
//                prevStartingYear = finPrvVector.get(0).toString();
//                prevEndingYear = finPrvVector.get(1).toString();
//
//                List finEndList = em.createNativeQuery("select distinct MINDATE as finFirstDt,MAXDATE as finLastDt from yearend where f_year = 2015").getResultList();
//                Vector finEndVector = (Vector) finEndList.get(0);
//                curStartingYear = finEndVector.get(0).toString();
//                curEndingYear = finEndVector.get(1).toString();

                //**Current deposit accounts
                List listPrev1 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,accountmaster b where a.spflag = 15 " + subQueryv + " "
                        + "and cast(a.spflag as unsigned) = b.accstatus and a.acno = b.acno and a.dt between '" + prevStartingYear + "' and '" + prevEndingYear + "' and b.accttype in"
                        + "(select acctcode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C')").getResultList();
                // List listPrev1 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where effdt between '" + prevStartingYear + "' and '" + prevEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "'))").getResultList();
                Vector vtrPer1 = (Vector) listPrev1.get(0);
                List listCur1 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,accountmaster b where a.spflag = 15 " + subQueryv + " "
                        + "and cast(a.spflag as unsigned) = b.accstatus and a.acno = b.acno and a.dt between '" + curStartingYear + "' and '" + curEndingYear + "' and b.accttype in"
                        + "(select acctcode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C')").getResultList();
                //List listCur1 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where effdt between '" + curStartingYear + "' and '" + curStartingYear + "' " + subquery + " and spflag='15' and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "'))").getResultList();
                Vector vtrCur1 = (Vector) listCur1.get(0);
                DeafForm1Pojo pojo1 = new DeafForm1Pojo();
                pojo1.setParticular("Current deposit accounts");
                pojo1.setPrevYearAcno(Integer.parseInt(vtrPer1.get(0).toString()));
                pojo1.setPrevYearAmt(new BigDecimal(vtrPer1.get(1).toString()));
                pojo1.setCurYearAcno(Integer.parseInt(vtrCur1.get(0).toString()));
                pojo1.setCurYearAmt(new BigDecimal(vtrCur1.get(1).toString()));
                dataList.add(pojo1);
                //**Savings bank deposit accounts
                List listPrev2 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,accountmaster b where a.spflag = 15 " + subQueryv + " "
                        + "and cast(a.spflag as unsigned) = b.accstatus and a.acno = b.acno and a.dt between '" + prevStartingYear + "' and '" + prevEndingYear + "' and b.accttype in"
                        + "(select acctcode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "' and CrDbFlag = 'C')").getResultList();
                //List listPrev2 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where effdt between '" + prevStartingYear + "' and '" + prevEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "'))").getResultList();
                Vector vtrPer2 = (Vector) listPrev2.get(0);
                List listCur2 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,accountmaster b where a.spflag = 15 " + subQueryv + " "
                        + "and cast(a.spflag as unsigned) = b.accstatus and a.acno = b.acno and a.dt between '" + curStartingYear + "' and '" + curEndingYear + "' and b.accttype in"
                        + "(select acctcode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "' and CrDbFlag = 'C')").getResultList();
                // List listCur2 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where effdt between '" + curStartingYear + "' and '" + curEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "'))").getResultList();
                Vector vtrCur2 = (Vector) listCur2.get(0);
                DeafForm1Pojo pojo2 = new DeafForm1Pojo();
                pojo2.setParticular("Savings bank deposit accounts");
                pojo2.setPrevYearAcno(Integer.parseInt(vtrPer2.get(0).toString()));
                pojo2.setPrevYearAmt(new BigDecimal(vtrPer2.get(1).toString()));
                pojo2.setCurYearAcno(Integer.parseInt(vtrCur2.get(0).toString()));
                pojo2.setCurYearAmt(new BigDecimal(vtrCur2.get(1).toString()));
                dataList.add(pojo2);
                //**Fixed or term deposit accounts
                List listPrev3 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,td_accountmaster b where a.spflag = 15 and cast(a.spflag as unsigned) = b.accstatus and a.acno = b.acno and a.dt between '" + prevStartingYear + "' and '" + prevEndingYear + "' " + subQueryv + "  and b.accttype in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
                // List listPrev3 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where effdt between '" + prevStartingYear + "' and '" + prevEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
                Vector vtrPer3 = (Vector) listPrev3.get(0);
                List listCur3 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus a,td_accountmaster b where a.spflag = 15 and cast(a.spflag as unsigned) = b.accstatus and a.acno = b.acno and a.dt between '" + curStartingYear + "' and '" + curEndingYear + "' " + subQueryv + "  and b.accttype in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
                //List listCur3 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where effdt between '" + curStartingYear + "' and '" + curEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))").getResultList();
                Vector vtrCur3 = (Vector) listCur3.get(0);
                DeafForm1Pojo pojo3 = new DeafForm1Pojo();
                pojo3.setParticular("Fixed or term deposit accounts");
                pojo3.setPrevYearAcno(Integer.parseInt(vtrPer3.get(0).toString()));
                pojo3.setPrevYearAmt(new BigDecimal(vtrPer3.get(1).toString()));
                pojo3.setCurYearAcno(Integer.parseInt(vtrCur3.get(0).toString()));
                pojo3.setCurYearAmt(new BigDecimal(vtrCur3.get(1).toString()));
                dataList.add(pojo3);
                //**Other deposit accounts in any form or with any name
                DeafForm1Pojo pojo4 = new DeafForm1Pojo();
                pojo4.setParticular("Other deposit accounts in any form or with any name");
                pojo4.setPrevYearAcno(0);
                pojo4.setPrevYearAmt(new BigDecimal("0"));
                pojo4.setCurYearAcno(0);
                pojo4.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo4);
                //**Credit balance in Cash credit accounts
                List listPrev4 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where substring(acno,3,2) = '" + CbsAcCodeConstant.CASH_CREDIT.getAcctCode() + "' " + subquery + " and deaf_dt between '" + prevStartingYear + "' and '" + prevEndingYear + "'").getResultList();
                Vector vtrPer4 = (Vector) listPrev4.get(0);
                List listCur4 = em.createNativeQuery("select count(*),ifnull(sum(claimed_amount),0) from cbs_claimed_details where substring(acno,3,2) = '" + CbsAcCodeConstant.CASH_CREDIT.getAcctCode() + "' " + subquery + " and deaf_dt between '" + curStartingYear + "' and '" + curEndingYear + "'").getResultList();
                Vector vtrCur4 = (Vector) listCur4.get(0);
                DeafForm1Pojo pojo5 = new DeafForm1Pojo();
                pojo5.setParticular("Credit balance in Cash credit accounts");
                pojo5.setPrevYearAcno(Integer.parseInt(vtrPer4.get(0).toString()));
                pojo5.setPrevYearAmt(new BigDecimal(vtrPer4.get(1).toString()));
                pojo5.setCurYearAcno(Integer.parseInt(vtrCur4.get(0).toString()));
                pojo5.setCurYearAmt(new BigDecimal(vtrCur4.get(1).toString()));
                dataList.add(pojo5);
                //**Margin Money against issue of Letter of Credit/Guarantee etc. or any Security deposit
                DeafForm1Pojo pojo6 = new DeafForm1Pojo();
                pojo6.setParticular("Margin Money against issue of Letter of Credit/Guarantee etc. or any Security deposit");
                pojo6.setPrevYearAcno(0);
                pojo6.setPrevYearAmt(new BigDecimal("0"));
                pojo6.setCurYearAcno(0);
                pojo6.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo6);
                //**Outstanding telegraphic transfers
                DeafForm1Pojo pojo7 = new DeafForm1Pojo();
                pojo7.setParticular("Outstanding telegraphic transfers");
                pojo7.setPrevYearAcno(0);
                pojo7.setPrevYearAmt(new BigDecimal("0"));
                pojo7.setCurYearAcno(0);
                pojo7.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo7);
                //**Mail transfers
                DeafForm1Pojo pojo8 = new DeafForm1Pojo();
                pojo8.setParticular("Mail transfers");
                pojo8.setPrevYearAcno(0);
                pojo8.setPrevYearAmt(new BigDecimal("0"));
                pojo8.setCurYearAcno(0);
                pojo8.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo8);
                //**Demand drafts
                List demandList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + prevStartingYear + "' and '" + prevEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,8)in(select glhead from billtypemaster where InstNature = 'DD')").getResultList();
                Vector demPrvVector = (Vector) demandList.get(0);
                List demandList1 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + curStartingYear + "' and '" + curEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,8)in(select glhead from billtypemaster where InstNature = 'DD')").getResultList();
                Vector demEndVector = (Vector) demandList1.get(0);
                DeafForm1Pojo pojo9 = new DeafForm1Pojo();
                pojo9.setParticular("Demand drafts");
                pojo9.setPrevYearAcno(Integer.parseInt(demPrvVector.get(0).toString()));
                pojo9.setPrevYearAmt(new BigDecimal(demPrvVector.get(1).toString()));
                pojo9.setCurYearAcno(Integer.parseInt(demEndVector.get(0).toString()));
                pojo9.setCurYearAmt(new BigDecimal(demEndVector.get(1).toString()));
                dataList.add(pojo9);
                //**Pay orders
                List payList = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + prevStartingYear + "' and '" + prevEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,8)= (select glhead from billtypemaster where InstNature = 'PO')").getResultList();
                Vector payPrvVector = (Vector) payList.get(0);
                List payList1 = em.createNativeQuery("select count(*),cast(ifnull(sum(amount),0) as decimal(14,2)) from accountstatus where dt between '" + curStartingYear + "' and '" + curEndingYear + "' " + subquery + " and spflag='15' and substring(acno,3,8)= (select glhead from billtypemaster where InstNature = 'PO')").getResultList();
                Vector payEndVector = (Vector) payList1.get(0);
                DeafForm1Pojo pojo10 = new DeafForm1Pojo();
                pojo10.setParticular("Pay orders");
                pojo10.setPrevYearAcno(Integer.parseInt(payPrvVector.get(0).toString()));
                pojo10.setPrevYearAmt(new BigDecimal(payPrvVector.get(1).toString()));
                pojo10.setCurYearAcno(Integer.parseInt(payEndVector.get(0).toString()));
                pojo10.setCurYearAmt(new BigDecimal(payEndVector.get(1).toString()));
                dataList.add(pojo10);
                //**Bankers cheques
                DeafForm1Pojo pojo11 = new DeafForm1Pojo();
                pojo11.setParticular("Bankers cheques");
                pojo11.setPrevYearAcno(0);
                pojo11.setPrevYearAmt(new BigDecimal("0"));
                pojo11.setCurYearAcno(0);
                pojo11.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo11);
                //**Sundry deposit accounts
                DeafForm1Pojo pojo12 = new DeafForm1Pojo();
                pojo12.setParticular("Sundry deposit accounts");
                pojo12.setPrevYearAcno(0);
                pojo12.setPrevYearAmt(new BigDecimal("0"));
                pojo12.setCurYearAcno(0);
                pojo12.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo12);
                //**Vostro accounts
                DeafForm1Pojo pojo13 = new DeafForm1Pojo();
                pojo13.setParticular("Vostro accounts");
                pojo13.setPrevYearAcno(0);
                pojo13.setPrevYearAmt(new BigDecimal("0"));
                pojo13.setCurYearAcno(0);
                pojo13.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo13);
                //**Inter-bank clearing adjustments credit
                DeafForm1Pojo pojo14 = new DeafForm1Pojo();
                pojo14.setParticular("Inter-bank clearing adjustments credit");
                pojo14.setPrevYearAcno(0);
                pojo14.setPrevYearAmt(new BigDecimal("0"));
                pojo14.setCurYearAcno(0);
                pojo14.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo14);
                //**Unadjusted National Electronic Funds Transfer (NEFT) credit balances
                DeafForm1Pojo pojo15 = new DeafForm1Pojo();
                pojo15.setParticular("Unadjusted National Electronic Funds Transfer (NEFT) credit balances");
                pojo15.setPrevYearAcno(0);
                pojo15.setPrevYearAmt(new BigDecimal("0"));
                pojo15.setCurYearAcno(0);
                pojo15.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo15);
                //**Other such transitory accounts credit
                DeafForm1Pojo pojo16 = new DeafForm1Pojo();
                pojo16.setParticular("Other such transitory accounts credit");
                pojo16.setPrevYearAcno(0);
                pojo16.setPrevYearAmt(new BigDecimal("0"));
                pojo16.setCurYearAcno(0);
                pojo16.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo16);
                //**Unreconciled credit balances on account of Automated Teller Machine (ATM) transactions
                DeafForm1Pojo pojo17 = new DeafForm1Pojo();
                pojo17.setParticular("Unreconciled credit balances on account of Automated Teller Machine (ATM) transactions");
                pojo17.setPrevYearAcno(0);
                pojo17.setPrevYearAmt(new BigDecimal("0"));
                pojo17.setCurYearAcno(0);
                pojo17.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo17);
                //**Undrawn balance amounts remaining in any prepaid card issued by banks
                DeafForm1Pojo pojo18 = new DeafForm1Pojo();
                pojo18.setParticular("Undrawn balance amounts remaining in any prepaid card issued by banks");
                pojo18.setPrevYearAcno(0);
                pojo18.setPrevYearAmt(new BigDecimal("0"));
                pojo18.setCurYearAcno(0);
                pojo18.setCurYearAmt(new BigDecimal("0"));
                dataList.add(pojo18);
            } else if (repType.equalsIgnoreCase("Form VIII")) {
                String branch = "";
                if (inputBr.equalsIgnoreCase("0A")) {
                    branch = "";
                } else {
                    branch = "and a.curbrcode ='" + inputBr + "'";
                }

                String curYearDt = CbsUtil.yearAdd(inputToDt, -10);
                String inputToDtpv = CbsUtil.yearAdd(inputToDt, -1);
                String prevYearfrDt = CbsUtil.yearAdd(inputToDtpv, -10);
                String inputFromDt = inputToDt.substring(0, 4) + "0101";

                //Add Balance brought forward from the previous return
                int caNo1 = 0, sbNo1 = 0, fdNo1 = 0, othetNo1 = 0, rdNo1 = 0, ddsNo1 = 0, glNo1 = 0, totalNo1 = 0;
                double caBal1 = 0, sbBal1 = 0, fdBal1 = 0, otherAmt1 = 0, rdBal1 = 0, ddsBal1 = 0, glAmt1 = 0, totalBal1 = 0;
                DeafForm1Pojo pojo1 = new DeafForm1Pojo();
                //For Current Accounts
                List listca1 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDtpv + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "'").getResultList();
                if (!listca1.isEmpty()) {
                    caNo1 = listca1.size();
                }
                List listca2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ca_recon r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDtpv + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDtpv + "'").getResultList();
                if (!listca2.isEmpty()) {
                    Vector caVector = (Vector) listca2.get(0);
                    caBal1 = Math.abs(Double.parseDouble(caVector.get(0).toString()));
                }
                //For Saving Accounts
                List listsb1 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDtpv + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "'").getResultList();
                if (!listsb1.isEmpty()) {
                    sbNo1 = listsb1.size();
                }
                List listsb2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from recon r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDtpv + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDtpv + "'").getResultList();
                if (!listsb2.isEmpty()) {
                    Vector sbVector = (Vector) listsb2.get(0);
                    sbBal1 = Double.parseDouble(sbVector.get(0).toString());
                }
                //For Fixed deposit Accounts
                List listfd1 = em.createNativeQuery("select distinct a.acno from td_vouchmst v,td_accountmaster a where matdt<= '" + prevYearfrDt + "' "
                        + "" + branch + " and status = 'A' and v.acno = a.acno "
                        + "and a.accttype in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                        + "and a.accstatus<>15").getResultList();
                if (!listfd1.isEmpty()) {
                    fdNo1 = listfd1.size();
                }
                List listfd2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from td_recon r,"
                        + "(select distinct a.acno from td_vouchmst v,td_accountmaster a  where matdt<= '" + prevYearfrDt + "' " + branch + "   "
                        + "and status = 'A' and v.acno = a.acno and a.accttype in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) and a.accstatus<>15) c where r.acno = c.acno and r.closeflag is null "
                        + "and r.trantype<>27 and r.dt <= '" + inputToDtpv + "'").getResultList();
                if (!listfd2.isEmpty()) {
                    Vector fdVector = (Vector) listfd2.get(0);
                    fdBal1 = Double.parseDouble(fdVector.get(0).toString());
                }
                //For Other Deposit Accounts
                List listgl1 = em.createNativeQuery("select distinct GLHEAD from billtypemaster").getResultList();
                if (!listgl1.isEmpty()) {
                    for (int i = 0; i < listgl1.size(); i++) {
                        Vector glVector = (Vector) listgl1.get(i);
                        String glHead = glVector.get(0).toString();
                        List listgl2 = em.createNativeQuery("select GLHEAD,InstNature from billtypemaster where GLHEAD = '" + glHead + "' group by GLHEAD,InstNature").getResultList();
                        if (!listgl2.isEmpty()) {
                            for (int j = 0; j < listgl2.size(); j++) {
                                glVector = (Vector) listgl2.get(j);
                                glHead = glVector.get(0).toString();
                                String glNature = glVector.get(1).toString();
                                String glacNo = inputBr + glHead + "01";
                                String glBranch = "";
                                if (inputBr.equalsIgnoreCase("0A")) {
                                    glBranch = "and substring(acno,3,8) = '" + glHead + "'";
                                } else {
                                    glBranch = "and acno = '" + glacNo + "'";
                                }
                                List result = new ArrayList();
                                if (glNature.equalsIgnoreCase("PO")) {
                                    result = em.createNativeQuery("select count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) from bill_po where status = 'Issued' " + glBranch + " and ORIGINDT <= '" + prevYearfrDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("DD")) {
                                    result = em.createNativeQuery("select count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) from bill_dd where status = 'Issued' " + glBranch + " and ORIGINDT <= '" + prevYearfrDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("AD")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_ad where status = 'Issued' " + glBranch + " and origindt <= '" + prevYearfrDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("TPO")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_tpo where status = 'Issued' " + glBranch + " and origindt <= '" + prevYearfrDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("SUN")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_sundry where status = 'Issued' " + glBranch + " and origindt <= '" + prevYearfrDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("SUP")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_suspense where status = 'Issued' " + glBranch + " and origindt <= '" + prevYearfrDt + "' order by origindt").getResultList();
                                }
                                if (!result.isEmpty()) {
                                    Vector ele = (Vector) result.get(0);
                                    int glAcno1 = Integer.parseInt(ele.get(0).toString());
                                    glNo1 = glNo1 + glAcno1;
                                    double Amt1 = Double.parseDouble(ele.get(1).toString());
                                    glAmt1 = glAmt1 + Amt1;
                                }
                            }
                        }
                    }
                    System.out.println("No of glPv" + glNo1 + "; Gl AmtPv" + glAmt1);
                }
                //For Recuuring Accounts
                List listrd1 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDtpv + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "'").getResultList();
                if (!listrd1.isEmpty()) {
                    rdNo1 = listrd1.size();
                }
                List listrd2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from rdrecon r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDtpv + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDtpv + "'").getResultList();
                if (!listrd2.isEmpty()) {
                    Vector rdVector = (Vector) listrd2.get(0);
                    rdBal1 = Double.parseDouble(rdVector.get(0).toString());
                }
                //For dds Accounts
                List listds1 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDtpv + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "'").getResultList();
                if (!listds1.isEmpty()) {
                    ddsNo1 = listds1.size();
                }
                List listds2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ddstransaction r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDtpv + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + prevYearfrDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDtpv + "'").getResultList();
                if (!listds2.isEmpty()) {
                    Vector ddsVector = (Vector) listds2.get(0);
                    ddsBal1 = Double.parseDouble(ddsVector.get(0).toString());
                }
                othetNo1 = glNo1 + rdNo1 + ddsNo1;
                otherAmt1 = glAmt1 + rdBal1 + ddsBal1;

                totalNo1 = caNo1 + sbNo1 + fdNo1 + othetNo1;
                totalBal1 = caBal1 + sbBal1 + fdBal1 + otherAmt1;

                pojo1.setParticular("Add Balance brought forward from the previous return");
                pojo1.setCaNo(caNo1);
                pojo1.setCaBal(caBal1);
                pojo1.setSbNo(sbNo1);
                pojo1.setSbBal(sbBal1);
                pojo1.setFdNo(fdNo1);
                pojo1.setFdBal(fdBal1);

                pojo1.setOtherNo(othetNo1);
                pojo1.setOtherBal(otherAmt1);

                pojo1.setTotalNo(totalNo1);
                pojo1.setTotalBal(totalBal1);

                dataList.add(pojo1);
                //End of Add Balance brought forward from the previous return
                //Add Accounts,if any inadvertantly omitted in the previous return
                DeafForm1Pojo pojo2 = new DeafForm1Pojo();
                pojo2.setParticular("Add Accounts,if any inadvertantly omitted in the previous return");
                pojo2.setCaNo(0);
                pojo2.setCaBal(0);
                pojo2.setSbNo(0);
                pojo2.setSbBal(0);
                pojo2.setFdNo(0);
                pojo2.setFdBal(0);
                pojo2.setOtherNo(0);
                pojo2.setOtherBal(0);
                pojo2.setTotalNo(0);
                pojo2.setTotalBal(0);
                dataList.add(pojo2);
                //End of Add Accounts,if any inadvertantly omitted in the previous return
                //Add during the year
                int caNo2 = 0, sbNo2 = 0, fdNo2 = 0, othetNo2 = 0, rdNo2 = 0, ddsNo2 = 0, glNo2 = 0, totalNo2 = 0;
                double caBal2 = 0, sbBal2 = 0, fdBal2 = 0, otherAmt2 = 0, rdBal2 = 0, ddsBal2 = 0, glAmt2 = 0, totalBal2 = 0;
                DeafForm1Pojo pojo3 = new DeafForm1Pojo();
                //For Current Accounts
                List listca11 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "'").getResultList();
                if (!listca11.isEmpty()) {
                    caNo2 = listca11.size();
                }
                List listca22 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ca_recon r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDt + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!listca22.isEmpty()) {
                    Vector caVector = (Vector) listca22.get(0);
                    caBal2 = Math.abs(Double.parseDouble(caVector.get(0).toString()));
                }
                //For Saving Accounts
                List listsb11 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "'").getResultList();
                if (!listsb11.isEmpty()) {
                    sbNo2 = listsb11.size();
                }
                List listsb22 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from recon r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDt + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!listsb22.isEmpty()) {
                    Vector sbVector = (Vector) listsb22.get(0);
                    sbBal2 = Double.parseDouble(sbVector.get(0).toString());
                }
                //For Fixed deposit Accounts
                List listfd11 = em.createNativeQuery("select distinct a.acno from td_vouchmst v,td_accountmaster a  where matdt<= '" + curYearDt + "' "
                        + "" + branch + " and status = 'A' and v.acno = a.acno "
                        + "and a.accttype in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                        + "and a.accstatus<>15").getResultList();
                if (!listfd11.isEmpty()) {
                    fdNo2 = listfd11.size();
                }

                List listfd22 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from td_recon r,"
                        + "(select distinct a.acno from td_vouchmst v,td_accountmaster a  where matdt<= '" + curYearDt + "' " + branch + " "
                        + "and status = 'A' and v.acno = a.acno and a.accttype in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) and a.accstatus<>15) c where r.acno = c.acno and r.closeflag is null "
                        + "and r.trantype<>27 and r.dt <= '" + inputToDt + "'").getResultList();
                if (!listfd22.isEmpty()) {
                    Vector fdVector = (Vector) listfd22.get(0);
                    fdBal2 = Double.parseDouble(fdVector.get(0).toString());
                }
                //For Other Deposit Accounts
                List listgl11 = em.createNativeQuery("select distinct GLHEAD from billtypemaster").getResultList();
                if (!listgl11.isEmpty()) {
                    for (int i = 0; i < listgl11.size(); i++) {
                        Vector glVector = (Vector) listgl11.get(i);
                        String glHead = glVector.get(0).toString();
                        List listgl2 = em.createNativeQuery("select GLHEAD,InstNature from billtypemaster where GLHEAD = '" + glHead + "' group by GLHEAD,InstNature").getResultList();
                        if (!listgl2.isEmpty()) {
                            for (int j = 0; j < listgl2.size(); j++) {
                                glVector = (Vector) listgl2.get(j);
                                glHead = glVector.get(0).toString();
                                String glNature = glVector.get(1).toString();
                                String glacNo = inputBr + glHead + "01";
                                String glBranch = "";
                                if (inputBr.equalsIgnoreCase("0A")) {
                                    glBranch = "and substring(acno,3,8) = '" + glHead + "'";
                                } else {
                                    glBranch = "and acno = '" + glacNo + "'";
                                }
                                List result = new ArrayList();
                                if (glNature.equalsIgnoreCase("PO")) {
                                    result = em.createNativeQuery("select count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) from bill_po where status = 'Issued' " + glBranch + " and ORIGINDT <= '" + curYearDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("DD")) {
                                    result = em.createNativeQuery("select count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) from bill_dd where status = 'Issued' " + glBranch + " and ORIGINDT <= '" + curYearDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("AD")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_ad where status = 'Issued' " + glBranch + " and origindt <= '" + curYearDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("TPO")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_tpo where status = 'Issued' " + glBranch + " and origindt <= '" + curYearDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("SUN")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_sundry where status = 'Issued' " + glBranch + " and origindt <= '" + curYearDt + "' order by origindt").getResultList();
                                } else if (glNature.equalsIgnoreCase("SUP")) {
                                    result = em.createNativeQuery("SELECT count(acno),cast(ifnull(sum(amount),0) as decimal(25,2)) FROM bill_suspense where status = 'Issued' " + glBranch + " and origindt <= '" + curYearDt + "' order by origindt").getResultList();
                                }
                                if (!result.isEmpty()) {
                                    Vector ele = (Vector) result.get(0);
                                    int glAcno2 = Integer.parseInt(ele.get(0).toString());
                                    glNo2 = glNo2 + glAcno2;
                                    double Amt2 = Double.parseDouble(ele.get(1).toString());
                                    glAmt2 = glAmt2 + Amt2;
                                }
                            }
                        }
                    }
                    System.out.println("No of glCu" + glNo2 + "; Gl AmtCu" + glAmt2);
                }
                //For Recuuring Accounts
                List listrd11 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "'").getResultList();
                if (!listrd11.isEmpty()) {
                    rdNo2 = listrd11.size();
                }
                List listrd22 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from rdrecon r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDt + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!listrd22.isEmpty()) {
                    Vector rdVector = (Vector) listrd22.get(0);
                    rdBal2 = Double.parseDouble(rdVector.get(0).toString());
                }
                //For dds Accounts
                List listds11 = em.createNativeQuery("select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "'").getResultList();
                if (!listds11.isEmpty()) {
                    ddsNo2 = listds11.size();
                }
                List listds22 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ddstransaction r,(select a.acno from accountmaster a where "
                        + "a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " "
                        + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + inputToDt + "')  group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!listds22.isEmpty()) {
                    Vector ddsVector = (Vector) listds22.get(0);
                    ddsBal2 = Double.parseDouble(ddsVector.get(0).toString());
                }
                othetNo2 = glNo2 + rdNo2 + ddsNo2;
                otherAmt2 = glAmt2 + rdBal2 + ddsBal2;

                totalNo2 = caNo2 + sbNo2 + fdNo2 + othetNo2;
                totalBal2 = caBal2 + sbBal2 + fdBal2 + otherAmt2;

                pojo3.setParticular("Add during the year");
                pojo3.setCaNo(caNo2 - caNo1);
                pojo3.setCaBal(caBal2 - caBal1);
                pojo3.setSbNo(sbNo2 - sbNo1);
                pojo3.setSbBal(sbBal2 - sbBal1);
                pojo3.setFdNo(fdNo2 - fdNo1);
                pojo3.setFdBal(fdBal2 - fdBal1);

                pojo3.setOtherNo(othetNo2 - othetNo1);
                pojo3.setOtherBal(otherAmt2 - otherAmt1);

                pojo3.setTotalNo(totalNo2 - totalNo1);
                pojo3.setTotalBal(totalBal2 - totalBal1);

                dataList.add(pojo3);
                //End of Add during the year
                // Less Accounts which have become operative or were closed during the year
                int caNo3 = 0, sbNo3 = 0, fdNo3 = 0, othetNo3 = 0, rdNo3 = 0, ddsNo3 = 0, glNo3 = 0, totalNo3 = 0;
                double caBal3 = 0, sbBal3 = 0, fdBal3 = 0, otherAmt3 = 0, rdBal3 = 0, ddsBal3 = 0, glAmt3 = 0, totalBal3 = 0;
                String opCloseBranch = "";
                if (inputBr.equalsIgnoreCase("0A")) {
                    opCloseBranch = "";
                } else {
                    opCloseBranch = "and substring(ast.acno,1,2)in('" + inputBr + "')";
                }
                //For Current Accounts
                List caopList1 = em.createNativeQuery("select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "')) ").getResultList();
                if (!caopList1.isEmpty()) {
                    caNo3 = caopList1.size();
                }

                List caopList2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ca_recon r,(select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "')) "
                        + ")a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();

                if (!caopList2.isEmpty()) {
                    Vector opVector = (Vector) caopList2.get(0);
                    caBal3 = Math.abs(Double.parseDouble(opVector.get(0).toString()));
                }
                //For Saving Accounts
                List sbopList1 = em.createNativeQuery("select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "')) ").getResultList();
                if (!sbopList1.isEmpty()) {
                    sbNo3 = sbopList1.size();
                }
                List sbopList2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from recon r,(select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "')) "
                        + ")a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!sbopList2.isEmpty()) {
                    Vector opVector = (Vector) sbopList2.get(0);
                    sbBal3 = Math.abs(Double.parseDouble(opVector.get(0).toString()));
                }
                //For Fixed Deposit Accounts
                List fdopList1 = em.createNativeQuery("select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select distinct a.acno from td_vouchmst v,td_accountmaster a  where matdt<= '" + curYearDt + "' "
                        + "" + branch + " and status = 'A' and v.acno = a.acno "
                        + "and a.accttype in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                        + "and a.accstatus<>15) unc, "
                        + "td_accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "')) ").getResultList();
                if (!fdopList1.isEmpty()) {
                    fdNo3 = fdopList1.size();
                }
                List fdopList2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from recon r,(select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select distinct a.acno from td_vouchmst v,td_accountmaster a  where matdt<= '" + curYearDt + "' "
                        + "" + branch + " and status = 'A' and v.acno = a.acno "
                        + "and a.accttype in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                        + "and a.accstatus<>15) unc, "
                        + "td_accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "')) "
                        + ")a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!fdopList2.isEmpty()) {
                    Vector opVector = (Vector) fdopList2.get(0);
                    fdBal3 = Math.abs(Double.parseDouble(opVector.get(0).toString()));
                }
                //For Recurrent(other) Accounts
                List rdopList1 = em.createNativeQuery("select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "')) ").getResultList();
                if (!rdopList1.isEmpty()) {
                    rdNo3 = rdopList1.size();
                }
                List rdopList2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from rdrecon r,(select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "')) "
                        + ")a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!rdopList2.isEmpty()) {
                    Vector opVector = (Vector) rdopList2.get(0);
                    rdBal3 = Math.abs(Double.parseDouble(opVector.get(0).toString()));
                }
                //For DDS(other) Accounts
                List dsopList1 = em.createNativeQuery("select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.DEPOSIT_SC + "')) ").getResultList();
                if (!dsopList1.isEmpty()) {
                    ddsNo3 = dsopList1.size();
                }
                List dsopList2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ddstransaction r,(select a.acno as acno from accountstatus a, "
                        + "(select ast.acno as npaAcno,ast.dt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(dt) as dt from accountstatus where dt  between '" + inputFromDt + "' and '" + inputToDt + "' and SPFLAG IN(1,9)  group by acno) b "
                        + " where aa.acno = b.ano and aa.dt = b.dt and aa.SPFLAG IN(1,9) "
                        + "group by aa.acno,aa.dt) c where ast.acno = c.aano and ast.dt = c.aadt and ast.spno = c.sno " + opCloseBranch + ") npa, "
                        + "(select acno,max(spno) as sno from accountstatus where dt between '" + inputFromDt + "' and '" + inputToDt + "' group by acno) c , "
                        + "(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "') unc, "
                        + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                        + "and  ac.acno = unc.acno  "
                        + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in(1,9) and  "
                        + "substring(a.acno,3,2) = atm.acctcode and a.dt = npa.npaEffDt and a.spno = c.sno  and a.dt between '" + inputFromDt + "' and '" + inputToDt + "' and  "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + inputToDt + "')  AND substring(ac.acno,3,2) in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.DEPOSIT_SC + "')) "
                        + ")a where r.acno = a.acno and r.dt <= '" + inputToDt + "'").getResultList();
                if (!dsopList2.isEmpty()) {
                    Vector opVector = (Vector) dsopList2.get(0);
                    ddsBal3 = Math.abs(Double.parseDouble(opVector.get(0).toString()));
                }

                DeafForm1Pojo pojo4 = new DeafForm1Pojo();
                othetNo3 = rdNo3 + ddsNo3;
                otherAmt3 = rdBal3 + ddsBal3;

                totalNo3 = caNo3 + sbNo3 + fdNo3 + othetNo3;
                totalBal3 = caBal3 + sbBal3 + fdBal3 + otherAmt3;

                pojo4.setParticular("Less Accounts which have become operative or were closed during the year");
                pojo4.setCaNo(caNo2 - caNo3);
                pojo4.setCaBal(caBal2 - caBal3);
                pojo4.setSbNo(sbNo2 - sbNo3);
                pojo4.setSbBal(sbBal2 - sbBal3);
                pojo4.setFdNo(fdNo2 - fdNo3);
                pojo4.setFdBal(fdBal2 - fdBal3);

                pojo4.setOtherNo(othetNo2 - othetNo3);
                pojo4.setOtherBal(otherAmt2 - otherAmt3);

                pojo4.setTotalNo(totalNo2 - totalNo3);
                pojo4.setTotalBal(totalBal2 - totalBal3);

                dataList.add(pojo4);
                //End of Less Accounts which have become operative or were closed during the year
                // Add Intrest Credited to the Accounts during the year
                int caNo4 = 0, sbNo4 = 0, fdNo4 = 0, othetNo4 = 0, rdNo4 = 0, ddsNo4 = 0, totalNo4 = 0;
                double caBal4 = 0, sbBal4 = 0, fdBal4 = 0, otherAmt4 = 0, rdBal4 = 0, ddsBal4 = 0, totalBal4 = 0;
                //For Current Accounts
                List caintList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from ca_recon r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.trantype = 8 and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!caintList.isEmpty()) {
                    Vector intVector = (Vector) caintList.get(0);
                    caNo4 = Integer.parseInt(intVector.get(0).toString());
                    caBal4 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                //For Saving Accounts
                List sbintList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from recon r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.trantype = 8 and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!sbintList.isEmpty()) {
                    Vector intVector = (Vector) sbintList.get(0);
                    sbNo4 = Integer.parseInt(intVector.get(0).toString());
                    sbBal4 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }
                //For Fixed Deposit Accounts
                List fdintList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from td_recon r,(select distinct a.acno from td_vouchmst v,td_accountmaster a  "
                        + "where matdt<= '" + curYearDt + "' " + branch + " and status = 'A' and v.acno = a.acno and a.accttype in(select AcctCode from accounttypemaster "
                        + "where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) and a.accstatus<>15)a where r.acno=a.acno and r.trantype = 8 and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!fdintList.isEmpty()) {
                    Vector intVector = (Vector) fdintList.get(0);
                    fdNo4 = Integer.parseInt(intVector.get(0).toString());
                    fdBal4 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                //For Recurreng Accounts
                List rdintList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from rdrecon r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.trantype = 8 and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!rdintList.isEmpty()) {
                    Vector intVector = (Vector) rdintList.get(0);
                    rdNo4 = Integer.parseInt(intVector.get(0).toString());
                    rdBal4 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                //For DDs Accounts
                List dsintList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from ddstransaction r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.trantype = 8 and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!dsintList.isEmpty()) {
                    Vector intVector = (Vector) dsintList.get(0);
                    ddsNo4 = Integer.parseInt(intVector.get(0).toString());
                    ddsBal4 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                DeafForm1Pojo pojo5 = new DeafForm1Pojo();
                othetNo4 = rdNo4 + ddsNo4;
                otherAmt4 = rdBal4 + ddsBal4;

                totalNo4 = caNo4 + sbNo4 + fdNo4 + othetNo4;
                totalBal4 = caBal4 + sbBal4 + fdBal4 + otherAmt4;

                pojo5.setParticular("Add Intrest Credited to the Accounts during the year");
                pojo5.setCaNo(caNo4);
                pojo5.setCaBal(caBal4);
                pojo5.setSbNo(sbNo4);
                pojo5.setSbBal(sbBal4);
                pojo5.setFdNo(fdNo4);
                pojo5.setFdBal(fdBal4);

                pojo5.setOtherNo(othetNo4);
                pojo5.setOtherBal(otherAmt4);

                pojo5.setTotalNo(totalNo4);
                pojo5.setTotalBal(totalBal4);

                dataList.add(pojo5);
                //End of Add Intrest Credited to the Accounts during the year
                //Less Incidental charges levied to the Accounts during the year
                int caNo5 = 0, sbNo5 = 0, fdNo5 = 0, othetNo5 = 0, rdNo5 = 0, ddsNo5 = 0, totalNo5 = 0;
                double caBal5 = 0, sbBal5 = 0, fdBal5 = 0, otherAmt5 = 0, rdBal5 = 0, ddsBal5 = 0, totalBal5 = 0;

                //For Current Accounts
                List caincList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from ca_recon r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.Details like '%incidental%' and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!caincList.isEmpty()) {
                    Vector intVector = (Vector) caincList.get(0);
                    caNo5 = Integer.parseInt(intVector.get(0).toString());
                    caBal5 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                //For Saving Accounts
                List sbincList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from recon r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.Details like '%incidental%' and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!sbincList.isEmpty()) {
                    Vector intVector = (Vector) sbincList.get(0);
                    sbNo5 = Integer.parseInt(intVector.get(0).toString());
                    sbBal5 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }
                //For Fixed Deposit Accounts
                List fdincList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from td_recon r,(select distinct a.acno from td_vouchmst v,td_accountmaster a  "
                        + "where  matdt<= '" + curYearDt + "' " + branch + " and status = 'A' and v.acno = a.acno and a.accttype in(select AcctCode from accounttypemaster "
                        + "where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) and a.accstatus<>15)a where r.acno=a.acno and r.Details like '%incidental%' and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!fdincList.isEmpty()) {
                    Vector intVector = (Vector) fdincList.get(0);
                    fdNo5 = Integer.parseInt(intVector.get(0).toString());
                    fdBal5 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                //For Recurreng Accounts
                List rdincList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from rdrecon r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.Details like '%incidental%' and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!rdincList.isEmpty()) {
                    Vector intVector = (Vector) rdincList.get(0);
                    rdNo5 = Integer.parseInt(intVector.get(0).toString());
                    rdBal5 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                //For DDS Accounts
                List dsincList = em.createNativeQuery("select count(distinct r.acno),ifnull(sum(cramt-dramt),0) from ddstransaction r,(select a.acno from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') " + branch + " and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + inputToDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + curYearDt + "')a where r.acno=a.acno and r.Details like '%incidental%' and r.dt between '" + inputFromDt + "' and '" + inputToDt + "'").getResultList();

                if (!dsincList.isEmpty()) {
                    Vector intVector = (Vector) dsincList.get(0);
                    ddsNo5 = Integer.parseInt(intVector.get(0).toString());
                    ddsBal5 = Math.abs(Double.parseDouble(intVector.get(1).toString()));
                }

                DeafForm1Pojo pojo6 = new DeafForm1Pojo();
                othetNo5 = rdNo5 + ddsNo5;
                otherAmt5 = rdBal5 + ddsBal5;

                totalNo5 = caNo5 + sbNo5 + fdNo5 + othetNo5;
                totalBal5 = caBal5 + sbBal5 + fdBal5 + otherAmt5;

                pojo6.setParticular("Less Incidental charges levied to the Accounts during the year");
                pojo6.setCaNo(caNo5);
                pojo6.setCaBal(caBal5);
                pojo6.setSbNo(sbNo5);
                pojo6.setSbBal(sbBal5);
                pojo6.setFdNo(fdNo5);
                pojo6.setFdBal(fdBal5);

                pojo6.setOtherNo(othetNo5);
                pojo6.setOtherBal(otherAmt5);

                pojo6.setTotalNo(totalNo5);
                pojo6.setTotalBal(totalBal5);

                dataList.add(pojo6);
                //End of Less Incidental charges levied to the Accounts during the year
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String setTelephoneNo(String brCode) {
        List phoneList = new ArrayList();
        String phoneNo = "";

        if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
            phoneList = em.createNativeQuery("select BrPhone from parameterinfo where BrnCode = '90'").getResultList();
        } else {
            phoneList = em.createNativeQuery("select BrPhone from parameterinfo where BrnCode = '" + brCode + "'").getResultList();
        }

        if (!phoneList.isEmpty()) {
            Vector vtr = (Vector) phoneList.get(0);
            phoneNo = vtr.get(0).toString();
        }
        return phoneNo;
    }

    public List<DeafMarkPojo> getDeafInfoData(String inputBr, String acNature,
            String inputAcType, String frDt, String toDt) throws ApplicationException {
        List<DeafMarkPojo> dataList = new ArrayList<DeafMarkPojo>();
        List result = new ArrayList();
        String table = "";
        String deafEffDate = ddsRepRemote.getDeafEfectiveDate();
        if (acNature.equalsIgnoreCase("FD") || acNature.equalsIgnoreCase("MS")) {
            table = "td_accountmaster";
        } else {
            table = "accountmaster";
        }
        try {
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                if (inputBr.equalsIgnoreCase("0A")) {
                    if (inputAcType.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("select b.acno,b.custname,a.spflag,"
//                                + "date_format(a.effdt,'%d/%m/%Y'),a.amount,date_format(a.dt,'%d/%m/%Y'),"
//                                + "ifnull(a.Voucher_No,''),trim(concat(trim(ifnull(d.PerAddressLine1,'')),',',trim(ifnull(d.perAddressLine2,'')),',',trim(ifnull(d.PerVillage,'')),',',trim(ifnull(d.PerBlock,'')))) as Address "
//                                + "from accountstatus a," + table + " b,customerid c,cbs_customer_master_detail d where "
//                                + "b.accttype in(select AcctCode from accounttypemaster where acctNature = '" + acNature + "') and a.effdt between '" + frDt + "' and '" + toDt + "' "
//                                + "and a.acno = b.acno and b.acno = c.acno and c.custid = cast(d.customerid as unsigned)"
//                                + "and a.SPFLAG = '15' /*and b.accstatus = '15'*/ order by b.acno").getResultList();
                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',',trim(ifnull(cd.PerBlock,'')))) as Address "
                                + "from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                                + "(select acno as ano, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  group by acno) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt "
                                + "group by aa.acno,aa.dt) c,customerid ci,cbs_customer_master_detail cd "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ci.custid = cd.customerid "
                                + "and substring(ast.acno,3,2)in(select AcctCode from accounttypemaster where acctNature = '" + acNature + "') "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "'").getResultList();
                    } else {
//                        result = em.createNativeQuery("select b.acno,b.custname,a.spflag,"
//                                + "date_format(a.effdt,'%d/%m/%Y'),a.amount,date_format(a.dt,'%d/%m/%Y'),"
//                                + "ifnull(a.Voucher_No,''),trim(concat(trim(ifnull(d.PerAddressLine1,'')),',',trim(ifnull(d.perAddressLine2,'')),',',trim(ifnull(d.PerVillage,'')),',',trim(ifnull(d.PerBlock,'')))) as Address "
//                                + "from accountstatus a," + table + " b,customerid c,cbs_customer_master_detail d where "
//                                + "b.accttype = '" + inputAcType + "' and a.effdt between '" + frDt + "' "
//                                + "and '" + toDt + "' and a.acno = b.acno and b.acno = c.acno and c.custid = cast(d.customerid as unsigned) and a.SPFLAG = '15' /*and b.accstatus = '15'*/ order by b.acno").getResultList();

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',',trim(ifnull(cd.PerBlock,'')))) as Address "
                                + "from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                                + "(select acno as ano, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  group by acno) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt "
                                + "group by aa.acno,aa.dt) c,customerid ci,cbs_customer_master_detail cd "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ci.custid = cd.customerid "
                                + "and substring(ast.acno,3,2) = '" + inputAcType + "' "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "'").getResultList();
                    }
                } else {
                    if (inputAcType.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("select b.acno,b.custname,a.spflag,"
//                                + "date_format(a.effdt,'%d/%m/%Y'),a.amount,date_format(a.dt,'%d/%m/%Y'),"
//                                + "ifnull(a.Voucher_No,''),trim(concat(trim(ifnull(d.PerAddressLine1,'')),',',trim(ifnull(d.perAddressLine2,'')),',',trim(ifnull(d.PerVillage,'')),',',trim(ifnull(d.PerBlock,'')))) as Address "
//                                + "from accountstatus a," + table + " b,customerid c,cbs_customer_master_detail d where "
//                                + "b.curbrcode = '" + inputBr + "' and b.accttype in(select AcctCode from accounttypemaster where acctNature = '" + acNature + "') and a.effdt between '" + frDt + "' "
//                                + "and '" + toDt + "' and a.acno = b.acno and b.acno = c.acno and c.custid = cast(d.customerid as unsigned) and a.SPFLAG = '15' /*and b.accstatus = '15'*/ order by b.acno").getResultList();

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',',trim(ifnull(cd.PerBlock,'')))) as Address "
                                + "from accountstatus ast,  "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                                + "(select acno as ano, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  group by acno) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt "
                                + "group by aa.acno,aa.dt) c,customerid ci,cbs_customer_master_detail cd "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ci.custid = cd.customerid "
                                + "and substring(ast.acno,1,2) = '" + inputBr + "' "
                                + "and substring(ast.acno,3,2)in(select AcctCode from accounttypemaster where acctNature = '" + acNature + "')  "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "'").getResultList();

                    } else {
//                        result = em.createNativeQuery("select b.acno,b.custname,a.spflag,"
//                                + "date_format(a.effdt,'%d/%m/%Y'),a.amount,date_format(a.dt,'%d/%m/%Y'),"
//                                + "ifnull(a.Voucher_No,''),trim(concat(trim(ifnull(d.PerAddressLine1,'')),',',trim(ifnull(d.perAddressLine2,'')),',',trim(ifnull(d.PerVillage,'')),',',trim(ifnull(d.PerBlock,'')))) as Address "
//                                + "from accountstatus a," + table + " b,customerid c,cbs_customer_master_detail d where "
//                                + "b.curbrcode = '" + inputBr + "' and b.accttype = '" + inputAcType + "' and "
//                                + "a.effdt between '" + frDt + "' and '" + toDt + "' and a.acno = b.acno and b.acno = c.acno and c.custid = cast(d.customerid as unsigned) "
//                                + "and a.SPFLAG = '15' /*and b.accstatus = '15'*/ order by b.acno").getResultList();

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',',trim(ifnull(cd.PerBlock,'')))) as Address "
                                + "from accountstatus ast,  "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno from accountstatus aa, "
                                + "(select acno as ano, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  group by acno) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt "
                                + "group by aa.acno,aa.dt) c,customerid ci,cbs_customer_master_detail cd "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ci.custid = cd.customerid "
                                + "and substring(ast.acno,1,2) = '" + inputBr + "' "
                                + "and substring(ast.acno,3,2) = '" + inputAcType + "'  "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "'").getResultList();
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                if (inputBr.equalsIgnoreCase("0A")) {
                    if (inputAcType.equalsIgnoreCase("ALL")) {

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',', "
                                + "trim(ifnull(cd.PerBlock,'')))) as Address  "
                                + "from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.Voucher_No from accountstatus aa, "
                                + "(select acno as ano,Voucher_No, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  "
                                + "and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + acNature + "'))  group by acno,Voucher_No) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt and aa.Voucher_No = b.Voucher_No "
                                + "group by aa.acno,aa.Voucher_No,aa.dt) c,customerid ci,cbs_customer_master_detail cd,td_vouchmst vch "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ast.acno = vch.acno "
                                + "and ast.Voucher_No = vch.VoucherNo "
                                + "and ci.custid = cd.customerid "
                                + "and substring(ast.acno,3,2)in(select AcctCode from accounttypemaster where acctNature = '" + acNature + "')  "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.Voucher_No = c.Voucher_No "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by acno,ast.Voucher_No order by acno,ast.Voucher_No").getResultList();

                    } else {

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',', "
                                + "trim(ifnull(cd.PerBlock,'')))) as Address  "
                                + "from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.Voucher_No from accountstatus aa, "
                                + "(select acno as ano,Voucher_No, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  "
                                + "and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + acNature + "'))  group by acno,Voucher_No) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt and aa.Voucher_No = b.Voucher_No "
                                + "group by aa.acno,aa.Voucher_No,aa.dt) c,customerid ci,cbs_customer_master_detail cd,td_vouchmst vch "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ast.acno = vch.acno "
                                + "and ast.Voucher_No = vch.VoucherNo "
                                + "and ci.custid = cd.customerid "
                                + "and substring(ast.acno,3,2) ='" + inputAcType + "'  "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.Voucher_No = c.Voucher_No "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by acno,ast.Voucher_No order by acno,ast.Voucher_No").getResultList();
                    }
                } else {

                    if (inputAcType.equalsIgnoreCase("ALL")) {

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',', "
                                + "trim(ifnull(cd.PerBlock,'')))) as Address  "
                                + "from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.Voucher_No from accountstatus aa, "
                                + "(select acno as ano,Voucher_No, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  "
                                + "and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + acNature + "'))  group by acno,Voucher_No) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt and aa.Voucher_No = b.Voucher_No "
                                + "group by aa.acno,aa.Voucher_No,aa.dt) c,customerid ci,cbs_customer_master_detail cd,td_vouchmst vch "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ast.acno = vch.acno "
                                + "and ast.Voucher_No = vch.VoucherNo "
                                + "and ci.custid = cd.customerid and substring(ast.acno,1,2) = '" + inputBr + "' "
                                + "and substring(ast.acno,3,2)in(select AcctCode from accounttypemaster where acctNature = '" + acNature + "')  "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.Voucher_No = c.Voucher_No "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by acno,ast.Voucher_No order by acno,ast.Voucher_No").getResultList();
                    } else {

                        result = em.createNativeQuery("select ast.acno as acno,cd.custfullName,cast(ast.spflag as decimal) as npaSpflag,ast.dt as npaEffDt, "
                                + "ast.amount,date_format(ast.dt,'%d/%m/%Y'),ifnull(ast.Voucher_No,''), "
                                + "trim(concat(trim(ifnull(cd.PerAddressLine1,'')),',',trim(ifnull(cd.perAddressLine2,'')),',',trim(ifnull(cd.PerVillage,'')),',', "
                                + "trim(ifnull(cd.PerBlock,'')))) as Address  "
                                + "from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.Voucher_No from accountstatus aa, "
                                + "(select acno as ano,Voucher_No, max(dt) as dt from accountstatus where dt  <='" + toDt + "'  "
                                + "and substring(acno,3,2)in(select AcctCode from accounttypemaster where acctnature in('" + acNature + "'))  group by acno,Voucher_No) b "
                                + "where aa.acno = b.ano and aa.dt = b.dt and aa.Voucher_No = b.Voucher_No "
                                + "group by aa.acno,aa.Voucher_No,aa.dt) c,customerid ci,cbs_customer_master_detail cd,td_vouchmst vch "
                                + "where ast.acno = c.aano "
                                + "and ast.acno = ci.acno "
                                + "and ast.acno = vch.acno "
                                + "and ast.Voucher_No = vch.VoucherNo "
                                + "and ci.custid = cd.customerid and substring(ast.acno,1,2) = '" + inputBr + "' "
                                + "and substring(ast.acno,3,2)='" + inputAcType + "'  "
                                + "and ast.dt = c.aadt and ast.spno = c.sno "
                                + "and ast.Voucher_No = c.Voucher_No "
                                + "and ast.spflag = 15 "
                                + "and ast.dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by acno,ast.Voucher_No order by acno,ast.Voucher_No").getResultList();
                    }
                }
            } else {
                String glAcno = "", subCondition = "";

                if (inputBr.equalsIgnoreCase("0A")) {
                    glAcno = ddsMgmt.getGlAcnoByGlCodeBGlNature(acNature, inputAcType) + "01";
                    subCondition = "where  substring(a.acno,3,10) = '" + glAcno + "'";
                } else {
                    glAcno = inputBr + ddsMgmt.getGlAcnoByGlCodeBGlNature(acNature, inputAcType) + "01";
                    subCondition = "where  a.acno = '" + glAcno + "'";
                }
                result = em.createNativeQuery("select a.acno,b.AcName,a.spflag,a.dt,"
                        + "a.amount,date_format(a.dt,'%d/%m/%Y'),ifnull(a.Seq_No,''),'' address from accountstatus a, "
                        + "gltable b " + subCondition + " and a.acno = b.acno and a.dt "
                        + "between '" + frDt + "' and '" + toDt + "' and a.SPFLAG = '15' order by a.acno").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DeafMarkPojo pojo = new DeafMarkPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    if (vtr.get(2).toString().equalsIgnoreCase("15")) {
                        pojo.setSpFlag("Deaf Mark");
                    }
                    pojo.setEffectDate(dmyy.format(ymdhms.parse(vtr.get(3).toString())));
                    pojo.setDeafAmt(Double.parseDouble(vtr.get(4).toString()));
                    String deafDate = getDeafDateBasedOnIntCalcDtOptimize(pojo.getEffectDate(), deafEffDate);
                    pojo.setDeafDate(deafDate.equals("") ? "" : dmyy.format(ymd.parse(deafDate)));
                    pojo.setReceiptNo(vtr.get(6).toString());
                    pojo.setAddress(vtr.get(7).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public String getDeafDateBasedOnIntCalcDtOptimize(String intCalcDt, String deafEffDate) throws ApplicationException {
        String deafDt = "";
        try {
            //Get Month Deaf Effective Date
            // String deafEffDate = ddsRepRemote.getDeafEfectiveDate();
            if (deafEffDate.equals("") || deafEffDate.equals("")) {
                throw new ApplicationException("Please define Deaf Effective Date");
            }
            String prevMonthDt = CbsUtil.monthAdd(ymd.format(dmyy.parse(intCalcDt)), -1);
            //Final Deaf Effective Date
            deafDt = prevMonthDt.substring(0, 6) + deafEffDate;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return deafDt;
    }

    public String getDeafDateBasedOnIntCalcDt(String intCalcDt) throws ApplicationException {
        String deafDt = "";
        try {
            //Get Month Deaf Effective Date
            String deafEffDate = ddsRepRemote.getDeafEfectiveDate();
            if (deafEffDate.equals("") || deafEffDate.equals("")) {
                throw new ApplicationException("Please define Deaf Effective Date");
            }
            String prevMonthDt = CbsUtil.monthAdd(ymd.format(dmyy.parse(intCalcDt)), -1);
            //Final Deaf Effective Date
            deafDt = prevMonthDt.substring(0, 6) + deafEffDate;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return deafDt;
    }

    public List<DeafMarkPojo> getUnAcnoInfoData(String branch, String acNature, String dt) throws ApplicationException {
        List<DeafMarkPojo> dataList = new ArrayList<DeafMarkPojo>();
        List result = new ArrayList();
        String acTable = "", reconTable = "";;
        try {

            reconTable = common.getTableName(acNature);
            String frDt = CbsUtil.yearAdd(dt, -10);

            String tranDescUnd = "";
            List tranDescSimpleList = em.createNativeQuery("select code from cbs_parameterinfo where "
                    + "name in ('UND')").getResultList();
            if (tranDescSimpleList.isEmpty()) {
                throw new ApplicationException("Please Check data  is exists for UND in CBS_PARAMETERINFO Table");
            } else {
                Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                tranDescUnd = tranDescSimpleVect.get(0).toString();
            }
            if (branch.equalsIgnoreCase("0A")) {
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select a.acno,a.custname,b.praddress,a.accstatus,v.voucherno  from td_vouchmst v,td_accountmaster a,"
                            + "td_customermaster b  where substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode "
                            + "and matdt<= '" + frDt + "' and v.status = 'A' and v.acno = a.acno and a.accttype in(select acctcode from accounttypemaster "
                            + "where crdbflag in('c','b') and acctnature='" + acNature + "') and a.accstatus not in (9,15)"
                            + "union "
                            + "select a.acno,a.custname,b.praddress,a.accstatus,'' from td_accountmaster a,td_customermaster b where a.accstatus = 15 "
                            + "and a.accttype in(select acctcode from accounttypemaster where acctnature = '" + acNature + "')"
                            + "and substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode").getResultList();
                } else {
                    result = em.createNativeQuery("select acno,custname,praddress,accstatus from ("
                            + "select a.acno,a.custname,b.praddress,a.accstatus from accountmaster a, " + reconTable + " r,customermaster b where a.acno = r.acno "
                            + "and a.accstatus<>15 and substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode "
                            + "and substring(r.acno,3,2) in(select acctcode from accounttypemaster a1 where a1.crdbflag in('c','b') "
                            + "and a1.acctnature='" + acNature + "')  and r.trandesc not in(" + tranDescUnd + ") and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + dt + "') "
                            + "group by a.acno,a.custname having max(a.last_txn_date)<='" + frDt + "' "
                            + "union "
                            + "select a.acno,a.custname,b.praddress,a.accstatus from accountmaster a,customermaster b where a.accstatus = 15 "
                            + "and a.accttype in(select acctcode from accounttypemaster where acctnature = '" + acNature + "')"
                            + "and substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode"
                            + ")d order by d.acno").getResultList();
                }
            } else {
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select a.acno,a.custname,b.praddress,v.voucherno,a.accstatus  from td_vouchmst v,td_accountmaster a,"
                            + "td_customermaster b  where a.curbrcode = '" + branch + "'and substring(a.acno,5,6) = b.custno and a.accttype = b.actype "
                            + "and a.curbrcode = b.brncode and matdt<= '" + frDt + "' and v.status = 'A' and v.acno = a.acno and a.accttype in"
                            + "(select acctcode from accounttypemaster where crdbflag in('c','b') and acctnature='" + acNature + "') "
                            + "and a.accstatus not in (9,15)"
                            + "union "
                            + "select a.acno,a.custname,b.praddress,a.accstatus,'' from td_accountmaster a,td_customermaster b where a.accstatus = 15 "
                            + "and a.accttype in(select acctcode from accounttypemaster where acctnature = '" + acNature + "')"
                            + "and substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode "
                            + "and a.curbrcode = '" + branch + "'").getResultList();
                } else {
                    result = em.createNativeQuery("select acno,custname,praddress,accstatus from ("
                            + "select a.acno,a.custname,b.praddress,a.accstatus from accountmaster a, " + reconTable + " r,customermaster b where a.acno = r.acno "
                            + "and a.accstatus<>15 and substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode "
                            + "and a.curbrcode ='" + branch + "'and substring(r.acno,3,2) in(select acctcode from accounttypemaster a1 "
                            + "where a1.crdbflag in('c','b') and a1.acctnature='" + acNature + "')  and r.trandesc not in(" + tranDescUnd + ") "
                            + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + dt + "') "
                            + "group by a.acno,a.custname having max(a.last_txn_date)<='" + frDt + "' "
                            + "union "
                            + "select a.acno,a.custname,b.praddress,a.accstatus from accountmaster a,customermaster b where a.accstatus = 15 "
                            + "and a.accttype in(select acctcode from accounttypemaster where acctnature = '" + acNature + "')"
                            + "and substring(a.acno,5,6) = b.custno and a.accttype = b.actype and a.curbrcode = b.brncode "
                            + "and a.curbrcode = '" + branch + "')d order by d.acno").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DeafMarkPojo pojo = new DeafMarkPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setAddress(vtr.get(2).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }
}
