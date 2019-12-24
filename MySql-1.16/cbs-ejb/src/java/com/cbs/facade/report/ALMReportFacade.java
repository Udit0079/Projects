package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.report.AlmAnnexture1Table;
import com.cbs.dto.report.AlmFddetailPojo;
import com.cbs.dto.report.AlmPojo;
import com.cbs.dto.report.AlmTlRoiWiseReportPojo;
import com.cbs.dto.report.TlAccountWisePojo;
import com.cbs.exception.ApplicationException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import com.cbs.dto.report.AlmTdAccountWiseReportPojo;
import com.cbs.dto.report.AlmTdRoiWiseReportPojo;
import com.cbs.dto.report.AlmTdRoiwiseOverdueReportPojo;
import com.cbs.dto.report.RevenueReportDrCrPojo;
import com.cbs.dto.report.RevenueStatementPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.tuple.component.PojoComponentTuplizer;

@Stateless(mappedName = "ALMReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ALMReportFacade implements ALMReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    GLReportFacadeRemote glReport;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    RbiMonthlyReportFacadeRemote rbiFacadeRemote;
    @EJB
    ALMQtrReportFacadeRemote almQtrFacadeRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd_1 = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat nft = new DecimalFormat("0.00");

    /**
     * ********************************************** TL ROI WISE ****************************
     */
    @Override
    public List<TlAccountWisePojo> tlAcWise(String date, String brCode) throws ApplicationException {
        List<TlAccountWisePojo> tlRoiWisePojos = new ArrayList<TlAccountWisePojo>();
        try {
            double prinAmt = 0, roi = 0, instAmt = 0, buk1 = 0, buk2 = 0, buk3 = 0, buk4 = 0, buk5 = 0, buk6 = 0, buk7 = 0, buk8 = 0;
            String disDT = "", instDT = "", prd = "", installementDT = "";
            int buckNo = 0, buckStartDay = 0, buckEndDay = 0, multiplyFactor = 0, bucNo = 0, loanMon = 0, monDiff = 0;
            long noOfDays = 0, loanDay = 0;
            String bnkName = null, bnkAddress = null, alphaCode = "";
            List objBan = common.getBranchNameandAddress(brCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List alphacodeList = em.createNativeQuery("select branchname from  branchmaster where brncode = " + Integer.parseInt(brCode)).getResultList();
            if (!alphacodeList.isEmpty()) {
                Vector balanceVect = (Vector) alphacodeList.get(0);
                if (balanceVect.get(0) != null) {
                    alphaCode = balanceVect.get(0).toString();
                }
            }
            List result = new ArrayList();
            List amountList = new ArrayList();
            result = em.createNativeQuery("SELECT DISTINCT A.ACNO FROM accountmaster A, loan_recon B WHERE A.ACCSTATUS != 9 AND A.ACNO = B.ACNO  "
                    + "AND A.ACCTTYPE='" + CbsAcCodeConstant.TERM_LOAN.getAcctCode() + "' AND A.CurBrCode ='" + brCode + "'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    buk1 = 0;
                    buk2 = 0;
                    buk3 = 0;
                    buk4 = 0;
                    buk5 = 0;
                    buk6 = 0;
                    buk7 = 0;
                    buk8 = 0;
                    TlAccountWisePojo balCert = new TlAccountWisePojo();
                    amountList = em.createNativeQuery("SELECT SUM(DRAMT) FROM loan_recon WHERE ACNO='" + record.get(0).toString() + "' AND TRANDESC = 6").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            prinAmt = Double.parseDouble(balanceVect.get(0).toString());
                        } else {
                            prinAmt = 0;
                        }
                    }
                    amountList = em.createNativeQuery("SELECT  INTDEPOSIT FROM accountmaster WHERE ACNO = '" + record.get(0).toString() + "'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            roi = Double.parseDouble(balanceVect.get(0).toString());
                        } else {
                            roi = 0;
                        }
                    }
                    amountList = em.createNativeQuery("SELECT date_format(MIN(DT), '%d/%m/%Y') FROM loan_recon WHERE ACNO='" + record.get(0).toString() + "' AND TRANDESC = 6").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            disDT = balanceVect.get(0).toString();
                        } else {
                            disDT = "";
                        }
                    }
                    amountList = em.createNativeQuery("SELECT MAX(INSTALLAMT) FROM emidetails WHERE ACNO='" + record.get(0).toString() + "' AND STATUS = 'UNPAID'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            instAmt = Double.parseDouble(balanceVect.get(0).toString());
                        } else {
                            instAmt = 0;
                        }
                    }
                    amountList = em.createNativeQuery("SELECT date_format(MIN(DUEDT), '%d/%m/%Y') FROM emidetails WHERE ACNO='" + record.get(0).toString() + "'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            instDT = balanceVect.get(0).toString();
                        } else {
                            instDT = "";
                        }
                    }
                    amountList = em.createNativeQuery("SELECT PERIODICITY FROM emidetails WHERE ACNO='" + record.get(0).toString() + "'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            prd = balanceVect.get(0).toString();
                        } else {
                            prd = "";
                        }
                    }
                    amountList = em.createNativeQuery("SELECT  COUNT(*) FROM emidetails WHERE ACNO='" + record.get(0).toString() + "' AND DUEDT>=now() AND STATUS = 'UNPAID'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            loanMon = Integer.parseInt(balanceVect.get(0).toString());
                        } else {
                            loanMon = 0;
                        }
                    }
                    amountList = em.createNativeQuery("SELECT date_format(MAX(DUEDT),'%Y%m%d') FROM emidetails WHERE ACNO='" + record.get(0).toString() + "' AND DUEDT >=now() AND STATUS = 'UNPAID'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            installementDT = balanceVect.get(0).toString();
                            loanDay = CbsUtil.dayDiff(new Date(), ymd.parse(installementDT));
                        } else {
                            loanDay = 0;
                        }
                    }
                    amountList = em.createNativeQuery("SELECT date_format(MIN(DUEDT),'%Y%m%d') FROM emidetails WHERE ACNO='" + record.get(0).toString() + "' AND DUEDT >=now() AND STATUS = 'UNPAID'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            installementDT = balanceVect.get(0).toString();
                            noOfDays = CbsUtil.dayDiff(new Date(), ymd.parse(installementDT));
                        }
                    }
                    amountList = em.createNativeQuery("SELECT COUNT(*)+1 FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = 'L' AND bucket_end_day<=" + noOfDays + "").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        if (balanceVect.get(0) != null) {
                            bucNo = Integer.parseInt(balanceVect.get(0).toString());
                        }
                    }
                    if (bucNo == 1) {
                        buk1 = instAmt;
                    }
                    if (bucNo == 2) {
                        buk2 = instAmt;
                    }
                    amountList = em.createNativeQuery("SELECT BUCKET_NO,BUCKET_START_DAY,BUCKET_END_DAY, CAST(REPLACE(CAST(ROUND(((BUCKET_END_DAY-BUCKET_START_DAY)/30.0),0) AS UNSIGNED),0,1) AS UNSIGNED) "
                            + "FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = 'I' AND (CAST(REPLACE(CAST(ROUND(((BUCKET_END_DAY-BUCKET_START_DAY)/30.0),0) AS UNSIGNED),0,1) AS UNSIGNED)) > 1 AND BUCKET_END_DAY <= " + loanDay
                            + " UNION "
                            + "SELECT BUCKET_NO,BUCKET_START_DAY,BUCKET_END_DAY, CAST(REPLACE(CAST(ROUND(((BUCKET_END_DAY-BUCKET_START_DAY)/30.0),"
                            + "0) AS UNSIGNED),0,1) AS UNSIGNED) FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = 'I' AND BUCKET_END_DAY >= " + loanDay + " LIMIT 1").getResultList();
                    if (!amountList.isEmpty()) {
                        for (int k = 0; k < amountList.size(); k++) {
                            Vector balanceVect = (Vector) amountList.get(k);
                            if (balanceVect.get(0) != null) {
                                buckNo = Integer.parseInt(balanceVect.get(0).toString());
                            } else {
                                buckNo = 0;
                            }
                            if (balanceVect.get(1) != null) {
                                buckStartDay = Integer.parseInt(balanceVect.get(1).toString());
                            } else {
                                buckStartDay = 0;
                            }
                            if (balanceVect.get(2) != null) {
                                buckEndDay = Integer.parseInt(balanceVect.get(2).toString());
                            } else {
                                buckEndDay = 0;
                            }
                            if (balanceVect.get(3) != null) {
                                multiplyFactor = Integer.parseInt(balanceVect.get(3).toString());
                            } else {
                                multiplyFactor = 0;
                            }
                            monDiff = (int) Math.ceil((loanDay - buckStartDay) / 30.0);
                            if ((buckNo == 3) && ((loanDay - buckEndDay) >= 0)) {
                                buk3 = (instAmt * multiplyFactor);
                            } else if ((buckNo == 3) && ((loanDay - buckEndDay) < 0)) {
                                buk3 = (instAmt * monDiff);
                            }
                            if ((buckNo == 4) && ((loanDay - buckEndDay) >= 0)) {
                                buk4 = (instAmt * multiplyFactor);
                            } else if ((buckNo == 4) && ((loanDay - buckEndDay) < 0)) {
                                buk4 = (instAmt * monDiff);
                            }
                            if ((buckNo == 5) && ((loanDay - buckEndDay) >= 0)) {
                                buk5 = (instAmt * multiplyFactor);
                            } else if ((buckNo == 5) && ((loanDay - buckEndDay) < 0)) {
                                buk5 = (instAmt * monDiff);
                            }
                            if ((buckNo == 6) && ((loanDay - buckEndDay) >= 0)) {
                                buk6 = (instAmt * multiplyFactor);
                            } else if ((buckNo == 6) && ((loanDay - buckEndDay) < 0)) {
                                buk6 = (instAmt * monDiff);
                            }
                            if ((buckNo == 7) && ((loanDay - buckEndDay) >= 0)) {
                                buk7 = (instAmt * multiplyFactor);
                            } else if ((buckNo == 7) && ((loanDay - buckEndDay) < 0)) {
                                buk7 = (instAmt * monDiff);
                            }
                            if ((buckNo == 8) && ((loanDay - buckEndDay) >= 0)) {
                                buk8 = (instAmt * multiplyFactor);
                            } else if ((buckNo == 8) && ((loanDay - buckEndDay) < 0)) {
                                buk8 = (instAmt * monDiff);
                            }
                        }
                    }
                    balCert.setACCNUM(record.get(0).toString());
                    balCert.setPRINAMT(new BigDecimal(prinAmt));
                    balCert.setROI(new BigDecimal(roi));
                    balCert.setDISDT(disDT);
                    balCert.setINSTAMT(new BigDecimal(instAmt));
                    balCert.setINSTDT(instDT);
                    balCert.setPRD(prd);
                    balCert.setBUK1(new BigDecimal(buk1));
                    balCert.setBUK2(new BigDecimal(buk2));
                    balCert.setBUK3(new BigDecimal(buk3));
                    balCert.setBUK4(new BigDecimal(buk4));
                    balCert.setBUK5(new BigDecimal(buk5));
                    balCert.setBUK6(new BigDecimal(buk6));
                    balCert.setBUK7(new BigDecimal(buk7));
                    balCert.setBUK8(new BigDecimal(buk8));
                    balCert.setLOANMONTH(loanMon);
                    balCert.setLOANDAYS(loanDay);
                    balCert.setBankname(bnkName);
                    balCert.setBankaddress(bnkAddress);
                    balCert.setBranchname(alphaCode);
                    if (loanDay > 0) {
                        tlRoiWisePojos.add(balCert);
                    }
                }
            }
            Collections.sort(tlRoiWisePojos);
            return tlRoiWisePojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<AlmTlRoiWiseReportPojo> getTlRoiwiseReport(String date, String brCode) throws ApplicationException {
        List<AlmTlRoiWiseReportPojo> returnList = new ArrayList<AlmTlRoiWiseReportPojo>();
        ComparatorDesc3 cmp = new ComparatorDesc3();
        double roi, buk1 = 0, buk2 = 0, buk3 = 0, buk4 = 0, buk5 = 0, buk6 = 0, buk7 = 0, buk8 = 0;
        String bnkName = null, bnkAddress = null, alphaCode = "";
        try {
            for (TlAccountWisePojo pojo : tlAcWise(date, brCode)) {
                buk1 = 0;
                buk2 = 0;
                buk3 = 0;
                buk4 = 0;
                buk5 = 0;
                buk6 = 0;
                buk7 = 0;
                buk8 = 0;
                roi = pojo.getROI().doubleValue();
                buk1 = pojo.getBUK1().doubleValue();
                buk2 = pojo.getBUK2().doubleValue();
                buk3 = pojo.getBUK3().doubleValue();
                buk4 = pojo.getBUK4().doubleValue();
                buk5 = pojo.getBUK5().doubleValue();
                buk6 = pojo.getBUK6().doubleValue();
                buk7 = pojo.getBUK7().doubleValue();
                buk8 = pojo.getBUK8().doubleValue();
                bnkName = pojo.getBankname();
                bnkAddress = pojo.getBankaddress();
                alphaCode = pojo.getBranchname();
                AlmTlRoiWiseReportPojo object = new AlmTlRoiWiseReportPojo();
                object.setRoi(roi);
                int index = Collections.binarySearch(returnList, object, cmp);
                if (index >= 0) {
                    object = returnList.get(index);
                    object.setBuk1(buk1 + object.getBuk1());
                    object.setBuk2(buk2 + object.getBuk2());
                    object.setBuk3(buk3 + object.getBuk3());
                    object.setBuk4(buk4 + object.getBuk4());
                    object.setBuk5(buk5 + object.getBuk5());
                    object.setBuk6(buk6 + object.getBuk6());
                    object.setBuk7(buk7 + object.getBuk7());
                    object.setBuk8(buk8 + object.getBuk8());
                    object.setBankname(bnkName);
                    object.setBankaddress(bnkAddress);
                    object.setBranchname(alphaCode);
                } else {
                    object.setBuk1(buk1);
                    object.setBuk2(buk2);
                    object.setBuk3(buk3);
                    object.setBuk4(buk4);
                    object.setBuk5(buk5);
                    object.setBuk6(buk6);
                    object.setBuk7(buk7);
                    object.setBuk8(buk8);
                    object.setBankname(bnkName);
                    object.setBankaddress(bnkAddress);
                    object.setBranchname(alphaCode);
                }
                if (index < 0) {
                    returnList.add(object);
                }
                Collections.sort(returnList);
            }
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    class ComparatorDesc3 implements Comparator<AlmTlRoiWiseReportPojo> {

        public int compare(AlmTlRoiWiseReportPojo o1, AlmTlRoiWiseReportPojo o2) {
            return new Double(o1.getRoi()).compareTo(new Double(o2.getRoi()));
        }
    }

    @Override
    public List<AlmTdRoiwiseOverdueReportPojo> getAlmTdRoiWiseOverdueReport(String date, String brCode) throws ApplicationException {
        List<AlmTdRoiwiseOverdueReportPojo> returnList = new ArrayList<AlmTdRoiwiseOverdueReportPojo>();
        try {
            TreeMap<Integer, Double> map = new TreeMap<Integer, Double>();
            List tempList = em.createNativeQuery("SELECT distinct bucket_no,percent_amt FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'L' AND heads_of_acc_no = 29 order by bucket_no").getResultList();
            for (int i = 0; i < tempList.size(); i++) {
                int key = -1;
                double value = -0;
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    key = Integer.parseInt(ele.get(0).toString());
                }
                if (ele.get(1) != null) {
                    value = Double.parseDouble(ele.get(1).toString());
                }
                map.put(key, value);
            }
            TreeMap<Double, Double> totalMap = new TreeMap<Double, Double>();
            List<AlmTdAccountWiseReportPojo> resultList = getAlmTdAccountWiseReport(date, brCode, true);
            for (AlmTdAccountWiseReportPojo pojo : resultList) {
                double roi = pojo.getRoi();
                double outstandingAmount = pojo.getOutstandingAmount();
                if (totalMap.containsKey(roi)) {
                    Double value = totalMap.get(roi);
                    totalMap.remove(roi);
                    totalMap.put(roi, value + outstandingAmount);
                } else {
                    totalMap.put(roi, outstandingAmount);
                }
            }
            Iterator itr = totalMap.entrySet().iterator();
            while (itr.hasNext()) {
                Entry next = (Entry) itr.next();
                double roi = (Double) next.getKey();
                double total = (Double) next.getValue();
                AlmTdRoiwiseOverdueReportPojo pojo = new AlmTdRoiwiseOverdueReportPojo();
                pojo.setRoi(roi);
                pojo.setTotal(total);
                if (map.get(1) != null) {
                    pojo.setBuk1((Math.abs(total) * map.get(1)) / 100);
                }
                if (map.get(2) != null) {
                    pojo.setBuk2((Math.abs(total) * map.get(2)) / 100);
                }
                if (map.get(3) != null) {
                    pojo.setBuk3((Math.abs(total) * map.get(3)) / 100);
                }
                if (map.get(4) != null) {
                    pojo.setBuk4((Math.abs(total) * map.get(4)) / 100);
                }
                if (map.get(5) != null) {
                    pojo.setBuk5((Math.abs(total) * map.get(5)) / 100);
                }
                if (map.get(6) != null) {
                    pojo.setBuk6((Math.abs(total) * map.get(6)) / 100);
                }
                if (map.get(7) != null) {
                    pojo.setBuk7((Math.abs(total) * map.get(7)) / 100);
                }
                if (map.get(8) != null) {
                    pojo.setBuk8((Math.abs(total) * map.get(8)) / 100);
                }
                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<AlmTdAccountWiseReportPojo> getAlmTdAccountWiseReport(String date, String brCode, boolean overdue) throws ApplicationException {
        List<AlmTdAccountWiseReportPojo> returnList = new ArrayList<AlmTdAccountWiseReportPojo>();
        try {

            String str = ">=";
            if (overdue) {
                str = "<";
            }
            List tempList = em.createNativeQuery("SELECT T.ACNO,T.VOUCHERNO,T.ROI,date_format(T.MATDT,'%Y-%m-%d') MATDT,T.CUMUPRINAMT,"
                    + "REPLACE(TIMESTAMPDIFF(DAY,'" + date + "',T.MATDT),'-','') DAYDIFF FROM td_vouchmst T, td_accountmaster A WHERE  A.ACNO= T.ACNO AND "
                    + "T.STATUS = 'A' AND T.MATDT " + str + " '" + date + "' AND A.CURBRCODE = '" + brCode + "'").getResultList();
            for (int i = 0; i < tempList.size(); i++) {
                Vector ele = (Vector) tempList.get(i);
                String acNo = ele.get(0).toString();
                double vchNo = new Double(ele.get(1).toString()).intValue();
                double roi = Double.parseDouble(ele.get(2).toString());

                String matDate = ele.get(3).toString();
                double osAmt = Double.parseDouble(ele.get(4).toString());
                int daysLeft = Integer.parseInt(ele.get(5).toString());
                String rangePeriod = "";
                List tempList2 = em.createNativeQuery("SELECT BUCKET_DESC FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = 'L' AND BUCKET_END_DAY >= "
                        + daysLeft + " and BUCKET_START_DAY <=" + daysLeft).getResultList();
                if (!tempList2.isEmpty()) {
                    ele = (Vector) tempList2.get(0);
                    if (ele != null || ele.get(0) != null) {
                        rangePeriod = ele.get(0).toString();
                    }
                }
                AlmTdAccountWiseReportPojo pojo = new AlmTdAccountWiseReportPojo();
                pojo.setAcNo(acNo);
                pojo.setDaysLeft(daysLeft);
                pojo.setMaturityDate(matDate);
                pojo.setOutstandingAmount(osAmt);

                pojo.setRangePeriod(rangePeriod);
                pojo.setRoi(roi);
                pojo.setVchNo(vchNo);
                pojo.setsNo(i + 1);
                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<AlmTdRoiWiseReportPojo> getAlmTdRoiWiseReport(String date, String brCode) throws ApplicationException {
        List<AlmTdRoiWiseReportPojo> returnList = new ArrayList<AlmTdRoiWiseReportPojo>();
        ComparatorDesc cmp = new ComparatorDesc();
        try {
            for (AlmTdAccountWiseReportPojo pojo : getAlmTdAccountWiseReport(date, brCode, false)) {
                double roi = pojo.getRoi();
                String rangePeriod = pojo.getRangePeriod().trim();
                double outstandingAmount = pojo.getOutstandingAmount();
                AlmTdRoiWiseReportPojo object = new AlmTdRoiWiseReportPojo();
                object.setRoi(roi);
                int index = Collections.binarySearch(returnList, object, cmp);
                if (index >= 0) {
                    object = returnList.get(index);
                    if (rangePeriod.equalsIgnoreCase("1 TO 14 DAYS")) {
                        object.setD1to14days(outstandingAmount + object.getD1to14days());
                    }
                    if (rangePeriod.equalsIgnoreCase("15 TO 28 DAYS")) {
                        object.setD15to28days(outstandingAmount + object.getD15to28days());
                    }
                    if (rangePeriod.equalsIgnoreCase("29 DAYS AND UPTO 3 MONTHS")) {
                        object.setD29daysandupto3months(outstandingAmount + object.getD29daysandupto3months());
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 3 MONTHS AND UPTO 6 MONTHS")) {
                        object.setOver3monthsandupto6months(outstandingAmount + object.getOver3monthsandupto6months());
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 6 MONTHS AND UPTO 12 MONTHS")) {
                        object.setOver6monthsandupto12months(outstandingAmount + object.getOver6monthsandupto12months());
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 1 YEAR AND UPTO 2 YEARS")) {
                        object.setOver1yearandupto2years(outstandingAmount + object.getOver1yearandupto2years());
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 2 YEARS AND UPTO 5 YEARS")) {
                        object.setOver2yearsandupto5years(outstandingAmount + object.getOver2yearsandupto5years());
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 5 YEARS")) {
                        object.setOver5years(outstandingAmount + object.getOver5years());
                    }
                } else {
                    if (rangePeriod.equalsIgnoreCase("1 TO 14 DAYS")) {
                        object.setD1to14days(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("15 TO 28 DAYS")) {
                        object.setD15to28days(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("29 DAYS AND UPTO 3 MONTHS")) {
                        object.setD29daysandupto3months(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 3 MONTHS AND UPTO 6 MONTHS")) {
                        object.setOver3monthsandupto6months(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 6 MONTHS AND UPTO 12 MONTHS")) {
                        object.setOver6monthsandupto12months(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 1 YEAR AND UPTO 2 YEARS")) {
                        object.setOver1yearandupto2years(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 2 YEARS AND UPTO 5 YEARS")) {
                        object.setOver2yearsandupto5years(outstandingAmount);
                    }
                    if (rangePeriod.equalsIgnoreCase("OVER 5 YEARS")) {
                        object.setOver5years(outstandingAmount);
                    }
                }
                if (index < 0) {
                    returnList.add(object);
                }
                Collections.sort(returnList);
            }
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    class ComparatorDesc implements Comparator<AlmTdRoiWiseReportPojo> {

        public int compare(AlmTdRoiWiseReportPojo o1, AlmTdRoiWiseReportPojo o2) {
            return new Double(o1.getRoi()).compareTo(new Double(o2.getRoi()));
        }
    }

    class ComparatorDesc2 implements Comparator<AlmTdRoiwiseOverdueReportPojo> {

        public int compare(AlmTdRoiwiseOverdueReportPojo o1, AlmTdRoiwiseOverdueReportPojo o2) {
            return new Double(o1.getRoi()).compareTo(new Double(o2.getRoi()));
        }
    }

    /**
     * ******************************************************************
     * FOLLOWING THREE FUNCTIONS ARE FOR ALM ANNEXTURE 1
    /*******************************************************************
     */
    /**
     * SELECT *FROM CBS_REP_ALM ('20040101','06','BRANCH') if the List return
     * blank then either the REPTYPE is not 'BRANCH' or ACCOUNTYPEMASTER TABLE
     * DO NOT CONTAIN APPROPRIATE DATA CONVERSION OF CBS_REP_ALM
     */
    @Override
    public List<AlmAnnexture1Table> cbsRepAlm(String dt, String brCode, String repType) throws ApplicationException {

        List<AlmAnnexture1Table> almAnnexture1TableList = new ArrayList<AlmAnnexture1Table>();

        double bucno = 0, percentAmt, cpamt;
        long noofdays = 0;
        int heads_of_ac_no;
        try {
            if (repType.equalsIgnoreCase("BRANCH")) {
                double glTOtal = 0;
                double grandTotal = 0;
                String actnature = "";

                AlmAnnexture1Table almAnnexture1Table = new AlmAnnexture1Table();

                List acTypeList = em.createNativeQuery("SELECT acctnature,acctCode,acctdesc,glhead FROM accounttypemaster "
                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND acctNature IS NOT NULL").getResultList();
                if (acTypeList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                }
                String accode, acctdesc, glhead;
                double crtotal, drtotal, total;

                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    actnature = vec.get(0).toString();
                    accode = vec.get(1).toString();
                    acctdesc = vec.get(2).toString();
                    glhead = vec.get(3).toString();
                    crtotal = 0;
                    drtotal = 0;
                    total = 0;
                    grandTotal = 0;

                    if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                + "where a.acno = r.acno and a.accttype ='" + accode + "' AND r.dt<='" + dt + "' and"
                                + " r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                        Vector vec1 = (Vector) resultSet.get(0);
                        crtotal = Double.parseDouble(vec1.get(0).toString());
                        drtotal = Double.parseDouble(vec1.get(1).toString());
                    }
                    if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                + " where a.acno = d.acno and a.accttype ='" + accode + "' AND d.dt<='" + dt + "' and"
                                + " d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                        Vector vec1 = (Vector) resultSet.get(0);
                        crtotal = Double.parseDouble(vec1.get(0).toString());
                        drtotal = Double.parseDouble(vec1.get(1).toString());
                    }
                    if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List resultlist = cbsRepGlbDebitBal(accode, dt, brCode);
                        crtotal = Double.parseDouble(resultlist.get(0).toString());
                        //oDBal1 = Double.parseDouble(resultlist.get(1).toString());
                    }
                    total = crtotal - drtotal;
                    glTOtal = 0;
                    List resultList = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon where "
                            + "substring(acno,3,8)='" + glhead + "' AND dt<='" + dt + "' and auth='Y' AND SUBSTRING(ACNO,1,2)='" + brCode + "'").getResultList();
                    Vector vec1 = (Vector) resultList.get(0);
                    double crtotal1 = Double.parseDouble(vec1.get(0).toString());
                    double drtotal1 = Double.parseDouble(vec1.get(1).toString());
                    glTOtal = crtotal1 - drtotal1;
                    grandTotal = glTOtal + total;
                    List l1 = em.createNativeQuery("SELECT heads_of_acc_no,bucket_no,percent_amt FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'L' AND heads_of_acc_no = 4").getResultList();
                    if (l1.isEmpty()) {
                        throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                    }
                    for (int j = 0; j < l1.size(); j++) {
                        Vector vec3 = (Vector) l1.get(j);
                        heads_of_ac_no = Integer.parseInt(vec3.get(0).toString());
                        bucno = Double.parseDouble(vec3.get(1).toString());
                        percentAmt = Double.parseDouble(vec3.get(2).toString());
                        almAnnexture1Table = new AlmAnnexture1Table();
                        almAnnexture1Table.setAcName(acctdesc);
                        almAnnexture1Table.setHeadofAcc(heads_of_ac_no);

                        almAnnexture1Table.setBuk1(0);
                        almAnnexture1Table.setBuk2(0);
                        almAnnexture1Table.setBuk3(0);
                        almAnnexture1Table.setBuk4(0);

                        almAnnexture1Table.setBuk5(0);
                        almAnnexture1Table.setBuk6(0);
                        almAnnexture1Table.setBuk7(0);
                        almAnnexture1Table.setBuk8(0);
                        almAnnexture1Table.setActype("A");
                        if (bucno == 1) {
                            almAnnexture1Table.setBuk1(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 2) {
                            almAnnexture1Table.setBuk2(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 3) {
                            almAnnexture1Table.setBuk3(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 4) {
                            almAnnexture1Table.setBuk4(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 5) {
                            almAnnexture1Table.setBuk5(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 6) {
                            almAnnexture1Table.setBuk6(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 7) {
                            almAnnexture1Table.setBuk7(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (bucno == 8) {
                            almAnnexture1Table.setBuk8(Math.abs(grandTotal) * percentAmt / 100);
                        }
                        if (grandTotal < 0) {
                            almAnnexture1Table.setActype("A");
                        } else if (grandTotal > 0) {
                            almAnnexture1Table.setActype("L");
                        }
                        int flag = 0;
                        for (int l = 0; l < almAnnexture1TableList.size(); l++) {
                            AlmAnnexture1Table almAnnexture1Table1 = almAnnexture1TableList.get(l);
                            if (almAnnexture1Table1.getAcName().equalsIgnoreCase(acctdesc)) {
                                flag = 1;
                                almAnnexture1Table1.setBuk1(almAnnexture1Table1.getBuk1() + almAnnexture1Table.getBuk1());
                                almAnnexture1Table1.setBuk2(almAnnexture1Table1.getBuk2() + almAnnexture1Table.getBuk2());
                                almAnnexture1Table1.setBuk3(almAnnexture1Table1.getBuk3() + almAnnexture1Table.getBuk3());

                                almAnnexture1Table1.setBuk4(almAnnexture1Table1.getBuk4() + almAnnexture1Table.getBuk4());
                                almAnnexture1Table1.setBuk5(almAnnexture1Table1.getBuk5() + almAnnexture1Table.getBuk5());
                                almAnnexture1Table1.setBuk6(almAnnexture1Table1.getBuk6() + almAnnexture1Table.getBuk6());
                                almAnnexture1Table1.setBuk7(almAnnexture1Table1.getBuk7() + almAnnexture1Table.getBuk7());
                                almAnnexture1Table1.setBuk8(almAnnexture1Table1.getBuk8() + almAnnexture1Table.getBuk8());
                            }
                        }
                        if (flag == 0) {
                            almAnnexture1TableList.add(almAnnexture1Table);
                        }
                    }
                }
                almAnnexture1Table = new AlmAnnexture1Table();
                almAnnexture1Table.setAcName("TERM DEPOSITS");
                almAnnexture1Table.setActype("L");

                almAnnexture1Table.setBuk1(0);
                almAnnexture1Table.setBuk2(0);
                almAnnexture1Table.setBuk3(0);

                almAnnexture1Table.setBuk4(0);
                almAnnexture1Table.setBuk5(0);
                almAnnexture1Table.setBuk6(0);
                almAnnexture1Table.setBuk7(0);

                almAnnexture1Table.setBuk7(0);
                almAnnexture1Table.setBuk8(0);
                almAnnexture1Table.setHeadofAcc(0);
                almAnnexture1TableList.add(almAnnexture1Table);
                List resultSet = em.createNativeQuery("SELECT t.ACNO,SUM(t.CRAMT)-SUM(t.DRAMT) FROM td_recon t, td_accountmaster a where "
                        + "a.acno = t.acno and a.accttype ='" + CbsAcCodeConstant.FIXED_DEPOSIT.getAcctCode() + "' AND t.dt<='" + dt + "' "
                        + "and t.auth='Y' AND a.curbrcode='" + brCode + "' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                        + "HAVING SUM(t.CRAMT)-SUM(t.DRAMT)>0 ORDER BY t.ACNO").getResultList();
                if (resultSet.isEmpty()) {
                    throw new ApplicationException("Data does not exist in TD_RECON");
                }
                for (int i = 0; i < resultSet.size(); i++) {
                    Vector vec = (Vector) resultSet.get(i);
                    String acno = vec.get(0).toString();
                    cpamt = Double.parseDouble(vec.get(1).toString());
                    List dtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt), '%Y%m%d'),'19000101') FROM td_vouchmst WHERE ACNO = '" + acno + "'").getResultList();
                    if (!dtList.isEmpty()) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        Vector vec4 = (Vector) dtList.get(0);
                        String maddt = vec4.get(0).toString();
                        Date maddt1 = null, dt1 = null;
                        try {
                            maddt1 = formatter.parse(maddt);
                            dt1 = formatter.parse(dt);
                        } catch (Exception e) {
                        }
                        if (maddt1.compareTo(dt1) >= 0) {
                            noofdays = CbsUtil.dayDiff(ymd.parse(dt), ymd.parse(maddt));
                            int size = em.createNativeQuery("SELECT * FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = 'L' AND bucket_end_day<=" + noofdays + "").getResultList().size();
                            bucno = size + 1;
                            for (int k = 0; k < almAnnexture1TableList.size(); k++) {
                                almAnnexture1Table = new AlmAnnexture1Table();
                                almAnnexture1Table = almAnnexture1TableList.get(k);
                                if (almAnnexture1Table.getAcName().equalsIgnoreCase("TERM DEPOSITS")) {
                                    if (bucno == 1) {
                                        almAnnexture1Table.setBuk1(almAnnexture1Table.getBuk1() + cpamt);
                                    }
                                    if (bucno == 2) {
                                        almAnnexture1Table.setBuk2(almAnnexture1Table.getBuk2() + cpamt);
                                    }
                                    if (bucno == 3) {
                                        almAnnexture1Table.setBuk3(almAnnexture1Table.getBuk3() + cpamt);
                                    }
                                    if (bucno == 4) {
                                        almAnnexture1Table.setBuk4(almAnnexture1Table.getBuk4() + cpamt);
                                    }
                                    if (bucno == 5) {
                                        almAnnexture1Table.setBuk5(almAnnexture1Table.getBuk5() + cpamt);
                                    }
                                    if (bucno == 6) {
                                        almAnnexture1Table.setBuk6(almAnnexture1Table.getBuk6() + cpamt);
                                    }
                                    if (bucno == 7) {
                                        almAnnexture1Table.setBuk7(almAnnexture1Table.getBuk7() + cpamt);
                                    }
                                    if (bucno == 8) {
                                        almAnnexture1Table.setBuk8(almAnnexture1Table.getBuk8() + cpamt);
                                    }
                                }
                            }
                        }
                        if (maddt1.compareTo(dt1) < 0) {
                            for (int k = 0; k < almAnnexture1TableList.size(); k++) {
                                almAnnexture1Table = new AlmAnnexture1Table();
                                almAnnexture1Table = almAnnexture1TableList.get(k);
                                if (almAnnexture1Table.getAcName().equalsIgnoreCase("TERM DEPOSITS")) {
                                    almAnnexture1Table.setBuk1(almAnnexture1Table.getBuk1() + cpamt);
                                }
                            }
                        }
                    }
                }
                // }
                int check = 0;
                for (int n = 0; n < almAnnexture1TableList.size(); n++) {
                    if (almAnnexture1TableList.get(n).getAcName().equalsIgnoreCase("TERM LOAN")) {
                        almAnnexture1TableList.get(n).setActype("A");
                        check = 1;
                        break;
                    }
                }
                if (check == 0) {
                    almAnnexture1Table = new AlmAnnexture1Table();
                    almAnnexture1Table.setAcName("TERM LOAN");
                    almAnnexture1Table.setHeadofAcc(0);
                    almAnnexture1Table.setBuk1(0);
                    almAnnexture1Table.setBuk2(0);
                    almAnnexture1Table.setBuk3(0);
                    almAnnexture1Table.setBuk4(0);
                    almAnnexture1Table.setBuk5(0);
                    almAnnexture1Table.setBuk6(0);
                    almAnnexture1Table.setBuk7(0);
                    almAnnexture1Table.setBuk7(0);
                    almAnnexture1Table.setBuk8(0);
                    almAnnexture1Table.setActype("A");
                    almAnnexture1TableList.add(almAnnexture1Table);
                }
                List resultSet1 = em.createNativeQuery("SELECT r.ACNO,SUM(r.DRAMT)-SUM(r.CRAMT) FROM loan_recon R, accountmaster a "
                        + "WHERE a.acno = r.acno and a.accttype ='" + CbsAcCodeConstant.TERM_LOAN.getAcctCode() + "' AND r.dt<='" + dt + "' "
                        + "and r.auth='Y' and a.curbrCode = '" + brCode + "' GROUP BY r.ACNO HAVING SUM(r.DRAMT)-SUM(r.CRAMT)>0 ORDER BY r.ACNO").getResultList();
                if (!resultSet1.isEmpty()) {
                    for (int i = 0; i < resultSet1.size(); i++) {
                        Vector vec = (Vector) resultSet1.get(i);
                        String acno = vec.get(0).toString();
                        double lamt = Double.parseDouble(vec.get(1).toString());
                        List dtList = em.createNativeQuery("SELECT coalesce(date_format(MAX(DUEDT), '%Y%m%d'),0) FROM emidetails WHERE ACNO='" + acno + "' AND DUEDT >='" + dt + "' AND STATUS = 'UNPAID'").getResultList();
                        if (!dtList.isEmpty()) {
                            Vector vec1 = (Vector) dtList.get(0);
                            if (vec1.get(0).toString().equalsIgnoreCase("0")) {
                                noofdays = 0;
                            } else {
                                Vector vec4 = (Vector) dtList.get(0);
                                String installmentdt = vec4.get(0).toString();
                                noofdays = CbsUtil.dayDiff(ymd.parse(dt), ymd.parse(installmentdt));

                            }
                            int size = em.createNativeQuery("SELECT * FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = 'L' AND bucket_end_day<=" + noofdays + "").getResultList().size();
                            bucno = size + 1;
                            for (int k = 0; k < almAnnexture1TableList.size(); k++) {
                                almAnnexture1Table = new AlmAnnexture1Table();
                                almAnnexture1Table = almAnnexture1TableList.get(k);
                                if (almAnnexture1Table.getAcName().equalsIgnoreCase("TERM LOAN")) {
                                    if (bucno == 1) {
                                        almAnnexture1Table.setBuk1(almAnnexture1Table.getBuk1() + lamt);
                                    }
                                    if (bucno == 2) {
                                        almAnnexture1Table.setBuk2(almAnnexture1Table.getBuk2() + lamt);
                                    }
                                    if (bucno == 3) {
                                        almAnnexture1Table.setBuk3(almAnnexture1Table.getBuk3() + lamt);
                                    }
                                    if (bucno == 4) {
                                        almAnnexture1Table.setBuk4(almAnnexture1Table.getBuk4() + lamt);
                                    }
                                    if (bucno == 5) {
                                        almAnnexture1Table.setBuk5(almAnnexture1Table.getBuk5() + lamt);
                                    }
                                    if (bucno == 6) {
                                        almAnnexture1Table.setBuk6(almAnnexture1Table.getBuk6() + lamt);
                                    }
                                    if (bucno == 7) {
                                        almAnnexture1Table.setBuk7(almAnnexture1Table.getBuk7() + lamt);
                                    }
                                    if (bucno == 8) {
                                        almAnnexture1Table.setBuk8(almAnnexture1Table.getBuk8() + lamt);
                                    }
                                }
                            }
                        }
                    }
                }
                List resultset = em.createNativeQuery("SELECT r.ACNO,SUM(r.CRAMT)-SUM(r.DRAMT) FROM loan_recon R, accountmaster a "
                        + "WHERE a.acno = r.acno and a.accttype ='" + CbsAcCodeConstant.TERM_LOAN.getAcctCode() + "' AND r.dt<='" + dt + "' "
                        + "and r.auth='Y' and a.curbrCode = '" + brCode + "' GROUP BY r.ACNO HAVING SUM(r.CRAMT)-SUM(r.DRAMT)>0 ORDER BY r.ACNO").getResultList();
                if (!resultset.isEmpty()) {
                    for (int k = 0; k < resultset.size(); k++) {
                        Vector vec = (Vector) resultset.get(k);
                        double lamt = Double.parseDouble(vec.get(1).toString());
                        for (int j = 0; j < almAnnexture1TableList.size(); j++) {
                            almAnnexture1Table = new AlmAnnexture1Table();
                            almAnnexture1Table = almAnnexture1TableList.get(j);
                            if (almAnnexture1Table.getAcName().equalsIgnoreCase("TERM LOAN")) {
                                almAnnexture1Table.setBuk1(almAnnexture1Table.getBuk1() - lamt);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < almAnnexture1TableList.size(); i++) {
                Double b1 = almAnnexture1TableList.get(i).getBuk1();
                Double b2 = almAnnexture1TableList.get(i).getBuk2();
                Double b3 = almAnnexture1TableList.get(i).getBuk3();
                Double b4 = almAnnexture1TableList.get(i).getBuk4();
                Double b5 = almAnnexture1TableList.get(i).getBuk5();
                Double b6 = almAnnexture1TableList.get(i).getBuk6();
                Double b7 = almAnnexture1TableList.get(i).getBuk7();
                Double b8 = almAnnexture1TableList.get(i).getBuk8();
                almAnnexture1TableList.get(i).setTotal(b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8);
            }
            return almAnnexture1TableList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * IMPLEMENTATIONS OF CBS_REP_GLB_DEBIT_BAL FUNCTION
     */
    @Override
    public List cbsRepGlbDebitBal(String accode, String dt, String brncode) throws ApplicationException {
        double tot = 0d;
        double acbal = 0d;
        List returnList = new ArrayList();
        try {
            List resultList = em.createNativeQuery("SELECT SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0)) FROM ca_recon WHERE "
                    + "valuedt<='" + dt + "' AND SUBSTRING(acno,3,2) ='" + accode + "'  AND auth = 'Y' "
                    + "AND acno in (select acno from accountmaster where curbrcode='" + brncode + "') GROUP BY acno").getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                Vector vec = (Vector) resultList.get(i);
                double amt = Double.parseDouble(vec.get(0).toString());
                if (amt > 0) {
                    tot = tot + amt;
                } else {
                    acbal = acbal + amt;
                }
            }
            returnList.add(tot);
            returnList.add(acbal);
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ******************************************************************
     * END OF FUNCTIONS FOR ALM ANNEXTURE 1
    /*******************************************************************
     */
    /**
     * ---------------------------------------------------------------------------------------------------*
     */
    /**
     * -----------ALM REPORT FOR ALM ANNEXTURE I PROFILE_PARAMETER = 'L' (
     * L=Liquidity )-------------
     */
    public List hoAlm(String date, String brCode, String repOpt) throws ApplicationException {
        List<AlmPojo> almPojoTable = new ArrayList<AlmPojo>();
        try {
            List almMasterList = em.createNativeQuery("SELECT GCODE, SGCODE, CODE, DESCRIPTION, HEADTYPE, CLASS, ACTYPE, HEAD_ACC_NO FROM cbs_alm_master "
                    + "WHERE PROFILE_PARAMETER = 'L' ORDER BY SNO, GCODE,SGCODE").getResultList();
            AlmPojo almPojo = new AlmPojo();
            Double amt = 0d, repOption = 0d;
            long noOfDays = 0;
            if (repOpt.equalsIgnoreCase("T")) {
                repOption = 1000d;
            } else if (repOpt.equalsIgnoreCase("L")) {
                repOption = 100000d;
            } else if (repOpt.equalsIgnoreCase("C")) {
                repOption = 10000000d;
            }
            if (repOpt.equalsIgnoreCase("R")) {
                repOption = 1d;
            }
            List almExist = em.createNativeQuery("select * from parameterinfo_report where reportname='ALM'").getResultList();
            if (!almMasterList.isEmpty()) {
                for (int i = 0; i < almMasterList.size(); i++) {
                    Vector almMasterVect = (Vector) almMasterList.get(i);
                    Integer gCode = Integer.parseInt(almMasterVect.get(0).toString());
                    Integer sgCode = Integer.parseInt(almMasterVect.get(1).toString());
                    String code = almMasterVect.get(2).toString();
                    String desc = almMasterVect.get(3).toString();
                    String headType = almMasterVect.get(4).toString();
                    String classType = almMasterVect.get(5).toString();
                    String acType = almMasterVect.get(6).toString();
                    String headAcNo = almMasterVect.get(7).toString();
                    double crTotal, drTotal, totalCrDr = 0;

                    /**
                     * ************************* after date (Liability)(Future
                     * FD/RD/Recovery (Loan) **********************
                     */
                    if (headAcNo.equalsIgnoreCase("NULL") || headAcNo.equalsIgnoreCase("")) {
                        if (!acType.equalsIgnoreCase("")) {
                            List acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                    + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                            if (acTypeList.isEmpty()) {
                                throw new ApplicationException("Data does not exist in AccountTypeMaster");
                            }
                            String acCode, acctDesc, glHead;

                            for (int k = 0; k < acTypeList.size(); k++) {
                                Vector vec = (Vector) acTypeList.get(k);
                                String actnature = vec.get(0).toString();
                                acctDesc = vec.get(2).toString();
                                glHead = vec.get(3).toString();
                                crTotal = 0;
                                drTotal = 0;
                                totalCrDr = 0;
                                List resultSet = null;
                                if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    if (brCode.equalsIgnoreCase("90")) {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt<='" + date + "' "
                                                + " and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT)<>0 ORDER BY t.ACNO").getResultList();
                                    } else {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt<='" + date + "' "
                                                + " and t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT)<>0  ORDER BY t.ACNO").getResultList();
                                    }


                                } else if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                                        || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                                        || (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC))/*&& (headType.equalsIgnoreCase("A"))*/) {
                                    /**
                                     * **************************************
                                     * When Head Type = 'A' means Non NPA
                                     * Account
                                     * ***************************************
                                     */
                                    almPojo = new AlmPojo();
                                    almPojo.setAcType(acType);
                                    almPojo.setBuk1(0d);
                                    almPojo.setBuk2(0d);
                                    almPojo.setBuk3(0d);
                                    almPojo.setBuk4(0d);
                                    almPojo.setBuk5(0d);
                                    almPojo.setBuk6(0d);
                                    almPojo.setBuk7(0d);
                                    almPojo.setBuk8(0d);
                                    almPojo.setClassType(classType);
                                    //almPojo.setCode(code);
                                    almPojo.setDescription(desc);
                                    almPojo.setHeadOfAcc(headType);
                                    almPojo.setSgCode(sgCode);
                                    almPojo.setTotal(0d);
                                    almPojo.setgCode(gCode);
                                    List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                            + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = 'L' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                                    String dt1, dt2 = null, dt3;
                                    List getLoanValueList = null;
                                    Vector getLoanValueVect;
                                    if (!almBktMastList.isEmpty()) {
                                        for (int j = 0; j < almBktMastList.size(); j++) {
                                            Vector almBktMastVect = (Vector) almBktMastList.get(j);
                                            Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                            String recordStatus = almBktMastVect.get(1).toString();
                                            Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                            String bktDesc = almBktMastVect.get(3).toString();
                                            Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                            Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                            Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());

                                            if (bktStartDay == 1) {
                                                dt1 = CbsUtil.dateAdd(date, bktStartDay);
                                                dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                                if (brCode.equalsIgnoreCase("90")) {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "'  AND "
                                                                + " A.ACNO=L.ACNO AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();

                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + acType + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
                                                    }
                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "'AND  SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + acType + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
                                                    }
                                                }
                                                getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                amt = (Double) getLoanValueVect.get(0) / repOption;
                                            } else {
                                                dt3 = CbsUtil.dateAdd(dt2, 1);
                                                dt1 = dt3;
                                                dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                if (brCode.equalsIgnoreCase("90")) {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <='" + date + "' AND AUTH = 'Y' AND"
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2)  = '" + acType + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) ='" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + acType + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();
                                                    }

                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' "
                                                                + " AND DT <='" + date + "' AND AUTH = 'Y' AND ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "' AND  SUBSTRING(ACNO,3,2) ='" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + acType + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();
                                                    }

                                                }

                                                getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                amt = (Double) getLoanValueVect.get(0) / repOption;
                                            }

                                            if (bktNoMast == 1) {
                                                almPojo.setBuk1(almPojo.getBuk1() + amt);
                                            } else if (bktNoMast == 2) {
                                                almPojo.setBuk2(almPojo.getBuk2() + amt);
                                            } else if (bktNoMast == 3) {
                                                almPojo.setBuk3(almPojo.getBuk3() + amt);
                                            } else if (bktNoMast == 4) {
                                                almPojo.setBuk4(almPojo.getBuk4() + amt);
                                            } else if (bktNoMast == 5) {
                                                almPojo.setBuk5(almPojo.getBuk5() + amt);
                                            } else if (bktNoMast == 6) {
                                                almPojo.setBuk6(almPojo.getBuk6() + amt);
                                            } else if (bktNoMast == 7) {
                                                almPojo.setBuk7(almPojo.getBuk7() + amt);
                                            } else if (bktNoMast == 8) {
                                                almPojo.setBuk8(almPojo.getBuk8() + amt);
                                            }

                                        }
                                        almPojoTable.add(almPojo);
                                    } else {
                                        throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                    }
                                }
//                              /********************This following Condition is not for DL/TL Nature*********************/
                                if ((!actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                    if (!actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                        if (!actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                            if (!resultSet.isEmpty()) {
                                                almPojo = new AlmPojo();
                                                almPojo.setAcType(acType);
                                                almPojo.setBuk1(0d);
                                                almPojo.setBuk2(0d);
                                                almPojo.setBuk3(0d);
                                                almPojo.setBuk4(0d);
                                                almPojo.setBuk5(0d);
                                                almPojo.setBuk6(0d);
                                                almPojo.setBuk7(0d);
                                                almPojo.setBuk8(0d);
                                                almPojo.setClassType(classType);
                                                //almPojo.setCode(code);
                                                almPojo.setDescription(desc);
                                                almPojo.setHeadOfAcc(headType);
                                                almPojo.setSgCode(sgCode);
                                                almPojo.setTotal(0d);
                                                almPojo.setgCode(gCode);
                                                for (int a = 0; a < resultSet.size(); a++) {
                                                    Vector resultSetVec = (Vector) resultSet.get(a);
                                                    String acno = resultSetVec.get(0).toString();
                                                    amt = Double.parseDouble(resultSetVec.get(1).toString()) / repOption;
                                                    List dtList = null;
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                                        dtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + date + "') FROM td_vouchmst WHERE ACNO = '" + acno + "'").getResultList();
                                                    }

                                                    if (!dtList.isEmpty()) {
                                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                                        Vector vec4 = (Vector) dtList.get(0);
                                                        String maddt = vec4.get(0).toString();
                                                        Date maddt1 = null, dt1 = null;
                                                        try {
                                                            maddt1 = formatter.parse(maddt);
                                                            dt1 = formatter.parse(date);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (maddt1.compareTo(dt1) > 0) {
                                                            noOfDays = CbsUtil.dayDiff(ymd.parse(date), ymd.parse(maddt));
                                                        } else {
                                                            noOfDays = 1;
                                                        }
                                                        List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, "
                                                                + "BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,RECORD_MOD_CNT  FROM "
                                                                + "cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = 'L' AND RECORD_STATUS = 'A' "
                                                                + "ORDER BY BUCKET_NO").getResultList();
                                                        if (!almBktMastList.isEmpty()) {
                                                            for (int j = 0; j < almBktMastList.size(); j++) {
                                                                Vector almBktMastVect = (Vector) almBktMastList.get(j);
                                                                Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                                                String recordStatus = almBktMastVect.get(1).toString();
                                                                Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                                                String bktDesc = almBktMastVect.get(3).toString();
                                                                Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                                                Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                                                Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());

                                                                if (noOfDays >= bktStartDay && noOfDays <= bktEndDay) {
                                                                    if (bktNoMast == 1) {
                                                                        almPojo.setBuk1(almPojo.getBuk1() + amt);
                                                                    } else if (bktNoMast == 2) {
                                                                        almPojo.setBuk2(almPojo.getBuk2() + amt);
                                                                    } else if (bktNoMast == 3) {
                                                                        almPojo.setBuk3(almPojo.getBuk3() + amt);
                                                                    } else if (bktNoMast == 4) {
                                                                        almPojo.setBuk4(almPojo.getBuk4() + amt);
                                                                    } else if (bktNoMast == 5) {
                                                                        almPojo.setBuk5(almPojo.getBuk5() + amt);
                                                                    } else if (bktNoMast == 6) {
                                                                        almPojo.setBuk6(almPojo.getBuk6() + amt);
                                                                    } else if (bktNoMast == 7) {
                                                                        almPojo.setBuk7(almPojo.getBuk7() + amt);
                                                                    } else if (bktNoMast == 8) {
                                                                        almPojo.setBuk8(almPojo.getBuk8() + amt);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                                        }
                                                    } else {
                                                        if (actnature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                                                            throw new ApplicationException("Data does not exist in TD_VouchMst");
                                                        }
                                                    }
                                                }
                                                almPojoTable.add(almPojo);
                                            } else {
                                                if (actnature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                                                    throw new ApplicationException("Data does not exist in TD_RECON or td_accountmaster");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            almPojo = new AlmPojo();
                            almPojo.setAcType(acType);
                            almPojo.setBuk1(0d);
                            almPojo.setBuk2(0d);
                            almPojo.setBuk3(0d);
                            almPojo.setBuk4(0d);
                            almPojo.setBuk5(0d);
                            almPojo.setBuk6(0d);
                            almPojo.setBuk7(0d);
                            almPojo.setBuk8(0d);
                            almPojo.setClassType(classType);
                            //almPojo.setCode(code);
                            almPojo.setDescription(desc);
                            almPojo.setHeadOfAcc(headType);
                            almPojo.setSgCode(sgCode);
                            almPojo.setTotal(0d);
                            almPojo.setgCode(gCode);

                            almPojoTable.add(almPojo);
                        }
                    } else {
                        /**
                         * ************************* before date SB/CA ********************************************************
                         */
                        if (acType != null) {
                            List acTypeList = new ArrayList();
                            if (!acType.equalsIgnoreCase("")) {
                                acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                                if (acTypeList.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                }
                                String acCode, acctDesc, glHead;

                                for (int p = 0; p < acTypeList.size(); p++) {
                                    Vector vec = (Vector) acTypeList.get(p);
                                    String actnature = vec.get(0).toString();
                                    acctDesc = vec.get(2).toString();
                                    glHead = vec.get(3).toString();
                                    crTotal = 0;
                                    drTotal = 0;
                                    totalCrDr = 0;
                                    Integer sign = null;
                                    String caQuery;
                                    if (classType.equalsIgnoreCase("O")) {
                                        sign = 1;
                                    } else if (classType.equalsIgnoreCase("I")) {
                                        sign = -1;
                                    }
                                    if (almExist.isEmpty()) {
                                        caQuery = "";
                                    } else {
                                        caQuery = " AND acno in (select acno from ca_recon WHERE DT <= '" + date + "' group by acno having sign(sum(cramt-dramt)) = " + sign + ") ";
                                    }
                                    if (brCode.equalsIgnoreCase("90")) {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt<='" + date + "' and r.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt<='" + date + "' and  d.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (headType.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' " + caQuery
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            } else {
                                                List resultSet = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from ca_recon where "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' " + caQuery
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            }
                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (headType.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0)  FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "') AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString());
                                            crTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                    } else {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt<='" + date + "' and"
                                                    + " r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt<='" + date + "' and"
                                                    + " d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (headType.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') " + caQuery
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            } else {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') " + caQuery
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            }


                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (headType.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0) FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND AUTH = 'Y'  AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "' "
                                                    + "AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND  L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString());
                                            crTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                    }
                                    totalCrDr = crTotal - drTotal;
                                }
                            }
                        }

                        List totalAmtList;

                        almPojo = new AlmPojo();
                        almPojo.setAcType(acType);
                        almPojo.setBuk1(0d);
                        almPojo.setBuk2(0d);
                        almPojo.setBuk3(0d);
                        almPojo.setBuk4(0d);
                        almPojo.setBuk5(0d);
                        almPojo.setBuk6(0d);
                        almPojo.setBuk7(0d);
                        almPojo.setBuk8(0d);
                        almPojo.setClassType(classType);
                        //almPojo.setCode(code);
                        almPojo.setDescription(desc);
                        almPojo.setHeadOfAcc(headType);
                        almPojo.setSgCode(sgCode);
                        almPojo.setTotal(0d);
                        almPojo.setgCode(gCode);
                        Double totalAmt = 0d;
                        if (brCode.equalsIgnoreCase("90")) {
                            /**
                             * *** Head Office***************
                             */
                            if (code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                totalAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(AMT,0)),0) FROM cashinhand WHERE LDATE = (SELECT MAX(LDATE) FROM cashinhand WHERE  LDATE <= '" + date + "')").getResultList();
                                if (totalAmtList.size() == 0) {
                                    throw new ApplicationException("Please check the CASHINHAND table on date " + date);
                                } else {
                                    Vector totalAmtvect = (Vector) totalAmtList.get(0);
                                    totalAmt = (Double.parseDouble(totalAmtvect.get(0).toString()) + totalCrDr) / repOption;
                                }

                            } else if (code.equals("+P&L")) {
                                totalAmt = (glReport.IncomeExp(date, "0A", "0A") + totalCrDr) / repOption;
                            } else if (code.equals("-P&L")) {
                                totalAmt = (glReport.IncomeExp(date, "0A", "0A") + totalCrDr) / repOption;
                            } else {
                                code = code;
                                totalAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0) AS AMT FROM gl_recon WHERE SUBSTRING(ACNO,3,8) = '" + code + "' AND AUTH = 'Y' AND DT <='" + date + "'").getResultList();
                                Vector totalAmtvect = (Vector) totalAmtList.get(0);
                                totalAmt = (Double.parseDouble(totalAmtvect.get(0).toString()) + totalCrDr) / repOption;
                            }

                        } else {
                            /**
                             * *** Branch Wise***************
                             */
                            if (code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                totalAmtList = em.createNativeQuery("SELECT ifnull(AMT,0) FROM cashinhand WHERE LDATE = (SELECT MAX(LDATE) FROM cashinhand WHERE LDATE<='" + date + "')  AND BRNCODE = '" + brCode + "'").getResultList();
                                if (totalAmtList.size() == 0) {
                                    throw new ApplicationException("Please check the CASHINHAND table on date " + date);
                                } else {
                                    Vector totalAmtvect = (Vector) totalAmtList.get(0);
                                    totalAmt = (Double.parseDouble(totalAmtvect.get(0).toString()) + totalCrDr) / repOption;
                                }
                            } else if (code.equals("+P&L")) {
                                totalAmt = (glReport.IncomeExp(date, brCode, brCode) + totalCrDr) / repOption;
                            } else if (code.equals("-P&L")) {
                                totalAmt = (glReport.IncomeExp(date, brCode, brCode) + totalCrDr) / repOption;
                            } else {
                                code = brCode.concat(code).concat("01");
                                totalAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0) AS AMT FROM gl_recon "
                                        + "WHERE ACNO = '" + code + "' AND AUTH = 'Y' AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND DT <='" + date + "'").getResultList();
                                Vector totalAmtvect = (Vector) totalAmtList.get(0);
                                totalAmt = (Double.parseDouble(totalAmtvect.get(0).toString()) + totalCrDr) / repOption;
                            }
                        }



                        if (classType.equalsIgnoreCase("O") && totalAmt > 0) {
                            totalAmt = totalAmt;
                        } else if (classType.equalsIgnoreCase("I") && code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                            totalAmt = totalAmt;
                        } else if (classType.equalsIgnoreCase("I") && totalAmt < 0) {
                            totalAmt = totalAmt;
                        } else {
                            totalAmt = 0.0;
                        }
                        List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'L' AND HEADS_OF_ACC_NO = " + headAcNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                        if (!almAccClassList.isEmpty()) {
                            for (int k = 0; k < almAccClassList.size(); k++) {
                                Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                String flow = almAcClassVect.get(0).toString();
                                Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                if (bucketNo == 1) {
                                    almPojo.setBuk1(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 2) {
                                    almPojo.setBuk2(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 3) {
                                    almPojo.setBuk3(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 4) {
                                    almPojo.setBuk4(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 5) {
                                    almPojo.setBuk5(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 6) {
                                    almPojo.setBuk6(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 7) {
                                    almPojo.setBuk7(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 8) {
                                    almPojo.setBuk8(Math.abs(totalAmt) * percentage / 100);
                                }
                            }
                            //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                        } else {
                            throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                        }
                        almPojoTable.add(almPojo);
                    }
                }
            } else {
                throw new ApplicationException("Data does not exist in CBS_ALM_MASTER");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return almPojoTable;
    }

    /**
     * --------------------------------------------------------------------------------------------------------------------*
     */
    /**
     * -----------ALM REPORT FOR ALM ANNEXTURE II PROFILE_PARAMETER = 'I' (
     * I=Interest Rate Sensitivity )-------------
     */
    public List hoAlmInterestSensitivty(String date, String brCode, String repOpt) throws ApplicationException {
        List<AlmPojo> almPojoTable = new ArrayList<AlmPojo>();
        try {
            List almMasterList = em.createNativeQuery("SELECT GCODE, SGCODE, CODE, DESCRIPTION, HEADTYPE, CLASS, ACTYPE, HEAD_ACC_NO FROM cbs_alm_master "
                    + "WHERE PROFILE_PARAMETER = 'I' ORDER BY SNO, GCODE,SGCODE").getResultList();
            AlmPojo almPojo = new AlmPojo();
            Double amt = 0d, repOption = 0d;
            long noOfDays = 0;
            if (repOpt.equalsIgnoreCase("T")) {
                repOption = 1000d;
            } else if (repOpt.equalsIgnoreCase("L")) {
                repOption = 100000d;
            } else if (repOpt.equalsIgnoreCase("C")) {
                repOption = 10000000d;
            }
            if (repOpt.equalsIgnoreCase("R")) {
                repOption = 1d;
            }
            if (!almMasterList.isEmpty()) {
                for (int i = 0; i < almMasterList.size(); i++) {
                    Vector almMasterVect = (Vector) almMasterList.get(i);
                    Integer gCode = Integer.parseInt(almMasterVect.get(0).toString());
                    Integer sgCode = Integer.parseInt(almMasterVect.get(1).toString());
                    String code = almMasterVect.get(2).toString();
                    String desc = almMasterVect.get(3).toString();
                    String headType = almMasterVect.get(4).toString();
                    String classType = almMasterVect.get(5).toString();
                    String acType = almMasterVect.get(6).toString();
                    String headAcNo = almMasterVect.get(7).toString();
                    double crTotal, drTotal, totalCrDr = 0;

                    /**
                     * ************************* after date (Liability)(Future
                     * FD/RD/Recovery (Loan) **********************
                     */
                    if (headAcNo.equalsIgnoreCase("NULL") || headAcNo.equalsIgnoreCase("")) {
                        if (!acType.equalsIgnoreCase("")) {
                            List acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                    + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                            if (acTypeList.isEmpty()) {
                                throw new ApplicationException("Data does not exist in AccountTypeMaster");
                            }
                            String acCode, acctDesc, glHead;

                            for (int k = 0; k < acTypeList.size(); k++) {
                                Vector vec = (Vector) acTypeList.get(k);
                                String actnature = vec.get(0).toString();
                                acctDesc = vec.get(2).toString();
                                glHead = vec.get(3).toString();
                                crTotal = 0;
                                drTotal = 0;
                                totalCrDr = 0;
                                List resultSet = null;
                                if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    if (brCode.equalsIgnoreCase("90")) {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt<='" + date + "' "
                                                + " and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT)>0 ORDER BY t.ACNO").getResultList();
                                    } else {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt<='" + date + "' "
                                                + " and t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT)>0  ORDER BY t.ACNO").getResultList();
                                    }
                                } else if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                                        || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                                        || (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC))/*&& (headType.equalsIgnoreCase("A"))*/) {
                                    /**
                                     * **************************************
                                     * When Head Type = 'A' means Non NPA
                                     * Account   ***************************************
                                     */
                                    almPojo = new AlmPojo();
                                    almPojo.setAcType(acType);
                                    almPojo.setBuk1(0d);
                                    almPojo.setBuk2(0d);
                                    almPojo.setBuk3(0d);
                                    almPojo.setBuk4(0d);
                                    almPojo.setBuk5(0d);
                                    almPojo.setBuk6(0d);
                                    almPojo.setBuk7(0d);
                                    almPojo.setClassType(classType);
                                    //almPojo.setCode(code);
                                    almPojo.setDescription(desc);
                                    almPojo.setHeadOfAcc(headType);
                                    almPojo.setSgCode(sgCode);
                                    almPojo.setTotal(0d);
                                    almPojo.setgCode(gCode);
                                    List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,RECORD_MOD_CNT "
                                            + " FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = 'I' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                                    String dt1, dt2 = null, dt3;
                                    List getLoanValueList = null;
                                    Vector getLoanValueVect;
                                    if (!almBktMastList.isEmpty()) {
                                        for (int j = 0; j < almBktMastList.size(); j++) {
                                            Vector almBktMastVect = (Vector) almBktMastList.get(j);
                                            Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                            String recordStatus = almBktMastVect.get(1).toString();
                                            Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                            String bktDesc = almBktMastVect.get(3).toString();
                                            Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                            Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                            Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());

                                            if (bktStartDay == 1) {
                                                dt1 = CbsUtil.dateAdd(date, bktStartDay);
                                                dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                                if (brCode.equalsIgnoreCase("90")) {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "'  AND "
                                                                + " A.ACNO=L.ACNO AND date_add(A.OPENINGDT,INTERVAL L.LOAN_PD_MONTH MONTH) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();

                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + acType + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
                                                    }
                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,INTERVAL L.LOAN_PD_MONTH MONTH) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "'AND  SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + acType + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
                                                    }
                                                }
                                                getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                amt = (Double) getLoanValueVect.get(0) / repOption;
                                            } else {
                                                dt3 = CbsUtil.dateAdd(dt2, 1);
                                                dt1 = dt3;
                                                dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                if (brCode.equalsIgnoreCase("90")) {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <='" + date + "' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2)  = '" + acType + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND date_add(A.OPENINGDT,INTERVAL L.LOAN_PD_MONTH MONTH) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) ='" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + acType + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();
                                                    }

                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' "
                                                                + " AND DT <='" + date + "' AND ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,INTERVAL L.LOAN_PD_MONTH MONTH) BETWEEN '"
                                                                + dt1 + "' AND '" + dt2 + "') AND  ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT INSULL(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "' AND  SUBSTRING(ACNO,3,2) ='" + acType + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + acType + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();
                                                    }

                                                }

                                                getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                amt = (Double) getLoanValueVect.get(0) / repOption;
                                            }

                                            if (bktNoMast == 1) {
                                                almPojo.setBuk1(almPojo.getBuk1() + amt);
                                            } else if (bktNoMast == 2) {
                                                almPojo.setBuk2(almPojo.getBuk2() + amt);
                                            } else if (bktNoMast == 3) {
                                                almPojo.setBuk3(almPojo.getBuk3() + amt);
                                            } else if (bktNoMast == 4) {
                                                almPojo.setBuk4(almPojo.getBuk4() + amt);
                                            } else if (bktNoMast == 5) {
                                                almPojo.setBuk5(almPojo.getBuk5() + amt);
                                            } else if (bktNoMast == 6) {
                                                almPojo.setBuk6(almPojo.getBuk6() + amt);
                                            } else if (bktNoMast == 7) {
                                                almPojo.setBuk7(almPojo.getBuk7() + amt);
                                            }

                                        }
                                        almPojoTable.add(almPojo);
                                    } else {
                                        throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                    }
                                }
//                              /********************This following Condition is not for DL/TL Nature*********************/
                                if ((!actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                    if (!actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                        if (!actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                            if (!resultSet.isEmpty()) {
                                                almPojo = new AlmPojo();
                                                almPojo.setAcType(acType);
                                                almPojo.setBuk1(0d);
                                                almPojo.setBuk2(0d);
                                                almPojo.setBuk3(0d);
                                                almPojo.setBuk4(0d);
                                                almPojo.setBuk5(0d);
                                                almPojo.setBuk6(0d);
                                                almPojo.setBuk7(0d);
                                                almPojo.setClassType(classType);
                                                //almPojo.setCode(code);
                                                almPojo.setDescription(desc);
                                                almPojo.setHeadOfAcc(headType);
                                                almPojo.setSgCode(sgCode);
                                                almPojo.setTotal(0d);
                                                almPojo.setgCode(gCode);
                                                for (int a = 0; a < resultSet.size(); a++) {
                                                    Vector resultSetVec = (Vector) resultSet.get(a);
                                                    String acno = resultSetVec.get(0).toString();
                                                    amt = Double.parseDouble(resultSetVec.get(1).toString()) / repOption;
                                                    List dtList = null;
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                                        dtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + date + "') FROM td_vouchmst WHERE ACNO = '" + acno + "'").getResultList();
                                                    }
                                                    if (!dtList.isEmpty()) {
                                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                                        Vector vec4 = (Vector) dtList.get(0);
                                                        String maddt = vec4.get(0).toString();
                                                        Date maddt1 = null, dt1 = null;
                                                        try {
                                                            maddt1 = formatter.parse(maddt);
                                                            dt1 = formatter.parse(date);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (maddt1.compareTo(dt1) >= 0) {
                                                            noOfDays = CbsUtil.dayDiff(ymd.parse(date), ymd.parse(maddt));
                                                        } else {
                                                            noOfDays = 1;
                                                        }
                                                        List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = 'I' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                                                        if (!almBktMastList.isEmpty()) {
                                                            for (int j = 0; j < almBktMastList.size(); j++) {
                                                                Vector almBktMastVect = (Vector) almBktMastList.get(j);
                                                                Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                                                String recordStatus = almBktMastVect.get(1).toString();
                                                                Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                                                String bktDesc = almBktMastVect.get(3).toString();
                                                                Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                                                Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                                                Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());

                                                                if (noOfDays >= bktStartDay && noOfDays <= bktEndDay) {
                                                                    if (bktNoMast == 1) {
                                                                        almPojo.setBuk1(almPojo.getBuk1() + amt);
                                                                    } else if (bktNoMast == 2) {
                                                                        almPojo.setBuk2(almPojo.getBuk2() + amt);
                                                                    } else if (bktNoMast == 3) {
                                                                        almPojo.setBuk3(almPojo.getBuk3() + amt);
                                                                    } else if (bktNoMast == 4) {
                                                                        almPojo.setBuk4(almPojo.getBuk4() + amt);
                                                                    } else if (bktNoMast == 5) {
                                                                        almPojo.setBuk5(almPojo.getBuk5() + amt);
                                                                    } else if (bktNoMast == 6) {
                                                                        almPojo.setBuk6(almPojo.getBuk6() + amt);
                                                                    } else if (bktNoMast == 7) {
                                                                        almPojo.setBuk7(almPojo.getBuk7() + amt);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                                        }
                                                    } else {
                                                        if (actnature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                                                            throw new ApplicationException("Data does not exist in TD_VouchMst");
                                                        }

                                                    }
                                                }
                                                almPojoTable.add(almPojo);
                                            } else {
                                                if (actnature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                                                    throw new ApplicationException("Data does not exist in TD_RECON or td_accountmaster");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            almPojo = new AlmPojo();
                            almPojo.setAcType(acType);
                            almPojo.setBuk1(0d);
                            almPojo.setBuk2(0d);
                            almPojo.setBuk3(0d);
                            almPojo.setBuk4(0d);
                            almPojo.setBuk5(0d);
                            almPojo.setBuk6(0d);
                            almPojo.setBuk7(0d);
                            almPojo.setClassType(classType);
                            //almPojo.setCode(code);
                            almPojo.setDescription(desc);
                            almPojo.setHeadOfAcc(headType);
                            almPojo.setSgCode(sgCode);
                            almPojo.setTotal(0d);
                            almPojo.setgCode(gCode);

                            almPojoTable.add(almPojo);
                        }
                    } else {
                        /**
                         * ************************* before date SB/CA ********************************************************
                         */
                        if (acType != null) {
                            List acTypeList = new ArrayList();
                            if (!acType.equalsIgnoreCase("")) {
                                acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                                if (acTypeList.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                }
                                String acCode, acctDesc, glHead;

                                for (int p = 0; p < acTypeList.size(); p++) {
                                    Vector vec = (Vector) acTypeList.get(p);
                                    String actnature = vec.get(0).toString();
                                    acctDesc = vec.get(2).toString();
                                    glHead = vec.get(3).toString();
                                    crTotal = 0;
                                    drTotal = 0;
                                    totalCrDr = 0;

                                    if (brCode.equalsIgnoreCase("90")) {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt<='" + date + "' and  r.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt<='" + date + "' and d.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (headType.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on a.acno=l.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            } else {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on a.acno=l.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            }
                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (headType.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0)  FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "') AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString());
                                            crTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                    } else {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt<='" + date + "' and r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt<='" + date + "' and d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString());
                                            drTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (headType.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') "
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on a.acno=l.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + acType
                                                        + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            } else {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') "
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on a.acno=l.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + acType
                                                        + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString());
                                                drTotal = Double.parseDouble(vec1.get(1).toString());
                                            }


                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (headType.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0) FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND DT <= '" + date + "' AND  ACNO IN (SELECT A.ACNO FROM "
                                                    + "accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + date + "' "
                                                    + "AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) AND  ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT "
                                                    + "JOIN loan_appparameter L ON A.ACNO=L.ACNO  WHERE A.ACCSTATUS IN('11','12','13') AND A.curbrcode= '"
                                                    + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + acType + "' AND  L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString());
                                            crTotal = Double.parseDouble(vec1.get(1).toString());
                                        }
                                    }
                                    totalCrDr = crTotal - drTotal;
                                }
                            }
                        }

                        List totalAmtList;

                        almPojo = new AlmPojo();
                        almPojo.setAcType(acType);
                        almPojo.setBuk1(0d);
                        almPojo.setBuk2(0d);
                        almPojo.setBuk3(0d);
                        almPojo.setBuk4(0d);
                        almPojo.setBuk5(0d);
                        almPojo.setBuk6(0d);
                        almPojo.setBuk7(0d);
                        almPojo.setClassType(classType);
                        //almPojo.setCode(code);
                        almPojo.setDescription(desc);
                        almPojo.setHeadOfAcc(headType);
                        almPojo.setSgCode(sgCode);
                        almPojo.setTotal(0d);
                        almPojo.setgCode(gCode);

                        if (brCode.equalsIgnoreCase("90")) {
                            /**
                             * *** Head Office***************
                             */
                            if (code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                totalAmtList = em.createNativeQuery("SELECT SUM(ifnull(AMT,0)) FROM cashinhand WHERE LDATE = (SELECT MAX(LDATE) FROM "
                                        + "cashinhand WHERE  LDATE <= '" + date + "'").getResultList();
                                if (totalAmtList.size() == 0) {
                                    throw new ApplicationException("Please check the CASHINHAND table on date " + date);
                                }
                            } else {
                                code = code;
                                totalAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0) AS AMT FROM gl_recon "
                                        + "WHERE SUBSTRING(ACNO,3,8) = '" + code + "' AND AUTH = 'Y' AND DT <='" + date + "'").getResultList();
                            }

                        } else {
                            /**
                             * *** Branch Wise***************
                             */
                            if (code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                totalAmtList = em.createNativeQuery("SELECT ifnull(AMT,0) FROM cashinhand WHERE LDATE = (SELECT MAX(LDATE) FROM "
                                        + "cashinhand WHERE LDATE<='" + date + "')  AND BRNCODE = '" + brCode + "'").getResultList();
                                if (totalAmtList.size() == 0) {
                                    throw new ApplicationException("Please check the CASHINHAND table on date " + date);
                                }
                            } else {
                                code = brCode.concat(code).concat("01");
                                totalAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0) AS AMT FROM gl_recon "
                                        + "WHERE ACNO = '" + code + "' AND AUTH = 'Y' AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND DT <='" + date + "'").getResultList();
                            }
                        }


                        Vector totalAmtvect = (Vector) totalAmtList.get(0);
                        Double totalAmt = (Double.parseDouble(totalAmtvect.get(0).toString()) + totalCrDr) / repOption;

                        List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class "
                                + "WHERE PROFILE_PARAMETER = 'I' AND HEADS_OF_ACC_NO = " + headAcNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                        if (!almAccClassList.isEmpty()) {
                            for (int k = 0; k < almAccClassList.size(); k++) {
                                Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                String flow = almAcClassVect.get(0).toString();
                                Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                if (bucketNo == 1) {
                                    almPojo.setBuk1(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 2) {
                                    almPojo.setBuk2(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 3) {
                                    almPojo.setBuk3(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 4) {
                                    almPojo.setBuk4(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 5) {
                                    almPojo.setBuk5(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 6) {
                                    almPojo.setBuk6(Math.abs(totalAmt) * percentage / 100);
                                }
                                if (bucketNo == 7) {
                                    almPojo.setBuk7(Math.abs(totalAmt) * percentage / 100);
                                }
                            }
                            //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                        } else {
                            throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                        }
                        almPojoTable.add(almPojo);
                    }
                }
            } else {
                throw new ApplicationException("Data does not exist in CBS_ALM_MASTER");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return almPojoTable;
    }

//    public List revenueStatementOfPL(String fromDt, String todt, String brCode, String repOpt) throws ApplicationException {
//        throw new ApplicationException("Not Implemented");
//    }
//    
//    public List revenueStatement(String fromDt, String todt, String brCode, String repOpt) throws ApplicationException {
//        List<RevenueStatementPojo> revenuePojoTable = new ArrayList<RevenueStatementPojo>();
//        try {
//            List almMasterList = em.createNativeQuery("select gcode, sgcode, code, description, headtype, class, actype, head_acc_no from "
//                    + "cbs_revenue_master ORDER BY HEADTYPE desc, gcode,sgcode").getResultList();
//            RevenueStatementPojo revenuePojo = new RevenueStatementPojo();
//            Double amt = 0d, repOption = 0d;
//            long noOfDays = 0;
//            if (repOpt.equalsIgnoreCase("T")) {
//                repOption = 1000d;
//            } else if (repOpt.equalsIgnoreCase("L")) {
//                repOption = 100000d;
//            } else if (repOpt.equalsIgnoreCase("C")) {
//                repOption = 10000000d;
//            }
//            if (repOpt.equalsIgnoreCase("R")) {
//                repOption = 1d;
//            }
//            if (!almMasterList.isEmpty()) {
//                for (int i = 0; i < almMasterList.size(); i++) {
//                    Vector almMasterVect = (Vector) almMasterList.get(i);
//                    Integer gCode = Integer.parseInt(almMasterVect.get(0).toString());
//                    Integer sgCode = Integer.parseInt(almMasterVect.get(1).toString());
//                    String code = almMasterVect.get(2).toString();
//                    String desc = almMasterVect.get(3).toString();
//                    String headType = almMasterVect.get(4).toString();
//                    String classType = almMasterVect.get(5).toString();
//                    String acType = almMasterVect.get(6).toString();
//                    String headAcNo = almMasterVect.get(7).toString();
//                    double crTotal = 0, drTotal = 0, totalCrDr = 0, openingBal = 0, closingBal = 0;
//
//                    revenuePojo = new RevenueStatementPojo();
//                    revenuePojo.setAcType(acType);
//                    revenuePojo.setOpBal(0d);
//                    revenuePojo.setCrAmt(0d);
//                    revenuePojo.setDrAmt(0d);
//                    revenuePojo.setClassType(classType);
//                    revenuePojo.setCode(code);
//                    revenuePojo.setDescription(desc);
//                    revenuePojo.setHeadOfAcc(headAcNo);
//                    revenuePojo.setHeadType(headType);
//                    revenuePojo.setSgCode(sgCode);
//                    revenuePojo.setgCode(gCode);
//
//                    if (acType != null) {
//                        List acTypeList;
//                        if (!acType.equalsIgnoreCase("")) {
//                            acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
//                                    + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
//                            if (acTypeList.isEmpty()) {
//                                throw new ApplicationException("Data does not exist in AccountTypeMaster");
//                            }
//                            String acCode, acctDesc, glHead;
//                            for (int p = 0; p < acTypeList.size(); p++) {
//                                Vector vec = (Vector) acTypeList.get(p);
//                                String actnature = vec.get(0).toString();
//                                acctDesc = vec.get(2).toString();
//                                glHead = vec.get(3).toString();
//                                crTotal = 0;
//                                drTotal = 0;
//                                totalCrDr = 0;
//                                closingBal = 0;
//                                if (brCode.equalsIgnoreCase("0A")) {
//                                    /************** ALL BRANCH *******************/
//                                    if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                                        List resultSet = em.createNativeQuery("SELECT ifnull(sum(ifnull(r.cramt,0)),0),ifnull(sum(ifnull(r.dramt,0)),0) from recon r, accountmaster a "
//                                                + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt between '" + fromDt + "' "
//                                                + "AND '" + todt + "' and r.auth='Y'").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("SELECT (ifnull(sum(ifnull(r.cramt,0)),0)-ifnull(sum(ifnull(r.dramt,0)),0)) from recon r, accountmaster a "
//                                                + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt<'" + fromDt + "' "
//                                                + "and r.auth='Y'").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("SELECT (ifnull(sum(ifnull(r.cramt,0)),0)-ifnull(sum(ifnull(r.dramt,0)),0)) from recon r, accountmaster a "
//                                                + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt<='" + todt + "' "
//                                                + "and r.auth='Y'").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//
//                                    }
//                                    if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                                        List resultSet = em.createNativeQuery("select ifnull(sum(ifnull(d.cramt,0)),0),ifnull(sum(ifnull(d.dramt,0)),0) from ddstransaction d, accountmaster a "
//                                                + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt between '" + fromDt + "' "
//                                                + " AND '" + todt + "' and d.auth='Y'").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("select (ifnull(sum(ifnull(d.cramt,0)),0)-ifnull(sum(ifnull(d.dramt,0)),0)) from ddstransaction d, accountmaster a "
//                                                + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt<'" + fromDt + "'  "
//                                                + " and d.auth='Y'").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("select (ifnull(sum(ifnull(d.cramt,0)),0)-ifnull(sum(ifnull(d.dramt,0)),0)) from ddstransaction d, accountmaster a "
//                                                + " where a.acno = d.acno and a.accttype ='" + acType + "' AND d.dt<='" + todt + "'  "
//                                                + " and d.auth='Y'").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                        if (classType.equalsIgnoreCase("O")) {
//                                            List resultSet, openingBalSet, closingBalSet;
//                                            Vector vec1, vec2, vec3;
//                                            openingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)) FROM ca_recon WHERE "
//                                                    + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
//                                            for (int k = 0; k < openingBalSet.size(); k++) {
//                                                vec2 = (Vector) openingBalSet.get(k);
//                                                openingBal = openingBal + Double.parseDouble(vec2.get(0).toString());
//                                            }
//                                            closingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)) FROM ca_recon WHERE "
//                                                    + " dt <= '" + todt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
//                                            for (int k = 0; k < closingBalSet.size(); k++) {
//                                                vec3 = (Vector) closingBalSet.get(k);
//                                                closingBal = closingBal + Double.parseDouble(vec3.get(0).toString());
//                                            }
//                                        } else {
//                                            List resultSet, openingBalSet, closingBalSet;
//                                            Vector vec1, vec2, vec3;
//                                            openingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)) FROM ca_recon WHERE "
//                                                    + "  dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + "  GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
//                                            for (int k = 0; k < openingBalSet.size(); k++) {
//                                                vec2 = (Vector) openingBalSet.get(k);
//                                                openingBal = openingBal + Double.parseDouble(vec2.get(0).toString());
//                                            }
//                                            closingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)) FROM ca_recon WHERE "
//                                                    + "  dt <= '" + todt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + "  GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
//                                            for (int k = 0; k < closingBalSet.size(); k++) {
//                                                vec3 = (Vector) closingBalSet.get(k);
//                                                closingBal = closingBal + Double.parseDouble(vec3.get(0).toString());
//                                            }
//                                        }
//                                        List<RevenueReportDrCrPojo> revenueCrDrTable = caRevenueBalance(glHead, classType, acType, fromDt, todt, brCode);
//                                        if (revenueCrDrTable.size() > 0) {
//                                            for (int l = 0; l < revenueCrDrTable.size(); l++) {
//                                                crTotal = crTotal + revenueCrDrTable.get(l).getCrAmt();
//                                                drTotal = drTotal + revenueCrDrTable.get(l).getDrAmt();
//                                            }
//                                        }
//                                    }
//                                    if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN)))) {
//                                        List resultSet = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)),0) , ifnull(SUM(ifnull(DRAMT,0)),0)  FROM loan_recon WHERE "
//                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND AUTH = 'Y' AND  DT between '" + fromDt + "' AND '" + todt + "' AND "
//                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + todt + "') ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(CRAMT,0)),0) - ifnull(SUM(ifnull(DRAMT,0)),0))  FROM loan_recon WHERE "
//                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND AUTH = 'Y' AND  DT < '" + fromDt + "' AND "
//                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT< '" + fromDt + "') ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(CRAMT,0)),0) - ifnull(SUM(ifnull(DRAMT,0)),0))  FROM loan_recon WHERE "
//                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "' AND AUTH = 'Y' AND  DT <= '" + todt + "'  AND "
//                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT< '" + todt + "') ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
//                                        List resultSet = em.createNativeQuery("SELECT ifnull(SUM(ifnull(t.CRAMT,0)),0),ifnull(SUM(ifnull(t.DRAMT,0)),0) FROM td_recon t, td_accountmaster a where "
//                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt between '" + fromDt + "' AND ,'" + todt + "' AND "
//                                                + " t.auth='Y' AND t.CLOSEFLAG IS NULL ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//
//                                        List openingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(t.CRAMT,0)),0)-ifnull(SUM(ifnull(t.DRAMT,0)),0)) FROM td_recon t, td_accountmaster a where "
//                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt < '" + fromDt + "' AND "
//                                                + " t.auth='Y' AND t.CLOSEFLAG IS NULL ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//
//                                        List closingBalSet = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(t.CRAMT,0)),0)-ifnull(SUM(ifnull(t.DRAMT,0)),0)) FROM td_recon t, td_accountmaster a where "
//                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt <= '" + todt + "' AND "
//                                                + " t.auth='Y' AND t.CLOSEFLAG IS NULL ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
//                                        List resultSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0), ifnull(sum(ifnull(dramt,0)),0) from rdrecon r,"
//                                                + "accountmaster a where substring(R.acno,3,2) ='" + acType + "' and r.acno = a.acno "
//                                                + " AND  R.DT between '" + fromDt + "' AND '" + todt + "' and r.auth = 'Y' ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//
//                                        List openingBalSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from rdrecon r,"
//                                                + "accountmaster a where substring(R.acno,3,2) ='" + acType + "' and r.acno = a.acno "
//                                                + " AND  R.DT < '" + fromDt + "' and r.auth = 'Y' ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from rdrecon r,"
//                                                + "accountmaster a where substring(R.acno,3,2) ='" + acType + "' and r.acno = a.acno "
//                                                + " AND  R.DT <= '" + todt + "' and r.auth = 'Y' ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if ((actnature.equalsIgnoreCase("OF"))) {
//                                        List resultSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0), ifnull(sum(ifnull(dramt,0)),0) from of_recon"
//                                                + " where substring(acno,3,2) ='" + acType + "' and DT between  '" + fromDt + "' and '" + todt + "' and auth = 'Y' ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("select (ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0)) from of_recon"
//                                                + " where substring(acno,3,2) ='" + acType + "' and DT <  '" + fromDt + "' and auth = 'Y' ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("select (ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0)) from of_recon"
//                                                + " where substring(acno,3,2) ='" + acType + "' and DT <=  '" + todt + "' and auth = 'Y' ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec2.get(0).toString());
//                                    }
//                                } else {
//                                    /*********************Branch Wise **************************/
//                                    if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                                        List resultSet = em.createNativeQuery("SELECT ifnull(sum(ifnull(r.cramt,0)),0),ifnull(sum(ifnull(r.dramt,0)),0) from recon r, accountmaster a "
//                                                + "where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt between '" + fromDt + "' "
//                                                + "AND '" + todt + "' AND r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("SELECT abs(ifnull(sum(ifnull(r.cramt,0)),0)-ifnull(sum(ifnull(r.dramt,0)),0)) from recon r, accountmaster a "
//                                                + " where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt < '" + fromDt + "' "
//                                                + " AND r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("SELECT abs(ifnull(sum(ifnull(r.cramt,0)),0)-ifnull(sum(ifnull(r.dramt,0)),0)) from recon r, accountmaster a "
//                                                + " where a.acno = r.acno and a.accttype ='" + acType + "' AND r.dt <= '" + todt + "' "
//                                                + " AND r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                                        List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
//                                                + " where a.acno = d.acno and a.accttype ='" + acType + "'  AND d.dt between '" + fromDt + "' "
//                                                + " AND '" + todt + "' and d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("select abs(ifnull(sum(d.cramt),0)-ifnull(sum(d.dramt),0)) from ddstransaction d, accountmaster a "
//                                                + " where a.acno = d.acno and a.accttype ='" + acType + "'  AND d.dt < '" + fromDt + "'  "
//                                                + " and d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("select abs(ifnull(sum(d.cramt),0)-ifnull(sum(d.dramt),0)) from ddstransaction d, accountmaster a "
//                                                + " where a.acno = d.acno and a.accttype ='" + acType + "'  AND d.dt <= '" + todt + "'  "
//                                                + " and d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                        if (classType.equalsIgnoreCase("O")) {
//                                            List resultSet, openingBalSet, closingBalSet;
//                                            Vector vec1, vec2, vec3;
//                                            openingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0)) FROM ca_recon WHERE "
//                                                    + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') "
//                                                    + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
//
//                                            for (int k = 0; k < openingBalSet.size(); k++) {
//                                                vec2 = (Vector) openingBalSet.get(k);
//                                                openingBal = openingBal + Double.parseDouble(vec2.get(0).toString());
//                                            }
//                                            closingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0)) FROM ca_recon WHERE "
//                                                    + " dt <= '" + todt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') "
//                                                    + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
//
//                                            for (int k = 0; k < closingBalSet.size(); k++) {
//                                                vec3 = (Vector) closingBalSet.get(k);
//                                                closingBal = closingBal + Double.parseDouble(vec3.get(0).toString());
//                                            }
//                                        } else {
//                                            List resultSet, openingBalSet, closingBalSet;
//                                            Vector vec1, vec2, vec3;
//                                            openingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0)) FROM ca_recon WHERE "
//                                                    + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') "
//                                                    + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
//
//                                            for (int k = 0; k < openingBalSet.size(); k++) {
//                                                vec2 = (Vector) openingBalSet.get(k);
//                                                openingBal = openingBal + Double.parseDouble(vec2.get(0).toString());
//                                            }
//                                            closingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0)) FROM ca_recon WHERE "
//                                                    + " dt <= '" + todt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
//                                                    + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') "
//                                                    + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
//
//                                            for (int k = 0; k < closingBalSet.size(); k++) {
//                                                vec3 = (Vector) closingBalSet.get(k);
//                                                closingBal = closingBal + Double.parseDouble(vec3.get(0).toString());
//                                            }
//                                        }
//                                        List<RevenueReportDrCrPojo> revenueCrDrTable = caRevenueBalance(glHead, classType, acType, fromDt, todt, brCode);
//                                        if (revenueCrDrTable.size() > 0) {
//                                            for (int l = 0; l < revenueCrDrTable.size(); l++) {
//                                                crTotal = crTotal + revenueCrDrTable.get(l).getCrAmt();
//                                                drTotal = drTotal + revenueCrDrTable.get(l).getDrAmt();
//                                            }
//                                        }
//                                    }
//                                    if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN)))) {
//                                        List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0) FROM loan_recon WHERE "
//                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "'  AND AUTH = 'Y' AND  DT between '" + fromDt + "' AND '" + todt + "' AND "
//                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + todt + "' "
//                                                + "AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        drTotal = Double.parseDouble(vec1.get(0).toString());
//                                        crTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(DRAMT),0) - ifnull(SUM(CRAMT),0)) FROM loan_recon WHERE "
//                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "'  AND AUTH = 'Y' AND  DT < '" + fromDt + "' AND "
//                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + todt + "' "
//                                                + " AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(DRAMT),0) - ifnull(SUM(CRAMT),0)) FROM loan_recon WHERE "
//                                                + " SUBSTRING(ACNO,3,2) = '" + acType + "'  AND AUTH = 'Y' AND  DT <= '" + todt + "' AND "
//                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + acType + "' AND A.OPENINGDT<= '" + todt + "' "
//                                                + " AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
//                                        List resultSet = em.createNativeQuery("SELECT ifnull(SUM(ifnull(t.CRAMT,0)),0), ifnull(SUM(ifnull(t.DRAMT,0)),0) FROM td_recon t, td_accountmaster a where "
//                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt between '" + fromDt + "' AND '" + todt + "' AND "
//                                                + " t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 AND t.CLOSEFLAG IS NULL ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(ifnull(t.CRAMT,0)),0)-ifnull(SUM(ifnull(t.DRAMT,0)),0)) FROM td_recon t, td_accountmaster a where "
//                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt < '" + fromDt + "' AND "
//                                                + " t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 AND t.CLOSEFLAG IS NULL ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("SELECT abs(ifnull(SUM(ifnull(t.CRAMT,0)),0)-ifnull(SUM(ifnull(t.DRAMT,0)),0)) FROM td_recon t, td_accountmaster a where "
//                                                + " a.acno = t.acno and a.accttype ='" + acType + "' AND t.dt <= '" + todt + "' AND "
//                                                + " t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 AND t.CLOSEFLAG IS NULL ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
//                                        List resultSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0), ifnull(sum(ifnull(dramt,0)),0) from rdrecon r,"
//                                                + " accountmaster a where substring(R.acno,3,2) ='" + acType + "' and r.acno = a.acno "
//                                                + " and a.curbrcode = '" + brCode + "' AND  R.DT between '" + fromDt + "' AND '" + todt + "' and r.auth = 'Y' ").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//
//                                        List openingBalSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from rdrecon r,"
//                                                + " accountmaster a where substring(R.acno,3,2) ='" + acType + "' and r.acno = a.acno "
//                                                + " and a.curbrcode = '" + brCode + "' AND  R.DT < '" + fromDt + "' and r.auth = 'Y' ").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from rdrecon r,"
//                                                + " accountmaster a where substring(R.acno,3,2) ='" + acType + "' and r.acno = a.acno "
//                                                + " and a.curbrcode = '" + brCode + "' AND  R.DT <= '" + todt + "' and r.auth = 'Y' ").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                    if ((actnature.equalsIgnoreCase("OF"))) {
//                                        List resultSet = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0), ifnull(sum(ifnull(dramt,0)),0) from of_recon"
//                                                + " where substring(acno,3,2) ='" + acType + "' and DT between  '" + fromDt + "' and '" + todt + "' and auth = 'Y' and substring(acno,1,2)='" + brCode + "'").getResultList();
//                                        Vector vec1 = (Vector) resultSet.get(0);
//                                        crTotal = Double.parseDouble(vec1.get(0).toString());
//                                        drTotal = Double.parseDouble(vec1.get(1).toString());
//                                        List openingBalSet = em.createNativeQuery("select abs(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0)) from of_recon"
//                                                + " where substring(acno,3,2) ='" + acType + "' and DT <  '" + fromDt + "' and auth = 'Y' and substring(acno,1,2)='" + brCode + "'").getResultList();
//                                        Vector vec2 = (Vector) openingBalSet.get(0);
//                                        openingBal = Double.parseDouble(vec2.get(0).toString());
//                                        List closingBalSet = em.createNativeQuery("select abs(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0)) from of_recon"
//                                                + " where substring(acno,3,2) ='" + acType + "' and DT <=  '" + todt + "' and auth = 'Y' and substring(acno,1,2)='" + brCode + "'").getResultList();
//                                        Vector vec3 = (Vector) closingBalSet.get(0);
//                                        closingBal = Double.parseDouble(vec3.get(0).toString());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    List totalAmtList;
//                    Vector totalAmtVect;
//                    double openingAmt = 0, closingAmt = 0;
//                    if (brCode.equalsIgnoreCase("0A")) {
//                        /***** Head Office****************/
//                        if (code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode()+SiplConstant.GL_CSH_HEAD.getValue())) {
//                            totalAmtList = em.createNativeQuery("SELECT abs(ifnull(SUM(ifnull(AMT,0)),0)) FROM cashinhand WHERE LDATE = "
//                                    + "(SELECT MAX(LDATE) FROM cashinhand WHERE  LDATE < '" + fromDt + "' )").getResultList();
//                            if (totalAmtList.size() == 0) {
//                                throw new ApplicationException("Please check the CASHINHAND table on date " + fromDt);
//                            } else {
//                                totalAmtVect = (Vector) totalAmtList.get(0);
//                                openingAmt = Double.parseDouble(totalAmtVect.get(0).toString());
//                            }
//                            drTotal = drTotal + cashInHandDr(fromDt, todt, brCode);
//                            crTotal = crTotal + cashInHandCr(fromDt, todt, brCode);
//                            openingBal = openingBal + openingAmt;
//                            closingBal = openingBal - drTotal + crTotal;
//
//                        } else if (code.equalsIgnoreCase("+P&L")) {
//                            double balPL = glReport.IncomeExp(todt, brCode, brCode);
//                            if (balPL >= 0) {
//                                crTotal = crTotal + balPL;
//                            }
//                            closingBal = closingBal + balPL;
//                        } else if (code.equalsIgnoreCase("-P&L")) {
//                            double balPL = glReport.IncomeExp(todt, brCode, brCode);
//                            if (balPL < 0) {
//                                drTotal = drTotal + balPL;
//                            }
//                            closingBal = closingBal + balPL;
//                        } else {
//                            code = code;
//                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)),0), ifnull(SUM(ifnull(DRAMT,0)),0) AS AMT FROM "
//                                    + "gl_recon WHERE SUBSTRING(ACNO,3,8) = '" + code + "' AND AUTH = 'Y' AND DT between '" + fromDt + "' AND '" + todt + "' ").getResultList();
//                            Vector vec1 = (Vector) resultSet.get(0);
//                            crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
//                            drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
//
//                            totalAmtList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0)) AS AMT FROM "
//                                    + "gl_recon WHERE SUBSTRING(ACNO,3,8) = '" + code + "' AND AUTH = 'Y' AND DT <'" + fromDt + "'").getResultList();
//                            totalAmtVect = (Vector) totalAmtList.get(0);
//                            openingAmt = Double.parseDouble(totalAmtVect.get(0).toString());
//                            openingBal = openingBal + openingAmt;
//
//                            totalAmtList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0)) AS AMT FROM "
//                                    + "gl_recon WHERE SUBSTRING(ACNO,3,8) = '" + code + "' AND AUTH = 'Y' AND DT <='" + todt + "'").getResultList();
//                            totalAmtVect = (Vector) totalAmtList.get(0);
//                            closingAmt = Double.parseDouble(totalAmtVect.get(0).toString());
//                            closingBal = closingBal + closingAmt;
//                        }
//                    } else {
//                        /***** Branch Wise****************/
//                        if (code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode()+SiplConstant.GL_CSH_HEAD.getValue())) {
//                            if (!brCode.equalsIgnoreCase("90")) {
//                                totalAmtList = em.createNativeQuery("SELECT ifnull(AMT,0) FROM CASHINHAND WHERE LDATE = (SELECT MAX(LDATE) FROM "
//                                        + "CASHINHAND WHERE LDATE<'" + fromDt + "')  AND BRNCODE = '" + brCode + "'").getResultList();
//                                if (totalAmtList.size() == 0) {
//                                    throw new ApplicationException("Please check the CASHINHAND table on date " + fromDt);
//                                } else {
//                                    totalAmtVect = (Vector) totalAmtList.get(0);
//                                    openingAmt = Double.parseDouble(totalAmtVect.get(0).toString());
//                                }
//                            }
//                            drTotal = drTotal + cashInHandDr(fromDt, todt, brCode);
//                            crTotal = crTotal + cashInHandCr(fromDt, todt, brCode);
//                            openingBal = openingBal + openingAmt;
//                        } else if (code.equalsIgnoreCase("+P&L")) {
//                            double openingPL = glReport.IncomeExp(CbsUtil.dateAdd(fromDt, -1), brCode, brCode);
//                            double balPL = glReport.IncomeExp(todt, brCode, brCode);
//                            if (balPL >= 0) {
//                                crTotal = crTotal + balPL;
//                            }
//                            closingBal = closingBal + balPL;
//                        } else if (code.equalsIgnoreCase("-P&L")) {
//                            double openingPL = glReport.IncomeExp(CbsUtil.dateAdd(fromDt, -1), brCode, brCode);
//                            double balPL = glReport.IncomeExp(todt, brCode, brCode);
//                            if (balPL < 0) {
//                                drTotal = drTotal + Double.parseDouble(new BigDecimal(balPL).abs().toString());
//                            }
//                            closingBal = closingBal + balPL;
//                        } else {
//                            code = brCode.concat(code).concat("01");
//                            if (code.length() == 12) {
//                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)),0), ifnull(SUM(ifnull(DRAMT,0)),0) AS AMT FROM gl_recon WHERE "
//                                        + "ACNO = '" + code + "' AND AUTH = 'Y' AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND DT between '" + fromDt + "' AND '" + todt + "'").getResultList();
//                                Vector vec1 = (Vector) resultSet.get(0);
//                                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
//                                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
//                                totalAmtList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0)) AS AMT FROM gl_recon WHERE "
//                                        + "ACNO = '" + code + "' AND AUTH = 'Y' AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND DT <'" + fromDt + "'").getResultList();
//                                totalAmtVect = (Vector) totalAmtList.get(0);
//                                openingAmt = Double.parseDouble(totalAmtVect.get(0).toString());
//                                openingBal = openingBal + openingAmt;
//                                totalAmtList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(CRAMT,0)) -SUM(ifnull(DRAMT,0)),0))  AS AMT FROM gl_recon WHERE "
//                                        + "ACNO = '" + code + "' AND AUTH = 'Y' AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND DT <='" + todt + "'").getResultList();
//                                totalAmtVect = (Vector) totalAmtList.get(0);
//                                closingAmt = Double.parseDouble(totalAmtVect.get(0).toString());
//                                closingBal = closingBal + closingAmt;
//                            }
//                        }
//                    }
//                    if (classType.equalsIgnoreCase("O")) {
//                        if (closingBal >= 0) {
//                            revenuePojo.setCrAmt(Double.parseDouble(new BigDecimal(crTotal).abs().toString()));
//                            revenuePojo.setDrAmt(Double.parseDouble(new BigDecimal(drTotal).abs().toString()));
//                            revenuePojo.setOpBal(Double.parseDouble(new BigDecimal(openingBal).abs().toString()));
//                            revenuePojo.setClosingBal(Double.parseDouble(new BigDecimal(closingBal).abs().toString()));
//                        } else if (closingBal < 0) {
//                            revenuePojo.setCrAmt(0d);
//                            revenuePojo.setDrAmt(0d);
//                            revenuePojo.setOpBal(0d);
//                            revenuePojo.setClosingBal(0d);
//                        }
//                    } else if (classType.equalsIgnoreCase("I")) {
//                        if (desc.equalsIgnoreCase("CASH IN HAND")) {
//                            closingBal = openingBal - drTotal + crTotal;
//                        }
//                        if (closingBal <= 0) {
//                            revenuePojo.setCrAmt(Double.parseDouble(new BigDecimal(crTotal).abs().toString()));
//                            revenuePojo.setDrAmt(Double.parseDouble(new BigDecimal(drTotal).abs().toString()));
//                            revenuePojo.setOpBal(Double.parseDouble(new BigDecimal(openingBal).abs().toString()));
//                            revenuePojo.setClosingBal(Double.parseDouble(new BigDecimal(closingBal).abs().toString()));
//                        } else if (closingBal > 0) {
//                            if (brCode.equalsIgnoreCase("0A")) {
//                                if (desc.equalsIgnoreCase("CASH IN HAND")) {
//                                    revenuePojo.setCrAmt(Double.parseDouble(new BigDecimal(crTotal).abs().toString()));
//                                    revenuePojo.setDrAmt(Double.parseDouble(new BigDecimal(drTotal).abs().toString()));
//                                    revenuePojo.setOpBal(Double.parseDouble(new BigDecimal(openingBal).abs().toString()));
//                                    revenuePojo.setClosingBal(Double.parseDouble(new BigDecimal(closingBal).abs().toString()));
//                                } else {
//                                    revenuePojo.setCrAmt(0d);
//                                    revenuePojo.setDrAmt(0d);
//                                    revenuePojo.setOpBal(0d);
//                                    revenuePojo.setClosingBal(0d);
//                                }
//                            } else {
//                                if (desc.equalsIgnoreCase("CASH IN HAND")) {
//                                    revenuePojo.setCrAmt(Double.parseDouble(new BigDecimal(crTotal).abs().toString()));
//                                    revenuePojo.setDrAmt(Double.parseDouble(new BigDecimal(drTotal).abs().toString()));
//                                    revenuePojo.setOpBal(Double.parseDouble(new BigDecimal(openingBal).abs().toString()));
//                                    revenuePojo.setClosingBal(Double.parseDouble(new BigDecimal(closingBal).abs().toString()));
//                                } else if (acType != null) {
//                                    if (!acType.equalsIgnoreCase("")) {
//                                        revenuePojo.setCrAmt(Double.parseDouble(new BigDecimal(crTotal).abs().toString()));
//                                        revenuePojo.setDrAmt(Double.parseDouble(new BigDecimal(drTotal).abs().toString()));
//                                        revenuePojo.setOpBal(Double.parseDouble(new BigDecimal(openingBal).abs().toString()));
//                                        revenuePojo.setClosingBal(Double.parseDouble(new BigDecimal(closingBal).abs().toString()));
//                                    } else if (openingBal < 0 && closingBal == 0.0) {
//                                        revenuePojo.setCrAmt(Double.parseDouble(new BigDecimal(crTotal).abs().toString()));
//                                        revenuePojo.setDrAmt(Double.parseDouble(new BigDecimal(drTotal).abs().toString()));
//                                        revenuePojo.setOpBal(Double.parseDouble(new BigDecimal(openingBal).abs().toString()));
//                                        revenuePojo.setClosingBal(Double.parseDouble(new BigDecimal(closingBal).abs().toString()));
//                                    } else {
//                                        revenuePojo.setCrAmt(0d);
//                                        revenuePojo.setDrAmt(0d);
//                                        revenuePojo.setOpBal(0d);
//                                        revenuePojo.setClosingBal(0d);
//                                    }
//                                } else {
//                                    revenuePojo.setCrAmt(0d);
//                                    revenuePojo.setDrAmt(0d);
//                                    revenuePojo.setOpBal(0d);
//                                    revenuePojo.setClosingBal(0d);
//                                }
//                            }
//                        }
//                    }
//                    revenuePojoTable.add(revenuePojo);
//                }
//            } else {
//                throw new ApplicationException("Data does not exist in CBS_REVENUE_MASTER");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException(e);
//        }
//        return revenuePojoTable;
//    }
    public double cashInHandDr(String fromDt, String toDt, String brnCode) throws ApplicationException {
        double amt = 0;
        List acTypeList = em.createNativeQuery("select ACCTNATURE,ACCTCODE from accounttypemaster where acctnature not in ('NP')").getResultList();
        if (acTypeList.isEmpty()) {
            throw new ApplicationException("Data does not exist in AccountTypeMaster");
        }
        String acCode, acctDesc, glHead;

        for (int k = 0; k < acTypeList.size(); k++) {
            Vector vec = (Vector) acTypeList.get(k);
            String acctNature = vec.get(0).toString();
            acCode = vec.get(1).toString();
            String tableName = CbsUtil.getReconTableName(acctNature);
            List AmtList, glAmtList;
            if (brnCode.equalsIgnoreCase("0A")) {
                if (tableName.equalsIgnoreCase("td_recon")) {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and "
                            + " trantype<> 27 AND CLOSEFLAG IS NULL and auth='Y' AND trantype=0  "
                            + " AND SUBSTRING(ACNO,3,2) = '" + acCode + "'").getResultList();
                } else {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where  "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and "
                            + " auth='Y'  AND trantype=0 "
                            + " AND SUBSTRING(ACNO,3,2) = '" + acCode + "'").getResultList();
                }
                glAmtList = em.createNativeQuery("select ifnull(sum(ifnull(Dramt,0)),0) from gl_recon where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and "
                        + " DT BETWEEN '" + fromDt + "' and  '" + toDt + "' and auth='Y'").getResultList();
            } else {
                if (tableName.equalsIgnoreCase("td_recon")) {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and "
                            + " trantype<> 27 AND CLOSEFLAG IS NULL and auth='Y' AND trantype=0  "
                            + " AND SUBSTRING(ACNO,3,2) = '" + acCode + "' and org_brnid = '" + brnCode + "'").getResultList();
                } else {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where  "
                            + " dt between '" + fromDt + "' and '" + toDt + "' and  auth='Y'  AND trantype=0 "
                            + " AND SUBSTRING(ACNO,3,2) = '" + acCode + "' and org_brnid = '" + brnCode + "'").getResultList();
                }
                glAmtList = em.createNativeQuery("select ifnull(sum(ifnull(Dramt,0)),0) from gl_recon where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and "
                        + " DT BETWEEN '" + fromDt + "' and  '" + toDt + "' and auth='Y' and org_brnid = '" + brnCode + "'").getResultList();
            }

            Vector amtVec = (Vector) AmtList.get(0);
            Vector glAmtVec = (Vector) glAmtList.get(0);
            amt = amt + Double.parseDouble(amtVec.get(0).toString()) + Double.parseDouble(glAmtVec.get(0).toString());
        }
        return amt;
    }

    public double cashInHandCr(String fromDt, String toDt, String brnCode) throws ApplicationException {
        double amt = 0;
        List acTypeList = em.createNativeQuery("select ACCTNATURE,ACCTCODE from accounttypemaster where acctnature not in ('NP')").getResultList();
        if (acTypeList.isEmpty()) {
            throw new ApplicationException("Data does not exist in AccountTypeMaster");
        }
        String acCode, acctDesc, glHead;

        for (int k = 0; k < acTypeList.size(); k++) {
            Vector vec = (Vector) acTypeList.get(k);
            String acctNature = vec.get(0).toString();
            acCode = vec.get(1).toString();
            String tableName = CbsUtil.getReconTableName(acctNature);
            List AmtList, glAmtList;
            if (brnCode.equalsIgnoreCase("0A")) {
                if (tableName.equalsIgnoreCase("td_recon")) {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0) from " + tableName + " where "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and  trantype<> 27 AND CLOSEFLAG IS NULL and auth='Y'"
                            + " AND trantype=0 AND SUBSTRING(ACNO,3,2) = '" + acCode + "'").getResultList();
                } else {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0) from " + tableName + " where  "
                            + " dt between '" + fromDt + "' and '" + toDt + "' and  auth='Y'  AND trantype=0 "
                            + " AND SUBSTRING(ACNO,3,2) = '" + acCode + "'").getResultList();
                }
                glAmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0) from gl_recon where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and "
                        + " DT BETWEEN '" + fromDt + "' and  '" + toDt + "' and auth='Y'").getResultList();
            } else {
                if (tableName.equalsIgnoreCase("td_recon")) {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0) from " + tableName + " where "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and trantype<> 27 AND CLOSEFLAG IS NULL and auth='Y' "
                            + " AND trantype=0 AND SUBSTRING(ACNO,3,2) = '" + acCode + "' and org_brnid = '" + brnCode + "'").getResultList();
                } else {
                    AmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0) from " + tableName + " where  "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and  auth='Y'  AND trantype=0 "
                            + " AND SUBSTRING(ACNO,3,2) = '" + acCode + "' and org_brnid = '" + brnCode + "'").getResultList();
                }
                glAmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0) from gl_recon where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and "
                        + " DT BETWEEN '" + fromDt + "' and  '" + toDt + "' and auth='Y' and org_brnid = '" + brnCode + "'").getResultList();
            }

            Vector amtVec = (Vector) AmtList.get(0);
            Vector glAmtVec = (Vector) glAmtList.get(0);
            amt = amt + Double.parseDouble(amtVec.get(0).toString()) + Double.parseDouble(glAmtVec.get(0).toString());
        }
        return amt;
    }

    public List caRevenueBalance(String glHead, String classType, String acType, String fromDt, String toDt, String brnCode) throws ApplicationException {
        List resultSetOp = null, resultSetCl = null;
        List<RevenueReportDrCrPojo> returnList = new ArrayList<RevenueReportDrCrPojo>();
        RevenueReportDrCrPojo pojo = new RevenueReportDrCrPojo();
        Vector vec1, vec2;
        double crTotal = 0, drTotal = 0,
                opBalP = 0, clBalP = 0,
                opBalN = 0, clBalN = 0,
                rBalP = 0, rBalN = 0;
        if (classType.equalsIgnoreCase("O")) {
            if (brnCode.equalsIgnoreCase("0A")) {
                resultSetOp = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster ) "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
                resultSetCl = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt <= '" + toDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster) "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
            } else {
                resultSetOp = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brnCode + "') "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
                resultSetCl = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt <= '" + toDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brnCode + "') "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
            }
            for (int j = 0; j < resultSetOp.size(); j++) {
                vec1 = (Vector) resultSetOp.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                opBalP = opBalP + Double.parseDouble(vec1.get(2).toString());
            }
            for (int j = 0; j < resultSetCl.size(); j++) {
                vec1 = (Vector) resultSetCl.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                clBalP = clBalP + Double.parseDouble(vec1.get(2).toString());
            }
        } else {
            if (brnCode.equalsIgnoreCase("0A")) {
                resultSetOp = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y'  AND acno in (select acno from accountmaster ) "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
                resultSetCl = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt <= '" + toDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y'  AND acno in (select acno from accountmaster) "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
            } else {
                resultSetOp = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brnCode + "') "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
                resultSetCl = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt <= '" + toDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brnCode + "') "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)<0 ORDER BY ACNO").getResultList();
            }
            for (int j = 0; j < resultSetOp.size(); j++) {
                vec1 = (Vector) resultSetOp.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                opBalN = opBalN + Double.parseDouble(vec1.get(2).toString());
            }
            for (int j = 0; j < resultSetCl.size(); j++) {
                vec1 = (Vector) resultSetCl.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                clBalN = clBalN + Double.parseDouble(vec1.get(2).toString());
            }
        }
        rBalP = clBalP - opBalP;
        rBalN = clBalN - opBalN;
        /**
         * *** For getting total for GLHead of Account Types****
         */
        List glHeadList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0),ifnull(sum(ifnull(dramt,0)),0) from gl_recon where  "
                + "dt between '" + fromDt + "' and '" + toDt + "' and substring(acno,3,8)='" + glHead + "' and auth='Y'").getResultList();
        Vector vec = (Vector) glHeadList.get(0);
        double glCrTotal = Double.parseDouble(vec.get(0).toString());
        double glDrTotal = Double.parseDouble(vec.get(1).toString());
        double glTotal = glCrTotal - glDrTotal;

        glHeadList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0),ifnull(sum(ifnull(dramt,0)),0) from gl_recon where "
                + " dt<'" + fromDt + "' and  substring(acno,3,8)='" + glHead + "' and auth='Y' ").getResultList();
        vec = (Vector) glHeadList.get(0);
        double glCrTotalPrev = Double.parseDouble(vec.get(0).toString());
        double glDrTotalPrev = Double.parseDouble(vec.get(1).toString());
        double glTotalPrev = glCrTotalPrev - glDrTotalPrev;

        if (glTotalPrev < 0) {
            opBalN = opBalN + glTotalPrev;
            clBalN = clBalN + glTotalPrev;
        } else {
            opBalP = opBalP + glTotalPrev;
            clBalP = clBalP + glTotalPrev;
        }

        if (glTotal < 0) {
            rBalN = rBalN + glTotal;
        } else {
            rBalP = rBalP + glTotal;
        }

        if (classType.equalsIgnoreCase("I")) {
            if (brnCode.equalsIgnoreCase("0A")) {
                resultSetOp = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt < " + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster ) "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
                resultSetCl = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt <= '" + toDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster) "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
            } else {
                resultSetOp = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt < '" + fromDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brnCode + "') "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
                resultSetCl = em.createNativeQuery("SELECT ifnull(SUM(ifnull(cramt,0)),0),ifnull(SUM(ifnull(dramt,0)),0), ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0) FROM ca_recon WHERE "
                        + " dt <= '" + toDt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' "
                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brnCode + "') "
                        + " GROUP BY ACNO HAVING ifnull(SUM(ifnull(cramt,0)),0)-ifnull(SUM(ifnull(dramt,0)),0)>0 ORDER BY ACNO").getResultList();
            }
            for (int j = 0; j < resultSetOp.size(); j++) {
                vec1 = (Vector) resultSetOp.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                opBalP = opBalP + Double.parseDouble(vec1.get(2).toString());
            }
            for (int j = 0; j < resultSetCl.size(); j++) {
                vec1 = (Vector) resultSetCl.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                clBalP = clBalP + Double.parseDouble(vec1.get(2).toString());
            }
            List rBalList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0),ifnull(sum(ifnull(dramt,0)),0) from ca_recon where  "
                    + "dt between '" + fromDt + "' and '" + toDt + "' and substring(acno,3,2)='" + acType + "' AND acno in (select acno from "
                    + "accountmaster where curbrcode= '" + brnCode + "')  and auth='Y' ").getResultList();
            Vector vecRBal = (Vector) rBalList.get(0);
            rBalN = -Double.parseDouble(new BigDecimal(opBalP).abs().toString()) + Double.parseDouble(vecRBal.get(1).toString());
            rBalP = -Double.parseDouble(new BigDecimal(clBalP).abs().toString()) + Double.parseDouble(vecRBal.get(0).toString());
        }



        if (classType.equalsIgnoreCase("O")) {
            pojo.setCrAmt(Double.parseDouble(new BigDecimal(clBalP).abs().toString()));
            pojo.setDrAmt(Double.parseDouble(new BigDecimal(opBalP).abs().toString()));
        } else {
            pojo.setCrAmt(Double.parseDouble(new BigDecimal(rBalP).abs().toString()));
            pojo.setDrAmt(Double.parseDouble(new BigDecimal(rBalN).abs().toString()));
        }
        returnList.add(pojo);
        return returnList;
    }

    public List<AlmFddetailPojo> getAlmFdDetail(String brCode, String nature, String bucketNo, String tillDt, String bucketBase, String acType, String frDt, String toDt, String dlCase) throws ApplicationException {
        List<AlmFddetailPojo> dataList = new ArrayList<AlmFddetailPojo>();
        List result = new ArrayList();
        List almBktMastList = new ArrayList();
        String almFdVchWiseDate = tillDt, npaAcDetails = "";
        try {
            List almFdDateList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'ALM-FD-VCH-DATE'").getResultList();
            if (!almFdDateList.isEmpty()) {
                Vector almFdDateVect = (Vector) almFdDateList.get(0);
                almFdVchWiseDate = almFdDateVect.get(0).toString();
            }
            if (nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", tillDt, "", brCode.equalsIgnoreCase("90") ? "0A" : brCode, "Y");
                /*
                 * Getting the NPA Account List
                 */
                if (resultList.size() > 0) {
                    for (int x = 0; x < resultList.size(); x++) {
                        if (x == 0) {
                            npaAcDetails = "'" + resultList.get(x).getAcno() + "'";
                        } else {
                            npaAcDetails = npaAcDetails + ", '" + resultList.get(x).getAcno() + "'";
                        }
                    }
                    npaAcDetails = " AND A.ACNO NOT IN (" + npaAcDetails + ") ";
                }
            }
            if (bucketBase.equalsIgnoreCase("N")) {
                String table = common.getTableName(nature);
                if (nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature = '" + nature + "'").getResultList();
                    for (int i = 0; i < actCodeList.size(); i++) {
                        Vector vtr = (Vector) actCodeList.get(i);
                        String actp = vtr.get(0).toString();
                        String dt1, dt2 = null, dt3;
                        List getLoanValueList = null;
                        Vector getLoanValueVect;
                        Integer bktStartDay = Integer.parseInt(frDt);
                        Integer bktEndDay = Integer.parseInt(toDt);
                        if (bktStartDay == 1) {
                            dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                            dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                            if (brCode.equalsIgnoreCase("0A")) {
                                getLoanValueList = em.createNativeQuery("SELECT acno,ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                        + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "'  "
                                        + "AND A.ACNO=L.ACNO AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "' " + npaAcDetails + ")  group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                            } else {
                                getLoanValueList = em.createNativeQuery("SELECT acno,ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                        + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "'  "
                                        + "AND A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "' " + npaAcDetails + ")"
                                        + " group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                            }
                        } else {
                            dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                            dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                            if (brCode.equalsIgnoreCase("0A")) {
                                getLoanValueList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                        + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<='" + tillDt + "' "
                                        + "AND A.ACNO=L.ACNO AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "' " + npaAcDetails + ") group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                            } else {
                                getLoanValueList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                        + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2)= '" + actp + "' AND A.OPENINGDT<='" + tillDt + "' "
                                        + "AND A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) "
                                        + "BETWEEN '" + dt1 + "' AND '" + dt2 + "' " + npaAcDetails + ") "
                                        + "group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                            }
                        }
                        if (!getLoanValueList.isEmpty()) {
                            for (int k = 0; k < getLoanValueList.size(); k++) {
                                Vector ele = (Vector) getLoanValueList.get(k);
                                AlmFddetailPojo pojo = new AlmFddetailPojo();
                                String acno = ele.get(0).toString();
                                double amount = Double.parseDouble(ele.get(1).toString());
                                List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acno + "'").getResultList();
                                Vector nvr = (Vector) nameList.get(0);
                                String custName = nvr.get(0).toString();
                                List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                        + "where ac.acno = '" + acno + "'and ac.acno = cl.acno").getResultList();
                                Vector ele1 = (Vector) matDtList.get(0);
                                String matDt = ele1.get(0).toString();
                                pojo.setAcNo(acno);
                                pojo.setAmount(amount);
                                pojo.setCustname(custName);
                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                pojo.setBktDesc(bucketNo);
                                dataList.add(pojo);
                            }
                        }
                    }
                } else if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List actCodeList;
                    if (acType.equalsIgnoreCase("ALL")) {
                        actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature = '" + nature + "'").getResultList();
                    } else {
                        actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where AcctCode = '" + acType + "'").getResultList();
                    }
                    for (int i = 0; i < actCodeList.size(); i++) {
                        Vector vtr = (Vector) actCodeList.get(i);
                        String actp = vtr.get(0).toString();
                        List acNoList = null;
                        if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                            acNoList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND "
                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                    + " A.ACNO=L.ACNO " + npaAcDetails + ") "
                                    + " group by acno having ifnull(sum(ifnull(dramt,0)) - sum(ifnull(cramt,0)),0) <>0 order by acno").getResultList();
                        } else {
                            acNoList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND "
                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "' AND "
                                    + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "'  " + npaAcDetails + ") "
                                    + " group by acno having ifnull(sum(ifnull(dramt,0)) - sum(ifnull(cramt,0)),0) <>0 order by acno").getResultList();
                        }
                        if (!acNoList.isEmpty()) {
                            for (int h = 0; h < acNoList.size(); h++) {
                                double loanBal = 0d, outStand = 0d;
                                Vector acNoVect = (Vector) acNoList.get(h);
                                String acNo = acNoVect.get(0).toString();
                                double bal = 0d;
                                loanBal = Double.parseDouble(acNoVect.get(1).toString());
//                                System.out.println("%%%%%acno:" + acNo + "; Bal:" + loanBal);
                                if (loanBal < 0) {
                                    /*
                                     * If Account have Credit (Cr) Balance
                                     */
                                    bal = loanBal;
                                    if (bal != 0) {
                                        String dt1, dt2 = null, dt3;
                                        List getLoanValueList = null;
                                        Vector getLoanValueVect;
                                        AlmFddetailPojo pojo = new AlmFddetailPojo();
                                        List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acNo + "'").getResultList();
                                        Vector nvr = (Vector) nameList.get(0);
                                        String custName = nvr.get(0).toString();
                                        List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                + "where ac.acno = '" + acNo + "'and ac.acno = cl.acno").getResultList();
                                        Vector ele1 = (Vector) matDtList.get(0);
                                        String matDt = ele1.get(0).toString();
//                                        System.out.println("NON EMI acno:" + acNo + "; amt:" + bal);
                                        pojo.setAcNo(acNo);
                                        pojo.setAmount(bal);
                                        pojo.setCustname(custName);
                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                        pojo.setBktDesc(bucketNo);
                                        dataList.add(pojo);
                                    }
                                } else {
                                    /*
                                     * If Account have Debit (Dr) Balance
                                     */
                                    List emiExistOfLoanAcNo = em.createNativeQuery("SELECT * FROM emidetails  WHERE acno = '" + acNo + "'").getResultList();
                                    if (emiExistOfLoanAcNo.isEmpty()) {
                                        /*
                                         * EMI NOT EXIST
                                         */
                                        String dt1, dt2 = null, dt3;
                                        List getLoanValueList = null;
                                        AlmFddetailPojo pojo = new AlmFddetailPojo();
                                        long bktStartDay1 = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(frDt));
                                        long bktEndDay1 = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(toDt));
                                        Integer bktStartDay = (int) (long) bktStartDay1;
                                        Integer bktEndDay = (int) (long) bktEndDay1;
                                        if (bktStartDay == 1) {
                                            dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                            dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                            getLoanValueList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                    + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                            if (getLoanValueList.size() > 0) {
                                                bal = loanBal;
                                                loanBal = 0d;
                                            } else {
                                                bal = 0d;
                                            }
                                        } else {
                                            dt3 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                            dt1 = dt3;
                                            dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                            getLoanValueList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                    + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                            if (getLoanValueList.size() > 0) {
                                                bal = loanBal;
                                                loanBal = 0d;
                                            } else {
                                                bal = 0d;
                                            }
                                        }
//                                        System.out.println("NON EMI acno:" + acNo + "; amt:" + bal + "; noOfDays:" + (bktEndDay - bktStartDay) + "; bktStartDay:" + bktStartDay + "; bktEndDay:" + bktEndDay + "; dt1:" + dt1 + "; dt2:" + dt2);
                                        List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acNo + "'").getResultList();
                                        Vector nvr = (Vector) nameList.get(0);
                                        String custName = nvr.get(0).toString();
                                        List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                + "where ac.acno = '" + acNo + "'and ac.acno = cl.acno").getResultList();
                                        Vector ele1 = (Vector) matDtList.get(0);
                                        String matDt = ele1.get(0).toString();
                                        if (bal != 0) {
                                            pojo.setAcNo(acNo);
                                            pojo.setAmount(bal);
                                            pojo.setCustname(custName);
                                            pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                            pojo.setBktDesc(bucketNo);
                                            dataList.add(pojo);
                                        }
                                    } else {
                                        /*
                                         * EMI EXIST
                                         */
                                        String dt1, dt2 = null, dt3;
                                        List getLoanValueList = null;
                                        Vector getLoanValueVect;
                                        AlmFddetailPojo pojo = new AlmFddetailPojo();
                                        long bktStartDay1 = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(frDt));
                                        long bktEndDay1 = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(toDt));
                                        Integer bktStartDay = (int) (long) bktStartDay1;
                                        Integer bktEndDay = (int) (long) bktEndDay1;
                                        double emiBal = 0d;

                                        if (bktStartDay == 1) {
                                            dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                            dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                            getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(installamt,0)) ,0) FROM emidetails "
                                                    + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + dt1 + "' and '" + dt2 + "' ").getResultList();
                                            getLoanValueVect = (Vector) getLoanValueList.get(0);
                                            emiBal = Double.parseDouble(getLoanValueVect.get(0).toString());
                                            if (loanBal > emiBal) {
                                                List emiExistAfterDt2 = em.createNativeQuery("SELECT * FROM emidetails "
                                                        + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') > '" + dt2 + "' ").getResultList();
                                                if (emiExistAfterDt2.isEmpty()) {
                                                    /*
                                                     * IF EMI NOT EXIST and Some Balance have pending
                                                     */
                                                    List acNoExpList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                            + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                            + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                                    if (acNoExpList.size() > 0) {
                                                        outStand = loanBal;
                                                        loanBal = 0d;
                                                    } else {
                                                        outStand = emiBal;
                                                        loanBal = loanBal - emiBal;
                                                    }
                                                } else {
                                                    outStand = emiBal;
                                                    loanBal = loanBal - emiBal;
                                                }
                                            } else {
                                                outStand = loanBal;
                                                loanBal = 0d;
                                            }
                                            bal = outStand;
                                        } else {
                                            dt3 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                            dt1 = dt3;
                                            dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                            double balRecon1 = 0d;
                                            List balRecon = em.createNativeQuery("SELECT ifnull(SUM(ifnull(installamt,0)) ,0) FROM emidetails "
                                                    + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + tillDt + "' and '" + dt1 + "'  ").getResultList();
                                            if (balRecon.size() > 0) {
                                                Vector balreconVect = (Vector) balRecon.get(0);
                                                balRecon1 = Double.parseDouble(balreconVect.get(0).toString());
                                            }
                                            if (balRecon1 > loanBal) {
                                                loanBal = 0;
                                            } else {
                                                List emiExistBetweenD1Dt2 = em.createNativeQuery("SELECT * FROM emidetails "
                                                        + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + dt1 + "' and '" + dt2 + "'").getResultList();
                                                if (emiExistBetweenD1Dt2.isEmpty()) {
                                                    loanBal = 0d;
                                                } else {
                                                    loanBal = loanBal - balRecon1;
                                                }
                                            }

                                            getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(installamt,0)) ,0) FROM emidetails "
                                                    + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + dt1 + "' and '" + dt2 + "' ").getResultList();
                                            getLoanValueVect = (Vector) getLoanValueList.get(0);
                                            emiBal = Double.parseDouble(getLoanValueVect.get(0).toString());
                                            if (loanBal > emiBal) {
                                                List emiExistAfterDt2 = em.createNativeQuery("SELECT * FROM emidetails "
                                                        + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') > '" + dt2 + "' ").getResultList();
                                                if (emiExistAfterDt2.isEmpty()) {
                                                    /*
                                                     * IF EMI NOT EXIST and Some Balance have pending
                                                     */
                                                    List acNoExpList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                            + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                            + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                                    if (acNoExpList.size() > 0) {
                                                        outStand = loanBal;
                                                        loanBal = 0d;
                                                    } else {
                                                        outStand = emiBal;
                                                        loanBal = loanBal - emiBal;
                                                    }
                                                } else {
                                                    outStand = emiBal;
                                                    loanBal = loanBal - emiBal;
                                                }
                                            } else {
                                                outStand = loanBal;
                                                loanBal = 0d;
                                            }
                                            bal = outStand;
                                        }
//                                        System.out.println("EMI acno:" + acNo + "; LoanBal:" + loanBal + "; amt:" + bal + "; noOfDays:" + (bktEndDay - bktStartDay) + "; bktStartDay:" + bktStartDay + "; bktEndDay:" + bktEndDay + "; dt1:" + dt1 + "; dt2:" + dt2);
                                        List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acNo + "'").getResultList();
                                        Vector nvr = (Vector) nameList.get(0);
                                        String custName = nvr.get(0).toString();
                                        List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                + "where ac.acno = '" + acNo + "'and ac.acno = cl.acno").getResultList();
                                        Vector ele1 = (Vector) matDtList.get(0);
                                        String matDt = ele1.get(0).toString();
                                        if (bal != 0) {
                                            pojo.setAcNo(acNo);
                                            pojo.setAmount(bal);
                                            pojo.setCustname(custName);
                                            pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                            pojo.setBktDesc(bucketNo);
                                            dataList.add(pojo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    List actCodeList;
                    if (acType.equalsIgnoreCase("ALL")) {
                        actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature = '" + nature + "'").getResultList();
                    } else {
                        actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where AcctCode = '" + acType + "'").getResultList();
                    }
                    for (int k = 0; k < actCodeList.size(); k++) {
                        Vector vtr = (Vector) actCodeList.get(k);
                        String actp = vtr.get(0).toString();
                        if (brCode.equalsIgnoreCase("0A")) {
                            if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM " + table + " t, accountmaster a where "
                                        + "a.acno = t.acno and a.accttype ='" + actp + "' "
                                        + "AND t.dt<= DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' "
                                        + "GROUP BY t.ACNO HAVING SUM(t.CRAMT)-SUM(t.DRAMT)>0  ORDER BY t.ACNO").getResultList();
                            } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a "
                                        + "where a.acno = t.acno and a.accttype  ='" + actp + "' "
                                        + "AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                        + "HAVING SUM(t.CRAMT)-SUM(t.DRAMT) <> 0 ORDER BY t.ACNO").getResultList();
                            }
                        } else {
                            if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM " + table + " t, accountmaster a where "
                                        + "a.acno = t.acno and a.accttype  ='" + actp + "' "
                                        + "AND t.dt<= DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' AND a.curbrcode='" + brCode + "'GROUP BY t.ACNO "
                                        + "HAVING SUM(t.CRAMT)-SUM(t.DRAMT)>0  ORDER BY t.ACNO").getResultList();
                            } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a "
                                        + "where a.acno = t.acno and a.accttype  ='" + actp + "' "
                                        + "AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 "
                                        + "AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO HAVING SUM(t.CRAMT)-SUM(t.DRAMT)<> 0  ORDER BY t.ACNO").getResultList();
                            }
                        }
                        if (!result.isEmpty()) {
                            for (int i = 0; i < result.size(); i++) {
                                Vector ele = (Vector) result.get(i);
                                AlmFddetailPojo pojo = new AlmFddetailPojo();
                                String acno = ele.get(0).toString();
                                double amount = Double.parseDouble(ele.get(1).toString());
                                long noOfDays = 0;
//                                System.out.println("AcNo:" + acno + "; Bal:" + amount);
                                List nameList = new ArrayList();
                                if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    nameList = em.createNativeQuery("select CUSTNAME from td_accountmaster where acno = '" + acno + "'").getResultList();
                                } else {
                                    nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acno + "'").getResultList();
                                }
                                Vector nvr = (Vector) nameList.get(0);
                                String custName = nvr.get(0).toString();
                                List matDtList = new ArrayList();
                                if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                    matDtList = em.createNativeQuery("SELECT  coalesce(DATE_FORMAT(MAX(Rdmatdate),'%Y%m%d'),'" + tillDt + "') FROM accountmaster WHERE ACNO = '" + acno + "'").getResultList();
                                } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    matDtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + tillDt + "') FROM td_vouchmst WHERE ACNO = '" + acno + "' /*and (cldt > '" + tillDt + "' or cldt is null)*/ and td_madedt <='" + tillDt + "' ").getResultList();
                                }
                                if (!matDtList.isEmpty()) {
                                    Vector ele1 = (Vector) matDtList.get(0);
                                    String matDt = ele1.get(0).toString();
                                    Date maddt1 = null, dt1 = null;
                                    maddt1 = ymd.parse(matDt);
                                    dt1 = ymd.parse(tillDt);
                                    if (maddt1.compareTo(dt1) > 0) {
                                        noOfDays = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(matDt));
                                    } else {
                                        noOfDays = 1;
                                    }
                                    long bktStartDay = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(frDt));
                                    long bktEndDay = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(toDt));
                                    if (noOfDays >= bktStartDay && noOfDays <= bktEndDay) {
                                        pojo.setAcNo(acno);
                                        pojo.setAmount(amount);
                                        pojo.setCustname(custName);
                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                        pojo.setBktDesc(bucketNo);
                                        dataList.add(pojo);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                String table = common.getTableName(nature);
                if (nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    if (dlCase.equalsIgnoreCase("M")) {
                        String actCodeQuery = "", brnQuery = "";
                        if (acType.equalsIgnoreCase("ALL")) {
                            actCodeQuery = "select AcctCode from accounttypemaster where acctNature = '" + nature + "'";
                        } else {
                            actCodeQuery = "select AcctCode from accounttypemaster where AcctCode = '" + acType + "'";
                        }
                        if (!brCode.equalsIgnoreCase("0A")) {
                            brnQuery = " AND A.curbrcode='" + brCode + "' ";
                        }
                        String query = "";
                        String minBktNo = "", minBktDesc = "", maxBktNo = "", maxBktDesc = "";
                        query = "(select aa.mainAcNo, aa.mainCustName, aa.bal, aa.matdate, aa.acctnature, aa.lienacno, aa.VoucherNo, aa.Remarks, \n"
                                + "td.acctnature, td.acno as lienAcNo, td.custname as lienCustName, td.VoucherNo as lienVoucherNo, \n"
                                + "date_format(td.MatDt,'%Y%m%d')  as lienMatDt, td.diffAmt, td.mainVchBal, td.lien  \n"
                                + "from  \n"
                                + "(select aa.acno as mainAcNo, am.custname as mainCustName, aa.bal, l.*, atm.acctnature from \n"
                                + "(SELECT acno,ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) as bal FROM loan_recon  \n"
                                + "WHERE SUBSTRING(ACNO,3,2) in (" + actCodeQuery + ")  \n"
                                + "AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND ACNO IN  \n"
                                + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE   \n"
                                + "SUBSTRING(A.ACNO,3,2) in (" + actCodeQuery + ") " + brnQuery + " \n"
                                + "AND A.OPENINGDT<= '" + tillDt + "'   " + npaAcDetails
                                + "AND A.ACNO=L.ACNO) group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno)aa  \n"
                                + "left join loansecurity l on aa.acno = l.acno  \n"
                                + "left join accounttypemaster atm on substring(l.lienacno,3,2) = atm.acctcode  \n"
                                + "left join accountmaster am on aa.acno = am.acno \n"
                                + "where l.Entrydate <='" + tillDt + "' and (ifnull(l.ExpiryDate,'') = '' OR l.ExpiryDate > '" + tillDt + "')  \n"
                                + "/*and aa.acno = '022100393301'*/) aa  \n"
                                + "left join  \n"
                                + " "
                                + "(select atm.acctnature, td.acno, td.custname, td.VoucherNo, td.MatDt, td.diffAmt, td.mainVchBal, td.lien from  \n"
                                + "((select fd.acno, fd.custname, fd.VoucherNo, fd.MatDt, fd.differenceAmt1 as diffAmt, fd.mainVchBal, fd.lien from \n"
                                + "((select vv.acno, vv.custname, vv.VoucherNo, vv.cnt, vv.totalVouchOpBal, vv.actualAcBalance, vv.fddt, vv.MatDt, vv.cldt, vv.IntToAcno, vv.txn, vv.rowNo,  \n"
                                + "vv.balStatus,  cast(if(vv.balStatus='A',0,  \n"
                                + "if(vv.cnt=1, if(vv.txn=0 and vv.actualAcBalance>0,vv.actualAcBalance,0), if(vv.cnt=vv.rowNo, if(vv.txn=0, 0,  if((vv.IntToAcno='' OR vv.IntToAcno=vv.acno),   \n"
                                + "if(vv.actualAcBalance<=vv.totalVouchOpBal, if((vv.actualAcBalance=vv.totalVouchOpBal), 0, if((vv.IntToAcno=vv.acno), 0, vv.actualAcBalance-vv.totalVouchOpBal)),  \n"
                                + "vv.actualAcBalance-vv.totalVouchOpBal),  \n"
                                + "if(vv.actualAcBalance<=vv.totalVouchOpBal, 0,vv.actualAcBalance-vv.totalVouchOpBal))),0)))  as decimal(25,2)) as differenceAmt1,    \n"
                                + "cast(if(vv.balStatus='A',vv.actualAcBalance, if(vv.cnt=1, if(vv.txn=0,0,vv.actualAcBalance), if(vv.txn=0, 0, if((vv.IntToAcno='' OR vv.IntToAcno=vv.acno),   \n"
                                + "if(vv.actualAcBalance<=vv.totalVouchOpBal, if(vv.actualAcBalance=vv.totalVouchOpBal, vv.vouchOpBal, if((vv.IntToAcno=vv.acno), vv.prinAmt, vv.vouchOpBal)),  \n"
                                + "vv.vouchOpBal),  if(vv.actualAcBalance<=vv.totalVouchOpBal,  \n"
                                + "if(vv.actualAcBalance=vv.totalVouchOpBal,vv.vouchOpBal,vv.prinAmt), vv.vouchOpBal))))) as decimal(25,2)) as mainVchBal , vv.lien   \n"
                                + "from  \n"
                                + "(select  xx.acno, xx.custname, xx.VoucherNo, xx.cnt, xx.vchBal, xx.TotalVchBal,  xx.prinAmt, xx.intAmt, xx.tdsAmt, xx.drInt, xx.vouchOpBal,  \n"
                                + "xx.totalVouchOpBal,   xx.actualAcBalance,   xx.fddt, xx.MatDt, xx.cldt, xx.IntToAcno, xx.txn, cast(((CASE CONCAT(xx.acno)          WHEN @cur_crew_type          \n"
                                + "THEN @curRow := @curRow + 1         ELSE @curRow := 0 END) + 1) as decimal) AS rowNo, @cur_crew_type := CONCAT(xx.acno) AS cur_crew_type, xx.balStatus, xx.lien   \n"
                                + "from    \n"
                                + "(select  aa.acno, aa.custname, aa.VoucherNo, zz.cnt, aa.bal as vchBal, cast(vch.vchBal as decimal(25,2)) as TotalVchBal,  aa.prinAmt,  \n"
                                + "aa.intAmt, aa.tdsAmt, aa.drInt, ((ifnull(aa.prinAmt,0)+ifnull(aa.intAmt,0))-ifnull(aa.tdsAmt,0)-ifnull(aa.drInt,0)) as vouchOpBal,  \n"
                                + "cast(zz.totalVouchOpBal as decimal(25,2))  as totalVouchOpBal,   cast(aa.actualAcBalance as decimal(25,2))  as actualAcBalance,    \n"
                                + "aa.fddt, aa.MatDt, aa.cldt, aa.IntToAcno, aa.txn, aa.balStatus, aa.lien from     \n"
                                + "(SELECT d.acno, aa.custname, d.VoucherNo, ifnull(tt.bal,0) as bal,  \n"
                                + "if(tt.txn<>0,ifnull(d.prinAmt,0),0) as prinAmt,ifnull(d.intAmt,0) as intAmt,ifnull(d.tdsAmt,0) as tdsAmt,ifnull(d.drInt,0) as drInt,    \n"
                                + "aa.actualAcBalance, d.fddt, d.MatDt, d.cldt, d.IntToAcno, ifnull(tt.txn,0) as txn, d.balStatus, d.lien  FROM     \n"
                                + "(select r.acno, ifnull(a.VoucherNo,0) as VoucherNo, a.cldt, a.MatDt, a.fddt, ifnull(a.prinAmt,0) as prinAmt,  ifnull(a.intAmt,0) as intAmt,   \n"
                                + "ifnull(a.tdsAmt,0) as tdsAmt, ifnull(a.drInt,0) as drInt,   a.IntToAcno, r.balStatus, a.lien from     \n"
                                + "(select r.acno, r.balStatus from   \n"
                                + "(select distinct acno,0 as remAmt, 'V' as balStatus, substring(acno,1,2) as curbrcode from td_vouchmst where   \n"
                                + "(cldt is null OR cldt >'" + tillDt + "')  and fddt<='" + tillDt + "'  group by  acno  \n"
                                + "union  \n"
                                + "SELECT distinct t.acno, cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) as remAmt, 'A' as balStatus, curbrcode  \n"
                                + "FROM td_recon t, td_accountmaster a  where a.acno = t.acno \n"
                                + "/*and a.acno in ('021900306801','032400726401','042400482801','042400487401')*/  \n"
                                + "AND t.dt<='" + tillDt + "' and t.auth='Y' AND t.CLOSEFLAG IS NULL  and t.ACNO not in (select distinct acno from td_vouchmst where   \n"
                                + "(cldt is null OR cldt >'" + tillDt + "')  and fddt<='" + tillDt + "'  group by  acno) GROUP BY t.ACNO  \n"
                                + "HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0   )r  \n"
                                + "where substring(r.acno,3,2) in (select AcctCode from accounttypemaster where Acctnature in ('FD','MS')) )r  \n"
                                + "left join   \n"
                                + "(select a.acno, a.VoucherNo, a.cldt, a.MatDt, a.fddt, ifnull(a.prinAmt,0) as prinAmt,  ifnull(b.intAmt,0) as intAmt,  ifnull(c.tdsAmt,0) as tdsAmt,  \n"
                                + "ifnull(d.drInt,0) as drInt,   a.IntToAcno, a.lien from   \n"
                                + "(select acno, VoucherNo, ifnull(PrinAmt,0) as prinAmt, cldt, MatDt, fddt, IntToAcno, ifnull(lien,'N') as lien from td_vouchmst  \n"
                                + "where (cldt is null OR cldt >'" + tillDt + "')  \n"
                                + "and fddt<='" + tillDt + "' group by  acno, VoucherNo)a  /*gettting Voucher wise Principle, Close Date, Mature Date*/  \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo, ifnull(sum(Interest),0) as intAmt from td_interesthistory where dt<='" + tillDt + "' group by acno, VoucherNo)b    \n"
                                + "on a.ACNO  = b.acno and a.VoucherNo = b.VoucherNo /*Getting the Voucher wise Interest*/  \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo,ifnull(sum(tds),0) as tdsAmt from tdshistory where dt<='" + tillDt + "' group by acno, VoucherNo)c   \n"
                                + "on a.ACNO  = c.acno and a.VoucherNo = c.VoucherNo  /*Getting the Voucher wise TDS*/  \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo,ifnull(sum(dramt),0) as drInt from td_recon where dt<='" + tillDt + "'  and ty= 1 and trantype = 2 and trandesc = 4  \n"
                                + "and details like '%Int.on Vch : %' and intFlag is null and auth = 'Y' group by acno,VoucherNo)d   \n"
                                + "on a.ACNO  = d.acno and a.VoucherNo = d.VoucherNo  /* Getting the Voucher wise Debit Interest transfered in another account*/  \n"
                                + "group by a.acno, a.voucherno order by a.acno, a.voucherno, a.MatDt)a  \n"
                                + "on a.acno = r.acno)d /* Getting the Voucher wise Prin+int-tds-intTransfered*/   \n"
                                + "left join   \n"
                                + "(SELECT t.ACNO,t.VoucherNo, cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) as bal, count(*) as txn FROM td_recon t, td_accountmaster a   \n"
                                + "where a.acno = t.acno  AND t.dt<='" + tillDt + "' and t.auth='Y'  \n"
                                + "AND t.CLOSEFLAG IS NULL \n"
                                + "/*and t.acno in ('021900306801','032400726401','042400482801','042400487401')*/ \n"
                                + " GROUP BY t.ACNO, t.VoucherNo)tt  /*Vouchetr wise balance*/ \n"
                                + " on d.acno = tt.ACNO and d.VoucherNo = tt.VoucherNo    \n"
                                + "left join   \n"
                                + "(SELECT t.ACNO,t.VoucherNo, cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) as actualAcBalance, ifnull(a.custname,'') as custname  \n"
                                + "FROM td_recon t, td_accountmaster a  where a.acno = t.acno AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y%m%d') and t.auth='Y' AND  \n"
                                + "t.CLOSEFLAG IS NULL  GROUP BY t.ACNO)aa on aa.ACNO = d.ACNO /*Account Wise Balance*/   \n"
                                + "group by d.acno, d.voucherno order by d.acno, d.voucherno, d.MatDt)aa    \n"
                                + "left join   \n"
                                + "(select z.acno, sum(z.vouchOpBal) as totalVouchOpBal, count(*) as cnt from  /*total Account wise Prin+int-tds-intTranfer*/   \n"
                                + "(select a.acno, a.VoucherNo, a.prinAmt, a.cldt, a.MatDt,  \n"
                                + "((ifnull(if(ifnull(e.txnCnt,0)<>0,a.prinAmt,0),0)+ifnull(b.intAmt,0))-ifnull(c.tdsAmt,0)-ifnull(d.drInt,0)) as vouchOpBal,  \n"
                                + "ifnull(e.txnCnt,0) as txnCnt from   \n"
                                + "(select acno, VoucherNo, ifnull(PrinAmt,0) as prinAmt, cldt, MatDt, fddt from td_vouchmst where (cldt is null OR cldt >'" + tillDt + "')   \n"
                                + "and fddt<='" + tillDt + "'  group by  acno, VoucherNo)a /*gettting Voucher wise Principle, Close Date, Mature Date*/   \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo, ifnull(sum(Interest),0) as intAmt from td_interesthistory where dt<='" + tillDt + "' group by acno, VoucherNo)b    \n"
                                + "on a.ACNO  = b.acno and a.VoucherNo = b.VoucherNo /*Getting the Voucher wise Interest*/   \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo,ifnull(sum(tds),0) as tdsAmt from tdshistory where dt<='" + tillDt + "' group by acno, VoucherNo)c  on a.ACNO  = c.acno  \n"
                                + "and a.VoucherNo = c.VoucherNo  /*Getting the Voucher wise TDS*/   \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo,ifnull(sum(dramt),0) as drInt, count(*) as txnCnt from td_recon where dt<='" + tillDt + "'  and ty= 1 and trantype = 2 and trandesc = 4  \n"
                                + "and details like '%Int.on Vch : %' and intFlag is null and auth = 'Y' group by acno,VoucherNo)d   \n"
                                + "on a.ACNO  = d.acno and a.VoucherNo = d.VoucherNo  /* Getting the Voucher wise Debit Interest transfered in another account*/   \n"
                                + "left join   \n"
                                + "(select acno,VoucherNo, count(*) as txnCnt from td_recon where dt<='" + tillDt + "' and   intFlag is null and auth = 'Y' group by acno,VoucherNo)e   \n"
                                + "on a.ACNO  = e.acno and a.VoucherNo = e.VoucherNo  /* Getting the Voucher wise Debit Interest transfered in another account*/   \n"
                                + "group by a.acno, a.voucherno order by a.acno, a.voucherno, a.MatDt)z group by z.acno)zz  \n"
                                + "on zz.acno = aa.ACNO /*getting total no of active Voucher and Account wise Prin+int-tds-intTranfer*/    \n"
                                + "left join   \n"
                                + "(select aa.ACNO, sum(aa.vchBal) as vchBal from  (select acno, VoucherNo, ifnull(PrinAmt,0) as prinAmt, cldt, MatDt, fddt from td_vouchmst  \n"
                                + "where (cldt is null OR cldt >'" + tillDt + "')  and fddt<='" + tillDt + "' group by  acno, VoucherNo)bb   \n"
                                + "left join   \n"
                                + "(SELECT t.ACNO,t.VoucherNo, (IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0)) as vchBal FROM td_recon t, td_accountmaster a  where a.acno = t.acno  \n"
                                + "/*and a.acno = '021900164101' */ "
                                + "AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y%m%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL   \n"
                                + "GROUP BY t.ACNO,t.VoucherNo)aa on bb.acno = aa.ACNO and aa.VoucherNo= bb.VoucherNo   \n"
                                + "where (bb.cldt is null OR bb.cldt >'" + tillDt + "')  and bb.fddt<='" + tillDt + "'  and aa.VoucherNo is not null and aa.VoucherNo<>0 group by aa.ACNO)vch  \n"
                                + "on aa.ACNO = vch.ACNO /*Getting Total Active Voucher Balance*/  \n"
                                + "where   (aa.cldt is null OR aa.cldt >'" + tillDt + "')   group by aa.acno, aa.voucherno  order by aa.acno, aa.voucherno, aa.MatDt) xx,  \n"
                                + "(SELECT @curRow := 0, @cur_crew_type := '') counter )vv  order by vv.acno, vv.VoucherNo  ))fd)  \n"
                                + "union all  \n"
                                + "(SELECT t.acno,custname, 0 as VoucherNo,coalesce(DATE_FORMAT(MAX(Rdmatdate),'%Y%m%d'),'" + tillDt + "') as MatDt, 0 as diffAmt, \n"
                                + "IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as mainVchBal, 'Y' as lien FROM rdrecon t, accountmaster a \n"
                                + "where a.acno = t.acno AND DATE_FORMAT(t.dt,'%Y%m%d')<= '" + tillDt + "' and t.auth='Y'  \n"
                                + "GROUP BY t.ACNO HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0  ORDER BY t.ACNO ))td, accounttypemaster atm  \n"
                                + "where substring(td.acno,3,2) = atm.acctcode )td  \n"
                                + "on aa.lienacno = td.acno and  \n"
                                + "if(((aa.acctnature = 'FD' OR aa.acctnature = 'MS') and aa.VoucherNo=0), \n"
                                + "if(INSTR(aa.Remarks, 'VchNo')>0, SUBSTRING_INDEX(SUBSTRING_INDEX(aa.Remarks,'VchNo:',-1),';',1),aa.VoucherNo), aa.VoucherNo)= td.VoucherNo) \n"
                                + "order by aa.mainAcNo, aa.matdate, aa.lienacno, td.acno, td.VoucherNo ";

//                        System.out.println("query:" + query);
                        result = em.createNativeQuery(query).getResultList();
                        Map<String, Double> map = new HashMap<String, Double>();
                        /*Made the map which have the bal as per account wise*/
                        if (!result.isEmpty()) {
                            for (int i = 0; i < result.size(); i++) {
                                Vector ele = (Vector) result.get(i);
                                //Current Bal in of account 
                                if (!map.containsKey(ele.get(0))) { //Not Present                                    
                                    map.put(ele.get(0).toString(), Double.parseDouble(ele.get(2).toString()));
                                }
                            }
                        }
                        List fstSecDayList = new ArrayList();
                        fstSecDayList = almQtrFacadeRemote.getAlmBucketMaster(bucketBase, tillDt, bucketNo);
                        if (!fstSecDayList.isEmpty()) {
                            for (int j = 0; j < fstSecDayList.size(); j++) {
                                Vector ele2 = (Vector) fstSecDayList.get(j);
                                Integer bktNo = Integer.parseInt(ele2.get(2).toString());
                                String bktDesc = ele2.get(3).toString();
                                long bktStartDay = Integer.parseInt(ele2.get(4).toString());
                                long bktEndDay = Integer.parseInt(ele2.get(5).toString());

                                minBktNo = ele2.get(12).toString();
                                minBktDesc = ele2.get(13).toString();
                                maxBktNo = ele2.get(14).toString();
                                maxBktDesc = ele2.get(15).toString();
                                if (!result.isEmpty()) {
                                    for (int i = 0; i < result.size(); i++) {
                                        Vector ele = (Vector) result.get(i);
                                        AlmFddetailPojo pojo = new AlmFddetailPojo();
                                        String acno = null, custName = null, vchNo = null, matDt = null;
                                        double amount = 0, diffAmount = 0;

                                        acno = ele.get(0) == null ? "" : ele.get(0).toString();
                                        custName = ele.get(1) == null ? "" : ele.get(1).toString();
                                        String lienAcNo = ele.get(9) == null ? "" : ele.get(9).toString();
                                        vchNo = ele.get(11) == null ? "0" : ele.get(11).toString();
                                        matDt = ele.get(12) == null ? tillDt : ele.get(12).toString();
                                        amount = ele.get(14) == null ? 0 : Double.parseDouble(ele.get(14).toString());
                                        diffAmount = ele.get(13) == null ? 0 : Double.parseDouble(ele.get(13).toString());

                                        long noOfDays = 0;
                                        Date maddt1 = null, dt1 = null;
                                        maddt1 = ymd.parse(matDt);
                                        dt1 = ymd.parse(tillDt);
                                        if (maddt1.compareTo(dt1) > 0) {
                                            noOfDays = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(matDt));
                                        } else {
                                            noOfDays = 1;
                                        }

                                        if (noOfDays >= bktStartDay && noOfDays <= bktEndDay) {
                                            if (map.containsKey(acno) && ele.get(14) != null) {
                                                double loanAcBal = map.get(acno).doubleValue();
                                                if (loanAcBal >= amount) {
                                                    amount = amount;
                                                    map.put(acno, (loanAcBal - amount));
                                                } else {
                                                    amount = loanAcBal;
                                                    map.remove(acno);
                                                }
//                                                System.out.println("AcNo:" + acno + "; Bal:" + amount + "; Bucket:" + bktDesc + "; vchNo:" + vchNo);
                                                pojo.setAcNo(acno);
                                                pojo.setVchNo(lienAcNo.concat(": ").concat(vchNo));
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(dmy.format(ymd.parse(matDt)));
                                                pojo.setBktDesc(bktDesc);
                                                pojo.setBktNo(bktNo.toString());
                                                dataList.add(pojo);
                                                if (diffAmount != 0) {
                                                    pojo = new AlmFddetailPojo();
//                                                    System.out.println(">>>AcNo:" + acno + "; Bal:" + diffAmount + "; Bucket:" + bktDesc + "; vchNo:" + vchNo);
                                                    pojo.setAcNo(acno);
                                                    pojo.setVchNo("0");
                                                    pojo.setAmount(diffAmount);
                                                    pojo.setCustname(custName);
                                                    pojo.setMatDate(dmy.format(ymd.parse(tillDt)));
                                                    pojo.setBktDesc(maxBktDesc);
                                                    pojo.setBktNo(maxBktNo);
                                                    dataList.add(pojo);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!map.isEmpty()) {
                            for (String key : map.keySet()) {
//                                System.out.println(key);
                                AlmFddetailPojo pojo = new AlmFddetailPojo();
                                pojo.setAcNo(key);
                                pojo.setVchNo("0");
                                pojo.setAmount(map.get(key).doubleValue());
                                pojo.setCustname("");
                                pojo.setMatDate(dmy.format(ymd.parse(tillDt)));
                                pojo.setBktDesc(maxBktDesc);
                                pojo.setBktNo(maxBktNo);
                                dataList.add(pojo);
                            }
                        }
                    } else {
                        List actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature = '" + nature + "'").getResultList();
                        for (int i = 0; i < actCodeList.size(); i++) {
                            Vector vtr = (Vector) actCodeList.get(i);
                            String actp = vtr.get(0).toString();
                            if (bucketNo.equalsIgnoreCase("ALL")) {
                                almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                        + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                            } else {
                                almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND BUCKET_NO = '" + bucketNo + "' ").getResultList();
                            }

                            String dt1, dt2 = null, dt3;
                            List getLoanValueList = null;
                            Vector getLoanValueVect;
                            if (!almBktMastList.isEmpty()) {
                                for (int j = 0; j < almBktMastList.size(); j++) {
                                    Vector almBktMastVect = (Vector) almBktMastList.get(j);
                                    Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                    String recordStatus = almBktMastVect.get(1).toString();
                                    Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                    String bktDesc = almBktMastVect.get(3).toString();
                                    Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                    Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                    Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());

                                    if (bktStartDay == 1) {
                                        dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                        dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));

                                        if (brCode.equalsIgnoreCase("0A")) {
                                            getLoanValueList = em.createNativeQuery("SELECT acno,ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                                    + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "'  "
                                                    + "AND A.ACNO=L.ACNO AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "' " + npaAcDetails + ")  group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                                        } else {
                                            getLoanValueList = em.createNativeQuery("SELECT acno,ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                                    + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "'  "
                                                    + "AND A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "' " + npaAcDetails + ") "
                                                    + " group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                                        }
                                    } else {

                                        if (bucketNo.equalsIgnoreCase("ALL")) {
                                            dt3 = CbsUtil.dateAdd(dt2, 1);
                                            dt1 = dt3;
                                            dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                        } else {
                                            dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                            dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                        }

                                        if (brCode.equalsIgnoreCase("0A")) {
                                            getLoanValueList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                                    + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<='" + tillDt + "' "
                                                    + "AND A.ACNO=L.ACNO AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "' " + npaAcDetails + ") group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                                        } else {
                                            getLoanValueList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + tillDt + "' AND AUTH = 'Y' AND ACNO IN "
                                                    + "(SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L WHERE  SUBSTRING(A.ACNO,3,2)= '" + actp + "' AND A.OPENINGDT<='" + tillDt + "' "
                                                    + "AND A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month) "
                                                    + "BETWEEN '" + dt1 + "' AND '" + dt2 + "' " + npaAcDetails + ") "
                                                    + "group by acno HAVING SUM(CRAMT)-SUM(DRAMT)<>0  ORDER BY acno").getResultList();
                                        }
                                    }

                                    if (!getLoanValueList.isEmpty()) {
                                        for (int k = 0; k < getLoanValueList.size(); k++) {
                                            Vector ele = (Vector) getLoanValueList.get(k);
                                            AlmFddetailPojo pojo = new AlmFddetailPojo();
                                            String acno = ele.get(0).toString();
                                            double amount = Double.parseDouble(ele.get(1).toString());

                                            List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acno + "'").getResultList();
                                            Vector nvr = (Vector) nameList.get(0);
                                            String custName = nvr.get(0).toString();

                                            List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                    + "where ac.acno = '" + acno + "'and ac.acno = cl.acno").getResultList();
                                            Vector ele1 = (Vector) matDtList.get(0);
                                            String matDt = ele1.get(0).toString();

                                            if (bktNoMast == 1) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 2) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 3) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 4) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 5) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 6) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 7) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            } else if (bktNoMast == 8) {
                                                pojo.setBktNo(bktNoMast.toString());
                                                pojo.setAcNo(acno);
                                                pojo.setAmount(amount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                pojo.setBktDesc(bktDesc);
                                            }
                                            dataList.add(pojo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List actCodeList;
                    if (acType.equalsIgnoreCase("ALL")) {
                        actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature = '" + nature + "'").getResultList();
                    } else {
                        actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where AcctCode = '" + acType + "'").getResultList();
                    }

                    for (int i = 0; i < actCodeList.size(); i++) {
                        Vector vtr = (Vector) actCodeList.get(i);
                        String actp = vtr.get(0).toString();
                        List acNoList = null;
                        if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                            acNoList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND "
                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                    + " A.ACNO=L.ACNO "+ npaAcDetails +") "
                                    + " group by acno having ifnull(sum(ifnull(dramt,0)) - sum(ifnull(cramt,0)),0) <>0 order by acno").getResultList();
                        } else {
                            acNoList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + tillDt + "' AND AUTH = 'Y' AND "
                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + tillDt + "' AND "
                                    + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' "+ npaAcDetails +") "
                                    + "  group by acno having ifnull(sum(ifnull(dramt,0)) - sum(ifnull(cramt,0)),0) <>0 order by acno").getResultList();
                        }
                        if (!acNoList.isEmpty()) {
                            for (int h = 0; h < acNoList.size(); h++) {
                                double loanBal = 0d, outStand = 0d;
                                Vector acNoVect = (Vector) acNoList.get(h);

                                String acNo = acNoVect.get(0).toString();
                                double bal = 0d;
                                loanBal = Double.parseDouble(acNoVect.get(1).toString());
//                            System.out.println("%%%%%acno:" + acNo + "; Bal:" + loanBal);
                                if (loanBal < 0) {
                                    /*
                                     * If Account have Credit (Cr) Balance
                                     */
                                    bal = loanBal;
                                    if (bal != 0) {
                                        List almBktMastList1 = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                                + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' and BUCKET_NO = '1' ORDER BY BUCKET_NO").getResultList();
                                        String dt1, dt2 = null, dt3;
                                        List getLoanValueList = null;
                                        Vector getLoanValueVect;
                                        if (!almBktMastList1.isEmpty()) {
                                            for (int j = 0; j < almBktMastList1.size(); j++) {
                                                AlmFddetailPojo pojo = new AlmFddetailPojo();
                                                Vector almBktMastVect = (Vector) almBktMastList1.get(j);
                                                Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                                String bktDesc = almBktMastVect.get(3).toString();

                                                List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acNo + "'").getResultList();
                                                Vector nvr = (Vector) nameList.get(0);
                                                String custName = nvr.get(0).toString();

                                                List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                        + "where ac.acno = '" + acNo + "'and ac.acno = cl.acno").getResultList();
                                                Vector ele1 = (Vector) matDtList.get(0);
                                                String matDt = ele1.get(0).toString();

//                                            System.out.println("NON EMI acno:" + acNo + "; amt:" + bal);
                                                if (bktNoMast == 1) {
                                                    pojo.setBktNo(bktNoMast.toString());
                                                    pojo.setAcNo(acNo);
                                                    pojo.setAmount(bal);
                                                    pojo.setCustname(custName);
                                                    pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                    pojo.setBktDesc(bktDesc);
                                                }
                                                dataList.add(pojo);
                                            }
                                        } else {
                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                        }
                                    }
                                } else {
                                    /*
                                     * If Account have Debit (Dr) Balance
                                     */
                                    List emiExistOfLoanAcNo = em.createNativeQuery("SELECT * FROM emidetails  WHERE acno = '" + acNo + "'").getResultList();
                                    if (emiExistOfLoanAcNo.isEmpty()) {
                                        /*
                                         * EMI NOT EXIST
                                         */
                                        List almBktMastList1;
                                        if (bucketNo.equalsIgnoreCase("ALL")) {
                                            almBktMastList1 = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                                    + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                                        } else {
                                            almBktMastList1 = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                                    + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' AND BUCKET_NO = '" + bucketNo + "' ORDER BY BUCKET_NO").getResultList();
                                        }

                                        String dt1, dt2 = null, dt3;
                                        List getLoanValueList = null;
                                        Vector getLoanValueVect;
                                        if (!almBktMastList1.isEmpty()) {
                                            for (int j = 0; j < almBktMastList1.size(); j++) {
                                                AlmFddetailPojo pojo = new AlmFddetailPojo();
                                                Vector almBktMastVect = (Vector) almBktMastList1.get(j);
                                                Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                                String recordStatus = almBktMastVect.get(1).toString();
                                                Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                                String bktDesc = almBktMastVect.get(3).toString();
                                                Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                                Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                                Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());

                                                if (bktStartDay == 1) {
                                                    dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                                    dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                                    getLoanValueList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                            + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                            + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();

                                                    if (getLoanValueList.size() > 0) {
                                                        //bal = loanBal / repOpt;
                                                        bal = loanBal;
                                                        loanBal = 0d;
                                                    } else {
                                                        bal = 0d;
                                                    }
                                                } else {
                                                    if (almBktMastList1.size() == 1) {
//                                                    dt2= CbsUtil.dateAdd(tillDt, (bktStartDay-1));
                                                        dt3 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                                        dt1 = dt3;
                                                        dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                    } else {
                                                        dt3 = CbsUtil.dateAdd(dt2, 1);
                                                        dt1 = dt3;
                                                        dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                    }
                                                    getLoanValueList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                            + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                            + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                                    if (getLoanValueList.size() > 0) {
                                                        // bal = loanBal / repOpt;
                                                        bal = loanBal;
                                                        loanBal = 0d;
                                                    } else {
                                                        bal = 0d;
                                                    }
                                                }
//                                            System.out.println("NON EMI acno:" + acNo + "; amt:" + bal + "; noOfDays:" + (bktEndDay - bktStartDay) + "; bktStartDay:" + bktStartDay + "; bktEndDay:" + bktEndDay + "; dt1:" + dt1 + "; dt2:" + dt2);
                                                List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acNo + "'").getResultList();
                                                Vector nvr = (Vector) nameList.get(0);
                                                String custName = nvr.get(0).toString();

                                                List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                        + "where ac.acno = '" + acNo + "'and ac.acno = cl.acno").getResultList();
                                                Vector ele1 = (Vector) matDtList.get(0);
                                                String matDt = ele1.get(0).toString();
                                                if (bal != 0) {
                                                    if (bktNoMast == 1) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 2) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 3) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 4) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 5) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 6) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 7) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 8) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    }
                                                    dataList.add(pojo);
                                                }
                                            }
                                        } else {
                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                        }
                                    } else {
                                        /*
                                         * EMI EXIST
                                         */
                                        List almBktMastList2;
                                        if (bucketNo.equalsIgnoreCase("ALL")) {
                                            almBktMastList2 = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                                    + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                                        } else {
                                            almBktMastList2 = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                                    + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' AND BUCKET_NO = '" + bucketNo + "' ORDER BY BUCKET_NO").getResultList();
                                        }

                                        String dt1, dt2 = null, dt3;
                                        List getLoanValueList = null;
                                        Vector getLoanValueVect;
                                        if (!almBktMastList2.isEmpty()) {
                                            for (int j = 0; j < almBktMastList2.size(); j++) {
                                                AlmFddetailPojo pojo = new AlmFddetailPojo();
                                                Vector almBktMastVect = (Vector) almBktMastList2.get(j);
                                                Integer verNo = Integer.parseInt(almBktMastVect.get(0).toString());
                                                String recordStatus = almBktMastVect.get(1).toString();
                                                Integer bktNoMast = Integer.parseInt(almBktMastVect.get(2).toString());
                                                String bktDesc = almBktMastVect.get(3).toString();
                                                Integer bktStartDay = Integer.parseInt(almBktMastVect.get(4).toString());
                                                Integer bktEndDay = Integer.parseInt(almBktMastVect.get(5).toString());
                                                Integer recmodCnt = Integer.parseInt(almBktMastVect.get(6).toString());
                                                double emiBal = 0d;
                                                if (bktStartDay == 1) {
                                                    dt1 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                                    dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));

                                                    getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(installamt,0)) ,0) FROM emidetails "
                                                            + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + dt1 + "' and '" + dt2 + "' ").getResultList();

                                                    getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                    emiBal = Double.parseDouble(getLoanValueVect.get(0).toString());
                                                    if (loanBal > emiBal) {
                                                        List emiExistAfterDt2 = em.createNativeQuery("SELECT * FROM emidetails "
                                                                + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') > '" + dt2 + "' ").getResultList();
                                                        if (emiExistAfterDt2.isEmpty()) {
                                                            /*
                                                             * IF EMI NOT EXIST and Some Balance have pending
                                                             */
                                                            List acNoExpList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                    + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                                            if (acNoExpList.size() > 0) {
                                                                outStand = loanBal;
                                                                loanBal = 0d;
                                                            } else {
                                                                outStand = emiBal;
                                                                loanBal = loanBal - emiBal;
                                                            }
                                                        } else {
                                                            outStand = emiBal;
                                                            loanBal = loanBal - emiBal;
                                                        }
                                                    } else {
                                                        outStand = loanBal;
                                                        loanBal = 0d;
                                                    }
                                                    // bal = outStand / repOpt;
                                                    bal = outStand;
                                                } else {
                                                    if (almBktMastList2.size() == 1) {
                                                        dt3 = CbsUtil.dateAdd(tillDt, bktStartDay);
                                                        dt1 = dt3;
                                                        dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                        double balRecon1 = 0d;
                                                        List balRecon = em.createNativeQuery("SELECT ifnull(SUM(ifnull(installamt,0)) ,0) FROM emidetails "
                                                                + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + tillDt + "' and '" + dt1 + "'  ").getResultList();
                                                        if (balRecon.size() > 0) {
                                                            Vector balreconVect = (Vector) balRecon.get(0);
                                                            balRecon1 = Double.parseDouble(balreconVect.get(0).toString());
                                                        }
                                                        if (balRecon1 > loanBal) {
                                                            loanBal = 0;
                                                        } else {
                                                            List emiExistBetweenD1Dt2 = em.createNativeQuery("SELECT * FROM emidetails "
                                                                    + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + dt1 + "' and '" + dt2 + "'").getResultList();
                                                            if (emiExistBetweenD1Dt2.isEmpty()) {
                                                                loanBal = 0d;
                                                            } else {
                                                                loanBal = loanBal - balRecon1;
                                                            }
                                                        }
                                                    } else {
                                                        dt3 = CbsUtil.dateAdd(dt2, 1);
                                                        dt1 = dt3;
                                                        dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                    }

                                                    getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(installamt,0)) ,0) FROM emidetails "
                                                            + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') between '" + dt1 + "' and '" + dt2 + "' ").getResultList();

                                                    getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                    emiBal = Double.parseDouble(getLoanValueVect.get(0).toString());
                                                    if (loanBal > emiBal) {
                                                        List emiExistAfterDt2 = em.createNativeQuery("SELECT * FROM emidetails "
                                                                + " WHERE acno = '" + acNo + "' and DATE_FORMAT(duedt, '%Y%m%d') > '" + dt2 + "' ").getResultList();
                                                        if (emiExistAfterDt2.isEmpty()) {
                                                            /*
                                                             * IF EMI NOT EXIST and Some Balance have pending
                                                             */
                                                            List acNoExpList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                    + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + tillDt + "'  AND "
                                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(A.OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                                            if (acNoExpList.size() > 0) {
                                                                outStand = loanBal;
                                                                loanBal = 0d;
                                                            } else {
                                                                outStand = emiBal;
                                                                loanBal = loanBal - emiBal;
                                                            }
                                                        } else {
                                                            outStand = emiBal;
                                                            loanBal = loanBal - emiBal;
                                                        }

                                                    } else {
                                                        outStand = loanBal;
                                                        loanBal = 0d;
                                                    }
                                                    // bal = outStand / repOpt;
                                                    bal = outStand;
                                                }
//                                            System.out.println("EMI acno:" + acNo + "; LoanBal:" + loanBal + "; amt:" + bal + "; noOfDays:" + (bktEndDay - bktStartDay) + "; bktStartDay:" + bktStartDay + "; bktEndDay:" + bktEndDay + "; dt1:" + dt1 + "; dt2:" + dt2);
                                                List nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acNo + "'").getResultList();
                                                Vector nvr = (Vector) nameList.get(0);
                                                String custName = nvr.get(0).toString();

                                                List matDtList = em.createNativeQuery("select date_format(date_add(ac.openingdt, interval cl.loan_pd_month month),'%Y%m%d') from accountmaster ac, cbs_loan_acc_mast_sec cl "
                                                        + "where ac.acno = '" + acNo + "'and ac.acno = cl.acno").getResultList();
                                                Vector ele1 = (Vector) matDtList.get(0);
                                                String matDt = ele1.get(0).toString();
                                                if (bal != 0) {
                                                    if (bktNoMast == 1) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 2) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 3) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 4) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 5) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 6) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 7) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNoMast == 8) {
                                                        pojo.setBktNo(bktNoMast.toString());
                                                        pojo.setAcNo(acNo);
                                                        pojo.setAmount(bal);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    }
                                                    dataList.add(pojo);
                                                }
                                            }
                                            //                                            almPojoTable.add(almPojo);
                                        } else {
                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    /*ALM Before almFdVchWiseDate then FD Account wise total */
                    if (!ymd.parse(tillDt).after(ymd.parse(almFdVchWiseDate))) {
                        List actCodeList;
                        if (acType.equalsIgnoreCase("ALL")) {
                            actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature = '" + nature + "'").getResultList();
                        } else {
                            actCodeList = em.createNativeQuery("select AcctCode from accounttypemaster where AcctCode = '" + acType + "'").getResultList();
                        }

                        for (int k = 0; k < actCodeList.size(); k++) {
                            Vector vtr = (Vector) actCodeList.get(k);
                            String actp = vtr.get(0).toString();

                            if (brCode.equalsIgnoreCase("0A")) {
                                if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                    result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM " + table + " t, accountmaster a where "
                                            + "a.acno = t.acno and a.accttype ='" + actp + "' "
                                            + "AND t.dt<= DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' "
                                            + "GROUP BY t.ACNO HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0  ORDER BY t.ACNO").getResultList();
                                } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a "
                                            + "where a.acno = t.acno and a.accttype  ='" + actp + "' "
                                            + "AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                            + "HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0 ORDER BY t.ACNO").getResultList();
                                }
                            } else {
                                if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                    result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM " + table + " t, accountmaster a where "
                                            + "a.acno = t.acno and a.accttype  ='" + actp + "' "
                                            + "AND t.dt<= DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' AND a.curbrcode='" + brCode + "'GROUP BY t.ACNO "
                                            + "HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0  ORDER BY t.ACNO").getResultList();
                                } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    result = em.createNativeQuery("SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a "
                                            + "where a.acno = t.acno and a.accttype  ='" + actp + "' "
                                            + "AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' AND a.curbrcode='" + brCode + "'  and t.trantype<>27 "
                                            + "AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0  ORDER BY t.ACNO").getResultList();
                                }
                            }

                            if (!result.isEmpty()) {
                                for (int i = 0; i < result.size(); i++) {
                                    Vector ele = (Vector) result.get(i);
                                    AlmFddetailPojo pojo = new AlmFddetailPojo();
                                    String acno = ele.get(0).toString();
                                    double amount = Double.parseDouble(ele.get(1).toString());
                                    long noOfDays = 0;
//                                    System.out.println("AcNo:" + acno + "; Bal:" + amount);
                                    List nameList = new ArrayList();
                                    if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        nameList = em.createNativeQuery("select CUSTNAME from td_accountmaster where acno = '" + acno + "'").getResultList();
                                    } else {
                                        nameList = em.createNativeQuery("select CUSTNAME from accountmaster where acno = '" + acno + "'").getResultList();
                                    }
                                    Vector nvr = (Vector) nameList.get(0);
                                    String custName = nvr.get(0).toString();

                                    List matDtList = new ArrayList();
                                    if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                        matDtList = em.createNativeQuery("SELECT  coalesce(DATE_FORMAT(MAX(Rdmatdate),'%Y%m%d'),'" + tillDt + "') FROM accountmaster WHERE ACNO = '" + acno + "'").getResultList();
                                    } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        // matDtList = em.createNativeQuery("SELECT  coalesce(DATE_FORMAT(MAX(Matdt),'%Y%m%d'),'" + tillDt + "') FROM td_vouchmst WHERE ACNO = '" + acno + "' and td_madedt <=DATE_FORMAT('" + tillDt + "','%Y-%m-%d') ").getResultList();

                                        matDtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + tillDt + "') FROM td_vouchmst WHERE ACNO = '" + acno + "' /*and (cldt > '" + tillDt + "' or cldt is null)*/ and td_madedt <='" + tillDt + "' ").getResultList();
                                    }

                                    if (!matDtList.isEmpty()) {
                                        Vector ele1 = (Vector) matDtList.get(0);
                                        String matDt = ele1.get(0).toString();

                                        Date maddt1 = null, dt1 = null;
                                        maddt1 = ymd.parse(matDt);
                                        dt1 = ymd.parse(tillDt);

                                        if (maddt1.compareTo(dt1) > 0) {
                                            noOfDays = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(matDt));
                                        } else {
                                            noOfDays = 1;
                                        }

                                        List fstSecDayList = new ArrayList();
                                        if (bucketNo.equalsIgnoreCase("ALL")) {
                                            fstSecDayList = em.createNativeQuery("SELECT BUCKET_NO, BUCKET_START_DAY, BUCKET_END_DAY ,bucket_desc  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + bucketBase + "' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
                                        } else {
                                            fstSecDayList = em.createNativeQuery("SELECT  bucket_no,bucket_start_day,bucket_end_day ,bucket_desc FROM cbs_alm_bucket_master where  PROFILE_PARAMETER = '" + bucketBase + "' AND BUCKET_NO = '" + bucketNo + "'").getResultList();

                                        }

                                        if (!fstSecDayList.isEmpty()) {
                                            for (int j = 0; j < fstSecDayList.size(); j++) {
                                                Vector ele2 = (Vector) fstSecDayList.get(j);

                                                Integer bktNo = Integer.parseInt(ele2.get(0).toString());
                                                Integer bktStartDay = Integer.parseInt(ele2.get(1).toString());
                                                Integer bktEndDay = Integer.parseInt(ele2.get(2).toString());
                                                String bktDesc = ele2.get(3).toString();

                                                if (noOfDays >= bktStartDay && noOfDays <= bktEndDay) {
                                                    if (bktNo == 1) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 2) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 3) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 4) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 5) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 6) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 7) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    } else if (bktNo == 8) {
                                                        pojo.setBktNo(ele2.get(0).toString());
                                                        pojo.setAcNo(acno);
                                                        pojo.setAmount(amount);
                                                        pojo.setCustname(custName);
                                                        pojo.setMatDate(matDt.substring(6, 8) + "/" + matDt.substring(4, 6) + "/" + matDt.substring(0, 4));
                                                        pojo.setBktDesc(bktDesc);
                                                    }
                                                    // System.out.print("BktDesc->"+pojo.getBktDesc()+"Acno->"+pojo.getAcNo()+"Name->"+pojo.getCustname()+"matdate->"+pojo.getMatDate()+"Amount->"+pojo.getAmount());
                                                    dataList.add(pojo);
                                                }
                                            }
                                        } else {
                                            throw new ApplicationException("Data does not exist in CBS_ALM_BUCKET_MASTER !");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        /*ALM Before almFdVchWiseDate then FD Voucher wise total As per Indraprastha and New Agra Requirement*/
                        String actCodeQuery = "", brnQuery = "", brnRdQuery = "";
                        if (acType.equalsIgnoreCase("ALL")) {
                            actCodeQuery = "select AcctCode from accounttypemaster where acctNature = '" + nature + "'";
                        } else {
                            actCodeQuery = "select AcctCode from accounttypemaster where AcctCode = '" + acType + "'";
                        }
                        if (!brCode.equalsIgnoreCase("0A")) {
                            brnQuery = " AND r.curbrcode='" + brCode + "' ";
                            brnRdQuery = " AND a.curbrcode='" + brCode + "' ";
                        }
                        String query = "";
                        if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            query = "select vv.acno, vv.custname, vv.VoucherNo, vv.cnt, vv.totalVouchOpBal, vv.actualAcBalance, vv.fddt, vv.MatDt, vv.cldt, vv.IntToAcno, vv.txn, vv.rowNo, vv.balStatus,  "
                                    + "cast(if(vv.balStatus='A',0, "
                                    + "if(vv.cnt=1, if(vv.txn=0 and vv.actualAcBalance>0,vv.actualAcBalance,0), "
                                    + "if(vv.cnt=vv.rowNo, "
                                    + "if(vv.txn=0, 0,  "
                                    + "if((vv.IntToAcno='' OR vv.IntToAcno=vv.acno),  "
                                    + "if(vv.actualAcBalance<=vv.totalVouchOpBal, if((vv.actualAcBalance=vv.totalVouchOpBal), 0, if((vv.IntToAcno=vv.acno), 0, vv.actualAcBalance-vv.totalVouchOpBal)), vv.actualAcBalance-vv.totalVouchOpBal), "
                                    + "if(vv.actualAcBalance<=vv.totalVouchOpBal, 0,vv.actualAcBalance-vv.totalVouchOpBal))),0)))  as decimal(25,2)) as differenceAmt1,  "
                                    + "cast(if(vv.balStatus='A',vv.actualAcBalance, if(vv.cnt=1, if(vv.txn=0,0,vv.actualAcBalance), "
                                    + "if(vv.txn=0, 0, "
                                    + "if((vv.IntToAcno='' OR vv.IntToAcno=vv.acno),  "
                                    + "if(vv.actualAcBalance<=vv.totalVouchOpBal, if(vv.actualAcBalance=vv.totalVouchOpBal, vv.vouchOpBal, if((vv.IntToAcno=vv.acno), vv.prinAmt, vv.vouchOpBal)), vv.vouchOpBal),  "
                                    + "if(vv.actualAcBalance<=vv.totalVouchOpBal, if(vv.actualAcBalance=vv.totalVouchOpBal,vv.vouchOpBal,vv.prinAmt), vv.vouchOpBal))))) as decimal(25,2)) as mainVchBal, vv.lien "
                                    + "  from "
                                    + "(select  xx.acno, xx.custname, xx.VoucherNo, xx.cnt, xx.vchBal, xx.TotalVchBal, "
                                    + " xx.prinAmt, xx.intAmt, xx.tdsAmt, xx.drInt, xx.vouchOpBal, xx.totalVouchOpBal,  "
                                    + " xx.actualAcBalance,  "
                                    + " xx.fddt, xx.MatDt, xx.cldt, xx.IntToAcno, xx.txn, cast(((CASE CONCAT(xx.acno)  "
                                    + "        WHEN @cur_crew_type "
                                    + "        THEN @curRow := @curRow + 1 "
                                    + "        ELSE @curRow := 0 END) + 1) as decimal) AS rowNo, @cur_crew_type := CONCAT(xx.acno) AS cur_crew_type, xx.balStatus, xx.lien  from  "
                                    + " "
                                    + "(select  aa.acno, aa.custname, aa.VoucherNo, zz.cnt, aa.bal as vchBal, cast(vch.vchBal as decimal(25,2)) as TotalVchBal, "
                                    + " aa.prinAmt, aa.intAmt, aa.tdsAmt, aa.drInt, ((ifnull(aa.prinAmt,0)+ifnull(aa.intAmt,0))-ifnull(aa.tdsAmt,0)-ifnull(aa.drInt,0)) as vouchOpBal, cast(zz.totalVouchOpBal as decimal(25,2))  as totalVouchOpBal,  "
                                    + " cast(aa.actualAcBalance as decimal(25,2))  as actualAcBalance,  "
                                    + " aa.fddt, aa.MatDt, aa.cldt, aa.IntToAcno, aa.txn, aa.balStatus, aa.lien from  "
                                    + "  "
                                    + "(SELECT d.acno, aa.custname, d.VoucherNo, ifnull(tt.bal,0) as bal,if(tt.txn<>0,ifnull(d.prinAmt,0),0) as prinAmt,ifnull(d.intAmt,0) as intAmt,ifnull(d.tdsAmt,0) as tdsAmt,ifnull(d.drInt,0) as drInt,  "
                                    + " aa.actualAcBalance, d.fddt, d.MatDt, d.cldt, d.IntToAcno, ifnull(tt.txn,0) as txn, d.balStatus, d.lien  FROM  "
                                    + "  "
                                    + "(select r.acno, ifnull(a.VoucherNo,0) as VoucherNo, a.cldt, a.MatDt, a.fddt, ifnull(a.prinAmt,0) as prinAmt,  ifnull(a.intAmt,0) as intAmt,  "
                                    + "ifnull(a.tdsAmt,0) as tdsAmt, ifnull(a.drInt,0) as drInt,   a.IntToAcno, r.balStatus, a.lien from  "
                                    + "  "
                                    + "(select r.acno, r.balStatus from  "
                                    + "(select distinct acno,0 as remAmt, 'V' as balStatus, substring(acno,1,2) as curbrcode from td_vouchmst where  "
                                    + "(cldt is null OR cldt >'" + tillDt + "')  "
                                    + "and fddt<='" + tillDt + "'  group by  acno "
                                    + "union "
                                    + "SELECT distinct t.acno, cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) as remAmt, 'A' as balStatus, curbrcode FROM td_recon t, td_accountmaster a  "
                                    + "where a.acno = t.acno /*and a.acno in ('021900306801','032400726401','042400482801','042400487401')*/ "
                                    + "AND t.dt<='" + tillDt + "' and t.auth='Y' AND t.CLOSEFLAG IS NULL  and t.ACNO not in (select distinct acno from td_vouchmst where  "
                                    + "(cldt is null OR cldt >'" + tillDt + "')  "
                                    + "and fddt<='" + tillDt + "'  group by  acno) "
                                    + "GROUP BY t.ACNO HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0  "
                                    + " "
                                    + ")r where substring(r.acno,3,2) in (" + actCodeQuery + ") " + brnQuery + ")r "
                                    + "left join "
                                    + " "
                                    + "(select a.acno, a.VoucherNo, a.cldt, a.MatDt, a.fddt, ifnull(a.prinAmt,0) as prinAmt,  ifnull(b.intAmt,0) as intAmt,  "
                                    + "ifnull(c.tdsAmt,0) as tdsAmt, ifnull(d.drInt,0) as drInt,   a.IntToAcno, a.lien from  "
                                    + "(select acno, VoucherNo, ifnull(PrinAmt,0) as prinAmt, cldt, MatDt, fddt, IntToAcno, ifnull(lien,'N') as lien from td_vouchmst where (cldt is null OR cldt >'" + tillDt + "') and fddt<='" + tillDt + "' group by  acno, VoucherNo)a  /*gettting Voucher wise Principle, Close Date, Mature Date*/ "
                                    + "left join  "
                                    + "(select acno,VoucherNo, ifnull(sum(Interest),0) as intAmt from td_interesthistory where dt<='" + tillDt + "' group by acno, VoucherNo)b   on a.ACNO  = b.acno and a.VoucherNo = b.VoucherNo /*Getting the Voucher wise Interest*/ "
                                    + "left join  "
                                    + "(select acno,VoucherNo,ifnull(sum(tds),0) as tdsAmt from tdshistory where dt<='" + tillDt + "' group by acno, VoucherNo)c  on a.ACNO  = c.acno and a.VoucherNo = c.VoucherNo  /*Getting the Voucher wise TDS*/ "
                                    + "left join  "
                                    + "(select acno,VoucherNo,ifnull(sum(dramt),0) as drInt from td_recon where dt<='" + tillDt + "'  "
                                    + "and ty= 1 and trantype = 2 and trandesc = 4 and details like '%Int.on Vch : %' and intFlag is null and auth = 'Y' group by acno,VoucherNo)d  on a.ACNO  = d.acno and a.VoucherNo = d.VoucherNo  /* Getting the Voucher wise Debit Interest transfered in another account*/ "
                                    + "group by a.acno, a.voucherno order by a.acno, a.voucherno, a.MatDt)a on a.acno = r.acno)d /* Getting the Voucher wise Prin+int-tds-intTransfered*/ "
                                    + " "
                                    + "left join  "
                                    + "(SELECT t.ACNO,t.VoucherNo, cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) as bal, count(*) as txn FROM td_recon t, td_accountmaster a  "
                                    + "where a.acno = t.acno  "
                                    + "AND t.dt<='" + tillDt + "' and t.auth='Y' AND t.CLOSEFLAG IS NULL /*and t.acno in ('021900306801','032400726401','042400482801','042400487401')*/GROUP BY t.ACNO, t.VoucherNo)tt /*Vouchetr wise balance*/ on d.acno = tt.ACNO and d.VoucherNo = tt.VoucherNo  "
                                    + " "
                                    + "left join  "
                                    + "(SELECT t.ACNO,t.VoucherNo, cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) as actualAcBalance, ifnull(a.custname,'') as custname FROM td_recon t, td_accountmaster a  "
                                    + "where a.acno = t.acno AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y%m%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO)aa on aa.ACNO = d.ACNO /*Account Wise Balance*/  "
                                    + " "
                                    + "group by d.acno, d.voucherno order by d.acno, d.voucherno, d.MatDt)aa  "
                                    + " "
                                    + "left join  "
                                    + "(select z.acno, sum(z.vouchOpBal) as totalVouchOpBal, count(*) as cnt from  /*total Account wise Prin+int-tds-intTranfer*/  "
                                    + "(select a.acno, a.VoucherNo, a.prinAmt, a.cldt, a.MatDt, ((ifnull(if(ifnull(e.txnCnt,0)<>0,a.prinAmt,0),0)+ifnull(b.intAmt,0))-ifnull(c.tdsAmt,0)-ifnull(d.drInt,0)) as vouchOpBal, ifnull(e.txnCnt,0) as txnCnt from  "
                                    + "(select acno, VoucherNo, ifnull(PrinAmt,0) as prinAmt, cldt, MatDt, fddt from td_vouchmst where (cldt is null OR cldt >'" + tillDt + "')  and fddt<='" + tillDt + "'  group by  acno, VoucherNo)a /*gettting Voucher wise Principle, Close Date, Mature Date*/  "
                                    + "left join  "
                                    + "(select acno,VoucherNo, ifnull(sum(Interest),0) as intAmt from td_interesthistory where dt<='" + tillDt + "' group by acno, VoucherNo)b   on a.ACNO  = b.acno and a.VoucherNo = b.VoucherNo /*Getting the Voucher wise Interest*/  "
                                    + "left join  "
                                    + "(select acno,VoucherNo,ifnull(sum(tds),0) as tdsAmt from tdshistory where dt<='" + tillDt + "' group by acno, VoucherNo)c  on a.ACNO  = c.acno and a.VoucherNo = c.VoucherNo  /*Getting the Voucher wise TDS*/  "
                                    + "left join  "
                                    + "(select acno,VoucherNo,ifnull(sum(dramt),0) as drInt, count(*) as txnCnt from td_recon where dt<='" + tillDt + "'  "
                                    + "and ty= 1 and trantype = 2 and trandesc = 4 and details like '%Int.on Vch : %' and intFlag is null and auth = 'Y' group by acno,VoucherNo)d  on a.ACNO  = d.acno and a.VoucherNo = d.VoucherNo  /* Getting the Voucher wise Debit Interest transfered in another account*/  "
                                    + "left join  "
                                    + "(select acno,VoucherNo, count(*) as txnCnt from td_recon where dt<='" + tillDt + "' and  "
                                    + " intFlag is null and auth = 'Y' group by acno,VoucherNo)e  on a.ACNO  = e.acno and a.VoucherNo = e.VoucherNo  /* Getting the Voucher wise Debit Interest transfered in another account*/  "
                                    + "group by a.acno, a.voucherno order by a.acno, a.voucherno, a.MatDt)z group by z.acno)zz on zz.acno = aa.ACNO /*getting total no of active Voucher and Account wise Prin+int-tds-intTranfer*/  "
                                    + " "
                                    + "left join  "
                                    + "(select aa.ACNO, sum(aa.vchBal) as vchBal from  "
                                    + "(select acno, VoucherNo, ifnull(PrinAmt,0) as prinAmt, cldt, MatDt, fddt from td_vouchmst where (cldt is null OR cldt >'" + tillDt + "')  and fddt<='" + tillDt + "' group by  acno, VoucherNo)bb  "
                                    + "left join  "
                                    + "(SELECT t.ACNO,t.VoucherNo, (IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0)) as vchBal FROM td_recon t, td_accountmaster a  "
                                    + "where a.acno = t.acno /*and a.acno = '021900164101' */ "
                                    + "AND t.dt<=DATE_FORMAT('" + tillDt + "','%Y%m%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO,t.VoucherNo)aa on bb.acno = aa.ACNO and aa.VoucherNo= bb.VoucherNo  "
                                    + "where (bb.cldt is null OR bb.cldt >'" + tillDt + "')  and bb.fddt<='" + tillDt + "'  and aa.VoucherNo is not null and aa.VoucherNo<>0 group by aa.ACNO)vch on aa.ACNO = vch.ACNO /*Getting Total Active Voucher Balance*/ "
                                    + "where   (aa.cldt is null OR aa.cldt >'" + tillDt + "')   "
                                    + "group by aa.acno, aa.voucherno  order by aa.acno, aa.voucherno, aa.MatDt) xx, (SELECT @curRow := 0, @cur_crew_type := '') counter )vv  order by vv.acno, vv.VoucherNo  ";
                        } else if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            query = "SELECT t.ACNO,IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0), custname, "
                                    + "coalesce(DATE_FORMAT(MAX(Rdmatdate),'%Y%m%d'),'" + tillDt + "') as matDt FROM rdrecon t, accountmaster a where "
                                    + "a.acno = t.acno and a.accttype  in (" + actCodeQuery + ") " + brnRdQuery
                                    + "AND t.dt<= DATE_FORMAT('" + tillDt + "','%Y-%m-%d') and t.auth='Y' "
                                    + "GROUP BY t.ACNO HAVING cast(IFNULL((SUM(t.CRAMT)-SUM(t.DRAMT)),0) as decimal(25,2)) > 0  ORDER BY t.ACNO";
                        }
//                        System.out.println("query:" + query);
                        result = em.createNativeQuery(query).getResultList();
//                        long bktStartDay = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(frDt));
//                        long bktEndDay = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(toDt));
                        List fstSecDayList = new ArrayList();
                        fstSecDayList = almQtrFacadeRemote.getAlmBucketMaster(bucketBase, tillDt, bucketNo);
                        if (!fstSecDayList.isEmpty()) {
                            for (int j = 0; j < fstSecDayList.size(); j++) {
                                Vector ele2 = (Vector) fstSecDayList.get(j);
                                Integer bktNo = Integer.parseInt(ele2.get(2).toString());
                                String bktDesc = ele2.get(3).toString();
                                long bktStartDay = Integer.parseInt(ele2.get(4).toString());
                                long bktEndDay = Integer.parseInt(ele2.get(5).toString());

                                String minBktNo = ele2.get(12).toString();
                                String minBktDesc = ele2.get(13).toString();
                                String maxBktNo = ele2.get(14).toString();
                                String maxBktDesc = ele2.get(15).toString();
                                if (!result.isEmpty()) {
                                    for (int i = 0; i < result.size(); i++) {
                                        Vector ele = (Vector) result.get(i);
                                        AlmFddetailPojo pojo = new AlmFddetailPojo();
                                        String acno = null, custName = null, vchNo = null, matDt = null;
                                        double amount = 0, diffAmount = 0;
                                        if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                            acno = ele.get(0) == null ? "" : ele.get(0).toString();
                                            custName = ele.get(1) == null ? "" : ele.get(1).toString();
                                            vchNo = ele.get(2) == null ? "0" : ele.get(2).toString();
                                            matDt = ele.get(7) == null ? tillDt : ymd.format(ymd_1.parse(ele.get(7).toString()));
                                            amount = ele.get(14) == null ? 0 : Double.parseDouble(ele.get(14).toString());
                                            diffAmount = ele.get(13) == null ? 0 : Double.parseDouble(ele.get(13).toString());
                                        } else if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            acno = ele.get(0).toString();
                                            amount = Double.parseDouble(ele.get(1).toString());
                                            custName = ele.get(2).toString();
                                            matDt = ele.get(3).toString();
                                        }
                                        long noOfDays = 0;
                                        Date maddt1 = null, dt1 = null;
                                        maddt1 = ymd.parse(matDt);
                                        dt1 = ymd.parse(tillDt);
                                        if (maddt1.compareTo(dt1) > 0) {
                                            noOfDays = CbsUtil.dayDiff(ymd.parse(tillDt), ymd.parse(matDt));
                                        } else {
                                            noOfDays = 1;
                                        }
                                        if (noOfDays >= bktStartDay && noOfDays <= bktEndDay) {
//                                            System.out.println("AcNo:" + acno + "; Bal:" + amount + "; Bucket:" + bktDesc + "; vchNo:" + vchNo);
                                            pojo.setAcNo(acno);
                                            pojo.setVchNo(vchNo);
                                            pojo.setAmount(amount);
                                            pojo.setCustname(custName);
                                            pojo.setMatDate(dmy.format(ymd.parse(matDt)));
                                            pojo.setBktDesc(bktDesc);
                                            pojo.setBktNo(bktNo.toString());
                                            dataList.add(pojo);
                                            if (diffAmount != 0) {
                                                pojo = new AlmFddetailPojo();
//                                                System.out.println(">>>AcNo:" + acno + "; Bal:" + diffAmount + "; Bucket:" + bktDesc + "; vchNo:" + vchNo);
                                                pojo.setAcNo(acno);
                                                pojo.setVchNo("0");
                                                pojo.setAmount(diffAmount);
                                                pojo.setCustname(custName);
                                                pojo.setMatDate(dmy.format(ymd.parse(tillDt)));
                                                pojo.setBktDesc(maxBktDesc);
                                                pojo.setBktNo(maxBktNo);
                                                dataList.add(pojo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
