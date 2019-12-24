/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.Oss4PartGExpPojo;
import com.cbs.dto.report.Oss4PartGMgmtPojo;
import com.cbs.dto.report.Oss4PartGPojo;
import com.cbs.dto.report.Oss4PartGSecAdvPojo;
import com.cbs.dto.report.Oss4PartGUnSecAdvPojo;
import com.cbs.dto.report.PrioritySectorPojo;
import com.cbs.dto.report.RbiSos4Pojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.TopDepositorBorrowerPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.DateSlabPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaRecoveryPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.Oss4PartACombinedPojo;
import com.cbs.pojo.TopLoanPoJoCompareGroupID;
import com.cbs.pojo.TopLoanPojo;
import com.cbs.pojo.TopLoanPojoComparatorForOutStanding;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.PostfixEvaluator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiReportPartDFacade")
public class RbiReportPartDFacade implements RbiReportPartDFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    RbiReportFacadeRemote rbiReportFacade;
    @EJB
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    LoanInterestCalculationFacadeRemote loanIntRemote;
    @EJB
    private RbiReportFacadeRemote rbiReportRemote;
    @EJB
    LoanReportFacadeRemote loanRemote;
    @EJB
    InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    MiscReportFacadeRemote miscReportFacade;
    @EJB
    RbiQuarterlyReportFacadeRemote rbiQuaterly;
    @EJB
    private GLReportFacadeRemote glReport;
    @EJB
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    @EJB
    private HoReportFacadeRemote hoRemote;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.####");

    /**
     * It is a part of SOSS4/Statement5.
     *
     * @param reportDt
     * @param orgnCode
     * @param repOpt
     * @return
     * @throws ApplicationException
     */
    public List<RbiSos4Pojo> getPartD(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<RbiSos4Pojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION,REFER_CONTENT  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'OSS4-PART-D' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            BigDecimal totalNpa = new BigDecimal("0");
            List<NpaStatusReportPojo> npaResultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", reportDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
            for (int n = 0; n < npaResultList.size(); n++) {
                totalNpa = totalNpa.add(npaResultList.get(n).getBalance());
            }
            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
            BigDecimal totalLoan = new BigDecimal("0");
            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0",
                    reportDt, "A", 0, 999999999999.99, "S", "0",
                    "0", "0", "0", "0", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            for (int z = 0; z < resultList.size(); z++) {
                if (resultList.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    totalLoan = totalLoan.add(resultList.get(z).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultList.get(z).getOutstanding() : new BigDecimal("0"));
                } else {
                    totalLoan = totalLoan.add(resultList.get(z).getOutstanding());
                }
//                totalLoan = totalLoan.add(resultList.get(z).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultList.get(z).getOutstanding() : new BigDecimal("0"));
            }
//            System.out.println(">>>totalLoan: " + totalLoan + "; Npa:" + totalNpa);
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
                    Long totAccountNo = 0l, nAccountNo = 0l;
                    BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0), lossProv = new BigDecimal("0");

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
                    String fGNo = oss1Vect.get(18).toString();  //Sector
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString(); //SubSector
                    String referContent = oss1Vect.get(24).toString();//For Actual Number Of Accounts to Be print

                    if (sssGNo != 0) {
                        AdditionalStmtPojo params = new AdditionalStmtPojo();
                        params.setAcType(acType);
                        params.setNature(acNature);
                        params.setBrnCode(orgnCode);
                        params.setClassification(classification);
                        params.setDate(reportDt);
                        params.setToDate(reportDt);
                        params.setRangeBasedOn(rangeBaseOn);
                        params.setFromRange(rangeFrom);
                        params.setToRange(rangeTo);
                        params.setGlFromHead(fGNo);
                        params.setGlToHead(npaClassification);
                        params.setFlag(fSGNo);

//                        if (rangeBaseOn == null || rangeBaseOn.equalsIgnoreCase("") || rangeBaseOn.equalsIgnoreCase("RS")) {
                        if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                            params.setOrgBrCode("Oss4PartD");
//                            resultList = new ArrayList<LoanMisCellaniousPojo>();
//                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
//                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
//                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, "0", "0", "0", "0", "0",
//                                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "D", "N","0");
//                            AcctBalPojo pojo = getAcctsAndBalD(params);
//                            LoanMisCellaniousPojo val1 = resultList.get(0);
//                            totAmount = totAmount.add(val1.getOutstanding());

                            for (int k = 0; k < resultList.size(); k++) {
                                LoanMisCellaniousPojo val = resultList.get(k);
                                if (fGNo.equalsIgnoreCase(resultList.get(k).getSector()) && npaClassification.equalsIgnoreCase(resultList.get(k).getSubSector())) {
//                                totAmount = totAmount.add(val.getOutstanding());
                                    if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        totAmount = totAmount.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                        if (referContent.equalsIgnoreCase("A")) {
                                            if (!(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                totAccountNo = totAccountNo + 1;
                                            }
                                        } else {
                                            totAccountNo = totAccountNo + 1;
                                        }
                                    } else {
                                        totAmount = totAmount.add(resultList.get(k).getOutstanding());
                                        totAccountNo = totAccountNo + 1;
                                    }
                                    String acno = val.getAcNo();
//                                    if (!acno.isEmpty()) {
//                                        totAccountNo = totAccountNo + 1;
//                                    }
                                    for (int npa = 0; npa < npaResultList.size(); npa++) {
                                        String npaAc = npaResultList.get(npa).getAcno();
                                        if (npaAc.equalsIgnoreCase(acno)) {
                                            nAccountNo = nAccountNo + 1;
                                            nAmount = nAmount.add(npaResultList.get(npa).getBalance());
//                                            if(npaResultList.get(npa).getPresentStatus().equalsIgnoreCase("Loss")) {
                                                lossProv = lossProv.add(npaResultList.get(npa).getProvAmt());
//                                            }
                                            }
                                        }
                                    }
                                }
                            }
//                            totAccountNo = pojo.getNumberOFAcct();
//                            totAmount = pojo.getBalance();

//                            if (!params.getAcType().equals("")) {
//                                params.setAcType("NPA." + params.getAcType());
//                            } else {
//                                params.setNature("NPA." + params.getNature());
//                            }
//                            params.setOrgBrCode("Oss4PartDNpa");
//                            pojo = getAcctsAndBalD(params);
//                            nAccountNo = totAccountNo;
//                            nAmount = totAmount;
//                        } else {
//                            params.setOrgBrCode("Oss4PartD");
//                            AcctBalPojo pojo = getAcctsAndBalAmtRangeWiseD(params);
//                            totAccountNo = pojo.getNumberOFAcct();
//                            totAmount = pojo.getBalance();
//
//                            if (!params.getAcType().equals("")) {
//                                params.setAcType("NPA." + params.getAcType());
//                            } else {
//                                params.setNature("NPA." + params.getNature());
//                            }
////                            params.setOrgBrCode("Oss4PartDNpa");
//                            pojo = getAcctsAndBalAmtRangeWiseD(params);
//                            nAccountNo = pojo.getNumberOFAcct();
//                            nAmount = pojo.getBalance();
//                        }
                    } else {
                        totAmount = new BigDecimal("0");
                        nAmount = new BigDecimal("0");
                    }

                    RbiSos4Pojo pojo = new RbiSos4Pojo();
                    pojo.setsNo(sNo);
                    pojo.setReportName(reportName);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setGlheadFrom(glHeadFrom);
                    pojo.setGlHeadTo(glHeadTo);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setTotAccountNo(totAccountNo);
                    pojo.setLossProv(lossProv);
                    if (totAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setTotAmount(totAmount.abs().divide(repOpt));
                        pojo.setPerTotalAmt(((totAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                        pojo.setLossProv(lossProv.abs().divide(repOpt));
                    } else {
                        pojo.setTotAmount(totAmount);
                        pojo.setPerTotalAmt(totAmount);                        
                    }

                    pojo.setnAccountNo(nAccountNo);
                    if (nAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setnAmount(nAmount.abs().divide(repOpt));
                        pojo.setPerNpaAmt(((nAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalNpa.divide(repOpt), RoundingMode.HALF_UP).abs());
                        pojo.setLossProv(lossProv.abs().divide(repOpt));
                    } else {
                        pojo.setnAmount(nAmount);
                        pojo.setPerNpaAmt(nAmount);                        
                    }                    
                    //pojo.setLossProv(lossProv);
                    rbiPojoTable.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<RbiSos4Pojo> getIndustryWiseExposure(String reptName, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<RbiSos4Pojo>();
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reptName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            BigDecimal totalNpa = new BigDecimal("0");
            List<NpaAccountDetailPojo> npaResultList = overDueReportFacade.getNpaDetail("ALL", "ALL", reportDt, "0A", "N");
            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
            BigDecimal totalLoan = new BigDecimal("0");
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                resultList = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0",
                        reportDt, "A", 0, 999999999999.99, "S", "0",
                        "0", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                for (int i = 0; i < oss1Query.size(); i++) {
                    Long totAccountNo = 0l, nAccountNo = 0l;
                    BigDecimal totAmount = new BigDecimal(0), subAmount = new BigDecimal(0), doubtAmount = new BigDecimal(0), lossAmount = new BigDecimal(0);
                    BigDecimal totNpaAmount = new BigDecimal(0), subPercAmt = new BigDecimal(0), doubtPercAmt = new BigDecimal(0), lossPercAmt = new BigDecimal(0), totalPerAmt = new BigDecimal(0);
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
                    if (sssGNo != 0) {
                        AdditionalStmtPojo params = new AdditionalStmtPojo();
                        params.setAcType(acType);
                        params.setNature(acNature);
                        params.setBrnCode(orgnCode);
                        params.setClassification(classification);
                        params.setDate(reportDt);
                        params.setToDate(reportDt);
                        params.setRangeBasedOn(rangeBaseOn);
                        params.setFromRange(rangeFrom);
                        params.setToRange(rangeTo);
                        params.setGlFromHead(fGNo);
                        params.setGlToHead(npaClassification);
                        params.setFlag(fSGNo);
                        if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
//                            resultList = new ArrayList<LoanMisCellaniousPojo>();
                            params.setOrgBrCode("Oss4PartD");
//                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
//                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
//                                    "0" , "0", "0", "0", "0", "0","0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", 
//                                    "0", "0", "0", "0", "0", "Active", "0", "D", "N",npaClassification.equalsIgnoreCase("") ? "0" : npaClassification);
                            for (int k = 0; k < resultList.size(); k++) {
                                LoanMisCellaniousPojo val = resultList.get(k);
                                if (npaClassification.equalsIgnoreCase(resultList.get(k).getIndustry())) {
                                    if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        totAmount = totAmount.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                    } else {
                                        totAmount = totAmount.add(resultList.get(k).getOutstanding());
                                    }
                                    String acno = val.getAcNo();
                                    for (int npa = 0; npa < npaResultList.size(); npa++) {
                                        String npaAc = npaResultList.get(npa).getAcNo();
                                        if (npaAc.equalsIgnoreCase(acno)) {
                                            if (npaResultList.get(npa).getCurrentStatus().contains("SUB")) {
                                                subAmount = subAmount.add(npaResultList.get(npa).getOutstdBal());
                                                subPercAmt = subPercAmt.add(npaResultList.get(npa).getProvAmt());
                                            } else if (npaResultList.get(npa).getCurrentStatus().contains("DOU")) {
                                                doubtAmount = doubtAmount.add(npaResultList.get(npa).getOutstdBal());
                                                doubtPercAmt = doubtPercAmt.add(npaResultList.get(npa).getProvAmt());
                                            } else if (npaResultList.get(npa).getCurrentStatus().equalsIgnoreCase("LOSS")) {
                                                lossAmount = lossAmount.add(npaResultList.get(npa).getOutstdBal());
                                                lossPercAmt = lossPercAmt.add(npaResultList.get(npa).getProvAmt());
                                            }
                                        }
                                    }
                                }
                            }
                            totNpaAmount = subAmount.add(doubtAmount.add(lossAmount));
                            totalPerAmt = subPercAmt.add(doubtPercAmt.add(lossPercAmt));
                        }
                    } else {
                        totAmount = new BigDecimal("0");
                        subAmount = new BigDecimal("0");
                        doubtAmount = new BigDecimal("0");
                        lossAmount = new BigDecimal("0");
                        totNpaAmount = new BigDecimal("0");
                    }
                    RbiSos4Pojo pojo = new RbiSos4Pojo();
                    pojo.setsNo(sNo);
                    pojo.setReportName(reportName);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setGlheadFrom(glHeadFrom);
                    pojo.setGlHeadTo(glHeadTo);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setTotAccountNo(totAccountNo);
                    if (totAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setTotAmount(totAmount.abs().divide(repOpt));
                    } else {
                        pojo.setTotAmount(totAmount);
                    }
                    pojo.setnAccountNo(nAccountNo);
                    if (subAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setnAmount(subAmount.abs().divide(repOpt));
                    } else {
                        pojo.setnAmount(subAmount);
                    }
                    if (doubtAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setTrdAmount(doubtAmount.abs().divide(repOpt));
                    } else {
                        pojo.setTrdAmount(doubtAmount);
                    }
                    if (lossAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setFourthAmount(lossAmount.abs().divide(repOpt));
                    } else {
                        pojo.setFourthAmount(lossAmount);
                    }
                    if (totNpaAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setTotalNPAAmt(totNpaAmount.abs().divide(repOpt));
                    } else {
                        pojo.setTotalNPAAmt(totNpaAmount);
                    }
                    if (subPercAmt.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setSubPerAmt(subPercAmt.abs().divide(repOpt));
                    } else {
                        pojo.setSubPerAmt(subPercAmt);
                    }
                    if (doubtPercAmt.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setDoubtPerAmt(doubtPercAmt.abs().divide(repOpt));
                    } else {
                        pojo.setDoubtPerAmt(doubtPercAmt);
                    }
                    if (lossPercAmt.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setLossPerAmt(lossPercAmt.abs().divide(repOpt));
                    } else {
                        pojo.setLossPerAmt(lossPercAmt);
                    }
                    if (totalPerAmt.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setTotalPercAmt(totalPerAmt.abs().divide(repOpt));
                    } else {
                        pojo.setTotalPercAmt(totalPerAmt);
                    }
                    rbiPojoTable.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<Oss4PartACombinedPojo> getSOS4PartA(String reportDt, String orgnCode, BigDecimal repOpt, List osBlancelist) throws ApplicationException {
        List<Oss4PartACombinedPojo> mainList = new ArrayList<Oss4PartACombinedPojo>();
        List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();          //First Part DataList
        List<RbiSos4Pojo> s4List = new ArrayList<RbiSos4Pojo>();                //Second Part DataList
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'OSS4-PART-A' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT.");
            } else {
//                System.out.println("Start Time PART A-I:" + new Date());
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", reportDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                for (int i = 0; i < oss1Query.size(); i++) {
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

                    BigDecimal calculatedAmount = new BigDecimal("0.00");
                    RbiSossPojo pojo = new RbiSossPojo();
                    if (!glHeadFrom.equals("")) {  //FROM GLHEAD CHECKING AND DATA FILLING  [For Deductions]
                        AdditionalStmtPojo stmtPojo = new AdditionalStmtPojo();
                        stmtPojo.setBrnCode(orgnCode);
                        stmtPojo.setClassification(classification);
                        stmtPojo.setDate(reportDt);
                        stmtPojo.setGlFromHead(glHeadFrom);
                        stmtPojo.setGlToHead(glHeadTo);
                        stmtPojo.setFlag(fSGNo);
                        List<GlHeadPojo> headPojoList = getGLHeadAndBal(stmtPojo);
                        for (int j = 0; j < headPojoList.size(); j++) {
                            GlHeadPojo headPojo = headPojoList.get(j);
                            calculatedAmount = calculatedAmount.add(headPojo.getBalance());
                        }
                    } else if (!acNature.equals("") || !acType.equals("")) {    //NATURE AND TYPE CHECKING AND DATA FILLING
                        if (rangeBaseOn.equals("Y")) {    //For Doubtful
                            AdditionalStmtPojo stmtPojo = new AdditionalStmtPojo();
                            stmtPojo.setNature(acNature);
                            stmtPojo.setAcType(acType);
                            stmtPojo.setClassification(classification);
                            stmtPojo.setDate(reportDt);
                            stmtPojo.setBrnCode(orgnCode);
                            stmtPojo.setRangeBasedOn(rangeBaseOn);
                            stmtPojo.setFromRange(rangeFrom);
                            stmtPojo.setToRange(rangeTo);
                            stmtPojo.setNpaClassification(npaClassification);
                            stmtPojo.setFlag(fSGNo);
                            AcctBalPojo balPojo = getAmountBasedOnNpaClassificationForOss4(stmtPojo, resultList);
                            calculatedAmount = balPojo.getBalance();
                        } else {    //For Substandard And Loss
                            AdditionalStmtPojo stmtPojo = new AdditionalStmtPojo();
                            stmtPojo.setNature(acNature);
                            stmtPojo.setAcType(acType);
                            stmtPojo.setClassification(classification);
                            stmtPojo.setDate(reportDt);
                            stmtPojo.setBrnCode(orgnCode);
                            stmtPojo.setRangeBasedOn(rangeBaseOn);
                            stmtPojo.setFromRange("");
                            stmtPojo.setToRange("");
                            stmtPojo.setNpaClassification(npaClassification);
                            stmtPojo.setFlag(fSGNo);
                            AcctBalPojo balPojo = getAmountBasedOnNpaClassificationForOss4(stmtPojo, resultList);
                            calculatedAmount = balPojo.getBalance();
                        }
                    }

                    pojo.setsNo(sNo);
                    pojo.setReportName(reportName);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setGlheadFrom(glHeadFrom);
                    pojo.setGlHeadTo(glHeadTo);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setAmt(calculatedAmount.abs().divide(repOpt));

                    rbiPojoTable.add(pojo);
                }
            }
//            System.out.println("Start Of SOSS2 Value:" + new Date());
            List<RbiSossPojo> oss1List = new ArrayList<>();
            List<RbiSossPojo> oss7PartAList = new ArrayList<>();
            List chk1 = em.createNativeQuery("SELECT  distinct(FORMULA) FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'OSS4-PART-A' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME,CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            if (!chk1.isEmpty()) {
                List<String> dates = new ArrayList<>();
                dates.add(reportDt);
                for (int i = 0; i < chk1.size(); i++) {
                    Vector vect = (Vector) chk1.get(i);
                    String chk = vect.get(0).toString();
                    if (chk.contains("XBRLOSS7")) {
                        oss1List = rbiSoss1And2Remote.getSOSS2("XBRLOSS1", reportDt, orgnCode, repOpt, "N", osBlancelist, "0");
                        oss7PartAList = rbiQuaterly.getOss7PartA("XBRLOSS7-PART-A", dates.get(0), orgnCode, repOpt, "Y", osBlancelist, "Y");
                        break;
                    } else if (chk.contains("OSS7")) {
                        oss1List = rbiSoss1And2Remote.getSOSS2("SOSS1", reportDt, orgnCode, repOpt, "N", osBlancelist, "0");
                        oss7PartAList = rbiQuaterly.getOss7PartA("OSS7-PART-A", dates.get(0), orgnCode, repOpt, "Y", osBlancelist, "Y");
                        break;
                    }
                }
            }
            //Now calculation of formula rows
            for (int k = 0; k < rbiPojoTable.size(); k++) {
                RbiSossPojo fPojo = rbiPojoTable.get(k);
                if (!fPojo.getFormula().isEmpty()) {
                    if (fPojo.getFormula().contains("XBRLOSS1")) {
                        String[] strArr = fPojo.getFormula().split("@");
                        BigDecimal bal = rbiReportFacade.getValueFromFormula(oss1List, strArr[1]);
                        fPojo.setAmt(bal);
                    } else if (fPojo.getFormula().contains("OSS1")) {
                        String[] sb = fPojo.getFormula().split("~");
                        String[] st = sb[1].split("@");
                        BigDecimal bal = rbiReportFacade.getValueFromFormula(oss1List, st[1]);
                        String newFormula = sb[0].concat(bal.toString()).concat(sb[2]);
                        fPojo.setAmt(rbiReportFacade.getValueFromFormula(rbiPojoTable, newFormula));
                    } else if (fPojo.getFormula().contains("OSS7")) {
                        String[] strArr = fPojo.getFormula().split("#");
                        BigDecimal bal = rbiReportFacade.getValueFromFormula(oss7PartAList, strArr[1]);
                        fPojo.setAmt(bal);
                    } else {
                        fPojo.setAmt(rbiReportFacade.getValueFromFormula(rbiPojoTable, fPojo.getFormula()));
                    }
                }
            }
            /*II-Part Calculation*/
//            System.out.println("Start Time NPA Rec:" + new Date());
            Object[] arr = getQuarterEndDtForCurDt(orgnCode, reportDt);
            for (int j = 0; j < arr.length; j++) {
                BigDecimal fAmt = new BigDecimal("0");
                BigDecimal sAmt = new BigDecimal("0");
                BigDecimal tAmt = new BigDecimal("0");
                BigDecimal lAmt = new BigDecimal("0");
                RbiSos4Pojo qPojo = new RbiSos4Pojo();

                String qEndDt = arr[j].toString();
                qPojo.setfGNo(dmy.format(ymd.parse(qEndDt))); // Setting Quarter Date

                String startDt = "", endDt = "";
                String calDt = ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(CbsUtil.monthAdd(qEndDt, -3))));
                startDt = CbsUtil.dateAdd(calDt, 1);
                endDt = qEndDt;
                /*Before Quarter Date: NPA Balance*/
                List<NpaStatusReportPojo> resultListBegin = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", calDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                if (!resultListBegin.isEmpty()) {
                    for (int o = 0; o < resultListBegin.size(); o++) {
                        fAmt = fAmt.add(resultListBegin.get(o).getBalance());
                    }
                }
                /*New addition during Quarter start date and end date*/
                List<NpaStatusReportPojo> resultListDuring = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", startDt, qEndDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                if (!resultListDuring.isEmpty()) {
                    for (int o = 0; o < resultListDuring.size(); o++) {
                        sAmt = sAmt.add(resultListDuring.get(o).getBalance());
                    }
                }
                /*End of the Quarter Date: NPA Balance*/
                List<NpaStatusReportPojo> resultListEnd = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", qEndDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                if (!resultListEnd.isEmpty()) {
                    for (int o = 0; o < resultListEnd.size(); o++) {
                        lAmt = lAmt.add(resultListEnd.get(o).getBalance());
                    }
                }

//                            else if (glHeadFrom.equalsIgnoreCase("DURINGREC")) {
////                                System.out.println("DURINGREC:" + startDt + ">>>" + endDt + ">>>" + new Date());
//                                List<NpaRecoveryPojo> resultList = npaRemote.getNpaRecoveryReportData("ALL", startDt, endDt, orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode);
//                                rowAmount = getNpaAmountRecoveryDuringQuarters(acNature, acType, startDt, endDt, orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, resultList);
//                                sAmt = sAmt.add(rowAmount);
//                            }
//                            else if (glHeadFrom.equalsIgnoreCase("DURINGNPA")) {
//                                rowAmount = getNewNpaAccountDuringPeriod(acNature, acType, startDt, endDt, orgnCode.equalsIgnoreCase("90")?"0A":orgnCode);
//                                tAmt = tAmt.add(rowAmount);
//                            }
                qPojo.setnAmount(fAmt.abs().divide(repOpt));//Beforee Quarter Start Date
                qPojo.setFourthAmount(sAmt.abs().divide(repOpt));//New NPA addition during Quarter
                qPojo.setTrdAmount(((fAmt.abs().add(sAmt.abs()).subtract(lAmt.abs())).compareTo(new BigDecimal("0")) <= 0) ? new BigDecimal(0) : (fAmt.abs().add(sAmt.abs()).subtract(lAmt.abs())).divide(repOpt));
                qPojo.setTotAmount(lAmt.abs().divide(repOpt));//Quaarter End NPA Bal
                s4List.add(qPojo);
            }
//            System.out.println("End Time NPA Rec:" + new Date());
            Oss4PartACombinedPojo mainPojo = new Oss4PartACombinedPojo();
            mainPojo.setRbiPojoTable(rbiPojoTable);
            mainPojo.setS4List(s4List);
            mainList.add(mainPojo);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return mainList;
    }

    public List<RbiSos4Pojo> getSOS4PartE(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<RbiSos4Pojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION, REFER_INDEX, REFER_CONTENT  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'OSS4-PART-E' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME,CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            String reportBase = "S";
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                List reportParameter = em.createNativeQuery("select code from cbs_parameterinfo where Name= 'SANCTION_AMT_APPLY_DT' ").getResultList();
                if (!reportParameter.isEmpty()) {
                    Vector vect = (Vector) reportParameter.get(0);
                    String reportBaseDt = vect.get(0).toString();
                    if (ymd.parse(reportDt).after(ymd.parse(reportBaseDt))) {
                        reportBase = "O";
                    }
                }
                for (int i = 0; i < oss1Query.size(); i++) {
                    BigDecimal calculatedAmount = new BigDecimal("0");

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
                    String glHeadFrom = oss1Vect.get(12).toString();            //GlHead From
                    String glHeadTo = oss1Vect.get(13).toString();              //GlHead To
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();                  // 1- Priority, 2- Non Priority
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString();     //SubSector
//                    String referIndex = oss1Vect.get(24).toString();

//                    List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
//                    List<LoanMisCellaniousPojo> resultList1 = new ArrayList<LoanMisCellaniousPojo>();
//                    resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature, acType, reportDt, "R", 0, 0,"ALL" , "PRIOR", "0", "0", "0", "0", "0", "0", "0", "0", fSssGNo, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ALL");
//                    resultList1 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature, acType, reportDt, "R", 0, 0,"ALL" , "NONPR", "0", "0", "0", "0", "0", "0", "0", "0", fSssGNo, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ALL");
                    if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                        List<LoanMisCellaniousPojo> resultList2 = new ArrayList<LoanMisCellaniousPojo>();
                        resultList2 = loanReportFacade.cbsLoanMisReport(orgnCode,
                                acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), reportBase,
                                fGNo.equalsIgnoreCase("") ? "0" : fGNo, npaClassification.equalsIgnoreCase("") ? "0" : npaClassification,
                                "0", "0", "0", "0", "0", "0", fSGNo.equalsIgnoreCase("") ? "0" : fSGNo,
                                fSsGNo.equalsIgnoreCase("") ? "0" : fSsGNo, fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo,
                                "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ALL", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");

                        for (int k = 0; k < resultList2.size(); k++) {
                            LoanMisCellaniousPojo val1 = resultList2.get(k);
                            if (resultList2.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                calculatedAmount = calculatedAmount.add(resultList2.get(k).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultList2.get(k).getOutstanding() : new BigDecimal("0"));
                            } else {
                                calculatedAmount = calculatedAmount.add(resultList2.get(k).getOutstanding());
                            }
//                            BigDecimal a = val1.getOutstanding();
//                            calculatedAmount = calculatedAmount.add(a);
                        }

                    }

//                    if (!acNature.equals("")) {                       
//                            if(fGNo.contains("PRIOR") ){
//                                for( int k= 0; k< resultList.size(); k++){
////                                    if(fSssGNo.contains("FB") && (fSsssGNo.contains("INDI") || fSsssGNo.contains("MKTM") ||fSsssGNo.contains("ABASS"))){
//                                        LoanMisCellaniousPojo val1= resultList.get(k);
//                                        BigDecimal a= val1.getOutstanding();
//                                        calculatedAmount = calculatedAmount.add(a); 
////                                    }
//                                }
//                            } else if(fGNo.contains("NONPR")){
//                                for( int k= 0; k< resultList1.size(); k++){
////                                    if(fSssGNo.contains("FB") && (fSsssGNo.contains("INDI") || fSsssGNo.contains("MKTM") ||fSsssGNo.contains("ABASS"))){
//                                        LoanMisCellaniousPojo val1= resultList1.get(k);
//                                        BigDecimal a= val1.getOutstanding();
//                                        calculatedAmount = calculatedAmount.add(a); 
////                                    }
//                                }
//                            } else if((!(fGNo.contains("NONPR"))) ||(!(fGNo.contains("PRIOR")))){
//                                for( int k= 0; k< resultList2.size(); k++){
////                                    if(fSssGNo.contains("FB") && (fSsssGNo.contains("INDI") || fSsssGNo.contains("MKTM") ||fSsssGNo.contains("ABASS"))){
//                                        LoanMisCellaniousPojo val1= resultList2.get(k);
//                                        BigDecimal a= val1.getOutstanding();
//                                        calculatedAmount = calculatedAmount.add(a); 
////                                    }
//                                }
//                            }
//                 
////                        AdditionalStmtPojo obj = new AdditionalStmtPojo();
////                        obj.setClassification(classification);
////                        obj.setBrnCode(orgnCode);
////                        obj.setDate(reportDt);
////                        obj.setGlFromHead(glHeadFrom);
////                        obj.setGlToHead(glHeadTo);
////                        obj.setFlag(fSGNo);
////
////                        List<GlHeadPojo> responseList = getGLHeadAndBal(obj);
////                        for (GlHeadPojo glPojo : responseList) {
////                            calculatedAmount = calculatedAmount.add(glPojo.getBalance());
////                        }
//                    } else if (!(acNature.equals("") && acType.equals(""))) {
//                        AdditionalStmtPojo params = new AdditionalStmtPojo();
//                        params.setNature(acNature);
//                        params.setAcType(acType);
//                        params.setBrnCode(orgnCode);
//                        params.setDate(reportDt);
//                        params.setToDate(reportDt);
//                        params.setGlFromHead(fGNo);
//                        params.setGlToHead(npaClassification);
//                        params.setOrgBrCode("Oss4PartD");
//                        params.setClassification(classification);
//                        params.setFlag(fSGNo);
//                        if (rangeBaseOn == null || rangeBaseOn.equalsIgnoreCase("")) {
//                            AcctBalPojo pojo = getAcctsAndBalD(params);
//                            calculatedAmount = calculatedAmount.add(pojo.getBalance());
//                        } else {
//                            params.setFromRange(rangeFrom);
//                            params.setToRange(rangeTo);
//
//                            AcctBalPojo pojo = getAcctsAndBalAmtRangeWiseD(params);
//                            calculatedAmount = calculatedAmount.add(pojo.getBalance());
//                        }
//                    }

                    RbiSos4Pojo pojo = new RbiSos4Pojo();
                    pojo.setsNo(sNo);
                    pojo.setReportName(reportName);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setGlheadFrom(glHeadFrom);
                    pojo.setGlHeadTo(glHeadTo);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setTotAmount(calculatedAmount.abs().divide(repOpt));
                    pojo.setReferIndex("E");

                    rbiPojoTable.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<RbiSos4Pojo> getSOS4PartF(String reportDt, String orgnCode, BigDecimal repOpt, String summaryOpt) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<RbiSos4Pojo>();
        List<String> dates = new ArrayList<String>();
        dates.add(reportDt);
        List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'OSS4-PART-F' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME,CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            List chk1 = em.createNativeQuery("SELECT  distinct(FORMULA) FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'OSS4-PART-F' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME,CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            List<RbiSossPojo> oss7PartAList = new ArrayList<RbiSossPojo>();
            if (!chk1.isEmpty()) {
                for (int i = 0; i < chk1.size(); i++) {
                    Vector vect = (Vector) chk1.get(i);
                    String chk = vect.get(0).toString();
                    if (chk.contains("XBRLOSS7")) {
                        oss7PartAList = rbiQuaterly.getOss7PartA("XBRLOSS7-PART-A", reportDt, orgnCode, new BigDecimal("1"), "Y", osBlancelist, "Y");
                        break;
                    } else if (chk.contains("OSS7")) {
                        oss7PartAList = rbiQuaterly.getOss7PartA("OSS7-PART-A", reportDt, orgnCode, new BigDecimal("1"), "Y", osBlancelist, "Y");
                        break;
                    }
                }
            }
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                String preFormula = "", npaAcDetails = "";
                for (int i = 0; i < oss1Query.size(); i++) {
                    BigDecimal calculatedAmount = new BigDecimal("0");

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
                    String glHeadFrom = oss1Vect.get(12).toString();            //GlHead From
                    String glHeadTo = oss1Vect.get(13).toString();              //GlHead To
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();                  // 1- Priority, 2- Non Priority
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString();     //SubSector
                    RbiSos4Pojo rbiSossPojo = new RbiSos4Pojo();
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setTotAmount(new BigDecimal("0.00"));
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

                    if (!glHeadFrom.equals("") && !glHeadTo.equals("")) {
                        AdditionalStmtPojo obj = new AdditionalStmtPojo();
                        obj.setClassification(classification);
                        obj.setBrnCode(orgnCode);
                        obj.setDate(reportDt);
                        obj.setGlFromHead(glHeadFrom);
                        obj.setGlToHead(glHeadTo);
                        obj.setFlag(fSGNo);

                        List<GlHeadPojo> responseList = getGLHeadAndBal(obj);
//                        for (GlHeadPojo glPojo : responseList) {
//                            calculatedAmount = calculatedAmount.add(glPojo.getBalance());
//                        }
                        /*Setting into main list*/
                        for (int z = 0; z < responseList.size(); z++) {
                            rbiSossPojo = new RbiSos4Pojo();
                            GlHeadPojo glHeadPo = responseList.get(z);
                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                    noOfAc = (long) glPojoList.size();
                                rbiSossPojo.setTotAmount(glHeadPo.getBalance());
                            } else {
                                calculatedAmount = new BigDecimal(formatter.format(glHeadPo.getBalance().divide(repOpt).doubleValue()));
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo.setTotAmount(calculatedAmount);
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    rbiSossPojo.setTotAmount(calculatedAmount.multiply(new BigDecimal("-1")));
                                } else {
                                    rbiSossPojo.setTotAmount((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? calculatedAmount : calculatedAmount.abs());
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
                            if (!(gNo.equals(2) && sGNo.equals(5))) {
                                rbiSossPojo.setTotAmount(calculatedAmount.divide(repOpt).abs());
                            } else {
                                rbiSossPojo.setTotAmount(calculatedAmount.abs());
                            }
                            rbiSossPojo.setReferIndex("F");
                            rbiPojoTable.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
//                                preZ = z;
                        }
                    } else if (!(acNature.equals("") && acType.equals(""))) {
                        AdditionalStmtPojo params = new AdditionalStmtPojo();
                        params.setNature(acNature);
                        params.setAcType(acType);
                        params.setBrnCode(orgnCode);
                        params.setDate(reportDt);
                        params.setToDate(reportDt);
                        params.setGlFromHead(fGNo);
                        params.setGlToHead(npaClassification);
                        params.setOrgBrCode("Oss4PartD");
                        params.setClassification(classification);
                        params.setFlag(fSGNo);
                        if (rangeBaseOn == null || rangeBaseOn.equalsIgnoreCase("")) {
                            AcctBalPojo pojo = getAcctsAndBalD(params);
                            calculatedAmount = calculatedAmount.add(pojo.getBalance());
                        } else {
                            params.setFromRange(rangeFrom);
                            params.setToRange(rangeTo);

                            AcctBalPojo pojo = getAcctsAndBalAmtRangeWiseD(params);
                            calculatedAmount = calculatedAmount.add(pojo.getBalance());
                        }
                    } else {
                        if (!formula.isEmpty()) {
                            if (formula.equalsIgnoreCase("+P&L") || formula.equalsIgnoreCase("-P&L")) {
                                double balPL = glReport.IncomeExp(reportDt, "0A", "0A");
                                calculatedAmount = new BigDecimal(balPL, MathContext.UNLIMITED);
                            } else if (formula.contains("OSS7")) {
                                String[] strArr = formula.split("#");
                                calculatedAmount = calculatedAmount.add(rbiReportRemote.getValueFromFormula(oss7PartAList, strArr[1]));
                            }
                        } else if (npaClassification.equalsIgnoreCase("NDTL")) {
                            Vector ele = null;
                            reportDt = rbiReportFacade.getMinFinYear(reportDt);
                            List reportList2 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + reportDt + "'").getResultList();
                            if (reportList2.isEmpty()) {
                                throw new ApplicationException("Last Reporting Friday Date does not defined...");
                            }
                            ele = (Vector) reportList2.get(0);
                            String repFriDate = ele.get(0).toString();
                            repFriDate = CbsUtil.dateAdd(repFriDate, -14);
                            calculatedAmount = hoRemote.getNewNdtl("0A", repFriDate);
                        }
                        RbiSos4Pojo pojo = new RbiSos4Pojo();
                        pojo.setsNo(sNo);
                        pojo.setReportName(reportName);
                        pojo.setDescription(description);
                        pojo.setgNo(gNo);
                        pojo.setsGNo(sGNo);
                        pojo.setSsGNo(ssGNo);
                        pojo.setSssGNo(sssGNo);
                        pojo.setSsssGNo(ssssGNo);
                        pojo.setClassification(classification);
                        pojo.setCountApplicable(countApplicable);
                        pojo.setAcNature(acNature);
                        pojo.setAcType(acType);
                        pojo.setGlheadFrom(glHeadFrom);
                        pojo.setGlHeadTo(glHeadTo);
                        pojo.setRangeBaseOn(rangeBaseOn);
                        pojo.setRangeFrom(rangeFrom);
                        pojo.setRangeTo(rangeTo);
                        pojo.setFormula(formula);
                        pojo.setfGNo(fGNo);
                        pojo.setfSGNo(fSGNo);
                        pojo.setfSsGNo(fSsGNo);
                        pojo.setfSssGNo(fSssGNo);
                        pojo.setfSsssGNo(fSsssGNo);
                        pojo.setNpaClassification(npaClassification);
                        if (!(gNo.equals(2) && sGNo.equals(5))) {
                            pojo.setTotAmount(calculatedAmount.divide(repOpt).abs());
                        } else {
                            pojo.setTotAmount(calculatedAmount.abs());
                        }
                        pojo.setReferIndex("F");

                        rbiPojoTable.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<TopLoanPojo> getSoss4PartB4(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<TopLoanPojo> resultList = new ArrayList<TopLoanPojo>();
        BigDecimal capital = new BigDecimal("0");
        BigDecimal advance = new BigDecimal("0");
        BigDecimal sanctionLimit = new BigDecimal("0");
        BigDecimal fGno = new BigDecimal("0");
        Integer imparedCount = 0;
        BigDecimal totalNpa = new BigDecimal("0");
        List<NpaStatusReportPojo> npaResultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", reportDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
//        for (int n = 0; n < npaResultList.size(); n++) {
//            totalNpa = totalNpa.add(npaResultList.get(n).getBalance());
//        }
        List<String> dates = new ArrayList<String>();
        dates.add(reportDt);
        List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
        try {
            List dataList = em.createNativeQuery("select sno, report_name, description,ac_nature, ac_type, gl_head_from, "
                    + "gl_head_to,f_gno,classification from cbs_ho_rbi_stmt_report where report_name = 'OSS4-PART-B-4' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
//                System.out.println("Start Time PART B-4:" + new Date());
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String description = element.get(2).toString().trim();
                    String nature = element.get(3).toString().trim();
                    String acType = element.get(4).toString().trim();
                    String glFrom = element.get(5).toString().trim();
                    String glTo = element.get(6).toString().trim();
                    if (!element.get(7).toString().trim().equalsIgnoreCase("")) {
                        fGno = new BigDecimal(element.get(7).toString().trim());
                    } else {
                        fGno = new BigDecimal("0");
                    }
                    String classification = element.get(8).toString().trim();
                    AdditionalStmtPojo obj = new AdditionalStmtPojo();
                    obj.setClassification(classification);
                    obj.setBrnCode(orgnCode);
                    obj.setDate(reportDt);

                    if (description.equalsIgnoreCase("IMPARED-COUNT")) {
                        imparedCount = fGno.intValue();
                    } else if (description.equalsIgnoreCase("IMPARED-SANCTION-LIMIT")) {
                        sanctionLimit = fGno;
                    } else if (description.equalsIgnoreCase("CAPITAL-FUND")) {
                        GlHeadPojo glHeadPojo = new GlHeadPojo();
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glFrom + "' and '" + glTo + "' group "
                                + "by substring(acno,3,8)").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
                            RbiSossPojo rbiSossPojo = new RbiSossPojo();

                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                            if (newBalPojo == null) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                capital = capital.add(new BigDecimal(0.00));
                            } else {
                                capital = capital.add(newBalPojo.getBalance());
                            }
                        }
//                        obj.setGlFromHead(glFrom);
//                        obj.setGlToHead(glTo);
//
//                        List<GlHeadPojo> responseList = getGLHeadAndBal(obj);
//                        for (GlHeadPojo glPojo : responseList) {
//                            capital = capital.add(glPojo.getBalance());
//                        }
                    } else if (description.equalsIgnoreCase("TOTAL-ADVANCE")) {
                        if (!glFrom.equals("") && !glTo.equals("")) {
                            GlHeadPojo glHeadPojo = new GlHeadPojo();
                            List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                    + "substring(acno,3,8) between '" + glFrom + "' and '" + glTo + "' group "
                                    + "by substring(acno,3,8)").getResultList();
                            for (int n = 0; n < glNameList.size(); n++) {
                                Vector vector = (Vector) glNameList.get(n);
                                GlHeadPojo glPojo = new GlHeadPojo();
                                RbiSossPojo rbiSossPojo = new RbiSossPojo();

                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                                if (newBalPojo == null) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                    advance = advance.add(new BigDecimal(0.00));
                                } else {
                                    advance = advance.add(newBalPojo.getBalance());
                                }
                            }
//                            obj.setGlFromHead(glFrom);
//                            obj.setGlToHead(glTo);
//
//                            List<GlHeadPojo> responseList = getGLHeadAndBal(obj);
//                            for (GlHeadPojo glPojo : responseList) {
//                                advance = advance.add(glPojo.getBalance());
//                            }
                        } else if (!(nature.equals("") && acType.equals(""))) {
                            List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
//                                if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                            String acTypeQuery = "";
                            List natureList = null;
                            if (!((nature == null) || nature.equalsIgnoreCase(""))) {
                                acTypeQuery = ("select acctcode,acctdesc from accounttypemaster where "
                                        + "acctnature in ('" + nature + "')");
                            }
                            if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                acTypeQuery = (!acTypeQuery.equalsIgnoreCase("")
                                        ? acTypeQuery.concat(" union all select acctcode,acctdesc from accounttypemaster where acctcode in ('" + acType + "')")
                                        : "select acctcode,acctdesc from accounttypemaster where acctcode in ('" + acType + "')");
                            }
                            natureList = em.createNativeQuery(acTypeQuery).getResultList();
                            /*Setting into main list*/
                            if (!natureList.isEmpty()) {
                                for (int z = 0; z < natureList.size(); z++) {
                                    Vector vector = (Vector) natureList.get(z);
                                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                                    if (newBalPojo == null) {
                                        advance = advance.add(new BigDecimal(0.00));
                                    } else {
                                        advance = advance.add(newBalPojo.getBalance());
                                    }
                                }
                            }
//                            if (!nature.equals("")) {
//                                obj.setNature(nature);
//                                obj.setAcType("");
//                                obj.setOrgBrCode("");
//                                AcctBalPojo pojo = getAcctsAndBalD(obj);                 //This is A classification (-)
//                                advance = advance.add(pojo.getBalance());
//
//                                obj.setClassification("L");
//                                AcctBalPojo plusAmountPojo = getAcctsAndBalD(obj);       //This is L classification (+)
//                                advance = advance.add(plusAmountPojo.getBalance());
//
//                                obj.setClassification("A");
//                                obj.setNature("NPA." + nature);
//                                AcctBalPojo npaPojo = getAcctsAndBalD(obj);              //This is for NPA(- Amount)
//                                advance = advance.add(npaPojo.getBalance());
//                            } else if (!acType.equals("")) {
//                                obj.setNature("");
//                                obj.setAcType(acType);
//                                obj.setOrgBrCode("");
//                                AcctBalPojo pojo = getAcctsAndBalD(obj);                 //This is A classification (-)
//                                advance = advance.add(pojo.getBalance());
//
//                                obj.setClassification("L");
//                                AcctBalPojo plusAmountPojo = getAcctsAndBalD(obj);       //This is L classification (+)
//                                advance = advance.add(plusAmountPojo.getBalance());
//
//                                obj.setClassification("A");
//                                obj.setAcType("NPA." + acType);
//                                AcctBalPojo npaPojo = getAcctsAndBalD(obj);              //This is for NPA(- Amount)
//                                advance = advance.add(npaPojo.getBalance());
//                            }
                        }
                    }
                }
                //Now picking the impared accounts as well as sanction accounts.Now combined datalist will return.
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String description = element.get(2).toString().trim();
                    String nature = element.get(3).toString().trim();
                    String acType = element.get(4).toString().trim();

                    List<GlHeadPojo> glHeadPojoList = new ArrayList<GlHeadPojo>();
                    if (description.equalsIgnoreCase("IMPARED-ACCOUNT")) {
                        String fromDt = CbsUtil.dateAdd(reportDt, -89);
                        glHeadPojoList = getOverDueAccountInBetweenDateAndTheirOutStandingBal(fromDt, reportDt, nature, acType, "IMPARED", npaResultList);
                    } else if (description.equalsIgnoreCase("SANCTION-ACCOUNT")) {
                        glHeadPojoList = accountHavingSanctionAsPerAmount(reportDt, nature, acType, sanctionLimit.doubleValue(), "SANCTIONED");
                    }
                    if (!glHeadPojoList.isEmpty()) {
                        for (int k = 0; k < glHeadPojoList.size(); k++) {
                            GlHeadPojo cPojo = glHeadPojoList.get(k);
                            TopLoanPojo finalPojo = new TopLoanPojo();
                            finalPojo.setSno(k);
                            finalPojo.setAccountNo(cPojo.getGlHead());
                            finalPojo.setBorrowerName(cPojo.getGlName());
                            BigDecimal bal = cPojo.getBalance();
                            finalPojo.setOutStanding(bal);
                            List subList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no=183 and "
                                    + "ref_code in (select sub_sector from cbs_loan_borrower_details "
                                    + "where acc_no='" + cPojo.getGlHead() + "')").getResultList();
                            if (!subList.isEmpty()) {
                                Vector subVec = (Vector) subList.get(0);
                                finalPojo.setSubSector(subVec.get(0).toString());
                            } else {
                                finalPojo.setSubSector("");
                            }

                            finalPojo.setFunded(new BigDecimal("0"));
                            finalPojo.setNonFunded(new BigDecimal("0"));
                            finalPojo.setPercToAdvance((bal.multiply(new BigDecimal("100"))).divide(advance, BigDecimal.ROUND_HALF_UP));
                            finalPojo.setCapitalFund(capital);
                            if (capital.compareTo(BigDecimal.ZERO) != 0) {
                                finalPojo.setPercToCapital((bal.multiply(new BigDecimal("100"))).divide(capital, BigDecimal.ROUND_HALF_UP));
                            } else {
                                finalPojo.setPercToCapital(new BigDecimal("0"));
                            }
                            finalPojo.setRiskClassification(cPojo.getGlClassification());
                            finalPojo.setDataClass(cPojo.getRemarks());
                            finalPojo.setRowCount(imparedCount.toString());

                            resultList.add(finalPojo);
                        }
                    }
                }
//                System.out.println("End Time PART B-4:" + new Date());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public List<TopLoanPojo> getSoss4PartC1(String reportDt, String orgnCode, BigDecimal repOpt, String borrowerType, List osBlancelist) throws ApplicationException {
        List<TopLoanPojo> resultList = new ArrayList<TopLoanPojo>();
        List<TopLoanPojo> tmpList = new ArrayList<TopLoanPojo>();
        BigDecimal capital = new BigDecimal("0");
        BigDecimal advance = new BigDecimal("0");
        Double excessPerc = 0.0;
        Integer minRowCount = 0;
        List<String> dates = new ArrayList<String>();
        dates.add(reportDt);
        List<NpaStatusReportPojo> npaResultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", reportDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
        try {
            List dataList = em.createNativeQuery("select sno, report_name, description,ac_nature, ac_type, gl_head_from, "
                    + "gl_head_to,f_gno,classification,formula from cbs_ho_rbi_stmt_report where report_name = 'OSS4-PART-C-1' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
//                System.out.println("Start Time PART C-1:" + new Date());
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String description = element.get(2).toString().trim();
                    String nature = element.get(3).toString().trim();
                    String acType = element.get(4).toString().trim();
                    String glFrom = element.get(5).toString().trim();
                    String glTo = element.get(6).toString().trim();
                    String fGno = element.get(7).toString().trim();
                    String classification = element.get(8).toString().trim();
                    String formula = element.get(9).toString();

                    AdditionalStmtPojo obj = new AdditionalStmtPojo();
                    obj.setClassification(classification);
                    obj.setBrnCode(orgnCode);
                    obj.setDate(reportDt);
                    obj.setOrgBrCode("");

                    if (description.equalsIgnoreCase("EXCESS-PERCENT")) {
                        excessPerc = Double.parseDouble(fGno);
                    } else if (description.equalsIgnoreCase("MIN-ROW-COUNT")) {
                        minRowCount = Integer.parseInt(fGno);
                    } else if (description.equalsIgnoreCase("CAPITAL-FUND")) {
                        if (!formula.isEmpty() && formula.contains("OSS7")) {
                            List<RbiSossPojo> oss7PartAList = rbiQuaterly.getOss7PartA("OSS7-PART-A", reportDt, orgnCode, new BigDecimal("1"), "Y", osBlancelist, "Y");
                            String[] strArr = formula.split("#");
                            capital = capital.add(rbiReportRemote.getValueFromFormula(oss7PartAList, strArr[1]));
                        } else {
                            GlHeadPojo glHeadPojo = new GlHeadPojo();
                            List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                    + "substring(acno,3,8) between '" + glFrom + "' and '" + glTo + "' group "
                                    + "by substring(acno,3,8)").getResultList();
                            for (int n = 0; n < glNameList.size(); n++) {
                                Vector vector = (Vector) glNameList.get(n);
                                GlHeadPojo glPojo = new GlHeadPojo();
                                RbiSossPojo rbiSossPojo = new RbiSossPojo();

                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                                if (newBalPojo == null) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                                    capital = capital.add(new BigDecimal(0.00));
                                } else {
                                    capital = capital.add(newBalPojo.getBalance());
                                }
                            }

//                            obj.setGlFromHead(glFrom);
//                            obj.setGlToHead(glTo);
//
//                            List<GlHeadPojo> responseList = getGLHeadAndBal(obj);
//                            for (GlHeadPojo glPojo : responseList) {
//                                capital = capital.add(glPojo.getBalance());
//                            }
                        }
                    } else if (description.equalsIgnoreCase("TOTAL-ADVANCE")) {
                        if (!glFrom.equals("") && !glTo.equals("")) {
                            GlHeadPojo glHeadPojo = new GlHeadPojo();
                            List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                    + "substring(acno,3,8) between '" + glFrom + "' and '" + glTo + "' group "
                                    + "by substring(acno,3,8)").getResultList();
                            for (int n = 0; n < glNameList.size(); n++) {
                                Vector vector = (Vector) glNameList.get(n);
                                GlHeadPojo glPojo = new GlHeadPojo();
                                RbiSossPojo rbiSossPojo = new RbiSossPojo();

                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                                if (newBalPojo == null) {
                                    advance = advance.add(new BigDecimal(0.00));
                                } else {
                                    advance = advance.add(newBalPojo.getBalance());
                                }
                            }
//                            obj.setGlFromHead(glFrom);
//                            obj.setGlToHead(glTo);
//
//                            List<GlHeadPojo> responseList = getGLHeadAndBal(obj);
//                            for (GlHeadPojo glPojo : responseList) {
//                                advance = advance.add(glPojo.getBalance());
//                            }
                        } else if (!(nature.equals("") && acType.equals(""))) {
//                            if (!nature.equals("")) {
                            List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
//                                if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                            String acTypeQuery = "";
                            List natureList = null;
                            if (!((nature == null) || nature.equalsIgnoreCase(""))) {
                                acTypeQuery = ("select acctcode,acctdesc from accounttypemaster where "
                                        + "acctnature in ('" + nature + "')");
                            }
                            if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                acTypeQuery = (!acTypeQuery.equalsIgnoreCase("")
                                        ? acTypeQuery.concat(" union all select acctcode,acctdesc from accounttypemaster where acctcode in ('" + acType + "')")
                                        : "select acctcode,acctdesc from accounttypemaster where acctcode in ('" + acType + "')");
                            }
                            natureList = em.createNativeQuery(acTypeQuery).getResultList();
                            /*Setting into main list*/
                            if (!natureList.isEmpty()) {
                                for (int z = 0; z < natureList.size(); z++) {
                                    Vector vector = (Vector) natureList.get(z);
                                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                                    if (newBalPojo == null) {
                                        advance = advance.add(new BigDecimal(0.00));
                                    } else {
                                        advance = advance.add(newBalPojo.getBalance());
                                    }
                                }
                            }
//                            obj.setNature(nature);
//                            obj.setAcType("");
//                            AcctBalPojo pojo = getAcctsAndBalD(obj);                     //This is A classification (-)
//                            advance = advance.add(pojo.getBalance());
//
//                            obj.setClassification("L");
//                            AcctBalPojo plusAmountPojo = getAcctsAndBalD(obj);           //This is L classification (+)
//                            advance = advance.add(plusAmountPojo.getBalance());
//
//                            obj.setClassification("A");
//                            obj.setNature("NPA." + nature);
//                            AcctBalPojo npaPojo = getAcctsAndBalD(obj);                  //This is for NPA(- Amount)
//                            advance = advance.add(npaPojo.getBalance());
//                            } else if (!acType.equals("")) {
//                                obj.setNature("");
//                                obj.setAcType(acType);
//                                AcctBalPojo pojo = getAcctsAndBalD(obj);                     //This is A classification (-)
//                                advance = advance.add(pojo.getBalance());
//
//                                obj.setClassification("L");
//                                AcctBalPojo plusAmountPojo = getAcctsAndBalD(obj);           //This is L classification (+)
//                                advance = advance.add(plusAmountPojo.getBalance());
//
//                                obj.setClassification("A");
//                                obj.setAcType("NPA." + acType);
//                                AcctBalPojo npaPojo = getAcctsAndBalD(obj);                  //This is for NPA(- Amount)
//                                advance = advance.add(npaPojo.getBalance());
//                            }
                        }
                    }
                }
//                System.out.println("Top Borrower>>>>>+++:"+new Date());
                //Now picking Loan Accounts With their Outstanding as wel as Sanctionlimit and Asset Classification.
                List<TopDepositorBorrowerPojo> reportList = new ArrayList<TopDepositorBorrowerPojo>();
                reportList = miscReportFacade.getTopDepositorBorrower("0A", "Sanction Wise", "19000101", reportDt, minRowCount, "Customer Id", "Bank", borrowerType, 0d, 99999999999999d);
                for (int j = 0; j < dataList.size(); j++) {
                    for (int i = 0; i < reportList.size(); i++) {
                        if (!dataList.isEmpty()) {
                            TopDepositorBorrowerPojo val = reportList.get(i);
                            //System.out.println("Cust Id->" + val1.getCustId()+ "Acno ->" + val.getAcNo());
                            String npaStatus = "";
                            Integer custId = Integer.parseInt(val.getCustId());
                            String acNo = val.getAcNo();
                            String acName = val.getDeositorName();
                            BigDecimal outsBal = val.getDepositAmt(); // Setting the Outstanding Bal
                            BigDecimal sanLimit = val.getOutStandingBal().abs(); //Setting the Sanction Limit
                            Vector element = (Vector) dataList.get(j);
                            String description = element.get(2).toString().trim();
                            String nature = element.get(3).toString().trim();
                            String acType = element.get(4).toString().trim();
                            List<GlHeadPojo> glHeadPojoList = new ArrayList<GlHeadPojo>();
                            if (description.equalsIgnoreCase("LOAN-ACCOUNT")) {
//                               AdditionalStmtPojo params = new AdditionalStmtPojo();
//                               params.setAcType(acType);
//                               params.setNature(nature);
//                               params.setDate(reportDt);
//                               params.setToDate(reportDt);
//                               glHeadPojoList = getTotalOutStanding(params);
//                               if (!glHeadPojoList.isEmpty()) {
//                                   for (int k = 0; k < glHeadPojoList.size(); k++) {
                                TopLoanPojo finalPojo = new TopLoanPojo();
//                                        GlHeadPojo cPojo = glHeadPojoList.get(k);
                                finalPojo.setSno(i);
                                finalPojo.setCustId(custId);
                                finalPojo.setAccountNo(custId.toString().concat("\n").concat(acNo));
//                                        BigDecimal outbal = cPojo.getBalance();
                                finalPojo.setBorrowerName(acName);
                                finalPojo.setOutStanding(outsBal.divide(repOpt));
//                                BigDecimal sanLimit = new BigDecimal("0");
//                                String acNat = common.getAcNatureByAcNo(acNo);
//                                List subList = new ArrayList();
//                                if(acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                    subList = em.createNativeQuery("select a.ODLimit,ifnull(a.adhoclimit,0)  from loan_appparameter a where a.acno='" + acNo + "' "
//                                            + " union "
//                                            + " select a.ODLimit,ifnull(a.adhoclimit,0)  from loan_appparameter a where a.acno='" + acNo + "' and '"+reportDt+"' between Adhocapplicabledt and  AdhocExpiry ").getResultList();
//                                } else {
//                                    subList = em.createNativeQuery("select IFNULL(sanctionlimit,0) from loan_appparameter where acno='" + acNo + "'").getResultList();
//                                }                                 
//                                if (!subList.isEmpty()) {
//                                    Vector subVec = (Vector) subList.get(0);
//                                    if(acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                        sanLimit = new BigDecimal(subVec.get(0).toString()).add(new BigDecimal(subVec.get(1).toString()));
//                                    } else {
//                                        sanLimit = new BigDecimal(subVec.get(0).toString());
//                                    }
//                                }
                                finalPojo.setSanctionLimit(sanLimit.divide(repOpt));
                                if (!nature.equals("")) {
                                    finalPojo.setNature(nature);
                                } else if (!acType.equals("")) {
                                    finalPojo.setNature(acType);
                                }
                                finalPojo.setFunded(new BigDecimal("0"));
                                finalPojo.setNonFunded(new BigDecimal("0"));
                                finalPojo.setCapitalFund(capital.divide(repOpt));
                                finalPojo.setPercToAdvance((sanLimit.multiply(new BigDecimal("100"))).divide(advance.abs(), 2, RoundingMode.HALF_UP));
                                if (capital.compareTo(BigDecimal.ZERO) != 0) {
                                    finalPojo.setPercToCapital((sanLimit.multiply(new BigDecimal("100"))).divide(capital, 2, RoundingMode.HALF_UP));
                                } else {
                                    finalPojo.setPercToCapital(new BigDecimal("0"));
                                }
//                                        finalPojo.setRiskClassification(getAccountStatusDescription(Integer.parseInt(cPojo.getRemarks())));
                                for (int npa = 0; npa < npaResultList.size(); npa++) {
                                    String npaAc = npaResultList.get(npa).getAcno();
                                    if (npaAc.equalsIgnoreCase(acNo)) {
                                        npaStatus = npaResultList.get(npa).getPresentStatus();
                                    }
                                }
                                finalPojo.setGroupCustId(val.getGroupId().equalsIgnoreCase("null") ? 0 : Integer.parseInt(val.getGroupId()));
                                finalPojo.setGroupName(val.getGroupName());
                                finalPojo.setRiskClassification(!npaStatus.equalsIgnoreCase("") ? npaStatus : "Standard");
                                if (finalPojo.getPercToCapital().compareTo(new BigDecimal(excessPerc)) >= 0) {
                                    resultList.add(finalPojo);                  //Direct adding the excessPerc Account
                                } else {
                                    tmpList.add(finalPojo);
                                }
//                                   }
//                               }
                            }
                        }
                    }
                }
//                System.out.println("END Time PART C-4:" + new Date());
//               for (int i = 0; i < dataList.size(); i++) {
//                    Vector element = (Vector) dataList.get(i);
//                    String description = element.get(2).toString().trim();
//                    String nature = element.get(3).toString().trim();
//                    String acType = element.get(4).toString().trim();
//
//                    List<GlHeadPojo> glHeadPojoList = new ArrayList<GlHeadPojo>();
//                    if (description.equalsIgnoreCase("LOAN-ACCOUNT")) {
//                        AdditionalStmtPojo params = new AdditionalStmtPojo();
//                        params.setAcType(acType);
//                        params.setNature(nature);
//                        params.setDate(reportDt);
//                        params.setToDate(reportDt);
//
//                        glHeadPojoList = getTotalOutStanding(params);
//
//                        if (!glHeadPojoList.isEmpty()) {
//                            for (int k = 0; k < glHeadPojoList.size(); k++) {
//                                TopLoanPojo finalPojo = new TopLoanPojo();
//
//                                GlHeadPojo cPojo = glHeadPojoList.get(k);
//                                finalPojo.setSno(k);
//                                finalPojo.setAccountNo(cPojo.getGlHead());
//                                finalPojo.setBorrowerName(cPojo.getGlName());
//                                BigDecimal outbal = cPojo.getBalance();
//                                finalPojo.setOutStanding(outbal);
//                                BigDecimal sanLimit = new BigDecimal("0");
//                                List subList = em.createNativeQuery("select IFNULL(sanctionlimit,0) from loan_appparameter where acno='" + cPojo.getGlHead() + "'").getResultList();
//                                if (!subList.isEmpty()) {
//                                    Vector subVec = (Vector) subList.get(0);
//                                    sanLimit = new BigDecimal(subVec.get(0).toString());
//                                }
//                                finalPojo.setSanctionLimit(sanLimit);
//                                if (!nature.equals("")) {
//                                    finalPojo.setNature(nature);
//                                } else if (!acType.equals("")) {
//                                    finalPojo.setNature(acType);
//                                }
//                                finalPojo.setFunded(new BigDecimal("0"));
//                                finalPojo.setNonFunded(new BigDecimal("0"));
//                                finalPojo.setCapitalFund(capital);
//                                finalPojo.setPercToAdvance((sanLimit.multiply(new BigDecimal("100"))).divide(advance, 2, RoundingMode.HALF_UP));
//                                if (capital.compareTo(BigDecimal.ZERO) != 0) {
//                                    finalPojo.setPercToCapital((sanLimit.multiply(new BigDecimal("100"))).divide(capital, 2, RoundingMode.HALF_UP));
//                                } else {
//                                    finalPojo.setPercToCapital(new BigDecimal("0"));
//                                }
//                                finalPojo.setRiskClassification(getAccountStatusDescription(Integer.parseInt(cPojo.getRemarks())));
//
//                                if (finalPojo.getPercToCapital().compareTo(new BigDecimal(excessPerc)) >= 0) {
//                                    resultList.add(finalPojo);                  //Direct adding the excessPerc Account
//                                } else {
//                                    tmpList.add(finalPojo);
//                                }
//                            }
//                        }
//                    }
//                }
                //Now creating final resultList with min row count account.
                if (!tmpList.isEmpty()) {
                    if (borrowerType.equalsIgnoreCase("1")) {
                        Collections.sort(tmpList, new TopLoanPojoComparatorForOutStanding());
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        Collections.sort(tmpList, new TopLoanPoJoCompareGroupID());
                    }
//                    if (tmpList.size() > minRowCount) {
//                        tmpList = tmpList.subList(0, minRowCount);
//                    }
                    for (TopLoanPojo o1 : tmpList) {
                        resultList.add(o1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public AcctBalPojo getAcctsAndBalD(AdditionalStmtPojo params) throws ApplicationException {
        try {
            int al = 1;
            if (params.getClassification().equals("A")) {
                al = -1;
            }
            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", bd_query = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
            }

            if (params.getNature().contains("NPA") || params.getAcType().contains("NPA")) {
                npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and l.npadt<= '" + params.getDate() + "')";
            } else {
                npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus not in('11','12','13') " + acCodeQuery + ")";
            }

            if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartD")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            }

            List dataList = new ArrayList();
            String executionQuery = "";
            if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno having sign(sum(cramt-dramt)) = " + al + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
                } else {
                    executionQuery = "select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno having sign(sum(cramt-dramt)) = " + al + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery;
                    dataList = em.createNativeQuery(executionQuery).getResultList();
                }
            } else {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno having sign(sum(cramt-dramt)) = " + al + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
                } else {
                    executionQuery = "select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno having sign(sum(cramt-dramt)) = " + al + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery;
                    dataList = em.createNativeQuery(executionQuery).getResultList();
                }
            }
            AcctBalPojo acctBal = new AcctBalPojo();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                acctBal.setNumberOFAcct(Long.parseLong(ele.get(0).toString()));
                acctBal.setBalance(new BigDecimal(ele.get(1).toString()));
            }
            return acctBal;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public AcctBalPojo getAcctsAndBalAmtRangeWiseD(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", bd_query = "";;
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + params.getAcType() + "')";
            }

            if (params.getNature().contains("NPA") || params.getAcType().contains("NPA")) {
                npaQuery = "and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and l.npadt<= '" + params.getDate() + "')";
            } else {
                npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus not in('11','12','13') " + acCodeQuery + ")";
            }

            if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartD")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "'";
            }

            List dataList = new ArrayList();
            String executionQuery = "";
            if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
                } else {
                    executionQuery = "select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery;
                    dataList = em.createNativeQuery(executionQuery).getResultList();
                }
            } else {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "R.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
                } else {
                    executionQuery = "select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery;
                    dataList = em.createNativeQuery(executionQuery).getResultList();
                }
            }

            AcctBalPojo acctBal = new AcctBalPojo();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                acctBal.setNumberOFAcct(Long.parseLong(ele.get(0).toString()));
                acctBal.setBalance(new BigDecimal(ele.get(1).toString()));
            }
            return acctBal;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<GlHeadPojo> getGLHeadAndBal(AdditionalStmtPojo params) throws ApplicationException {
        try {
            int al = 1;
            if (params.getClassification().equals("A")) {
                al = -1;
            }
            List dataList = new ArrayList();
            String executionQuery = "";
            if (params.getBrnCode().equals("0A") || params.getBrnCode().equals("90")) {
                executionQuery = "select  substring(r.acno,3,8), acname,cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                        + "gltable g where r.acno=g.acno and dt <='" + params.getDate() + "' and substring(r.acno,3,8) between '" + params.getGlFromHead()
                        + "' and '" + params.getGlToHead() + "' group by substring(r.acno,3,8),acname having sign(sum(cramt-dramt)) = " + al;

//                System.out.println("Execution Query Is : " + executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            } else {
                executionQuery = "select  substring(r.acno,3,8), acname,cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                        + "gltable g where r.acno=g.acno and dt <='" + params.getDate() + "' and substring(r.acno,3,8) between '" + params.getGlFromHead()
                        + "' and '" + params.getGlToHead() + "' and substring(r.acno,1,2)='" + params.getBrnCode() + "' group by substring(r.acno,3,8),acname "
                        + "having sign(sum(cramt-dramt)) = " + al;

//                System.out.println("Execution Query Is : " + executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            }
            List<GlHeadPojo> pojoList = new ArrayList<GlHeadPojo>();
            GlHeadPojo pojo;
            for (int i = 0; i < dataList.size(); i++) {
                Vector vec = (Vector) dataList.get(i);
                pojo = new GlHeadPojo();
                pojo.setGlHead(vec.get(0).toString());
                pojo.setGlName(vec.get(1).toString());
                pojo.setBalance(new BigDecimal(vec.get(2).toString()));
                pojoList.add(pojo);
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public AcctBalPojo getAmountBasedOnNpaClassification(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npadt = "", tillDt = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
            }
            if (params.getClassification().equalsIgnoreCase("11")) {              //SUB STANDARD
                npadt = " l.npadt ";
            } else if (params.getClassification().equalsIgnoreCase("12")) {       //DOUBTFUL
                npadt = " l.dbtdt ";
            } else if (params.getClassification().equalsIgnoreCase("13")) {       //LOSS
                npadt = " l.dcdt ";
            }
            if (!params.getFromRange().equals("") && !params.getToRange().equals("")) {
                String frDt = "", toDt = "";
                String dtOneYearBack = CbsUtil.yearAdd(params.getDate(), -1);
                String dtThreeYearBack = CbsUtil.yearAdd(params.getDate(), -3);
                if (params.getToRange().equals("1.00")) {
                    frDt = CbsUtil.dateAdd(dtOneYearBack, 1);
                    toDt = params.getDate();
//                    System.out.println("==>>>"+params.getToRange());
                    npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + " between '" + frDt + "' and '" + toDt + "')";
                    dtQuery = " between '" + frDt + "' and '" + toDt + "'";
                    closingQ = " '" + toDt + "' ";
                    tillDt = " <='" + params.getDate() + "' ";
                } else if (params.getToRange().equals("3.00")) {
                    frDt = CbsUtil.dateAdd(dtThreeYearBack, 1);
                    toDt = dtOneYearBack;
//                    System.out.println(">>>>>==>>>"+params.getToRange());
                    npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + " between '" + frDt + "' and '" + toDt + "')";
                    dtQuery = " between '" + frDt + "' and '" + toDt + "'";
                    closingQ = " '" + params.getDate() + "'";
                    tillDt = " <='" + params.getDate() + "' ";
                } else {
                    frDt = dtThreeYearBack;
//                    System.out.println(">>>>>>>>==>>>"+params.getToRange());
                    npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + "<= '" + frDt + "')";
                    dtQuery = " <='" + frDt + "'";
                    closingQ = " '" + params.getDate() + "'";
                    tillDt = " <='" + params.getDate() + "' ";
                }
            } else {
                npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + "<= '" + params.getDate() + "')";
                dtQuery = " <='" + params.getDate() + "'";
                closingQ = " '" + params.getDate() + "'";
                tillDt = " <='" + params.getDate() + "' ";
            }

            List dataList = new ArrayList();
            String executionQuery = "";
            if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                executionQuery = "select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(25,2)) from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " WHERE DT " + tillDt + ") "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > " + closingQ + " ) and r.acno = a.acno and dt " + tillDt + acCodeQuery + npaQuery;

//                System.out.println("Execution-Query :" + executionQuery + "\n");
                dataList = em.createNativeQuery(executionQuery).getResultList();
            } else {
                executionQuery = "select count(distinct a.acno), cast(IFNULL(sum(cramt- dramt),0) as decimal(25,2)) from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " WHERE DT<= '" + params.getDate() + "' ) "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > " + closingQ + " ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt " + tillDt + acCodeQuery + npaQuery;

//                System.out.println("Execution-Query :" + executionQuery + "\n");
                dataList = em.createNativeQuery(executionQuery).getResultList();
            }
            AcctBalPojo acctBal = new AcctBalPojo();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                acctBal.setNumberOFAcct(Long.parseLong(ele.get(0).toString()));
                acctBal.setBalance(new BigDecimal(ele.get(1).toString()));
//                System.out.println(acNature+"-"+params.getAcType()+"=>>>"+params.getToRange()+"; Bal:"+new BigDecimal(ele.get(1).toString())+"; "+"");
            }
            return acctBal;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public AcctBalPojo getAmountBasedOnNpaClassificationForOss4(AdditionalStmtPojo params, List<NpaStatusReportPojo> resultList) throws ApplicationException {
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npadt = "", tillDt = "", acStatus = "", delinqCycle1 = "", flag = "";
            String acNature = params.getNature();
            BigDecimal bal = new BigDecimal("0.00");
            if (!acNature.equals("")) {
//                tableName = common.getTableName(acNature);
                acCodeQuery = " select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbFlag in ('D','B')";
            }
            if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
//                acNature = common.getAcNatureByAcType(acctcode);
//                tableName = common.getTableName(acNature);
                acCodeQuery = (!acCodeQuery.equalsIgnoreCase("")
                        ? acCodeQuery.concat(" union all select acctcode from accounttypemaster where acctcode in ('" + acctcode + "') and crdbFlag in ('D','B')")
                        : " select acctcode from accounttypemaster where acctcode in ('" + acctcode + "') and crdbFlag in ('D','B')");
            }
//            List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
            String delinqCycle = "";
            List acTypeList = em.createNativeQuery(acCodeQuery).getResultList();
            if (!acTypeList.isEmpty()) {
                for (int l = 0; l < acTypeList.size(); l++) {
                    Vector acTypeVect = (Vector) acTypeList.get(l);
                    String acType = acTypeVect.get(0).toString();
                    if (params.getNpaClassification().equalsIgnoreCase("11")) {              //SUB STANDARD
                        acStatus = "SUB STANDARD";
                        delinqCycle = "SUB";
                        //                resultList = npaRemote.getNpaStatusReportData1("1", acCodeQuery, "19000101", params.getDate(), acStatus, params.getBrnCode().equalsIgnoreCase("90")?"0A":params.getBrnCode(), "Y");
                        for (int i = 0; i < resultList.size(); i++) {
                            NpaStatusReportPojo npaStatus = new NpaStatusReportPojo();
                            if (resultList.get(i).getPresentStatus().substring(0, 3).equalsIgnoreCase("SUB") && resultList.get(i).getAcno().substring(2, 4).equalsIgnoreCase(acType)) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                            //                    System.out.println(acNature+"-"+params.getAcType()+"=>>>"+params.getToRange()+"; Bal:"+bal);
                        }
                    } else if (params.getNpaClassification().equalsIgnoreCase("12")) {       //DOUBTFUL
                        acStatus = "DOUBT FUL";
                        delinqCycle = "D";
                        Long daysPastDue = 0l;
                        //                resultList = npaRemote.getNpaStatusReportData1("1", acCodeQuery, "19000101", params.getDate(), acStatus, params.getBrnCode().equalsIgnoreCase("90")?"0A":params.getBrnCode(), "Y");
                        for (int i = 0; i < resultList.size(); i++) {
                            NpaStatusReportPojo npaStatus = new NpaStatusReportPojo();
                            if (resultList.get(i).getPresentStatus().substring(0, 3).equalsIgnoreCase("DOU") && resultList.get(i).getAcno().substring(2, 4).equalsIgnoreCase(acType)) {
                                String schemeCode = loanReportFacade.getSchemeCodeAcNoWise(resultList.get(i).getAcno());
                                Long dayDiff = CbsUtil.dayDiff(ymd.parse(ymd.format(dmy.parse(resultList.get(i).getDoubtFullDt()))), ymd.parse(params.getDate()));
                                if (em.createNativeQuery("select acno from loansecurity where acno = '" + resultList.get(i).getAcno() + "'").getResultList().size() > 0) {
                                    flag = "S";
                                } else {
                                    flag = "U";
                                }
                                List delinquencyCycleList = em.createNativeQuery("select delinq_cycle, days_past_due from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle.concat("%") + "' order by cast(days_past_due as unsigned)").getResultList();
                                if (delinquencyCycleList.size() > 0) {
                                    for (int j = 0; j < delinquencyCycleList.size(); j++) {
                                        Vector delinVect = (Vector) delinquencyCycleList.get(j);
                                        delinqCycle1 = delinVect.get(0).toString();
                                        daysPastDue = Long.parseLong(delinVect.get(1).toString());
                                        //                            System.out.println("???? acno:"+resultList.get(i).getAcno()+" ; delinqCycle:"+delinqCycle+" ; del1:"+delinqCycle1+"; dayspastdue:"+daysPastDue+"; daydiff:"+dayDiff+"; balAcno:"+resultList.get(i).getBalance()+"; Total:"+bal);
                                        if (dayDiff <= daysPastDue) {
                                            delinqCycle1 = delinqCycle1;
                                            if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                                                BigDecimal dayDiff1 = new BigDecimal(dayDiff.toString()).divide(new BigDecimal("365"), 2, RoundingMode.HALF_UP);
                                                if ((dayDiff1.compareTo(new BigDecimal(params.getFromRange())) == 1) && (dayDiff1.compareTo(new BigDecimal(params.getToRange())) != 1)) {
                                                    bal = bal.add(resultList.get(i).getBalance());
                                                    //                                        System.out.println("???? acno:"+resultList.get(i).getAcno()+" ; ToRange:"+params.getToRange()+" ; delinqCycle:"+delinqCycle+" ; del1:"+delinqCycle1+"; dayspastdue:"+daysPastDue+"; daydiff:"+dayDiff+"; daydiff11:"+dayDiff1+"; balAcno:"+resultList.get(i).getBalance()+"; Total:"+bal);
                                                } else {
                                                    bal = bal.add(new BigDecimal("0"));
                                                }
                                            } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                                                BigDecimal dayDiff1 = new BigDecimal(dayDiff.toString()).divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
                                                if ((dayDiff1.compareTo(new BigDecimal(params.getFromRange())) == 1) && (dayDiff1.compareTo(new BigDecimal(params.getToRange())) != 1)) {
                                                    bal = bal.add(resultList.get(i).getBalance());
                                                } else {
                                                    bal = bal.add(new BigDecimal("0"));
                                                }
                                            } else if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                                                BigDecimal dayDiff1 = new BigDecimal(dayDiff.toString());
                                                if ((dayDiff1.compareTo(new BigDecimal(params.getFromRange())) == 1) && (dayDiff1.compareTo(new BigDecimal(params.getToRange())) != 1)) {
                                                    bal = bal.add(resultList.get(i).getBalance());
                                                } else {
                                                    bal = bal.add(new BigDecimal("0"));
                                                }
                                            } else {
                                                bal = bal.add(resultList.get(i).getBalance());
                                            }
                                            j = delinquencyCycleList.size();
                                        }
                                    }
                                }
                            }
                        }
                    } else if (params.getNpaClassification().equalsIgnoreCase("13")) {       //LOSS
                        acStatus = "LOSS";
                        delinqCycle = "L";
                        //                resultList = npaRemote.getNpaStatusReportData1("1", acCodeQuery, "19000101", params.getDate(), acStatus, params.getBrnCode().equalsIgnoreCase("90")?"0A":params.getBrnCode(), "Y");
                        for (int i = 0; i < resultList.size(); i++) {
                            NpaStatusReportPojo npaStatus = new NpaStatusReportPojo();
                            if (resultList.get(i).getPresentStatus().substring(0, 3).equalsIgnoreCase("LOS") && resultList.get(i).getAcno().substring(2, 4).equalsIgnoreCase(acType)) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                            //                    System.out.println(acNature+"-"+params.getAcType()+"=>>>"+params.getToRange()+"; Bal:"+bal);
                        }
                    }
                }
            }


            AcctBalPojo acctBal = new AcctBalPojo();
            acctBal.setNumberOFAcct(Long.parseLong(String.valueOf(resultList.size())));
            acctBal.setBalance(bal);
            return acctBal;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    /*
     * Formula implementation of RBI Report (
     */
    private BigDecimal getValueFromFormula(List<RbiSossPojo> dataList, String formula) throws ApplicationException {
        try {
            BigDecimal balance;
            String exp = "";
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(",")) {
                    balance = getOperandBalance(dataList, arr[i]);
                    if (exp.equals("")) {
                        exp = exp + balance.toString();
                    } else {
                        exp = exp + " " + balance.toString();
                    }
                } else {
                    if (exp.equals("")) {
                        exp = exp + arr[i];
                    } else {
                        exp = exp + " " + arr[i];
                    }
                }
            }
            return PostfixEvaluator.evaluate(exp);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private BigDecimal getOperandBalance(List<RbiSossPojo> dataList, String csvExp) throws ApplicationException {
        try {
            BigDecimal balance = new BigDecimal("0");
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else if (arr[2] != 0 && arr[3] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else if (arr[2] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getNpaAmountBasedOnQuarters(String acNature, String acctcode, String startDt, String endDt, String brncode) throws ApplicationException {
        BigDecimal amt = new BigDecimal("0");
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npaAmt = "";
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!acctcode.equals("")) {
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                    acNature = common.getAcNatureByAcType(acctcode);
                    tableName = common.getTableName(acNature);
                }
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
            }

            if (endDt.equals("")) {
                dtQuery = " <='" + startDt + "'";
                closingQ = " >'" + startDt + "'";
            } else {
                dtQuery = " between '" + startDt + "' and '" + endDt + "'";
                closingQ = " >'" + endDt + "'";
            }
            if (acNature.contains("REC")) {
                npaAmt = " cast(IFNULL(sum(cramt),0) as decimal(25,2))";
            } else {
                npaAmt = " cast(IFNULL(sum(cramt- dramt),0) as decimal(25,2))";
            }
            npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and l.npadt " + dtQuery + ")";

            List dataList = new ArrayList();
            String executionQuery = "";
            if (brncode.equalsIgnoreCase("0A") || brncode.equalsIgnoreCase("90")) {
                executionQuery = "select count(distinct a.acno), " + npaAmt + " from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " WHERE DT " + dtQuery + " ) "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate " + closingQ + " ) and r.acno = a.acno and dt " + dtQuery + acCodeQuery + npaQuery;

//                System.out.println("Execution Query Is: " + executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            } else {
                executionQuery = "select count(distinct a.acno), " + npaAmt + " from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " WHERE DT " + dtQuery + " ) "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate " + closingQ + " ) and r.acno = a.acno and a.CurBrCode = '" + brncode + "' and dt " + dtQuery + acCodeQuery + npaQuery;

//                System.out.println("Execution Query Is: " + executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            }
            Vector ele = (Vector) dataList.get(0);
            amt = new BigDecimal(ele.get(1).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return amt;
    }

    public BigDecimal getNpaAmountBasedOnBeginQuarters(String acNature, String acctcode, String startDt, String endDt, String brncode, List<NpaStatusReportPojo> resultList) throws ApplicationException {
        BigDecimal amt = new BigDecimal("0");
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npaAmt = "", acTypeQuery = "", acType = "", acctType = "", crDbFlag = "";
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                acTypeQuery = "select acctcode,accttype,crdbFlag from accounttypemaster where acctnature in ('" + acNature + "') and crdbFlag in ('B','D') ";
            }
            if (!acctcode.equals("")) {
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acTypeQuery = (!acTypeQuery.equalsIgnoreCase("")
                        ? acTypeQuery.concat(" union all select acctcode,accttype,crdbflag from accounttypemaster where acctcode in ('" + acctcode + "')  and crdbFlag in ('B','D') ")
                        : " select acctcode,accttype,crdbflag from accounttypemaster where acctcode in ('" + acctcode + "')  and crdbFlag in ('B','D') ");
            }
//            List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
            String executionQuery = "";
            List acTypeList = em.createNativeQuery(acTypeQuery).getResultList();
            if (brncode.equalsIgnoreCase("0A") || brncode.equalsIgnoreCase("90")) {
                if (!acTypeList.isEmpty()) {
                    for (int l = 0; l < acTypeList.size(); l++) {
                        Vector acTypeVect = (Vector) acTypeList.get(l);
                        acType = acTypeVect.get(0).toString();
                        acctType = acTypeVect.get(1).toString();
                        crDbFlag = acTypeVect.get(2).toString();
//                        if(crDbFlag.equalsIgnoreCase("D")){                            
//                            resultList = npaRemote.getNpaStatusReportData1("0", acType, "19000101", startDt, "", brncode.equalsIgnoreCase("90")?"0A":brncode, "Y");
                        if (resultList.size() > 0) {
                            for (int m = 0; m < resultList.size(); m++) {
                                if (acType.equalsIgnoreCase(resultList.get(m).getAcno().substring(2, 4))) {
                                    amt = amt.add(resultList.get(m).getBalance());
                                }
                            }
                        }
//                        } else {
//                            amt = amt;
//                        }
                    }
                }
            } else {
                if (!acTypeList.isEmpty()) {
                    for (int l = 0; l < acTypeList.size(); l++) {
                        Vector acTypeVect = (Vector) acTypeList.get(l);
                        acType = acTypeVect.get(0).toString();
                        acctType = acTypeVect.get(1).toString();
                        crDbFlag = acTypeVect.get(2).toString();
//                        if(crDbFlag.equalsIgnoreCase("D")){
//                            resultList = npaRemote.getNpaStatusReportData1("0", acType, "19000101", startDt, "", brncode.equalsIgnoreCase("90")?"0A":brncode, "Y");
                        if (resultList.size() > 0) {
                            for (int m = 0; m < resultList.size(); m++) {
                                if (acType.equalsIgnoreCase(resultList.get(m).getAcno().substring(2, 4))) {
                                    amt = amt.add(resultList.get(m).getBalance());
                                }
                            }
                        }
//                        } else {
//                            amt = amt;
//                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return amt;
    }

    public BigDecimal getNpaAmountRecoveryDuringQuarters(String acNature, String acctcode, String startDt, String endDt, String brncode, List<NpaRecoveryPojo> resultList) throws ApplicationException {
        BigDecimal amt = new BigDecimal("0");
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npaAmt = "", acTypeQuery = "", acType = "";
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
//                tableName = common.getTableName(acNature);
                acTypeQuery = "select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbFlag in ('D','B')";
            }
            if (!acctcode.equals("")) {
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
//                    acNature = common.getAcNatureByAcType(acctcode);
//                    tableName = common.getTableName(acNature);
                }
                acTypeQuery = (!acTypeQuery.equalsIgnoreCase("")
                        ? acTypeQuery.concat(" union all select acctcode from accounttypemaster where acctcode in ( '" + acctcode + "')  and crdbFlag in ('D','B')")
                        : "select acctcode from accounttypemaster where acctcode in ( '" + acctcode + "' ) and crdbFlag in ('D','B')");
            }
//            List<NpaRecoveryPojo> resultList = new ArrayList<NpaRecoveryPojo>();
            String executionQuery = "";
            if (brncode.equalsIgnoreCase("0A") || brncode.equalsIgnoreCase("90")) {
                List acTypeList = em.createNativeQuery(acTypeQuery).getResultList();
                if (!acTypeList.isEmpty()) {
                    for (int l = 0; l < acTypeList.size(); l++) {
                        Vector acTypeVect = (Vector) acTypeList.get(l);
                        acType = acTypeVect.get(0).toString();
                        //List<NpaRecoveryPojo> getNpaRecoveryData(String acType, String fromDt, String toDt, String orgnBrCode)
//                        resultList = npaRemote.getNpaRecoveryData(acType, startDt, endDt, brncode);
                        if (resultList.size() > 0) {
                            for (int m = 0; m < resultList.size(); m++) {
                                if (resultList.get(m).getAcType().equalsIgnoreCase(acType)) {
//                                    amt = amt.add(resultList.get(m).getSubRec().add(resultList.get(m).getDeRec()).add(resultList.get(m).getLossRec()));
                                    amt = amt.add(resultList.get(m).getDeRec());
//                                System.out.println(startDt+"-"+endDt+":acno:"+resultList.get(m).getAcno()+";"+resultList.get(m).getSubRec()+";"+resultList.get(m).getDeRec()+";"+resultList.get(m).getLossRec());
                                }
                            }
                        }
                    }
                }
            } else {
                List acTypeList = em.createNativeQuery(acTypeQuery).getResultList();
                if (!acTypeList.isEmpty()) {
                    for (int l = 0; l < acTypeList.size(); l++) {
                        Vector acTypeVect = (Vector) acTypeList.get(l);
                        acType = acTypeVect.get(0).toString();
//                        resultList = npaRemote.getNpaRecoveryData(acType, startDt, endDt, brncode);
                        if (resultList.size() > 0) {
                            for (int m = 0; m < resultList.size(); m++) {
                                if (resultList.get(m).getAcType().equalsIgnoreCase(acType)) {
//                                    amt = amt.add(resultList.get(m).getSubRec().add(resultList.get(m).getDeRec()).add(resultList.get(m).getLossRec()));
                                    amt = amt.add(resultList.get(m).getDeRec());
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
        return amt;
    }

    public BigDecimal getNewNpaAccountDuringPeriod(String acNature, String acctcode, String startDt, String endDt, String brncode) throws ApplicationException {
        BigDecimal amt = new BigDecimal("0");
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npaAmt = "";
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature in ( '" + acNature + "'))";
            } else if (!acctcode.equals("")) {
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                    acNature = common.getAcNatureByAcType(acctcode);
                    tableName = common.getTableName(acNature);
                }
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode in ( '" + acctcode + "'))";
            }

            if (endDt.equals("")) {
                dtQuery = " <='" + startDt + "'";
                closingQ = " >'" + startDt + "'";
            } else {
                dtQuery = " between '" + startDt + "' and '" + endDt + "'";
                closingQ = " >'" + endDt + "'";
            }
            if (acNature.contains("REC")) {
                npaAmt = " cast(IFNULL(sum(cramt),0) as decimal(25,2))";
            } else {
                npaAmt = " cast(IFNULL(sum(cramt- dramt),0) as decimal(25,2))";
            }


            List dataList = new ArrayList();
            String executionQuery = "";
            if (brncode.equalsIgnoreCase("0A") || brncode.equalsIgnoreCase("90")) {
                npaQuery = " r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and l.npadt between '" + startDt + "' and '" + endDt + "')";

                executionQuery = "select count(distinct r.acno), " + npaAmt + " from " + tableName + " r  where " + npaQuery;
//                System.out.println("Execution Query Is: " + executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            } else {
                npaQuery = " r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and a.CurBrCode = '" + brncode + "' and l.npadt between '" + startDt + "' and '" + endDt + "')";

                executionQuery = "select count(distinct r.acno), " + npaAmt + " from " + tableName + " r  where " + npaQuery;
//                System.out.println("Execution Query Is: " + executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            }
            Vector ele = (Vector) dataList.get(0);
            amt = new BigDecimal(ele.get(1).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return amt;
    }

    public Object[] getQuarterEndDtForCurDt(String brncode, String reportDt) throws ApplicationException {
        try {
            if (brncode.equals("0A")) {
                brncode = "90";
            }
            String fYearEndDate = "", qEndDate = "";
//            YearEndDatePojo yearEndDtPojo = rbiReportFacade.getYearEndDataAccordingToDate(brncode, reportDt);
//            fYearEndDate = yearEndDtPojo.getMaxDate();
            List<String> qEndDateList = new CopyOnWriteArrayList<String>();
            qEndDate = reportDt;
            qEndDateList.add(qEndDate);
            for (int i = 0; i < 3; i++) {
                qEndDate = CbsUtil.monthAdd(qEndDate, -3);
                qEndDateList.add(ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(qEndDate))));
            }
            for (String qEndDtLoop : qEndDateList) {
                /**
                 * Only qEndDtLoop <= curdate should be in the list.
                 */
                if (ymd.parse(qEndDtLoop).compareTo(ymd.parse(reportDt)) == 1) {
                    qEndDateList.remove(qEndDtLoop);
                }
            }
            if (qEndDateList.size() == 4) {
                qEndDateList.remove(3);
            }
//            for (String strDt : qEndDateList) {
//                System.out.println("Now Date is::" + strDt);
//            }
            Object[] arr = qEndDateList.toArray();
            Arrays.sort(arr);
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public YearEndDatePojo getYearEndData(String brncode) {
        List yearEndList = em.createNativeQuery("select mindate,maxdate,f_year from yearend where yearendflag='N' and brncode='" + brncode + "'").getResultList();
        Vector yearEnd = (Vector) yearEndList.get(0);
        String minDate = yearEnd.get(0).toString();
        String maxDate = yearEnd.get(1).toString();
        String fYear = yearEnd.get(2).toString();
        YearEndDatePojo pojoList = new YearEndDatePojo();
        for (int i = 0; i < yearEndList.size(); i++) {
            Vector vec = (Vector) yearEndList.get(i);
            pojoList.setMinDate(vec.get(0).toString());
            pojoList.setMaxDate(vec.get(1).toString());
            pojoList.setfYear(vec.get(2).toString());
        }
        return pojoList;
    }

    public List<GlHeadPojo> getOverDueAccountInBetweenDateAndTheirOutStandingBal(String frDt, String toDt, String acNature, String acType, String absParam, List<NpaStatusReportPojo> npaResultList) throws ApplicationException {
        List<GlHeadPojo> list = new ArrayList<GlHeadPojo>();
        String tableName = "", acCodeQuery = "";
        try {
//            if (!acNature.equals("")) {
//                tableName = common.getTableName(acNature);
//                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "' and crdbFlag in ('D','B'))";
//            } else if (!acType.equals("")) {
//                acNature = common.getAcNatureByAcType(acType);
//                tableName = common.getTableName(acNature);
//                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acType + "'  and crdbFlag in ('D','B'))";
//            }
//            String executionQuery = "select distinct(a.acno),a.custname from " + tableName + " r,accountmaster a where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDt + "') "
//                    + "and r.acno = a.acno " + acCodeQuery + " and r.dt between '" + frDt + "' and '" + toDt + "' and r.acno in (select acno from " + tableName + " WHERE "
//                    + "dt between '" + frDt + "' and '" + toDt + "' group by acno having sum(cramt) = 0) and r.acno "
//                    + "in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno "
//                    + "where a.accstatus not in('11','12','13') " + acCodeQuery + ")";

//            System.out.println("Execution Query Is: " + executionQuery);
            List dataList = null ;
            if (!acNature.equals("")) {
                dataList = em.createNativeQuery(" select acctcode from accounttypemaster where acctnature = '" + acNature + "' and crdbFlag in ('D','B')").getResultList();
            } else if (!acType.equals("")) {
                dataList = em.createNativeQuery(" select acctcode from accounttypemaster where acctcode = '" + acType + "' and crdbFlag in ('D','B')").getResultList();                
            }
            
            if (!dataList.isEmpty() && !npaResultList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) { //AcctType Checking                    
                    Vector element = (Vector) dataList.get(i);
                    for (int j = 0; j < npaResultList.size(); j++) { //acNo Checking                        
                        if (npaResultList.get(j).getAcno().substring(2, 4).equalsIgnoreCase(element.get(0).toString())) { //AcctCode checking
                            GlHeadPojo pojo = new GlHeadPojo();
                            String accountNo = npaResultList.get(j).getAcno();
                            pojo.setGlHead(npaResultList.get(j).getAcno());
                            pojo.setGlName(npaResultList.get(j).getCustName());
//                            List dataList1 = em.createNativeQuery("select cast(IFNULL(sum(cramt- dramt),0) as decimal(25,2)) as bal from " + tableName + " where acno='" + accountNo + "' and dt<='" + toDt + "'").getResultList();
//                            element = (Vector) dataList1.get(0);
                            if (absParam.equalsIgnoreCase("Y")) {
                                pojo.setBalance(npaResultList.get(j).getBalance().abs());
                            } else {
                                pojo.setBalance(npaResultList.get(j).getBalance()); //Abs() which is removed by Alok
                            }
                            pojo.setGlClassification(npaResultList.get(j).getPresentStatus());
                            pojo.setRemarks(absParam);
                            list.add(pojo);
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List<GlHeadPojo> accountHavingSanctionAsPerAmount(String reportDt, String acNature, String acType, Double amount, String remarks) throws ApplicationException {
        List<GlHeadPojo> list = new ArrayList<GlHeadPojo>();
        String acCodeQuery = "", tableName = "";
        try {
            if (!acNature.equals("")) {
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!acType.equals("")) {
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acType + "')";
                acNature = common.getAcNatureByAcType(acType);
                tableName = common.getTableName(acNature);
            }
            String executionQuery = "select l.acno,cast(IFNULL(l.sanctionlimit,0) as decimal(25,2)),a.custname "
                    + "from  loan_appparameter l,accountmaster a where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + reportDt + "') "
                    + acCodeQuery + " and l.acno = a.acno and l.acno in(select acno from loan_appparameter where sanctionlimit >=" + amount + ") "
                    + "and a.accstatus not in('11','12','13')";

//            System.out.println("Execution Query Is: " + executionQuery);
            List dataList = em.createNativeQuery(executionQuery).getResultList();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    GlHeadPojo pojo = new GlHeadPojo();
                    Vector element = (Vector) dataList.get(i);
                    pojo.setGlHead(element.get(0).toString());
                    List dataListBal = em.createNativeQuery("select cast(IFNULL(sum(cramt- dramt),0) as decimal(25,2)) as bal from " + tableName + " where acno='" + element.get(0).toString() + "' and dt<='" + reportDt + "'").getResultList();
                    Vector elementBal = (Vector) dataListBal.get(0);
                    pojo.setBalance(new BigDecimal(elementBal.get(0).toString())); //Abs() which is removed by Alok
                    //pojo.setBalance(new BigDecimal(element.get(1).toString()));
                    pojo.setGlName(element.get(2).toString());
                    pojo.setRemarks(remarks);

                    list.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List<GlHeadPojo> getTotalOutStanding(AdditionalStmtPojo params) throws ApplicationException {
        List<GlHeadPojo> list = new ArrayList<GlHeadPojo>();
        try {
            String tableName = "", acCodeQuery = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
            }
            String executionQuery = "select a.acno,a.custname, cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) "
                    + "as amt,a.accstatus as accstatus from " + tableName + " r,accountmaster a where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) "
                    + "and r.acno = a.acno and r.dt <='" + params.getDate() + "' " + acCodeQuery + " and "
                    + "a.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where "
                    + "a.accstatus not in('11','12','13') " + acCodeQuery + " union select a.acno from accountmaster a "
                    + "left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and "
                    + "l.npadt>'" + params.getDate() + "') group by a.acno, a.custname,a.accstatus union all select a.acno, a.custname,"
                    + "cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) as amt,a.accstatus as accstatus from npa_recon r,accountmaster a "
                    + "where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and "
                    + "r.acno = a.acno and r.dt <='" + params.getDate() + "' " + acCodeQuery + " and a.acno in "
                    + "(select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where "
                    + "a.accstatus not in('11','12','13') " + acCodeQuery + " union select a.acno from accountmaster a "
                    + "left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " "
                    + "and l.npadt>'" + params.getDate() + "' union select a.acno from accountmaster a left join "
                    + "loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and "
                    + "l.npadt<='" + params.getDate() + "') group by a.acno, a.custname,a.accstatus";

//            System.out.println("Execution Query Is: " + executionQuery);
            List dataList = em.createNativeQuery(executionQuery).getResultList();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    GlHeadPojo pojo = new GlHeadPojo();
                    Vector element = (Vector) dataList.get(i);
                    pojo.setGlHead(element.get(0).toString());
                    pojo.setGlName(element.get(1).toString());
                    pojo.setBalance(new BigDecimal(element.get(2).toString()));
                    pojo.setRemarks(element.get(3).toString());

                    list.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public String getAccountStatusDescription(Integer accountStatus) {
        String statusDesc = "";
        switch (accountStatus) {
            case 1:
                statusDesc = "Standard";
                break;
            case 9:
                statusDesc = "Currently Closed";
                break;
            case 11:
                statusDesc = "SubStandard";
                break;
            case 12:
                statusDesc = "Doubtful";
                break;
            case 13:
                statusDesc = "Loss";
                break;
            default:
                statusDesc = "";
                break;
        }
        return statusDesc;
    }

    public List<Oss4PartGPojo> getSOS4PartG(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<Oss4PartGPojo> Oss4PartGPojoTable = new ArrayList<Oss4PartGPojo>();
        try {
            List<Oss4PartGMgmtPojo> Oss4PartMgmtTable = new ArrayList<Oss4PartGMgmtPojo>();
            List<Oss4PartGUnSecAdvPojo> Oss4PartGUnSecTable = new ArrayList<Oss4PartGUnSecAdvPojo>();
            List<Oss4PartGExpPojo> Oss4PartGExpTable = new ArrayList<Oss4PartGExpPojo>();
            List<Oss4PartGSecAdvPojo> Oss4PartGSecTable = new ArrayList<Oss4PartGSecAdvPojo>();

            List mgmtQuery = em.createNativeQuery("select b.title,b.name,r.ref_desc,b.desg from banks_dir_details b, cbs_ref_rec_type r "
                    + " where b.edu_details = r.ref_code and r.ref_rec_no ='214' AND desg not in ('DIRREL','MGRREL','SECREL','SEC','MGR') order by b.desg desc").getResultList();

            if (!mgmtQuery.isEmpty()) {
                for (int i = 0; i < mgmtQuery.size(); i++) {
                    Oss4PartGMgmtPojo mgmtPojo = new Oss4PartGMgmtPojo();
                    Vector mgmtVect = (Vector) mgmtQuery.get(i);

                    mgmtPojo.setTitle(mgmtVect.get(0) != null ? mgmtVect.get(0).toString() : "");
                    mgmtPojo.setName(mgmtVect.get(1) != null ? mgmtVect.get(1).toString() : "");
                    mgmtPojo.setEdu_details(mgmtVect.get(2) != null ? mgmtVect.get(2).toString() : "");
                    mgmtPojo.setRem_desg(mgmtVect.get(3) != null ? mgmtVect.get(3).toString() : "");

                    Oss4PartMgmtTable.add(mgmtPojo);
                }
            } else {
                Oss4PartGMgmtPojo mgmtPojo = new Oss4PartGMgmtPojo();
                mgmtPojo.setTitle("");
                mgmtPojo.setName("");
                mgmtPojo.setEdu_details("");
                mgmtPojo.setRem_desg("");

                Oss4PartMgmtTable.add(mgmtPojo);
            }

            List unSecQuery = new ArrayList();

            if (orgnCode.equalsIgnoreCase("90")) {
                unSecQuery = em.createNativeQuery("select a.custname,cb.acc_No,cb.cust_id,cb.Exposure,cb.asset_class_reason,ifnull(cb.rel_name,''),"
                        + " ifnull(cb.rel_with_ac_holder,''),a.openingdt,ifnull(a.odlimit,0),ifnull((select custfullname from cbs_customer_master_detail where customerid =ifnull(cb.dir_cust_id,cb.cust_id)),(select custfullname from cbs_customer_master_detail where customerid =cb.cust_id)) from cbs_loan_borrower_details cb, accountmaster a where cb.secured ='UNSEC' "
                        + " and cb.ACC_NO = a.acno and cb.relation  like '%DIR%' and (ifnull(a.closingdate,'')='' or a.closingdate > '" + reportDt + "')").getResultList();
            } else {
                unSecQuery = em.createNativeQuery("select a.custname,cb.acc_No,cb.cust_id,cb.Exposure,cb.asset_class_reason,ifnull(cb.rel_name,''),"
                        + " ifnull(cb.rel_with_ac_holder,''),a.openingdt,ifnull(a.odlimit,0),ifnull((select custfullname from cbs_customer_master_detail where customerid =ifnull(cb.dir_cust_id,cb.cust_id)),(select custfullname from cbs_customer_master_detail where customerid =cb.cust_id)) from cbs_loan_borrower_details cb, accountmaster a where cb.secured ='UNSEC' "
                        + " and cb.ACC_NO = a.acno and substring(acno,3,2)='" + orgnCode + "' and cb.relation  like '%DIR%' and (ifnull(a.closingdate,'')='' or a.closingdate > '" + reportDt + "')").getResultList();
            }

            if (!unSecQuery.isEmpty()) {
                for (int i = 0; i < unSecQuery.size(); i++) {
                    Oss4PartGUnSecAdvPojo unSecPojo = new Oss4PartGUnSecAdvPojo();
                    Vector unSecVect = (Vector) unSecQuery.get(i);

                    String uName = unSecVect.get(0).toString();
                    String uAcNo = unSecVect.get(1).toString();
                    String uCid = unSecVect.get(2).toString();
                    String uExpId = unSecVect.get(3).toString();
                    String uClassId = unSecVect.get(4).toString();
                    String uRelName = unSecVect.get(5).toString();
                    String uROwner = unSecVect.get(6).toString();
                    String uOpDate = unSecVect.get(7).toString();
                    String uSancAmt = unSecVect.get(8).toString();

                    String uRelDesc = "";
                    List uRelQuery = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no ='004' "
                            + " and ref_code ='" + uROwner + "'").getResultList();
                    if (!uRelQuery.isEmpty()) {
                        Vector uRelVect = (Vector) uRelQuery.get(0);
                        uRelDesc = uRelVect.get(0).toString();
                    }

                    String uExpDesc = "";
                    List uExpQuery = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no ='191' "
                            + " and ref_code ='" + uExpId + "'").getResultList();
                    if (!uExpQuery.isEmpty()) {
                        Vector uExpVect = (Vector) uExpQuery.get(0);
                        uExpDesc = uExpVect.get(0).toString();
                    }

                    String uSecNat = "";
                    BigDecimal uSecVal = new BigDecimal("0");
                    float uSecMargin = 0;
                    float uAvgMargin = 0;

                    List uSecQuery = em.createNativeQuery("select securityChg,matvalue,margin from loansecurity where acno ='" + uAcNo + "'"
                            + " and status in ('ACTIVE','EXPIRED') and (ExpiryDate is null or ExpiryDate = '' or ExpiryDate>='" + reportDt + "') and Entrydate<='" + reportDt + "'").getResultList();
                    if (!uSecQuery.isEmpty()) {
                        float uCnt = uSecQuery.size();
                        for (int j = 0; j < uSecQuery.size(); j++) {
                            Vector uSecVect = (Vector) uSecQuery.get(j);
//                            uSecNat = uSecNat + uSecVect.get(0).toString();
                            uSecVal = uSecVal.add(new BigDecimal(uSecVect.get(1).toString()));
                            uSecMargin = uSecMargin + Float.parseFloat(uSecVect.get(2).toString());
                        }
                        uAvgMargin = uSecMargin / uCnt;
                    }

                    BigDecimal uSl = new BigDecimal("0");
                    BigDecimal uMargin = new BigDecimal("0");
                    List uqlList = em.createNativeQuery("SELECT SANCTIONLIMIT FROM loan_appparameter Where acno ='" + uAcNo + "'").getResultList();
                    if (!uqlList.isEmpty()) {
                        Vector uSlVect = (Vector) uqlList.get(0);
                        uSl = uSl.add(new BigDecimal(uSlVect.get(0).toString()));
                    }
                    List uSecNatquery = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where ref_rec_no='187' and  ref_code in(select SECURED from cbs_loan_borrower_details where acc_no ='" + uAcNo + "')").getResultList();
                    if (!uSecNatquery.isEmpty()) {
                        for (int l = 0; l < uSecNatquery.size(); l++) {
                            Vector uSecVect1 = (Vector) uSecNatquery.get(l);
                            uSecNat = uSecNat + uSecVect1.get(0).toString();
                        }
                    }
                    if (uSecVal.compareTo(new BigDecimal("0")) != 0) {
                        uMargin = ((uSl.subtract(uSecVal)).multiply(new BigDecimal("100"))).divide(uSecVal, BigDecimal.ROUND_HALF_DOWN).abs();
                    }

                    double uOs = Double.parseDouble(loanIntRemote.outStandingAsOnDate(uAcNo, reportDt));

                    String uAssetDesc = npaClass(uAcNo, reportDt);

//                    String uAssetDesc = "";
//                    List uAssetQuery = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no ='199' "
//                            + " and ref_code ='" + uClassId + "'").getResultList();
//                    if (!uAssetQuery.isEmpty()) {
//                        Vector uAssetVect = (Vector) uAssetQuery.get(0);
//                        uAssetDesc = uAssetVect.get(0).toString();
//                    }
                    unSecPojo.setName(uAcNo.concat("\n").concat(uName));
                    unSecPojo.setRelName(uRelName);
                    unSecPojo.setNatInterest("");
                    if (uRelDesc.equalsIgnoreCase("Self")) {
                        unSecPojo.setRelation(uRelDesc.concat(" ( ").concat(unSecVect.get(9).toString()).concat(" ) "));
                    } else {
                        unSecPojo.setRelation(uRelDesc.concat(" of ").concat(unSecVect.get(9).toString()));
                    }
                    unSecPojo.setTpExp(uExpDesc);
                    unSecPojo.setExpDate(uOpDate);
                    unSecPojo.setExpAmt(new BigDecimal(uSancAmt).abs());
                    unSecPojo.setSecNature(uSecNat);
                    unSecPojo.setSecValue(uSecVal);
                    unSecPojo.setSecRoi(uMargin);
                    unSecPojo.setAmtOs(new BigDecimal(uOs));
                    unSecPojo.setClassification(uAssetDesc);
                    unSecPojo.setProv(new BigDecimal("0"));

                    Oss4PartGUnSecTable.add(unSecPojo);
                }
            } else {
                Oss4PartGUnSecAdvPojo unSecPojo = new Oss4PartGUnSecAdvPojo();
                unSecPojo.setName("");
                unSecPojo.setRelName("");
                unSecPojo.setNatInterest("");
                unSecPojo.setRelation("");
                unSecPojo.setTpExp("");
                unSecPojo.setExpDate("");
                unSecPojo.setExpAmt(new BigDecimal("0"));
                unSecPojo.setSecNature("");
                unSecPojo.setSecValue(new BigDecimal("0"));
                unSecPojo.setSecRoi(new BigDecimal("0"));
                unSecPojo.setAmtOs(new BigDecimal("0"));
                unSecPojo.setClassification("");
                unSecPojo.setProv(new BigDecimal("0"));

                Oss4PartGUnSecTable.add(unSecPojo);
            }

            Oss4PartGExpPojo gExpPojo = new Oss4PartGExpPojo();
            gExpPojo.setName("");
            gExpPojo.setTpExp("");
            gExpPojo.setExpAmt(new BigDecimal("0"));
            gExpPojo.setExpPerc(new BigDecimal("0"));

            Oss4PartGExpTable.add(gExpPojo);

            List sSecQuery = new ArrayList();

            if (orgnCode.equalsIgnoreCase("90") || orgnCode.equalsIgnoreCase("0A")) {
                sSecQuery = em.createNativeQuery("select a.custname,cb.acc_No,cb.cust_id,cb.Exposure,cb.asset_class_reason,ifnull(cb.rel_name,''),"
                        + " ifnull(cb.rel_with_ac_holder,''),ifnull((select custfullname from cbs_customer_master_detail where customerid =ifnull(cb.dir_cust_id,cb.cust_id)),(select custfullname from cbs_customer_master_detail where customerid =cb.cust_id)) from cbs_loan_borrower_details cb, accountmaster a where cb.secured !='UNSEC' "
                        + " and cb.ACC_NO = a.acno and cb.relation  in ('DIR','DIRREL','SEC') and (ifnull(a.closingdate,'')='' or a.closingdate > '" + reportDt + "')").getResultList();
            } else {
                sSecQuery = em.createNativeQuery("select a.custname,cb.acc_No,cb.cust_id,cb.Exposure,cb.asset_class_reason,ifnull(cb.rel_name,''),"
                        + " ifnull(cb.rel_with_ac_holder,''),ifnull((select custfullname from cbs_customer_master_detail where customerid =ifnull(cb.dir_cust_id,cb.cust_id)),(select custfullname from cbs_customer_master_detail where customerid =cb.cust_id)) from cbs_loan_borrower_details cb, accountmaster a where cb.secured !='UNSEC' "
                        + " and cb.ACC_NO = a.acno and substring(acno,3,2)='" + orgnCode + "' and cb.relation  in ('DIR','DIRREL','SEC') and (ifnull(a.closingdate,'')='' or a.closingdate > '" + reportDt + "')").getResultList();
            }

            if (!sSecQuery.isEmpty()) {
                for (int k = 0; k < sSecQuery.size(); k++) {
                    Oss4PartGSecAdvPojo sSecPojo = new Oss4PartGSecAdvPojo();
                    Vector sSecVect = (Vector) sSecQuery.get(k);

                    String sName = sSecVect.get(0).toString();
                    String sAcNo = sSecVect.get(1).toString();
                    String sExpId = sSecVect.get(3).toString();
                    String sClassId = sSecVect.get(4).toString();
                    String sROwner = sSecVect.get(6).toString();

                    String sRelDesc = "";
                    List sRelQuery = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no ='004' "
                            + " and ref_code ='" + sROwner + "'").getResultList();
                    if (!sRelQuery.isEmpty()) {
                        Vector sRelVect = (Vector) sRelQuery.get(0);
                        sRelDesc = sRelVect.get(0).toString();
                    }

                    String sExpDesc = "";
                    List sExpQuery = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no ='191' "
                            + " and ref_code ='" + sExpId + "'").getResultList();
                    if (!sExpQuery.isEmpty()) {
                        Vector sExpVect = (Vector) sExpQuery.get(0);
                        sExpDesc = sExpVect.get(0).toString();
                    }

                    String sSecNat = "";
                    BigDecimal sSecVal = new BigDecimal("0");
                    float sSecMargin = 0;
                    float sAvgMargin = 0;

                    List sSecQuery1 = em.createNativeQuery("select securityChg,matvalue,ifnull(margin,0) from loansecurity where acno ='" + sAcNo + "'"
                            + " and status in ('ACTIVE','EXPIRED') and (ExpiryDate is null or ExpiryDate = '' or ExpiryDate>='" + reportDt + "') and Entrydate<='" + reportDt + "'").getResultList();
                    if (!sSecQuery1.isEmpty()) {
                        float sCnt = sSecQuery1.size();
                        for (int l = 0; l < sSecQuery1.size(); l++) {
                            Vector sSecVect1 = (Vector) sSecQuery1.get(l);
//                            sSecNat = sSecNat + sSecVect1.get(0).toString();
                            sSecVal = sSecVal.add(new BigDecimal(sSecVect1.get(1).toString()));
                            sSecMargin = sSecMargin + Float.parseFloat(sSecVect1.get(2).toString());
                        }
                        sAvgMargin = sSecMargin / sCnt;
                    }

                    List sSecNatquery = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where ref_rec_no='187' and  ref_code in(select SECURED from cbs_loan_borrower_details where acc_no ='" + sAcNo + "')").getResultList();
                    if (!sSecNatquery.isEmpty()) {
                        for (int l = 0; l < sSecNatquery.size(); l++) {
                            Vector sSecVect1 = (Vector) sSecNatquery.get(l);
                            sSecNat = sSecNat + sSecVect1.get(0).toString();
                        }
                    }
                    BigDecimal sSl = new BigDecimal("0");
                    BigDecimal sMargin = new BigDecimal("0");
                    List sqlList = em.createNativeQuery("SELECT SANCTIONLIMIT FROM loan_appparameter Where acno ='" + sAcNo + "'").getResultList();
                    if (!sqlList.isEmpty()) {
                        Vector sSlVect = (Vector) sqlList.get(0);
                        sSl = sSl.add(new BigDecimal(sSlVect.get(0).toString()));
                    }
                    if (sSecVal.compareTo(new BigDecimal("0")) != 0) {
                        sMargin = ((sSl.subtract(sSecVal)).multiply(new BigDecimal("100"))).divide(sSecVal, BigDecimal.ROUND_HALF_DOWN).abs();
                    }

                    double sOs = Double.parseDouble(loanIntRemote.outStandingAsOnDate(sAcNo, reportDt));

                    String roiGet = loanIntRemote.getRoiLoanAccount(sOs, reportDt, sAcNo);

                    String sAssetDesc = npaClass(sAcNo, reportDt);

//                    String sAssetDesc = "";
//                    List sAssetQuery = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no ='199' "
//                            + " and ref_code ='" + sClassId + "'").getResultList();
//                    if (!sAssetQuery.isEmpty()) {
//                        Vector sAssetVect = (Vector) sAssetQuery.get(0);
//                        sAssetDesc = sAssetVect.get(0).toString();
//                    }

                    sSecPojo.setName(sAcNo.concat("\n").concat(sName));
                    if (sRelDesc.equalsIgnoreCase("Self")) {
                        sSecPojo.setRelation(sRelDesc.concat(" ( ").concat(sSecVect.get(7).toString()).concat(" ) "));
                    } else {
                        sSecPojo.setRelation(sRelDesc.concat(" of ").concat(sSecVect.get(7).toString()));
                    }
                    sSecPojo.setTpExp(sExpDesc);
                    sSecPojo.setExpAmt(new BigDecimal(sOs).divide(repOpt).abs());
                    sSecPojo.setExpRoi(new BigDecimal(roiGet));
                    sSecPojo.setSecNature(sSecNat);
                    sSecPojo.setSecValue(sSecVal.divide(repOpt));
                    sSecPojo.setSecRoi(sMargin);
                    sSecPojo.setClassification(sAssetDesc);
                    sSecPojo.setProv(new BigDecimal("0"));

                    Oss4PartGSecTable.add(sSecPojo);
                }
            } else {
                Oss4PartGSecAdvPojo sSecPojo = new Oss4PartGSecAdvPojo();
                sSecPojo.setName("");
                sSecPojo.setRelation("");
                sSecPojo.setTpExp("");
                sSecPojo.setExpAmt(new BigDecimal("0"));
                sSecPojo.setExpRoi(new BigDecimal("0"));
                sSecPojo.setSecNature("");
                sSecPojo.setSecValue(new BigDecimal("0"));
                sSecPojo.setSecRoi(new BigDecimal("0"));
                sSecPojo.setClassification("");
                sSecPojo.setProv(new BigDecimal("0"));

                Oss4PartGSecTable.add(sSecPojo);
            }

            Oss4PartGPojo partGPojo = new Oss4PartGPojo();
            partGPojo.setOss4PartGMgmtList(Oss4PartMgmtTable);
            partGPojo.setOss4PartGUnSecAdvList(Oss4PartGUnSecTable);
            partGPojo.setOss4PartGExpList(Oss4PartGExpTable);
            partGPojo.setOss4PartGSecAdvList(Oss4PartGSecTable);

            Oss4PartGPojoTable.add(partGPojo);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return Oss4PartGPojoTable;
    }

    public String npaClass(String acNo, String tillDate) throws ApplicationException {
        String npStatus = "";
        NumberFormat formatter = new DecimalFormat("#.##");
        try {
            String acNat = common.getAcNatureByAcNo(acNo);
            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List accountList = em.createNativeQuery("select custname,accstatus from accountmaster where acno = '" + acNo + "'").getResultList();
                Vector element = (Vector) accountList.get(0);
                String custname = element.get(0).toString();
                String accStatus = element.get(1).toString();

                BigDecimal outStandBal = new BigDecimal(formatter.format(Double.parseDouble(loanIntRemote.outStandingAsOnDate(acNo, tillDate)))).abs();
                List limitList = null;
                Vector limitVec = null;
                BigDecimal chkLimit = loanRemote.getCheckLimit(acNo);

                /**
                 * If outstanding balance is less than chkLimit and there is no
                 * credit for previous days(Ex:Substandard-90,doubtful-365
                 * days)*
                 */
                if (outStandBal.compareTo(chkLimit) == -1) {
                    long dayDiff = 0;
                    if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                        npStatus = npaAccountAddition(accStatus, tillDate, acNo, custname, outStandBal);
                    } else {
                        limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                + "ca_recon where acno = '" + acNo + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                        limitVec = (Vector) limitList.get(0);
                        String lastCrDt = limitVec.get(0).toString();

                        dayDiff = CbsUtil.dayDiff(ymd.parse(lastCrDt), ymd.parse(tillDate));

                        limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                        limitVec = (Vector) limitList.get(0);
                        long stdDays = Long.parseLong(limitVec.get(1).toString());
                        if (dayDiff > stdDays) {
                            npStatus = "Sub-standard";
                        }
                    }
                } /**
                 * If outstanding balance is greater than limit amount
                 * continuously for more than 90 days. while Npa account is
                 * same.
                 */
                else if (outStandBal.compareTo(chkLimit) == 1) {
                    if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                        npStatus = npaAccountAddition(accStatus, tillDate, acNo, custname, outStandBal);
                    } else {
                        //Only For Standard Account
                        boolean addAccount = false;
                        limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                        limitVec = (Vector) limitList.get(0);
                        Long stdDays = Long.parseLong(limitVec.get(1).toString());

                        String fromDt = CbsUtil.dateAdd(tillDate, -(stdDays.intValue()));   //For more than 90 days for standard.
                        String toDt = CbsUtil.dateAdd(tillDate, -1);                        //Because I have already checked the tillDate.
                        List<DateSlabPojo> list = loanRemote.getLimitOnRangeOfDate(fromDt, toDt, acNo);
                        for (int k = 0; k < list.size(); k++) {
                            addAccount = false;
                            DateSlabPojo obj = list.get(k);
                            String tmpFrDt = obj.getFromDt();
                            String tmpToDt = obj.getToDt();
                            BigDecimal ccodLimit = obj.getLimit();

                            limitList = em.createNativeQuery("select distinct(date_format(dt,'%Y%m%d')) as trandt from "
                                    + "ca_recon where acno='" + acNo + "' and (cramt<>0 or dramt<>0) and "
                                    + "dt between '" + tmpFrDt + "' and '" + tmpToDt + "' order by trandt").getResultList();
                            for (int h = 0; h < limitList.size(); h++) {
                                limitVec = (Vector) limitList.get(h);
                                String tranDt = limitVec.get(0).toString();

                                BigDecimal tempOutstanding = new BigDecimal(formatter.format(Double.parseDouble(loanIntRemote.outStandingAsOnDate(acNo, tranDt)))).abs();

                                if (tempOutstanding.compareTo(ccodLimit) == -1) {
                                    addAccount = false;
                                    break;  // From here it should go to the accountList For LOOP. For next account number of the same type.

                                } else {
                                    addAccount = true;
                                }
                            }
                        }
                        if (addAccount == true) {
                            npStatus = "Sub-standard";
                        }
                    }
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                npStatus = "Standard";
                List accountList = em.createNativeQuery("select custname,accstatus from accountmaster where acno = '" + acNo + "'").getResultList();

                Vector element = (Vector) accountList.get(0);
                String custname = element.get(0).toString();

                String accStatus = null;
                String presentStatus = interBranchFacade.fnGetLoanStatusTillDate(acNo, tillDate);
                if (presentStatus.equalsIgnoreCase("DOU")) {
                    accStatus = "12";
                } else if (presentStatus.equalsIgnoreCase("SUB")) {
                    accStatus = "11";
                } else if (presentStatus.equalsIgnoreCase("LOS")) {
                    accStatus = "13";
                } else if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                    accStatus = "1";
                } else {
                    accStatus = "1";
                }

                BigDecimal outStandBal = new BigDecimal(formatter.format(Double.parseDouble(loanIntRemote.outStandingAsOnDate(acNo, tillDate)))).abs();
                if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                    //For NPA Account.
                    npStatus = npaAccountAddition(accStatus, tillDate, acNo, custname, outStandBal);
                } else {
                    //For Standard Account.
                    List list = em.createNativeQuery("select date_format(min(duedt),'%Y%m%d') min_unpaid_dt,count(status) as unpaid_count,installamt "
                            + "from emidetails where acno = '" + acNo + "' and status = 'unpaid' and "
                            + "duedt < '" + tillDate + "' group by installamt").getResultList();
                    if (list.isEmpty()) {
                        if (!outStandBal.equals(new BigDecimal("0"))) {
                            List checklist = em.createNativeQuery("select * from emidetails where acno = '" + acNo + "'").getResultList();
                            if (checklist.isEmpty()) {
                                throw new ApplicationException("There is no data in emidetails for :" + acNo);
                            }
                        }
                    } else {
                        Vector listVec = (Vector) list.get(0);
                        String minUnPaidDt = listVec.get(0).toString();
                        Long dayDiff = CbsUtil.dayDiff(ymd.parse(minUnPaidDt), ymd.parse(tillDate));

                        list = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                        listVec = (Vector) list.get(0);
                        Long stdDays = Long.parseLong(listVec.get(1).toString());
                        if (dayDiff > stdDays) {
                            npStatus = "Sub-standard";
                        }
                    }
                }
            } else {
                npStatus = "Standard";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return npStatus;
    }

    public List<PrioritySectorPojo> getPrioritySector(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION,REFER_INDEX  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'PRIOR' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> resultList1 = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> resultList2 = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> resultList3 = new ArrayList<LoanMisCellaniousPojo>();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
//                    System.out.println("I "+i);
                    Long totAccountNo = 0l, nAccountNoSC = 0l, nAccountNoST = 0l, nAccountNoMinor = 0l;
                    Long totAccountNoForm = 0l, nAccountNoSCForm = 0l, nAccountNoSTForm = 0l, nAccountNoMinorForm = 0l;
                    BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0), nAmountSC = new BigDecimal(0), nAmountST = new BigDecimal(0), nAmountMinor = new BigDecimal(0);
                    BigDecimal totAmountForm = new BigDecimal(0), nAmountForm = new BigDecimal(0), nAmountSCForm = new BigDecimal(0), nAmountSTForm = new BigDecimal(0), nAmountMinorForm = new BigDecimal(0);
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
                    String fGNo = oss1Vect.get(18).toString();  //Sector
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString(); //SubSector
                    String referIndex = oss1Vect.get(24).toString();
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setNature(acNature);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(reportDt);
                    params.setToDate(reportDt);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setFromRange(rangeFrom);
                    params.setToRange(rangeTo);
                    params.setGlFromHead(fGNo);
                    params.setGlToHead(npaClassification);
                    params.setFlag(fSGNo);
                    if (sGNo != 0) {
                        if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                            /* For total */
                            resultList = new ArrayList<LoanMisCellaniousPojo>();
                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList.size(); k++) {
                                LoanMisCellaniousPojo val = resultList.get(k);
                                if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    totAmount = totAmount.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                } else {
                                    totAmount = totAmount.add(resultList.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    totAccountNo = totAccountNo + 1;
                                }
                            }
                            /* For SC */
                            resultList1 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList1 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "SC", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N", "", "N", "N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList1.size(); k++) {
                                LoanMisCellaniousPojo val = resultList1.get(k);
                                if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmountSC = nAmountSC.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                } else {
                                    nAmountSC = nAmountSC.add(resultList.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    nAccountNoSC = nAccountNoSC + 1;
                                }
                            }
                            /* For ST */
                            resultList2 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList2 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "ST", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList2.size(); k++) {
                                LoanMisCellaniousPojo val = resultList2.get(k);
                                if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmountST = nAmountST.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                } else {
                                    nAmountST = nAmountST.add(resultList.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    nAccountNoST = nAccountNoST + 1;
                                }
                            }
                            /* For Minorities */
                            resultList3 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList3 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "MC", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList3.size(); k++) {
                                LoanMisCellaniousPojo val = resultList3.get(k);
                                if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmountMinor = nAmountMinor.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                } else {
                                    nAmountMinor = nAmountMinor.add(resultList.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    nAccountNoMinor = nAccountNoMinor + 1;
                                }
                            }
                        }
                    }
                    PrioritySectorPojo pojo = new PrioritySectorPojo();
                    pojo.setReportName(reportName);
                    pojo.setsNo(i);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setTotAccountNo(totAccountNo);
                    pojo.setTotAmount(totAmount.abs().divide(repOpt));
                    pojo.setnAccountNoSC(nAccountNoSC);
                    pojo.setnAmountSC(nAmountSC.abs().divide(repOpt));
                    pojo.setnAccountNoST(nAccountNoST);
                    pojo.setnAmountST(nAmountST.abs().divide(repOpt));
                    pojo.setnAccountNoWk(nAccountNoMinor);
                    pojo.setnAmountWk(nAmountMinor.abs().divide(repOpt));
                    rbiPojoTable.add(pojo);
                }
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    PrioritySectorPojo priorPojo = rbiPojoTable.get(k);
                    BigDecimal bal = new BigDecimal("0.0");
                    if (!rbiPojoTable.get(k).getFormula().isEmpty()) {
                        List<PrioritySectorPojo> pojo1 = getValueFromFormula1(rbiPojoTable, priorPojo.getFormula());
//                        bal = bal.add(getValueFromFormula1(rbiPojoTable, priorPojo.getFormula()));
                        for (int l = 0; l < pojo1.size(); l++) {
                            bal = bal.add(pojo1.get(l).getTotAmount());
//                            priorPojo.setTotAmount(pojo1.get(l).getTotAmount());
//                            priorPojo.setnAmountSC(pojo1.get(l).getnAmountSC());
//                            priorPojo.setnAmountST(pojo1.get(l).getnAmountST());
//                            priorPojo.setnAmountMinor(pojo1.get(l).getnAmountMinor());
//                            priorPojo.setTotAccountNo(pojo1.get(l).getTotAccountNo());
//                            priorPojo.setnAccountNoSC(pojo1.get(l).getnAccountNoSC());
//                            priorPojo.setnAccountNoST(pojo1.get(l).getnAccountNoST());
//                            priorPojo.setnAccountNoMinor(pojo1.get(l).getnAccountNoMinor());
                        }
                        priorPojo.setTotAmount(bal);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<PrioritySectorPojo> getValueFromFormula1(List<PrioritySectorPojo> dataList, String formula) throws ApplicationException {
        try {

            String exp = "", exp1 = "", exp2 = "", exp3 = "", exp4 = "", exp5 = "", exp6 = "", exp7 = "";
            List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
            List<PrioritySectorPojo> rbiPojoTab = new ArrayList<PrioritySectorPojo>();
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                PrioritySectorPojo pojo2 = new PrioritySectorPojo();
                BigDecimal totAmt = new BigDecimal("0.0"), nAmtSc = new BigDecimal("0.0");
                BigDecimal nAmtST = new BigDecimal("0.0"), nAmtMinor = new BigDecimal("0.0");
                long toAc = 0l, nAcSc = 0l, nAcST = 0l, nAcMin = 0l;
                if (arr[i].contains(",")) {
                    String[] strArr = arr[i].split(",");
                    int[] arr1 = new int[strArr.length];
                    for (int l = 0; l < strArr.length; l++) {
                        arr1[l] = Integer.parseInt(strArr[l]);
                    }
                    rbiPojoTable = getOperandBalance1(dataList, arr[i]);
//                    for (int k = 0; k < rbiPojoTable.size(); k++) {
                    totAmt = rbiPojoTable.get(27).getTotAmount();
//                        totAmt = rbiPojoTable.get(k).getTotAmount();
//                    System.out.println("::Total Amount" + totAmt);
//                        }  
                    if (exp.equals("")) {
                        exp = exp + totAmt.toString();
                    } else {
                        exp = exp + " " + totAmt.toString();
                    }
                    totAmt = PostfixEvaluator.evaluate(exp);
//                        System.out.println("SC Amount Inside "+nAmtSc + "::ST Amount" +nAmtST);

//                    System.out.println("::Total Amount After" + totAmt);
                    pojo2.setTotAmount(totAmt);
                    rbiPojoTab.add(pojo2);
                } else {
                    if (exp.equals("")) {
                        exp = exp + arr[i];

                    } else {
                        exp = exp + " " + arr[i];
                    }
                    totAmt = PostfixEvaluator.evaluate(exp);
//                        System.out.println("SC Amount Inside "+nAmtSc + "::ST Amount" +nAmtST);

//                    System.out.println("::Total Amount After Formula" + totAmt);
                    pojo2.setTotAmount(totAmt);
                    rbiPojoTab.add(pojo2);
                }
            }
            return rbiPojoTab;
//            return PostfixEvaluator.evaluate(exp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private List<PrioritySectorPojo> getOperandBalance1(List<PrioritySectorPojo> dataList, String csvExp) throws ApplicationException {
        try {
            List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
            List<PrioritySectorPojo> rbiPojoTab = new ArrayList<PrioritySectorPojo>();
            BigDecimal totAmount = new BigDecimal("0"), nAmountSc = new BigDecimal("0"), nAmountST = new BigDecimal("0"), nAmountMinor = new BigDecimal("0");
            long totAcno = 0l, nAcNoSC = 0l, nAcNoST = 0l, nAcNoMinor = 0l;
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        totAmount = totAmount.add(pojo.getTotAmount());

                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            } else if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        totAmount = totAmount.add(pojo.getTotAmount());


                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            } else if (arr[1] != 0 && arr[2] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        totAmount = totAmount.add(pojo.getTotAmount());
                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            } else if (arr[1] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        totAmount = totAmount.add(pojo.getTotAmount());
                    }
                    rbiPojoTable.add(pojo);
                }
            } else {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0])) {
                        totAmount = totAmount.add(pojo.getTotAmount());
                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            }
            return rbiPojoTable;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public String npaAccountAddition(String accStatus, String tillDate, String acno, String custname, BigDecimal outStandBal) throws ApplicationException {
        String npaDt = "", newNpaDt = "";
        long dayDiff = 0, newDayDiff = 0;
        List limitList = null;
        Vector limitVec = null;
        String statusN = "";
        try {
            String npaDtQuery = "";
            if (accStatus.equalsIgnoreCase("11")) {
                npaDtQuery = "npadt";
            } else if (accStatus.equalsIgnoreCase("12")) {
                npaDtQuery = "dbtdt";
            } else if (accStatus.equalsIgnoreCase("13")) {
                npaDtQuery = "dcdt";
            }
            if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                limitList = em.createNativeQuery("select ifnull(date_format(" + npaDtQuery + ",'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                limitVec = (Vector) limitList.get(0);
                npaDt = limitVec.get(0).toString();
            }

            if (accStatus.equalsIgnoreCase("11")) {
                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                        + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                        + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('SUB'))").getResultList();
                limitVec = (Vector) limitList.get(0);
                long subDays = Long.parseLong(limitVec.get(1).toString());

                statusN = "Sub-standard";
                if (dayDiff > subDays) {
                    // Add for doubtful - upto 1 year
                    statusN = "D1";
                }
            } else if (accStatus.equalsIgnoreCase("12")) {
                //Pick all three doubtfull days and compare with dayDiff. Based on that fill the pojo for report only.
                dayDiff = CbsUtil.dayDiff(ymd.parse(npaDt), ymd.parse(tillDate));
                newNpaDt = npaDt;
                String cycle = "";
                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                        + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                        + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                        + "order by days").getResultList();
                if (limitList.isEmpty()) {
                    throw new ApplicationException("Please fill data in cbs_ref_rec_type.");
                }
                for (int j = 0; j < limitList.size(); j++) {
                    limitVec = (Vector) limitList.get(j);
                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                        cycle = limitVec.get(0).toString();
                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                        break;
                    } else {
                        cycle = "D3";
                    }
                    newDayDiff = Long.parseLong(limitVec.get(1).toString());
                }
                statusN = cycle;
            } else if (accStatus.equalsIgnoreCase("13")) {
                statusN = "Loss";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return statusN;
    }
}