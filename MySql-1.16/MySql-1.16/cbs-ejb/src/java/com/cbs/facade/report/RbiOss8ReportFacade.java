/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.ho.share.DividendCalculationFacadeRemote;
import com.cbs.dto.DividendTable;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiOss8ReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class RbiOss8ReportFacade implements RbiOss8ReportFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @EJB
    CommonReportMethodsRemote common;
    @EJB
    HoReportFacadeRemote hoReport;
    @EJB
    private RbiReportFacadeRemote rbiReportRemote;
    @EJB
    RbiQuarterlyReportFacadeRemote quarterlyRemote;
    @EJB
    NpaReportFacadeRemote npaRemote;
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
    
    public List<RbiSossPojo> getOss8Detail(String repName, List<String> dates, String orgnCode, BigDecimal repOpt, int noOfEmp, double dividend, String summaryOpt) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List osBlancelistIncomeExp = null, osBlancelistIncomeExp1;
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
            double shareValue = 0;
            if (repName.equalsIgnoreCase("OSS8")) {
                for (int k = 0; k < dates.size(); k++) {
                    osBlancelistIncomeExp1 = monthlyRemote.getAsOnDateBalanceBetweenDateList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, rbiReportRemote.getMinFinYear(dates.get(k)), dates.get(k));
                    if (k == 0) {
                        osBlancelistIncomeExp = osBlancelistIncomeExp1;
                    } else {
                        osBlancelistIncomeExp.addAll(osBlancelistIncomeExp1);
                    }
                    //                System.out.println(k+":List1:"+osBlancelistIncomeExp1.size()+"; Final List2:"+osBlancelistIncomeExp.size());
                }
            }


            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT order by "
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
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                    if (fSGNo.equalsIgnoreCase("NOE")) {

                        bal = new BigDecimal(noOfEmp);
                        rbiSossPojo.setAmt(bal);
                    } else if (fSGNo.equalsIgnoreCase("SV")) {
                        shareValue = certRemote.getperShareValue();
                        bal = new BigDecimal(shareValue);
                        rbiSossPojo.setAmt(bal.divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    } else if (fSGNo.equalsIgnoreCase("DVDEC")) {
                        for (int m = 0; m < dates.size(); m++) {
                            String dt = dates.get(m);
                            List<DividendTable> divList = divRemote.calculateDividend("All", shareValue, dividend, Integer.parseInt(quarterlyRemote.getFinYear(dt)), ymd.parse(dt), "System", orgnCode, "0");
                            for (DividendTable divObj : divList) {
                                bal = bal.add(new BigDecimal(divObj.getDivamt()));
                            }
                            //                        bal= new BigDecimal(dividend);                            
                        }
                        rbiSossPojo.setAmt(bal);
                    } else if (fSGNo.equalsIgnoreCase("DV")) {

                        //                    List<DividendTable> divList = divRemote.calculateDividend("All", shareValue, dividend, Integer.parseInt(getFinYear(repDt)), ymd.parse(repDt), "System", brCode);
                        //
                        //                    for (DividendTable divObj : divList) {
                        //                        bal = bal.add(new BigDecimal(divObj.getDivamt()));
                        //                    }
                        bal = new BigDecimal(dividend);
                        rbiSossPojo.setAmt(bal);
                    } else if (fSGNo.equalsIgnoreCase("NDTL")) {
                        for (int m = 0; m < dates.size(); m++) {
                            String dt = dates.get(m);
                            bal = hoReport.getNewNdtl(orgnCode, dt);
                        }
                        rbiSossPojo.setAmt(bal.divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    } else if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
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
//                                    params.setDate(dt);
//                                    params.setToDate(dt);
                                    if (fSGNo.equalsIgnoreCase("AFB")) {
                                        String frDt = quarterlyRemote.getMinFinYear(dt);
                                        params.setDate(frDt);
                                        params.setToDate(dt);
                                        bal = quarterlyRemote.getFortnightlyAvgBal(params).abs();
                                        rbiSossPojo.setAmt(bal.divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                                    } else if (fSGNo.equalsIgnoreCase("S") || fSGNo.equalsIgnoreCase("SS") || fSGNo.equalsIgnoreCase("AC") || fSGNo.equalsIgnoreCase("NET")) {
                                        params.setAcType(vector.get(0).toString());
                                        params.setBrnCode(orgnCode);
                                        params.setClassification(classification);
                                        params.setDate(dt);
                                        params.setToDate(dt);
                                        params.setFromRange(rangeFrom);
                                        params.setGlFromHead("");
                                        params.setGlToHead("");
                                        params.setNature("");
                                        params.setOrgBrCode(orgnCode);
                                        params.setRangeBasedOn(rangeBaseOn);
                                        params.setToRange(rangeTo);
                                        params.setFlag(fSGNo);
                                        bal = quarterlyRemote.getSectorWiseBal(params, fGNo, fSGNo).abs();
                                        rbiSossPojo.setAmt(bal.divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                                    } else {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
//                                        System.out.println("acType:"+vector.get(0).toString()+"; Name:"+vector.get(0).toString()+"; classification:"+classification+"; Date:"+dt+"; Bal:"+newBalPojo.getBalance());
                                        if (newBalPojo == null) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                        } else {
                                            if (classification.equalsIgnoreCase("A") || classification.equalsIgnoreCase("E") && newBalPojo.getBalance().doubleValue() <= 0) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP).abs());
                                            } else {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
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
//                                preZ = z;
                            }
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = rbiReportRemote.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = rbiReportRemote.getNpaAcctsAndBal(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP).doubleValue()));
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
                    } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8),acname").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            params = new AdditionalStmtPojo();
                            params.setAcType(acType);
                            params.setClassification(classification);
                            params.setFromRange(rangeFrom);
                            params.setGlFromHead(vector.get(0).toString());
                            params.setGlToHead(vector.get(0).toString());
                            params.setNature(acNature);
                            params.setRangeBasedOn(rangeBaseOn);
                            params.setToRange(rangeTo);
                            params.setOrgBrCode(orgnCode);
                            params.setBrnCode(orgnCode);

                            rbiSossPojo = new RbiSossPojo();
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                if (fSGNo.equalsIgnoreCase("AFB")) {
                                    String frDt = quarterlyRemote.getMinFinYear(dt);
                                    params.setDate(frDt);
                                    params.setToDate(dt);
                                    bal = quarterlyRemote.getFortnightlyAvgBal(params).abs();
                                    rbiSossPojo.setAmt(bal.divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                                } else {
                                    if (params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                        params.setDate(dt);
                                        params.setToDate(dt);
                                        bal = quarterlyRemote.getOsBal(params).abs();
                                        rbiSossPojo.setAmt(bal.divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                                    } else {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(((classification.equalsIgnoreCase("L") || classification.equalsIgnoreCase("A")) ? osBlancelist : osBlancelistIncomeExp), vector.get(0).toString(), classification, dt);
                                        if (newBalPojo == null) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                        } else {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
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
                List<RbiSossPojo> oss7PartAList = new ArrayList<>();
                List<RbiSossPojo> SOSS2 = new ArrayList<>();
                List formulaList = em.createNativeQuery("select distinct formula from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT order by "
                        + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                        + "cast(ssss_gno as unsigned), sno").getResultList();
                if (!formulaList.isEmpty()) {
                    for (int f = 0; f < formulaList.size(); f++) {
                        Vector formulaVect = (Vector) formulaList.get(f);
                        if (formulaVect.get(0).toString().contains("XBRLOSS7")) {
                            oss7PartAList = quarterlyRemote.getOss7PartA("XBRLOSS7-PART-A", dates.get(0), orgnCode, repOpt, "Y", osBlancelist, "Y");
                            break;
                        } else if (formulaVect.get(0).toString().contains("OSS7")) {
                            oss7PartAList = quarterlyRemote.getOss7PartA("XBRLOSS7-PART-A", dates.get(0), orgnCode, repOpt, "Y", osBlancelist, "Y");
                            break;
                        }
                    }
                }
                if (!formulaList.isEmpty()) {
                    for (int f = 0; f < formulaList.size(); f++) {
                        Vector formulaVect = (Vector) formulaList.get(f);
                        if (formulaVect.get(0).toString().contains("XBRLOSS2")) {
                            SOSS2 = rbiSoss1Remote.getSOSS2("XBRLOSS2", dates.get(0), orgnCode, repOpt, "Y", osBlancelist, "0");
                            break;
                        } else if (formulaVect.get(0).toString().contains("OSS2")) {
                            SOSS2 = rbiSoss1Remote.getSOSS2("SOSS2", dates.get(0), orgnCode, repOpt, "Y", osBlancelist, "0");
                            break;
                        }
                    }
                }
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        if (rbiSossPojo.getFormula().contains("OSS7")) {
                            String[] strArr = rbiSossPojo.getFormula().split("#");
                            BigDecimal bal = rbiReportRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                            rbiSossPojo.setAmt(bal);
                            rbiSossPojo.setDescription(rbiSossPojo.getDescription().concat(" (").concat(rbiSossPojo.getFormula()).concat(")"));
                        } else if (rbiSossPojo.getFormula().contains("SOSS2")) {
                            String[] strArr = rbiSossPojo.getFormula().split("#");
                            BigDecimal bal = rbiReportRemote.getValueFromFormula(SOSS2, strArr[1]);
                            rbiSossPojo.setAmt(bal);
                            rbiSossPojo.setDescription(rbiSossPojo.getDescription().concat(" (").concat(rbiSossPojo.getFormula()).concat(")"));
                        } else {
                            BigDecimal bal = rbiReportRemote.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula());
                            rbiSossPojo.setAmt(bal);
                        }
                    }
                }
//                for (int k = 0; k < rbiPojoTable.size(); k++) {
//                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
//                    if (!rbiSossPojo.getFormula().isEmpty()) {                        
//                        BigDecimal bal = new BigDecimal("0.0");
//                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
//                            for (int m = 0; m < dates.size(); m++) {
//                                String dt = dates.get(m);
//                                double balPL = glReport.IncomeExp(dt, "0A", "0A");
//                                if ((rbiSossPojo.getClassification().equalsIgnoreCase("L")||rbiSossPojo.getClassification().equalsIgnoreCase("I")) && balPL >= 0) {
////                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt,2, BigDecimal.ROUND_HALF_UP).doubleValue())));
//                                    if(m==0){
//                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt,2, BigDecimal.ROUND_HALF_UP).doubleValue())));
//                                    } else {
//                                        rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt,2, BigDecimal.ROUND_HALF_UP).doubleValue())));
//                                    }
//                                } else if ((rbiSossPojo.getClassification().equalsIgnoreCase("A")||rbiSossPojo.getClassification().equalsIgnoreCase("E")) && balPL < 0) {
//                                    //rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt,2, BigDecimal.ROUND_HALF_UP).doubleValue())).abs());
//                                    if(m==0){
//                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt,2, BigDecimal.ROUND_HALF_UP).doubleValue())).abs());
//                                    } else {
//                                        rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt,2, BigDecimal.ROUND_HALF_UP).doubleValue())).abs());
//                                    }
//                                } else {
//                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
//                                }
//                            }                           
//                        } else {
//                            BigDecimal[] arr = monthlyRemote.getValueFromFormulaForFormOne(rbiPojoTable, rbiSossPojo.getFormula(), dates);
//                            rbiSossPojo.setAmt(arr[0]);
//                            rbiSossPojo.setSecondAmount(arr[1]);
//                            rbiSossPojo.setThirdAmount(arr[2]);
//                            rbiSossPojo.setFourthAmount(arr[3]);
//                            rbiSossPojo.setFifthAmount(arr[4]);
//                            rbiSossPojo.setSixthAmount(arr[5]);
//                            rbiSossPojo.setSeventhAmount(arr[6]);
////                            bal = bal.add(rbiReportRemote.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula()));
//                            rbiSossPojo.setDescription(rbiSossPojo.getDescription().concat( rbiSossPojo.getClassification().equalsIgnoreCase("L") ?"(ASSET SIDE)":"(LIABILITY SIDE)"));
////                            rbiSossPojo.setAmt(bal);
//                        }
//                    }                    
//                }
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
    
}
