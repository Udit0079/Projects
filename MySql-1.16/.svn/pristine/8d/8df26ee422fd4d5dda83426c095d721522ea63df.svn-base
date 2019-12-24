/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.InterOfycPojo1;
import com.cbs.dto.report.InterOfycPojo2;
import com.cbs.dto.report.InterOfycPojo3;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import com.cbs.dto.report.RbiSos4Pojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.Statement8PoJo;
import com.cbs.dto.report.Statement9PoJo;
import com.cbs.dto.report.TopDepositorBorrowerPojo;
import com.cbs.dto.report.ho.InterOfycPojo;
import com.cbs.dto.report.ho.Oss7BusinessPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.Oss4PartACombinedPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.PostfixEvaluator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author saurabhsipl
 */
@Stateless(mappedName = "StmtOneToTenFacade")
public class StmtOneToTenFacade implements StmtOneToTenFacadeRemote {

    @EJB
    RbiMonthlyReportFacadeRemote monthlyRemote;
    @EJB
    RbiQuarterlyReportFacadeRemote quarterlyRemote;
    @EJB
    RbiReportFacadeRemote rbiRemote;
    @EJB
    private NpaReportFacadeRemote npaRemote;
    @EJB
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    GLReportFacadeRemote glReport;
    @EJB
    private HoReportFacadeRemote hoRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    MiscReportFacadeRemote misReport;
    @EJB
    RbiReportPartDFacadeRemote partDRemote;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    @PersistenceContext
    private EntityManager em;
    NumberFormat formatter = new DecimalFormat("#.####");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private BigDecimal reptOpt;

    public List<RbiSossPojo> getStaementTwo(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String summaryOpt) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List osBlancelist = null, osBlancelistIncomeExp1;
            if (reportName.equalsIgnoreCase("STMT2")) {
                for (int k = 0; k < dates.size(); k++) {
                    osBlancelistIncomeExp1 = monthlyRemote.getAsOnDateBalanceBetweenDateList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, rbiRemote.getMinFinYear(dates.get(k)), dates.get(k));
                    if (k == 0) {
                        osBlancelist = osBlancelistIncomeExp1;
                    } else {
                        osBlancelist.addAll(osBlancelistIncomeExp1);
                    }
                }
            } else {
                osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
            }
            String dt = "";
            if (reportName.equalsIgnoreCase("STMT1") || reportName.equalsIgnoreCase("STMT2")) {
                dt = dates.get(2);
            } else {
                dt = dates.get(0);
            }
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index,refer_content  from cbs_ho_rbi_stmt_report where report_name = '" + reportName + "' and '" + dt + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist ");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                String preFormula = "", npaAcDetails = "";
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dates.get(0), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                for (int i = 0; i < oss1Query.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String repptName = oss1Vect.get(1).toString();
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
                    if (i == 78) {
                        System.out.println("found");
                    }
                    Integer cl;
                    if (classification.equalsIgnoreCase("L")) {
                        cl = 1;
                    } else if (classification.equalsIgnoreCase("A")) {
                        cl = -1;
                    }
                    /*Addition ath the first time*/
                    rbiSossPojo.setgNo(gNo);
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
                    rbiSossPojo.setReportName(repptName);
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
                    /*End*/
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    if (repptName.equalsIgnoreCase("STMT2")) {
                        params.setDate(rbiRemote.getMinFinYear(dates.get(0)));
                    } else {
                        params.setDate(dates.get(0));
                    }
                    params.setToDate(dates.get(0));
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
                            /*Setting into main list*/
                            for (int z = 0; z < natureList.size(); z++) {
                                Vector vector = (Vector) natureList.get(z);
                                rbiSossPojo = new RbiSossPojo();
                                for (int l = 0; l < dates.size(); l++) {
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        params.setNature("");
                                        params.setAcType(vector.get(0).toString());
                                        noOfAc = rbiRemote.getAccountStatusReport(dates.get(l), orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(noOfAc.toString()));
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                        List<String> monthEndList = new ArrayList();
                                        if (fSsGNo.equalsIgnoreCase("GLB")) {
                                            monthEndList.add(dates.get(l));
                                        } else if (fSsGNo.equalsIgnoreCase("AVG")) {
                                            monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(rbiRemote.getMinFinYear(dates.get(l)), dates.get(l));
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
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
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
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, amtAsPerClass);
                                        } else {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, amtAsPerClass.divide(repOpt));
                                        }
                                    } else if (referIndex.equalsIgnoreCase("NRO") || referIndex.equalsIgnoreCase("INDIA")) {
                                        // For Residence And NRO Balance in Liability Side
                                        String tableName = "";
                                        String nriQuery = "";
                                        BigDecimal amtAsPerClass = new BigDecimal("0");
                                        BigDecimal numberOfAcs = new BigDecimal("0");
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
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, numberOfAcs);
                                        } else {
                                            if ((amtAsPerClass.compareTo(new BigDecimal("0"))) == 0) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, amtAsPerClass);
                                            } else {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, amtAsPerClass.divide(repOpt));
                                            }
                                        }
                                    } else {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                                        if (newBalPojo == null) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                                        } else {
                                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                            } else {
                                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                            }
                                        }
                                    }
                                }
                                rbiSossPojo.setgNo(gNo);
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
                                    if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getSecondAmount().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getThirdAmount().compareTo(new BigDecimal(0)) != 0) {
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
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getSecondAmount().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getThirdAmount().compareTo(new BigDecimal(0)) != 0) {
                                    rbiPojoTable.add(rbiSossPojo);
                                }
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;
                            }
                        } else {
                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                acctBal = rbiRemote.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                acctBal = rbiRemote.getAcctsAndBalTimeRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                acctBal = rbiRemote.getNpaAcctsAndBal(params);
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
                                    List dataList = em.createNativeQuery("select count(*) from accountmaster where OpeningDt <= '" + dates.get(0) + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where " + acCodeQuery + " ) and acno not in (select distinct acno from " + tableName + " ) and (closingdate is null or closingdate ='' or closingdate >'" + dates.get(0) + "')").getResultList();
                                    Vector vect = (Vector) dataList.get(0);
                                    noOfAc = Long.parseLong(vect.get(0).toString());
                                } else {
                                    List dataList = em.createNativeQuery("select count(*) from td_accountmaster where OpeningDt <= '" + dates.get(0) + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where " + acCodeQuery + ") and acno not in (select distinct acno from " + tableName + " ) and (closingdate is null or closingdate ='' or closingdate >'" + dates.get(0) + "')").getResultList();
                                    Vector vect = (Vector) dataList.get(0);
                                    noOfAc = Long.parseLong(vect.get(0).toString());
                                }
                            }
                            rbiSossPojo = new RbiSossPojo();
                            for (int l = 0; l < dates.size(); l++) {
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(noOfAc.toString()));
                                } else {
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal.divide(repOpt));
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal.multiply(new BigDecimal("-1")).divide(repOpt));
                                    } else {
                                        rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, bal.divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, bal.abs().divide(repOpt));
                                    }
                                }
                            }
                            rbiSossPojo.setgNo(gNo);
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
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8)").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
                            /* If GL Head Series have multiple GL Head */
                            rbiSossPojo = new RbiSossPojo();
                            for (int l = 0; l < dates.size(); l++) {
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal("0"));
                                } else {
                                    if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                        List<String> monthEndList = new ArrayList();
                                        if (fSsGNo.equalsIgnoreCase("GLB")) {
                                            monthEndList.add(dates.get(l));
                                        } else if (fSsGNo.equalsIgnoreCase("AVG")) {
                                            monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(rbiRemote.getMinFinYear(dates.get(l)), dates.get(l));
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
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
                                    } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                        if (repptName.equalsIgnoreCase("STMT2")) {
                                            params.setDate(rbiRemote.getMinFinYear(dates.get(l)));
                                        } else {
                                            params.setDate(dates.get(l));
                                        }
                                        String date = params.getDate();
                                        String amt = "";
                                        String minFDate = rbiRemote.getMinFinYear(params.getDate());
                                        GlHeadPojo newBalPojo = null;
                                        if (params.getRefer_index().equalsIgnoreCase("F")) {
                                            if (params.getRefer_content().equalsIgnoreCase("<")) {
                                                date = "dt  < '" + minFDate + "' ";
                                                amt = "cast(sum(cramt-dramt) as decimal(15,2))";
                                            } else if (params.getRefer_content().equalsIgnoreCase(">")) {
                                                date = "dt  between '" + minFDate + "' and '" + params.getToDate() + "' ";
                                                if (params.getClassification().equalsIgnoreCase("A")) {
                                                    amt = "cast(sum(cramt) as decimal(15,2))";
                                                } else if (params.getClassification().equalsIgnoreCase("L")) {
                                                    amt = "cast(sum(dramt) as decimal(15,2))";
                                                }
                                            } else {
                                                date = "dt  between '" + minFDate + "' and '" + params.getToDate() + "' ";
                                                amt = "cast(sum(cramt-dramt) as decimal(15,2))";
                                            }
                                        } else if (params.getRefer_index().equalsIgnoreCase("Q")) {
                                            if (params.getRefer_content().equalsIgnoreCase("<")) {
                                                date = " dt < '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' ";
                                                amt = "cast(sum(cramt-dramt) as decimal(15,2))";
                                            } else if (params.getRefer_content().equalsIgnoreCase(">")) {
                                                date = " dt between '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' and '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "L") + "' ";
                                                if (params.getClassification().equalsIgnoreCase("A")) {
                                                    amt = "cast(sum(cramt) as decimal(15,2))";
                                                } else if (params.getClassification().equalsIgnoreCase("L")) {
                                                    amt = "cast(sum(dramt) as decimal(15,2))";
                                                }
                                            } else {
                                                date = " dt between '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' and '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "L") + "' ";
                                                amt = "cast(sum(cramt-dramt) as decimal(15,2))";
                                            }
                                        }
                                        List dlist = em.createNativeQuery("select ifnull(" + amt + ",0) from gl_recon where substring(acno,3,8) = '" + vector.get(0).toString() + "' and " + date + " ").getResultList();
                                        if (dlist.isEmpty() || dlist == null) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                                        } else {
                                            Vector listVect = (Vector) dlist.get(0);
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(listVect.get(0).toString()).divide(repOpt));
                                        }
                                    } else {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                                        if (newBalPojo == null) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                                        } else {
                                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply((new BigDecimal("-1"))).divide(repOpt));
                                            } else {
                                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                            }
                                        }
                                    }
                                }
                            }
                            rbiSossPojo.setgNo(gNo);
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
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getSecondAmount().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getThirdAmount().compareTo(new BigDecimal(0)) != 0) {
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
                            if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getSecondAmount().compareTo(new BigDecimal(0)) != 0 || rbiSossPojo.getThirdAmount().compareTo(new BigDecimal(0)) != 0) {
                                rbiPojoTable.add(rbiSossPojo);
                            }
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                        }
                    } else if (fSssGNo.equalsIgnoreCase("EMP")) {
                        /* For Number Of Employees To Be printed in STATEMENT TWO */
                        rbiSossPojo = new RbiSossPojo();
                        for (int m = 0; m < dates.size(); m++) {
                            List list = em.createNativeQuery("select ifnull(refer_index,0) from cbs_ho_rbi_stmt_report where "
                                    + "report_name ='" + reportName + "' and F_SSS_GNO= 'EMP' and '" + dates.get(m) + "'  between EFF_FR_DT and EFF_TO_DT").getResultList();
                            for (int l = 0; l < list.size(); l++) {
                                Vector listVect = (Vector) list.get(l);
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, m, new BigDecimal(listVect.get(0).toString()));
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
                    } else if (!(fSssGNo.equalsIgnoreCase("")) || !(fSsGNo.equalsIgnoreCase("")) || !(fSGNo.equalsIgnoreCase("")) || !(fGNo.equalsIgnoreCase(""))) {
                        rbiSossPojo = new RbiSossPojo();
                        for (int l = 0; l < dates.size(); l++) {
                            List<LoanMisCellaniousPojo> misResultList = new ArrayList<LoanMisCellaniousPojo>();
                            misResultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    dates.get(l), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "O",
                                    fGNo.equalsIgnoreCase("") ? "0" : fGNo, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", fSsGNo.equalsIgnoreCase("") ? "0" : fSsGNo,
                                    fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(misResultList.size()));
                            } else {
                                BigDecimal a = new BigDecimal("0");
                                for (int k = 0; k < misResultList.size(); k++) {
                                    if (misResultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        a = a.add(misResultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misResultList.get(k).getOutstanding());
                                    } else {
                                        a = a.add(misResultList.get(k).getOutstanding());
                                    }
                                }
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, a.divide(repOpt).abs());
                                rbiSossPojo.setAmt(a.divide(repOpt).abs());
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
                            /*Setting into main list*/
                            for (int z = 0; z < natureList.size(); z++) {
                                Vector vector = (Vector) natureList.get(z);
                                rbiSossPojo = new RbiSossPojo();
                                for (int l = 0; l < dates.size(); l++) {
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        params.setNature("");
                                        params.setAcType(vector.get(0).toString());
                                        noOfAc = rbiRemote.getAccountStatusReport(dates.get(l), orgnCode, params.getAcType(), params.getNature(), params.getClassification());
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(noOfAc.toString()));
                                    } else if (fSsGNo.equalsIgnoreCase("AVG") || fSsGNo.equalsIgnoreCase("GLB")) {
                                        List<String> monthEndList = new ArrayList();
                                        if (fSsGNo.equalsIgnoreCase("GLB")) {
                                            monthEndList.add(dates.get(0));
                                        } else if (fSsGNo.equalsIgnoreCase("AVG")) {
                                            monthEndList = CbsUtil.getMonthEndDtFormCurDtInFinancialYear(rbiRemote.getMinFinYear(dates.get(l)), dates.get(l));
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
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, avgBal.divide(new BigDecimal(monthEndList.size()), 2, RoundingMode.HALF_UP));
                                    } else {
                                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                                        if (newBalPojo == null) {
                                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                                        } else {
                                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                            } else {
                                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                            }
                                        }
                                    }
                                }
                                rbiSossPojo.setgNo(gNo);
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
                        } else {
                            rbiSossPojo = new RbiSossPojo();
                            for (int l = 0; l < dates.size(); l++) {
                                if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                    acctBal = rbiRemote.getAcctsAndBalAmtRangeWise(params);
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = acctBal.getNumberOFAcct();
                                    } else {
                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                    }
                                } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                    acctBal = rbiRemote.getAcctsAndBalTimeRangeWise(params);
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = acctBal.getNumberOFAcct();
                                    } else {
                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                    }
                                } else if (rangeBaseOn.equalsIgnoreCase("Dt")) {
                                    acctBal = rbiRemote.getNpaAcctsAndBal(params);
                                    if (countApplicable.equalsIgnoreCase("Y")) {
                                        noOfAc = acctBal.getNumberOFAcct();
                                    } else {
                                        bal = new BigDecimal(formatter.format(acctBal.getBalance().divide(repOpt).doubleValue()));
                                    }
                                }
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(noOfAc.toString()));
                                } else {
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal);
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal.multiply(new BigDecimal("-1")));
                                    } else {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, (fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : bal.abs());
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
                        Vector ele = null;
                        for (int l = 0; l < dates.size(); l++) {
                            bal = new BigDecimal("0");
                            List reportList2 = em.createNativeQuery("select date_format(max(repfridate),'%Y%m%d') from ho_reportingfriday where repfridate < '" + dates.get(l) + "'").getResultList();
                            if (reportList2.isEmpty()) {
                                throw new ApplicationException("Last Reporting Friday Date does not defined...");
                            }
                            ele = (Vector) reportList2.get(0);
                            String repFriDate = ele.get(0).toString();
                            repFriDate = CbsUtil.dateAdd(repFriDate, -14);
                            bal = hoRemote.getNewNdtl("0A", repFriDate);
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal.divide(repOpt));
                        }
                        rbiSossPojo.setgNo(gNo);
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
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        BigDecimal bal = new BigDecimal("0.0");
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                            for (int l = 0; l < dates.size(); l++) {
                                double balPL = glReport.IncomeExp(dates.get(l), "0A", "0A");
                                if (rbiSossPojo.getClassification().equalsIgnoreCase("L") && balPL >= 0) {
                                    if (l == 0) {
                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else if (l == 1) {
                                        rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else if (l == 2) {
                                        rbiSossPojo.setThirdAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    }
//                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL < 0) {
                                    if (reportName.contains("STMT1") && rbiSossPojo.getgNo() == 1) {
                                        if (l == 0) {
                                            rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                        } else if (l == 1) {
                                            rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                        } else if (l == 2) {
                                            rbiSossPojo.setThirdAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                        }
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else {
                                        if (l == 0) {
                                            rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        } else if (l == 1) {
                                            rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        } else if (l == 2) {
                                            rbiSossPojo.setThirdAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                        }
//                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())).abs());
                                    }
                                } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL == 0) {
                                    if (l == 0) {
                                        rbiSossPojo.setAmt(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else if (l == 1) {
                                        rbiSossPojo.setSecondAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    } else if (l == 2) {
                                        rbiSossPojo.setThirdAmount(new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                    }
//                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(formatter.format(new BigDecimal(balPL).divide(repOpt).doubleValue())));
                                }
                            }
                        } else {
//                            bal = rbiRemote.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula());
//                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal);
                            BigDecimal[] arr = getValueFromFormulaForFormOne(rbiPojoTable, rbiSossPojo.getFormula(), dates);
                            rbiSossPojo.setAmt((arr[0] == null) ? new BigDecimal("0") : arr[0]);
                            rbiSossPojo.setSecondAmount((arr[1] == null) ? new BigDecimal("0") : arr[1]);
                            rbiSossPojo.setThirdAmount((arr[2] == null) ? new BigDecimal("0") : arr[2]);
                            rbiSossPojo.setFourthAmount((arr[3] == null) ? new BigDecimal("0") : arr[3]);
                            rbiSossPojo.setFifthAmount((arr[4] == null) ? new BigDecimal("0") : arr[4]);
                            rbiSossPojo.setSixthAmount((arr[5] == null) ? new BigDecimal("0") : arr[5]);
                            rbiSossPojo.setSeventhAmount((arr[6] == null) ? new BigDecimal("0") : arr[6]);
                        }
                    }
                }
            }
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public BigDecimal[] getValueFromFormulaForFormOne(List<RbiSossPojo> dataList, String formula, List<String> dates)
            throws ApplicationException {
        BigDecimal[] amtList = new BigDecimal[7];
        amtList[0] = new BigDecimal("0");
        amtList[1] = new BigDecimal("0");
        amtList[2] = new BigDecimal("0");
        amtList[3] = new BigDecimal("0");
        amtList[4] = new BigDecimal("0");
        amtList[5] = new BigDecimal("0");
        amtList[6] = new BigDecimal("0");
        try {
            String exp = "";
            String[] arr = formula.split(" ");
            for (int k = 0; k < dates.size(); k++) {
                for (int i = 0; i < arr.length; i++) {
                    BigDecimal balance = new BigDecimal("0");
                    if (arr[i].contains(",")) {
                        balance = getOperandBalanceForFormOne(dataList, arr[i], k);
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
                BigDecimal bal = PostfixEvaluator.evaluate(exp);
                if (k == 0) {
                    amtList[0] = bal;
                } else if (k == 1) {
                    amtList[1] = bal;
                } else if (k == 2) {
                    amtList[2] = bal;
                } else if (k == 3) {
                    amtList[3] = bal;
                } else if (k == 4) {
                    amtList[4] = bal;
                } else if (k == 5) {
                    amtList[5] = bal;
                } else if (k == 6) {
                    amtList[6] = bal;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return amtList;
    }

    private BigDecimal getOperandBalanceForFormOne(List<RbiSossPojo> dataList, String csvExp,
            int amtIndex) throws ApplicationException {
        try {
            BigDecimal balance = new BigDecimal("0");
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        balance = setOperandBalance(pojo, balance, amtIndex);
                    }
                }
            } else if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        balance = setOperandBalance(pojo, balance, amtIndex);
                    }
                }
            } else if (arr[1] != 0 && arr[2] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        balance = setOperandBalance(pojo, balance, amtIndex);
                    }
                }
            } else if (arr[1] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        balance = setOperandBalance(pojo, balance, amtIndex);
                    }
                }
            } else {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0])) {
                        balance = setOperandBalance(pojo, balance, amtIndex);
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal setOperandBalance(RbiSossPojo pojo, BigDecimal balance, int k) throws ApplicationException {
        if (k == 0) {
            balance = balance.add(pojo.getAmt() == null ? new BigDecimal("0") : pojo.getAmt());
        } else if (k == 1) {
            balance = balance.add(pojo.getSecondAmount() == null ? new BigDecimal("0") : pojo.getSecondAmount());
        } else if (k == 2) {
            balance = balance.add(pojo.getThirdAmount() == null ? new BigDecimal("0") : pojo.getThirdAmount());
        } else if (k == 3) {
            balance = balance.add(pojo.getFourthAmount() == null ? new BigDecimal("0") : pojo.getFourthAmount());
        } else if (k == 4) {
            balance = balance.add(pojo.getFifthAmount() == null ? new BigDecimal("0") : pojo.getFifthAmount());
        } else if (k == 5) {
            balance = balance.add(pojo.getSixthAmount() == null ? new BigDecimal("0") : pojo.getSixthAmount());
        } else if (k == 6) {
            balance = balance.add(pojo.getSeventhAmount() == null ? new BigDecimal("0") : pojo.getSeventhAmount());
        }
        return balance;
    }

    public List<Oss4PartACombinedPojo> getStaementFourPartAAndB(List<String> dates, BigDecimal repOpt, String reptName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<Oss4PartACombinedPojo> mainList = new ArrayList<Oss4PartACombinedPojo>();
        List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();          //First Part DataList
        List<RbiSos4Pojo> s4List = new ArrayList<RbiSos4Pojo>();                //Second Part DataList
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reptName + "' and '" + dates.get(2) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            } else {
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dates.get(0), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                List<NpaStatusReportPojo> resultList1 = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dates.get(1), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                List<NpaStatusReportPojo> resultList2 = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dates.get(2), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
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
                    for (int l = 0; l < dates.size(); l++) {
                        List<NpaStatusReportPojo> finalList = null;
                        if (l == 0) {
                            finalList = resultList;
                        } else if (l == 1) {
                            finalList = resultList1;
                        } else if (l == 2) {
                            finalList = resultList2;
                        }
                        if (!glHeadFrom.equals("")) {  //FROM GLHEAD CHECKING AND DATA FILLING  [For Deductions]
                            AdditionalStmtPojo stmtPojo = new AdditionalStmtPojo();
                            stmtPojo.setBrnCode(orgnCode);
                            stmtPojo.setClassification(classification);
                            stmtPojo.setDate(dates.get(l));
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
                                stmtPojo.setDate(dates.get(l));
                                stmtPojo.setBrnCode(orgnCode);
                                stmtPojo.setRangeBasedOn(rangeBaseOn);
                                stmtPojo.setFromRange(rangeFrom);
                                stmtPojo.setToRange(rangeTo);
                                stmtPojo.setNpaClassification(npaClassification);
                                stmtPojo.setFlag(fSGNo);
                                AcctBalPojo balPojo = partDRemote.getAmountBasedOnNpaClassificationForOss4(stmtPojo, finalList);
                                calculatedAmount = balPojo.getBalance();
                            } else {    //For Substandard And Loss
                                AdditionalStmtPojo stmtPojo = new AdditionalStmtPojo();
                                stmtPojo.setNature(acNature);
                                stmtPojo.setAcType(acType);
                                stmtPojo.setClassification(classification);
                                stmtPojo.setDate(dates.get(l));
                                stmtPojo.setBrnCode(orgnCode);
                                stmtPojo.setRangeBasedOn(rangeBaseOn);
                                stmtPojo.setFromRange("");
                                stmtPojo.setToRange("");
                                stmtPojo.setNpaClassification(npaClassification);
                                stmtPojo.setFlag(fSGNo);
                                AcctBalPojo balPojo = partDRemote.getAmountBasedOnNpaClassificationForOss4(stmtPojo, finalList);
                                calculatedAmount = balPojo.getBalance();
                            }
                        }
                        pojo = monthlyRemote.setReportAmount(pojo, l, calculatedAmount.abs().divide(repOpt));
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

                    rbiPojoTable.add(pojo);
                }
            }
            //Now calculation of formula rows
            for (int k = 0; k < rbiPojoTable.size(); k++) {
                RbiSossPojo fPojo = rbiPojoTable.get(k);
                if (!fPojo.getFormula().isEmpty()) {
                    for (int l = 0; l < dates.size(); l++) {
                        fPojo = monthlyRemote.setReportAmount(fPojo, l, rbiRemote.getValueFromFormula(rbiPojoTable, fPojo.getFormula()));
                    }
                }
            }
            /*II-Part Calculation*/

            for (int j = 0; j < dates.size(); j++) {
                BigDecimal fAmt = new BigDecimal("0");
                BigDecimal sAmt = new BigDecimal("0");
                BigDecimal tAmt = new BigDecimal("0");
                BigDecimal lAmt = new BigDecimal("0");
                RbiSos4Pojo qPojo = new RbiSos4Pojo();

                String qEndDt = dates.get(j);
                qPojo.setfGNo(dmy.format(ymd.parse(qEndDt))); // Setting Quarter Date

                String startDt = "", endDt = "";
                String calDt = ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(CbsUtil.yearAdd(qEndDt, -1))));
                startDt = CbsUtil.dateAdd(calDt, 1);
                endDt = qEndDt;
                List<NpaStatusReportPojo> resultListBegin = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", calDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                if (!resultListBegin.isEmpty()) {
                    for (int o = 0; o < resultListBegin.size(); o++) {
                        fAmt = fAmt.add(resultListBegin.get(o).getBalance());
                    }
                }
                List<NpaStatusReportPojo> resultListDuring = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", startDt, qEndDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                if (!resultListDuring.isEmpty()) {
                    for (int o = 0; o < resultListDuring.size(); o++) {
                        sAmt = sAmt.add(resultListDuring.get(o).getBalance());
                    }
                }
                List<NpaStatusReportPojo> resultListEnd = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", qEndDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
                if (!resultListEnd.isEmpty()) {
                    for (int o = 0; o < resultListEnd.size(); o++) {
                        lAmt = lAmt.add(resultListEnd.get(o).getBalance());
                    }
                }

                qPojo.setnAmount(fAmt.abs().divide(repOpt));//Beforee Quarter Start Date
                qPojo.setFourthAmount(sAmt.abs().divide(repOpt));//New NPA addition during Quarter
                qPojo.setTrdAmount(((fAmt.abs().add(sAmt.abs()).subtract(lAmt.abs())).compareTo(new BigDecimal("0")) <= 0) ? new BigDecimal(0) : (fAmt.abs().add(sAmt.abs()).subtract(lAmt.abs())).divide(repOpt));
                qPojo.setTotAmount(lAmt.abs().divide(repOpt));//Quaarter End NPA Bal
                s4List.add(qPojo);
            }
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

    public Object[] getQuarterEndDtForCurDt(String brncode, String reportDt) throws ApplicationException {
        try {
            if (brncode.equals("0A")) {
                brncode = "90";
            }
            String fYearEndDate = "", qEndDate = "";
            List<String> qEndDateList = new CopyOnWriteArrayList<String>();
            qEndDate = reportDt;
            qEndDateList.add(qEndDate);
            for (int i = 0; i < 3; i++) {
                qEndDate = CbsUtil.monthAdd(qEndDate, -3);
                qEndDateList.add(ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(qEndDate))));
            }
            for (String qEndDtLoop : qEndDateList) {
                if (ymd.parse(qEndDtLoop).compareTo(ymd.parse(reportDt)) == 1) {
                    qEndDateList.remove(qEndDtLoop);
                }
            }
            if (qEndDateList.size() == 4) {
                qEndDateList.remove(3);
            }
            Object[] arr = qEndDateList.toArray();
            Arrays.sort(arr);
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RbiSos4Pojo> getStaementFivePartA(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION, REFER_CONTENT  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            BigDecimal totalNpa = new BigDecimal("0");
//            List<ProvDetailOfLoanAccounts> provList = rbiRemote.getProvisionAccordingToLoan("SOSS3.PART-A", dmy.format(ymd.parse(dates.get(0))), "0A", repOpt);
//            if (provList.isEmpty()) {
//                throw new ApplicationException("DATA DOES NOT EXIST FOR PROVISION!!!!");
//            }
            List<NpaStatusReportPojo> npaResultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dates.get(0), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
            for (int n = 0; n < npaResultList.size(); n++) {
                totalNpa = totalNpa.add(npaResultList.get(n).getBalance());
            }
            BigDecimal totalLoan = new BigDecimal("0");
            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
            Map<String, LoanMisCellaniousPojo> mapLoanMis = new HashMap<String, LoanMisCellaniousPojo>();
            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0",
                    dates.get(0), "A", 0, 999999999999.99, "S", "0",
                    "0", "0", "0", "0", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            for (int z = 0; z < resultList.size(); z++) {
                LoanMisCellaniousPojo loanMis;
                if (!resultList.isEmpty()) {
                    for (int e = 0; e < resultList.size(); e++) {
                        loanMis = resultList.get(e);
                        mapLoanMis.put(resultList.get(e).getAcNo(), loanMis);
                    }
                }
                if (resultList.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    totalLoan = totalLoan.add(resultList.get(z).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultList.get(z).getOutstanding() : new BigDecimal("0"));
                } else {
                    totalLoan = totalLoan.add(resultList.get(z).getOutstanding());
                }
            }
            String acnoNotIn = "";
            if (reportFormat.equalsIgnoreCase("Y")) {
                if (oss1Query.isEmpty()) {
                    throw new ApplicationException("DATA DOES NOT EXIST FOR THIS STATEMENT!!!");
                } else {
                    for (int i = 0; i < oss1Query.size(); i++) {                        
                        BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0), provAmt = new BigDecimal(0);
                        Vector oss1Vect = (Vector) oss1Query.get(i);
                        Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                        String reptName = oss1Vect.get(1).toString();
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
                        String refContent = oss1Vect.get(24).toString();
                        if (!npaClassification.equalsIgnoreCase("")) {
                            AdditionalStmtPojo params = new AdditionalStmtPojo();
                            params.setAcType(acType);
                            params.setNature(acNature);
                            params.setBrnCode(orgnCode);
                            params.setClassification(classification);
                            params.setDate(dates.get(0));
                            params.setToDate(dates.get(0));
                            params.setRangeBasedOn(rangeBaseOn);
                            params.setFromRange(rangeFrom);
                            params.setToRange(rangeTo);
                            params.setGlFromHead(fGNo);
                            params.setGlToHead(npaClassification);
                            params.setFlag(fSGNo);
                            if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                                String security = refContent;
                                String acno = "";//vect.get(0).toString();
                                String custName = "";//vect.get(0).toString();
                                totAmount = new BigDecimal(0);
                                Long totAccountNo = 0l, nAccountNo = 0l;
                                nAmount = new BigDecimal(0); provAmt = new BigDecimal(0);
                                for (int t = 0; t < resultList.size(); t++) {
                                    LoanMisCellaniousPojo val = resultList.get(t);
                                    if (security.equalsIgnoreCase(resultList.get(t).getSecurity())) {
                                        acno = resultList.get(t).getAcNo();
                                        custName = resultList.get(t).getCustName();
                                        
                                        if (resultList.get(t).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            totAmount = resultList.get(t).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(t).getOutstanding();
                                            totAccountNo = totAccountNo + (resultList.get(t).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? 0 : 1);
                                        } else {
                                            totAmount = resultList.get(t).getOutstanding();
                                            totAccountNo = totAccountNo + 1;
                                        }
                                        for (int npa = 0; npa < npaResultList.size(); npa++) {
                                            String npaAc = npaResultList.get(npa).getAcno();
                                            if (npaAc.equalsIgnoreCase(acno)) {
                                                nAccountNo = nAccountNo + 1;
                                                nAmount = nAmount.add(npaResultList.get(npa).getBalance());
                                                provAmt = provAmt.add(npaResultList.get(npa).getProvAmt());
                                            }
                                        }                                        
                                    }
                                }
                                RbiSos4Pojo pojo = new RbiSos4Pojo();
                                pojo.setsNo(sNo);
                                pojo.setReportName(reptName);
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
                                pojo.setAcNo(acno);
                                pojo.setCustName(custName);
                                pojo.setDescription(description);
                                pojo.setTotAccountNo(totAccountNo);
                                if (totAmount.compareTo(new BigDecimal("0")) != 0) {
                                    pojo.setTotAmount(totAmount.abs().divide(repOpt));
                                    pojo.setPerTotalAmt(((totAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                                } else {
                                    pojo.setTotAmount(totAmount);
                                    pojo.setPerTotalAmt(totAmount);
                                }
                                pojo.setnAccountNo(nAccountNo);
                                if (nAmount.compareTo(new BigDecimal("0")) != 0) {
                                    pojo.setnAmount(nAmount.abs().divide(repOpt));
                                    pojo.setProvMade(provAmt.abs().divide(repOpt));
                                    pojo.setPerNpaAmt(((nAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                                } else {
                                    pojo.setnAmount(nAmount);
                                    pojo.setPerNpaAmt(nAmount);
                                    pojo.setProvMade(provAmt);
                                }
                                System.out.println("Acno:" + totAccountNo + "; Npa:" + nAccountNo);
                                rbiPojoTable.add(pojo);
                            }
                        } else {
                            Long totAccountNo = 0l, nAccountNo = 0l;
                            totAmount = new BigDecimal("0");
                            nAmount = new BigDecimal("0");
                            RbiSos4Pojo pojo = new RbiSos4Pojo();
                            pojo.setsNo(sNo);
                            pojo.setReportName(reptName);
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
                            pojo.setAcNo("");
                            pojo.setCustName("");
                            pojo.setDescription(description);
                            pojo.setTotAccountNo(totAccountNo);
                            pojo.setTotAmount(totAmount);
                            pojo.setPerTotalAmt(totAmount);
                            pojo.setnAccountNo(nAccountNo);
                            pojo.setnAmount(nAmount);
                            pojo.setPerNpaAmt(nAmount);
                            pojo.setProvMade(provAmt);
                            rbiPojoTable.add(pojo);
                        }
                    }
                }
            } else {
                if (oss1Query.isEmpty()) {
                    throw new ApplicationException("DATA DOES NOT EXIST FOR THIS STATEMENT!!!");
                } else {
                    for (int i = 0; i < oss1Query.size(); i++) {
                        Long totAccountNo = 0l, nAccountNo = 0l;
                        BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0), provAmt = new BigDecimal(0);
                        Vector oss1Vect = (Vector) oss1Query.get(i);
                        Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                        String reptName = oss1Vect.get(1).toString();
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
                        String refContent = oss1Vect.get(24).toString();
                        if (!npaClassification.equalsIgnoreCase("")) {
                            AdditionalStmtPojo params = new AdditionalStmtPojo();
                            params.setAcType(acType);
                            params.setNature(acNature);
                            params.setBrnCode(orgnCode);
                            params.setClassification(classification);
                            params.setDate(dates.get(0));
                            params.setToDate(dates.get(0));
                            params.setRangeBasedOn(rangeBaseOn);
                            params.setFromRange(rangeFrom);
                            params.setToRange(rangeTo);
                            params.setGlFromHead(fGNo);
                            params.setGlToHead(npaClassification);
                            params.setFlag(fSGNo);
                            if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                                String security = refContent;
                                String acno = "";//vect.get(0).toString();
                                String custName = "";//vect.get(0).toString();
                                totAmount = new BigDecimal(0);

                                for (int t = 0; t < resultList.size(); t++) {
                                    LoanMisCellaniousPojo val = resultList.get(t);
                                    if (security.equalsIgnoreCase(resultList.get(t).getSecurity())) {
                                        acno = resultList.get(t).getAcNo();
                                        custName = resultList.get(t).getCustName();
                                        nAmount = new BigDecimal(0);
                                        provAmt = new BigDecimal(0);
                                        if (resultList.get(t).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            totAmount = resultList.get(t).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(t).getOutstanding();
                                        } else {
                                            totAmount = resultList.get(t).getOutstanding();
                                        }
                                        for (int npa = 0; npa < npaResultList.size(); npa++) {
                                            String npaAc = npaResultList.get(npa).getAcno();
                                            if (npaAc.equalsIgnoreCase(acno)) {
                                                nAmount = npaResultList.get(npa).getBalance();
                                                provAmt = npaResultList.get(npa).getProvAmt();
                                            }
                                        }
                                        RbiSos4Pojo pojo = new RbiSos4Pojo();
                                        pojo.setsNo(sNo);
                                        pojo.setReportName(reptName);
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
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custName);
                                        pojo.setDescription(security.equalsIgnoreCase("") ? "OTHER" : security);
                                        pojo.setTotAccountNo(totAccountNo);
                                        if (totAmount.compareTo(new BigDecimal("0")) != 0) {
                                            pojo.setTotAmount(totAmount.abs().divide(repOpt));
                                            pojo.setPerTotalAmt(((totAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                                        } else {
                                            pojo.setTotAmount(totAmount);
                                            pojo.setPerTotalAmt(totAmount);
                                        }
                                        pojo.setnAccountNo(nAccountNo);
                                        if (nAmount.compareTo(new BigDecimal("0")) != 0) {
                                            pojo.setnAmount(nAmount.abs().divide(repOpt));
                                            pojo.setProvMade(provAmt.abs().divide(repOpt));
                                            pojo.setPerNpaAmt(((nAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                                        } else {
                                            pojo.setnAmount(nAmount);
                                            pojo.setPerNpaAmt(nAmount);
                                            pojo.setProvMade(provAmt);
                                        }
                                        if (totAmount.compareTo(new BigDecimal("0")) != 0) {
                                            rbiPojoTable.add(pojo);
                                        }
                                    }
                                }
                            }
                        } else {
                            totAmount = new BigDecimal("0");
                            nAmount = new BigDecimal("0");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rbiPojoTable;
    }

    public List<RbiSos4Pojo> getStaementFive(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<RbiSos4Pojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            BigDecimal totalNpa = new BigDecimal("0");
            List<ProvDetailOfLoanAccounts> provList = rbiRemote.getProvisionAccordingToLoan("SOSS3.PART-A", dmy.format(ymd.parse(dates.get(0))), "0A", repOpt);
            if (provList.isEmpty()) {
                throw new ApplicationException("DATA DOES NOT EXIST FOR PROVISION!!!!");
            }
            List<NpaStatusReportPojo> npaResultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dates.get(0), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
            for (int n = 0; n < npaResultList.size(); n++) {
                totalNpa = totalNpa.add(npaResultList.get(n).getBalance());
            }
            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
            BigDecimal totalLoan = new BigDecimal("0");
            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0",
                    dates.get(0), "A", 0, 999999999999.99, "S", "0",
                    "0", "0", "0", "0", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            for (int z = 0; z < resultList.size(); z++) {
                if (resultList.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    totalLoan = totalLoan.add(resultList.get(z).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultList.get(z).getOutstanding() : new BigDecimal("0"));
                } else {
                    totalLoan = totalLoan.add(resultList.get(z).getOutstanding());
                }
            }
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("DATA DOES NOT EXIST FOR THIS STATEMENT!!!");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
                    Long totAccountNo = 0l, nAccountNo = 0l;
                    BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0), provAmt = new BigDecimal(0);

                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String reptName = oss1Vect.get(1).toString();
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

                    if (sssGNo != 0) {
                        AdditionalStmtPojo params = new AdditionalStmtPojo();
                        params.setAcType(acType);
                        params.setNature(acNature);
                        params.setBrnCode(orgnCode);
                        params.setClassification(classification);
                        params.setDate(dates.get(0));
                        params.setToDate(dates.get(0));
                        params.setRangeBasedOn(rangeBaseOn);
                        params.setFromRange(rangeFrom);
                        params.setToRange(rangeTo);
                        params.setGlFromHead(fGNo);
                        params.setGlToHead(npaClassification);
                        params.setFlag(fSGNo);

                        if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                            params.setOrgBrCode("Oss4PartD");
                            resultList = new ArrayList<LoanMisCellaniousPojo>();
                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    dates.get(0), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, "0", "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "D", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");

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
                                for (int npa = 0; npa < npaResultList.size(); npa++) {
                                    String npaAc = npaResultList.get(npa).getAcno();
                                    if (npaAc.equalsIgnoreCase(acno)) {
                                        nAccountNo = nAccountNo + 1;
                                        nAmount = nAmount.add(npaResultList.get(npa).getBalance());
                                    }
                                }
                                for (int l = 0; l < provList.size(); l++) {
                                    String provAcNo = provList.get(l).getAcno();
                                    if (acno.equalsIgnoreCase(provAcNo)) {
                                        if (!(provList.get(l).getOutstanding() == null)) {
                                            if (!(provList.get(l).getOutstanding().compareTo(new BigDecimal(0)) == 1)) {
                                                provAmt = provAmt.add(new BigDecimal(provList.get(l).getProvamt().toString()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        totAmount = new BigDecimal("0");
                        nAmount = new BigDecimal("0");
                    }

                    RbiSos4Pojo pojo = new RbiSos4Pojo();
                    pojo.setsNo(sNo);
                    pojo.setReportName(reptName);
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
                        pojo.setPerTotalAmt(((totAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                    } else {
                        pojo.setTotAmount(totAmount);
                        pojo.setPerTotalAmt(totAmount);
                    }

                    pojo.setnAccountNo(nAccountNo);
                    if (nAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setnAmount(nAmount.abs().divide(repOpt));
                        pojo.setPerNpaAmt(((nAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoan.divide(repOpt), RoundingMode.HALF_UP).abs());
                    } else {
                        pojo.setnAmount(nAmount);
                        pojo.setPerNpaAmt(nAmount);
                    }
                    pojo.setProvMade(provAmt);
                    rbiPojoTable.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<RbiSos4Pojo> getStaementSix(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<RbiSos4Pojo> rbiPojoTable = new ArrayList<RbiSos4Pojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            List<LoanMisCellaniousPojo> resultListPrev = new ArrayList<LoanMisCellaniousPojo>();
            BigDecimal totalLoanPrev = new BigDecimal("0");
            resultListPrev = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0",
                    dates.get(0), "A", 0, 999999999999.99, "S", "0",
                    "0", "0", "0", "0", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            for (int z = 0; z < resultListPrev.size(); z++) {
                if (resultListPrev.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    totalLoanPrev = totalLoanPrev.add(resultListPrev.get(z).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultListPrev.get(z).getOutstanding() : new BigDecimal("0"));
                } else {
                    totalLoanPrev = totalLoanPrev.add(resultListPrev.get(z).getOutstanding());
                }
            }
            List<LoanMisCellaniousPojo> resultListCur = new ArrayList<LoanMisCellaniousPojo>();
            BigDecimal totalLoanCur = new BigDecimal("0");
            resultListCur = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0",
                    dates.get(1), "A", 0, 999999999999.99, "S", "0",
                    "0", "0", "0", "0", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            for (int z = 0; z < resultListCur.size(); z++) {
                if (resultListCur.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    totalLoanCur = totalLoanCur.add(resultListCur.get(z).getOutstanding().compareTo(new BigDecimal("0")) < 0 ? resultListCur.get(z).getOutstanding() : new BigDecimal("0"));
                } else {
                    totalLoanCur = totalLoanCur.add(resultListCur.get(z).getOutstanding());
                }
            }
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
                    Long totAccountNo = 0l, nAccountNo = 0l;
                    BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0);

                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String reptName = oss1Vect.get(1).toString();
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

                    if (sssGNo != 0) {
                        AdditionalStmtPojo params = new AdditionalStmtPojo();
                        params.setAcType(acType);
                        params.setNature(acNature);
                        params.setBrnCode(orgnCode);
                        params.setClassification(classification);
                        params.setDate(dates.get(1));
                        params.setToDate(dates.get(1));
                        params.setRangeBasedOn(rangeBaseOn);
                        params.setFromRange(rangeFrom);
                        params.setToRange(rangeTo);
                        params.setGlFromHead(fGNo);
                        params.setGlToHead(npaClassification);
                        params.setFlag(fSGNo);
                        if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                            params.setOrgBrCode("Oss4PartD");
                            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    dates.get(0), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, "0", "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "D", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");

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
                            List<LoanMisCellaniousPojo> resultList1 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList1 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    dates.get(1), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, "0", "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "D", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");

                            for (int k = 0; k < resultList1.size(); k++) {
                                LoanMisCellaniousPojo val1 = resultList1.get(k);
                                if (resultList1.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmount = nAmount.add(resultList1.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList1.get(k).getOutstanding());
                                } else {
                                    nAmount = nAmount.add(resultList1.get(k).getOutstanding());
                                }
                                String acno1 = val1.getAcNo();
                                if (!acno1.isEmpty()) {
                                    nAccountNo = nAccountNo + 1;
                                }
                            }
                        }
                    } else {
                        totAmount = new BigDecimal("0");
                        nAmount = new BigDecimal("0");
                    }
                    RbiSos4Pojo pojo = new RbiSos4Pojo();
                    pojo.setsNo(i + 1);
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
                        pojo.setPerTotalAmt(((totAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoanPrev.divide(repOpt), RoundingMode.HALF_UP).abs());
                    } else {
                        pojo.setTotAmount(totAmount);
                        pojo.setPerTotalAmt(totAmount);
                    }

                    pojo.setnAccountNo(nAccountNo);
                    if (nAmount.compareTo(new BigDecimal("0")) != 0) {
                        pojo.setnAmount(nAmount.abs().divide(repOpt));
                        pojo.setPerNpaAmt(((nAmount.abs().divide(repOpt)).multiply(new BigDecimal("100"))).divide(totalLoanCur.divide(repOpt), RoundingMode.HALF_UP).abs());
                    } else {
                        pojo.setnAmount(nAmount);
                        pojo.setPerNpaAmt(nAmount);
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

    public List<LoanMisCellaniousPojo> getStaementSeven(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List sectorList = null;
        Vector sectorVect = null, tempVector = null;
        List<LoanMisCellaniousPojo> finalResult = new ArrayList<LoanMisCellaniousPojo>();
        List<NpaAccountDetailPojo> resultlist = new ArrayList<NpaAccountDetailPojo>();
        try {
            List<LoanMisCellaniousPojo> finalResult1 = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> finalResult2 = new ArrayList<LoanMisCellaniousPojo>();
            finalResult1 = loanReportFacade.cbsLoanMisReport("0A", "0", "", dates.get(0), "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "DIRREL", "", "", "", "", "", "", "", "Active", "0", "N", "S", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            finalResult2 = loanReportFacade.cbsLoanMisReport("0A", "0", "", dates.get(0), "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "DIR", "", "", "", "", "", "", "", "Active", "0", "N", "S", "0", "N", "0", "N", "N", "0", "0", "0", "0");
            resultlist = overDueReportFacade.getNpaDetail("ALL", "ALL", dates.get(0), "0A", "Y");
            finalResult.addAll(finalResult1);
            finalResult.addAll(finalResult2);
            if (!finalResult.isEmpty()) {
                for (int i = 0; i < finalResult.size(); i++) {
                    BigDecimal lienValue = new BigDecimal("0");
                    BigDecimal matValue = new BigDecimal("0");
                    String natureOfSec = "";
                    String matDt = "";
                    String dirName = "";
                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + finalResult.get(i).getAcNo() + "'").getResultList();
                    if (!loanSecurity.isEmpty()) {
                        for (int l = 0; l < loanSecurity.size(); l++) {
                            Vector vect = (Vector) loanSecurity.get(l);
                            lienValue = lienValue.add(new BigDecimal(vect.get(2).toString()));
                            natureOfSec = natureOfSec.concat(vect.get(3).toString());
                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                        }
                    }
                    if (!(finalResult.get(i).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC) || finalResult.get(i).getAcNature().equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                        List emiDetail = em.createNativeQuery("select max(duedt) from emidetails details where acno ='" + finalResult.get(i).getAcNo() + "'").getResultList();
                        if (!emiDetail.isEmpty()) {
                            Vector vect = (Vector) emiDetail.get(0);
                            matDt = dmy.format(y_m_dFormat.parse(vect.get(0).toString()));
                        }
                    }
                    String flag = "S";
                    String assetClass = "STANDARD";
                    BigDecimal provAmt = new BigDecimal("0");
                    if (finalResult.get(i).getSecured().equalsIgnoreCase("FLSEC")) {
                        flag = "S";
                    } else {
                        flag = "U";
                    }
                    List prov = em.createNativeQuery("select PROV_IN_PERCENT from cbs_scheme_delinquency_details where DELINQ_CYCLE='STD' and SCHEME_CODE = '" + finalResult.get(i).getSchemeCode() + "' and flag='" + flag + "'").getResultList();
                    if (!prov.isEmpty()) {
                        Vector vect = (Vector) prov.get(0);
                        provAmt = finalResult.get(i).getOutstanding().multiply(new BigDecimal(vect.get(0).toString()));
                    }
                    if (!resultlist.isEmpty()) {
                        for (int l = 0; l < resultlist.size(); l++) {
                            if (finalResult.get(i).getAcNo().equalsIgnoreCase(resultlist.get(l).getAcNo())) {
                                assetClass = resultlist.get(l).getStatus();
                                provAmt = resultlist.get(l).getProvAmt();
                            }
                        }
                    }
                    String relation = common.getRefRecDesc("210", finalResult.get(i).getRelation());
                    finalResult.get(i).setRelation(relation);
                    finalResult.get(i).setDirName(dirName);
                    finalResult.get(i).setNatureOfSecurity(natureOfSec);
                    finalResult.get(i).setValueOfSecurity(matValue.divide(repOpt));
                    finalResult.get(i).setSanctionAmt(finalResult.get(i).getSanctionAmt().divide(repOpt));
                    finalResult.get(i).setOutstanding(finalResult.get(i).getOutstanding().divide(repOpt));
                    finalResult.get(i).setExposureCategory(String.valueOf(finalResult.get(i).getOutstanding()));
                    finalResult.get(i).setSecurityMargin(new BigDecimal("0"));
                    finalResult.get(i).setMaturityDt(matDt);
                    finalResult.get(i).setProvAmt(provAmt.divide(repOpt));
                    finalResult.get(i).setClassification(assetClass);
                }
            } else {
                LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                finalResult.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return finalResult;
    }

    public List<Statement8PoJo> getStaementEight(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<Statement8PoJo> topDepBowList = new ArrayList<Statement8PoJo>();
        List dataList = new ArrayList();
        List<TopDepositorBorrowerPojo> resultList = misReport.getTopDepositorBorrower("0A", "Sanction Wise", "19000101", dates.get(0), 250000, reportName, "0A", "",0d,99999999999999d);
        String table1 = "", table2 = "";
        BigDecimal limit = new BigDecimal("0");
        try {
            List<RbiSossPojo> oss7PartAList = new ArrayList<RbiSossPojo>();
            List<RbiSossPojo> oss1List = new ArrayList<RbiSossPojo>();
            List oss1Query = em.createNativeQuery("SELECT distinct FORMULA  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'STMT8' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            if (!oss1Query.isEmpty()) {
                BigDecimal oss1Limit = new BigDecimal(0), oss7Limit = new BigDecimal(0);
                String formula = oss1Query.get(0).toString();
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
                for (int k = 0; k < oss1Query.size(); k++) {
                    Vector vect = (Vector) oss1Query.get(k);
                    String chk = vect.get(0).toString();
                    if (chk.contains("XBRLOSS7")) {
                        oss7PartAList = quarterlyRemote.getOss7PartA("XBRLOSS7-PART-A", dates.get(0), orgnCode, new BigDecimal("1"), "Y", osBlancelist, "Y");
                        oss1List = rbiSoss1And2Remote.getSOSS2("XBRLOSS1", dates.get(0), orgnCode, new BigDecimal("1"), "N", osBlancelist, "0");
                        break;
                    } else if (chk.contains("OSS7")) {
                        oss7PartAList = quarterlyRemote.getOss7PartA("OSS7-PART-A", dates.get(0), orgnCode, new BigDecimal("1"), "Y", osBlancelist, "Y");
                        oss1List = rbiSoss1And2Remote.getSOSS2("SOSS1", dates.get(0), orgnCode, new BigDecimal("1"), "N", osBlancelist, "0");
                        break;
                    }
                }
                for (int k = 0; k < oss1Query.size(); k++) {
                    Vector vect = (Vector) oss1Query.get(k);
                    formula = vect.get(0).toString();
                    String[] strArr = formula.split("@");
                    if (formula.contains("XBRLOSS7")) {
                        oss7Limit = rbiRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                    } else if (formula.contains("OSS7")) {
                        oss7Limit = rbiRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                    } else if (formula.contains("XBRLOSS1")) {
                        oss1Limit = rbiRemote.getValueFromFormula(oss1List, strArr[1]);
                    } else if (formula.contains("OSS1")) {
                        oss1Limit = rbiRemote.getValueFromFormula(oss1List, strArr[1]);
                    }
                }
                if (oss1Limit.compareTo(oss7Limit) > 0) {
                    limit = oss7Limit;
                } else if (oss7Limit.compareTo(oss1Limit) > 0) {
                    limit = oss1Limit;
                }
            }
            int sno = 1;
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    TopDepositorBorrowerPojo val = resultList.get(i);
                    Statement8PoJo pojo = new Statement8PoJo();

                    String natureOfSec = "";
                    BigDecimal matValue = new BigDecimal("0");
                    String custName = "";
                    BigDecimal sancAmt = new BigDecimal("0");
                    String sanctDt = "";
                    List custDetail = em.createNativeQuery("select custname from cbs_customer_master_detail where customerid ='" + val.getCustId() + "'").getResultList();
                    if (!custDetail.isEmpty()) {
                        Vector vect = (Vector) custDetail.get(0);
                        custName = vect.get(0).toString();
                    }
                    String cond = "";
                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(SecurityOption,'') from loansecurity where acno ='" + val.getAcNo() + "' and Status ='ACTIVE' ").getResultList();
                    if (!loanSecurity.isEmpty()) {
                        for (int l = 0; l < loanSecurity.size(); l++) {
                            Vector vect = (Vector) loanSecurity.get(l);
                            cond = vect.get(3).toString();
                            if (l == 0) {
                                natureOfSec = cond;
                            }
                            if (!natureOfSec.contains(vect.get(3).toString())) {
                                natureOfSec = natureOfSec.concat(", ".concat(vect.get(3).toString()));
                            }
                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                        }
                    }
                    String assetClass = "";
                    List sanctDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,''),PresentStatus from loan_appparameter where acno ='" + val.getAcNo() + "'").getResultList();
                    if (!sanctDtList.isEmpty()) {
                        Vector vect = (Vector) sanctDtList.get(0);
                        if (vect.get(1) != null) {
                            sanctDt = vect.get(1).toString();
                            if (sanctDt.equalsIgnoreCase("")) {
                                List opendt = em.createNativeQuery("select cast(openingdt as date) from accountmaster where acno ='" + val.getAcNo() + "' ").getResultList();
                                if (!opendt.isEmpty()) {
                                    Vector sancVect = (Vector) opendt.get(0);
                                    sanctDt = sancVect.get(0).toString();
                                }
                            }
                        } else {
                            List opendt = em.createNativeQuery("select cast(openingdt as date) from accountmaster where acno ='" + val.getAcNo() + "' ").getResultList();
                            if (!opendt.isEmpty()) {
                                sanctDt = opendt.get(0).toString();
                            }
                        }
                        sancAmt = new BigDecimal(vect.get(0).toString());
                        assetClass = vect.get(2).toString();
                    }
                    String loanType = "";
                    List typeOfLoan = em.createNativeQuery("select ACCTDESC from accounttypemaster where ACCTCODE =substring('" + val.getAcNo() + "',3,2)").getResultList();
                    if (!typeOfLoan.isEmpty()) {
                        Vector vect = (Vector) typeOfLoan.get(0);
                        loanType = vect.get(0).toString();
                    }
                    pojo.setsNo(sno);
                    pojo.setCustName(custName);
                    pojo.setCustId(val.getCustId());
                    pojo.setAcno(val.getAcNo());
                    pojo.setTypeOfLoan(loanType);
                    pojo.setSancDate(dmy.format(y_m_dFormat.parse(sanctDt)));
                    if ((sancAmt.compareTo(new BigDecimal(0)) == 1)) {
                        pojo.setSancLimit(sancAmt.divide(repOpt));
                    } else {
                        pojo.setSancLimit(sancAmt.divide(repOpt));
                    }
                    BigDecimal outStanding = new BigDecimal(common.getBalanceOnDate(val.getAcNo(), dates.get(0)));
                    if ((outStanding.compareTo(new BigDecimal(0)) == 1)) {
                        pojo.setOutstanding(outStanding.divide(repOpt));
                    } else {
                        pojo.setOutstanding(outStanding.divide(repOpt));
                    }
                    pojo.setNatOfSec(natureOfSec);
                    if ((matValue.compareTo(new BigDecimal(0)) == 1)) {
                        pojo.setValOfSec(matValue.divide(repOpt));
                    } else {
                        pojo.setValOfSec(matValue);
                    }
                    if (assetClass.equalsIgnoreCase("OPERATIVE")) {
                        pojo.setAssetClass("STANDARD");
                    } else {
                        pojo.setAssetClass(assetClass);
                    }
                    pojo.setRemarks("");
                    if ((limit.divide(repOpt).compareTo(outStanding.divide(repOpt).abs()) < 0) && (outStanding.compareTo(new BigDecimal("0")) < 0)) {
                        sno = sno + 1;
                        topDepBowList.add(pojo);
                    }
                }
            } else {
                Statement8PoJo pojo = new Statement8PoJo();
                pojo.setsNo(1);
                pojo.setCustName("");
                pojo.setCustId("");
                pojo.setAcno("");
                pojo.setTypeOfLoan("");
                pojo.setSancDate("");
                pojo.setSancLimit(new BigDecimal(0));
                pojo.setOutstanding(new BigDecimal(0));
                pojo.setValOfSec(new BigDecimal(0));
                pojo.setAssetClass("");
                pojo.setRemarks("");
                topDepBowList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return topDepBowList;
    }

    public List misSheetPart5Data(List osBlancelist, String reportName, BigDecimal repOpt, List<String> dates, String orgnCode) throws Exception {
        List dataList = new ArrayList();
        List<RbiSossPojo> oss7PartAList = new ArrayList<RbiSossPojo>();
        List<RbiSossPojo> oss1List = new ArrayList<RbiSossPojo>();
        try {
            BigDecimal limit = new BigDecimal("0");
            List oss1Query = em.createNativeQuery("SELECT distinct FORMULA,description  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            if (!oss1Query.isEmpty()) {
                BigDecimal indLimit = new BigDecimal(0), groupLimit = new BigDecimal(0);
                String formula = oss1Query.get(0).toString();
                for (int k = 0; k < oss1Query.size(); k++) {
                    Vector vect = (Vector) oss1Query.get(k);
                    String chk = vect.get(0).toString();
                    if (chk.contains("XBRLOSS7")) {
                        oss7PartAList = quarterlyRemote.getOss7PartA("XBRLOSS7-PART-A", dates.get(0), orgnCode, new BigDecimal("1"), "Y", osBlancelist, "N");
                        break;
                    } else if (chk.contains("OSS7")) {
                        oss7PartAList = quarterlyRemote.getOss7PartA("OSS7-PART-A", dates.get(0), orgnCode, new BigDecimal("1"), "Y", osBlancelist, "N");
                        break;
                    }
                }
                for (int k = 0; k < oss1Query.size(); k++) {
                    Vector vect = (Vector) oss1Query.get(k);
                    formula = vect.get(0).toString();
                    String[] strArr = formula.split("@");
                    if (formula.contains("XBRLOSS7")) {
                        limit = rbiRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                        if (vect.get(1).toString().contains("Individual")) {
                            indLimit = limit;
                        } else if (vect.get(1).toString().contains("Group")) {
                            groupLimit = limit;
                        }
                    } else if (formula.contains("OSS7")) {
                        limit = rbiRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                        if (vect.get(1).toString().contains("Individual")) {
                            indLimit = limit;
                        } else if (vect.get(1).toString().contains("Group")) {
                            groupLimit = limit;
                        }
                    }
                }
                dataList.add(groupLimit);
                dataList.add(indLimit);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
        return dataList;
    }

    public List misSheetPart6Data(List osBlancelist, String reportName, BigDecimal repOpt, List<String> dates, String orgnCode) throws Exception {
        List dataList = new ArrayList();
        List<RbiSossPojo> oss7PartAList = new ArrayList<RbiSossPojo>();
        List<RbiSossPojo> oss1List = new ArrayList<RbiSossPojo>();
        try {
            BigDecimal limit = new BigDecimal("0");
            List oss1Query = em.createNativeQuery("SELECT distinct FORMULA,description  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            if (!oss1Query.isEmpty()) {
                BigDecimal tenPerOfTot = new BigDecimal(0), fivePerOfTot = new BigDecimal(0);
                String formula = oss1Query.get(0).toString();
                for (int k = 0; k < oss1Query.size(); k++) {
                    Vector vect = (Vector) oss1Query.get(k);
                    String chk = vect.get(0).toString();
                    if (chk.contains("XBRLOSS1")) {
                        oss1List = rbiSoss1And2Remote.getSOSS2("XBRLOSS1", dates.get(0), orgnCode, new BigDecimal("1"), "N", osBlancelist, "0");
                        break;
                    } else if (chk.contains("SOSS1")) {
                        oss1List = rbiSoss1And2Remote.getSOSS2("SOSS1", dates.get(0), orgnCode, new BigDecimal("1"), "N", osBlancelist, "0");
                        break;
                    }
                }
                BigDecimal bal = new BigDecimal("100");
                for (int k = 0; k < oss1Query.size(); k++) {
                    Vector vect = (Vector) oss1Query.get(k);
                    formula = vect.get(0).toString();
                    if (formula.contains("XBRLOSS1")) {
                        String[] strArr = formula.split("@");
                        bal = bal.add(rbiRemote.getValueFromFormula(oss1List, strArr[1]));
                    } else if (formula.contains("SOSS1")) {
                        String[] sb = formula.split("~");
                        String[] st = sb[1].split("@");
                        String newFormula = sb[0].concat(bal.toString()).concat(sb[2]);
                        bal = bal.add(rbiRemote.getValueFromFormula(oss1List, newFormula));
                    }
                }
                limit = bal;
                tenPerOfTot = limit.multiply(new BigDecimal("10")).divide(new BigDecimal("100"));
                fivePerOfTot = limit.multiply(new BigDecimal("5")).divide(new BigDecimal("100"));
                dataList.add(limit);
                dataList.add(tenPerOfTot);
                dataList.add(fivePerOfTot);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
        return dataList;
    }

    public List misSheetPart1Data(String fromDt, String toDt, BigDecimal repOpt) throws Exception {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select cast(ifnull(sum(amt),0) as decimal(25,2)),ifnull(min(roi),0),ifnull(max(roi),0),ifnull(sum(int_amt),0) from investment_call_master where deal_dt between '" + fromDt + "' and '" + toDt + "' order by deal_dt").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<Statement9PoJo> getStaementNine(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<Statement9PoJo> dtlDataList = new ArrayList<Statement9PoJo>();
        List resultlist = getStaementTwo(dates, repOpt, reportName, orgnCode, reportIn, reportIn);
        try {
            for (int k = 0; k < resultlist.size(); k++) {
                RbiSossPojo val = (RbiSossPojo) resultlist.get(k);
                Statement9PoJo pojo = new Statement9PoJo();
                pojo.setsNo(k + 1);
                List ar = common.getBranchNameandAddress("90");
                pojo.setBranchName(ar.get(0).toString());
                pojo.setPartyName("");
                pojo.setBeneficiaryName("");
                pojo.setValue(val.getAmt());
                pojo.setIssueDt("");
                pojo.setExpiryDt("");
                pojo.setSecurity("");
                pojo.setRemarks("");
                dtlDataList.add(pojo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dtlDataList;
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

    public List<Oss7BusinessPojo> getStaementTen(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException {
        List<Oss7BusinessPojo> dataList = new ArrayList<Oss7BusinessPojo>();
        try {
            /*Calling all the sub report and creating the list*/
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
            List<RbiSossPojo> oss7PartA1List = getStmtTenPartA("STMT10-PART-A1", dates, orgnCode, repOpt, reportFormat, osBlancelist);
            List<RbiSossPojo> oss7PartA2List = getStmtTenPartA("STMT10-PART-A2", dates, orgnCode, repOpt, reportFormat, osBlancelist);
            List<RbiSossPojo> oss7PartBList = getStmtTenPartBAndPartCSec2("STMT10-PART-B", dates, orgnCode, repOpt, reportFormat, osBlancelist);

            /*Creating an object of all sub report list*/
            Oss7BusinessPojo pojo = new Oss7BusinessPojo();
            pojo.setOss7PartAList(oss7PartA1List);
            pojo.setOss7PartBList(oss7PartA2List);
            pojo.setOss7PartCSec1List(oss7PartBList);

            /*Addition of object into returning list*/
            dataList.add(pojo);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<RbiSossPojo> getStmtTenPartA(String repName, List<String> dates, String orgnCode,
            BigDecimal repOpt, String reportFormat, List osBlancelist) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        BigDecimal hundred = new BigDecimal("100");
        try {
            List list = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, "
                    + "classification, count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, "
                    + "ifnull(range_from,0), ifnull(range_to,0), formula, f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, "
                    + "npa_classification,refer_index,refer_content from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Data Does Not exist For This Statement!!!!!");
            }
            Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
            String preFormula = "";
            for (int i = 0; i < list.size(); i++) {
                RbiSossPojo rbiSossPojo = new RbiSossPojo();
                Vector element = (Vector) list.get(i);
                Integer sNo = Integer.parseInt(element.get(0).toString());
                String reportName = element.get(1).toString();
                String description = element.get(2).toString();
                Integer gNo = Integer.parseInt(element.get(3).toString());
                Integer sGNo = Integer.parseInt(element.get(4).toString());
                Integer ssGNo = Integer.parseInt(element.get(5).toString());
                Integer sssGNo = Integer.parseInt(element.get(6).toString());
                Integer ssssGNo = Integer.parseInt(element.get(7).toString());
                String classification = element.get(8).toString();
                String countApplicable = element.get(9).toString();
                String acNature = element.get(10).toString();
                String acType = element.get(11).toString();
                String glHeadFrom = element.get(12).toString();
                String glHeadTo = element.get(13).toString();
                String rangeBaseOn = element.get(14).toString();
                String rangeFrom = element.get(15).toString();
                String rangeTo = element.get(16).toString();
                String formula = element.get(17).toString();
                String fGNo = element.get(18).toString();
                String fSGNo = element.get(19).toString();
                String fSsGNo = element.get(20).toString();
                String fSssGNo = element.get(21).toString();
                String fSsssGNo = element.get(22).toString();
                String npaClassification = element.get(23).toString();
                String referIndex = element.get(24).toString();
                String referContent = element.get(25).toString();
                /*As it is insertion of heading in the RBI MASTER*/
                if (!referIndex.equalsIgnoreCase("Y")) {
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    rbiSossPojo.setAmt(new BigDecimal("0"));
                    if (reportFormat.equalsIgnoreCase("N")) {
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

                            dataList.add(rbiSossPojo);
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
                        dataList.add(rbiSossPojo);
                    }
                }
                /*Report Processing*/
                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setBrnCode(orgnCode);
                params.setClassification(classification);
                params.setDate(dates.get(0));
                params.setToDate(dates.get(0));
                params.setFromRange(rangeFrom);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setNature(acNature);
                params.setAcType(acType);
                params.setOrgBrCode(orgnCode);
                params.setRangeBasedOn(rangeBaseOn);
                params.setToRange(rangeTo);
                params.setFlag(fSGNo);
                List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                if (!(glHeadFrom == null || glHeadFrom.equalsIgnoreCase("")) && !(glHeadTo == null || glHeadTo.equalsIgnoreCase(""))) {
                    GlHeadPojo glHeadPojo = new GlHeadPojo();
                    List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                            + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                            + "by substring(acno,3,8)").getResultList();
                    for (int n = 0; n < glNameList.size(); n++) {
                        Vector vector = (Vector) glNameList.get(n);
                        GlHeadPojo glPojo = new GlHeadPojo();
                        for (int l = 0; l < dates.size(); l++) {
                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                            if (newBalPojo == null) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                } else {
                                    rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                }
                            }
                            rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                        }
                        if (reportFormat.equalsIgnoreCase("N")) {
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
                        dataList.add(rbiSossPojo);
                    }
                } else if (!(acNature == null || acNature.equalsIgnoreCase(""))) {
                    List natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                            + "acctnature ='" + params.getNature() + "'").getResultList();
                    for (int n = 0; n < natureList.size(); n++) {
                        Vector vector = (Vector) natureList.get(n);
                        params.setNature("");
                        params.setAcType(vector.get(0).toString());
                        for (int l = 0; l < dates.size(); l++) {
                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                            if (newBalPojo == null) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                } else {
                                    rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                }
                                rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                                /*For other amounts*/
                                if (reportFormat.equalsIgnoreCase("N")) {
                                    if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                        }
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
                                dataList.add(rbiSossPojo);
                            }
                        }
                    }
                } else if (!(acType == null || acType.equalsIgnoreCase(""))) {
                    List acTypeList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                            + "acctcode ='" + params.getAcType() + "'").getResultList();
                    Vector vector = (Vector) acTypeList.get(0);
                    GlHeadPojo glPojo = new GlHeadPojo();
                    glPojo.setGlHead(vector.get(0).toString());
                    glPojo.setGlName(vector.get(1).toString());
                    for (int l = 0; l < dates.size(); l++) {
                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                        if (newBalPojo == null) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                        } else {
                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                            } else {
                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                        }
                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, rbiSossPojo.getAmt());
                    }
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                            if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                            }
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
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                            if (sGNo == 0) {
                                rbiSossPojo.setsGNo(1);
                            } else {
                                rbiSossPojo.setsGNo(sGNo);
                                if (ssGNo == 0 && sGNo != 0) {
                                    rbiSossPojo.setSsGNo(1);
                                } else {
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                        rbiSossPojo.setSssGNo(1);
                                    } else {
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                            rbiSossPojo.setSsssGNo(1);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                        } else {
                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                        }
                                    }
                                }
                            }
                            preZ = 1;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                    }
                    /*For other amounts*/
                    dataList.add(rbiSossPojo);
                } else if (referIndex.equalsIgnoreCase("Y") && !(referContent == null || referContent.equals(""))) {
                    List<RbiSossPojo> referList = null;
                    if (referContent.equalsIgnoreCase("STMT10-PART-B")) {
                        referList = getStmtTenPartBAndPartCSec2(referContent, dates, orgnCode, repOpt, reportFormat, osBlancelist);
                        for (int l = 0; l < dates.size(); l++) {
                            BigDecimal referBal = new BigDecimal("0");
                            for (RbiSossPojo referObj : referList) {
                                if (l == 0) {
                                    referBal = referBal.add(referObj.getFifthAmount());
                                } else {
                                    if (referObj.getTenthAmount() == null) {
                                        referBal = referBal.add(new BigDecimal(0));
                                    } else {
                                        referBal = referBal.add(referObj.getTenthAmount());
                                    }
                                }
                            }
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, referBal);
                        }
                    }
                    /*Addition into main list*/
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setgNo(gNo);
                    rbiSossPojo.setsGNo(sGNo);
                    rbiSossPojo.setSsGNo(ssGNo);
                    rbiSossPojo.setSssGNo(sssGNo);
                    rbiSossPojo.setSsssGNo(ssssGNo);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    if (reportFormat.equalsIgnoreCase("N")) {
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

                            dataList.add(rbiSossPojo);
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

                        dataList.add(rbiSossPojo);
                    }

                }
            }
            /*Formula Processing*/
            for (int k = 0; k < dataList.size(); k++) {
                RbiSossPojo rbiSossPojo = dataList.get(k);
                if (!(rbiSossPojo.getFormula() == null || rbiSossPojo.getFormula().equals(""))) {
                    for (int l = 0; l < dates.size(); l++) {
                        BigDecimal bal = new BigDecimal("0.0");
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                            double balPL = glReport.IncomeExp(dates.get(l), "0A", "0A");
                            if (rbiSossPojo.getClassification().equalsIgnoreCase("L") && balPL >= 0) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(balPL).divide(repOpt));
                            } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL < 0) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(balPL).divide(repOpt));
                            }
                        } else {
                            bal = bal.add(rbiRemote.getValueFromFormula(dataList, rbiSossPojo.getFormula()));
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, bal);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
//        for (int i = 0; i < dataList.size(); i++) {
//                System.out.println(dataList.get(i).getsNo() + ";" + dataList.get(i).getReportName() + ";" + dataList.get(i).getDescription() + ";" + dataList.get(i).getgNo()
//                        + ";" + dataList.get(i).getsGNo() + ";" + dataList.get(i).getSsGNo() + ";" + dataList.get(i).getSssGNo() + ";" + dataList.get(i).getSsssGNo()
//                        + ";" + dataList.get(i).getClassification() + ";" + dataList.get(i).getCountApplicable() + ";" + dataList.get(i).getAcNature()
//                        + ";" + dataList.get(i).getAcType() + ";" + dataList.get(i).getNpaClassification() + ";" + dataList.get(i).getGlheadFrom()
//                        + ";" + dataList.get(i).getGlHeadTo() + ";" + dataList.get(i).getRangeBaseOn() + ";" + dataList.get(i).getRangeFrom()
//                        + ";" + dataList.get(i).getRangeTo() + ";" + dataList.get(i).getFormula() + ";" + dataList.get(i).getfGNo()
//                        + ";" + dataList.get(i).getfSGNo() + ";" + dataList.get(i).getfSsGNo() + ";" + dataList.get(i).getfSssGNo()
//                        + ";" + dataList.get(i).getfSsssGNo() + ";" + dataList.get(i).getAmt() + ";" + dataList.get(i).getSecondAmount()
//                        + ";" + dataList.get(i).getThirdAmount() + ";" + dataList.get(i).getFourthAmount() + ";" + dataList.get(i).getFifthAmount()
//                        + ";" + dataList.get(i).getSixthAmount() + ";" + dataList.get(i).getSeventhAmount());
//        }
        return dataList;
    }

    public List<RbiSossPojo> getStmtTenPartBAndPartCSec2(String repName, List<String> dates, String orgnCode,
            BigDecimal repOpt, String reportFormat, List osBlancelist) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        try {
            String npaAcDetails = "";
            BigDecimal hundred = new BigDecimal("100");
            List list = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, "
                    + "classification, count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, "
                    + "ifnull(range_from,0), ifnull(range_to,0), formula, f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, "
                    + "npa_classification from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
            if (list.isEmpty()) {
                RbiSossPojo rbiSossPojo = new RbiSossPojo();
                rbiSossPojo.setsNo(1);
                rbiSossPojo.setReportName("");
                rbiSossPojo.setDescription("");
                rbiSossPojo.setgNo(1);
                rbiSossPojo.setsGNo(0);
                rbiSossPojo.setSsGNo(0);
                rbiSossPojo.setSssGNo(0);
                rbiSossPojo.setSsssGNo(0);
                rbiSossPojo.setClassification("");
                rbiSossPojo.setCountApplicable("");
                rbiSossPojo.setAcNature("");
                rbiSossPojo.setAcType("");
                rbiSossPojo.setNpaClassification("");
                rbiSossPojo.setGlheadFrom("");
                rbiSossPojo.setGlHeadTo("");
                rbiSossPojo.setRangeBaseOn("");
                rbiSossPojo.setRangeFrom("");
                rbiSossPojo.setRangeTo("");
                rbiSossPojo.setFormula("");
                rbiSossPojo.setfGNo("");
                rbiSossPojo.setfSGNo("");
                rbiSossPojo.setfSsGNo("");
                rbiSossPojo.setfSssGNo("");
                rbiSossPojo.setfSsssGNo("");
                rbiSossPojo.setAmt(new BigDecimal("0"));            //Book Value-->Amount
                rbiSossPojo.setSecondAmount(new BigDecimal("0"));   //Margins And Provisions-->Amount
                rbiSossPojo.setThirdAmount(new BigDecimal("0"));    //Book Value Net-->Amount
                rbiSossPojo.setFourthAmount(new BigDecimal("0"));   //Risk Weight Percentage-->Percentage
                rbiSossPojo.setFifthAmount(new BigDecimal("0"));    //Risk Adjusted Value-->Amount

                dataList.add(rbiSossPojo);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector element = (Vector) list.get(i);
                    Integer sNo = Integer.parseInt(element.get(0).toString());
                    String reportName = element.get(1).toString();
                    String description = element.get(2).toString();
                    Integer gNo = Integer.parseInt(element.get(3).toString());
                    Integer sGNo = Integer.parseInt(element.get(4).toString());
                    Integer ssGNo = Integer.parseInt(element.get(5).toString());
                    Integer sssGNo = Integer.parseInt(element.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(element.get(7).toString());
                    String classification = element.get(8).toString();
                    String countApplicable = element.get(9).toString();
                    String acNature = element.get(10).toString();
                    String acType = element.get(11).toString();
                    String glHeadFrom = element.get(12).toString();
                    String glHeadTo = element.get(13).toString();
                    String rangeBaseOn = element.get(14).toString();
                    String rangeFrom = element.get(15).toString();
                    String rangeTo = element.get(16).toString();
                    String formula = element.get(17).toString();
                    String fGNo = element.get(18).toString();
                    String fSGNo = element.get(19).toString();
                    String fSsGNo = element.get(20).toString();
                    String fSssGNo = element.get(21).toString();                    //Margin Percentage
                    String fSsssGNo = element.get(22).toString();                   //Risk Weight Percentage
                    String npaClassification = element.get(23).toString();
                    /*As it is insertion of heading in the RBI MASTER*/
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setgNo(gNo);
                    rbiSossPojo.setsGNo(sGNo);
                    rbiSossPojo.setSsGNo(ssGNo);
                    rbiSossPojo.setSssGNo(sssGNo);
                    rbiSossPojo.setSsssGNo(ssssGNo);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    rbiSossPojo.setAmt(new BigDecimal("0"));            //Book Value-->Amount
                    rbiSossPojo.setSecondAmount(new BigDecimal("0"));   //Margins And Provisions-->Amount
                    rbiSossPojo.setThirdAmount(new BigDecimal("0"));    //Book Value Net-->Amount
                    rbiSossPojo.setFourthAmount(new BigDecimal("0"));   //Risk Weight Percentage-->Percentage
                    rbiSossPojo.setFifthAmount(new BigDecimal("0"));    //Risk Adjusted Value-->Amount
                    dataList.add(rbiSossPojo);
                    /*Report Processing*/
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(dates.get(0));
                    params.setToDate(dates.get(0));
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
//                    List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                    AcctBalPojo acctBal = new AcctBalPojo();
                    if (!(acNature == null || acNature.equalsIgnoreCase(""))) {
                        List natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                + "acctnature ='" + params.getNature() + "'").getResultList();
                        for (int n = 0; n < natureList.size(); n++) {
                            Vector vector = (Vector) natureList.get(n);
                            params.setNature("");
                            params.setAcType(vector.get(0).toString());
                            for (int l = 0; l < dates.size(); l++) {
                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                                if (newBalPojo == null) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                                } else {
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                    } else {
                                        rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                    }
                                }
                                if (l == 0) {
                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                                    rbiSossPojo.setNinethAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                    rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getNinethAmount()));//Book Value Net-->Amount
                                    rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                    rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                                } else {
                                    rbiSossPojo.setSixthAmount(rbiSossPojo.getSecondAmount());
                                    rbiSossPojo.setSeventhAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                    rbiSossPojo.setEightthAmount(rbiSossPojo.getSecondAmount().subtract(rbiSossPojo.getSeventhAmount()));//Book Value Net-->Amount
//                                    rbiSossPojo.setNinethAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                    rbiSossPojo.setTenthAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                                }
                            }
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
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
                            dataList.add(rbiSossPojo);

                        }
                    } else if (!(acType == null || acType.equalsIgnoreCase(""))) {
                        List acTypeList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                + "acctcode ='" + params.getAcType() + "'").getResultList();
                        Vector vector = (Vector) acTypeList.get(0);
                        GlHeadPojo glPojo = new GlHeadPojo();
                        glPojo.setGlHead(vector.get(0).toString());
                        glPojo.setGlName(vector.get(1).toString());
                        for (int l = 0; l < dates.size(); l++) {
                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                            if (newBalPojo == null) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                } else {
                                    rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                }
                            }
                            if (l == 0) {
                                rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                                rbiSossPojo.setNinethAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getNinethAmount()));//Book Value Net-->Amount
                                rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                            } else {
                                rbiSossPojo.setSixthAmount(rbiSossPojo.getSecondAmount());
                                rbiSossPojo.setSeventhAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                rbiSossPojo.setEightthAmount(rbiSossPojo.getSecondAmount().subtract(rbiSossPojo.getSeventhAmount()));//Book Value Net-->Amount
//                                    rbiSossPojo.setNinethAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                rbiSossPojo.setTenthAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                            }
                        }
                        dataList.add(rbiSossPojo);

                    } else if (!(glHeadFrom == null || glHeadFrom.equalsIgnoreCase(""))
                            && !(glHeadTo == null || glHeadTo.equalsIgnoreCase(""))) {
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8)").getResultList();

                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            for (int l = 0; l < dates.size(); l++) {
                                GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dates.get(l));
                                if (newBalPojo == null) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, new BigDecimal(0.00));
                                } else {
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt));
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                    } else {
                                        rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, l, newBalPojo.getBalance().abs().divide(repOpt));
                                    }
                                }
                                if (l == 0) {
                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                                    rbiSossPojo.setNinethAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                    rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getNinethAmount()));//Book Value Net-->Amount
                                    rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                    rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                                } else {
                                    rbiSossPojo.setSixthAmount(rbiSossPojo.getSecondAmount());
                                    rbiSossPojo.setSeventhAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                    rbiSossPojo.setEightthAmount(rbiSossPojo.getSecondAmount().subtract(rbiSossPojo.getSeventhAmount()));//Book Value Net-->Amount
//                                    rbiSossPojo.setNinethAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                    rbiSossPojo.setTenthAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                                }
                            }
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
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
                            dataList.add(rbiSossPojo);
                        }
                    } else if (!npaClassification.equalsIgnoreCase("")) {
                        List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
                        for (int l = 0; l < dates.size(); l++) {
                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    dates.get(l), "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    "0", "0", "0", "0", npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, "0", "0",
                                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                            BigDecimal a = new BigDecimal("0");
                            for (int k = 0; k < resultList.size(); k++) {
                                LoanMisCellaniousPojo loanMisCellaniousPojo = new LoanMisCellaniousPojo();
                                LoanMisCellaniousPojo val = resultList.get(k);
                                if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    a = a.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                } else {
                                    a = a.add(resultList.get(k).getOutstanding());
                                }
                            }
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, l, a.divide(repOpt));
                            if (l == 0) {
                                rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                                rbiSossPojo.setNinethAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getNinethAmount()));//Book Value Net-->Amount
                                rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                                rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                            } else {
                                rbiSossPojo.setSixthAmount(rbiSossPojo.getSecondAmount());
                                rbiSossPojo.setSeventhAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                                rbiSossPojo.setEightthAmount(rbiSossPojo.getSecondAmount().subtract(rbiSossPojo.getSeventhAmount()));//Book Value Net-->Amount
                                rbiSossPojo.setTenthAmount((rbiSossPojo.getSecondAmount().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                            }
                        }
                        dataList.add(rbiSossPojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<InterOfycPojo> getInterOfycMemoDetail(String acno, String fromDate, String ToDate) throws ApplicationException {
        List<InterOfycPojo> dataList = new ArrayList<InterOfycPojo>();
        try {
            String custName = "", addNdContact = "", constitution = "", nameOfProp = "", panNo = "", cibilScore = "", businessNature = "", bankerConst = "", bankingSince = "", purposeOfLoan = "", primarySec = "", secondorysecurity = "", businessAdd = "", machineAdd = "", infraAvailable = "", boardExhibited = "", priSecNature = "", lastInspectionDt = "", sundrydebt = "", sundryCredit = "", stockNature = "", insuranceExpireDt = "", returnRecvdTill = "", colSecNature = "", colSecLocation = "", colSecValue = "", colSecAcno = "", colSecBranch = "";
            String odAcno = "", odDrNoOfDays = "", odLimitPerc = "", odBalanceExceedDays = "", tdAcNo = "", odNatureOfSec = "";
            BigDecimal odSactAmt = new BigDecimal("0"), odTotalDr = new BigDecimal("0"), odTotalCr = new BigDecimal("0"), colSecSancAmt = new BigDecimal("0"),
                    odAvgBalance = new BigDecimal("0"), odBalance = new BigDecimal("0"), odInterestCharged = new BigDecimal("0"), colSecBalance = new BigDecimal("0"), odOutstanding = new BigDecimal("0"), sbAcAvgBal = new BigDecimal("0"), sbBalAsOnDt = new BigDecimal("0"),
                    tdAcBalAsOnDt = new BigDecimal("0"), totalDepositAmt = new BigDecimal("0"), sactionLimit = new BigDecimal("0"), insuranceAmt = new BigDecimal("0");
            BigDecimal otherLoanSacAmt = new BigDecimal("0"), otherLoanBalance = new BigDecimal("0"), stockValue = new BigDecimal("0");
            String totalGroupExposure = "", partyDealingRating = "", totalNoOfReturnedChq = "", projectionRecvd = "", verifyStatus = "", otherInfo = "", guarantor1Rating = "",
                    guarantor2Rating = "", guarantor3Rating = "", guarantor4Rating = "", guarantorOtherInfo = "", resProfOfApplicant = "", resProfOfGuar1 = "", resProfOfGuar2 = "",
                    resProfOfGuar3 = "", resProfOfGuar4 = "", othDoc1 = "", othDoc2 = "", othDoc3 = "", othDoc4 = "", othDoc5 = "", otherLoanCondAcno = "";

            String custid1 = "", custid2 = "", custid3 = "", custid4 = "";
            List<InterOfycPojo1> data1 = new ArrayList<InterOfycPojo1>();
            InterOfycPojo1 poJo1 = new InterOfycPojo1();
            InterOfycPojo2 poJo2 = new InterOfycPojo2();
            InterOfycPojo3 poJo3 = new InterOfycPojo3();
            List<InterOfycPojo2> data2 = new ArrayList<InterOfycPojo2>();
            List<InterOfycPojo3> data3 = new ArrayList<InterOfycPojo3>();
            List custList = em.createNativeQuery("select a.custname,a.OpeningDt,ifnull(a.custid1,''),ifnull(a.custid2,''),ifnull(a.custid3,''), ifnull(a.custid4,''),"
                    + "  ifnull(b.mobilenumber, ifnull(b.TelexNumber, ifnull(b.MailPhoneNumber,  ifnull(b.MailTelexNumber, ifnull(b.MailFaxNumber, ''))))) as telephonenumber, "
                    + "  b.MailAddressLine1, b.MailAddressLine2, b.MailVillage, b.MailBlock,"
                    + "(select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = b.mailCityCode) as MailCityCode, "
                    + " (select ifnull(stateCodeNo,'') from statecodemappingtable where stateCode = b.mailstatecode) as mailstatecode, "
                    + " b.MailPostalCode from accountmaster a, cbs_customer_master_detail  b "
                    + " where a.acno ='" + acno + "' and b.customerid = (select custid from customerid where acno ='" + acno + "')").getResultList();
            if (!custList.isEmpty()) {
                Vector custDetail = (Vector) custList.get(0);
                custName = custDetail.get(0).toString();
                bankingSince = custDetail.get(1).toString();
                custid1 = custDetail.get(2) != null ? custDetail.get(2).toString() : "";
                custid2 = custDetail.get(3) != null ? custDetail.get(3).toString() : "";
                custid3 = custDetail.get(5) != null ? custDetail.get(4).toString() : "";
                custid4 = custDetail.get(5) != null ? custDetail.get(5).toString() : "";
                String telephoneNumber = custDetail.get(6) != null ? custDetail.get(6).toString() : "";
                String mailAddressLine1 = custDetail.get(7) != null ? custDetail.get(7).toString() : "";
                String mailAddressLine2 = custDetail.get(8) != null ? custDetail.get(8).toString() : "";
                String mailVillage = custDetail.get(9) != null ? custDetail.get(9).toString() : "";
                String mailBlock = custDetail.get(10) != null ? custDetail.get(10).toString() : "";
                String mailCityCode = custDetail.get(11) != null ? custDetail.get(11).toString() : "";
                String mailStateCode = custDetail.get(12) != null ? custDetail.get(12).toString() : "";
                String mailPostalCode = custDetail.get(13) != null ? custDetail.get(13).toString() : "";
                String address = (!mailAddressLine1.trim().equalsIgnoreCase("") ? mailAddressLine1.trim() : mailAddressLine1.trim()).concat(
                        !mailAddressLine2.trim().equalsIgnoreCase("") ? " ".concat(mailAddressLine2.trim()) : mailAddressLine2.trim()).concat(
                        !mailVillage.trim().equalsIgnoreCase("") ? " ".concat(mailVillage.trim()) : mailVillage.trim()).concat(
                        !mailBlock.trim().equalsIgnoreCase("") ? " ".concat(mailBlock.trim()) : mailBlock.trim()).concat(
                        !mailCityCode.trim().equalsIgnoreCase("") ? " ".concat(mailCityCode.trim()) : mailCityCode.trim());
                addNdContact = "Address :" + address + " Contact:" + telephoneNumber;
            }
            String prop1 = "", prop2 = "", prop3 = "", prop4 = "";
            String prop1PAN = "", prop2PAN = "", prop3PAN = "", prop4PAN = "";
            if (!(custid1.equalsIgnoreCase("") || custid2.equalsIgnoreCase("") || custid3.equalsIgnoreCase("") || custid4.equalsIgnoreCase(""))) {
                if (!custid1.equalsIgnoreCase("")) {
                    List propDetail1 = em.createNativeQuery("select custname,ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail where customerid ='" + custid1 + "' ").getResultList();
                    if (!propDetail1.isEmpty()) {
                        Vector propVect = (Vector) propDetail1.get(0);
                        prop1 = propVect.get(0).toString();
                        prop1PAN = propVect.get(1).toString();
                    }
                }
                if (!custid2.equalsIgnoreCase("")) {
                    List propDetail2 = em.createNativeQuery("select custname,ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail where customerid ='" + custid2 + "' ").getResultList();
                    if (!propDetail2.isEmpty()) {
                        Vector propVect = (Vector) propDetail2.get(0);
                        prop2 = propVect.get(0).toString();
                        prop2PAN = propVect.get(1).toString();
                    }
                }
                if (!custid3.equalsIgnoreCase("")) {
                    List propDetail3 = em.createNativeQuery("select custname,ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail where customerid ='" + custid3 + "' ").getResultList();
                    if (!propDetail3.isEmpty()) {
                        Vector propVect = (Vector) propDetail3.get(0);
                        prop3 = propVect.get(0).toString();
                        prop3PAN = propVect.get(1).toString();
                    }
                }
                if (!custid4.equalsIgnoreCase("")) {
                    List propDetail4 = em.createNativeQuery("select custname,ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail where customerid ='" + custid4 + "' ").getResultList();
                    if (!propDetail4.isEmpty()) {
                        Vector propVect = (Vector) propDetail4.get(0);
                        prop4 = propVect.get(0).toString();
                        prop4PAN = propVect.get(1).toString();
                    }
                }
                nameOfProp = (!prop1.trim().equalsIgnoreCase("") ? prop1.trim() : prop1.trim()).concat(
                        !prop2.trim().equalsIgnoreCase("") ? prop2.trim() : prop2.trim()).concat(
                        !prop3.trim().equalsIgnoreCase("") ? prop3.trim() : prop3.trim()).concat(
                        !prop4.trim().equalsIgnoreCase("") ? prop4.trim() : prop4.trim());
                panNo = (!prop1PAN.trim().equalsIgnoreCase("") ? prop1PAN.trim() : prop1PAN.trim()).concat(
                        !prop2PAN.trim().equalsIgnoreCase("") ? prop2PAN.trim() : prop2PAN.trim()).concat(
                        !prop3PAN.trim().equalsIgnoreCase("") ? prop3PAN.trim() : prop3PAN.trim()).concat(
                        !prop4PAN.trim().equalsIgnoreCase("") ? prop4PAN.trim() : prop4PAN.trim());
            }
            List odlimitList = em.createNativeQuery("select odlimit from loan_appparameter where acno ='" + acno + "' ").getResultList();
            if (!odlimitList.isEmpty()) {
                Vector vect = (Vector) odlimitList.get(0);
                sactionLimit = new BigDecimal(vect.get(0).toString());
            }
            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + ToDate + "' and txnid = (select max(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + ToDate + "')").getResultList();
            if (!sanctionLimitDtList.isEmpty()) {
                Vector vist = (Vector) sanctionLimitDtList.get(0);
                sactionLimit = new BigDecimal(vist.get(1).toString());
            }
            List securityList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + ToDate + "' and txnid = (select max(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + ToDate + "')").getResultList();
            if (!sanctionLimitDtList.isEmpty()) {
                Vector vist = (Vector) sanctionLimitDtList.get(0);
                sactionLimit = new BigDecimal(vist.get(1).toString());
            }
            poJo1.setPartyName(custName);
            poJo1.setAddNdContact(addNdContact);
            poJo1.setConstitution(constitution);
            poJo1.setNameOfProp(nameOfProp);
            poJo1.setPanNo(panNo);
            poJo1.setCibilScore(cibilScore);
            poJo1.setBusinessNature(businessNature);
            poJo1.setBankerConst(bankerConst);
            poJo1.setBankingSince(bankingSince);
            poJo1.setSactionLimit(sactionLimit);
            poJo1.setPurposeOfLoan(purposeOfLoan);
            poJo1.setPrimarySec(primarySec);
            poJo1.setSecondorysecurity(secondorysecurity);
            poJo1.setBusinessAdd(businessAdd);
            poJo1.setMachineAdd(machineAdd);
            poJo1.setInfraAvailable(infraAvailable);
            poJo1.setBoardExhibited(boardExhibited);
            poJo1.setPriSecNature(priSecNature);
            poJo1.setLastInspectionDt(lastInspectionDt);
            poJo1.setStockValue(stockValue);;
            poJo1.setSundrydebt(sundrydebt);
            poJo1.setSundryCredit(sundryCredit);
            poJo1.setStockNature(stockNature);
            poJo1.setInsuranceAmt(insuranceAmt);
            poJo1.setInsuranceExpireDt(insuranceExpireDt);
            poJo1.setReturnRecvdTill(returnRecvdTill);
            poJo1.setColSecNature(colSecNature);
            poJo1.setColSecLocation(colSecLocation);
            poJo1.setColSecValue(colSecBalance);
            poJo1.setColSecAcno(colSecAcno);
            poJo1.setColSecBranch(colSecBranch);
            poJo1.setColSecSancAmt(colSecSancAmt);
            poJo1.setColSecBalance(colSecBalance);
            data1.add(poJo1);

            poJo2.setOdAcno(odAcno);
            poJo2.setOdSactAmt(odSactAmt);
            poJo2.setOdTotalDr(odTotalDr);
            poJo2.setOdTotalCr(odTotalCr);
            poJo2.setOdAvgBalance(odAvgBalance);
            poJo2.setOdDrNoOfDays(odDrNoOfDays);
            poJo2.setOdBalance(odBalance);
            poJo2.setOdLimitPerc(odLimitPerc);
            poJo2.setOdInterestCharged(odInterestCharged);
            poJo2.setOdOutstanding(odOutstanding);
            poJo2.setOdNatureOfSec(odNatureOfSec);
            poJo2.setOdBalanceExceedDays(odBalanceExceedDays);
            poJo2.setSbAcAvgBal(sbAcAvgBal);
            poJo2.setSbBalAsOnDt(sbBalAsOnDt);
            poJo2.setTdAcNo(tdAcNo);
            poJo2.setTdAcBalAsOnDt(tdAcBalAsOnDt);
            poJo2.setTotalDepositAmt(totalDepositAmt);
            data2.add(poJo2);

            poJo3.setOtherLoanSacAmt(otherLoanSacAmt);
            poJo3.setOtherLoanCondAcno(otherLoanCondAcno);
            poJo3.setOtherLoanBalance(otherLoanBalance);
            poJo3.setTotalGroupExposure(totalGroupExposure);
            poJo3.setPartyDealingRating(partyDealingRating);
            poJo3.setTotalNoOfReturnedChq(totalNoOfReturnedChq);
            poJo3.setProjectionRecvd(projectionRecvd);
            poJo3.setVerifyStatus(verifyStatus);
            poJo3.setOtherInfo(otherInfo);
            poJo3.setGuarantor1Rating(guarantor1Rating);
            poJo3.setGuarantor2Rating(guarantor2Rating);
            poJo3.setGuarantor3Rating(guarantor3Rating);
            poJo3.setGuarantor4Rating(guarantor4Rating);
            poJo3.setGuarantorOtherInfo(guarantorOtherInfo);
            poJo3.setResProfOfApplicant(resProfOfApplicant);
            poJo3.setResProfOfGuar1(resProfOfGuar1);
            poJo3.setResProfOfGuar2(resProfOfGuar2);
            poJo3.setResProfOfGuar3(resProfOfGuar3);
            poJo3.setResProfOfGuar4(resProfOfGuar4);
            poJo3.setOthDoc1(othDoc1);
            poJo3.setOthDoc2(othDoc2);
            poJo3.setOthDoc3(othDoc3);
            poJo3.setOthDoc4(othDoc4);
            poJo3.setOthDoc5(othDoc5);
            data3.add(poJo3);

            InterOfycPojo pojo = new InterOfycPojo();
            pojo.setPart1(new JRBeanCollectionDataSource(data1));
            pojo.setPart2(new JRBeanCollectionDataSource(data2));
            pojo.setPart3(new JRBeanCollectionDataSource(data3));
            dataList.add(pojo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataList;
    }
}
