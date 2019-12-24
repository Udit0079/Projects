/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.RbiSoss1AnnexI2;
import com.cbs.dto.report.RbiSoss1AnnexI3And4;
import com.cbs.dto.report.RbiSoss1AnnexI5;
import com.cbs.dto.report.RbiSoss1AnnexII;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiSoss1And2ReportFacade")
public class RbiSoss1And2ReportFacade implements RbiSoss1And2ReportFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalcFacade;    
    @EJB
    RbiReportPartDFacadeRemote rbiReportPartD;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    @EJB
    private RbiReportFacadeRemote RbiReportFacade;
    @EJB
    private GLReportFacadeRemote glReport;
    @EJB
    private NpaReportFacadeRemote npaRemote;
    @EJB
    private HoReportFacadeRemote hoRemote;
    @EJB
    RbiQuarterlyReportFacadeRemote rbiQuaterly;
    @EJB
    OverDueReportFacadeRemote overDueRemote;
    @PersistenceContext
    private EntityManager em;
    NumberFormat formatter = new DecimalFormat("#.####");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private BigDecimal reptOpt;

    public List<RbiSossPojo> getSOSS1(String reptName, String dt, String orgnCode, BigDecimal repOpt, String summaryOpt) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' and '" + dt + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                String preFormula = "", npaAcDetails = "";
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
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
                    Integer cl;
                    if (classification.equalsIgnoreCase("L")) {
                        cl = 1;
                    } else if (classification.equalsIgnoreCase("A")) {
                        cl = -1;
                    }
                    /*Addition ath the first time*/
//                    if (acType == null || acType.equals("")) {
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setAmt(new BigDecimal("0.00"));
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
                    if (reptName.equalsIgnoreCase("SOSS2")) {
                        params.setDate(RbiReportFacade.getMinFinYear(dt));
                    } else {
                        params.setDate(dt);
                    }
                    params.setToDate(dt);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
                    params.setNpaAcList(resultList);
                    Long noOfAc = 0l;
                    BigDecimal bal = new BigDecimal("0");
                    AcctBalPojo acctBal = new AcctBalPojo();
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

                                GlHeadPojo glPojo = new GlHeadPojo();
                                glPojo.setGlHead(vector.get(0).toString());
                                glPojo.setGlName(vector.get(1).toString());
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = RbiReportFacade.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                    glPojo.setBalance(new BigDecimal(noOfAc.toString()));
                                } else {
                                    acctBal = RbiReportFacade.getAcctsAndBal(params);
                                    glPojo.setBalance(acctBal.getBalance());
                                }
                                glHeadList.add(glPojo);
                            }
                            /*Setting into main list*/
                            for (int z = 0; z < glHeadList.size(); z++) {
                                rbiSossPojo = new RbiSossPojo();
                                GlHeadPojo glHeadPo = glHeadList.get(z);
                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                    noOfAc = (long) glPojoList.size();
                                    rbiSossPojo.setAmt(glHeadPo.getBalance());
                                } else {
                                    bal = new BigDecimal(formatter.format(glHeadPo.getBalance().divide(repOpt).doubleValue()));
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo.setAmt(bal);
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
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
                                if (summaryOpt.equalsIgnoreCase("N")) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));

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
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));

                                        if (sGNo == 0) {
                                            rbiSossPojo.setsGNo(z + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(sGNo);
                                            if (ssGNo == 0 && sGNo != 0) {
                                                rbiSossPojo.setSsGNo(z + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(ssGNo);
                                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSssGNo(z + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(sssGNo);
                                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(z + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = z + 1;
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

                            /**
                             * **************************
                             */
//                            acctBal = getAcctsAndBal(params);
//                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                noOfAc = acctBal.getNumberOFAcct();
//                            } else {
//                                bal = acctBal.getBalance();
//                            }
                            /**
                             * ********************
                             */
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = RbiReportFacade.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                if (reptName.equalsIgnoreCase("MON_AGG") || reptName.equalsIgnoreCase("XBRLFORM9")) {
                                    bal = new BigDecimal(formatter.format(monthlyRemote.getMonetaryAggregatesFdValue(acNature, acType, dt, rangeBaseOn, rangeFrom, rangeTo).divide(repOpt).doubleValue()));
                                } else {
                                    acctBal = RbiReportFacade.getAcctsAndBalTimeRangeWise(params);
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = acctBal.getNumberOFAcct();
                                    } else {
                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                    }
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = RbiReportFacade.getNpaAcctsAndBal(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            }

                            rbiSossPojo = new RbiSossPojo();
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                //noOfAc = (long) glPojoList.size();
                                rbiSossPojo.setAmt(new BigDecimal(noOfAc.toString()));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo.setAmt(bal);
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
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
                        GlHeadPojo glHeadPojo = new GlHeadPojo();
                        glPojoList = RbiReportFacade.getGLHeadAndBal(params);
                        if (glPojoList.size() > 0) {
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
//                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
//                            } else {
//                                rbiSossPojo.setAmt(new BigDecimal(formatter.format(((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs()).divide(repOpt).doubleValue())));
//                            }
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
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setfGNo(fGNo);
                            rbiSossPojo.setfSGNo(fSGNo);
                            rbiSossPojo.setfSsGNo(fSsGNo);
                            rbiSossPojo.setfSssGNo(fSssGNo);
                            rbiSossPojo.setfSsssGNo(fSsssGNo);
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
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);

//                                rbiPojoTable.add(rbiSossPojo);
                            }

                            /* If GL Head Series have multiple GL Head */
                            for (int j = 0; j < glPojoList.size(); j++) {
                                rbiSossPojo = new RbiSossPojo();
                                GlHeadPojo glHeadPo = glPojoList.get(j);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = (long) glPojoList.size();
                                    rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
                                } else {
                                    bal = new BigDecimal(formatter.format(glHeadPo.getBalance().divide(repOpt).doubleValue()));
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo.setAmt(bal);
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
                                    } else {
                                        rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : bal.abs());
                                    }
//                                    rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs());
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
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));

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
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(j + 1)).concat(". ").concat(glHeadPo.getGlName()));

                                        if (sGNo == 0) {
                                            rbiSossPojo.setsGNo(j + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(sGNo);
                                            if (ssGNo == 0 && sGNo != 0) {
                                                rbiSossPojo.setSsGNo(j + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(ssGNo);
                                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSssGNo(j + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(sssGNo);
                                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(j + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(j + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(j + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = j + 1;
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
                        }
                    } else if (fSssGNo.equalsIgnoreCase("EMP")) {
                        /* For Number Of Employees To Be printed in SOSS2 */
                        rbiSossPojo = new RbiSossPojo();
                        int l = 0;
                        List list = em.createNativeQuery("select sum(ifnull(code,0)) as code from parameterinfo_report where "
                                + "reportname in ('BANK-OTHER-STAFF-NO','BANK-OFFICER-NO')").getResultList();
                        for (l = 0; l < list.size(); l++) {
                            Vector listVect = (Vector) list.get(l);
                            rbiSossPojo.setAmt(new BigDecimal(listVect.get(0).toString()));
                        }
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setDescription(description);
                        rbiPojoTable.add(rbiSossPojo);
                    }
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo;
                    preFormula = formula;
                }
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal bal = new BigDecimal("0.0");
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                            double balPL = glReport.IncomeExp(dt, "0A", "0A");
                            if (rbiSossPojo.getClassification().equalsIgnoreCase("L") && balPL >= 0) {
                                rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                            } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL < 0) {
                                if (reptName.contains("OSS1") && rbiSossPojo.getgNo() == 1) {
                                    rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                } else {
                                    rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                }
                            }

                        } else {
                            bal = bal.add(RbiReportFacade.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula()));
                            rbiSossPojo.setAmt(bal);
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

    public List<RbiSossPojo> getSOSS2(String reptName, String dt, String orgnCode, BigDecimal repOpt, String summaryOpt, List osBlancelist,String noOfEmp) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List<String> dates = new ArrayList<String>();
            dates.add(dt);
////            if (reptName.equalsIgnoreCase("SOSS2") || reptName.equalsIgnoreCase("XBRLOSS2")) {
////                osBlancelist = monthlyRemote.getAsOnDateBalanceBetweenDateList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, getMinFinYear(dt), dt);
////            } else {
////                osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
////            }
            // OverDue Figures Addition in the FORM 9 and XBRL FORM 9 report.
            BigDecimal overDueAmt = new BigDecimal("0"), totalLoan = new BigDecimal("0"),priorAmt = new BigDecimal("0");
            double overDuePer = 0d,priorPerc = 0d;
            if (reptName.equalsIgnoreCase("FORM9") || reptName.equalsIgnoreCase("XBRLFORM9")) {
                List<LoanMisCellaniousPojo> totalLoanList = new ArrayList<LoanMisCellaniousPojo>();
                totalLoanList = loanReportFacade.cbsLoanMisReport(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "0", "0",dt, "A", 0.0, 99999999999999.99, "O",
                        "0" , "0" , "0",  "0" ,"0" , "0", "0", "0","0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"
                        , "0", "0", "Active", "0", "N", "S", "0", "Y","0","N","N", "0", "0", "0", "0");
                /*This includes all the details related to overdue and Total Loans*/
                for (int t = 0; t< totalLoanList.size() ; t++) {
                    if (totalLoanList.get(t).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        totalLoan = totalLoan.add(totalLoanList.get(t).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : totalLoanList.get(t).getOutstanding());
                    } else {
                        totalLoan = totalLoan.add(totalLoanList.get(t).getOutstanding());
                    }
                    overDueAmt = overDueAmt.add(totalLoanList.get(t).getOverdueAmt());
                    if (totalLoanList.get(t).getSector().equalsIgnoreCase("PRIOR")) {
                        if (totalLoanList.get(t).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            priorAmt = priorAmt.add(totalLoanList.get(t).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : totalLoanList.get(t).getOutstanding());
                        } else {
                            priorAmt = priorAmt.add(totalLoanList.get(t).getOutstanding());
                        }
                    }
                }
                overDuePer = (overDueAmt.doubleValue()/totalLoan.doubleValue())*100;
                priorPerc = (priorAmt.doubleValue()/totalLoan.doubleValue())*100;
            }

            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index,refer_content  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            List<RbiSossPojo> oss7PartAList = new ArrayList<RbiSossPojo>();
            if (reptName.equalsIgnoreCase("DIR_REL_PART_B")||reptName.equalsIgnoreCase("OSS4-PART-F")) {
                List chk1 = em.createNativeQuery("SELECT  distinct(FORMULA) FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reptName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                        + "REPORT_NAME,CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                        + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
                if (!chk1.isEmpty()) {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector vect = (Vector) chk1.get(i);
                        String chk = vect.get(0).toString();
                        if (chk.contains("XBRLOSS7")) {
                            oss7PartAList = rbiQuaterly.getOss7PartA("XBRLOSS7-PART-A", dates.get(0), orgnCode, new BigDecimal("1"), "Y", osBlancelist,"Y");
                            break;
                        } else if (chk.contains("OSS7")) {
                            oss7PartAList = rbiQuaterly.getOss7PartA("OSS7-PART-A", dates.get(0), orgnCode, new BigDecimal("1"), "Y", osBlancelist,"Y");
                            break;
                        }
                    }
                }
            }
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                String preFormula = "", npaAcDetails = "";                
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");                
                for (int i = 0; i < oss1Query.size(); i++) {
//                    System.out.println("i:"+i+"; Time:"+new Date());
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
                    String referIndex = oss1Vect.get(24).toString();
                    String referContent = oss1Vect.get(25).toString();
                    Integer cl;
                    if (classification.equalsIgnoreCase("L")) {
                        cl = 1;
                    } else if (classification.equalsIgnoreCase("A")) {
                        cl = -1;
                    }
                    /*Addition ath the first time*/
//                    if (acType == null || acType.equals("")) {
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setAmt(new BigDecimal("0.00"));
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
                    rbiSossPojo.setRefIndex(referIndex);
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
                    if (reptName.equalsIgnoreCase("SOSS2")) {
                        params.setDate(RbiReportFacade.getMinFinYear(dt));
                    } else {
                        params.setDate(dt);
                    }
                    params.setToDate(dt);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
                    params.setNpaAcList(resultList);
                    params.setRefer_index(referIndex);
                    params.setRefer_content(referContent);
                    Long noOfAc = 0l;
                    BigDecimal bal = new BigDecimal("0");
                    AcctBalPojo acctBal = new AcctBalPojo();
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();

                    if ((!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) && ((rangeFrom.equalsIgnoreCase("0.00") || rangeFrom.equalsIgnoreCase("")) && (rangeTo.equalsIgnoreCase("0.00") || rangeTo.equalsIgnoreCase("")))) {
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
                            if (acNature.equalsIgnoreCase("NPA") || acType.equalsIgnoreCase("NPA")) {
                                natureList = em.createNativeQuery(" select ifnull(ACCODE,''),ifnull(ACCODE,'') from ho_crr_asset_liab where ACCODE = 'NPA' and date ='" + dt + "'").getResultList();
                            }

//                            for (int n = 0; n < natureList.size(); n++) {
//                                Vector vector = (Vector) natureList.get(n);
//                                params.setNature("");
//                                params.setAcType(vector.get(0).toString());
//
//                                GlHeadPojo glPojo = new GlHeadPojo();
//                                glPojo.setGlHead(vector.get(0).toString());
//                                glPojo.setGlName(vector.get(1).toString());
//                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                    noOfAc = getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
//                                    glPojo.setBalance(new BigDecimal(noOfAc.toString()));
//                                } else {
//                                    acctBal = getAcctsAndBal(params);
//                                    glPojo.setBalance(acctBal.getBalance());
//                                }
//                                glHeadList.add(glPojo);
//                            }
                            /*Setting into main list*/
                            for (int z = 0; z < natureList.size(); z++) {
                                Vector vector = (Vector) natureList.get(z);
                                rbiSossPojo = new RbiSossPojo();
//                                GlHeadPojo glHeadPo = glHeadList.get(z);
                                if (countApplicable.equalsIgnoreCase("Y") && referIndex.equalsIgnoreCase("")) {
//                                    noOfAc = (long) glPojoList.size();
                                    params.setNature("");
                                    params.setAcType(vector.get(0).toString());
                                    noOfAc = RbiReportFacade.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(noOfAc.toString()));
                                } else if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                    List<String> monthEndList = new ArrayList();
                                    String monthEnd = dt;
                                    if (fSsGNo.equalsIgnoreCase("GLB")) {
                                        monthEndList.add(dt);
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") && !referContent.equalsIgnoreCase("AVG")) {
                                        monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(RbiReportFacade.getMinFinYear(dt), dt);
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") && referContent.equalsIgnoreCase("AVG")) {
                                        Integer month = Integer.parseInt(referIndex);
                                        monthEnd = CbsUtil.dateAdd(CbsUtil.monthAdd(RbiReportFacade.getMinFinYear(dt), month), -1);
                                        if (!ymd.parse(monthEnd).after(ymd.parse(dt))) {
                                            monthEndList.add(monthEnd);
                                        } else if (ymd.parse(monthEnd).after(ymd.parse(dt))) {
                                            monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(RbiReportFacade.getMinFinYear(dt), dt);
                                        }
                                    }

                                    List balList = monthlyRemote.getInsertedGlbList(monthEndList);
                                    BigDecimal avgBal = new BigDecimal(0);
                                    for (int v = 0; v < monthEndList.size(); v++) {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(balList, vector.get(0).toString(), classification, monthEndList.get(v));
                                        if (newBalPojo == null) {
                                            avgBal = avgBal.add(new BigDecimal(0));
                                        } else {
                                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                                avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                                avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                            } else {
                                                avgBal = avgBal.add(fSGNo.equalsIgnoreCase("ACT") ? newBalPojo.getBalance().divide(repOpt) : newBalPojo.getBalance().abs().divide(repOpt));
                                            }    //                                        
                                        }
                                    }
                                    if (referContent.equalsIgnoreCase("AVG") && (!ymd.parse(monthEnd).after(ymd.parse(dt)))) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(referIndex), 2, RoundingMode.HALF_UP));
                                    } else if (referContent.equalsIgnoreCase("AVG") && (ymd.parse(monthEnd).after(ymd.parse(dt)))) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0));;
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
                                    }
                                } else if (npaClassification.equalsIgnoreCase("SOC") || npaClassification.equalsIgnoreCase("IND")) {
                                    // For Individuals and Society Balance in Liability Side
                                    String tableName = "";
                                    String acctCatQuery = "";
                                    BigDecimal amtAsPerClass = new BigDecimal("0");
                                    List codeQuery = em.createNativeQuery("select ifnull(code,'COS') from cbs_parameterinfo where name ='SOCIETY_IN'").getResultList();
                                    String code = "";
                                    if (!codeQuery.isEmpty()) {
                                        Vector codeVect = (Vector) codeQuery.get(0);
                                        code = codeVect.get(0).toString();
                                    } else {
                                        code = "'COS','RS'";//For Co-Operative Societies Only
                                    }
                                    if (npaClassification.equalsIgnoreCase("IND")) {
                                        acctCatQuery = "and b.acctCategory not in (" + code + ")";
                                    } else if (npaClassification.equalsIgnoreCase("SOC")) {
                                        acctCatQuery = "and b.acctCategory in (" + code + ")";
                                    }
                                    if (!acNature.equalsIgnoreCase("")) {
                                        tableName = common.getTableName(acNature);
                                    } else if (!acType.equalsIgnoreCase("")) {
                                        List acnature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
                                        if (!acnature.isEmpty()) {
                                            Vector vect = (Vector) acnature.get(0);
                                            acNature = vect.get(0).toString();
                                            tableName = common.getTableName(acNature);
                                        }
                                    }
                                    String cls = "";
                                    if (params.getClassification().equalsIgnoreCase("L")) {
                                        cls = "1";
                                    } else if (params.getClassification().equalsIgnoreCase("A")) {
                                        cls = "-1";
                                    }
                                    if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                        List dataList = em.createNativeQuery("select count(distinct a.acno), ifnull(sum(a.amt),0) from ( "
                                                + " select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " a,accountmaster b "
                                                + " where  a.acno in (select acno from " + tableName + " WHERE DT <= '" + dt + "' group by acno   having sign(sum(cramt-dramt)) = " + cls + " )  "
                                                + " and (b.closingdate is null or b.closingdate = '' or b.closingdate > '" + dt + "' )   "
                                                + " and a.acno = b.acno and a.dt <='" + dt + "' " + acctCatQuery + " "
                                                + " and b.accttype in (select acctcode from accounttypemaster where acctcode = '" + vector.get(0).toString() + "') group by b.acno  ) a").getResultList();
                                        if (!dataList.isEmpty()) {
                                            Vector vect = (Vector) dataList.get(0);
                                            amtAsPerClass = new BigDecimal(vect.get(1).toString());
                                        }
                                    } else {
                                        List dataList = em.createNativeQuery("select substring(a.acno,3,2) as acno, cast(ifnull(sum(a.cramt-a.dramt),0)as decimal(25,2)) as amt from " + tableName + " a ,td_accountmaster b where a.dt <='" + dt + "'  and a.auth ='Y' and a.closeflag is null  and a.acno =b.acno  and substring(a.acno,3,2) = " + vector.get(0).toString() + " " + acctCatQuery + " group by substring(a.acno,3,2) ;").getResultList();
                                        if (!dataList.isEmpty()) {
                                            Vector vect = (Vector) dataList.get(0);
                                            amtAsPerClass = new BigDecimal(vect.get(1).toString());
                                        }
                                    }
                                    if ((amtAsPerClass.compareTo(new BigDecimal("0"))) == 0) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, amtAsPerClass);
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, amtAsPerClass.divide(repOpt));
                                    }
                                } else if (referIndex.equalsIgnoreCase("NRO") || referIndex.equalsIgnoreCase("INDIA") ) {
                                    // For Residence And NRO Balance in Liability Side
                                    String tableName = "";
                                    String nriQuery = "";
                                    BigDecimal amtAsPerClass = new BigDecimal("0");
                                    BigDecimal numberOfAcs= new BigDecimal("0");
                                    List codeQuery = em.createNativeQuery("select ifnull(code,'COS') from cbs_parameterinfo where name ='NRI_IN'").getResultList();
                                    //Non Resident Indian = 02 Code for recf_rec_no 303;
                                    String code = "";
                                    if (!codeQuery.isEmpty()) {
                                        Vector codeVect = (Vector) codeQuery.get(0);
                                        code = codeVect.get(0).toString();
                                    } else {
                                        code = "'02'";//For Non Resident Indian Only
                                    }
                                    if (referIndex.equalsIgnoreCase("INDIA")) {
                                        nriQuery = "and c.nriflag not in (" + code + ")";
                                    } else if (referIndex.equalsIgnoreCase("NRO")) {
                                        nriQuery = "and c.nriflag in (" + code + ")";
                                    }
                                    if (!acNature.equalsIgnoreCase("")) {
                                        tableName = common.getTableName(acNature);
                                    } else if (!acType.equalsIgnoreCase("")) {
                                        List acnature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
                                        if (!acnature.isEmpty()) {
                                            Vector vect = (Vector) acnature.get(0);
                                            acNature = vect.get(0).toString();
                                            tableName = common.getTableName(acNature);
                                        }
                                    }
                                    String cls = "";
                                    if (params.getClassification().equalsIgnoreCase("L")) {
                                        cls = " having sign(sum(cramt-dramt)) >= -1  ";
                                    } else if (params.getClassification().equalsIgnoreCase("A")) {
                                        cls = " having sign(sum(cramt-dramt)) <= 1 ";
                                    }
                                    List dataList = new ArrayList<>();
                                    if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                        dataList = em.createNativeQuery("select count(distinct a.acno), ifnull(sum(a.amt),0) from ( "
                                                + " select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " a,accountmaster b,cbs_customer_master_detail c ,customerid d "
                                                + " where  a.acno in (select acno from " + tableName + " WHERE DT <= '" + dt + "' group by acno   " + cls + " )  "
                                                + " and (b.closingdate is null or b.closingdate = '' or b.closingdate > '" + dt + "' )   "
                                                + " and a.acno = b.acno and a.dt <='" + dt + "' " + nriQuery + " and a.acno= d.acno and d.acno = b.acno and c.customerid = d.custid "
                                                + " and b.accttype in (select acctcode from accounttypemaster where acctcode = '" + vector.get(0).toString() + "') group by b.acno  ) a").getResultList();
                                        if (!dataList.isEmpty()) {
                                            Vector vect = (Vector) dataList.get(0);
                                            numberOfAcs = new BigDecimal(vect.get(0).toString());
                                            amtAsPerClass = new BigDecimal(vect.get(1).toString());
                                        }
                                    } else {
                                        dataList = em.createNativeQuery("select substring(a.acno,3,2) as acno, cast(ifnull(sum(a.cramt-a.dramt),0)as decimal(25,2)) as amt from " + tableName + " a ,td_accountmaster b ,cbs_customer_master_detail c,customerid d where a.dt <='" + dt + "'  and a.auth ='Y' and a.closeflag is null  and a.acno =b.acno  and substring(a.acno,3,2) = " + vector.get(0).toString() + " " + nriQuery + " and a.acno= d.acno and d.acno = b.acno and c.customerid = d.custid  group by substring(a.acno,3,2) ;").getResultList();
                                        if (!dataList.isEmpty()) {
                                            Vector vect = (Vector) dataList.get(0);
                                            numberOfAcs = new BigDecimal(vect.get(0).toString());
                                            amtAsPerClass = new BigDecimal(vect.get(1).toString());
                                        }
                                    }
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, numberOfAcs);
                                    } else {
                                        if ((amtAsPerClass.compareTo(new BigDecimal("0"))) == 0) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, amtAsPerClass);
                                        } else {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, amtAsPerClass.divide(repOpt));
                                        }
                                    }
                                } else {
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                    } else {
                                        if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                                        } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                        } else {
                                            rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                        }
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                    }


//                                    bal = new BigDecimal(formatter.format(glHeadPo.getBalance().divide(repOpt).doubleValue()));
//                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
//                                        rbiSossPojo.setAmt(bal);
//                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
//                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
//                                    } else {
//                                        rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : bal.abs());
//                                    }

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
                                    if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
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
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));

                                            if (sGNo == 0) {
                                                rbiSossPojo.setsGNo(z + 1);
                                            } else {
                                                rbiSossPojo.setsGNo(sGNo);
                                                if (ssGNo == 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsGNo(z + 1);
                                                } else {
                                                    rbiSossPojo.setSsGNo(ssGNo);
                                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSssGNo(z + 1);
                                                    } else {
                                                        rbiSossPojo.setSssGNo(sssGNo);
                                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                            rbiSossPojo.setSsssGNo(z + 1);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = z + 1;
                                        }
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
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    rbiPojoTable.add(rbiSossPojo);
                                }
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;
//                                preZ = z;
                            }

                            /**
                             * **************************
                             */
//                            acctBal = getAcctsAndBal(params);
//                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                noOfAc = acctBal.getNumberOFAcct();
//                            } else {
//                                bal = acctBal.getBalance();
//                            }
                            /**
                             * ********************
                             */
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = RbiReportFacade.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                if (reptName.equalsIgnoreCase("MON_AGG") || reptName.equalsIgnoreCase("XBRLFORM9")) {
                                    bal = new BigDecimal(formatter.format(monthlyRemote.getMonetaryAggregatesFdValue(acNature, acType, dt, rangeBaseOn, rangeFrom, rangeTo).divide(repOpt).doubleValue()));
                                } else {
                                    acctBal = RbiReportFacade.getAcctsAndBalTimeRangeWise(params);
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = acctBal.getNumberOFAcct();
                                    } else {
                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                    }
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = RbiReportFacade.getNpaAcctsAndBal(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("NIR")) {
                                String tableName = "";
                                String acCodeQuery = "";
                                if (!acNature.equalsIgnoreCase("")) {
                                    acCodeQuery = "acctnature in ('" + acNature + "')";
                                    tableName = common.getTableName(acNature);
                                } else if (!acType.equalsIgnoreCase("")) {
                                    List acnature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
                                    if (!acnature.isEmpty()) {
                                        Vector vect = (Vector) acnature.get(0);
                                        acNature = vect.get(0).toString();
                                        acCodeQuery = "acctcode in ('" + acType + "')";
                                        tableName = common.getTableName(acNature);
                                    }
                                }
                                if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    List dataList = em.createNativeQuery("select count(*) from accountmaster where OpeningDt <= '" + dt + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where " + acCodeQuery + " ) and acno not in (select distinct acno from " + tableName + " ) and (closingdate is null or closingdate ='' or closingdate >'" + dt + "')").getResultList();
                                    Vector vect = (Vector) dataList.get(0);
                                    noOfAc = Long.parseLong(vect.get(0).toString());
                                } else {
                                    List dataList = em.createNativeQuery("select count(*) from td_accountmaster where OpeningDt <= '" + dt + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where " + acCodeQuery + ") and acno not in (select distinct acno from " + tableName + " ) and (closingdate is null or closingdate ='' or closingdate >'" + dt + "')").getResultList();
                                    Vector vect = (Vector) dataList.get(0);
                                    noOfAc = Long.parseLong(vect.get(0).toString());
                                }
                            }

                            rbiSossPojo = new RbiSossPojo();
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                //noOfAc = (long) glPojoList.size();
                                rbiSossPojo.setAmt(new BigDecimal(noOfAc.toString()));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo.setAmt(bal);
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
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
                    } else if ((!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) //&& ((fSsGNo.equalsIgnoreCase("")) && (fSssGNo.equalsIgnoreCase("")) && (fSsssGNo.equalsIgnoreCase("")))
                            ) {
                        GlHeadPojo glHeadPojo = new GlHeadPojo();
//                        glPojoList = getGLHeadAndBal(params);
//                        if (glPojoList.size() > 0) {
//                        List glNameList = em.createNativeQuery("select distinct substring(r.acno,3,8), g.acname from gl_recon r,  "
//                                + "gltable g where r.acno=g.acno and substring(r.acno,3,8) between  '" + glHeadFrom + "' and '" + glHeadTo + "' "
//                                + "group by substring(acno,3,8) having sign(sum(cramt-dramt))<>0").getResultList();
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8)").getResultList();

                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
//                            rbiSossPojo.setsNo(sNo);
//                            rbiSossPojo.setAcNature(acNature);
//                            rbiSossPojo.setAcType(acType);
////                            if (countApplicable.equalsIgnoreCase("Y")) {
////                                rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
////                            } else {
////                                rbiSossPojo.setAmt(new BigDecimal(formatter.format(((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs()).divide(repOpt).doubleValue())));
////                            }
//                            rbiSossPojo.setClassification(classification);
//                            rbiSossPojo.setCountApplicable(countApplicable);
//                            rbiSossPojo.setDescription(description);
//                            rbiSossPojo.setFormula(formula);
//                            rbiSossPojo.setGlHeadTo(glHeadTo);
//                            rbiSossPojo.setGlheadFrom(glHeadFrom);
//                            rbiSossPojo.setNpaClassification(npaClassification);
//                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
//                            rbiSossPojo.setRangeFrom(rangeFrom);
//                            rbiSossPojo.setRangeTo(rangeTo);
//                            rbiSossPojo.setReportName(reportName);
//                            rbiSossPojo.setgNo(gNo);
//                            rbiSossPojo.setsGNo(sGNo);
//                            rbiSossPojo.setSsGNo(ssGNo);
//                            rbiSossPojo.setSssGNo(sssGNo);
//                            rbiSossPojo.setSsssGNo(ssssGNo);
//                            rbiSossPojo.setfGNo(fGNo);
//                            rbiSossPojo.setfSGNo(fSGNo);
//                            rbiSossPojo.setfSsGNo(fSsGNo);
//                            rbiSossPojo.setfSssGNo(fSssGNo);
//                            rbiSossPojo.setfSsssGNo(fSsssGNo);
//                            if (summaryOpt.equalsIgnoreCase("N")) {
//                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
//                                    rbiSossPojo.setgNo(preGNo);
//                                    rbiSossPojo.setsGNo(preSGNo);
//                                    rbiSossPojo.setSsGNo(preSsGNo);
//                                    rbiSossPojo.setSssGNo(preSssGNo);
//                                    rbiSossPojo.setSsssGNo(preSsssGNo);
//
//                                } else {
//                                    rbiSossPojo.setgNo(gNo);
//                                    rbiSossPojo.setsGNo(sGNo);
//                                    rbiSossPojo.setSsGNo(ssGNo);
//                                    rbiSossPojo.setSssGNo(sssGNo);
//                                    rbiSossPojo.setSsssGNo(ssssGNo);
//
//                                    rbiPojoTable.add(rbiSossPojo);
//                                    preGNo = gNo;
//                                    preSGNo = sGNo;
//                                    preSsGNo = ssGNo;
//                                    preSssGNo = sssGNo;
//                                    preSsssGNo = ssssGNo;
//                                    preFormula = formula;
//                                }
//                            } else {
//                                rbiSossPojo.setgNo(gNo);
//                                rbiSossPojo.setsGNo(sGNo);
//                                rbiSossPojo.setSsGNo(ssGNo);
//                                rbiSossPojo.setSssGNo(sssGNo);
//                                rbiSossPojo.setSsssGNo(ssssGNo);
//                                rbiSossPojo.setDescription(description);
//
////                                rbiPojoTable.add(rbiSossPojo);
//                            }

                            /* If GL Head Series have multiple GL Head */
//                            for (int j = 0; j < glPojoList.size(); j++) {
                            rbiSossPojo = new RbiSossPojo();
//                                GlHeadPojo glHeadPo = glPojoList.get(j);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                rbiSossPojo.setAmt(new BigDecimal("0"));
                            } else {
                                if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                    List<String> monthEndList = new ArrayList();
                                    String monthEnd = dt;
                                    if (fSsGNo.equalsIgnoreCase("GLB")) {
                                        monthEndList.add(dt);
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") && !referContent.equalsIgnoreCase("AVG")) {
                                        monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(RbiReportFacade.getMinFinYear(dt), dt);
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") && referContent.equalsIgnoreCase("AVG")) {
                                        Integer month = Integer.parseInt(referIndex);
                                        monthEnd = CbsUtil.dateAdd(CbsUtil.monthAdd(RbiReportFacade.getMinFinYear(dt), month), -1);
                                        if (!ymd.parse(monthEnd).after(ymd.parse(dt))) {
                                            monthEndList.add(monthEnd);
                                        } else if (ymd.parse(monthEnd).after(ymd.parse(dt))) {
                                            monthEndList.add(dt);
                                        }
                                    }

                                    List balList = monthlyRemote.getInsertedGlbList(monthEndList);
                                    BigDecimal avgBal = new BigDecimal(0);
                                    for (int v = 0; v < monthEndList.size(); v++) {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(balList, vector.get(0).toString(), classification, monthEndList.get(v));
                                        if (newBalPojo == null) {
                                            avgBal = avgBal.add(new BigDecimal(0));
                                        } else {
                                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                                avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                                avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                            } else {
                                                avgBal = avgBal.add(fSGNo.equalsIgnoreCase("ACT") ? newBalPojo.getBalance().divide(repOpt) : newBalPojo.getBalance().abs().divide(repOpt));
                                            }    //                                        
                                        }
                                    }
                                    if (referContent.equalsIgnoreCase("AVG") && (!ymd.parse(monthEnd).after(ymd.parse(dt)))) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(referIndex), 2, RoundingMode.HALF_UP));
                                    } else if (referContent.equalsIgnoreCase("AVG") && (ymd.parse(monthEnd).after(ymd.parse(dt)))) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0));;
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
                                    }
                                } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                    String date = params.getDate();
                                    String amt = "";
                                    String minFDate = RbiReportFacade.getMinFinYear(params.getDate());
                                    GlHeadPojo newBalPojo = null;
                                    if (params.getRefer_index().equalsIgnoreCase("F")) {
                                        if (params.getRefer_content().equalsIgnoreCase("<")) {
                                            date = "dt  < '" + minFDate + "' ";
                                            amt = "ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0)";
                                        } else if (params.getRefer_content().equalsIgnoreCase(">")) {
                                            date = "dt  between '" + minFDate + "' and '" + params.getToDate() + "' ";
                                            if (params.getClassification().equalsIgnoreCase("A")) {
                                                amt = "ifnull(cast(sum(cramt) as decimal(15,2)),0)";
                                            } else if (params.getClassification().equalsIgnoreCase("L")) {
                                                amt = "ifnull(cast(sum(dramt) as decimal(15,2)),0)";
                                            }
                                        } else {
                                            date = "dt  between '" + minFDate + "' and '" + params.getToDate() + "' ";
                                            amt = "ifnull(cast(sum(dramt) as decimal(15,2)),0)";
                                        }
                                    } else if (params.getRefer_index().equalsIgnoreCase("Q")) {
                                        if (params.getRefer_content().equalsIgnoreCase("<")) {
                                            date = " dt < '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' ";
                                            amt = "ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0)";
                                        } else if (params.getRefer_content().equalsIgnoreCase(">")) {
                                            date = " dt between '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' and '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "L") + "' ";
                                            if (params.getClassification().equalsIgnoreCase("A")) {
                                                amt = "ifnull(cast(sum(cramt) as decimal(15,2)),0)";
                                            } else if (params.getClassification().equalsIgnoreCase("L")) {
                                                amt = "ifnull(cast(sum(dramt) as decimal(15,2)),0)";
                                            }
                                        } else {
                                            date = " dt between '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' and '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "L") + "' ";
                                            amt = "ifnull(cast(sum(dramt) as decimal(15,2)),0)";
                                        }
                                    }
                                    List dlist = em.createNativeQuery("select " + amt + " from gl_recon where substring(acno,3,8) = '" + vector.get(0).toString() + "' and " + date + " ").getResultList();
                                    if (dlist.isEmpty() || dlist == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                    } else {
                                        Vector listVect = (Vector) dlist.get(0);
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(listVect.get(0).toString()).divide(repOpt));
                                    }
                                } else {
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                    } else {
                                        if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                                        } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply((new BigDecimal("-1"))).divide(repOpt));
                                        } else {
                                            rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                        }
                                        //                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                    }
                                }

//                                    rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs());
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
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
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
                            if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                rbiPojoTable.add(rbiSossPojo);
                            }
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;

//                            }
                        }
                    } else if (fSssGNo.equalsIgnoreCase("EMP")) {
                        /* For Number Of Employees To Be printed in SOSS2 */
                        rbiSossPojo = new RbiSossPojo();
                        int l = 0;
                        if(!noOfEmp.equalsIgnoreCase("0")) {
                            rbiSossPojo.setAmt(new BigDecimal(noOfEmp));
                        } else {
                            List list = em.createNativeQuery("select sum(ifnull(code,0)) as code from parameterinfo_report where "
                                    + "reportname in ('BANK-OTHER-STAFF-NO','BANK-OFFICER-NO')").getResultList();
                            for (l = 0; l < list.size(); l++) {
                                Vector listVect = (Vector) list.get(l);
                                rbiSossPojo.setAmt(new BigDecimal(listVect.get(0).toString()));
                            }
                        }
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setDescription(description);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (fSssGNo.equalsIgnoreCase("OVERDUE")) {
                        /* For Overdue Figure To Be Shown in XBRL FORM 9*/
                        rbiSossPojo = new RbiSossPojo();
                        if(!overDueAmt.toString().equalsIgnoreCase("0")) {
                            rbiSossPojo.setAmt(overDueAmt.divide(repOpt).abs());
                        } else {
                            rbiSossPojo.setAmt(overDueAmt);
                        }
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setDescription(description);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (fSssGNo.equalsIgnoreCase("ODPERC")) {
                        /* For Overdue Percentage Figure To Be Shown in XBRL FORM 9*/
                        rbiSossPojo = new RbiSossPojo();
                        rbiSossPojo.setAmt(new BigDecimal(overDuePer));
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setDescription(description);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (fSssGNo.equalsIgnoreCase("PRIORPER")) {
                        /* For Priority Sector Percentage To Be Shown in XBRL FORM 9*/
                        rbiSossPojo = new RbiSossPojo();
                        rbiSossPojo.setAmt(new BigDecimal(priorPerc));
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setDescription(description);
                        rbiPojoTable.add(rbiSossPojo);
                    } else if (!(fSssGNo.equalsIgnoreCase("")) || !(fSsGNo.equalsIgnoreCase("")) || !(fSGNo.equalsIgnoreCase("")) || !(fGNo.equalsIgnoreCase(""))) {
                        List<LoanMisCellaniousPojo> misResultList = new ArrayList<LoanMisCellaniousPojo>();
                        misResultList = loanReportFacade.cbsLoanMisReport(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                dt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "O",
                                fGNo.equalsIgnoreCase("") ? "0" : fGNo, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", fSsGNo.equalsIgnoreCase("") ? "0" : fSsGNo,
                                fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "0", "0", "0",
                                "0", "0", "0", "0", "0", "0", "0", "0", referContent.equalsIgnoreCase("") ? "0" : referContent, "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N","0","N","N", "0", "0", "0", "0");
                        /*(String branchCode, String acNature, String accType, 
                         * String dt, String reportBasedOn, double from, double to, String reportBase, 
                         * String sector, String subSector, String modeOfAdvance, String secured, 
                         * String typeOfAdvance, String purposeOfAdvance, 
                         * String guarnteeCover, String purOfAdv, String exposure, String exposureCategory, String exposureCategoryPurpose, String schemeCode, String intType, String applicantCategory, String categoryOpt, String minorCommunity, String relation, String relOwner, String drawingPwrInd, String popuGroup, String sanctionLevel, String sanctionAuth, String courts, String restructuring, String loanPeriod)*/
                        if (countApplicable.equalsIgnoreCase("Y")) {
                            BigDecimal a = new BigDecimal("0");
                            Long b = 0l;
                            for (int k = 0; k < misResultList.size(); k++) {
                                if (misResultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    if (misResultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) < 0) {
                                        b = b + 1;
                                    }
                                } else {
                                    b = b + 1;
                                }
                            }
                            rbiSossPojo.setAmt(new BigDecimal(b));
                        } else {
                            BigDecimal a = new BigDecimal("0");
                            for (int k = 0; k < misResultList.size(); k++) {
                                if (misResultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    a = a.add(misResultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misResultList.get(k).getOutstanding());
                                } else {
                                    a = a.add(misResultList.get(k).getOutstanding());
                                }
                            }
                            rbiSossPojo.setAmt(a.divide(repOpt).abs());
                        }
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setDescription(description);
//                        rbiPojoTable.add(rbiSossPojo);
                    } else if ((!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) && !((rangeFrom.equalsIgnoreCase("0.00") || rangeFrom.equalsIgnoreCase("")) && (rangeTo.equalsIgnoreCase("0.00") || rangeTo.equalsIgnoreCase("")))) {
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
                            if (acNature.equalsIgnoreCase("NPA") || acType.equalsIgnoreCase("NPA")) {
                                natureList = em.createNativeQuery(" select ifnull(ACCODE,'') from ho_crr_asset_liab where ACCODE = 'NPA'").getResultList();
                            }
//                            for (int n = 0; n < natureList.size(); n++) {
//                                Vector vector = (Vector) natureList.get(n);
//                                params.setNature("");
//                                params.setAcType(vector.get(0).toString());
//
//                                GlHeadPojo glPojo = new GlHeadPojo();
//                                glPojo.setGlHead(vector.get(0).toString());
//                                glPojo.setGlName(vector.get(1).toString());
//                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                    noOfAc = getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
//                                    glPojo.setBalance(new BigDecimal(noOfAc.toString()));
//                                } else {
//                                    acctBal = getAcctsAndBal(params);
//                                    glPojo.setBalance(acctBal.getBalance());
//                                }
//                                glHeadList.add(glPojo);
//                            }
                            /*Setting into main list*/
                            for (int z = 0; z < natureList.size(); z++) {
                                Vector vector = (Vector) natureList.get(z);
                                rbiSossPojo = new RbiSossPojo();
//                                GlHeadPojo glHeadPo = glHeadList.get(z);
                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                    noOfAc = (long) glPojoList.size();
                                    params.setNature("");
                                    params.setAcType(vector.get(0).toString());
                                    noOfAc = RbiReportFacade.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(noOfAc.toString()));
                                } else if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                    List<String> monthEndList = new ArrayList();
                                    String monthEnd = dt;
                                    if (fSsGNo.equalsIgnoreCase("GLB")) {
                                        monthEndList.add(dt);
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") && !referContent.equalsIgnoreCase("AVG")) {
                                        monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(RbiReportFacade.getMinFinYear(dt), dt);
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") && referContent.equalsIgnoreCase("AVG")) {
                                        Integer month = Integer.parseInt(referIndex);
                                        monthEnd = CbsUtil.dateAdd(CbsUtil.monthAdd(RbiReportFacade.getMinFinYear(dt), month), -1);
                                        if (!ymd.parse(monthEnd).after(ymd.parse(dt))) {
                                            monthEndList.add(monthEnd);
                                        } else if (ymd.parse(monthEnd).after(ymd.parse(dt))) {
                                            monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(RbiReportFacade.getMinFinYear(dt), dt);
                                        }
                                    }

                                    List balList = monthlyRemote.getInsertedGlbList(monthEndList);
                                    BigDecimal avgBal = new BigDecimal(0);
                                    for (int v = 0; v < monthEndList.size(); v++) {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(balList, vector.get(0).toString(), classification, monthEndList.get(v));
                                        if (newBalPojo == null) {
                                            avgBal = avgBal.add(new BigDecimal(0));
                                        } else {
                                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                                avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                                avgBal = avgBal.add(newBalPojo.getBalance().divide(repOpt));
                                            } else {
                                                avgBal = avgBal.add(fSGNo.equalsIgnoreCase("ACT") ? newBalPojo.getBalance().divide(repOpt) : newBalPojo.getBalance().abs().divide(repOpt));
                                            }
                                        }
                                    }
                                    if (referContent.equalsIgnoreCase("AVG") && (!ymd.parse(monthEnd).after(ymd.parse(dt)))) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(referIndex), 2, RoundingMode.HALF_UP));
                                    } else if (referContent.equalsIgnoreCase("AVG") && (ymd.parse(monthEnd).after(ymd.parse(dt)))) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0));;
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
                                    }
                                } else {
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                    } else {
                                        if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                                        } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                        } else {
                                            rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                        }
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                    }
//                                    bal = new BigDecimal(formatter.format(glHeadPo.getBalance().divide(repOpt).doubleValue()));
//                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
//                                        rbiSossPojo.setAmt(bal);
//                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
//                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
//                                    } else {
//                                        rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : bal.abs());
//                                    }
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
                                    if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
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
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                            if (sGNo == 0) {
                                                rbiSossPojo.setsGNo(z + 1);
                                            } else {
                                                rbiSossPojo.setsGNo(sGNo);
                                                if (ssGNo == 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsGNo(z + 1);
                                                } else {
                                                    rbiSossPojo.setSsGNo(ssGNo);
                                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSssGNo(z + 1);
                                                    } else {
                                                        rbiSossPojo.setSssGNo(sssGNo);
                                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                            rbiSossPojo.setSsssGNo(z + 1);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = z + 1;
                                        }
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
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    rbiPojoTable.add(rbiSossPojo);
                                }
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;
                            }
                            /**
                             * **************************
                             */
//                            acctBal = getAcctsAndBal(params);
//                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                noOfAc = acctBal.getNumberOFAcct();
//                            } else {
//                                bal = acctBal.getBalance();
//                            }
                            /**
                             * ********************
                             */
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = RbiReportFacade.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                if (reptName.equalsIgnoreCase("MON_AGG") || reptName.equalsIgnoreCase("XBRLFORM9")) {
                                    bal = new BigDecimal(formatter.format(monthlyRemote.getMonetaryAggregatesFdValue(acNature, acType, dt, rangeBaseOn, rangeFrom, rangeTo).divide(repOpt).doubleValue()));
                                } else {
                                    acctBal = RbiReportFacade.getAcctsAndBalTimeRangeWise(params);
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = acctBal.getNumberOFAcct();
                                    } else {
                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                    }
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = RbiReportFacade.getNpaAcctsAndBal(params);
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
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo.setAmt(bal);
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
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
                    } else if (npaClassification.equalsIgnoreCase("NDTL")) {
                        rbiSossPojo = new RbiSossPojo();
//                        Vector ele = null;
//                        List reportList2 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + dt + "'").getResultList();
//                        if (reportList2.isEmpty()) {
//                            throw new ApplicationException("Last Reporting Friday Date does not defined...");
//                        }
//                        ele = (Vector) reportList2.get(0);
//                        String repFriDate = ele.get(0).toString();
//                        repFriDate = CbsUtil.dateAdd(dt, -14);
                        bal = hoRemote.getTotalNdtl("0A", dt);
                        rbiSossPojo.setAmt(bal.divide(repOpt));
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
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo;
                    preFormula = formula;
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
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
//                    System.out.println("K: "+k+"rbiSossPojo.getFormula(): "+rbiSossPojo.getFormula());
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal bal = new BigDecimal("0.0");
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                            double balPL = glReport.IncomeExp(dt, "0A", "0A");
                            if (rbiSossPojo.getClassification().equalsIgnoreCase("L") && balPL >= 0) {
                                if (rbiSossPojo.getgNo() == 1) {
                                    rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                } else {
                                    rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).multiply(new BigDecimal("-1")).divide(repOpt).doubleValue())));
                                }
                            } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL < 0) {
                                if ((reptName.contains("OSS1") || reptName.contains("FORM9")) && rbiSossPojo.getgNo() == 1) {
                                    rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                } else {
                                    rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                }
                            }
                        } else if (rbiSossPojo.getFormula().contains("OSS7")) {
                            String[] strArr = rbiSossPojo.getFormula().split("#");
                            bal = bal.add(RbiReportFacade.getValueFromFormula(oss7PartAList, strArr[1]));
                            rbiSossPojo.setAmt(bal.divide(repOpt));
                        } else {
                            bal = bal.add(RbiReportFacade.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula()));
                            if (rbiSossPojo.getRefIndex().equalsIgnoreCase("Rs") && (rbiSossPojo.getReportName().equalsIgnoreCase("DICGC") ||rbiSossPojo.getReportName().equalsIgnoreCase("DICGC_OLD")) ) {
                                rbiSossPojo.setAmt(bal.multiply(repOpt));
                            } else {
                                rbiSossPojo.setAmt(bal);
                            }
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
            /**
             * For Values to Be Absolute*
             */
//            for(int j=0 ; j<rbiPojoTable.size() ; j++){
//                rbiPojoTable.get(j).setAmt(rbiPojoTable.get(j).getAmt().abs());
//            }
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RbiSoss1AnnexI2> getSOSS1AnnexI2(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<RbiSoss1AnnexI2> rbiSoss1AnnexI2PojoTable = new ArrayList<RbiSoss1AnnexI2>();
            List soss1AnnexI2List = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (soss1AnnexI2List.size() > 0) {
            } else {
                RbiSoss1AnnexI2 rbiSossPojo = new RbiSoss1AnnexI2();
                rbiSossPojo.setBankName("");
                rbiSossPojo.setDepositAmt(new BigDecimal(0));
                rbiSossPojo.setRoi(new BigDecimal(0));
                rbiSossPojo.setDateOfReceipt("");
                rbiSossPojo.setPdInDays("000");
                rbiSossPojo.setPdInMonth("000");
                rbiSossPojo.setPdInYears("0000");
                rbiSossPojo.setMatDt("");
                rbiSoss1AnnexI2PojoTable.add(rbiSossPojo);
            }
            return rbiSoss1AnnexI2PojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RbiSoss1AnnexI3And4> getSOSS1AnnexI3And4(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<RbiSoss1AnnexI3And4> rbiSoss1AnnexI3And4PojoTable = new ArrayList<RbiSoss1AnnexI3And4>();
            List soss1AnnexI3And4List = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (soss1AnnexI3And4List.size() > 0) {
                for (int i = 0; i < soss1AnnexI3And4List.size(); i++) {
                    RbiSoss1AnnexI3And4 rbiSossPojo = new RbiSoss1AnnexI3And4();
                    Vector oss1Vect = (Vector) soss1AnnexI3And4List.get(i);
                    // Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    //  String reportName = oss1Vect.get(1).toString();
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    // Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    // Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    // Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    // String classification = oss1Vect.get(8).toString();
                    // String countApplicable = oss1Vect.get(9).toString();
                    //  String acNature = oss1Vect.get(10).toString();
                    //  String acType = oss1Vect.get(11).toString();
                    //   String glHeadFrom = oss1Vect.get(12).toString();
                    //  String glHeadTo = oss1Vect.get(13).toString();
                    //   String rangeBaseOn = oss1Vect.get(14).toString();
                    //   String rangeFrom = oss1Vect.get(15).toString();
                    //   String rangeTo = oss1Vect.get(16).toString();
                    //   String formula = oss1Vect.get(17).toString();
                    //   String fGNo = oss1Vect.get(18).toString();
                    //    String fSGNo = oss1Vect.get(19).toString();
                    //   String fSsGNo = oss1Vect.get(20).toString();
                    //    String fSssGNo = oss1Vect.get(21).toString();
                    //     String fSsssGNo = oss1Vect.get(22).toString();
                    //     String npaClassification = oss1Vect.get(23).toString();

                    rbiSossPojo.setgNo(gNo);
                    rbiSossPojo.setsGNo(sGNo);
                    rbiSossPojo.setTimePd(description);
                    rbiSossPojo.setNoOfDrEntry(0);
                    rbiSossPojo.setDrAmt(new BigDecimal(0));
                    rbiSossPojo.setNoOfCrEntry(0);
                    rbiSossPojo.setCrAmt(new BigDecimal(0));
                    rbiSoss1AnnexI3And4PojoTable.add(rbiSossPojo);
                }
            } else {
                RbiSoss1AnnexI3And4 rbiSossPojo = new RbiSoss1AnnexI3And4();
                rbiSossPojo.setgNo(1);
                rbiSossPojo.setsGNo(0);
                rbiSossPojo.setTimePd("Please fill the master of SOSS1AnnexI3And4 in CBS_HO_RBI_STMT_REPORT Table");
                rbiSossPojo.setNoOfDrEntry(0);
                rbiSossPojo.setDrAmt(new BigDecimal(0));
                rbiSossPojo.setNoOfCrEntry(0);
                rbiSossPojo.setCrAmt(new BigDecimal(0));
                rbiSoss1AnnexI3And4PojoTable.add(rbiSossPojo);
            }
            return rbiSoss1AnnexI3And4PojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RbiSoss1AnnexI5> getSOSS1AnnexI5(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<RbiSoss1AnnexI5> rbiSoss1AnnexI5PojoTable = new ArrayList<RbiSoss1AnnexI5>();
            List soss1AnnexI5List = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (soss1AnnexI5List.size() > 0) {
            } else {
                RbiSoss1AnnexI5 rbiSossPojo = new RbiSoss1AnnexI5();
                rbiSossPojo.setBorrowerName("");
                rbiSossPojo.setBeneficiaryName("");
                rbiSossPojo.setOutStdAmtAsOnQtrEnd(new BigDecimal(0));
                rbiSoss1AnnexI5PojoTable.add(rbiSossPojo);
            }
            return rbiSoss1AnnexI5PojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RbiSoss1AnnexII> getSOSS1AnnexII(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<RbiSoss1AnnexII> rbiSoss1AnnexIIPojoTable = new ArrayList<RbiSoss1AnnexII>();
            List soss1AnnexIIList = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (soss1AnnexIIList.size() > 0) {
                for (int i = 0; i < soss1AnnexIIList.size(); i++) {
                    RbiSoss1AnnexII rbiSossPojo = new RbiSoss1AnnexII();
                    Vector oss1Vect = (Vector) soss1AnnexIIList.get(i);
                    // Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    // String reportName = oss1Vect.get(1).toString();
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    // String classification = oss1Vect.get(8).toString();
                    // String countApplicable = oss1Vect.get(9).toString();
                    // String acNature = oss1Vect.get(10).toString();
                    //  String acType = oss1Vect.get(11).toString();
                    //  String glHeadFrom = oss1Vect.get(12).toString();
                    //  String glHeadTo = oss1Vect.get(13).toString();
                    //  String rangeBaseOn = oss1Vect.get(14).toString();
                    //  String rangeFrom = oss1Vect.get(15).toString();
                    //  String rangeTo = oss1Vect.get(16).toString();
                    //  String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();
                    //    String fSGNo = oss1Vect.get(19).toString();
                    //    String fSsGNo = oss1Vect.get(20).toString();
                    //   String fSssGNo = oss1Vect.get(21).toString();
                    //    String fSsssGNo = oss1Vect.get(22).toString();
                    //    String npaClassification = oss1Vect.get(23).toString();

                    rbiSossPojo.setgNo(gNo);
                    rbiSossPojo.setsGNo(sGNo);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setDueFromName("");
                    rbiSossPojo.setDueAmt(new BigDecimal(0));
                    rbiSossPojo.setProvisionMade(new BigDecimal(0));
                    rbiSossPojo.setFooter(fGNo);
                    rbiSoss1AnnexIIPojoTable.add(rbiSossPojo);
                }
            } else {
                RbiSoss1AnnexII rbiSossPojo = new RbiSoss1AnnexII();
                rbiSossPojo.setgNo(1);
                rbiSossPojo.setsGNo(0);
                rbiSossPojo.setDescription("Please fill the master of Annex II in CBS_HO_RBI_STMT_REPORT Table");
                rbiSossPojo.setDueFromName("");
                rbiSossPojo.setDueAmt(new BigDecimal(0));
                rbiSossPojo.setProvisionMade(new BigDecimal(0));
                rbiSoss1AnnexIIPojoTable.add(rbiSossPojo);
            }
            return rbiSoss1AnnexIIPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

}
