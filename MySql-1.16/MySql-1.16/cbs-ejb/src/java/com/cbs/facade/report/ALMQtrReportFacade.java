/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.AlmPojo;
import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.report.AlmFddetailPojo;
import com.cbs.dto.report.AlmFdshortedByBktNo;
import com.cbs.dto.report.CrrSlrPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.RbiAlmPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.RevenueReportDrCrPojo;
import com.cbs.dto.report.ho.ComparatorByGno;
import com.cbs.dto.report.ho.ComparatorByReportName;
import com.cbs.dto.report.ho.ComparatorBySSSSgno;
import com.cbs.dto.report.ho.ComparatorBySSSgno;
import com.cbs.dto.report.ho.ComparatorBySSgno;
import com.cbs.dto.report.ho.ComparatorBySgno;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.BucketWiseInvestmentPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Vector;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
@Stateless(mappedName = "ALMQtrReportFacade")
public class ALMQtrReportFacade implements ALMQtrReportFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private CommonReportMethodsRemote commonRemote;
    @EJB
    private RbiReportFacadeRemote rbiReportRemote;
    @EJB
    GLReportFacadeRemote glReport;
    @EJB
    HoReportFacadeRemote hoRemote;
    @EJB
    RbiQuarterlyReportFacadeRemote quarterlyRemote;
    @EJB
    LoanReportFacadeRemote loanRemote;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    InvestmentReportMgmtFacadeRemote invstRemote;
    @EJB
    ALMReportFacadeRemote almFacadeRemote;
    @EJB
    RbiMonthlyReportFacadeRemote rbiMonthlyReportFacade;
    @PersistenceContext
    private EntityManager em;
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List hoAlm(String date, String brCode, String repOpt) throws ApplicationException {
        List<AlmPojo> almPojoTable = new ArrayList<AlmPojo>();
        try {
            List almMasterList = em.createNativeQuery("SELECT GNO,S_GNO,SS_GNO,SSS_GNO,SSSS_GNO,DESCRIPTION,CLASSIFICATION,AC_NATURE,AC_TYPE,GL_HEAD_FROM,"
                    + " GL_HEAD_TO,F_GNO,FORMULA,count_applicable,npa_classification,f_s_gno FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'ALM-L' ORDER BY CLASSIFICATION DESC,cast(gno as unsigned),"
                    + " cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            AlmPojo almPojo = new AlmPojo();
            Double amt = 0d, repOption = 0d;
            long noOfDays = 0;
            if (repOpt.equalsIgnoreCase("T")) {
                repOption = 1000d;
            } else if (repOpt.equalsIgnoreCase("L")) {
                repOption = 100000d;
            } else if (repOpt.equalsIgnoreCase("C")) {
                repOption = 10000000d;
            } else if (repOpt.equalsIgnoreCase("R")) {
                repOption = 1d;
            }
            List almExist = em.createNativeQuery("select * from parameterinfo_report where reportname='ALM'").getResultList();
            if (!almMasterList.isEmpty()) {
                for (int i = 0; i < almMasterList.size(); i++) {
                    Vector almMasterVect = (Vector) almMasterList.get(i);
//                    System.out.println("i:" + i);
                    Integer gCode = Integer.parseInt(almMasterVect.get(0).toString());
                    Integer sgCode = Integer.parseInt(almMasterVect.get(1).toString());
                    Integer ssgCode = Integer.parseInt(almMasterVect.get(2).toString());
                    Integer sssgCode = Integer.parseInt(almMasterVect.get(3).toString());
                    Integer ssssgCode = Integer.parseInt(almMasterVect.get(4).toString());
                    String desc = almMasterVect.get(5).toString();
                    String headType = almMasterVect.get(6).toString();
                    String classType = "";
                    if (headType.equalsIgnoreCase("L")) {
                        classType = "O";
                    } else {
                        classType = "I";
                    }
                    String acNat = almMasterVect.get(7).toString();
                    String acType = almMasterVect.get(8).toString();
                    String codeFr = almMasterVect.get(9).toString();
                    String codeTo = almMasterVect.get(10).toString();
                    String headAcNo = almMasterVect.get(11).toString();
                    String fOpt = almMasterVect.get(12).toString();
                    String cntApp = almMasterVect.get(13).toString();
                    String npaClass = almMasterVect.get(14).toString();
                    String fSGNo = almMasterVect.get(15).toString();

                    double crTotal, drTotal, totalCrDr = 0;

                    /**
                     * ************************* after date (Liability)(Future
                     * FD/RD/Recovery (Loan) **********************
                     */
                    if (headAcNo.equalsIgnoreCase("NULL") || headAcNo.equalsIgnoreCase("")) {
                        if ((!acType.equalsIgnoreCase("")) || (!acNat.equalsIgnoreCase(""))) {
                            List acTypeList = new ArrayList();
                            if (!acNat.equalsIgnoreCase("")) {
                                acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE IN (SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE ='" + acNat + "')").getResultList();
                                if (acTypeList.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                }
                            } else {
                                acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                                if (acTypeList.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                }
                            }

                            almPojo = new AlmPojo();
                            almPojo.setgCode(gCode);
                            almPojo.setSgCode(sgCode);
                            almPojo.setSsgCode(ssgCode);
                            almPojo.setSssgCode(sssgCode);
                            almPojo.setSsssgCode(ssssgCode);
                            almPojo.setDescription(desc);
                            almPojo.setHeadType(headType);
                            almPojo.setClassType(classType);
                            almPojo.setAcNature(acNat);
                            almPojo.setAcType(acType);
                            almPojo.setCodeFr(codeFr);
                            almPojo.setCodeTo(codeTo);
                            almPojo.setHeadOfAcc(headAcNo);
                            almPojo.setBuk1(0d);
                            almPojo.setBuk2(0d);
                            almPojo.setBuk3(0d);
                            almPojo.setBuk4(0d);
                            almPojo.setBuk5(0d);
                            almPojo.setBuk6(0d);
                            almPojo.setBuk7(0d);
                            almPojo.setBuk8(0d);
                            almPojo.setTotal(0d);
                            almPojo.setFormula(fOpt);
                            almPojo.setCountApplicable(cntApp);
                            almPojo.setNpaClassification(npaClass);
                            almPojoTable.add(almPojo);

                            String acCode, acctDesc, glHead;
                            totalCrDr = 0;

                            for (int k = 0; k < acTypeList.size(); k++) {
                                Vector vec = (Vector) acTypeList.get(k);
                                String actnature = vec.get(0).toString();
                                String actp = vec.get(1).toString();
                                acctDesc = vec.get(2).toString();
                                glHead = vec.get(3).toString();
                                crTotal = 0;
                                drTotal = 0;
                                List resultSet = null;
                                if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    if (brCode.equalsIgnoreCase("90")) {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + actp + "' AND t.dt<='" + date + "' "
                                                + " and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT)<>0 ORDER BY t.ACNO").getResultList();
                                    } else {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + actp + "' AND t.dt<='" + date + "' "
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
                                    almPojo.setgCode(gCode);
                                    almPojo.setSgCode(sgCode);
                                    almPojo.setHeadType(headType);
                                    almPojo.setClassType(classType);
                                    almPojo.setAcNature(actnature);
                                    almPojo.setAcType(actp);
                                    almPojo.setCodeFr(codeFr);
                                    almPojo.setCodeTo(codeTo);
                                    almPojo.setHeadOfAcc(headAcNo);
                                    almPojo.setBuk1(0d);
                                    almPojo.setBuk2(0d);
                                    almPojo.setBuk3(0d);
                                    almPojo.setBuk4(0d);
                                    almPojo.setBuk5(0d);
                                    almPojo.setBuk6(0d);
                                    almPojo.setBuk7(0d);
                                    almPojo.setBuk8(0d);
                                    almPojo.setTotal(0d);
                                    almPojo.setFormula(fOpt);
                                    almPojo.setCountApplicable(cntApp);
                                    almPojo.setNpaClassification(npaClass);

                                    if (ssgCode == 0) {
                                        almPojo.setSsgCode(k + 1);
                                    } else {
                                        almPojo.setSsgCode(ssgCode);
                                    }
                                    if (sssgCode == 0 && ssgCode != 0) {
                                        almPojo.setSssgCode(k + 1);
                                    } else {
                                        almPojo.setSssgCode(sssgCode);
                                    }
                                    if (ssssgCode == 0 && sssgCode != 0 && ssgCode != 0) {
                                        almPojo.setSsssgCode(k + 1);
                                        almPojo.setDescription(acctDesc);
                                    } else {
                                        almPojo.setSsssgCode(ssssgCode);
                                        almPojo.setDescription(String.valueOf("               ").concat(String.valueOf(k + 1)).concat(". ").concat(acctDesc));
                                    }

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
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "'  AND "
                                                                + " A.ACNO=L.ACNO AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();

                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + actp + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
                                                    }
                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "'AND  SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + actp + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
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
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + date + "' AND AUTH = 'Y' AND"
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2)  = '" + actp + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) ='" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + actp + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();
                                                    }
                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' "
                                                                + " AND DT <='" + date + "' AND AUTH = 'Y' AND ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "' AND  SUBSTRING(ACNO,3,2) ='" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + actp + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
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
                                        if (!actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (!actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                                if (!actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                    if (!resultSet.isEmpty()) {
                                                        almPojo = new AlmPojo();
                                                        almPojo.setgCode(gCode);
                                                        almPojo.setSgCode(sgCode);
                                                        almPojo.setSsgCode(ssgCode);
                                                        almPojo.setSssgCode(sssgCode);
                                                        almPojo.setSsssgCode(ssssgCode);
                                                        almPojo.setDescription(desc);
                                                        almPojo.setHeadType(headType);
                                                        almPojo.setClassType(classType);
                                                        almPojo.setAcNature(actnature);
                                                        almPojo.setAcType(actp);
                                                        almPojo.setCodeFr(codeFr);
                                                        almPojo.setCodeTo(codeTo);
                                                        almPojo.setHeadOfAcc(headAcNo);
                                                        almPojo.setBuk1(0d);
                                                        almPojo.setBuk2(0d);
                                                        almPojo.setBuk3(0d);
                                                        almPojo.setBuk4(0d);
                                                        almPojo.setBuk5(0d);
                                                        almPojo.setBuk6(0d);
                                                        almPojo.setBuk7(0d);
                                                        almPojo.setBuk8(0d);
                                                        almPojo.setTotal(0d);
                                                        almPojo.setFormula(fOpt);
                                                        almPojo.setCountApplicable(cntApp);
                                                        almPojo.setNpaClassification(npaClass);

                                                        for (int a = 0; a < resultSet.size(); a++) {
                                                            Vector resultSetVec = (Vector) resultSet.get(a);
                                                            String acno = resultSetVec.get(0).toString();
                                                            amt = Double.parseDouble(resultSetVec.get(1).toString()) / repOption;
                                                            List dtList = null;
                                                            if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                                                dtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + date + "') FROM td_vouchmst WHERE ACNO = '" + acno + "' /*and (cldt > '" + date + "' or cldt is null)*/ and td_madedt <='" + date + "' ").getResultList();
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
                                                                                //System.out.println("acno:"+acno+"; amt:"+amt+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; maddt:"+maddt);
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
                                }
                            }
                        } else {
                            almPojo = new AlmPojo();

                            almPojo.setgCode(gCode);
                            almPojo.setSgCode(sgCode);
                            almPojo.setSsgCode(ssgCode);
                            almPojo.setSssgCode(sssgCode);
                            almPojo.setSsssgCode(ssssgCode);
                            almPojo.setDescription(desc);
                            almPojo.setHeadType(headType);
                            almPojo.setClassType(classType);
                            almPojo.setAcNature(acNat);
                            almPojo.setAcType(acType);
                            almPojo.setCodeFr(codeFr);
                            almPojo.setCodeTo(codeTo);
                            almPojo.setHeadOfAcc(headAcNo);
                            almPojo.setBuk1(0d);
                            almPojo.setBuk2(0d);
                            almPojo.setBuk3(0d);
                            almPojo.setBuk4(0d);
                            almPojo.setBuk5(0d);
                            almPojo.setBuk6(0d);
                            almPojo.setBuk7(0d);
                            almPojo.setBuk8(0d);
                            almPojo.setTotal(0d);
                            almPojo.setFormula(fOpt);
                            almPojo.setCountApplicable(cntApp);
                            almPojo.setNpaClassification(npaClass);

                            almPojoTable.add(almPojo);
                        }
                    } else {
                        /**
                         * ************************* before date SB/CA
                         * ********************************************************
                         */
                        if ((acType != null) || (acNat != null)) {
                            List acTypeList = new ArrayList();
                            if ((!acType.equalsIgnoreCase("")) || (!acNat.equalsIgnoreCase(""))) {
                                if (!acNat.equalsIgnoreCase("")) {
                                    acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                            + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE IN (SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE ='" + acNat + "')").getResultList();
                                    if (acTypeList.isEmpty()) {
                                        throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                    }
                                } else {
                                    acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                            + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                                    if (acTypeList.isEmpty()) {
                                        throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                    }
                                }

                                String acCode, acctDesc, glHead;
                                totalCrDr = 0;

                                almPojo = new AlmPojo();
                                almPojo.setgCode(gCode);
                                almPojo.setSgCode(sgCode);
                                almPojo.setSsgCode(ssgCode);
                                almPojo.setSssgCode(sssgCode);
                                almPojo.setSsssgCode(ssssgCode);
                                almPojo.setDescription(desc);
                                almPojo.setHeadType(headType);
                                almPojo.setClassType(classType);
                                almPojo.setAcNature(acNat);
                                almPojo.setAcType(acType);
                                almPojo.setCodeFr(codeFr);
                                almPojo.setCodeTo(codeTo);
                                almPojo.setHeadOfAcc(headAcNo);
                                almPojo.setBuk1(0d);
                                almPojo.setBuk2(0d);
                                almPojo.setBuk3(0d);
                                almPojo.setBuk4(0d);
                                almPojo.setBuk5(0d);
                                almPojo.setBuk6(0d);
                                almPojo.setBuk7(0d);
                                almPojo.setBuk8(0d);
                                almPojo.setTotal(0d);
                                almPojo.setFormula(fOpt);
                                almPojo.setCountApplicable(cntApp);
                                almPojo.setNpaClassification(npaClass);
                                almPojoTable.add(almPojo);

                                for (int p = 0; p < acTypeList.size(); p++) {
                                    Vector vec = (Vector) acTypeList.get(p);
                                    String actnature = vec.get(0).toString();
                                    String actp = vec.get(1).toString();
                                    acctDesc = vec.get(2).toString();
                                    glHead = vec.get(3).toString();
                                    crTotal = 0;
                                    drTotal = 0;
                                    Integer sign = null;
                                    String caQuery;
                                    if (classType.equalsIgnoreCase("O")) {
                                        sign = 1;
                                    } else if (classType.equalsIgnoreCase("I")) {
                                        sign = -1;
                                    }
                                    String actQuery = " having sign(sum(cramt-dramt)) = " + sign;
                                    if (fSGNo.equalsIgnoreCase("ACT")) {
                                        actQuery = "";
                                    }

                                    if (almExist.isEmpty()) {
                                        caQuery = "";
                                    } else {
                                        caQuery = " AND acno in (select acno from ca_recon WHERE DT <= '" + date + "' group by acno " + actQuery + ") ";
                                    }
                                    if (brCode.equalsIgnoreCase("90")) {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + actp + "' AND r.dt<='" + date + "' and r.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + actp + "' AND d.dt<='" + date + "' and  d.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (npaClass.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' " + caQuery
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            } else {
                                                List resultSet = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from ca_recon where "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' " + caQuery
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            }
                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (npaClass.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0)  FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "') AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            crTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                    } else {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + actp + "' AND r.dt<='" + date + "' and"
                                                    + " r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + actp + "' AND d.dt<='" + date + "' and"
                                                    + " d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (npaClass.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') " + caQuery
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            } else {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') " + caQuery
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            }
                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (npaClass.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0) FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y'  AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "' "
                                                    + "AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND  L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            crTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                    }

                                    almPojo = new AlmPojo();

                                    almPojo.setgCode(gCode);
                                    almPojo.setSgCode(sgCode);
                                    almPojo.setHeadType(headType);
                                    almPojo.setClassType(classType);
                                    almPojo.setAcNature(acNat);
                                    almPojo.setAcType(actp);
                                    almPojo.setCodeFr(codeFr);
                                    almPojo.setCodeTo(codeTo);
                                    almPojo.setHeadOfAcc(headAcNo);
                                    almPojo.setBuk1(0d);
                                    almPojo.setBuk2(0d);
                                    almPojo.setBuk3(0d);
                                    almPojo.setBuk4(0d);
                                    almPojo.setBuk5(0d);
                                    almPojo.setBuk6(0d);
                                    almPojo.setBuk7(0d);
                                    almPojo.setBuk8(0d);
                                    almPojo.setTotal(0d);
                                    almPojo.setFormula(fOpt);
                                    almPojo.setCountApplicable(cntApp);
                                    almPojo.setNpaClassification(npaClass);

                                    if (ssgCode == 0) {
                                        almPojo.setSsgCode(p + 1);
                                    } else {
                                        almPojo.setSsgCode(ssgCode);
                                    }
                                    if (sssgCode == 0 && ssgCode != 0) {
                                        almPojo.setSssgCode(p + 1);
                                    } else {
                                        almPojo.setSssgCode(sssgCode);
                                    }
                                    if (ssssgCode == 0 && sssgCode != 0 && ssgCode != 0) {
                                        almPojo.setSsssgCode(p + 1);
                                        almPojo.setDescription(acctDesc);
                                    } else {
                                        almPojo.setSsssgCode(ssssgCode);
                                        almPojo.setDescription(String.valueOf("               ").concat(String.valueOf(p + 1)).concat(". ").concat(acctDesc));
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
                                                almPojo.setBuk1(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 2) {
                                                almPojo.setBuk2(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 3) {
                                                almPojo.setBuk3(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 4) {
                                                almPojo.setBuk4(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 5) {
                                                almPojo.setBuk5(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 6) {
                                                almPojo.setBuk6(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 7) {
                                                almPojo.setBuk7(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 8) {
                                                almPojo.setBuk8(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                        }
                                        //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                    } else {
                                        throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                                    }
                                    almPojoTable.add(almPojo);

                                    totalCrDr = totalCrDr + (crTotal - drTotal);
                                }
                            }
                        }

                        almPojo = new AlmPojo();

                        almPojo.setgCode(gCode);
                        almPojo.setSgCode(sgCode);
                        almPojo.setSsgCode(ssgCode);
                        almPojo.setSssgCode(sssgCode);
                        almPojo.setSsssgCode(ssssgCode);
                        almPojo.setDescription(desc);
                        almPojo.setHeadType(headType);
                        almPojo.setClassType(classType);
                        almPojo.setAcNature(acNat);
                        almPojo.setAcType(acType);
                        almPojo.setCodeFr(codeFr);
                        almPojo.setCodeTo(codeTo);
                        almPojo.setHeadOfAcc(headAcNo);
                        almPojo.setBuk1(0d);
                        almPojo.setBuk2(0d);
                        almPojo.setBuk3(0d);
                        almPojo.setBuk4(0d);
                        almPojo.setBuk5(0d);
                        almPojo.setBuk6(0d);
                        almPojo.setBuk7(0d);
                        almPojo.setBuk8(0d);
                        almPojo.setTotal(0d);
                        almPojo.setFormula(fOpt);
                        almPojo.setCountApplicable(cntApp);
                        almPojo.setNpaClassification(npaClass);
                        /**
                         * *** Head Office***************
                         */
                        int al = 1;
                        if (classType.equalsIgnoreCase("I")) {
                            al = -1;
                        }
                        String actQuery = " having sign(sum(cramt-dramt)) = " + al;
                        if (fSGNo.equalsIgnoreCase("ACT")) {
                            actQuery = "";
                        }
                        List dataList = new ArrayList();
                        if (brCode.equalsIgnoreCase("90")) {
                            //                System.out.println("params.getGlFromHead():" + params.getGlFromHead());
                            if (codeFr.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                dataList = em.createNativeQuery("select 1, 'Cash in Hand', sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "'").getResultList();
                            } else {
                                dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                                        + " gltable g where r.acno=g.acno and dt <='" + date + "'  and substring(r.acno,3,8) between '" + codeFr + "' "
                                        + " and '" + codeTo + "' group by substring(r.acno,3,8),acname " + actQuery).getResultList();
                            }
                        } else {
                            if (codeFr.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                dataList = em.createNativeQuery("select 1, 'Cash in Hand', sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "'  and brncode = '" + brCode + "'").getResultList();
                            } else {
                                dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                                        + " gltable g where r.acno=g.acno and dt <='" + date + "'  and substring(r.acno,3,8) between '" + codeFr
                                        + " ' and '" + codeTo + "' and substring(r.acno,1,2)='" + brCode + "' group by substring(r.acno,3,8) "
                                        + actQuery).getResultList();
                            }
                        }
                        if (dataList.size() > 0) {
                            almPojoTable.add(almPojo);

                            for (int j = 0; j < dataList.size(); j++) {
                                Vector vec = (Vector) dataList.get(j);
                                Double totalAmt = 0d;
                                String code = vec.get(0).toString();
                                //totalAmt = totalAmt + (Double.parseDouble(vec.get(2).toString()) + totalCrDr) / repOption;
                                totalAmt = totalAmt + (Double.parseDouble(vec.get(2).toString())) / repOption;
                                if (fSGNo.equalsIgnoreCase("ACT")) {
                                    totalAmt = totalAmt;
                                } else {
                                    if (classType.equalsIgnoreCase("O") && totalAmt > 0) {
                                        totalAmt = totalAmt;
                                    } else if (classType.equalsIgnoreCase("I") && code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                        totalAmt = totalAmt;
                                    } else if (classType.equalsIgnoreCase("I") && totalAmt < 0) {
                                        totalAmt = totalAmt;
                                    } else {
                                        totalAmt = 0.0;
                                    }
                                }
                                almPojo = new AlmPojo();
                                almPojo.setBuk1(0d);
                                almPojo.setBuk2(0d);
                                almPojo.setBuk3(0d);
                                almPojo.setBuk4(0d);
                                almPojo.setBuk5(0d);
                                almPojo.setBuk6(0d);
                                almPojo.setBuk7(0d);
                                almPojo.setBuk8(0d);
                                almPojo.setTotal(0d);
                                List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'L' AND HEADS_OF_ACC_NO = " + headAcNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                                if (!almAccClassList.isEmpty()) {
                                    for (int k = 0; k < almAccClassList.size(); k++) {
                                        Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                        String flow = almAcClassVect.get(0).toString();
                                        Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                        Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                        Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                        if (bucketNo == 1) {
                                            almPojo.setBuk1((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 2) {
                                            almPojo.setBuk2((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 3) {
                                            almPojo.setBuk3((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 4) {
                                            almPojo.setBuk4((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 5) {
                                            almPojo.setBuk5((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 6) {
                                            almPojo.setBuk6((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 7) {
                                            almPojo.setBuk7((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 8) {
                                            almPojo.setBuk8((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                    }
                                    //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                } else {
                                    throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                                }
                                almPojo.setgCode(gCode);
                                almPojo.setSgCode(sgCode);
                                almPojo.setHeadType(headType);
                                almPojo.setClassType(classType);
                                almPojo.setAcNature(acNat);
                                almPojo.setAcType(acType);
                                almPojo.setCodeFr(code);
                                almPojo.setCodeTo(code);
                                almPojo.setHeadOfAcc(headAcNo);
                                almPojo.setFormula(fOpt);
                                almPojo.setCountApplicable(cntApp);
                                almPojo.setNpaClassification(npaClass);
                                if (ssgCode == 0) {
                                    almPojo.setSsgCode(j + 1);
                                } else {
                                    almPojo.setSsgCode(ssgCode);
                                }
                                if (sssgCode == 0 && ssgCode != 0) {
                                    almPojo.setSssgCode(j + 1);
                                } else {
                                    almPojo.setSssgCode(sssgCode);
                                }
                                if (ssssgCode == 0 && sssgCode != 0 && ssgCode != 0) {
                                    almPojo.setSsssgCode(j + 1);
                                    almPojo.setDescription(vec.get(1).toString());
                                } else {
                                    almPojo.setSsssgCode(ssssgCode);
                                    almPojo.setDescription(String.valueOf("               ").concat(String.valueOf(j + 1)).concat(". ").concat(vec.get(1).toString()));
                                }
                                almPojoTable.add(almPojo);
                            }
                        } else {
                            Double totalAmt = 0d;
                            if (brCode.equalsIgnoreCase("90")) {
                                if (fOpt.equals("+P&L")) {
                                    totalAmt = glReport.IncomeExp(date, "0A", "0A");
                                } else if (fOpt.equals("-P&L")) {
                                    totalAmt = glReport.IncomeExp(date, "0A", "0A");
                                }
                            } else {
                                if (fOpt.equals("+P&L")) {
                                    totalAmt = glReport.IncomeExp(date, brCode, brCode);
                                } else if (fOpt.equals("-P&L")) {
                                    totalAmt = glReport.IncomeExp(date, brCode, brCode);
                                }
                            }

                            //totalAmt = totalAmt + ((totalCrDr) / repOption);
                            totalAmt = totalAmt / repOption;
                            if (classType.equalsIgnoreCase("O") && totalAmt > 0) {
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

    public List getAlmBucketMaster(String profileParameter, String dt, String bktNo) {
//        List bucketHistlist = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO,BUCKET_DESC, BUCKET_START_DAY, "
//                + " BUCKET_END_DAY,RECORD_MOD_CNT  FROM cbs_alm_bucket_master_his  WHERE PROFILE_PARAMETER = '"+profileParameter+"' "
//                + " AND RECORD_STATUS = 'A' and '"+dt+"' between EFF_FR_DT and EFF_TO_DT ORDER BY BUCKET_NO ").getResultList();
        String bktQuery = "";
        if (!bktNo.equalsIgnoreCase("ALL")) {
            bktQuery = " and ver.bucket_no = " + bktNo + " ";
        }
        String query = "select ver.version_no,ver.record_status,ver.bucket_no,ver.bucket_desc,ver.bucket_start_day,"
                + "ver.bucket_end_day,ver.modifi,ver.enter_by,ver.enter_dt,ver.last_mod_by,ver.last_mod_dt,ver.profile_parameter,"
                + "ver.minBktNo, c.bucket_desc as minBucketDesc, "
                + "ver.maxBktNo, b.bucket_desc as maxBucketDesc "
                + "from (select r.version_no,r.record_status,r.bucket_no,r.bucket_desc,r.bucket_start_day,r.bucket_end_day,r.record_mod_cnt,r.enter_by,r.enter_dt,r.last_mod_by,r.last_mod_dt,r.profile_parameter  from "
                + "(select version_no,record_status,bucket_no,bucket_desc,bucket_start_day,bucket_end_day,record_mod_cnt,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter from cbs_alm_bucket_master   where ifnull(enter_dt,'19000101') <= '" + dt + "' and (ifnull(last_mod_dt,'')='' or date_format(ifnull(last_mod_dt,ifnull(enter_dt,'19000101')),'%Y%m%d') <='" + dt + "')   and profile_parameter ='" + profileParameter + "'"
                + " union all  "
                + " select version_no,record_status,bucket_no,bucket_desc,bucket_start_day,bucket_end_day,record_mod_cnt,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter from cbs_alm_bucket_master_his   where ifnull(enter_dt,'19000101') <= '" + dt + "' and (ifnull(last_mod_dt,'')='' or date_format(ifnull(last_mod_dt,ifnull(enter_dt,'19000101')),'%Y%m%d') <= '" + dt + "') and profile_parameter ='" + profileParameter + "') r "
                + " ) a,"
                + " (select mo.version_no,mo.record_status,mo.bucket_no,mo.bucket_desc,mo.bucket_start_day,mo.bucket_end_day, max(mo.modi) as modifi,mo.enter_by,mo.enter_dt,mo.last_mod_dt,mo.last_mod_by,mo.profile_parameter, "
                + " (SELECT min(bucket_no)  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = mo.profile_parameter AND RECORD_STATUS = mo.record_status) as minBktNo,  "
                + " (SELECT max(bucket_no)  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = mo.profile_parameter AND RECORD_STATUS = mo.record_status) as maxBktNo from  "
                + " ( select g.version_no,g.record_status,g.bucket_no,g.bucket_desc,g.bucket_start_day,g.bucket_end_day, g.record_mod_cnt as modi,g.enter_by,g.enter_dt,g.last_mod_dt,g.last_mod_by,g.profile_parameter from cbs_alm_bucket_master g "
                + " where date_format(ifnull(g.last_mod_dt,ifnull(g.enter_dt,'19000101')),'%Y%m%d')  <='" + dt + "'  and g.profile_parameter='" + profileParameter + "' "
                + " group by  g.bucket_no"
                + " union all select g.version_no,g.record_status,g.bucket_no,g.bucket_desc,g.bucket_start_day,g.bucket_end_day, g.record_mod_cnt as modi,g.enter_by,g.enter_dt,g.last_mod_dt,g.last_mod_by,g.profile_parameter from cbs_alm_bucket_master_his g "
                + " where date_format(ifnull(g.last_mod_dt,ifnull(g.enter_dt,'19000101')),'%Y%m%d')  <='" + dt + "'  and g.profile_parameter='" + profileParameter + "' "
                + " group by  g.bucket_no "
                + " ) mo group by mo.bucket_no ) ver "
                + " left join "
                + " cbs_alm_bucket_master b on ver.PROFILE_PARAMETER = b.PROFILE_PARAMETER  AND b.RECORD_STATUS = ver.record_status and b.BUCKET_NO = ver.maxBktNo "
                + " left join "
                + " cbs_alm_bucket_master c on ver.PROFILE_PARAMETER = c.PROFILE_PARAMETER  AND c.RECORD_STATUS = ver.record_status and c.BUCKET_NO = ver.minBktNo "
                + " where a.record_mod_cnt = ver.modifi and a.bucket_no = ver.bucket_no and date_format(ifnull(a.last_mod_dt,ifnull(a.enter_dt,'19000101')),'%Y%m%d')  <='" + dt + "' " + bktQuery
                + " group by bucket_no order by  bucket_no";
//        System.out.println("Query:" + query);
        List bucketList = em.createNativeQuery(query).getResultList();
        return bucketList;
    }

    public List getAlmAccMaster(String profileParameter, String headNo, String dt) {
        List bucketList = em.createNativeQuery("select aa.flow,aa.heads_of_acc_no,aa.bucket_no,aa.percent_amt,aa.remarks,aa.enter_by,aa.enter_dt,aa.last_mod_by,aa.last_mod_dt,aa.profile_parameter  "
                + " from (select flow,heads_of_acc_no,bucket_no,percent_amt,remarks,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter  from cbs_alm_acc_class   "
                + " where ifnull(enter_dt,'19000101') <= '" + dt + "' and (ifnull(last_mod_dt,'')='' or date_format(ifnull(last_mod_dt,ifnull(enter_dt,'19000101')),'%Y%m%d') <='" + dt + "')   "
                + " and profile_parameter ='" + profileParameter + "' and  HEADS_OF_ACC_NO = '" + headNo + "'  "
                + " union   "
                + " select flow,heads_of_acc_no,bucket_no,percent_amt,remarks,enter_by,enter_dt,last_mod_by,ifnull(last_mod_dt,ifnull(enter_dt,'1900-01-01')) as last_mod_dt,profile_parameter "
                + " from cbs_alm_acc_class_his   where (date_format(ifnull(last_mod_dt,ifnull(enter_dt,'19000101')),'%Y%m%d') <= '" + dt + "') and profile_parameter ='" + profileParameter + "' "
                + " and  HEADS_OF_ACC_NO = '" + headNo + "'  ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO,last_mod_dt) aa where aa.last_mod_dt = (select max(aa.last_mod_dt) from "
                + " (select flow,heads_of_acc_no,bucket_no,percent_amt,remarks,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter  from cbs_alm_acc_class   "
                + " where ifnull(enter_dt,'19000101') <= '" + dt + "' and (ifnull(last_mod_dt,'')='' or date_format(ifnull(last_mod_dt,ifnull(enter_dt,'19000101')),'%Y%m%d') <='" + dt + "')   "
                + " and profile_parameter ='" + profileParameter + "' and  HEADS_OF_ACC_NO = '" + headNo + "'  "
                + " union   "
                + " select flow,heads_of_acc_no,bucket_no,percent_amt,remarks,enter_by,enter_dt,last_mod_by,ifnull(last_mod_dt,ifnull(enter_dt,'1900-01-01')) as last_mod_dt,profile_parameter from "
                + " cbs_alm_acc_class_his   where (date_format(ifnull(last_mod_dt,ifnull(enter_dt,'19000101')),'%Y%m%d') <= '" + dt + "') and profile_parameter ='" + profileParameter + "' "
                + " and  HEADS_OF_ACC_NO = '" + headNo + "'  ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO,last_mod_dt) aa )").getResultList();
        return bucketList;
    }

    public List<BucketWiseInvestmentPojo> getBucketWiseInvestmentdataForALM(String report, String toDt, String profileParameter) throws ApplicationException {
        List<BucketWiseInvestmentPojo> dataList = new ArrayList<BucketWiseInvestmentPojo>();
        try {
            List result = em.createNativeQuery("select bucket_no,bucket_desc,bucket_start_day,bucket_end_day from cbs_alm_bucket_master where profile_parameter = '" + profileParameter + "'").getResultList();
            if (!result.isEmpty()) {
                BucketWiseInvestmentPojo pojo = new BucketWiseInvestmentPojo();
                String dt1, dt2 = null, dt3;
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    String bktNo = vtr.get(0).toString();
                    String bktDesc = vtr.get(1).toString();
                    Integer bktStartDay = Integer.parseInt(vtr.get(2).toString());
                    Integer bktEndDay = Integer.parseInt(vtr.get(3).toString());
                    List result1 = new ArrayList();
                    if (report.equalsIgnoreCase("F")) {
                        if (bktNo.equalsIgnoreCase("1")) {
                            //dt1 = CbsUtil.dateAdd(toDt, bktStartDay);
                            dt1 = toDt;
                            dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                        } else {
                            dt3 = CbsUtil.dateAdd(dt2, 1);
                            dt1 = dt3;
                            dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                        }
//                        result1 = em.createNativeQuery("select ifnull(sum(a.tot_amt_int_rec),0),ifnull(sum(b.face_value),0) from investment_fdr_dates a,"
//                                + "investment_fdr_details b where a.mat_dt between '" + dt1 + "' and '" + dt2 + "' AND (a.CLOSED_DT is null or a.CLOSED_DT> '" + toDt + "') and a.ctrl_no = b.ctrl_no").getResultList();                        
                        result1 = em.createNativeQuery("select ifnull(sum(a.tot_amt_int_rec),0),ifnull(sum(b.face_value),0) from investment_fdr_dates a,"
                                + "investment_fdr_details b where a.mat_dt between '" + dt1 + "' and '" + dt2 + "' AND (a.CLOSED_DT is null or a.CLOSED_DT> '" + toDt + "') and a.ctrl_no = b.ctrl_no").getResultList();
                    } else {
                        if (bktNo.equalsIgnoreCase("1")) {
                            dt1 = toDt;
                            dt2 = CbsUtil.dateAdd(dt1, (bktStartDay - bktEndDay));
                        } else {
                            dt3 = CbsUtil.dateAdd(dt2, -1);
                            dt1 = dt3;
                            dt2 = CbsUtil.dateAdd(dt3, (bktStartDay - bktEndDay));
                        }
                        result1 = em.createNativeQuery("select ifnull(sum(a.dramt-a.cramt),0),0 from goi_recon a, investment_master b where a.CTRLNO = b.CONTROLNO "
                                + " and b.dt<='" + toDt + "' and (b.closedt is null or b.closedt='' or b.closedt> '" + toDt + "') and a.trantype ='8' and  "
                                + " a.dt between '" + dt2 + "' and '" + dt1 + "'").getResultList();
                        result1 = em.createNativeQuery(" select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)),0 from  investment_master b , "
                                + " goi_recon c where c.CTRLNO = b.CONTROLNO and c.trantype='8'  and b.SETTLEDT between '19000101' and '" + toDt + "' "
                                + " and (b.closedt is null or b.closedt='' or b.closedt> '" + toDt + "') and c.dt between '" + dt2 + "' and '" + dt1 + "'").getResultList();
                    }
                    if (!result1.isEmpty()) {
                        Vector ele = (Vector) result1.get(0);
                        BigDecimal fdAmt = new BigDecimal(ele.get(1).toString());
                        BigDecimal intAmt = new BigDecimal(ele.get(0).toString());
                        if (bktNo.equalsIgnoreCase("1")) {
                            pojo.setBkt1IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("2")) {
                            pojo.setBkt2IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("3")) {
                            pojo.setBkt3IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("4")) {
                            pojo.setBkt4IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("5")) {
                            pojo.setBkt5IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("6")) {
                            pojo.setBkt6IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("7")) {
                            pojo.setBkt7IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("8")) {
                            pojo.setBkt8IntAmt(intAmt);
                        }
                    }
                    pojo.setIntType("Int Accured Amount");
                    if (report.equalsIgnoreCase("F")) {
                        pojo.setRepType("FD");
                    } else {
                        pojo.setRepType("Security");
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<RbiAlmPojo> hoAlmDetailsSummary(String reptName, String dt, String orgnCode, Double repOpt, String summaryOpt, String profileParameter) throws ApplicationException {
        try {
            List<RbiAlmPojo> rbiPojoTable = new ArrayList<RbiAlmPojo>();
            List<String> dates = new ArrayList<>();
            dates.add(dt);
            List osBlancelist = rbiMonthlyReportFacade.getAsOnDateBalanceList(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, dates);
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName + "' and '" + dt + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "CLASSIFICATION DESC, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in Master Table");
            } else {
                Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
                double crrAmt = 0;
                String preFormula = "", npaAcDetails = "";
                System.out.println("Start Time:"+new Date());
                List<CrrSlrPojo> crrListAsPer = hoRemote.getCrrDetails(orgnCode, "R", dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)), "ALL");
                crrAmt = crrListAsPer.get(0).getSurplus() / repOpt;
                System.out.println("End Time:"+new Date());
                /**
                 * *****NPA Details of all accounts******
                 */
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");


                /**
                 * *****Getting the FD & MS Bucket wise details******
                 */
                List<AlmFddetailPojo> reportListFd = almFacadeRemote.getAlmFdDetail(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, CbsConstant.FIXED_AC, "ALL", dt, profileParameter, "ALL", "", "", "D");
                if (!reportListFd.isEmpty()) {
                    ComparatorChain chObj = new ComparatorChain();
                    //chObj.addComparator(new AlmFdShortedBybketWise());
                    chObj.addComparator(new AlmFdshortedByBktNo());
                    Collections.sort(reportListFd, chObj);
                }
                List<AlmFddetailPojo> reportListMs = almFacadeRemote.getAlmFdDetail(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, CbsConstant.MS_AC, "ALL", dt, profileParameter, "ALL", "", "", "D");
                if (!reportListMs.isEmpty()) {
                    ComparatorChain chObj = new ComparatorChain();
                    //chObj.addComparator(new AlmFdShortedBybketWise());
                    chObj.addComparator(new AlmFdshortedByBktNo());
                    Collections.sort(reportListMs, chObj);
                }
                List<AlmFddetailPojo> reportListDl = almFacadeRemote.getAlmFdDetail(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, CbsConstant.DEMAND_LOAN, "ALL", dt, profileParameter, "ALL", "", "", "M");
                if (!reportListDl.isEmpty()) {
                    ComparatorChain chObj = new ComparatorChain();
                    //chObj.addComparator(new AlmFdShortedBybketWise());
                    chObj.addComparator(new AlmFdshortedByBktNo());
                    Collections.sort(reportListDl, chObj);
                }
                /**
                 * *****End of Getting the FD & MS Bucket wise details******
                 */
                for (int i = 0; i < oss1Query.size(); i++) {
//                    System.out.println("i:" + i);
                    RbiAlmPojo rbiSossPojo = new RbiAlmPojo();
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
//                    System.out.println("I"+i +"Description"+description +"account nature "+acNature +"Account type "+acType+"GlHead from "+glHeadFrom+"Glhead To "+glHeadTo +"\n");
                    long noOfDays = 0;
                    Integer cl = null;
                    if (classification.equalsIgnoreCase("L")) {
                        cl = 1;
                    } else if (classification.equalsIgnoreCase("A")) {
                        cl = -1;
                    }
                    String crDbFlag = "";
                    /*Addition ath the first time*/
//                    if (acType == null || acType.equals("")) {
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setAmt(0d);
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
                    rbiSossPojo.setAmt(0d);
                    rbiSossPojo.setSecondAmount(0d);
                    rbiSossPojo.setThirdAmount(0d);
                    rbiSossPojo.setFourthAmount(0d);
                    rbiSossPojo.setFifthAmount(0d);
                    rbiSossPojo.setSixthAmount(0d);
                    rbiSossPojo.setSeventhAmount(0d);
                    rbiSossPojo.setEighthAmount(0d);
                    rbiSossPojo.setNineAmount(0d);
                    rbiSossPojo.setTenAmount(0d);
                    rbiSossPojo.setElevenAmount(0d);
                    rbiSossPojo.setTwelveAmount(0d);

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
                    Double bal = 0d;
                    AcctBalPojo acctBal = new AcctBalPojo();
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();

                    if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
                        List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();

                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                            List natureList = null;
                            if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                                if (acNature.contains(".")) {
                                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                                } else {
                                    acNature = params.getNature();
                                }
                                natureList = em.createNativeQuery("select acctcode,acctdesc,crdbFlag from accounttypemaster where "
                                        + "acctnature in ('" + acNature + "')").getResultList();


                            } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                                if (acType.contains(".")) {
                                    acType = acType.substring(acType.indexOf(".") + 1);
                                } else {
                                    acType = params.getAcType();
                                }
                                natureList = em.createNativeQuery("select acctcode,acctdesc,crdbFlag from accounttypemaster where "
                                        + "acctcode in ('" + acType + "')").getResultList();
                            }
                            String caQuery = "";
                            if (!fGNo.equalsIgnoreCase("")) {         //if FGNO is not blank means Fixed bucket no is provided
                                if (npaClassification.equalsIgnoreCase("")) {
                                    for (int n = 0; n < natureList.size(); n++) {
                                        Vector vector = (Vector) natureList.get(n);
                                        params.setNature("");
                                        params.setAcType("NON-NPA." + vector.get(0).toString());

                                        GlHeadPojo glPojo = new GlHeadPojo();
                                        glPojo.setGlHead(vector.get(0).toString());
                                        glPojo.setGlName(vector.get(1).toString());
                                        acType = vector.get(0).toString();
                                        acNature = commonRemote.getAcNatureByAcType(acType);
                                        crDbFlag = vector.get(2).toString();
                                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            List almExist = em.createNativeQuery("select * from parameterinfo_report where reportname='ALM'").getResultList();
                                            String actQuery = " having sign(sum(cramt-dramt)) = " + cl;
                                            if (fSGNo.equalsIgnoreCase("ACT")) {
                                                actQuery = "";
                                            }

                                            if (almExist.isEmpty()) {
                                                caQuery = "";
                                            } else {
                                                caQuery = " AND acno in (select acno from ca_recon WHERE DT <= '" + dt + "' group by acno " + actQuery + ") ";
                                            }
                                            npaAcDetails = "";
                                            if (crDbFlag.equalsIgnoreCase("D") || crDbFlag.equalsIgnoreCase("B")) {
                                                npaAcDetails = " AND ACNO NOT IN (select a.acno from accountstatus a, "
                                                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + dt + "' group by acno) b,"
                                                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' group by acno) c ,"
                                                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                                                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + dt + "' and "
                                                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + dt + "') ) ";
                                            }

                                            List resultSet = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from ca_recon where "
                                                    + " dt<='" + dt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' " + caQuery
                                                    + npaAcDetails).getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            glPojo.setBalance(new BigDecimal(vec1.get(0).toString()));

                                        } else {
                                            acctBal = rbiReportRemote.getAcctsAndBal(params);
                                            if (countApplicable.equalsIgnoreCase("Y")) {
                                                noOfAc = acctBal.getNumberOFAcct();
                                                glPojo.setBalance(new BigDecimal(noOfAc.toString()));
                                            } else {
                                                glPojo.setBalance(acctBal.getBalance());
                                            }
                                        }
                                        glHeadList.add(glPojo);
                                    }
                                } else {
                                    /*NPA Accounts value*/
                                    for (int n = 0; n < natureList.size(); n++) {
                                        Vector vector = (Vector) natureList.get(n);
                                        params.setNature("");
                                        params.setAcType("NPA." + vector.get(0).toString());
                                        bal = 0d;
                                        GlHeadPojo glPojo = new GlHeadPojo();
                                        glPojo.setGlHead(vector.get(0).toString());
                                        glPojo.setGlName(vector.get(1).toString());
                                        acType = vector.get(0).toString();
                                        acNature = commonRemote.getAcNatureByAcType(acType);
                                        crDbFlag = vector.get(2).toString();
//                                        acctBal = rbiReportRemote.getAcctsAndBal(params);
//                                        if (countApplicable.equalsIgnoreCase("Y")) {
//                                            noOfAc = acctBal.getNumberOFAcct();
//                                            glPojo.setBalance(new BigDecimal(noOfAc.toString()));
//                                        } else {
//                                            glPojo.setBalance(acctBal.getBalance());
//                                        }   
                                        BigDecimal balance = new BigDecimal("0");
                                        String flag = "0", npaClass = "";
                                        if (npaClassification.equalsIgnoreCase("N")) {
                                            flag = "0";
                                            npaClass = "";
                                        } else {
                                            flag = "1";
                                            if (npaClassification.equalsIgnoreCase("11")) {
                                                npaClass = "SUB STANDARD";
                                            } else if (npaClassification.equalsIgnoreCase("12")) {
                                                npaClass = "DOUBT FUL";
                                            } else if (npaClassification.equalsIgnoreCase("13")) {
                                                npaClass = "LOSS";
                                            }
                                        }
                                        if (crDbFlag.equalsIgnoreCase("D") || crDbFlag.equalsIgnoreCase("B")) {
//                                            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportData1(flag, acType, "19000101", dt, npaClass, orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode,"Y");
//                                            if (resultList.size() > 0) {
//                                                for (int x = 0; x < resultList.size(); x++) {
//                                                    balance = balance.add(resultList.get(x).getBalance());
//                                                }
//                                            }
                                            List<NpaStatusReportPojo> npaResultList = resultList;//npaRemote.getNpaStatusReportData1(flag, acType, "19000101", dt, npaClass, orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode,"Y");
                                            if (npaResultList.size() > 0) {
                                                for (int x = 0; x < npaResultList.size(); x++) {
                                                    if (npaClassification.equalsIgnoreCase("11")) {
                                                        if (npaResultList.get(x).getAcno().substring(2, 4).equalsIgnoreCase(acType) && !(npaResultList.get(x).getSubStandardDt() == null || npaResultList.get(x).getSubStandardDt().equalsIgnoreCase(""))) {
                                                            balance = balance.add(npaResultList.get(x).getBalance());
                                                        }
                                                    } else if (npaClassification.equalsIgnoreCase("12")) {
                                                        if (npaResultList.get(x).getAcno().substring(2, 4).equalsIgnoreCase(acType) && !(npaResultList.get(x).getDoubtFullDt() == null || npaResultList.get(x).getDoubtFullDt().equalsIgnoreCase(""))) {
                                                            balance = balance.add(npaResultList.get(x).getBalance());
                                                        }
                                                    } else if (npaClassification.equalsIgnoreCase("13")) {
                                                        if (npaResultList.get(x).getAcno().substring(2, 4).equalsIgnoreCase(acType) && !(npaResultList.get(x).getLossDt() == null || npaResultList.get(x).getLossDt().equalsIgnoreCase(""))) {
                                                            balance = balance.add(npaResultList.get(x).getBalance());
                                                        }
                                                    } else {
                                                        balance = balance.add(npaResultList.get(x).getAcno().substring(2, 4).equalsIgnoreCase(acType) ? npaResultList.get(x).getBalance() : new BigDecimal("0"));
                                                    }
//                                                    if(npaResultList.get(x).getAcno().substring(2, 4).equalsIgnoreCase(acType) && npaResultList.get(x).getSubStandardDt()!=null){
//                                                        balance = balance.add(npaResultList.get(x).getAcno().substring(2, 4).equalsIgnoreCase(acType)?npaResultList.get(x).getBalance():new BigDecimal("0"));
//                                                    }
                                                }
                                            }
                                        }
                                        glPojo.setBalance(balance);
                                        glHeadList.add(glPojo);
                                    }
                                }

                                /*Setting into main list*/
                                for (int z = 0; z < glHeadList.size(); z++) {
                                    rbiSossPojo = new RbiAlmPojo();
                                    GlHeadPojo glHeadPo = glHeadList.get(z);
                                    bal = Double.parseDouble(formatter.format((glHeadPo.getBalance().doubleValue()) / repOpt));
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        bal = bal;
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        bal = bal * -1;
                                    } else if (classification.equalsIgnoreCase("A") && bal < 0) {
                                        bal = bal * -1;
                                    } else if (classification.equalsIgnoreCase("A") && bal > 0 && gNo == 2) {
                                        /*If NPA Account Have Credit Balance in It*/
                                        bal = bal * -1;
                                    } else {
                                        bal = (fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : Math.abs(bal);
                                    }
                                    rbiSossPojo.setAmt(0d);
                                    rbiSossPojo.setSecondAmount(0d);
                                    rbiSossPojo.setThirdAmount(0d);
                                    rbiSossPojo.setFourthAmount(0d);
                                    rbiSossPojo.setFifthAmount(0d);
                                    rbiSossPojo.setSixthAmount(0d);
                                    rbiSossPojo.setSeventhAmount(0d);
                                    rbiSossPojo.setEighthAmount(0d);
                                    rbiSossPojo.setNineAmount(0d);
                                    rbiSossPojo.setTenAmount(0d);
                                    rbiSossPojo.setElevenAmount(0d);
                                    rbiSossPojo.setTwelveAmount(0d);
                                    List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND HEADS_OF_ACC_NO = " + fGNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                                    if (almAccClassList.isEmpty()) {
                                        almAccClassList = getAlmAccMaster(profileParameter, fGNo, dt);
                                    }
                                    if (!almAccClassList.isEmpty()) {
                                        for (int k = 0; k < almAccClassList.size(); k++) {
                                            Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                            String flow = almAcClassVect.get(0).toString();
                                            Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                            Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                            Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                            if (bucketNo == 1) {
                                                rbiSossPojo.setAmt((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 2) {
                                                rbiSossPojo.setSecondAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 3) {
                                                rbiSossPojo.setThirdAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 4) {
                                                rbiSossPojo.setFourthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 5) {
                                                rbiSossPojo.setFifthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 6) {
                                                rbiSossPojo.setSixthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 7) {
                                                rbiSossPojo.setSeventhAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 8) {
                                                rbiSossPojo.setEighthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 9) {
                                                rbiSossPojo.setNineAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 10) {
                                                rbiSossPojo.setTenAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 11) {
                                                rbiSossPojo.setElevenAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 12) {
                                                rbiSossPojo.setTwelveAmount((bal * percentage) / 100);
                                            }
                                        }
                                        //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                    } else {
                                        throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
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
                            } else {
                                /*
                                 * NON-NPA LOAN Accounts and FD/TD/MS Accounts
                                 */
                                for (int n = 0; n < natureList.size(); n++) {
                                    rbiSossPojo = new RbiAlmPojo();
                                    Vector vector = (Vector) natureList.get(n);
                                    String actp = vector.get(0).toString();
                                    acNature = commonRemote.getAcNatureByAcType(actp);
                                    String acName = vector.get(1).toString();
                                    crDbFlag = vector.get(2).toString();
                                    rbiSossPojo.setAmt(0d);
                                    rbiSossPojo.setSecondAmount(0d);
                                    rbiSossPojo.setThirdAmount(0d);
                                    rbiSossPojo.setFourthAmount(0d);
                                    rbiSossPojo.setFifthAmount(0d);
                                    rbiSossPojo.setSixthAmount(0d);
                                    rbiSossPojo.setSeventhAmount(0d);
                                    rbiSossPojo.setEighthAmount(0d);
                                    rbiSossPojo.setNineAmount(0d);
                                    rbiSossPojo.setTenAmount(0d);
                                    rbiSossPojo.setElevenAmount(0d);
                                    rbiSossPojo.setTwelveAmount(0d);
                                    List resultSet = null;
                                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List almExist = em.createNativeQuery("select * from parameterinfo_report where reportname='ALM'").getResultList();

                                        String actQuery = " having sign(sum(cramt-dramt)) = " + cl;
                                        if (fSGNo.equalsIgnoreCase("ACT")) {
                                            actQuery = "";
                                        }
                                        if (almExist.isEmpty()) {
                                            caQuery = "";
                                        } else {
                                            caQuery = " AND acno in (select acno from ca_recon WHERE DT <= '" + dt + "' group by acno " + actQuery + ") ";
                                        }
                                        npaAcDetails = "";
                                        if (crDbFlag.equalsIgnoreCase("D") || crDbFlag.equalsIgnoreCase("B")) {
                                            npaAcDetails = " AND ACNO NOT IN (select a.acno from accountstatus a, "
                                                    + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + dt + "' group by acno) b,"
                                                    + "(select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' group by acno) c ,"
                                                    + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                                                    + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + dt + "' and "
                                                    + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + dt + "') ) ";
                                        }

                                        resultSet = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from ca_recon where "
                                                + " dt<='" + dt + "' AND SUBSTRING(acno,3,2) ='" + acType + "' AND auth = 'Y' " + caQuery
                                                + npaAcDetails).getResultList();
//                                            Vector vec1 = (Vector) resultSet.get(0);
//                                            bal = new BigDecimal(vec1.get(0).toString()) ;  

                                    } else if ((acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                        List<AlmFddetailPojo> reportList = new ArrayList<AlmFddetailPojo>();
                                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                                            reportList = reportListFd;
                                        } else if (acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                            reportList = reportListMs;
                                        }
                                        List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
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

                                                if (!reportList.isEmpty()) {
                                                    for (int a = 0; a < reportList.size(); a++) {
                                                        if (reportList.get(a).getAcNo().substring(2, 4).equalsIgnoreCase(actp)) {
                                                            String acno = reportList.get(a).getAcNo();
                                                            bal = reportList.get(a).getAmount() / repOpt;
                                                            String bktNo = reportList.get(a).getBktNo();
                                                            if (bktNoMast == 1 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                            } else if (bktNoMast == 2 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setSecondAmount(rbiSossPojo.getSecondAmount() == null ? 0 + bal : rbiSossPojo.getSecondAmount() + bal);
                                                            } else if (bktNoMast == 3 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setThirdAmount(rbiSossPojo.getThirdAmount() == null ? 0 + bal : rbiSossPojo.getThirdAmount() + bal);
                                                            } else if (bktNoMast == 4 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setFourthAmount(rbiSossPojo.getFourthAmount() == null ? 0 + bal : rbiSossPojo.getFourthAmount() + bal);
                                                            } else if (bktNoMast == 5 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setFifthAmount(rbiSossPojo.getFifthAmount() == null ? 0 + bal : rbiSossPojo.getFifthAmount() + bal);
                                                            } else if (bktNoMast == 6 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setSixthAmount(rbiSossPojo.getSixthAmount() == null ? 0 + bal : rbiSossPojo.getSixthAmount() + bal);
                                                            } else if (bktNoMast == 7 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setSeventhAmount(rbiSossPojo.getSeventhAmount() == null ? 0 + bal : rbiSossPojo.getSeventhAmount() + bal);
                                                            } else if (bktNoMast == 8 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setEighthAmount(rbiSossPojo.getEighthAmount() == null ? 0 + bal : rbiSossPojo.getEighthAmount() + bal);
                                                            } else if (bktNoMast == 9 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setNineAmount(rbiSossPojo.getNineAmount() == null ? 0 + bal : rbiSossPojo.getNineAmount() + bal);
                                                            } else if (bktNoMast == 10 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setTenAmount(rbiSossPojo.getTenAmount() == null ? 0 + bal : rbiSossPojo.getTenAmount() + bal);
                                                            } else if (bktNoMast == 11 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setElevenAmount(rbiSossPojo.getElevenAmount() == null ? 0 + bal : rbiSossPojo.getElevenAmount() + bal);
                                                            } else if (bktNoMast == 12 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                rbiSossPojo.setTwelveAmount(rbiSossPojo.getTwelveAmount() == null ? 0 + bal : rbiSossPojo.getTwelveAmount() + bal);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if ((acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC))/*&& (headType.equalsIgnoreCase("A"))*/) {
                                        String table = commonRemote.getTableName(acNature);
                                        List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
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
                                                    dt1 = CbsUtil.dateAdd(dt, bktStartDay);
                                                    dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                                    if (orgnCode.equalsIgnoreCase("90")) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM " + table + " WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + dt + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + actp + "' AND OPENINGDT<= '" + dt + "'  AND DATE_FORMAT(ifnull(RDMATDATE,'" + dt + "'), '%Y%m%d') <='" + dt2 + "')").getResultList();
                                                    } else {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM " + table + " WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + orgnCode + "'AND  SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + dt + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + actp + "' AND OPENINGDT<= '" + dt + "'  AND DATE_FORMAT(ifnull(RDMATDATE,'" + dt + "'), '%Y%m%d') <='" + dt2 + "')").getResultList();
                                                    }
                                                    getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                    bal = Double.parseDouble(getLoanValueVect.get(0).toString()) / repOpt;
//                                                    if(gNo==1 && classification.equalsIgnoreCase("A")){
//                                                        rbiSossPojo.setAmt(bal);
//                                                    } else if(gNo==2 && classification.equalsIgnoreCase("L")){
//                                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
//                                                    } else {
//                                                        rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs());
//                                                    }
                                                } else {
                                                    dt3 = CbsUtil.dateAdd(dt2, 1);
                                                    dt1 = dt3;
                                                    dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                    if (orgnCode.equalsIgnoreCase("90")) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM " + table + " WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) ='" + actp + "' AND DT <='" + dt + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + actp + "' AND OPENINGDT<='" + dt + "' AND DATE_FORMAT(ifnull(RDMATDATE,'" + dt + "'), '%Y%m%d') "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();

                                                    } else {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM " + table + " WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + orgnCode + "' AND  SUBSTRING(ACNO,3,2) ='" + actp + "' AND DT <='" + dt + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + actp + "' AND OPENINGDT<='" + dt + "' AND DATE_FORMAT(ifnull(RDMATDATE,'" + dt + "'), '%Y%m%d') "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();

                                                    }
                                                    getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                    bal = Double.parseDouble(getLoanValueVect.get(0).toString()) / repOpt;
                                                }

//                                                if (bktNoMast == 1) {
//                                                    //System.out.println("acno:"+acno+"; amt:"+amt+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; maddt:"+maddt);
//                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt().add(bal));
                                                if (bktNoMast == 1) {
                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                } else if (bktNoMast == 2) {
                                                    rbiSossPojo.setSecondAmount(rbiSossPojo.getSecondAmount() == null ? 0 + bal : rbiSossPojo.getSecondAmount() + bal);
                                                } else if (bktNoMast == 3) {
                                                    rbiSossPojo.setThirdAmount(rbiSossPojo.getThirdAmount() == null ? 0 + bal : rbiSossPojo.getThirdAmount() + bal);
                                                } else if (bktNoMast == 4) {
                                                    rbiSossPojo.setFourthAmount(rbiSossPojo.getFourthAmount() == null ? 0 + bal : rbiSossPojo.getFourthAmount() + bal);
                                                } else if (bktNoMast == 5) {
                                                    rbiSossPojo.setFifthAmount(rbiSossPojo.getFifthAmount() == null ? 0 + bal : rbiSossPojo.getFifthAmount() + bal);
                                                } else if (bktNoMast == 6) {
                                                    rbiSossPojo.setSixthAmount(rbiSossPojo.getSixthAmount() == null ? 0 + bal : rbiSossPojo.getSixthAmount() + bal);
                                                } else if (bktNoMast == 7) {
                                                    rbiSossPojo.setSeventhAmount(rbiSossPojo.getSeventhAmount() == null ? 0 + bal : rbiSossPojo.getSeventhAmount() + bal);
                                                } else if (bktNoMast == 8) {
                                                    rbiSossPojo.setEighthAmount(rbiSossPojo.getEighthAmount() == null ? 0 + bal : rbiSossPojo.getEighthAmount() + bal);
                                                } else if (bktNoMast == 9) {
                                                    rbiSossPojo.setNineAmount(rbiSossPojo.getNineAmount() == null ? 0 + bal : rbiSossPojo.getNineAmount() + bal);
                                                } else if (bktNoMast == 10) {
                                                    rbiSossPojo.setTenAmount(rbiSossPojo.getTenAmount() == null ? 0 + bal : rbiSossPojo.getTenAmount() + bal);
                                                } else if (bktNoMast == 11) {
                                                    rbiSossPojo.setElevenAmount(rbiSossPojo.getElevenAmount() == null ? 0 + bal : rbiSossPojo.getElevenAmount() + bal);
                                                } else if (bktNoMast == 12) {
                                                    rbiSossPojo.setTwelveAmount(rbiSossPojo.getTwelveAmount() == null ? 0 + bal : rbiSossPojo.getTwelveAmount() + bal);
                                                }
                                            }
//                                            almPojoTable.add(almPojo);
                                        } else {
                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                        }
                                    } else if ((acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                        if (fSsssGNo.equalsIgnoreCase("M")) {
                                            /*Handling the Bucket wise */
                                            List<AlmFddetailPojo> reportList = new ArrayList<AlmFddetailPojo>();
                                            reportList = reportListDl;
                                            List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
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

                                                    if (!reportList.isEmpty()) {
                                                        for (int a = 0; a < reportList.size(); a++) {
                                                            if (reportList.get(a).getAcNo().substring(2, 4).equalsIgnoreCase(actp)) {
                                                                String acno = reportList.get(a).getAcNo();
                                                                bal = reportList.get(a).getAmount() / repOpt;
                                                                String bktNo = reportList.get(a).getBktNo();
                                                                if (bktNoMast == 1 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                                } else if (bktNoMast == 2 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setSecondAmount(rbiSossPojo.getSecondAmount() == null ? 0 + bal : rbiSossPojo.getSecondAmount() + bal);
                                                                } else if (bktNoMast == 3 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setThirdAmount(rbiSossPojo.getThirdAmount() == null ? 0 + bal : rbiSossPojo.getThirdAmount() + bal);
                                                                } else if (bktNoMast == 4 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setFourthAmount(rbiSossPojo.getFourthAmount() == null ? 0 + bal : rbiSossPojo.getFourthAmount() + bal);
                                                                } else if (bktNoMast == 5 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setFifthAmount(rbiSossPojo.getFifthAmount() == null ? 0 + bal : rbiSossPojo.getFifthAmount() + bal);
                                                                } else if (bktNoMast == 6 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setSixthAmount(rbiSossPojo.getSixthAmount() == null ? 0 + bal : rbiSossPojo.getSixthAmount() + bal);
                                                                } else if (bktNoMast == 7 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setSeventhAmount(rbiSossPojo.getSeventhAmount() == null ? 0 + bal : rbiSossPojo.getSeventhAmount() + bal);
                                                                } else if (bktNoMast == 8 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setEighthAmount(rbiSossPojo.getEighthAmount() == null ? 0 + bal : rbiSossPojo.getEighthAmount() + bal);
                                                                } else if (bktNoMast == 9 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setNineAmount(rbiSossPojo.getNineAmount() == null ? 0 + bal : rbiSossPojo.getNineAmount() + bal);
                                                                } else if (bktNoMast == 10 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setTenAmount(rbiSossPojo.getTenAmount() == null ? 0 + bal : rbiSossPojo.getTenAmount() + bal);
                                                                } else if (bktNoMast == 11 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setElevenAmount(rbiSossPojo.getElevenAmount() == null ? 0 + bal : rbiSossPojo.getElevenAmount() + bal);
                                                                } else if (bktNoMast == 12 && bktNo.equalsIgnoreCase(bktNoMast.toString())) {
                                                                    rbiSossPojo.setTwelveAmount(rbiSossPojo.getTwelveAmount() == null ? 0 + bal : rbiSossPojo.getTwelveAmount() + bal);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            /**
                                             * **************************************
                                             * When Head Type = 'A' means Non
                                             * NPA Account
                                             * ***************************************
                                             */
                                            npaAcDetails = "";
                                            if ((acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                                npaAcDetails = " AND ACNO NOT IN (select a.acno from accountstatus a, "
                                                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + dt + "' group by acno) b,"
                                                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' group by acno) c ,"
                                                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                                                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + dt + "' and "
                                                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + dt + "') ) ";
                                            }
                                            String table = commonRemote.getTableName(acNature);
                                            List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
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
                                                        dt1 = CbsUtil.dateAdd(dt, bktStartDay);
                                                        dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                                        if (orgnCode.equalsIgnoreCase("90")) {
                                                            getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + dt + "' AND AUTH = 'Y' AND "
                                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + dt + "'  AND "
                                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "') "
                                                                    + npaAcDetails).getResultList();


                                                        } else {
                                                            getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + dt + "' AND AUTH = 'Y' AND "
                                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + dt + "' AND "
                                                                    + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + orgnCode + "' AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "') "
                                                                    + npaAcDetails).getResultList();

                                                        }
                                                        getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                        bal = Double.parseDouble(getLoanValueVect.get(0).toString()) / repOpt;
//                                                    if(gNo==1 && classification.equalsIgnoreCase("A")){
//                                                        rbiSossPojo.setAmt(bal);
//                                                    } else if(gNo==2 && classification.equalsIgnoreCase("L")){
//                                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
//                                                    } else {
//                                                        rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs());
//                                                    }
                                                    } else {
                                                        dt3 = CbsUtil.dateAdd(dt2, 1);
                                                        dt1 = dt3;
                                                        dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                        if (orgnCode.equalsIgnoreCase("90")) {
                                                            getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + dt + "' AND AUTH = 'Y' AND"
                                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                    + " WHERE  SUBSTRING(A.ACNO,3,2)  = '" + actp + "' AND A.OPENINGDT<='" + dt + "' AND "
                                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') BETWEEN '" + dt1 + "' AND '" + dt2 + "') "
                                                                    + npaAcDetails).getResultList();
                                                        } else {
                                                            getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' "
                                                                    + " AND DT <='" + dt + "' AND AUTH = 'Y' AND ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<='" + dt + "' AND "
                                                                    + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + orgnCode + "' AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') BETWEEN '" + dt1 + "' AND '" + dt2 + "') "
                                                                    + npaAcDetails).getResultList();
                                                        }
                                                        getLoanValueVect = (Vector) getLoanValueList.get(0);
                                                        bal = Double.parseDouble(getLoanValueVect.get(0).toString()) / repOpt;
//                                                    if(gNo==1 && classification.equalsIgnoreCase("A")){
//                                                        rbiSossPojo.setAmt(bal);
//                                                    } else if(gNo==2 && classification.equalsIgnoreCase("L")){
//                                                        rbiSossPojo.setAmt(bal.multiply(new BigDecimal("-1")));
//                                                    } else {
//                                                        rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs());
//                                                    }
                                                    }

//                                                if (bktNoMast == 1) {
//                                                    //System.out.println("acno:"+acno+"; amt:"+amt+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; maddt:"+maddt);
//                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt().add(bal));
                                                    if (bktNoMast == 1) {
                                                        rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                    } else if (bktNoMast == 2) {
                                                        rbiSossPojo.setSecondAmount(rbiSossPojo.getSecondAmount() == null ? 0 + bal : rbiSossPojo.getSecondAmount() + bal);
                                                    } else if (bktNoMast == 3) {
                                                        rbiSossPojo.setThirdAmount(rbiSossPojo.getThirdAmount() == null ? 0 + bal : rbiSossPojo.getThirdAmount() + bal);
                                                    } else if (bktNoMast == 4) {
                                                        rbiSossPojo.setFourthAmount(rbiSossPojo.getFourthAmount() == null ? 0 + bal : rbiSossPojo.getFourthAmount() + bal);
                                                    } else if (bktNoMast == 5) {
                                                        rbiSossPojo.setFifthAmount(rbiSossPojo.getFifthAmount() == null ? 0 + bal : rbiSossPojo.getFifthAmount() + bal);
                                                    } else if (bktNoMast == 6) {
                                                        rbiSossPojo.setSixthAmount(rbiSossPojo.getSixthAmount() == null ? 0 + bal : rbiSossPojo.getSixthAmount() + bal);
                                                    } else if (bktNoMast == 7) {
                                                        rbiSossPojo.setSeventhAmount(rbiSossPojo.getSeventhAmount() == null ? 0 + bal : rbiSossPojo.getSeventhAmount() + bal);
                                                    } else if (bktNoMast == 8) {
                                                        rbiSossPojo.setEighthAmount(rbiSossPojo.getEighthAmount() == null ? 0 + bal : rbiSossPojo.getEighthAmount() + bal);
                                                    } else if (bktNoMast == 9) {
                                                        rbiSossPojo.setNineAmount(rbiSossPojo.getNineAmount() == null ? 0 + bal : rbiSossPojo.getNineAmount() + bal);
                                                    } else if (bktNoMast == 10) {
                                                        rbiSossPojo.setTenAmount(rbiSossPojo.getTenAmount() == null ? 0 + bal : rbiSossPojo.getTenAmount() + bal);
                                                    } else if (bktNoMast == 11) {
                                                        rbiSossPojo.setElevenAmount(rbiSossPojo.getElevenAmount() == null ? 0 + bal : rbiSossPojo.getElevenAmount() + bal);
                                                    } else if (bktNoMast == 12) {
                                                        rbiSossPojo.setTwelveAmount(rbiSossPojo.getTwelveAmount() == null ? 0 + bal : rbiSossPojo.getTwelveAmount() + bal);
                                                    }
                                                }
//                                            almPojoTable.add(almPojo);
                                            } else {
                                                throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                            }
                                        }
                                    } else if ((acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN))/*&& (headType.equalsIgnoreCase("A"))*/) {
                                        npaAcDetails = "";
                                        /*
                                         * Getting the NPA Account List
                                         */
//                                        if(!npaAcDetails.equalsIgnoreCase("")){
                                        npaAcDetails = " AND ACNO NOT IN (select a.acno from accountstatus a, "
                                                + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + dt + "' group by acno) b,"
                                                + "(select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' group by acno) c ,"
                                                + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                                                + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + dt + "' and "
                                                + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + dt + "') ) ";
//                                        }
                                        List acNoList = null;
                                        if (orgnCode.equalsIgnoreCase("90")) {
                                            acNoList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + dt + "' AND AUTH = 'Y' AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + dt + "'  AND "
                                                    + " A.ACNO=L.ACNO ) "
                                                    + npaAcDetails + " group by acno having ifnull(sum(ifnull(dramt,0)) - sum(ifnull(cramt,0)),0) <>0 order by acno").getResultList();
                                        } else {
                                            acNoList = em.createNativeQuery("SELECT acno, ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + dt + "' AND AUTH = 'Y' AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                    + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + dt + "' AND "
                                                    + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + orgnCode + "' ) "
                                                    + npaAcDetails + "  group by acno having ifnull(sum(ifnull(dramt,0)) - sum(ifnull(cramt,0)),0) <>0 order by acno").getResultList();
                                        }
                                        if (!acNoList.isEmpty()) {
                                            for (int h = 0; h < acNoList.size(); h++) {
                                                double loanBal = 0d, outStand = 0d;
                                                Vector acNoVect = (Vector) acNoList.get(h);
                                                String acNo = acNoVect.get(0).toString();
                                                bal = 0d;
                                                loanBal = Double.parseDouble(acNoVect.get(1).toString());
//                                                System.out.println("%%%%%acno:"+acNo+"; Bal:"+loanBal);
                                                if (loanBal <= 0) {
                                                    /*
                                                     * If Account have Credit (Cr) Balance
                                                     */
                                                    bal = loanBal / repOpt;
                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                } else {
                                                    /*
                                                     * If Account have Debit (Dr) Balance
                                                     */
                                                    List emiExistOfLoanAcNo = em.createNativeQuery("SELECT * FROM emidetails  WHERE acno = '" + acNo + "'").getResultList();
                                                    if (emiExistOfLoanAcNo.isEmpty()) {
                                                        /*
                                                         * EMI NOT EXIST
                                                         */
                                                        List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");

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
                                                                    dt1 = CbsUtil.dateAdd(dt, bktStartDay);
                                                                    dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                                                                    getLoanValueList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                            + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + dt + "'  AND "
                                                                            + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();

                                                                    if (getLoanValueList.size() > 0) {
                                                                        bal = loanBal / repOpt;
                                                                        loanBal = 0d;
                                                                    } else {
                                                                        bal = 0d;
                                                                    }
                                                                } else {
                                                                    dt3 = CbsUtil.dateAdd(dt2, 1);
                                                                    dt1 = dt3;
                                                                    dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                                                                    getLoanValueList = em.createNativeQuery("SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                            + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + dt + "'  AND "
                                                                            + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
                                                                    if (getLoanValueList.size() > 0) {
                                                                        bal = loanBal / repOpt;
                                                                        loanBal = 0d;
                                                                    } else {
                                                                        bal = 0d;
                                                                    }
                                                                }
//                                                                System.out.println("NON EMI acno:"+acNo+"; amt:"+bal+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; dt1:"+dt1+"; dt2:"+dt2);
                                                                //                                                if (bktNoMast == 1) {
                                                                //                                                    //System.out.println("acno:"+acno+"; amt:"+amt+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; maddt:"+maddt);
                                                                //                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt().add(bal));
                                                                if (bktNoMast == 1) {
                                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                                } else if (bktNoMast == 2) {
                                                                    rbiSossPojo.setSecondAmount(rbiSossPojo.getSecondAmount() == null ? 0 + bal : rbiSossPojo.getSecondAmount() + bal);
                                                                } else if (bktNoMast == 3) {
                                                                    rbiSossPojo.setThirdAmount(rbiSossPojo.getThirdAmount() == null ? 0 + bal : rbiSossPojo.getThirdAmount() + bal);
                                                                } else if (bktNoMast == 4) {
                                                                    rbiSossPojo.setFourthAmount(rbiSossPojo.getFourthAmount() == null ? 0 + bal : rbiSossPojo.getFourthAmount() + bal);
                                                                } else if (bktNoMast == 5) {
                                                                    rbiSossPojo.setFifthAmount(rbiSossPojo.getFifthAmount() == null ? 0 + bal : rbiSossPojo.getFifthAmount() + bal);
                                                                } else if (bktNoMast == 6) {
                                                                    rbiSossPojo.setSixthAmount(rbiSossPojo.getSixthAmount() == null ? 0 + bal : rbiSossPojo.getSixthAmount() + bal);
                                                                } else if (bktNoMast == 7) {
                                                                    rbiSossPojo.setSeventhAmount(rbiSossPojo.getSeventhAmount() == null ? 0 + bal : rbiSossPojo.getSeventhAmount() + bal);
                                                                } else if (bktNoMast == 8) {
                                                                    rbiSossPojo.setEighthAmount(rbiSossPojo.getEighthAmount() == null ? 0 + bal : rbiSossPojo.getEighthAmount() + bal);
                                                                } else if (bktNoMast == 9) {
                                                                    rbiSossPojo.setNineAmount(rbiSossPojo.getNineAmount() == null ? 0 + bal : rbiSossPojo.getNineAmount() + bal);
                                                                } else if (bktNoMast == 10) {
                                                                    rbiSossPojo.setTenAmount(rbiSossPojo.getTenAmount() == null ? 0 + bal : rbiSossPojo.getTenAmount() + bal);
                                                                } else if (bktNoMast == 11) {
                                                                    rbiSossPojo.setElevenAmount(rbiSossPojo.getElevenAmount() == null ? 0 + bal : rbiSossPojo.getElevenAmount() + bal);
                                                                } else if (bktNoMast == 12) {
                                                                    rbiSossPojo.setTwelveAmount(rbiSossPojo.getTwelveAmount() == null ? 0 + bal : rbiSossPojo.getTwelveAmount() + bal);
                                                                }
                                                            }
                                                            //                                            almPojoTable.add(almPojo);
                                                        } else {
                                                            throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                                                        }

                                                    } else {
                                                        /*
                                                         * EMI EXIST
                                                         */
                                                        List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
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
                                                                double emiBal = 0d;
                                                                if (bktStartDay == 1) {
                                                                    dt1 = CbsUtil.dateAdd(dt, bktStartDay);
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
                                                                                    + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + dt + "'  AND "
                                                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
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
                                                                    bal = outStand / repOpt;
                                                                } else {
                                                                    dt3 = CbsUtil.dateAdd(dt2, 1);
                                                                    dt1 = dt3;
                                                                    dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));

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
                                                                                    + " WHERE  A.ACNO = '" + acNo + "' AND A.OPENINGDT<= '" + dt + "'  AND "
                                                                                    + " A.ACNO=L.ACNO AND DATE_FORMAT(date_add(OPENINGDT,interval L.LOAN_PD_MONTH month), '%Y%m%d') <= '" + dt2 + "' ").getResultList();
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
                                                                    bal = outStand / repOpt;
                                                                }
//                                                                System.out.println("EMI acno:"+acNo+"; LoanBal:"+loanBal+"; amt:"+bal+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; dt1:"+dt1+"; dt2:"+dt2);

                                                                //                                                if (bktNoMast == 1) {
                                                                //                                                    //System.out.println("acno:"+acno+"; amt:"+amt+"; noOfDays:"+noOfDays+"; bktStartDay:"+bktStartDay+"; bktEndDay:"+bktEndDay+"; maddt:"+maddt);
                                                                //                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt().add(bal));
                                                                if (bktNoMast == 1) {
                                                                    rbiSossPojo.setAmt(rbiSossPojo.getAmt() == null ? 0 + bal : rbiSossPojo.getAmt() + bal);
                                                                } else if (bktNoMast == 2) {
                                                                    rbiSossPojo.setSecondAmount(rbiSossPojo.getSecondAmount() == null ? 0 + bal : rbiSossPojo.getSecondAmount() + bal);
                                                                } else if (bktNoMast == 3) {
                                                                    rbiSossPojo.setThirdAmount(rbiSossPojo.getThirdAmount() == null ? 0 + bal : rbiSossPojo.getThirdAmount() + bal);
                                                                } else if (bktNoMast == 4) {
                                                                    rbiSossPojo.setFourthAmount(rbiSossPojo.getFourthAmount() == null ? 0 + bal : rbiSossPojo.getFourthAmount() + bal);
                                                                } else if (bktNoMast == 5) {
                                                                    rbiSossPojo.setFifthAmount(rbiSossPojo.getFifthAmount() == null ? 0 + bal : rbiSossPojo.getFifthAmount() + bal);
                                                                } else if (bktNoMast == 6) {
                                                                    rbiSossPojo.setSixthAmount(rbiSossPojo.getSixthAmount() == null ? 0 + bal : rbiSossPojo.getSixthAmount() + bal);
                                                                } else if (bktNoMast == 7) {
                                                                    rbiSossPojo.setSeventhAmount(rbiSossPojo.getSeventhAmount() == null ? 0 + bal : rbiSossPojo.getSeventhAmount() + bal);
                                                                } else if (bktNoMast == 8) {
                                                                    rbiSossPojo.setEighthAmount(rbiSossPojo.getEighthAmount() == null ? 0 + bal : rbiSossPojo.getEighthAmount() + bal);
                                                                } else if (bktNoMast == 9) {
                                                                    rbiSossPojo.setNineAmount(rbiSossPojo.getNineAmount() == null ? 0 + bal : rbiSossPojo.getNineAmount() + bal);
                                                                } else if (bktNoMast == 10) {
                                                                    rbiSossPojo.setTenAmount(rbiSossPojo.getTenAmount() == null ? 0 + bal : rbiSossPojo.getTenAmount() + bal);
                                                                } else if (bktNoMast == 11) {
                                                                    rbiSossPojo.setElevenAmount(rbiSossPojo.getElevenAmount() == null ? 0 + bal : rbiSossPojo.getElevenAmount() + bal);
                                                                } else if (bktNoMast == 12) {
                                                                    rbiSossPojo.setTwelveAmount(rbiSossPojo.getTwelveAmount() == null ? 0 + bal : rbiSossPojo.getTwelveAmount() + bal);
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
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(acName));

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
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(acName));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(preSsssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(acName));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = preZ + 1;
                                        } else {
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(acName));

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
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(acName));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(acName));
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
                                acctBal = rbiReportRemote.getAcctsAndBalAmtRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = Double.parseDouble(formatter.format(acctBal.getBalance().doubleValue())) / repOpt;
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Y") || rangeBaseOn.equalsIgnoreCase("M") || rangeBaseOn.equalsIgnoreCase("D")) {
                                acctBal = rbiReportRemote.getAcctsAndBalTimeRangeWise(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = Double.parseDouble(formatter.format(acctBal.getBalance().doubleValue())) / repOpt;
                                }
                            } else if (rangeBaseOn.equalsIgnoreCase("Dt") || npaClassification.equalsIgnoreCase("N")) {
                                acctBal = rbiReportRemote.getNpaAcctsAndBal(params);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = acctBal.getNumberOFAcct();
                                } else {
                                    bal = Double.parseDouble(formatter.format(acctBal.getBalance().doubleValue())) / repOpt;
                                }
                            }

                            rbiSossPojo = new RbiAlmPojo();
                            rbiSossPojo.setAmt(0d);
                            rbiSossPojo.setSecondAmount(0d);
                            rbiSossPojo.setThirdAmount(0d);
                            rbiSossPojo.setFourthAmount(0d);
                            rbiSossPojo.setFifthAmount(0d);
                            rbiSossPojo.setSixthAmount(0d);
                            rbiSossPojo.setSeventhAmount(0d);
                            rbiSossPojo.setEighthAmount(0d);
                            rbiSossPojo.setNineAmount(0d);
                            rbiSossPojo.setTenAmount(0d);
                            rbiSossPojo.setElevenAmount(0d);
                            rbiSossPojo.setTwelveAmount(0d);
                            if (countApplicable.equalsIgnoreCase("Y")) {
                                //noOfAc = (long) glPojoList.size();
                                rbiSossPojo.setAmt(noOfAc.doubleValue());
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    bal = bal;
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    bal = bal * -1;
                                } else if (classification.equalsIgnoreCase("A") && bal < 0) {
                                    bal = bal * -1;
                                } else {
                                    bal = (fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : Math.abs(bal);
                                }
                                List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND HEADS_OF_ACC_NO = " + fGNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                                if (almAccClassList.isEmpty()) {
                                    almAccClassList = getAlmAccMaster(profileParameter, fGNo, dt);
                                }
                                if (!almAccClassList.isEmpty()) {
                                    for (int k = 0; k < almAccClassList.size(); k++) {
                                        Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                        String flow = almAcClassVect.get(0).toString();
                                        Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                        Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                        Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                        if (bucketNo == 1) {
                                            rbiSossPojo.setAmt((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 2) {
                                            rbiSossPojo.setSecondAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 3) {
                                            rbiSossPojo.setThirdAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 4) {
                                            rbiSossPojo.setFourthAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 5) {
                                            rbiSossPojo.setFifthAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 6) {
                                            rbiSossPojo.setSixthAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 7) {
                                            rbiSossPojo.setSeventhAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 8) {
                                            rbiSossPojo.setEighthAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 9) {
                                            rbiSossPojo.setNineAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 10) {
                                            rbiSossPojo.setTenAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 11) {
                                            rbiSossPojo.setElevenAmount((bal * percentage) / 100);
                                        }
                                        if (bucketNo == 12) {
                                            rbiSossPojo.setTwelveAmount((bal * percentage) / 100);
                                        }
                                    }
                                    //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                } else {
                                    throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
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
//                        glPojoList = rbiReportRemote.getGLHeadAndBal(params);
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8)").getResultList();
                        if (glNameList.size() > 0) {
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
                            rbiSossPojo.setAmt(0d);
                            rbiSossPojo.setSecondAmount(0d);
                            rbiSossPojo.setThirdAmount(0d);
                            rbiSossPojo.setFourthAmount(0d);
                            rbiSossPojo.setFifthAmount(0d);
                            rbiSossPojo.setSixthAmount(0d);
                            rbiSossPojo.setSeventhAmount(0d);
                            rbiSossPojo.setEighthAmount(0d);
                            rbiSossPojo.setNineAmount(0d);
                            rbiSossPojo.setTenAmount(0d);
                            rbiSossPojo.setElevenAmount(0d);
                            rbiSossPojo.setTwelveAmount(0d);
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
                            for (int j = 0; j < glNameList.size(); j++) {
                                Vector vector = (Vector) glNameList.get(j);
                                GlHeadPojo glPojo = new GlHeadPojo();
                                rbiSossPojo = new RbiAlmPojo();
                                rbiSossPojo.setAmt(0d);
                                rbiSossPojo.setSecondAmount(0d);
                                rbiSossPojo.setThirdAmount(0d);
                                rbiSossPojo.setFourthAmount(0d);
                                rbiSossPojo.setFifthAmount(0d);
                                rbiSossPojo.setSixthAmount(0d);
                                rbiSossPojo.setSeventhAmount(0d);
                                rbiSossPojo.setEighthAmount(0d);
                                rbiSossPojo.setNineAmount(0d);
                                rbiSossPojo.setTenAmount(0d);
                                rbiSossPojo.setElevenAmount(0d);
                                rbiSossPojo.setTwelveAmount(0d);
//                                GlHeadPojo glHeadPo = glNameList.get(j);
                                if (countApplicable.equalsIgnoreCase("Y")) {
                                    noOfAc = (long) glNameList.size();
                                    rbiSossPojo.setAmt(noOfAc.doubleValue());
                                } else {
//                                    bal = Double.parseDouble(formatter.format(glHeadPo.getBalance().doubleValue())) / repOpt;
                                    GlHeadPojo newBalPojo = rbiMonthlyReportFacade.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        bal = Double.parseDouble("0");
                                    } else {
                                        bal = Double.parseDouble(newBalPojo.getBalance().toString()) / repOpt;
                                    }
                                    if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                        bal = bal;
                                    } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                        bal = bal * -1;
                                    } else if (classification.equalsIgnoreCase("A") && bal < 0) {
                                        bal = bal * -1;
                                    } else {
                                        bal = (fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : Math.abs(bal);
                                    }
//                                    rbiSossPojo.setAmt((fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase("")))?bal:bal.abs());
                                    List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND HEADS_OF_ACC_NO = " + fGNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                                    if (almAccClassList.isEmpty()) {
                                        almAccClassList = getAlmAccMaster(profileParameter, fGNo, dt);
                                    }
                                    if (!almAccClassList.isEmpty()) {
                                        for (int k = 0; k < almAccClassList.size(); k++) {
                                            Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                            String flow = almAcClassVect.get(0).toString();
                                            Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                            Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                            Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                            if (fSsGNo.equalsIgnoreCase("NDTL") && (crrAmt > 0)) {
                                                if (Math.abs(bal) > 0 && Math.abs(bal) >= crrAmt) {
                                                    rbiSossPojo.setAmt((crrAmt));
                                                    bal = bal - crrAmt;
                                                    crrAmt = 0;
                                                } else if (bucketNo == 1) {
                                                    rbiSossPojo.setAmt((bal * percentage) / 100);
                                                }
                                            } else {
                                                if (bucketNo == 1) {
                                                    rbiSossPojo.setAmt((bal * percentage) / 100);
                                                }
                                            }

                                            if (bucketNo == 2) {
                                                rbiSossPojo.setSecondAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 3) {
                                                rbiSossPojo.setThirdAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 4) {
                                                rbiSossPojo.setFourthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 5) {
                                                rbiSossPojo.setFifthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 6) {
                                                rbiSossPojo.setSixthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 7) {
                                                rbiSossPojo.setSeventhAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 8) {
                                                rbiSossPojo.setEighthAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 9) {
                                                rbiSossPojo.setNineAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 10) {
                                                rbiSossPojo.setTenAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 11) {
                                                rbiSossPojo.setElevenAmount((bal * percentage) / 100);
                                            }
                                            if (bucketNo == 12) {
                                                rbiSossPojo.setTwelveAmount((bal * percentage) / 100);
                                            }
                                        }
                                        //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                    } else {
                                        throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
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
                                    if (!bal.equals(0d)) {
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
                                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(j + 1)).concat(". ").concat(vector.get(1).toString()));

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
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(j + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        } else {
                                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(j + 1)).concat(". ").concat(vector.get(1).toString()));
                                                        }
                                                    }
                                                }
                                            }
                                            preZ = j + 1;
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
                                if (!bal.equals(0d)) {
                                    rbiPojoTable.add(rbiSossPojo);
                                }
                                preGNo = gNo;
                                preSGNo = sGNo;
                                preSsGNo = ssGNo;
                                preSssGNo = sssGNo;
                                preSsssGNo = ssssGNo;
                                preFormula = formula;

                            }
                        }
                    } else if (fSsGNo.equalsIgnoreCase("GOI") || fSsGNo.equalsIgnoreCase("INVEST")) {
                        rbiSossPojo = new RbiAlmPojo();
                        rbiSossPojo.setAmt(0d);
                        rbiSossPojo.setSecondAmount(0d);
                        rbiSossPojo.setThirdAmount(0d);
                        rbiSossPojo.setFourthAmount(0d);
                        rbiSossPojo.setFifthAmount(0d);
                        rbiSossPojo.setSixthAmount(0d);
                        rbiSossPojo.setSeventhAmount(0d);
                        rbiSossPojo.setEighthAmount(0d);
                        rbiSossPojo.setNineAmount(0d);
                        rbiSossPojo.setTenAmount(0d);
                        rbiSossPojo.setElevenAmount(0d);
                        rbiSossPojo.setTwelveAmount(0d);
                        List resultSet = null;
//                        List<BucketWiseInvestmentPojo> reportList = new ArrayList<>();
                        List<GoiReportPojo> resultListG = new ArrayList<GoiReportPojo>();
                        List<DueDateDiaryPojo> reportList = new ArrayList<>();
                        if (fSsGNo.equalsIgnoreCase("INVEST")) {
                            List<Object[]> dataList = invstRemote.getDueDateReportActive1(ymd.parse(CbsUtil.dateAdd(dt, 1)));
                            if (!dataList.isEmpty()) {
                                for (int l = 0; l < dataList.size(); l++) {
                                    DueDateDiaryPojo pojo = new DueDateDiaryPojo();
                                    Object[] array = dataList.get(l);
                                    InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                                    InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                                    pojo.setSno(l);
                                    pojo.setCtrlNo(dateEntity.getCtrlNo());
                                    pojo.setPurDt(dmy.format(dateEntity.getPurchaseDt()));
                                    pojo.setMatDt(dmy.format(dateEntity.getMatDt()));
                                    pojo.setFdrNo(detailEntity.getFdrNo());
                                    pojo.setFaceValue(formatter.format(detailEntity.getFaceValue()));
                                    pojo.setBookValue(formatter.format(detailEntity.getBookValue()));
                                    pojo.setRoi(detailEntity.getRoi());
                                    pojo.setSellarName(detailEntity.getSellerName());
                                    pojo.setIntOpt(detailEntity.getIntOpt());
                                    pojo.setBranch(detailEntity.getBranch());
                                    pojo.setTotalRec(Double.toString(invstRemote.getReceviableAmt(Integer.parseInt(dateEntity.getCtrlNo().toString()), dt)));
                                    pojo.setOldCtrlNo(0);
                                    pojo.setCloseDt(dmy.format(new Date()));
                                    pojo.setPrd(dateEntity.getDays().toString() + "D " + dateEntity.getMonths().toString() + "M " + dateEntity.getYears().toString() + "Y");
                                    reportList.add(pojo);
                                }
                            }
                        } else if (fSsGNo.equalsIgnoreCase("GOI")) {
                            SimpleDateFormat ymdd = new SimpleDateFormat("yyyy-MM-dd");
                            resultListG = new ArrayList<GoiReportPojo>();
//                            List<InvestmentMaster> resultListGOI = invstRemote.getIndivisualReportTypeAllSellarNameGoi("ALL", ymd.parse("19000101"), ymd.parse(dt), "P");
                            List<InvestmentMaster> resultListGOI = invstRemote.getAllReportTypeAllSellarName(ymd.parse("19000101"), ymd.parse(dt), "TERM DEPOSIT", "P");
                            if (!resultListGOI.isEmpty()) {
                                for (InvestmentMaster entity : resultListGOI) {
                                    GoiReportPojo pojo = new GoiReportPojo();
                                    long daydiff = 0l;

                                    pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                                    pojo.setSecDtl(entity.getSecdesc());
                                    pojo.setSellarName(entity.getSellername());
                                    pojo.setSettleDt(ymd.format(entity.getSettledt()));
                                    pojo.setMatDt(ymd.format(entity.getMatdt()));
                                    pojo.setFaceValue(entity.getFacevalue());
                                    double amortamt = invstRemote.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), dt);
                                    pojo.setBookvalue(entity.getBookvalue() - amortamt);
                                    pojo.setYtm(entity.getYtm());
                                    String marked = invstRemote.getMarkByControlNo(entity.getInvestmentMasterPK().getControlno(), entity.getInvestmentMasterPK().getSectype(), dt);
                                    if (marked.equalsIgnoreCase("")) {
                                        marked = entity.getMarking();
                                    }
                                    if (marked.equalsIgnoreCase("HM")) {
                                        pojo.setMarking("1");
                                    } else if (marked.equalsIgnoreCase("HT")) {
                                        pojo.setMarking("2");
                                    } else if (marked.equalsIgnoreCase("AF")) {
                                        pojo.setMarking("3");
                                    } else {
                                        pojo.setMarking("4");
                                    }
                                    pojo.setRecInt(invstRemote.getReceGoiAmt(entity.getInvestmentMasterPK().getControlno(), dt));
                                    List reciDate = invstRemote.getRecivableDate(entity.getInvestmentMasterPK().getControlno());
                                    Vector vec = (Vector) reciDate.get(0);
                                    String intDate1 = vec.get(0).toString().substring(4, 10);
                                    String intDate2 = vec.get(1).toString().substring(4, 10);
                                    String veryyear = dt.substring(0, 4);
                                    Date recivableDate1 = ymdd.parse(veryyear + intDate1);
                                    Date recivableDate2 = ymdd.parse(veryyear + intDate2);
                                    String dayDiff1 = String.valueOf(CbsUtil.dayDiff(ymd.parse(dt), recivableDate1));
                                    String DaysDate1 = "";
                                    if (dayDiff1.contains("-")) {
                                        DaysDate1 = dayDiff1.substring(dayDiff1.indexOf("-") + 1);
                                        dayDiff1 = DaysDate1;
                                    }
                                    String dayDiff2 = String.valueOf(CbsUtil.dayDiff(ymd.parse(dt), recivableDate2));
                                    String DaysDate2 = "";
                                    if (dayDiff2.contains("-")) {
                                        DaysDate2 = dayDiff2.substring(dayDiff1.indexOf("-") + 1);
                                        dayDiff2 = DaysDate2;
                                    }
                                    if (recivableDate1.after(ymd.parse(dt)) || recivableDate2.after(ymd.parse(dt))) {
                                        if (recivableDate1.after(ymd.parse(dt)) && recivableDate2.after(ymd.parse(dt))) {
                                            if (Long.parseLong(dayDiff1) < Long.parseLong(dayDiff2)) {
                                                pojo.setRecievableDt(ymd.format(recivableDate1));
                                            } else {
                                                pojo.setRecievableDt(ymd.format(recivableDate2));
                                            }
                                        } else if (recivableDate1.after(ymd.parse(dt))) {
                                            pojo.setRecievableDt(ymd.format(recivableDate1));
                                        } else if (recivableDate2.after(ymd.parse(dt))) {
                                            pojo.setRecievableDt(ymd.format(recivableDate2));
                                        }
                                    } else {
                                        Date minDate = recivableDate1.before(recivableDate2) ? recivableDate1 : recivableDate2;
                                        Long yearExtend = Long.parseLong(ymd.format(minDate).substring(0, 4)) + 1;
                                        String extendDate = yearExtend.toString() + ymd.format(minDate).substring(4, 8);
                                        pojo.setRecievableDt(extendDate);
                                    }
                                    resultListG.add(pojo);
                                }
                            }
                        }
                        if (reportList != null && !reportList.isEmpty()) {
//                            List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, "
//                                    + "BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,RECORD_MOD_CNT  FROM "
//                                    + "cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND RECORD_STATUS = 'A' "
//                                    + "ORDER BY BUCKET_NO").getResultList();
                            List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
                            double amt1 = 0d, amt2 = 0d, amt3 = 0d, amt4 = 0d, amt5 = 0d, amt6 = 0d, amt7 = 0d, amt8 = 0d, amt9 = 0d, amt10 = 0d, amt11 = 0d, amt12 = 0d;
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
                                    for (int l = 0; l < reportList.size(); l++) {
                                        DueDateDiaryPojo pojo = reportList.get(l);
                                        long dayDiff = CbsUtil.dayDiff(ymd.parse(dt), dmy.parse(pojo.getMatDt()));
                                        dayDiff = (dayDiff <= 0 ? 1 : dayDiff);
                                        if (dayDiff >= bktStartDay && dayDiff <= bktEndDay) {
//                                            System.out.println("Day Diff>>" + dayDiff+"; Ctrl No>>"+pojo.getCtrlNo()+"; Date:"+pojo.getMatDt()+"; Amt:"+pojo.getFaceValue()+"; Bkt:"+bktStartDay+";"+bktEndDay);
                                            if (bktNoMast == 1) {
                                                amt1 = amt1 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setAmt(amt1 / repOpt);
                                            } else if (bktNoMast == 2) {
                                                amt2 = amt2 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setSecondAmount(amt2 / repOpt);
                                            } else if (bktNoMast == 3) {
                                                amt3 = amt3 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setThirdAmount(amt3 / repOpt);
                                            } else if (bktNoMast == 4) {
                                                amt4 = amt4 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setFourthAmount(amt4 / repOpt);
                                            } else if (bktNoMast == 5) {
                                                amt5 = amt5 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setFifthAmount(amt5 / repOpt);
                                            } else if (bktNoMast == 6) {
                                                amt6 = amt6 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setSixthAmount(amt6 / repOpt);
                                            } else if (bktNoMast == 7) {
                                                amt7 = amt7 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setSeventhAmount(amt7 / repOpt);
                                            } else if (bktNoMast == 8) {
                                                amt8 = amt8 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setEighthAmount(amt8 / repOpt);
                                            } else if (bktNoMast == 9) {
                                                amt9 = amt9 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setNineAmount(amt9 / repOpt);
                                            } else if (bktNoMast == 10) {
                                                amt10 = amt10 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setTenAmount(amt10 / repOpt);
                                            } else if (bktNoMast == 11) {
                                                amt11 = amt11 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setElevenAmount(amt11 / repOpt);
                                            } else if (bktNoMast == 12) {
                                                amt12 = amt12 + Double.parseDouble(pojo.getFaceValue());
                                                rbiSossPojo.setTwelveAmount(amt12 / repOpt);
                                            }
                                        }
                                    }
//                                    if (bktNoMast == 1) {
//                                        rbiSossPojo.setAmt(reportList.get(0).getBkt1IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 2) {
//                                        rbiSossPojo.setSecondAmount(reportList.get(0).getBkt2IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 3) {
//                                        rbiSossPojo.setThirdAmount(reportList.get(0).getBkt3IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 4) {
//                                        rbiSossPojo.setFourthAmount(reportList.get(0).getBkt4IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 5) {
//                                        rbiSossPojo.setFifthAmount(reportList.get(0).getBkt5IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 6) {
//                                        rbiSossPojo.setSixthAmount(reportList.get(0).getBkt6IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 7) {
//                                        rbiSossPojo.setSeventhAmount(reportList.get(0).getBkt7IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 8) {
//                                        rbiSossPojo.setEighthAmount(reportList.get(0).getBkt8IntAmt().doubleValue() / repOpt);
//                                    }
                                }
                            } else {
                                throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                            }
                        } else if (!resultListG.isEmpty()) {
                            List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
                            double amt1 = 0d, amt2 = 0d, amt3 = 0d, amt4 = 0d, amt5 = 0d, amt6 = 0d, amt7 = 0d, amt8 = 0d, amt9 = 0d, amt10 = 0d, amt11 = 0d, amt12 = 0d;
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
                                    for (int l = 0; l < resultListG.size(); l++) {
                                        GoiReportPojo pojo = resultListG.get(l);
                                        long dayDiff = CbsUtil.dayDiff(ymd.parse(dt), ymd.parse(pojo.getMatDt()));
                                        dayDiff = (dayDiff <= 0 ? 1 : dayDiff);
                                        if (dayDiff >= bktStartDay && dayDiff <= bktEndDay) {
                                            if (bktNoMast == 1) {
                                                amt1 = amt1 + pojo.getBookvalue();
                                                rbiSossPojo.setAmt(amt1 / repOpt);
                                            } else if (bktNoMast == 2) {
                                                amt2 = amt2 + pojo.getBookvalue();
                                                rbiSossPojo.setSecondAmount(amt2 / repOpt);
                                            } else if (bktNoMast == 3) {
                                                amt3 = amt3 + pojo.getBookvalue();
                                                rbiSossPojo.setThirdAmount(amt3 / repOpt);
                                            } else if (bktNoMast == 4) {
                                                amt4 = amt4 + pojo.getBookvalue();
                                                rbiSossPojo.setFourthAmount(amt4 / repOpt);
                                            } else if (bktNoMast == 5) {
                                                amt5 = amt5 + pojo.getBookvalue();
                                                rbiSossPojo.setFifthAmount(amt5 / repOpt);
                                            } else if (bktNoMast == 6) {
                                                amt6 = amt6 + pojo.getBookvalue();
                                                rbiSossPojo.setSixthAmount(amt6 / repOpt);
                                            } else if (bktNoMast == 7) {
                                                amt7 = amt7 + pojo.getBookvalue();
                                                rbiSossPojo.setSeventhAmount(amt7 / repOpt);
                                            } else if (bktNoMast == 8) {
                                                amt8 = amt8 + pojo.getBookvalue();
                                                rbiSossPojo.setEighthAmount(amt8 / repOpt);
                                            } else if (bktNoMast == 9) {
                                                amt9 = amt9 + pojo.getBookvalue();
                                                rbiSossPojo.setNineAmount(amt9 / repOpt);
                                            } else if (bktNoMast == 10) {
                                                amt10 = amt10 + pojo.getBookvalue();
                                                rbiSossPojo.setTenAmount(amt10 / repOpt);
                                            } else if (bktNoMast == 11) {
                                                amt11 = amt11 + pojo.getBookvalue();
                                                rbiSossPojo.setElevenAmount(amt11 / repOpt);
                                            } else if (bktNoMast == 12) {
                                                amt12 = amt12 + pojo.getBookvalue();
                                                rbiSossPojo.setTwelveAmount(amt12 / repOpt);
                                            }
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
                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                        rbiSossPojo.setRangeFrom(rangeFrom);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        if (summaryOpt.equalsIgnoreCase("N")) {
                            if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("GOI") ? "Investment-GOI" : "INVESTMENT-Banker's FDR"));

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
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("GOI") ? "Investment-GOI" : "INVESTMENT-Banker's FDR"));
                                            } else {
                                                rbiSossPojo.setSsssGNo(preSsssGNo);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("GOI") ? "Investment-GOI" : "INVESTMENT-Banker's FDR"));
                                            }
                                        }
                                    }
                                }
                                preZ = preZ + 1;
                            } else {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(0 + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("GOI") ? "Investment-GOI" : "INVESTMENT-Banker's FDR"));

                                if (sGNo == 0) {
                                    rbiSossPojo.setsGNo(0 + 1);
                                } else {
                                    rbiSossPojo.setsGNo(sGNo);
                                    if (ssGNo == 0 && sGNo != 0) {
                                        rbiSossPojo.setSsGNo(0 + 1);
                                    } else {
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                            rbiSossPojo.setSssGNo(0 + 1);
                                        } else {
                                            rbiSossPojo.setSssGNo(sssGNo);
                                            if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSsssGNo(0 + 1);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(0 + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("GOI") ? "Investment-GOI" : "INVESTMENT-Banker's FDR"));
                                            } else {
                                                rbiSossPojo.setSsssGNo(ssssGNo);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(0 + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("GOI") ? "Investment-GOI" : "INVESTMENT-Banker's FDR"));
                                            }
                                        }
                                    }
                                }
                                preZ = 0 + 1;
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

                    } else if (fSsGNo.equalsIgnoreCase("INTREC") || fSsGNo.equalsIgnoreCase("INTRECG")) {
                        rbiSossPojo = new RbiAlmPojo();
                        rbiSossPojo.setAmt(0d);
                        rbiSossPojo.setSecondAmount(0d);
                        rbiSossPojo.setThirdAmount(0d);
                        rbiSossPojo.setFourthAmount(0d);
                        rbiSossPojo.setFifthAmount(0d);
                        rbiSossPojo.setSixthAmount(0d);
                        rbiSossPojo.setSeventhAmount(0d);
                        rbiSossPojo.setEighthAmount(0d);
                        rbiSossPojo.setNineAmount(0d);
                        rbiSossPojo.setTenAmount(0d);
                        rbiSossPojo.setElevenAmount(0d);
                        rbiSossPojo.setTwelveAmount(0d);
                        List resultSet = null;
//                        List<BucketWiseInvestmentPojo> reportList = new ArrayList<>();
                        List<GoiReportPojo> resultListG = new ArrayList<GoiReportPojo>();
                        List<DueDateDiaryPojo> reportList = new ArrayList<>();
                        if (fSsGNo.equalsIgnoreCase("INTREC")) {
                            List<Object[]> dataList = invstRemote.getDueDateReportActive1(ymd.parse(CbsUtil.dateAdd(dt, 1)));
                            if (!dataList.isEmpty()) {
                                for (int l = 0; l < dataList.size(); l++) {
                                    DueDateDiaryPojo pojo = new DueDateDiaryPojo();
                                    Object[] array = dataList.get(l);
                                    InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                                    InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                                    pojo.setSno(l);
                                    pojo.setCtrlNo(dateEntity.getCtrlNo());
                                    pojo.setPurDt(dmy.format(dateEntity.getPurchaseDt()));
                                    pojo.setMatDt(dmy.format(dateEntity.getMatDt()));
                                    pojo.setFdrNo(detailEntity.getFdrNo());
                                    pojo.setFaceValue(formatter.format(detailEntity.getFaceValue()));
                                    pojo.setBookValue(formatter.format(detailEntity.getBookValue()));
                                    pojo.setRoi(detailEntity.getRoi());
                                    pojo.setSellarName(detailEntity.getSellerName());
                                    pojo.setIntOpt(detailEntity.getIntOpt());
                                    pojo.setBranch(detailEntity.getBranch());
                                    pojo.setTotalRec(Double.toString(invstRemote.getReceviableAmt(Integer.parseInt(dateEntity.getCtrlNo().toString()), dt)));
                                    pojo.setOldCtrlNo(0);
                                    pojo.setCloseDt(dmy.format(new Date()));
                                    pojo.setPrd(dateEntity.getDays().toString() + "D " + dateEntity.getMonths().toString() + "M " + dateEntity.getYears().toString() + "Y");
                                    reportList.add(pojo);
                                }
                            }
                        } else {
                            SimpleDateFormat ymdd = new SimpleDateFormat("yyyy-MM-dd");
                            resultListG = new ArrayList<GoiReportPojo>();
//                            List<InvestmentMaster> resultListGOI = invstRemote.getIndivisualReportTypeAllSellarNameGoi("ALL", ymd.parse("19000101"), ymd.parse(dt), "All");
                            List<InvestmentMaster> resultListGOI = invstRemote.getAllReportTypeAllSellarName(ymd.parse("19000101"), ymd.parse(dt), "TERM DEPOSIT", "All");
                            if (!resultListGOI.isEmpty()) {
                                for (InvestmentMaster entity : resultListGOI) {
                                    GoiReportPojo pojo = new GoiReportPojo();
                                    long daydiff = 0l;

                                    pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                                    pojo.setSecDtl(entity.getSecdesc());
                                    pojo.setSellarName(entity.getSellername());
                                    pojo.setSettleDt(ymd.format(entity.getSettledt()));
                                    pojo.setMatDt(ymd.format(entity.getMatdt()));
                                    pojo.setFaceValue(entity.getFacevalue());
                                    double amortamt = invstRemote.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), dt);
                                    pojo.setBookvalue(entity.getBookvalue() - amortamt);
                                    pojo.setYtm(entity.getYtm());
                                    String marked = invstRemote.getMarkByControlNo(entity.getInvestmentMasterPK().getControlno(), entity.getInvestmentMasterPK().getSectype(), dt);
                                    if (marked.equalsIgnoreCase("")) {
                                        marked = entity.getMarking();
                                    }
                                    if (marked.equalsIgnoreCase("HM")) {
                                        pojo.setMarking("1");
                                    } else if (marked.equalsIgnoreCase("HT")) {
                                        pojo.setMarking("2");
                                    } else if (marked.equalsIgnoreCase("AF")) {
                                        pojo.setMarking("3");
                                    } else {
                                        pojo.setMarking("4");
                                    }
                                    pojo.setRecInt(invstRemote.getReceGoiAmt(entity.getInvestmentMasterPK().getControlno(), dt));
                                    List reciDate = invstRemote.getRecivableDate(entity.getInvestmentMasterPK().getControlno());
                                    Vector vec = (Vector) reciDate.get(0);
                                    String intDate1 = vec.get(0).toString().substring(4, 10);
                                    String intDate2 = vec.get(1).toString().substring(4, 10);
                                    String veryyear = dt.substring(0, 4);
                                    Date recivableDate1 = ymdd.parse(veryyear + intDate1);
                                    Date recivableDate2 = ymdd.parse(veryyear + intDate2);
                                    String dayDiff1 = String.valueOf(CbsUtil.dayDiff(ymd.parse(dt), recivableDate1));
                                    String DaysDate1 = "";
                                    if (dayDiff1.contains("-")) {
                                        DaysDate1 = dayDiff1.substring(dayDiff1.indexOf("-") + 1);
                                        dayDiff1 = DaysDate1;
                                    }
                                    String dayDiff2 = String.valueOf(CbsUtil.dayDiff(ymd.parse(dt), recivableDate2));
                                    String DaysDate2 = "";
                                    if (dayDiff2.contains("-")) {
                                        DaysDate2 = dayDiff2.substring(dayDiff1.indexOf("-") + 1);
                                        dayDiff2 = DaysDate2;
                                    }
                                    if (recivableDate1.after(ymd.parse(dt)) || recivableDate2.after(ymd.parse(dt))) {
                                        if (recivableDate1.after(ymd.parse(dt)) && recivableDate2.after(ymd.parse(dt))) {
                                            if (Long.parseLong(dayDiff1) < Long.parseLong(dayDiff2)) {
                                                pojo.setRecievableDt(ymd.format(recivableDate1));
                                            } else {
                                                pojo.setRecievableDt(ymd.format(recivableDate2));
                                            }
                                        } else if (recivableDate1.after(ymd.parse(dt))) {
                                            pojo.setRecievableDt(ymd.format(recivableDate1));
                                        } else if (recivableDate2.after(ymd.parse(dt))) {
                                            pojo.setRecievableDt(ymd.format(recivableDate2));
                                        }
                                    } else {
                                        Date minDate = recivableDate1.before(recivableDate2) ? recivableDate1 : recivableDate2;
                                        Long yearExtend = Long.parseLong(ymd.format(minDate).substring(0, 4)) + 1;
                                        String extendDate = yearExtend.toString() + ymd.format(minDate).substring(4, 8);
                                        pojo.setRecievableDt(extendDate);
                                    }
                                    resultListG.add(pojo);
                                }
                            }
                        }
                        if (reportList != null && !reportList.isEmpty()) {
//                            List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, "
//                                    + "BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,RECORD_MOD_CNT  FROM "
//                                    + "cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND RECORD_STATUS = 'A' "
//                                    + "ORDER BY BUCKET_NO").getResultList();
                            List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
                            double amt1 = 0d, amt2 = 0d, amt3 = 0d, amt4 = 0d, amt5 = 0d, amt6 = 0d, amt7 = 0d, amt8 = 0d, amt9 = 0d, amt10 = 0d, amt11 = 0d, amt12 = 0d;
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
                                    for (int l = 0; l < reportList.size(); l++) {
                                        DueDateDiaryPojo pojo = reportList.get(l);
                                        long dayDiff = CbsUtil.dayDiff(ymd.parse(dt), dmy.parse(pojo.getMatDt()));
                                        dayDiff = (dayDiff <= 0 ? 1 : dayDiff);
                                        if (dayDiff >= bktStartDay && dayDiff <= bktEndDay) {
                                            if (bktNoMast == 1) {
                                                amt1 = amt1 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setAmt(amt1 / repOpt);
                                            } else if (bktNoMast == 2) {
                                                amt2 = amt2 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setSecondAmount(amt2 / repOpt);
                                            } else if (bktNoMast == 3) {
                                                amt3 = amt3 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setThirdAmount(amt3 / repOpt);
                                            } else if (bktNoMast == 4) {
                                                amt4 = amt4 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setFourthAmount(amt4 / repOpt);
                                            } else if (bktNoMast == 5) {
                                                amt5 = amt5 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setFifthAmount(amt5 / repOpt);
                                            } else if (bktNoMast == 6) {
                                                amt6 = amt6 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setSixthAmount(amt6 / repOpt);
                                            } else if (bktNoMast == 7) {
                                                amt7 = amt7 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setSeventhAmount(amt7 / repOpt);
                                            } else if (bktNoMast == 8) {
                                                amt8 = amt8 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setEighthAmount(amt8 / repOpt);
                                            } else if (bktNoMast == 9) {
                                                amt9 = amt9 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setNineAmount(amt9 / repOpt);
                                            } else if (bktNoMast == 10) {
                                                amt10 = amt10 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setTenAmount(amt10 / repOpt);
                                            } else if (bktNoMast == 11) {
                                                amt11 = amt11 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setElevenAmount(amt11 / repOpt);
                                            } else if (bktNoMast == 12) {
                                                amt12 = amt12 + Double.parseDouble(pojo.getTotalRec());
                                                rbiSossPojo.setTwelveAmount(amt12 / repOpt);
                                            }
                                        }
                                    }
//                                    if (bktNoMast == 1) {
//                                        rbiSossPojo.setAmt(reportList.get(0).getBkt1IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 2) {
//                                        rbiSossPojo.setSecondAmount(reportList.get(0).getBkt2IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 3) {
//                                        rbiSossPojo.setThirdAmount(reportList.get(0).getBkt3IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 4) {
//                                        rbiSossPojo.setFourthAmount(reportList.get(0).getBkt4IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 5) {
//                                        rbiSossPojo.setFifthAmount(reportList.get(0).getBkt5IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 6) {
//                                        rbiSossPojo.setSixthAmount(reportList.get(0).getBkt6IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 7) {
//                                        rbiSossPojo.setSeventhAmount(reportList.get(0).getBkt7IntAmt().doubleValue() / repOpt);
//                                    } else if (bktNoMast == 8) {
//                                        rbiSossPojo.setEighthAmount(reportList.get(0).getBkt8IntAmt().doubleValue() / repOpt);
//                                    }
                                }
                            } else {
                                throw new ApplicationException("Data does not exist in cbs_alm_bucket_master");
                            }
                        } else if (!resultListG.isEmpty()) {
                            List almBktMastList = getAlmBucketMaster(profileParameter, dt, "ALL");
                            double amt1 = 0d, amt2 = 0d, amt3 = 0d, amt4 = 0d, amt5 = 0d, amt6 = 0d, amt7 = 0d, amt8 = 0d, amt9 = 0d, amt10 = 0d, amt11 = 0d, amt12 = 0d;
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
                                    for (int l = 0; l < resultListG.size(); l++) {
                                        GoiReportPojo pojo = resultListG.get(l);
                                        long dayDiff = CbsUtil.dayDiff(ymd.parse(dt), ymd.parse(pojo.getMatDt()));
                                        dayDiff = (dayDiff <= 0 ? 1 : dayDiff);
                                        if (dayDiff >= bktStartDay && dayDiff <= bktEndDay) {
                                            if (bktNoMast == 1) {
                                                amt1 = amt1 + pojo.getRecInt();
                                                rbiSossPojo.setAmt(amt1 / repOpt);
                                            } else if (bktNoMast == 2) {
                                                amt2 = amt2 + pojo.getRecInt();
                                                rbiSossPojo.setSecondAmount(amt2 / repOpt);
                                            } else if (bktNoMast == 3) {
                                                amt3 = amt3 + pojo.getRecInt();
                                                rbiSossPojo.setThirdAmount(amt3 / repOpt);
                                            } else if (bktNoMast == 4) {
                                                amt4 = amt4 + pojo.getRecInt();
                                                rbiSossPojo.setFourthAmount(amt4 / repOpt);
                                            } else if (bktNoMast == 5) {
                                                amt5 = amt5 + pojo.getRecInt();
                                                rbiSossPojo.setFifthAmount(amt5 / repOpt);
                                            } else if (bktNoMast == 6) {
                                                amt6 = amt6 + pojo.getRecInt();
                                                rbiSossPojo.setSixthAmount(amt6 / repOpt);
                                            } else if (bktNoMast == 7) {
                                                amt7 = amt7 + pojo.getRecInt();
                                                rbiSossPojo.setSeventhAmount(amt7 / repOpt);
                                            } else if (bktNoMast == 8) {
                                                amt8 = amt8 + pojo.getRecInt();
                                                rbiSossPojo.setEighthAmount(amt8 / repOpt);
                                            } else if (bktNoMast == 9) {
                                                amt9 = amt9 + pojo.getRecInt();
                                                rbiSossPojo.setNineAmount(amt9 / repOpt);
                                            } else if (bktNoMast == 10) {
                                                amt10 = amt10 + pojo.getRecInt();
                                                rbiSossPojo.setTenAmount(amt10 / repOpt);
                                            } else if (bktNoMast == 11) {
                                                amt11 = amt11 + pojo.getRecInt();
                                                rbiSossPojo.setElevenAmount(amt11 / repOpt);
                                            } else if (bktNoMast == 12) {
                                                amt12 = amt12 + pojo.getRecInt();
                                                rbiSossPojo.setTwelveAmount(amt12 / repOpt);
                                            }
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
                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                        rbiSossPojo.setRangeFrom(rangeFrom);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        if (summaryOpt.equalsIgnoreCase("N")) {
                            if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("INTRECG") ? "Investment-GOI Inerest" : "INVESTMENT-Banker's FDR Inerest"));

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
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("INTRECG") ? "Investment-GOI Inerest" : "INVESTMENT-Banker's FDR Inerest"));
                                            } else {
                                                rbiSossPojo.setSsssGNo(preSsssGNo);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("INTRECG") ? "Investment-GOI Inerest" : "INVESTMENT-Banker's FDR"));
                                            }
                                        }
                                    }
                                }
                                preZ = preZ + 1;
                            } else {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(0 + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("INTRECG") ? "Investment-GOI Inerest" : "INVESTMENT-Banker's FDR Inerest"));

                                if (sGNo == 0) {
                                    rbiSossPojo.setsGNo(0 + 1);
                                } else {
                                    rbiSossPojo.setsGNo(sGNo);
                                    if (ssGNo == 0 && sGNo != 0) {
                                        rbiSossPojo.setSsGNo(0 + 1);
                                    } else {
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                            rbiSossPojo.setSssGNo(0 + 1);
                                        } else {
                                            rbiSossPojo.setSssGNo(sssGNo);
                                            if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSsssGNo(0 + 1);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(0 + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("INTRECG") ? "Investment-GOI Inerest" : "INVESTMENT-Banker's FDR Inerest"));
                                            } else {
                                                rbiSossPojo.setSsssGNo(ssssGNo);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(0 + 1)).concat(". ").concat(fSsGNo.equalsIgnoreCase("INTRECG") ? "Investment-GOI-Inerest" : "INVESTMENT-Banker's FDR Inerest"));
                                            }
                                        }
                                    }
                                }
                                preZ = 0 + 1;
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
                    } else if (fSsGNo.equalsIgnoreCase("UNAVAILP")) {
                        /*For Un Availed portion of the CC and OD*/
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
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
                        rbiSossPojo.setAmt(0d);
                        rbiSossPojo.setSecondAmount(0d);
                        rbiSossPojo.setThirdAmount(0d);
                        rbiSossPojo.setFourthAmount(0d);
                        rbiSossPojo.setFifthAmount(0d);
                        rbiSossPojo.setSixthAmount(0d);
                        rbiSossPojo.setSeventhAmount(0d);
                        rbiSossPojo.setEighthAmount(0d);
                        rbiSossPojo.setNineAmount(0d);
                        rbiSossPojo.setTenAmount(0d);
                        rbiSossPojo.setElevenAmount(0d);
                        rbiSossPojo.setTwelveAmount(0d);
                        rbiSossPojo = new RbiAlmPojo();
                        bal = Double.parseDouble("0");
                        List<LoanMisCellaniousPojo> misList = loanRemote.cbsLoanMisReport(orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "CA", "0", dt, "A", 0.0, 99999999999999.99, "O",
                                "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "S", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                        if (!misList.isEmpty()) {
                            BigDecimal totalLoan = new BigDecimal("0"), odLimit = new BigDecimal("0");
                            for (int t = 0; t < misList.size(); t++) {
                                if (misList.get(t).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    totalLoan = totalLoan.add(misList.get(t).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : misList.get(t).getOutstanding());
                                    odLimit = odLimit.add(misList.get(t).getOdLimit());
                                }
                            }
                            bal = Double.parseDouble((odLimit.abs().subtract(totalLoan.abs())).toString()) / repOpt;
                        }
                        if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                            bal = bal;
                        } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                            bal = bal * -1;
                        } else if (classification.equalsIgnoreCase("A") && bal < 0) {
                            bal = bal * -1;
                        } else {
                            bal = (fSGNo.equalsIgnoreCase("ACT") && (classification.equalsIgnoreCase(""))) ? bal : Math.abs(bal);
                        }
                        List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND HEADS_OF_ACC_NO = " + fGNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                        if (almAccClassList.isEmpty()) {
                            almAccClassList = getAlmAccMaster(profileParameter, fGNo, dt);
                        }
                        if (!almAccClassList.isEmpty()) {
                            for (int k = 0; k < almAccClassList.size(); k++) {
                                Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                String flow = almAcClassVect.get(0).toString();
                                Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());
                                if (bucketNo == 1) {
                                    rbiSossPojo.setAmt((bal * percentage) / 100);
                                }
                                if (bucketNo == 2) {
                                    rbiSossPojo.setSecondAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 3) {
                                    rbiSossPojo.setThirdAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 4) {
                                    rbiSossPojo.setFourthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 5) {
                                    rbiSossPojo.setFifthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 6) {
                                    rbiSossPojo.setSixthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 7) {
                                    rbiSossPojo.setSeventhAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 8) {
                                    rbiSossPojo.setEighthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 9) {
                                    rbiSossPojo.setNineAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 10) {
                                    rbiSossPojo.setTenAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 11) {
                                    rbiSossPojo.setElevenAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 12) {
                                    rbiSossPojo.setTwelveAmount((bal * percentage) / 100);
                                }
                            }
                        } else {
                            throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
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
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat("Unavailed portion of Cash Credit / Overdraft"));
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
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat("Unavailed portion of Cash Credit / Overdraft"));
                                            } else {
                                                rbiSossPojo.setSsssGNo(preSsssGNo);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat("Unavailed portion of Cash Credit / Overdraft"));
                                            }
                                        }
                                    }
                                }
                                preZ = preZ + 1;
                            } else {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(0 + 1)).concat(". ").concat("Unavailed portion of Cash Credit / Overdraft"));

                                if (sGNo == 0) {
                                    rbiSossPojo.setsGNo(0 + 1);
                                } else {
                                    rbiSossPojo.setsGNo(sGNo);
                                    if (ssGNo == 0 && sGNo != 0) {
                                        rbiSossPojo.setSsGNo(0 + 1);
                                    } else {
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                            rbiSossPojo.setSssGNo(0 + 1);
                                        } else {
                                            rbiSossPojo.setSssGNo(sssGNo);
                                            if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSsssGNo(0 + 1);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(0 + 1)).concat(". ").concat("Unavailed portion of Cash Credit / Overdraft"));
                                            } else {
                                                rbiSossPojo.setSsssGNo(ssssGNo);
                                                rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(0 + 1)).concat(". ").concat("Unavailed portion of Cash Credit / Overdraft"));
                                            }
                                        }
                                    }
                                }
                                preZ = 0 + 1;
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
                        if (!bal.equals(0d)) {
                            rbiPojoTable.add(rbiSossPojo);
                        }
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
                double balPL = 0d;
                if (orgnCode.equalsIgnoreCase("0A") || orgnCode.equalsIgnoreCase("90")) {
                    balPL = glReport.IncomeExp(dt, "0A", "0A");
                } else {
                    balPL = glReport.IncomeExp(dt, orgnCode, orgnCode);
                }
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    RbiAlmPojo rbiSossPojo = rbiPojoTable.get(k);
                    if (!rbiSossPojo.getFormula().isEmpty()) {
                        Double bal = 0d;
                        if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {

                            if (rbiSossPojo.getClassification().equalsIgnoreCase("L") && balPL >= 0) {
                                bal = bal + Double.parseDouble(formatter.format(balPL)) / repOpt;
//                                rbiSossPojo.setAmt(Double.parseDouble(formatter.format(balPL))/repOpt);
                            } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL < 0) {
                                bal = bal + Double.parseDouble(formatter.format(balPL * -1)) / repOpt;

//                                rbiSossPojo.setAmt(Double.parseDouble(formatter.format(Math.abs(balPL/repOpt))));
                            }

                        } else {
                            bal = bal + rbiMonthlyReportFacade.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula());
//                            rbiSossPojo.setAmt(bal);
                        }
                        List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = '" + profileParameter + "' AND HEADS_OF_ACC_NO = " + rbiPojoTable.get(k).getfGNo() + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                        if (almAccClassList.isEmpty()) {
                            almAccClassList = getAlmAccMaster(profileParameter, rbiPojoTable.get(k).getfGNo(), dt);
                        }
                        if (!almAccClassList.isEmpty()) {
                            for (int l = 0; l < almAccClassList.size(); l++) {
                                Vector almAcClassVect = (Vector) almAccClassList.get(l);
                                String flow = almAcClassVect.get(0).toString();
                                Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                if (bucketNo == 1) {
                                    rbiSossPojo.setAmt((bal * percentage) / 100);
                                }
                                if (bucketNo == 2) {
                                    rbiSossPojo.setSecondAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 3) {
                                    rbiSossPojo.setThirdAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 4) {
                                    rbiSossPojo.setFourthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 5) {
                                    rbiSossPojo.setFifthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 6) {
                                    rbiSossPojo.setSixthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 7) {
                                    rbiSossPojo.setSeventhAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 8) {
                                    rbiSossPojo.setEighthAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 9) {
                                    rbiSossPojo.setNineAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 10) {
                                    rbiSossPojo.setTenAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 11) {
                                    rbiSossPojo.setElevenAmount((bal * percentage) / 100);
                                }
                                if (bucketNo == 12) {
                                    rbiSossPojo.setTwelveAmount((bal * percentage) / 100);
                                }
                            }
                            //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                        } else {
                            throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
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

    public List hoAlmInterestSensitivty(String date, String brCode, String repOpt) throws ApplicationException {
        List<AlmPojo> almPojoTable = new ArrayList<AlmPojo>();
        try {
            List almMasterList = em.createNativeQuery("SELECT GNO,S_GNO,SS_GNO,SSS_GNO,SSSS_GNO,DESCRIPTION,CLASSIFICATION,AC_NATURE,AC_TYPE,GL_HEAD_FROM,"
                    + " GL_HEAD_TO,F_GNO,FORMULA,count_applicable,npa_classification, f_s_gno FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = 'ALM-I' and '" + date + "' between EFF_FR_DT and EFF_TO_DT ORDER BY CLASSIFICATION DESC,cast(gno as unsigned),"
                    + " cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            AlmPojo almPojo = new AlmPojo();
            Double amt = 0d, repOption = 0d;
            long noOfDays = 0;
            if (repOpt.equalsIgnoreCase("T")) {
                repOption = 1000d;
            } else if (repOpt.equalsIgnoreCase("L")) {
                repOption = 100000d;
            } else if (repOpt.equalsIgnoreCase("C")) {
                repOption = 10000000d;
            } else if (repOpt.equalsIgnoreCase("R")) {
                repOption = 1d;
            }
            List almExist = em.createNativeQuery("select * from parameterinfo_report where reportname='ALM'").getResultList();
            if (!almMasterList.isEmpty()) {
                for (int i = 0; i < almMasterList.size(); i++) {
                    Vector almMasterVect = (Vector) almMasterList.get(i);

                    Integer gCode = Integer.parseInt(almMasterVect.get(0).toString());
                    Integer sgCode = Integer.parseInt(almMasterVect.get(1).toString());
                    Integer ssgCode = Integer.parseInt(almMasterVect.get(2).toString());
                    Integer sssgCode = Integer.parseInt(almMasterVect.get(3).toString());
                    Integer ssssgCode = Integer.parseInt(almMasterVect.get(4).toString());
                    String desc = almMasterVect.get(5).toString();
                    String headType = almMasterVect.get(6).toString();
                    String classType = "";
                    if (headType.equalsIgnoreCase("L")) {
                        classType = "O";
                    } else {
                        classType = "I";
                    }
                    String acNat = almMasterVect.get(7).toString();
                    String acType = almMasterVect.get(8).toString();
                    String codeFr = almMasterVect.get(9).toString();
                    String codeTo = almMasterVect.get(10).toString();
                    String headAcNo = almMasterVect.get(11).toString();
                    String fOpt = almMasterVect.get(12).toString();
                    String cntApp = almMasterVect.get(13).toString();
                    String npaClass = almMasterVect.get(14).toString();
                    String fSGNo = almMasterVect.get(15).toString();

                    double crTotal, drTotal, totalCrDr = 0;

                    /**
                     * ************************* after date (Liability)(Future
                     * FD/RD/Recovery (Loan) **********************
                     */
                    if (headAcNo.equalsIgnoreCase("NULL") || headAcNo.equalsIgnoreCase("")) {
                        if ((!acType.equalsIgnoreCase("")) || (!acNat.equalsIgnoreCase(""))) {
                            List acTypeList = new ArrayList();
                            if (!acNat.equalsIgnoreCase("")) {
                                acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE IN (SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE ='" + acNat + "')").getResultList();
                                if (acTypeList.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                }
                            } else {
                                acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                        + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                                if (acTypeList.isEmpty()) {
                                    throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                }
                            }

                            almPojo = new AlmPojo();
                            almPojo.setgCode(gCode);
                            almPojo.setSgCode(sgCode);
                            almPojo.setSsgCode(ssgCode);
                            almPojo.setSssgCode(sssgCode);
                            almPojo.setSsssgCode(ssssgCode);
                            almPojo.setDescription(desc);
                            almPojo.setHeadType(headType);
                            almPojo.setClassType(classType);
                            almPojo.setAcNature(acNat);
                            almPojo.setAcType(acType);
                            almPojo.setCodeFr(codeFr);
                            almPojo.setCodeTo(codeTo);
                            almPojo.setHeadOfAcc(headAcNo);
                            almPojo.setBuk1(0d);
                            almPojo.setBuk2(0d);
                            almPojo.setBuk3(0d);
                            almPojo.setBuk4(0d);
                            almPojo.setBuk5(0d);
                            almPojo.setBuk6(0d);
                            almPojo.setBuk7(0d);
                            almPojo.setBuk8(0d);
                            almPojo.setTotal(0d);
                            almPojo.setFormula(fOpt);
                            almPojo.setCountApplicable(cntApp);
                            almPojo.setNpaClassification(npaClass);
                            almPojoTable.add(almPojo);

                            String acCode, acctDesc, glHead;
                            totalCrDr = 0;

                            for (int k = 0; k < acTypeList.size(); k++) {
                                Vector vec = (Vector) acTypeList.get(k);
                                String actnature = vec.get(0).toString();
                                String actp = vec.get(1).toString();
                                acctDesc = vec.get(2).toString();
                                glHead = vec.get(3).toString();
                                crTotal = 0;
                                drTotal = 0;
                                List resultSet = null;
                                if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    if (brCode.equalsIgnoreCase("90")) {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + actp + "' AND t.dt<='" + date + "' "
                                                + " and t.auth='Y' AND t.CLOSEFLAG IS NULL  GROUP BY t.ACNO "
                                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT)<>0 ORDER BY t.ACNO").getResultList();
                                    } else {
                                        resultSet = em.createNativeQuery("SELECT t.ACNO,ifnull((SUM(t.CRAMT)-SUM(t.DRAMT)),0) FROM td_recon t, td_accountmaster a where "
                                                + " a.acno = t.acno and a.accttype ='" + actp + "' AND t.dt<='" + date + "' "
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
                                    almPojo.setgCode(gCode);
                                    almPojo.setSgCode(sgCode);
                                    almPojo.setHeadType(headType);
                                    almPojo.setClassType(classType);
                                    almPojo.setAcNature(actnature);
                                    almPojo.setAcType(actp);
                                    almPojo.setCodeFr(codeFr);
                                    almPojo.setCodeTo(codeTo);
                                    almPojo.setHeadOfAcc(headAcNo);
                                    almPojo.setBuk1(0d);
                                    almPojo.setBuk2(0d);
                                    almPojo.setBuk3(0d);
                                    almPojo.setBuk4(0d);
                                    almPojo.setBuk5(0d);
                                    almPojo.setBuk6(0d);
                                    almPojo.setBuk7(0d);
                                    almPojo.setBuk8(0d);
                                    almPojo.setTotal(0d);
                                    almPojo.setFormula(fOpt);
                                    almPojo.setCountApplicable(cntApp);
                                    almPojo.setNpaClassification(npaClass);

                                    if (ssgCode == 0) {
                                        almPojo.setSsgCode(k + 1);
                                    } else {
                                        almPojo.setSsgCode(ssgCode);
                                    }
                                    if (sssgCode == 0 && ssgCode != 0) {
                                        almPojo.setSssgCode(k + 1);
                                    } else {
                                        almPojo.setSssgCode(sssgCode);
                                    }
                                    if (ssssgCode == 0 && sssgCode != 0 && ssgCode != 0) {
                                        almPojo.setSsssgCode(k + 1);
                                        almPojo.setDescription(acctDesc);
                                    } else {
                                        almPojo.setSsssgCode(ssssgCode);
                                        almPojo.setDescription(String.valueOf("               ").concat(String.valueOf(k + 1)).concat(". ").concat(acctDesc));
                                    }

                                    List almBktMastList = em.createNativeQuery("SELECT VERSION_NO, RECORD_STATUS, BUCKET_NO, BUCKET_DESC, BUCKET_START_DAY, BUCKET_END_DAY,"
                                            + "RECORD_MOD_CNT  FROM cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = 'I' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO").getResultList();
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
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "'  AND "
                                                                + " A.ACNO=L.ACNO AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();

                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + actp + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
                                                    }
                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) <= '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<= '" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "'AND  SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) = '" + actp + "' AND OPENINGDT<= '" + date + "'  AND RDMATDATE <='" + dt2 + "')").getResultList();
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
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <='" + date + "' AND AUTH = 'Y' AND"
                                                                + " ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2)  = '" + actp + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,3,2) ='" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + actp + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
                                                                + " BETWEEN '" + dt1 + "' AND '" + dt2 + "')").getResultList();
                                                    }
                                                } else {
                                                    if ((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(DRAMT,0)) - SUM(ifnull(CRAMT,0)),0) FROM loan_recon WHERE "
                                                                + " SUBSTRING(ACNO,3,2) = '" + actp + "' "
                                                                + " AND DT <='" + date + "' AND AUTH = 'Y' AND ACNO IN (SELECT A.ACNO FROM accountmaster  A,cbs_loan_acc_mast_sec L "
                                                                + " WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<='" + date + "' AND "
                                                                + " A.ACNO=L.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' AND date_add(OPENINGDT,interval L.LOAN_PD_MONTH month) BETWEEN '" + dt1 + "' AND '" + dt2 + "') AND "
                                                                + " ACNO NOT IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                                + " WHERE A.ACCSTATUS IN('11','12','13') AND L.NPADT<='" + date + "')").getResultList();
                                                    } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                        getLoanValueList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)),0) FROM rdrecon WHERE AUTH='Y' AND "
                                                                + " SUBSTRING(ACNO,1,2) = '" + brCode + "' AND  SUBSTRING(ACNO,3,2) ='" + actp + "' AND DT <='" + date + "' AND ACNO IN "
                                                                + " (SELECT ACNO FROM accountmaster WHERE  SUBSTRING(ACNO,3,2) ='" + actp + "' AND OPENINGDT<='" + date + "' AND RDMATDATE "
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
                                        if (!actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (!actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                                if (!actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                    if (!resultSet.isEmpty()) {
                                                        almPojo = new AlmPojo();
                                                        almPojo.setgCode(gCode);
                                                        almPojo.setSgCode(sgCode);
                                                        almPojo.setSsgCode(ssgCode);
                                                        almPojo.setSssgCode(sssgCode);
                                                        almPojo.setSsssgCode(ssssgCode);
                                                        almPojo.setDescription(desc);
                                                        almPojo.setHeadType(headType);
                                                        almPojo.setClassType(classType);
                                                        almPojo.setAcNature(actnature);
                                                        almPojo.setAcType(actp);
                                                        almPojo.setCodeFr(codeFr);
                                                        almPojo.setCodeTo(codeTo);
                                                        almPojo.setHeadOfAcc(headAcNo);
                                                        almPojo.setBuk1(0d);
                                                        almPojo.setBuk2(0d);
                                                        almPojo.setBuk3(0d);
                                                        almPojo.setBuk4(0d);
                                                        almPojo.setBuk5(0d);
                                                        almPojo.setBuk6(0d);
                                                        almPojo.setBuk7(0d);
                                                        almPojo.setBuk8(0d);
                                                        almPojo.setTotal(0d);
                                                        almPojo.setFormula(fOpt);
                                                        almPojo.setCountApplicable(cntApp);
                                                        almPojo.setNpaClassification(npaClass);

                                                        for (int a = 0; a < resultSet.size(); a++) {
                                                            Vector resultSetVec = (Vector) resultSet.get(a);
                                                            String acno = resultSetVec.get(0).toString();
                                                            amt = Double.parseDouble(resultSetVec.get(1).toString()) / repOption;
                                                            List dtList = null;
                                                            if ((actnature.equalsIgnoreCase(CbsConstant.TD_AC)) || (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actnature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                                                dtList = em.createNativeQuery("SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + date + "') FROM td_vouchmst WHERE ACNO = '" + acno + "' /*and (cldt > '" + date + "' or cldt is null)*/ and td_madedt <='" + date + "' ").getResultList();
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
                                                                        + "cbs_alm_bucket_master  WHERE PROFILE_PARAMETER = 'I' AND RECORD_STATUS = 'A' "
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
                                }
                            }
                        } else {
                            almPojo = new AlmPojo();

                            almPojo.setgCode(gCode);
                            almPojo.setSgCode(sgCode);
                            almPojo.setSsgCode(ssgCode);
                            almPojo.setSssgCode(sssgCode);
                            almPojo.setSsssgCode(ssssgCode);
                            almPojo.setDescription(desc);
                            almPojo.setHeadType(headType);
                            almPojo.setClassType(classType);
                            almPojo.setAcNature(acNat);
                            almPojo.setAcType(acType);
                            almPojo.setCodeFr(codeFr);
                            almPojo.setCodeTo(codeTo);
                            almPojo.setHeadOfAcc(headAcNo);
                            almPojo.setBuk1(0d);
                            almPojo.setBuk2(0d);
                            almPojo.setBuk3(0d);
                            almPojo.setBuk4(0d);
                            almPojo.setBuk5(0d);
                            almPojo.setBuk6(0d);
                            almPojo.setBuk7(0d);
                            almPojo.setBuk8(0d);
                            almPojo.setTotal(0d);
                            almPojo.setFormula(fOpt);
                            almPojo.setCountApplicable(cntApp);
                            almPojo.setNpaClassification(npaClass);

                            almPojoTable.add(almPojo);
                        }
                    } else {
                        /**
                         * ************************* before date SB/CA
                         * ********************************************************
                         */
                        if ((acType != null) || (acNat != null)) {
                            List acTypeList = new ArrayList();
                            if ((!acType.equalsIgnoreCase("")) || (!acNat.equalsIgnoreCase(""))) {
                                if (!acNat.equalsIgnoreCase("")) {
                                    acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                            + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE IN (SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE ='" + acNat + "')").getResultList();
                                    if (acTypeList.isEmpty()) {
                                        throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                    }
                                } else {
                                    acTypeList = em.createNativeQuery("SELECT ACCTNATURE,ACCTCODE,ACCTDESC,GLHEAD FROM accounttypemaster "
                                            + "WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL AND ACCTCODE = '" + acType + "'").getResultList();
                                    if (acTypeList.isEmpty()) {
                                        throw new ApplicationException("Data does not exist in AccountTypeMaster");
                                    }
                                }

                                String acCode, acctDesc, glHead;
                                totalCrDr = 0;

                                almPojo = new AlmPojo();
                                almPojo.setgCode(gCode);
                                almPojo.setSgCode(sgCode);
                                almPojo.setSsgCode(ssgCode);
                                almPojo.setSssgCode(sssgCode);
                                almPojo.setSsssgCode(ssssgCode);
                                almPojo.setDescription(desc);
                                almPojo.setHeadType(headType);
                                almPojo.setClassType(classType);
                                almPojo.setAcNature(acNat);
                                almPojo.setAcType(acType);
                                almPojo.setCodeFr(codeFr);
                                almPojo.setCodeTo(codeTo);
                                almPojo.setHeadOfAcc(headAcNo);
                                almPojo.setBuk1(0d);
                                almPojo.setBuk2(0d);
                                almPojo.setBuk3(0d);
                                almPojo.setBuk4(0d);
                                almPojo.setBuk5(0d);
                                almPojo.setBuk6(0d);
                                almPojo.setBuk7(0d);
                                almPojo.setBuk8(0d);
                                almPojo.setTotal(0d);
                                almPojo.setFormula(fOpt);
                                almPojo.setCountApplicable(cntApp);
                                almPojo.setNpaClassification(npaClass);
                                almPojoTable.add(almPojo);

                                for (int p = 0; p < acTypeList.size(); p++) {
                                    Vector vec = (Vector) acTypeList.get(p);
                                    String actnature = vec.get(0).toString();
                                    String actp = vec.get(1).toString();
                                    acctDesc = vec.get(2).toString();
                                    glHead = vec.get(3).toString();
                                    crTotal = 0;
                                    drTotal = 0;
                                    Integer sign = null;
                                    String caQuery;
                                    if (classType.equalsIgnoreCase("O")) {
                                        sign = 1;
                                    } else if (classType.equalsIgnoreCase("I")) {
                                        sign = -1;
                                    }
                                    String actQuery = "  having sign(sum(cramt-dramt))  = " + sign;
                                    if (fSGNo.equalsIgnoreCase("ACT")) {
                                        actQuery = "";
                                    }
                                    if (almExist.isEmpty()) {
                                        caQuery = "";
                                    } else {
                                        caQuery = " AND acno in (select acno from ca_recon WHERE DT <= '" + date + "' group by acno " + actQuery + ") ";
                                    }
                                    if (brCode.equalsIgnoreCase("90")) {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + actp + "' AND r.dt<='" + date + "' and r.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + actp + "' AND d.dt<='" + date + "' and  d.auth='Y'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (npaClass.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' " + caQuery
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            } else {
                                                List resultSet = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from ca_recon where "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' " + caQuery
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.accstatus  in ('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            }
                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (npaClass.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0)  FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y' AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A  WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "') AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            crTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                    } else {
                                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from recon r, accountmaster a "
                                                    + "where a.acno = r.acno and a.accttype ='" + actp + "' AND r.dt<='" + date + "' and"
                                                    + " r.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                            List resultSet = em.createNativeQuery("select ifnull(sum(d.cramt),0),ifnull(sum(d.dramt),0) from ddstransaction d, accountmaster a "
                                                    + " where a.acno = d.acno and a.accttype ='" + actp + "' AND d.dt<='" + date + "' and"
                                                    + " d.auth='Y' AND a.curbrcode='" + brCode + "'").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                        if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            if (npaClass.equalsIgnoreCase("N")) {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') " + caQuery
                                                        + " AND acno in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            } else {
                                                List resultSet = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0) FROM ca_recon WHERE "
                                                        + " dt<='" + date + "' AND SUBSTRING(acno,3,2) ='" + actp + "' AND auth = 'Y' "
                                                        + " AND acno in (select acno from accountmaster where curbrcode= '" + brCode + "') " + caQuery
                                                        + " AND acno NOT in (select A.acno from accountmaster A left join loan_appparameter L "
                                                        + " on A.acno=L.acno where A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND A.accstatus  in ('11','12','13') AND L.npadt<='" + date + "') ").getResultList();
                                                Vector vec1 = (Vector) resultSet.get(0);
                                                crTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                                drTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                            }
                                        }
                                        if (((actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) && (npaClass.equalsIgnoreCase("N"))) {
                                            List resultSet = em.createNativeQuery("SELECT ifnull(SUM(DRAMT),0) , ifnull(SUM(CRAMT),0) FROM loan_recon WHERE "
                                                    + " SUBSTRING(ACNO,3,2) = '" + actp + "' AND DT <= '" + date + "' AND AUTH = 'Y'  AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster  A WHERE  SUBSTRING(A.ACNO,3,2) = '" + actp + "' AND A.OPENINGDT<= '" + date + "' "
                                                    + "AND SUBSTRING(A.ACNO,1,2) = '" + brCode + "' ) AND "
                                                    + " ACNO IN (SELECT A.ACNO FROM accountmaster A LEFT JOIN loan_appparameter L ON A.ACNO=L.ACNO "
                                                    + " WHERE A.ACCSTATUS IN('11','12','13') AND A.curbrcode= '" + brCode + "' AND SUBSTRING(A.acno,3,2) ='" + actp + "' AND  L.NPADT<= '" + date + "')").getResultList();
                                            Vector vec1 = (Vector) resultSet.get(0);
                                            drTotal = Double.parseDouble(vec1.get(0).toString()) / repOption;
                                            crTotal = Double.parseDouble(vec1.get(1).toString()) / repOption;
                                        }
                                    }

                                    almPojo = new AlmPojo();

                                    almPojo.setgCode(gCode);
                                    almPojo.setSgCode(sgCode);
                                    almPojo.setHeadType(headType);
                                    almPojo.setClassType(classType);
                                    almPojo.setAcNature(acNat);
                                    almPojo.setAcType(actp);
                                    almPojo.setCodeFr(codeFr);
                                    almPojo.setCodeTo(codeTo);
                                    almPojo.setHeadOfAcc(headAcNo);
                                    almPojo.setBuk1(0d);
                                    almPojo.setBuk2(0d);
                                    almPojo.setBuk3(0d);
                                    almPojo.setBuk4(0d);
                                    almPojo.setBuk5(0d);
                                    almPojo.setBuk6(0d);
                                    almPojo.setBuk7(0d);
                                    almPojo.setBuk8(0d);
                                    almPojo.setTotal(0d);
                                    almPojo.setFormula(fOpt);
                                    almPojo.setCountApplicable(cntApp);
                                    almPojo.setNpaClassification(npaClass);

                                    if (ssgCode == 0) {
                                        almPojo.setSsgCode(p + 1);
                                    } else {
                                        almPojo.setSsgCode(ssgCode);
                                    }
                                    if (sssgCode == 0 && ssgCode != 0) {
                                        almPojo.setSssgCode(p + 1);
                                    } else {
                                        almPojo.setSssgCode(sssgCode);
                                    }
                                    if (ssssgCode == 0 && sssgCode != 0 && ssgCode != 0) {
                                        almPojo.setSsssgCode(p + 1);
                                        almPojo.setDescription(acctDesc);
                                    } else {
                                        almPojo.setSsssgCode(ssssgCode);
                                        almPojo.setDescription(String.valueOf("               ").concat(String.valueOf(p + 1)).concat(". ").concat(acctDesc));
                                    }

                                    List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'I' AND HEADS_OF_ACC_NO = " + headAcNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                                    if (!almAccClassList.isEmpty()) {
                                        for (int k = 0; k < almAccClassList.size(); k++) {
                                            Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                            String flow = almAcClassVect.get(0).toString();
                                            Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                            Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                            Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                            if (bucketNo == 1) {
                                                almPojo.setBuk1(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 2) {
                                                almPojo.setBuk2(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 3) {
                                                almPojo.setBuk3(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 4) {
                                                almPojo.setBuk4(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 5) {
                                                almPojo.setBuk5(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 6) {
                                                almPojo.setBuk6(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 7) {
                                                almPojo.setBuk7(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                            if (bucketNo == 8) {
                                                almPojo.setBuk8(Math.abs(crTotal - drTotal) * percentage / 100);
                                            }
                                        }
                                        //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                    } else {
                                        throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                                    }
                                    almPojoTable.add(almPojo);

                                    totalCrDr = totalCrDr + (crTotal - drTotal);
                                }
                            }
                        }

                        almPojo = new AlmPojo();

                        almPojo.setgCode(gCode);
                        almPojo.setSgCode(sgCode);
                        almPojo.setSsgCode(ssgCode);
                        almPojo.setSssgCode(sssgCode);
                        almPojo.setSsssgCode(ssssgCode);
                        almPojo.setDescription(desc);
                        almPojo.setHeadType(headType);
                        almPojo.setClassType(classType);
                        almPojo.setAcNature(acNat);
                        almPojo.setAcType(acType);
                        almPojo.setCodeFr(codeFr);
                        almPojo.setCodeTo(codeTo);
                        almPojo.setHeadOfAcc(headAcNo);
                        almPojo.setBuk1(0d);
                        almPojo.setBuk2(0d);
                        almPojo.setBuk3(0d);
                        almPojo.setBuk4(0d);
                        almPojo.setBuk5(0d);
                        almPojo.setBuk6(0d);
                        almPojo.setBuk7(0d);
                        almPojo.setBuk8(0d);
                        almPojo.setTotal(0d);
                        almPojo.setFormula(fOpt);
                        almPojo.setCountApplicable(cntApp);
                        almPojo.setNpaClassification(npaClass);
                        /**
                         * *** Head Office***************
                         */
                        int al = 1;
                        if (classType.equalsIgnoreCase("I")) {
                            al = -1;
                        }
                        String actQuery = "  having sign(sum(cramt-dramt))  = " + al;
                        if (fSGNo.equalsIgnoreCase("ACT")) {
                            actQuery = "";
                        }
                        List dataList = new ArrayList();
                        if (brCode.equalsIgnoreCase("90")) {
                            //                System.out.println("params.getGlFromHead():" + params.getGlFromHead());
                            if (codeFr.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                dataList = em.createNativeQuery("select 1, 'Cash in Hand', sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "'").getResultList();
                            } else {
                                dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                                        + " gltable g where r.acno=g.acno and dt <='" + date + "'  and substring(r.acno,3,8) between '" + codeFr + "' "
                                        + " and '" + codeTo + "' group by substring(r.acno,3,8),acname " + actQuery).getResultList();
                            }
                        } else {
                            if (codeFr.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                dataList = em.createNativeQuery("select 1, 'Cash in Hand', sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "'  and brncode = '" + brCode + "'").getResultList();
                            } else {
                                dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                                        + " gltable g where r.acno=g.acno and dt <='" + date + "'  and substring(r.acno,3,8) between '" + codeFr
                                        + " ' and '" + codeTo + "' and substring(r.acno,1,2)='" + brCode + "' group by substring(r.acno,3,8) "
                                        + actQuery).getResultList();
                            }
                        }
                        if (dataList.size() > 0) {
                            almPojoTable.add(almPojo);

                            for (int j = 0; j < dataList.size(); j++) {
                                Vector vec = (Vector) dataList.get(j);
                                Double totalAmt = 0d;
                                String code = vec.get(0).toString();
                                //totalAmt = totalAmt + (Double.parseDouble(vec.get(2).toString()) + totalCrDr) / repOption;
                                totalAmt = totalAmt + (Double.parseDouble(vec.get(2).toString())) / repOption;
                                if (fSGNo.equalsIgnoreCase("ACT")) {
                                    totalAmt = totalAmt;
                                } else {
                                    if (classType.equalsIgnoreCase("O") && totalAmt > 0) {
                                        totalAmt = totalAmt;
                                    } else if (classType.equalsIgnoreCase("I") && code.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                        totalAmt = totalAmt;
                                    } else if (classType.equalsIgnoreCase("I") && totalAmt < 0) {
                                        totalAmt = totalAmt;
                                    } else {
                                        totalAmt = 0.0;
                                    }
                                }

                                almPojo = new AlmPojo();
                                almPojo.setBuk1(0d);
                                almPojo.setBuk2(0d);
                                almPojo.setBuk3(0d);
                                almPojo.setBuk4(0d);
                                almPojo.setBuk5(0d);
                                almPojo.setBuk6(0d);
                                almPojo.setBuk7(0d);
                                almPojo.setBuk8(0d);
                                almPojo.setTotal(0d);
                                List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'I' AND HEADS_OF_ACC_NO = " + headAcNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
                                if (!almAccClassList.isEmpty()) {
                                    for (int k = 0; k < almAccClassList.size(); k++) {
                                        Vector almAcClassVect = (Vector) almAccClassList.get(k);
                                        String flow = almAcClassVect.get(0).toString();
                                        Integer headsOfAcNoClass = Integer.parseInt(almAcClassVect.get(1).toString());
                                        Integer bucketNo = Integer.parseInt(almAcClassVect.get(2).toString());
                                        Double percentage = Double.parseDouble(almAcClassVect.get(3).toString());

                                        if (bucketNo == 1) {
                                            almPojo.setBuk1((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 2) {
                                            almPojo.setBuk2((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 3) {
                                            almPojo.setBuk3((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 4) {
                                            almPojo.setBuk4((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 5) {
                                            almPojo.setBuk5((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 6) {
                                            almPojo.setBuk6((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 7) {
                                            almPojo.setBuk7((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                        if (bucketNo == 8) {
                                            almPojo.setBuk8((fSGNo.equalsIgnoreCase("ACT") ? totalAmt : Math.abs(totalAmt)) * percentage / 100);
                                        }
                                    }
                                    //System.out.println("hello;"+almPojo.getgCode()+";"+almPojo.getSgCode()+";"+almPojo.getCode()+";"+almPojo.getDescription()+";"+almPojo.getHeadType()+";"+almPojo.getClassType()+";"+almPojo.getAcType()+";"+almPojo.getHeadOfAcc()+";"+almPojo.getBuk1()+";"+almPojo.getBuk2()+";"+almPojo.getBuk3()+";"+almPojo.getBuk4()+";"+almPojo.getBuk5()+";"+almPojo.getBuk6()+";"+almPojo.getBuk7()+";"+almPojo.getBuk8());
                                } else {
                                    throw new ApplicationException("Data does not exist in CBS_ALM_ACC_CLASS");
                                }
                                almPojo.setgCode(gCode);
                                almPojo.setSgCode(sgCode);
                                almPojo.setHeadType(headType);
                                almPojo.setClassType(classType);
                                almPojo.setAcNature(acNat);
                                almPojo.setAcType(acType);
                                almPojo.setCodeFr(code);
                                almPojo.setCodeTo(code);
                                almPojo.setHeadOfAcc(headAcNo);
                                almPojo.setFormula(fOpt);
                                almPojo.setCountApplicable(cntApp);
                                almPojo.setNpaClassification(npaClass);
                                if (ssgCode == 0) {
                                    almPojo.setSsgCode(j + 1);
                                } else {
                                    almPojo.setSsgCode(ssgCode);
                                }
                                if (sssgCode == 0 && ssgCode != 0) {
                                    almPojo.setSssgCode(j + 1);
                                } else {
                                    almPojo.setSssgCode(sssgCode);
                                }
                                if (ssssgCode == 0 && sssgCode != 0 && ssgCode != 0) {
                                    almPojo.setSsssgCode(j + 1);
                                    almPojo.setDescription(vec.get(1).toString());
                                } else {
                                    almPojo.setSsssgCode(ssssgCode);
                                    almPojo.setDescription(String.valueOf("               ").concat(String.valueOf(j + 1)).concat(". ").concat(vec.get(1).toString()));
                                }
                                almPojoTable.add(almPojo);
                            }
                        } else {
                            Double totalAmt = 0d;
                            if (brCode.equalsIgnoreCase("90")) {
                                if (fOpt.equals("+P&L")) {
                                    totalAmt = glReport.IncomeExp(date, "0A", "0A");
                                } else if (fOpt.equals("-P&L")) {
                                    totalAmt = glReport.IncomeExp(date, "0A", "0A");
                                }
                            } else {
                                if (fOpt.equals("+P&L")) {
                                    totalAmt = glReport.IncomeExp(date, brCode, brCode);
                                } else if (fOpt.equals("-P&L")) {
                                    totalAmt = glReport.IncomeExp(date, brCode, brCode);
                                }
                            }

                            //totalAmt = totalAmt + ((totalCrDr) / repOption);
                            totalAmt = totalAmt / repOption;
                            if (classType.equalsIgnoreCase("O") && totalAmt > 0) {
                                totalAmt = totalAmt;
                            } else if (classType.equalsIgnoreCase("I") && totalAmt < 0) {
                                totalAmt = totalAmt;
                            } else {
                                totalAmt = 0.0;
                            }

                            List almAccClassList = em.createNativeQuery("SELECT FLOW, HEADS_OF_ACC_NO, BUCKET_NO, PERCENT_AMT FROM cbs_alm_acc_class WHERE PROFILE_PARAMETER = 'I' AND HEADS_OF_ACC_NO = " + headAcNo + " ORDER BY FLOW, HEADS_OF_ACC_NO, BUCKET_NO").getResultList();
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
}
