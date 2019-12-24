/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.BalanceSheetDTO;
import com.cbs.dto.DateByComparator;
import com.cbs.dto.RdIntDetail;
import com.cbs.dto.report.CrrSlrPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.DtlRegisterPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.ho.share.DividendCalculationFacadeRemote;
import com.cbs.dto.DividendTable;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.AlmFddetailPojo;
import com.cbs.dto.report.LoanAcDetailsTable;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import com.cbs.dto.report.TopDepositorBorrowerPojo;
import com.cbs.dto.report.ho.AdvncAgnstShareDebtPojo;
import com.cbs.dto.report.ho.AnnualNPAStmtConsolidate;
import com.cbs.dto.report.ho.AnnualNPAStmtPojo;
import com.cbs.dto.report.ho.ChallanPojo;
import com.cbs.dto.report.ho.DeducteeChallanPojo;
import com.cbs.dto.report.ho.DeducteePojo;
import com.cbs.dto.report.ho.DuplicateCustIdPojo;
import com.cbs.dto.report.ho.StmtOfOpenCloseOfficePojo;
import com.cbs.dto.report.ho.UCBInvstmntInOtherUCBPojo;
import com.cbs.dto.report.ho.ComparatorByGno;
import com.cbs.dto.report.ho.ComparatorByReportName;
import com.cbs.dto.report.ho.ComparatorBySSSSgno;
import com.cbs.dto.report.ho.ComparatorBySSSgno;
import com.cbs.dto.report.ho.ComparatorBySSgno;
import com.cbs.dto.report.ho.ComparatorBySgno;
import com.cbs.dto.report.ho.DeducteeChallanPojo;
import com.cbs.dto.report.ho.DeducteePojo;
import com.cbs.dto.report.ho.DuplicateCustIdPojo;
import com.cbs.dto.report.ho.StmtOfOpenCloseOfficePojo;
import com.cbs.dto.report.ho.UCBInvstmntInOtherUCBPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.ExpenditureBalDayWisePojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Dhirendra Singh
 */
@Stateless(mappedName = "HoReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class HoReportFacade implements HoReportFacadeRemote {

    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private RbiReportFacadeRemote rbiReportRemote;
    @EJB
    RbiQuarterlyReportFacadeRemote quarterlyRemote;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    private GLReportFacadeRemote glReport;
    @EJB
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    @EJB
    private RbiSoss1And2ReportFacadeRemote rbiSoss1Remote;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    private CertIssueFacadeRemote certRemote;
    @EJB
    private DividendCalculationFacadeRemote divRemote;
    @EJB
    private ALMReportFacadeRemote almRemote;
    @EJB
    private InvestmentReportMgmtFacadeRemote investRptMgmtFacade;
    @EJB
    MiscReportFacadeRemote miscReportFacade;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @PersistenceContext
    private EntityManager em;
    NumberFormat formatter = new DecimalFormat("#.####");
    NumberFormat formatter2 = new DecimalFormat("0.00");
    NumberFormat formatter4 = new DecimalFormat("0.0000");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat y_m_d_h_m_sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<CrrSlrPojo> getCrrDetails(String brCode, String repOpt, String frDt, String toDt, String parameter) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        List<CrrSlrPojo> crrList = new ArrayList<CrrSlrPojo>();
        try {
            Vector ele = null;
            double crrPer = 0;
            frDt = ymd.format(dmy.parse(frDt));
            toDt = ymd.format(dmy.parse(toDt));

            String preRepDate = "";
            double ntdl = 0;
            String dt = frDt;
            double bal = 0;
            double repAmt = 0;

            repAmt = repAmount(repOpt);
            List<RdIntDetail> crList = null;
            if (!parameter.equalsIgnoreCase("NDTL")) {
                crList = getCashReserve(brCode, frDt, toDt, "C");
                Collections.sort(crList, new DateByComparator());
            }

            while (ymd.parse(dt).getTime() <= ymd.parse(toDt).getTime()) {
                CrrSlrPojo crrSlrPojo = new CrrSlrPojo();
                List reportList = em.createNativeQuery("select ifnull(crrperc,0) from ho_crr_parameter where wefdate=(select max(wefdate) from ho_crr_parameter where wefdate <='" + dt + "' )").getResultList();
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Crr Percentage does not defined.");
                }
                ele = (Vector) reportList.get(0);
                crrPer = Double.parseDouble(ele.get(0).toString());

                List reportList2 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + dt + "'").getResultList();
                if (reportList2.isEmpty()) {
                    throw new ApplicationException("Last Reporting Friday Date does not defined.");
                }
                ele = (Vector) reportList2.get(0);
                if (ele.get(0) == null) {
                    throw new ApplicationException("Last Reporting Friday Date does not defined.");
                }
                String repFriDate = ele.get(0).toString();
                repFriDate = CbsUtil.dateAdd(repFriDate, -14);
                if (ymd.parse(dt).getTime() == ymd.parse(frDt).getTime()) {
                    preRepDate = repFriDate;
                    ntdl = getNewNdtl(brCode, repFriDate).doubleValue() / repAmt;
                } else {
                    if (ymd.parse(repFriDate).getTime() != ymd.parse(preRepDate).getTime()) {
                        ntdl = getNewNdtl(brCode, repFriDate).doubleValue() / repAmt;
                    }
                    preRepDate = repFriDate;
                }
                double req = CbsUtil.round((ntdl * crrPer) / 100, 2);
                if (!parameter.equalsIgnoreCase("NDTL")) {
                    int index = getIndex(crList, ymd.parse(dt));

                    if (index != -1) {
                        RdIntDetail pojo = crList.get(index);
                        bal = Math.abs(pojo.getBalance()) / repAmt;
                    }

                    double deficit = req - bal;
                    if (deficit < 0) {
                        deficit = 0;
                    }

                    double surplus = bal - req;
                    if (surplus < 0) {
                        surplus = 0;
                    }
                    crrSlrPojo.setActMaint(CbsUtil.round(bal, 2));
                    crrSlrPojo.setDeficit(CbsUtil.round(deficit, 2));
                    crrSlrPojo.setSurplus(CbsUtil.round(surplus, 2));
                }

                crrSlrPojo.setDate(dmy.format(ymd.parse(dt)));
                crrSlrPojo.setNtdl(CbsUtil.round(ntdl, 2));
                crrSlrPojo.setReqToBeMaint(req);

                crrSlrPojo.setInterest(0.0);
                crrList.add(crrSlrPojo);
                dt = CbsUtil.dateAdd(dt, 1);
            }
            return crrList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<CrrSlrPojo> getSlrDetails(String brCode, String repOpt, String frDt, String toDt, String parameter) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        List<CrrSlrPojo> crrList = new ArrayList<CrrSlrPojo>();
        try {
            double crrPer = 0, slrPer = 0;
            frDt = ymd.format(dmy.parse(frDt));
            toDt = ymd.format(dmy.parse(toDt));
            String preRepDate = "";
            double ntdl = 0;
            String dt = frDt;

            double slrBal = 0;
            double cashReserve = 0;
            double repAmt = 0;
            repAmt = repAmount(repOpt);
            List<RdIntDetail> crList = null;
            List<RdIntDetail> appSecList = null;
            if (!parameter.equalsIgnoreCase("NDTL")) {
                crList = getCashReserve(brCode, frDt, toDt, "S");
                Collections.sort(crList, new DateByComparator());

                appSecList = getAppSecurities(brCode, frDt, toDt);
                Collections.sort(appSecList, new DateByComparator());
            }

            List parameterInfoReportList = em.createNativeQuery("SELECT * FROM parameterinfo_report WHERE REPORTNAME = 'CRRSLRHO'").getResultList();

            while (ymd.parse(dt).getTime() <= ymd.parse(toDt).getTime()) {
                CrrSlrPojo crrSlrPojo = new CrrSlrPojo();

                List reportList = em.createNativeQuery("select ifnull(crrperc,0), ifnull(slrperc,0) from ho_crr_parameter where wefdate=(select max(wefdate) from ho_crr_parameter where wefdate <='" + dt + "')").getResultList();
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Slr Percentage does not defined.");
                } else {
                    Vector ele = (Vector) reportList.get(0);
                    crrPer = Double.parseDouble(ele.get(0).toString());
                    slrPer = Double.parseDouble(ele.get(1).toString());
                }

                List reportList3 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + dt + "'").getResultList();
                if (reportList3.isEmpty()) {
                    throw new ApplicationException("Last Reporting Friday Date does not defined.");
                }
                Vector ele3 = (Vector) reportList3.get(0);
                if (ele3.get(0) == null) {
                    throw new ApplicationException("Last Reporting Friday Date does not defined.");
                }
                String repFriDate = ele3.get(0).toString();
                repFriDate = CbsUtil.dateAdd(repFriDate, -14);

                if (ymd.parse(dt).getTime() == ymd.parse(frDt).getTime()) {
                    preRepDate = repFriDate;
                    ntdl = getNewNdtl(brCode, repFriDate).doubleValue() / repAmt;
                } else {
                    if (ymd.parse(repFriDate).getTime() != ymd.parse(preRepDate).getTime()) {
                        ntdl = getNewNdtl(brCode, repFriDate).doubleValue() / repAmt;
                    }
                    preRepDate = repFriDate;
                }
                double reqCrr = CbsUtil.round((ntdl * crrPer) / 100, 2);
                double reqSlr = CbsUtil.round((ntdl * slrPer) / 100, 2);
                if (!parameter.equalsIgnoreCase("NDTL")) {
                    int index = getIndex(crList, ymd.parse(dt));
                    if (index != -1) {
                        RdIntDetail pojo = crList.get(index);
                        cashReserve = Math.abs(pojo.getBalance()) / repAmt;
                    }

                    int slrIndex = getIndex(appSecList, ymd.parse(dt));
                    if (slrIndex != -1) {
                        RdIntDetail pojo = appSecList.get(slrIndex);
                        slrBal = Math.abs(pojo.getBalance()) / repAmt;
                    }
                    cashReserve = cashReserve - reqCrr;

                    //Commented by dhirendra singh after discussed with Mr. Diwaker.
                    //double actMaint = cashReserve + slrBal;
                    double actMaint;
                    if (parameterInfoReportList.size() > 0) {
                        actMaint = slrBal + (cashReserve < 0 ? 0 : cashReserve);
                    } else {
                        actMaint = slrBal;
                    }

                    double deficit = reqSlr - actMaint;
                    if (deficit < 0) {
                        deficit = 0;
                    }

                    double surplus = actMaint - reqSlr;
                    if (surplus < 0) {
                        surplus = 0;
                    }
                    crrSlrPojo.setActMaint(CbsUtil.round(actMaint, 2));
                    crrSlrPojo.setDeficit(CbsUtil.round(deficit, 2));
                    crrSlrPojo.setSurplus(CbsUtil.round(surplus, 2));
                }

                crrSlrPojo.setDate(dmy.format(ymd.parse(dt)));
                crrSlrPojo.setNtdl(CbsUtil.round(ntdl, 2));
                crrSlrPojo.setReqToBeMaint(reqSlr);

                crrSlrPojo.setInterest(0.0);
                crrList.add(crrSlrPojo);
                dt = CbsUtil.dateAdd(dt, 1);
            }
            return crrList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<CrrSlrPojo> getSlrDetailsMis(String brCode, String repOpt, String frDt, String toDt, String parameter) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        List<CrrSlrPojo> crrList = new ArrayList<CrrSlrPojo>();
        try {
            double crrPer = 0, slrPer = 0;
            frDt = ymd.format(dmy.parse(frDt));
            toDt = ymd.format(dmy.parse(toDt));
            String preRepDate = "";
            double ntdl = 0;
            String dt = frDt;
            double slrBal = 0;
            double cashReserve = 0;
            double repAmt = 0;
            repAmt = repAmount(repOpt);
            List<RdIntDetail> crList = null;
            List<RdIntDetail> appSecList = null;
            String nextDt = dt;
            List repFriList = em.createNativeQuery("select '" + frDt + "'  "
                    + " union "
                    + " select distinct date_format((repfridate),'%Y%m%d') from ho_reportingfriday where repfridate between '" + frDt + "' and '" + toDt + "'"
                    + " union "
                    + " select '" + toDt + "' ").getResultList();
            List parameterInfoReportList = em.createNativeQuery("SELECT * FROM parameterinfo_report WHERE REPORTNAME = 'CRRSLRHO'").getResultList();
            for (int t = 0; t < repFriList.size(); t++) {
                Vector vect = (Vector) repFriList.get(t);
                for (int s = t + 1; s < repFriList.size(); s++) {
                    Vector vect1 = (Vector) repFriList.get(t + 1);
                    nextDt = vect1.get(0).toString();
                }
                if ((!vect.get(0).toString().equalsIgnoreCase(dt) && t != 0) || t == 0) {
                    if (ymd.parse(dt).getTime() <= ymd.parse(toDt).getTime()) {
//                        if (ymd.parse(dt).getTime() <= ymd.parse(vect.get(0).toString()).getTime()) {
                        CrrSlrPojo crrSlrPojo = new CrrSlrPojo();
                        List reportList = em.createNativeQuery("select ifnull(crrperc,0), ifnull(slrperc,0) from ho_crr_parameter where wefdate=(select max(wefdate) from ho_crr_parameter where wefdate <='" + dt + "')").getResultList();
                        if (reportList.isEmpty()) {
                            throw new ApplicationException("Slr Percentage does not defined.");
                        } else {
                            Vector ele = (Vector) reportList.get(0);
                            crrPer = Double.parseDouble(ele.get(0).toString());
                            slrPer = Double.parseDouble(ele.get(1).toString());
                        }
                        String newDt = "";
                        crList = new ArrayList<>();
                        appSecList = new ArrayList<>();
                        if (!parameter.equalsIgnoreCase("NDTL")) {
                            crList = getCashReserve(brCode, dt, dt, "S");
                            Collections.sort(crList, new DateByComparator());
                            appSecList = getAppSecurities(brCode, dt, dt);
                            Collections.sort(appSecList, new DateByComparator());
                            newDt = vect.get(0).toString();
                        }
                        List reportList3 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + dt + "'").getResultList();
                        if (reportList3.isEmpty()) {
                            throw new ApplicationException("Last Reporting Friday Date does not defined.");
                        }
                        Vector ele3 = (Vector) reportList3.get(0);
                        if (ele3.get(0) == null) {
                            throw new ApplicationException("Last Reporting Friday Date does not defined.");
                        }
                        String repFriDate = ele3.get(0).toString();
                        repFriDate = CbsUtil.dateAdd(repFriDate, -14);
                        if (ymd.parse(dt).getTime() == ymd.parse(frDt).getTime()) {
                            preRepDate = repFriDate;
                            ntdl = getNewNdtl(brCode, repFriDate).doubleValue() / repAmt;
                        } else {
                            if (ymd.parse(repFriDate).getTime() != ymd.parse(preRepDate).getTime()) {
                                ntdl = getNewNdtl(brCode, repFriDate).doubleValue() / repAmt;
                            }
                            preRepDate = repFriDate;
                        }
                        double reqCrr = CbsUtil.round((ntdl * crrPer) / 100, 2);
                        double reqSlr = CbsUtil.round((ntdl * slrPer) / 100, 2);
                        if (!parameter.equalsIgnoreCase("NDTL")) {
                            int index = getIndex(crList, ymd.parse(dt));
                            if (index != -1) {
                                RdIntDetail pojo = crList.get(index);
                                cashReserve = Math.abs(pojo.getBalance()) / repAmt;
                            }
                            int slrIndex = getIndex(appSecList, ymd.parse(dt));
                            if (slrIndex != -1) {
                                RdIntDetail pojo = appSecList.get(slrIndex);
                                slrBal = Math.abs(pojo.getBalance()) / repAmt;
                            }
                            crrSlrPojo.setCrrActualMaint(cashReserve);
                            cashReserve = cashReserve - reqCrr;
                            double actMaint;
                            if (parameterInfoReportList.size() > 0) {
                                actMaint = slrBal + (cashReserve < 0 ? 0 : cashReserve);
                            } else {
                                actMaint = slrBal;
                            }
                            double deficit = reqSlr - actMaint;
                            if (deficit < 0) {
                                deficit = 0;
                            }
                            double surplus = actMaint - reqSlr;
                            if (surplus < 0) {
                                surplus = 0;
                            }
                            crrSlrPojo.setActMaint(CbsUtil.round(actMaint, 2));
                            crrSlrPojo.setDeficit(CbsUtil.round(deficit, 2));
                            crrSlrPojo.setSurplus(CbsUtil.round(surplus, 2));
                        }
                        if (ymd.parse(newDt).getTime() != ymd.parse(toDt).getTime()) {
                            crrSlrPojo.setDate(dmy.format(ymd.parse(dt)).concat(" to ").concat(dmy.format(ymd.parse(nextDt.toString()))));
                        } else {
                            crrSlrPojo.setDate(dmy.format(ymd.parse(dt)).concat(" to ").concat(dmy.format(ymd.parse(toDt))));
                        }
//                        System.out.println("Date is " + crrSlrPojo.getDate());
                        crrSlrPojo.setNtdl(CbsUtil.round(ntdl, 2));
                        crrSlrPojo.setReqToBeMaint(reqSlr);
                        crrSlrPojo.setCrrReqToBeMaint(reqCrr);
                        crrSlrPojo.setInterest(0.0);
                        crrList.add(crrSlrPojo);
                        dt = CbsUtil.dateAdd(nextDt, 1);
//                            if (ymd.parse(newDt).getTime() == ymd.parse(dt).getTime()) {
//                                break;
//                            }
//                        }
                    }
                }
                if (ymd.parse(dt).getTime() > ymd.parse(toDt).getTime()) {
                    break;
                }
            }
            return crrList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private List<RdIntDetail> getCashReserve(String brCode, String frDt, String toDt, String opt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List<RdIntDetail> dataList = new ArrayList<RdIntDetail>();
            List oss1Query = em.createNativeQuery("select gl_head_from, gl_head_to, f_sss_gno, f_ssss_gno,classification,f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + frDt + "' between eff_fr_dt and eff_to_dt and ((f_sss_gno like 'CR%' or f_ssss_gno = 'CR')  OR (f_ssss_gno like 'SLREX%' or f_ssss_gno = 'SLREX')) and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_ho_rbi_stmt_report");
            }
            for (int i = 0; i < oss1Query.size(); i++) {
                Vector oss1Vect = (Vector) oss1Query.get(i);
                String glHeadFrom = oss1Vect.get(0).toString();
                String glHeadTo = oss1Vect.get(1).toString();

                String fSssGNo = oss1Vect.get(2).toString();
                String fSsssGNo = oss1Vect.get(3).toString();
                String classification = oss1Vect.get(4).toString();
                String fSGNo = oss1Vect.get(5).toString();

                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setBrnCode(brCode);
                params.setDate(CbsUtil.dateAdd(frDt, -1));
                params.setToDate(CbsUtil.dateAdd(frDt, -1));
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setOrgBrCode(brCode);
                params.setClassification(classification);
                params.setFlag(fSGNo);
                List<RdIntDetail> osList;

                List methodList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CRRSLR-PAR'").getResultList();
                if (methodList.isEmpty()) {
                    osList = getOsBalanceList(params, frDt, toDt);
                } else {
                    Vector methodVect = (Vector) methodList.get(0);
                    String method = methodVect.get(0).toString();
                    if (method.equalsIgnoreCase("N")) {
                        osList = getOsBalanceList(params, frDt, toDt);
                    } else {
                        osList = getOsBalanceList1(params, frDt, toDt);
                    }
                }

                Collections.sort(osList, new DateByComparator());
                for (RdIntDetail osPojo : osList) {
                    int index = getIndex(dataList, osPojo.getDate());
                    if (index == -1) {
                        RdIntDetail dataPo = new RdIntDetail();
                        dataPo.setDate(osPojo.getDate());
                        dataPo.setBalance(osPojo.getBalance());
                        dataList.add(dataPo);
                    } else {
                        RdIntDetail dataPo = dataList.get(index);
                        dataList.remove(dataPo);
                        double dataBal = dataPo.getBalance();
                        if (opt.equals("C")) {
                            if (fSsssGNo.equalsIgnoreCase("CR") || fSsssGNo.equalsIgnoreCase("SLREX") || fSssGNo.equalsIgnoreCase("CRP")) {
                                dataBal = dataBal + osPojo.getBalance();
                            }
                            if (fSssGNo.equalsIgnoreCase("CRM")) {
                                dataBal = dataBal - osPojo.getBalance();
                            }
                        } else {
                            if (fSsssGNo.equalsIgnoreCase("CR") || fSssGNo.equalsIgnoreCase("CRP")) {
                                dataBal = dataBal + osPojo.getBalance();
                            }
                            if (fSssGNo.equalsIgnoreCase("CRM")) {
                                dataBal = dataBal - osPojo.getBalance();
                            }
                        }

                        RdIntDetail dataP = new RdIntDetail();
                        dataP.setDate(dataPo.getDate());
                        dataP.setBalance(dataBal);
                        dataList.add(index, dataP);
                    }
                }
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private List<RdIntDetail> getAppSecurities(String brCode, String frDt, String toDt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List<RdIntDetail> dataList = new ArrayList<RdIntDetail>();
            List oss1Query = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + frDt + "' between eff_fr_dt and eff_to_dt and (f_ssss_gno = 'APP-SEC') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_ho_rbi_stmt_report");
            }
            for (int i = 0; i < oss1Query.size(); i++) {
                Vector oss1Vect = (Vector) oss1Query.get(i);

                String glHeadFrom = oss1Vect.get(2).toString();
                String glHeadTo = oss1Vect.get(3).toString();
                String classification = oss1Vect.get(4).toString();
                String fSGNo = oss1Vect.get(5).toString();

                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setBrnCode(brCode);
                params.setDate(CbsUtil.dateAdd(frDt, -1));
                params.setToDate(CbsUtil.dateAdd(frDt, -1));
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setOrgBrCode(brCode);
                params.setClassification(classification);
                params.setFlag(fSGNo);
                List<RdIntDetail> osList;

                List methodList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'CRRSLR-PAR'").getResultList();
                if (methodList.isEmpty()) {
                    osList = getOsBalanceList(params, frDt, toDt);
                } else {
                    Vector methodVect = (Vector) methodList.get(0);
                    String method = methodVect.get(0).toString();
                    if (method.equalsIgnoreCase("N")) {
                        osList = getOsBalanceList(params, frDt, toDt);
                    } else {
                        osList = getOsBalanceList1(params, frDt, toDt);
                    }
                }
                Collections.sort(osList, new DateByComparator());
                for (RdIntDetail osPojo : osList) {
                    int index = getIndex(dataList, osPojo.getDate());
                    if (index == -1) {
                        RdIntDetail dataPo = new RdIntDetail();
                        dataPo.setDate(osPojo.getDate());
                        dataPo.setBalance(osPojo.getBalance());
                        dataList.add(dataPo);
                    } else {
                        RdIntDetail dataPo = dataList.get(index);
                        dataList.remove(dataPo);
                        double dataBal = dataPo.getBalance();
                        dataBal = dataBal + osPojo.getBalance();

                        RdIntDetail dataP = new RdIntDetail();
                        dataP.setDate(dataPo.getDate());
                        dataP.setBalance(dataBal);
                        dataList.add(index, dataP);
                    }
                }
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private List<RdIntDetail> getOsBalanceList(AdditionalStmtPojo params, String frDt, String toDt) throws ApplicationException {
        try {
            RdIntDetail dataPojo;
            List<RdIntDetail> osList = new ArrayList<RdIntDetail>();
            BigDecimal bal = new BigDecimal(0);
            if (!((params.getGlFromHead() == null) || (params.getGlFromHead().equalsIgnoreCase(""))) && !((params.getGlToHead() == null) || (params.getGlToHead().equalsIgnoreCase("")))) {
                if (!params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    List<GlHeadPojo> tmpPojoList = rbiReportRemote.getGLHeadAndBal(params);
                    for (GlHeadPojo glPojo : tmpPojoList) {
                        bal = bal.add(glPojo.getBalance());
                    }
                }

                List<RdIntDetail> tmpList = getDateWiseBal(params.getBrnCode(), params.getGlFromHead(), params.getGlToHead(), frDt, toDt);
                Collections.sort(tmpList, new DateByComparator());
                String dt = frDt;
                if (tmpList.isEmpty()) {
                    while (ymd.parse(dt).getTime() <= ymd.parse(toDt).getTime()) {
                        dataPojo = new RdIntDetail();
                        dataPojo.setDate(ymd.parse(dt));
                        dataPojo.setBalance(bal.doubleValue());
                        osList.add(dataPojo);
                        dt = CbsUtil.dateAdd(dt, 1);
                    }
                } else {
                    while (ymd.parse(dt).getTime() <= ymd.parse(toDt).getTime()) {
                        int index = getIndex(tmpList, ymd.parse(dt));
                        if (index == -1) {
                            RdIntDetail dataPo = new RdIntDetail();
                            dataPo.setDate(ymd.parse(dt));
                            dataPo.setBalance(bal.doubleValue());
                            osList.add(dataPo);
                        } else {
                            RdIntDetail dataPo = tmpList.get(index);
                            if (params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                bal = new BigDecimal(dataPo.getBalance());
                            } else {
                                bal = bal.add(new BigDecimal(dataPo.getBalance()));
                            }
                            dataPojo = new RdIntDetail();
                            dataPojo.setDate(ymd.parse(dt));
                            dataPojo.setBalance(bal.doubleValue());
                            osList.add(index, dataPojo);
                        }
                        dt = CbsUtil.dateAdd(dt, 1);
                    }
                }
            }
            return osList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private List<RdIntDetail> getOsBalanceList1(AdditionalStmtPojo params, String frDt, String toDt) throws ApplicationException {
        try {
            RdIntDetail dataPojo;
            List<RdIntDetail> osList = new ArrayList<RdIntDetail>();
            BigDecimal bal = new BigDecimal(0);
            if (!((params.getGlFromHead() == null) || (params.getGlFromHead().equalsIgnoreCase(""))) && !((params.getGlToHead() == null) || (params.getGlToHead().equalsIgnoreCase("")))) {
                String dt = frDt;
                while (ymd.parse(dt).getTime() <= ymd.parse(toDt).getTime()) {
                    params.setDate(dt);
                    params.setToDate(dt);
                    List<GlHeadPojo> tmpPojoList = rbiReportRemote.getGLHeadAndBal(params);
                    bal = new BigDecimal(0);
                    for (GlHeadPojo glPojo : tmpPojoList) {
                        bal = bal.add(glPojo.getBalance());
                    }
                    RdIntDetail dataPo = new RdIntDetail();
                    dataPo.setDate(ymd.parse(dt));
                    dataPo.setBalance(bal.doubleValue());
                    osList.add(dataPo);
                    dt = CbsUtil.dateAdd(dt, 1);
                }
            }
            return osList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public int getIndex(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                RdIntDetail obj = list.get(i);
                if (obj.getDate().getTime() == dt.getTime()) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private List<RdIntDetail> getDateWiseBal(String brCode, String fromHead, String toHead, String frDt, String toDt) throws ApplicationException {
        try {
            List<RdIntDetail> dateWiseAmtList = new ArrayList<RdIntDetail>();
            RdIntDetail pojo;
            List dataList;
            if (brCode.equals("0A")) {
                if (fromHead.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select date_format(ldate,'%Y%m%d'), ifnull(sum(amt)*-1, 0) from cashinhand where ldate between'" + frDt + "' and '" + toDt + "' group by ldate").getResultList();
                } else {
                    dataList = em.createNativeQuery("select date_format(dt,'%Y%m%d'), ifnull(sum(Cramt)-sum(Dramt),0) from gl_recon where dt between'" + frDt + "' and '" + toDt + "' and trandesc <>13  "
                            + "and substring(acno,3,8) between '" + fromHead + " ' and '" + toHead + "' group by dt ").getResultList();
                }
            } else {
                if (fromHead.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select date_format(ldate,'%Y%m%d'), ifnull(sum(amt)*-1, 0) from cashinhand where ldate between'" + frDt + "' and '" + toDt + "'   and brncode = '" + brCode + "' group by ldate").getResultList();
                } else {
                    dataList = em.createNativeQuery("select date_format(dt,'%Y%m%d'), ifnull(sum(Cramt)-sum(Dramt),0) from gl_recon where dt between'" + frDt + "' and '" + toDt + "'  and trandesc <>13 "
                            + "and  substring(acno,3,8) between '" + fromHead + " ' and '" + toHead + "' and substring(acno,1,2)='" + brCode + "' group by dt ").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector vec = (Vector) dataList.get(i);
                    pojo = new RdIntDetail();
                    pojo.setBalance(Double.parseDouble(vec.get(1).toString()));
                    pojo.setDate(ymd.parse(vec.get(0).toString()));
                    dateWiseAmtList.add(pojo);
                }
            }
            return dateWiseAmtList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public BigDecimal getNewNdtl(String brCode, String dt) throws ApplicationException {
        try {
            String ndtlStr = getNdtlOneTwoThree(brCode, dt);
            String[] ndtlArr = ndtlStr.split("~`");

            BigDecimal balOne = new BigDecimal(ndtlArr[0]);
            BigDecimal balTwo = new BigDecimal(ndtlArr[1]);
            BigDecimal balThree = new BigDecimal(ndtlArr[2]);

            BigDecimal ndtl = balOne.subtract(balThree.abs());
            if (ndtl.compareTo(new BigDecimal(0)) > 0) {
                ndtl = ndtl.add(balTwo);
            } else {
                ndtl = balTwo;
            }
            return ndtl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private String getNdtlOneTwoThree(String brCode, String dt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt  and (f_ssss_gno ='NDTL-F') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            BigDecimal balOne;
            if (oss1QueryList.isEmpty()) {
                balOne = new BigDecimal(0);
            } else {
                balOne = getAsOnDateBal(oss1QueryList, brCode, dt, 1);
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt  and (f_ssss_gno ='NDTL-S') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') and classification = 'L' "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            BigDecimal balTwo;
            if (oss1QueryList.isEmpty()) {
                balTwo = new BigDecimal(0);
            } else {
                balTwo = getAsOnDateBal(oss1QueryList, brCode, dt, 1);
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt  and (f_ssss_gno ='NDTL-S') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') and classification = 'A' "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();

            if (oss1QueryList.isEmpty()) {
                balTwo = balTwo.add(new BigDecimal(0));
            } else {
                balTwo = balTwo.add(getAsOnDateBal(oss1QueryList, brCode, dt, -1));
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + " where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt and (f_ssss_gno ='NDTL-S') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'')  and classification not in ('A','L') "
                    + " order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + " cast(ssss_gno as unsigned)").getResultList();

            if (oss1QueryList.isEmpty()) {
                balTwo = balTwo.add(new BigDecimal(0));
            } else {
                balTwo = balTwo.add(getAsOnDateBal(oss1QueryList, brCode, dt, -1));
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt and (f_ssss_gno ='NDTL-T') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            BigDecimal balThree;
            if (oss1QueryList.isEmpty()) {
                balThree = new BigDecimal(0);
            } else {
                balThree = getAsOnDateBal(oss1QueryList, brCode, dt, -1);
            }

            String ndtlStr = balOne + "~`" + balTwo + "~`" + balThree;
            return ndtlStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getTotalNdtl(String brCode, String dt) throws ApplicationException {
        try {
            String ndtlStr = getTotalNdtlOneTwoThree(brCode, dt);
            String[] ndtlArr = ndtlStr.split("~`");

            BigDecimal balOne = new BigDecimal(ndtlArr[0]);
            BigDecimal balTwo = new BigDecimal(ndtlArr[1]);
            BigDecimal balThree = new BigDecimal(ndtlArr[2]);

            BigDecimal ndtl = balOne.subtract(balThree.abs());
            if (ndtl.compareTo(new BigDecimal(0)) > 0) {
                ndtl = ndtl.add(balTwo);
            } else {
                ndtl = balTwo;
            }
            return ndtl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private String getTotalNdtlOneTwoThree(String brCode, String dt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt  and (f_ssss_gno ='NDTL-F') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            BigDecimal balOne = new BigDecimal(0);
            if (oss1QueryList.isEmpty()) {
                balOne = new BigDecimal(0);
            } else {
                balOne = getAsOnDateBalForTotalNDTL(oss1QueryList, brCode, dt, 1);
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt  and (f_ssss_gno ='NDTL-S') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') and classification = 'L' "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            BigDecimal balTwo = new BigDecimal(0);
            if (oss1QueryList.isEmpty()) {
                balTwo = new BigDecimal(0);
            } else {
                balTwo = getAsOnDateBalForTotalNDTL(oss1QueryList, brCode, dt, 1);
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt  and (f_ssss_gno ='NDTL-S') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') and classification = 'A' "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();

            if (oss1QueryList.isEmpty()) {
                balTwo = balTwo.add(new BigDecimal(0));
            } else {
                balTwo = balTwo.add(getAsOnDateBalForTotalNDTL(oss1QueryList, brCode, dt, -1));
            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + " where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt and (f_ssss_gno ='NDTL-S') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'')  and classification not in ('A','L') "
                    + " order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + " cast(ssss_gno as unsigned)").getResultList();

            if (oss1QueryList.isEmpty()) {
                balTwo = balTwo.add(new BigDecimal(0));
            } else {
                balTwo = balTwo.add(getAsOnDateBalForTotalNDTL(oss1QueryList, brCode, dt, -1));
                /*For Total Demand And Time Liabilities */

            }

            oss1QueryList = em.createNativeQuery("select ac_nature, ac_type, gl_head_from, gl_head_to, f_ssss_gno,classification, f_s_gno from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1'  and '" + dt + "' between eff_fr_dt and eff_to_dt and (f_ssss_gno ='NDTL-T') and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            BigDecimal balThree;
            if (oss1QueryList.isEmpty()) {
                balThree = new BigDecimal(0);
            } else {
                balThree = getAsOnDateBalForTotalNDTL(oss1QueryList, brCode, dt, -1);
            }

            String ndtlStr = balOne + "~`" + balTwo + "~`" + balThree;
            return ndtlStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private BigDecimal getAsOnDateBal(List oss1Query, String brCode, String dt, int cls) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            BigDecimal balance = new BigDecimal(0);
            String glQuery = "";
            for (int i = 0; i < oss1Query.size(); i++) {
                Vector oss1Vect = (Vector) oss1Query.get(i);
                String acNature = oss1Vect.get(0).toString();
                String acType = oss1Vect.get(1).toString();

                String glHeadFrom = oss1Vect.get(2).toString();
                String glHeadTo = oss1Vect.get(3).toString();

                String classification = oss1Vect.get(5).toString();
                String fSGNo = oss1Vect.get(6).toString();
                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setNature(acNature);
                params.setBrnCode(brCode);
                params.setDate(dt);
                params.setToDate(dt);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setOrgBrCode(brCode);
                params.setClassification(classification);
                params.setFlag(fSGNo);
                AcctBalPojo acctBal = null;
                if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                    acctBal = rbiReportRemote.getAcctsAndBal(params);
                    balance = balance.add(acctBal.getBalance());
                }
                if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                    if (acType.contains("LIEN") || acType.contains("SSI")) {
                        balance = balance.add(quarterlyRemote.getAcCodeAmount(acType, dt).multiply(new BigDecimal("-1")));
                    } else {
                        acNature = common.getAcNatureByAcType(acType);
                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        balance = balance.add(acctBal.getBalance());
                    }
                }
                if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                    if (glQuery.equalsIgnoreCase("")) {
                        glQuery = glQuery + " substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "'";
                    } else {
                        glQuery = glQuery + " or substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "'";
                    }
                }
            }
            if (!glQuery.equals("")) {
                List dataList;
                if (brCode.equals("0A")) {
                    dataList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (select ifnull(sum(Cramt)-sum(Dramt),0) as amt from gl_recon where dt <='" + dt + "' /*and trandesc <>13*/  "
                            + "and (" + glQuery + ") group by substring(acno,3,8) having sign(sum(cramt-dramt)) = " + cls + ") a").getResultList();
                } else {
                    dataList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (select ifnull(sum(Cramt)-sum(Dramt),0) as amt from gl_recon where dt <='" + dt + "' /*and trandesc <>13*/ "
                            + "and substring(acno,1,2)='" + brCode + "' and (" + glQuery + ")  group by substring(acno,3,8) having sign(sum(cramt-dramt)) = " + cls + ") a").getResultList();
                }
                if (!dataList.isEmpty()) {
                    Vector vec = (Vector) dataList.get(0);
                    balance = balance.add(new BigDecimal(vec.get(0).toString()));
                }
            }
            return balance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private BigDecimal getAsOnDateBalForTotalNDTL(List oss1Query, String brCode, String dt, int cls) throws ApplicationException {
        /* This method will only Provide the Total Demand and Time Liabilites and No Lien Amount will be subtracted */
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            BigDecimal balance = new BigDecimal(0);
            String glQuery = "";
            for (int i = 0; i < oss1Query.size(); i++) {
                Vector oss1Vect = (Vector) oss1Query.get(i);
                String acNature = oss1Vect.get(0).toString();
                String acType = oss1Vect.get(1).toString();
                String glHeadFrom = oss1Vect.get(2).toString();
                String glHeadTo = oss1Vect.get(3).toString();
                String classification = oss1Vect.get(5).toString();
                String fSGNo = oss1Vect.get(6).toString();
                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setNature(acNature);
                params.setBrnCode(brCode);
                params.setDate(dt);
                params.setToDate(dt);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setOrgBrCode(brCode);
                params.setClassification(classification);
                params.setFlag(fSGNo);
                AcctBalPojo acctBal = null;
                if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                    acctBal = rbiReportRemote.getAcctsAndBal(params);
                    balance = balance.add(acctBal.getBalance());
                }
                if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                    if (acType.contains("LIEN") || acType.contains("SSI")) {
                        /* For Total Demand Liabilities Lien Amount will not subtracted*/
                        balance = balance.add(new BigDecimal(0));
                    } else {
                        acNature = common.getAcNatureByAcType(acType);
                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        balance = balance.add(acctBal.getBalance());
                    }
                }
                if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                    if (glQuery.equalsIgnoreCase("")) {
                        glQuery = glQuery + " substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "'";
                    } else {
                        glQuery = glQuery + " or substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "'";
                    }
                }
            }
            if (!glQuery.equals("")) {
                List dataList;
                if (brCode.equals("0A")) {
                    dataList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (select ifnull(sum(Cramt)-sum(Dramt),0) as amt from gl_recon where dt <='" + dt + "' /*and trandesc <>13*/  "
                            + "and (" + glQuery + ") group by substring(acno,3,8) having sign(sum(cramt-dramt)) = " + cls + ") a").getResultList();
                } else {
                    dataList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (select ifnull(sum(Cramt)-sum(Dramt),0) as amt from gl_recon where dt <='" + dt + "' /*and trandesc <>13*/ "
                            + "and substring(acno,1,2)='" + brCode + "' and (" + glQuery + ")  group by substring(acno,3,8) having sign(sum(cramt-dramt)) = " + cls + ") a").getResultList();
                }
                if (!dataList.isEmpty()) {
                    Vector vec = (Vector) dataList.get(0);
                    balance = balance.add(new BigDecimal(vec.get(0).toString()));
                }
            }
            return balance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<BalanceSheetDTO> getBalanceSheetData(String dt) throws ApplicationException {
        List<BalanceSheetDTO> balanceSheetList = new ArrayList<BalanceSheetDTO>();
        BalanceSheetDTO balDTO;
        try {
            Calendar cal = Calendar.getInstance();
            Date userDt = ymd.parse(dt);
            cal.setTime(userDt);
            int preFinYear = 0;
            if (cal.get(Calendar.MONTH) <= 2) {
                preFinYear = cal.get(Calendar.YEAR) - 2;
            } else {
                preFinYear = cal.get(Calendar.YEAR) - 1;
            }
            List yearList = em.createNativeQuery("select mindate,maxdate from cbs_yearend where yearendflag='Y' AND f_year = " + preFinYear).getResultList();
            if (yearList.isEmpty()) {
                throw new ApplicationException("Previous Year data does not exist.");
            }
            Vector ele = (Vector) yearList.get(0);
            String preMinDate = ele.get(0).toString();
            String preMaxDate = ele.get(1).toString();
            yearList = em.createNativeQuery("select mindate,maxdate from cbs_yearend where mindate<='" + dt + "' and maxdate>='" + dt + "'").getResultList();
            if (yearList.isEmpty()) {
                throw new ApplicationException("Current Year data does not exist.");
            }
            ele = (Vector) yearList.get(0);
            String minDate = ele.get(0).toString();
            String maxDate = ele.get(1).toString();

            List reportList = em.createNativeQuery("select gno,s_gno,ss_gno,sss_gno,ac_code,ac_desc,glb_actype from "
                    + "ho_balance_sheet_master").getResultList();
            if (reportList.isEmpty()) {
                throw new ApplicationException("Master data does not defined.");
            }
            for (int i = 0; i < reportList.size(); i++) {
                balDTO = new BalanceSheetDTO();
                Vector element = (Vector) reportList.get(i);

                String tmpGLHead = (element.get(4) != null ? element.get(4).toString() : "");
                String tmpGNo = (element.get(0) != null ? element.get(0).toString() : "0");
                String tmpSGNo = (element.get(1) != null ? element.get(1).toString() : "0");
                String tmpSSGNo = (element.get(2) != null ? element.get(2).toString() : "0");
                String tmpSSSGNo = (element.get(3) != null ? element.get(3).toString() : "0");
                String tmpDesc = (element.get(5) != null ? element.get(5).toString() : "");
                String tmpglb_actype = (element.get(6) != null ? element.get(5).toString() : "");


                balDTO.setgNo(Integer.parseInt(tmpGNo));
                balDTO.setsGNo(Integer.parseInt(tmpSGNo));
                balDTO.setSsGNo(Integer.parseInt(tmpSSGNo));
                balDTO.setSssGNo(Integer.parseInt(tmpSSSGNo));

                balDTO.setAcNo(tmpGLHead);
                balDTO.setAcDesc(tmpDesc);
                balDTO.setGlbAcType(tmpglb_actype);

                double preTotal = 0;
                double curTotal = 0;
                if (!tmpGLHead.equals("") && !tmpGLHead.equals("0")) {
                    if (tmpGLHead.equals("06002500") || tmpGLHead.equals("06002000")) {
                        List dataList = em.createNativeQuery("select ifnull(sum(cash_cr_amt + trf_cr_amt + clg_cr_amt),0),ifnull(sum(cash_dr_amt + trf_dr_amt + clg_dr_amt),0) from "
                                + "ho_consolidate_entry where dt between '" + preMinDate + "' AND '" + preMaxDate + "' and substring(acno,3,8) between '06002000' "
                                + "and '06003000'").getResultList();
                        if (dataList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in considate entry");
                        }
                        Vector ele1 = (Vector) dataList.get(0);
                        String crAmt = (ele1.get(0) != null ? ele1.get(0).toString() : "0");
                        String drAmt = (ele1.get(1) != null ? ele1.get(1).toString() : "0");
                        preTotal = Double.parseDouble(crAmt) - Double.parseDouble(drAmt);

                        dataList = em.createNativeQuery("Select ifnull(sum(cash_cr_amt + trf_cr_amt + clg_cr_amt),0),ifnull(sum(cash_dr_amt + trf_dr_amt + clg_dr_amt),0) from "
                                + "ho_consolidate_entry where dt between '" + minDate + "' and '" + maxDate + "' and substring(acno,3,8) between '06002000' "
                                + "and '06003000'").getResultList();
                        ele1 = (Vector) dataList.get(0);
                        crAmt = (ele1.get(0) != null ? ele1.get(0).toString() : "0");
                        drAmt = (ele1.get(1) != null ? ele1.get(1).toString() : "0");
                        curTotal = Double.parseDouble(crAmt) - Double.parseDouble(drAmt);
                    } else if (tmpGLHead.equals("06005501")) {
                        List dataList = em.createNativeQuery("select ifnull(sum(amt),0) from cashinhand where ldate = '" + preMaxDate + "'").getResultList();
                        if (dataList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in cash in hand");
                        }
                        Vector ele1 = (Vector) dataList.get(0);
                        String amt = (ele1.get(0) != null ? ele1.get(0).toString() : "0");
                        preTotal = Double.parseDouble(amt);

                        dataList = em.createNativeQuery("select ifnull(sum(amt),0) from cashinhand where ldate = '" + maxDate + "'").getResultList();
                        if (dataList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in cash in hand");
                        }
                        ele1 = (Vector) dataList.get(0);
                        amt = (ele1.get(0) != null ? ele1.get(0).toString() : "0");
                        curTotal = Double.parseDouble(amt);
                    } else {
                        List dataList = em.createNativeQuery("select ifnull(sum(cash_cr_amt + trf_cr_amt + clg_cr_amt),0),ifnull(sum(cash_dr_amt + trf_dr_amt + clg_dr_amt),0) from "
                                + "ho_consolidate_entry where dt between '" + preMinDate + "' and '" + preMaxDate + "' and substring(acno,3,8) = '" + tmpGLHead + "'").getResultList();
                        if (dataList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in considate entry");
                        }
                        Vector ele1 = (Vector) dataList.get(0);
                        String crAmt = (ele1.get(0) != null ? ele1.get(0).toString() : "0");
                        String drAmt = (ele1.get(1) != null ? ele1.get(1).toString() : "0");
                        preTotal = Double.parseDouble(crAmt) - Double.parseDouble(drAmt);

                        dataList = em.createNativeQuery("select ifnull(sum(cash_cr_amt + trf_cr_amt + clg_cr_amt),0),ifnull(sum(cash_dr_amt + trf_dr_amt + clg_dr_amt),0) from "
                                + "ho_consolidate_entry where dt between '" + minDate + "' and '" + maxDate + "' and substring(acno,3,8) = '" + tmpGLHead + "'").getResultList();
                        ele1 = (Vector) dataList.get(0);
                        crAmt = (ele1.get(0) != null ? ele1.get(0).toString() : "0");
                        drAmt = (ele1.get(1) != null ? ele1.get(1).toString() : "0");
                        curTotal = Double.parseDouble(crAmt) - Double.parseDouble(drAmt);
                    }
                }
                balanceSheetList.add(balDTO);
            }
            return balanceSheetList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List<RbiSossPojo> getBalanceSheet(String reptName, List<String> dates, String orgnCode, BigDecimal repOpt, String summaryOpt, int staffNo) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List osBlancelistIncomeExp = null, osBlancelistIncomeExp1;
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode, dates);

            int misReportSociety = fts.getCodeForReportName("MIS_REPORT_SOCIETY");
            for (int k = 0; k < dates.size(); k++) {
                osBlancelistIncomeExp1 = monthlyRemote.getAsOnDateBalanceBetweenDateList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, rbiReportRemote.getMinFinYear(dates.get(k)), dates.get(k));
                if (k == 0) {
                    osBlancelistIncomeExp = osBlancelistIncomeExp1;
                } else {
                    osBlancelistIncomeExp.addAll(osBlancelistIncomeExp1);
                }
//                System.out.println(k+":List1:"+osBlancelistIncomeExp1.size()+"; Final List2:"+osBlancelistIncomeExp.size());
            }


            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' and '" + dates.get(0) + "' between eff_fr_dt and eff_to_dt order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();

            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                String preFormula = "", npaAcDetails = "";
//                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode,"Y");
                for (int i = 0; i < oss1Query.size(); i++) {
//                    System.out.println("i:"+i);
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String reportName = oss1Vect.get(1).toString();
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    String classification = oss1Vect.get(8).toString();
                    String countApplicable = oss1Vect.get(9).toString();
                    String acNature = oss1Vect.get(10).toString();
                    String acType = oss1Vect.get(11).toString();
                    String glHeadFrom = oss1Vect.get(12).toString();
                    String glHeadTo = oss1Vect.get(13).toString();
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString();
                    String refIndex = oss1Vect.get(24).toString();
                    String refContent = oss1Vect.get(25).toString();
                    Integer cl;
                    if (classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("I")) {
                        cl = 1;
                    } else if (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E")) {
                        cl = -1;
                    }
                    /*Addition ath the first time*/
//                    if (acType == null || acType.equals("")) {
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setAmt(new BigDecimal("0.00"));
                    rbiSossPojo.setSecondAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    String subDescShare = "";
                    if (((acNature == null) || acNature.equalsIgnoreCase("")) && ((acType == null) || acType.equalsIgnoreCase("")) && (((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && ((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) && (!(refIndex == null) || refIndex.equalsIgnoreCase(""))) {
                        for (int m = 0; m < dates.size(); m++) {
                            String dt = dates.get(m);
                            List shareAmt = em.createNativeQuery("select shareamt from  share_value where Effectivedt = (select max(Effectivedt) from  share_value where Effectivedt <= '" + dt + "')").getResultList();
                            Vector shareAmtVect = (Vector) shareAmt.get(0);
                            String shareValue = shareAmtVect.get(0).toString();
                            if (refIndex.equalsIgnoreCase("AC")) {
                                if (m == 0) {
                                    subDescShare = "\n  ";
                                    subDescShare = subDescShare.concat(formatter.format(new BigDecimal(refContent == null ? new DecimalFormat("#.##").format(Double.parseDouble("0")) : new DecimalFormat("#.##").format(Double.parseDouble(refContent))).abs().divide(repOpt).divide(new BigDecimal(shareValue)).doubleValue()).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each "));
                                } else {
                                    subDescShare = subDescShare.concat("\n (").concat(formatter.format(new BigDecimal(refContent == null ? new DecimalFormat("#.##").format(Double.parseDouble("0")) : new DecimalFormat("#.##").format(Double.parseDouble(refContent))).abs().divide(repOpt).divide(new BigDecimal(shareValue)).doubleValue()).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each ")).concat(" in previous year)");
                                }

                                if (!refContent.equalsIgnoreCase("")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(refContent).abs().divide(repOpt));
                                } else {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                }
                            } else if (refIndex.equalsIgnoreCase("IC") || refIndex.equalsIgnoreCase("SC") || refIndex.equalsIgnoreCase("CC")) {
                                if (m == 0) {
                                    subDescShare = "\n  ";
                                    subDescShare = subDescShare.concat(formatter.format(new BigDecimal(refContent == null ? new DecimalFormat("#.##").format(Double.parseDouble("0")) : new DecimalFormat("#.##").format(Double.parseDouble(refContent))).abs().divide(repOpt).divide(new BigDecimal(shareValue)).doubleValue()).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each "));
                                } else {
                                    subDescShare = subDescShare.concat("\n (").concat(formatter.format(new BigDecimal(refContent == null ? new DecimalFormat("#.##").format(Double.parseDouble("0")) : new DecimalFormat("#.##").format(Double.parseDouble(refContent))).abs().divide(repOpt).divide(new BigDecimal(shareValue)).doubleValue()).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each ")).concat(" in previous year)");
                                }
                            }
                        }
                        rbiSossPojo.setDescription(description.concat(" ").concat(subDescShare));
                    }
                    if (summaryOpt.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setgNo(preGNo);
                            rbiSossPojo.setsGNo(preSGNo);
                            rbiSossPojo.setSsGNo(preSsGNo);
                            rbiSossPojo.setSssGNo(preSssGNo);
                            rbiSossPojo.setSsssGNo(preSsssGNo);

                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);

                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                            preZ = 0;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);


                        rbiSossPojo.setDescription(description);

                        rbiPojoTable.add(rbiSossPojo);
                    }
//                    }
                    /*End*/

                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
//                    if (reptName.equalsIgnoreCase("SOSS2")) {
//                        params.setDate(rbiReportRemote.getMinFinYear(dt));
//                    } else {
//                        params.setDate(dt);
//                    }
//                    params.setToDate(dt);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
//                    params.setNpaAcList(resultList);
                    Long noOfAc = 0l;
                    BigDecimal bal = new BigDecimal("0");
                    AcctBalPojo acctBal = new AcctBalPojo();
                    String tmpAcNature = acNature, tmpAcType = acType;
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();

                    if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
                        List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                            List natureList = null;
                            if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                                natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                        + "acctnature in ('" + params.getNature() + "')").getResultList();
                            } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                        + "acctcode in ('" + params.getAcType() + "')").getResultList();
                            }

                            for (int n = 0; n < natureList.size(); n++) {
                                Vector vector = (Vector) natureList.get(n);
                                params.setNature("");
                                params.setAcType(vector.get(0).toString());

                                rbiSossPojo = new RbiSossPojo();
                                GlHeadPojo glPojo = new GlHeadPojo();
                                glPojo.setGlHead(vector.get(0).toString());
                                glPojo.setGlName(vector.get(1).toString());
//                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                    noOfAc = rbiReportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
//                                    glPojo.setBalance(new BigDecimal(noOfAc.toString()));
//                                } else {
//                                    acctBal = rbiReportRemote.getAcctsAndBal(params);
//                                    glPojo.setBalance(acctBal.getBalance());
//                                }
                                //** For Working Capital in the Monthly Progress Report **//
                                if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                    List<String> monthEndList = new ArrayList();
                                    List balList = monthlyRemote.getInsertedGlbList(monthEndList);
                                    BigDecimal avgBal = new BigDecimal(0);
                                    if (fSsGNo.equalsIgnoreCase("GLB")) {
                                        monthEndList = dates;
                                    }
                                    if (!monthEndList.isEmpty()) {
                                        for (int v = 0; v < monthEndList.size(); v++) {
                                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(balList, vector.get(0).toString(), classification, monthEndList.get(v));
                                            if (newBalPojo == null) {
                                                avgBal = avgBal.add(new BigDecimal(0));
                                            } else {
                                                if ((gNo == 1 && classification.equalsIgnoreCase("A")) || (gNo == 5 && classification.equalsIgnoreCase("E"))) {
                                                    avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                                } else if ((gNo == 2 && classification.equalsIgnoreCase("L")) || (gNo == 4 && classification.equalsIgnoreCase("I"))) {
                                                    avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                                } else {
                                                    avgBal = avgBal.add(fSGNo.equalsIgnoreCase("ACT") ? newBalPojo.getBalance().divide(repOpt) : newBalPojo.getBalance().abs().divide(repOpt));
                                                }
                                            }
                                        }
                                    }
                                    if (!avgBal.equals(0)) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal);
                                    }
                                }
                                for (int m = 0; m < dates.size(); m++) {
                                    String dt = dates.get(m);
                                    //                                    params.setDate(dt);
                                    //                                    params.setToDate(dt);
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
//                                    System.out.println("acType:"+vector.get(0).toString()+"; Name:"+vector.get(0).toString()+"; classification:"+classification+"; Date:"+dt+"; Bal:"+newBalPojo.getBalance());
                                    if (newBalPojo == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                    } else {
                                        if (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E") && newBalPojo.getBalance().doubleValue() <= 0) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt).abs());
                                        } else {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt));
                                        }
                                    }
                                }
                                if (misReportSociety == 1) {
                                    for (int m = 0; m < dates.size(); m++) {
                                        String dt = dates.get(m);
                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                            noOfAc = rbiReportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
//                                        rbiSossPojo.setBalance(new BigDecimal(noOfAc.toString()));
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(noOfAc.toString()));
//                                        if (m == 0) {
//                                            rbiSossPojo.setAmt(new BigDecimal(noOfAc.toString()));
//                                        } else if (m == 1) {
//                                            rbiSossPojo.setSecondAmount(new BigDecimal(noOfAc.toString()));
//                                        } else if (m == 2) {
//                                            rbiSossPojo.setThirdAmount(new BigDecimal(noOfAc.toString()));
//                                        }
                                        }
//                                        else {
//                                            acctBal = rbiReportRemote.getAcctsAndBal(params);
//                                            rbiSossPojo.setAmt(acctBal.getBalance());
//                                        }
                                    }
                                }
                                rbiSossPojo.setsNo(sNo);
                                rbiSossPojo.setAcNature(acNature);
                                rbiSossPojo.setAcType(acType);
                                rbiSossPojo.setClassification(classification);
                                rbiSossPojo.setCountApplicable(countApplicable);
                                rbiSossPojo.setFormula(formula);
                                rbiSossPojo.setGlHeadTo(glHeadTo);
                                rbiSossPojo.setGlheadFrom(glHeadFrom);
                                rbiSossPojo.setNpaClassification(npaClassification);
                                rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                                rbiSossPojo.setRangeFrom(rangeFrom);
                                rbiSossPojo.setRangeTo(rangeTo);
                                rbiSossPojo.setReportName(reportName);
                                rbiSossPojo.setgNo(gNo);
                                if (reptName.equalsIgnoreCase("BalSheetDetRBI") || reptName.equalsIgnoreCase("BALSHEETITEMS")) {
                                    if (summaryOpt.equalsIgnoreCase("N")) {
                                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));

                                            if (preSGNo == 0) {
                                                rbiSossPojo.setsGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setsGNo(preSGNo);
                                                if (preSsGNo == 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSsGNo(preSsGNo);
                                                    if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSssGNo(preZ + 1);
                                                    } else {
                                                        rbiSossPojo.setSssGNo(preSssGNo);
                                                        if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                            rbiSossPojo.setSsssGNo(preZ + 1);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(preSsssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = preZ + 1;
                                        } else {
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));

                                            if (sGNo == 0) {
                                                rbiSossPojo.setsGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setsGNo(sGNo);
                                                if (ssGNo == 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setSsGNo(ssGNo);
                                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSssGNo(n + 1);
                                                    } else {
                                                        rbiSossPojo.setSssGNo(sssGNo);
                                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                            rbiSossPojo.setSsssGNo(n + 1);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = n + 1;
                                        }
                                    } else {
                                        rbiSossPojo.setgNo(gNo);
                                        rbiSossPojo.setsGNo(sGNo);
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                        rbiSossPojo.setDescription(description);
                                    }

                                    rbiSossPojo.setfGNo(fGNo);
                                    rbiSossPojo.setfSGNo(fSGNo);
                                    rbiSossPojo.setfSsGNo(fSsGNo);
                                    rbiSossPojo.setfSssGNo(fSssGNo);
                                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                                    rbiPojoTable.add(rbiSossPojo);

                                } else {
                                    if ((rbiSossPojo.getAmt().compareTo(new BigDecimal("0.00")) > 0) || (rbiSossPojo.getSecondAmount().compareTo(new BigDecimal("0.00")) > 0)) {
                                        if (summaryOpt.equalsIgnoreCase("N")) {
                                            if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));

                                                if (preSGNo == 0) {
                                                    rbiSossPojo.setsGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setsGNo(preSGNo);
                                                    if (preSsGNo == 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSsGNo(preZ + 1);
                                                    } else {
                                                        rbiSossPojo.setSsGNo(preSsGNo);
                                                        if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                            rbiSossPojo.setSssGNo(preZ + 1);
                                                        } else {
                                                            rbiSossPojo.setSssGNo(preSssGNo);
                                                            if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                                rbiSossPojo.setSsssGNo(preZ + 1);
                                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                            } else {
                                                                rbiSossPojo.setSsssGNo(preSsssGNo);
                                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                            }
                                                        }
                                                    }
                                                }
                                                preZ = preZ + 1;
                                            } else {
                                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));

                                                if (sGNo == 0) {
                                                    rbiSossPojo.setsGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setsGNo(sGNo);
                                                    if (ssGNo == 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsGNo(n + 1);
                                                    } else {
                                                        rbiSossPojo.setSsGNo(ssGNo);
                                                        if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                            rbiSossPojo.setSssGNo(n + 1);
                                                        } else {
                                                            rbiSossPojo.setSssGNo(sssGNo);
                                                            if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                                rbiSossPojo.setSsssGNo(n + 1);
                                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                            } else {
                                                                rbiSossPojo.setSsssGNo(ssssGNo);
                                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                            }
                                                        }
                                                    }
                                                }
                                                preZ = n + 1;
                                            }
                                        } else {
                                            rbiSossPojo.setgNo(gNo);
                                            rbiSossPojo.setsGNo(sGNo);
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            rbiSossPojo.setSssGNo(sssGNo);
                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                            rbiSossPojo.setDescription(description);
                                        }

                                        rbiSossPojo.setfGNo(fGNo);
                                        rbiSossPojo.setfSGNo(fSGNo);
                                        rbiSossPojo.setfSsGNo(fSsGNo);
                                        rbiSossPojo.setfSssGNo(fSssGNo);
                                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                                        rbiPojoTable.add(rbiSossPojo);
                                    }
                                }
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;
//                                preZ = z;
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                            Date npaDt = null;
                            for (int m = 0; m < dates.size(); m++) {
                                BigDecimal bal2 = new BigDecimal("0");
                                String dt = dates.get(m);
                                if (acNature.contains("NPA")) {
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
                                        resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(), "Y");

                                        resultList.size();
                                        if (m == 0) {
                                            rbiSossPojo.setAmt(new BigDecimal(resultList.size()));
                                        } else if (m == 1) {
                                            rbiSossPojo.setSecondAmount(new BigDecimal(resultList.size()));
                                        } else if (m == 2) {
                                            rbiSossPojo.setThirdAmount(new BigDecimal(resultList.size()));
                                        }

                                    } else {
                                        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
                                        resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(), "Y");
                                        for (int g = 0; g < resultList.size(); g++) {
                                            if (resultList.get(g).getPresentStatus().contains("SUB")) {
                                                npaDt = dmy.parse(resultList.get(g).getSubStandardDt());
                                            } else if (resultList.get(g).getPresentStatus().contains("DOU")) {
                                                npaDt = dmy.parse(resultList.get(g).getDoubtFullDt());
                                            } else if (resultList.get(g).getPresentStatus().contains("LOS")) {
                                                npaDt = dmy.parse(resultList.get(g).getLossDt());
                                            }
                                            Integer value = 19000101;
                                            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
                                            Date frDt = originalFormat.parse(value.toString());
                                            if (npaDt.after(frDt) && (npaDt.equals(ymd.parse(dates.get(m))) || npaDt.before(ymd.parse(dates.get(m))))) {
                                                bal2 = bal2.add(resultList.get(g).getBalance());
                                                String acno1 = resultList.get(g).getAcno();
//                                                 System.out.println("acno=" + acno1);
////                                                 System.out.println(acNature+"-"+params.getAcType()+":"+resultList.get(i).getAcno() +": Bal:"+bal+":Actual bal:"+resultList.get(i).getBalance()+": NpaDt:"+npaDt+": FrDt:"+fromDt+": ToDt:"+toDt+":Status:"+resultList.get(i).getPresentStatus());
//                                                 System.out.println(":"+resultList.get(i).getAcno() +": Bal:"+bal2+":Actual bal:"+resultList.get(i).getBalance());
                                            }

                                        }
                                        if (m == 0) {
                                            rbiSossPojo.setAmt(bal2.divide(repOpt));
                                        } else if (m == 1) {
                                            rbiSossPojo.setSecondAmount(bal2.divide(repOpt));
                                        } else if (m == 2) {
                                            rbiSossPojo.setThirdAmount(bal2.divide(repOpt));
                                        }
                                    }
//                                         System.out.println("BAlance Is After" + bal2);
                                } else if (acNature.contains("OVER")) {

//                                    List<GlHeadPojo> headPojoList = null;
//                                    if (rangeBaseOn.equalsIgnoreCase("D")) {
//                                        headPojoList = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.dateAdd(dates.get(m), new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.dateAdd(dates.get(m), new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dates.get(m), new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
//                                    } else if (rangeBaseOn.equalsIgnoreCase("M")) {
//                                        headPojoList = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.monthAdd(dates.get(m), new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.monthAdd(dates.get(m), new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dates.get(m), new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
//                                    } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
//                                        headPojoList = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.yearAdd(dates.get(m), new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.yearAdd(dates.get(m), new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dates.get(m), new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
//                                    }
//                                    if (countApplicable.equalsIgnoreCase("Y")) {
//                                        noOfAc = new Long(headPojoList.size());
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(noOfAc));
//                                    } else {
//                                        for (int l = 0; l < headPojoList.size(); l++) {
//                                            GlHeadPojo headPojo = headPojoList.get(l);
//                                            bal2 = bal2.add(headPojo.getBalance());
//                                        }
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt).abs());
//                                    }
//                                    if (countApplicable.equalsIgnoreCase("Y")) {
//                                        noOfAc = new Long(headPojoList.size());
//                                    } else {
//                                        for (int l = 0; l < headPojoList.size(); l++) {
//                                            GlHeadPojo headPojo = headPojoList.get(l);
//                                            bal = bal.add(headPojo.getBalance());
//                                        }
//                                    }
//                                    acctBal = new AcctBalPojo();
//                                    acctBal.setNumberOFAcct(noOfAc);
//                                    acctBal.setBalance(bal);
//                                         System.out.println(acNature+"; Bal:"+bal);
                                    bal = new BigDecimal(0);
                                    List<OverdueEmiReportPojo> headPojoList1 = null;
                                    headPojoList1 = overDueReportFacade.getOverdueEmiReport(1, 0, "", "All", 1, 5000, dt, "0A", "A", false, "", "N");
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = new Long(headPojoList1.size());
                                        if (m == 0) {
                                            rbiSossPojo.setAmt(new BigDecimal(noOfAc));
                                        } else if (m == 1) {
                                            rbiSossPojo.setSecondAmount(new BigDecimal(noOfAc));
                                        } else if (m == 2) {
                                            rbiSossPojo.setThirdAmount(new BigDecimal(noOfAc));
                                        }
                                    } else {
                                        for (int l = 0; l < headPojoList1.size(); l++) {
                                            OverdueEmiReportPojo headPojo = headPojoList1.get(l);
                                            bal = bal.add(headPojo.getAmountOverdue());
                                        }
                                        if (m == 0) {
                                            rbiSossPojo.setAmt(bal.divide(repOpt));
                                        } else if (m == 1) {
                                            rbiSossPojo.setSecondAmount(bal.divide(repOpt));
                                        } else if (m == 2) {
                                            rbiSossPojo.setThirdAmount(bal.divide(repOpt));
                                        }
                                    }
                                } else if (acNature.contains("staff")) {
                                    // rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(50));
                                    if (m == 0) {
                                        rbiSossPojo.setAmt(new BigDecimal(staffNo));
                                    } else if (m == 1) {
                                        rbiSossPojo.setSecondAmount(new BigDecimal(staffNo));
                                    } else if (m == 2) {
                                        rbiSossPojo.setThirdAmount(new BigDecimal(staffNo));
                                    }
                                } else if (acNature.contains("folio")) {
                                    // rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(50));
                                    List folioNoList = em.createNativeQuery("select * from share_holder where Regdate <='" + dt + "'").getResultList();
                                    folioNoList.size();
                                    if (m == 0) {
                                        rbiSossPojo.setAmt(new BigDecimal(folioNoList.size()));
                                    } else if (m == 1) {
                                        rbiSossPojo.setSecondAmount(new BigDecimal(folioNoList.size()));
                                    } else if (m == 2) {
                                        rbiSossPojo.setThirdAmount(new BigDecimal(folioNoList.size()));
                                    }
                                } else if (fGNo.contains("Y")) {
                                    acctBal = rbiReportRemote.getNpaBasedOnAllNpaStatus(params);
                                    bal = acctBal.getBalance();
                                } else {
                                    acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
                                    bal = acctBal.getBalance();
                                }
                            }
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = rbiReportRemote.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = rbiReportRemote.getNpaAcctsAndBal(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            }

                            rbiSossPojo = new RbiSossPojo();
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                rbiSossPojo.setAmt(new BigDecimal(noOfAc.toString()));
                            } else {
                                if ((gNo == 1 && (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E"))) || ((gNo == 5 && classification.equalsIgnoreCase("E")))) {
                                    rbiSossPojo.setAmt(bal);
                                } else if ((gNo == 2 && (classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("I"))) || ((gNo == 4 && classification.equalsIgnoreCase("I")))) {
                                    rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
                                } else {
                                    rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : bal.abs());
                                }
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                            rbiSossPojo.setRangeFrom(rangeFrom);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                        }
                    } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8),acname").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            rbiSossPojo = new RbiSossPojo();
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                List shareAmt = em.createNativeQuery("select shareamt from  share_value where Effectivedt = (select max(Effectivedt) from  share_value where Effectivedt <= '" + dt + "')").getResultList();
                                Vector shareAmtVect = (Vector) shareAmt.get(0);
                                String shareValue = shareAmtVect.get(0).toString();
                                if (refIndex.equalsIgnoreCase("AC")) {
                                    if (m == 0) {
                                        subDescShare = "\n  ";
                                        subDescShare = subDescShare.concat(new DecimalFormat("#.##").format(new BigDecimal(refContent == null ? "0" : refContent).abs().divide(repOpt).divide(new BigDecimal(shareValue))).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each "));
                                    } else {
                                        subDescShare = subDescShare.concat("\n (").concat(new DecimalFormat("#.##").format(new BigDecimal(refContent == null ? "0" : refContent).abs().divide(repOpt).divide(new BigDecimal(shareValue))).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each ")).concat(" in previous year )");
                                    }

                                    if (!refContent.equalsIgnoreCase("")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(refContent).abs().divide(repOpt));
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                    }
                                } else {
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                    } else {
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().abs().divide(repOpt));
                                        if ((gNo == 1 && classification.equalsIgnoreCase("A")) || (gNo == 5 && classification.equalsIgnoreCase("E"))) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt));
                                        } else if ((gNo == 2 && classification.equalsIgnoreCase("L")) || (gNo == 5 && classification.equalsIgnoreCase("E"))) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().multiply((new BigDecimal("-1"))).divide(repOpt));
                                        } else {
                                            rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().abs().divide(repOpt));
                                        }
                                        if (refIndex.equalsIgnoreCase("IC") || refIndex.equalsIgnoreCase("SC") || refIndex.equalsIgnoreCase("CC")) {
                                            if (m == 0) {
                                                subDescShare = "\n  ";
                                                subDescShare = subDescShare.concat(new DecimalFormat("#.##").format(newBalPojo.getBalance().abs().divide(repOpt).divide(new BigDecimal(shareValue)).doubleValue()).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each "));
                                            } else {
                                                String shareDesc = "year";
                                                if (misReportSociety == 1 && reptName.contains("mis_report_uday_society")) {
                                                    shareDesc = "day";
                                                }
                                                subDescShare = subDescShare.concat("\n (").concat(new DecimalFormat("#.##").format(newBalPojo.getBalance().abs().divide(repOpt).divide(new BigDecimal(shareValue)).doubleValue()).toString().concat(" Shares of Rs ").concat(new DecimalFormat("#.##").format(Double.parseDouble(shareValue))).concat("/- each ")).concat(" in previous " + shareDesc + " )");
                                            }
                                        }
                                    }
                                }
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
//                                rbiSossPojo.setRangeBaseOn(rangeBaseOn);
//                                rbiSossPojo.setRangeFrom(rangeFrom);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            if (reptName.equalsIgnoreCase("BalSheetDetRBI") || reptName.equalsIgnoreCase("BALSHEETITEMS")) {
                                if (summaryOpt.equalsIgnoreCase("N")) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(subDescShare));
                                        rbiSossPojo.setsGNo(sGNo);
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                        if (preSGNo == 0) {
                                            rbiSossPojo.setsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(preSGNo);
                                            if (preSsGNo == 0 && preSGNo != 0) {
                                                rbiSossPojo.setSsGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(preSsGNo);
                                                if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSssGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(preSssGNo);
                                                    if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(preZ + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                        rbiSossPojo.setsGNo(sGNo);
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                        if (sGNo == 0) {
                                            rbiSossPojo.setsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(sGNo);
                                            if (ssGNo == 0 && sGNo != 0) {
                                                rbiSossPojo.setSsGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(ssGNo);
                                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSssGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(sssGNo);
                                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(n + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = n + 1;
                                    }
                                } else {
                                    rbiSossPojo.setgNo(gNo);
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                    rbiSossPojo.setDescription(description);
                                }


                                rbiSossPojo.setfGNo(fGNo);
                                rbiSossPojo.setfSGNo(fSGNo);
                                rbiSossPojo.setfSsGNo(fSsGNo);
                                rbiSossPojo.setfSssGNo(fSssGNo);
                                rbiSossPojo.setfSsssGNo(fSsssGNo);

                                rbiPojoTable.add(rbiSossPojo);
                            } else {
                                if ((rbiSossPojo.getAmt().compareTo(new BigDecimal("0.00")) > 0) || (rbiSossPojo.getSecondAmount().compareTo(new BigDecimal("0.00")) > 0)) {
                                    if (summaryOpt.equalsIgnoreCase("N")) {
                                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(subDescShare));
                                            rbiSossPojo.setsGNo(sGNo);
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            rbiSossPojo.setSssGNo(sssGNo);
                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                            if (preSGNo == 0) {
                                                rbiSossPojo.setsGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setsGNo(preSGNo);
                                                if (preSsGNo == 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSsGNo(preSsGNo);
                                                    if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSssGNo(preZ + 1);
                                                    } else {
                                                        rbiSossPojo.setSssGNo(preSssGNo);
                                                        if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                            rbiSossPojo.setSsssGNo(preZ + 1);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = preZ + 1;
                                        } else {
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                            rbiSossPojo.setsGNo(sGNo);
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            rbiSossPojo.setSssGNo(sssGNo);
                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                            if (sGNo == 0) {
                                                rbiSossPojo.setsGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setsGNo(sGNo);
                                                if (ssGNo == 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setSsGNo(ssGNo);
                                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSssGNo(n + 1);
                                                    } else {
                                                        rbiSossPojo.setSssGNo(sssGNo);
                                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                            rbiSossPojo.setSsssGNo(n + 1);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = n + 1;
                                        }
                                    } else {
                                        rbiSossPojo.setgNo(gNo);
                                        rbiSossPojo.setsGNo(sGNo);
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                        rbiSossPojo.setDescription(description);
                                    }


                                    rbiSossPojo.setfGNo(fGNo);
                                    rbiSossPojo.setfSGNo(fSGNo);
                                    rbiSossPojo.setfSsGNo(fSsGNo);
                                    rbiSossPojo.setfSssGNo(fSssGNo);
                                    rbiSossPojo.setfSsssGNo(fSsssGNo);

                                    rbiPojoTable.add(rbiSossPojo);
                                }
                            }
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;

                        }
                    } else if (fGNo.contains("PRIOR") || fGNo.contains("NONPR") && ((glHeadFrom.equalsIgnoreCase("")) && (glHeadTo.equalsIgnoreCase("")) && (acNature.equalsIgnoreCase("")) && (acType.equalsIgnoreCase("")))) {
                        List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
                        for (int l = 0; l < dates.size(); l++) {
                            rbiSossPojo = new RbiSossPojo();
                            Long totAccountNo = 0l, nAccountNo = 0l;
                            if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                                BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0);
                                resultList = new ArrayList<LoanMisCellaniousPojo>();
                                resultList = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0", dates.get(l), "A", 0, 999999999999.99, "S",
                                        fGNo.equalsIgnoreCase("") ? "0" : fGNo, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
                                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                                for (int k = 0; k < resultList.size(); k++) {
                                    LoanMisCellaniousPojo val = resultList.get(k);
                                    totAmount = totAmount.add(val.getOutstanding());
                                }
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, totAmount.divide(repOpt).abs());
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                            rbiSossPojo.setRangeFrom(rangeFrom);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            if (summaryOpt.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    if (preSGNo == 0) {
                                        rbiSossPojo.setsGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(preSGNo);
                                        if (preSsGNo == 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(preSsGNo);
                                            if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                rbiSossPojo.setSssGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(preSssGNo);
                                                if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(preZ + 1);
//                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo);
//                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    if (sGNo == 0) {
                                        rbiSossPojo.setsGNo(l + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(sGNo);
                                        if (ssGNo == 0 && sGNo != 0) {
                                            rbiSossPojo.setSsGNo(l + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSssGNo(l + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(sssGNo);
                                                if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(l + 1);
//                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo);
//                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = l + 1;
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                            }

                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                        }
                    } else if (fSssGNo.contains("ROD")) {
                        rbiSossPojo = new RbiSossPojo();
                        List list = em.createNativeQuery("select REFER_CONTENT ,description from cbs_ho_rbi_stmt_report where report_name ='BALSHEETITEMS' and F_SSS_GNO='ROD' "
                                + "and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT  ").getResultList();
                        for (int n = 0; n < dates.size(); n++) {
                            Vector listVect = (Vector) list.get(0);
                            rbiSossPojo.setAmt(new BigDecimal(listVect.get(0).toString()));
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;
                        }
                    } else if (!(fSssGNo.equalsIgnoreCase("")) || !(fSsGNo.equalsIgnoreCase("")) || !(fSGNo.equalsIgnoreCase("")) || !(fGNo.equalsIgnoreCase(""))) {
                        for (int l = 0; l < dates.size(); l++) {
                            rbiSossPojo = new RbiSossPojo();
                            Long totAccountNo = 0l, nAccountNo = 0l;
                            BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0);
                            if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                                List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
                                resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                        dates.get(l), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "O",
                                        fGNo.equalsIgnoreCase("") ? "0" : fGNo, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", fSsGNo.equalsIgnoreCase("") ? "0" : fSsGNo,
                                        fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "0", "0", "0",
                                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                                for (int k = 0; k < resultList.size(); k++) {
//                                    LoanMisCellaniousPojo val = resultList.get(k);
//                                    totAmount = totAmount.add(val.getOutstanding());
                                    if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        totAmount = totAmount.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                    } else {
                                        totAmount = totAmount.add(resultList.get(k).getOutstanding());
                                    }
                                }
//                                System.out.println("Total Amount "+totAmount);
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, totAmount.divide(repOpt).abs());
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                            rbiSossPojo.setRangeFrom(rangeFrom);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            if (summaryOpt.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    if (preSGNo == 0) {
                                        rbiSossPojo.setsGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(preSGNo);
                                        if (preSsGNo == 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(preSsGNo);
                                            if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                rbiSossPojo.setSssGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(preSssGNo);
                                                if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo);
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    if (sGNo == 0) {
                                        rbiSossPojo.setsGNo(l + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(sGNo);
                                        if (ssGNo == 0 && sGNo != 0) {
                                            rbiSossPojo.setSsGNo(l + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSssGNo(l + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(sssGNo);
                                                if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(l + 1);
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                                }
                                            }
                                        }
                                    }
                                    preZ = l + 1;
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                            }
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                        }
                    } else if (refIndex.contains("ODL")) {
                        for (int l = 0; l < dates.size(); l++) {
                            BigDecimal totalNpa = new BigDecimal("0");
                            List<NpaStatusReportPojo> npaResultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", dates.get(l), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                            for (int n = 0; n < npaResultList.size(); n++) {
                                totalNpa = totalNpa.add(npaResultList.get(n).getBalance());
                            }
                            if (!totalNpa.equals(0)) {
                                rbiSossPojo.setAmt(totalNpa.divide(repOpt).abs());
                            } else {
                                rbiSossPojo.setAmt(totalNpa);
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;
                        }
                    } else if (refIndex.contains("NPA") && (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D"))) {
//                        for(int k=0;k< dates.size() ; k++){
//                            String date = dates.get(k);
//                            acctBal = rbiReportRemote.getNpaBasedOnAllNpaStatus(params);
//                            rbiSossPojo.setAmt(acctBal.getBalance());
//                        }
                        Date npaDt = null;
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            String dt = dates.get(m);
//                             if (acNature.contains("1AAA")) {
                            List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
                            List<NpaStatusReportPojo> resultList1 = new ArrayList<NpaStatusReportPojo>();
//                                 resultList1 = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal("19000101", dt, acNature, acType, "0", dt, rangeFrom, rangeTo);
                            resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", dt, "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(), "Y");
                            for (int g = 0; g < resultList.size(); g++) {
                                if (resultList.get(g).getPresentStatus().contains("SUB")) {
                                    npaDt = dmy.parse(resultList.get(g).getSubStandardDt());
                                } else if (resultList.get(g).getPresentStatus().contains("DOU")) {
                                    npaDt = dmy.parse(resultList.get(g).getDoubtFullDt());
                                } else if (resultList.get(g).getPresentStatus().contains("LOS")) {
                                    npaDt = dmy.parse(resultList.get(g).getLossDt());
                                }
                                Integer value = 19000101;
                                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
                                Date frDt = originalFormat.parse(value.toString());
                                if (npaDt.after(frDt) && (npaDt.equals(ymd.parse(dates.get(m))) || npaDt.before(ymd.parse(dates.get(m))))) {
                                    bal2 = bal2.add(resultList.get(g).getBalance());
//                                         String acno1 =resultList.get(g).getAcno();
//                                         System.out.println("acno=" + acno1);
//                                         System.out.println(":"+resultList.get(i).getAcno() +": Bal:"+bal2+":Actual bal:"+resultList.get(i).getBalance());
                                }
                                bal = bal2;
                            }
//                                     if (rangeBaseOn.equalsIgnoreCase("Rs")) {
//                                        acctBal = rbiReportRemote.getAcctsAndBalAmtRangeWise(params);
//                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
//                                     } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
//                                        acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
//                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
//                                     } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
//                                        acctBal = rbiReportRemote.getNpaAcctsAndBal(params);
//                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
//                                     }
                            if (m == 0) {
                                rbiSossPojo.setAmt(bal2.abs());
                            } else if (m == 1) {
                                rbiSossPojo.setSecondAmount(bal2.abs());
                            }
//                                 System.out.println("BAlance Is After" + bal2 +"& Date Is " + dates.get(m));
//                             }
                        }
                    }
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo;
                    preFormula = formula;
                }
//                for (int i = 0; i < rbiPojoTable.size(); i++) {
//                    System.out.println(rbiPojoTable.get(i).getsNo() + ";" + rbiPojoTable.get(i).getReportName() + ";" + rbiPojoTable.get(i).getDescription() + ";" + rbiPojoTable.get(i).getgNo()
//                            + ";" + rbiPojoTable.get(i).getsGNo() + ";" + rbiPojoTable.get(i).getSsGNo() + ";" + rbiPojoTable.get(i).getSssGNo() + ";" + rbiPojoTable.get(i).getSsssGNo()
//                            + ";" + rbiPojoTable.get(i).getClassification() + ";" + rbiPojoTable.get(i).getCountApplicable() + ";" + rbiPojoTable.get(i).getAcNature()
//                            + ";" + rbiPojoTable.get(i).getAcType() + ";" + rbiPojoTable.get(i).getNpaClassification() + ";" + rbiPojoTable.get(i).getGlheadFrom()
//                            + ";" + rbiPojoTable.get(i).getGlHeadTo() + ";" + rbiPojoTable.get(i).getFormula() + ";>>>>>>>" + rbiPojoTable.get(i).getAmt() + ";" + rbiPojoTable.get(i).getSecondAmount()
//                            + ";" + rbiPojoTable.get(i).getThirdAmount() + ";" + rbiPojoTable.get(i).getFourthAmount() + ";" + rbiPojoTable.get(i).getFifthAmount()
//                            + ";" + rbiPojoTable.get(i).getSixthAmount() + ";" + rbiPojoTable.get(i).getSeventhAmount());
//                }                
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal bal = new BigDecimal("0.0");
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                double balPL = glReport.IncomeExp(dt, "0A", "0A");
                                if ((rbiSossPojo.getClassification().equalsIgnoreCase("L") || rbiSossPojo.getClassification().equalsIgnoreCase("I")) && balPL >= 0) {
//                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    if (rbiSossPojo.getgNo().equals(Integer.parseInt("2"))) {
                                        balPL = balPL * -1;
                                    }
                                    if (m == 0) {
                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else {
                                        rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    }
                                } else if ((rbiSossPojo.getClassification().equalsIgnoreCase("A") || rbiSossPojo.getClassification().equalsIgnoreCase("E")) && balPL < 0) {
                                    if (reptName.contains("OSS1") && rbiSossPojo.getgNo() == 1) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
//                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else {
                                        //rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        if (m == 0) {
                                            rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        } else {
                                            rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        }
                                    }
                                } else {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                }
                            }
//                        } else if (rbiSossPojo.getFormula().contains("OSS7")) {
//                            String[] strArr = rbiSossPojo.getFormula().split("#");
//                            for(int l = 0; l< dates.size();l++){
//                                bal = new BigDecimal(0);
//                                List<RbiSossPojo> oss7PartAList = quarterlyRemote.getOss7PartA("OSS7-PART-A", dates.get(l), orgnCode, new BigDecimal("1"), "Y", osBlancelist);
//                                bal = bal.add(rbiReportRemote.getValueFromFormula(oss7PartAList, strArr[1]));
//                            }
                        } else if (rbiSossPojo.getFormula().contains("OSS7")) {
                            for (int m = 0; m < dates.size(); m++) {
                                List<RbiSossPojo> oss7PartAList = quarterlyRemote.getOss7PartA("OSS7-PART-A", dates.get(m), orgnCode, repOpt, "Y", osBlancelist, "Y");
                                String[] strArr = rbiSossPojo.getFormula().split("#");
                                bal = rbiReportRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                                bal = bal.abs();
                                if (m == 0) {
                                    rbiSossPojo.setAmt(bal);
                                } else {
                                    rbiSossPojo.setSecondAmount(bal);
                                }
                            }

                        } else {
                            BigDecimal[] arr = monthlyRemote.getValueFromFormulaForFormOne(rbiPojoTable, rbiSossPojo.getFormula(), dates);
                            rbiSossPojo.setAmt(arr[0]);
                            rbiSossPojo.setSecondAmount(arr[1]);
                            rbiSossPojo.setThirdAmount(arr[2]);
                            rbiSossPojo.setFourthAmount(arr[3]);
                            rbiSossPojo.setFifthAmount(arr[4]);
                            rbiSossPojo.setSixthAmount(arr[5]);
                            rbiSossPojo.setSeventhAmount(arr[6]);
//                            bal = bal.add(rbiReportRemote.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula()));
                            String desc = "(LIABILITY SIDE)";
                            if (misReportSociety == 1 && reptName.contains("mis_report_uday_society")) {
                                desc = "";
                            }
                            rbiSossPojo.setDescription(rbiSossPojo.getDescription().concat(rbiSossPojo.getClassification().equalsIgnoreCase("L") ? "(ASSET SIDE)" : desc));
//                            rbiSossPojo.setAmt(bal);
                        }
                    }
                }
            }
//            for (int i = 0; i < rbiPojoTable.size(); i++) {
//                System.out.println(rbiPojoTable.get(i).getsNo() + ";" + rbiPojoTable.get(i).getReportName() + ";" + rbiPojoTable.get(i).getDescription() + ";" + rbiPojoTable.get(i).getgNo()
//                        + ";" + rbiPojoTable.get(i).getsGNo() + ";" + rbiPojoTable.get(i).getSsGNo() + ";" + rbiPojoTable.get(i).getSssGNo() + ";" + rbiPojoTable.get(i).getSsssGNo()
//                        + ";" + rbiPojoTable.get(i).getClassification() + ";" + rbiPojoTable.get(i).getCountApplicable() + ";" + rbiPojoTable.get(i).getAcNature()
//                        + ";" + rbiPojoTable.get(i).getAcType() + ";" + rbiPojoTable.get(i).getNpaClassification() + ";" + rbiPojoTable.get(i).getGlheadFrom()
//                        + ";" + rbiPojoTable.get(i).getGlHeadTo() + ";" + rbiPojoTable.get(i).getRangeBaseOn() + ";" + rbiPojoTable.get(i).getRangeFrom()
//                        + ";" + rbiPojoTable.get(i).getRangeTo() + ";" + rbiPojoTable.get(i).getFormula() + ";" + rbiPojoTable.get(i).getfGNo()
//                        + ";" + rbiPojoTable.get(i).getfSGNo() + ";" + rbiPojoTable.get(i).getfSsGNo() + ";" + rbiPojoTable.get(i).getfSssGNo()
//                        + ";" + rbiPojoTable.get(i).getfSsssGNo() + ";" + rbiPojoTable.get(i).getAmt() + ";" + rbiPojoTable.get(i).getSecondAmount()
//                        + ";" + rbiPojoTable.get(i).getThirdAmount() + ";" + rbiPojoTable.get(i).getFourthAmount() + ";" + rbiPojoTable.get(i).getFifthAmount()
//                        + ";" + rbiPojoTable.get(i).getSixthAmount() + ";" + rbiPojoTable.get(i).getSeventhAmount());
//            }
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RbiSossPojo> getMisDataToMgmt(String reptName, List<String> dates, String orgnCode, BigDecimal repOpt, String summaryOpt, int noOfEmpPreDate, int noOfEmpAsOnDate) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List osBlancelistIncomeExp = null, osBlancelistIncomeExp1;
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode, dates);
            /**
             * For Balance as on Previous Financial Year *
             */
            YearEndDatePojo yDate1 = new YearEndDatePojo();
            YearEndDatePojo yDate2 = new YearEndDatePojo();
            List dataList1 = null, dataList2 = null;
            /* In this case we will get the dataList in Rs and amount will be divided by report option at the time of setting the aamounts*/
            for (int k = 0; k < dates.size(); k++) {
                if (k == 0) {
                    yDate1 = (YearEndDatePojo) rbiReportRemote.getYearEndDataAccordingToDate("90", dates.get(k));
                    String minFDate1 = yDate1.getMinDate();
                    dataList1 = rbiSoss1Remote.getSOSS2("SOSS1", CbsUtil.dateAdd(minFDate1, -1), "0A", new BigDecimal("1"), summaryOpt, osBlancelist, "0");
                } else {
                    yDate2 = (YearEndDatePojo) rbiReportRemote.getYearEndDataAccordingToDate("90", dates.get(k));
                    String minFDate2 = yDate2.getMinDate();
                    dataList2 = rbiSoss1Remote.getSOSS2("SOSS1", CbsUtil.dateAdd(minFDate2, -1), "0A", new BigDecimal("1"), summaryOpt, osBlancelist, "0");
                }
            }
            for (int k = 0; k < dates.size(); k++) {
                osBlancelistIncomeExp1 = monthlyRemote.getAsOnDateBalanceBetweenDateList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, rbiReportRemote.getMinFinYear(dates.get(k)), dates.get(k));
                if (k == 0) {
                    osBlancelistIncomeExp = osBlancelistIncomeExp1;
                } else {
                    osBlancelistIncomeExp.addAll(osBlancelistIncomeExp1);
                }
            }
            List<OverdueNonEmiResultList> finalNonEMIList1 = new ArrayList<OverdueNonEmiResultList>();
            List<OverdueNonEmiResultList> finalNonEMIList2 = new ArrayList<OverdueNonEmiResultList>();
            List finalEMIList1 = new ArrayList();
            List finalEMIList2 = new ArrayList();
            List acnature = em.createNativeQuery("select acctcode from accounttypemaster where acctnature ='" + CbsConstant.CURRENT_AC + "' and crdbflag in ('B','D')").getResultList();
            if (!acnature.isEmpty()) {
                for (int n = 0; n < acnature.size(); n++) {
                    Vector vect = (Vector) acnature.get(n);
                    String acType = vect.get(0).toString();
                    for (int k = 0; k < dates.size(); k++) {
                        List<OverdueNonEmiResultList> reportList = new ArrayList<OverdueNonEmiResultList>();
                        reportList = overDueReportFacade.getOverDueNonEmi("CA", acType, dates.get(k), "0A", "N");
                        if (k == 0) {
                            if (!reportList.isEmpty()) {
                                finalNonEMIList1.addAll(reportList);
                            }
                        } else {
                            if (!reportList.isEmpty()) {
                                finalNonEMIList2.addAll(reportList);
                            }
                        }
                    }
                }
            }
            for (int k = 0; k < dates.size(); k++) {
                List<OverdueNonEmiResultList> reportListDL = new ArrayList<OverdueNonEmiResultList>();
                List<OverdueNonEmiResultList> reportListTl = new ArrayList<OverdueNonEmiResultList>();
                List<OverdueEmiReportPojo> reportListEMI = new ArrayList<OverdueEmiReportPojo>();
                reportListDL = overDueReportFacade.getOverDueNonEmi("DL", "ALL", dates.get(k), "0A", "N");
                reportListTl = overDueReportFacade.getOverDueNonEmi("TL", "ALL", dates.get(k), "0A", "N");
                reportListEMI = overDueReportFacade.getOverdueEmiReport(Integer.parseInt("1"), Integer.parseInt("0"), "", "ALL", Integer.parseInt("0"), Integer.parseInt("2"), dates.get(k), "0A", "", true, "", "N");
                if (k == 0) {
                    if (!reportListDL.isEmpty()) {
                        finalNonEMIList1.addAll(reportListDL);
                    }
                    if (!reportListTl.isEmpty()) {
                        finalNonEMIList1.addAll(reportListTl);
                    }
                    if (!reportListEMI.isEmpty()) {
                        finalEMIList1.addAll(reportListEMI);
                    }
                } else {
                    if (!reportListDL.isEmpty()) {
                        finalNonEMIList2.addAll(reportListDL);
                    }
                    if (!reportListTl.isEmpty()) {
                        finalNonEMIList2.addAll(reportListTl);
                    }
                    if (!reportListEMI.isEmpty()) {
                        finalEMIList2.addAll(reportListEMI);
                    }
                }
            }
            List<NpaAccountDetailPojo> npaListFirst = null, npaListSecond = null;
            String reportIn = "";
            if (repOpt.compareTo(new BigDecimal(1)) == 0) {
                reportIn = "R";
            } else if (repOpt.compareTo(new BigDecimal(1000)) == 0) {
                reportIn = "T";
            } else if (repOpt.compareTo(new BigDecimal(100000)) == 0) {
                reportIn = "L";
            } else if (repOpt.compareTo(new BigDecimal(10000000)) == 0) {
                reportIn = "C";
            }
            List<CrrSlrPojo> crrFirst = null, crrSecond = null, slrFirst = null, slrSecond = null;
            for (int k = 0; k < dates.size(); k++) {
                if (k == 0) {
                    crrFirst = getCrrDetails("0A", reportIn, dmy.format(ymd.parse(dates.get(k))), dmy.format(ymd.parse(dates.get(k))), "ALL");
                    slrFirst = getSlrDetails("0A", reportIn, dmy.format(ymd.parse(dates.get(k))), dmy.format(ymd.parse(dates.get(k))), "ALL");
                } else {
                    crrSecond = getCrrDetails("0A", reportIn, dmy.format(ymd.parse(dates.get(k))), dmy.format(ymd.parse(dates.get(k))), "ALL");
                    slrSecond = getSlrDetails("0A", reportIn, dmy.format(ymd.parse(dates.get(k))), dmy.format(ymd.parse(dates.get(k))), "ALL");
                }
            }
            List<TopDepositorBorrowerPojo> topListFirst = null, topListSecond = null;
            List<LoanAcDetailsTable> loanAcDetailFirst = null, loanAcDetailSecond = null;
            for (int k = 0; k < dates.size(); k++) {
                if (k == 0) {
                    npaListFirst = overDueReportFacade.getNpaDetail("ALL", "ALL", dates.get(k), "0A", "Y");
                    topListFirst = miscReportFacade.getTopDepositorBorrower("0A", "Sanction Wise", "19000101", dates.get(k), 1, "Customer Id", "Bank", "", 0d, 99999999999999d);
                    loanAcDetailFirst = loanReportFacade.loanAcDetailsReport("ALL", dates.get(k), "0A");
                } else {
                    topListSecond = miscReportFacade.getTopDepositorBorrower("0A", "Sanction Wise", "19000101", dates.get(k), 1, "Customer Id", "Bank", "", 0d, 99999999999999d);
                    loanAcDetailSecond = loanReportFacade.loanAcDetailsReport("ALL", dates.get(k), "0A");
                    npaListSecond = overDueReportFacade.getNpaDetail("ALL", "ALL", dates.get(k), "0A", "Y");
                }
            }
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                String preFormula = "", npaAcDetails = "";
                for (int i = 0; i < oss1Query.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String reportName = oss1Vect.get(1).toString();
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    String classification = oss1Vect.get(8).toString();
                    String countApplicable = oss1Vect.get(9).toString();
                    String acNature = oss1Vect.get(10).toString();
                    String acType = oss1Vect.get(11).toString();
                    String glHeadFrom = oss1Vect.get(12).toString();
                    String glHeadTo = oss1Vect.get(13).toString();
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString();
                    String refIndex = oss1Vect.get(24).toString();
                    String refContent = oss1Vect.get(25).toString();
                    Integer cl;
                    if (classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("I")) {
                        cl = 1;
                    } else if (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E")) {
                        cl = -1;
                    }
                    /*Addition ath the first time*/
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setAmt(new BigDecimal("0.00"));
                    rbiSossPojo.setSecondAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setRefIndex(refIndex);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    String subDescShare = "";
                    if (summaryOpt.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setgNo(preGNo);
                            rbiSossPojo.setsGNo(preSGNo);
                            rbiSossPojo.setSsGNo(preSsGNo);
                            rbiSossPojo.setSssGNo(preSssGNo);
                            rbiSossPojo.setSsssGNo(preSsssGNo);

                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);

                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                            preZ = 0;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiPojoTable.add(rbiSossPojo);
                    }
                    /*End*/
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
                    Long noOfAc = 0l;
                    BigDecimal bal = new BigDecimal("0");
                    AcctBalPojo acctBal = new AcctBalPojo();
                    String tmpAcNature = acNature, tmpAcType = acType;
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                    if ((!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) && refIndex.equalsIgnoreCase("")) {
                        List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                            List natureList = null;
                            if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                                natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                        + "acctnature in ('" + params.getNature() + "')").getResultList();
                            } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                        + "acctcode in ('" + params.getAcType() + "')").getResultList();
                            }

                            for (int n = 0; n < natureList.size(); n++) {
                                Vector vector = (Vector) natureList.get(n);
                                params.setNature("");
                                params.setAcType(vector.get(0).toString());
                                rbiSossPojo = new RbiSossPojo();
                                GlHeadPojo glPojo = new GlHeadPojo();
                                glPojo.setGlHead(vector.get(0).toString());
                                glPojo.setGlName(vector.get(1).toString());
                                for (int m = 0; m < dates.size(); m++) {
                                    String dt = dates.get(m);
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                    } else {
                                        if (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E") && newBalPojo.getBalance().doubleValue() <= 0) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt).abs());
                                        } else {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt));
                                        }
                                    }
                                }
                                rbiSossPojo.setsNo(sNo);
                                rbiSossPojo.setAcNature(acNature);
                                rbiSossPojo.setAcType(acType);
                                rbiSossPojo.setClassification(classification);
                                rbiSossPojo.setCountApplicable(countApplicable);
                                rbiSossPojo.setFormula(formula);
                                rbiSossPojo.setGlHeadTo(glHeadTo);
                                rbiSossPojo.setGlheadFrom(glHeadFrom);
                                rbiSossPojo.setNpaClassification(npaClassification);
                                rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                                rbiSossPojo.setRangeFrom(rangeFrom);
                                rbiSossPojo.setRangeTo(rangeTo);
                                rbiSossPojo.setReportName(reportName);
                                rbiSossPojo.setgNo(gNo);
                                if (summaryOpt.equalsIgnoreCase("N")) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));

                                        if (preSGNo == 0) {
                                            rbiSossPojo.setsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(preSGNo);
                                            if (preSsGNo == 0 && preSGNo != 0) {
                                                rbiSossPojo.setSsGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(preSsGNo);
                                                if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSssGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(preSssGNo);
                                                    if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(preZ + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                        if (sGNo == 0) {
                                            rbiSossPojo.setsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(sGNo);
                                            if (ssGNo == 0 && sGNo != 0) {
                                                rbiSossPojo.setSsGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(ssGNo);
                                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSssGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(sssGNo);
                                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(n + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = n + 1;
                                    }
                                } else {
                                    rbiSossPojo.setgNo(gNo);
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                    rbiSossPojo.setDescription(description);
                                }
                                rbiSossPojo.setfGNo(fGNo);
                                rbiSossPojo.setfSGNo(fSGNo);
                                rbiSossPojo.setfSsGNo(fSsGNo);
                                rbiSossPojo.setfSssGNo(fSssGNo);
                                rbiSossPojo.setfSsssGNo(fSsssGNo);
                                rbiPojoTable.add(rbiSossPojo);
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                            Date npaDt = null;
                            rbiSossPojo = new RbiSossPojo();
                            for (int m = 0; m < dates.size(); m++) {
                                BigDecimal bal2 = new BigDecimal("0");
                                String dt = dates.get(m);
                                if (acNature.contains("NPA")) {
                                    List<NpaStatusReportPojo> npaList = new ArrayList<NpaStatusReportPojo>();
                                    npaList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(), "Y");
                                    for (int g = 0; g < npaList.size(); g++) {

                                        if (npaList.get(g).getPresentStatus().contains("SUB")) {
                                            npaDt = dmy.parse(npaList.get(g).getSubStandardDt());
                                        } else if (npaList.get(g).getPresentStatus().contains("DOU")) {
                                            npaDt = dmy.parse(npaList.get(g).getDoubtFullDt());
                                        } else if (npaList.get(g).getPresentStatus().contains("LOS")) {
                                            npaDt = dmy.parse(npaList.get(g).getLossDt());
                                        }
                                        Integer value = 19000101;
                                        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
                                        Date frDt = originalFormat.parse(value.toString());
                                        if (npaDt.after(frDt) && (npaDt.equals(ymd.parse(dates.get(m))) || npaDt.before(ymd.parse(dates.get(m))))) {
                                            bal2 = bal2.add(npaList.get(g).getBalance());
                                            String acno1 = npaList.get(g).getAcno();
                                        }
                                    }
                                    rbiSossPojo.setAmt(bal2);
                                } else if (acNature.contains("OVER")) {
                                    List<GlHeadPojo> headPojoList = null;
                                    rbiSossPojo = new RbiSossPojo();
                                    if (rangeBaseOn.equalsIgnoreCase("D")) {
                                        headPojoList = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.dateAdd(dates.get(m), new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.dateAdd(dates.get(m), new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dates.get(m), new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                    } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                        headPojoList = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.monthAdd(dates.get(m), new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.monthAdd(dates.get(m), new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dates.get(m), new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                    } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                        headPojoList = rbiReportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.yearAdd(dates.get(m), new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.yearAdd(dates.get(m), new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dates.get(m), new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                    }
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = new Long(headPojoList.size());
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(noOfAc));
                                    } else {
                                        for (int l = 0; l < headPojoList.size(); l++) {
                                            GlHeadPojo headPojo = headPojoList.get(l);
                                            bal2 = bal2.add(headPojo.getBalance());
                                        }
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt).abs());
                                    }
                                } else if (fGNo.contains("Y")) {
                                    acctBal = rbiReportRemote.getNpaBasedOnAllNpaStatus(params);
                                    bal = acctBal.getBalance();
                                } else {
                                    acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
                                    bal = acctBal.getBalance();
                                }
                                rbiSossPojo.setsNo(sNo);
                                rbiSossPojo.setAcNature(acNature);
                                rbiSossPojo.setAcType(acType);
                                rbiSossPojo.setClassification(classification);
                                rbiSossPojo.setCountApplicable(countApplicable);
                                rbiSossPojo.setFormula(formula);
                                rbiSossPojo.setGlHeadTo(glHeadTo);
                                rbiSossPojo.setGlheadFrom(glHeadFrom);
                                rbiSossPojo.setNpaClassification(npaClassification);
                                rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                                rbiSossPojo.setRangeFrom(rangeFrom);
                                rbiSossPojo.setRangeTo(rangeTo);
                                rbiSossPojo.setReportName(reportName);
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                                rbiSossPojo.setfGNo(fGNo);
                                rbiSossPojo.setfSGNo(fSGNo);
                                rbiSossPojo.setfSsGNo(fSsGNo);
                                rbiSossPojo.setfSssGNo(fSssGNo);
                                rbiSossPojo.setfSsssGNo(fSsssGNo);
                                rbiPojoTable.add(rbiSossPojo);
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;
                            }
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = rbiReportRemote.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = rbiReportRemote.getNpaAcctsAndBal(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            }
                            rbiSossPojo = new RbiSossPojo();
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                rbiSossPojo.setAmt(new BigDecimal(noOfAc.toString()));
                            } else {
                                if (gNo == 1 && (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E"))) {
                                    rbiSossPojo.setAmt(bal);
                                } else if (gNo == 2 && (classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("I"))) {
                                    rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
                                } else {
                                    rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : bal.abs());
                                }
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                            rbiSossPojo.setRangeFrom(rangeFrom);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                        }
                    } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase(""))) && !(refIndex.equalsIgnoreCase("NNPA"))) {
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8),acname").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            rbiSossPojo = new RbiSossPojo();
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
                                if (newBalPojo == null) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                } else {
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt));
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().multiply((new BigDecimal("-1"))).divide(repOpt));
                                    } else {
                                        rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().abs().divide(repOpt));
                                    }
                                }
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            if (summaryOpt.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(subDescShare));
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                    if (preSGNo == 0) {
                                        rbiSossPojo.setsGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(preSGNo);
                                        if (preSsGNo == 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(preSsGNo);
                                            if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                rbiSossPojo.setSssGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(preSssGNo);
                                                if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(preZ + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                    if (sGNo == 0) {
                                        rbiSossPojo.setsGNo(n + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(sGNo);
                                        if (ssGNo == 0 && sGNo != 0) {
                                            rbiSossPojo.setSsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSssGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(sssGNo);
                                                if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(n + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()).concat(" ").concat(subDescShare));
                                                }
                                            }
                                        }
                                    }
                                    preZ = n + 1;
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                            }
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;
                        }
                    } else if (!(fSssGNo.equalsIgnoreCase("")) || !(fSsGNo.equalsIgnoreCase("")) || !(fSGNo.equalsIgnoreCase("")) || !(fGNo.equalsIgnoreCase(""))) {
                        rbiSossPojo = new RbiSossPojo();
                        for (int l = 0; l < dates.size(); l++) {
                            Long totAccountNo = 0l, nAccountNo = 0l;
                            BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0);
                            if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                                List<LoanMisCellaniousPojo> misList = new ArrayList<LoanMisCellaniousPojo>();
                                misList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                        dates.get(l), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "O",
                                        fGNo.equalsIgnoreCase("") ? "0" : fGNo, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", fSsGNo.equalsIgnoreCase("") ? "0" : fSsGNo,
                                        fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "0", "0", "0",
                                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                                for (int k = 0; k < misList.size(); k++) {
                                    if (misList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        totAmount = totAmount.add(misList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(k).getOutstanding());
                                    } else {
                                        totAmount = totAmount.add(misList.get(k).getOutstanding());
                                    }
                                }
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, totAmount.divide(repOpt).abs());
                            }
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                        preGNo = gNo;
                        preSGNo = sGNo;
                        preSsGNo = ssGNo;
                        preSssGNo = sssGNo;
                        preSsssGNo = ssssGNo;
                        preFormula = formula;

                    } else if (refIndex.equalsIgnoreCase("NDTL") || refIndex.equalsIgnoreCase("CRR") || refIndex.equalsIgnoreCase("SLR")) {
                        Vector ele = null;
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            List<CrrSlrPojo> finalCRRList = null;
                            List<CrrSlrPojo> finalSLRList = null;
                            if (m == 0) {
                                finalCRRList = crrFirst;
                                finalSLRList = slrFirst;
                            } else {
                                finalCRRList = crrSecond;
                                finalSLRList = slrSecond;
                            }
                            if (refIndex.equalsIgnoreCase("NDTL") || refIndex.equalsIgnoreCase("CRR")) {
                                if (!finalCRRList.isEmpty()) {
                                    CrrSlrPojo vec = finalCRRList.get(0);
                                    if (refIndex.equalsIgnoreCase("NDTL")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(vec.getNtdl()));
                                    } else if (refIndex.equalsIgnoreCase("CRR")) {
                                        if (refContent.equalsIgnoreCase("RM")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(vec.getReqToBeMaint()));
                                        } else if (refContent.equalsIgnoreCase("AM")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(vec.getActMaint()));
                                        }
                                    }
                                }
                            } else if (refIndex.equalsIgnoreCase("SLR")) {
                                if (!finalSLRList.isEmpty()) {
                                    CrrSlrPojo vec1 = finalSLRList.get(0);
                                    if (refContent.equalsIgnoreCase("RM")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(vec1.getReqToBeMaint()));
                                    } else if (refContent.equalsIgnoreCase("AM")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(vec1.getActMaint()));
                                    }
                                }
                            }
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (refIndex.equalsIgnoreCase("ALM")) {
                        String frDt = "", toDt = "", bucketNo = "";
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            if (rangeBaseOn.equalsIgnoreCase("D")) {
                                frDt = CbsUtil.dateAdd(dates.get(m), (int) Double.parseDouble(rangeFrom));
                            } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                frDt = CbsUtil.monthAdd(dates.get(m), (int) Double.parseDouble(rangeFrom));
                            } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                frDt = CbsUtil.yearAdd(dates.get(m), (int) Double.parseDouble(rangeFrom));
                            }
                            if (rangeBaseOn.equalsIgnoreCase("D")) {
                                toDt = CbsUtil.dateAdd(dates.get(m), (int) Double.parseDouble(rangeTo));
                            } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                toDt = CbsUtil.monthAdd(dates.get(m), (int) Double.parseDouble(rangeTo));
                            } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                toDt = CbsUtil.yearAdd(dates.get(m), (int) Double.parseDouble(rangeTo));
                            }
                            String rangFrom = String.valueOf((int) Double.parseDouble(rangeFrom));
                            String rangTo = String.valueOf((int) Double.parseDouble(rangeTo));
//                            if (rangeBaseOn.equalsIgnoreCase("D")) {
//                                bucketNo = rangFrom.concat(" To ").concat(rangTo).concat(" Days");
//                            } else if (rangeBaseOn.equalsIgnoreCase("M")) {
//                                bucketNo = rangFrom.concat(" To ").concat(rangTo).concat(" Months");
//                            } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
//                                bucketNo = rangFrom.concat(" To ").concat(rangTo).concat(" Years");
//                            }
                            bucketNo = fSsssGNo;
                            List<AlmFddetailPojo> almFDdetail = almRemote.getAlmFdDetail("0A", acNature, bucketNo, dates.get(m), "N", acType.equalsIgnoreCase("") ? "ALL" : acType, frDt, toDt, "D");
                            if (!almFDdetail.isEmpty()) {
                                for (int l = 0; l < almFDdetail.size(); l++) {
                                    AlmFddetailPojo vec = almFDdetail.get(l);
                                    bal2 = bal2.add(new BigDecimal(vec.getAmount()));
                                }
                            }
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt).abs());
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (refContent.equalsIgnoreCase("GOI:GOVERNMENT SECURITIES:HM") || refContent.equalsIgnoreCase("GOI:GOVERNMENT SECURITIES:AF  ") || refContent.equalsIgnoreCase("GOI:GOVERNMENT SECURITIES:HT")) {
                        rbiSossPojo = new RbiSossPojo();
                        if (glHeadFrom.contains(":")) {
                            for (int m = 0; m < dates.size(); m++) {
                                BigDecimal bal2 = new BigDecimal("0");
                                String[] a = glHeadFrom.split(":");
                                List<InvestmentMaster> invstmntList = investRptMgmtFacade.getIndivisualReportTypeAllSellarNameAndMArking(a[1], a[2].equalsIgnoreCase("") ? "0" : a[2], ymd.parse(dates.get(m)));
                                if (!invstmntList.isEmpty()) {
                                    for (InvestmentMaster entity : invstmntList) {
                                        BigDecimal tresurBill = new BigDecimal(entity.getBookvalue());
                                        double amortizationAmt = investRptMgmtFacade.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), dates.get(m));
                                        bal2 = bal2.add(tresurBill.subtract(new BigDecimal(amortizationAmt)));
                                    }
                                }
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt).abs());
                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
                            rbiPojoTable.add(rbiSossPojo);

                        }
                    } else if (refIndex.equalsIgnoreCase("NPA")) {
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            List<NpaAccountDetailPojo> finalList = null;
                            if (m == 0) {
                                finalList = npaListFirst;
                            } else {
                                finalList = npaListSecond;
                            }
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                if (refContent.equalsIgnoreCase("SUB")) {
                                    for (int l = 0; l < finalList.size(); l++) {
                                        if (finalList.get(l).getSortAcStatus().equalsIgnoreCase("11")) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("D1")) {
                                    for (int l = 0; l < finalList.size(); l++) {
                                        if (finalList.get(l).getSortAcStatus().equalsIgnoreCase("12D1")) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("D2")) {
                                    for (int l = 0; l < finalList.size(); l++) {
                                        if (finalList.get(l).getSortAcStatus().equalsIgnoreCase("12D2")) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("D3")) {
                                    for (int l = 0; l < finalList.size(); l++) {
                                        if (finalList.get(l).getSortAcStatus().equalsIgnoreCase("12D3")) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("L")) {
                                    for (int l = 0; l < finalList.size(); l++) {
                                        if (finalList.get(l).getSortAcStatus().equalsIgnoreCase("13")) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    }
                                } else {
                                    noOfAc = new Long(finalList.size());
                                }
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(noOfAc));
                            } else {
                                if (refContent.equalsIgnoreCase("SUB")) {
                                    for (int k = 0; k < finalList.size(); k++) {
                                        if (finalList.get(k).getSortAcStatus().equalsIgnoreCase("11")) {
                                            NpaAccountDetailPojo vec = finalList.get(k);
                                            bal2 = bal2.add(vec.getOutstdBal());
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("D1")) {
                                    for (int k = 0; k < finalList.size(); k++) {
                                        if (finalList.get(k).getSortAcStatus().equalsIgnoreCase("12D1")) {
                                            NpaAccountDetailPojo vec = finalList.get(k);
                                            bal2 = bal2.add(vec.getOutstdBal());
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("D2")) {
                                    for (int k = 0; k < finalList.size(); k++) {
                                        if (finalList.get(k).getSortAcStatus().equalsIgnoreCase("12D2")) {
                                            NpaAccountDetailPojo vec = finalList.get(k);
                                            bal2 = bal2.add(vec.getOutstdBal());
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("D3")) {
                                    for (int k = 0; k < finalList.size(); k++) {
                                        if (finalList.get(k).getSortAcStatus().equalsIgnoreCase("12D3")) {
                                            NpaAccountDetailPojo vec = finalList.get(k);
                                            bal2 = bal2.add(vec.getOutstdBal());
                                        }
                                    }
                                } else if (refContent.equalsIgnoreCase("L")) {
                                    for (int k = 0; k < finalList.size(); k++) {
                                        if (finalList.get(k).getSortAcStatus().equalsIgnoreCase("13")) {
                                            NpaAccountDetailPojo vec = finalList.get(k);
                                            bal2 = bal2.add(vec.getOutstdBal());
                                        }
                                    }
                                } else {
                                    for (int k = 0; k < finalList.size(); k++) {
                                        NpaAccountDetailPojo vec = finalList.get(k);
                                        bal2 = bal2.add(vec.getOutstdBal());
                                    }
                                }
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt).abs());
                            }
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (refIndex.equalsIgnoreCase("IND") || refIndex.equalsIgnoreCase("GROUP") || refIndex.equalsIgnoreCase("VIO")) {
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            List<TopDepositorBorrowerPojo> topList = new ArrayList<TopDepositorBorrowerPojo>();
                            List<LoanAcDetailsTable> loanAcDetails = new ArrayList<LoanAcDetailsTable>();
                            if (m == 0) {
                                topList = topListFirst;
                                loanAcDetails = loanAcDetailFirst;
                            } else {
                                topList = topListSecond;
                                loanAcDetails = loanAcDetailSecond;
                            }
                            if ((refIndex.equalsIgnoreCase("IND") || refIndex.equalsIgnoreCase("GROUP"))) {
                                if (!topList.isEmpty()) {
                                    TopDepositorBorrowerPojo val = topList.get(0);
                                    String acNo = val.getAcNo();
                                    bal2 = val.getOutStandingBal().abs();
                                    List acTypeList = em.createNativeQuery("select ifnull(acctCategory,'Un') from accountmaster where acno ='" + acNo + "'").getResultList();
                                    Vector vect = (Vector) acTypeList.get(0);
                                    String acTpe = vect.get(0).toString();
                                    if (acTpe.equalsIgnoreCase("IND") || acTpe.equalsIgnoreCase("UN") && refIndex.equalsIgnoreCase("IND")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt));
                                    } else if ((acTpe.contains("PC") || acTpe.equalsIgnoreCase("HUF") || acTpe.equalsIgnoreCase("TG") || acTpe.equalsIgnoreCase("RS")) && refIndex.equalsIgnoreCase("GROUP")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt));
                                    }
                                }
                            } else if (refIndex.equalsIgnoreCase("VIO")) {
                                BigDecimal outstanding = new BigDecimal("0");
                                BigDecimal sancttionAmt = new BigDecimal("0");
                                if (!loanAcDetails.isEmpty()) {
                                    for (int n = 0; n < loanAcDetails.size(); n++) {
                                        LoanAcDetailsTable val = loanAcDetails.get(n);
                                        sancttionAmt = val.getAmountSanc().abs();
                                        outstanding = val.getBalance().abs();
                                        if (outstanding.compareTo(sancttionAmt) > 0) {
                                            bal2 = bal2.add(outstanding);
                                        }
                                    }
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, bal2.divide(repOpt));
                                }
                            }
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);

                    } else if (refIndex.equalsIgnoreCase("EMP")) {
                        rbiSossPojo = new RbiSossPojo();
                        int noOfEmp = 0;
                        for (int m = 0; m < dates.size(); m++) {
                            if (m == 0) {
                                noOfEmp = noOfEmpAsOnDate;
                            } else {
                                noOfEmp = noOfEmpPreDate;
                            }
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(noOfEmp));
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);

                    } else if (refIndex.equalsIgnoreCase("NNPA")) {
                        /* For NET NPA Percentage */
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8),acname").getResultList();
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            BigDecimal provAmt = new BigDecimal("0");
                            BigDecimal totalLoan = new BigDecimal("0");
                            List<NpaAccountDetailPojo> finalList = null;
                            if (m == 0) {
                                finalList = npaListFirst;
                            } else {
                                finalList = npaListSecond;
                            }
                            for (int k = 0; k < finalList.size(); k++) {
                                NpaAccountDetailPojo vec = finalList.get(k);
                                bal2 = bal2.add(vec.getOutstdBal());
                            }
                            List<ProvDetailOfLoanAccounts> provList = rbiReportRemote.getProvisionAccordingToLoan("SOSS3.PART-A", dmy.format(ymd.parse(dates.get(m))), "0A", new BigDecimal("1"));
                            for (int k = 0; k < provList.size(); k++) {
                                totalLoan = totalLoan.add(provList.get(k).getOutstanding() == null ? new BigDecimal("0") : provList.get(k).getOutstanding());
                            }
                            for (int n = 0; n < glNameList.size(); n++) {
                                Vector vector = (Vector) glNameList.get(n);
                                GlHeadPojo glPojo = new GlHeadPojo();
                                glPojo.setGlHead(vector.get(0).toString());
                                glPojo.setGlName(vector.get(1).toString());
                                String dt = dates.get(m);
                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
                                if (newBalPojo == null) {
                                    provAmt = provAmt.add(new BigDecimal(0.00));
                                } else {
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        provAmt = provAmt.add(newBalPojo.getBalance());
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        provAmt = provAmt.add(newBalPojo.getBalance().multiply((new BigDecimal("-1"))));
                                    } else {
                                        provAmt = (fSGNo.equalsIgnoreCase("ACT")) ? provAmt.add(newBalPojo.getBalance()) : provAmt.add(newBalPojo.getBalance().abs());
                                    }
                                }
                            }
                            BigDecimal chkamt = bal2.subtract(provAmt);
                            BigDecimal netAmt = ((bal2.subtract(provAmt)).divide(totalLoan, 6, RoundingMode.HALF_UP));
                            if (chkamt.compareTo(bal) > 0) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal("0"));
                            } else {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, netAmt.multiply(new BigDecimal("100")));
                            }
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);

                    } else if (refIndex.equalsIgnoreCase("GNPA")) {
                        /* For Grosss NPA Percentage */
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            BigDecimal totalLoan = new BigDecimal("0");
                            List<ProvDetailOfLoanAccounts> provList = rbiReportRemote.getProvisionAccordingToLoan("SOSS3.PART-A", dmy.format(ymd.parse(dates.get(m))), "0A", new BigDecimal("1"));
                            for (int k = 0; k < provList.size(); k++) {
                                if (provList.get(k).getCategory().equalsIgnoreCase("11") || provList.get(k).getCategory().equalsIgnoreCase("12") || provList.get(k).getCategory().equalsIgnoreCase("13")) {
                                    bal2 = bal2.add(provList.get(k).getOutstanding() == null ? new BigDecimal("0") : provList.get(k).getOutstanding());
                                }
                                totalLoan = totalLoan.add(provList.get(k).getOutstanding() == null ? new BigDecimal("0") : provList.get(k).getOutstanding());
                            }
                            BigDecimal grossAmt = bal2.divide(totalLoan, 6, RoundingMode.HALF_UP);
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, grossAmt.multiply(new BigDecimal("100")));
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (refIndex.equalsIgnoreCase("OVER")) {
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            BigDecimal bal2 = new BigDecimal("0");
                            List<OverdueNonEmiResultList> finalList = new ArrayList<OverdueNonEmiResultList>();
                            List<OverdueEmiReportPojo> finalListEMi = new ArrayList<OverdueEmiReportPojo>();
                            if (m == 0) {
                                finalList = finalNonEMIList1;
                                finalListEMi = finalEMIList1;
                            } else {
                                finalList = finalNonEMIList2;
                                finalListEMi = finalEMIList2;
                            }
                            BigDecimal odAmt = new BigDecimal("0");
                            for (int l = 0; l < finalList.size(); l++) {
                                odAmt = odAmt.add(new BigDecimal(finalList.get(l).getOverDue()));
                            }
                            for (int l = 0; l < finalListEMi.size(); l++) {
                                odAmt = odAmt.add(finalListEMi.get(l).getAmountOverdue());
                            }
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                int noOfAcs = finalList.size() + finalListEMi.size();
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(noOfAcs));
                            } else {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, odAmt.divide(repOpt).abs());
                            }
                        }
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                    }
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo;
                    preFormula = formula;
                }
//                for (int i = 0; i < rbiPojoTable.size(); i++) {
//                    System.out.println(rbiPojoTable.get(i).getsNo() + ";" + rbiPojoTable.get(i).getReportName() + ";" + rbiPojoTable.get(i).getDescription() + ";" + rbiPojoTable.get(i).getgNo()
//                            + ";" + rbiPojoTable.get(i).getsGNo() + ";" + rbiPojoTable.get(i).getSsGNo() + ";" + rbiPojoTable.get(i).getSssGNo() + ";" + rbiPojoTable.get(i).getSsssGNo()
//                            + ";" + rbiPojoTable.get(i).getClassification() + ";" + rbiPojoTable.get(i).getCountApplicable() + ";" + rbiPojoTable.get(i).getAcNature()
//                            + ";" + rbiPojoTable.get(i).getAcType() + ";" + rbiPojoTable.get(i).getNpaClassification() + ";" + rbiPojoTable.get(i).getGlheadFrom()
//                            + ";" + rbiPojoTable.get(i).getGlHeadTo() + ";" + rbiPojoTable.get(i).getFormula() + ";>>>>>>>" + rbiPojoTable.get(i).getAmt() + ";" + rbiPojoTable.get(i).getSecondAmount()
//                            + ";" + rbiPojoTable.get(i).getThirdAmount() + ";" + rbiPojoTable.get(i).getFourthAmount() + ";" + rbiPojoTable.get(i).getFifthAmount()
//                            + ";" + rbiPojoTable.get(i).getSixthAmount() + ";" + rbiPojoTable.get(i).getSeventhAmount());
//                }                
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
                    String refIndex = rbiSossPojo.getRefIndex();
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal bal = new BigDecimal("0.0");
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                double balPL = glReport.IncomeExp(dt, "0A", "0A");
                                if ((rbiSossPojo.getClassification().equalsIgnoreCase("L") || rbiSossPojo.getClassification().equalsIgnoreCase("I")) && balPL >= 0) {
                                    if (m == 0) {
                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else {
                                        rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    }
                                } else if ((rbiSossPojo.getClassification().equalsIgnoreCase("A") || rbiSossPojo.getClassification().equalsIgnoreCase("E")) && balPL < 0) {
                                    if (reptName.contains("OSS1") && rbiSossPojo.getgNo() == 1) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else {
                                        if (m == 0) {
                                            rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        } else {
                                            rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        }
                                    }
                                } else {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                }
                            }
                        } else if (rbiSossPojo.getFormula().contains("OSS2")) {
                            for (int m = 0; m < dates.size(); m++) {
                                List<RbiSossPojo> oss2List = rbiSoss1Remote.getSOSS2("SOSS2", dates.get(m), "0A", new BigDecimal("1"), summaryOpt, osBlancelist, "0");
                                String[] strArr = rbiSossPojo.getFormula().split("#");
                                bal = rbiReportRemote.getValueFromFormula(oss2List, strArr[1]);
                                bal = bal.abs();
                                if (m == 0) {
                                    if ((bal.compareTo(new BigDecimal("0")) == 0)) {
                                        rbiSossPojo.setAmt(bal);
                                    } else {
                                        rbiSossPojo.setAmt(bal.divide(repOpt));
                                    }
                                } else {
                                    if ((bal.compareTo(new BigDecimal("0")) == 0)) {
                                        rbiSossPojo.setSecondAmount(bal);
                                    } else {
                                        rbiSossPojo.setSecondAmount(bal.divide(repOpt));
                                    }
                                }
                            }
                        } else if (rbiSossPojo.getFormula().contains("OSS1")) {
                            for (int m = 0; m < dates.size(); m++) {
                                List<RbiSossPojo> oss1List = null;
                                if (rbiSossPojo.getRangeBaseOn().equalsIgnoreCase("P")) {
                                    if (m == 0) {
                                        oss1List = dataList1;
                                    } else {
                                        oss1List = dataList2;
                                    }
                                } else {
                                    oss1List = rbiSoss1Remote.getSOSS2("SOSS1", dates.get(m), "0A", new BigDecimal("1"), summaryOpt, osBlancelist, "0");
                                }
                                String[] strArr = rbiSossPojo.getFormula().split("#");
                                bal = rbiReportRemote.getValueFromFormula(oss1List, strArr[1]);
                                bal = bal.abs();
                                if (m == 0) {
                                    if ((bal.compareTo(new BigDecimal("0")) == 0)) {
                                        rbiSossPojo.setAmt(bal);
                                    } else {
                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal(rbiSossPojo.getRefIndex())).divide(new BigDecimal("100")).divide(repOpt));
                                    }
                                } else {
                                    if ((bal.compareTo(new BigDecimal("0")) == 0)) {
                                        rbiSossPojo.setSecondAmount(bal);
                                    } else {
                                        rbiSossPojo.setSecondAmount(bal.multiply(new BigDecimal(rbiSossPojo.getRefIndex())).divide(new BigDecimal("100")).divide(repOpt));
                                    }
                                }
                            }
                        } else {
                            BigDecimal[] arr = monthlyRemote.getValueFromFormulaForFormOne(rbiPojoTable, rbiSossPojo.getFormula(), dates);
                            rbiSossPojo.setAmt((refIndex.equalsIgnoreCase("+ive") && arr[0].doubleValue() < 0) ? new BigDecimal("0") : arr[0]);
                            rbiSossPojo.setSecondAmount((refIndex.equalsIgnoreCase("+ive") && arr[1].doubleValue() < 0) ? new BigDecimal("0") : arr[1]);
                            rbiSossPojo.setThirdAmount((refIndex.equalsIgnoreCase("+ive") && arr[2].doubleValue() < 0) ? new BigDecimal("0") : arr[2]);
                            rbiSossPojo.setFourthAmount((refIndex.equalsIgnoreCase("+ive") && arr[3].doubleValue() < 0) ? new BigDecimal("0") : arr[3]);
                            rbiSossPojo.setFifthAmount((refIndex.equalsIgnoreCase("+ive") && arr[4].doubleValue() < 0) ? new BigDecimal("0") : arr[4]);
                            rbiSossPojo.setSixthAmount((refIndex.equalsIgnoreCase("+ive") && arr[5].doubleValue() < 0) ? new BigDecimal("0") : arr[5]);
                            rbiSossPojo.setSeventhAmount((refIndex.equalsIgnoreCase("+ive") && arr[6].doubleValue() < 0) ? new BigDecimal("0") : arr[6]);
                        }
                    }
                }
            }
//            for (int i = 0; i < rbiPojoTable.size(); i++) {
//                System.out.println(rbiPojoTable.get(i).getsNo() + ";" + rbiPojoTable.get(i).getReportName() + ";" + rbiPojoTable.get(i).getDescription() + ";" + rbiPojoTable.get(i).getgNo()
//                        + ";" + rbiPojoTable.get(i).getsGNo() + ";" + rbiPojoTable.get(i).getSsGNo() + ";" + rbiPojoTable.get(i).getSssGNo() + ";" + rbiPojoTable.get(i).getSsssGNo()
//                        + ";" + rbiPojoTable.get(i).getClassification() + ";" + rbiPojoTable.get(i).getCountApplicable() + ";" + rbiPojoTable.get(i).getAcNature()
//                        + ";" + rbiPojoTable.get(i).getAcType() + ";" + rbiPojoTable.get(i).getNpaClassification() + ";" + rbiPojoTable.get(i).getGlheadFrom()
//                        + ";" + rbiPojoTable.get(i).getGlHeadTo() + ";" + rbiPojoTable.get(i).getRangeBaseOn() + ";" + rbiPojoTable.get(i).getRangeFrom()
//                        + ";" + rbiPojoTable.get(i).getRangeTo() + ";" + rbiPojoTable.get(i).getFormula() + ";" + rbiPojoTable.get(i).getfGNo()
//                        + ";" + rbiPojoTable.get(i).getfSGNo() + ";" + rbiPojoTable.get(i).getfSsGNo() + ";" + rbiPojoTable.get(i).getfSssGNo()
//                        + ";" + rbiPojoTable.get(i).getfSsssGNo() + ";" + rbiPojoTable.get(i).getAmt() + ";" + rbiPojoTable.get(i).getSecondAmount()
//                        + ";" + rbiPojoTable.get(i).getThirdAmount() + ";" + rbiPojoTable.get(i).getFourthAmount() + ";" + rbiPojoTable.get(i).getFifthAmount()
//                        + ";" + rbiPojoTable.get(i).getSixthAmount() + ";" + rbiPojoTable.get(i).getSeventhAmount());
//            }
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public boolean isFridayDate(String fridayDt) throws ApplicationException {
        try {
            List fridayList = em.createNativeQuery("select repfridate from ho_reportingfriday where repfridate = '" + fridayDt + "'").getResultList();
            if (!fridayList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllAlternateFriday(String firstAltFriDt, String monthLastDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select date_format(repfridate,'%Y%m%d') from ho_reportingfriday where repfridate>'" + firstAltFriDt + "' and repfridate<='" + monthLastDt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getFormOneStructure() throws ApplicationException {
        try {
            return em.createNativeQuery("select gno,s_gno,ss_gno,sss_gno,ac_code,formula,f_gno,f_s_gno,f_ss_gno,f_sss_gno,acno_description from ho_crr_form1 order by sno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public BigDecimal getAcCodeAmount(String accode, String dt) throws ApplicationException {
        BigDecimal amount = new BigDecimal("0");
        try {
            List amountList = null;
            if (accode.contains("SSI")) {
                List accList = em.createNativeQuery("select a.acno from cbs_loan_borrower_details l, accountmaster a where a.acno = l.acc_no and  l.type_of_advance in('" + accode.substring(accode.indexOf(".") + 1) + "') and sub_sector = 'se' and a.openingdt <='" + dt + "'").getResultList();
                if (accList.size() > 0) {
                    for (int i = 0; i < accList.size(); i++) {
                        Vector accVect = (Vector) accList.get(i);
                        String acNo = accVect.get(0).toString();
                        String acNature = common.getAcNatureByAcNo(acNo);
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from ca_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from loan_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                        }
                        Vector element = (Vector) amountList.get(0);
                        amount = amount.add(new BigDecimal(element.get(0).toString()));
                    }
                } else {
                    amount = new BigDecimal("0");
                }
            } else if (accode.contains("LIEN")) {
                List accList = em.createNativeQuery("select distinct acno from loansecurity where substring(lienacno,3,2) in ('" + accode.substring(accode.indexOf(".") + 1) + "') and status = 'active' and entrydate<='" + dt + "' order by acno").getResultList();
                if (accList.size() > 0) {
                    for (int i = 0; i < accList.size(); i++) {
                        Vector accVect = (Vector) accList.get(i);
                        String acNo = accVect.get(0).toString();
                        String acNature = common.getAcNatureByAcNo(acNo);
                        List lienSecAcNoList = null;
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from ca_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                            lienSecAcNoList = em.createNativeQuery("select ifnull(sum(ifnull(matvalue,0)),0) from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' and matdate >='" + dt + "' and lienacno is not null").getResultList();
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from loan_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                            lienSecAcNoList = em.createNativeQuery("select ifnull(sum(ifnull(matvalue,0)),0) from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' and entrydate<='" + dt + "' and lienacno is not null").getResultList();
                        }
                        Vector element = (Vector) amountList.get(0);
                        BigDecimal amt1 = new BigDecimal(element.get(0).toString());
                        Vector lienSecAcNoVect = (Vector) lienSecAcNoList.get(0);
                        BigDecimal lienSecAcNoAmt = new BigDecimal(lienSecAcNoVect.get(0).toString());
                        if (lienSecAcNoAmt.compareTo(amt1) == -1) {
                            amt1 = lienSecAcNoAmt; //amt1.subtract(lienSecAcNoAmt);
                        } else if (lienSecAcNoAmt.compareTo(amt1) == 0) {
                            amt1 = lienSecAcNoAmt; //amt1.subtract(lienSecAcNoAmt);
                        } else if (lienSecAcNoAmt.compareTo(amt1) == 1) {
                            amt1 = amt1;
                        }
                        amount = amount.add(amt1);
                    }
                } else {
                    amount = new BigDecimal("0");
                }
            } else {
                amountList = em.createNativeQuery("select ifnull(sum(ifnull(bal,0)),0) from ho_crr_asset_liab where accode='" + accode + "' and date='" + dt + "'").getResultList();
                Vector element = (Vector) amountList.get(0);
                amount = new BigDecimal(element.get(0).toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return amount;
    }

    public List getFormNineStructure() throws ApplicationException {
        try {
            return em.createNativeQuery("select gno,s_gno,ss_gno,sss_gno,ac_code,formula,f_gno,f_s_gno,f_ss_gno,f_sss_gno,acno_description from ho_crr_form9 order by sno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getMonetaryAggregatesStructure() throws ApplicationException {
        try {
            return em.createNativeQuery("select gno,s_gno,ss_gno,sss_gno,ac_code,formula,f_gno,f_s_gno,f_ss_gno,f_sss_gno,acno_description from ho_crr_monetary_agg order by sno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public Double getMonetaryAggregatesFdValue(String acType, String formula, String date) throws ApplicationException {
        try {
            Double amt = 0d, amt1 = 0d;
            List resultSet = em.createNativeQuery("select t.acno,ifnull((sum(t.cramt)-sum(t.dramt)),0) from td_recon t, td_accountmaster a where a.acno = "
                    + "t.acno and a.accttype ='" + acType + "' AND t.dt<='" + date + "' and t.auth='Y' AND t.closeflag is null  group by t.acno "
                    + " having sum(t.cramt)-sum(t.dramt)>0 order by t.acno").getResultList();
            for (int a = 0; a < resultSet.size(); a++) {
                Vector resultSetVec = (Vector) resultSet.get(a);
                String acno = resultSetVec.get(0).toString();
                amt1 = Double.parseDouble(resultSetVec.get(1).toString());

                List dtList = null;
                dtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + date + "') FROM td_vouchmst WHERE ACNO = '" + acno + "'").getResultList();
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
                    long noOfDays;
                    if (maddt1.compareTo(dt1) >= 0) {
                        noOfDays = CbsUtil.dayDiff(ymd.parse(date), ymd.parse(maddt));
                    } else {
                        noOfDays = 1;
                    }
                    if (formula.equalsIgnoreCase("<=365-")) {
                        if (noOfDays <= 365) {
                            amt = amt + amt1;
                        } else {
                            amt = amt + 0;
                        }
                    } else {
                        if (noOfDays > 365) {
                            amt = amt + amt1;
                        } else {
                            amt = amt + 0;
                        }
                    }
                }
            }
            return amt;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCrrSlrParameter() throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(crrperc,0),ifnull(slrperc,0) from ho_crr_parameter").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAnnextureStructure() throws ApplicationException {
        try {
            return em.createNativeQuery("select gNo,sGNo,ssGNo,acCode,formula,f_gNo,f_sGNo,f_ssGNo,acDesc from ho_annexure order by sNo").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private double repAmount(String repOpt) throws ApplicationException {
        try {
            double repAmt = 1;
            if (repOpt.equals("T")) {
                repAmt = 1000;
            } else if (repOpt.equals("L")) {
                repAmt = 100000;
            } else if (repOpt.equals("C")) {
                repAmt = 10000000;
            } else if (repOpt.equals("R")) {
                repAmt = 1;
            }
            return repAmt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getCrrSlrPercentage(String brCode, String date) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            Map<String, List<RbiSossPojo>> dtlDataMap = new HashMap<String, List<RbiSossPojo>>();
            List reportList = em.createNativeQuery("select ifnull(crrperc,0), ifnull(slrperc,0) from ho_crr_parameter where wefdate=(select max(wefdate) from ho_crr_parameter where wefdate <='" + date + "' )").getResultList();
            if (reportList.isEmpty()) {
                throw new ApplicationException("Crr / Slr Percentage does not defined.");
            }
            return reportList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<DtlRegisterPojo> getDtlRegisterData(String brCode, String date, String repOpt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List<DtlRegisterPojo> dtlRegList = new ArrayList<DtlRegisterPojo>();
            List reportList2 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + date + "'").getResultList();
            if (reportList2.isEmpty()) {
                throw new ApplicationException("Last Reporting Friday Date does not defined.");
            }
            Vector ele = (Vector) reportList2.get(0);
            if (ele.get(0) == null) {
                throw new ApplicationException("Last Reporting Friday Date does not defined.");
            }
            String repFriDate = ele.get(0).toString();
            repFriDate = CbsUtil.dateAdd(repFriDate, -14);
//            String ndtlStr = getNdtlOneTwoThree(brCode, repFriDate);
//            String[] ndtlArr = ndtlStr.split("~`");
//
//            BigDecimal balOne = new BigDecimal(ndtlArr[0]);
//            BigDecimal balTwo = new BigDecimal(ndtlArr[1]);
//
//            BigDecimal balThree = new BigDecimal(ndtlArr[2]);
//            BigDecimal ndtl = balOne.subtract(balThree.abs());
//
//            if (ndtl.compareTo(new BigDecimal(0)) > 0) {
//                throw new ApplicationException("There is an error for getting data.");
//            }
//            ndtl = balTwo.divide(new BigDecimal(repAmount(repOpt)));
            //double reqCrr = CbsUtil.round((Double.parseDouble(ndtl.toString()) * crrPer) / 100, 2);
            //double reqSlr = CbsUtil.round((Double.parseDouble(ndtl.toString()) * slrPer) / 100, 2);

            DtlRegisterPojo dtlRegPojo = new DtlRegisterPojo();

            List oss1QueryList = em.createNativeQuery("select gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, ac_nature, ac_type, gl_head_from, gl_head_to, "
                    + "f_sss_gno, f_ssss_gno, count_applicable from cbs_ho_rbi_stmt_report  where report_name = 'form1' and f_ssss_gno ='NDTL-S' AND '" + repFriDate + "' between EFF_FR_DT and EFF_TO_DT  "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1QueryList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_ho_rbi_stmt_report");
            }
            List<RbiSossPojo> ndtlList = getDataList(oss1QueryList, brCode, repFriDate, "NDTL", repOpt);
            /*Sorting of list*/
            ComparatorChain chain = new ComparatorChain();
            chain.addComparator(new ComparatorByGno());
            chain.addComparator(new ComparatorBySgno());
            chain.addComparator(new ComparatorBySSgno());
            chain.addComparator(new ComparatorBySSSgno());
            chain.addComparator(new ComparatorBySSSSgno());
            Collections.sort(ndtlList, chain);
            /*Retuning List*/
            dtlRegPojo.setNdtlList(ndtlList);

//            for(RbiSossPojo po : ndtlList){
//            //  System.out.print("Nature = "+pojo.getAcNature() + " \t Type = "+ pojo.getAcType() +" \t GLHead = "+pojo.getDescription());
//               System.out.println("NDTL: "+po.getsNo()+":"+po.getReportName()+":"+po.getClassification()+":"+po.getgNo()+":"+po.getsGNo()+":"+po.getSsGNo()+":"+po.getSssGNo()+":"+po.getSsssGNo()+":"+po.getDescription()+":"+po.getAcNature()+":"+po.getAcType()+":"+po.getGlheadFrom()+":"+po.getGlHeadTo()+":"+po.getFormula()+":"+po.getAmt());
//            }
            oss1QueryList = em.createNativeQuery("select gno, s_gno, ss_gno, sss_gno, ssss_gno, classification,ac_nature, ac_type,gl_head_from, gl_head_to, f_sss_gno, f_ssss_gno, count_applicable  from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1' and ((f_sss_gno like 'CR%' or f_ssss_gno = 'CR')  OR (f_ssss_gno like 'SLREX%' or f_ssss_gno = 'SLREX')) and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') AND '" + date + "' between EFF_FR_DT and EFF_TO_DT   "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1QueryList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_ho_rbi_stmt_report");
            }
            List<RbiSossPojo> crrList = getDataList(oss1QueryList, brCode, date, "CRR", repOpt);
            dtlRegPojo.setCrrList(crrList);

//            for(RbiSossPojo po : crrList){
//            //  System.out.print("Nature = "+pojo.getAcNature() + " \t Type = "+ pojo.getAcType() +" \t GLHead = "+pojo.getDescription());
//               System.out.println("CrrList: "+po.getsNo()+":"+po.getReportName()+":"+po.getClassification()+":"+po.getgNo()+":"+po.getsGNo()+":"+po.getSsGNo()+":"+po.getSssGNo()+":"+po.getSsssGNo()+":"+po.getDescription()+":"+po.getAcNature()+":"+po.getAcType()+":"+po.getGlheadFrom()+":"+po.getGlHeadTo()+":"+po.getFormula()+":"+po.getAmt());
//            }
            oss1QueryList = em.createNativeQuery("select gno, s_gno, ss_gno, sss_gno, ssss_gno, classification,ac_nature, ac_type, gl_head_from, gl_head_to, f_sss_gno, f_ssss_gno, count_applicable   from cbs_ho_rbi_stmt_report "
                    + "where report_name = 'form1' and ((f_ssss_gno = 'APP-SEC') OR (f_ssss_gno like 'SLREX%' or f_ssss_gno = 'SLREX')) and (ac_nature <>'' or ac_type <>'' or gl_head_from <>'') AND '" + date + "' between EFF_FR_DT and EFF_TO_DT  "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1QueryList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_ho_rbi_stmt_report");
            }
            List<RbiSossPojo> slrList = getDataList(oss1QueryList, brCode, date, "SLR", repOpt);
            dtlRegPojo.setSlrList(slrList);
//            for(RbiSossPojo po : slrList){
//            //  System.out.print("Nature = "+pojo.getAcNature() + " \t Type = "+ pojo.getAcType() +" \t GLHead = "+pojo.getDescription());
//               System.out.println("SlrList: "+po.getsNo()+":"+po.getReportName()+":"+po.getClassification()+":"+po.getgNo()+":"+po.getsGNo()+":"+po.getSsGNo()+":"+po.getSssGNo()+":"+po.getSsssGNo()+":"+po.getDescription()+":"+po.getAcNature()+":"+po.getAcType()+":"+po.getGlheadFrom()+":"+po.getGlHeadTo()+":"+po.getFormula()+":"+po.getAmt());
//            }
            dtlRegList.add(dtlRegPojo);
            return dtlRegList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List maxReportFriday() throws ApplicationException {
        try {
            return em.createNativeQuery("Select max(repfridate) From ho_reportingfriday Where RepFriDate<=now() Order By repfridate desc").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<RbiSossPojo> getDataList(List oss1Query, String brCode, String dt, String option, String repOpt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List<RbiSossPojo> dtlDataList = new ArrayList<RbiSossPojo>();
            List osBlancelist = getAsOnDateBalanceList(brCode, dt);

            for (int i = 0; i < oss1Query.size(); i++) {
                RbiSossPojo objPojo = new RbiSossPojo();
                Vector oss1Vect = (Vector) oss1Query.get(i);
                Integer gNo = Integer.parseInt(oss1Vect.get(0).toString());
                Integer sGNo = oss1Vect.get(1).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(1).toString());
                Integer ssGNo = oss1Vect.get(2).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(2).toString());
                Integer sssGNo = oss1Vect.get(3).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(3).toString());
                Integer ssssGNo = oss1Vect.get(4).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(4).toString());

                String classification = oss1Vect.get(5).toString();
                String acNature = oss1Vect.get(6).toString();
                String acType = oss1Vect.get(7).toString();

                String glHeadFrom = oss1Vect.get(8).toString();
                String glHeadTo = oss1Vect.get(9).toString();
                String fSsssGNo = oss1Vect.get(11).toString();
                String cntApply = oss1Vect.get(12).toString();
                String description = null;
                List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                GlHeadPojo pojo;

//                System.out.println("Acct Namtue = " + acNature + " and gl series = " + glHeadFrom + " and " + glHeadTo);
                if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
                    String acQuery = "";
                    if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                        acQuery = "acctnature ='" + acNature + "' ";
                    } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                        acQuery = "acctcode ='" + acType + "'";
                    }
                    if (acType.contains("LIEN")) {
                        if (acType.contains("LIEN")) {
                            BigDecimal acctBal = new BigDecimal("0");
                            List dataList = em.createNativeQuery("select acctcode,acctdesc,glhead from accounttypemaster where acctcode = '" + acType.substring(5) + "'").getResultList();
                            pojo = new GlHeadPojo();
                            if (!dataList.isEmpty()) {
                                Vector vec = (Vector) dataList.get(0);
                                pojo.setGlHead(vec.get(0).toString());
                                pojo.setGlName(vec.get(1).toString());
                                acctBal = quarterlyRemote.getAcCodeAmount(acType, dt);
                                pojo.setBalance(acctBal.divide(new BigDecimal(repAmount(repOpt))));
                            } else if (acType.equalsIgnoreCase("LIEN.ALL")) {
                                pojo.setGlHead("Loan Against Deposit");
                                pojo.setGlName("Loan Against Deposit");
                                acctBal = quarterlyRemote.getAcCodeAmount(acType, dt);
                                pojo.setBalance(acctBal.divide(new BigDecimal(repAmount(repOpt))));
                            } else {
                                pojo.setBalance(acctBal);
                            }
                            glPojoList.add(pojo);
                        }
                    } else {
                        List dataList = em.createNativeQuery("select acctcode,acctdesc,glhead from accounttypemaster where " + acQuery).getResultList();
                        for (int k = 0; k < dataList.size(); k++) {
                            Vector vec = (Vector) dataList.get(k);

                            pojo = new GlHeadPojo();
                            pojo.setGlHead(vec.get(0).toString());
                            pojo.setGlName(vec.get(1).toString());

                            GlHeadPojo newBalPojo = getOSBalance(osBlancelist, vec.get(0).toString(), classification);
                            if (newBalPojo == null) {
                                pojo.setBalance(new BigDecimal(0.00));
                            } else {
                                pojo.setBalance(newBalPojo.getBalance().divide(new BigDecimal(repAmount(repOpt))));
                            }
                            glPojoList.add(pojo);
                        }
                    }
                } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                    List glNameList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable where  substring(acno,3,8) between '" + glHeadFrom
                            + "' and '" + glHeadTo + "' group by substring(acno,3,8)").getResultList();
                    for (int n = 0; n < glNameList.size(); n++) {
                        Vector vector = (Vector) glNameList.get(n);
                        GlHeadPojo glPojo = new GlHeadPojo();
                        glPojo.setGlHead(vector.get(0).toString());
                        glPojo.setGlName(vector.get(1).toString());
                        description = vector.get(1).toString();
                        GlHeadPojo glPojoOld = getOSBalance(osBlancelist, vector.get(0).toString(), classification);

//                        System.out.println("Gl Head = " + vector.get(0).toString());
                        if (glPojoOld == null) {
                            glPojo.setBalance(new BigDecimal(0.00));
                        } else {
                            glPojo.setBalance(glPojoOld.getBalance().divide(new BigDecimal(repAmount(repOpt))));
                        }
                        if (glPojo.getBalance().compareTo(new BigDecimal(0.00)) != 0) {
                            glPojoList.add(glPojo);
                        }
                    }
                }
                if (option.equalsIgnoreCase("NDTL") || option.equalsIgnoreCase("CRR") || option.equalsIgnoreCase("SLR")) {
                    if (i == 0) {
                        objPojo.setgNo(1);
                        objPojo.setsGNo(0);
                        objPojo.setSsGNo(0);
                        objPojo.setSssGNo(0);
                        objPojo.setSsssGNo(0);
                        objPojo.setsNo(i);
                        objPojo.setAcNature("");
                        objPojo.setAcType("");
                        objPojo.setCountApplicable("");
                        objPojo.setClassification(classification);
                        objPojo.setDescription(option.concat(" (").concat(dmy.format(ymd.parse(dt))).concat(")"));
                        dtlDataList.add(objPojo);
                        objPojo = new RbiSossPojo();
                    }
                    if (option.equalsIgnoreCase("NDTL")) {
                        if (gNo == 1 && sGNo == 2 && ssGNo == 1) {
                            objPojo.setDescription("Demand Liability");
                            gNo = 1;
                            sGNo = 1;
                            ssGNo = 0;
                            sssGNo = 0;
                            ssssGNo = 0;
                        } else if (gNo == 1 && sGNo == 2 && ssGNo == 2) {
                            objPojo.setDescription("Time Liability");
                            gNo = 1;
                            sGNo = 2;
                            ssGNo = 0;
                            sssGNo = 0;
                            ssssGNo = 0;
                        } else {
                            if (acType.contains("LIEN")) {
                                objPojo.setDescription("Time Liability");
                                gNo = 1;
                                sGNo = 2;
                                ssGNo = 0;
                                sssGNo = 0;
                                ssssGNo = 0;
                            } else {
                                //if (gNo == 1 && sGNo == 2 && ssGNo == 3) {
                                objPojo.setDescription("Other Liability");
                                gNo = 1;
                                sGNo = 3;
                                ssGNo = 0;
                                sssGNo = 0;
                                ssssGNo = 0;
                            }
                        }
                    } else if (option.equalsIgnoreCase("CRR")) {
                        gNo = 1;
                        sGNo = i + 1;
                        ssGNo = 0;
                        sssGNo = 0;
                        ssssGNo = 0;
                    } else if (option.equalsIgnoreCase("SLR")) {
                        gNo = 1;
                        sGNo = i + 1;
                        ssGNo = 0;
                        sssGNo = 0;
                        ssssGNo = 0;
                    }


                    objPojo.setsNo(i + 1);
                    objPojo.setAcNature(acNature);
                    objPojo.setAcType(acType);
                    objPojo.setClassification(classification);
                    objPojo.setGlHeadTo(glHeadTo);
                    objPojo.setgNo(gNo);
                    objPojo.setsGNo(sGNo);
                    objPojo.setSsGNo(ssGNo);
                    objPojo.setSssGNo(sssGNo);
                    objPojo.setSsssGNo(ssssGNo);
                    objPojo.setCountApplicable(cntApply);
//                    if(!description.equalsIgnoreCase("null")){
                    dtlDataList.add(objPojo);
//                    }

                }
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                for (int j = 0; j < glPojoList.size(); j++) {
                    objPojo = new RbiSossPojo();
                    GlHeadPojo glHeadPo = glPojoList.get(j);
                    if (option.equalsIgnoreCase("NDTL")) {
                        objPojo.setsNo((i + 1) + j + 1);
                    } else {
                        objPojo.setsNo(j + 1);
                    }
                    objPojo.setAcNature(acNature);
                    objPojo.setAcType(acType);
                    objPojo.setClassification(classification);
                    objPojo.setGlHeadTo(glHeadPo.getGlHead());
                    objPojo.setgNo(gNo);
                    objPojo.setsGNo(sGNo);
                    objPojo.setNpaClassification("");
                    if (option.equalsIgnoreCase("SLR")) {
                        objPojo.setAmt(glHeadPo.getBalance() == null ? new BigDecimal(0.00) : (classification.equalsIgnoreCase("A") ? (glHeadPo.getBalance().doubleValue() < 0 ? (fSsssGNo.equalsIgnoreCase("SLREX") ? glHeadPo.getBalance() : glHeadPo.getBalance().abs()) : glHeadPo.getBalance().multiply(new BigDecimal(-1))) : glHeadPo.getBalance()));
                    } else {
                        objPojo.setAmt(glHeadPo.getBalance() == null ? new BigDecimal(0.00) : (classification.equalsIgnoreCase("A") ? (glHeadPo.getBalance().doubleValue() < 0 ? (option.equalsIgnoreCase("NDTL") ? glHeadPo.getBalance() : glHeadPo.getBalance().abs()) : glHeadPo.getBalance().multiply(new BigDecimal(-1))) : glHeadPo.getBalance()));
//                      objPojo.setAmt(glHeadPo.getBalance()==null?new BigDecimal(0.00):(classification.equalsIgnoreCase("A")?(glHeadPo.getBalance().doubleValue()<0?glHeadPo.getBalance().abs():glHeadPo.getBalance().multiply(new BigDecimal(-1))) :glHeadPo.getBalance()));
                    }

                    if (ssGNo == 0) {
                        objPojo.setSsGNo(j + 1);
                    } else {
                        objPojo.setSsGNo(ssGNo);
                    }
                    if (sssGNo == 0 && ssGNo != 0) {
                        objPojo.setSssGNo(j + 1);
                    } else {
                        objPojo.setSssGNo(sssGNo);
                    }
                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
                        objPojo.setSsssGNo(j + 1);
                        objPojo.setDescription(glHeadPo.getGlName());
                    } else {
                        objPojo.setSsssGNo(ssssGNo);
                        objPojo.setDescription(String.valueOf("        ").concat(glHeadPo.getGlName()));
                    }
                    dtlDataList.add(objPojo);
                }
            }
            Integer dCnt = 0, tCnt = 0, oCnt = 0;
            String preDString = "", preTString = "", preOString = "";
            if (option.equalsIgnoreCase("NDTL")) {
                for (int m = 0; m < dtlDataList.size(); m++) {
                    if (dtlDataList.get(m).getAmt() == null) {
                        dtlDataList.get(m).setAmt(new BigDecimal(0.00));
                    }
                    if (dtlDataList.get(m).getDescription().equalsIgnoreCase("Demand Liability")) {
                        if (dCnt == 0) {
                            preDString = dtlDataList.get(m).getDescription();
                        } else {
                            if (preDString.equalsIgnoreCase(dtlDataList.get(m).getDescription())) {
                                dtlDataList.remove(m);
                                m = m - 1;
                            }
                        }
                        dCnt++;
                    } else if (dtlDataList.get(m).getDescription().equalsIgnoreCase("Time Liability")) {
                        if (tCnt == 0) {
                            preTString = dtlDataList.get(m).getDescription();
                        } else {
                            if (preTString.equalsIgnoreCase(dtlDataList.get(m).getDescription())) {
                                dtlDataList.remove(m);
                                m = m - 1;
                            }
                        }
                        tCnt++;
                    } else if (dtlDataList.get(m).getDescription().equalsIgnoreCase("Other Liability")) {
                        if (oCnt == 0) {
                            preOString = dtlDataList.get(m).getDescription();
                        } else {
                            if (preOString.equalsIgnoreCase(dtlDataList.get(m).getDescription())) {
                                dtlDataList.remove(m);
                                m = m - 1;
                            }
                        }
                        oCnt++;
                    }
                }
            }

            for (int n = 0; n < dtlDataList.size(); n++) {
                if ((dtlDataList.get(n).getDescription() == null)) {
                    dtlDataList.remove(n);
                    n = n - 1;
                }
            }
            return dtlDataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<GlHeadPojo> getAsOnDateBalanceList(String brCode, String date) throws ApplicationException {
        try {
            String query = CbsUtil.getBranchWiseQuery(brCode, date);
            List glResultList = em.createNativeQuery(query).getResultList();
            List<GlHeadPojo> balanceList = new ArrayList<GlHeadPojo>();
            GlHeadPojo balPojo;
            for (int i = 0; i < glResultList.size(); i++) {
                Vector vect = (Vector) glResultList.get(i);
                balPojo = new GlHeadPojo();
//                 System.out.println("gl head is =" + vect.get(0)+"; value:"+ vect.get(1));
                balPojo.setGlHead(vect.get(0).toString());
                balPojo.setBalance(new BigDecimal(vect.get(1).toString()));
                balanceList.add(balPojo);
            }
            return balanceList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public GlHeadPojo getOSBalance(List<GlHeadPojo> balanceList, String acno, String cls) throws ApplicationException {
        try {
            if (cls.equalsIgnoreCase("L")) {
                for (GlHeadPojo pojo : balanceList) {
                    if (pojo.getGlHead().equals(acno) && pojo.getBalance().compareTo(new BigDecimal(0)) > 0) {
                        return pojo;
                    }
                }
            } else {
                for (GlHeadPojo pojo : balanceList) {
                    if (pojo.getGlHead().equals(acno) && pojo.getBalance().compareTo(new BigDecimal(0)) < 0) {
                        return pojo;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<AnnualNPAStmtConsolidate> getAnnualNPAStmt(String brCode, String date, BigDecimal repOpt, String reptName) throws ApplicationException {
        try {
            List<AnnualNPAStmtPojo> pojoList = new ArrayList<AnnualNPAStmtPojo>();
            List<AnnualNPAStmtConsolidate> mainList = new ArrayList<AnnualNPAStmtConsolidate>();
            List<LoanMisCellaniousPojo> stdList = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> stdListAtStart = new ArrayList<LoanMisCellaniousPojo>();
            String parameter = "Y";
            List chqParameter = common.getCode("ANNUAL_NPA_STD_INTEREST");
            if (!chqParameter.isEmpty()) {
                parameter = chqParameter.get(0).toString();
            }
            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", date, "ALL", "0A", "");
            List<LoanMisCellaniousPojo> misList = loanReportFacade.cbsLoanMisReport("0A", "0", "", date, "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            stdList.addAll(misList);
            for (int k = 0; k < resultList.size(); k++) {
                for (int l = 0; l < stdList.size(); l++) {
                    if (resultList.get(k).getAcno().equalsIgnoreCase(stdList.get(l).getAcNo())) {
                        stdList.remove(l);
                    }
                }
            }
//            List startDate = em.createNativeQuery("select mindate from yearend where brncode = '90' and '"+date+"' between MINDATE and MAXDATE").getResultList();
//            Vector element = (Vector) startDate.get(0);
//            String beginDate = element.get(0).toString();
            String beginDate = CbsUtil.dateAdd(rbiReportRemote.getMinFinYear(date), -1);
            List<NpaStatusReportPojo> resultListAtStart = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", beginDate, "ALL", "0A", "");
            List<LoanMisCellaniousPojo> misListAtStart = loanReportFacade.cbsLoanMisReport("0A", "0", "", beginDate, "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "ACTIVE", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");

            stdListAtStart.addAll(misListAtStart);
            for (int k = 0; k < resultList.size(); k++) {
                for (int l = 0; l < stdListAtStart.size(); l++) {
                    if (resultList.get(k).getAcno().equalsIgnoreCase(stdListAtStart.get(l).getAcNo())) {
                        stdListAtStart.remove(l);
                    }
                }
            }
            BigDecimal totalOutStandingAtStart = new BigDecimal("0");
            for (int k = 0; k < misListAtStart.size(); k++) {
                if (misListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    totalOutStandingAtStart = totalOutStandingAtStart.add(misListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misListAtStart.get(k).getOutstanding());
                } else {
                    totalOutStandingAtStart = totalOutStandingAtStart.add(misListAtStart.get(k).getOutstanding());
                }
            }
            BigDecimal npaAtStart = new BigDecimal("0");
            for (int k = 0; k < resultListAtStart.size(); k++) {
                npaAtStart = npaAtStart.add(resultListAtStart.get(k).getBalance());
            }
            BigDecimal npaAsOnDate = new BigDecimal("0");
            for (int k = 0; k < resultList.size(); k++) {
                npaAsOnDate = npaAsOnDate.add(resultList.get(k).getBalance());
            }
            List ossQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                    + "report_name = '" + reptName + "' order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned), classification desc, sno ").getResultList();
            if (ossQuery.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT for ANNUAL NPA STATEMENT");
            } else {
                BigDecimal totalLoan = new BigDecimal("0");
                for (int i = 0; i < ossQuery.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector ossVect = (Vector) ossQuery.get(i);
                    Integer sNo = Integer.parseInt(ossVect.get(0).toString());
                    String reportName = ossVect.get(1).toString();
                    String description = ossVect.get(2).toString();
                    Integer gNo = Integer.parseInt(ossVect.get(3).toString());
                    Integer sGNo = Integer.parseInt(ossVect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(ossVect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(ossVect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(ossVect.get(7).toString());
                    String classification = ossVect.get(8).toString();
                    String countApplicable = ossVect.get(9).toString();
                    String acNature = ossVect.get(10).toString();
                    String acType = ossVect.get(11).toString();
                    String glHeadFrom = ossVect.get(12).toString();
                    String glHeadTo = ossVect.get(13).toString();
                    String rangeBaseOn = ossVect.get(14).toString();
                    String rangeFrom = ossVect.get(15).toString();
                    String rangeTo = ossVect.get(16).toString();
                    String formula = ossVect.get(17).toString();
                    String fGNo = ossVect.get(18).toString();
                    String fSGNo = ossVect.get(19).toString();
                    String fSsGNo = ossVect.get(20).toString();
                    String fSssGNo = ossVect.get(21).toString();
                    String fSsssGNo = ossVect.get(22).toString();
                    String npaClassification = ossVect.get(23).toString();
                    String referIndex = ossVect.get(24).toString();
                    String referContent = ossVect.get(25).toString();
                    Long noOfAc = 0l;
                    BigDecimal principalAmt = new BigDecimal("0"), interestAmt = new BigDecimal("0"), provPerc = new BigDecimal("0");
                    BigDecimal provAmt = new BigDecimal("0"), existingProv = new BigDecimal("0"), provCurrYear = new BigDecimal("0");
                    BigDecimal totalProv = new BigDecimal("0"), totalOutStanding = new BigDecimal("0"), hundred = new BigDecimal("100.00");
                    BigDecimal glBalCurr = new BigDecimal("0"), glBalPrev = new BigDecimal("0");
                    double endDiff, startDiff;
                    String acStatus = "";
                    if (npaClassification.equalsIgnoreCase("11")) {
                        acStatus = "SUB STANDARD";
                    } else if (npaClassification.equalsIgnoreCase("12")) {
                        acStatus = "DOUBTFUL";
                    } else if (npaClassification.equalsIgnoreCase("13")) {
                        acStatus = "LOSS";
                    } else if (npaClassification.equalsIgnoreCase("1")) {
                        acStatus = "STANDARD";
                    }
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                    List<GlHeadPojo> glPojoList1 = new ArrayList<GlHeadPojo>();
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(brCode);
                    params.setClassification(classification);
                    params.setDate(date);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag("");

                    AdditionalStmtPojo params1 = new AdditionalStmtPojo();
                    params1.setAcType(acType);
                    params1.setBrnCode(brCode);
                    params1.setClassification(classification);
                    params1.setDate(beginDate);
                    params1.setFromRange(rangeFrom);
                    params1.setGlFromHead(glHeadFrom);
                    params1.setGlToHead(glHeadTo);
                    params1.setNature(acNature);
                    params1.setRangeBasedOn(rangeBaseOn);
                    params1.setToRange(rangeTo);
                    params1.setFlag("");

                    if (sGNo != 0 && !(classification.equalsIgnoreCase("")) && gNo != 2) {
                        if (referIndex.equalsIgnoreCase("Y") && npaClassification.equalsIgnoreCase("")) {
                            /* For Total Accounts*/
                            for (int k = 0; k < misList.size(); k++) {
                                if (misList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(k).getOutstanding());
                                    if (misList.get(k).getStatus().equalsIgnoreCase("DOUBTFUL") || misList.get(k).getStatus().equalsIgnoreCase("LOSS") || misList.get(k).getStatus().equalsIgnoreCase("SUB STANDARD")) {
                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                        Vector vect = (Vector) intAmt.get(0);
                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                    } else {
                                        if (parameter.equalsIgnoreCase("Y")) {
                                            List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from ca_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                            Vector vect = (Vector) intAmt.get(0);
                                            interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                        }
                                    }
                                } else {
                                    totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding());
                                    if (misList.get(k).getStatus().equalsIgnoreCase("DOUBTFUL") || misList.get(k).getStatus().equalsIgnoreCase("LOSS") || misList.get(k).getStatus().equalsIgnoreCase("SUB STANDARD")) {
                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                        Vector vect = (Vector) intAmt.get(0);
                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                    } else {
                                        if (parameter.equalsIgnoreCase("Y")) {
                                            List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from loan_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                            Vector vect = (Vector) intAmt.get(0);
                                            interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                        }
                                    }
                                }
                                String acno = misList.get(k).getAcNo();
                                if (!acno.isEmpty()) {
                                    noOfAc = noOfAc + 1;
                                }
                            }
                            totalLoan = totalOutStanding;
                        } else if (!npaClassification.equalsIgnoreCase("") && fSsssGNo.equalsIgnoreCase("")) {
                            /* For Substandard & Loss Category*/
                            if (npaClassification.equalsIgnoreCase("11") || npaClassification.equalsIgnoreCase("13")) {
                                for (int j = 0; j < resultList.size(); j++) {
                                    for (int k = 0; k < misList.size(); k++) {
                                        if (resultList.get(j).getAcno().equalsIgnoreCase(misList.get(k).getAcNo())) {
                                            if (fSssGNo.equalsIgnoreCase(misList.get(k).getSecured())) {
                                                if (acStatus.equalsIgnoreCase(resultList.get(j).getPresentStatus())) {
                                                    if (misList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    } else {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    }
                                                    String acno = misList.get(k).getAcNo();
                                                    if (!acno.isEmpty()) {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                }
                                            } else if (fSssGNo.equalsIgnoreCase("")) {
                                                if (acStatus.equalsIgnoreCase(resultList.get(j).getPresentStatus())) {
                                                    if (misList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    } else {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    }
                                                    String acno = misList.get(k).getAcNo();
                                                    if (!acno.isEmpty()) {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (npaClassification.equalsIgnoreCase("1")) {
                                /* For Standard Category*/
                                for (int k = 0; k < stdList.size(); k++) {
                                    if (fSssGNo.equalsIgnoreCase(misList.get(k).getSecured()) && !fSssGNo.equalsIgnoreCase("")) {
                                        if (stdList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            totalOutStanding = totalOutStanding.add(stdList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : stdList.get(k).getOutstanding());
                                            if (parameter.equalsIgnoreCase("Y")) {
                                                List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from ca_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                Vector vect = (Vector) intAmt.get(0);
                                                interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        } else {
                                            totalOutStanding = totalOutStanding.add(stdList.get(k).getOutstanding());
                                            if (parameter.equalsIgnoreCase("Y")) {
                                                List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from loan_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                Vector vect = (Vector) intAmt.get(0);
                                                interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        }
                                        String acno = stdList.get(k).getAcNo();
                                        if (!acno.isEmpty()) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    } else if (fSssGNo.equalsIgnoreCase("")) {
                                        if (stdList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            totalOutStanding = totalOutStanding.add(stdList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : stdList.get(k).getOutstanding());
                                            if (parameter.equalsIgnoreCase("Y")) {
                                                List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from ca_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                Vector vect = (Vector) intAmt.get(0);
                                                interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        } else {
                                            totalOutStanding = totalOutStanding.add(stdList.get(k).getOutstanding());
                                            if (parameter.equalsIgnoreCase("Y")) {
                                                List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from loan_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                Vector vect = (Vector) intAmt.get(0);
                                                interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        }
                                        String acno = stdList.get(k).getAcNo();
                                        if (!acno.isEmpty()) {
                                            noOfAc = noOfAc + 1;
                                        }
                                    }
                                }
                            }
                        } else if (!npaClassification.equalsIgnoreCase("") && !fSsssGNo.equalsIgnoreCase("") && (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D"))) {
                            /* For DoubtFul Category*/
                            for (int j = 0; j < resultList.size(); j++) {
                                for (int k = 0; k < misList.size(); k++) {
                                    if (resultList.get(j).getAcno().equalsIgnoreCase(misList.get(k).getAcNo())) {
                                        if (fSssGNo.equalsIgnoreCase(misList.get(k).getSecured())) {
                                            if (resultList.get(j).getPresentStatus().contains(acStatus)) {
                                                endDiff = Double.valueOf(rangeTo);
                                                startDiff = Double.valueOf(rangeFrom);
                                                String acNat = common.getAcNatureByAcNo(resultList.get(j).getAcno());
                                                NpaAccountDetailPojo pojo1 = new NpaAccountDetailPojo();
                                                pojo1 = loanReportFacade.npaAccountAddition(npaClassification, date, resultList.get(j).getAcno(), resultList.get(j).getCustName(), resultList.get(j).getBalance(), acNat);
                                                long d = pojo1.getTotalDayDiff();
                                                if (endDiff >= d && startDiff <= d) {
                                                    if (misList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    } else {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    }
                                                    String acno = misList.get(k).getAcNo();
                                                    if (!acno.isEmpty()) {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                }
                                            }
                                        } else if (fSssGNo.equalsIgnoreCase("")) {
                                            if (resultList.get(j).getPresentStatus().contains(acStatus)) {
                                                endDiff = Double.valueOf(rangeTo);
                                                startDiff = Double.valueOf(rangeFrom);
                                                String acNat = common.getAcNatureByAcNo(resultList.get(j).getAcno());
                                                NpaAccountDetailPojo pojo1 = new NpaAccountDetailPojo();
                                                pojo1 = loanReportFacade.npaAccountAddition(npaClassification, date, resultList.get(j).getAcno(), resultList.get(j).getCustName(), resultList.get(j).getBalance(), acNat);
                                                long d = pojo1.getTotalDayDiff();
                                                if (endDiff >= d && startDiff <= d) {
                                                    if (misList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    } else {
                                                        totalOutStanding = totalOutStanding.add(misList.get(k).getOutstanding());
                                                        List intAmt = em.createNativeQuery("select ifnull(sum(ifnull(cramt-dramt,0)),0) from npa_recon where acno = '" + misList.get(k).getAcNo() + "' and dt<='" + date + "' and trandesc in (3,4)").getResultList();
                                                        Vector vect = (Vector) intAmt.get(0);
                                                        interestAmt = interestAmt.add(new BigDecimal(vect.get(0).toString()));
                                                    }
                                                    String acno = misList.get(k).getAcNo();
                                                    if (!acno.isEmpty()) {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (npaClassification.equalsIgnoreCase("11") || npaClassification.equalsIgnoreCase("12") || npaClassification.equalsIgnoreCase("13")) {
                            principalAmt = totalOutStanding;
                        } else if (npaClassification.equalsIgnoreCase("") && referIndex.equalsIgnoreCase("Y")) {
                            principalAmt = totalLoan;
                        } else {
                            principalAmt = totalOutStanding.add(interestAmt);
                        }
                        if (principalAmt.compareTo(new BigDecimal("0")) > 0) {
                            principalAmt = totalOutStanding;
                        }
                        if ((fSsGNo.equalsIgnoreCase("GLPD") && (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))))) {
                            /* For Provision according to GLHeads*/
//                            glPojoList = rbiReportRemote.getGLHeadAndBal(params);
                            glPojoList1 = monthlyRemote.getAsOnDateBalanceBetweenDateList("0A", beginDate, date);
                            List balList = em.createNativeQuery("Select ifnull(sum(cramt-dramt),0) from gl_recon where substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' and dt between '" + beginDate + "' and '" + date + "' ").getResultList();
                            Vector vect = (Vector) balList.get(0);
                            provCurrYear = new BigDecimal(vect.get(0).toString());
                        } else if (fSsGNo.equalsIgnoreCase("P")) {
                            provCurrYear = provCurrYear.add(totalOutStanding.multiply(new BigDecimal(referContent)).divide(new BigDecimal("100")));
                        }
                        if ((fSsGNo.equalsIgnoreCase("GLPD") && (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))))) {
                            /* For Provision according to GLHeads*/
                            glPojoList = rbiReportRemote.getGLHeadAndBal(params1);
                            if (glPojoList.size() > 0) {
                                /* If GL Head Series have multiple GL Head */
                                for (int m = 0; m < glPojoList.size(); m++) {
                                    GlHeadPojo glHeadPo = glPojoList.get(m);
                                    existingProv = existingProv.add(glHeadPo.getBalance());
                                }
                            }
                        } else if (fSsGNo.equalsIgnoreCase("P")) {
                            BigDecimal totalOutStandingAtStartForProv = new BigDecimal("0");
                            /* For Provision according to Percentage*/
                            if (!npaClassification.equalsIgnoreCase("") && fSsssGNo.equalsIgnoreCase("")) {
                                /* For Substandard & Loss Category*/
                                if (npaClassification.equalsIgnoreCase("11") || npaClassification.equalsIgnoreCase("13")) {
                                    for (int j = 0; j < resultListAtStart.size(); j++) {
                                        for (int k = 0; k < misListAtStart.size(); k++) {
                                            if (resultListAtStart.get(j).getAcno().equalsIgnoreCase(misListAtStart.get(k).getAcNo())) {
                                                if (fSssGNo.equalsIgnoreCase(misListAtStart.get(k).getSecured())) {
                                                    if (acStatus.equalsIgnoreCase(resultListAtStart.get(j).getPresentStatus())) {
                                                        if (misListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misListAtStart.get(k).getOutstanding());
                                                        } else {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding());
                                                        }
                                                    }
                                                } else if (fSssGNo.equalsIgnoreCase("")) {
                                                    if (acStatus.equalsIgnoreCase(resultListAtStart.get(j).getPresentStatus())) {
                                                        if (misListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misListAtStart.get(k).getOutstanding());
                                                        } else {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (npaClassification.equalsIgnoreCase("1")) {
                                    /* For Standard Category*/
                                    for (int k = 0; k < stdListAtStart.size(); k++) {
                                        if (fSssGNo.equalsIgnoreCase(stdListAtStart.get(k).getSecured()) && !fSssGNo.equalsIgnoreCase("")) {
                                            if (stdListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(stdListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : stdListAtStart.get(k).getOutstanding());
                                            } else {
                                                totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(stdListAtStart.get(k).getOutstanding());
                                            }
                                        } else if (fSssGNo.equalsIgnoreCase("")) {
                                            if (stdListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(stdListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : stdListAtStart.get(k).getOutstanding());
                                            } else {
                                                totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(stdListAtStart.get(k).getOutstanding());
                                            }
                                        }
                                    }
                                }
                            } else if (!npaClassification.equalsIgnoreCase("") && !fSsssGNo.equalsIgnoreCase("") && (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D"))) {
                                /* For DoubtFul Category*/
                                for (int j = 0; j < resultListAtStart.size(); j++) {
                                    for (int k = 0; k < misListAtStart.size(); k++) {
                                        if (resultListAtStart.get(j).getAcno().equalsIgnoreCase(misListAtStart.get(k).getAcNo())) {
                                            if (fSssGNo.equalsIgnoreCase(misListAtStart.get(k).getSecured())) {
                                                if (acStatus.equalsIgnoreCase(resultListAtStart.get(j).getPresentStatus())) {
                                                    endDiff = Double.valueOf(rangeTo);
                                                    startDiff = Double.valueOf(rangeFrom);
                                                    String acNat = common.getAcNatureByAcNo(resultListAtStart.get(j).getAcno());
                                                    NpaAccountDetailPojo pojo1 = new NpaAccountDetailPojo();
                                                    pojo1 = loanReportFacade.npaAccountAddition(npaClassification, date, resultListAtStart.get(j).getAcno(), resultListAtStart.get(j).getCustName(), resultListAtStart.get(j).getBalance(), acNat);
                                                    long d = pojo1.getTotalDayDiff();
                                                    if (endDiff >= d && startDiff <= d) {
                                                        if (misListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misListAtStart.get(k).getOutstanding());
                                                        } else {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding());
                                                        }
                                                    }
                                                }
                                            } else if (fSssGNo.equalsIgnoreCase("")) {
                                                if (acStatus.equalsIgnoreCase(resultListAtStart.get(j).getPresentStatus())) {
                                                    endDiff = Double.valueOf(rangeTo);
                                                    startDiff = Double.valueOf(rangeFrom);
                                                    String acNat = common.getAcNatureByAcNo(resultListAtStart.get(j).getAcno());
                                                    NpaAccountDetailPojo pojo1 = new NpaAccountDetailPojo();
                                                    pojo1 = loanReportFacade.npaAccountAddition(npaClassification, date, resultListAtStart.get(j).getAcno(), resultListAtStart.get(j).getCustName(), resultListAtStart.get(j).getBalance(), acNat);
                                                    long d = pojo1.getTotalDayDiff();
                                                    if (endDiff >= d && startDiff <= d) {
                                                        if (misListAtStart.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misListAtStart.get(k).getOutstanding());
                                                        } else {
                                                            totalOutStandingAtStartForProv = totalOutStandingAtStartForProv.add(misListAtStart.get(k).getOutstanding());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            existingProv = existingProv.add(totalOutStandingAtStartForProv.multiply(new BigDecimal(referContent)).divide(new BigDecimal("100")));
                        }
//                    } if(gNo== 2) {
//                        glPojoList= rbiReportRemote.getGLHeadAndBal(params1);//Previous Year 
//                        glPojoList1 =rbiReportRemote.getGLHeadAndBal(params);// As On Date
//                        if (glPojoList.size() > 0) {
//                                /* If GL Head Series have multiple GL Head */
//                            for (int m = 0; m < glPojoList.size(); m++) {
//                                GlHeadPojo glHeadPo = glPojoList.get(m);
//                                glBalPrev = glBalPrev.add(glHeadPo.getBalance());
//                            }
//                        }
//                        if (glPojoList1.size() > 0) {
//                                /* If GL Head Series have multiple GL Head */
//                            for (int m = 0; m < glPojoList1.size(); m++) {
//                                GlHeadPojo glHeadPo = glPojoList1.get(m);
//                                glBalCurr = glBalCurr.add(glHeadPo.getBalance());
//                            }
//                        }
                    }
                    AnnualNPAStmtPojo pojo = new AnnualNPAStmtPojo();
////                    if(gNo!=2){
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setClassification(classification);
                    pojo.setFormula(formula);
                    pojo.setGlheadFrom(glHeadFrom);
                    pojo.setGlheadTo(glHeadTo);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setReportName(reportName);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setDescription(description);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNumberOfAc(noOfAc);
                    pojo.setPrincipalAmt(principalAmt.divide(repOpt).abs());
                    pojo.setInterestAmt(interestAmt.divide(repOpt).abs());
                    if (totalOutStanding.compareTo(new BigDecimal("0")) == 0) {
                        pojo.setPercOfLoanAndAdv(new BigDecimal("0"));
                    } else {
                        pojo.setPercOfLoanAndAdv(totalOutStanding.divide(totalLoan, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred));
                    }
                    pojo.setProvPerc(referContent);
                    if (referContent.equalsIgnoreCase("")) {
                        pojo.setProvAmt(new BigDecimal(0.0));
                    } else {
                        pojo.setProvAmt(totalOutStanding.multiply(new BigDecimal(referContent)).divide(repOpt).divide(new BigDecimal("100")).abs());
                    }
                    pojo.setStartProv(existingProv.divide(repOpt).abs());
                    pojo.setActualProv(provCurrYear.divide(repOpt).abs());
                    pojo.setTotalProv(provCurrYear.add(existingProv).divide(repOpt).abs());
                    pojo.setExcessBDDRProv(provCurrYear.abs().subtract(existingProv.abs()).divide(repOpt));
//                    } else if(gNo==2){
//                        if(sGNo==1){
//                            pojo.setGlBalanceCurrA(glBalCurr.divide(repOpt).abs());
//                            pojo.setGlBalancePrevA(glBalPrev.divide(repOpt).abs());
//                        } else if(sGNo==2){                      
//                            pojo.setGlBalanceCurrB(glBalCurr.divide(repOpt).abs());
//                            pojo.setGlBalancePrevB(glBalPrev.divide(repOpt).abs());
//                        } else if(sGNo==3){
//                            pojo.setGlBalanceCurrC(glBalCurr.divide(repOpt).abs());
//                            pojo.setGlBalancePrevC(glBalPrev.divide(repOpt).abs());
//                        } else if(sGNo==4){                        
//                            pojo.setGlBalanceCurrT(glBalCurr.divide(repOpt).abs());
//                            pojo.setGlBalancePrevT(glBalPrev.divide(repOpt).abs());
//                        }
//                    }
                    /* For Part 2*/
                    pojo.setAsOnDate(dmy.format(ymd.parse(date)));
                    pojo.setBeginDate(dmy.format(ymd.parse(beginDate)));
                    pojo.setRemarks("");
                    pojo.setTotalOutStandingCurr(totalLoan.divide(repOpt).abs());
                    pojo.setTotalOutStandingPrev(totalOutStandingAtStart.divide(repOpt).abs());
                    pojo.setNpaAsOnDate(npaAsOnDate.divide(repOpt).abs());
                    pojo.setNpaAtStart(npaAtStart.divide(repOpt).abs());
                    if (totalLoan.compareTo(new BigDecimal("0")) == 0) {
                        pojo.setPercNPAToLoanGrossCurr(new BigDecimal("0"));
                    } else {
                        pojo.setPercNPAToLoanGrossCurr(npaAsOnDate.divide(totalLoan, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred));
                    }
                    if (npaAtStart.compareTo(new BigDecimal("0")) == 0) {
                        pojo.setPercNPAToLoanGrossPrev(new BigDecimal("0"));
                    } else {
                        pojo.setPercNPAToLoanGrossPrev(npaAtStart.divide(totalOutStandingAtStart, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred));
                    }
                    pojo.setNetAdvncCurr(totalLoan.add(glBalCurr).add(glBalCurr.add(npaAsOnDate)).divide(repOpt).abs());
                    pojo.setNetAdvncPrev(totalOutStandingAtStart.add(glBalPrev).add(glBalPrev.add(npaAtStart)).divide(repOpt).abs());
                    pojo.setNetNPACurr(npaAsOnDate.add(glBalCurr).add(glBalCurr.add(npaAsOnDate)).divide(repOpt).abs());
                    pojo.setNetNPAPrev(npaAtStart.add(glBalPrev).add(glBalPrev.add(npaAtStart)).divide(repOpt).abs());
                    pojo.setTotProvAfterBDDRCurr(glBalCurr.add(npaAsOnDate).divide(repOpt));
                    pojo.setTotProvAfterBDDRPrev(glBalPrev.add(npaAtStart).divide(repOpt));
                    pojoList.add(pojo);
                }
            }
            List<String> dates = new ArrayList<String>();
            dates.add(date);
            dates.add(beginDate);
            List<RbiSossPojo> partBList = new ArrayList<RbiSossPojo>();
            partBList = getBalanceSheet("ANNUAL_NPA_PART_B", dates, (brCode.equalsIgnoreCase("90") ? "0A" : brCode), repOpt, "Y", 0);
            AnnualNPAStmtConsolidate mainPojo = new AnnualNPAStmtConsolidate();
            mainPojo.setMainList(pojoList);
            mainPojo.setPartBList(partBList);
            mainList.add(mainPojo);
            return mainList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<StmtOfOpenCloseOfficePojo> getStmtOfOpenCloseOffice(String brCode, String fromDt, String toDate) throws ApplicationException {
        List<StmtOfOpenCloseOfficePojo> resulList = new ArrayList<StmtOfOpenCloseOfficePojo>();
        Vector vect = null;
        try {
            List brnList = em.createNativeQuery("select b.bankname,a.BranchName,b.bankaddress,a.State, a.City,ifnull(a.block,''),ifnull(a.taluk,''),ifnull(a.location_type,''),ifnull(a.office_type,''),date_format(ifnull(a.opendate,''),'%Y%m%d'),ifnull(date_format(ifnull(a.closedate,''),'%Y%m%d'),'')"
                    + " from branchmaster a, bnkadd b where  a.AlphaCode =b.alphacode  and (closedate between '" + ymd.format(dmy.parse(fromDt)) + "'  and '" + ymd.format(dmy.parse(toDate)) + "')"
                    + " UNION "
                    + " select b.bankname,a.BranchName,b.bankaddress,a.State, a.City,ifnull(a.block,''),ifnull(a.taluk,''),ifnull(a.location_type,''),ifnull(a.office_type,''),date_format(ifnull(a.opendate,''),'%Y%m%d'),ifnull(date_format(ifnull(a.closedate,''),'%Y%m%d'),'')"
                    + " from branchmaster a, bnkadd b where  a.AlphaCode =b.alphacode  and (opendate between '" + ymd.format(dmy.parse(fromDt)) + "'  and '" + ymd.format(dmy.parse(toDate)) + "')").getResultList();
            if (!brnList.isEmpty()) {
                for (int i = 0; i < brnList.size(); i++) {
                    StmtOfOpenCloseOfficePojo pojo = new StmtOfOpenCloseOfficePojo();
                    vect = (Vector) brnList.get(i);
                    pojo.setBranchName(vect.get(1).toString());
                    pojo.setBrnAdd(vect.get(2).toString());
                    pojo.setState(vect.get(3).toString());
                    pojo.setDistrict(vect.get(4).toString());
                    pojo.setBlock(vect.get(5).toString());
                    pojo.setTaluk(vect.get(6).toString());
                    pojo.setPopulation(vect.get(7).toString());
                    pojo.setOfficetype(vect.get(8).toString());
                    String openDt = vect.get(9).toString();
                    if (openDt.equalsIgnoreCase("")) {
                        pojo.setOpenDt("");
                    } else {
                        pojo.setOpenDt(dmy.format(ymd.parse(vect.get(9).toString())));
                    }
                    String closeDt = vect.get(10).toString();
                    if (closeDt.equalsIgnoreCase("")) {
                        pojo.setCloseDt("");
                    } else {
                        pojo.setCloseDt(dmy.format(ymd.parse(vect.get(10).toString())));
                    }

                    resulList.add(pojo);
                }
            } else {
                StmtOfOpenCloseOfficePojo pojo = new StmtOfOpenCloseOfficePojo();
                pojo.setBranchName("");
                pojo.setBrnAdd("");
                pojo.setState("");
                pojo.setDistrict("");
                pojo.setBlock("");
                pojo.setTaluk("");
                pojo.setPopulation("");
                pojo.setOfficetype("");
                pojo.setOpenDt("");
                pojo.setCloseDt("");
                resulList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return resulList;
    }

    public List<UCBInvstmntInOtherUCBPojo> getUCBsInvestmentInOtherUCB(String reportName, BigDecimal reptOpt, String brnCode, String asOnDate) throws ApplicationException {
        List<UCBInvstmntInOtherUCBPojo> resulList = new ArrayList<UCBInvstmntInOtherUCBPojo>();
        try {
            List ossQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                    + "report_name = '" + reportName + "' order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned), classification desc, sno ").getResultList();
            if (ossQuery.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            } else {
                BigDecimal totalLoan = new BigDecimal("0");
                for (int i = 0; i < ossQuery.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector ossVect = (Vector) ossQuery.get(i);
                    Integer sNo = Integer.parseInt(ossVect.get(0).toString());
                    String reptName = ossVect.get(1).toString();
                    String description = ossVect.get(2).toString();
                    Integer gNo = Integer.parseInt(ossVect.get(3).toString());
                    Integer sGNo = Integer.parseInt(ossVect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(ossVect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(ossVect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(ossVect.get(7).toString());
                    String classification = ossVect.get(8).toString();
                    String countApplicable = ossVect.get(9).toString();
                    String acNature = ossVect.get(10).toString();
                    String acType = ossVect.get(11).toString();
                    String glHeadFrom = ossVect.get(12).toString();
                    String glHeadTo = ossVect.get(13).toString();
                    String rangeBaseOn = ossVect.get(14).toString();
                    String rangeFrom = ossVect.get(15).toString();
                    String rangeTo = ossVect.get(16).toString();
                    String formula = ossVect.get(17).toString();
                    String fGNo = ossVect.get(18).toString();
                    String fSGNo = ossVect.get(19).toString();
                    String fSsGNo = ossVect.get(20).toString();
                    String fSssGNo = ossVect.get(21).toString();
                    String fSsssGNo = ossVect.get(22).toString();
                    String npaClassification = ossVect.get(23).toString();
                    String referIndex = ossVect.get(24).toString();
                    String referContent = ossVect.get(25).toString();
                    if (!(glHeadFrom.equalsIgnoreCase("") && glHeadTo.equalsIgnoreCase(""))) {
                        List glList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable where substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "'").getResultList();
                        if (!glList.isEmpty()) {
                            for (int j = 0; j < glList.size(); j++) {
                                UCBInvstmntInOtherUCBPojo pojo = new UCBInvstmntInOtherUCBPojo();
                                Vector vect = (Vector) glList.get(j);
                                List dataList = em.createNativeQuery("select b.SEC_TYPE,a.PURCHASE_DT,b.BOOK_VALUE, b.ROI,a.MAT_DT from investment_fdr_dates a,investment_fdr_details b where b.CTRL_NO= a.CTRL_NO and b.SELLER_NAME like '%" + vect.get(1).toString() + "%' and a.PURCHASE_DT <='" + asOnDate + "'").getResultList();
                                if (!dataList.isEmpty()) {
                                    for (int k = 0; k < dataList.size(); k++) {
                                        Vector list = (Vector) dataList.get(k);
                                        pojo.setBankName(vect.get(1).toString());
                                        pojo.setCategory(list.get(0).toString());
                                        pojo.setNameOfDeposit(list.get(0).toString());
                                        pojo.setDateOfDeposit(dmy.format(ymd.parse(list.get(1).toString())));
                                        pojo.setAmount(new BigDecimal(list.get(2).toString()).divide(reptOpt));
                                        pojo.setRoi(Double.parseDouble(list.get(3).toString()));
                                        pojo.setDateOfMaturity(dmy.format(ymd.parse(list.get(4).toString())));
                                        pojo.setRcsNo("");
                                        pojo.setRcsDate("");
                                        pojo.setRemarks("");
                                        resulList.add(pojo);
                                    }
                                }
                            }
                        } else {
                            UCBInvstmntInOtherUCBPojo pojo = new UCBInvstmntInOtherUCBPojo();
                            pojo.setBankName("");
                            pojo.setCategory("");
                            pojo.setNameOfDeposit("");
                            pojo.setDateOfDeposit("");
                            pojo.setAmount(new BigDecimal("0"));
                            pojo.setRoi(Double.parseDouble("0"));
                            pojo.setDateOfMaturity("");
                            pojo.setRcsNo("");
                            pojo.setRcsDate("");
                            pojo.setRemarks("");
                            resulList.add(pojo);
                        }
                    }
                }
                if (resulList.size() == 0) {
                    UCBInvstmntInOtherUCBPojo pojo = new UCBInvstmntInOtherUCBPojo();
                    pojo.setBankName("");
                    pojo.setCategory("");
                    pojo.setNameOfDeposit("");
                    pojo.setDateOfDeposit("");
                    pojo.setAmount(new BigDecimal("0"));
                    pojo.setRoi(Double.parseDouble("0"));
                    pojo.setDateOfMaturity("");
                    pojo.setRcsNo("");
                    pojo.setRcsDate("");
                    pojo.setRemarks("");
                    resulList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return resulList;
    }

    public List<AdvncAgnstShareDebtPojo> getAdvncAgnstShareAndDebt(String reportName, BigDecimal reptOpt, String brnCode, String asOnDate) throws ApplicationException {
        List<AdvncAgnstShareDebtPojo> resulList = new ArrayList<AdvncAgnstShareDebtPojo>();
        try {
            if (resulList.size() == 0) {
                AdvncAgnstShareDebtPojo pojo = new AdvncAgnstShareDebtPojo();
                pojo.setName("");
                pojo.setPan("");
                pojo.setStatus("");
                pojo.setDateOfAdvnc("");
                pojo.setTypeOfSecurity("");
                pojo.setNatureOfCreditFacility("");
                pojo.setSanctAmt(new BigDecimal("0"));
                pojo.setOutstndngAtEndOfQtr(new BigDecimal("0"));
                pojo.setMktValueOfShare("");
                pojo.setShareType("");
                pojo.setDueDtofRepymntOfAdvnc("");
                resulList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return resulList;
    }

    public List<DuplicateCustIdPojo> getDuplicateCustIdData(String reportType, String orderBy, String branchCode, String duplicateBy) throws ApplicationException {
        List<DuplicateCustIdPojo> dataList = new ArrayList<DuplicateCustIdPojo>();
        List result = new ArrayList();
        try {
            String branch = "", branchMerge = "";
//            if (branchCode.equalsIgnoreCase("0A")) {
//                branch = "";
//                branchMerge = "";
//            } else {
//                branch = "and a.brCode = '" + branchCode + "'";
//                branchMerge = "and primarybrcode = '" + branchCode + "'";
//            }
//            Map<Integer, String> alphaCodeMap = new HashMap<Integer, String>();
//            List alphaCodeList = em.createNativeQuery("select BrnCode,alphacode from branchmaster").getResultList();
//            for (int i = 0; i < alphaCodeList.size(); i++) {
//                Vector alpVector = (Vector) alphaCodeList.get(i);
//                alphaCodeMap.put(Integer.parseInt(alpVector.get(0).toString()), alpVector.get(1).toString());
//            }
            if (reportType.equalsIgnoreCase("M")) {
                result = em.createNativeQuery("select distinct CustFullName ,fathername ,PAN_GIRNumber ,date_format(dateofbirth,'%d/%m/%Y') as dob,customerid "
                        + "from cbs_id_merge_auth a,cbs_customer_master_detail b where a.orgId = b.customerid " + branchMerge + " order by " + orderBy + "").getResultList();
            } else {
                if (duplicateBy.equalsIgnoreCase("1")) {
                    result = em.createNativeQuery("select cuname,fname,pan,dob,custid from( "
                            + "select distinct b.custid,b.cuname,b.fname,b.pan,b.dob,b.brCode from "
                            + "(SELECT customerid as custid,CustFullName as cuname,fathername as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode "
                            + "from cbs_customer_master_detail where(PAN_GIRNumber <> ''  and PAN_GIRNumber is not null) and (fathername <> '' "
                            + "and fathername is not null) and (dateofbirth <> '' and dateofbirth is not null) and SuspensionFlg not in('Y','S') ) a, "
                            + "(SELECT customerid as custid,CustFullName as cuname,fathername as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode "
                            + "from cbs_customer_master_detail where (PAN_GIRNumber <> ''  and PAN_GIRNumber is not null) and(fathername <> '' and fathername is not null) "
                            + "and (dateofbirth <> '' and dateofbirth is not null) and SuspensionFlg not in('Y','S') ) b "
                            + "where a.custid<>b.custid and a.cuname = b.cuname and a.fname = b.fname and a.dob = b.dob and a.pan = b.pan "
                            + "and a.custid not in(select mergId from cbs_id_merge_auth union select orgId  from cbs_id_merge_auth) "
                            + "union "
                            + "select distinct a.custid,a.cuname,a.fname,a.pan,a.dob,a.brCode from "
                            + "(SELECT customerid as custid,CustFullName as cuname,fathername as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode "
                            + "from cbs_customer_master_detail where (PAN_GIRNumber <> ''  and PAN_GIRNumber is not null) and(fathername <> '' and fathername is not null) "
                            + "and (dateofbirth <> '' and dateofbirth is not null) and SuspensionFlg not in('Y','S')) a, "
                            + "(SELECT customerid as custid,CustFullName as cuname,fathername as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode "
                            + "from cbs_customer_master_detail where (PAN_GIRNumber <> ''  and PAN_GIRNumber is not null) and(fathername <> '' and fathername is not null) "
                            + "and (dateofbirth <> '' and dateofbirth is not null) and SuspensionFlg not in('Y','S') ) b  "
                            + "where a.custid<>b.custid and a.cuname = b.cuname and a.fname = b.fname and a.dob = b.dob and a.pan = b.pan "
                            + "and a.custid not in(select mergId from cbs_id_merge_auth union select orgId  from cbs_id_merge_auth) "
                            + ")f group by cuname,fname,pan,dob order by " + orderBy + " ").getResultList();
                } else if (duplicateBy.equalsIgnoreCase("2")) {
                    // Cust Name & Father Name
//                    result = em.createNativeQuery("select distinct b.custid,b.cuname,b.fname,b.dob,b.pan,b.brCode,c.AlphaCode from \n"
//                            + "(SELECT customerid as custid,CustFullName as cuname,fathername as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode \n"
//                            + "from cbs_customer_master_detail where (custname <> '' and custname is not null) and (fathername <> '' and fathername is not null) and SuspensionFlg<>'Y' ) a, \n"
//                            + "(SELECT customerid as custid,CustFullName as cuname,fathername as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode \n"
//                            + "from cbs_customer_master_detail where (custname <> '' and custname is not null) and(fathername <> '' and fathername is not null) and SuspensionFlg<>'Y' ) b,branchmaster c \n"
//                            + "where a.custid<>b.custid and a.cuname = b.cuname and a.fname = b.fname and cast(b.brCode as unsigned ) = c.BrnCode\n"
//                            + "and a.custid not in(select mergId from cbs_id_merge_auth union select orgId  from cbs_id_merge_auth) order by 2").getResultList();
                    result = em.createNativeQuery("select distinct b.custid,b.cuname,b.fname,b.dob,upper(b.pan),b.brCode,c.AlphaCode from \n"
                            + "(SELECT customerid as custid,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode \n"
                            + "from cbs_customer_master_detail where (CustFullName <> '' and CustFullName is not null) and (concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) <> '') and SuspensionFlg not in('Y','S')) a, \n"
                            + "(SELECT customerid as custid,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode \n"
                            + "from cbs_customer_master_detail where (CustFullName <> '' and CustFullName is not null) and(concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) <> '') and SuspensionFlg not in('Y','S')) b,branchmaster c \n"
                            + "where a.custid<>b.custid and a.cuname = b.cuname and a.fname = b.fname and cast(b.brCode as unsigned ) = c.BrnCode\n"
                            + "order by 2").getResultList();
                } else if (duplicateBy.equalsIgnoreCase("3")) {
                    // Pan No
                    result = em.createNativeQuery("select distinct b.custid,b.cuname,b.fname,b.dob,upper(b.pan),b.brCode,c.AlphaCode from \n"
                            + "(SELECT customerid as custid,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode \n"
                            + "from cbs_customer_master_detail where (PAN_GIRNumber REGEXP '[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}') and SuspensionFlg not in('Y','S')) a, \n"
                            + "(SELECT customerid as custid,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode \n"
                            + "from cbs_customer_master_detail where (PAN_GIRNumber REGEXP '[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}') and SuspensionFlg not in('Y','S')) b,branchmaster c\n"
                            + "where a.custid<>b.custid and a.pan = b.pan and cast(b.brCode as unsigned ) = c.BrnCode\n"
                            + "/*and a.custid not in(select mergId from cbs_id_merge_auth union select orgId  from cbs_id_merge_auth)*/  order by 5;").getResultList();
                } else if (duplicateBy.equalsIgnoreCase("4")) {
                    // Aadhar no
                    result = em.createNativeQuery("select distinct bb.custId,bb.cuname,bb.fname,bb.pan,bb.dob,bb.brCode,cc.AlphaCode,bb.Aadhar from\n"
                            + "(select cast(b.customerid as unsigned) custId,a.IdentityNo Aadhar,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode  from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid and(a.IdentityNo <> ''  and a.IdentityNo is not null and length(a.IdentityNo) = 12)and b.SuspensionFlg not in('Y','S')\n"
                            + "union\n"
                            + "select cast(a.customerid as unsigned) custId,a.IdentityNo Aadhar,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode  from cbs_customer_master_detail a where a.legal_document ='E' and(a.IdentityNo <> ''  and a.IdentityNo is not null and length(a.IdentityNo) = 12) and a.SuspensionFlg not in('Y','S')) aa,\n"
                            + "(select cast(b.customerid as unsigned) custId,a.IdentityNo Aadhar,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode  from cbs_cust_identity_details a, cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid and(a.IdentityNo <> ''  and a.IdentityNo is not null and length(a.IdentityNo) = 12) and b.SuspensionFlg not in('Y','S')\n"
                            + "union\n"
                            + "select cast(a.customerid as unsigned) custId,a.IdentityNo Aadhar,CustFullName as cuname,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as fname,PAN_GIRNumber as pan,date_format(dateofbirth,'%d/%m/%Y') as dob,primarybrcode as brCode  from cbs_customer_master_detail a where a.legal_document ='E' and(a.IdentityNo <> ''  and a.IdentityNo is not null and length(a.IdentityNo) = 12) and a.SuspensionFlg not in('Y','S'))bb,branchmaster cc\n"
                            + "where aa.custId<>bb.custId and aa.Aadhar = bb.Aadhar and cast(aa.brCode as unsigned ) = cc.BrnCode \n"
                            + "order by 8").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DuplicateCustIdPojo pojo = new DuplicateCustIdPojo();
                    if (duplicateBy.equalsIgnoreCase("1") || reportType.equalsIgnoreCase("M")) {
                        String custName = vtr.get(0).toString();
                        String fatherName = vtr.get(1).toString();
                        String panNo = vtr.get(2).toString();
                        String dob = vtr.get(3).toString();
                        String cId = "";
                        List duplicateList = new ArrayList();
                        if (reportType.equalsIgnoreCase("M")) {
                            cId = vtr.get(4).toString();
                            duplicateList = em.createNativeQuery("select mergId from cbs_id_merge_auth where orgId = '" + cId + "'").getResultList();
                        } else {
                            cId = vtr.get(4).toString();
                            duplicateList = em.createNativeQuery("select customerid from cbs_customer_master_detail where "
                                    + "CustFullName like'%" + custName + "%' and PAN_GIRNumber like '%" + panNo + "%' "
                                    + "and fathername like '%" + fatherName + "%'and dateofbirth ='" + ymd.format(dmy.parse(dob)) + "'"
                                    + "and customerid not in(select mergId from cbs_id_merge_auth union select orgId  from cbs_id_merge_auth)").getResultList();
                        }
                        Vector v = new Vector();
                        for (int j = 0; j < duplicateList.size(); j++) {
                            Vector ele = (Vector) duplicateList.get(j);
                            v.add(ele.get(0).toString());
                        }
                        String mergOriginalId = "";
                        if (duplicateBy.equalsIgnoreCase("1")) {
                            List mergOriginalIdList = em.createNativeQuery("select orgId  from cbs_id_merge_auth where orgId = '" + cId + "'").getResultList();
                            if (!mergOriginalIdList.isEmpty()) {
                                Vector ele = (Vector) mergOriginalIdList.get(0);
                                mergOriginalId = ele.get(0).toString();
                            }
                        }
                        if (reportType.equalsIgnoreCase("M")) {
                            pojo.setCustId(cId + " : " + v.toString());
                        } else {
                            if (mergOriginalId.equalsIgnoreCase("")) {
                                pojo.setCustId(v.toString());
                            } else {
                                pojo.setCustId("MergOrgId:" + mergOriginalId + "," + v.toString());
                            }
                        }
                        pojo.setCuName(custName);
                        pojo.setfName(fatherName);
                        pojo.setPanNo(panNo);
                        pojo.setDob(dob);
                    } else {
                        pojo.setCustId(vtr.get(0).toString());
                        pojo.setCuName(vtr.get(1).toString());
                        pojo.setfName(vtr.get(2).toString());
                        pojo.setDob(vtr.get(3).toString());
                        pojo.setPanNo(vtr.get(4).toString());
                        pojo.setAlphaCode(vtr.get(6).toString());
                        if (duplicateBy.equalsIgnoreCase("2")) {
                            pojo.setIdNo(vtr.get(1).toString());
                            pojo.setDuplicateId("Duplicate By Cust Name & Father Name");
                        } else if (duplicateBy.equalsIgnoreCase("3")) {
                            pojo.setIdNo(vtr.get(4).toString());
                            pojo.setDuplicateId("Duplicate By Pan No.");
                        } else if (duplicateBy.equalsIgnoreCase("4")) {
                            pojo.setAadhar(vtr.get(7).toString());
                            pojo.setIdNo(vtr.get(7).toString());
                            pojo.setDuplicateId("Duplicate By Aadhar No.");
                        }
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<DtlRegisterPojo> getDtlForInvestment(String brCode, String date, String repOpt) throws ApplicationException {
        if (brCode.equalsIgnoreCase("90")) {
            brCode = "0A";
        }
        try {
            List<DtlRegisterPojo> dtlRegList = new ArrayList<DtlRegisterPojo>();
            List reportList2 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + date + "'").getResultList();
            if (reportList2.isEmpty()) {
                throw new ApplicationException("Last Reporting Friday Date does not defined.");
            }
            Vector ele = (Vector) reportList2.get(0);
            if (ele.get(0) == null) {
                throw new ApplicationException("Last Reporting Friday Date does not defined.");
            }
            String repFriDate = ele.get(0).toString();
            repFriDate = CbsUtil.dateAdd(repFriDate, -14);
            DtlRegisterPojo dtlRegPojo = new DtlRegisterPojo();

            List oss1QueryList = em.createNativeQuery("select gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, ac_nature, ac_type, gl_head_from, gl_head_to, "
                    + "f_sss_gno, f_ssss_gno, count_applicable from cbs_ho_rbi_stmt_report  where report_name = 'form1' and f_ssss_gno ='NDTL-S' AND '" + repFriDate + "' between EFF_FR_DT and EFF_TO_DT  "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1QueryList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_ho_rbi_stmt_report");
            }
            List<RbiSossPojo> ndtlList = getDataList(oss1QueryList, brCode, repFriDate, "NDTL", repOpt);
            dtlRegPojo.setNdtlList(ndtlList);

            dtlRegList.add(dtlRegPojo);
            return dtlRegList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getVoucherWiseData(String minFrDt, String frDt, String toDt, String branch) throws ApplicationException {
        try {
            return em.createNativeQuery("select custid, sum(Interest) from (\n"
                    + "select b.custid,a.Interest from td_interesthistory a,(select b.custid,a.Acno,a.VoucherNo,date_format(min(a.dt),'%Y%m%d')vouchMinDt from tdshistory a,(select b.custid,a.Acno,VoucherNo,a.dt \n"
                    + "from tdshistory a,customerid b where  a.acno=b.acno and dt between '" + frDt + "' and '" + toDt + "')b\n"
                    + "where a.acno = b.acno and a.VoucherNo = b.VoucherNo\n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "' group by a.acno,a.VoucherNo having (vouchMinDt<='" + frDt + "'))b where a.acno=b.acno " + branch + " and a.VoucherNo=b.VoucherNo\n"
                    + "and a.dt between '" + frDt + "' and '" + toDt + "'\n"
                    + "union all\n"
                    + "select b.custid,a.Interest from td_interesthistory a,(select b.custid,a.Acno,a.VoucherNo,date_format(min(a.dt),'%Y%m%d') vouchMinDt from tdshistory a,(select b.custid,a.Acno,VoucherNo,a.dt \n"
                    + "from tdshistory a,customerid b where  a.acno=b.acno and dt between '" + frDt + "' and '" + toDt + "')b\n"
                    + "where a.acno = b.acno and a.VoucherNo = b.VoucherNo\n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "' group by a.acno,a.VoucherNo having (vouchMinDt>'" + frDt + "')) b where a.acno=b.acno " + branch + " and a.VoucherNo=b.VoucherNo\n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "'\n"
                    + "union all\n"
                    + "select custid,Interest from (\n"
                    + "select b.custid,a.Interest from rd_interesthistory a,(select b.custid,a.Acno,date_format(min(a.dt),'%Y%m%d')vouchMinDt from tdshistory a,(select b.custid,a.Acno,a.dt \n"
                    + "from tdshistory a,customerid b where  a.acno=b.acno and dt between '" + frDt + "' and '" + toDt + "')b\n"
                    + "where a.acno = b.acno \n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "' group by a.acno,a.VoucherNo having (vouchMinDt<='" + frDt + "'))b where a.acno=b.acno " + branch + " \n"
                    + "and a.dt between '" + frDt + "' and '" + toDt + "'\n"
                    + "union \n"
                    + "select b.custid,a.Interest from rd_interesthistory a,(select b.custid,a.Acno,date_format(min(a.dt),'%Y%m%d') vouchMinDt from tdshistory a,(select b.custid,a.Acno,a.dt \n"
                    + "from tdshistory a,customerid b where  a.acno=b.acno and dt between '" + frDt + "' and '" + toDt + "')b\n"
                    + "where a.acno = b.acno \n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "' group by a.acno,a.VoucherNo having (vouchMinDt>'" + frDt + "')) b where a.acno=b.acno " + branch + " \n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "'\n"
                    + ")a\n"
                    + ")aa group by custid").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getClosedVoucherWiseIntData(String minFrDt, String frDt, String toDt) throws ApplicationException {
        try {
//            return em.createNativeQuery("select b.custid,sum(Interest) from td_interesthistory a,customerid b,\n"
//                    + "(select distinct b.custid closeId from tds_reserve_history a,customerid b where a.acno = b.acno and dt between '" + frDt + "' and '" + toDt + "' and closeAcTds <> 0)c\n"
//                    + "where b.custid = c.closeId and a.acno = b.acno and a.dt between '" + minFrDt + "' and '" + toDt + "' group by b.custid").getResultList();
            return em.createNativeQuery("select cId,sum(IntAmt) from (\n"
                    + "select b.custid cId,ifnull(sum(Interest),0) IntAmt from td_interesthistory a,customerid b,\n"
                    + "(select distinct b.custid closeId from tds_reserve_history a,customerid b where a.acno = b.acno and dt between '" + frDt + "' and '" + toDt + "' and closeAcTds <> 0)c\n"
                    + "where b.custid = c.closeId and a.acno = b.acno and a.dt between '" + minFrDt + "' and '" + toDt + "' group by b.custid\n"
                    + "union all\n"
                    + "select custid cId,ifnull(sum(Interest),0) IntAmt from (\n"
                    + "select b.custid,a.Interest from rd_interesthistory a,(select b.custid,a.Acno,date_format(min(a.dt),'%Y%m%d')vouchMinDt from tdshistory a,(select b.custid,a.Acno,a.dt \n"
                    + "from tdshistory a,customerid b where  a.acno=b.acno and dt between '" + frDt + "' and '" + toDt + "')b\n"
                    + "where a.acno = b.acno \n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "' group by a.acno,a.VoucherNo having (vouchMinDt<='" + frDt + "'))b where a.acno=b.acno \n"
                    + "and a.dt between '" + frDt + "' and '" + toDt + "'\n"
                    + "union \n"
                    + "select b.custid,a.Interest from rd_interesthistory a,(select b.custid,a.Acno,date_format(min(a.dt),'%Y%m%d') vouchMinDt from tdshistory a,(select b.custid,a.Acno,a.dt \n"
                    + "from tdshistory a,customerid b where  a.acno=b.acno and dt between '" + frDt + "' and '" + toDt + "')b\n"
                    + "where a.acno = b.acno \n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "' group by a.acno,a.VoucherNo having (vouchMinDt>'" + frDt + "')) b where a.acno=b.acno \n"
                    + "and a.dt between '" + minFrDt + "' and '" + toDt + "'\n"
                    + ")a group by custid\n"
                    + ")f group by cId").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public DeducteeChallanPojo getDeducteeChallanReturnData(String tdsOption, String frDt, String toDt, String bsrCode, String totalAmount, String submissionDate, String srNo, String voucherNo, String custType, String surCharge, String eduCess, String interest, String fee, String penaltyOthers, String minQuarterDt, String finYear, String branch) throws ApplicationException {

        DeducteeChallanPojo deducteeChallan = new DeducteeChallanPojo();
        List<DeducteePojo> deducteePojos = new ArrayList<DeducteePojo>();
        List<ChallanPojo> challanPojos = new ArrayList<ChallanPojo>();
        List result = new ArrayList();
        String subCondition = "", subQuarter1 = "", minorData = "", branchCondition = "";
        try {

            if (branch.equalsIgnoreCase("0A")) {
                branchCondition = "";
            } else {
                branchCondition = "and substring(a.acno,1,2)='" + branch + "'";
            }

            List yearList = em.createNativeQuery("select distinct MINDATE,MAXDATE,F_YEAR from yearend where yearendflag='N'").getResultList();
            Vector yVector = (Vector) yearList.get(0);
            String finMinDt = yVector.get(0).toString();
            String finMaxDt = yVector.get(1).toString();
            String year = yVector.get(2).toString();
            String tdsTable = "tds_docdetail_header";

            if (!year.equalsIgnoreCase(finYear)) {
                tdsTable = "tds_docdetail_header_his";
                List yearList1 = em.createNativeQuery("select distinct MINDATE,MAXDATE,F_YEAR from yearend where F_YEAR = '" + finYear + "'").getResultList();
                yVector = (Vector) yearList1.get(0);
                finMinDt = yVector.get(0).toString();
                finMaxDt = yVector.get(1).toString();
                year = yVector.get(2).toString();
            }

            Map<String, String> vouchMap = new HashMap<String, String>();
            List dataList = getVoucherWiseData(finMinDt, frDt, toDt, branchCondition);
            for (int i = 0; i < dataList.size(); i++) {
                Vector vtr = (Vector) dataList.get(i);
                vouchMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }
            // MinorId interest of 15gh majorId
            Map<String, Double> minor15ghMap = new HashMap<String, Double>();
            List minor15ghList = em.createNativeQuery("select majrId,ifnull(sum(Interest),0) from (\n"
                    + "select cast(guardiancode as unsigned) majrId,Interest from td_interesthistory a,customerid b,(select guardiancode,CustomerId from  cbs_cust_minorinfo a,\n"
                    + "(select distinct custId majorid from tds_docdetail_header a,cbs_cust_minorinfo b where a.custid = b.guardiancode and a.custid in(select custid from tds_15gh_return where FYear = '" + year + "') and date_format(submission_date,'%Y%m%d') <='" + toDt + "'\n"
                    + ")b where a.guardiancode = b.majorid)c\n"
                    + "where b.custid = cast(c.CustomerId as unsigned) and a.acno = b.acno and dt between '" + minQuarterDt + "' and '" + toDt + "'\n"
                    + "union all\n"
                    + "select cast(guardiancode as unsigned) majrId,Interest from rd_interesthistory a,customerid b,(select guardiancode,CustomerId from  cbs_cust_minorinfo a,\n"
                    + "(select distinct custId majorid from tds_docdetail_header a,cbs_cust_minorinfo b where a.custid = b.guardiancode and a.custid in(select custid from tds_15gh_return where FYear = '" + year + "') and date_format(submission_date,'%Y%m%d') <='" + toDt + "'\n"
                    + ")b where a.guardiancode = b.majorid)c\n"
                    + "where b.custid = cast(c.CustomerId as unsigned) and a.acno = b.acno and dt between '" + minQuarterDt + "' and '" + toDt + "'\n"
                    + ")a group by majrId "
                    + "UNION ALL "
                    + "select majrId,ifnull(sum(Interest),0) from (\n"
                    + "select cast(guardiancode as unsigned) majrId,Interest from td_interesthistory a,customerid b,(select guardiancode,CustomerId from  cbs_cust_minorinfo a,\n"
                    + "(select distinct custId majorid from tds_docdetail_header a,cbs_cust_minorinfo b where a.custid = b.guardiancode and a.custid not in(select custid from tds_15gh_return where FYear = '" + year + "') and date_format(submission_date,'%Y%m%d') <='" + toDt + "'\n"
                    + ")b where a.guardiancode = b.majorid)c\n"
                    + "where b.custid = cast(c.CustomerId as unsigned) and a.acno = b.acno and dt between '" + minQuarterDt + "' and '" + toDt + "'\n"
                    + "union all\n"
                    + "select cast(guardiancode as unsigned) majrId,Interest from rd_interesthistory a,customerid b,(select guardiancode,CustomerId from  cbs_cust_minorinfo a,\n"
                    + "(select distinct custId majorid from tds_docdetail_header a,cbs_cust_minorinfo b where a.custid = b.guardiancode and a.custid not in(select custid from tds_15gh_return where FYear = '" + year + "') and date_format(submission_date,'%Y%m%d') <='" + toDt + "'\n"
                    + ")b where a.guardiancode = b.majorid)c\n"
                    + "where b.custid = cast(c.CustomerId as unsigned) and a.acno = b.acno and dt between '" + minQuarterDt + "' and '" + toDt + "'\n"
                    + ")a group by majrId").getResultList();
            for (int i = 0; i < minor15ghList.size(); i++) {
                Vector vtr = (Vector) minor15ghList.get(i);
                minor15ghMap.put(vtr.get(0).toString(), Double.parseDouble(vtr.get(1).toString()));
            }
//            Map<String, String> closedVouchMap = new HashMap<String, String>();
//            List closedVoucherIntList = getClosedVoucherWiseIntData(finMinDt, frDt, toDt);
//            for (int i = 0; i < closedVoucherIntList.size(); i++) {
//                Vector vtr = (Vector) closedVoucherIntList.get(i);
//                closedVouchMap.put(vtr.get(0).toString(), vtr.get(1).toString());
//            }
            if (custType.equalsIgnoreCase("ALL")) {
                subCondition = "";
                //  and substring(PAN_GIRNumber,4,1) <> 'C'
            } else if (custType.equalsIgnoreCase("02")) {
                //subCondition = "and cmd.CustEntityType = '" + custType + "'";
                // Pan No 4th charater C is company other Individual
                subCondition = "and substring(PAN_GIRNumber,4,1) = 'C'";
            } else {
                subCondition = "and substring(PAN_GIRNumber,4,1) <> 'C'";
            }

            double tds = 0d, tdsRateWithPan = 0d, tdsRateNoPan = 0d;
            List tdsList = em.createNativeQuery("SELECT ifnull(TDS_AMOUNT,-1),ifnull(TDS_RATE *(1 + TDS_SURCHARGE /100.0),-1),tdsRate_pan "
                    + "FROM tdsslab WHERE TYPE=1 AND TDS_APPLICABLEDATE IN(SELECT MAX(TDS_APPLICABLEDATE) FROM tdsslab WHERE TDS_APPLICABLEDATE<='" + toDt + "' AND TYPE=1)").getResultList();
            if (!tdsList.isEmpty()) {
                Vector tdVector = (Vector) tdsList.get(0);
                tds = Double.parseDouble(tdVector.get(0).toString());
                tdsRateWithPan = Double.parseDouble(tdVector.get(1).toString());
                tdsRateNoPan = Double.parseDouble(tdVector.get(2).toString());
            }

            // Sumary data
            BigDecimal totalinterestAmountt = new BigDecimal("0");
            BigDecimal totalTdsAmount = new BigDecimal("0");
            //minQuarterDt = finMinDt; // because minQuarterDt not comming proper,
            String a[] = getTotalTdsAmt26qReturn(subCondition, frDt, toDt, finMinDt, minQuarterDt, tdsTable, year, branchCondition);
            totalinterestAmountt = new BigDecimal(a[0]);
            totalTdsAmount = new BigDecimal(a[1]);
            // end Sumary data 
            double dblBenifitAge = fts.getCodeForReportName("SR-CITIZEN-AGE").doubleValue();

            result = em.createNativeQuery("select guardianId,minorId,sum(interestAmt),IntmaxDt,sum(tds),tdsMaxDt,CustFullName,Pan,CustEntityType,uniqueIdentificationNo,minorflag,DateOfBirth,DATE_ADD(DateOfBirth,INTERVAL " + dblBenifitAge + " YEAR) from (\n"
                    + "/*tds current quarter*/\n"
                    + "select cast(cmd.customerid as unsigned) guardianId,'' minorId,ina.interestAmt,ina.IntmaxDt,'0' tds,''tdsMaxDt,cmd.CustFullName,cmd.PAN_GIRNumber as Pan,cmd.CustEntityType,tr.uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth,DATE_ADD(cmd.DateOfBirth,INTERVAL " + dblBenifitAge + " YEAR) \n"
                    + "from cbs_customer_master_detail cmd, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno  " + branchCondition + " and a.dt between  '" + finMinDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + finMinDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + finMinDt + "' and '" + toDt + "' group by c.CustId \n"
                    + ")ff group by cid having(interestAmt>= 0) order by cid)ina ," + tdsTable + " tr \n"
                    + "where cmd.customerid = tr.CustId and cast(cmd.customerid as unsigned) = ina.cid and tr.custid not in(select custid from tds_15gh_return where FYear = '" + year + "')\n"
                    + "" + subCondition + " and date_format(tr.submission_date,'%Y%m%d') between '" + minQuarterDt + "' and '" + toDt + "' and tr.uniqueIdentificationNo <> ''\n"
                    + "/*tds_15gh_return (Quaretr 1)*/\n"
                    + "union \n"
                    + "select cast(cmd.customerid as unsigned) guardianId,'' minorId,ina.interestAmt,ina.IntmaxDt,'0' tds,''tdsMaxDt,cmd.CustFullName,cmd.PAN_GIRNumber as Pan,cmd.CustEntityType,tr.uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth,DATE_ADD(cmd.DateOfBirth,INTERVAL " + dblBenifitAge + " YEAR) \n"
                    + "from cbs_customer_master_detail cmd, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno  " + branchCondition + " and a.dt between  '" + minQuarterDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + minQuarterDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + minQuarterDt + "' and '" + toDt + "' group by c.CustId \n"
                    + ")ff group by cid order by cid)ina ," + tdsTable + " tr,tds_15gh_return tdr \n"
                    + "where cmd.customerid = tr.CustId and cast(cmd.customerid as unsigned) = ina.cid and tdr.FYear ='" + year + "' \n"
                    + "" + subCondition + " and date_format(tr.submission_date,'%Y%m%d') between '" + finMinDt + "' and '" + toDt + "' and tr.uniqueIdentificationNo <> ''\n"
                    + "and cmd.customerid = tdr.CustId\n"
                    + "/* No minor*/ \n"
                    + "union\n"
                    + "select cast(cmd.customerid as unsigned) guardianId, '' minorId,ina.interestAmt,ina.IntmaxDt,ifnull(tdsAmt,0),tds.maxdt,cmd.CustFullName,cmd.PAN_GIRNumber as Pan,cmd.CustEntityType,''uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth,DATE_ADD(cmd.DateOfBirth,INTERVAL " + dblBenifitAge + " YEAR) \n"
                    + "from cbs_customer_master_detail cmd,(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt \n"
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno " + branchCondition + " and a.recovered = 'R' and a.tdsRecoveredDt between  '" + frDt + "' and '" + toDt + "' group by c.CustId having(tdsAmt<>0))tds, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between  '" + frDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c  \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId \n"
                    + ")ff group by cid union\n"
                    + "select ifnull(ina.cid, tds.CustId) ,ifnull(ina.interestAmt,0),ifnull(ina.IntmaxDt,tds.maxDt) from\n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from (\n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c\n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between  '" + frDt + "' and '" + toDt + "' group by c.CustId\n"
                    + "union all\n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c\n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId\n"
                    + "union all\n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c\n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId\n"
                    + ")ff group by cid order by cid)ina\n"
                    + "right join\n"
                    + "(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt\n"
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno  " + branchCondition + " and a.recovered = 'R' and a.tdsRecoveredDt between  '" + frDt + "' and '" + toDt + "' group by c.CustId)tds\n"
                    + "on tds.CustId = ina.cid where ina.cid is null order by cid)ina \n"
                    + "where cast(cmd.customerid as unsigned) = tds.CustId and tds.CustId = ina.cid \n"
                    + "" + subCondition + " and cmd.minorflag <>'Y'\n"
                    + "/* With minor*/ \n"
                    + "union\n"
                    + "select cast(mis.guardiancode as unsigned) guardianId,cast(cmd.customerid as unsigned),ina.interestAmt,ina.IntmaxDt,ifnull(tdsAmt,0),tds.maxdt,cmd.CustFullName,cmd.PAN_GIRNumber as Pan,cmd.CustEntityType,''uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth,DATE_ADD(cmd.DateOfBirth,INTERVAL " + dblBenifitAge + " YEAR) \n"
                    + "from cbs_customer_master_detail cmd,(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt \n"
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno " + branchCondition + " and a.recovered = 'R' and a.tdsRecoveredDt between  '" + frDt + "' and '" + toDt + "' group by c.CustId having(tdsAmt<>0))tds, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between  '" + frDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c  \n"
                    + "where a.Acno = c.Acno " + branchCondition + " and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId \n"
                    + ")ff group by cid order by cid)ina,cbs_cust_minorinfo mis \n"
                    + "where cast(cmd.customerid as unsigned) = tds.CustId and tds.CustId = ina.cid \n"
                    + "and cmd.customerid = mis.CustomerId " + subCondition + "and cmd.minorflag = 'Y'\n"
                    + ")a group by guardianId order by 10").getResultList();

            double totalTaxDeductee = 0d, taxDeductee181920 = 0d;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    DeducteePojo pojo = new DeducteePojo();
                    Vector vtr = (Vector) result.get(i);
                    String custId = vtr.get(0).toString();
                    String minorId = vtr.get(1).toString();
                    String intAmt = vtr.get(2).toString();
                    String IntmaxDt = vtr.get(3).toString();
                    String tdsAmt = vtr.get(4).toString();
                    String tdsmaxDt = vtr.get(5).toString();
                    String custName = vtr.get(6).toString();
                    String panNo = vtr.get(7).toString();
                    String customerType = vtr.get(8).toString();
                    String uin = vtr.get(9).toString();
                    String minorFlag = vtr.get(10).toString();
                    String dob = vtr.get(11).toString();
                    String srCiznDob = vtr.get(12).toString();
                    String srCiznFlag = "N";
                    /*Cust Type (01)= INDIVIDUAL && AGE>=60*/
                    // if ((customerType.equalsIgnoreCase("01")) && (CbsUtil.yearDiff(ymd.parse(dob), new Date()) >= dblBenifitAge)) {
                    if ((customerType.equalsIgnoreCase("01")) && (y_m_d_h_m_sFormat.parse(srCiznDob).before(new Date()) || y_m_d_h_m_sFormat.parse(srCiznDob).equals(new Date()))) {
                        srCiznFlag = "Y";
                    } else {
                        srCiznFlag = "N";
                    }
//                    if (!uin.equalsIgnoreCase("")) {
//                        tdsAmt = "0";
//                    }
                    String majorPanforMinor = "";
                    if (!minorId.equalsIgnoreCase("")) {
                        List list1 = em.createNativeQuery("select PAN_GIRNumber from cbs_customer_master_detail where customerid = '" + custId + "'").getResultList();
                        if (!list1.isEmpty()) {
                            Vector pVector = (Vector) list1.get(0);
                            majorPanforMinor = pVector.get(0).toString();
                            panNo = majorPanforMinor;
                        }
                    }
                    BigDecimal total789 = new BigDecimal("0");
                    //totalTaxDeductee = totalTaxDeductee + Double.parseDouble(tdsAmt);
                    total789 = new BigDecimal(totalTdsAmount.doubleValue() + Double.parseDouble(interest) + Double.parseDouble(penaltyOthers));
                    taxDeductee181920 = taxDeductee181920 + Double.parseDouble(tdsAmt) + Double.parseDouble(surCharge) + Double.parseDouble(eduCess);

                    pojo.setRowNo(i + 1);
                    pojo.setChallanSerial(1);
                    pojo.setUpdateDeductee("--Select--");
                    pojo.setBsrCodeOfBranch(bsrCode);
                    pojo.setDateTaxDeposit(submissionDate);
                    pojo.setReceiptNo(srNo);
                    pojo.setSectionUnderPaymentMade("94A");
                    pojo.setTotalTaxDeductee(totalTdsAmount);
                    pojo.setInterestAmt(Double.parseDouble(interest));
                    pojo.setOtherPenalty(Double.parseDouble(penaltyOthers));
                    pojo.setTotal789(total789);
                    pojo.setSrlNo(i + 1);
                    if (panNo.trim().length() == 10) {
                        pojo.setDeducteeRefNo("");
                    } else {
                        pojo.setDeducteeRefNo("PANNOTAVBL");
                    }
                    pojo.setLastPanOfDeductee("");
                    if (panNo.trim().length() == 10) {
                        pojo.setDeducteePan(panNo);
                    } else {
                        pojo.setDeducteePan("PANNOTAVBL");
                    }
                    pojo.setNameOfDeductee(custName);
                    pojo.setCreditAmtDate(IntmaxDt);
                    pojo.setPaymentAmt(formatter2.format(Double.parseDouble(intAmt)));
                    pojo.setTds(formatter2.format(Double.parseDouble(tdsAmt)));
                    pojo.setSurChargeAmt(formatter2.format(Double.parseDouble(surCharge)));
                    pojo.setEduCessAmt(formatter2.format(Double.parseDouble(eduCess)));
                    pojo.setTotalTaxDeducted181920("");
                    pojo.setTotalTaxDeposited(formatter2.format(Double.parseDouble(tdsAmt)));
                    pojo.setLastTotalTaxDeposited("");
                    pojo.setTaxDeducteeDate(tdsmaxDt);
                    if (srCiznFlag.equalsIgnoreCase("Y")) {
                        pojo.setReasonNonLowHigherDeduction("R");
                    } else {
                        if (panNo.trim().length() == 10) {
                            pojo.setReasonNonLowHigherDeduction("--Select--");
                        } else {
                            pojo.setReasonNonLowHigherDeduction("C");
                        }
                    }
                    if (!uin.equalsIgnoreCase("")) {
                        pojo.setReasonNonLowHigherDeduction("B - In case of 15G/15H");
                    }
                    if (panNo.trim().length() == 10) {
                        if (panNo.trim().substring(3, 4).equalsIgnoreCase("C")) {
                            pojo.setDeducteeCode("01");
                        } else {
                            pojo.setDeducteeCode("02");
                        }
                    } else {
                        pojo.setDeducteeCode("02");
                    }
                    if (panNo.trim().length() == 10) {
                        pojo.setTaxDeducteeRate(formatter4.format(tdsRateWithPan));
                    } else {
                        pojo.setTaxDeducteeRate(formatter4.format(tdsRateNoPan));
                    }
                    pojo.setPaidByBookEntry("--Select--");
                    if (!uin.equalsIgnoreCase("")) {
                        pojo.setTaxDeducteeRate(formatter4.format(0));
                        if (uin.contains("G")) {
                            pojo.setCertificateNo("G" + finMaxDt.substring(2, 4) + CbsUtil.lPadding(7, Integer.parseInt(custId)));
                        } else {
                            pojo.setCertificateNo("H" + finMaxDt.substring(2, 4) + CbsUtil.lPadding(7, Integer.parseInt(custId)));
                        }
                    } else {
                        pojo.setCertificateNo(uin);
                    }
                    pojo.setCustId(custId);
                    // if first time tds cut in this case show All Interest Amt.                
                    if (uin.equalsIgnoreCase("")) {
                        // if (minorFlag.equalsIgnoreCase("Y")) {
                        double voucherWiseIntAmt = 0;
                        String vIntAmt = "0";
                        Vector idVector = new Vector();
                        List minorMajorcustIdList = em.createNativeQuery("select distinct guardiancode from cbs_cust_minorinfo where guardiancode = '" + custId + "' "
                                + "union  "
                                + "select customerid from cbs_cust_minorinfo where guardiancode = '" + custId + "'").getResultList();
                        if (!minorMajorcustIdList.isEmpty()) {
                            for (int j = 0; j < minorMajorcustIdList.size(); j++) {
                                Vector mVector = (Vector) minorMajorcustIdList.get(j);
                                String miMjId = mVector.get(0).toString();
                                if (!(ymd.parse(finMinDt).getTime() == ymd.parse(frDt).getTime())) {
                                    List list = em.createNativeQuery("select * from tds_reserve_history a,customerid b where  a.acno=b.acno and dt >= '" + finMinDt + "' and dt <'" + frDt + "' and b.custid='" + miMjId + "'").getResultList();
                                    if (list.isEmpty()) {
                                        List intList = em.createNativeQuery("select sum(intAmt) from(\n"
                                                + "select ifnull(sum(Interest),0) intAmt from rd_interesthistory a,customerid b where a.acno=b.acno and custid = '" + miMjId + "' and a.dt between '" + finMinDt + "' and '" + toDt + "'\n"
                                                + "union All\n"
                                                + "select ifnull(sum(Interest),0) intAmt from td_interesthistory a,customerid b where a.acno=b.acno and custid = '" + miMjId + "' and a.dt between '" + finMinDt + "' and '" + toDt + "'\n"
                                                + " )a").getResultList();
                                        Vector intVector = (Vector) intList.get(0);
                                        voucherWiseIntAmt = voucherWiseIntAmt + Double.parseDouble(intVector.get(0).toString());
                                        idVector.add(miMjId);
                                    } else {
                                        String vouchIntAmt = vouchMap.get(miMjId);
                                        if (vouchIntAmt == null) {
                                            vouchIntAmt = "0";
                                        }
                                        voucherWiseIntAmt = voucherWiseIntAmt + Double.parseDouble(vouchIntAmt);
                                        idVector.add(miMjId);
                                    }
                                } else {
                                    String vouchIntAmt = vouchMap.get(miMjId);
                                    if (vouchIntAmt == null) {
                                        vouchIntAmt = "0";
                                    }
                                    voucherWiseIntAmt = voucherWiseIntAmt + Double.parseDouble(vouchIntAmt);
                                    idVector.add(miMjId);
                                }
                            }
                            pojo.setCustId(idVector.toString());
                            if (voucherWiseIntAmt < Double.parseDouble(intAmt)) {
                                pojo.setPaymentAmt(formatter2.format(Double.parseDouble(intAmt)));
                            } else {
                                pojo.setPaymentAmt(formatter2.format(voucherWiseIntAmt));
                            }
                        } else {
                            //Change on 20180502                    
                            vIntAmt = vouchMap.get(custId) != null ? vouchMap.get(custId) : "0";
                            if (Double.parseDouble(vIntAmt) < Double.parseDouble(intAmt)) {
                                pojo.setPaymentAmt(formatter2.format(Double.parseDouble(intAmt)));
                            } else {
                                pojo.setPaymentAmt(formatter2.format(Double.parseDouble(vIntAmt)));
                            }
                            if (!(ymd.parse(finMinDt).getTime() == ymd.parse(frDt).getTime())) {
                                List list = em.createNativeQuery("select * from tds_reserve_history a,customerid b where  a.acno=b.acno and dt >= '" + finMinDt + "' and dt <'" + frDt + "' and b.custid='" + custId + "'").getResultList();
                                if (list.isEmpty()) {
                                    List intList = em.createNativeQuery("select sum(intAmt) from(\n"
                                            + "select ifnull(sum(Interest),0) intAmt from rd_interesthistory a,customerid b where a.acno=b.acno and custid = '" + custId + "' and a.dt between '" + finMinDt + "' and '" + toDt + "'\n"
                                            + "union All\n"
                                            + "select ifnull(sum(Interest),0) intAmt from td_interesthistory a,customerid b where a.acno=b.acno and custid = '" + custId + "' and a.dt between '" + finMinDt + "' and '" + toDt + "'\n"
                                            + " )a").getResultList();
                                    Vector intVector = (Vector) intList.get(0);
                                    pojo.setPaymentAmt(formatter2.format(Double.parseDouble(intVector.get(0).toString())));
                                }
                            }
//                            String idIntAmt = closedVouchMap.get(custId);
//                            if (idIntAmt == null) {
//                                pojo.setPaymentAmt(formatter2.format(Double.parseDouble(vIntAmt)));
//                            } else {
//                                
//                                if (Double.parseDouble(vIntAmt) > Double.parseDouble(idIntAmt)) {
//                                    pojo.setPaymentAmt(formatter2.format(Double.parseDouble(vIntAmt)));
//                                } else {
//                                    pojo.setPaymentAmt(formatter2.format(Double.parseDouble(idIntAmt)));
//                                }               
//                            }
                        }
                    } else {
                        Vector idVector = new Vector();
                        if (minor15ghMap.get(custId) != null) {
                            List minorMajorcustIdList = em.createNativeQuery("select distinct guardiancode from cbs_cust_minorinfo where guardiancode = '" + custId + "' "
                                    + "union  "
                                    + "select customerid from cbs_cust_minorinfo where guardiancode = '" + custId + "'").getResultList();
                            if (!minorMajorcustIdList.isEmpty()) {
                                for (int j = 0; j < minorMajorcustIdList.size(); j++) {
                                    Vector mVector = (Vector) minorMajorcustIdList.get(j);
                                    String miMjId = mVector.get(0).toString();
                                    idVector.add(miMjId);
                                }
                                pojo.setCustId(idVector.toString());
                                pojo.setPaymentAmt(formatter2.format(Double.parseDouble(intAmt) + minor15ghMap.get(custId)));
                            }
                        }
                    }
                    // END if first time tds cut in this case show All Interest Amt.
                    deducteePojos.add(pojo);
                }
                double totalDeposited45678 = 0d;
                totalDeposited45678 = totalTdsAmount.doubleValue() + Double.parseDouble(surCharge) + Double.parseDouble(eduCess) + Double.parseDouble(interest) + Double.parseDouble(fee) + Double.parseDouble(penaltyOthers);

                ChallanPojo chpojo = new ChallanPojo();
                chpojo.setSrlNo(Integer.parseInt("1"));
                chpojo.setUpdateDeductee("");
                chpojo.setSectionCode("");

                chpojo.setTds(new BigDecimal(formatter2.format(totalTdsAmount.doubleValue())));
                chpojo.setSurChargeAmt(formatter2.format(Double.parseDouble(surCharge)));
                chpojo.setEduCessAmt(formatter2.format(Double.parseDouble(eduCess)));
                chpojo.setInterestAmt(formatter2.format(Double.parseDouble(interest)));
                chpojo.setFees(formatter2.format(Double.parseDouble(fee)));
                chpojo.setOtherPenalty(formatter2.format(Double.parseDouble(penaltyOthers)));
                chpojo.setLastTotalTaxDeposited("");
                chpojo.setTotalAmtDeposit456789("");
                chpojo.setChallanDDoNo("");
                chpojo.setLastBsrCode("");
                chpojo.setBsrCodeRef(bsrCode);
                chpojo.setLastDateTaxDeposit("");
                chpojo.setDateOnWhichAmtDeposited(submissionDate);
                chpojo.setLastDdovoucherchallanSrno("");
                chpojo.setChallanSerial(Integer.parseInt(srNo));
                chpojo.setModeOfDepositYesno("No");
                chpojo.setInterestToBeAllocated(formatter2.format(0));
                chpojo.setOthers(formatter2.format(0));
                chpojo.setMinorNo("200");
                chpojo.setChallanBalance("");
                challanPojos.add(chpojo);
            }

            deducteeChallan.setDeducteeList(deducteePojos);
            deducteeChallan.setChallanList(challanPojos);
            return deducteeChallan;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String[] getTotalTdsAmt26qReturn(String condition, String frDate, String toDate, String finMinDt, String minQuterDt, String table, String fYear, String branch) throws ApplicationException {
        List result = new ArrayList();
        String arr[] = new String[2];

        try {
            result = em.createNativeQuery("select sum(interestAmt),sum(tds)from (\n"
                    + "/*tds current quarter*/\n"
                    + "select cast(cmd.customerid as unsigned) guardianId,'' minorId,ina.interestAmt,ina.IntmaxDt,'0' tds,''tdsMaxDt,cmd.CustFullName,ifnull(if (exists(select ifnull(IdentityNo,'') \n"
                    + "from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid  \n"
                    + "and aa.customerid = cmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')), ifnull(cmd.PAN_GIRNumber,'')) as Pan,cmd.CustEntityType,tr.uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth \n"
                    + "from cbs_customer_master_detail cmd, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between  '" + finMinDt + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + finMinDt + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + finMinDt + "' and '" + toDate + "' group by c.CustId \n"
                    + ")ff group by cid having(interestAmt>= 0) order by cid)ina ," + table + " tr \n"
                    + "where cmd.customerid = tr.CustId and cast(cmd.customerid as unsigned) = ina.cid and tr.custid not in(select custid from tds_15gh_return where FYear = '" + fYear + "') \n"
                    + "" + condition + " and date_format(tr.submission_date,'%Y%m%d') between '" + minQuterDt + "' and '" + toDate + "' and tr.uniqueIdentificationNo <> ''\n"
                    + "/*tds_15gh_return (Quaretr 1)*/\n"
                    + "union \n"
                    + "select cast(cmd.customerid as unsigned) guardianId,'' minorId,ina.interestAmt,ina.IntmaxDt,'0' tds,''tdsMaxDt,cmd.CustFullName,ifnull(if (exists(select ifnull(IdentityNo,'') \n"
                    + "from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid  \n"
                    + "and aa.customerid = cmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')), ifnull(cmd.PAN_GIRNumber,'')) as Pan,cmd.CustEntityType,tr.uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth \n"
                    + "from cbs_customer_master_detail cmd, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between  '" + minQuterDt + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + minQuterDt + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + minQuterDt + "' and '" + toDate + "' group by c.CustId \n"
                    + ")ff group by cid order by cid)ina ," + table + " tr,tds_15gh_return tdr \n"
                    + "where cmd.customerid = tr.CustId and cast(cmd.customerid as unsigned) = ina.cid and tdr.FYear ='" + fYear + "' \n"
                    + "" + condition + " and date_format(tr.submission_date,'%Y%m%d') between '" + finMinDt + "' and '" + toDate + "' and tr.uniqueIdentificationNo <> ''\n"
                    + "and cmd.customerid = tdr.CustId\n"
                    + "/* No minor*/ \n"
                    + "union\n"
                    + "select cast(cmd.customerid as unsigned) guardianId, '' minorId,ina.interestAmt,ina.IntmaxDt,ifnull(tdsAmt,0),tds.maxdt,cmd.CustFullName,ifnull(if (exists(select ifnull(IdentityNo,'') \n"
                    + "from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid \n"
                    + "and aa.customerid = cmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')), ifnull(cmd.PAN_GIRNumber,'')) as Pan,cmd.CustEntityType,''uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth \n"
                    + "from cbs_customer_master_detail cmd,(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt \n"
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno " + branch + " and a.recovered = 'R' and a.tdsRecoveredDt between  '" + frDate + "' and '" + toDate + "' group by c.CustId having(tdsAmt<>0))tds, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between  '" + frDate + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + frDate + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c  \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + frDate + "' and '" + toDate + "' group by c.CustId \n"
                    + ")ff group by cid union\n"
                    + "select ifnull(ina.cid, tds.CustId) ,ifnull(ina.interestAmt,0),ifnull(ina.IntmaxDt,tds.maxDt) from\n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from (\n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c\n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between  '" + frDate + "' and '" + toDate + "' group by c.CustId\n"
                    + "union all\n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c\n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + frDate + "' and '" + toDate + "' group by c.CustId\n"
                    + "union all\n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c\n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + frDate + "' and '" + toDate + "' group by c.CustId\n"
                    + ")ff group by cid order by cid)ina\n"
                    + "right join\n"
                    + "(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt\n"
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno " + branch + " and a.recovered = 'R' and a.tdsRecoveredDt between  '" + frDate + "' and '" + toDate + "' group by c.CustId)tds\n"
                    + "on tds.CustId = ina.cid where ina.cid is null order by cid)ina \n"
                    + "where cast(cmd.customerid as unsigned) = tds.CustId and tds.CustId = ina.cid \n"
                    + "" + condition + " and cmd.minorflag <>'Y'\n"
                    + "/* With minor*/ \n"
                    + "union\n"
                    + "select cast(mis.guardiancode as unsigned) guardianId,cast(cmd.customerid as unsigned),ina.interestAmt,ina.IntmaxDt,ifnull(tdsAmt,0),tds.maxdt,cmd.CustFullName,ifnull(if (exists(select ifnull(IdentityNo,'') \n"
                    + "from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),  \n"
                    + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid \n"
                    + "and aa.customerid = cmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')), ifnull(cmd.PAN_GIRNumber,'')) as Pan,cmd.CustEntityType,''uniqueIdentificationNo,cmd.minorflag,cmd.DateOfBirth \n"
                    + "from cbs_customer_master_detail cmd,(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt \n"
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno " + branch + " and a.recovered = 'R' and a.tdsRecoveredDt between  '" + frDate + "' and '" + toDate + "' group by c.CustId having(tdsAmt<>0))tds, \n"
                    + "(select cid,sum(intAmt)interestAmt,IntmaxDt from ( \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from td_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between  '" + frDate + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from rd_interesthistory a, customerid c \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + frDate + "' and '" + toDate + "' group by c.CustId \n"
                    + "union all \n"
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')IntmaxDt  from dds_interesthistory a, customerid c  \n"
                    + "where a.Acno = c.Acno " + branch + " and a.dt between '" + frDate + "' and '" + toDate + "' group by c.CustId \n"
                    + ")ff group by cid order by cid)ina,cbs_cust_minorinfo mis \n"
                    + "where cast(cmd.customerid as unsigned) = tds.CustId and tds.CustId = ina.cid \n"
                    + "and cmd.customerid = mis.CustomerId " + condition + "and cmd.minorflag = 'Y'\n"
                    + ")a ").getResultList();

            Vector totalVector = (Vector) result.get(0);
            String totalIntAmt = totalVector.get(0).toString();
            String totalTdsAmt = totalVector.get(1).toString();

            arr[0] = totalIntAmt;
            arr[1] = totalTdsAmt;


        } catch (Exception ex) {
        }

        return arr;
    }

    /*NOTE: VERY INPORTANT
     * If you are doing the changes in getLoanDepositAnnextureData() METHOD, Also mofidy the getLoanDepositAnnextureDataSummary()*/
    @Override
    public List<ExpenditureBalDayWisePojo> getLoanDepositAnnextureData(String branchCode, String reportType, String tillDate) throws ApplicationException {
        List<ExpenditureBalDayWisePojo> dataList = new ArrayList<>();
        try {
            String branchCondition = "", brnCondition = "";
            if (!branchCode.equalsIgnoreCase("0A")) {
                branchCondition = "and substring(acno,1,2)='" + branchCode + "'";
            }
            if (reportType.equalsIgnoreCase("SO") || reportType.equalsIgnoreCase("SC") || reportType.equalsIgnoreCase("NP")) {
                if (!branchCode.equalsIgnoreCase("0A")) {
                    brnCondition = "and curbrcode = '" + branchCode + "'";
                }
            }
            Map<Integer, String> brMap = new HashMap<>();
            List brList = em.createNativeQuery("select BrnCode,alphacode from branchmaster").getResultList();
            for (int i = 0; i < brList.size(); i++) {
                Vector brVector = (Vector) brList.get(i);
                brMap.put(Integer.parseInt(brVector.get(0).toString()), brVector.get(1).toString());
            }
            String fdMatHead = "", rdMatHead = "";
            List resultList = em.createNativeQuery("select Code from glbmast where Description like '%MATURED FD%'").getResultList();
            Vector eleMat;
            if (!resultList.isEmpty()) {
                eleMat = (Vector) resultList.get(0);
                fdMatHead = eleMat.get(0).toString();
            }
            resultList = em.createNativeQuery("select Code from glbmast where Description like '%MATURED RD%'").getResultList();
            if (!resultList.isEmpty()) {
                eleMat = (Vector) resultList.get(0);
                rdMatHead = eleMat.get(0).toString();
            }
//            Map<String, Integer> map = getAllMonthsn(tillDate);
            List map = getAllMonthsn(tillDate);
//            int v = map.get(CbsUtil.getMonthName(Integer.parseInt(tillDate.substring(4, 6))));
//            List prevList = em.createNativeQuery("select max(MAXDATE) from yearend where yearendflag='Y'").getResultList();
//            Vector yVector = (Vector) prevList.get(0);
            String prvfinYear = map.get(0).toString();

            if ((reportType.equalsIgnoreCase("SO") || reportType.equalsIgnoreCase("SC"))) {
                int month = Integer.parseInt(tillDate.substring(4, 6));
                String toDate = ymd.format(CbsUtil.calculateMonthEndDate(month, Integer.parseInt(tillDate.substring(0, 4))));
                String firstDt = tillDate.substring(0, 4) + tillDate.substring(4, 6) + "01";
                String comDt = "", openedCond = "";
                if (reportType.equalsIgnoreCase("SO")) {
                    comDt = "OpeningDt";
                    openedCond = "";
                } else {
                    comDt = "ClosingDate";
                    openedCond = "and OpeningDt <='" + toDate + "'";
                }
                List natList = em.createNativeQuery("select AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "' "
                        + "union all "
                        + "select AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "'and CrDbFlag = 'C' "
                        + "union all "
                        + "select AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "'and CrDbFlag = 'D' "
                        + "union all "
                        + "select ''AcctCode,accttype,acctnature from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') "
                        + "union all "
                        + "select ''AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "' group by acctnature "
                        + "union all "
                        + "select ''AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.TERM_LOAN + "' group by acctnature "
                        + "union all "
                        + "select ''AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.DEMAND_LOAN + "' group by acctnature "
                        + "union all "
                        + "select ''AcctCode,accttype,acctnature from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "' group by acctnature").getResultList();
                for (int k = 0; k < natList.size(); k++) {
                    Vector natVector = (Vector) natList.get(k);
                    String actCode = natVector.get(0).toString();
                    String accType = natVector.get(1).toString();
                    String acctNature = natVector.get(2).toString();
                    List list2 = new ArrayList();
                    if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        list2 = em.createNativeQuery("select curbrcode,b.accttype nature,count(ACNo),c.AlphaCode  from accountmaster a,accounttypemaster b,branchmaster c "
                                + "where " + comDt + " between '" + firstDt + "' and '" + toDate + "'  " + openedCond + " and a.Accttype= AcctCode and acctnature = '" + acctNature + "' "
                                + "and curbrcode = c.BrnCode " + brnCondition + "group by curbrcode").getResultList();
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        list2 = em.createNativeQuery("select curbrcode,b.accttype nature,count(ACNo),c.AlphaCode  from td_accountmaster a,accounttypemaster b,branchmaster c "
                                + "where " + comDt + " between '" + firstDt + "' and '" + toDate + "' " + openedCond + " and a.Accttype= AcctCode and acctnature = '" + acctNature + "' "
                                + "and curbrcode = c.BrnCode " + brnCondition + "group by curbrcode").getResultList();
                    } else {
                        list2 = em.createNativeQuery("select curbrcode,b.accttype nature,count(ACNo),c.AlphaCode  from accountmaster a,accounttypemaster b,branchmaster c "
                                + "where " + comDt + " between '" + firstDt + "' and '" + toDate + "' " + openedCond + " and a.Accttype= AcctCode and acctnature = '" + acctNature + "' "
                                + "and (CrDbFlag = 'D' OR CrDbFlag = 'C') and AcctCode = '" + actCode + "' and curbrcode = c.BrnCode " + brnCondition + "group by curbrcode").getResultList();
                    }

                    if (!list2.isEmpty()) {
                        for (int j = 0; j < list2.size(); j++) {
                            Vector ele = (Vector) list2.get(j);
                            ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                            String branch = ele.get(3).toString();
                            String nature = ele.get(1).toString();
                            String bal = ele.get(2).toString();
                            pojo.setBranch(branch);
                            pojo.setGlName(nature);
                            pojo.setExpsBal(new BigDecimal(bal).abs());
                            dataList.add(pojo);
                        }
                    }
                }
            } else if (reportType.equalsIgnoreCase("NP")) {
                int month = Integer.parseInt(tillDate.substring(4, 6));
                String toDate = ymd.format(CbsUtil.calculateMonthEndDate(month, Integer.parseInt(tillDate.substring(0, 4))));
                String firstDt = tillDate.substring(0, 4) + tillDate.substring(4, 6) + "01";
                List list2 = new ArrayList();
                list2 = em.createNativeQuery("select aa.curbrcode,openedAcno,closedAcno,aa.AlphaCode from ( "
                        + "(select curbrcode,sum(opened) openedAcno,AlphaCode from("
                        + "select curbrcode,count(ACNo) opened,c.AlphaCode  from accountmaster a,accounttypemaster b,branchmaster c "
                        + "where OpeningDt between '" + firstDt + "' and '" + toDate + "'and a.Accttype= AcctCode "
                        + "and curbrcode = c.BrnCode " + brnCondition + "group by curbrcode "
                        + "union all "
                        + "select curbrcode,count(ACNo) opened,c.AlphaCode  from td_accountmaster a,accounttypemaster b,branchmaster c "
                        + "where OpeningDt between '" + firstDt + "' and '" + toDate + "'and a.Accttype= AcctCode "
                        + "and curbrcode = c.BrnCode " + brnCondition + " group by curbrcode "
                        + ")a group by curbrcode)aa, "
                        + "(select curbrcode,sum(closed) closedAcno,AlphaCode from( "
                        + "select curbrcode,count(ACNo) closed,c.AlphaCode  from accountmaster a,accounttypemaster b,branchmaster c "
                        + "where ClosingDate between '" + firstDt + "' and '" + toDate + "'and a.Accttype= AcctCode "
                        + "and curbrcode = c.BrnCode " + brnCondition + " group by curbrcode "
                        + "union all "
                        + "select curbrcode,count(ACNo) closed,c.AlphaCode  from td_accountmaster a,accounttypemaster b,branchmaster c "
                        + "where ClosingDate between '" + firstDt + "' and '" + toDate + "'and a.Accttype= AcctCode "
                        + "and curbrcode = c.BrnCode " + brnCondition + " group by curbrcode "
                        + ")b group by curbrcode)bb "
                        + ")where aa.curbrcode = bb.curbrcode order by 4").getResultList();

                if (!list2.isEmpty()) {
                    for (int i = 0; i < list2.size(); i++) {
                        Vector vtr = (Vector) list2.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(vtr.get(3).toString());
                        pojo.setOpenedAcno(Integer.parseInt(vtr.get(1).toString()));
                        pojo.setClosedAcno(Integer.parseInt(vtr.get(2).toString()));
                        pojo.setNetOpenAcno(pojo.getOpenedAcno() - pojo.getClosedAcno());
                        dataList.add(pojo);
                    }
                }
            } else {
                for (int i = 0; i < map.size(); i++) {
//                    int month = Integer.parseInt(prvfinYear.substring(4, 6)) + i;
                    String toDate = map.get(i).toString();//ymd.format(CbsUtil.calculateMonthEndDate(month, Integer.parseInt(prvfinYear.substring(0, 4))));
                    if (ymd.parse(toDate).after(ymd.parse(tillDate))) {
                        toDate = tillDate;
                    }
                    List list1 = new ArrayList();
                    if (reportType.equalsIgnoreCase("D")) {
                        list1 = em.createNativeQuery("select substring(acno,1,2) branch,'sb'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from recon where dt <='" + toDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0) "
                                + "union all "
                                + "select substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in"
                                + "(select acno  from ca_recon where dt <= '" + toDate + "' " + branchCondition + ""
                                + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)"
                                + "and dt <= '" + toDate + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                                + "union all "
                                + "select branch,nature,sum(balance) balance from ( "
                                + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + toDate + "' " + branchCondition + " "
                                + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)"
                                + "union all "
                                + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + toDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                                + ")a group by branch having(balance >0)"
                                + "union all "
                                + "select branch,nature,sum(balance) balance from ( "
                                + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + toDate + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0)"
                                + "union all "
                                + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + toDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                                + ")b group by branch having(balance >0) "
                                + "union all "
                                + "select substring(acno,1,2) branch ,'ds'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from ddstransaction "
                                + "where dt <='" + toDate + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0)").getResultList();
                    } else if (reportType.equalsIgnoreCase("L")) {
                        list1 = em.createNativeQuery("select branch,AcctType nature,balance from "
                                + "(select substring(acno,3,2) acType,substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in "
                                + "(select acno  from ca_recon where dt <= '" + toDate + "'  " + branchCondition + " "
                                + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                                + "and dt <= '" + toDate + "'  and auth ='Y' group by substring(acno,1,2),substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0))a, "
                                + "accounttypemaster b where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D' and acType= AcctCode "
                                + "union all "
                                + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                                + "from loan_recon r,accounttypemaster a where dt <='" + toDate + "' " + branchCondition + "  "
                                + "and substring(acno,3,2)= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by  substring(acno,1,2),AcctCode having(balance <>0) "
                                + "union all "
                                + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                                + "from loan_recon r,accounttypemaster a where dt <='" + toDate + "' " + branchCondition + " "
                                + "and substring(acno,3,2)= AcctCode and acctnature= '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by  substring(acno,1,2),AcctCode having(balance <>0) "
                                + "").getResultList();
                    } else if (reportType.equalsIgnoreCase("N")) {
                        list1 = em.createNativeQuery("select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo)  from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.SAVING_AC + "' and CrDbFlag = 'C' group by curbrcode "
                                + "union all "
                                + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C'group by  curbrcode,a.Accttype "
                                + "union all "
                                + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) from td_accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') and CrDbFlag = 'C' group by curbrcode "
                                + "union all "
                                + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.RECURRING_AC + "' and CrDbFlag = 'C' group by curbrcode "
                                + "union all "
                                + "select curbrcode,b.Accttype nature,count(ACNo) from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D'group by curbrcode,a.Accttype "
                                + "union all "
                                + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by curbrcode "
                                + "union all "
                                + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' "
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " "
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by curbrcode "
                                + "union all "
                                + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) from accountmaster a,accounttypemaster b where OpeningDt <='" + toDate + "' \n"
                                + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + toDate + "') " + branchCondition + " \n"
                                + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode").getResultList();
                    }
                    if (!list1.isEmpty()) {
                        for (int j = 0; j < list1.size(); j++) {
                            Vector ele = (Vector) list1.get(j);
                            ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                            String branch = ele.get(0).toString();
                            String nature = ele.get(1).toString();
                            String bal = ele.get(2).toString();
                            pojo.setBranch(brMap.get(Integer.parseInt(branch)));
                            pojo.setGlName(nature.toUpperCase());
                            pojo.setExpsBal(new BigDecimal(bal).abs());
                            pojo.setTxnDate(toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8));
                            dataList.add(pojo);
                        }
                    }
                }
                if (reportType.equalsIgnoreCase("D")) {
                    List list2 = new ArrayList();
                    String prevtilDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(tillDate.substring(4, 6)) - 1, Integer.parseInt(tillDate.substring(0, 4))));
                    if (tillDate.substring(4, 6).equalsIgnoreCase("01")) {
                        prevtilDt = CbsUtil.dateAdd(prevtilDt, 2);
                    }
                    list2 = em.createNativeQuery("select a.branch,a.nature,a.balance,b.balance,a.balance-b.balance from "
                            + "(select substring(acno,1,2) branch,'sb'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from recon where dt <='" + tillDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in"
                            + "(select acno  from ca_recon where dt <= '" + tillDate + "' " + branchCondition + ""
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)"
                            + "and dt <= '" + tillDate + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + tillDate + "' " + branchCondition + " "
                            + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + tillDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")a group by branch having(balance >0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + tillDate + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + tillDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")b group by branch having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ds'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from ddstransaction where dt <='" + tillDate + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0))a,"
                            + ""
                            + "(select substring(acno,1,2) branch,'sb'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from recon where dt <='" + prevtilDt + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in"
                            + "(select acno  from ca_recon where dt <= '" + prevtilDt + "' " + branchCondition + ""
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)"
                            + "and dt <= '" + prevtilDt + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + prevtilDt + "' " + branchCondition + " "
                            + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + prevtilDt + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")a group by branch having(balance >0)"
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + prevtilDt + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + prevtilDt + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")b group by branch having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ds'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from ddstransaction where dt <='" + prevtilDt + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0))b "
                            + "where a.branch = b.branch and a.nature =b.nature").getResultList();
                    for (int i = 0; i < list2.size(); i++) {
                        Vector ele = (Vector) list2.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(common.getAlphacodeByBrncode(ele.get(0).toString()));
                        pojo.setGlName(ele.get(1).toString().toUpperCase());
                        pojo.setExpsBal(new BigDecimal(ele.get(4).toString()));
                        pojo.setTxnDate("For the month");
                        dataList.add(pojo);
                    }

                    list2 = em.createNativeQuery("select a.branch,a.nature,a.balance,b.balance,a.balance-b.balance from "
                            + "(select substring(acno,1,2) branch,'sb'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from recon where dt <='" + tillDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in"
                            + "(select acno  from ca_recon where dt <= '" + tillDate + "' " + branchCondition + ""
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)"
                            + "and dt <= '" + tillDate + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + tillDate + "' " + branchCondition + " "
                            + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + tillDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")a group by branch having(balance >0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + tillDate + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + tillDate + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")b group by branch having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ds'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from ddstransaction where dt <='" + tillDate + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0))a,"
                            + ""
                            + "(select substring(acno,1,2) branch,'sb'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from recon where dt <='" + prvfinYear + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in"
                            + "(select acno  from ca_recon where dt <= '" + prvfinYear + "' " + branchCondition + ""
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)"
                            + "and dt <= '" + prvfinYear + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + prvfinYear + "' " + branchCondition + " "
                            + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + prvfinYear + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")a group by branch having(balance >0) "
                            + "union all "
                            + "select branch,nature,sum(balance) balance from ( "
                            + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + prvfinYear + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0)"
                            + "union all "
                            + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + prvfinYear + "' " + branchCondition + " group by substring(acno,1,2) having(balance >0)"
                            + ")b group by branch having(balance >0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ds'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from ddstransaction where dt <='" + prvfinYear + "' " + branchCondition + " group by  substring(acno,1,2) having(balance >0))b "
                            + "where a.branch = b.branch and a.nature =b.nature").getResultList();

                    for (int i = 0; i < list2.size(); i++) {
                        Vector ele = (Vector) list2.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(common.getAlphacodeByBrncode(ele.get(0).toString()));
                        pojo.setGlName(ele.get(1).toString().toUpperCase());
                        pojo.setExpsBal(new BigDecimal(ele.get(4).toString()));
                        pojo.setTxnDate("Upto the month");
                        dataList.add(pojo);
                    }

//                    // For Sumary Report
//                    v = map.get(CbsUtil.getMonthName(Integer.parseInt(tillDate.substring(4, 6))));
//                    for (int i = 0; i <= v; i++) {
//                        int month = Integer.parseInt(prvfinYear.substring(4, 6)) + i;
//                        String toDate = ymd.format(CbsUtil.calculateMonthEndDate(month, Integer.parseInt(prvfinYear.substring(0, 4))));
//                        if (ymd.parse(toDate).after(ymd.parse(tillDate))) {
//                            toDate = tillDate;
//                        }
//                        double sbCaBal = 0d, fdRdBal = 0d, sbCaFdBal = 0d;
//                        List sumlist = em.createNativeQuery("select 'YYYY'branch,'SB' nature,cast(ifnull(sum(cramt-dramt),0)/100000 as decimal(25,2)) balance from recon where dt<='" + toDate + "'\n"
//                                + "union all\n"
//                                + "SELECT 'YYYY'branch,nature,sum(balance) from\n"
//                                + "(select 'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in\n"
//                                + "(select acno  from ca_recon where dt <= '" + toDate + "' \n"
//                                + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)\n"
//                                + "and dt <= '" + toDate + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0))a\n"
//                                + "union all\n"
//                                + "select 'YYYY'branch,'FD/RD' nature,sum(balance)from(\n"
//                                + "select nature,sum(balance) balance from ( \n"
//                                + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + toDate + "' \n"
//                                + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)\n"
//                                + "union all \n"
//                                + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon \n"
//                                + "where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + toDate + "' group by substring(acno,1,2) having(balance >0)\n"
//                                + ")a\n"
//                                + "union all\n"
//                                + "select nature,sum(balance) balance from ( \n"
//                                + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + toDate + "' group by  substring(acno,1,2) having(balance >0)\n"
//                                + "union all \n"
//                                + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance \n"
//                                + "from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + toDate + "' group by substring(acno,1,2) having(balance >0)\n"
//                                + ")b)aa").getResultList();
//                        
//                        for (int j = 0; j < sumlist.size(); j++) {
//                            Vector ele = (Vector) sumlist.get(j);
//                            ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
//                            pojo.setBranch(ele.get(0).toString());
//                            pojo.setGlName(ele.get(1).toString().toUpperCase());
//                            pojo.setExpsBal(new BigDecimal(ele.get(2).toString()));
//                            if (ele.get(1).toString().equalsIgnoreCase("sb") || ele.get(1).toString().equalsIgnoreCase("ca")) {
//                                sbCaBal = sbCaBal + Double.parseDouble(ele.get(2).toString());
//                            } else {
//                                fdRdBal = fdRdBal + Double.parseDouble(ele.get(2).toString());
//                            }
//                            sbCaFdBal = sbCaFdBal + Double.parseDouble(ele.get(2).toString());
//                            pojo.setTxnDate(toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8));
//                            dataList.add(pojo);
//                        }
//                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
//                        pojo.setBranch("ZZZZ");
//                        pojo.setGlName("CASA %");
//                        pojo.setExpsBal(new BigDecimal((sbCaBal / sbCaFdBal) * 100));
//                        pojo.setTxnDate(toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8));
//                        dataList.add(pojo);
//                    }
//                    // End For Sumary Report

                } else if (reportType.equalsIgnoreCase("L")) {
                    String prevtilDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(tillDate.substring(4, 6)) - 1, Integer.parseInt(tillDate.substring(0, 4))));
                    if (tillDate.substring(4, 6).equalsIgnoreCase("01")) {
                        prevtilDt = CbsUtil.dateAdd(prevtilDt, 2);
                    }
                    List list2 = em.createNativeQuery("select a.branch,a.nature,a.balance,b.balance,abs(a.balance)-abs(b.balance) from "
                            + "(select branch,AcctType nature,balance from "
                            + "(select substring(acno,3,2) acType,substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in "
                            + "(select acno  from ca_recon where dt <= '" + tillDate + "' " + branchCondition + " "
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                            + "and dt <= '" + tillDate + "'  and auth ='Y' group by substring(acno,1,2),substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0))a, "
                            + "accounttypemaster b where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D' and acType= AcctCode "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + tillDate + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by substring(acno,1,2),AcctCode having(balance <>0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + tillDate + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature= '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by  substring(acno,1,2),nature having(balance <>0))a, "
                            + "(select branch,AcctType nature,balance from "
                            + "(select substring(acno,3,2) acType,substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in "
                            + "(select acno  from ca_recon where dt <= '" + prevtilDt + "'  " + branchCondition + " "
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                            + "and dt <= '" + prevtilDt + "'  and auth ='Y' group by substring(acno,1,2),substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0))a, "
                            + "accounttypemaster b where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D' and acType= AcctCode "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + prevtilDt + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by substring(acno,1,2),AcctCode having(balance <>0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + prevtilDt + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature= '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by substring(acno,1,2),nature having(balance <>0))b "
                            + "where a.branch = b.branch and a.nature =b.nature").getResultList();
                    for (int i = 0; i < list2.size(); i++) {
                        Vector ele = (Vector) list2.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(common.getAlphacodeByBrncode(ele.get(0).toString()));
                        pojo.setGlName(ele.get(1).toString().toUpperCase());
                        pojo.setExpsBal(new BigDecimal(ele.get(4).toString()));
                        pojo.setTxnDate("For the month");
                        dataList.add(pojo);
                    }
                    list2 = em.createNativeQuery("select a.branch,a.nature,a.balance,b.balance,abs(a.balance)-abs(b.balance) from "
                            + "(select branch,AcctType nature,balance from "
                            + "(select substring(acno,3,2) acType,substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in "
                            + "(select acno  from ca_recon where dt <= '" + tillDate + "' " + branchCondition + " "
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                            + "and dt <= '" + tillDate + "'  and auth ='Y' group by substring(acno,1,2),substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0))a, "
                            + "accounttypemaster b where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D' and acType= AcctCode "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + tillDate + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by substring(acno,1,2),AcctCode having(balance <>0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + tillDate + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature= '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by  substring(acno,1,2),nature having(balance <>0))a, "
                            + "(select branch,AcctType nature,balance from "
                            + "(select substring(acno,3,2) acType,substring(acno,1,2) branch ,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in "
                            + "(select acno  from ca_recon where dt <= '" + prvfinYear + "'  " + branchCondition + " "
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                            + "and dt <= '" + prvfinYear + "'  and auth ='Y' group by substring(acno,1,2),substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0))a, "
                            + "accounttypemaster b where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D' and acType= AcctCode "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + prvfinYear + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by substring(acno,1,2),AcctCode having(balance <>0) "
                            + "union all "
                            + "select substring(acno,1,2) branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance "
                            + "from loan_recon r,accounttypemaster a where dt <='" + prvfinYear + "' " + branchCondition + " "
                            + "and substring(acno,3,2)= AcctCode and acctnature= '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by substring(acno,1,2),nature having(balance <>0))b "
                            + "where a.branch = b.branch and a.nature =b.nature").getResultList();
                    for (int i = 0; i < list2.size(); i++) {
                        Vector ele = (Vector) list2.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(common.getAlphacodeByBrncode(ele.get(0).toString()));
                        pojo.setGlName(ele.get(1).toString().toUpperCase());
                        pojo.setExpsBal(new BigDecimal(ele.get(4).toString()));
                        pojo.setTxnDate("Upto the month");
                        dataList.add(pojo);
                    }
                } else if (reportType.equalsIgnoreCase("N")) {
                    String prevtilDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(tillDate.substring(4, 6)) - 1, Integer.parseInt(tillDate.substring(0, 4))));
                    if (tillDate.substring(4, 6).equalsIgnoreCase("01")) {
                        prevtilDt = CbsUtil.dateAdd(prevtilDt, 2);
                    }
                    List list2 = em.createNativeQuery("select aa.curbrcode,aa.nature,aa.Closed,bb.Opened,aa.Closed+bb.Opened fortheMonOpened from\n"
                            + "(select a.curbrcode,a.nature,a.closedAcno1,b.closedAcno2,closedAcno1-closedAcno2 Closed from\n"
                            + "(select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo) closedAcno1  from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'SB' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'C'group by  curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) closedAcno1 from td_accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature in('FD','MS') and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'RD' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,b.Accttype nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'D'group by curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'DL' and CrDbFlag = 'D' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'TL' and CrDbFlag = 'D' group by curbrcode "
                            + "union all "
                            + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode)a,\n"
                            + "(select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo) closedAcno2  from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'SB' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'C'group by  curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) closedAcno2 from td_accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature in('FD','MS') and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'RD' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,b.Accttype nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'D'group by curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'DL' and CrDbFlag = 'D' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'TL' and CrDbFlag = 'D' group by curbrcode "
                            + "union all "
                            + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode)b\n"
                            + "where a.curbrcode = b.curbrcode and a.nature =b.nature)aa,\n"
                            + "(select a.curbrcode,a.nature,a.openedCount1,b.openedCount2,a.openedCount1-b.openedCount2 Opened from\n"
                            + "(select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo) openedCount1  from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "') " + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'SB' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) openedCount1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "')" + branchCondition + "  \n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'C'group by  curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) openedCount1 from td_accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "') " + branchCondition + "  \n"
                            + "and a.Accttype= AcctCode and acctnature in('FD','MS') and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) openedCount1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "') " + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'RD' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,b.Accttype nature,count(ACNo) openedCount1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'D'group by curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) openedCount1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'DL' and CrDbFlag = 'D' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) openedCount1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'TL' and CrDbFlag = 'D' group by curbrcode "
                            + "union all "
                            + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) openedCount1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + tillDate + "') " + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode)a,\n"
                            + "(select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo) openedCount2  from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "') " + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'SB' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) openedCount2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "')" + branchCondition + "  \n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'C'group by  curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) openedCount2 from td_accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "') " + branchCondition + "  \n"
                            + "and a.Accttype= AcctCode and acctnature in('FD','MS') and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) openedCount2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "') " + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'RD' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,b.Accttype nature,count(ACNo) openedCount2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'D'group by curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) openedCount2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'DL' and CrDbFlag = 'D' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) openedCount2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'TL' and CrDbFlag = 'D' group by curbrcode "
                            + "union all "
                            + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) openedCount2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ClosingDate is null OR ClosingDate='' OR ClosingDate >'" + prevtilDt + "') " + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode)b\n"
                            + "where a.curbrcode = b.curbrcode and a.nature =b.nature)bb\n"
                            + "where aa.curbrcode = bb.curbrcode and aa.nature =bb.nature").getResultList();

                    for (int i = 0; i < list2.size(); i++) {
                        Vector ele = (Vector) list2.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(common.getAlphacodeByBrncode(ele.get(0).toString()));
                        pojo.setGlName(ele.get(1).toString().toUpperCase());
                        pojo.setExpsBal(new BigDecimal(ele.get(4).toString()));
                        pojo.setTxnDate("For the month AOpened");
                        dataList.add(pojo);
                    }

                    List list3 = em.createNativeQuery("select a.curbrcode,a.nature,a.closedAcno1,b.closedAcno2,closedAcno1-closedAcno2 Closed from\n"
                            + "(select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo) closedAcno1  from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'SB' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'C'group by  curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) closedAcno1 from td_accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature in('FD','MS') and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'RD' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,b.Accttype nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'D'group by curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'DL' and CrDbFlag = 'D' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'TL' and CrDbFlag = 'D' group by curbrcode  "
                            + "union all "
                            + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) closedAcno1 from accountmaster a,accounttypemaster b where OpeningDt <='" + tillDate + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + tillDate + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode)a,\n"
                            + "(select curbrcode,ifnull(b.acctnature,'SB') nature,count(ACNo) closedAcno2  from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'SB' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'CA') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'C'group by  curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'FD') nature,count(ACNo) closedAcno2 from td_accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "') " + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature in('FD','MS') and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'RD') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = 'RD' and CrDbFlag = 'C' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,b.Accttype nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'CA' and CrDbFlag = 'D'group by curbrcode,a.Accttype \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'DL') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'DL' and CrDbFlag = 'D' group by curbrcode \n"
                            + "union all \n"
                            + "select curbrcode,ifnull(b.acctnature,'TL') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + "\n"
                            + "and a.Accttype= AcctCode and acctnature = 'TL' and CrDbFlag = 'D' group by curbrcode "
                            + "union all "
                            + "select curbrcode,ifnull(b.acctnature,'DS') nature,count(ACNo) closedAcno2 from accountmaster a,accounttypemaster b where OpeningDt <='" + prevtilDt + "' \n"
                            + "and (ifnull(closingdate,'')<>'' and closingdate<='" + prevtilDt + "')" + branchCondition + " \n"
                            + "and a.Accttype= AcctCode and acctnature = '" + CbsConstant.DEPOSIT_SC + "' and CrDbFlag = 'C' group by curbrcode)b\n"
                            + "where a.curbrcode = b.curbrcode and a.nature =b.nature having(Closed<>0)").getResultList();

                    for (int i = 0; i < list3.size(); i++) {
                        Vector ele = (Vector) list3.get(i);
                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                        pojo.setBranch(common.getAlphacodeByBrncode(ele.get(0).toString()));
                        pojo.setGlName(ele.get(1).toString().toUpperCase());
                        pojo.setExpsBal(new BigDecimal(ele.get(4).toString()));
                        pojo.setTxnDate("For the month Closed");
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    /*NOTE: VERY INPORTANT
     * If you are doing the changes in getLoanDepositAnnextureDataSummary METHOD, Also mofidy the getLoanDepositAnnextureData()*/
    public List<ExpenditureBalDayWisePojo> getLoanDepositAnnextureDataSummary(String branchCode, String reportType, String tillDate) throws ApplicationException {
        List<ExpenditureBalDayWisePojo> dataList = new ArrayList<>();
        try {
            String branchCondition = "", brnCondition = "";
            if (!branchCode.equalsIgnoreCase("0A")) {
                branchCondition = "and substring(acno,1,2)='" + branchCode + "'";
            }
            if (reportType.equalsIgnoreCase("SO") || reportType.equalsIgnoreCase("SC") || reportType.equalsIgnoreCase("NP")) {
                if (!branchCode.equalsIgnoreCase("0A")) {
                    brnCondition = "and curbrcode = '" + branchCode + "'";
                }
            }
            Map<Integer, String> brMap = new HashMap<>();
            List brList = em.createNativeQuery("select BrnCode,alphacode from branchmaster").getResultList();
            for (int i = 0; i < brList.size(); i++) {
                Vector brVector = (Vector) brList.get(i);
                brMap.put(Integer.parseInt(brVector.get(0).toString()), brVector.get(1).toString());
            }
            String fdMatHead = "", rdMatHead = "";
            List resultList = em.createNativeQuery("select Code from glbmast where Description like '%MATURED FD%'").getResultList();
            Vector eleMat;
            if (!resultList.isEmpty()) {
                eleMat = (Vector) resultList.get(0);
                fdMatHead = eleMat.get(0).toString();
            }
            resultList = em.createNativeQuery("select Code from glbmast where Description like '%MATURED RD%'").getResultList();
            if (!resultList.isEmpty()) {
                eleMat = (Vector) resultList.get(0);
                rdMatHead = eleMat.get(0).toString();
            }
//            Map<String, Integer> map = getAllMonthsn(tillDate);
            List map = getAllMonthsn(tillDate);
//            int v = map.get(CbsUtil.getMonthName(Integer.parseInt(tillDate.substring(4, 6))));
//            List prevList = em.createNativeQuery("select max(MAXDATE) from yearend where yearendflag='Y'").getResultList();
//            Vector yVector = (Vector) prevList.get(0);
            String prvfinYear = map.get(0).toString(); //yVector.get(0).toString();
            // For Sumary Report
            if (reportType.equalsIgnoreCase("D")) {
                ExpenditureBalDayWisePojo pojo;
//                v = map.get(CbsUtil.getMonthName(Integer.parseInt(tillDate.substring(4, 6))));
                for (int i = 0; i < map.size(); i++) {
//                    int month = Integer.parseInt(prvfinYear.substring(4, 6)) + i;
                    String toDate = map.get(i).toString();//ymd.format(CbsUtil.calculateMonthEndDate(month, Integer.parseInt(prvfinYear.substring(0, 4))));
                    if (ymd.parse(toDate).after(ymd.parse(tillDate))) {
                        toDate = tillDate;
                    }
                    double sbCaBal = 0d, fdRdBal = 0d, sbCaFdBal = 0d;
                    List sumlist = em.createNativeQuery("select 'YYYY'branch,'SB' nature,cast(ifnull(sum(cramt-dramt),0)/100000 as decimal(25,2)) balance from recon where dt<='" + toDate + "'\n"
                            + "union all\n"
                            + "SELECT 'YYYY'branch,nature,sum(balance) from\n"
                            + "(select 'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in\n"
                            + "(select acno  from ca_recon where dt <= '" + toDate + "' \n"
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)\n"
                            + "and dt <= '" + toDate + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0))a\n"
                            + "union all\n"
                            + "select 'YYYY'branch,'FD/RD' nature,sum(balance)from(\n"
                            + "select nature,sum(balance) balance from ( \n"
                            + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + toDate + "' \n"
                            + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)\n"
                            + "union all \n"
                            + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon \n"
                            + "where substring(acno,3,8) = '" + fdMatHead + "' and dt <='" + toDate + "' group by substring(acno,1,2) having(balance >0)\n"
                            + ")a\n"
                            + "union all\n"
                            + "select nature,sum(balance) balance from ( \n"
                            + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + toDate + "' group by  substring(acno,1,2) having(balance >0)\n"
                            + "union all \n"
                            + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance \n"
                            + "from gl_recon where substring(acno,3,8) = '" + rdMatHead + "' and dt <='" + toDate + "' group by substring(acno,1,2) having(balance >0)\n"
                            + ")b)aa "
                            + "union all "
                            + "select substring(acno,1,2) branch ,'ds'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from ddstransaction where dt <='" + toDate + "' group by  substring(acno,1,2) having(balance >0)").getResultList();

                    for (int j = 0; j < sumlist.size(); j++) {
                        Vector ele = (Vector) sumlist.get(j);
//                        ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
//                        pojo.setBranch(ele.get(0).toString());
//                        pojo.setGlName(ele.get(1).toString().toUpperCase());
//                        pojo.setExpsBal(new BigDecimal(ele.get(2).toString()));
                        if (ele.get(1).toString().equalsIgnoreCase("sb") || ele.get(1).toString().equalsIgnoreCase("ca")) {
                            sbCaBal = sbCaBal + Double.parseDouble(ele.get(2).toString());
                        } else {
                            fdRdBal = fdRdBal + Double.parseDouble(ele.get(2).toString());
                        }
                        sbCaFdBal = sbCaFdBal + Double.parseDouble(ele.get(2).toString());
//                        pojo.setTxnDate(toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8));
//                        dataList.add(pojo);
                    }
                    pojo = new ExpenditureBalDayWisePojo();
                    pojo.setBranch("ZZZZ");
                    pojo.setGlName("CASA %");
                    pojo.setExpsBal(new BigDecimal((sbCaBal / sbCaFdBal) * 100));
                    pojo.setTxnDate(toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8));
                    dataList.add(pojo);
                }
                pojo = new ExpenditureBalDayWisePojo();
                pojo.setBranch("ZZZZ");
                pojo.setGlName("CASA %");
                pojo.setExpsBal(new BigDecimal("0"));
                pojo.setTxnDate("For the month");
                dataList.add(pojo);
                pojo = new ExpenditureBalDayWisePojo();
                pojo.setBranch("ZZZZ");
                pojo.setGlName("CASA %");
                pojo.setExpsBal(new BigDecimal("0"));
                pojo.setTxnDate("Upto the month");
                dataList.add(pojo);

            } else if (reportType.equalsIgnoreCase("L")) {

                ExpenditureBalDayWisePojo pojo;
                for (int i = 0; i < map.size(); i++) {
                    String toDate = map.get(i).toString();
                    if (ymd.parse(toDate).after(ymd.parse(tillDate))) {
                        toDate = tillDate;
                    }
                    double loanBal = 0d, totalDeposit = 0d;
                    List sumlist = em.createNativeQuery("select 'YYY' branch,AcctType nature,balance from \n"
                            + "(select substring(acno,3,2) acType,'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in \n"
                            + "(select acno  from ca_recon where dt <= '" + toDate + "'\n"
                            + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) \n"
                            + "and dt <= '" + toDate + "'  and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0))a, \n"
                            + "accounttypemaster b where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'D' and acType= AcctCode \n"
                            + "union all \n"
                            + "select 'YYY' branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance \n"
                            + "from loan_recon r,accounttypemaster a where dt <='" + toDate + "'\n"
                            + "and substring(acno,3,2)= AcctCode and acctnature = '" + CbsConstant.DEMAND_LOAN + "' and CrDbFlag = 'D' group by AcctCode having(balance <>0) \n"
                            + "union all \n"
                            + "select 'YYY' branch ,acctnature nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance \n"
                            + "from loan_recon r,accounttypemaster a where dt <='" + toDate + "'\n"
                            + "and substring(acno,3,2)= AcctCode and acctnature= '" + CbsConstant.TERM_LOAN + "' and CrDbFlag = 'D' group by AcctCode having(balance <>0) ").getResultList();

                    for (int j = 0; j < sumlist.size(); j++) {
                        Vector ele = (Vector) sumlist.get(j);
                        loanBal = loanBal + Double.parseDouble(ele.get(2).toString());
                    }

                    pojo = new ExpenditureBalDayWisePojo();
                    pojo.setBranch("ZZZZ");
                    pojo.setGlName("CD Ratio%");
                    totalDeposit = getTotalDepositbyDt(toDate, fdMatHead, rdMatHead);
                    pojo.setExpsBal(new BigDecimal((loanBal / totalDeposit) * 100));
                    pojo.setTxnDate(toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8));
                    dataList.add(pojo);
                }
                pojo = new ExpenditureBalDayWisePojo();
                pojo.setBranch("ZZZZ");
                pojo.setGlName("CD Ratio%");
                pojo.setExpsBal(new BigDecimal("0"));
                pojo.setTxnDate("For the month");
                dataList.add(pojo);
                pojo = new ExpenditureBalDayWisePojo();
                pojo.setBranch("ZZZZ");
                pojo.setGlName("CD Ratio%");
                pojo.setExpsBal(new BigDecimal("0"));
                pojo.setTxnDate("Upto the month");
                dataList.add(pojo);
            }
            // End For Sumary Report
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public double getTotalDepositbyDt(String toDate, String fdGlHesd, String rdGlHead) throws ApplicationException {
        double totalDeposit = 0d;
        try {

            List totalDepositList = em.createNativeQuery("select sum(balance) from (\n"
                    + "select 'ZZZ'branch,'SB' nature,cast(ifnull(sum(cramt-dramt),0)/100000 as decimal(25,2)) balance from recon where dt<='" + toDate + "' "
                    + "union all "
                    + "select 'ZZZ'branch,'DDS' nature,cast(ifnull(sum(cramt-dramt),0)/100000 as decimal(25,2)) balance from ddstransaction where dt<='" + toDate + "'\n"
                    + "union all\n"
                    + "SELECT 'ZZZ'branch,nature,sum(balance) from\n"
                    + "(select 'ca'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from ca_recon where acno in\n"
                    + "(select acno  from ca_recon where dt <= '" + toDate + "' \n"
                    + "group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)\n"
                    + "and dt <= '" + toDate + "'   and auth ='Y' group by substring(acno,1,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0))a\n"
                    + "union all\n"
                    + "select 'ZZZ'branch,'FD/RD' nature,sum(balance)from(\n"
                    + "select nature,sum(balance) balance from ( \n"
                    + "select substring(acno,1,2) branch ,'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from td_recon where dt <='" + toDate + "' \n"
                    + "and closeflag is null and Trantype<> 27 group by  substring(acno,1,2) having(balance >0)\n"
                    + "union all \n"
                    + "select substring(acno,1,2) branch, 'fd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance from gl_recon \n"
                    + "where substring(acno,3,8) = '" + fdGlHesd + "' and dt <='" + toDate + "' group by substring(acno,1,2) having(balance >0)\n"
                    + ")a\n"
                    + "union all\n"
                    + "select nature,sum(balance) balance from ( \n"
                    + "select substring(acno,1,2) branch ,'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance  from rdrecon where dt <='" + toDate + "' group by  substring(acno,1,2) having(balance >0)\n"
                    + "union all \n"
                    + "select substring(acno,1,2) branch, 'rd'nature,cast(sum(cramt-dramt)/100000 as decimal(25,2)) balance \n"
                    + "from gl_recon where substring(acno,3,8) = '" + rdGlHead + "' and dt <='" + toDate + "' group by substring(acno,1,2) having(balance >0)\n"
                    + ")b)aa\n"
                    + ")aaa").getResultList();
            Vector vtr = (Vector) totalDepositList.get(0);
            totalDeposit = Double.parseDouble(vtr.get(0).toString());
            return totalDeposit;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllMonthsn(String reportDt) {
        //Map<String, Integer> map = new TreeMap<String, Integer>();
//        List map = new ArrayList();
        List<String> mEndDateList = new ArrayList<String>();
        try {
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo) rbiReportRemote.getYearEndDataAccordingToDate("90", reportDt);
            String finStartDt = yDate.getMinDate();
            String finEndDt = yDate.getMaxDate();



            String mEndDate = "";
            String preFinYearEndDt = CbsUtil.dateAdd(finStartDt, -1);
            mEndDateList.add(preFinYearEndDt);
            mEndDate = ymd.format(ymd.parse(reportDt));;

            mEndDateList.add(mEndDate);
            for (int i = 0; i < CbsUtil.monthDiff(ymd.parse(finStartDt), CbsUtil.getLastDateOfMonth(ymd.parse(reportDt))); i++) {
                mEndDate = CbsUtil.monthAdd(mEndDate, -1);
                mEndDate = ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(mEndDate)));
                mEndDateList.add(mEndDate);
            }
            Collections.sort(mEndDateList);
//            int i = 0;
//            for (String strDt : mEndDateList) {
//                map.put(strDt, i++);
//                System.out.println("Now Date is::" + strDt);
//            }


//        return mEndDateList;
//        map.put("April", 1);
//        map.put("May", 2);
//        map.put("June", 3);
//        map.put("July", 4);
//        map.put("August", 5);
//        map.put("September", 6);
//        map.put("October", 7);
//        map.put("November", 8);
//        map.put("December", 9);
//        map.put("January", 10);
//        map.put("February", 11);
//        map.put("March", 12);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mEndDateList;
    }
}