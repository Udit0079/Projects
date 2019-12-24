/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.ProfitAndLossPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "SecondaryGlReportFacade")
public class SecondaryGlReportFacade implements SecondaryGlReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List<ProfitAndLossPojo> getComperativePLDetails(String brncode, String firstDt, String secondDt,
            String option) throws ApplicationException {
        List<ProfitAndLossPojo> dataList = new ArrayList<ProfitAndLossPojo>();
        String minDt = "", compDt = "";
        try {
            String branchCode = brncode.equals("ALL") ? "90" : brncode;
            //Evaluate the min date for first compare date.
            String firstMinDt = getMinAndMaxDate(firstDt, branchCode).get(0);
            //Evaluate the min date for second compare date.
            String secondMinDt = getMinAndMaxDate(secondDt, branchCode).get(0);
            //Evaluate balance list for these two compare date.
            List<GlHeadPojo> firstOsBlanceList = new ArrayList<GlHeadPojo>();
            List<GlHeadPojo> secondOsBlanceList = new ArrayList<GlHeadPojo>();
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    minDt = firstMinDt;
                    compDt = firstDt;
                    firstOsBlanceList = getBalanceList(brncode, minDt, compDt, option);
                } else if (j == 1) {
                    minDt = secondMinDt;
                    compDt = secondDt;
                    secondOsBlanceList = getBalanceList(brncode, minDt, compDt, option);
                }
            }
            //Taking all pl heads from plmast.
            List plList = em.createNativeQuery("select code,description,classification from plmast where "
                    + "subgroupcode<>0 order by classification desc,groupcode").getResultList();
            if (plList.isEmpty()) {
                throw new ApplicationException("There is no data in plmast.");
            }
            for (int i = 0; i < plList.size(); i++) {
                Vector ele = (Vector) plList.get(i);
                if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                        || ele.get(0).toString().equals("")
                        || ele.get(1).toString().equals("")
                        || ele.get(2).toString().equals("")) {
                    throw new ApplicationException("Check values in plmast.");
                }
                if (ele.get(0).toString().trim().length() != 8) {
                    throw new ApplicationException("Check values for code::" + ele.get(0).toString());
                }
                String code = ele.get(0).toString().trim();
                String description = ele.get(1).toString().trim();
                String classification = ele.get(2).toString().trim();
                //Creation of Pl Object
                ProfitAndLossPojo plObj = new ProfitAndLossPojo();
                plObj.setAcno(code);
                plObj.setAcName(description);
                plObj.setType(classification);
                /* GL head value */
                GlHeadPojo pojo = getOsBalance(firstOsBlanceList, plObj.getAcno());
                plObj.setAmount(pojo == null ? new BigDecimal(0) : pojo.getBalance());

                pojo = getOsBalance(secondOsBlanceList, plObj.getAcno());
                plObj.setAmountOne(pojo == null ? new BigDecimal(0) : pojo.getBalance());
                dataList.add(plObj);
            }
            List<ProfitAndLossPojo> totPlList = new ArrayList<ProfitAndLossPojo>();
            //For Consolidate Profit & Loss On First Date.
            BigDecimal plOnFirstDt = new BigDecimal(0);
            for (GlHeadPojo obj : firstOsBlanceList) {
                plOnFirstDt = plOnFirstDt.add(obj.getBalance());
            }
            totPlList = getTotalPl(0, plOnFirstDt, firstDt, secondDt);  //0-for first date
            for (ProfitAndLossPojo plPojo : totPlList) {
                dataList.add(plPojo);
            }
            //For Consolidate Profit & Loss On Second Date.
            BigDecimal plOnSecondDt = new BigDecimal(0);
            for (GlHeadPojo obj : secondOsBlanceList) {
                plOnSecondDt = plOnSecondDt.add(obj.getBalance());
            }
            totPlList = getTotalPl(1, plOnSecondDt, firstDt, secondDt);  //1-for second date
            for (ProfitAndLossPojo plPojo : totPlList) {
                dataList.add(plPojo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<ProfitAndLossPojo> getTotalPl(int seqNo, BigDecimal plAmount, String firstDt,
            String secondDt) throws ApplicationException {
        List<ProfitAndLossPojo> list = new ArrayList<ProfitAndLossPojo>();
        try {
            //seqNo-0 for firstDt, seqNo-1 for secondDt.
            if (plAmount.compareTo(new BigDecimal("0")) != 0) {
                if (plAmount.compareTo(new BigDecimal("0")) == -1) {
                    ProfitAndLossPojo obj = new ProfitAndLossPojo();
                    obj.setAcno("");
                    obj.setType("I");
                    String plDt = "";
                    if (seqNo == 0) {
                        obj.setAmount(plAmount);
                        obj.setAmountOne(new BigDecimal("0"));
                        plDt = firstDt;
                    } else if (seqNo == 1) {
                        obj.setAmount(new BigDecimal("0"));
                        obj.setAmountOne(plAmount);
                        plDt = secondDt;
                    }
//                    obj.setAcName("P&L(LOSS) On:" + dmy.format(ymd.parse(plDt)));
                    obj.setAcName("P&L(LOSS) ");
                    list.add(obj);
                } else if (plAmount.compareTo(new BigDecimal("0")) == 1) {
                    ProfitAndLossPojo obj = new ProfitAndLossPojo();
                    obj.setAcno("");
                    obj.setType("E");
                    String plDt = "";
                    if (seqNo == 0) {
                        obj.setAmount(plAmount);
                        obj.setAmountOne(new BigDecimal("0"));
                        plDt = firstDt;
                    } else if (seqNo == 1) {
                        obj.setAmount(new BigDecimal("0"));
                        obj.setAmountOne(plAmount);
                        plDt = secondDt;
                    }
//                    obj.setAcName("P&L(PROFIT) On:" + dmy.format(ymd.parse(plDt)));
                    obj.setAcName("P&L(PROFIT) ");
                    list.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List<String> getMinAndMaxDate(String dt, String branchCode) throws ApplicationException {
        List<String> rangeList = new ArrayList<String>();
        try {
            List list = em.createNativeQuery("select mindate,maxdate from yearend where mindate<='" + dt + "' and "
                    + "maxdate>='" + dt + "' and brncode='" + branchCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There should be date range in yearend for date::"
                        + dmy.format(ymd.parse(dt)));
            }
            Vector ele = (Vector) list.get(0);
            rangeList.add(ele.get(0).toString());
            rangeList.add(ele.get(1).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return rangeList;
    }

    public List<GlHeadPojo> getBalanceList(String brCode, String minDt, String compDt,
            String option) throws ApplicationException {
        try {
            String query = CbsUtil.getBrWiseComperativePlQuery(brCode, minDt, compDt, option);
            List glResultList = em.createNativeQuery(query).getResultList();
            List<GlHeadPojo> balanceList = new ArrayList<GlHeadPojo>();
            GlHeadPojo balPojo;
            for (int i = 0; i < glResultList.size(); i++) {
                Vector vect = (Vector) glResultList.get(i);
                balPojo = new GlHeadPojo();
//                System.out.println("gl head is =" + vect.get(0) + "; value:" + vect.get(1));
                balPojo.setGlHead(vect.get(0).toString());
                balPojo.setBalance(new BigDecimal(vect.get(1).toString()));
                balanceList.add(balPojo);
            }
            return balanceList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public GlHeadPojo getOsBalance(List<GlHeadPojo> balanceList, String acno) throws ApplicationException {
        try {
            for (GlHeadPojo pojo : balanceList) {
                if (pojo.getGlHead().equals(acno) && pojo.getBalance().compareTo(new BigDecimal(0)) != 0) {
                    return pojo;
                }
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
