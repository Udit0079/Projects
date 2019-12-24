/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.HoIntObj;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author root
 */
@Stateless(mappedName = "HoInterestCalcFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)

public class HoInterestCalcFacade implements HoInterestCalcFacadeRemote {

    @PersistenceContext
    private EntityManager em;

    @Resource
    EJBContext context;

    @EJB
    private HoReportFacadeRemote hoReport;

    @EJB
    InterBranchTxnFacadeRemote facadeRemote;

    @EJB
    FtsPostingMgmtFacadeRemote ftsPosting;

    @EJB
    CommonReportMethodsRemote common;

    private static SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public Map<String, List<HoIntObj>> hoIntCalculation(String brnCode, String qEnd, float roi) throws ApplicationException {
        try {
            Map<String, List<HoIntObj>> dataMap = new HashMap<String, List<HoIntObj>>();

            List<HoIntObj> intList = new ArrayList<HoIntObj>();
            HoIntObj intObj;
            List acctNatLst = em.createNativeQuery("SELECT Code,AcType,GlbActype from glbmast WHERE  (ifnull(ACTYPE,'')<>''OR actstatus=20) order by groupcode").getResultList();
            if (acctNatLst.isEmpty()) {
                throw new ApplicationException("Data does not exit in GLB Master");
            }
            Calendar cal = Calendar.getInstance();
            int curYear = cal.get(Calendar.YEAR);
            String dateOne = ymmd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(qEnd), curYear));
            //cal.set(Calendar.MONTH, Integer.parseInt(qEnd));
            //cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            //String dateOne = ymmd.format(cal.getTime());
            List<GlHeadPojo> osBlancelist = hoReport.getAsOnDateBalanceList(brnCode, dateOne);
            Map<Integer, Double> balMapOne = getAggrigateDepositAdvance(osBlancelist, acctNatLst);

            //cal = Calendar.getInstance();
           // cal.set(Calendar.MONTH, Integer.parseInt(qEnd) - 1);
           // cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
           // String dateTwo = ymmd.format(cal.getTime());
            
            String dateTwo = ymmd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(qEnd)-1, curYear));
            osBlancelist = hoReport.getAsOnDateBalanceList(brnCode, dateTwo);
            Map<Integer, Double> balMapTwo = getAggrigateDepositAdvance(osBlancelist, acctNatLst);

            //cal = Calendar.getInstance();
           // cal.set(Calendar.MONTH, Integer.parseInt(qEnd) - 2);
            //cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
           // String dateThree = ymmd.format(cal.getTime());
            String dateThree = ymmd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(qEnd)-2, curYear));
            osBlancelist = hoReport.getAsOnDateBalanceList(brnCode, dateThree);
            Map<Integer, Double> balMapThree = getAggrigateDepositAdvance(osBlancelist, acctNatLst);

            double totalDeposit = balMapOne.get(1) + balMapTwo.get(1) + balMapThree.get(1);
            double totalAdvance = Math.abs(balMapOne.get(2) + balMapTwo.get(2) + balMapThree.get(2));

            intObj = createHoIntObj("A", "Aggregate Deposit as on " + dmy.format(ymmd.parse(dateThree)), balMapThree.get(1));
            intList.add(intObj);
            intObj = createHoIntObj("B", "Aggregate Deposit as on " + dmy.format(ymmd.parse(dateTwo)), balMapTwo.get(1));
            intList.add(intObj);
            intObj = createHoIntObj("C", "Aggregate Deposit as on " + dmy.format(ymmd.parse(dateOne)), balMapOne.get(1));
            intList.add(intObj);
            intObj = createHoIntObj("D", "Total Deposit (A+B+C) ", totalDeposit);
            intList.add(intObj);
            intObj = createHoIntObj("E", "Average Deposit (D/3) ", totalDeposit / 3);
            intList.add(intObj);

            intObj = createHoIntObj("F", "Aggregate Advance as on " + dmy.format(ymmd.parse(dateThree)), Math.abs(balMapThree.get(2)));
            intList.add(intObj);
            intObj = createHoIntObj("G", "Aggregate Advance as on " + dmy.format(ymmd.parse(dateTwo)), Math.abs(balMapTwo.get(2)));
            intList.add(intObj);
            intObj = createHoIntObj("H", "Aggregate Advance as on " + dmy.format(ymmd.parse(dateOne)), Math.abs(balMapOne.get(2)));
            intList.add(intObj);
            intObj = createHoIntObj("I", "Total Advance (E+F+G) ", totalAdvance);
            intList.add(intObj);
            intObj = createHoIntObj("J", "Average Advance (I/3) ", totalAdvance / 3);
            intList.add(intObj);

            double accessBal = (totalDeposit / 3) - (totalAdvance / 3);
            intObj = createHoIntObj("K", "Access of Average Deposit over Average Advance (E-J)) ", accessBal);
            intList.add(intObj);

            double interest = (accessBal * roi) / 400;
            String desc = "";
            Map<String, String> headMap;
            if (interest < 0) {
                desc = "Central Office Account Interest Payable";
                headMap = getInterestHead("N", brnCode);
            } else {
                desc = "Central Office Account Interest Receivable";
                headMap = getInterestHead("P", brnCode);
            }
            intObj = createHoIntObj("L", desc, interest);
            intList.add(intObj);
            dataMap.put("L", intList);

            List<HoIntObj> glHeadList = new ArrayList<HoIntObj>();
            intObj = createHoIntObj("HO", headMap.get("HO"), 0);
            glHeadList.add(intObj);

            intObj = createHoIntObj("BR", headMap.get("BR"), 0);
            glHeadList.add(intObj);
            dataMap.put("H", glHeadList);

            List<HoIntObj> tolIntList = new ArrayList<HoIntObj>();
            intObj = createHoIntObj("Interest", "", interest);
            tolIntList.add(intObj);
            dataMap.put("I", tolIntList);
            return dataMap;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private Map<String, String> getInterestHead(String purpose, String brnCode) throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("SELECT acno,purpose from abb_parameter_info WHERE  purpose like 'HO" + purpose + "I%'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exit in GLB Master");
            }
            Map<String, String> headMap = new HashMap<String, String>();
            for (int i = 0; i < dataList.size(); i++) {
                Vector vect = (Vector) dataList.get(i);
                if (vect.get(1).toString().equalsIgnoreCase("HOPID") || vect.get(1).toString().equalsIgnoreCase("HONIC")) {
                    headMap.put("HO", "90" + vect.get(0).toString());
                } else {
                    headMap.put("BR", brnCode + vect.get(0).toString());
                }
            }
            return headMap;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private HoIntObj createHoIntObj(String sNo, String desc, double amount) throws ApplicationException {
        try {
            HoIntObj intObj = new HoIntObj();
            intObj.setsNo(sNo);
            intObj.setDesc(desc);
            intObj.setAmount(new BigDecimal(amount));
            return intObj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private Map<Integer, Double> getAggrigateDepositAdvance(List<GlHeadPojo> osBlancelist, List acctNatLst) throws ApplicationException {
        try {
            Map<Integer, Double> balMap = new HashMap<Integer, Double>();
            String acType, gLHead = "", classification;
            NumberFormat formatter = new DecimalFormat("#.##");
            GlHeadPojo pojo = null;
            double aggDep = 0;
            double aggAdv = 0;
            for (int i = 0; i < acctNatLst.size(); i++) {
                Vector element = (Vector) acctNatLst.get(i);

                gLHead = (element.get(0) != null ? element.get(0).toString() : "").trim();
                acType = element.get(1).toString();
                classification = element.get(2).toString().trim();
                double tmpBal = 0;

                pojo = new GlHeadPojo();
                /* AcType value */
                pojo = hoReport.getOSBalance(osBlancelist, acType, classification);
                if (pojo != null) {
                    tmpBal = Double.valueOf(formatter.format(pojo.getBalance()));
                } else {
                    tmpBal = 0;
                }
                pojo = hoReport.getOSBalance(osBlancelist, gLHead, classification);
                if (pojo != null) {
                    tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                } else {
                    if (classification.equalsIgnoreCase("L")) {
                        pojo = hoReport.getOSBalance(osBlancelist, gLHead, "A");
                        if (pojo != null) {
                            tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                        } else {
                            tmpBal = tmpBal + 0;
                        }
                    } else {
                        pojo = hoReport.getOSBalance(osBlancelist, gLHead, "L");
                        if (pojo != null) {
                            tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                        } else {
                            tmpBal = tmpBal + 0;
                        }
                    }
                }
                if (classification.equals("A")) {
                    aggAdv = aggAdv + tmpBal;
                } else {
                    aggDep = aggDep + tmpBal;
                }
            }
            balMap.put(1, aggDep);
            balMap.put(2, aggAdv);
            return balMap;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String hoIntPosting(String orgBrnCode, String brnCode, String drHead, String crHead, double amt, String userName, String qEnd) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = "";
            String alphaCode = common.getAlphacodeByBrncode(brnCode);
            String details = "Ho Interest of branch " + alphaCode;

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Integer.parseInt(qEnd));
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            String toDt = ymmd.format(cal.getTime());

            cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Integer.parseInt(qEnd) - 2);
            cal.set(Calendar.DATE, cal.getMinimum(Calendar.DATE));
            String fromDt = ymmd.format(cal.getTime());

            List dataList = em.createNativeQuery("SELECT actype from int_post_history WHERE  fromdate='" + fromDt + "' and todate='" + toDt + "' and brncode='" + brnCode
                    + "' and actype='HOI'").getResultList();
            if (!dataList.isEmpty()) {
                throw new ApplicationException("Interest already posted.");
            }
            float trsNo = ftsPosting.getTrsNo();

            //Do not modify in any case with out confirming From Dinesh
            if (ftsPosting.getCurrentBrnCode(crHead).equals(orgBrnCode)) {
                msg = facadeRemote.cbsPostingCx(crHead, 0, ymmd.format(new Date()), amt, 0, 2, details, 0f, "A", "", "", 3, 0F, ftsPosting.getRecNo(), 0,
                        orgBrnCode, orgBrnCode, userName, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = facadeRemote.cbsPostingSx(drHead, 1, ymmd.format(new Date()), amt, 0f, 2, details, 0f, "A", "", "", 3, 0f, ftsPosting.getRecNo(), 0,
                        ftsPosting.getCurrentBrnCode(drHead), orgBrnCode, userName, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            } else {
                msg = facadeRemote.cbsPostingCx(drHead, 1, ymmd.format(new Date()), amt, 0f, 2, details, 0f, "A", "", "", 3, 0F, ftsPosting.getRecNo(), 0,
                        orgBrnCode, orgBrnCode, userName, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = facadeRemote.cbsPostingSx(crHead, 0, ymmd.format(new Date()), amt, 0f, 2, details, 0f, "A", "", "", 3, 0f, ftsPosting.getRecNo(), 0,
                        ftsPosting.getCurrentBrnCode(crHead), orgBrnCode, userName, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }
            Query insertQuery = em.createNativeQuery("INSERT INTO int_post_history(actype,todate,fromdate,enterby,auth,trantime,authby,brncode)"
                    + " VALUES ('HOI','"+toDt+"','"+fromDt+"','"+userName+"','Y',now(),'SYSTEM','"+brnCode+"')");
            int resultNew = insertQuery.executeUpdate();
            if (resultNew <= 0) {
                throw new ApplicationException("Dividend Posting Failed");
            }
            ut.commit();
            return "Interest Posted successfully.";
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }
    }
}
