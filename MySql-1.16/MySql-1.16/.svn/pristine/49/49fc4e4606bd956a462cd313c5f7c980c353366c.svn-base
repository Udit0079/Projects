package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.report.CashierCashPojo;
import com.cbs.dto.report.CbsCashClgTimewisePojo;
import com.cbs.dto.report.CbsCashTrfClgScrollTimewisePojo;
import com.cbs.dto.report.DayBookPojo;
import com.cbs.dto.report.DayBookStmPojo;
import com.cbs.dto.report.IncomeExpenditurePojo;
import com.cbs.dto.report.IncomeExpenditureStTimePojo;
import com.cbs.dto.report.LedgerReportPojo;
import com.cbs.dto.report.SubDailyReportPojo;
import com.cbs.dto.report.LongBookReportPojo;
import com.cbs.dto.report.MiscLongBookReportPojo;
import com.cbs.dto.report.MiscLongBookSubTotalReportPojo;
import com.cbs.dto.report.MonthlyDepositProgressReportPojo;
import com.cbs.dto.report.SortedTrfScroll;
import com.cbs.dto.report.TokenBookPojo;
import com.cbs.dto.report.TransactionCountPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacade;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "LedgerReportFacade")
public class LedgerReportFacade implements LedgerReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymd_1 = new SimpleDateFormat("yyyy-MM-dd");
    @EJB
    LoanReportFacadeRemote loanReportFacadeLocal;
    @EJB
    private MiscReportFacadeRemote miscReportFacadeLocal;
    @EJB
    private FtsPostingMgmtFacadeRemote fts;
    NumberFormat nft = new DecimalFormat("0.00");

    @Override
    public List<LedgerReportPojo> getLedgerReport(String acType, String fromAccount, String toAccount, String fromDate, String toDate, String brCode) throws ApplicationException {
        List<LedgerReportPojo> returnList = new ArrayList<LedgerReportPojo>();
        try {
            List tempList = null;
            Vector tempVector;
            while (fromAccount.length() < 6) {
                fromAccount = "0" + fromAccount;
            }
            while (toAccount.length() < 6) {
                toAccount = "0" + toAccount;
            }
            String acNo;
            String acNat = common.getAcNatureByAcType(acType);
            if (brCode.equalsIgnoreCase("0A")) {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList = em.createNativeQuery("select acno from td_accountmaster where substring(acno,5,6) between '" + fromAccount + "' and '" + toAccount + "'  and accttype='" + acType + "' "
                            + "AND (closingdate IS NULL OR closingdate = '' OR closingdate >'" + toDate + "')").getResultList();
                } else {
                    tempList = em.createNativeQuery("select acno from accountmaster where substring(acno,5,6) between '" + fromAccount + "' and '" + toAccount + "'  and accttype='" + acType + "' "
                            + "AND (closingdate IS NULL OR closingdate = '' OR closingdate >'" + toDate + "')").getResultList();
                }
            } else {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList = em.createNativeQuery("select acno from td_accountmaster where substring(acno,5,6) between '" + fromAccount + "' and '" + toAccount + "' and curbrcode='" + brCode + "' and accttype='" + acType + "' "
                            + "AND (closingdate IS NULL OR closingdate = '' OR closingdate >'" + toDate + "')").getResultList();
                } else {
                    tempList = em.createNativeQuery("select acno from accountmaster where substring(acno,5,6) between '" + fromAccount + "' and '" + toAccount + "' and curbrcode='" + brCode + "' and accttype='" + acType + "' "
                            + "AND (closingdate IS NULL OR closingdate = '' OR closingdate >'" + toDate + "')").getResultList();
                }
            }

            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acNo = tempVector.get(0).toString();
                if (common.getAcTypeByAcNo(acNo) != null) {
                    for (AccountStatementReportPojo pojo1 : miscReportFacadeLocal.getAccountStatementReport(acNo, fromDate, toDate)) {
                        LedgerReportPojo pojo = new LedgerReportPojo();
                        pojo.setAcNo(pojo1.getAcNo());
                        pojo.setAcType(pojo1.getAcType());
                        double odlimit = 0;
                        double roi = 0;
                        if (fts.getAccountNature(pojo.getAcNo()).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            List tempList1 = em.createNativeQuery("select odlimit from accountmaster where acno='" + acNo + "'").getResultList();
                            if (!tempList1.isEmpty()) {
                                Vector ele = (Vector) tempList1.get(0);
                                if (ele != null || ele.get(0) != null) {
                                    odlimit = Double.parseDouble(ele.get(0).toString());
                                }
                            }
                            roi = Double.parseDouble(loanInterestCalculationBean.getRoiLoanAccount(odlimit, toDate, acNo));
                        }
                        pojo.setRoi(new BigDecimal(roi));
                        pojo.setAmtSanc(new BigDecimal(odlimit));
                        pojo.setBalance(new BigDecimal(pojo1.getBalance()));
                        pojo.setChequeNo(pojo1.getChequeNo());
                        pojo.setCrAdd(pojo1.getCrAdd());
                        pojo.setCustName(pojo1.getCustName());
                        pojo.setDate(pojo1.getDate());
                        pojo.setDeposit(new BigDecimal(pojo1.getDeposit()));
                        pojo.setJtName(pojo1.getJtName());
                        pojo.setNomination(pojo1.getNomination());
                        pojo.setOpenBal(new BigDecimal(pojo1.getOpeningBal()));
                        pojo.setParticulars(pojo1.getParticulars());
                        pojo.setPendBal(new BigDecimal(pojo1.getPendingBal()));
                        pojo.setPrAdd(pojo1.getPrAdd());
                        pojo.setWithDrawl(new BigDecimal(pojo1.getWithdrawl()));
                        returnList.add(pojo);
                    }
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    @Override
    public List<MiscLongBookReportPojo> getMiscLongBookReport(String DT, String BRCODE, String orderby) {
        long begin = System.nanoTime();
        List<MiscLongBookReportPojo> resultList = new ArrayList<MiscLongBookReportPojo>();
        String orderBy = "";
        if (orderby.equalsIgnoreCase("recno")) {
            orderBy = " order by gl_recon.recno";
        } else if (orderby.equalsIgnoreCase("a.acno")) {
            orderBy = " order by gltable.acno";
        }
        try {
            List resultListFirst = em.createNativeQuery("select gltable.acno,acname,cramt,dramt,details,trantype,ty,recno from gl_recon, gltable where dt='" + DT + "' and gl_recon.acno=gltable.acno and auth='Y'"
                    + " and trantype<>5 and gl_recon.org_brnid = '" + BRCODE + "' and gltable.acno not in (select distinct acno from gltable where acname like '%CASH IN HAND%')"
                    + " and gltable.acno not in (select distinct acno from gltable where acname like '%INTERSOLE%') and (substring(gltable.acno,3,8) not between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue()
                    + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "') and gl_recon.trandesc <> 999  " + orderBy + " ").getResultList();
            if (resultListFirst.size() > 0) {
                for (int i = 0; i < resultListFirst.size(); i++) {
                    Vector element = (Vector) resultListFirst.get(i);
                    String glAcno = element.get(0).toString();
                    String acName = element.get(1).toString();
                    double crAmt = Double.parseDouble(element.get(2).toString());
                    double drAmt = Double.parseDouble(element.get(3).toString());
                    String details = element.get(4).toString();
                    int tranType = Integer.parseInt(element.get(5).toString());
                    int ty = Integer.parseInt(element.get(6).toString());
                    double recno = Double.parseDouble(element.get(7).toString());

                    MiscLongBookReportPojo pojoObj = new MiscLongBookReportPojo();
                    pojoObj.setRecno(recno);
                    if (tranType == 0) {
                        if (ty == 0) {
                            pojoObj.setGlAcno(glAcno);
                            pojoObj.setAcName(acName);
                            pojoObj.setDtl(details);
                            pojoObj.setCrCsh(new BigDecimal(crAmt));
                            pojoObj.setCrClg(new BigDecimal(0));
                            pojoObj.setCrTrs(new BigDecimal(0));
                            pojoObj.setDrCsh(new BigDecimal(0));
                            pojoObj.setDrClg(new BigDecimal(0));
                            pojoObj.setDrTrs(new BigDecimal(0));
                            resultList.add(pojoObj);
                        } else {
                            pojoObj.setGlAcno(glAcno);
                            pojoObj.setAcName(acName);
                            pojoObj.setDtl(details);
                            pojoObj.setCrCsh(new BigDecimal(0));
                            pojoObj.setCrClg(new BigDecimal(0));
                            pojoObj.setCrTrs(new BigDecimal(0));
                            pojoObj.setDrCsh(new BigDecimal(drAmt));
                            pojoObj.setDrClg(new BigDecimal(0));
                            pojoObj.setDrTrs(new BigDecimal(0));
                            resultList.add(pojoObj);
                        }
                    }
                    if (tranType == 2 || tranType == 8) {
                        if (ty == 0) {
                            pojoObj.setGlAcno(glAcno);
                            pojoObj.setAcName(acName);
                            pojoObj.setDtl(details);
                            pojoObj.setCrCsh(new BigDecimal(0));
                            pojoObj.setCrClg(new BigDecimal(0));
                            pojoObj.setCrTrs(new BigDecimal(crAmt));
                            pojoObj.setDrCsh(new BigDecimal(0));
                            pojoObj.setDrClg(new BigDecimal(0));
                            pojoObj.setDrTrs(new BigDecimal(0));
                            resultList.add(pojoObj);
                        } else {
                            pojoObj.setGlAcno(glAcno);
                            pojoObj.setAcName(acName);
                            pojoObj.setDtl(details);
                            pojoObj.setCrCsh(new BigDecimal(0));
                            pojoObj.setCrClg(new BigDecimal(0));
                            pojoObj.setCrTrs(new BigDecimal(0));
                            pojoObj.setDrCsh(new BigDecimal(0));
                            pojoObj.setDrClg(new BigDecimal(0));
                            pojoObj.setDrTrs(new BigDecimal(drAmt));
                            resultList.add(pojoObj);
                        }
                    }
                    if (tranType == 1) {
                        if (ty == 0) {
                            pojoObj.setGlAcno(glAcno);
                            pojoObj.setAcName(acName);
                            pojoObj.setDtl(details);
                            pojoObj.setCrCsh(new BigDecimal(0));
                            pojoObj.setCrClg(new BigDecimal(crAmt));
                            pojoObj.setCrTrs(new BigDecimal(0));
                            pojoObj.setDrCsh(new BigDecimal(0));
                            pojoObj.setDrClg(new BigDecimal(0));
                            pojoObj.setDrTrs(new BigDecimal(0));
                            resultList.add(pojoObj);
                        } else {
                            pojoObj.setGlAcno(glAcno);
                            pojoObj.setAcName(acName);
                            pojoObj.setDtl(details);
                            pojoObj.setCrCsh(new BigDecimal(0));
                            pojoObj.setCrClg(new BigDecimal(0));
                            pojoObj.setCrTrs(new BigDecimal(0));
                            pojoObj.setDrCsh(new BigDecimal(0));
                            pojoObj.setDrClg(new BigDecimal(drAmt));
                            pojoObj.setDrTrs(new BigDecimal(0));
                            resultList.add(pojoObj);
                        }
                    }
                }
            }
            if (orderby.equalsIgnoreCase("recno")) {
                Collections.sort(resultList, new OrderByRecnoComparatorMisLongBook());
            } else if (orderby.equalsIgnoreCase("a.acno")) {
                Collections.sort(resultList, new AcNoByComparatorMisLongBook());
            }
            System.out.println("Returned List Size---->" + resultList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Execution time for getMiscLongBookDetails() method is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        return resultList;
    }

    private class AcNoByComparatorMisLongBook implements Comparator<MiscLongBookReportPojo> {

        public int compare(MiscLongBookReportPojo tdIntDetailObj1, MiscLongBookReportPojo tdIntDetailObj2) {
            return tdIntDetailObj1.getGlAcno().compareTo(tdIntDetailObj2.getGlAcno());
        }
    }

    private class OrderByRecnoComparatorMisLongBook implements Comparator<MiscLongBookReportPojo> {

        public int compare(MiscLongBookReportPojo tdIntDetailObj1, MiscLongBookReportPojo tdIntDetailObj2) {
            return Double.compare(tdIntDetailObj1.getRecno(), tdIntDetailObj2.getRecno());
        }
    }

    @Override
    public List<MiscLongBookSubTotalReportPojo> getMiscLongBookSubTotalReport(String date, String brCode, String fromTime, String toTime, boolean timeAllowed) throws ApplicationException {
        List<MiscLongBookSubTotalReportPojo> returnList = new ArrayList<MiscLongBookSubTotalReportPojo>();
        String acNo = "", acName = "", details = "";
        double crAmt = 0.0, drAmt = 0.0;
        int tranType = 0, iy = 0;
        List tempList = null;
        try {
            if (timeAllowed) {
                tempList = em.createNativeQuery("select coalesce(substring(g.acno,3,8),'0') as acno,coalesce(t.acname,'0') as acname, "
                        + "concat(coalesce(g.details,'0'), coalesce(g.instno,'0')) as details, coalesce(g.cramt,0) as cramt,coalesce(g.dramt,0) as dramt,g.trantype,g.iy from "
                        + "gl_recon g,gltable t where g.dt = '" + date + "' and dt = '" + date + "' and g.acno=t.acno and g.trantime between '" + fromTime + "' and '"
                        + toTime + "' and (substring(g.acno,3,8) not between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "') and "
                        + "(substring(g.acno,3,6)<>'" + SiplConstant.GL_CASH.getValue() + "') and g.acno  not in (select distinct glhead from accounttypemaster where glhead is not null) and auth='Y' and "
                        + " trantype<>5 and g.org_brnid = '" + brCode + "' and t.acno not in (select distinct acno from gltable where acname like '%CASH IN HAND%') "
                        + " and t.acno not in (select distinct acno from gltable where acname like '%INTERSOLE%') and g.trandesc <> 999 order by g.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select coalesce(substring(g.acno,3,8),'0') as acno,coalesce(t.acname,'0') as acname,concat(coalesce(g.details,'0'), coalesce(g.instno,'0')) as details"
                        + ",coalesce(g.cramt,0) as cramt,coalesce(g.dramt,0) as dramt,g.trantype,g.iy from gl_recon g,gltable t where g.dt = '" + date + "' and dt = '" + date + "' "
                        + "and g.acno=t.acno and (substring(g.acno,3,8) not between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "') "
                        + "and (substring(g.acno,3,6)<>'" + SiplConstant.GL_CASH.getValue() + "') and g.acno  not in (select distinct glhead from accounttypemaster "
                        + "where glhead is not null) and auth='Y' and trantype<>5 and g.org_brnid = '" + brCode + "' and t.acno not in "
                        + "(select distinct acno from gltable where acname like '%CASH IN HAND%') and t.acno not in (select distinct acno from gltable "
                        + "where acname like '%INTERSOLE%') and g.trandesc <> 999 order by g.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    acNo = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    acName = ele.get(1).toString();
                }
                if (ele.get(2) != null) {
                    details = ele.get(2).toString();
                }
                if (ele.get(3) != null) {
                    crAmt = Double.parseDouble(ele.get(3).toString());
                }
                if (ele.get(4) != null) {
                    drAmt = Double.parseDouble(ele.get(4).toString());
                }
                if (ele.get(5) != null) {
                    tranType = Integer.parseInt(ele.get(5).toString());
                }
                if (ele.get(6) != null) {
                    iy = Integer.parseInt(ele.get(6).toString());
                }
                MiscLongBookSubTotalReportPojo pojo = new MiscLongBookSubTotalReportPojo();
                pojo.setAcName(acName);
                pojo.setAcNo(acNo);
                pojo.setCrAmt(crAmt);
                pojo.setDetails(details);
                pojo.setDrAmt(drAmt);
                pojo.setIy(iy);
                pojo.setTranType(tranType);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    /**
     * ********************* INCOME EXPENDITURE **************
     */
    /**
     *
     * @param dt
     * @param brCode
     * @return
     */
    @Override
    public List<IncomeExpenditurePojo> incomeExpenditure(String dt, String brCode) throws ApplicationException {
        List<IncomeExpenditurePojo> incomeExpenditurePojos = new ArrayList<IncomeExpenditurePojo>();
        try {
            List value = new ArrayList();
            String acno;
            String acName;
            double crAmt;
            double drAmt;
            String details;
            int tranType;
            int ty;
            String bnkName = null, bnkAddress = null;
            List objBan = common.getBranchNameandAddress(brCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }

            value = em.createNativeQuery("select gltable.acno,acname,cramt,dramt,details,trantype,ty from gl_recon, gltable where dt='" + dt + "' "
                    + "and gl_recon.acno=gltable.acno and substring(gl_recon.acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "' and '"
                    + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' and auth='y' and trantype<>5 and gl_recon.org_brnid = '" + brCode + "' order by gltable.acno").getResultList();
            if (value.size() > 0) {
                for (int i = 0; i < value.size(); i++) {
                    Vector values = (Vector) value.get(i);
                    acno = values.get(0).toString();
                    acName = values.get(1).toString();
                    crAmt = Double.parseDouble(values.get(2).toString());
                    drAmt = Double.parseDouble(values.get(3).toString());
                    details = values.get(4).toString();
                    tranType = Integer.parseInt(values.get(5).toString());
                    ty = Integer.parseInt(values.get(6).toString());

                    if (tranType == 0) {
                        if (ty == 0) {
                            IncomeExpenditurePojo balCert = new IncomeExpenditurePojo();
                            balCert.setACNO(acno);
                            balCert.setAC_NAME(acName);
                            balCert.setDTL(details);
                            balCert.setCR_CSH(new BigDecimal(crAmt));
                            balCert.setCR_CLG(new BigDecimal(0));
                            balCert.setCR_TRF(new BigDecimal(0));
                            balCert.setDR_CSH(new BigDecimal(0));
                            balCert.setDR_CLG(new BigDecimal(0));
                            balCert.setDR_TRF(new BigDecimal(0));
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            incomeExpenditurePojos.add(balCert);
                        } else {
                            IncomeExpenditurePojo balCert = new IncomeExpenditurePojo();
                            balCert.setACNO(acno);
                            balCert.setAC_NAME(acName);
                            balCert.setDTL(details);
                            balCert.setCR_CSH(new BigDecimal(0));
                            balCert.setCR_CLG(new BigDecimal(0));
                            balCert.setCR_TRF(new BigDecimal(0));
                            balCert.setDR_CSH(new BigDecimal(drAmt));
                            balCert.setDR_CLG(new BigDecimal(0));
                            balCert.setDR_TRF(new BigDecimal(0));
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            incomeExpenditurePojos.add(balCert);
                        }
                    }
                    if (tranType == 2 || tranType == 8) {
                        if (ty == 0) {
                            IncomeExpenditurePojo balCert = new IncomeExpenditurePojo();
                            balCert.setACNO(acno);
                            balCert.setAC_NAME(acName);
                            balCert.setDTL(details);
                            balCert.setCR_CSH(new BigDecimal(0));
                            balCert.setCR_CLG(new BigDecimal(0));
                            balCert.setCR_TRF(new BigDecimal(crAmt));
                            balCert.setDR_CSH(new BigDecimal(0));
                            balCert.setDR_CLG(new BigDecimal(0));
                            balCert.setDR_TRF(new BigDecimal(0));
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            incomeExpenditurePojos.add(balCert);
                        } else {
                            IncomeExpenditurePojo balCert = new IncomeExpenditurePojo();
                            balCert.setACNO(acno);
                            balCert.setAC_NAME(acName);
                            balCert.setDTL(details);
                            balCert.setCR_CSH(new BigDecimal(0));
                            balCert.setCR_CLG(new BigDecimal(0));
                            balCert.setCR_TRF(new BigDecimal(0));
                            balCert.setDR_CSH(new BigDecimal(0));
                            balCert.setDR_CLG(new BigDecimal(0));
                            balCert.setDR_TRF(new BigDecimal(drAmt));
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            incomeExpenditurePojos.add(balCert);
                        }
                    }
                    if (tranType == 1) {
                        if (ty == 0) {
                            IncomeExpenditurePojo balCert = new IncomeExpenditurePojo();
                            balCert.setACNO(acno);
                            balCert.setAC_NAME(acName);
                            balCert.setDTL(details);
                            balCert.setCR_CSH(new BigDecimal(0));
                            balCert.setCR_CLG(new BigDecimal(crAmt));
                            balCert.setCR_TRF(new BigDecimal(0));
                            balCert.setDR_CSH(new BigDecimal(0));
                            balCert.setDR_CLG(new BigDecimal(0));
                            balCert.setDR_TRF(new BigDecimal(0));
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            incomeExpenditurePojos.add(balCert);
                        } else {
                            IncomeExpenditurePojo balCert = new IncomeExpenditurePojo();
                            balCert.setACNO(acno);
                            balCert.setAC_NAME(acName);
                            balCert.setDTL(details);
                            balCert.setCR_CSH(new BigDecimal(0));
                            balCert.setCR_CLG(new BigDecimal(0));
                            balCert.setCR_TRF(new BigDecimal(0));
                            balCert.setDR_CSH(new BigDecimal(0));
                            balCert.setDR_CLG(new BigDecimal(drAmt));
                            balCert.setDR_TRF(new BigDecimal(0));
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            incomeExpenditurePojos.add(balCert);
                        }
                    }
                }
            }
            return incomeExpenditurePojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************************************** End
     * **************************************
     */
    /**
     * ********************* INCOME EXPENDITURE SUB TOTAL REPORT **************
     */
    /**
     *
     * @param date
     * @param brnCode
     * @param fromTime
     * @param toTime
     * @param timeAllowed
     * @return
     */
    @Override
    public List<IncomeExpenditureStTimePojo> incomeExpenditureStTime(String date, String brnCode, String fromTime, String toTime, boolean timeAllowed) throws ApplicationException {
        List<IncomeExpenditureStTimePojo> incomeExpenditureStTimePojos = new ArrayList<IncomeExpenditureStTimePojo>();
        try {
            List result = new ArrayList();
            if (timeAllowed) {
                result = em.createNativeQuery("select a.acno,b.acname,a.cramt,a.dramt,a.dt,ifnull(a.details,'') details ,c.bankname,c.bankaddress from "
                        + "gl_recon a,gltable b,bnkadd c,branchmaster d where  a.dt = '" + date + "' and a.acno=b.acno and a.trantime between '" + fromTime + "' "
                        + "and '" + toTime + "' and substring(a.acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + " ' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' "
                        + "and a.auth='y' and a.trantype <> 5 and a.org_brnid='" + brnCode + "' and d.brncode = "
                        + Integer.parseInt(brnCode) + " "
                        + "and d.alphacode = c.alphacode order by a.acno").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,b.acname,a.cramt,a.dramt,a.dt,ifnull(a.details,'') details ,c.bankname,c.bankaddress from "
                        + "gl_recon a,gltable b,bnkadd c,branchmaster d where  a.dt = '" + date + "' and a.acno=b.acno  and substring(a.acno,3,8) "
                        + "between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + " ' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "'  and a.auth='Y' and a.trantype <> 5 "
                        + "and a.org_brnid='" + brnCode + "' and   d.brncode = "
                        + Integer.parseInt(brnCode) + " and d.alphacode = c.alphacode "
                        + "order by a.acno").getResultList();
            }
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Vector record = (Vector) result.get(i);
                    IncomeExpenditureStTimePojo balCert = new IncomeExpenditureStTimePojo();
                    balCert.setAcno(record.get(0).toString());
                    balCert.setAcname(record.get(1).toString());
                    balCert.setCramt(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                    balCert.setDramt(new BigDecimal(Double.parseDouble(record.get(3).toString())));
                    balCert.setDt(ymd_1.parse(record.get(4).toString()));
                    balCert.setDetails(record.get(5).toString());
                    balCert.setBankname(record.get(6).toString());
                    balCert.setBankaddress(record.get(7).toString());
                    incomeExpenditureStTimePojos.add(balCert);
                }
            }
            return incomeExpenditureStTimePojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ******************************* End
     * *************************************
     */
    /**
     * ****************************************** SUB DAILY REPORT
     * ******************************
     */
    /**
     *
     * @param brnCode
     * @return
     */
    @Override
    public List<SubDailyReportPojo> subDailyReport(String brnCode, String date, String optType, String optDType) throws ApplicationException {
        List temList, mainList;
        Vector tempVector, mainVector;
        String acccode, acctnature, acctDesc;
        List<SubDailyReportPojo> subDailyReportPojos = new ArrayList<SubDailyReportPojo>();
        try {

            String bnkCode = fts.getBankCode();
            String branch = "";
            if (bnkCode.equalsIgnoreCase("rcbl")) {
                branch = "and (r.org_brnid='" + brnCode + "' or r.dest_brnid = '" + brnCode + "')";
            } else {
                branch = "and r.org_brnid = '" + brnCode + "'";
            }
            temList = em.createNativeQuery("select distinct acctcode,acctNature,acctdesc from accounttypemaster").getResultList();
            if (!temList.isEmpty()) {
                for (int i = 0; i < temList.size(); i++) {
                    tempVector = (Vector) temList.get(i);
                    acccode = tempVector.get(0).toString();
                    acctnature = tempVector.get(1).toString();
                    acctDesc = tempVector.get(2).toString();
                    if (optDType.equalsIgnoreCase("Y")) {
                        if (!acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) && !acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            mainList = em.createNativeQuery("select r.acno,dt,custname,cramt,dramt,trantype,auth,ty,iy,r.EnterBy,r.authby,ifnull(instno,'') from " + CbsUtil.getReconTableName(acctnature) + " r,accountmaster a "
                                    + "Where r.acno=a.acno " + branch + " and dt='" + date + "' and substring(r.acno,3,2)='" + acccode + "' and auth= 'Y'").getResultList();
                        } else {
                            mainList = em.createNativeQuery("select r.acno,dt,custname,cramt,dramt,trantype,auth,ty,iy,r.EnterBy,r.authby,ifnull(instno,'') from " + CbsUtil.getReconTableName(acctnature) + " r,"
                                    + "td_accountmaster a Where r.acno=a.acno " + branch + " and dt='" + date + "' and substring(r.acno,3,2)='" + acccode + "' "
                                    + "and auth= 'y' and trantype<>27 and closeflag IS NULL ").getResultList();
                        }
                        if (!mainList.isEmpty()) {
                            for (int j = 0; j < mainList.size(); j++) {
                                SubDailyReportPojo balCert = new SubDailyReportPojo();
                                mainVector = (Vector) mainList.get(j);
                                balCert.setAcno(mainVector.get(0).toString());
                                balCert.setActype(acccode);
                                balCert.setAcdesc(acctDesc);
                                balCert.setCustname(mainVector.get(2).toString());
                                balCert.setEnterBy(mainVector.get(9).toString());
                                balCert.setAuthBy(mainVector.get(10).toString());
                                balCert.setChequeNo(mainVector.get(11).toString());
                                if (mainVector.get(5).toString().equalsIgnoreCase("0")) {
                                    if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                                        balCert.setCrcash(new BigDecimal(mainVector.get(3).toString()));
                                        balCert.setCrclg(BigDecimal.ZERO);
                                        balCert.setCrtrans(BigDecimal.ZERO);
                                        balCert.setDrcash(BigDecimal.ZERO);
                                        balCert.setDrclg(BigDecimal.ZERO);
                                        balCert.setDrtrans(BigDecimal.ZERO);
                                    } else {
                                        balCert.setDrcash(new BigDecimal(mainVector.get(4).toString()));
                                        balCert.setCrclg(BigDecimal.ZERO);
                                        balCert.setCrtrans(BigDecimal.ZERO);
                                        balCert.setCrcash(BigDecimal.ZERO);
                                        balCert.setDrclg(BigDecimal.ZERO);
                                        balCert.setDrtrans(BigDecimal.ZERO);
                                    }

                                } else if (mainVector.get(5).toString().equalsIgnoreCase("1")) {
                                    if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                                        balCert.setCrclg(new BigDecimal(mainVector.get(3).toString()));
                                        balCert.setCrcash(BigDecimal.ZERO);
                                        balCert.setCrtrans(BigDecimal.ZERO);
                                        balCert.setDrcash(BigDecimal.ZERO);
                                        balCert.setDrclg(BigDecimal.ZERO);
                                        balCert.setDrtrans(BigDecimal.ZERO);
                                    } else {
                                        balCert.setDrclg(new BigDecimal(mainVector.get(4).toString()));
                                        balCert.setCrclg(BigDecimal.ZERO);
                                        balCert.setCrtrans(BigDecimal.ZERO);
                                        balCert.setCrcash(BigDecimal.ZERO);
                                        balCert.setDrcash(BigDecimal.ZERO);
                                        balCert.setDrtrans(BigDecimal.ZERO);
                                    }
                                } else if (mainVector.get(5).toString().equalsIgnoreCase("2") || mainVector.get(5).toString().equalsIgnoreCase("8")) {
                                    if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                                        balCert.setCrtrans(new BigDecimal(mainVector.get(3).toString()));
                                        balCert.setCrclg(BigDecimal.ZERO);
                                        balCert.setCrcash(BigDecimal.ZERO);
                                        balCert.setDrcash(BigDecimal.ZERO);
                                        balCert.setDrclg(BigDecimal.ZERO);
                                        balCert.setDrtrans(BigDecimal.ZERO);
                                    } else {
                                        balCert.setDrtrans(new BigDecimal(mainVector.get(4).toString()));
                                        balCert.setCrcash(BigDecimal.ZERO);
                                        balCert.setCrclg(BigDecimal.ZERO);
                                        balCert.setCrtrans(BigDecimal.ZERO);
                                        balCert.setDrcash(BigDecimal.ZERO);
                                        balCert.setDrclg(BigDecimal.ZERO);
                                    }
                                }
                                subDailyReportPojos.add(balCert);
                            }
                            Collections.sort(subDailyReportPojos);
                        }
                    } else {
                        if (!acctnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            if (!acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) && !acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                mainList = em.createNativeQuery("select r.acno,dt,custname,cramt,dramt,trantype,auth,ty,iy,r.EnterBy,r.authby,ifnull(instno,'') from " + CbsUtil.getReconTableName(acctnature) + " r,"
                                        + "accountmaster a Where r.acno=a.acno " + branch + " and dt='" + date + "' and substring(r.acno,3,2)='" + acccode + "' "
                                        + "and auth= 'Y'").getResultList();
                            } else {
                                mainList = em.createNativeQuery("select r.acno,dt,custname,cramt,dramt,trantype,auth,ty,iy,r.EnterBy,r.authby,ifnull(instno,'') from " + CbsUtil.getReconTableName(acctnature) + " r,"
                                        + "td_accountmaster a Where r.acno=a.acno " + branch + " and dt='" + date + "' and substring(r.acno,3,2)='" + acccode + "' "
                                        + "and auth= 'Y' AND trantype<>27 and CloseFlag IS NULL ").getResultList();
                            }
                            if (!mainList.isEmpty()) {
                                for (int j = 0; j < mainList.size(); j++) {
                                    SubDailyReportPojo balCert = new SubDailyReportPojo();
                                    mainVector = (Vector) mainList.get(j);
                                    balCert.setAcno(mainVector.get(0).toString());
                                    balCert.setActype(acccode);
                                    balCert.setAcdesc(acctDesc);
                                    balCert.setCustname(mainVector.get(2).toString());
                                    balCert.setEnterBy(mainVector.get(9).toString());
                                    balCert.setAuthBy(mainVector.get(10).toString());
                                    balCert.setChequeNo(mainVector.get(11).toString());
                                    if (mainVector.get(5).toString().equalsIgnoreCase("0")) {
                                        if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                                            balCert.setCrcash(new BigDecimal(mainVector.get(3).toString()));
                                            balCert.setCrclg(BigDecimal.ZERO);
                                            balCert.setCrtrans(BigDecimal.ZERO);
                                            balCert.setDrcash(BigDecimal.ZERO);
                                            balCert.setDrclg(BigDecimal.ZERO);
                                            balCert.setDrtrans(BigDecimal.ZERO);
                                        } else {
                                            balCert.setDrcash(new BigDecimal(mainVector.get(4).toString()));
                                            balCert.setCrclg(BigDecimal.ZERO);
                                            balCert.setCrtrans(BigDecimal.ZERO);
                                            balCert.setCrcash(BigDecimal.ZERO);
                                            balCert.setDrclg(BigDecimal.ZERO);
                                            balCert.setDrtrans(BigDecimal.ZERO);
                                        }

                                    } else if (mainVector.get(5).toString().equalsIgnoreCase("1")) {
                                        if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                                            balCert.setCrclg(new BigDecimal(mainVector.get(3).toString()));
                                            balCert.setCrcash(BigDecimal.ZERO);
                                            balCert.setCrtrans(BigDecimal.ZERO);
                                            balCert.setDrcash(BigDecimal.ZERO);
                                            balCert.setDrclg(BigDecimal.ZERO);
                                            balCert.setDrtrans(BigDecimal.ZERO);
                                        } else {
                                            balCert.setDrclg(new BigDecimal(mainVector.get(4).toString()));
                                            balCert.setCrclg(BigDecimal.ZERO);
                                            balCert.setCrtrans(BigDecimal.ZERO);
                                            balCert.setCrcash(BigDecimal.ZERO);
                                            balCert.setDrcash(BigDecimal.ZERO);
                                            balCert.setDrtrans(BigDecimal.ZERO);
                                        }
                                    } else if (mainVector.get(5).toString().equalsIgnoreCase("2") || mainVector.get(5).toString().equalsIgnoreCase("8")) {
                                        if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                                            balCert.setCrtrans(new BigDecimal(mainVector.get(3).toString()));
                                            balCert.setCrclg(BigDecimal.ZERO);
                                            balCert.setCrcash(BigDecimal.ZERO);
                                            balCert.setDrcash(BigDecimal.ZERO);
                                            balCert.setDrclg(BigDecimal.ZERO);
                                            balCert.setDrtrans(BigDecimal.ZERO);
                                        } else {
                                            balCert.setDrtrans(new BigDecimal(mainVector.get(4).toString()));
                                            balCert.setCrcash(BigDecimal.ZERO);
                                            balCert.setCrclg(BigDecimal.ZERO);
                                            balCert.setCrtrans(BigDecimal.ZERO);
                                            balCert.setDrcash(BigDecimal.ZERO);
                                            balCert.setDrclg(BigDecimal.ZERO);
                                        }
                                    }
                                    subDailyReportPojos.add(balCert);
                                }
                                Collections.sort(subDailyReportPojos);
                            }
                        }
                    }
                }
            }
            if (optType.equalsIgnoreCase("Y")) {
//                mainList = em.createNativeQuery("select gl_recon.acno,dt,acname,cramt,dramt,trantype,auth,ty,iy from gl_recon ,gltable am  where gl_recon.acno=am.acno "
//                        + "and substring(gl_recon.acno,1,2) = '" + brnCode + "' and dt='" + date + "' and auth='Y'  and trantype<>5 "
//                        + "and substring(gl_recon.acno,3,10) not in (select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%') "
//                        + "order by gl_recon.dt ").getResultList();
                mainList = em.createNativeQuery("select gl_recon.acno,dt,acname,cramt,dramt,trantype,auth,ty,iy,gl_recon.EnterBy,gl_recon.authby,ifnull(gl_recon.instno,'') from gl_recon ,gltable am  "
                        + "where dt='" + date + "' and gl_recon.acno=am.acno and auth='Y'  and trantype<>5 and gl_recon.org_brnid = '" + brnCode + "'"
                        + "and am.acno not in (select distinct acno from gltable where acname like '%CASH IN HAND%') "
                        + "and am.acno not in (select distinct acno from gltable where acname like '%INTERSOLE%') "
                        + "order by gl_recon.dt").getResultList();
            } else {
//                mainList = em.createNativeQuery("select gl_recon.acno,dt,acname,cramt,dramt,trantype,auth,ty,iy from gl_recon ,gltable am  where gl_recon.acno=am.acno "
//                        + "and substring(gl_recon.acno,1,2) = '" + brnCode + "' and substring(gl_recon.acno,5,6) not between '" + SiplConstant.GL_PL_ST.getValue() + "' and '" + SiplConstant.GL_PL_END.getValue() + "' "
//                        + "and dt='" + date + "' and auth='y'  and trantype<>5 "
//                        + "and substring(gl_recon.acno,3,10) not in (select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%') "
//                        + "order by gl_recon.dt ").getResultList();
                mainList = em.createNativeQuery("select gl_recon.acno,dt,acname,cramt,dramt,trantype,auth,ty,iy,gl_recon.EnterBy,gl_recon.authby,ifnull(gl_recon.instno,'') from gl_recon ,gltable am  "
                        + "where dt='" + date + "' and gl_recon.acno=am.acno and auth='y'  and trantype<>5 and gl_recon.org_brnid = '" + brnCode + "'  "
                        + "and am.acno not in (select distinct acno from gltable where acname like '%CASH IN HAND%') "
                        + "and am.acno not in (select distinct acno from gltable where acname like '%INTERSOLE%') "
                        + "and (substring(am.acno,3,8) not between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "'"
                        + "and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "') "
                        + "order by gl_recon.dt").getResultList();
            }
            if (!mainList.isEmpty()) {
                for (int j = 0; j < mainList.size(); j++) {
                    SubDailyReportPojo balCert = new SubDailyReportPojo();
                    mainVector = (Vector) mainList.get(j);
                    balCert.setAcno(mainVector.get(0).toString());
                    balCert.setActype(CbsAcCodeConstant.GL_ACCNO.getAcctCode());
                    balCert.setAcdesc(common.getAcctDecription(CbsAcCodeConstant.GL_ACCNO.getAcctCode()));
                    balCert.setCustname(mainVector.get(2).toString());
                    balCert.setEnterBy(mainVector.get(9).toString());
                    balCert.setAuthBy(mainVector.get(10).toString());
                    balCert.setChequeNo(mainVector.get(11).toString());
                    if (mainVector.get(5).toString().equalsIgnoreCase("0")) {
                        if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                            balCert.setCrcash(new BigDecimal(mainVector.get(3).toString()));
                            balCert.setCrclg(BigDecimal.ZERO);
                            balCert.setCrtrans(BigDecimal.ZERO);
                            balCert.setDrcash(BigDecimal.ZERO);
                            balCert.setDrclg(BigDecimal.ZERO);
                            balCert.setDrtrans(BigDecimal.ZERO);
                        } else {
                            balCert.setDrcash(new BigDecimal(mainVector.get(4).toString()));
                            balCert.setCrclg(BigDecimal.ZERO);
                            balCert.setCrtrans(BigDecimal.ZERO);
                            balCert.setCrcash(BigDecimal.ZERO);
                            balCert.setDrclg(BigDecimal.ZERO);
                            balCert.setDrtrans(BigDecimal.ZERO);
                        }

                    } else if (mainVector.get(5).toString().equalsIgnoreCase("1")) {
                        if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                            balCert.setCrclg(new BigDecimal(mainVector.get(3).toString()));
                            balCert.setCrcash(BigDecimal.ZERO);
                            balCert.setCrtrans(BigDecimal.ZERO);
                            balCert.setDrcash(BigDecimal.ZERO);
                            balCert.setDrclg(BigDecimal.ZERO);
                            balCert.setDrtrans(BigDecimal.ZERO);
                        } else {
                            balCert.setDrclg(new BigDecimal(mainVector.get(4).toString()));
                            balCert.setCrclg(BigDecimal.ZERO);
                            balCert.setCrtrans(BigDecimal.ZERO);
                            balCert.setCrcash(BigDecimal.ZERO);
                            balCert.setDrcash(BigDecimal.ZERO);
                            balCert.setDrtrans(BigDecimal.ZERO);
                        }
                    } else if (mainVector.get(5).toString().equalsIgnoreCase("2") || mainVector.get(5).toString().equalsIgnoreCase("8")) {
                        if (mainVector.get(7).toString().equalsIgnoreCase("0")) {
                            balCert.setCrtrans(new BigDecimal(mainVector.get(3).toString()));
                            balCert.setCrclg(BigDecimal.ZERO);
                            balCert.setCrcash(BigDecimal.ZERO);
                            balCert.setDrcash(BigDecimal.ZERO);
                            balCert.setDrclg(BigDecimal.ZERO);
                            balCert.setDrtrans(BigDecimal.ZERO);
                        } else {
                            balCert.setDrtrans(new BigDecimal(mainVector.get(4).toString()));
                            balCert.setCrcash(BigDecimal.ZERO);
                            balCert.setCrclg(BigDecimal.ZERO);
                            balCert.setCrtrans(BigDecimal.ZERO);
                            balCert.setDrcash(BigDecimal.ZERO);
                            balCert.setDrclg(BigDecimal.ZERO);
                        }
                    }
                    subDailyReportPojos.add(balCert);
                }
                Collections.sort(subDailyReportPojos);
            }
//            List result = em.createNativeQuery("SELECT substring(a.acno,3,2) as actype, a.*,b.bankname,b.bankaddress FROM lgBal_Temp1 a ,BNKADD b,BranchMaster d where b.alphacode = d.alphacode and d.brncode = cast('" + brnCode + "' as integer)").getResultList();
//            if (result.size() > 0) {
//                for (int i = 0; i < result.size(); i++) {
//                    Vector record = (Vector) result.get(i);
//                    SubDailyReportPojo balCert = new SubDailyReportPojo();
//                    balCert.setActype(record.get(0).toString());
//                    balCert.setAcno(record.get(1).toString());
//                    balCert.setCustname(record.get(2).toString());
//                    balCert.setCrcash(new BigDecimal(nft.format(Double.parseDouble(record.get(3).toString()))));
//                    balCert.setCrtrans(new BigDecimal(nft.format(Double.parseDouble(record.get(4).toString()))));
//                    balCert.setCrclg(new BigDecimal(nft.format(Double.parseDouble(record.get(5).toString()))));
//                    balCert.setDrcash(new BigDecimal(nft.format(Double.parseDouble(record.get(6).toString()))));
//                    balCert.setDrtrans(new BigDecimal(nft.format(Double.parseDouble(record.get(7).toString()))));
//                    balCert.setDrclg(new BigDecimal(nft.format(Double.parseDouble(record.get(8).toString()))));
//                    subDailyReportPojos.add(balCert);
//                }
//            }
            return subDailyReportPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * *********************************** End
     * **********************************
     */
    @Override
    public List<LongBookReportPojo> getLongBookReport(String brCode) throws ApplicationException {
        List<LongBookReportPojo> returnList = new ArrayList<LongBookReportPojo>();
        List tempList = null;
        String acNo = "";
        String custName = "";
        double crCash = 0.0;
        double crTrans = 0.0;
        double crClg = 0.0;
        double drCash = 0.0;
        double drTrans = 0.0;
        double drClg = 0.0;
        String bankName = "";
        String branchAddress = "";
        try {
            List branchNameandAddress = common.getBranchNameandAddress(brCode);
            bankName = branchNameandAddress.get(0).toString();
            branchAddress = branchNameandAddress.get(1).toString();
            tempList = em.createNativeQuery("select all acno, custname, crcash, crtrans, crclg, drcash, drtrans, drclg from lgbal_temp a where a.org_brnid = '" + brCode + "' order by a.acno").getResultList();
            for (int i = 0; i < tempList.size(); i++) {
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    acNo = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    custName = ele.get(1).toString();
                }
                if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                    crCash = Double.parseDouble(ele.get(2).toString());
                }
                if (ele.get(3) != null || !ele.get(3).toString().equalsIgnoreCase("")) {
                    crTrans = Double.parseDouble(ele.get(3).toString());
                }
                if (ele.get(4) != null || !ele.get(4).toString().equalsIgnoreCase("")) {
                    crClg = Double.parseDouble(ele.get(4).toString());
                }
                if (ele.get(5) != null || !ele.get(5).toString().equalsIgnoreCase("")) {
                    drCash = Double.parseDouble(ele.get(5).toString());
                }
                if (ele.get(6) != null || !ele.get(6).toString().equalsIgnoreCase("")) {
                    drTrans = Double.parseDouble(ele.get(6).toString());
                }
                if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                    drClg = Double.parseDouble(ele.get(7).toString());
                }
                LongBookReportPojo pojo = new LongBookReportPojo();
                pojo.setAcNo(acNo);
                pojo.setCustName(custName);
                pojo.setCrCash(crCash);
                pojo.setCrClg(crClg);
                pojo.setCrTrans(crTrans);
                pojo.setDrCash(drCash);
                pojo.setDrClg(drClg);
                pojo.setDrTrans(drTrans);
                pojo.setBranchAddress(branchAddress);
                pojo.setBankName(bankName);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<MonthlyDepositProgressReportPojo> getMonthlyDepositProgressReport(String fDate, String brCode, String repType) throws ApplicationException {
        List<MonthlyDepositProgressReportPojo> returnList = new ArrayList<MonthlyDepositProgressReportPojo>();
        String acType = "";
        String acDesc = "";
        String yearStartDate = "";
        String acNature = "";
        String preMonthEndDate = "";
        int noAcAtBeginOfYear = 0;
        double beginOfYearBalance = 0.0;
        int noAcTargetOfYear = 0;
        double targetBalOfYear = 0.0;
        int targetAcOfMonth = 0;
        double targetBalOfMonth = 0.0;
        int noAcAtpreMonth = 0;
        double preMonthBalance = 0.0;
        int noAcAtCurrentMonth = 0;
        double currentMonthBal = 0.0;
        List tempList;
        List acNoList;
        Vector ele, ele1;
        String Tab1 = "", str_l = "";
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                brCode = "90";
            }
            tempList = em.createNativeQuery("select mindate from yearend where '" + fDate + "' between mindate and maxdate and brncode='" + brCode + "'").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele != null) {
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        yearStartDate = ele.get(0).toString();
                    }
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(ymdFormat.parse(fDate));
                cal.add(Calendar.MONTH, -1);
                String tYear = String.valueOf(cal.get(Calendar.YEAR));
                String tMnth = String.valueOf(cal.get(Calendar.MONTH) + 1);
                String tDays = String.valueOf(cal.getActualMaximum(Calendar.DATE));
                while (tYear.length() < 4) {
                    tYear = "0" + tYear;
                }
                while (tMnth.length() < 2) {
                    tMnth = "0" + tMnth;
                }
                while (tDays.length() < 2) {
                    tDays = "0" + tDays;
                }
                preMonthEndDate = tYear + tMnth + tDays;
                if (repType.equalsIgnoreCase("D")) {
                    acNoList = em.createNativeQuery("select acctnature,acctcode,acctdesc from accounttypemaster where acctnature not in ('" + CbsConstant.PAY_ORDER + "','"
                            + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.NON_PERFORM_AC + "','" + CbsConstant.NON_PERFORM_AC + "') "
                            + "and acctnature IS NOT NULL and acctcode not in ('" + CbsAcCodeConstant.CASH_CREDIT.getAcctCode() + "','" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "') order by acctCode").getResultList();
                } else {
                    acNoList = em.createNativeQuery("select acctnature,acctcode,acctdesc from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') "
                            + "and acctnature IS NOT NULL and crdbflag = 'D' order by acctCode").getResultList();
                }

                for (int i = 0; i < acNoList.size(); i++) {
                    MonthlyDepositProgressReportPojo pojo = new MonthlyDepositProgressReportPojo();
                    beginOfYearBalance = 0;
                    noAcAtBeginOfYear = 0;
                    preMonthBalance = 0;
                    noAcAtpreMonth = 0;
                    currentMonthBal = 0;
                    noAcAtCurrentMonth = 0;
                    ele = (Vector) acNoList.get(i);
                    acNature = ele.get(0).toString();
                    acType = ele.get(1).toString();
                    acDesc = ele.get(2).toString();
                    String tablename = common.getTableName(acNature);
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        Tab1 = "td_accountmaster";
                        str_l = " and trantype<>27 and closeflag is null ";
                    } else {
                        Tab1 = "accountmaster";
                        str_l = "";
                    }
                    if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                        tempList = em.createNativeQuery("select ifnull((sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0))),0)/100000 From " + tablename + " a," + Tab1 + " b  "
                                + "where a.dt <='" + yearStartDate + "' and substring(a.acno,3,2)='" + acType + "' and a.auth='Y' " + str_l + " AND a.acno=b.acno ").getResultList();
                    } else {
                        tempList = em.createNativeQuery("select ifnull((sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0))),0)/100000 From " + tablename + " a," + Tab1 + " b  "
                                + "where a.dt <='" + yearStartDate + "' and substring(a.acno,3,2)='" + acType + "' and a.auth='Y' " + str_l + " AND a.acno=b.acno and b.curbrcode='" + brCode + "'").getResultList();
                    }

                    if (!tempList.isEmpty()) {
                        ele1 = (Vector) tempList.get(0);
                        beginOfYearBalance = Double.parseDouble(ele1.get(0).toString());
                    }
                    if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                        tempList = em.createNativeQuery("select count(acno) from " + Tab1 + " where substring(acno,3,2)='" + acType + "' and openingdt <='" + yearStartDate + "' "
                                + "and (ifnull(ClosingDate,'')='' or ClosingDate >'" + yearStartDate + "')  ").getResultList();
                    } else {
                        tempList = em.createNativeQuery("select count(acno) from " + Tab1 + " where substring(acno,3,2)='" + acType + "' and openingdt <='" + yearStartDate + "' "
                                + "and (ifnull(ClosingDate,'')='' or ClosingDate >'" + yearStartDate + "') and curbrcode='" + brCode + "' ").getResultList();
                    }

                    if (!tempList.isEmpty()) {
                        ele1 = (Vector) tempList.get(0);
                        noAcAtBeginOfYear = Integer.parseInt(ele1.get(0).toString());
                    }
                    if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                        tempList = em.createNativeQuery("select ifnull((sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0))),0)/100000 from " + tablename + " a , " + Tab1 + " b "
                                + "where a.Dt <='" + preMonthEndDate + "' and substring(a.acno,3,2)='" + acType + "' and a.auth='Y' " + str_l + " "
                                + "and a.acno=b.acno").getResultList();
                    } else {
                        tempList = em.createNativeQuery("select ifnull((sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0))),0)/100000 from " + tablename + " a , " + Tab1 + " b "
                                + "where a.Dt <='" + preMonthEndDate + "' and substring(a.acno,3,2)='" + acType + "' and a.auth='Y' " + str_l + " "
                                + "and a.acno=b.acno and b.curbrcode='" + brCode + "'").getResultList();
                    }

                    if (!tempList.isEmpty()) {
                        ele1 = (Vector) tempList.get(0);
                        preMonthBalance = Double.parseDouble(ele1.get(0).toString());
                    }
                    if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                        tempList = em.createNativeQuery("SELECT COUNT(acno) From " + Tab1 + " Where substring(acno,3,2)='" + acType + "' and OpeningDt <='" + preMonthEndDate + "' "
                                + "and (ifnull(ClosingDate,'')='' or ClosingDate >'" + preMonthEndDate + "') ").getResultList();
                    } else {
                        tempList = em.createNativeQuery("SELECT COUNT(acno) From " + Tab1 + " Where substring(acno,3,2)='" + acType + "' and OpeningDt <='" + preMonthEndDate + "' "
                                + "and (ifnull(ClosingDate,'')='' or ClosingDate >'" + preMonthEndDate + "') AND CurBrCode='" + brCode + "'").getResultList();
                    }

                    if (!tempList.isEmpty()) {
                        ele1 = (Vector) tempList.get(0);
                        noAcAtpreMonth = Integer.parseInt(ele1.get(0).toString());
                    }
                    if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                        tempList = em.createNativeQuery("SELECT ifnull((sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0))),0)/100000 From " + tablename + " a, " + Tab1 + " b "
                                + "where a.dt <='" + fDate + "' and substring(a.acno,3,2)='" + acType + "' and a.auth='Y' " + str_l + " "
                                + "AND a.acno=b.acno ").getResultList();
                    } else {
                        tempList = em.createNativeQuery("SELECT ifnull((sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0))),0)/100000 From " + tablename + " a, " + Tab1 + " b "
                                + "where a.dt <='" + fDate + "' and substring(a.acno,3,2)='" + acType + "' and a.auth='Y' " + str_l + " "
                                + "AND a.acno=b.acno and b.curbrcode='" + brCode + "'").getResultList();
                    }
                    if (!tempList.isEmpty()) {
                        ele1 = (Vector) tempList.get(0);
                        currentMonthBal = Double.parseDouble(ele1.get(0).toString());
                    }
                    if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                        tempList = em.createNativeQuery("SELECT COUNT(acno) From " + Tab1 + " Where substring(acno,3,2)='" + acType + "' and OpeningDt <= "
                                + CbsUtil.monthAdd(preMonthEndDate, 1) + " and (ifnull(ClosingDate,'')='' or ClosingDate > '" + CbsUtil.monthAdd(preMonthEndDate, 1)
                                + "') ").getResultList();
                    } else {
                        tempList = em.createNativeQuery("SELECT COUNT(acno) From " + Tab1 + " Where substring(acno,3,2)='" + acType + "' and OpeningDt <= "
                                + CbsUtil.monthAdd(preMonthEndDate, 1) + " and (ifnull(ClosingDate,'')='' or ClosingDate > '" + CbsUtil.monthAdd(preMonthEndDate, 1)
                                + "') AND CurBrCode='" + brCode + "'").getResultList();
                    }

                    if (!tempList.isEmpty()) {
                        ele1 = (Vector) tempList.get(0);
                        noAcAtCurrentMonth = Integer.parseInt(ele1.get(0).toString());
                    }
                    pojo.setAcDesc(acDesc);
                    pojo.setAcType(acType);
                    pojo.setBeginOfYearBalance(new BigDecimal(beginOfYearBalance));
                    pojo.setCurrentMonthBal(new BigDecimal(currentMonthBal));
                    pojo.setNoAcAtBeginOfYear(noAcAtBeginOfYear);
                    pojo.setNoAcAtCurrentMonth(noAcAtCurrentMonth);
                    pojo.setNoAcAtpreMonth(noAcAtpreMonth);
                    pojo.setNoAcTargetOfYear(noAcTargetOfYear);
                    pojo.setPreMonthBalance(new BigDecimal(preMonthBalance));
                    pojo.setTargetAcOfMonth(targetAcOfMonth);
                    pojo.setTargetBalOfMonth(new BigDecimal(targetBalOfMonth));
                    pojo.setTargetBalOfYear(new BigDecimal(targetBalOfYear));
                    returnList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List<CbsCashClgTimewisePojo> getCbsCashClgScrollData(String userDate, String scrollType,
            String orgBrnCode, String fromTime, String toTime, String timeAllowed, String orderBy) throws ApplicationException {

        List<CbsCashClgTimewisePojo> reportDataList = new ArrayList<CbsCashClgTimewisePojo>();
        NumberFormat formatter = new DecimalFormat("#.##");
        List dataList = new ArrayList();
        try {
            if (scrollType.equalsIgnoreCase("CASH")) {
                if (timeAllowed.equalsIgnoreCase("Y")) {
                    dataList = em.createNativeQuery("SELECT a.acno,a.custname,a.cramt,a.dramt,b.opamt,c.bankname,c.bankaddress,a.EnterBy,a.authby ,a.recno FROM "
                            + "(SELECT acno,custname,cramt,dramt,dt,ty,recno,TRANTIME,iy,org_brnid,EnterBy,authby FROM tokentable_credit where auth='N'"
                            + "UNION "
                            + "SELECT acno,custname,cramt,dramt,dt,ty,recno,TRANTIME, iy,org_brnid,EnterBy,authby FROM tokentable_debit "
                            + "UNION "
                            + "SELECT acno,custname,cramt,dramt,dt,ty,recno,TRANTIME, iy,org_brnid,EnterBy,authby FROM recon_cash_d WHERE ty=0) a , "
                            + "opcash b ,bnkadd c, branchmaster d WHERE  a.dt='" + userDate + "' and a.acno not in (select distinct acno "
                            + "from gltable where acname like '%CASH IN HAND%') and a.acno not in "
                            + "(select distinct acno from gltable where acname like '%INTERSOLE%') "
                            + "AND b.tdate = (select max(tdate) from opcash wherE tdate < now() "
                            + "and brncode = '" + orgBrnCode + "') AND a.org_brnid = '" + orgBrnCode + "' and d.alphacode = c.alphacode  "
                            + "and d.brncode =" + Integer.parseInt(orgBrnCode) + " and d.brncode = b.brncode "
                            + "AND a.trantime between '" + fromTime + "' AND '" + toTime + "'  order by " + orderBy).getResultList();
                } else if (timeAllowed.equalsIgnoreCase("N")) {
                    dataList = em.createNativeQuery("Select a.acno,a.custname,a.cramt,a.dramt,b.opamt,c.bankname,c.bankaddress,a.EnterBy,a.authby ,a.recno from "
                            + "(SELECT acno,custname,cramt,dramt,dt,ty,recno,TRANTIME,iy,org_brnid,EnterBy,authby FROM tokentable_credit where auth='N'"
                            + "UNION "
                            + "SELECT acno,custname,cramt,dramt,dt,ty,recno,TRANTIME, iy,org_brnid,EnterBy,authby FROM tokentable_debit "
                            + "UNION "
                            + "SELECT acno,custname,cramt,dramt,dt,ty,recno,TRANTIME, iy,org_brnid,EnterBy,authby FROM recon_cash_d WHERE ty=0) a , "
                            + "opcash b ,bnkadd c,"
                            + "branchmaster d where  a.dt='" + userDate + "' and a.acno not in (select distinct acno "
                            + "from gltable where acname like '%CASH IN HAND%') and a.acno not in "
                            + "(select distinct acno from gltable where acname like '%INTERSOLE%') "
                            + "And b.tdate = (select max(tdate) from opcash where tdate < now() "
                            + "and brncode = '" + orgBrnCode + "') AND a.org_brnid = '" + orgBrnCode + "' and d.alphacode = c.alphacode  "
                            + "and d.brncode = " + Integer.parseInt(orgBrnCode) + " and d.brncode=b.brncode "
                            + " ORDER BY " + orderBy).getResultList();
                }

                if (dataList.size() > 0) {
                    for (int i = 0; i < dataList.size(); i++) {
                        CbsCashClgTimewisePojo cashClgObject = new CbsCashClgTimewisePojo();
                        Vector element1 = (Vector) dataList.get(i);
                        cashClgObject.setAccNo((String) element1.get(0));
                        cashClgObject.setCustName((String) element1.get(1));
                        cashClgObject.setCrAmt(new BigDecimal(formatter.format(element1.get(2))));
                        cashClgObject.setDrAmt(new BigDecimal(formatter.format(element1.get(3))));
                        cashClgObject.setOpAmt(new BigDecimal(formatter.format(element1.get(4))));
                        cashClgObject.setBankName((String) element1.get(5));
                        cashClgObject.setBankAddress((String) element1.get(6));
                        cashClgObject.setEnterBy((String) element1.get(7));
                        cashClgObject.setAuthBy((String) element1.get(8));
                        cashClgObject.setRecno(Double.parseDouble(element1.get(9).toString()));
                        reportDataList.add(cashClgObject);
                    }
                }
            }
            if (scrollType.equalsIgnoreCase("CLG")) {
                if (timeAllowed.equalsIgnoreCase("Y")) {
                    dataList = em.createNativeQuery("SELECT a.acno,a.custname,a.cramt,a.dramt,b.bankname,"
                            + "b.bankaddress,a.enterBy,a.authBy,a.recno  FROM recon_clg_d a,bnkadd b , branchmaster c "
                            + "WHERE b.alphacode = c.alphacode AND c.brncode = " + Integer.parseInt(orgBrnCode) + " "
                            + "AND a.dt='" + userDate + "' AND a.trantype=1 AND a.auth='Y'  AND a.org_brnid = '" + orgBrnCode + "' "
                            + "AND a.ACNO NOT IN (select distinct acno from gltable where acname like '%INTERSOLE%') "
                            + "AND a.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY " + orderBy).getResultList();
                } else if (timeAllowed.equalsIgnoreCase("N")) {
                    dataList = em.createNativeQuery("SELECT a.acno,a.custname,a.cramt,a.dramt,b.bankname,"
                            + "b.bankaddress,a.enterBy,a.authBy,a.recno  from recon_clg_d a,bnkadd b , branchmaster c "
                            + "WHERE b.alphacode = c.alphacode AND c.brncode = " + Integer.parseInt(orgBrnCode) + " "
                            + "AND a.dt='" + userDate + "' AND a.trantype=1 AND a.auth='Y'  AND a.org_brnid = '" + orgBrnCode + "' "
                            + "AND a.ACNO NOT IN (select distinct acno from gltable where acname like '%INTERSOLE%') "
                            + "ORDER BY " + orderBy).getResultList();
                }

                if (dataList.size() > 0) {
                    for (int i = 0; i < dataList.size(); i++) {
                        CbsCashClgTimewisePojo cashClgObject = new CbsCashClgTimewisePojo();
                        Vector element1 = (Vector) dataList.get(i);
                        cashClgObject.setAccNo((String) element1.get(0));
                        cashClgObject.setCustName((String) element1.get(1));
                        cashClgObject.setCrAmt(new BigDecimal(formatter.format(element1.get(2))));
                        cashClgObject.setDrAmt(new BigDecimal(formatter.format(element1.get(3))));
                        cashClgObject.setBankName((String) element1.get(4));
                        cashClgObject.setBankAddress((String) element1.get(5));
                        cashClgObject.setEnterBy(element1.get(6).toString());
                        cashClgObject.setAuthBy(element1.get(7).toString());
                        cashClgObject.setRecno(Double.parseDouble(element1.get(8).toString()));
                        reportDataList.add(cashClgObject);
                    }
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return reportDataList;
    }

    /**
     * *
     */
    public List<CbsCashTrfClgScrollTimewisePojo> getCbsCashTrfClgScrollData(String userDate, String scrollType,
            String orgBrnCode, String fromTime, String toTime, String timeAllowed, String orderby, String optRep) throws ApplicationException {

        List<CbsCashTrfClgScrollTimewisePojo> reportDataList = new ArrayList<CbsCashTrfClgScrollTimewisePojo>();
        NumberFormat formatter = new DecimalFormat("#.##");
        String bankName = "", bankAddress = "";
        Long totalCrVch = 0L, totalDrVch = 0L;
        String orderBy = "";
        if (orderby.equalsIgnoreCase("recno")) {
            orderBy = " order by d.recno ";
        } else if (orderby.equalsIgnoreCase("a.acno")) {
            orderBy = " order by d.acno";
        } else if (orderby.equalsIgnoreCase("trantime")) {
            orderBy = " order by trsno";
        }

        List Bank_type_list = fts.getSmsDetails();
        Vector vec1 = (Vector) Bank_type_list.get(0);
        String Bank_type = vec1.get(0).toString();

        String repOption = "";
        if (optRep.equalsIgnoreCase("SG")) {
            if (Bank_type.equalsIgnoreCase("CCBL")) {
                repOption = " and (d.authby='system' or d.enterby='system' or d.trandesc='66' )";
            } else {
                repOption = " and (d.authby='system' or d.enterby='system' )";
            }
        } else if (optRep.equalsIgnoreCase("OG")) {
            if (Bank_type.equalsIgnoreCase("CCBL")) {
                repOption = " and (d.authby <> 'system' and d.enterby <>'system' and d.trandesc <> '66')";
            } else {
                repOption = " and (d.authby <> 'system' and d.enterby <>'system')";
            }
        }

        try {
            if (scrollType.equalsIgnoreCase("CASH")) {
                if (timeAllowed.equalsIgnoreCase("N")) {
                    List dataList1 = em.createNativeQuery("SELECT d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from td_recon d,td_accountmaster a WHERE SUBSTRING(d.Acno,3,2)=a.Accttype "
                            + "AND d.Acno=a.acno AND dt='" + userDate + "' AND trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList1) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList2 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "of_recon d,td_accountmaster a where substring(d.tdAcno,3,2)=a.Accttype and "
                            + "d.tdAcno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList2) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList3 = em.createNativeQuery("select d.acno,a.acname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from gl_recon d,gltable a where d.Acno=a.acno and dt='" + userDate + "' and trantype=0 "
                            + "AND d.TranDesc <> 999 AND d.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable "
                            + "WHERE ACNAME LIKE '%CASH IN HAND%') AND d.ACNO NOT IN "
                            + "(SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') "
                            + "AND d.org_brnid = '" + orgBrnCode + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList3) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList4 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from ca_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList4) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList5 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "ddstransaction d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND IY NOT IN (9999) "
                            + "AND d.org_brnid = '" + orgBrnCode + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList5) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList6 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "rdrecon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList6) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList7 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "'  "
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList7) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList8 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "loan_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList8) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList9 = em.createNativeQuery("select opamt from opcash where "
                            + "tdate = " + CbsUtil.dateAdd(userDate, -1) + " AND BRNCODE = '" + orgBrnCode + "'").getResultList();

                    if (dataList9.size() > 0) {
                        Vector element2 = (Vector) dataList9.get(0);

                        for (CbsCashTrfClgScrollTimewisePojo updatePojo : reportDataList) {
                            updatePojo.setLastBal(new BigDecimal(formatter.format(element2.get(0))));
                        }
                    } //Else block put By Ankit        
                    else {
                        List resultList = em.createNativeQuery("select dayBeginFlag from bankdays where date=" + CbsUtil.dateAdd(userDate, -1) + " and brncode='" + orgBrnCode + "'").getResultList();
                        if (!resultList.isEmpty()) {
                            Vector vec = (Vector) resultList.get(0);
                            String holidayFlag = vec.get(0).toString();
                            String date = userDate;
                            int dayDecreaseFlag = 2;
                            while (!holidayFlag.equalsIgnoreCase("Y")) {
                                resultList = em.createNativeQuery("select dayBeginFlag,date from bankdays where date=" + CbsUtil.dateAdd(userDate, -dayDecreaseFlag) + " and brncode='" + orgBrnCode + "'").getResultList();
                                vec = (Vector) resultList.get(0);
                                holidayFlag = vec.get(0).toString();
                                date = vec.get(1).toString();
                                ++dayDecreaseFlag;
                            }
                            dataList9 = em.createNativeQuery("SELECT opamt FROM opcash WHERE "
                                    + "tdate ='" + date + "'  AND BRNCODE = '" + orgBrnCode + "'").getResultList();
                            if (dataList9.size() > 0) {
                                Vector element2 = (Vector) dataList9.get(0);

                                for (CbsCashTrfClgScrollTimewisePojo updatePojo : reportDataList) {
                                    updatePojo.setLastBal(new BigDecimal(formatter.format(element2.get(0))));
                                }
                            }

                        }
                    }
                    if (orderby.equalsIgnoreCase("recno")) {
                        Collections.sort(reportDataList, new OrderByRecnoComparator());
                    } else if (orderby.equalsIgnoreCase("a.acno")) {
                        Collections.sort(reportDataList, new AcNoByComparator());
                    }
                } else if (timeAllowed.equalsIgnoreCase("Y")) {
                    List dataList1 = em.createNativeQuery("SELECT d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "FROM td_recon d,td_accountmaster a WHERE SUBSTRING(d.Acno,3,2)=a.Accttype "
                            + "AND d.Acno=a.acno AND dt='" + userDate + "' AND trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList1) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList2 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "of_recon d,td_accountmaster a where substring(d.tdAcno,3,2)=a.Accttype and "
                            + "d.tdAcno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList2) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList3 = em.createNativeQuery("select d.acno,a.acname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from gl_recon d,gltable a where d.Acno=a.acno and dt='" + userDate + "' and trantype=0 "
                            + "AND d.TranDesc <> 999 AND d.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable "
                            + "WHERE ACNAME LIKE '%CASH IN HAND%') AND d.ACNO NOT IN "
                            + "(SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList3) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList4 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from ca_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList4) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList5 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "ddstransaction d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND IY NOT IN (9999) "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + " ").getResultList();
                    for (Object object1 : dataList5) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList6 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "rdrecon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList6) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList7 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "'  "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList7) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList8 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "loan_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=0 AND d.org_brnid = '" + orgBrnCode + "' "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'  " + orderBy + " ").getResultList();
                    for (Object object1 : dataList8) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }

                    List dataList9 = em.createNativeQuery("SELECT opamt FROM opcash WHERE "
                            + "tdate =  " + CbsUtil.dateAdd(userDate, -1) + " AND BRNCODE = '" + orgBrnCode + "'").getResultList();
                    if (dataList9.size() > 0) {
                        Vector element2 = (Vector) dataList9.get(0);

                        for (CbsCashTrfClgScrollTimewisePojo updatePojo : reportDataList) {
                            updatePojo.setLastBal(new BigDecimal(formatter.format(element2.get(0))));
                        }
                    }
                    if (orderby.equalsIgnoreCase("recno")) {
                        Collections.sort(reportDataList, new OrderByRecnoComparator());
                    } else if (orderby.equalsIgnoreCase("a.acno")) {
                        Collections.sort(reportDataList, new AcNoByComparator());
                    }
                }

            } else if (scrollType.equalsIgnoreCase("CLG")) {
                if (timeAllowed.equalsIgnoreCase("N")) {
                    List dataList1 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "td_recon d,td_accountmaster a where d.Acno=a.acno and dt='" + userDate + "' "
                            + "and trantype =1 and auth='Y' and closeflag is null AND d.org_brnid = '" + orgBrnCode + "' "
                            + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList1) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
//                    List dataList2 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy "
//                            + "from td_recon d,td_accountmaster a where substring(d.Acno,3,2)=a.Accttype "
//                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
//                            + "AND d.org_brnid = '" + orgBrnCode + "' order by d.acno").getResultList();
//                    for (Object object1 : dataList2) {
//                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
//                        Vector element1 = (Vector) object1;
//                        pojoObject1.setAccNo((String) element1.get(0));
//                        pojoObject1.setAccName((String) element1.get(1));
//                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
//                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
//                        pojoObject1.setEnterBy(element1.get(4).toString());
//                        pojoObject1.setAuthBy(element1.get(5).toString());
//                        reportDataList.add(pojoObject1);
//                    }
                    List dataList3 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "of_recon d,td_accountmaster a where substring(d.tdAcno,3,2)=a.Accttype "
                            + "and d.tdAcno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + orderBy + "").getResultList();
                    for (Object object1 : dataList3) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList4 = em.createNativeQuery("select d.acno,a.acname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "gl_recon d,gltable a where d.Acno=a.acno and dt='" + userDate + "' and trantype=1 "
                            + "and auth='Y' AND d.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable "
                            + "WHERE ACNAME LIKE '%CASH IN HAND%') AND d.ACNO NOT IN "
                            + "(SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TranDesc <> 999 " + orderBy + "").getResultList();
                    for (Object object1 : dataList4) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList5 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "rdrecon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + orderBy + "").getResultList();
                    for (Object object1 : dataList5) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList6 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' AND "
                            + "d.org_brnid = '" + orgBrnCode + "' " + orderBy + "").getResultList();
                    for (Object object1 : dataList6) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList7 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from loan_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + orderBy + "").getResultList();
                    for (Object object1 : dataList7) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList8 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "ca_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + orderBy + "").getResultList();
                    for (Object object1 : dataList8) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList9 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "ddstransaction d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + orderBy + "").getResultList();
                    for (Object object1 : dataList9) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    if (orderby.equalsIgnoreCase("recno")) {
                        Collections.sort(reportDataList, new OrderByRecnoComparator());
                    } else if (orderby.equalsIgnoreCase("a.acno")) {
                        Collections.sort(reportDataList, new AcNoByComparator());
                    }
                } else if (timeAllowed.equalsIgnoreCase("Y")) {
                    List dataList1 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "td_recon d,td_accountmaster a where d.Acno=a.acno and dt='" + userDate + "' "
                            + "and trantype =1 and auth='Y' and closeflag is null AND d.org_brnid = '" + orgBrnCode + "' "
                            + "AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList1) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
//                    List dataList2 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy "
//                            + "from td_recon d,td_accountmaster a where substring(d.Acno,3,2)=a.Accttype "
//                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
//                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
//                            + "order by d.acno").getResultList();
//                    for (Object object1 : dataList2) {
//                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
//                        Vector element1 = (Vector) object1;
//                        pojoObject1.setAccNo((String) element1.get(0));
//                        pojoObject1.setAccName((String) element1.get(1));
//                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
//                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
//                        pojoObject1.setEnterBy(element1.get(4).toString());
//                        pojoObject1.setAuthBy(element1.get(5).toString());
//                        reportDataList.add(pojoObject1);
//                    }
                    List dataList3 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "of_recon d,td_accountmaster a where substring(d.tdAcno,3,2)=a.Accttype "
                            + "and d.tdAcno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + "").getResultList();
                    for (Object object1 : dataList3) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList4 = em.createNativeQuery("select d.acno,a.acname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "gl_recon d,gltable a where d.Acno=a.acno and dt='" + userDate + "' and trantype=1 "
                            + "and auth='Y' AND d.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable "
                            + "WHERE ACNAME LIKE '%CASH IN HAND%') AND d.ACNO NOT IN "
                            + "(SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TranDesc <> 999 AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + "").getResultList();
                    for (Object object1 : dataList4) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList5 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "rdrecon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + "").getResultList();
                    for (Object object1 : dataList5) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList6 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' AND "
                            + "d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + "").getResultList();
                    for (Object object1 : dataList6) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList7 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno "
                            + "from loan_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + "").getResultList();
                    for (Object object1 : dataList7) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList8 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "ca_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + orderBy + "").getResultList();
                    for (Object object1 : dataList8) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    List dataList9 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,d.enterBy,d.authBy,d.recno from "
                            + "ddstransaction d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype=1 and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + "order by d.acno").getResultList();
                    for (Object object1 : dataList9) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setEnterBy(element1.get(4).toString());
                        pojoObject1.setAuthBy(element1.get(5).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(6).toString()));
                        reportDataList.add(pojoObject1);
                    }
                    if (orderby.equalsIgnoreCase("recno")) {
                        Collections.sort(reportDataList, new OrderByRecnoComparator());
                    } else if (orderby.equalsIgnoreCase("a.acno")) {
                        Collections.sort(reportDataList, new AcNoByComparator());
                    }
                }

            } else if (scrollType.equalsIgnoreCase("TRF")) {
                if (timeAllowed.equalsIgnoreCase("N")) {
                    List dataList1 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from td_recon d,td_accountmaster a where d.Acno=a.acno and dt='" + userDate + "' and "
                            + "trantype in (2,8) and auth='Y' and closeflag is null "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList1) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList2 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from of_recon d,td_accountmaster a where  substring(d.tdAcno,3,2)=a.Accttype "
                            + "and d.tdAcno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + "  " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList2) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList3 = em.createNativeQuery("select d.acno,a.acname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from gl_recon d,gltable a where  d.acno=a.acno and dt='" + userDate + "' "
                            + "and trantype in (2,8) and auth='Y' AND d.ACNO NOT IN "
                            + "(select distinct acno from gltable where acname like '%CASH IN HAND%') "
                            + "AND d.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable "
                            + "WHERE ACNAME LIKE '%INTERSOLE%') AND d.TranDesc <> 999 "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList3) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList4 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from loan_recon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList4) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList5 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from recon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList5) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList6 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from rdrecon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList6) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList7 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from ddstransaction d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList7) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList8 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from ca_recon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList8) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    if (orderby.equalsIgnoreCase("recno")) {
                        Collections.sort(reportDataList, new OrderByRecnoComparator());
                    } else if (orderby.equalsIgnoreCase("a.acno")) {
                        Collections.sort(reportDataList, new AcNoByComparator());
                    } else if (orderby.equalsIgnoreCase("trantime")) {
                        Collections.sort(reportDataList, new SortedTrfScroll());
                    }
                } else if (timeAllowed.equalsIgnoreCase("Y")) {
                    List dataList1 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from td_recon d,td_accountmaster a where d.Acno=a.acno and dt='" + userDate + "' and "
                            + "trantype in (2,8) and auth='Y' and closeflag is null "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList1) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList2 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from of_recon d,td_accountmaster a where  substring(d.tdAcno,3,2)=a.Accttype "
                            + "and d.tdAcno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList2) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList3 = em.createNativeQuery("select d.acno,a.acname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from gl_recon d,gltable a where  d.acno=a.acno and dt='" + userDate + "' "
                            + "and trantype in (2,8) and auth='Y' AND d.ACNO NOT IN "
                            + "(SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%') "
                            + "AND d.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable "
                            + "WHERE ACNAME LIKE '%INTERSOLE%') AND d.TranDesc <> 999 "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList3) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList4 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from loan_recon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList4) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList5 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from recon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList5) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList6 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from rdrecon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList6) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList7 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from ddstransaction d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList7) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    List dataList8 = em.createNativeQuery("select d.acno,a.custname,dramt,cramt,trsno,d.ty,d.enterBy,d.authBy,d.recno "
                            + "from ca_recon d,accountmaster a where  substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + userDate + "' and trantype in (2,8) and auth='Y' "
                            + "AND d.org_brnid = '" + orgBrnCode + "' AND d.TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "'"
                            + " " + repOption + " " + orderBy + " asc").getResultList();
                    for (Object object1 : dataList8) {
                        CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                        Vector element1 = (Vector) object1;
                        pojoObject1.setAccNo((String) element1.get(0));
                        pojoObject1.setAccName((String) element1.get(1));
                        pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(2))));
                        pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(3))));
                        pojoObject1.setLastBal(new BigDecimal(formatter.format(element1.get(4))));
                        Integer ty = Integer.parseInt(element1.get(5).toString());
                        pojoObject1.setEnterBy(element1.get(6).toString());
                        pojoObject1.setAuthBy(element1.get(7).toString());
                        pojoObject1.setRecno(Double.valueOf(element1.get(8).toString()));
                        if (ty == 0) {
                            totalCrVch = totalCrVch + 1;
                        } else if (ty == 1) {
                            totalDrVch = totalDrVch + 1;
                        }
                        reportDataList.add(pojoObject1);
                    }
                    if (orderby.equalsIgnoreCase("recno")) {
                        Collections.sort(reportDataList, new OrderByRecnoComparator());
                    } else if (orderby.equalsIgnoreCase("a.acno")) {
                        Collections.sort(reportDataList, new AcNoByComparator());
                    } else if (orderby.equalsIgnoreCase("trantime")) {
                        Collections.sort(reportDataList, new SortedTrfScroll());
                    }
                }
            }

            List dataList1 = common.getBranchNameandAddress(orgBrnCode);
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            if (reportDataList.size() > 0) {
                for (CbsCashTrfClgScrollTimewisePojo object : reportDataList) {
                    object.setvTotalCrVch(totalCrVch);
                    object.setvTotalDrVch(totalDrVch);
                    object.setBankName(bankName);
                    object.setBankAddress(bankAddress);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return reportDataList;
    }

    /**
     * *
     */
    public List<DayBookPojo> getDayBookReportData(String userDate, String orgBrnCode, String exCounterInclude) throws ApplicationException {
        List<DayBookPojo> dataList = new ArrayList<DayBookPojo>();
        try {
            String brCodes = "";

            List<DayBookStmPojo> dataListStm;
            DayBookStmPojo pojoStm;
            DayBookPojo dayBookObject;
            NumberFormat formatter = new DecimalFormat("#.##");
            List resultList = new ArrayList();
            double crbal0 = 0, crbal1 = 0, crbal2 = 0, drbal0 = 0, drbal1 = 0, drbal2 = 0, opamt = 0;
            String tmpReconTab;
            String bankName = "", bankAddress = "";
            List dataList1 = new ArrayList();
            String branch1 = "", branch2 = "", branch3 = "";
            if (exCounterInclude.equals("Y")) {
                List exBrCodeLst = em.createNativeQuery("select brnCode from branchmaster where ex_counter = 'Y' and parent_brncode = "
                        + Integer.parseInt(orgBrnCode)).getResultList();
                if (exBrCodeLst.isEmpty()) {
                    throw new ApplicationException("Extension Counter does not exist");
                }
                for (int i = 0; i < exBrCodeLst.size(); i++) {
                    Vector element = (Vector) exBrCodeLst.get(i);
                    if (brCodes.equals("")) {
                        brCodes = "\'" + CbsUtil.lPadding(2, Integer.parseInt(element.get(0).toString())) + "\'";
                    } else {
                        brCodes = brCodes + ",\'" + CbsUtil.lPadding(2, Integer.parseInt(element.get(0).toString())) + "\'";
                    }
                }
                brCodes = brCodes + ",\'" + orgBrnCode + "\'";
            } else {
                brCodes = orgBrnCode;
            }

            if (orgBrnCode.equalsIgnoreCase("0A")) {
                branch1 = "";
                branch2 = "";
                branch3 = "";
            } else {
                branch1 = "AND r.org_brnid in(" + brCodes + ")";
                branch2 = "AND gl_recon.org_brnid in(" + brCodes + ") ";
                branch3 = "AND of_recon.org_brnid in(" + brCodes + ") ";

            }
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                dataList1 = common.getBranchNameandAddress("90");
            } else {
                dataList1 = common.getBranchNameandAddress(orgBrnCode);
            }

            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            if (orgBrnCode.equalsIgnoreCase("0A")) {
                resultList = em.createNativeQuery("SELECT sum(b.opamt) FROM opcash b where b.tdate = (SELECT max(tdate) from opcash where tdate < '" + userDate + "' ) ").getResultList();
            } else {
                resultList = em.createNativeQuery("SELECT sum(b.opamt) FROM opcash b where b.tdate = (SELECT max(tdate) from opcash where tdate < '" + userDate
                        + "' and brncode in(" + brCodes + ")) and b.brncode in(" + brCodes + ")").getResultList();
            }
            if (!resultList.isEmpty()) {
                Vector element = (Vector) resultList.get(0);
                opamt = Double.parseDouble(element.get(0).toString());
            }

            if (orgBrnCode.equalsIgnoreCase("0A")) {
                resultList = em.createNativeQuery("SELECT a.acctnature, d.acctcode,d.glhead ,d.description,"
                        + "d.optstatus,d.groupcode, d.subgroupcode,d.dbactype,d.frglhead,d.toglhead "
                        + "from daybookmast d, accounttypemaster a where a.acctcode = d.ACCTCODE  "
                        + "group by a.acctnature,d.acctcode ORDER BY d.GLHEAD").getResultList();
            } else {
                resultList = em.createNativeQuery("SELECT a.acctnature, d.acctcode,d.glhead ,d.description,"
                        + "d.optstatus,d.groupcode, d.subgroupcode,d.dbactype,d.frglhead,d.toglhead "
                        + "from daybookmast d, accounttypemaster a where a.acctcode = d.ACCTCODE  "
                        + "AND d.Brncode in(" + brCodes + ") group by a.acctnature,d.acctcode ORDER BY d.GLHEAD").getResultList();
            }

            if (!resultList.isEmpty()) {
                for (Object object : resultList) {
                    dataListStm = new ArrayList<DayBookStmPojo>();
                    Vector element = (Vector) object;
                    String acctNature = (String) element.get(0);
                    String tmpAcType = (String) element.get(1);
                    int tmpAcStatus = Integer.parseInt(element.get(4).toString());
                    String tmpDesc = (String) element.get(3);
                    int tmpGrCode = Integer.parseInt(element.get(5) != null ? element.get(5).toString() : "0");
                    int tmpSubGrCode = Integer.parseInt(element.get(6) != null ? element.get(6).toString() : "0");
                    String tmpGlHead = (String) element.get(2);
                    tmpReconTab = common.getTableName(acctNature);
                    if (acctNature.equals(CbsConstant.DEMAND_LOAN) || acctNature.equals(CbsConstant.TERM_LOAN) || acctNature.equals(CbsConstant.DEPOSIT_SC)
                            || acctNature.equals(CbsConstant.NON_PERFORM_AC) || acctNature.equals(CbsConstant.RECURRING_AC)) {
                        crbal0 = 0d;
                        crbal1 = 0d;
                        crbal2 = 0d;
                        drbal0 = 0d;
                        drbal1 = 0d;
                        drbal2 = 0d;

                        List resultList1 = em.createNativeQuery("SELECT sum(ifnull(cramt,0)),sum(ifnull(dramt,0)) ,trantype  from " + tmpReconTab + " r "
                                + "where substring(r.acno,3,2)= '" + tmpAcType + "'and  DT = '" + userDate + "'"
                                + "AND AUTH = 'Y' and  r.trantype <>5 " + branch1 + " "
                                + "group by trantype").getResultList();
                        if (!resultList1.isEmpty()) {
                            for (Object obj : resultList1) {
                                Vector ele = (Vector) obj;
                                pojoStm = new DayBookStmPojo();
                                pojoStm.setCrBal(Double.parseDouble(ele.get(0).toString()));
                                pojoStm.setDrBal(Double.parseDouble(ele.get(1).toString()));
                                pojoStm.setTranType(Integer.parseInt(ele.get(2).toString()));
                                dataListStm.add(pojoStm);
                            }
                        }
                        if (!dataListStm.isEmpty()) {
                            for (DayBookStmPojo stmPojo : dataListStm) {
                                if (stmPojo.getTranType() == 2 || stmPojo.getTranType() == 8) {
                                    crbal2 = crbal2 + stmPojo.getCrBal();
                                    drbal2 = drbal2 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 1) {
                                    crbal1 = crbal1 + stmPojo.getCrBal();
                                    drbal1 = drbal1 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 0) {
                                    crbal0 = crbal0 + stmPojo.getCrBal();
                                    drbal0 = drbal0 + stmPojo.getDrBal();
                                }
                            }
                        }
                    } else if (acctNature.equals(CbsConstant.FIXED_AC)
                            || acctNature.equals(CbsConstant.MS_AC)) {
                        crbal0 = 0d;
                        crbal1 = 0d;
                        crbal2 = 0d;
                        drbal0 = 0d;
                        drbal1 = 0d;
                        drbal2 = 0d;
                        List resultList1 = em.createNativeQuery("SELECT sum(ifnull(cramt,0)),sum(ifnull(dramt,0)) ,trantype  from " + tmpReconTab + " r "
                                + "where substring(r.acno,3,2)= '" + tmpAcType + "' and  DT = '" + userDate + "' "
                                + "AND AUTH = 'Y' and  r.trantype <>5 AND  r.closeflag is null "
                                + "" + branch1 + "group by trantype").getResultList();
                        if (!resultList1.isEmpty()) {
                            for (Object obj : resultList1) {
                                Vector ele = (Vector) obj;
                                pojoStm = new DayBookStmPojo();
                                pojoStm.setCrBal(Double.parseDouble(ele.get(0).toString()));
                                pojoStm.setDrBal(Double.parseDouble(ele.get(1).toString()));
                                pojoStm.setTranType(Integer.parseInt(ele.get(2).toString()));
                                dataListStm.add(pojoStm);
                            }
                        }
                        if (!dataListStm.isEmpty()) {
                            for (DayBookStmPojo stmPojo : dataListStm) {
                                if (stmPojo.getTranType() == 2 || stmPojo.getTranType() == 8) {
                                    crbal2 = crbal2 + stmPojo.getCrBal();
                                    drbal2 = drbal2 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 1) {
                                    crbal1 = crbal1 + stmPojo.getCrBal();
                                    drbal1 = drbal1 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 0) {
                                    crbal0 = crbal0 + stmPojo.getCrBal();
                                    drbal0 = drbal0 + stmPojo.getDrBal();
                                }
                            }
                        }
                    } else if (acctNature.equals(CbsConstant.OF_AC)) {
                        crbal0 = 0d;
                        crbal1 = 0d;
                        crbal2 = 0d;
                        drbal0 = 0d;
                        drbal1 = 0d;
                        drbal2 = 0d;
                        List resultList1 = em.createNativeQuery("select SUM(r.CRAMT),SUM(r.dramt) ,r.trantype "
                                + "from (select sum(ifnull(cramt,0)) cramt, sum(ifnull(dramt,0)) dramt,TRANTYPE "
                                + "from of_recon  where dt ='" + userDate + "' and substring(acno,3,2)= '" + CbsAcCodeConstant.OF_AC.getAcctCode() + "' "
                                + "and trantype <>5 and  auth = 'Y' " + branch3 + " "
                                + "group by trantype "
                                + "Union select sum(ifnull(cramt,0)) , sum(ifnull(dramt,0)),TRANTYPE "
                                + "from gl_recon where dt ='" + userDate + "' and trantype <>5 and auth = 'Y' "
                                + "" + branch2 + " and substring(acno,3,8) in "
                                + "(select glhead from accounttypemaster where acctcode='" + CbsAcCodeConstant.OF_AC.getAcctCode() + "') "
                                + "group by trantype) r group by r.trantype").getResultList();
                        if (!resultList1.isEmpty()) {
                            for (Object obj : resultList1) {
                                Vector ele = (Vector) obj;
                                pojoStm = new DayBookStmPojo();
                                pojoStm.setCrBal(Double.parseDouble(ele.get(0).toString()));
                                pojoStm.setDrBal(Double.parseDouble(ele.get(1).toString()));
                                pojoStm.setTranType(Integer.parseInt(ele.get(2).toString()));
                                dataListStm.add(pojoStm);
                            }
                        }
                        if (!dataListStm.isEmpty()) {
                            for (DayBookStmPojo stmPojo : dataListStm) {
                                if (stmPojo.getTranType() == 2 || stmPojo.getTranType() == 8) {
                                    crbal2 = crbal2 + stmPojo.getCrBal();
                                    drbal2 = drbal2 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 1) {
                                    crbal1 = crbal1 + stmPojo.getCrBal();
                                    drbal1 = drbal1 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 0) {
                                    crbal0 = crbal0 + stmPojo.getCrBal();
                                    drbal0 = drbal0 + stmPojo.getDrBal();
                                }
                            }
                        }

                    } else if (acctNature.equals(CbsConstant.SAVING_AC) || acctNature.equals(CbsConstant.CURRENT_AC)) {
                        crbal0 = 0d;
                        crbal1 = 0d;
                        crbal2 = 0d;
                        drbal0 = 0d;
                        drbal1 = 0d;
                        drbal2 = 0d;
                        List resultList1 = new ArrayList();
                        if (tmpAcStatus == 2) {
                            resultList1 = em.createNativeQuery("select sum(ifnull(cramt,0)) ,sum(ifnull(dramt,0)),"
                                    + "TRANTYPE, optstatus  from " + tmpReconTab + "  r , accountmaster a "
                                    + "where substring(r.acno,3,2)= '" + tmpAcType + "'and  DT = '" + userDate + "' "
                                    + "AND AUTH = 'Y' and r.acno =a.acno and  a.optstatus=2 "
                                    + "" + branch1 + " group by r.TRANTYPE, a.optstatus "
                                    + "ORDER BY OPTSTATUS").getResultList();
                        } else if (tmpAcStatus == 1) {
                            resultList1 = em.createNativeQuery("select sum(ifnull(cramt,0)) ,sum(ifnull(dramt,0)),"
                                    + "TRANTYPE, optstatus  from " + tmpReconTab + "  r , accountmaster a "
                                    + "where substring(r.acno,3,2)= '" + tmpAcType + "'and  DT = '" + userDate + "' "
                                    + "AND AUTH = 'Y' and r.acno =a.acno and  a.optstatus<>2 "
                                    + "" + branch1 + " group by r.TRANTYPE, a.optstatus "
                                    + "ORDER BY OPTSTATUS").getResultList();
                        } else if (tmpAcStatus == 0) {
                            resultList1 = em.createNativeQuery("select sum(ifnull(cramt,0)) ,sum(ifnull(dramt,0)),"
                                    + "TRANTYPE, optstatus  from " + tmpReconTab + "  r , accountmaster a "
                                    + "where substring(r.acno,3,2)= '" + tmpAcType + "'and  DT = '" + userDate + "' "
                                    + "AND AUTH = 'Y' and r.acno =a.acno "
                                    + "" + branch1 + " group by r.TRANTYPE, a.optstatus "
                                    + "ORDER BY OPTSTATUS").getResultList();
                        }
                        if (!resultList1.isEmpty()) {
                            for (Object obj : resultList1) {
                                Vector ele = (Vector) obj;
                                pojoStm = new DayBookStmPojo();
                                pojoStm.setCrBal(Double.parseDouble(ele.get(0).toString()));
                                pojoStm.setDrBal(Double.parseDouble(ele.get(1).toString()));
                                pojoStm.setTranType(Integer.parseInt(ele.get(2).toString()));
                                pojoStm.setOptStatus(Integer.parseInt(ele.get(3).toString()));
                                dataListStm.add(pojoStm);
                            }
                        }
                        if (!dataListStm.isEmpty()) {
                            for (DayBookStmPojo stmPojo : dataListStm) {
                                if (stmPojo.getTranType() == 2 || stmPojo.getTranType() == 8) {
                                    crbal2 = crbal2 + stmPojo.getCrBal();
                                    drbal2 = drbal2 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 1) {
                                    crbal1 = crbal1 + stmPojo.getCrBal();
                                    drbal1 = drbal1 + stmPojo.getDrBal();
                                } else if (stmPojo.getTranType() == 0) {
                                    crbal0 = crbal0 + stmPojo.getCrBal();
                                    drbal0 = drbal0 + stmPojo.getDrBal();
                                }
                            }
                        }

                    }
                    if (crbal0 > 0 || crbal1 > 0 || crbal2 > 0 || drbal0 > 0 || drbal1 > 0 || drbal2 > 0) {
                        dayBookObject = new DayBookPojo();
                        dayBookObject.setAcname(tmpDesc);
                        dayBookObject.setAcctcode(tmpAcType);
                        dayBookObject.setGroupcode(tmpGrCode);
                        dayBookObject.setSubgroupcode(tmpSubGrCode);
                        dayBookObject.setCrbal0(new BigDecimal(formatter.format(crbal0)));
                        dayBookObject.setCrbal1(new BigDecimal(formatter.format(crbal1)));
                        dayBookObject.setCrbal2(new BigDecimal(formatter.format(crbal2)));
                        dayBookObject.setDrbal0(new BigDecimal(formatter.format(drbal0)));
                        dayBookObject.setDrbal1(new BigDecimal(formatter.format(drbal1)));
                        dayBookObject.setDrbal2(new BigDecimal(formatter.format(drbal2)));
                        dayBookObject.setAcnum(tmpGlHead);
                        dayBookObject.setCrdittotal(new BigDecimal(formatter.format(crbal0 + crbal1 + crbal2)));
                        dayBookObject.setDebittotal(new BigDecimal(formatter.format(drbal0 + drbal1 + drbal2)));
                        dayBookObject.setBranchCode(orgBrnCode);
                        dayBookObject.setOpamt(new BigDecimal(formatter.format(opamt)));
                        dayBookObject.setBankname(bankName);
                        dayBookObject.setBankaddress(bankAddress);
                        dataList.add(dayBookObject);
                    }
                }
            }

            if (orgBrnCode.equalsIgnoreCase("0A")) {
                resultList = em.createNativeQuery("SELECT distinct d.acctcode,d.glhead ,d.description ,d.groupcode, "
                        + "d.subgroupcode,d.dbactype,d.frglhead,d.toglhead FROM daybookmast d "
                        + "where d.acctcode='' group by d.frglhead,d.toglhead ").getResultList();
            } else {
                resultList = em.createNativeQuery("SELECT d.acctcode,d.glhead ,d.description ,d.groupcode, "
                        + "d.subgroupcode,d.dbactype,d.frglhead,d.toglhead FROM daybookmast d "
                        + "where d.acctcode='' AND BRNCODE in(" + brCodes + ") group by d.frglhead,d.toglhead  ").getResultList();
            }

            if (!resultList.isEmpty()) {
                for (Object object : resultList) {
                    Vector element = (Vector) object;
                    dataListStm = new ArrayList<DayBookStmPojo>();
                    crbal0 = 0d;
                    crbal1 = 0d;
                    crbal2 = 0d;
                    drbal0 = 0d;
                    drbal1 = 0d;
                    drbal2 = 0d;

                    String tmpAcType = (String) element.get(0);
                    String tmpGlHead = (String) element.get(1);
                    String tmpDesc = (String) element.get(2);
                    int tmpGrCode = Integer.parseInt(element.get(3) != null ? element.get(3).toString() : "0");
                    int tmpSubGrCode = Integer.parseInt(element.get(4) != null ? element.get(4).toString() : "0");
                    String tmpFrGlHead = (String) element.get(6);
                    String tmpToGlHead = (String) element.get(7);
                    List resultList1 = em.createNativeQuery("select sum(ifnull(cramt,0)),sum(ifnull(dramt,0)),trantype "
                            + "from gl_recon,gltable where gl_recon.acno = gltable.acno and dt ='" + userDate + "' "
                            + "and ((substring(gl_recon.acno,3,8) between '" + tmpFrGlHead + "' and '" + tmpToGlHead + "' )) "
                            + "AND trantype <>5 and auth = 'Y' " + branch2 + " "
                            + "and gl_recon.TranDesc <> 999 group by trantype ").getResultList();
                    if (!resultList1.isEmpty()) {
                        for (Object obj : resultList1) {
                            Vector ele = (Vector) obj;
                            pojoStm = new DayBookStmPojo();
                            pojoStm.setCrBal(Double.parseDouble(ele.get(0).toString()));
                            pojoStm.setDrBal(Double.parseDouble(ele.get(1).toString()));
                            pojoStm.setTranType(Integer.parseInt(ele.get(2).toString()));
                            dataListStm.add(pojoStm);
                        }
                    }
                    if (!dataListStm.isEmpty()) {
                        for (DayBookStmPojo stmPojo : dataListStm) {
                            if (stmPojo.getTranType() == 2 || stmPojo.getTranType() == 8) {
                                crbal2 = crbal2 + stmPojo.getCrBal();
                                drbal2 = drbal2 + stmPojo.getDrBal();
                            } else if (stmPojo.getTranType() == 1) {
                                crbal1 = crbal1 + stmPojo.getCrBal();
                                drbal1 = drbal1 + stmPojo.getDrBal();
                            } else if (stmPojo.getTranType() == 0) {
                                crbal0 = crbal0 + stmPojo.getCrBal();
                                drbal0 = drbal0 + stmPojo.getDrBal();
                            }
                        }
                    }
                    if (crbal0 > 0 || crbal1 > 0 || crbal2 > 0 || drbal0 > 0 || drbal1 > 0 || drbal2 > 0) {
                        dayBookObject = new DayBookPojo();
                        dayBookObject.setAcname(tmpDesc);
                        dayBookObject.setAcctcode(tmpAcType);
                        dayBookObject.setGroupcode(tmpGrCode);
                        dayBookObject.setSubgroupcode(tmpSubGrCode);
                        dayBookObject.setCrbal0(new BigDecimal(formatter.format(crbal0)));
                        dayBookObject.setCrbal1(new BigDecimal(formatter.format(crbal1)));
                        dayBookObject.setCrbal2(new BigDecimal(formatter.format(crbal2)));
                        dayBookObject.setDrbal0(new BigDecimal(formatter.format(drbal0)));
                        dayBookObject.setDrbal1(new BigDecimal(formatter.format(drbal1)));
                        dayBookObject.setDrbal2(new BigDecimal(formatter.format(drbal2)));
                        dayBookObject.setAcnum(tmpGlHead);
                        dayBookObject.setCrdittotal(new BigDecimal(formatter.format(crbal0 + crbal1 + crbal2)));
                        dayBookObject.setDebittotal(new BigDecimal(formatter.format(drbal0 + drbal1 + drbal2)));
                        dayBookObject.setBranchCode(orgBrnCode);
                        dayBookObject.setOpamt(new BigDecimal(formatter.format(opamt)));
                        dayBookObject.setBankname(bankName);
                        dayBookObject.setBankaddress(bankAddress);
                        dataList.add(dayBookObject);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }

    public List getAllAccounType() throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select acctcode from accounttypemaster").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }

    public List<CashierCashPojo> getReceivePaymentData(String enterBy, String asOnDt, String type, String brCode) throws ApplicationException {
        List<CashierCashPojo> dataList = new ArrayList<CashierCashPojo>();
        List result = new ArrayList();
        List result2 = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                if (type.equalsIgnoreCase("0")) {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "'  union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "'union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' ").getResultList();
                    }
                } else {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno  and tranType= '0'and ty='" + type + "'union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno  and tranType= '0'and ty='" + type + "' union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' ").getResultList();
                    }
                }
            } else {
                if (type.equalsIgnoreCase("0")) {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "'  union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "'union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' ").getResultList();
                    }
                } else {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "'union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all select  "
                                + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' union all "
                                + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "' ").getResultList();
                    }
                }
            }
            for (Object object1 : result) {
                CashierCashPojo pojo = new CashierCashPojo();
                Vector element1 = (Vector) object1;
                pojo.setAcNo(element1.get(0).toString());
                pojo.setCustName(element1.get(1).toString());
                pojo.setTokenNo(element1.get(2).toString());
                pojo.setAmount(Double.parseDouble(element1.get(3).toString()));
                pojo.setAuthBy(element1.get(4).toString());
                dataList.add(pojo);
            }

            if (brCode.equalsIgnoreCase("0A")) {
                if (type.equalsIgnoreCase("0")) {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result2 = em.createNativeQuery("select  a.acno,a.acname,r.tokenno,r.cramt,r.authby from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.cramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno  and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "'").getResultList();
                    }

                } else {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.dramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno  and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.dramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno  and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "'").getResultList();
                    }
                }
            } else {
                if (type.equalsIgnoreCase("0")) {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.cramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.cramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "'").getResultList();
                    }

                } else {
                    if (enterBy.equalsIgnoreCase("ALL")) {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.dramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "'").getResultList();
                    } else {
                        result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.dramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                                + "and a.acno = r.acno and substring(a.acno,1,2)='" + brCode + "' and tranType= '0'and ty='" + type + "' and enterby = '" + enterBy + "'").getResultList();
                    }
                }
            }

            for (Object object1 : result2) {
                CashierCashPojo pojo = new CashierCashPojo();
                Vector element1 = (Vector) object1;
                pojo.setAcNo(element1.get(0).toString());
                pojo.setCustName(element1.get(1).toString());
                pojo.setTokenNo(element1.get(2).toString());
                pojo.setAmount(Double.parseDouble(element1.get(3).toString()));
                pojo.setAuthBy(element1.get(4).toString());
                dataList.add(pojo);
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;

    }

    public List<TokenBookPojo> getTokenBookData(String tokenPaidBy, String asOnDt, String type, String brCode) throws ApplicationException {
        List<TokenBookPojo> dataList = new ArrayList<TokenBookPojo>();
        List result = new ArrayList();
        List result2 = new ArrayList();
        try {
            if (type.equalsIgnoreCase("0")) {
                if (tokenPaidBy.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "'").getResultList();
                } else {
                    result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.cramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' ").getResultList();
                }
            } else {
                if (tokenPaidBy.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all select  "
                            + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all select  "
                            + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' ").getResultList();
                } else {
                    result = em.createNativeQuery("select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ca_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from loan_recon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from rdrecon r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all select  "
                            + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from ddstransaction r,accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all select  "
                            + "a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from td_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' union all "
                            + "select  a.acno,a.custName,r.tokenNo,r.dramt,r.authBy from of_recon r,td_accountmaster a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "' ").getResultList();
                }
            }

            for (Object object1 : result) {
                TokenBookPojo pojo = new TokenBookPojo();
                Vector element1 = (Vector) object1;
                pojo.setAcNo(element1.get(0).toString());
                pojo.setCustName(element1.get(1).toString());
                pojo.setTokenNo(element1.get(2).toString());
                pojo.setAmount(Double.parseDouble(element1.get(3).toString()));
                pojo.setAuthBy(element1.get(4).toString());
                dataList.add(pojo);
            }

            if (type.equalsIgnoreCase("0")) {
                if (tokenPaidBy.equalsIgnoreCase("ALL")) {
                    result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.cramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "'").getResultList();
                } else {
                    result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.cramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "'").getResultList();
                }
            } else {
                if (tokenPaidBy.equalsIgnoreCase("ALL")) {
                    result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.dramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "'").getResultList();
                } else {
                    result2 = em.createNativeQuery("select  a.acno,a.AcName,r.tokenNo,r.dramt,r.authBy from gl_recon r,gltable a  where r.auth ='Y' and r.dt='" + asOnDt + "' "
                            + "and a.acno = r.acno and substring(r.acno,1,2)='" + brCode + "'and tranType= '0'and ty='" + type + "' and tokenpaidBy = '" + tokenPaidBy + "'").getResultList();
                }
            }

            for (Object object1 : result2) {
                TokenBookPojo pojo = new TokenBookPojo();
                Vector element1 = (Vector) object1;
                pojo.setAcNo(element1.get(0).toString());
                pojo.setCustName(element1.get(1).toString());
                pojo.setTokenNo(element1.get(2).toString());
                pojo.setAmount(Double.parseDouble(element1.get(3).toString()));
                pojo.setAuthBy(element1.get(4).toString());
                dataList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<TransactionCountPojo> getTransactionCountData(String frDate, String toDate, String orgBrnCode, String fromTime, String toTime, String timeAllowed) throws ApplicationException {
        List<TransactionCountPojo> dataList = new ArrayList<TransactionCountPojo>();
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        List result3 = new ArrayList();
        String brCode = "";
        try {

            if (timeAllowed.equalsIgnoreCase("N")) {

                List brList = common.getAlphacodeAllAndBranch(orgBrnCode);
                for (int i = 0; i < brList.size(); i++) {
                    Vector vtr = (Vector) brList.get(i);
                    brCode = vtr.get(1).toString().length() < 2 ? "0" + vtr.get(1).toString() : vtr.get(1).toString();

                    result1 = em.createNativeQuery("select sum(No_of_Transaction ) a from ("
                            + "select count(*) as No_of_Transaction  from recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ca_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from rdrecon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from td_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from loan_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from of_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ddstransaction where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from gl_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 0 "
                            + "and TranDesc <> '999' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%')"
                            + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%'))a").getResultList();

                    for (Object object1 : result1) {
                        TransactionCountPojo pojo = new TransactionCountPojo();
                        Vector element1 = (Vector) object1;
                        pojo.setTransactionMode("Cash");
                        pojo.setBrnCode(brCode);
                        pojo.setAlphaCode(common.getBranchNameByBrncode(brCode));
                        pojo.setNoOfTransaction(Integer.parseInt(element1.get(0).toString()));
                        dataList.add(pojo);
                    }

                    result2 = em.createNativeQuery("select sum(No_of_Transaction ) a from ("
                            + "select count(*) as No_of_Transaction  from recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ca_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from rdrecon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from td_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from loan_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from of_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ddstransaction where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from gl_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType = 1 "
                            + "and TranDesc <> '999' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%')"
                            + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%'))a").getResultList();

                    for (Object object1 : result2) {
                        TransactionCountPojo pojo = new TransactionCountPojo();
                        Vector element1 = (Vector) object1;
                        pojo.setTransactionMode("Clearing");
                        pojo.setBrnCode(brCode);
                        pojo.setAlphaCode(common.getBranchNameByBrncode(brCode));
                        pojo.setNoOfTransaction(Integer.parseInt(element1.get(0).toString()));
                        dataList.add(pojo);
                    }

                    result3 = em.createNativeQuery("select sum(No_of_Transaction ) a from ("
                            + "select count(*) as No_of_Transaction  from recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ca_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from rdrecon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from td_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) and closeflag is null "
                            + "union all "
                            + "select count(*) as No_of_Transaction from loan_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from of_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ddstransaction where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from gl_recon where org_brnid = '" + brCode + "' and dt between '" + frDate + "' and '" + toDate + "' and TranType in(2,8) "
                            + "and TranDesc <> '999' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%')"
                            + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%'))a").getResultList();

                    for (Object object1 : result3) {
                        TransactionCountPojo pojo = new TransactionCountPojo();
                        Vector element1 = (Vector) object1;
                        pojo.setTransactionMode("Transfer");
                        pojo.setBrnCode(brCode);
                        pojo.setAlphaCode(common.getBranchNameByBrncode(brCode));
                        pojo.setNoOfTransaction(Integer.parseInt(element1.get(0).toString()));
                        dataList.add(pojo);
                    }
                }

            } else {
                List brList = common.getAlphacodeAllAndBranch(orgBrnCode);
                for (int i = 0; i < brList.size(); i++) {
                    Vector vtr = (Vector) brList.get(i);
                    brCode = vtr.get(1).toString().length() < 2 ? "0" + vtr.get(1).toString() : vtr.get(1).toString();

                    result1 = em.createNativeQuery("select sum(No_of_Transaction ) a from ("
                            + "select count(*) as No_of_Transaction  from recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ca_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from rdrecon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from td_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from loan_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from of_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ddstransaction where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from gl_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 0 "
                            + "and TranDesc <> '999' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%')"
                            + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%'))a").getResultList();

                    for (Object object1 : result1) {
                        TransactionCountPojo pojo = new TransactionCountPojo();
                        Vector element1 = (Vector) object1;
                        pojo.setTransactionMode("Cash");
                        pojo.setBrnCode(brCode);
                        pojo.setAlphaCode(common.getBranchNameByBrncode(brCode));
                        pojo.setNoOfTransaction(Integer.parseInt(element1.get(0).toString()));
                        dataList.add(pojo);
                    }

                    result2 = em.createNativeQuery("select sum(No_of_Transaction ) a from ("
                            + "select count(*) as No_of_Transaction  from recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ca_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from rdrecon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from td_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from loan_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from of_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ddstransaction where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "union all "
                            + "select count(*) as No_of_Transaction from gl_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType = 1 "
                            + "and TranDesc <> '999' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%')"
                            + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%'))a").getResultList();

                    for (Object object1 : result2) {
                        TransactionCountPojo pojo = new TransactionCountPojo();
                        Vector element1 = (Vector) object1;
                        pojo.setTransactionMode("Clearing");
                        pojo.setBrnCode(brCode);
                        pojo.setAlphaCode(common.getBranchNameByBrncode(brCode));
                        pojo.setNoOfTransaction(Integer.parseInt(element1.get(0).toString()));
                        dataList.add(pojo);
                    }

                    result3 = em.createNativeQuery("select sum(No_of_Transaction ) a from ("
                            + "select count(*) as No_of_Transaction  from recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from ca_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from rdrecon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8) "
                            + "union all "
                            + "select count(*) as No_of_Transaction from td_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8) and closeflag is null "
                            + "union all "
                            + "select count(*) as No_of_Transaction from loan_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8)"
                            + "union all "
                            + "select count(*) as No_of_Transaction from of_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8)"
                            + "union all "
                            + "select count(*) as No_of_Transaction from ddstransaction where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8)"
                            + "union all "
                            + "select count(*) as No_of_Transaction from gl_recon where org_brnid = '" + brCode + "' and trantime between '" + fromTime + "' and '" + toTime + "' and TranType in(2,8) "
                            + "and TranDesc <> '999' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%CASH IN HAND%')"
                            + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%'))a").getResultList();

                    for (Object object1 : result3) {
                        TransactionCountPojo pojo = new TransactionCountPojo();
                        Vector element1 = (Vector) object1;
                        pojo.setTransactionMode("Transfer");
                        pojo.setBrnCode(brCode);
                        pojo.setAlphaCode(common.getBranchNameByBrncode(brCode));
                        pojo.setNoOfTransaction(Integer.parseInt(element1.get(0).toString()));
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }

    private class AcNoByComparator implements Comparator<CbsCashTrfClgScrollTimewisePojo> {

        public int compare(CbsCashTrfClgScrollTimewisePojo tdIntDetailObj1, CbsCashTrfClgScrollTimewisePojo tdIntDetailObj2) {
            return tdIntDetailObj1.getAccNo().compareTo(tdIntDetailObj2.getAccNo());
        }
    }

    private class OrderByRecnoComparator implements Comparator<CbsCashTrfClgScrollTimewisePojo> {

        public int compare(CbsCashTrfClgScrollTimewisePojo tdIntDetailObj1, CbsCashTrfClgScrollTimewisePojo tdIntDetailObj2) {
            return Double.compare(tdIntDetailObj1.getRecno(), tdIntDetailObj2.getRecno());
        }
    }

    @Override
    public List<CbsCashTrfClgScrollTimewisePojo> getMismatchTransferScrollData(String orgBrnCode, String dt, String auth) throws ApplicationException {
        List<CbsCashTrfClgScrollTimewisePojo> dataList = new ArrayList<>();
        NumberFormat formatter = new DecimalFormat("#.##");
        try {
            List result = new ArrayList();
            if (auth.equalsIgnoreCase("Y")) {
                result = em.createNativeQuery("select CrAmt,DrAmt,date_format(a.dt,'%d/%m/%Y'),a.trsno from(\n"
                        + "select ifnull(sum(CreditAmt),0) CrAmt,dt,trsno from (\n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from recon where dt = '" + dt + "' and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all \n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from ca_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from loan_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from rdrecon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from ddstransaction where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from td_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) and closeflag is null group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(cramt),0) CreditAmt,dt,trsno from gl_recon where dt = '" + dt + "' and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6)  and auth='Y' \n"
                        + "AND ACNO NOT IN (select distinct acno from gltable where acname like '%CASH IN HAND%') \n"
                        + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') \n"
                        + "AND TranDesc <> 999 group by dt,trsno\n"
                        + ")a group by dt,trsno)a,\n"
                        + "(\n"
                        + "select ifnull(sum(DebitAmt),0) DrAmt,dt,trsno from (\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from ca_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from loan_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from rdrecon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from ddstransaction where dt = '" + dt + "' and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from td_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6) and closeflag is null group by dt,trsno\n"
                        + "union all\n"
                        + "select ifnull(sum(dramt),0) DebitAmt,dt,trsno from gl_recon where dt = '" + dt + "'and org_brnid = '" + orgBrnCode + "' and trantype in (2,8,6)  and auth='Y'\n"
                        + "AND ACNO NOT IN (select distinct acno from gltable where acname like '%CASH IN HAND%') \n"
                        + "AND ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') \n"
                        + "AND TranDesc <> 999 group by dt,trsno\n"
                        + ")b group by dt,trsno )b where a.dt=b.dt and a.trsno = b.trsno and CrAmt <> DrAmt order by 4").getResultList();
            } else {
//                result = em.createNativeQuery("select a.acno,a.custname,a.dramt,a.cramt,a.trsno,a.ty,a.enterBy,a.authBy,a.recno "
//                        + "from recon_trf_d a,(select trsno,recno,acno,custname,cramt,dramt,enterby,authby,dt from recon_trf_d "
//                        + "where dt ='" + ymdFormat.format(new Date()) + "'  group by dt,trsno having sum(cramt) <> sum(Dramt))b where  a.dt ='" + ymdFormat.format(new Date()) + "' "
//                        + "and a.trsno=b.trsno and a.org_brnid = '" + orgBrnCode + "' order by a.trsno").getResultList();
                result = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0),date_format(dt,'%d/%m/%Y'),trsno from recon_trf_d where dt ='" + dt + "'and org_brnid = '" + orgBrnCode + "'and trantype in (2,8,6) "
                        + "group by dt,trsno having sum(cramt) <> sum(Dramt) order by trsno").getResultList();
            }

            for (Object object1 : result) {
                CbsCashTrfClgScrollTimewisePojo pojoObject1 = new CbsCashTrfClgScrollTimewisePojo();
                Vector element1 = (Vector) object1;
                pojoObject1.setDrBal(new BigDecimal(formatter.format(element1.get(0))));
                pojoObject1.setCrBal(new BigDecimal(formatter.format(element1.get(1))));
                pojoObject1.setDt(element1.get(2).toString());
                pojoObject1.setRecno(Double.valueOf(element1.get(3).toString()));
                dataList.add(pojoObject1);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }
}
