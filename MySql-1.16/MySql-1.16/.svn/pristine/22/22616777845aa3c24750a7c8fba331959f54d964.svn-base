package com.cbs.facade.ho.deadstock;

import com.cbs.dto.DeadstockTranTable;
import com.cbs.dto.ItemMasterTable;
import com.cbs.dto.ItemStatusReportTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "DeadstockFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DeadstockFacade implements DeadstockFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting43;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List getDepreciationGroupCodde() throws ApplicationException {
        try {
            List result = em.createNativeQuery("select group_name,group_code from item_group").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBrachList() throws ApplicationException {
        try {
            List result = em.createNativeQuery("select AlphaCode,brncode from branchmaster").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getItemCodeListReport(String groupCode) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select distinct(dm.item_code),item_name from item_code ic,deadstock_movement dm where ic.group_code='" + groupCode + "' and ic.item_code=dm.item_code").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDistinctNoReport(String groupCode, int itemcode) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select item_distinctive_sno from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "'and ic.item_code='" + itemcode + "' and ic.item_code=dm.item_code  ").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getItemCodeList(String brncode, String groupCode) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select distinct(dm.item_code),item_name from item_code ic,deadstock_movement dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "' and ic.item_code=dm.item_code").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<Vector> getDpriciation(String groupCode, String itemcode, String brncode, int itemdistNo, String itemOption, String frDt, String toDt) throws ApplicationException {
        double orignalCost;
        double bookCost;
        double depPercent;
        double depAmt;
        double depAmtPrevious;
        String depMethod;
        String depRound;
        String roundBase;
        String roundCeilFloor;
        String itemDistinctiveSno;
        List<Vector> result = new ArrayList<Vector>();
        try {

            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String todate = ymd.format(date);
//            Calendar cal = Calendar.getInstance();
//            Calendar fromcal = Calendar.getInstance();
//            Calendar tocal = Calendar.getInstance();
//            cal.setTime(date);
//            if (cal.get(Calendar.MONTH) < 3) {
//                fromcal.set(Calendar.MONTH, 3);
//                fromcal.set(Calendar.DAY_OF_MONTH, 1);
//                fromcal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
//                tocal.set(Calendar.MONTH, 2);
//                tocal.set(Calendar.DAY_OF_MONTH, 31);
//                tocal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
//            } else {
//                fromcal.set(Calendar.MONTH, 3);
//                fromcal.set(Calendar.DAY_OF_MONTH, 1);
//                fromcal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
//                tocal.set(Calendar.MONTH, 2);
//                tocal.set(Calendar.DAY_OF_MONTH, 31);
//                tocal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
//            }

            List finList = em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='" + brncode + "'").getResultList();
            Vector yVector = (Vector) finList.get(0);
            String finYear = yVector.get(0).toString();
            String finYearlyDt = finYear + "0930";
            String finYearHalflyDt = finYear + "1001";

            if (itemOption.equalsIgnoreCase("A")) {
                List list = em.createNativeQuery("select distinct(dm.item_code),item_name from item_code ic,deadstock_movement dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "' and ic.item_code=dm.item_code").getResultList();
                String iCode = "", iName = "";
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector gVector = (Vector) list.get(i);
                        iCode = gVector.get(0).toString();
                        iName = gVector.get(1).toString();
                        List disNoList = em.createNativeQuery("select item_distinctive_sno from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "'  and ic.item_code= " + iCode + "  and ic.item_code=dm.item_code").getResultList();
                        if (!disNoList.isEmpty()) {
                            for (int j = 0; j < disNoList.size(); j++) {
                                Vector iVector = (Vector) disNoList.get(j);
                                String disNo = iVector.get(0).toString();
                                List result1 = em.createNativeQuery("select dep_date from dep_apply where item_code='" + iCode + "' and Item_distinctive_sno='" + disNo + "' and Item_group='" + groupCode + "'and dep_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                                if (result1.isEmpty()) {
                                    List discription = em.createNativeQuery("select dep_percent,dep_method,dep_round,round_base,round_ceil_floor from item_group_depreciation where group_Code='" + groupCode + "'").getResultList();
                                    List itemvalue = em.createNativeQuery("select item_distinctive_sno,original_cost,item_book_value,item_depreciation from deadstock_items where dest_brn_id='" + brncode + "' and  item_Code='" + iCode + "' and item_distinctive_sno = " + disNo + "").getResultList();
                                    if (!discription.isEmpty() && !itemvalue.isEmpty()) {
                                        Vector vtr = (Vector) discription.get(0);
                                        depPercent = Double.parseDouble(vtr.get(0).toString());
                                        depMethod = vtr.get(1).toString();
                                        depRound = vtr.get(2).toString();
                                        roundBase = vtr.get(3).toString();
                                        roundCeilFloor = vtr.get(4).toString();
                                        for (int k = 0; k < itemvalue.size(); k++) {
                                            Vector itemvtr = (Vector) itemvalue.get(k);
                                            itemDistinctiveSno = itemvtr.get(0).toString();
                                            orignalCost = Double.parseDouble(itemvtr.get(1).toString());
                                            bookCost = Double.parseDouble(itemvtr.get(2).toString());
                                            depAmtPrevious = Double.parseDouble(itemvtr.get(3).toString());
                                            String purDt = "", depDt = "";
                                            int year = 0, dd = 0, mm = 0;
                                            if (depMethod.equalsIgnoreCase("S")) {
                                                if (bookCost > 0) {
                                                    List dtList = em.createNativeQuery("select purchasedate,lastDepreciationDt from (\n"
                                                            + "(select date_format(tran_date,'%Y%m%d') purchasedate from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "'  \n"
                                                            + "and ic.item_code= " + iCode + "  and ic.item_code=dm.item_code and item_distinctive_sno = " + disNo + ")a,\n"
                                                            + "(select date_format(max(dep_date),'%Y%m%d') lastDepreciationDt from dep_apply where item_group = '" + groupCode + "' and item_code = " + iCode + " and item_distinctive_sno = " + disNo + " and dep_date<= '" + ymd.format(new Date()) + "')b\n"
                                                            + ")").getResultList();
                                                    if (!dtList.isEmpty()) {
                                                        Vector dtVector = (Vector) dtList.get(0);
                                                        purDt = dtVector.get(0).toString();
                                                        if (dtVector.get(1) == null) {
                                                            List dList = em.createNativeQuery("select date_format(max(dep_date),'%Y%m%d') lastDepreciationDt from dep_apply where item_group = '" + groupCode + "' and item_code = " + iCode + " ").getResultList();
                                                            Vector dVector = (Vector) dList.get(0);
                                                            if (dVector.get(0) == null) {
                                                                depDt = purDt;
                                                            } else {
                                                                depDt = dVector.get(0).toString();
                                                            }
                                                        } else {
                                                            depDt = dtVector.get(1).toString();
                                                        }
                                                        List ymdList = CbsUtil.getYrMonDayDiff(purDt, depDt);
                                                        year = Integer.parseInt(ymdList.get(0).toString());
                                                        mm = Integer.parseInt(ymdList.get(1).toString());
                                                        dd = Integer.parseInt(ymdList.get(2).toString());
                                                    }
                                                }
                                            }
                                            if (depMethod.equalsIgnoreCase("S")) {
                                                // if (!(year >= 3 || bookCost <= 1)) {
                                                if (bookCost > 0) {

                                                    String depFlag = "";
                                                    List purchaseDtList = em.createNativeQuery("select date_format(tran_date,'%Y%m%d') purchasedate from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "' "
                                                            + "and ic.item_code= " + iCode + "  and ic.item_code=dm.item_code and item_distinctive_sno = '" + disNo + "'and tran_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                                                    if (!purchaseDtList.isEmpty()) {
                                                        Vector ele = (Vector) purchaseDtList.get(0);
                                                        String pDt = ele.get(0).toString();
                                                        if (ymd.parse(pDt).after(ymd.parse(finYearlyDt))) {
                                                            depFlag = "H";
                                                        }
                                                    }
                                                    depAmt = calculateDescription(orignalCost, bookCost, itemDistinctiveSno, depPercent, depMethod, depRound, roundBase, roundCeilFloor, depFlag);
                                                    Vector vtrResult = new Vector();
                                                    vtrResult.add(0, orignalCost);
                                                    vtrResult.add(1, itemDistinctiveSno);
                                                    vtrResult.add(2, depAmt);
                                                    vtrResult.add(3, depPercent);
                                                    if (depRound.equalsIgnoreCase("Y")) {
                                                        vtrResult.add(4, Math.round(bookCost));
                                                    } else {
                                                        vtrResult.add(4, bookCost);
                                                    }
                                                    vtrResult.add(5, iCode);
                                                    vtrResult.add(6, iName);
                                                    vtrResult.add(7, depAmtPrevious);
                                                    result.add(vtrResult);
                                                }
                                            } else {
                                                String depFlag = "";
                                                if (bookCost > 0) {
                                                    List purchaseDtList = em.createNativeQuery("select date_format(tran_date,'%Y%m%d') purchasedate from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "' "
                                                            + "and ic.item_code= " + iCode + "  and ic.item_code=dm.item_code and item_distinctive_sno = '" + disNo + "'and tran_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                                                    if (!purchaseDtList.isEmpty()) {
                                                        Vector ele = (Vector) purchaseDtList.get(0);
                                                        String pDt = ele.get(0).toString();
                                                        System.out.println("Purchase Date " + pDt);
                                                        if (ymd.parse(pDt).after(ymd.parse(finYearlyDt))) {
                                                            depFlag = "H";
                                                        }
                                                    }

                                                    depAmt = calculateDescription(orignalCost, bookCost, itemDistinctiveSno, depPercent, depMethod, depRound, roundBase, roundCeilFloor, depFlag);
                                                    Vector vtrResult = new Vector();
                                                    vtrResult.add(0, orignalCost);
                                                    vtrResult.add(1, itemDistinctiveSno);
                                                    vtrResult.add(2, depAmt);
                                                    vtrResult.add(3, depPercent);
                                                    vtrResult.add(4, bookCost);
                                                    vtrResult.add(5, iCode);
                                                    vtrResult.add(6, iName);
                                                    vtrResult.add(7, depAmtPrevious);
                                                    result.add(vtrResult);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                List result1 = em.createNativeQuery("select dep_date from dep_apply where item_code='" + itemcode + "' and Item_distinctive_sno='" + itemdistNo + "' and Item_group='" + groupCode + "'and dep_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                if (result1.isEmpty()) {
                    List inameList = em.createNativeQuery("select item_name from item_code where item_code = '" + itemcode + "'").getResultList();
                    Vector inVector = (Vector) inameList.get(0);
                    List discription = em.createNativeQuery("select dep_percent,dep_method,dep_round,round_base,round_ceil_floor from item_group_depreciation where group_Code='" + groupCode + "'").getResultList();
                    List itemvalue = em.createNativeQuery("select item_distinctive_sno,original_cost,item_book_value,item_depreciation from deadstock_items where dest_brn_id='" + brncode + "' and  item_Code='" + itemcode + "' and item_distinctive_sno = " + itemdistNo + "").getResultList();
                    if (!discription.isEmpty() && !itemvalue.isEmpty()) {
                        Vector vtr = (Vector) discription.get(0);
                        depPercent = Double.parseDouble(vtr.get(0).toString());
                        depMethod = vtr.get(1).toString();
                        depRound = vtr.get(2).toString();
                        roundBase = vtr.get(3).toString();
                        roundCeilFloor = vtr.get(4).toString();
                        for (int i = 0; i < itemvalue.size(); i++) {
                            Vector itemvtr = (Vector) itemvalue.get(i);
                            itemDistinctiveSno = itemvtr.get(0).toString();
                            orignalCost = Double.parseDouble(itemvtr.get(1).toString());
                            bookCost = Double.parseDouble(itemvtr.get(2).toString());
                            depAmtPrevious = Double.parseDouble(itemvtr.get(3).toString());
                            String purDt = "", depDt = "";
                            int year = 0, dd = 0, mm = 0;
                            if (depMethod.equalsIgnoreCase("S")) {
                                if (bookCost > 0) {
                                    List dtList = em.createNativeQuery("select purchasedate,lastDepreciationDt from (\n"
                                            + "(select date_format(tran_date,'%Y%m%d') purchasedate from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "'  \n"
                                            + "and ic.item_code= " + itemcode + "  and ic.item_code=dm.item_code and item_distinctive_sno = " + itemdistNo + ")a,\n"
                                            + "(select date_format(max(dep_date),'%Y%m%d') lastDepreciationDt from dep_apply where item_group = '" + groupCode + "' and item_code = " + itemcode + " and item_distinctive_sno = " + itemdistNo + " and dep_date<= '" + ymd.format(new Date()) + "')b\n"
                                            + ")").getResultList();
                                    if (!dtList.isEmpty()) {
                                        Vector dtVector = (Vector) dtList.get(0);
                                        purDt = dtVector.get(0).toString();
                                        //depDt = dtVector.get(1).toString();
                                        if (dtVector.get(1) == null) {
                                            List dList = em.createNativeQuery("select date_format(max(dep_date),'%Y%m%d') lastDepreciationDt from dep_apply where item_group = '" + groupCode + "' and item_code = " + itemcode + " ").getResultList();
                                            Vector dVector = (Vector) dList.get(0);
                                            if (dVector.get(0) == null) {
                                                depDt = purDt;
                                            } else {
                                                depDt = dVector.get(0).toString();
                                            }
                                        } else {
                                            depDt = dtVector.get(1).toString();
                                        }
                                        List ymdList = CbsUtil.getYrMonDayDiff(purDt, depDt);
                                        year = Integer.parseInt(ymdList.get(0).toString());
                                        mm = Integer.parseInt(ymdList.get(1).toString());
                                        dd = Integer.parseInt(ymdList.get(2).toString());
                                    }
                                }
                            }
                            if (depMethod.equalsIgnoreCase("S")) {
                                // if (!(year >= 3 || bookCost <= 1)) {
                                if (bookCost > 0) {
                                    String depFlag = "";
                                    List purchaseDtList = em.createNativeQuery("select date_format(tran_date,'%Y%m%d') purchasedate from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "' "
                                            + "and ic.item_code= " + itemcode + "  and ic.item_code=dm.item_code and item_distinctive_sno = '" + itemDistinctiveSno + "'and tran_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                                    if (!purchaseDtList.isEmpty()) {
                                        Vector ele = (Vector) purchaseDtList.get(0);
                                        String pDt = ele.get(0).toString();
                                        System.out.println("Purchase Date " + pDt);
                                        if (ymd.parse(pDt).after(ymd.parse(finYearlyDt))) {
                                            depFlag = "H";
                                        }
                                    }
                                    depAmt = calculateDescription(orignalCost, bookCost, itemDistinctiveSno, depPercent, depMethod, depRound, roundBase, roundCeilFloor, depFlag);
                                    Vector vtrResult = new Vector();
                                    vtrResult.add(0, orignalCost);
                                    vtrResult.add(1, itemDistinctiveSno);
                                    vtrResult.add(2, depAmt);
                                    vtrResult.add(3, depPercent);
                                    if (depRound.equalsIgnoreCase("Y")) {
                                        vtrResult.add(4, Math.round(bookCost));
                                    } else {
                                        vtrResult.add(4, bookCost);
                                    }
                                    vtrResult.add(5, itemcode);
                                    vtrResult.add(6, inVector.get(0).toString());
                                    vtrResult.add(7, depAmtPrevious);
                                    result.add(vtrResult);
                                }
                            } else {
                                String depFlag = "";
                                if (bookCost > 0) {
                                    List purchaseDtList = em.createNativeQuery("select date_format(tran_date,'%Y%m%d') purchasedate from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "' "
                                            + "and ic.item_code= " + itemcode + "  and ic.item_code=dm.item_code and item_distinctive_sno = '" + itemDistinctiveSno + "'and tran_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                                    if (!purchaseDtList.isEmpty()) {
                                        Vector ele = (Vector) purchaseDtList.get(0);
                                        String pDt = ele.get(0).toString();
                                        if (ymd.parse(pDt).after(ymd.parse(finYearlyDt))) {
                                            depFlag = "H";
                                        }
                                    }
                                    depAmt = calculateDescription(orignalCost, bookCost, itemDistinctiveSno, depPercent, depMethod, depRound, roundBase, roundCeilFloor, depFlag);
                                    Vector vtrResult = new Vector();
                                    vtrResult.add(0, orignalCost);
                                    vtrResult.add(1, itemDistinctiveSno);
                                    vtrResult.add(2, depAmt);
                                    vtrResult.add(3, depPercent);
                                    vtrResult.add(4, bookCost);
                                    vtrResult.add(5, itemcode);
                                    vtrResult.add(6, inVector.get(0).toString());
                                    vtrResult.add(7, depAmtPrevious);
                                    result.add(vtrResult);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return result;
    }

    public List getDistinctNo(String brncode, String groupCode, int itemCode) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select item_distinctive_sno from item_code ic,deadstock_items dm where ic.group_code='" + groupCode + "' and dm.dest_brn_id='" + brncode + "'  and ic.item_code=" + itemCode + "  and ic.item_code=dm.item_code").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    private double calculateDescription(double orignalCost, double bookvalue, String itemsno, double deppresent, String depMethod, String depRound, String roundBase, String roundCeilFloor, String depFlag) throws ApplicationException {
        double depamt = 0.0;
        try {
            if (depMethod.equalsIgnoreCase("D")) {
                if (depFlag.equalsIgnoreCase("H")) {
                    depamt = (bookvalue * deppresent / 100) / 2;
                } else {
                    depamt = bookvalue * deppresent / 100;
                }
            } else {
                if (depFlag.equalsIgnoreCase("H")) {
                    depamt = (orignalCost * deppresent / 100) / 2;
                } else {
                    depamt = orignalCost * deppresent / 100;
                }
            }

            if (depamt > bookvalue) {
                depamt = bookvalue;
            } else {
                depamt = depamt;
            }
            if (depRound.equalsIgnoreCase("Y")) {
                List result = em.createNativeQuery("select round(" + depamt + "," + Integer.parseInt(roundBase) + ")").getResultList();
                Vector vtr = (Vector) result.get(0);
                depamt = Double.parseDouble(vtr.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return depamt;
    }

    public String postDepreciation(List<Vector> data, String frDt, String toDt) throws ApplicationException {
        String msg = "Depreciation is posted susceessfuly and Batch No is : ";
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

        String userName = "", brncode = "";
        Vector vtr1 = (Vector) data.get(0);
        userName = vtr1.get(7).toString();
        brncode = vtr1.get(8).toString();

        List crList = em.createNativeQuery("select acno from abb_parameter_info where PURPOSE = 'DEAD STOCK'").getResultList();
        if (crList.isEmpty()) {
            throw new ApplicationException("Dead stock Gl head does not exist.");
        }
        Vector crVector = (Vector) crList.get(0);
        String crGlHead = brncode + crVector.get(0).toString();

        List drList = em.createNativeQuery("select acno from abb_parameter_info where PURPOSE = 'DEPRECIATION - DEAD STOCK'").getResultList();
        if (drList.isEmpty()) {
            throw new ApplicationException("Depreciation Dead stock Gl head does not exist.");
        }

        Vector drVector = (Vector) drList.get(0);
        String drGlHead = brncode + drVector.get(0).toString();

        List finList = em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='" + brncode + "'").getResultList();
        Vector yVector = (Vector) finList.get(0);
        String finYear = yVector.get(0).toString();
        String financialYear = finYear + "-" + (Integer.parseInt(finYear.substring(2, 4)) + 1);
        double totalDeperationAmtByGroupCode = 0;
        Date date = new Date();
        String todate = ymd.format(date);
        Calendar cal = Calendar.getInstance();
        Calendar fromcal = Calendar.getInstance();
        Calendar tocal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.MONTH) < 3) {
            fromcal.set(Calendar.MONTH, 3);
            fromcal.set(Calendar.DAY_OF_MONTH, 1);
            fromcal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
            tocal.set(Calendar.MONTH, 2);
            tocal.set(Calendar.DAY_OF_MONTH, 31);
            tocal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        } else {
            fromcal.set(Calendar.MONTH, 3);
            fromcal.set(Calendar.DAY_OF_MONTH, 1);
            fromcal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            tocal.set(Calendar.MONTH, 2);
            tocal.set(Calendar.DAY_OF_MONTH, 31);
            tocal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        }
        UserTransaction ut = context.getUserTransaction();
        float trsNo = ftsPosting43.getTrsNo();
        try {
            ut.begin();
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Vector vtr = (Vector) data.get(i);
                    double depAmtPreCur = Double.parseDouble(vtr.get(2).toString()) + Double.parseDouble(vtr.get(9).toString());

                    List result = em.createNativeQuery("select dep_date from dep_apply where item_code='" + vtr.get(0).toString() + "' and Item_distinctive_sno='" + vtr.get(1).toString() + "' and Item_group='" + vtr.get(6).toString() + "'and dep_date between '" + frDt + "' and '" + toDt + "'").getResultList();
                    int count = 0;
                    if (result.isEmpty()) {
                        totalDeperationAmtByGroupCode = totalDeperationAmtByGroupCode + Double.parseDouble(vtr.get(2).toString());
                        int count3 = em.createNativeQuery("update deadstock_items set item_depreciation='" + vtr.get(2).toString() + "',item_book_value='" + vtr.get(4).toString() + "',dep_applied_upto = '" + toDt + "' where item_distinctive_sno='" + vtr.get(1).toString() + "' and dest_brn_id='" + vtr.get(8).toString() + "' and item_code='" + vtr.get(0).toString() + "'").executeUpdate();
                        if (count3 < 0) {
                            ut.rollback();
                            throw new ApplicationException("Problem in Updation !");
                        }
                        int count1 = em.createNativeQuery("insert into dep_tran values('" + vtr.get(0).toString() + "','" + vtr.get(1).toString() + "'," + null + ",'" + toDt + "','" + vtr.get(2).toString() + "','" + brncode + "','" + brncode + "','" + vtr.get(7).toString() + "')").executeUpdate();
                        if (count1 < 0) {
                            ut.rollback();
                            throw new ApplicationException("Problem in Insertion !");
                        }

                        int count2 = em.createNativeQuery("insert into dep_apply values('" + vtr.get(0).toString() + "','" + vtr.get(1).toString() + "','" + vtr.get(6).toString() + "','" + vtr.get(5).toString() + "','" + vtr.get(2).toString() + "','" + vtr.get(4).toString() + "'," + null + ",'" + brncode + "','" + brncode + "','" + toDt + "','" + vtr.get(7).toString() + "')").executeUpdate();
                        if (count2 < 0) {
                            ut.rollback();
                            throw new ApplicationException("Problem in Insertion !");
                        }
                    }
                }

                msg = ftsPosting43.insertRecons(ftsPosting43.getAcNatureByCode(crGlHead.substring(2, 4)), crGlHead, 0, totalDeperationAmtByGroupCode, ymd.format(date), ymd.format(date), 2,
                        "DEPRECIATION ON FIXED ASSETS FOR THE F/Y " + financialYear, userName, trsNo,
                        "", ftsPosting43.getRecNo(), "Y", userName, 0, 3, "", ymd.format(date), 0f, "", "", 0, "",
                        0f, "", null, brncode, brncode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                msg = ftsPosting43.updateBalance(ftsPosting43.getAcNatureByCode(crGlHead.substring(2, 4)), crGlHead, totalDeperationAmtByGroupCode, 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                msg = ftsPosting43.insertRecons(ftsPosting43.getAcNatureByCode(drGlHead.substring(2, 4)), drGlHead, 1, totalDeperationAmtByGroupCode, ymd.format(date), ymd.format(date), 2,
                        "DEPRECIATION ON FIXED ASSETS FOR THE F/Y " + financialYear, userName, trsNo,
                        "", ftsPosting43.getRecNo(), "Y", userName, 0, 3, "", ymd.format(date), 0f, "", "", 0, "",
                        0f, "", null, brncode, brncode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                msg = ftsPosting43.updateBalance(ftsPosting43.getAcNatureByCode(drGlHead.substring(2, 4)), drGlHead, 0, totalDeperationAmtByGroupCode, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                if (msg.equalsIgnoreCase("true")) {
                    msg = "Depreciation is posted susceessfuly and Batch No is : ";
                }
                ut.commit();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg + trsNo;
    }

    public List getGroupList() throws ApplicationException {
        try {
            return em.createNativeQuery("select group_name from item_group order by group_name").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Vector getGroupCode(String groupCode) throws ApplicationException {
        try {
            return (Vector) em.createNativeQuery("select group_name from item_group where group_code like '" + groupCode + "'").getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getMaxItemCode() throws ApplicationException {
        try {
            String maxCode = null;
            List code = em.createNativeQuery("select max(ITEM_CODE) from item_code").getResultList();
            if (!code.isEmpty()) {
                Vector vtr = (Vector) code.get(0);
                if (vtr.get(0) != null) {
                    maxCode = vtr.get(0).toString().equalsIgnoreCase("null") ? "" : vtr.get(0).toString();
                }
            }
            return maxCode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List viewData() throws ApplicationException {
        try {
            return em.createNativeQuery("select d.group_name,c.item_code,c.item_name,c.enter_by from item_code c, item_group d where c.group_code=d.group_code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveItemMasterRecord(String selectGroup, long itemCode, String itemName, String userName) throws ApplicationException {
        try {
            String dt = ymd.format(new Date());
            String msg = "";
            Vector groupCode = (Vector) em.createNativeQuery("select group_code from item_group where group_name like '" + selectGroup + "'").getSingleResult();
            if (groupCode.get(0) != null || groupCode != null) {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                Query insertItemQuery = em.createNativeQuery("insert into item_code (item_code,item_name,group_code,DT,enter_by) values (?,?,?,'" + dt + "',?)");
                insertItemQuery.setParameter(1, itemCode);
                insertItemQuery.setParameter(2, itemName);
                insertItemQuery.setParameter(3, groupCode.get(0).toString());
                insertItemQuery.setParameter(4, userName);
                int count = insertItemQuery.executeUpdate();
                if (count > 0) {
                    msg = "Record has been saved successfully";
                    ut.commit();
                } else {
                    ut.rollback();
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String updateItemMasterRecord(String selectGroup, long itemCode, String itemName, String userName) throws ApplicationException {
        try {
            String msg = "";
            String dt = ymd.format(new Date());
            Vector groupCode = (Vector) em.createNativeQuery("select group_code from item_group where group_name like '" + selectGroup + "'").getSingleResult();
            if (groupCode.get(0) != null || groupCode != null) {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                String groupCode1 = groupCode.get(0).toString();
                Query updateItemQuery = em.createNativeQuery("update item_code set item_name=?,group_code=?,last_update_by=?,last_update_dt='" + dt + "' where item_code=?");
                updateItemQuery.setParameter(1, itemName);
                updateItemQuery.setParameter(2, groupCode1);
                updateItemQuery.setParameter(3, userName);
                updateItemQuery.setParameter(4, itemCode);
                int count = updateItemQuery.executeUpdate();
                if (count > 0) {
                    ut.commit();
                    msg = "Record has been updated successfully";
                } else {
                    ut.rollback();
                }

            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException {
        try {
            Vector ele = null;
            Query selectQuery = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and br.brncode=cast('" + orgnbrcode + "' AS unsigned)");
            List list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
            }
            return ele;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<ItemStatusReportTable> getDataAccordingToBrnCode(String brncode, String fromDt, String toDt) throws ApplicationException {
        try {
            List<ItemStatusReportTable> reportTables = new ArrayList<ItemStatusReportTable>();
            List resultList = null;
            resultList = em.createNativeQuery("select di.item_distinctive_sno as dsno,ic.item_code as itemCode,ic.item_name as itemName,ig.group_code as groupCode,di.del_flag as delFlag,di.dest_brn_id as forBranch,di.original_cost as originCost,di.ITEM_BOOK_VALUE as bookValue "
                    + " from item_code ic,item_group ig,deadstock_items di where ic.group_code=ig.group_code and ic.item_code=di.item_code and di.dest_brn_id='" + brncode + "' and di.tran_date between '" + fromDt + "' and '" + toDt + "' order by di.item_code,di.item_distinctive_sno").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    ItemStatusReportTable grid = new ItemStatusReportTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        grid.setDsno(Integer.parseInt(ele.get(0).toString()));
                    }
                    if (ele.get(1) != null) {
                        grid.setItemCode(Integer.parseInt(ele.get(1).toString()));
                    }
                    if (ele.get(2) != null) {
                        grid.setItemName(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        grid.setGroupCode(ele.get(3).toString());
                    }
                    if (ele.get(4) != null) {
                        String mode = ele.get(4).toString();
                        if (mode.equalsIgnoreCase("Y")) {
                            mode = "Transfered";
                        } else {
                            mode = "Available";
                        }
                        grid.setDelFlag(mode);
                    }
                    if (ele.get(5) != null) {
                        grid.setGroupCode(ele.get(5).toString());
                    }
                    if (ele.get(6) != null) {
                        grid.setOriginCost(Double.parseDouble(ele.get(6).toString()));
                    }
                    if (ele.get(7) != null) {
                        grid.setBookValue(Double.parseDouble(ele.get(7).toString()));
                    }
                    int sno = i;
                    grid.setSno(++sno);
                    reportTables.add(grid);
                }
            }
            return reportTables;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAccountDetailsGlhead(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("Select acname,acno,postflag,MSGFLAG from gltable where acno='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAccountNoAccordingToGlhead(String acname) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno from gltable where acname like '" + acname + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getItemName(String itemCode) throws ApplicationException {
        try {
            String name = null;
            Vector vec = (Vector) em.createNativeQuery("select item_name from item_code where item_Code=" + itemCode).getSingleResult();
            if (vec != null || vec.get(0) != null) {
                name = vec.get(0).toString();
            }
            return name;
        } catch (javax.persistence.NoResultException e) {
            return "";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectAlphaCodeByBranchCode(String brncode) throws ApplicationException {
        try {
            return em.createNativeQuery("select alphacode from branchmaster where brncode=" + Integer.parseInt(brncode)).getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getItemList(String orgBrnCode, String flag) throws ApplicationException {
        try {
            String brncode = (orgBrnCode.length() < 2) ? ("0" + orgBrnCode) : orgBrnCode;
            if (flag.equalsIgnoreCase("P")) {
                return em.createNativeQuery("select item_code from item_code order by item_code").getResultList();
            } else if (flag.equalsIgnoreCase("T")) {
                return em.createNativeQuery("select distinct item_code from deadstock_items where DEST_BRN_ID like '" + brncode + "' order by item_code").getResultList();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveTransactionRecord(List<DeadstockTranTable> batchTable, float trsno, List<ItemMasterTable> itemTable, String tranMode, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            NumberFormat formatter = new DecimalFormat("#.##");
            String msg = "";
            String currDate = ymd.format(new Date());
            double depAmount = 0.0;
            List itemDsno = new ArrayList();
            int flag = 0;
            if (itemTable != null || itemTable.size() > 0) {
                for (ItemMasterTable imtObj : itemTable) {
                    if (imtObj.getTranFlag().equalsIgnoreCase("Y")) {
                        Query query = em.createNativeQuery("insert into deadstock_transfer_temp values (?,?,?,?,?)");
                        query.setParameter(1, trsno);
                        query.setParameter(2, imtObj.getItemCode());
                        query.setParameter(3, imtObj.getItemDistinctiveSno());
                        query.setParameter(4, imtObj.getAmtPerUnit());
                        query.setParameter(5, imtObj.getFlag());
                        query.executeUpdate();
                        itemDsno.add(imtObj.getItemDistinctiveSno());
                    }
                    if (imtObj.getTranFlag().equalsIgnoreCase("Y") && tranMode.equalsIgnoreCase("3")) {
                        if (imtObj.getAmtPerUnit() > imtObj.getItemSaleValue()) {
                            double lossAmount = imtObj.getAmtPerUnit() - imtObj.getItemSaleValue();
                            Query insertWriteOff = em.createNativeQuery("insert into deadstock_written_off (trsno,TRAN_DATE,ITEM_CODE,ORG_BRN_ID,DEST_BRN_ID,ITEM_DISTINCTIVE_SNO,DEP_APPLIED_UPTO,ORIGINAL_COST,ITEM_DEPRECIATION,ITEM_BOOK_VALUE,ITEM_SALE_VALUE,ITEM_PROFIT,ITEM_LOSS,ENTER_BY) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            insertWriteOff.setParameter(1, trsno);
                            insertWriteOff.setParameter(2, currDate);
                            insertWriteOff.setParameter(3, imtObj.getItemCode());
                            insertWriteOff.setParameter(4, imtObj.getOriginBranchId());
                            insertWriteOff.setParameter(5, imtObj.getDestBrnid());
                            insertWriteOff.setParameter(6, imtObj.getItemDistinctiveSno());
                            insertWriteOff.setParameter(7, imtObj.getDepApplyUpto());
                            insertWriteOff.setParameter(8, imtObj.getOriginalCost());
                            insertWriteOff.setParameter(9, imtObj.getItemDepAmount());
                            insertWriteOff.setParameter(10, imtObj.getAmtPerUnit());
                            insertWriteOff.setParameter(11, imtObj.getItemSaleValue());
                            insertWriteOff.setParameter(12, 0.0);
                            insertWriteOff.setParameter(13, formatter.format(lossAmount));
                            insertWriteOff.setParameter(14, userName);
                            insertWriteOff.executeUpdate();
                        } else if (imtObj.getAmtPerUnit() < imtObj.getItemSaleValue()) {
                            double itemProfit = imtObj.getItemSaleValue() - imtObj.getAmtPerUnit();
                            Query insertWriteOff = em.createNativeQuery("insert into deadstock_written_off (trsno,TRAN_DATE,ITEM_CODE,ORG_BRN_ID,DEST_BRN_ID,ITEM_DISTINCTIVE_SNO,DEP_APPLIED_UPTO,ORIGINAL_COST,ITEM_DEPRECIATION,ITEM_BOOK_VALUE,ITEM_SALE_VALUE,ITEM_PROFIT,ITEM_LOSS,ENTER_BY) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            insertWriteOff.setParameter(1, trsno);
                            insertWriteOff.setParameter(2, currDate);
                            insertWriteOff.setParameter(3, imtObj.getItemCode());
                            insertWriteOff.setParameter(4, imtObj.getOriginBranchId());
                            insertWriteOff.setParameter(5, imtObj.getDestBrnid());
                            insertWriteOff.setParameter(6, imtObj.getItemDistinctiveSno());
                            insertWriteOff.setParameter(7, imtObj.getDepApplyUpto());
                            insertWriteOff.setParameter(8, imtObj.getOriginalCost());
                            insertWriteOff.setParameter(9, imtObj.getItemDepAmount());
                            insertWriteOff.setParameter(10, imtObj.getAmtPerUnit());
                            insertWriteOff.setParameter(11, imtObj.getItemSaleValue());
                            insertWriteOff.setParameter(12, formatter.format(itemProfit));
                            insertWriteOff.setParameter(13, 0.0);
                            insertWriteOff.setParameter(14, userName);
                            insertWriteOff.executeUpdate();
                        } else {
                            Query insertWriteOff = em.createNativeQuery("insert into deadstock_written_off (trsno,TRAN_DATE,ITEM_CODE,ORG_BRN_ID,DEST_BRN_ID,ITEM_DISTINCTIVE_SNO,DEP_APPLIED_UPTO,ORIGINAL_COST,ITEM_DEPRECIATION,ITEM_BOOK_VALUE,ITEM_SALE_VALUE,ITEM_PROFIT,ITEM_LOSS,ENTER_BY) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            insertWriteOff.setParameter(1, trsno);
                            insertWriteOff.setParameter(2, currDate);
                            insertWriteOff.setParameter(3, imtObj.getItemCode());
                            insertWriteOff.setParameter(4, imtObj.getOriginBranchId());
                            insertWriteOff.setParameter(5, imtObj.getDestBrnid());
                            insertWriteOff.setParameter(6, imtObj.getItemDistinctiveSno());
                            insertWriteOff.setParameter(7, imtObj.getDepApplyUpto());
                            insertWriteOff.setParameter(8, imtObj.getOriginalCost());
                            insertWriteOff.setParameter(9, imtObj.getItemDepAmount());
                            insertWriteOff.setParameter(10, imtObj.getAmtPerUnit());
                            insertWriteOff.setParameter(11, imtObj.getItemSaleValue());
                            insertWriteOff.setParameter(12, 0.0);
                            insertWriteOff.setParameter(13, 0.0);
                            insertWriteOff.setParameter(14, userName);
                            insertWriteOff.executeUpdate();
                        }
                    }
                }
            }
            for (int i = 0; i < batchTable.size(); i++) {
                DeadstockTranTable obj = batchTable.get(i);
                Query insertQuery = em.createNativeQuery("insert into deadstock_tran (acno,balance,tran_date,dr_amt,cr_amt,ty,tran_type,"
                        + "recno,trsno,instno,pay_by,auth,enter_by,auth_by,tran_desc,tran_time,details,org_brn_id,dest_brn_id,"
                        + "value_dt,inst_dt,tran_ref,tran_ref_dt,invoice_no,invoice_dt,advise_no,item_code,item_rate,item_qty,"
                        + "tran_total,fyear,seqno,tran_mode,comm_amt,favour_of,cust_name_po) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                insertQuery.setParameter(1, obj.getAcno());
                insertQuery.setParameter(2, obj.getBalance());
                insertQuery.setParameter(3, obj.getDt());
                insertQuery.setParameter(4, obj.getDrAmt());
                insertQuery.setParameter(5, obj.getCrAmt());
                insertQuery.setParameter(6, obj.getTy());
                insertQuery.setParameter(7, obj.getTranType());
                insertQuery.setParameter(8, obj.getRecno());
                insertQuery.setParameter(9, trsno);
                insertQuery.setParameter(10, obj.getInstNo());
                insertQuery.setParameter(11, obj.getPayBy());
                insertQuery.setParameter(12, obj.getAuth());
                insertQuery.setParameter(13, obj.getEnterBy());
                insertQuery.setParameter(14, obj.getAuthBy());
                insertQuery.setParameter(15, obj.getTranDesc());
                insertQuery.setParameter(16, obj.getTranTime());
                insertQuery.setParameter(17, obj.getDetails());
                String branch = "";
                String forBranch = "";
                try {
                    Vector brcode = (Vector) em.createNativeQuery("select brncode from branchmaster where alphacode='" + obj.getOrgBrnCode().toString() + "'").getSingleResult();
                    if (brcode != null || brcode.get(0) != null) {
                        branch = brcode.get(0).toString();
                        branch = (branch.length() < 2) ? ("0" + branch) : branch;
                    }
                } catch (javax.persistence.NoResultException e) {
                    msg = "Error in getting branch";
                }
                try {
                    Vector brcode = (Vector) em.createNativeQuery("select brncode from branchmaster where alphacode='" + obj.getDestBrnCode().toString() + "'").getSingleResult();
                    if (brcode != null || brcode.get(0) != null) {
                        forBranch = brcode.get(0).toString();
                        forBranch = (forBranch.length() < 2) ? ("0" + forBranch) : forBranch;
                    } else {
                        msg = "Responding Branch does not exists. ";
                        return msg;
                    }
                } catch (javax.persistence.NoResultException e) {
                    msg = "Responding Branch does not exists. ";
                    return msg;
                }
                insertQuery.setParameter(18, branch);
                insertQuery.setParameter(19, forBranch);
                insertQuery.setParameter(20, obj.getDt());
                insertQuery.setParameter(21, obj.getInstDate());
                insertQuery.setParameter(22, obj.getTranRef());
                insertQuery.setParameter(23, obj.getTranRefDt());
                insertQuery.setParameter(24, obj.getInvoiceNo());
                insertQuery.setParameter(25, obj.getInvoiceDt());
                insertQuery.setParameter(26, obj.getAdviseNo());
                insertQuery.setParameter(27, obj.getItemCode());
                insertQuery.setParameter(28, obj.getItemRate());
                insertQuery.setParameter(29, obj.getItmQty());
                insertQuery.setParameter(30, obj.getTranTotal());
                insertQuery.setParameter(31, obj.getYear());
                insertQuery.setParameter(32, obj.getSequencNo());
                insertQuery.setParameter(33, obj.getTranMode());
                insertQuery.setParameter(34, obj.getComm());
                insertQuery.setParameter(35, obj.getFavourOf());
                insertQuery.setParameter(36, obj.getCustName());
                int count = insertQuery.executeUpdate();
                if (count <= 0) {
                    flag = 1;
                    ut.rollback();
                    break;
                }
                if (!(obj.getSequencNo().equalsIgnoreCase("") && obj.getYear().equalsIgnoreCase("")) && tranMode.equalsIgnoreCase("1")) {
                    int fyear = Integer.parseInt(obj.getYear());
                    float seqNo = Float.parseFloat(obj.getSequencNo());
                    int statusPaid = em.createNativeQuery("update bill_suspense set status='PAID' where fyear=" + fyear + " and seqno=" + seqNo + " and acno='" + obj.getAcno() + "'").executeUpdate();
                    if (statusPaid <= 0) {
                        ut.rollback();
                        break;
                    }
                }
                if ((!(obj.getSequencNo().equalsIgnoreCase("0"))) && (!(obj.getYear().equalsIgnoreCase("0"))) && tranMode.equalsIgnoreCase("3")) {
                    int fyear = Integer.parseInt(obj.getYear());
                    float seqNo = Float.parseFloat(obj.getSequencNo());
                    int statusPaid = em.createNativeQuery("update bill_sundry set status='PAID' where fyear=" + fyear + " and seqno=" + seqNo + " and acno='" + obj.getAcno() + "'").executeUpdate();
                    if (statusPaid <= 0) {
                        ut.rollback();
                        break;
                    }
                }
                if (obj.getTranMode() == 2 && i == 1) {
                    if (itemDsno.size() > 0) {
                        for (int j = 0; j < itemDsno.size(); j++) {
                            List depAmountList = em.createNativeQuery("select ifnull(item_depreciation,0.0) as depAmount from deadstock_items where ITEM_DISTINCTIVE_SNO=" + itemDsno.get(j)).getResultList();
                            if (depAmountList.size() > 0) {
                                Vector depAmt = (Vector) depAmountList.get(0);
                                depAmount += Double.parseDouble(depAmt.get(0).toString());
                            }
                        }
                    }
                    if (depAmount != 0.0 && i == 1) {
                        for (int k = 0; k < 2; k++) {
                            Query insertDep = em.createNativeQuery("insert into deadstock_tran (acno,balance,tran_date,dr_amt,cr_amt,ty,tran_type,recno,trsno,pay_by,auth,enter_by,auth_by,tran_desc,tran_time,details,ORG_BRN_ID,DEST_BRN_ID,VALUE_DT,INSTNO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            if (k == 0) {
                                String acno = "";
//                                List saAcccountList = em.createNativeQuery("select acno from gltable where acname like 'STATUTORY RESERVE'").getResultList();
//                                if (saAcccountList.size() > 0) {
//                                    Vector vect = (Vector) saAcccountList.get(0);
//                                    acno = forBranch + vect.get(0).toString().substring(2, 10) + "01";
//                                }
                                acno = forBranch + ftsPosting43.getAcnoByPurpose("STATUTORY RESERVE");
                                insertDep.setParameter(1, acno);
                                insertDep.setParameter(4, depAmount);
                                insertDep.setParameter(5, 0.0);
                                insertDep.setParameter(6, 1);
                                insertDep.setParameter(8, ftsPosting43.getRecNo());
                                insertDep.setParameter(16, "Dep. Reserve");
                            } else if (k == 1) {
                                String acno = "";
//                                List hoAcccountList = em.createNativeQuery("select acno from gltable where acname like 'HEAD OFFICE'").getResultList();
//                                if (hoAcccountList.size() > 0) {
//                                    Vector vect = (Vector) hoAcccountList.get(0);
//                                    acno = forBranch + vect.get(0).toString().substring(2, 10) + "01";
//                                }
                                acno = forBranch + ftsPosting43.getAcnoByPurpose("HEAD OFFICE");
                                insertDep.setParameter(1, acno);
                                insertDep.setParameter(4, 0.0);
                                insertDep.setParameter(5, depAmount);
                                insertDep.setParameter(6, 0);
                                insertDep.setParameter(8, ftsPosting43.getRecNo());
                                insertDep.setParameter(16, "HO A/C");
                            }
                            insertDep.setParameter(2, 0);
                            insertDep.setParameter(3, obj.getDt());
                            insertDep.setParameter(7, 2);
                            insertDep.setParameter(9, trsno);
                            insertDep.setParameter(10, 3);
                            insertDep.setParameter(11, "N");
                            insertDep.setParameter(12, obj.getEnterBy());
                            insertDep.setParameter(13, "");
                            insertDep.setParameter(14, 80);
                            insertDep.setParameter(15, obj.getTranTime());
                            insertDep.setParameter(17, branch);
                            insertDep.setParameter(18, forBranch);
                            insertDep.setParameter(19, obj.getDt());
                            insertDep.setParameter(20, obj.getInstNo());
                            int countUpdate = insertDep.executeUpdate();
                            if (countUpdate <= 0) {
                                flag = 2;
                                ut.rollback();
                                break;
                            }
                        }
                    }
                }
            }
            if (flag == 1 || flag == 2) {
                msg = "Some error occured in saving";
            } else {
                ut.commit();
                msg = "Genrated batch no. is " + trsno;
            }
            return msg;
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

    public List getDataInGrid(String itemCode, String orgnBrCode) throws ApplicationException {
        try {
            String brncode = (orgnBrCode.length() < 2) ? ("0" + orgnBrCode) : orgnBrCode;
//            return em.createNativeQuery("select ITEM_DISTINCTIVE_SNO,original_cost from deadstock_items where item_code=" + itemCode + " and"
//                    + " DEST_BRN_ID like '" + brncode + "' and del_flag='N' and item_distinctive_sno not in (select item_distinctive_sno from deadstock_transfer_temp WHERE FLAG='T')").getResultList();
            return em.createNativeQuery("select ITEM_DISTINCTIVE_SNO,original_cost from deadstock_items where item_code=" + itemCode + " and"
                    + " DEST_BRN_ID like '" + brncode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDataInGridForWriteOff(String itemCode, String orgnBrCode) throws ApplicationException {
        try {
            String brncode = (orgnBrCode.length() < 2) ? ("0" + orgnBrCode) : orgnBrCode;
            return em.createNativeQuery("select ITEM_DISTINCTIVE_SNO,ITEM_BOOK_VALUE,DEST_BRN_ID,DEP_APPLIED_UPTO,ORIGINAL_COST,ITEM_DEPRECIATION from deadstock_items where item_code=" + itemCode + " and "
                    + "DEST_BRN_ID like '" + brncode + "' and item_distinctive_sno not in (select item_distinctive_sno from deadstock_transfer_temp WHERE FLAG='W')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectAlphaCode() throws ApplicationException {
        try {
            return em.createNativeQuery("select alphacode from branchmaster order by alphacode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String checkAmountForSuspenseAc(String seqenceNo, String year, String acno, String amount) throws ApplicationException {
        try {
            float amt = Float.parseFloat(amount);
            String msg = "";
            int fyear = Integer.parseInt(year);
            float seqno = Float.parseFloat(seqenceNo);
            List resList = em.createNativeQuery("select amount from bill_suspense where fyear=" + fyear + " and seqno=" + seqno + " and acno='" + acno + "'").getResultList();
            if (!resList.isEmpty()) {
                Vector vec = (Vector) resList.get(0);
                if (Float.parseFloat(vec.get(0).toString()) != amt) {
                    msg = "true";
                } else {
                    msg = "false";
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String checkAmountForSundryAc(String seqenceNo, String year, String acno, String amount) throws ApplicationException {
        try {
            float amt = Float.parseFloat(amount);
            String msg = "";
            int fyear = Integer.parseInt(year);
            float seqno = Float.parseFloat(seqenceNo);
            List resList = em.createNativeQuery("select amount from bill_sundry where fyear=" + fyear + " and seqno=" + seqno + " and acno='" + acno + "'").getResultList();
            if (!resList.isEmpty()) {
                Vector vec = (Vector) resList.get(0);
                if (Float.parseFloat(vec.get(0).toString()) != amt) {
                    msg = "true";
                } else {
                    msg = "false";
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDepreciationGroupList() throws ApplicationException {
        try {
            return em.createNativeQuery("select group_name from item_group order by group_name").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Vector getDepreciationGroupCode(String groupCode) throws ApplicationException {
        try {
            return (Vector) em.createNativeQuery("select group_name from item_group where group_code like '" + groupCode + "'").getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveRecord(String selectGroup, String wefPeriod, float depPercent, String method, String round, String roundBase, String range, String userName) throws ApplicationException {
        String msg = "";
        String dt = ymd.format(new Date());
        UserTransaction ut = context.getUserTransaction();
        try {
            Vector groupCode = (Vector) em.createNativeQuery("select group_code from item_group where group_name like '" + selectGroup + "'").getSingleResult();
            if (groupCode.get(0) != null || groupCode != null) {
                ut.begin();
                Query insertDepQuery = em.createNativeQuery("insert into item_group_depreciation (group_code,wef_period,dep_percent,dep_method,dep_round,round_base,round_ceil_floor,ENTER_BY,dt) values (?,?,?,?,?,?,?,?,'" + dt + "')");
                insertDepQuery.setParameter(1, groupCode.get(0).toString());
                insertDepQuery.setParameter(2, wefPeriod);
                insertDepQuery.setParameter(3, depPercent);
                insertDepQuery.setParameter(4, method);
                insertDepQuery.setParameter(5, round);
                insertDepQuery.setParameter(6, roundBase);
                insertDepQuery.setParameter(7, range);
                insertDepQuery.setParameter(8, userName);
                int count = insertDepQuery.executeUpdate();
                if (count > 0) {
                    ut.commit();
                    msg = "Record has been saved successfully";
                } else {
                    ut.rollback();
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List viewDepreciationData() throws ApplicationException {
        try {
            return em.createNativeQuery("select d.group_code,c.item_code,c.item_name,d.wef_period,d.dep_percent,d.dep_method,d.dep_round,d.round_base,d.round_ceil_floor,d.enter_by from item_code c, item_group_depreciation d where c.group_code=d.group_code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String updateRecord(String selectGroup, String wefPeriod, float depPercent, String method, String round, String roundBase, String range, String userName) throws ApplicationException {
        try {
            String msg = "";
            UserTransaction ut = context.getUserTransaction();
            String dt = ymd.format(new Date());
            Vector groupCode = (Vector) em.createNativeQuery("select group_code from item_group where group_name like '" + selectGroup + "'").getSingleResult();
            if (groupCode.get(0) != null || groupCode != null) {
                ut.begin();
                Query updateDepQuery = em.createNativeQuery("update item_group_depreciation set group_code=?,wef_period=?,dep_percent=?,dep_method=?,dep_round=?,round_base=?,round_ceil_floor=?,last_update_by=?,last_update_dt='" + dt + "'"
                        + " where group_code=?");
                updateDepQuery.setParameter(1, groupCode.get(0).toString());
                updateDepQuery.setParameter(2, wefPeriod);
                updateDepQuery.setParameter(3, depPercent);
                updateDepQuery.setParameter(4, method);
                updateDepQuery.setParameter(5, round);
                updateDepQuery.setParameter(6, roundBase);
                updateDepQuery.setParameter(7, range);
                updateDepQuery.setParameter(8, userName);
                updateDepQuery.setParameter(9, groupCode.get(0).toString());
                int count = updateDepQuery.executeUpdate();
                if (count > 0) {
                    ut.commit();
                    msg = "Record has been updated successfully";
                } else {
                    ut.rollback();
                    msg = "No record updated";
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public long getMaxGroupCode() throws ApplicationException {
        try {
            long itemGroup = 0;
            Vector maxItemGroup1 = (Vector) em.createNativeQuery("select MAX(SUBSTRING(group_code,6,10)) from item_group").getSingleResult();
            if (maxItemGroup1 == null || maxItemGroup1.get(0) == null) {
                itemGroup = 0;
            } else {
                itemGroup = Integer.parseInt(maxItemGroup1.get(0).toString());
            }
            return itemGroup;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveData(String code, String name, String dt, String userName) throws ApplicationException {
        try {
            UserTransaction ut = context.getUserTransaction();
            String msg = "";
            ut.begin();
            Query insertQuery = em.createNativeQuery("insert into item_group (group_code,group_name,dt,enter_by)values(?,?,?,?)");
            insertQuery.setParameter(1, code);
            insertQuery.setParameter(2, name);
            insertQuery.setParameter(3, dt);
            insertQuery.setParameter(4, userName);
            int count = insertQuery.executeUpdate();
            if (count > 0) {
                ut.commit();
                msg = "Record has been saved successfully";
            } else {
                ut.rollback();
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List viewItemGroupData() throws ApplicationException {
        try {
            return em.createNativeQuery("select GROUP_CODE,GROUP_NAME,ENTER_BY from item_group order by GROUP_CODE").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String updateItemGroupRecord(String code, String name, String dt, String userName) throws ApplicationException {
        try {
            String msg = "";
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            Query updateQuery = em.createNativeQuery("update item_group set group_name=? , last_update_dt=?,last_update_by=? where group_code='" + code + "'");
            updateQuery.setParameter(1, name);
            updateQuery.setParameter(2, dt);
            updateQuery.setParameter(3, userName);
            int x = updateQuery.executeUpdate();
            if (x <= 0) {
                ut.rollback();
                msg = "No record updated";
            } else {
                ut.commit();
                msg = "Record has been successfully Updated";
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ****************for Batch Deletion*******************************
     */
    public List viewDataInGrid(int trsNo) throws ApplicationException {
        try {
            String date = ymd.format(new Date());
            List selectList = em.createNativeQuery("select t.trsno,t.ACNO,g.acName,t.Cr_amt,t.Dr_Amt,t.instno,t.Enter_By,t.auth,t.Details,"
                    + "t.recno,t.tran_date,t.DEST_BRN_ID,t.ORG_BRN_ID,t.ty,t.ITEM_code FROM deadstock_tran t, gltable g "
                    + "where t.acno=g.acno and t.auth='N' AND tran_date='" + date + "' and t.tran_desc in (80,81) and t.trsno=" + trsNo).getResultList();
            return selectList;
        } catch (Exception e) {
            throw new ApplicationException(e);
            // return null;
        }
    }

    public String deleteBatch(int trsno, String userName) throws ApplicationException {
        String msg = "";
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            int countRec = em.createNativeQuery("insert into deadstock_tran_history(acno, balance, tran_date, dr_amt, cr_amt, ty, tran_type, recno, trsno, instno, pay_by, auth, enter_by, auth_by, tran_desc, tran_time, details, org_brn_id, dest_brn_id, value_dt, inst_dt, tran_ref, tran_ref_dt, invoice_no, invoice_dt, advise_no, item_code, item_rate, item_qty, tran_total, fyear, seqno, tran_mode, comm_amt, favour_of, cust_name_po) "
                    + "select ACNO,BALANCE,TRAN_DATE,DR_AMT ,CR_AMT ,TY,TRAN_TYPE ,RECNO ,TRSNO ,INSTNO ,PAY_BY ,'D' as AUTH ,ENTER_BY ,'" + userName + "' as AUTH_BY ,TRAN_DESC ,now() as TRAN_DATE,DETAILS ,ORG_BRN_ID ,"
                    + " DEST_BRN_ID ,VALUE_DT ,INST_DT,TRAN_REF ,TRAN_REF_DT ,INVOICE_NO ,INVOICE_DT ,ADVISE_NO ,ITEM_CODE ,ITEM_RATE ,ITEM_QTY ,TRAN_TOTAL ,FYEAR ,SEQNO ,TRAN_MODE ,COMM_AMT ,FAVOUR_OF ,CUST_NAME_PO from deadstock_tran where trsno=" + trsno).executeUpdate();
            int count = em.createNativeQuery("delete from deadstock_tran where trsno=" + trsno).executeUpdate();
            if (count > 0 && countRec > 0) {
                msg = "true";
                ut.commit();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }

    public List<ItemStatusReportTable> getDeadStockData(String brncode, String toDt) throws ApplicationException {
        List<ItemStatusReportTable> dataList = new ArrayList<ItemStatusReportTable>();
        List result = new ArrayList();
        try {
            Map<String, String> depPercentMap = new HashMap<String, String>();
            List list = em.createNativeQuery("select group_code,dep_percent from item_group_depreciation order by group_code").getResultList();
            for (int j = 0; j < list.size(); j++) {
                Vector ele = (Vector) list.get(j);
                depPercentMap.put(ele.get(0).toString(), ele.get(1).toString());
            }

            String bnkCode = ftsPosting43.getBankCode();
            if (brncode.equalsIgnoreCase("0A")) {
//                result = em.createNativeQuery("select b.group_code,c.group_name,b.item_code,b.item_name,date_format(a.tran_date,'%d/%m/%Y') purchasedate,"
//                        + "a.org_brn_id,dest_brn_id,a.dep_applied_upto,sum(a.original_cost),count(item_distinctive_sno) Qty,sum(a.item_depreciation),sum(a.item_book_value),a.del_flag,a.n0_of_movements "
//                        + "from deadstock_items a,item_code b ,item_group c where b.group_code =  c.group_code and a.item_code = b.item_code "
//                        + "and tran_date <= '" + toDt + "' group by b.group_code,b.item_code order by b.group_code,b.item_code").getResultList();
                result = em.createNativeQuery("select GroupCode,GroupName,ItemCode,ItemName,purchasedate,OriginBranch,DestBranch,DepAppliedUpto,OriginalCost,Qty,DepreciationAmt,BookValue,DelFlag,Movements from "
                        + "( "
                        + "select b.group_code GroupCode,c.group_name GroupName,b.item_code ItemCode,b.item_name ItemName,date_format(a.tran_date,'%d/%m/%Y') purchasedate, "
                        + "a.org_brn_id OriginBranch,dest_brn_id DestBranch,a.dep_applied_upto DepAppliedUpto,sum(a.original_cost) OriginalCost,count(item_distinctive_sno) Qty, "
                        + "sum(a.item_depreciation) DepreciationAmt,sum(a.item_book_value) BookValue,a.del_flag DelFlag,a.n0_of_movements Movements  "
                        + "from deadstock_items a,item_code b ,item_group c where b.group_code =  c.group_code and a.item_code = b.item_code "
                        + "and tran_date <= '" + toDt + "' group by b.group_code,b.item_code "
                        + "union all "
                        + "select b.group_code GroupCode,c.group_name GroupName,b.item_code ItemCode,b.item_name ItemName,date_format(a.tran_date,'%d/%m/%Y') purchasedate, "
                        + "a.org_brn_id OriginBranch,dest_brn_id DestBranch,a.dep_applied_upto DepAppliedUpto,sum(a.original_cost) OriginalCost,count(item_distinctive_sno) Qty,"
                        + "sum(a.item_depreciation) DepreciationAmt,sum(a.item_book_value) BookValue,'' DelFlag,'' Movements "
                        + "from deadstock_written_off a,item_code b ,item_group c where b.group_code =  c.group_code and a.item_code = b.item_code "
                        + "and tran_date <= '" + toDt + "' group by b.group_code,b.item_code "
                        + ")d order by d.GroupCode,d.ItemCode").getResultList();
            } else {
//                result = em.createNativeQuery("select b.group_code,c.group_name,b.item_code,b.item_name,date_format(a.tran_date,'%d/%m/%Y') purchasedate,"
//                        + "a.org_brn_id,dest_brn_id,a.dep_applied_upto,sum(a.original_cost),count(item_distinctive_sno) Qty,sum(a.item_depreciation),sum(a.item_book_value),a.del_flag,a.n0_of_movements "
//                        + "from deadstock_items a,item_code b ,item_group c where b.group_code =  c.group_code and a.item_code = b.item_code "
//                        + "and a.org_brn_id = '" + brncode + "' and tran_date <= '" + toDt + "' group by b.group_code,b.item_code order by b.group_code,b.item_code").getResultList();
                result = em.createNativeQuery("select GroupCode,GroupName,ItemCode,ItemName,purchasedate,OriginBranch,DestBranch,DepAppliedUpto,OriginalCost,Qty,DepreciationAmt,BookValue,DelFlag,Movements from "
                        + "( "
                        + "select b.group_code GroupCode,c.group_name GroupName,b.item_code ItemCode,b.item_name ItemName,date_format(a.tran_date,'%d/%m/%Y') purchasedate,"
                        + "a.org_brn_id OriginBranch,dest_brn_id DestBranch,a.dep_applied_upto DepAppliedUpto,sum(a.original_cost) OriginalCost,count(item_distinctive_sno) Qty, "
                        + "sum(a.item_depreciation) DepreciationAmt,sum(a.item_book_value) BookValue,a.del_flag DelFlag,a.n0_of_movements Movements "
                        + "from deadstock_items a,item_code b ,item_group c where b.group_code =  c.group_code and a.item_code = b.item_code and a.org_brn_id = '" + brncode + "' "
                        + "and tran_date <= '" + toDt + "' group by b.group_code,b.item_code  "
                        + "union all "
                        + "select b.group_code GroupCode,c.group_name GroupName,b.item_code ItemCode,b.item_name ItemName,date_format(a.tran_date,'%d/%m/%Y') purchasedate, "
                        + "a.org_brn_id OriginBranch,dest_brn_id DestBranch,a.dep_applied_upto DepAppliedUpto,sum(a.original_cost) OriginalCost,count(item_distinctive_sno) Qty, "
                        + "sum(a.item_depreciation) DepreciationAmt,sum(a.item_book_value) BookValue,'' DelFlag,'' Movements  "
                        + "from deadstock_written_off a,item_code b ,item_group c where b.group_code =  c.group_code and a.item_code = b.item_code and a.org_brn_id = '" + brncode + "' "
                        + "and tran_date <= '" + toDt + "' group by b.group_code,b.item_code "
                        + ")d order by d.GroupCode,d.ItemCode").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    ItemStatusReportTable pojo = new ItemStatusReportTable();
                    pojo.setGroupCode(vtr.get(0).toString());
                    pojo.setGroupName(vtr.get(1).toString());
                    pojo.setItemCode(Integer.parseInt(vtr.get(2).toString()));
                    pojo.setItemName(vtr.get(3).toString());
                    pojo.setPurchaseDt(vtr.get(4).toString());
                    pojo.setOriginBranch(vtr.get(5).toString());
                    pojo.setForBranch(vtr.get(6).toString());
                    pojo.setOriginCost(Double.parseDouble(vtr.get(8).toString()));
                    pojo.setDsno(Integer.parseInt(vtr.get(9).toString()));
                    pojo.setDepreciationPercent(Double.parseDouble(depPercentMap.get(vtr.get(0).toString())));
                    pojo.setBookValue(Double.parseDouble(vtr.get(11).toString()));
                    pojo.setDelFlag(vtr.get(12) == null ? "" : vtr.get(12).toString());
                    pojo.setNoOfMovement(vtr.get(13).toString());

                    if (pojo.getDelFlag().equalsIgnoreCase("N") && vtr.get(13).toString().equalsIgnoreCase("1")) {
                        pojo.setStatus("Purchase");
                    } else if (pojo.getDelFlag().equalsIgnoreCase("Y") && vtr.get(13).toString().equalsIgnoreCase("2")) {
                        pojo.setStatus("Transfer");
                    } else {
                        pojo.setStatus("Sold");
                    }
                    //if (bnkCode.equalsIgnoreCase("NABU")) {
                    if (pojo.getStatus().equalsIgnoreCase("Sold")) {
                        pojo.setBookValue(0.0);
                    }
                    //}
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return dataList;
    }

    public List<ItemStatusReportTable> getDepreciationReportData(String groupCode, String itemcode, int itemDistNo, String calFromDate, String caltoDate, String itemOption) throws ApplicationException {
        List<ItemStatusReportTable> dataList = new ArrayList<ItemStatusReportTable>();
        int n = 1;
        List list = new ArrayList();
        try {
            if (itemOption.equalsIgnoreCase("A")) {
                list = em.createNativeQuery("select i.item_code,item_name,item_distinctive_sno,origin_value,item_book_value,"
                        + "total_depreciation,date_format(dep_date,'%d/%m/%Y') from dep_apply d,item_code i where d.item_group='" + groupCode + "' and d.item_group=i.group_code "
                        + "and i.item_code= d.item_code and d.dep_date between '" + calFromDate + "' and '" + caltoDate + "'").getResultList();
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vtr = (Vector) list.get(i);
                        ItemStatusReportTable pojo = new ItemStatusReportTable();
                        pojo.setSrNo(n++);
                        pojo.setItemCode(Integer.parseInt(vtr.get(0).toString()));
                        pojo.setItemName(vtr.get(1).toString());
                        pojo.setDsno(Integer.parseInt(vtr.get(2).toString()));
                        pojo.setOriginCost(Double.parseDouble(vtr.get(3).toString()));
                        pojo.setBookValue(Double.parseDouble(vtr.get(4).toString()));
                        pojo.setDepreciationAmt(Double.parseDouble(vtr.get(5).toString()));
                        pojo.setDepDate(vtr.get(6).toString());
                        dataList.add(pojo);
                    }
                }
            } else {

                if (itemDistNo == 0) {
                    list = em.createNativeQuery("select i.item_code,item_name,item_distinctive_sno,origin_value,item_book_value,total_depreciation,"
                            + "date_format(dep_date,'%d/%m/%Y') from dep_apply d,item_code i where d.item_code='" + itemcode + "'and "
                            + "d.item_group='" + groupCode + "' and i.item_code=d.item_code "
                            + "and i.group_code=d.item_group and d.dep_date between '" + calFromDate + "' and '" + caltoDate + "'").getResultList();
                } else {
                    list = em.createNativeQuery("select i.item_code,item_name,item_distinctive_sno,origin_value,item_book_value,total_depreciation,"
                            + "date_format(dep_date,'%d/%m/%Y') from dep_apply d,item_code i where d.item_code='" + itemcode + "'and "
                            + "d.item_group='" + groupCode + "'and d.item_distinctive_sno='" + itemDistNo + "'and i.item_code=d.item_code "
                            + "and i.group_code=d.item_group and d.dep_date between '" + calFromDate + "' and '" + caltoDate + "'").getResultList();
                }

                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vtr = (Vector) list.get(i);
                        ItemStatusReportTable pojo = new ItemStatusReportTable();
                        pojo.setSrNo(n++);
                        pojo.setItemCode(Integer.parseInt(vtr.get(0).toString()));
                        pojo.setItemName(vtr.get(1).toString());
                        pojo.setDsno(Integer.parseInt(vtr.get(2).toString()));
                        pojo.setOriginCost(Double.parseDouble(vtr.get(3).toString()));
                        pojo.setBookValue(Double.parseDouble(vtr.get(4).toString()));
                        pojo.setDepreciationAmt(Double.parseDouble(vtr.get(5).toString()));
                        pojo.setDepDate(vtr.get(6).toString());
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return dataList;
    }

    public List getFrToDateByItemGroup(String brncode, String groupCode) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select date_format(max(dep_date),'%Y%m%d') frDate from dep_apply where item_group = '" + groupCode + "'").getResultList();
            if (!result.isEmpty()) {
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
