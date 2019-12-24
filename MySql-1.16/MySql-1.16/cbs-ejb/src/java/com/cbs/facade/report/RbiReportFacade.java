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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.cbs.dto.report.RbiSossPojo;
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
@Stateless(mappedName = "RbiReportFacade")
public class RbiReportFacade implements RbiReportFacadeRemote {

    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalcFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    RbiReportPartDFacadeRemote rbiReportPartD;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsFacade;
    @EJB
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote;
    @EJB
    private NpaReportFacadeRemote npaRemote;
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

    public YearEndDatePojo getYearEndData(String brncode) {
        List yearEndList = em.createNativeQuery("select mindate,maxdate,f_year from yearend where yearendflag='N' and brncode='" + brncode + "'").getResultList();
        YearEndDatePojo pojo = new YearEndDatePojo();
        // for (int i = 0; i < yearEndList.size(); i++) {
        Vector vec = (Vector) yearEndList.get(0);
        pojo.setMinDate(vec.get(0).toString());
        pojo.setMaxDate(vec.get(1).toString());
        pojo.setfYear(vec.get(2).toString());
        // }
        return pojo;
    }

    public YearEndDatePojo getYearEndDataAccordingToDate(String brncode, String date) {
        List yearEndList = em.createNativeQuery("select mindate,maxdate,f_year from yearend where brncode='" + brncode + "' and '" + date + "' between mindate and maxdate").getResultList();
        YearEndDatePojo pojo = new YearEndDatePojo();
        //for (int i = 0; i < yearEndList.size(); i++) {
        Vector vec = (Vector) yearEndList.get(0);
        pojo.setMinDate(vec.get(0).toString());
        pojo.setMaxDate(vec.get(1).toString());
        pojo.setfYear(vec.get(2).toString());
        // }
        return pojo;
    }

    public YearEndDatePojo getYearEndDataAccordingFinYear(String brncode, String year) {
        List yearEndList = em.createNativeQuery("select mindate,maxdate,f_year from yearend where brncode='" + brncode + "' and  F_YEAR = '" + year + "' ").getResultList();
        YearEndDatePojo pojo = new YearEndDatePojo();
        //for (int i = 0; i < yearEndList.size(); i++) {
        Vector vec = (Vector) yearEndList.get(0);
        pojo.setMinDate(vec.get(0).toString());
        pojo.setMaxDate(vec.get(1).toString());
        pojo.setfYear(vec.get(2).toString());
        // }
        return pojo;
    }

    public String getMinFinYear(String dt) throws ApplicationException {
        try {
            List yearEndList = em.createNativeQuery("select mindate from cbs_yearend where '" + dt + "' between mindate and maxdate").getResultList();
            Vector vec = (Vector) yearEndList.get(0);
            return vec.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public AcctBalPojo getAcctsAndBal(AdditionalStmtPojo params) throws ApplicationException {
        try {
            int al;
            String havingQuery = "", acctcode = "", acctcodeQuery = "";
//            if (params.getClassification().equals("A")) {
//                al = -1;
//                havingQuery = " having sign(sum(cramt-dramt)) = " + al;
//            } else if (params.getClassification().equals("L")) {
//                al = 1;
//                havingQuery = " having sign(sum(cramt-dramt)) = " + al;
//            } else if (params.getClassification().equals("")) {
//                havingQuery = "";
//            }            
            if (params.getClassification().equals("A")) {
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = -1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (params.getClassification().equals("L")) {
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = 1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (params.getClassification().equals("")) {
                havingQuery = "";
            }

            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", allNapQuery = "", bd_query = "", closingQuery = "", maxAcNoQuery = "", npaAcDetails = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                closingQuery = " and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) ";


                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    acCodeQuery = " substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
                } else {
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
                }

            } else if (!params.getAcType().equals("")) {
                acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                closingQuery = " and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) ";
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    acCodeQuery = " substring(acno,3,2) in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                } else if (acNature.equals(CbsConstant.OF_AC)) {
                    acCodeQuery = " and substring(r.acno,3,2) in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                } else {
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                }

            }

            if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartD")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            } else if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartDNpa")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            }
            if (acNature.equalsIgnoreCase("CA") || acNature.equalsIgnoreCase("DL") || acNature.equalsIgnoreCase("TL")) {
//                if (params.getNpaAcList()!=null) {
//                    if (params.getNpaAcList().size() > 0) {
//                        List<NpaStatusReportPojo> resultList = params.getNpaAcList();//npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(),"Y");
//                        int z=0;
//                        for (int x = 0; x < resultList.size(); x++) {
//                            if (z == 0) {
//                                npaAcDetails = "'" + resultList.get(x).getAcno() + "'";
//                                z++;
//                            } else {
//                                npaAcDetails = npaAcDetails + ", '" + resultList.get(x).getAcno() + "'";
//                                z++;
//                            }
//                        }
//                    }
//                }
                npaAcDetails = " select a.acno from accountstatus a, "
                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + params.getDate() + "' group by acno) b,"
                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + params.getDate() + "' group by acno) c ,"
                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + params.getDate() + "' and "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + params.getDate() + "')  ";
            }
            /*
             * Only for Non-NPA account outstanding as on date
             */
            if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
                if (!npaAcDetails.equalsIgnoreCase("")) {
                    npaQuery = " and a.acno not in (" + npaAcDetails + ") ";
                }
            } /*
             * Only for NPA account outstanding as on date
             */ else if (params.getNature().contains("NPA") || params.getAcType().contains("NPA")) {
                if (!npaAcDetails.equalsIgnoreCase("")) {
                    npaQuery = " and  a.acno in (" + npaAcDetails + ")";
                }
            }
            List dataList = new ArrayList();
            List maxAcNo = new ArrayList();
            if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select  count(distinct acno),cast(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) from " + tableName + ""
                            + " where " + acCodeQuery + " and DT <= '" + params.getDate() + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from td_accountmaster a where  " + maxAcNoQuery).getResultList();
                } else if (acNature.equals(CbsConstant.OF_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), ifnull(sum(a.amt),0) from (select  r.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " r,accountmaster a where "
                            + " r.acno = a.acno and  r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno  " + havingQuery + ") "
                            + " and r.auth = 'Y' and r.dt <='"
                            + params.getDate() + "' " + acCodeQuery + " group by r.acno "
                            + allNapQuery
                            + " ) a").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from " + tableName + " a").getResultList();
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno), ifnull(sum(a.amt),0) from (select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " r,accountmaster a where "
                            + " r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno  " + havingQuery + ") "
                            + closingQuery + "  and r.acno = a.acno and r.auth = 'Y' and r.dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno "
                            + allNapQuery
                            + " ) a").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from accountmaster a, cbs_loan_acc_mast_sec b where  a.acno = b.acno and " + maxAcNoQuery).getResultList();
                }
            } else {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select  count(distinct acno), cast(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) from " + tableName + ""
                            + " where " + acCodeQuery + " and DT <= '" + params.getDate() + "' and substring(acno,1,2) = '" + params.getBrnCode() + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();

                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from td_accountmaster a where  " + maxAcNoQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno),ifnull(sum(a.amt),0) from (select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " r,accountmaster a where "
                            + " r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno  " + havingQuery + ") "
                            + closingQuery + "  and r.acno = a.acno  and r.auth = 'Y' and a.CurBrCode = '" + params.getBrnCode() + "' and r.dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno "
                            + " ) a").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from accountmaster a where  " + maxAcNoQuery).getResultList();
                }
            }

            AcctBalPojo acctBal = new AcctBalPojo();

            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                acctBal.setNumberOFAcct(Long.parseLong(ele.get(0).toString()));
                acctBal.setBalance(new BigDecimal(ele.get(1).toString()));
            }
//            System.out.println("sno:" + params.getAcType() + "; " + params.getNature());
            if (maxAcNo.size() > 0) {
                for (int i = 0; i < maxAcNo.size(); i++) {
                    Vector ele = (Vector) maxAcNo.get(i);
                    acctBal.setAcNo(ele.get(0).toString());
                }
            }
            return acctBal;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public AcctBalPojo getAcctsAndBalAmtRangeWise(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", bd_query = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature in ( '" + acNature + "'))";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode in ( '" + params.getAcType() + "'))";
            }
            String npaAcDetails = "";
            if (acNature.equalsIgnoreCase("CA") || acNature.equalsIgnoreCase("DL") || acNature.equalsIgnoreCase("TL")) {
//                if (params.getNpaAcList().size() > 0) {
//                    List<NpaStatusReportPojo> resultList = params.getNpaAcList();//npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(),"Y");
//                    int z=0;
//                    for (int x = 0; x < resultList.size(); x++) {
//                        if (z == 0) {
//                            npaAcDetails = "'" + resultList.get(x).getAcno() + "'";
//                            z++;
//                        } else {
//                            npaAcDetails = npaAcDetails + ", '" + resultList.get(x).getAcno() + "'";
//                            z++;
//                        }
//                    }
//                }      
                npaAcDetails = " select a.acno from accountstatus a, "
                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + params.getDate() + "' group by acno) b,"
                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + params.getDate() + "' group by acno) c ,"
                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + params.getDate() + "' and "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + params.getDate() + "')  ";
            }
            /*
             * Only for Non-NPA account outstanding as on date
             */
//            if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
//                if(!npaAcDetails.equalsIgnoreCase("")){
//                    npaQuery = " and a.acno not in (" + npaAcDetails + ") ";
//                }
//            } 
            /*
             * Only for NPA account outstanding as on date
             */
            if (params.getNature().contains("NPA") || params.getAcType().contains("NPA")) {
                if (!npaAcDetails.equalsIgnoreCase("")) {
                    npaQuery = " and  a.acno in (" + npaAcDetails + ") ";
                }
            } else {
                if (!npaAcDetails.equalsIgnoreCase("")) {
                    npaQuery = " and a.acno not in (" + npaAcDetails + ") ";
                }
            }

//            if (params.getNature().contains("NPA") || params.getAcType().contains("NPA")) {
//                npaQuery = "and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and l.npadt<= '" + params.getDate() + "')";
//            } else {
//                npaQuery = " and  a.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus not in('11','12','13') " + acCodeQuery
//                        + " union           select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus     in('11','12','13') " + acCodeQuery + " and l.npadt>'" + params.getDate() + "')";
//            }

            if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartD")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "'";
            } else if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartDNpa")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "'";
            }

            List dataList = new ArrayList();
            if (params.getBrnCode().equalsIgnoreCase("0A")) {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' and closeflag is null  group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and r.closeflag is null and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
//                    System.out.println("accTnature :"+acNature+">>>>:select (distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
//                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
//                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
//                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
//                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery);
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
//                    System.out.println("accTnature :"+acNature+">>>>:select (distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
//                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
//                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
//                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
//                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery);
                }
            } else {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' and closeflag is null  group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + " and r.closeflag is null and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                            + "r.acno in (select acno from " + tableName + " where dt <= '" + params.getDate() + "' group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery).getResultList();
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

    public AcctBalPojo getAcctsAndBalTimeRangeWise(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "";
            String acNature = params.getNature();
            String acCodeQuery = "";
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature in ( '" + acNature + "') and crdbflag in ('B','D'))";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode in ('" + params.getAcType() + "') and crdbflag in ('B','D'))";
            }
            List dataList = new ArrayList();
            if (params.getBrnCode().equalsIgnoreCase("0A")) {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' and closeflag is null  group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "' " + acCodeQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                            + params.getDate() + "' " + acCodeQuery).getResultList();
                }
            } else {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,td_accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' and closeflag is null  group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "' " + acCodeQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno), cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a where "
                            + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno "
                            + "having cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) between " + params.getFromRange() + " and " + params.getToRange() + ") "
                            + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                            + params.getDate() + "' " + acCodeQuery).getResultList();
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
            int al;
            String havingQuery = "";
            if (params.getClassification().equals("A")) {
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = -1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (params.getClassification().equals("L")) {
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = 1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (params.getClassification().equals("")) {
                havingQuery = "";
            }
//            System.out.println("From-Head :" + params.getGlFromHead() + "To-Head :" + params.getGlToHead() + " And Date = " + params.getDate());
            List dataList = new ArrayList();
            if (params.getBrnCode().equals("0A") || params.getBrnCode().equals("90")) {
//                System.out.println("params.getGlFromHead():" + params.getGlFromHead());
                if (params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select '" + params.getGlFromHead() + "', 'Cash in Hand', ifnull(sum(amt)*-1, 0) from cashinhand where ldate ='" + params.getToDate() + "'").getResultList();
                } else if (params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue().substring(0, 1))
                        || params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue().substring(0, 1))) {
                    dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                            + " gltable g where r.acno=g.acno and dt between '" + params.getDate() + "' and '" + params.getToDate() + "'  and trandesc <>13  and substring(r.acno,3,8) between '" + params.getGlFromHead()
                            + "' and '" + params.getGlToHead() + "' group by substring(r.acno,3,8), acname "
                            + havingQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                            + " gltable g where r.acno=g.acno and dt <='" + params.getToDate() + "' and substring(r.acno,3,8) between '" + params.getGlFromHead()
                            + "' and '" + params.getGlToHead() + "' group by substring(r.acno,3,8), acname " + havingQuery).getResultList();
                }
            } else {
                if (params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select '" + params.getGlFromHead() + "', 'Cash in Hand', ifnull(sum(amt)*-1, 0) from cashinhand where ldate ='" + params.getToDate() + "'  and brncode = '" + params.getBrnCode() + "'").getResultList();
                } else if (params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue().substring(0, 1))
                        || params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue().substring(0, 1))) {
                    dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                            + " gltable g where r.acno=g.acno and dt between '" + params.getDate() + "' and '" + params.getToDate() + "'  and trandesc <>13  and substring(r.acno,3,8) between '" + params.getGlFromHead()
                            + "' and '" + params.getGlToHead() + "' and substring(r.acno,1,2)='" + params.getBrnCode() + "' group by substring(r.acno,3,8), acname "
                            + havingQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select  substring(r.acno,3,8), acname,cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r, "
                            + " gltable g where r.acno=g.acno and dt <='" + params.getToDate() + "' and substring(r.acno,3,8) between '" + params.getGlFromHead()
                            + "' and '" + params.getGlToHead() + "' and substring(r.acno,1,2)='" + params.getBrnCode() + "' group by substring(r.acno,3,8), acname "
                            + havingQuery).getResultList();
                }
            }
            //}
            List<GlHeadPojo> pojoList = new ArrayList<GlHeadPojo>();
            GlHeadPojo pojo;
            for (int i = 0; i < dataList.size(); i++) {
                Vector vec = (Vector) dataList.get(i);
                pojo = new GlHeadPojo();
                pojo.setGlHead(vec.get(0).toString());
                pojo.setGlName(vec.get(1).toString());
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    if (params.getClassification().equalsIgnoreCase("A")) {
                        if (new BigDecimal(vec.get(2).toString()).compareTo(new BigDecimal("0")) < 0) {
                            pojo.setBalance(new BigDecimal(vec.get(2).toString()));
                        } else {
                            pojo.setBalance(new BigDecimal("0"));
                        }
                    } else if (params.getClassification().equalsIgnoreCase("L")) {
                        if (new BigDecimal(vec.get(2).toString()).compareTo(new BigDecimal("0")) >= 0) {
                            pojo.setBalance(new BigDecimal(vec.get(2).toString()));
                        } else {
                            pojo.setBalance(new BigDecimal("0"));
                        }
                    } else {
                        pojo.setBalance(new BigDecimal(vec.get(2).toString()));
                    }
                } else {
                    pojo.setBalance(new BigDecimal(vec.get(2).toString()));
                }

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    /*
     * Formula implementation of RBI Report (
     */

    public BigDecimal getValueFromFormula(List<RbiSossPojo> dataList, String formula) throws ApplicationException {
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
            e.printStackTrace();
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
            if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else if (arr[1] != 0 && arr[2] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else if (arr[1] != 0) {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            } else {
                for (RbiSossPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0])) {
                        balance = balance.add(pojo.getAmt());
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public AcctBalPojo getNpaBasedOnAllNpaStatus(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npadt = "", tillDt = "", acStatus = "", delinqCycle1 = "", flag = "";
            String acNature = params.getNature();
            String acctcode = params.getAcType();
            BigDecimal bal = new BigDecimal("0.00");
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                acNature = params.getNature().substring(params.getNature().indexOf(".") + 1);
                acCodeQuery = " select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in ('D','B') ";
            } else if (!acctcode.equals("")) {
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                //  acctcode = acctcode;
                acCodeQuery = " select acctcode from accounttypemaster where acctcode in ('" + acctcode + "') and crdbflag in ('D','B') ";
            }
            List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
            String delinqCycle = "";
            Date npaDt = null;
            String fromDt = null, toDt = null;
            if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                fromDt = CbsUtil.dateAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue());
                toDt = CbsUtil.dateAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue());
            } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                fromDt = CbsUtil.monthAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue());
                toDt = CbsUtil.monthAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue());
            } else if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                fromDt = CbsUtil.yearAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue());
                toDt = CbsUtil.yearAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue());
            }

            //resultList = npaRemote.getNpaStatusReportData("0", acCodeQuery, params.getDate(), acStatus, params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(),"Y");
            resultList = npaRemote.getNpaStatusReportDataOptimized("0", acCodeQuery, "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(), "Y");
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getPresentStatus().contains("SUB")) {
                    npaDt = dmy.parse(resultList.get(i).getSubStandardDt());
                    if (npaDt.after(ymd.parse(fromDt)) && (npaDt.equals(ymd.parse(toDt)) || npaDt.before(ymd.parse(toDt)))) {
                        bal = bal.add(resultList.get(i).getBalance());
//                    System.out.println(acNature+"-"+params.getAcType()+":"+resultList.get(i).getAcno() +": Bal:"+bal+":Actual bal:"+resultList.get(i).getBalance()+": NpaDt:"+npaDt+": FrDt:"+fromDt+": ToDt:"+toDt+":Status:"+resultList.get(i).getPresentStatus());
                    }
                } else if (resultList.get(i).getPresentStatus().contains("DOU") || resultList.get(i).getPresentStatus().contains("LOS")) {
                    if (resultList.get(i).getPresentStatus().contains("DOU")) {
                        npaDt = dmy.parse(resultList.get(i).getDoubtFullDt());
                    } else if (resultList.get(i).getPresentStatus().contains("LOS")) {
                        npaDt = dmy.parse(resultList.get(i).getLossDt());
                    }
                    int fromRange = 0;
                    int torange =0;
                    String oss3ParDt="";
                    List code = common.getCode("OSS3_NPA_DT");
                    // For the New OSS3 master(NPA classification changed from 12 Months to 18 Months and Above 12 months to Above 18 Month
                    if(!code.isEmpty()) {
                        oss3ParDt = code.get(0).toString();
                    }
                    if(ymd.parse(params.getDate()).after(ymd.parse(oss3ParDt))) {
                        if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                            long dayDiff= CbsUtil.dayDiff(npaDt, ymd.parse(toDt));
                            fromRange = new BigDecimal(params.getToRange()).intValue();
                            torange = new BigDecimal(params.getFromRange()).intValue();
                            if (dayDiff < Math.abs(torange) && dayDiff >= Math.abs(fromRange)) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                        } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                            fromRange = new BigDecimal(params.getToRange()).intValue();
                            torange = new BigDecimal(params.getFromRange()).intValue();
                            long monthdiff = CbsUtil.monthDiff(npaDt, ymd.parse(toDt));
                            if (monthdiff < Math.abs(torange) && monthdiff >= Math.abs(fromRange)) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                        } else if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                            long yearDiff = CbsUtil.yearDiff(npaDt, ymd.parse(toDt));
                            fromRange = new BigDecimal(params.getToRange()).intValue();
                            torange = new BigDecimal(params.getFromRange()).intValue();
                            if (yearDiff < Math.abs(torange) && yearDiff >= Math.abs(fromRange)) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                        } else {
                            bal = bal.add(resultList.get(i).getBalance());
                        }
                    } else {
                        if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                            fromRange = new BigDecimal(params.getToRange()).intValue();
                            if (Math.abs(fromRange) == 365) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                        } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                            fromRange = new BigDecimal(params.getToRange()).intValue();
                            if (Math.abs(fromRange) == 12) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                        } else if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                            fromRange = new BigDecimal(params.getToRange()).intValue();
                            if (Math.abs(fromRange) == 1) {
                                bal = bal.add(resultList.get(i).getBalance());
                            }
                        } else {
                            bal = bal.add(resultList.get(i).getBalance());
                        }
                    }
                }
//                else if (resultList.get(i).getPresentStatus().contains("LOS")) {
//                    npaDt = dmy.parse(resultList.get(i).getLossDt());
//                    bal = bal.add(resultList.get(i).getBalance());
//                }
//                if (npaDt.after(ymd.parse(fromDt)) && (npaDt.equals(ymd.parse(toDt)) || npaDt.before(ymd.parse(toDt)))) {
//                    bal = bal.add(resultList.get(i).getBalance());
////                    System.out.println(acNature+"-"+params.getAcType()+":"+resultList.get(i).getAcno() +": Bal:"+bal+":Actual bal:"+resultList.get(i).getBalance()+": NpaDt:"+npaDt+": FrDt:"+fromDt+": ToDt:"+toDt+":Status:"+resultList.get(i).getPresentStatus());
//                }
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

    /*For SOSS2 NPA/NPA Interest CR/DR/Balance*/
    public AcctBalPojo getNpaAcctsAndBal(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", npaAmt = "";
            String acNature = params.getNature();
            String acctcode = params.getAcType();
            String date = "<='" + params.getDate() + "'";
            String npaDate = "<='" + params.getDate() + "'";
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                if (!acNature.equalsIgnoreCase("ALL")) {
                    tableName = common.getTableName(acNature);
                    acNature = params.getNature().substring(params.getNature().indexOf(".") + 1);
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature in ( '" + acNature + "'))";
                }
            } else if (!params.getAcType().equals("")) {
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                //  acctcode = acctcode;
                if (!acNature.equalsIgnoreCase("ALL")) {
                    acNature = common.getAcNatureByAcType(acctcode);
                    tableName = common.getTableName(acNature);
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode in ('" + acctcode + "'))";
                }
            }
//            YearEndDatePojo yDate = new YearEndDatePojo();
//            yDate = (YearEndDatePojo) getYearEndData(params.getOrgBrCode().equalsIgnoreCase("0A") ? "90" : params.getOrgBrCode());
//            String minFDate = yDate.getMinDate();
            String minFDate = getMinFinYear(params.getDate());
            //  String maxFDate = yDate.getMaxDate();
            // String fyear = yDate.getfYear();
            String npaReconQuery = "";
            if (params.getRangeBasedOn().equalsIgnoreCase("Dt")) {
                if (params.getRefer_index().equalsIgnoreCase("F")) {
                    if (params.getRefer_content().equalsIgnoreCase("<")) {
                        date = " < '" + minFDate + "' ";
                    } else {
                        date = " between '" + minFDate + "' and '" + params.getToDate() + "' ";
                    }
                } else if (params.getRefer_index().equalsIgnoreCase("Q")) {
                    if (params.getRefer_content().equalsIgnoreCase("<")) {
                        date = " < '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' ";
                    } else {
                        date = " between '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F") + "' and '" + CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "L") + "' ";
                    }
                }
            } else if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                date = " between '" + CbsUtil.dateAdd(CbsUtil.dateAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue()), 1) + "' and '" + CbsUtil.dateAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue()) + "' ";
            } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                date = " between '" + CbsUtil.dateAdd(CbsUtil.monthAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue()), 1) + "' and '" + CbsUtil.monthAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue()) + "' ";
            } else if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                date = " between '" + CbsUtil.dateAdd(CbsUtil.yearAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue()), 1) + "' and '" + CbsUtil.yearAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue()) + "' ";
            } else {
                date = " <='" + params.getDate() + "'";
            }

            if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartD")) {
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            } else if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartDNpa")) {
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            }

            List dataList = new ArrayList();
            AcctBalPojo acctBal = new AcctBalPojo();
            if (!((params.getRefer_index().equalsIgnoreCase("Q") || params.getRefer_index().equalsIgnoreCase("F")) && params.getRefer_content().equalsIgnoreCase("<"))) {
                if (params.getNature().contains("NPA")) {
                    if (params.getNature().contains("INT")) {
                        if (params.getNature().contains("CR")) {
                            npaAmt = " cast(ifnull(sum(cramt),0) as decimal(14,2)) as amt";
                        } else if (params.getNature().contains("DR")) {
                            npaAmt = " cast(ifnull(sum(dramt),0) as decimal(14,2)) as amt";
                        } else {
                            npaAmt = " cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt";
                        }
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and (l.npadt < '" + params.getToDate() + "' or l.npadt is null or l.npadt = '')) and r.TranType in (8)";//and l.npadt " + date + ") and r.TranType in (8)";
                        if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                            npaReconQuery = " union all "
                                    + " select  a.acno, " + npaAmt + " from npa_recon r,accountmaster a where "
                                    + " (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getToDate() + "' ) and r.acno = a.acno and r.dt "
                                    + (params.getRangeBasedOn().equalsIgnoreCase("Dt") ? date : npaDate) + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno";
                        } else {
                            npaReconQuery = " union all "
                                    + " select a.acno, " + npaAmt + " from npa_recon r,accountmaster a where "
                                    + " (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getToDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and r.dt "
                                    + (params.getRangeBasedOn().equalsIgnoreCase("Dt") ? date : npaDate) + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno";
                        }
                    } else {
                        npaAmt = " cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt";
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and (l.npadt  < '" + params.getToDate() + "' or l.npadt is null or l.npadt = ''))";//and l.npadt " + date + ")";
                    }
                } else if (params.getAcType().contains("NPA")) {
                    if (params.getAcType().contains("INT")) {
                        if (params.getAcType().contains("CR")) {
                            npaAmt = " cast(ifnull(sum(cramt),0) as decimal(14,2)) as amt";
                        } else if (params.getAcType().contains("DR")) {
                            npaAmt = " cast(ifnull(sum(dramt),0) as decimal(14,2)) as amt";
                        } else {
                            npaAmt = " cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt";
                        }
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and (l.npadt < '" + params.getToDate() + "' or l.npadt is null or l.npadt = '')) and r.TranType in (8)";
                        if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                            npaReconQuery = " union all "
                                    + " select  a.acno, " + npaAmt + " from npa_recon r,accountmaster a where "
                                    + " (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getToDate() + "' ) and r.acno = a.acno and r.dt "
                                    + (params.getRangeBasedOn().equalsIgnoreCase("Dt") ? date : npaDate) + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno";
                        } else {
                            npaReconQuery = " union all "
                                    + " select a.acno, " + npaAmt + " from npa_recon r,accountmaster a where "
                                    + " (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getToDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and r.dt "
                                    + (params.getRangeBasedOn().equalsIgnoreCase("Dt") ? date : npaDate) + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno";
                        }

                    } else {
                        npaAmt = " cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt";
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and (l.npadt < '" + params.getToDate() + "' or l.npadt is null or l.npadt = ''))";//and l.npadt " + date + ")";
                    }
                } else {
                    npaAmt = " cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt";
                    npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and (l.npadt  " + date + " or l.npadt is null or l.npadt = ''))";//and l.npadt " + date + ")";
                }


                dataList = new ArrayList();
                if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                    dataList = em.createNativeQuery("select count(distinct a.acno),ifnull(sum(a.amt),0) from (select  a.acno, " + npaAmt + " from " + tableName + " r,accountmaster a where "
                            + " (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and r.dt "
                            + (params.getRangeBasedOn().equalsIgnoreCase("Dt") ? date : npaDate) + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno "
                            + npaReconQuery + " ) a").getResultList();
                } else {
                    dataList = em.createNativeQuery("select count(distinct a.acno),ifnull(sum(a.amt),0) from (select a.acno, " + npaAmt + " from " + tableName + " r,accountmaster a where "
                            + " (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and r.dt "
                            + (params.getRangeBasedOn().equalsIgnoreCase("Dt") ? date : npaDate) + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno "
                            + npaReconQuery + " ) a").getResultList();
                }
                acctBal = new AcctBalPojo();
                for (int i = 0; i < dataList.size(); i++) {
                    Vector ele = (Vector) dataList.get(i);
                    acctBal.setNumberOFAcct(Long.parseLong(ele.get(0).toString()));
                    acctBal.setBalance(new BigDecimal(ele.get(1).toString()));
                }
            } else {
                acctBal = new AcctBalPojo();
                if (params.getRefer_index().equalsIgnoreCase("F")) {
                    date = minFDate;
                } else if (params.getRefer_index().equalsIgnoreCase("Q")) {
                    date = CbsUtil.getQuarterFirstAndLastDate(params.getToDate().substring(4, 6), params.getToDate().substring(0, 4), "F");
                }
                List<NpaStatusReportPojo> resultListBegin = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", date, "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(), "Y");
                if (!resultListBegin.isEmpty()) {
                    BigDecimal fAmt = new BigDecimal("0");
                    for (int o = 0; o < resultListBegin.size(); o++) {
                        fAmt = fAmt.add(resultListBegin.get(o).getBalance());
                    }
                    acctBal.setNumberOFAcct(Long.valueOf(resultListBegin.size()));
                    acctBal.setBalance(fAmt);
                } else {
                    acctBal.setNumberOFAcct(0l);
                    acctBal.setBalance(new BigDecimal("0"));
                }
            }


            return acctBal;
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
                npaAmt = " cast(ifnull(sum(cramt),0) as decimal(25,2))";
            } else {
                npaAmt = " cast(ifnull(sum(cramt- dramt),0) as decimal(25,2))";
            }
            npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and l.npadt " + dtQuery + ")";

            List dataList = new ArrayList();
            if (brncode.equalsIgnoreCase("0A")) {
                dataList = em.createNativeQuery("select count(distinct a.acno), " + npaAmt + " from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " where dt " + dtQuery + " ) "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate " + closingQ + " ) and r.acno = a.acno and dt " + dtQuery + acCodeQuery + npaQuery).getResultList();
            } else {
                dataList = em.createNativeQuery("select count(distinct a.acno), " + npaAmt + " from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " where dt " + dtQuery + " ) "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate " + closingQ + " ) and r.acno = a.acno and a.CurBrCode = '" + brncode + "' and dt " + dtQuery + acCodeQuery + npaQuery).getResultList();
            }
            Vector ele = (Vector) dataList.get(0);
            amt = new BigDecimal(ele.get(1).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return amt;
    }

    public NpaConsolidateAndAcWisePojo getAmountBasedOnNpaClassification(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String acCodeQuery = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                //    tableName = common.getTableName(acNature);
                acCodeQuery = "  (select acctcode from accounttypemaster where acctnature in ( '" + acNature + "') and crdbflag in ('D','B')) ";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                acNature = common.getAcNatureByAcType(acctcode);
                //  tableName = common.getTableName(acNature);
                acCodeQuery = " (select acctcode from accounttypemaster where acctcode in ('" + acctcode + "') and crdbflag  in ('D','B')) ";
            }
            List<NpaStatusReportPojo> npaList = null;
            List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
            if (params.getNpaClassification().equalsIgnoreCase("11")) {              //SUB STANDARD
                npaList = npaRemote.getNpaStatusReportDataOptimized("1", acCodeQuery, "19000101", params.getDate(), "SUB", params.getBrnCode(), "Y");
            } else if (params.getNpaClassification().equalsIgnoreCase("12")) {       //DOUBTFUL
                npaList = npaRemote.getNpaStatusReportDataOptimized("1", acCodeQuery, "19000101", params.getDate(), "DOU", params.getBrnCode(), "Y");
            } else if (params.getNpaClassification().equalsIgnoreCase("13")) {       //LOSS
                npaList = npaRemote.getNpaStatusReportDataOptimized("1", acCodeQuery, "19000101", params.getDate(), "LOS", params.getBrnCode(), "Y");
            }
            BigDecimal totalNpaAmt = new BigDecimal("0");
            long totalAcNo = 0;
            if (params.getRangeBasedOn().equals("")) {
                for (int m = 0; m < npaList.size(); m++) {
                    totalNpaAmt = totalNpaAmt.add(npaList.get(m).getBalance());
                    totalAcNo = totalAcNo + 1;

                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                    pojo.setAcno(npaList.get(m).getAcno());
                    pojo.setBalance(npaList.get(m).getBalance());
                    pojo.setCustName(npaList.get(m).getCustName());
                    pojo.setDoubtFullDt(npaList.get(m).getDoubtFullDt());
                    pojo.setLossDt(npaList.get(m).getLossDt());
                    pojo.setPresentStatus(npaList.get(m).getPresentStatus());
                    pojo.setStandardDt(npaList.get(m).getStandardDt());
                    pojo.setSubStandardDt(npaList.get(m).getSubStandardDt());
                    resultList.add(pojo);
//                    System.out.println("m:" + npaList.get(m).getAcno() + "; " + npaList.get(m).getDoubtFullDt() + "; Bal:" + npaList.get(m).getBalance());
                }
            } else {
                Date npaDt = null;
                String fromDt = null, toDt = null;
                if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                    fromDt = CbsUtil.dateAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue());
                    toDt = CbsUtil.dateAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue());
                } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                    fromDt = CbsUtil.monthAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue());
                    toDt = CbsUtil.monthAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue());
                } else if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                    fromDt = CbsUtil.yearAdd(params.getDate(), new BigDecimal(params.getFromRange()).intValue());
                    toDt = CbsUtil.yearAdd(params.getDate(), new BigDecimal(params.getToRange()).intValue());
                }

                for (int m = 0; m < npaList.size(); m++) {
                    if (npaList.get(m).getPresentStatus().contains("SUB")) {
                        npaDt = dmy.parse(npaList.get(m).getSubStandardDt());
                    } else if (npaList.get(m).getPresentStatus().contains("DOU")) {
                        npaDt = dmy.parse(npaList.get(m).getDoubtFullDt());
                    } else if (npaList.get(m).getPresentStatus().contains("LOS")) {
                        npaDt = dmy.parse(npaList.get(m).getLossDt());
                    }
                    if (npaDt.after(ymd.parse(fromDt)) && (npaDt.equals(ymd.parse(toDt)) || npaDt.before(ymd.parse(toDt)))) {
                        totalNpaAmt = totalNpaAmt.add(npaList.get(m).getBalance());
                        totalAcNo = totalAcNo + 1;

                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                        pojo.setAcno(npaList.get(m).getAcno());
                        pojo.setBalance(npaList.get(m).getBalance());
                        pojo.setCustName(npaList.get(m).getCustName());
                        pojo.setDoubtFullDt(npaList.get(m).getDoubtFullDt());
                        pojo.setLossDt(npaList.get(m).getLossDt());
                        pojo.setPresentStatus(npaList.get(m).getPresentStatus());
                        pojo.setStandardDt(npaList.get(m).getStandardDt());
                        pojo.setSubStandardDt(npaList.get(m).getSubStandardDt());
                        resultList.add(pojo);
//                        System.out.println(acNature + "-" + params.getAcType() + ":" + npaList.get(m).getAcno() + ": Bal:" + totalNpaAmt + ":Actual bal:" + npaList.get(m).getBalance() + ": NpaDt:" + npaDt + ": FrDt:" + fromDt + ": ToDt:" + toDt + ":Status:" + npaList.get(m).getPresentStatus());
                    }
                }
            }

            List<AcctBalPojo> npaConList = new ArrayList<AcctBalPojo>();
            AcctBalPojo acctBal = new AcctBalPojo();
            acctBal.setNumberOFAcct(totalAcNo);
            acctBal.setBalance(totalNpaAmt);
            acctBal.setAcNo("");
            npaConList.add(acctBal);
            NpaConsolidateAndAcWisePojo NpaConsolidateAndAcWiseList = new NpaConsolidateAndAcWisePojo();
            NpaConsolidateAndAcWiseList.setPortConAcctBalList(npaConList);
            NpaConsolidateAndAcWiseList.setPortNpaAcStatusReportList(resultList);
            return NpaConsolidateAndAcWiseList;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<AcctBalPojo> getNpaAccountWithAmountList(AdditionalStmtPojo params) throws ApplicationException {
        try {
            String tableName = "", acCodeQuery = "", npaQuery = "", dtQuery = "", closingQ = "", npadt = "", tillDt = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                tableName = common.getTableName(acNature);
                if (params.getClassification().equalsIgnoreCase("L") || params.getClassification().equalsIgnoreCase("A")) {
                    acCodeQuery = " a.accttype in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "'))";
                } else {
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "'))";
                }

            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                if (params.getClassification().equalsIgnoreCase("L") || params.getClassification().equalsIgnoreCase("A")) {
                    acCodeQuery = " a.accttype in (select acctcode from accounttypemaster where acctcode in ('" + acctcode + "'))";
                } else {
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode in ('" + acctcode + "'))";
                }
            }
            if (params.getClassification().equalsIgnoreCase("11")) {             //SUB STANDARD
                npadt = " l.npadt ";
            } else if (params.getClassification().equalsIgnoreCase("12")) {       //DOUBTFUL
                npadt = " l.dbtdt ";
            } else if (params.getClassification().equalsIgnoreCase("13")) {       //LOSS
                npadt = " l.dcdt ";
            } else {                                                             //STANDARD
                npadt = " l.stadt ";
            }
            if (!params.getFromRange().equals("") && !params.getToRange().equals("")) {
                String frDt = "", toDt = "";
                String dtOneYearBack = CbsUtil.yearAdd(params.getDate(), -1);
                String dtThreeYearBack = CbsUtil.yearAdd(params.getDate(), -3);
                if (params.getToRange().equals("1.00")) {
                    frDt = CbsUtil.dateAdd(dtOneYearBack, 1);
                    toDt = params.getDate();
//                    System.out.println("==>>>" + params.getToRange());
                    npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + " between '" + frDt + "' and '" + toDt + "')";
                    dtQuery = " between '" + frDt + "' and '" + toDt + "'";
                    closingQ = " '" + toDt + "' ";
                    tillDt = " <='" + params.getDate() + "' ";
                } else if (params.getToRange().equals("3.00")) {
                    frDt = CbsUtil.dateAdd(dtThreeYearBack, 1);
                    toDt = dtOneYearBack;
//                    System.out.println(">>>>>==>>>" + params.getToRange());
                    npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + " between '" + frDt + "' and '" + toDt + "')";
                    dtQuery = " between '" + frDt + "' and '" + toDt + "'";
                    closingQ = " '" + params.getDate() + "'";
                    tillDt = " <='" + params.getDate() + "' ";
                } else if (params.getToRange().equals("99.00")) {
                    frDt = dtThreeYearBack;
//                    System.out.println(">>>>>>>>==>>>" + params.getToRange());
                    if (params.getClassification().equalsIgnoreCase("L") || params.getClassification().equalsIgnoreCase("A")) {
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where " + acCodeQuery + " and " + npadt + "<= '" + frDt + "')";
                    } else {
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + "<= '" + frDt + "')";
                    }

                    dtQuery = " <='" + frDt + "'";
                    closingQ = " '" + params.getDate() + "'";
                    tillDt = " <='" + params.getDate() + "' ";
                } else {
                    frDt = dtThreeYearBack;
//                    System.out.println(">>>>>>>>==>>>" + params.getToRange());
                    if (params.getClassification().equalsIgnoreCase("L") || params.getClassification().equalsIgnoreCase("A")) {
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where " + acCodeQuery + " and " + npadt + "<= '" + params.getDate() + "')";
                    } else {
                        npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where a.accstatus in('" + params.getClassification() + "') " + acCodeQuery + " and " + npadt + "<= '" + params.getDate() + "')";
                    }

                    dtQuery = " <='" + params.getDate() + "'";
                    closingQ = " '" + params.getDate() + "'";
                    tillDt = " <='" + params.getDate() + "' ";
                }
            } else {
                npaQuery = " and r.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where " + acCodeQuery + " and " + npadt + "<= '" + params.getDate() + "')";
                dtQuery = " <='" + params.getDate() + "'";
                closingQ = " '" + params.getDate() + "'";
                tillDt = " <='" + params.getDate() + "' ";
            }

            List dataList = new ArrayList();
            String executionQuery = "";
            if (params.getBrnCode().equalsIgnoreCase("0A")) {
                executionQuery = "select distinct a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(25,2)) from " + tableName + " r,accountmaster a where "
                        + "r.acno in (select acno from " + tableName + " where dt " + tillDt + ") "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > " + closingQ + " ) and r.acno = a.acno and dt " + tillDt + npaQuery + " group by a.acno";

//                System.out.println("Execution-Query :" + executionQuery + "\n");
                dataList = em.createNativeQuery(executionQuery).getResultList();
            } else {
                executionQuery = "select distinct a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(25,2)) from " + tableName + " r,accountmaster a where "
                        + "R.acno in (select acno from " + tableName + " WHERE DT<= '" + params.getDate() + "' ) "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > " + closingQ + " ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt " + tillDt + npaQuery + " group by a.acno";

//                System.out.println("Execution-Query :" + executionQuery + "\n");
                dataList = em.createNativeQuery(executionQuery).getResultList();
            }
            List<AcctBalPojo> acctBalList = new ArrayList<AcctBalPojo>();
            for (int i = 0; i < dataList.size(); i++) {
                AcctBalPojo acctBal = new AcctBalPojo();
                Vector ele = (Vector) dataList.get(i);
                acctBal.setAcNo(ele.get(0).toString());
                acctBal.setBalance(new BigDecimal(ele.get(1).toString()));
                acctBalList.add(acctBal);
//                System.out.println(acNature + "-" + params.getAcType() + "=>>>" + params.getToRange() + "; Bal:" + new BigDecimal(ele.get(1).toString()) + "; " + "");
            }
            return acctBalList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public BigDecimal provisionAccordingToLoan(AdditionalStmtPojo params) throws ApplicationException {
        try {
            BigDecimal outStanding = new BigDecimal("0");
            BigDecimal provAmt = new BigDecimal("0");
            String delinqCycle = "", delinqCycle1 = "", doubtDt = "", assetLiabQuery = "", tableName = "", flag = "";

            int al = 0;
            if (params.getClassification().equals("L")) {
                al = 1;
            } else if (params.getClassification().equals("A")) {
                al = -1;
            }
            /*
             * Only for Non-NPA account outstanding as on date
             */
            if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
                delinqCycle = "STD";
            }/*
             * Only for NPA account outstanding as on date
             */ else if (params.getNature().contains("NPA") || params.getAcType().contains("NPA") || params.getNpaClassification().contains("11") || params.getNpaClassification().contains("12") || params.getNpaClassification().contains("13")) {
                if (params.getNpaClassification().equalsIgnoreCase("11")) {
                    delinqCycle = "SUB";
                } else if (params.getNpaClassification().equalsIgnoreCase("12")) {
                    delinqCycle = "D";
                } else if (params.getNpaClassification().equalsIgnoreCase("13")) {
                    delinqCycle = "L";
                }
            } else {
                delinqCycle = "STD";
            }

            if (delinqCycle.equalsIgnoreCase("STD")) {
                AcctBalPojo acctBal = new AcctBalPojo();
                acctBal = getAcctsAndBal(params);//getNpaAccountWithAmountList(params);
                String acNo = acctBal.getAcNo();
                String schemeCode = "CA001";
                if (acNo.equalsIgnoreCase("000000000000")) {
                    schemeCode = schemeCode;
                } else {
                    schemeCode = loanReportFacade.getSchemeCodeAcNoWise(acNo);
                }

                outStanding = acctBal.getBalance();
                if (em.createNativeQuery("select acno from loansecurity where acno = '" + acNo + "'").getResultList().size() > 0) {
                    flag = "S";
                } else {
                    flag = "U";
                }
//                          if(delinqCycle.equalsIgnoreCase("STD")){
                if (params.getFlag().equalsIgnoreCase("P")) {
                    delinqCycle1 = delinqCycle;
//                    System.out.println("acno:" + acNo + "; scheme:" + schemeCode + "; flag:" + flag + "; delinqCycle1:" + delinqCycle1);
                    List delinquencyCycleList = em.createNativeQuery("select  prov_in_percent from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ").getResultList();
                    if (delinquencyCycleList.size() > 0) {
                        Vector delinVect = (Vector) delinquencyCycleList.get(0);
                        BigDecimal provInPer = new BigDecimal(delinVect.get(0).toString());
                        provAmt = (outStanding.multiply(provInPer).divide(new BigDecimal("100"))).add(provAmt);
                    } else {
                        throw new ApplicationException("scheme_code does not exist in cbs_scheme_delinquency_details for A/c No.:" + acNo);
                    }
                } else if (params.getFlag().equalsIgnoreCase("GL")) {
                    List<RbiSossPojo> resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3PROVS", doubtDt, "0A", reptOpt, "Y");
                    if (!resultListSoss1.isEmpty()) {
                        for (int z = 0; z < resultListSoss1.size(); z++) {
                            provAmt = provAmt.add(resultListSoss1.get(z).getAmt());
                        }
                    }
                    resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3PROVD1", doubtDt, "0A", reptOpt, "Y");
                    if (!resultListSoss1.isEmpty()) {
                        for (int z = 0; z < resultListSoss1.size(); z++) {
                            provAmt = provAmt.add(resultListSoss1.get(z).getAmt());
                        }
                    }
                    provAmt = (outStanding).add(provAmt);
                } else {
                    provAmt = (outStanding).add(provAmt);
                }
            } else {
                NpaConsolidateAndAcWisePojo NpaConsolidateAndAcWise = getAmountBasedOnNpaClassification(params);
                //   List<NpaStatusReportPojo> acctBalList = new ArrayList<NpaStatusReportPojo>();
                for (int k = 0; k < NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().size(); k++) {
                    String acNo = NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getAcno();
                    String schemeCode = loanReportFacade.getSchemeCodeAcNoWise(acNo);
                    outStanding = NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getBalance();
//                    if (em.createNativeQuery("select acno from loansecurity where acno = '" + acNo + "'").getResultList().size() > 0) {
                    List secured = em.createNativeQuery("select secured from cbs_loan_borrower_details where acc_no = '" + acNo + "'").getResultList();
                    if (!secured.isEmpty()) {
                        Vector secVect = (Vector) secured.get(0);
                        if (secVect.get(0).toString().equalsIgnoreCase("FLSEC")) {
                            flag = "S";
                        } else {
                            flag = "U";
                        }
                    }

                    List doubtAcnoList = null;
                    if (delinqCycle.contains("D")) {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getDoubtFullDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(dbtdt,'') from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else if (delinqCycle.equalsIgnoreCase("SUB")) {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getSubStandardDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(npadt,'') from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else if (delinqCycle.equalsIgnoreCase("L")) {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getLossDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(dcdt,'') from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getStandardDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(lp.stadt, a.OpeningDt) from loan_appparameter lp, accountmaster a  where a.acno = lp.Acno and a.acno = '" + acNo + "'").getResultList();
                    }

                    if (doubtDt.equalsIgnoreCase("")) {
                        doubtAcnoList = em.createNativeQuery("select ifnull(OpeningDt,'') from accountmaster where acno = '" + acNo + "'").getResultList();
                        Vector doubtDtvect = (Vector) doubtAcnoList.get(0);
                        doubtDt = doubtDtvect.get(0).toString();
                        //doubtDt = params.getDate();
                    }

                    Long dayDiff = CbsUtil.dayDiff(ymd.parse(doubtDt), ymd.parse(params.getDate()));
                    List delinquencyCycleList = em.createNativeQuery("select delinq_cycle, days_past_due from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle.concat("%") + "' order by cast(days_past_due as unsigned)").getResultList();
                    if (delinquencyCycleList.size() > 0) {
                        for (int j = 0; j < delinquencyCycleList.size(); j++) {
                            Vector delinVect = (Vector) delinquencyCycleList.get(j);
                            delinqCycle1 = delinVect.get(0).toString();
                            Long daysPastDue = Long.parseLong(delinVect.get(1).toString());
//                            System.out.println("???? acno:"+acNo+" ; delinqCycle:"+delinqCycle+" ; del1:"+delinqCycle1+"; dayspastdue:"+daysPastDue+"; daydiff:"+dayDiff);
                            if (dayDiff <= daysPastDue) {
                                delinqCycle1 = delinqCycle1;
                                if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                                    Long dayDiff1 = dayDiff / 365;
                                    if (/*dayDiff1 > new BigDecimal(params.getFromRange()).longValue() && */dayDiff1 <= new BigDecimal(params.getFromRange()).abs().longValue()) {
                                        outStanding = outStanding;
                                    } else {
                                        outStanding = new BigDecimal("0");
                                    }
                                } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                                    Long dayDiff1 = dayDiff / 12;
                                    if (/*dayDiff1 > new BigDecimal(params.getFromRange()).longValue() && */dayDiff1 <= new BigDecimal(params.getFromRange()).abs().longValue()) {
                                        outStanding = outStanding;
                                    } else {
                                        outStanding = new BigDecimal("0");
                                    }
                                } else if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                                    Long dayDiff1 = dayDiff;
                                    if (/*dayDiff1 > new BigDecimal(params.getFromRange()).longValue() &&*/dayDiff1 <= new BigDecimal(params.getFromRange()).abs().longValue()) {
                                        outStanding = outStanding;
                                    } else {
                                        outStanding = new BigDecimal("0");
                                    }
                                } else {
                                    outStanding = outStanding;
                                }

                                j = delinquencyCycleList.size();
                            }
                        }
                    }

                    if (params.getFlag().equalsIgnoreCase("P")) {
//                        System.out.println("acno:" + acNo + "; scheme:" + schemeCode + "; flag:" + flag + "; delinqCycle1:" + delinqCycle1);
                        delinquencyCycleList = em.createNativeQuery("select  prov_in_percent from  cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ").getResultList();
                        if (delinquencyCycleList.size() > 0) {
                            Vector delinVect = (Vector) delinquencyCycleList.get(0);
                            BigDecimal provInPer = new BigDecimal(delinVect.get(0).toString());
                            provAmt = (outStanding.multiply(provInPer).divide(new BigDecimal("100"))).add(provAmt);
//                            System.out.println("Acno:" + acNo + "; delinq:" + delinqCycle + "; prov %:" + provInPer + "; outstd:" + outStanding + "; proAmt:" + provAmt);
                        } else {
//                            System.out.println("query:select  prov_in_percent from  cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ");
                            throw new ApplicationException("scheme_code does not exist in cbs_scheme_delinquency_details for A/c No.:" + acNo);
                        }
                    } else {
                        provAmt = (outStanding).add(provAmt);
                    }

                }
            }
            return provAmt;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public Object[] getQuarterEndDtForCurDt(String brncode) throws ApplicationException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            String fYearEndDate = "", qEndDate = "";
            YearEndDatePojo yearEndDtPojo = getYearEndData(brncode);
            fYearEndDate = yearEndDtPojo.getMaxDate();
            List<String> qEndDateList = new CopyOnWriteArrayList<String>();
            qEndDate = fYearEndDate;
            qEndDateList.add(qEndDate);
            for (int i = 0; i < 3; i++) {
                qEndDate = CbsUtil.monthAdd(qEndDate, -3);
                qEndDateList.add(qEndDate);
            }
            for (String qEndDtLoop : qEndDateList) {
                /**
                 * Only qEndDtLoop <= curdate should be in the list.
                 */
                if (ymd.parse(qEndDtLoop).compareTo(ymd.parse(ymd.format(new Date()))) == 1) {
                    qEndDateList.remove(qEndDtLoop);
                }
            }

            Object[] arr = qEndDateList.toArray();
            Arrays.sort(arr);
            return arr;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<GlHeadPojo> accountHavingSanctionAsPerAmount(String reportDt, String acNature, String acType, Double amount, String remarks) throws ApplicationException {
        List<GlHeadPojo> list = new ArrayList<GlHeadPojo>();
        String acCodeQuery = "";
        try {
            if (!acNature.equals("")) {
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!acType.equals("")) {
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acType + "')";
            }
            List dataList = em.createNativeQuery("select l.acno,cast(ifnull(l.sanctionlimit,0) as decimal(25,2)),a.custname "
                    + "from  loan_appparameter l,accountmaster a where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + reportDt + "') "
                    + acCodeQuery + " and l.acno = a.acno and l.acno in(select acno from loan_appparameter where sanctionlimit >=" + amount + ") "
                    + "and a.accstatus not in('11','12','13')").getResultList();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    GlHeadPojo pojo = new GlHeadPojo();
                    Vector element = (Vector) dataList.get(i);
                    pojo.setGlHead(element.get(0).toString());
                    pojo.setBalance(new BigDecimal(element.get(1).toString()));
                    pojo.setGlName(element.get(2).toString());
                    pojo.setRemarks(remarks);

                    list.add(pojo);
                }
            }
        } catch (Exception ex) {
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
            List dataList = em.createNativeQuery("select a.acno,a.custname, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) "
                    + "as amt,a.accstatus as accstatus from " + tableName + " r,accountmaster a where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) "
                    + "and r.acno = a.acno and r.dt <='" + params.getDate() + "' " + acCodeQuery + " and "
                    + "a.acno in (select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where "
                    + "a.accstatus not in('11','12','13') " + acCodeQuery + " union select a.acno from accountmaster a "
                    + "left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and "
                    + "l.npadt>'" + params.getDate() + "') group by a.acno, a.custname,a.accstatus union all select a.acno, a.custname,"
                    + "cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt,a.accstatus as accstatus from npa_recon r,accountmaster a "
                    + "where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and "
                    + "r.acno = a.acno and r.dt <='" + params.getDate() + "' " + acCodeQuery + " and a.acno in "
                    + "(select a.acno from accountmaster a left join loan_appparameter l on a.acno=l.acno where "
                    + "a.accstatus not in('11','12','13') " + acCodeQuery + " union select a.acno from accountmaster a "
                    + "left join loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " "
                    + "and l.npadt>'" + params.getDate() + "' union select a.acno from accountmaster a left join "
                    + "loan_appparameter l on a.acno=l.acno where a.accstatus in('11','12','13') " + acCodeQuery + " and "
                    + "l.npadt<='" + params.getDate() + "') group by a.acno, a.custname,a.accstatus").getResultList();
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
    
    public List<GlHeadPojo> getOverDueAccountInBetweenDateAndTheirOutStandingBal(String frDt, String toDt, String acNature, String acType, String absParam, String tillDate, int rangeFrom, int rangeTo) throws ApplicationException {
        List<GlHeadPojo> list = new ArrayList<GlHeadPojo>();
        String tableName = "", acCodeQuery = "";
        List dataList = null;
        try {
            if (!acNature.equals("")) {
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!acType.equals("")) {
                acNature = common.getAcNatureByAcType(acType);
                tableName = common.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acType + "')";
            }
            if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                dataList = em.createNativeQuery("select a.acno,a.openingdt, ifnull(l.loan_pd_month,12) as period, date_add(openingdt, interval l.loan_pd_month month) as expdt, timestampdiff(month, date_add(openingdt, interval l.loan_pd_month month) ,'" + tillDate + "') as diff, "
                        + " a.custname from accountmaster a,cbs_loan_acc_mast_sec l where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + tillDate + "') "
                        + acCodeQuery + " AND a.openingdt<= '" + tillDate + "'  AND "
                        + " a.acno=l.acno and date_add(openingdt, interval l.loan_pd_month month) <= '" + tillDate + "' AND "
                        + " a.acno not in (select a.acno from accountstatus a,"
                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + tillDate + "' group by acno) b,"
                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + tillDate + "' group by acno) c ,"
                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and a.effdt = b.edt "
                        + "and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + tillDate + "' and "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + tillDate + "')  ) ").getResultList();

                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        GlHeadPojo pojo = new GlHeadPojo();
                        Vector element = (Vector) dataList.get(i);
                        String accountNo = element.get(0).toString();
                        String openingDt = element.get(1).toString();
                        int loanPd = Integer.parseInt(element.get(2).toString());
                        String expireDt = element.get(3).toString();
                        int diffOpeningDtExpDt = Integer.parseInt(element.get(4).toString());
                        String custName = element.get(5).toString();
                        SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                        if (loanPd == 0) {
                            loanPd = 12;
                            expireDt = CbsUtil.dateAdd(openingDt, loanPd);
                            diffOpeningDtExpDt = CbsUtil.monthDiff(ymmd.parse(expireDt), ymmd.parse(tillDate));
                        }
                        if (diffOpeningDtExpDt <= new BigDecimal(rangeFrom).abs().intValue() && diffOpeningDtExpDt > new BigDecimal(rangeTo).abs().intValue()) {
                            List dlCrBalList = em.createNativeQuery("select cast(ifnull(sum(cramt),0) as decimal(25,2)) as bal from " + tableName + " where acno='" + accountNo + "' and dt between '" + frDt + "' and '" + toDt + "' ").getResultList();
                            Vector dlCrBalVect = (Vector) dlCrBalList.get(0);
                            BigDecimal dlCrAmt = new BigDecimal(dlCrBalVect.get(0).toString());
                            if (dlCrAmt.compareTo(new BigDecimal("0")) == 0) {
                                List balList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(25,2)) as bal from " + tableName + " where acno='" + accountNo + "' and dt<='" + tillDate + "'").getResultList();
                                element = (Vector) balList.get(0);
                                if (absParam.equalsIgnoreCase("Y")) {
                                    pojo.setBalance(new BigDecimal(element.get(0).toString()).abs());
                                } else {
                                    pojo.setBalance(new BigDecimal(element.get(0).toString())); //Abs() which is removed by Alok
                                }
                                pojo.setGlHead(accountNo);
                                pojo.setGlName(custName);
                                pojo.setRemarks(absParam);
                                list.add(pojo);
                            }
                        }
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                dataList = em.createNativeQuery("select distinct(a.acno),a.custname from " + tableName + " r,accountmaster a where (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDt + "') "
                        + "and r.acno = a.acno " + acCodeQuery + " and r.dt between '" + frDt + "' and '" + toDt + "' and r.acno in (select acno from " + tableName + " where "
                        + "dt between '" + frDt + "' and '" + toDt + "' group by acno having sum(cramt) = 0) and r.acno "
                        + " not in (select a.acno from accountstatus a,"
                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + tillDate + "' group by acno) b,"
                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + tillDate + "' group by acno) c ,"
                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and a.effdt = b.edt "
                        + "and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + tillDate + "' and "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + tillDate + "') )").getResultList();
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        GlHeadPojo pojo = new GlHeadPojo();
                        Vector element = (Vector) dataList.get(i);
                        String accountNo = element.get(0).toString();
                        pojo.setGlHead(accountNo);
                        pojo.setGlName(element.get(1).toString());
                        List balList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(25,2)) as bal from " + tableName + " where acno='" + accountNo + "' and dt<='" + tillDate + "'").getResultList();
                        element = (Vector) balList.get(0);
                        if (absParam.equalsIgnoreCase("Y")) {
                            pojo.setBalance(new BigDecimal(element.get(0).toString()).abs());
                        } else {
                            pojo.setBalance(new BigDecimal(element.get(0).toString())); //Abs() which is removed by Alok
                        }
                        pojo.setRemarks(absParam);
                        list.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }
    
    /**
     * ***********========GETTING THE OUTSTANDING AMOUNT TILL DATE (+ive and
     * -ive both)========************
     */
    private String outStandingAsOnDate(String acNo, String tillDate) throws ApplicationException {
        String result = "";
        try {
            /**
             * ---Account is exists in Loan Secondary Table---*
             */
            String acNature;
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";

            List acNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + fTSPosting43CBSBean.getAccountCode(acNo) + "'").getResultList();
            if (acNatureList.isEmpty()) {
                return result = "Account Nature doesn't exists.";
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            List SecDetailsList = em.createNativeQuery("select acno, scheme_code, interest_table_code, moratorium_pd, acc_pref_dr, "
                    + "acc_pref_cr, rate_code, disb_type, calc_method, calc_on, int_app_freq, calc_level, compound_freq, ifnull(pegg_freq,0), "
                    + "loan_pd_month, loan_pd_day  from cbs_loan_acc_mast_sec where acno ='" + acNo + "'").getResultList();

            if (SecDetailsList.isEmpty()) {
                return result = "Account No Does Not Exists in Secondary Details table of Loan.";
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String intAppFreq = (String) SecDetailsVect.get(10);
                    List outStDrAmtList = null;
                    Vector outStDrAmtVect;
                    double drAmt = 0;
                    double outSt = 0;
                    if (intAppFreq.equalsIgnoreCase("S")) {
                        /* SIMPLE */
                        /*==Getting the DR Amount till Date==*/
                        /**
                         * *SIMPLE: Trandesc in (0,1,2,5,6,7,8,9,66) **
                         */
                        List tranDescSimpleList = em.createNativeQuery("select code from cbs_parameterinfo where name in ('INTCALSIMPLE')").getResultList();
                        if (tranDescSimpleList.isEmpty()) {
                            throw new ApplicationException("Please Check data  is exists for INTCALSIMPLE in CBS_PARAMETERINFO Table");
                        } else {
                            Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                            tranDescSimple = tranDescSimpleVect.get(0).toString();
                        }
                        List npaList = em.createNativeQuery("select code from parameterinfo_report where reportname in ('NPAINT')").getResultList();
                        if (npaList.isEmpty()) {
                            if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                npaQuery = "  union All select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from npa_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' group by acno ";
                            } else {
                                npaQuery = "  union All select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from npa_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by acno ";
                            }
                        } else {
                            npaQuery = "";
                        }
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            outStDrAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from "
                                    + "ca_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' group by  acno "
                                    + npaQuery).getResultList();

                            if (outStDrAmtList.isEmpty()) {
                                result = "No tranction done in this Account.";
                            } else {
                                for (int j = 0; j < outStDrAmtList.size(); j++) {
                                    outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                    drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                                }

                            }
                            outSt = drAmt;
                        } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            outStDrAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from "
                                    + "loan_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' group by  acno "
                                    + npaQuery).getResultList();

                            if (outStDrAmtList.isEmpty()) {
                                result = "No tranction done in this Account.";
                            } else {
                                for (int j = 0; j < outStDrAmtList.size(); j++) {
                                    outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                    drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                                }
                            }
                            outSt = drAmt;
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            /*==Getting the DR Amount till Date==*/
                            /**
                             * Getting the collection amount if previous demand
                             * overflowed
                             */
                            outStDrAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from "
                                    + "loan_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' and "
                                    + "tranDesc in (" + tranDescSimple + ") group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
                                result = "No tranction done in this Account.";
                            } else {
                                for (int j = 0; j < outStDrAmtList.size(); j++) {
                                    outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                    drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                                }
                            }
                            outSt = drAmt;
                        }
                    } else if (intAppFreq.equalsIgnoreCase("C")) {
                        /* COMPOUNDING */
                        /*==Getting the Outstanding Amount till Date==*/
                        /**
                         * *COMPOUNDING: tranDesc in (0,1,2,3,4,5,6,7,8,9,66)**
                         */
                        List tranDescCompList = em.createNativeQuery("select code from cbs_parameterinfo where name in ('INTCALCOMP')").getResultList();
                        if (tranDescCompList.isEmpty()) {
                            throw new ApplicationException("Please Check data  is exists for INTCALCOMP in CBS_PARAMETERINFO Table");
                        } else {
                            Vector tranDescCompVect = (Vector) tranDescCompList.get(0);
                            tranDescComp = tranDescCompVect.get(0).toString();
                        }
                        List npaList = em.createNativeQuery("select code from parameterinfo_report where reportname in ('NPAINT')").getResultList();
                        if (npaList.isEmpty()) {
                            if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                npaQuery = " union all select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from npa_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' group by acno ";
                            } else {
                                npaQuery = " union all select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from npa_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by acno ";
                            }
                        } else {
                            npaQuery = "";
                        }
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            outStDrAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from "
                                    + "ca_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
                                result = "No tranction done in this Account.";
                            } else {
                                for (int j = 0; j < outStDrAmtList.size(); j++) {
                                    outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                    drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                                }
                            }
                            outSt = drAmt;
                        } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            outStDrAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from "
                                    + "loan_recon where acno = '" + acNo + "' and valuedt <= '" + tillDate + "' and auth = 'Y' group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
                                result = "No tranction done in this Account.";
                            } else {
                                for (int j = 0; j < outStDrAmtList.size(); j++) {
                                    outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                    drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                                }
                            }
                            outSt = drAmt;
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            /*==Getting the DR Amount till Date==*/
                            /**
                             * Getting the collection amount if previouse demand
                             * overflowed
                             */
                            outStDrAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM "
                                    + "LOAN_RECON WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y'  group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
                                result = "No tranction done in this Account.";
                            } else {
                                for (int j = 0; j < outStDrAmtList.size(); j++) {
                                    outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                    drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                                }
                            }
                            outSt = drAmt;
                        }
                    }
                    result = Double.toString(outSt);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public long getAccountStatusReport(String date, String brnCode, String acctCode, String acNat, String classification) throws ApplicationException {
        try {
            brnCode = brnCode.equalsIgnoreCase("90") ? "0A" : brnCode;
            String acctNature = "", asPerClassQuery = "";
            long acnoActiveCount = 0;
            String gType = "", acTypeQuery = "";
            if (!acctCode.equalsIgnoreCase("")) {
                acTypeQuery = " acctcode = '" + acctCode + "' ";
                acctNature = common.getAcNatureByAcType(acctCode);
            } else if (!acNat.equalsIgnoreCase("")) {
                acTypeQuery = " acctnature = '" + acNat + "' ";
                acctNature = acNat;
            }

            if (classification.equalsIgnoreCase("L")) {
                asPerClassQuery = " and acno not in (select distinct acno from ca_recon where substring(acno,3,2) in (select acctcode from accounttypemaster where " + acTypeQuery + ")  and dt<='" + date + "'"
                        + " group by acno having sign(cast(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2))) < 0)";
            } else if (classification.equalsIgnoreCase("A")) {
                asPerClassQuery = " and acno in (select distinct acno from ca_recon where substring(acno,3,2) in (select acctcode from accounttypemaster where " + acTypeQuery + ") and  dt<='" + date + "' "
                        + " group by acno having sign(cast(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2))) < 0)";
            }
            if (acctNature.equalsIgnoreCase("FD") || (acctNature.equalsIgnoreCase("MS")) || acctNature.equalsIgnoreCase("RD") || acctNature.equalsIgnoreCase("DS") || acctNature.equalsIgnoreCase("SB")) {
                if (acctNature.equalsIgnoreCase("FD") || acctNature.equalsIgnoreCase("MS")) {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM td_accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                        }
                    } else {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM td_accountmaster WHERE curbrcode= '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                        }
                    }
                } else {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                        }
                    } else {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                        }
                    }
                }
            } else {
                if (acctNature.equalsIgnoreCase("CA")) {
                    if (!classification.equalsIgnoreCase("")) {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ") " + asPerClassQuery).getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                            }
                        } else {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ") " + asPerClassQuery).getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                            }
                        }
                    } else {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                            }
                        } else {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                            }
                        }
                    }
                } else {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                        }
                    } else {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT COUNT(acno) as acNo FROM accountmaster WHERE curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype in (select acctcode from accounttypemaster where " + acTypeQuery + ")").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            acnoActiveCount = Long.parseLong(ele2.get(0).toString());
                        }
                    }
                }
            }
            return acnoActiveCount;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<ProvDetailOfLoanAccounts> getProvisionAccordingToLoan(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<ProvDetailOfLoanAccounts> ProvisionAcctoLoan = new ArrayList<ProvDetailOfLoanAccounts>();
            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", ymd.format(dmy.parse(dt)), "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode, "Y");
            List ossQuery = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, refer_index, refer_content from cbs_ho_rbi_stmt_report where "
                    + "report_name = 'SOSS3.PART-A' and CLASSIFICATION in('A','L') AND F_SSS_GNO <> 'GLPD' and FORMULA =''  order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned), classification desc, sno ").getResultList();
            if (ossQuery.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            } else {
                for (int i = 0; i < ossQuery.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector ossVect = (Vector) ossQuery.get(i);
                    Integer sNo = Integer.parseInt(ossVect.get(0).toString());
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
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(ymd.format(dmy.parse(dt)));
                    params.setToDate(ymd.format(dmy.parse(dt)));
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
                    List<ProvDetailOfLoanAccounts> acctBal = provisionAccordingToLoanAcwise(params);
                    for (int j = 0; j < acctBal.size(); j++) {
                        BigDecimal Provbal = new BigDecimal(0.0);
                        BigDecimal Grossbal = new BigDecimal(0.0);
                        bal = new BigDecimal("0");
                        String acno = acctBal.get(j).getAcno();
                        String custName = acctBal.get(j).getCustName();
                        String nPADate = acctBal.get(j).getNpadt();
                        String category = acctBal.get(j).getCategory();
                        String subcategory = acctBal.get(j).getSubcategory();
                        BigDecimal provPer = acctBal.get(j).getProvper();
//                        if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                        grossAmt = acctBal.get(j).getOutstanding();
//                        }
//                        if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA") || params.getNpaClassification().equals("11") || params.getNpaClassification().equals("12") || params.getNpaClassification().equals("13")) {
//                            if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
//                                params.setFlag("P");
//                            } else {
//                                params.setFlag("P");
//                            }
                        provAmt = acctBal.get(j).getProvamt();
//                        } else if (params.getClassification().equalsIgnoreCase("A") && (!params.getNature().equals("") || !params.getAcType().equals(""))) {
//                            params.setFlag("P");
//                            provAmt = acctBal.get(j).getProvamt();
//                        }
                        ProvDetailOfLoanAccounts paramsClassification = new ProvDetailOfLoanAccounts();
                        paramsClassification.setAcno(acno);
                        paramsClassification.setCustName(custName);
                        paramsClassification.setCategory(category);
                        paramsClassification.setProvper(provPer);
                        paramsClassification.setNpadt(nPADate);
                        paramsClassification.setSubcategory(subcategory);
                        if (grossAmt.compareTo(new BigDecimal("0.00")) == 0) {
//                            paramsClassification.setOutstanding(grossAmt);
                        } else {
                            paramsClassification.setOutstanding(new BigDecimal(formatter.format(grossAmt.divide(repOpt).multiply(new BigDecimal("-1")).doubleValue())));//Gross Outstanding);
                        }
                        if (provAmt.compareTo(new BigDecimal("0.00")) == 0) {
//                            paramsClassification.setProvamt(provAmt);
                        } else {
                            paramsClassification.setProvamt(new BigDecimal(formatter.format(provAmt.divide(repOpt).multiply(new BigDecimal("-1")).doubleValue())));//Provision/Deduction);
                        }
                        ProvisionAcctoLoan.add(paramsClassification);
                    }
                }
            }
            return ProvisionAcctoLoan;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<ProvDetailOfLoanAccounts> provisionAccordingToLoanAcwise(AdditionalStmtPojo params) throws ApplicationException {
        try {
            BigDecimal outStanding = new BigDecimal("0");
            BigDecimal provAmt = new BigDecimal("0");
            BigDecimal provInPer = new BigDecimal("0");
            String delinqCycle = "", delinqCycle1 = "", doubtDt = "", assetLiabQuery = "", tableName = "", flag = "";

            List<ProvDetailOfLoanAccounts> AcctbalAccwise = new ArrayList<ProvDetailOfLoanAccounts>();

            int al = 0;
            if (params.getClassification().equals("L")) {
                al = 1;
            } else if (params.getClassification().equals("A")) {
                al = -1;
            }
            /*
             * Only for Non-NPA account outstanding as on date
             */
            if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
                delinqCycle = "STD";
            }/*
             * Only for NPA account outstanding as on date
             */ else if (params.getNature().contains("NPA") || params.getAcType().contains("NPA") || params.getNpaClassification().contains("11") || params.getNpaClassification().contains("12") || params.getNpaClassification().contains("13")) {
                if (params.getNpaClassification().equalsIgnoreCase("11")) {
                    delinqCycle = "SUB";
                } else if (params.getNpaClassification().equalsIgnoreCase("12")) {
                    delinqCycle = "D";
                } else if (params.getNpaClassification().equalsIgnoreCase("13")) {
                    delinqCycle = "L";
                }
            } else {
                delinqCycle = "STD";
            }

            if (delinqCycle.equalsIgnoreCase("STD")) {
                List<AcctBalPojo> acctBal1 = getAcctsAndBalAcwise(params);//getNpaAccountWithAmountList(params);
                for (int l = 0; l < acctBal1.size(); l++) {
                    String Acno1 = acctBal1.get(l).getAcNo();
                    String schemeCode = "CA001";
                    if (Acno1.equalsIgnoreCase("000000000000")) {
                        schemeCode = schemeCode;
                    } else {
                        schemeCode = loanReportFacade.getSchemeCodeAcNoWise(Acno1);
                    }
                    outStanding = acctBal1.get(l).getBalance();
                    if (em.createNativeQuery("select acno from loansecurity where acno = '" + Acno1 + "'").getResultList().size() > 0) {
                        flag = "S";
                    } else {
                        flag = "U";
                    }
                    if (params.getFlag().equalsIgnoreCase("P")) {
                        delinqCycle1 = delinqCycle;
                        List delinquencyCycleList = em.createNativeQuery("select  prov_in_percent from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ").getResultList();
                        if (delinquencyCycleList.size() > 0) {
                            Vector delinVect = (Vector) delinquencyCycleList.get(0);
                            provInPer = new BigDecimal(delinVect.get(0).toString());
                            if (!(outStanding.compareTo(new BigDecimal("0.0")) == 0)) {
                                provAmt = (outStanding.multiply(provInPer).divide(new BigDecimal("100")));
                            } else if (outStanding.compareTo(new BigDecimal("0.0")) == 0) {
                                provAmt = new BigDecimal("0");
                            }

                        } else {
                            throw new ApplicationException("scheme_code does not exist in cbs_scheme_delinquency_details for A/c No.:" + Acno1);
                        }
                    } else if (params.getFlag().equalsIgnoreCase("GL")) {
                        List<RbiSossPojo> resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3PROVS", doubtDt, "0A", reptOpt, "Y");
                        if (!resultListSoss1.isEmpty()) {
                            for (int z = 0; z < resultListSoss1.size(); z++) {
                                provAmt = provAmt.add(resultListSoss1.get(z).getAmt());
                            }
                        }
                        resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3PROVD1", doubtDt, "0A", reptOpt, "Y");
                        if (!resultListSoss1.isEmpty()) {
                            for (int z = 0; z < resultListSoss1.size(); z++) {
                                provAmt = provAmt.add(resultListSoss1.get(z).getAmt());
                            }
                        }
                        provAmt = (outStanding).add(provAmt);
                    } else {
                        delinqCycle1 = delinqCycle;
                        List delinquencyCycleList = em.createNativeQuery("select  prov_in_percent from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ").getResultList();
                        if (delinquencyCycleList.size() > 0) {
                            Vector delinVect = (Vector) delinquencyCycleList.get(0);
                            provInPer = new BigDecimal(delinVect.get(0).toString());
                            provAmt = (outStanding.multiply(provInPer).divide(new BigDecimal("100")));
                        }
                    }
                    ProvDetailOfLoanAccounts acctBal = new ProvDetailOfLoanAccounts();
                    acctBal.setAcno(Acno1);
                    acctBal.setCustName(ftsFacade.getCustName(Acno1));
                    acctBal.setOutstanding(outStanding);
                    acctBal.setProvamt(provAmt);
                    acctBal.setProvper(provInPer);
                    if (delinqCycle.equalsIgnoreCase("STD")) {
                        acctBal.setCategory("1");
                    }
                    acctBal.setSubcategory("");
                    acctBal.setNpadt("");
                    AcctbalAccwise.add(acctBal);
                }
            } else {
                NpaConsolidateAndAcWisePojo NpaConsolidateAndAcWise = getAmountBasedOnNpaClassification(params);
                for (int k = 0; k < NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().size(); k++) {
                    String acNo = NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getAcno();
                    String schemeCode = loanReportFacade.getSchemeCodeAcNoWise(acNo);
                    outStanding = NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getBalance();
//                    if (em.createNativeQuery("select acno from loansecurity where acno = '" + acNo + "'").getResultList().size() > 0) {
                    List secured = em.createNativeQuery("select secured from cbs_loan_borrower_details where acc_no = '" + acNo + "'").getResultList();
                    if (!secured.isEmpty()) {
                        Vector vect = (Vector) secured.get(0);
                        if (vect.get(0).toString().equalsIgnoreCase("FLSEC")) {
                            flag = "S";
                        } else {
                            flag = "U";
                        }
                    }

                    List doubtAcnoList = null;
                    if (delinqCycle.contains("D")) {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getDoubtFullDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(dbtdt,'') from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else if (delinqCycle.equalsIgnoreCase("SUB")) {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getSubStandardDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(npadt,'') from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else if (delinqCycle.equalsIgnoreCase("L")) {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getLossDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(dcdt,'') from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else {
                        doubtDt = ymd.format(dmy.parse(NpaConsolidateAndAcWise.getPortNpaAcStatusReportList().get(k).getStandardDt()));
                        doubtAcnoList = em.createNativeQuery("select ifnull(lp.stadt, a.OpeningDt) from loan_appparameter lp, accountmaster a  where a.acno = lp.Acno and a.acno = '" + acNo + "'").getResultList();
                    }

                    if (doubtDt.equalsIgnoreCase("")) {
                        doubtAcnoList = em.createNativeQuery("select ifnull(OpeningDt,'') from accountmaster where acno = '" + acNo + "'").getResultList();
                        Vector doubtDtvect = (Vector) doubtAcnoList.get(0);
                        doubtDt = doubtDtvect.get(0).toString();
                    }

                    Long dayDiff = CbsUtil.dayDiff(ymd.parse(doubtDt), ymd.parse(params.getDate()));
                    List delinquencyCycleList = em.createNativeQuery("select delinq_cycle, days_past_due from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle.concat("%") + "' order by cast(days_past_due as unsigned)").getResultList();
                    if (delinquencyCycleList.size() > 0) {
                        for (int j = 0; j < delinquencyCycleList.size(); j++) {
                            Vector delinVect = (Vector) delinquencyCycleList.get(j);
                            delinqCycle1 = delinVect.get(0).toString();
                            Long daysPastDue = Long.parseLong(delinVect.get(1).toString());
                            if (dayDiff <= daysPastDue) {
                                delinqCycle1 = delinqCycle1;
                                if (params.getRangeBasedOn().equalsIgnoreCase("Y")) {
                                    Long dayDiff1 = dayDiff / 365;
                                    if (/*dayDiff1 > new BigDecimal(params.getFromRange()).longValue() && */dayDiff1 <= new BigDecimal(params.getFromRange()).abs().longValue()) {
                                        outStanding = outStanding;
                                    } else {
                                        outStanding = new BigDecimal("0");
                                    }
                                } else if (params.getRangeBasedOn().equalsIgnoreCase("M")) {
                                    Long dayDiff1 = dayDiff / 12;
                                    if (/*dayDiff1 > new BigDecimal(params.getFromRange()).longValue() && */dayDiff1 <= new BigDecimal(params.getFromRange()).abs().longValue()) {
                                        outStanding = outStanding;
                                    } else {
                                        outStanding = new BigDecimal("0");
                                    }
                                } else if (params.getRangeBasedOn().equalsIgnoreCase("D")) {
                                    Long dayDiff1 = dayDiff;
                                    if (/*dayDiff1 > new BigDecimal(params.getFromRange()).longValue() &&*/dayDiff1 <= new BigDecimal(params.getFromRange()).abs().longValue()) {
                                        outStanding = outStanding;
                                    } else {
                                        outStanding = new BigDecimal("0");
                                    }
                                } else {
                                    outStanding = outStanding;
                                }
                                j = delinquencyCycleList.size();
                            }
                        }
                    }
                    if (params.getFlag().equalsIgnoreCase("P")) {
                        delinquencyCycleList = em.createNativeQuery("select  prov_in_percent from  cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ").getResultList();
                        if (delinquencyCycleList.size() > 0) {
                            Vector delinVect = (Vector) delinquencyCycleList.get(0);
                            provInPer = new BigDecimal(delinVect.get(0).toString());
                            provAmt = (outStanding.multiply(provInPer).divide(new BigDecimal("100")));
                        } else {
                            throw new ApplicationException("scheme_code does not exist in cbs_scheme_delinquency_details for A/c No.:" + acNo);
                        }
                    } else if (params.getFlag().equalsIgnoreCase("GLPD")) {
                        List<RbiSossPojo> resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3PROVS", doubtDt, "0A", reptOpt, "Y");
                        if (!resultListSoss1.isEmpty()) {
                            for (int z = 0; z < resultListSoss1.size(); z++) {
                                provAmt = provAmt.add(resultListSoss1.get(z).getAmt());
                            }
                        }
                        resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3PROVD1", doubtDt, "0A", reptOpt, "Y");
                        if (!resultListSoss1.isEmpty()) {
                            for (int z = 0; z < resultListSoss1.size(); z++) {
                                provAmt = provAmt.add(resultListSoss1.get(z).getAmt());
                            }
                        }
                        provAmt = (outStanding).add(provAmt);
                    } else {
                        List delinquencyCycleList1 = em.createNativeQuery("select  prov_in_percent from cbs_scheme_delinquency_details where scheme_code = '" + schemeCode + "' and flag in ('" + flag + "') and delinq_cycle like '" + delinqCycle1 + "' ").getResultList();
                        if (delinquencyCycleList1.size() > 0) {
                            Vector delinVect = (Vector) delinquencyCycleList1.get(0);
                            provInPer = new BigDecimal(delinVect.get(0).toString());
                            provAmt = (outStanding.multiply(provInPer).divide(new BigDecimal("100")));
                        }
                    }
                    ProvDetailOfLoanAccounts acctBal = new ProvDetailOfLoanAccounts();
                    acctBal.setAcno(acNo);
                    acctBal.setCustName(ftsFacade.getCustName(acNo));
                    acctBal.setOutstanding(outStanding);
                    acctBal.setProvper(provInPer);
                    acctBal.setProvamt(provAmt);
                    if (delinqCycle.equalsIgnoreCase("SUB")) {
                        acctBal.setCategory("11");
                    } else if (delinqCycle.equalsIgnoreCase("D")) {
                        acctBal.setCategory("12");
                    } else if (delinqCycle.equalsIgnoreCase("L")) {
                        acctBal.setCategory("13");
                    }
                    if (delinqCycle.equalsIgnoreCase("D")) {
                        acctBal.setSubcategory(delinqCycle1);
                    } else {
                        acctBal.setSubcategory("");
                    }
                    acctBal.setNpadt(doubtDt);
                    AcctbalAccwise.add(acctBal);
                }
            }
//            for(int k=0; k< AcctbalAccwise.size() ; k ++){
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//                System.out.println("Acno is ::" +AcctbalAccwise.get(k).getAcno() + "::OutStanding is ::" + AcctbalAccwise.get(k).getOutstanding() + "Provision ammount is :" + AcctbalAccwise.get(k).getProvamt() +"\n");
//            }
            return AcctbalAccwise;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<AcctBalPojo> getAcctsAndBalAcwise(AdditionalStmtPojo params) throws ApplicationException {
        try {
            int al;
            String havingQuery = "", acctcode = "", acctcodeQuery = "";
            if (params.getClassification().equals("A")) {
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = -1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (params.getClassification().equals("L")) {
                if (params.getFlag().equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = 1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (params.getClassification().equals("")) {
                havingQuery = "";
            }
            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", allNapQuery = "", bd_query = "", closingQuery = "", maxAcNoQuery = "", npaAcDetails = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = common.getTableName(acNature);
                closingQuery = " and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) ";
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    acCodeQuery = " substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
                } else {
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "' and CRDBFLAG in('B','D'))";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "' and CRDBFLAG in('B','D'))";
                }
            } else if (!params.getAcType().equals("")) {
                acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = common.getAcNatureByAcType(acctcode);
                tableName = common.getTableName(acNature);
                closingQuery = " and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) ";
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC) || acNature.equals(CbsConstant.OF_AC)) {
                    acCodeQuery = " substring(acno,3,2) in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
                } else {
                    acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "' and CRDBFLAG in('B','D'))";
                    maxAcNoQuery = "  a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "' and CRDBFLAG in('B','D'))";
                }
            }
            if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartD")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            } else if (params.getOrgBrCode().equalsIgnoreCase("Oss4PartDNpa")) {
                bd_query = ",cbs_loan_borrower_details b";
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + params.getGlFromHead() + "' and b.sub_sector='" + params.getGlToHead() + "' ";
            }
            if (acNature.equalsIgnoreCase("CA") || acNature.equalsIgnoreCase("DL") || acNature.equalsIgnoreCase("TL")) {
                npaAcDetails = " select a.acno from accountstatus a, "
                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + params.getDate() + "' group by acno) b,"
                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + params.getDate() + "' group by acno) c ,"
                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + params.getDate() + "' and "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + params.getDate() + "')  ";
            }
            /*
             * Only for Non-NPA account outstanding as on date
             */
            if (params.getNature().contains("NON-NPA") || params.getAcType().contains("NON-NPA")) {
                if (!npaAcDetails.equalsIgnoreCase("")) {
                    npaQuery = " and a.acno not in (" + npaAcDetails + ") ";
                }
            } /*
             * Only for NPA account outstanding as on date
             */ else if (params.getNature().contains("NPA") || params.getAcType().contains("NPA")) {
                if (!npaAcDetails.equalsIgnoreCase("")) {
                    npaQuery = " and  a.acno in (" + npaAcDetails + ")";
                }
            }
            List dataList = new ArrayList();
            List maxAcNo = new ArrayList();
            if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select  count(distinct acno),cast(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) from " + tableName + ""
                            + " where " + acCodeQuery + " and DT <= '" + params.getDate() + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from td_accountmaster a where  " + maxAcNoQuery).getResultList();
                } else if (acNature.equals(CbsConstant.OF_AC)) {
                    dataList = em.createNativeQuery("select count(distinct a.acno), ifnull(sum(a.amt),0) from (select  r.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " r,accountmaster a where "
                            + " r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno  " + havingQuery + ") "
                            + " and r.auth = 'Y' and r.dt <='"
                            + params.getDate() + "' and " + acCodeQuery + " group by r.acno "
                            + allNapQuery
                            + " ) a").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from " + tableName + " a").getResultList();
                } else {
                    String executionQuery = "";
                    executionQuery = "select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " r,accountmaster a where "
                            + " r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno  " + havingQuery + ") "
                            + closingQuery + "  and r.acno = a.acno and r.auth = 'Y' and r.dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno "
                            + allNapQuery
                            + " ";
                    dataList = em.createNativeQuery(executionQuery).getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from accountmaster a, cbs_loan_acc_mast_sec b where  a.acno = b.acno and " + maxAcNoQuery).getResultList();
                }
            } else {
                if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select  count(distinct acno), cast(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) from " + tableName + ""
                            + " where " + acCodeQuery + " and DT <= '" + params.getDate() + "' and substring(acno,1,2) = '" + params.getBrnCode() + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();

                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from td_accountmaster a where  " + maxAcNoQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " r,accountmaster a where "
                            + " r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno  " + havingQuery + ") "
                            + closingQuery + "  and r.acno = a.acno  and r.auth = 'Y' and a.CurBrCode = '" + params.getBrnCode() + "' and r.dt <='"
                            + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery + " group by a.acno "
                            + " ").getResultList();
                    maxAcNo = em.createNativeQuery("select ifnull(max(distinct a.acno),'000000000000') from accountmaster a where  " + maxAcNoQuery).getResultList();
                }
            }
            List<AcctBalPojo> AcctbalAccwise = new ArrayList<AcctBalPojo>();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                AcctBalPojo obj = new AcctBalPojo();
                obj.setAcNo(ele.get(0).toString());
                obj.setBalance(new BigDecimal(ele.get(1).toString()));
                AcctbalAccwise.add(obj);
            }
            return AcctbalAccwise;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
}
