/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.dto.report.PortFolioClassificationPojo;
import com.cbs.dto.report.Soss3PortFolioPojo;
import com.cbs.dto.report.PortFolioConPojo;
import com.cbs.dto.report.PortFolioLoanAdvPojo;
import com.cbs.dto.report.PortFolioOibaPojo;
import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaConsolidateAndAcWisePojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.PostfixEvaluator;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.cbs.dto.report.RbiSossPojo;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiSoss3ReportFacade")
public class RbiSoss3ReportFacade implements RbiSoss3ReportFacadeRemote {

    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalcFacade;
    @EJB
    RbiReportPartDFacadeRemote rbiReportPartD;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    private RbiReportFacadeRemote rbiRportRemote;
    @EJB
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    @EJB
    private NpaReportFacadeRemote npaRemote;
    @EJB
    private InvestmentReportMgmtFacadeRemote investRptMgmtFacade;
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
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public List<Soss3PortFolioPojo> getSOSS3(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<Soss3PortFolioPojo> Soss3PortFolioPojoTable = new ArrayList<Soss3PortFolioPojo>();
            List<PortFolioLoanAdvPojo> portFolioLoanAdvPojoTable = new ArrayList<PortFolioLoanAdvPojo>();
            List<PortFolioOibaPojo> portFolioOibaPojoTable = new ArrayList<PortFolioOibaPojo>();
            List<PortFolioConPojo> portFolioConPojoTable = new ArrayList<PortFolioConPojo>();
            List<PortFolioConPojo> portFolioBalanceSheetPojoTable = new ArrayList<PortFolioConPojo>();
            List<PortFolioConPojo> portFolioInvestmentPojoTable = new ArrayList<PortFolioConPojo>();
            List<PortFolioConPojo> portFolioNonSlrSecurityPojoTable = new ArrayList<PortFolioConPojo>();
            List<PortFolioClassificationPojo> portFolioClassificationPojoTable = new ArrayList<PortFolioClassificationPojo>();
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
            List ossQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                    + "report_name = '" + reptName + "' and '" + dt + "' between EFF_FR_DT and EFF_TO_DT order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned), cast(ssss_gno as unsigned)").getResultList();

            if (ossQuery.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                String npaAcDetails = "";
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
                    BigDecimal bal = new BigDecimal("0");
                    BigDecimal loanAmt = new BigDecimal("0");
                    BigDecimal odCcAmt = new BigDecimal("0");
                    BigDecimal billPuchasedAmt = new BigDecimal("0");
                    BigDecimal commPaperAmt = new BigDecimal("0");
                    BigDecimal notesBondsAmt = new BigDecimal("0");
                    BigDecimal interBankAmt = new BigDecimal("0");
                    BigDecimal leaseReceivablesAmt = new BigDecimal("0");
                    BigDecimal slrAmt = new BigDecimal("0");
                    String tmpAcNature = acNature, tmpAcType = acType;
                    if (referIndex.equalsIgnoreCase("Y")) {
                        List distDescList = em.createNativeQuery("select distinct description from cbs_ho_rbi_stmt_report where report_name  = '" + referContent + "'").getResultList();
                        if (distDescList.size() > 0) {
                            for (int j = 0; j < distDescList.size(); j++) {
                                Vector distDescVect = (Vector) distDescList.get(j);

                                List distOssSubQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                                        + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                                        + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                                        + "report_name  = '" + referContent + "' and description = '" + distDescVect.get(0).toString() + "' ").getResultList();

                                if (distOssSubQuery.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
                                } else {
                                    for (int k = 0; k < distOssSubQuery.size(); k++) {
                                        Vector distOssSubVect = (Vector) distOssSubQuery.get(k);
                                        String distAcNature = distOssSubVect.get(10).toString();
                                        String distAcType = distOssSubVect.get(11).toString();
                                        String distGlHeadFrom = distOssSubVect.get(12).toString();
                                        String distGlHeadTo = distOssSubVect.get(13).toString();
                                        // String distRangeBaseOn = distOssSubVect.get(14).toString();
                                        // String distRangeFrom = distOssSubVect.get(15).toString();
                                        // String distRangeTo = distOssSubVect.get(16).toString();
                                        acNature = tmpAcNature;
                                        acType = tmpAcType;
                                        if ((!((distAcNature == null) || distAcNature.equalsIgnoreCase("")))) {
                                            if (acNature.equalsIgnoreCase("NON-NPA") || acNature.equalsIgnoreCase("NPA") || acNature.equalsIgnoreCase("OVER")) {
                                                acNature = acNature.concat(".").concat(distAcNature);
                                            } else {
                                                acNature = distAcNature;
                                            }
                                            acType = "";
                                            glHeadFrom = "";
                                            glHeadTo = "";
                                        } else if ((!((distAcType == null) || distAcType.equalsIgnoreCase("")))) {
                                            if (acType.equalsIgnoreCase("NON-NPA") || acType.equalsIgnoreCase("NPA") || acType.equalsIgnoreCase("OVER")) {
                                                acType = acType.concat(".").concat(distAcType);
                                            } else {
                                                acType = distAcType;
                                            }
                                            acNature = "";
                                            glHeadFrom = "";
                                            glHeadTo = "";
                                        } else if ((!((distGlHeadFrom == null) || (distGlHeadFrom.equalsIgnoreCase(""))) && !((distGlHeadTo == null) || (distGlHeadTo.equalsIgnoreCase(""))))) {
                                            glHeadFrom = distGlHeadFrom;
                                            glHeadTo = distGlHeadTo;
                                            acNature = "";
                                            acType = "";
                                        }
                                        /**     
                                         * ***************************
                                         */
                                        Integer cl;
                                        if (classification.equalsIgnoreCase("L")) {
                                            cl = 1;
                                        } else {
                                            cl = -1;
                                        }
                                        AdditionalStmtPojo params = new AdditionalStmtPojo();
                                        params.setAcType(acType);
                                        params.setBrnCode(orgnCode);
                                        params.setClassification(classification);
                                        params.setDate(dt);
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
                                        noOfAc = 0l;
                                        bal = new BigDecimal("0");
                                        AcctBalPojo acctBal = new AcctBalPojo();
                                        List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                                        if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                                            if (!tmpAcNature.equalsIgnoreCase("NON-NPA")) {
                                                if (!tmpAcNature.equalsIgnoreCase("NPA")) {
                                                    if (!tmpAcNature.equalsIgnoreCase("OVER")) {
                                                        GlHeadPojo glHeadPojo = new GlHeadPojo();
                                                        glPojoList = rbiRportRemote.getGLHeadAndBal(params);
                                                        if (glPojoList.size() > 0) {
                                                            /* If GL Head Series have multiple GL Head */
                                                            for (int m = 0; m < glPojoList.size(); m++) {
                                                                GlHeadPojo glHeadPo = glPojoList.get(m);
                                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                                    noOfAc = noOfAc + (long) glPojoList.size();
                                                                    rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
                                                                } else {
                                                                    bal = bal.add(glHeadPo.getBalance());
                                                                    rbiSossPojo.setAmt(bal);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                                            if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
//                                                acctBal = getAcctsAndBal(params);
                                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                                    noOfAc = acctBal.getNumberOFAcct();
                                                    noOfAc = rbiRportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                                } else {
                                                    acctBal = rbiRportRemote.getAcctsAndBal(params);
                                                    bal = acctBal.getBalance();
                                                }
                                            } else if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                acctBal = rbiRportRemote.getAcctsAndBalAmtRangeWise(params);
                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                    noOfAc = acctBal.getNumberOFAcct();
                                                } else {
                                                    bal = acctBal.getBalance();
                                                }
                                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                                if (acNature.contains("NPA")) {
                                                    acctBal = rbiRportRemote.getNpaBasedOnAllNpaStatus(params);
                                                } else if (acNature.contains("OVER")) {
                                                    List<GlHeadPojo> headPojoList = null;
                                                    if (rangeBaseOn.equalsIgnoreCase("D")) {
                                                        headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.dateAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.dateAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                    } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                                        headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.monthAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.monthAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                                        headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.yearAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.yearAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                    }
                                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                                        noOfAc = new Long(headPojoList.size());
                                                    } else {
                                                        for (int l = 0; l < headPojoList.size(); l++) {
                                                            GlHeadPojo headPojo = headPojoList.get(l);
                                                            bal = bal.add(headPojo.getBalance());
                                                        }
                                                    }
                                                    acctBal = new AcctBalPojo();
                                                    acctBal.setNumberOFAcct(noOfAc);
                                                    acctBal.setBalance(bal);
//                                                    System.out.println(acNature+"; Bal:"+bal);                                                
                                                } else {
                                                    acctBal = rbiRportRemote.getAcctsAndBalTimeRangeWise(params);
                                                }
                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                    noOfAc = acctBal.getNumberOFAcct();
                                                } else {
                                                    bal = acctBal.getBalance();
                                                }
                                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                                acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                    noOfAc = acctBal.getNumberOFAcct();
                                                } else {
                                                    bal = acctBal.getBalance();
                                                }
                                            }
                                        } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                            if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
//                                                acctBal = getAcctsAndBal(params);
                                                if (countApplicable.equalsIgnoreCase("Y")) {
//                                                    noOfAc = acctBal.getNumberOFAcct();
                                                    noOfAc = rbiRportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                                } else {
                                                    acctBal = rbiRportRemote.getAcctsAndBal(params);
                                                    bal = acctBal.getBalance();
                                                }
                                            } else if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                acctBal = rbiRportRemote.getAcctsAndBalAmtRangeWise(params);
                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                    noOfAc = acctBal.getNumberOFAcct();
                                                } else {
                                                    bal = acctBal.getBalance();
                                                }
                                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                                if (acType.contains("NPA")) {
                                                    acctBal = rbiRportRemote.getNpaBasedOnAllNpaStatus(params);
                                                } else if (acType.contains("OVER")) {
                                                    List<GlHeadPojo> headPojoList = null;
                                                    if (rangeBaseOn.equalsIgnoreCase("D")) {
                                                        headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.dateAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.dateAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                    } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                                        headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.monthAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.monthAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                                        headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.yearAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.yearAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                    }
                                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                                        noOfAc = new Long(headPojoList.size());
                                                    } else {
                                                        for (int l = 0; l < headPojoList.size(); l++) {
                                                            GlHeadPojo headPojo = headPojoList.get(l);
//                                                            bal = bal.add(new BigDecimal(loanInterestCalcFacade.outStandingAsOnDate(headPojo.getGlHead(), dt)));
                                                            bal = bal.add(headPojo.getBalance());
//                                                            System.out.println(acType+": acno:"+headPojo.getGlHead()+"; Name:"+headPojo.getGlName()+"; Bal:"+new BigDecimal(loanInterestCalcFacade.outStandingAsOnDate(headPojo.getGlHead(), dt)));
//                                                            System.out.println(acType+": acno:"+headPojo.getGlHead()+"; Name:"+headPojo.getGlName()+"; Bal:"+headPojo.getBalance());
                                                        }
                                                    }
                                                    acctBal = new AcctBalPojo();
                                                    acctBal.setNumberOFAcct(noOfAc);
                                                    acctBal.setBalance(bal);
//                                                    System.out.println(acType+"; Bal:"+bal);                                                
                                                } else {
                                                    acctBal = rbiRportRemote.getAcctsAndBalTimeRangeWise(params);
                                                }
                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                    noOfAc = acctBal.getNumberOFAcct();
                                                } else {
                                                    bal = acctBal.getBalance();
                                                }
                                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                                acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                    noOfAc = acctBal.getNumberOFAcct();
                                                } else {
                                                    bal = acctBal.getBalance();
                                                }
                                            }
                                        }
                                        if (distDescVect.get(0).toString().equalsIgnoreCase("Loans")) {
                                            loanAmt = loanAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Overdrafts and Cash Credits")) {
                                            odCcAmt = odCcAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Bills Purchased and Discounted")) {
                                            billPuchasedAmt = billPuchasedAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Commercial Paper")) {
                                            commPaperAmt = commPaperAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Notes and bonds of corporates")) {
                                            notesBondsAmt = notesBondsAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Inter-Bank Assets")) {
                                            interBankAmt = interBankAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Lease receivables")) {
                                            leaseReceivablesAmt = leaseReceivablesAmt.add(bal);
                                        } else if (distDescVect.get(0).toString().equalsIgnoreCase("Others including SLR Investments")) {
                                            slrAmt = slrAmt.add(bal);
                                        }
                                        /**
                                         * ***************************
                                         */
                                    }
                                }
                            }
                        }
                    } else if (rangeBaseOn.equalsIgnoreCase("E")) {
                        AcctBalPojo acctBal = new AcctBalPojo();
                        /*E for Emi Based*/
                        List<OverdueEmiReportPojo> headPojoList = null;
                        headPojoList = overDueRemote.getOverdueEmiReport(1, 0, "", "All", Integer.valueOf(formatter.format(Double.parseDouble(ossVect.get(15).toString()))), Integer.valueOf(formatter.format(Double.parseDouble(ossVect.get(16).toString()))), dt, "0A", "A",true,"","Y");
                        if (countApplicable.equalsIgnoreCase("Y")) {
                            noOfAc = new Long(headPojoList.size());
                        } else {
                            for (int l = 0; l < headPojoList.size(); l++) {
                                OverdueEmiReportPojo headPojo = headPojoList.get(l);
                                bal = bal.add(headPojo.getAmountOverdue());
                            }
                        }
                        acctBal = new AcctBalPojo();
                        acctBal.setNumberOFAcct(noOfAc);
                        acctBal.setBalance(bal);
                        if (countApplicable.equalsIgnoreCase("Y")) {
                            noOfAc = acctBal.getNumberOFAcct();
                        } else {
                            loanAmt = loanAmt.add(acctBal.getBalance().multiply(new BigDecimal("-1")));
                        }
                    } else if (rangeBaseOn.equalsIgnoreCase("NE")) {
                        AcctBalPojo acctBal = new AcctBalPojo();
                        /*NE for Non Emi Based*/
                        List<OverdueNonEmiResultList> headPojoList = null;
                        headPojoList = overDueRemote.getOverDueNonEmi(acNature, acType.equalsIgnoreCase("") ? "ALL" : acType, dt, "0A","Y");
                        if (countApplicable.equalsIgnoreCase("Y")) {
                            noOfAc = new Long(headPojoList.size());
                        } else {
                            for (int l = 0; l < headPojoList.size(); l++) {
                                OverdueNonEmiResultList headPojo = headPojoList.get(l);
                                bal = bal.add(new BigDecimal(headPojo.getOverDue()));
                            }
                        }
                        acctBal = new AcctBalPojo();
                        acctBal.setNumberOFAcct(noOfAc);
                        acctBal.setBalance(bal);
                        if (countApplicable.equalsIgnoreCase("Y")) {
                            noOfAc = acctBal.getNumberOFAcct();
                        } else {
                            if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                loanAmt = loanAmt.add(acctBal.getBalance().multiply(new BigDecimal("-1")));
                            } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                odCcAmt = odCcAmt.add(acctBal.getBalance().multiply(new BigDecimal("-1")));
                            }
                        }
                    }

                    PortFolioLoanAdvPojo paramsLoanAdv = new PortFolioLoanAdvPojo();
                    paramsLoanAdv.setsNo(sNo);
                    paramsLoanAdv.setReportName(reportName);
                    paramsLoanAdv.setDescription(description);
                    paramsLoanAdv.setgNo(gNo);//gNo);
                    paramsLoanAdv.setsGNo(sGNo);//sGNo);
                    paramsLoanAdv.setSsGNo(ssGNo);//ssGNo);
                    paramsLoanAdv.setSssGNo(sssGNo);//sssGNo);
                    paramsLoanAdv.setSsssGNo(ssssGNo);//ssssGNo);
                    paramsLoanAdv.setClassification(classification);//classification);
                    paramsLoanAdv.setCountApplicable(countApplicable);//countApplicable);
                    paramsLoanAdv.setAcNature(acNature);//acNature);
                    paramsLoanAdv.setAcType(acType);//acType);
                    paramsLoanAdv.setNpaClassification(npaClassification);//npaClassification);
                    paramsLoanAdv.setGlheadFrom(glHeadFrom);//glheadFrom);
                    paramsLoanAdv.setGlHeadTo(glHeadTo);//glHeadTo);
                    paramsLoanAdv.setRangeBaseOn(rangeBaseOn);//rangeBaseOn);
                    paramsLoanAdv.setRangeFrom(new BigDecimal(rangeFrom));//rangeFrom);
                    paramsLoanAdv.setRangeTo(new BigDecimal(rangeTo));//rangeTo);
                    paramsLoanAdv.setFormula(formula);//formula);
                    paramsLoanAdv.setfGNo(fGNo);//fGNo);
                    paramsLoanAdv.setfSGNo(fSGNo);//fSGNo);
                    paramsLoanAdv.setfSsGNo(fSsGNo);//fSsGNo);
                    paramsLoanAdv.setfSssGNo(fSssGNo);//fSssGNo);
                    paramsLoanAdv.setfSsssGNo(fSsssGNo);//fSsssGNo);
                    paramsLoanAdv.setLoanAmt(new BigDecimal(formatter.format(loanAmt.divide(repOpt).doubleValue() * -1)));//loanAmt);
                    paramsLoanAdv.setOdCcAmt(new BigDecimal(formatter.format(odCcAmt.divide(repOpt).doubleValue() * -1)));//odCcAmt);
                    paramsLoanAdv.setBillDiscAmt(new BigDecimal(formatter.format(billPuchasedAmt.divide(repOpt).doubleValue() * -1)));//billDiscAmt);
                    portFolioLoanAdvPojoTable.add(paramsLoanAdv);

                    PortFolioOibaPojo paramsOiba = new PortFolioOibaPojo();
                    paramsOiba.setsNo(sNo);
                    paramsOiba.setReportName(reportName);
                    if (gNo.equals(1) && sGNo.equals(0) && ssGNo.equals(0) && sssGNo.equals(0) && ssssGNo.equals(0)) {
                        paramsOiba.setDescription("Portfolio Analysis-Other Interest bearing assets (OIBA)");
                    } else {
                        paramsOiba.setDescription(description);
                    }
                    paramsOiba.setgNo(gNo);//gNo);
                    paramsOiba.setsGNo(sGNo);//sGNo);
                    paramsOiba.setSsGNo(ssGNo);//ssGNo);
                    paramsOiba.setSssGNo(sssGNo);//sssGNo);
                    paramsOiba.setSsssGNo(ssssGNo);//ssssGNo);
                    paramsOiba.setClassification(classification);//classification);
                    paramsOiba.setCountApplicable(countApplicable);//countApplicable);
                    paramsOiba.setAcNature(acNature);//acNature);
                    paramsOiba.setAcType(acType);//acType);
                    paramsOiba.setNpaClassification(npaClassification);//npaClassification);
                    paramsOiba.setGlheadFrom(glHeadFrom);//glheadFrom);
                    paramsOiba.setGlHeadTo(glHeadTo);//glHeadTo);
                    paramsOiba.setRangeBaseOn(rangeBaseOn);//rangeBaseOn);
                    paramsOiba.setRangeFrom(new BigDecimal(rangeFrom));//rangeFrom);
                    paramsOiba.setRangeTo(new BigDecimal(rangeTo));//rangeTo);
                    paramsOiba.setFormula(formula);//formula);
                    paramsOiba.setfGNo(fGNo);//fGNo);
                    paramsOiba.setfSGNo(fSGNo);//fSGNo);
                    paramsOiba.setfSsGNo(fSsGNo);//fSsGNo);
                    paramsOiba.setfSssGNo(fSssGNo);//fSssGNo);
                    paramsOiba.setfSsssGNo(fSsssGNo);//fSsssGNo);
                    paramsOiba.setCommAmt(new BigDecimal(formatter.format(commPaperAmt.divide(repOpt).doubleValue() * -1)));//commAmt,,,,);
                    paramsOiba.setNoteAmt(new BigDecimal(formatter.format(notesBondsAmt.divide(repOpt).doubleValue() * -1)));//noteAmt);
                    paramsOiba.setInterAmt(new BigDecimal(formatter.format(interBankAmt.divide(repOpt).doubleValue() * -1)));//interAmt);
                    paramsOiba.setLeaseAmt(new BigDecimal(formatter.format(leaseReceivablesAmt.divide(repOpt).doubleValue() * -1)));//leaseAmt);
                    paramsOiba.setSlrAmt(new BigDecimal(formatter.format(slrAmt.divide(repOpt).doubleValue() * -1)));//slrAmt);
                    portFolioOibaPojoTable.add(paramsOiba);

                    PortFolioConPojo paramsCon = new PortFolioConPojo();
                    paramsCon.setsNo(sNo);
                    paramsCon.setReportName(reportName);
                    if (gNo.equals(1) && sGNo.equals(0) && ssGNo.equals(0) && sssGNo.equals(0) && ssssGNo.equals(0)) {
                        paramsCon.setDescription("Consolidated data from columns of previous two tables taken directly ie. Total Loan Assets & Total OIBA*");
                    } else {
                        paramsCon.setDescription(description);
                    }
                    paramsCon.setgNo(gNo);//gNo);
                    paramsCon.setsGNo(sGNo);//sGNo);
                    paramsCon.setSsGNo(ssGNo);//ssGNo);
                    paramsCon.setSssGNo(sssGNo);//sssGNo);
                    paramsCon.setSsssGNo(ssssGNo);//ssssGNo);
                    paramsCon.setClassification(classification);//classification);
                    paramsCon.setCountApplicable(countApplicable);//countApplicable);
                    paramsCon.setAcNature(acNature);//acNature);
                    paramsCon.setAcType(acType);//acType);
                    paramsCon.setNpaClassification(npaClassification);//npaClassification);
                    paramsCon.setGlheadFrom(glHeadFrom);//glheadFrom);
                    paramsCon.setGlHeadTo(glHeadTo);//glHeadTo);
                    paramsCon.setRangeBaseOn(rangeBaseOn);//rangeBaseOn);
                    paramsCon.setRangeFrom(new BigDecimal(rangeFrom));//rangeFrom);
                    paramsCon.setRangeTo(new BigDecimal(rangeTo));//rangeTo);
                    paramsCon.setFormula(formula);//formula);
                    paramsCon.setfGNo(fGNo);//fGNo);
                    paramsCon.setfSGNo(fSGNo);//fSGNo);
                    paramsCon.setfSsGNo(fSsGNo);//fSsGNo);
                    paramsCon.setfSssGNo(fSsGNo);//fSssGNo);
                    paramsCon.setfSsssGNo(fSsssGNo);//fSsssGNo);
                    paramsCon.setLoanAmt(new BigDecimal(formatter.format(loanAmt.divide(repOpt).doubleValue() * -1)));//loanAmt);
                    paramsCon.setOdCcAmt(new BigDecimal(formatter.format(odCcAmt.divide(repOpt).doubleValue() * -1)));//odCcAmt);
                    paramsCon.setBillDiscAmt(new BigDecimal(formatter.format(billPuchasedAmt.divide(repOpt).doubleValue() * -1)));//billDiscAmt);
                    paramsCon.setCommAmt(new BigDecimal(formatter.format(commPaperAmt.divide(repOpt).doubleValue() * -1)));//commAmt,,,,);
                    paramsCon.setNoteAmt(new BigDecimal(formatter.format(notesBondsAmt.divide(repOpt).doubleValue() * -1)));//noteAmt);
                    paramsCon.setInterAmt(new BigDecimal(formatter.format(interBankAmt.divide(repOpt).doubleValue() * -1)));//interAmt);
                    paramsCon.setLeaseAmt(new BigDecimal(formatter.format(leaseReceivablesAmt.divide(repOpt).doubleValue() * -1)));//leaseAmt);
                    paramsCon.setSlrAmt(new BigDecimal(formatter.format(slrAmt.divide(repOpt).doubleValue() * -1)));//slrAmt);
                    portFolioConPojoTable.add(paramsCon);
                }
                /*
                 * Formula implementation of Loan & Advances
                 */
                for (int m = 0; m < portFolioLoanAdvPojoTable.size(); m++) {
                    PortFolioLoanAdvPojo rbiSossPojo = portFolioLoanAdvPojoTable.get(m);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal loanBal = new BigDecimal("0"), odCcBal = new BigDecimal("0"), billPurAmt = new BigDecimal("0");
                        loanBal = loanBal.add(getValueFromFormulaLoanAdv(portFolioLoanAdvPojoTable, rbiSossPojo.getFormula(), "loanAmt"));
                        odCcBal = odCcBal.add(getValueFromFormulaLoanAdv(portFolioLoanAdvPojoTable, rbiSossPojo.getFormula(), "odCcAmt"));
                        billPurAmt = billPurAmt.add(getValueFromFormulaLoanAdv(portFolioLoanAdvPojoTable, rbiSossPojo.getFormula(), "billDiscAmt"));
                        rbiSossPojo.setLoanAmt(loanBal);
                        rbiSossPojo.setOdCcAmt(odCcBal);
                        rbiSossPojo.setBillDiscAmt(billPurAmt);
                    }
                }
                /*
                 * Formula implementation of Oiba
                 */
                for (int m = 0; m < portFolioOibaPojoTable.size(); m++) {
                    PortFolioOibaPojo rbiSossPojo = portFolioOibaPojoTable.get(m);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal commAmt = new BigDecimal("0"), noteAmt = new BigDecimal("0"), interAmt = new BigDecimal("0"), leaseAmt = new BigDecimal("0"), slrAmt = new BigDecimal("0");
                        commAmt = commAmt.add(getValueFromFormulaOiba(portFolioOibaPojoTable, rbiSossPojo.getFormula(), "commAmt"));
                        noteAmt = noteAmt.add(getValueFromFormulaOiba(portFolioOibaPojoTable, rbiSossPojo.getFormula(), "noteAmt"));
                        interAmt = interAmt.add(getValueFromFormulaOiba(portFolioOibaPojoTable, rbiSossPojo.getFormula(), "interAmt"));
                        leaseAmt = leaseAmt.add(getValueFromFormulaOiba(portFolioOibaPojoTable, rbiSossPojo.getFormula(), "leaseAmt"));
                        slrAmt = slrAmt.add(getValueFromFormulaOiba(portFolioOibaPojoTable, rbiSossPojo.getFormula(), "slrAmt"));
                        rbiSossPojo.setCommAmt(commAmt);
                        rbiSossPojo.setNoteAmt(noteAmt);
                        rbiSossPojo.setInterAmt(interAmt);
                        rbiSossPojo.setLeaseAmt(leaseAmt);
                        rbiSossPojo.setSlrAmt(slrAmt);
                    }
                }
                /*
                 * Formula implementation of Consolidate Loan & Advances and Oiba
                 */
                for (int m = 0; m < portFolioConPojoTable.size(); m++) {
                    PortFolioConPojo rbiSossPojo = portFolioConPojoTable.get(m);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal loanBal = new BigDecimal("0"), odCcBal = new BigDecimal("0"), billPurAmt = new BigDecimal("0");
                        BigDecimal commAmt = new BigDecimal("0"), noteAmt = new BigDecimal("0"), interAmt = new BigDecimal("0"), leaseAmt = new BigDecimal("0"), slrAmt = new BigDecimal("0");
                        loanBal = loanBal.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "loanAmt"));
                        odCcBal = odCcBal.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "odCcAmt"));
                        commAmt = commAmt.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "commAmt"));
                        noteAmt = noteAmt.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "noteAmt"));
                        interAmt = interAmt.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "interAmt"));
                        leaseAmt = leaseAmt.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "leaseAmt"));
                        slrAmt = slrAmt.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "slrAmt"));
                        billPurAmt = billPurAmt.add(getValueFromFormulaCon(portFolioConPojoTable, rbiSossPojo.getFormula(), "billDiscAmt"));
                        rbiSossPojo.setLoanAmt(loanBal);
                        rbiSossPojo.setOdCcAmt(odCcBal);
                        rbiSossPojo.setBillDiscAmt(billPurAmt);
                        rbiSossPojo.setCommAmt(commAmt);
                        rbiSossPojo.setNoteAmt(noteAmt);
                        rbiSossPojo.setInterAmt(interAmt);
                        rbiSossPojo.setLeaseAmt(leaseAmt);
                        rbiSossPojo.setSlrAmt(slrAmt);
                    }
                }
            }

            /*
             * PART A CLASSIFICATION OF LOAN AND ADVANCES (including Bill Credit)
             */
            ossQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                    + "report_name = 'SOSS3.PART-A' order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned), classification desc, sno ").getResultList();
            List<RbiSossPojo> oss7PartBList = new ArrayList<RbiSossPojo>();
            List code = common.getCode("TIER2");
            if (!code.isEmpty()) {
                List<String> dates = new ArrayList<String>();
                dates.add(dt);
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
                List chk1 = em.createNativeQuery("SELECT  distinct(FORMULA) FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'SOSS3.PART-A' and '" + dt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                        + "REPORT_NAME,CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                        + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
                if (!chk1.isEmpty()) {
                    for (int k = 0; k < chk1.size(); k++) {
                        Vector vect = (Vector) chk1.get(k);
                        String chk = vect.get(0).toString();
                        if (chk.contains("XBRLOSS7")) {
                            oss7PartBList = rbiQuaterly.getOss7PartBAndPartCSec2("XBRLOSS7-PART-B", dt, orgnCode, new BigDecimal("1"), "Y", osBlancelist);
                            break;
                        } else if (chk.contains("OSS7")) {
                            oss7PartBList = rbiQuaterly.getOss7PartBAndPartCSec2("OSS7-PART-B", dt, orgnCode, new BigDecimal("1"), "Y", osBlancelist);
                            break;
                        }
                    }
                }
            }
            if (ossQuery.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT for SOSS3");
            } else {
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
                    //   String referIndex = ossVect.get(24).toString();
                    //   String referContent = ossVect.get(25).toString();

                    Long noOfAc = 0l;
                    BigDecimal bal = new BigDecimal("0");
                    BigDecimal grossAmt = new BigDecimal("0");
                    BigDecimal provAmt = new BigDecimal("0");

                    if ((!((acNature == null) || acNature.equalsIgnoreCase("")))) {
                        acNature = acNature;
                        acType = "";
                        glHeadFrom = "";
                        glHeadTo = "";
                    } else if ((!((acType == null) || acType.equalsIgnoreCase("")))) {
                        acType = acType;
                        acNature = "";
                        glHeadFrom = "";
                        glHeadTo = "";
                    } else if ((!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase(""))))) {
                        glHeadFrom = glHeadFrom;
                        glHeadTo = glHeadTo;
                        acNature = "";
                        acType = "";
                    }
                    /**
                     * ***************************
                     */
//                    Integer cl;
//                    if (classification.equalsIgnoreCase("A")) {
//                        cl = -1;
//                    } else {
//                        cl = 1;
//                    }
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(dt);
                    params.setToDate(dt);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setNpaClassification(npaClassification);
                    params.setFlag("");
                    params.setNpaAcList(resultList);
                    noOfAc = 0l;
                    bal = new BigDecimal("0");
                    // AcctBalPojo acctBal = new AcctBalPojo();
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                    if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
//                        System.out.println("class:"+classification+"acNature:"+acNature+"acType:"+acType);
                        if (fSsssGNo.equalsIgnoreCase("Y")) {
                            NpaConsolidateAndAcWisePojo NpaConsolidateAndAcWise = rbiRportRemote.getAmountBasedOnNpaClassification(params);
                            for (int k = 0; k < NpaConsolidateAndAcWise.getPortConAcctBalList().size(); k++) {
                                bal = bal.add(NpaConsolidateAndAcWise.getPortConAcctBalList().get(k).getBalance());
//                                System.out.println("bal: "+acNature+": "+bal);
                            }
                        } else {
                            bal = rbiRportRemote.provisionAccordingToLoan(params);
                        }
//                        bal = provisionAccordingToLoan(params); 
//                        System.out.println("Class:" + classification + "acNature:" + acNature + "; Bal:" + bal);
                    } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
//                        System.out.println("class:"+classification+"acNature:"+acNature+"acType:"+acType);
                        if (fSsssGNo.equalsIgnoreCase("Y")) {
                            NpaConsolidateAndAcWisePojo NpaConsolidateAndAcWise = rbiRportRemote.getAmountBasedOnNpaClassification(params);
                            for (int k = 0; k < NpaConsolidateAndAcWise.getPortConAcctBalList().size(); k++) {
                                bal = bal.add(NpaConsolidateAndAcWise.getPortConAcctBalList().get(k).getBalance());
//                            System.out.println("bal: "+acNature+": "+bal);
                            }
//                            AcctBalPojo balPojo = getAmountBasedOnNpaClassification(params);
//                            bal = balPojo.getBalance(); 
//                            System.out.println("bal:"+acType+": "+bal);
                        } else {
                            bal = rbiRportRemote.provisionAccordingToLoan(params);
                        }
//                        bal = provisionAccordingToLoan(params);
//                        System.out.println("Class:" + classification + "acType:" + acType + "; Bal:" + bal);
                    } else if (!(fSssGNo.equalsIgnoreCase("GLPD")) && (!fSsssGNo.equalsIgnoreCase("Y"))
                            && (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase(""))))) {
                        // GlHeadPojo glHeadPojo = new GlHeadPojo();
                        glPojoList = rbiRportRemote.getGLHeadAndBal(params);
                        if (glPojoList.size() > 0) {
                            /* If GL Head Series have multiple GL Head */
                            for (int m = 0; m < glPojoList.size(); m++) {
                                GlHeadPojo glHeadPo = glPojoList.get(m);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = noOfAc + (long) glPojoList.size();
                                    rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
                                } else {
                                    bal = bal.add(glHeadPo.getBalance());
                                    rbiSossPojo.setAmt(bal);
                                }
                            }
                        }
                    }
                    grossAmt = grossAmt.add(bal);
//                    System.out.println("SNO: "+sNo+"; acNature:"+acNature+" & "+acType+" ; bal: "+bal);
                    if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA") || params.getNpaClassification().equals("11") || params.getNpaClassification().equals("12") || params.getNpaClassification().equals("13")) {
//                        System.out.println("sno:"+sNo);
                        if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
                            params.setFlag("P");
                        } else {
                            params.setFlag("P");
                        }
                        provAmt = rbiRportRemote.provisionAccordingToLoan(params);
                    } else if ((fSssGNo.equalsIgnoreCase("GLPD"))
                            && (params.getClassification().equalsIgnoreCase("L") || params.getClassification().equalsIgnoreCase("A") || !((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase(""))))) {
                        params.setFlag("GL");
                        /* For Implementation of the GL Head Provision instead of Delinquency Frequency */
                        glPojoList = rbiRportRemote.getGLHeadAndBal(params);
                        if (glPojoList.size() > 0) {
                            /* If GL Head Series have multiple GL Head */
                            for (int m = 0; m < glPojoList.size(); m++) {
                                GlHeadPojo glHeadPo = glPojoList.get(m);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = noOfAc + (long) glPojoList.size();
                                    rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
                                } else {
                                    bal = bal.add(glHeadPo.getBalance());
                                    rbiSossPojo.setAmt(bal);
                                }
                            }
                        }
                        provAmt = bal;
                    } else if (params.getClassification().equalsIgnoreCase("A") && (!params.getNature().equals("") || !params.getAcType().equals(""))) {
                        params.setFlag("P");

                        provAmt = rbiRportRemote.provisionAccordingToLoan(params);
                    } else if (!formula.isEmpty()) {
                        if (formula.contains("OSS7")) {
                            String[] strArr = formula.split("#");
                            bal = bal.add(rbiRportRemote.getValueFromFormula(oss7PartBList, strArr[1]));
                        }
                        if (params.getClassification().equalsIgnoreCase("L")) {
                            bal = bal.multiply(new BigDecimal("-1"));
                            grossAmt = bal;
                            provAmt = bal.multiply(new BigDecimal(fSsGNo).divide(new BigDecimal("100"))).multiply(new BigDecimal("-1"));
                        } else if (params.getClassification().equalsIgnoreCase("A")) {
                            grossAmt = bal;
                            provAmt = bal.multiply(new BigDecimal(fSsGNo).divide(new BigDecimal("100"))).multiply(new BigDecimal("-1"));
                        }
                    }
                    /**
                     * ***************************
                     */
                    PortFolioClassificationPojo paramsClassification = new PortFolioClassificationPojo();
                    paramsClassification.setsNo(sNo);
                    paramsClassification.setReportName(reportName);
                    paramsClassification.setDescription(description);
                    paramsClassification.setgNo(gNo);//gNo);
                    paramsClassification.setsGNo(sGNo);//sGNo);
                    paramsClassification.setSsGNo(ssGNo);//ssGNo);
                    paramsClassification.setSssGNo(sssGNo);//sssGNo);
                    paramsClassification.setSsssGNo(ssssGNo);//ssssGNo);
                    paramsClassification.setClassification(classification);//classification);
                    paramsClassification.setCountApplicable(countApplicable);//countApplicable);
                    paramsClassification.setAcNature(acNature);//acNature);
                    paramsClassification.setAcType(acType);//acType);
                    paramsClassification.setNpaClassification(npaClassification);//npaClassification);
                    paramsClassification.setGlheadFrom(glHeadFrom);//glheadFrom);
                    paramsClassification.setGlHeadTo(glHeadTo);//glHeadTo);
                    paramsClassification.setRangeBaseOn(rangeBaseOn);//rangeBaseOn);
                    paramsClassification.setRangeFrom(new BigDecimal(rangeFrom));//rangeFrom);
                    paramsClassification.setRangeTo(new BigDecimal(rangeTo));//rangeTo);
                    paramsClassification.setFormula(formula);//formula);
                    paramsClassification.setfGNo(fGNo);//fGNo);
                    paramsClassification.setfSGNo(fSGNo);//fSGNo);
                    paramsClassification.setfSsGNo(fSsGNo);//fSsGNo);
                    paramsClassification.setfSssGNo(fSssGNo);//fSssGNo);
                    paramsClassification.setfSsssGNo(fSsssGNo);//fSsssGNo);
//                    System.out.println("AAAAAAAAAAAAA" + description + "; " + grossAmt + "; grossAmt.divide(repOpt):" + grossAmt.divide(repOpt) + "; Formatter value:" + new BigDecimal(formatter.format(grossAmt.divide(repOpt).doubleValue())));
                    paramsClassification.setGrossOutstd(new BigDecimal(formatter.format(grossAmt.divide(repOpt).doubleValue())));//Gross Outstanding);
                    paramsClassification.setProvDedu(new BigDecimal(formatter.format(provAmt.divide(repOpt).doubleValue())));//Provision/Deduction);
                    portFolioClassificationPojoTable.add(paramsClassification);
                }
            }

            /*
             * PART B Other risk assets and exposure
             */
            List oss3Last = em.createNativeQuery("select distinct report_name from cbs_ho_rbi_stmt_report where report_name in ('SOSS3.PART-B','SOSS3.INVEST','SOSS3.NONSLR')").getResultList();
            if (oss3Last.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                for (int n = 0; n < oss3Last.size(); n++) {
                    Vector oss3LastVect = (Vector) oss3Last.get(n);
                    reptName = oss3LastVect.get(0).toString();
                    ossQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                            + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                            + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                            + "report_name = '" + reptName + "' order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                            + "cast(sss_gno as unsigned), cast(ssss_gno as unsigned)").getResultList();

                    if (ossQuery.isEmpty()) {
                        throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
                    } else {
                        for (int o = 0; o < ossQuery.size(); o++) {
                            RbiSossPojo rbiSossPojo = new RbiSossPojo();
                            Vector ossVect = (Vector) ossQuery.get(o);
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
                            // String fSssGNo = ossVect.get(21).toString();
                            String fSsssGNo = ossVect.get(22).toString();
                            String npaClassification = ossVect.get(23).toString();
                            String referIndex = ossVect.get(24).toString();
                            String referContent = ossVect.get(25).toString();

                            Long noOfAc = 0l;
                            BigDecimal bal = new BigDecimal("0");
                            BigDecimal loanAmt = new BigDecimal("0");
                            BigDecimal odCcAmt = new BigDecimal("0");
                            BigDecimal billPuchasedAmt = new BigDecimal("0");
                            BigDecimal commPaperAmt = new BigDecimal("0");
                            BigDecimal notesBondsAmt = new BigDecimal("0");
                            BigDecimal interBankAmt = new BigDecimal("0");
                            BigDecimal leaseReceivablesAmt = new BigDecimal("0");
                            BigDecimal slrAmt = new BigDecimal("0");
                            BigDecimal npaAmt = new BigDecimal("0");
                            BigDecimal provAmt = new BigDecimal("0");
                            if (referIndex.equalsIgnoreCase("Y")) {
                                List distDescList = em.createNativeQuery("select distinct description from cbs_ho_rbi_stmt_report where report_name  = '" + referContent + "'").getResultList();
                                if (distDescList.size() > 0) {
                                    for (int p = 0; p < distDescList.size(); p++) {
                                        Vector distDescVect = (Vector) distDescList.get(p);
                                        List distOssSubQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                                                + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                                                + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where report_name  = '"
                                                + referContent + "' AND description = '" + distDescVect.get(0).toString() + "' ").getResultList();

                                        if (distOssSubQuery.isEmpty()) {
                                            throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
                                        } else {
                                            for (int q = 0; q < distOssSubQuery.size(); q++) {
                                                Vector distOssSubVect = (Vector) distOssSubQuery.get(q);
                                                String distAcNature = distOssSubVect.get(10).toString();
                                                String distAcType = distOssSubVect.get(11).toString();
                                                String distGlHeadFrom = distOssSubVect.get(12).toString();
                                                String distGlHeadTo = distOssSubVect.get(13).toString();
                                                // String distRangeBaseOn = distOssSubVect.get(14).toString();
                                                //  String distRangeFrom = distOssSubVect.get(15).toString();
                                                // String distRangeTo = distOssSubVect.get(16).toString();

                                                if ((!((distAcNature == null) || distAcNature.equalsIgnoreCase("")))) {
                                                    if (acNature.equalsIgnoreCase("NON-NPA") || acNature.equalsIgnoreCase("NPA") || acNature.equalsIgnoreCase("OVER")) {
                                                        acNature = acNature.concat(".").concat(distAcNature);
                                                    } else {
                                                        acNature = distAcNature;
                                                    }
                                                    acType = "";
                                                    glHeadFrom = "";
                                                    glHeadTo = "";
                                                } else if ((!((distAcType == null) || distAcType.equalsIgnoreCase("")))) {
                                                    if (acType.equalsIgnoreCase("NON-NPA") || acType.equalsIgnoreCase("NPA") || acType.equalsIgnoreCase("OVER")) {
                                                        acType = acType.concat(".").concat(distAcType);
                                                    } else {
                                                        acType = distAcType;
                                                    }
                                                    acNature = "";
                                                    glHeadFrom = "";
                                                    glHeadTo = "";
                                                } else if ((!((distGlHeadFrom == null) || (distGlHeadFrom.equalsIgnoreCase(""))) && !((distGlHeadTo == null) || (distGlHeadTo.equalsIgnoreCase(""))))) {
                                                    glHeadFrom = distGlHeadFrom;
                                                    glHeadTo = distGlHeadTo;
//                                                    acNature = "";
//                                                    acType = "";
                                                } else {
                                                    acType = "";
                                                    acNature = "";
                                                    glHeadFrom = "";
                                                    glHeadTo = "";
                                                }
                                                /**
                                                 * ***************************
                                                 */
//                                                Integer cl;
//                                                if (classification.equalsIgnoreCase("L")) {
//                                                    cl = 1;
//                                                } else {
//                                                    cl = -1;
//                                                }
                                                AdditionalStmtPojo params = new AdditionalStmtPojo();
                                                params.setAcType(acType);
                                                params.setBrnCode(orgnCode);
                                                params.setClassification(classification);
                                                params.setDate(dt);
                                                params.setToDate(dt);
                                                params.setFromRange(rangeFrom);
                                                params.setGlFromHead(glHeadFrom);
                                                params.setGlToHead(glHeadTo);
                                                params.setNature(acNature);
                                                params.setOrgBrCode(orgnCode);
                                                params.setRangeBasedOn(rangeBaseOn);
                                                params.setToRange(rangeTo);
                                                params.setFlag(fSGNo);
                                                noOfAc = 0l;
                                                bal = new BigDecimal("0");
                                                AcctBalPojo acctBal = new AcctBalPojo();
                                                List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                                                if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                                                    if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
//                                                        acctBal = getAcctsAndBal(params);
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
//                                                            noOfAc = acctBal.getNumberOFAcct();
                                                            noOfAc = rbiRportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                                        } else {
                                                            acctBal = rbiRportRemote.getAcctsAndBal(params);
                                                            bal = acctBal.getBalance();
                                                        }
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                        acctBal = rbiRportRemote.getAcctsAndBalAmtRangeWise(params);
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                                            noOfAc = acctBal.getNumberOFAcct();
                                                        } else {
                                                            bal = acctBal.getBalance();
                                                        }
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                                        if (acNature.contains("NPA")) {
                                                            acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                                                        } else if (acNature.contains("OVER")) {
                                                            List<GlHeadPojo> headPojoList = null;
                                                            if (rangeBaseOn.equalsIgnoreCase("D")) {
                                                                headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.dateAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.dateAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                            } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                                                headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.monthAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.monthAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                            } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                                                headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.yearAdd(dt, new BigDecimal(rangeFrom).intValue()), 1), CbsUtil.yearAdd(dt, new BigDecimal(rangeTo).intValue()), acNature.substring(acNature.indexOf(".") + 1), acType.substring(acType.indexOf(".") + 1), "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                            }
                                                            if (countApplicable.equalsIgnoreCase("Y")) {
                                                                noOfAc = new Long(headPojoList.size());
                                                            } else {
                                                                for (int l = 0; l < headPojoList.size(); l++) {
                                                                    GlHeadPojo headPojo = headPojoList.get(l);
                                                                    bal = bal.add(headPojo.getBalance());
                                                                }
                                                            }
                                                            acctBal = new AcctBalPojo();
                                                            acctBal.setNumberOFAcct(noOfAc);
                                                            acctBal.setBalance(bal);
                                                        } else {
                                                            acctBal = rbiRportRemote.getAcctsAndBalTimeRangeWise(params);
                                                        }
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                                            noOfAc = acctBal.getNumberOFAcct();
                                                        } else {
                                                            bal = acctBal.getBalance();
                                                        }
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                                        acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                                            noOfAc = acctBal.getNumberOFAcct();
                                                        } else {
                                                            bal = acctBal.getBalance();
                                                        }
                                                    }
                                                } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                                    if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
//                                                        acctBal = getAcctsAndBal(params);
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
//                                                            noOfAc = acctBal.getNumberOFAcct();
                                                            noOfAc = rbiRportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                                        } else {
                                                            acctBal = rbiRportRemote.getAcctsAndBal(params);
                                                            bal = acctBal.getBalance();
                                                        }
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                        acctBal = rbiRportRemote.getAcctsAndBalAmtRangeWise(params);
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                                            noOfAc = acctBal.getNumberOFAcct();
                                                        } else {
                                                            bal = acctBal.getBalance();
                                                        }
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                                        if (acType.contains("NPA")) {
                                                            acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                                                        } else if (acType.contains("OVER")) {
                                                            List<GlHeadPojo> headPojoList = null;
                                                            if (rangeBaseOn.equalsIgnoreCase("D")) {
                                                                headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.dateAdd(dt, Integer.parseInt(rangeFrom)), 1), CbsUtil.dateAdd(dt, Integer.parseInt(rangeTo)), acNature, acType, "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                            } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                                                                headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.monthAdd(dt, Integer.parseInt(rangeFrom)), 1), CbsUtil.monthAdd(dt, Integer.parseInt(rangeTo)), acNature, acType, "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                            } else if (rangeBaseOn.equalsIgnoreCase("Y")) {
                                                                headPojoList = rbiRportRemote.getOverDueAccountInBetweenDateAndTheirOutStandingBal(CbsUtil.dateAdd(CbsUtil.yearAdd(dt, Integer.parseInt(rangeFrom)), 1), CbsUtil.yearAdd(dt, Integer.parseInt(rangeTo)), acNature, acType, "N", dt, new BigDecimal(rangeFrom).intValue(), new BigDecimal(rangeTo).intValue());
                                                            }
                                                            if (countApplicable.equalsIgnoreCase("Y")) {
                                                                noOfAc = new Long(headPojoList.size());
                                                            } else {
                                                                for (int l = 0; l < headPojoList.size(); l++) {
                                                                    GlHeadPojo headPojo = headPojoList.get(l);
                                                                    bal = bal.add(headPojo.getBalance());
                                                                }
                                                            }
                                                            acctBal = new AcctBalPojo();
                                                            acctBal.setNumberOFAcct(noOfAc);
                                                            acctBal.setBalance(bal);
                                                        } else {
                                                            acctBal = rbiRportRemote.getAcctsAndBalTimeRangeWise(params);
                                                        }
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                                            noOfAc = acctBal.getNumberOFAcct();
                                                        } else {
                                                            bal = acctBal.getBalance();
                                                        }
                                                    } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                                        acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                                                        if (countApplicable.equalsIgnoreCase("Y")) {
                                                            noOfAc = acctBal.getNumberOFAcct();
                                                        } else {
                                                            bal = acctBal.getBalance();
                                                        }
                                                    }
                                                } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                                                    if (glHeadFrom.contains("GOI") || glHeadFrom.contains("INVEST")) {
                                                        if (glHeadFrom.contains(":")) {
                                                            String[] a = glHeadFrom.split(":");
//                                                            List<InvestmentMaster> resultList1 = investRptMgmtFacade.getIndivisualReportTypeAllSellarName(glHeadFrom.substring(glHeadFrom.indexOf(".") + 1), ymd.parse(dt));
                                                            List<InvestmentMaster> resultList1 = investRptMgmtFacade.getIndivisualReportTypeAllSellarNameAndMArking(a[1], a[2].equalsIgnoreCase("") ? "0" : a[2], ymd.parse(dt));
                                                            if (!resultList1.isEmpty()) {
                                                                for (InvestmentMaster entity : resultList1) {
                                                                    BigDecimal tresurBill = new BigDecimal(entity.getBookvalue());
                                                                    double amortizationAmt = investRptMgmtFacade.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), dt);
                                                                    bal = bal.add(tresurBill.subtract(new BigDecimal(amortizationAmt)));
                                                                }
                                                            }
                                                        }
                                                        rbiSossPojo.setAmt(bal);
                                                    } else {
                                                        GlHeadPojo glHeadPojo = new GlHeadPojo();
                                                        glPojoList = rbiRportRemote.getGLHeadAndBal(params);
                                                        if (glPojoList.size() > 0) {
                                                            /* If GL Head Series have multiple GL Head */
                                                            for (int m = 0; m < glPojoList.size(); m++) {
                                                                GlHeadPojo glHeadPo = glPojoList.get(m);
                                                                if (countApplicable.equalsIgnoreCase("Y")) {
                                                                    noOfAc = noOfAc + (long) glPojoList.size();
                                                                    rbiSossPojo.setAmt(BigDecimal.valueOf(noOfAc.doubleValue()));
                                                                } else {
                                                                    bal = bal.add(glHeadPo.getBalance());
                                                                    rbiSossPojo.setAmt(bal);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (distDescVect.get(0).toString().equalsIgnoreCase("NONSLRAAPPUNRATED")) {
                                                    loanAmt = loanAmt.add(bal.abs());//OK
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("ONSTD") || distDescVect.get(0).toString().equalsIgnoreCase("OFFNOTINVK") || distDescVect.get(0).toString().equalsIgnoreCase("QLTBOKHLDMAT") || distDescVect.get(0).toString().equalsIgnoreCase("NONSLRRAT")) {
                                                    commPaperAmt = commPaperAmt.add(bal.abs()); //OK
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("ONSUBSTD") || distDescVect.get(0).toString().equalsIgnoreCase("OFFSTD") || distDescVect.get(0).toString().equalsIgnoreCase("QLTBOKAVLSAL") || distDescVect.get(0).toString().equalsIgnoreCase("NONSLRUNRAT")) {
                                                    notesBondsAmt = notesBondsAmt.add(bal.abs());//OK
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("ONDUBT") || distDescVect.get(0).toString().equalsIgnoreCase("OFFSUBSTDDUBT") || distDescVect.get(0).toString().equalsIgnoreCase("QLTBOKHLDTRD") || distDescVect.get(0).toString().equalsIgnoreCase("NONSLRBORROW")) {
                                                    interBankAmt = interBankAmt.add(bal.abs());//OK
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("ONLOSS") || distDescVect.get(0).toString().equalsIgnoreCase("OFFLOSS") || distDescVect.get(0).toString().equalsIgnoreCase("QLTMKTAVLSAL") || distDescVect.get(0).toString().equalsIgnoreCase("NONSLRNONBORROW")) {
                                                    leaseReceivablesAmt = leaseReceivablesAmt.add(bal.abs());//OK
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("QLTMKTHLDTRD") || distDescVect.get(0).toString().equalsIgnoreCase("NONSLRAAPPRATE")) {
                                                    slrAmt = slrAmt.add(bal.abs());//OK
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("NONSLRNPA")) {
                                                    npaAmt = npaAmt.add(bal.abs());
                                                } else if (distDescVect.get(0).toString().equalsIgnoreCase("NONSLRPROV")) {
                                                    provAmt = provAmt.add(bal.abs());
                                                }
                                                /**
                                                 * ***************************
                                                 */
                                            }
                                        }
                                    }
                                }
                            }



                            PortFolioConPojo paramsCon = new PortFolioConPojo();
                            paramsCon.setsNo(sNo);
                            paramsCon.setReportName(reportName);
                            paramsCon.setDescription(description);
                            paramsCon.setgNo(gNo);//gNo);
                            paramsCon.setsGNo(sGNo);//sGNo);
                            paramsCon.setSsGNo(ssGNo);//ssGNo);
                            paramsCon.setSssGNo(sssGNo);//sssGNo);
                            paramsCon.setSsssGNo(ssssGNo);//ssssGNo);
                            paramsCon.setClassification(classification);//classification);
                            paramsCon.setCountApplicable(countApplicable);//countApplicable);
                            paramsCon.setAcNature(acNature);//acNature);
                            paramsCon.setAcType(acType);//acType);
                            paramsCon.setNpaClassification(npaClassification);//npaClassification);
                            paramsCon.setGlheadFrom(glHeadFrom);//glheadFrom);
                            paramsCon.setGlHeadTo(glHeadTo);//glHeadTo);
                            paramsCon.setRangeBaseOn(rangeBaseOn);//rangeBaseOn);
                            paramsCon.setRangeFrom(new BigDecimal(rangeFrom));//rangeFrom);
                            paramsCon.setRangeTo(new BigDecimal(rangeTo));//rangeTo);
                            paramsCon.setFormula(formula);//formula);
                            paramsCon.setfGNo(fGNo);//fGNo);
                            paramsCon.setfSGNo(fSGNo);//fSGNo);
                            paramsCon.setfSsGNo(fSsGNo);//fSsGNo);
                            paramsCon.setfSssGNo(fSsGNo);//fSssGNo);
                            paramsCon.setfSsssGNo(fSsssGNo);//fSsssGNo);
                            paramsCon.setLoanAmt(new BigDecimal(formatter.format(loanAmt.divide(repOpt).doubleValue())));//loanAmt);
                            paramsCon.setOdCcAmt(new BigDecimal(formatter.format(odCcAmt.divide(repOpt).doubleValue())));//odCcAmt);
                            paramsCon.setBillDiscAmt(new BigDecimal(formatter.format(billPuchasedAmt.divide(repOpt).doubleValue())));//billDiscAmt);
                            paramsCon.setCommAmt(new BigDecimal(formatter.format(commPaperAmt.divide(repOpt).doubleValue())));//commAmt,,,,);
                            paramsCon.setNoteAmt(new BigDecimal(formatter.format(notesBondsAmt.divide(repOpt).doubleValue())));//noteAmt);
                            paramsCon.setInterAmt(new BigDecimal(formatter.format(interBankAmt.divide(repOpt).doubleValue())));//interAmt);
                            paramsCon.setLeaseAmt(new BigDecimal(formatter.format(leaseReceivablesAmt.divide(repOpt).doubleValue())));//leaseAmt);
                            paramsCon.setSlrAmt(new BigDecimal(formatter.format(slrAmt.divide(repOpt).doubleValue())));//slrAmt);
                            paramsCon.setNpaAmt(new BigDecimal(formatter.format(npaAmt.divide(repOpt).doubleValue())));//NPA AMOUNT);
                            paramsCon.setProvAmt(new BigDecimal(formatter.format(provAmt.divide(repOpt).doubleValue())));//PROVISION AMOUNT);
                            if (reptName.equalsIgnoreCase("SOSS3.PART-B")) {
                                portFolioBalanceSheetPojoTable.add(paramsCon);// = portFolioConPojoTable;
                            } else if (reptName.equalsIgnoreCase("SOSS3.INVEST")) {
                                portFolioInvestmentPojoTable.add(paramsCon);// = portFolioConPojoTable;
                            } else if (reptName.equalsIgnoreCase("SOSS3.NONSLR")) {
                                portFolioNonSlrSecurityPojoTable.add(paramsCon);// = portFolioConPojoTable;
                            }
                            //portFolioConPojoTable.add(paramsCon);
                        }

                        /*
                         * Formula implementation of Consolidate Loan & Advances and Oiba
                         */

                        if (reptName.equalsIgnoreCase("SOSS3.PART-B")) {
                            for (int s = 0; s < portFolioBalanceSheetPojoTable.size(); s++) {
                                PortFolioConPojo rbiSossPojo = portFolioBalanceSheetPojoTable.get(s);
                                if (!rbiSossPojo.getFormula().isEmpty()) {
                                    BigDecimal loanBal = new BigDecimal("0"), odCcBal = new BigDecimal("0"), billPurAmt = new BigDecimal("0");
                                    BigDecimal commAmt = new BigDecimal("0"), noteAmt = new BigDecimal("0"), interAmt = new BigDecimal("0"), leaseAmt = new BigDecimal("0"), slrAmt = new BigDecimal("0");
                                    loanBal = loanBal.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "loanAmt"));
                                    odCcBal = odCcBal.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "odCcAmt"));
                                    commAmt = commAmt.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "commAmt"));
                                    noteAmt = noteAmt.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "noteAmt"));
                                    interAmt = interAmt.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "interAmt"));
                                    leaseAmt = leaseAmt.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "leaseAmt"));
                                    slrAmt = slrAmt.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "slrAmt"));
                                    billPurAmt = billPurAmt.add(getValueFromFormulaCon(portFolioBalanceSheetPojoTable, rbiSossPojo.getFormula(), "billDiscAmt"));
                                    rbiSossPojo.setLoanAmt(loanBal.abs());
                                    rbiSossPojo.setOdCcAmt(odCcBal.abs());
                                    rbiSossPojo.setBillDiscAmt(billPurAmt.abs());
                                    rbiSossPojo.setCommAmt(commAmt.abs());
                                    rbiSossPojo.setNoteAmt(noteAmt.abs());
                                    rbiSossPojo.setInterAmt(interAmt.abs());
                                    rbiSossPojo.setLeaseAmt(leaseAmt.abs());
                                    rbiSossPojo.setSlrAmt(slrAmt.abs());
                                }
                            }
//                            portFolioBalanceSheetPojoTable = portFolioConPojoTable;
                        } else if (reptName.equalsIgnoreCase("SOSS3.INVEST")) {
                            for (int s = 0; s < portFolioInvestmentPojoTable.size(); s++) {
                                PortFolioConPojo rbiSossPojo = portFolioInvestmentPojoTable.get(s);
                                if (!rbiSossPojo.getFormula().isEmpty()) {
                                    BigDecimal loanBal = new BigDecimal("0"), odCcBal = new BigDecimal("0"), billPurAmt = new BigDecimal("0");
                                    BigDecimal commAmt = new BigDecimal("0"), noteAmt = new BigDecimal("0"), interAmt = new BigDecimal("0"), leaseAmt = new BigDecimal("0"), slrAmt = new BigDecimal("0");
                                    loanBal = loanBal.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "loanAmt"));
                                    odCcBal = odCcBal.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "odCcAmt"));
                                    commAmt = commAmt.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "commAmt"));
                                    noteAmt = noteAmt.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "noteAmt"));
                                    interAmt = interAmt.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "interAmt"));
                                    leaseAmt = leaseAmt.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "leaseAmt"));
                                    slrAmt = slrAmt.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "slrAmt"));
                                    billPurAmt = billPurAmt.add(getValueFromFormulaCon(portFolioInvestmentPojoTable, rbiSossPojo.getFormula(), "billDiscAmt"));
                                    rbiSossPojo.setLoanAmt(loanBal.abs());
                                    rbiSossPojo.setOdCcAmt(odCcBal.abs());
                                    rbiSossPojo.setBillDiscAmt(billPurAmt.abs());
                                    rbiSossPojo.setCommAmt(commAmt.abs());
                                    rbiSossPojo.setNoteAmt(noteAmt.abs());
                                    rbiSossPojo.setInterAmt(interAmt.abs());
                                    rbiSossPojo.setLeaseAmt(leaseAmt.abs());
                                    rbiSossPojo.setSlrAmt(slrAmt.abs());
                                }
                            }
//                            portFolioInvestmentPojoTable = portFolioConPojoTable;
                        } else if (reptName.equalsIgnoreCase("SOSS3.NONSLR")) {
                            for (int s = 0; s < portFolioNonSlrSecurityPojoTable.size(); s++) {
                                PortFolioConPojo rbiSossPojo = portFolioNonSlrSecurityPojoTable.get(s);
                                if (!rbiSossPojo.getFormula().isEmpty()) {
                                    BigDecimal loanBal = new BigDecimal("0"), odCcBal = new BigDecimal("0"), billPurAmt = new BigDecimal("0");
                                    BigDecimal commAmt = new BigDecimal("0"), noteAmt = new BigDecimal("0"), interAmt = new BigDecimal("0"), leaseAmt = new BigDecimal("0"), slrAmt = new BigDecimal("0");                                    
                                    loanBal = loanBal.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "loanAmt"));
                                    odCcBal = odCcBal.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "odCcAmt"));
                                    commAmt = commAmt.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "commAmt"));
                                    noteAmt = noteAmt.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "noteAmt"));
                                    interAmt = interAmt.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "interAmt"));
                                    leaseAmt = leaseAmt.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "leaseAmt"));
                                    slrAmt = slrAmt.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "slrAmt"));
                                    billPurAmt = billPurAmt.add(getValueFromFormulaCon(portFolioNonSlrSecurityPojoTable, rbiSossPojo.getFormula(), "billDiscAmt"));                                    
                                    rbiSossPojo.setLoanAmt(loanBal.abs());
                                    rbiSossPojo.setOdCcAmt(odCcBal.abs());
                                    rbiSossPojo.setBillDiscAmt(billPurAmt.abs());
                                    rbiSossPojo.setCommAmt(commAmt.abs());
                                    rbiSossPojo.setNoteAmt(noteAmt.abs());
                                    rbiSossPojo.setInterAmt(interAmt.abs());
                                    rbiSossPojo.setLeaseAmt(leaseAmt.abs());
                                    rbiSossPojo.setSlrAmt(slrAmt.abs());                                    
                                }
                            }
                        }
                    }
                }
            }

            Soss3PortFolioPojo pojo = new Soss3PortFolioPojo();
            pojo.setPortFolioConList(portFolioConPojoTable);
            pojo.setPortFolioLoanAdvList(portFolioLoanAdvPojoTable);
            pojo.setPortFolioOibaList(portFolioOibaPojoTable);
            pojo.setPortFolioClassificationList(portFolioClassificationPojoTable);
            pojo.setPortFolioBalanceSheetList(portFolioBalanceSheetPojoTable);
            pojo.setPortFolioInvestmentList(portFolioInvestmentPojoTable);
            pojo.setPortFolioNonSlrSecurityList(portFolioNonSlrSecurityPojoTable);

            Soss3PortFolioPojoTable.add(pojo);
            return Soss3PortFolioPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public BigDecimal getSoss3RefernceBal(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            BigDecimal referenceBal = new BigDecimal(0);
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                String npaAcDetails = "";
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                for (int i = 0; i < oss1Query.size(); i++) {
                    //   RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    //Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    // String reportName = oss1Vect.get(1).toString();
                    //  String description = oss1Vect.get(2).toString();
                    //   Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    //   Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    //   Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    //   Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    //   Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    String classification = oss1Vect.get(8).toString();
                    String countApplicable = oss1Vect.get(9).toString();
                    String acNature = oss1Vect.get(10).toString();
                    String acType = oss1Vect.get(11).toString();
                    String glHeadFrom = oss1Vect.get(12).toString();
                    String glHeadTo = oss1Vect.get(13).toString();
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    // String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    //  String npaClassification = oss1Vect.get(23).toString();
                    //  Integer cl;
//                    if (classification.equalsIgnoreCase("L")) {
//                        cl = 1;
//                    } else {
//                        cl = -1;
//                    }
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(dt);
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

                    if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {

//                            acctBal = getAcctsAndBal(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                noOfAc = acctBal.getNumberOFAcct();
                                noOfAc = rbiRportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                            } else {
                                acctBal = rbiRportRemote.getAcctsAndBal(params);
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                            acctBal = rbiRportRemote.getAcctsAndBalAmtRangeWise(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = acctBal.getNumberOFAcct();
                            } else {
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                            acctBal = rbiRportRemote.getAcctsAndBalTimeRangeWise(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = acctBal.getNumberOFAcct();
                            } else {
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                            acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = acctBal.getNumberOFAcct();
                            } else {
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        }
                    } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
//                            acctBal = getAcctsAndBal(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
//                                noOfAc = acctBal.getNumberOFAcct();
                                noOfAc = rbiRportRemote.getAccountStatusReport(dt, orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                            } else {
                                acctBal = rbiRportRemote.getAcctsAndBal(params);
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                            acctBal = rbiRportRemote.getAcctsAndBalAmtRangeWise(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = acctBal.getNumberOFAcct();
                            } else {
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                            acctBal = rbiRportRemote.getAcctsAndBalTimeRangeWise(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = acctBal.getNumberOFAcct();
                            } else {
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                            acctBal = rbiRportRemote.getNpaAcctsAndBal(params);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = acctBal.getNumberOFAcct();
                            } else {
                                bal = new BigDecimal(formatter.format(acctBal.getBalance().doubleValue()));
                            }
                        }
                    } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                        // GlHeadPojo glHeadPojo = new GlHeadPojo();
                        glPojoList = rbiRportRemote.getGLHeadAndBal(params);
                        if (glPojoList.size() > 0) {
                            /* If GL Head Series have multiple GL Head */
                            for (int j = 0; j < glPojoList.size(); j++) {
                                GlHeadPojo glHeadPo = glPojoList.get(j);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = noOfAc + (long) glPojoList.size();
                                } else {
                                    bal = bal.add(new BigDecimal(formatter.format(glHeadPo.getBalance().divide(repOpt).doubleValue())));

                                }
                            }
                        } else {
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                noOfAc = noOfAc + (long) glPojoList.size();
                            } else {
                                bal = new BigDecimal(formatter.format(bal.divide(repOpt).doubleValue()));
                            }
                        }
                    }
                    referenceBal = referenceBal.add(bal);
                }
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
//                        System.out.println("sno:" + rbiSossPojo.getsNo());
                        BigDecimal bal = new BigDecimal("0.0");
                        bal = bal.add(rbiRportRemote.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula()));
                        rbiSossPojo.setAmt(bal);
                        referenceBal = referenceBal.add(bal);
                    }
                }
            }
            return referenceBal;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    /*
     * Formula implementation of PORTFOLIO ANALYSIS of Loan & Advances  ***** SOSS3 ******
     */

    private BigDecimal getValueFromFormulaLoanAdv(List<PortFolioLoanAdvPojo> dataList, String formula, String columnName) throws ApplicationException {
        try {
            BigDecimal balance;
            String exp = "";
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(",")) {
                    balance = getOperandBalanceLoanAdv(dataList, arr[i], columnName);
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

    /*
     * Formula implementation of PORTFOLIO ANALYSIS of OIBA  ***** SOSS3 ******
     */
    private BigDecimal getValueFromFormulaOiba(List<PortFolioOibaPojo> dataList, String formula, String columnName) throws ApplicationException {
        try {
            BigDecimal balance;
            String exp = "";
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(",")) {
                    balance = getOperandBalanceOiba(dataList, arr[i], columnName);
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

    /*
     * Formula implementation of PORTFOLIO ANALYSIS of Consolidate Loan & Advances and OIBA  ***** SOSS3 ******
     */
    private BigDecimal getValueFromFormulaCon(List<PortFolioConPojo> dataList, String formula, String columnName) throws ApplicationException {
        try {
            BigDecimal balance;
            String exp = "";
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(",")) {
                    balance = getOperandBalanceCon(dataList, arr[i], columnName);
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

    /*
     * This is common for all PORTFOLIO ANALYSIS pojo ***** SOSS3 ******
     */
    public BigDecimal getAmountValue(String fieldName, PortFolioLoanAdvPojo loanAdv, PortFolioOibaPojo oiba, PortFolioConPojo con, String pojoName) throws ApplicationException {
        BigDecimal amount = new BigDecimal("0");
        try {
            Class aClass = null;
            Object value = null;
            Field field = null;


            if (pojoName.equalsIgnoreCase("loanAdv")) {
                aClass = PortFolioLoanAdvPojo.class;
                field = aClass.getField(fieldName);
                value = field.get(loanAdv);
            } else if (pojoName.equalsIgnoreCase("oiba")) {
                aClass = PortFolioOibaPojo.class;
                field = aClass.getField(fieldName);
                value = field.get(oiba);
            } else if (pojoName.equalsIgnoreCase("con")) {
                aClass = PortFolioConPojo.class;
                field = aClass.getField(fieldName);
                value = field.get(con);
            }
            amount = new BigDecimal(value.toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return amount;
    }

    /*
     *  Loan & Advances ***** SOSS3 ******
     */
    private BigDecimal getOperandBalanceLoanAdv(List<PortFolioLoanAdvPojo> dataList, String csvExp, String columnName) throws ApplicationException {
        try {
            BigDecimal balance = new BigDecimal("0");
            BigDecimal amt = new BigDecimal("0");
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (PortFolioLoanAdvPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        amt = getAmountValue(columnName, pojo, null, null, "loanAdv");
                        balance = balance.add(amt);
                    }
                }
            } else if (arr[2] != 0 && arr[3] != 0) {
                for (PortFolioLoanAdvPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        amt = getAmountValue(columnName, pojo, null, null, "loanAdv");
                        balance = balance.add(amt);
                    }
                }
            } else if (arr[2] != 0) {
                for (PortFolioLoanAdvPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        amt = getAmountValue(columnName, pojo, null, null, "loanAdv");
                        balance = balance.add(amt);
                    }
                }
            } else {
                for (PortFolioLoanAdvPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        amt = getAmountValue(columnName, pojo, null, null, "loanAdv");
                        balance = balance.add(amt);
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /*
     *  OIBA ***** SOSS3 ******
     */
    private BigDecimal getOperandBalanceOiba(List<PortFolioOibaPojo> dataList, String csvExp, String columnName) throws ApplicationException {
        try {
            BigDecimal balance = new BigDecimal("0");
            BigDecimal amt = new BigDecimal("0");
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (PortFolioOibaPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        amt = getAmountValue(columnName, null, pojo, null, "oiba");
                        balance = balance.add(amt);
                    }
                }
            } else if (arr[2] != 0 && arr[3] != 0) {
                for (PortFolioOibaPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        amt = getAmountValue(columnName, null, pojo, null, "oiba");
                        balance = balance.add(amt);
                    }
                }
            } else if (arr[2] != 0) {
                for (PortFolioOibaPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        amt = getAmountValue(columnName, null, pojo, null, "oiba");
                        balance = balance.add(amt);
                    }
                }
            } else {
                for (PortFolioOibaPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        amt = getAmountValue(columnName, null, pojo, null, "oiba");
                        balance = balance.add(amt);
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /*
     *  Consolidate of Loan & Advances and OIBA  ***** SOSS3 ******
     */
    private BigDecimal getOperandBalanceCon(List<PortFolioConPojo> dataList, String csvExp, String columnName) throws ApplicationException {
        try {
            BigDecimal balance = new BigDecimal("0");
            BigDecimal amt = new BigDecimal("0");
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (PortFolioConPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        amt = getAmountValue(columnName, null, null, pojo, "con");
                        balance = balance.add(amt);
                    }
                }
            } else if (arr[2] != 0 && arr[3] != 0) {
                for (PortFolioConPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        amt = getAmountValue(columnName, null, null, pojo, "con");
                        balance = balance.add(amt);
                    }
                }
            } else if (arr[2] != 0) {
                for (PortFolioConPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        amt = getAmountValue(columnName, null, null, pojo, "con");
                        balance = balance.add(amt);
                    }
                }
            } else {
                for (PortFolioConPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        amt = getAmountValue(columnName, null, null, pojo, "con");
                        balance = balance.add(amt);
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }


}
