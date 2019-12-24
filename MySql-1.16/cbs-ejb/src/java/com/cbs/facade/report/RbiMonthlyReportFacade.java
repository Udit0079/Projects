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
import com.cbs.dto.report.LienReportPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.RbiAlmPojo;
import com.cbs.dto.report.RevenueStatementPojo;
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
import com.cbs.utils.PostfixEvaluator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Vector;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiMonthlyReportFacade")
public class RbiMonthlyReportFacade implements RbiMonthlyReportFacadeRemote {
    
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
    @PersistenceContext
    private EntityManager em;
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        
    public GlHeadPojo getOSBalance(List<GlHeadPojo> balanceList, String acno) throws ApplicationException {
        try {
            for (GlHeadPojo pojo : balanceList) {
                if (pojo.getGlHead().equals(acno)) {
                    return pojo;
                }
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public GlHeadPojo getOSBalanceWithClassification(List<GlHeadPojo> balanceList, String acno, String cls, String date) throws ApplicationException {
        try {
            if (cls.equalsIgnoreCase("L") || cls.equalsIgnoreCase("I")) {
                for (GlHeadPojo pojo : balanceList) {
                    if (pojo.getGlHead().equals(acno) && pojo.getBalance().compareTo(new BigDecimal(0)) > 0 && ymd.parse(date).equals(ymd.parse(pojo.getDate()))) {
                        return pojo;
                    }
                }
            } else {
                for (GlHeadPojo pojo : balanceList) {
                    if (pojo.getGlHead().equals(acno) && pojo.getBalance().compareTo(new BigDecimal(0)) < 0 && ymd.parse(date).equals(ymd.parse(pojo.getDate()))) {
                        return pojo;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public String getBranchWiseQuery(String brCode, String fromDt, String todt) throws ApplicationException {
        try {
            String query = "";
            if (brCode.equals("0A")) {
                query = "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,8) as acno,sum(cramt-dramt) as samt from gl_recon where dt <'" + fromDt + "' "
                        + "and substring(acno,3,8)<>'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' "
                        + "group by substring(acno,3,8)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,8) as acno,sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,8)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,8) as acno,sum(cramt-dramt) as samt from gl_recon where dt <'" + fromDt + "' "
                        + "and substring(acno,3,8)<>'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' "
                        + "group by substring(acno,3,8)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,8) as acno,sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,8)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from recon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0) , "
                        + "cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from recon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from loan_recon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from loan_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from loan_recon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from loan_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from rdrecon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from rdrecon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from rdrecon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from rdrecon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from ddstransaction where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from ddstransaction where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from ddstransaction where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from ddstransaction where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from of_recon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from of_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from of_recon where dt <'" + fromDt + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from of_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from td_recon where dt <'" + fromDt + "' and closeflag is null and Trantype<> 27 "
                        + "group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from td_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and closeflag is null and Trantype<> 27 group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from td_recon where dt <'" + fromDt + "' and closeflag is null and Trantype<> 27 "
                        + "group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from td_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and closeflag is null and Trantype<> 27 group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "(select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',ifnull(cast(a.samt as decimal(25,2)),0) ,"
                        + "ifnull(cast(sum(b.camt) as decimal(25,2)),0),ifnull(cast(sum(b.damt) as decimal(25,2)),0),ifnull(cast((a.samt+sum(b.camt)-sum(b.damt)) as decimal(25,2)),0) from "
                        + "(select sum(amt) as samt from cashinhand where ldate ='" + CbsUtil.dateAdd(fromDt, -1) + "') a, "
                        + "(select sum(cramt) as camt,sum(dramt) as damt from recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from ca_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from rdrecon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from ddstransaction where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from td_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and closeflag is null and Trantype<> 27 "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from loan_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 "
                        + " union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from of_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0) b)";
            } else {
                query = "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,8) as acno,sum(cramt-dramt) as samt from gl_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,8)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,8) as acno,sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,8)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,8) as acno,sum(cramt-dramt) as samt from gl_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,8)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,8) as acno,sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,8)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from loan_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from loan_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from loan_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from loan_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from rdrecon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from rdrecon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from rdrecon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from rdrecon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from ddstransaction where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from ddstransaction where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from ddstransaction where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from ddstransaction where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from of_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from of_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from of_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode
                        + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from of_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "select a.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from td_recon where dt <'" + fromDt + "' and closeflag is null and Trantype<> 27 "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) a "
                        + "LEFT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from td_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and closeflag is null and Trantype<> 27 and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "UNION "
                        + "select b.acno,ifnull(cast(ifnull(a.samt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.camt,0) as decimal(25,2)),0),ifnull(cast(ifnull(b.damt,0) as decimal(25,2)),0), "
                        + "ifnull(cast((ifnull(a.samt,0)+ifnull(b.camt,0)-ifnull(b.damt,0)) as decimal(25,2)),0) "
                        + "from (select substring(acno,3,2) as acno,sum(cramt-dramt) as samt from td_recon where dt <'" + fromDt + "' and closeflag is null and Trantype<> 27 "
                        + "and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) a "
                        + "RIGHT OUTER JOIN "
                        + "(select substring(acno,3,2) as acno,sum(cramt) as camt,sum(dramt) as damt from td_recon where dt between '" + fromDt + "' and '" + todt + "' "
                        + "and closeflag is null and Trantype<> 27 and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,2)) b on a.acno = b.acno "
                        + "union all "
                        + "(select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' ,ifnull(cast(a.samt as decimal(25,2)),0),"
                        + "ifnull(cast(sum(b.camt) as decimal(25,2)),0),ifnull(cast(sum(b.damt) as decimal(25,2)),0),ifnull(cast((a.samt+sum(b.camt)-sum(b.damt)) as decimal(25,2)),0) from "
                        + "(select sum(amt) as samt from cashinhand where ldate ='" + CbsUtil.dateAdd(fromDt, -1) + "' and brncode='" + brCode + "') a, "
                        + "(select sum(cramt) as camt,sum(dramt) as damt from recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from ca_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from rdrecon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from ddstransaction where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from td_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and closeflag is null and Trantype<> 27 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from loan_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from of_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' "
                        + "union all "
                        + "select sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' and trantype =0 and substring(acno,1,2) = '" + brCode + "' ) b)";
            }
            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public List<RevenueStatementPojo> revenueStatement(String fromDt, String todt, String brCode, BigDecimal repOpt, String repType) throws ApplicationException {
        try {
            String reptName = "";
            String query = "";
            long begin = System.nanoTime();
            String revParm = "";
            List revenueParameterList = commonRemote.getCode("REV-STMT-CA");
            if (!revenueParameterList.isEmpty()) {
                revParm = revenueParameterList.get(0).toString();
            }
            if (repType.equalsIgnoreCase("GL")) {
                reptName = "REVGL";
                query = getBranchWiseQuery(brCode, fromDt, todt);
//                System.out.println(query);
            } else {
                reptName = "REVPL";
                if (brCode.equals("0A")) {
                    query = "select a.acno1,cast(a.samt as decimal(25,2)),ifnull(cast(b.camt as decimal(25,2)),0),ifnull(cast(b.damt as decimal(25,2)),0),ifnull(cast((a.samt+b.camt-b.damt) as decimal(25,2)),0) "
                            + "from (select substring(acno,3,8) as acno1,sum(cramt-dramt) as samt from gl_recon where dt <'" + fromDt + "' group by substring(acno,3,8)) a left join"
                            + "(select substring(acno,3,8) as acno2,sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' "
                            + "and trandesc<>13 group by substring(acno,3,8)) b on a.acno1 = b.acno2 and a.acno1 between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue()
                            + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' ";
                } else {
                    query = "select a.acno1,cast(a.samt as decimal(25,2)),ifnull(cast(b.camt as decimal(25,2)),0),ifnull(cast(b.damt as decimal(25,2)),0),ifnull(cast((a.samt+b.camt-b.damt) as decimal(25,2)),0) "
                            + "from (select substring(acno,3,8) as acno1,sum(cramt-dramt) as samt from gl_recon where dt <'" + fromDt + "' and substring(acno,1,2) = '" + brCode + "' group by substring(acno,3,8)) a left join"
                            + "(select substring(acno,3,8) as acno2,sum(cramt) as camt,sum(dramt) as damt from gl_recon where dt between '" + fromDt + "' and '" + todt + "' and substring(acno,1,2) = '" + brCode + "' "
                            + "and trandesc<>13 group by substring(acno,3,8)) b on a.acno1 = b.acno2 and a.acno1 between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue()
                            + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' ";
                }
            }
            
            List glResultList = em.createNativeQuery(query).getResultList();
            List<GlHeadPojo> balanceList = new ArrayList<GlHeadPojo>();
            GlHeadPojo balPojo;
            for (int i = 0; i < glResultList.size(); i++) {
                Vector vect = (Vector) glResultList.get(i);
                balPojo = new GlHeadPojo();
                // System.out.println("gl head is =" + vect.get(0));
                balPojo.setGlHead(vect.get(0).toString());
                balPojo.setOpBal(new BigDecimal(vect.get(1).toString()));
                balPojo.setTotalCr(new BigDecimal(vect.get(2).toString()));
                
                balPojo.setTotalDr(new BigDecimal(vect.get(3).toString()));
                if (repType.equalsIgnoreCase("GL")) {
                    balPojo.setBalance(new BigDecimal(vect.get(4).toString()));
                } else {
                    balPojo.setBalance(new BigDecimal(Double.parseDouble(vect.get(1).toString()) + Double.parseDouble(vect.get(2).toString()) - Double.parseDouble(vect.get(3).toString())));
                }
                balanceList.add(balPojo);
            }
            
            List<RevenueStatementPojo> rbiPojoTable = new ArrayList<RevenueStatementPojo>();
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = '" + reptName
                    + "' order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            }
            for (int i = 0; i < oss1Query.size(); i++) {
                
                RevenueStatementPojo revenuePojo = new RevenueStatementPojo();
                Vector oss1Vect = (Vector) oss1Query.get(i);
                Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                String reportName = oss1Vect.get(1).toString();
                String description = oss1Vect.get(2).toString();
                
                Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                Integer sGNo = oss1Vect.get(4).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(4).toString());
                Integer ssGNo = oss1Vect.get(5).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(5).toString());
                Integer sssGNo = oss1Vect.get(6).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(6).toString());
                Integer ssssGNo = oss1Vect.get(7).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(7).toString());
                
                String classification = oss1Vect.get(8).toString();
                String countApplicable = oss1Vect.get(9).toString();
                String acNature = oss1Vect.get(10).toString();
                String acType = oss1Vect.get(11).toString();
                
                String glHeadFrom = oss1Vect.get(12).toString();
                String glHeadTo = oss1Vect.get(13).toString();
                String formula = oss1Vect.get(17).toString();
                String fGno = oss1Vect.get(18).toString();
                String fSssGNo = oss1Vect.get(21).toString();
                String npaClassification = oss1Vect.get(23).toString();
                
                List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                GlHeadPojo pojo;

//                System.out.println("Acct Namtue = " + acNature + " and gl series = " + glHeadFrom + " and " + glHeadTo);
                if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
                    String acQuery = "";
                    if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                        if (!((fSssGNo == null) || fSssGNo.equalsIgnoreCase(""))) {
                            acQuery = "acctnature ='" + acNature + "' and acctcode not in ('" + fSssGNo + "')";
                        } else {
                            acQuery = "acctnature ='" + acNature + "' ";
                        }
                    } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                        acQuery = "acctcode ='" + acType + "'";
                    }
                    List dataList = em.createNativeQuery("select acctcode,acctdesc,glhead from accounttypemaster where " + acQuery).getResultList();
                    for (int k = 0; k < dataList.size(); k++) {
                        Vector vec = (Vector) dataList.get(k);
                        
                        pojo = new GlHeadPojo();
                        pojo.setGlHead(vec.get(0).toString());
                        pojo.setGlName(vec.get(1).toString());
                        String glhead = vec.get(2).toString();
                        BigDecimal closingBal;
                        if (acNature.equals(CbsConstant.CURRENT_AC)) {
                            if (revParm.equalsIgnoreCase("1")) {
                                BigDecimal opBal = new BigDecimal("0"), crBal = new BigDecimal("0"), drBal = new BigDecimal("0");
                                List l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM ca_recon r,accountmaster am WHERE "
                                        + "SUBSTRING(r.ACNO,3,2) = '" + vec.get(0).toString() + "' AND r.DT < '" + fromDt + "' AND r.auth = 'Y' AND am.acno = r.acno ").getResultList();
                                if (!l2.isEmpty()) {
                                    Vector vect = (Vector) l2.get(0);
                                    opBal = new BigDecimal(Double.parseDouble(vect.get(0).toString()));
                                }
                                l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                        + "ca_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + vec.get(0).toString() + "' AND r.DT between '" + fromDt + "' AND '" + todt + "' "
                                        + "and r.auth = 'Y' AND am.acno = r.acno ").getResultList();
                                if (!l2.isEmpty()) {
                                    Vector vect = (Vector) l2.get(0);
                                    crBal = new BigDecimal(vect.get(0).toString());
                                    drBal = new BigDecimal(vect.get(1).toString());
                                }
                                pojo.setOpBal(opBal);
                                pojo.setTotalCr(crBal);
                                pojo.setTotalDr(drBal);
                                closingBal = opBal.subtract(drBal).add(crBal);
                            } else {
                                RevenueReportDrCrPojo revPo = caRevenueBalance(glhead, classification, vec.get(0).toString(), fromDt, todt, brCode);
                                pojo.setOpBal(new BigDecimal(revPo.getOpBal()).abs());
                                
                                pojo.setTotalCr(new BigDecimal(revPo.getCrAmt()).abs());
                                pojo.setTotalDr(new BigDecimal(revPo.getDrAmt()).abs());
                                
                                closingBal = new BigDecimal(revPo.getClosingBal());
                            }
                        } else {
                            GlHeadPojo newBalPojo = getOSBalance(balanceList, vec.get(0).toString());
                            if (newBalPojo == null) {
                                pojo.setOpBal(new BigDecimal(0));
                                pojo.setTotalCr(new BigDecimal(0));
                                pojo.setTotalDr(new BigDecimal(0));
                                closingBal = new BigDecimal(0);
                            } else {
                                pojo.setOpBal(newBalPojo.getOpBal().abs());
                                pojo.setTotalCr(newBalPojo.getTotalCr());
                                pojo.setTotalDr(newBalPojo.getTotalDr());
                                closingBal = newBalPojo.getBalance();
                            }
                        }
                        if (classification.equals("L") && closingBal.compareTo(new BigDecimal(0)) >= 0) {
                            pojo.setBalance(closingBal.abs());
                        } else if (classification.equals("A") && closingBal.compareTo(new BigDecimal(0)) <= 0) {
                            pojo.setBalance(closingBal.abs());
                        } else {
                            pojo.setOpBal(new BigDecimal(0));
                            pojo.setBalance(new BigDecimal(0));
                            
                            pojo.setTotalCr(new BigDecimal(0));
                            pojo.setTotalDr(new BigDecimal(0));
                        }
                        glPojoList.add(pojo);
                    }
                } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                    List glNameList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable where "
                            + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                            + "by substring(acno,3,8)").getResultList();
                    for (int n = 0; n < glNameList.size(); n++) {
                        Vector vector = (Vector) glNameList.get(n);
                        
                        GlHeadPojo glPojoOld = getOSBalance(balanceList, vector.get(0).toString());
                        GlHeadPojo glPojo = new GlHeadPojo();
                        BigDecimal opBal = new BigDecimal(0);
                        BigDecimal closingBal = new BigDecimal(0);
//                        System.out.println("Gl Head = " + vector.get(0).toString());
                        if (glPojoOld == null) {
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            glPojo.setTotalCr(new BigDecimal(0));
                            glPojo.setTotalDr(new BigDecimal(0));
                        } else {
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            
                            glPojo.setTotalCr(glPojoOld.getTotalCr());
                            glPojo.setTotalDr(glPojoOld.getTotalDr());
                            
                            opBal = glPojoOld.getOpBal();
                            closingBal = glPojoOld.getBalance();
                        }
                        if (reportName.equals("REVGL")) {
                            if (classification.equals("L")) {
                                if (opBal.compareTo(new BigDecimal(0)) >= 0 && closingBal.compareTo(new BigDecimal(0)) >= 0) {
                                    glPojo.setOpBal(opBal);
                                    glPojo.setBalance(closingBal);
                                } else {
                                    if (fGno.equalsIgnoreCase("B")) {
                                        if (opBal.compareTo(new BigDecimal(0)) < 0 && closingBal.compareTo(new BigDecimal(0)) >= 0) {
                                            glPojo.setOpBal(new BigDecimal(0));
                                            glPojo.setBalance(closingBal.abs());
                                            glPojo.setTotalDr(new BigDecimal(0));
                                        } else if (opBal.compareTo(new BigDecimal(0)) >= 0 && closingBal.compareTo(new BigDecimal(0)) < 0) {
                                            glPojo.setOpBal(opBal);
                                            glPojo.setBalance(new BigDecimal(0));
                                            glPojo.setTotalCr(new BigDecimal(0));
                                        } else {
                                            glPojo.setOpBal(new BigDecimal(0));
                                            glPojo.setBalance(new BigDecimal(0));
                                            glPojo.setTotalCr(new BigDecimal(0));
                                            glPojo.setTotalDr(new BigDecimal(0));
                                        }
                                    } else {
                                        glPojo.setOpBal(opBal);
                                        glPojo.setBalance(closingBal);
                                    }
                                }
                            } else {
                                // if (glHeadFrom.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                //     glPojo.setOpBal(opBal.abs());
                                //     glPojo.setBalance(closingBal.abs());
                                // } else {
                                //  if (opBal.compareTo(new BigDecimal(0)) <= 0 && closingBal.compareTo(new BigDecimal(0)) <= 0) {
                                //      glPojo.setOpBal(opBal.abs());
                                //      glPojo.setBalance(closingBal.abs());

                                // } else {
                                //  if (fGno.equalsIgnoreCase("B")) {
                                if (opBal.compareTo(new BigDecimal(0)) <= 0 && closingBal.compareTo(new BigDecimal(0)) <= 0) {
                                    glPojo.setOpBal(opBal.abs());
                                    glPojo.setBalance(closingBal.abs());
                                    
                                } else if (opBal.compareTo(new BigDecimal(0)) <= 0 && closingBal.compareTo(new BigDecimal(0)) > 0) {
                                    if (fGno.equalsIgnoreCase("B")) {
                                        glPojo.setOpBal(opBal.abs());
                                        glPojo.setBalance(new BigDecimal(0));
                                        glPojo.setTotalCr(new BigDecimal(0));
                                    } else {
                                        glPojo.setOpBal(opBal.abs());
                                        glPojo.setBalance(closingBal.abs().multiply(new BigDecimal(-1)));
                                    }
                                } else if (opBal.compareTo(new BigDecimal(0)) > 0 && closingBal.compareTo(new BigDecimal(0)) < 0) {
                                    if (fGno.equalsIgnoreCase("B")) {
                                        glPojo.setOpBal(new BigDecimal(0));
                                        glPojo.setBalance(closingBal.abs());
                                        glPojo.setTotalCr(new BigDecimal(0));
                                    } else {
                                        glPojo.setOpBal(opBal.abs().multiply(new BigDecimal(-1)));
                                        glPojo.setBalance(closingBal.abs());
                                    }
                                } else {
                                    if (glHeadFrom.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                        glPojo.setOpBal(opBal.abs());
                                        glPojo.setBalance(closingBal.abs());
                                    } else {
                                        glPojo.setOpBal(new BigDecimal(0));
                                        glPojo.setBalance(new BigDecimal(0));
                                        
                                        glPojo.setTotalCr(new BigDecimal(0));
                                        glPojo.setTotalDr(new BigDecimal(0));
                                    }
                                }
                                // } else {
                                //      glPojo.setOpBal(opBal.abs().multiply(new BigDecimal(-1)));
                                //     glPojo.setBalance(closingBal.abs().multiply(new BigDecimal(-1)));
                                //  }
                            }
                            //  }
                            // }
                        } else {
                            glPojo.setOpBal(opBal);
                            glPojo.setBalance(closingBal);
                        }
                        if (glPojo.getOpBal().compareTo(new BigDecimal(0)) != 0 || glPojo.getTotalCr().compareTo(new BigDecimal(0)) != 0
                                || glPojo.getTotalDr().compareTo(new BigDecimal(0)) != 0 || glPojo.getBalance().compareTo(new BigDecimal(0)) != 0) {
                            glPojoList.add(glPojo);
                        }
                    }
                }
                if (!formula.equals("")) {
                    if (formula.equalsIgnoreCase("+P&L") || formula.equalsIgnoreCase("-P&L")) {
                        pojo = new GlHeadPojo();
                        pojo.setGlHead("");
                        pojo.setGlName(description);
                        double opBal = glReport.IncomeExp(CbsUtil.dateAdd(fromDt, -1), brCode, brCode);
                        pojo.setOpBal(new BigDecimal(opBal));
                        
                        double balPL = glReport.IncomeExp(todt, brCode, brCode);
                        pojo.setBalance(new BigDecimal(balPL));
                        
                        if (classification.equalsIgnoreCase("L") && balPL >= 0) {
                            glPojoList.add(pojo);
                        } else if (classification.equalsIgnoreCase("A") && balPL < 0) {
                            glPojoList.add(pojo);
                        }
                    }
                }
                revenuePojo.setsNo(sNo);
                revenuePojo.setAcNature(acNature);
                revenuePojo.setAcType(acType);
                revenuePojo.setClassification(classification);
                
                revenuePojo.setFormula(formula);
                revenuePojo.setGlHeadTo(glHeadTo);
                revenuePojo.setGlHeadFrom(glHeadFrom);
                
                revenuePojo.setReportName(reportName);
                revenuePojo.setgNo(gNo);
                revenuePojo.setsGNo(sGNo);
                revenuePojo.setSsGNo(ssGNo);
                revenuePojo.setSssGNo(sssGNo);
                revenuePojo.setSsssGNo(ssssGNo);
                revenuePojo.setDescription(description);
                revenuePojo.setNpaClassification(npaClassification);
                revenuePojo.setCountApplicable(countApplicable);
                
                
                rbiPojoTable.add(revenuePojo);
                
                for (int j = 0; j < glPojoList.size(); j++) {
                    revenuePojo = new RevenueStatementPojo();
                    GlHeadPojo glHeadPo = glPojoList.get(j);
                    revenuePojo.setsNo(sNo);
                    revenuePojo.setAcNature(acNature);
                    revenuePojo.setAcType(acType);
                    revenuePojo.setClassification(classification);
                    revenuePojo.setFormula(formula);
                    revenuePojo.setGlHeadTo(glHeadPo.getGlHead());
                    revenuePojo.setGlHeadFrom(glHeadPo.getGlHead());
                    revenuePojo.setReportName(reportName);
                    revenuePojo.setgNo(gNo);
                    revenuePojo.setsGNo(sGNo);
                    revenuePojo.setOpBal(glHeadPo.getOpBal());
                    revenuePojo.setClosingBal(glHeadPo.getBalance());
                    revenuePojo.setCrAmt(glHeadPo.getTotalCr());
                    revenuePojo.setDrAmt(glHeadPo.getTotalDr());
                    if (ssGNo == 0) {
                        revenuePojo.setSsGNo(j + 1);
                    } else {
                        revenuePojo.setSsGNo(ssGNo);
                    }
                    if (sssGNo == 0 && ssGNo != 0) {
                        revenuePojo.setSssGNo(j + 1);
                    } else {
                        revenuePojo.setSssGNo(sssGNo);
                    }
                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
                        revenuePojo.setSsssGNo(j + 1);
                        revenuePojo.setDescription(glHeadPo.getGlName());
                    } else {
                        revenuePojo.setSsssGNo(ssssGNo);
                        revenuePojo.setDescription(String.valueOf("               ").concat(String.valueOf(j + 1)).concat(". ").concat(glHeadPo.getGlName()));
                    }
                    rbiPojoTable.add(revenuePojo);
                }
            }
//            System.out.println("Execution time for getting data " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public RevenueReportDrCrPojo caRevenueBalance(String glHead, String classType, String acType, String fromDt, String toDt, String brnCode) throws ApplicationException {
        List resultSetOp = null, resultSetCl = null;
        //List<RevenueReportDrCrPojo> returnList = new ArrayList<RevenueReportDrCrPojo>();
        RevenueReportDrCrPojo pojo = new RevenueReportDrCrPojo();
        Vector vec1;
        double crTotal = 0, drTotal = 0,
                opBalP = 0, clBalP = 0,
                opBalN = 0, clBalN = 0,
                rBalP = 0, rBalN = 0,
                opBalActual = 0, closingBalActual = 0;
        if (classType.equalsIgnoreCase("L")) {
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
                opBalActual = opBalActual + Double.parseDouble(vec1.get(2).toString());
            }
            for (int j = 0; j < resultSetCl.size(); j++) {
                vec1 = (Vector) resultSetCl.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                clBalP = clBalP + Double.parseDouble(vec1.get(2).toString());
                closingBalActual = closingBalActual + Double.parseDouble(vec1.get(2).toString());
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
                opBalActual = opBalActual + Double.parseDouble(vec1.get(2).toString());
            }
            for (int j = 0; j < resultSetCl.size(); j++) {
                vec1 = (Vector) resultSetCl.get(j);
                crTotal = crTotal + Double.parseDouble(vec1.get(0).toString());
                drTotal = drTotal + Double.parseDouble(vec1.get(1).toString());
                clBalN = clBalN + Double.parseDouble(vec1.get(2).toString());
                closingBalActual = closingBalActual + Double.parseDouble(vec1.get(2).toString());
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
        
        if (classType.equalsIgnoreCase("A")) {
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
            List rBalList;
            if (brnCode.equalsIgnoreCase("0A")) {
                rBalList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0),ifnull(sum(ifnull(dramt,0)),0) from ca_recon where  "
                        + "dt between '" + fromDt + "' and '" + toDt + "' and substring(acno,3,2)='" + acType + "'  and auth='Y' ").getResultList();
            } else {
                rBalList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0),ifnull(sum(ifnull(dramt,0)),0) from ca_recon where  "
                        + "dt between '" + fromDt + "' and '" + toDt + "' and substring(acno,3,2)='" + acType + "' AND acno in (select acno from "
                        + "accountmaster where curbrcode= '" + brnCode + "')  and auth='Y' ").getResultList();
            }
            
            Vector vecRBal = (Vector) rBalList.get(0);
            rBalN = -Double.parseDouble(new BigDecimal(opBalP).abs().toString()) + Double.parseDouble(vecRBal.get(1).toString());
            rBalP = -Double.parseDouble(new BigDecimal(clBalP).abs().toString()) + Double.parseDouble(vecRBal.get(0).toString());
        }
        
        if (classType.equalsIgnoreCase("L")) {
            pojo.setCrAmt(Double.parseDouble(new BigDecimal(clBalP).abs().toString()));
            pojo.setDrAmt(Double.parseDouble(new BigDecimal(opBalP).abs().toString()));
        } else {
            pojo.setCrAmt(Double.parseDouble(new BigDecimal(rBalP).abs().toString()));
            pojo.setDrAmt(Double.parseDouble(new BigDecimal(rBalN).abs().toString()));
        }
        pojo.setOpBal(opBalActual);
        pojo.setClosingBal(closingBalActual);
        return pojo;
    }
    
    public String getAcctCodeWiseBal(AdditionalStmtPojo params) throws ApplicationException {
        try {
            int al = 1;
            if (params.getClassification().equals("A")) {
                al = -1;
            }
            List dataList = new ArrayList();
            String nature = commonRemote.getAcctNature(params.getAcType());
            String tableName = commonRemote.getTableName(nature);
            if (params.getBrnCode().equalsIgnoreCase("0A")) {
                if (nature.equals(CbsConstant.FIXED_AC) || nature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)), cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) from "
                            + tableName + " where substring(acno,3,2) = '" + params.getAcType() + "' and dt between '" + params.getDate() + "' and '" + params.getToDate()
                            + "' and auth = 'Y' and closeflag is null ").getResultList();
                } else {
                    dataList = em.createNativeQuery("select sum(a.cramt),sum(a.dramt) from (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)) as cramt, "
                            + "cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as dramt from " + tableName + " where substring(acno,3,2) = '" + params.getAcType()
                            + "' and dt between '" + params.getDate() + "' and '" + params.getToDate() + "' and auth = 'Y' group by acno  having sign(sum(cramt-dramt))  = "
                            + al + ") a ").getResultList();
                }
            } else {
                if (nature.equals(CbsConstant.FIXED_AC) || nature.equals(CbsConstant.MS_AC)) {
                    dataList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)), cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) from "
                            + tableName + " where substring(acno,3,2) = '" + params.getAcType() + "' and dt between '" + params.getDate() + "' and '" + params.getToDate()
                            + "' and substring(acno,1,2) = '" + params.getBrnCode() + "' and auth = 'Y' and closeflag is null").getResultList();
                    
                } else {
                    dataList = em.createNativeQuery("select sum(a.cramt),sum(a.dramt) from (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)) as cramt, "
                            + "cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as dramt from " + tableName + " where substring(acno,3,2) = '" + params.getAcType()
                            + "' and dt between '" + params.getDate() + "' and '" + params.getToDate() + "' and substring(acno,1,2) = '" + params.getBrnCode()
                            + "' and auth = 'Y' group by acno  having sign(sum(cramt-dramt))  = " + al + ") a ").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                Vector ele = (Vector) dataList.get(0);
                String cramt = ele.get(0) != null ? ele.get(0).toString() : "0";
                String dramt = ele.get(1) != null ? ele.get(1).toString() : "0";
                return cramt + "#~" + dramt;
            } else {
                return "0#~0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public String getGLHeadBal(String brCode, String head, String date, String cl) throws ApplicationException {
        try {
            int al = 1;
            if (cl.equals("A")) {
                al = -1;
            }
            String plStHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue().substring(0, 1);
            String plendHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue().substring(0, 1);
            List dataList = new ArrayList();
//            System.out.println("Gl Head is = "+head);
            if (brCode.equals("0A") || brCode.equals("90")) {
                if (head.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "'").getResultList();

                    //  } else if (head.trim().startsWith(plStHead) || head.trim().startsWith(plendHead)) {
//
                    //    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r "
                    //             + " where dt <='" + date + "'  and trandesc <>13  and substring(r.acno,3,8) = '" + head + "' ").getResultList();
                } else {
                    dataList = em.createNativeQuery("select  cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon where dt <='" + date + "' "
                            + "and substring(acno,3,8) ='" + head + " ' ").getResultList();
                }
            } else {
                if (head.trim().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "' and brncode = '" + brCode + "'").getResultList();

                    //  } else if (head.trim().startsWith(plStHead) || head.trim().startsWith(plendHead)) {
                    //        dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r "
                    //               + " where dt <='" + date + "'  and trandesc <>13  and substring(r.acno,3,8) = '" + head + "' and "
                    //                + "substring(acno,1,2)='" + brCode + "'").getResultList();
                } else {
                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon where dt <='" + date + "' "
                            + "and substring(acno,3,8) = '" + head + " ' and substring(acno,1,2)='" + brCode + "' ").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                Vector vec = (Vector) dataList.get(0);
                return vec.get(0).toString();
            } else {
                return "0";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public String getForm1GLHeadBal(String brCode, String head, String date, String cl, String flag) throws ApplicationException {
        try {
            int al;
            String havingQuery = "";
            if (cl.equalsIgnoreCase("A")) {
                if (flag.equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = -1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (cl.equals("L")) {
                if (flag.equalsIgnoreCase("ACT")) {
                    havingQuery = "";
                } else {
                    al = 1;
                    havingQuery = " having sign(sum(cramt-dramt)) = " + al;
                }
            } else if (cl.equals("")) {
                havingQuery = "";
            }
            
            String plStHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue().substring(0, 1);
            String plendHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue().substring(0, 1);
            List dataList = new ArrayList();
            if (brCode.equals("0A") || brCode.equals("90")) {
                if (head.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select ifnull(sum(ifnull(amt,0)),0)*-1 from cashinhand where ldate ='" + date + "'").getResultList();
                } else if (head.trim().startsWith(plStHead) || head.trim().startsWith(plendHead)) {
                    
                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r where dt <='" + date + "'  and trandesc <>13  and substring(r.acno,3,8) = '" + head + "' group by substring(r.acno,3,8)  " + havingQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select  cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon where dt <='" + date + "' and substring(acno,3,8) ='" + head + "' group by substring(acno,3,8)  " + havingQuery).getResultList();
                }
            } else {
                if (head.trim().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                    dataList = em.createNativeQuery("select sum(ifnull(amt,0))*-1 from cashinhand where ldate ='" + date + "' and brncode = '" + brCode + "'").getResultList();
                    
                } else if (head.trim().startsWith(plStHead) || head.trim().startsWith(plendHead)) {
                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r where dt <='" + date + "'  and trandesc <>13  and substring(r.acno,3,8) = '" + head + "' and substring(acno,1,2)='" + brCode + "' group by substring(r.acno,3,8)  " + havingQuery).getResultList();
                } else {
                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon where dt <='" + date + "' and substring(acno,3,8) = '" + head + "' and substring(acno,1,2)='" + brCode + "' group by substring(acno,3,8)  " + havingQuery).getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                Vector vec = (Vector) dataList.get(0);
                return vec.get(0).toString();
            } else {
                return "0";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public String getGLHeadDrCr(String brCode, String head, String frDt, String todt, String cl) throws ApplicationException {
        try {
            int al = 1;
            if (cl.equals("A")) {
                al = -1;
            }
            if (head.trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                return cashInHandDrCr(frDt, todt, brCode);
            } else {
                List dataList;
                if (brCode.equals("0A")) {
                    dataList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from gl_recon where dt between'" + frDt + "' and '"
                            + todt + "' and substring(acno,3,8) ='" + head + "'").getResultList();
                } else {
                    dataList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from gl_recon where dt between'" + frDt + "' and '"
                            + todt + "' and substring(acno,3,8) = '" + head + "' and substring(acno,1,2)='" + brCode + "'").getResultList();
                }
                if (!dataList.isEmpty()) {
                    Vector vec = (Vector) dataList.get(0);
                    return vec.get(0).toString() + "#~" + vec.get(1).toString();
                } else {
                    return "0#~0";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public String cashInHandDrCr(String fromDt, String toDt, String brnCode) throws ApplicationException {
        double drAmt = 0, crAmt = 0;
        List acTypeList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where acctnature not in ('NP')").getResultList();
        if (acTypeList.isEmpty()) {
            throw new ApplicationException("Data does not exist in AccountTypeMaster");
        }
        
        for (int k = 0; k < acTypeList.size(); k++) {
            Vector vec = (Vector) acTypeList.get(k);
            String acctNature = vec.get(0).toString();
            
            String acCode = vec.get(1).toString();
            String tableName = CbsUtil.getReconTableName(acctNature);
            
            List amtList, glAmtList;
            if (brnCode.equalsIgnoreCase("0A")) {
                if (tableName.equalsIgnoreCase("td_recon")) {
                    amtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where dt between '" + fromDt
                            + "' and  '" + toDt + "' and trantype<> 27 and closeflag is null and auth='Y' and trantype=0 and substring(acno,3,2) = '" + acCode + "'").getResultList();
                } else {
                    amtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where  "
                            + " dt between '" + fromDt + "' and  '" + toDt + "' and auth='Y'  AND trantype=0 and substring(acno,3,2) = '" + acCode + "'").getResultList();
                }
                glAmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0),ifnull(sum(ifnull(Dramt,0)),0) from gl_recon where substring(acno,3,8)='"
                        + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and dt between '" + fromDt + "' and  '" + toDt + "' and auth='Y'").getResultList();
            } else {
                if (tableName.equalsIgnoreCase("td_recon")) {
                    amtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where dt between '" + fromDt
                            + "' and  '" + toDt + "' and trantype<> 27 and closeflag is null and auth='Y' AND trantype=0  and substring(acno,3,2) = '" + acCode
                            + "' and org_brnid = '" + brnCode + "'").getResultList();
                } else {
                    amtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from " + tableName + " where  "
                            + " dt between '" + fromDt + "' and '" + toDt + "' and  auth='Y'  AND trantype=0 AND substring(acno,3,2) = '" + acCode + "' and org_brnid = '"
                            + brnCode + "'").getResultList();
                }
                glAmtList = em.createNativeQuery("select ifnull(sum(ifnull(Cramt,0)),0), ifnull(sum(ifnull(Dramt,0)),0) from gl_recon where substring(acno,3,8)='"
                        + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and dt between '" + fromDt + "' and  '" + toDt + "' and auth='Y' "
                        + "and org_brnid = '" + brnCode + "'").getResultList();
            }
            
            Vector amtVec = (Vector) amtList.get(0);
            Vector glAmtVec = (Vector) glAmtList.get(0);
            drAmt = drAmt + Double.parseDouble(amtVec.get(1).toString()) + Double.parseDouble(glAmtVec.get(1).toString());
            crAmt = crAmt + Double.parseDouble(amtVec.get(0).toString()) + Double.parseDouble(glAmtVec.get(0).toString());
        }
        return crAmt + "#~" + drAmt;
    }
    
    public List<RbiSossPojo> getMonetaryAggregateDetails(String reportDt, BigDecimal repOpt,
            String reportName, String orgnCode) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        try {
            List list = em.createNativeQuery("select sno,report_name,description,gno,s_gno,ss_gno,sss_gno,ssss_gno,"
                    + "classification,ac_nature,ac_type,gl_head_from,gl_head_to,range_base_on,ifnull(range_from,0) as range_from,"
                    + "ifnull(range_to,0) as range_to from cbs_ho_rbi_stmt_report where report_name = 'MON_AGG' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DTorder by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT for Monetary Aggregates.");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    RbiSossPojo pojo = new RbiSossPojo();
                    Vector element = (Vector) list.get(i);
                    String classification = element.get(8).toString();
                    String acNature = element.get(9).toString();
                    String acType = element.get(10).toString();
                    String glHeadFrom = element.get(11).toString();
                    String glHeadTo = element.get(12).toString();
                    String rangeBaseOn = element.get(13).toString();
                    String rangeFrom = element.get(14).toString();
                    String rangeTo = element.get(15).toString();
                    Integer cl;
                    if (classification.equalsIgnoreCase("L")) {
                        cl = 1;
                    } else {
                        cl = -1;
                    }
                    if (!(acNature == null || acNature.equalsIgnoreCase(""))) {
                    }
                    /*Setting all the values in the objects*/
                    pojo.setsNo(Integer.parseInt(element.get(0).toString()));
                    pojo.setReportName(element.get(1).toString());
                    pojo.setDescription(element.get(2).toString());
                    pojo.setgNo(Integer.parseInt(element.get(3).toString()));
                    pojo.setsGNo(Integer.parseInt(element.get(4).toString()));
                    pojo.setSsGNo(Integer.parseInt(element.get(5).toString()));
                    pojo.setSssGNo(Integer.parseInt(element.get(6).toString()));
                    pojo.setSsssGNo(Integer.parseInt(element.get(7).toString()));
                    pojo.setClassification(classification);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setGlheadFrom(glHeadFrom);
                    pojo.setGlHeadTo(glHeadTo);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
    
    public BigDecimal getMonetaryAggregatesFdValue(String acNature, String acType, String date, String rangeBaseOn,
            String fromRange, String toRange) throws ApplicationException {
        try {
            BigDecimal amt = new BigDecimal("0");
            String acCodeQuery = "";
            if (!(acNature == null || acNature.equalsIgnoreCase(""))) {
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!(acType == null || acType.equalsIgnoreCase(""))) {
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acType + "')";
            }
            List dataList = em.createNativeQuery("select t.acno,ifnull((sum(t.cramt)-sum(t.dramt)),0) from td_recon t,"
                    + "td_accountmaster a where a.acno = t.acno " + acCodeQuery + " AND t.dt<='" + date + "' and "
                    + "t.auth='Y' AND t.closeflag is null  group by t.acno having sum(t.cramt)-sum(t.dramt)<>0 order "
                    + "by t.acno").getResultList();
            for (int a = 0; a < dataList.size(); a++) {
                Vector element = (Vector) dataList.get(a);
                String acno = element.get(0).toString();
                BigDecimal amount = new BigDecimal(element.get(1).toString());
                
                List list = em.createNativeQuery("SELECT coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + date + "') FROM "
                        + "td_vouchmst WHERE ACNO = '" + acno + "' and td_madedt <='" + date + "' ").getResultList();
                if (!list.isEmpty()) {
                    Vector vector = (Vector) list.get(0);
                    String maddt = vector.get(0).toString();
                    long diff = 1;
                    if (ymd.parse(maddt).compareTo(ymd.parse(date)) >= 0) {
                        if (rangeBaseOn.equalsIgnoreCase("Y")) {
                            diff = CbsUtil.yearDiff(ymd.parse(date), ymd.parse(maddt));
                        } else if (rangeBaseOn.equalsIgnoreCase("M")) {
                            diff = CbsUtil.monthDiff(ymd.parse(date), ymd.parse(maddt));
                        } else if (rangeBaseOn.equalsIgnoreCase("D")) {
                            diff = CbsUtil.dayDiff(ymd.parse(date), ymd.parse(maddt));
                        }
                    }
                    if (diff >= new BigDecimal(fromRange).longValue() && diff <= new BigDecimal(toRange).longValue()) {
                        amt = amt.add(amount);
                    }
                }
            }
            return amt;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
        
    public List getStmtValue(String repOpt) throws ApplicationException {
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select description,gno,s_gno,ss_gno,sss_gno,ssss_gno,classification,count_applicable from cbs_ho_rbi_stmt_report where report_name ='" + repOpt + "'"
                    + " order by classification desc, cast(gno as unsigned),cast(s_gno as unsigned)").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    /**
     *
     * @param dates
     * @param repOpt
     * @param reportName
     * @param orgnCode
     * @param reportIn
     * @return
     * @throws ApplicationException
     */
    public List<RbiSossPojo> getForm1Details(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,
            String reportIn, String reportFormat) throws ApplicationException {
        if (orgnCode.equalsIgnoreCase("90")) {
            orgnCode = "0A";
        }
//        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
        try {
            Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
            String preFormula = "", npaAcDetails = "";
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, ifnull(REFER_INDEX,'')  from cbs_ho_rbi_stmt_report where report_name = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT  order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT for Form1.");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
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
//                    String rangeBaseOn = oss1Vect.get(14).toString();
//                    String rangeFrom = oss1Vect.get(15).toString();
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
                    rbiSossPojo.setSecondAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setThirdAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setFourthAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setFifthAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setSixthAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setSeventhAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setNpaClassification(npaClassification);
//                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
//                        rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setReportName(reportName);
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
                            rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                            
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
                    params.setAcType(acType.contains(".") ? acType.substring(acType.indexOf(".") + 1) : acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
//                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
//                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
//                    Long noOfAc = 0l;
//                    BigDecimal bal = new BigDecimal("0");
                    AcctBalPojo acctBal = new AcctBalPojo();
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                    
                    if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
                        List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
//                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                        List natureList = null;
                        if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                            natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                    + "acctnature in ('" + params.getNature() + "')").getResultList();
                        } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                            natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                    + "acctcode in ('" + params.getAcType() + "')").getResultList();
                        }
                        
                        for (int z = 0; z < natureList.size(); z++) {
                            Vector vector = (Vector) natureList.get(z);
                            rbiSossPojo = new RbiSossPojo();
//                            GlHeadPojo glHeadPo = glHeadList.get(z);
                            params.setNature("");
                            params.setAcType(vector.get(0).toString());
                            String accountNature = commonRemote.getAcNatureByAcType(vector.get(0).toString());
//                            glPojoList = new ArrayList<GlHeadPojo>();
//                            GlHeadPojo glPojo = new GlHeadPojo();
//                            glPojo.setGlHead(vector.get(0).toString());
//                            glPojo.setGlName(vector.get(1).toString());

                            if (acType.contains("LIEN")) {
                                for (int m = 0; m < dates.size(); m++) {
                                    BigDecimal accbal = new BigDecimal("0");
                                    BigDecimal accbal1 = new BigDecimal("0");
                                    String dt = dates.get(m);
                                    BigDecimal acctBal1 = quarterlyRemote.getAcCodeAmount(acType, dt);
//                                    List<LienReportPojo> acutalBalList = loanRemote.getLienReport(acType.substring(acType.indexOf(".") + 1), dt, orgnCode);
//                                    for (LienReportPojo pojo : acutalBalList) {
//                                        accbal = new BigDecimal(pojo.getActualValue());
//                                        accbal1 = accbal1.add(accbal);
//                                        // BigDecimal accbal1 = new BigDecimal(accbal);
//                                    }
//                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, (accbal1.divide(repOpt)).abs());
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, (acctBal1.divide(repOpt)).abs());
                                }
                            } else if (acType.contains("SSI")) {
                                for (int m = 0; m < dates.size(); m++) {
                                    BigDecimal accbal = new BigDecimal("0");
                                    BigDecimal accbal1 = new BigDecimal("0");
                                    String dt = dates.get(m);
                                    // BigDecimal acctBal1 = quarterlyRemote.getAcCodeAmount(acType, dt);
//                                    List<LienReportPojo> acutalBalList = loanRemote.getLienReport(acType.substring(acType.indexOf(".") + 1), dt, orgnCode);
//                                    for (LienReportPojo pojo : acutalBalList) {
//                                        accbal = new BigDecimal(pojo.getActualValue());
//                                        accbal1 = accbal1.add(accbal);
//                                        // BigDecimal accbal1 = new BigDecimal(accbal);
//                                    }
                                    accbal1 = accbal1.add(quarterlyRemote.getAcCodeAmount(acType, dt).abs());
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, (accbal1.divide(repOpt)).abs());
                                    //rbiSossPojo = setReportAmount(rbiSossPojo, m, (acctBal1.divide(repOpt)).abs());
                                }
                            } else {
                                for (int m = 0; m < dates.size(); m++) {
                                    String dt = dates.get(m);
                                    params.setDate(dt);
                                    params.setToDate(dt);
//                                    if(accountNature.equalsIgnoreCase("CA")||accountNature.equalsIgnoreCase("DL")||accountNature.equalsIgnoreCase("TL")){
//                                        List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", dt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode,"Y");
//                                        params.setNpaAcList(resultList);
//                                    }
                                    acctBal = rbiReportRemote.getAcctsAndBal(params);
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, (acctBal.getBalance().divide(repOpt)).abs());
                                }
                            }
//                            for (int m = 0; m < dates.size(); m++) {
//                                String dt = dates.get(m);
//                                params.setDate(dt);
//                                acctBal = rbiReportRemote.getAcctsAndBal(params);
//                                glPojo = setGlReportAmount(glPojo, m, (acctBal.getBalance().divide(repOpt)).abs());
//                            }
//                            glHeadList.add(glPojo);
//                            if (!glHeadList.isEmpty()) {
//                                for (int t = 0; t < glHeadList.size(); t++) {
//                                    GlHeadPojo obj = glHeadList.get(t);
//                                    rbiSossPojo.setAmt(obj.getFirstAmt());
//                                    rbiSossPojo.setSecondAmount(obj.getSecondAmt());
//                                    rbiSossPojo.setThirdAmount(obj.getThirsAmt());
//                                    rbiSossPojo.setFourthAmount(obj.getFourthAmt());
//                                    rbiSossPojo.setFifthAmount(obj.getFifthAmt());
//                                    rbiSossPojo.setSixthAmount(obj.getSixthAmt());
//                                    rbiSossPojo.setSeventhAmount(obj.getSeventhAmt());
//                                    rbiSossPojo.setAcType(acType);                                    
//                                }
//                            }
                            rbiSossPojo.setsNo(sNo);
                            rbiSossPojo.setAcNature(acNature);
                            rbiSossPojo.setAcType(acType);
                            rbiSossPojo.setClassification(classification);
                            rbiSossPojo.setCountApplicable(countApplicable);
                            rbiSossPojo.setFormula(formula);
                            rbiSossPojo.setGlHeadTo(glHeadTo);
                            rbiSossPojo.setGlheadFrom(glHeadFrom);
                            rbiSossPojo.setNpaClassification(npaClassification);
//                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
//                            rbiSossPojo.setRangeFrom(rangeFrom);
                            rbiSossPojo.setRangeTo(rangeTo);
                            rbiSossPojo.setReportName(reportName);
                            rbiSossPojo.setgNo(gNo);
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
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
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo);
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
                                                    rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
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
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;
                        }
//                        }
                    } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
//                        GlHeadPojo glHeadPojo = new GlHeadPojo();
//                        glPojoList = getGLHeadAndBal(params);
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8),acname").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            
                            GlHeadPojo glPojo = new GlHeadPojo();
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                params.setDate(dt);
                                params.setToDate(dt);
                                BigDecimal acctBal1 = new BigDecimal(getForm1GLHeadBal(orgnCode, glPojo.getGlHead(), dt, classification, fSGNo));
                                glPojo = setGlReportAmount(glPojo, m, (acctBal1.divide(repOpt)).abs());
                            }
                            glPojoList.add(glPojo);
                        }
                        if (glPojoList.size() > 0) {
                            rbiSossPojo.setAmt(new BigDecimal("0.00"));
                            rbiSossPojo.setSecondAmount(new BigDecimal("0.00"));
                            rbiSossPojo.setThirdAmount(new BigDecimal("0.00"));
                            rbiSossPojo.setFourthAmount(new BigDecimal("0.00"));
                            rbiSossPojo.setFifthAmount(new BigDecimal("0.00"));
                            rbiSossPojo.setSixthAmount(new BigDecimal("0.00"));
                            rbiSossPojo.setSeventhAmount(new BigDecimal("0.00"));
                            
                            
                            
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
//                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
//                            rbiSossPojo.setRangeFrom(rangeFrom);
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
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setgNo(preGNo);
                                    rbiSossPojo.setsGNo(preSGNo);
                                    rbiSossPojo.setSsGNo(preSsGNo);
                                    rbiSossPojo.setSssGNo(preSssGNo);
                                    rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                    
                                } else {
                                    rbiSossPojo.setgNo(gNo);
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                    
                                    rbiPojoTable.add(rbiSossPojo);
                                    preGNo = gNo;
                                    preSGNo = sGNo;
                                    preSsGNo = ssGNo;
                                    preSssGNo = sssGNo;
                                    preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                                    preFormula = formula;
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                rbiSossPojo.setDescription(description);
                                
                                rbiPojoTable.add(rbiSossPojo);
                            }


                            /* If GL Head Series have multiple GL Head */
                            for (int j = 0; j < glPojoList.size(); j++) {
                                rbiSossPojo = new RbiSossPojo();
                                GlHeadPojo glHeadPo = glPojoList.get(j);
                                
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
                                rbiSossPojo.setAmt(glHeadPo.getFirstAmt());
                                rbiSossPojo.setSecondAmount(glHeadPo.getSecondAmt());
                                rbiSossPojo.setThirdAmount(glHeadPo.getThirsAmt());
                                rbiSossPojo.setFourthAmount(glHeadPo.getFourthAmt());
                                rbiSossPojo.setFifthAmount(glHeadPo.getFifthAmt());
                                rbiSossPojo.setSixthAmount(glHeadPo.getSixthAmt());
                                rbiSossPojo.setSeventhAmount(glHeadPo.getSeventhAmt());
                                if (reportFormat.equalsIgnoreCase("N")) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
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
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(j + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                        rbiSossPojo.setsGNo(sGNo);
                                        rbiSossPojo.setSsGNo(ssGNo);
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        rbiSossPojo.setSsssGNo(ssssGNo);
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
                                                        rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
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
                                preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                                preFormula = formula;
                                
                            }
                        }
                    }
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                    preFormula = formula;
                }
            }

            //==========================================================================================================================
            /*Formula Processing*/
            for (int i = 0; i < rbiPojoTable.size(); i++) {
                RbiSossPojo pojo = rbiPojoTable.get(i);
                //System.out.println("hello; "+i+"; " + pojo.getgNo() + ";" + pojo.getsGNo() + ";" + pojo.getSsGNo() + ";" + pojo.getSssGNo() + ";" + pojo.getSsssGNo() + ";" + pojo.getDescription() + "; formula:" + pojo.getFormula());
                if (!(pojo.getFormula() == null || pojo.getFormula().equals(""))) {
                    if (pojo.getFormula().equalsIgnoreCase("CRR") || pojo.getFormula().equalsIgnoreCase("SLR")) {
                        for (int k = 0; k < dates.size(); k++) {
                            String dt = dates.get(k);
                            List<CrrSlrPojo> crrList = new ArrayList<CrrSlrPojo>();
                            if (pojo.getFormula().equalsIgnoreCase("CRR")) {
                                crrList = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)), "ALL");
                            } else if (pojo.getFormula().equalsIgnoreCase("SLR")) {
                                crrList = hoRemote.getSlrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)), "ALL");
                            }
                            pojo = setReportAmount(pojo, k, new BigDecimal(crrList.get(0).getReqToBeMaint()));
                        }
                    } else {
                        BigDecimal[] arr = getValueFromFormulaForFormOne(rbiPojoTable, pojo.getFormula(), dates);
                        pojo.setAmt(arr[0]);
                        pojo.setSecondAmount(arr[1]);
                        pojo.setThirdAmount(arr[2]);
                        pojo.setFourthAmount(arr[3]);
                        pojo.setFifthAmount(arr[4]);
                        pojo.setSixthAmount(arr[5]);
                        pojo.setSeventhAmount(arr[6]);
                    }
                }
            }
            /*NDTL Processing-(I-III)+II*/
            RbiSossPojo nopF = new RbiSossPojo();//It is (I-III) Part.
            RbiSossPojo nopS = new RbiSossPojo();//It is (II) Part.
            for (int i = 0; i < rbiPojoTable.size(); i++) {
                RbiSossPojo pojo = rbiPojoTable.get(i);
                if (pojo.getfSssGNo().equalsIgnoreCase("NOP-F")) {
                    nopF = pojo;
                } else if (pojo.getfSssGNo().equalsIgnoreCase("NOP-S")) {
                    nopS = pojo;
                }
            }
            
            if (nopF.getfSssGNo().equals("NOP-F") && nopS.getfSssGNo().equals("NOP-S")) {
                RbiSossPojo ntdlPojo = new RbiSossPojo();
                ntdlPojo.setsNo(nopF.getsNo());
                ntdlPojo.setReportName(nopF.getReportName());
                ntdlPojo.setDescription(nopF.getDescription());
                ntdlPojo.setgNo(nopF.getgNo());
                ntdlPojo.setsGNo(nopF.getsGNo());
                ntdlPojo.setSsGNo(nopF.getSsGNo());
                ntdlPojo.setSssGNo(nopF.getSssGNo());
                ntdlPojo.setSsssGNo(nopF.getSsssGNo());
                ntdlPojo.setClassification(nopF.getClassification());
                ntdlPojo.setAcNature(nopF.getAcNature());
                ntdlPojo.setGlheadFrom(nopF.getGlheadFrom());
                ntdlPojo.setGlHeadTo(nopF.getGlHeadTo());
                ntdlPojo.setFormula(nopF.getFormula());
                ntdlPojo.setCountApplicable(nopF.getCountApplicable());
                ntdlPojo.setfSssGNo(nopF.getfSssGNo());
                ntdlPojo.setAcType("");

                /*First Date Amount*/
                if (nopF.getAmt().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setAmt(nopS.getAmt());
                } else {
                    ntdlPojo.setAmt(nopF.getAmt().add(nopS.getAmt()));
                }
                /*Second Date Amount*/
                if (nopF.getSecondAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setSecondAmount(nopS.getSecondAmount());
                } else {
                    ntdlPojo.setSecondAmount(nopF.getSecondAmount().add(nopS.getSecondAmount()));
                }
                /*Third Date Amount*/
                if (nopF.getThirdAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setThirdAmount(nopS.getThirdAmount());
                } else {
                    ntdlPojo.setThirdAmount(nopF.getThirdAmount().add(nopS.getThirdAmount()));
                }
                /*Fourth Date Amount*/
                if (nopF.getFourthAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setFourthAmount(nopS.getFourthAmount());
                } else {
                    ntdlPojo.setFourthAmount(nopF.getFourthAmount().add(nopS.getFourthAmount()));
                }
                /*Fifth Date Amount*/
                if (nopF.getFifthAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setFifthAmount(nopS.getFifthAmount());
                } else {
                    ntdlPojo.setFifthAmount(nopF.getFifthAmount().add(nopS.getFifthAmount()));
                }
                /*Sixth Date Amount*/
                if (nopF.getSixthAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setSixthAmount(nopS.getSixthAmount());
                } else {
                    ntdlPojo.setSixthAmount(nopF.getSixthAmount().add(nopS.getSixthAmount()));
                }
                /*Seventh Date Amount*/
                if (nopF.getSeventhAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setSeventhAmount(nopS.getSeventhAmount());
                } else {
                    ntdlPojo.setSeventhAmount(nopF.getSeventhAmount().add(nopS.getSeventhAmount()));
                }
                
                rbiPojoTable.remove(nopF);
                rbiPojoTable.remove(nopS);
                rbiPojoTable.add(ntdlPojo);
            }
            /*Sorting of list*/
            ComparatorChain chain = new ComparatorChain();
            chain.addComparator(new ComparatorByReportName());
            chain.addComparator(new ComparatorByGno());
            chain.addComparator(new ComparatorBySgno());
            chain.addComparator(new ComparatorBySSgno());
            chain.addComparator(new ComparatorBySSSgno());
            chain.addComparator(new ComparatorBySSSSgno());
            Collections.sort(rbiPojoTable, chain);
            /*Retuning List*/
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public List<GlHeadPojo> getAsOnDateBalanceList(String brCode, List<String> dates) throws ApplicationException {
        try {
            List<GlHeadPojo> balanceList = new ArrayList<GlHeadPojo>();;
            for (int m = 0; m < dates.size(); m++) {
                String dt = dates.get(m);
                String query = CbsUtil.getBranchWiseQuery(brCode, dt);
                List glResultList = em.createNativeQuery(query).getResultList();
//                System.out.println("query:"+query);
//                balanceList = new ArrayList<GlHeadPojo>();
                GlHeadPojo balPojo;
                for (int i = 0; i < glResultList.size(); i++) {
                    Vector vect = (Vector) glResultList.get(i);
                    balPojo = new GlHeadPojo();
                    //                 System.out.println("gl head is =" + vect.get(0)+"; value:"+ vect.get(1));
                    balPojo.setDate(dt);
                    balPojo.setGlHead(vect.get(0).toString());
                    balPojo.setBalance(new BigDecimal(vect.get(1).toString()));
                    balanceList.add(balPojo);
                }
            }
            return balanceList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public List<GlHeadPojo> getInsertedGlbList(List<String> dates) throws ApplicationException {
        try {
            List<GlHeadPojo> balanceList = new ArrayList<GlHeadPojo>();
            for (int m = 0; m < dates.size(); m++) {
                String dt = dates.get(m);
//                String query = CbsUtil.getBranchWiseQuery(brCode, dt);
                List glResultList = em.createNativeQuery("select accode as acno, cast(ifnull(bal,0) as decimal(25,2)) as amt from ho_crr_asset_liab where date = '" + dt + "'").getResultList();
                GlHeadPojo balPojo;
                if (glResultList.size() > 0) {
                    for (int i = 0; i < glResultList.size(); i++) {
                        Vector vect = (Vector) glResultList.get(i);
                        balPojo = new GlHeadPojo();
                        //                 System.out.println("gl head is =" + vect.get(0)+"; value:"+ vect.get(1));
                        balPojo.setDate(dt);
                        balPojo.setGlHead(vect.get(0).toString());
                        balPojo.setBalance(new BigDecimal(vect.get(1).toString()));
                        balanceList.add(balPojo);
                    }
                } else {
                    throw new ApplicationException("Please run the process through *Month End Balance Entry* Form for " + dmy.format(ymd.parse(dt)) + " .");
                }
            }
            return balanceList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public List<GlHeadPojo> getAsOnDateBalanceBetweenDateList(String brCode, String frDt, String toDt) throws ApplicationException {
        try {
            List<GlHeadPojo> balanceList = new ArrayList<GlHeadPojo>();;
            
            String query = CbsUtil.getBranchWiseQueryBetweenDate(brCode, frDt, toDt);
//            System.out.println("Query OSS2: "+query);
            List glResultList = em.createNativeQuery(query).getResultList();
//                System.out.println("query:"+query);
//                balanceList = new ArrayList<GlHeadPojo>();
            GlHeadPojo balPojo;
            for (int i = 0; i < glResultList.size(); i++) {
                Vector vect = (Vector) glResultList.get(i);
                balPojo = new GlHeadPojo();
//                 System.out.println("gl head is =" + vect.get(0)+"; value:"+ vect.get(1));
                balPojo.setDate(toDt);
                balPojo.setGlHead(vect.get(0).toString());
                balPojo.setBalance(new BigDecimal(vect.get(1).toString()));
                balanceList.add(balPojo);
            }
            
            return balanceList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param dates
     * @param repOpt
     * @param reportName
     * @param orgnCode
     * @param reportIn
     * @return
     * @throws ApplicationException
     */
    public List<RbiSossPojo> getForm1DetailsAsPerGlb(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,
            String reportIn, String reportFormat) throws ApplicationException {
        //        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        List<RbiSossPojo> dtlDataList = new ArrayList<RbiSossPojo>();
        List osBlancelist = getAsOnDateBalanceList(orgnCode, dates);
        List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
        try {
            Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
            String preFormula = "";
            List<CrrSlrPojo> crrNDTLListNew = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dates.get(0))), dmy.format(ymd.parse(dates.get(dates.size() - 1))), "NDTL");
//            List<CrrSlrPojo> crrNDTLListNew = hoRemote.getCrrDetailsOnly(orgnCode, reportIn, dmy.format(ymd.parse(dates.get(0))), dmy.format(ymd.parse(dates.get(dates.size()-1))));                        
            List<CrrSlrPojo> slrNDTLListNew = hoRemote.getSlrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dates.get(0))), dmy.format(ymd.parse(dates.get(dates.size() - 1))), "NDTL");
//            List<CrrSlrPojo> slrNDTLListNew = hoRemote.getSlrDetailsOnly(orgnCode, reportIn, dmy.format(ymd.parse(dates.get(0))), dmy.format(ymd.parse(dates.get(dates.size()-1))));

            
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification, ifnull(REFER_INDEX,'')  from cbs_ho_rbi_stmt_report where report_name = '" + reportName + "' and '" + dates.get(0) + "' between EFF_FR_DT and EFF_TO_DT  order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned), sno").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT for Form1.");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
//                    System.out.println("i:" + i);
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
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
//                    String rangeBaseOn = oss1Vect.get(14).toString();
//                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString();
                    String refIndex = oss1Vect.get(24).toString();
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
                    rbiSossPojo.setSecondAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setThirdAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setFourthAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setFifthAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setSixthAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setSeventhAmount(new BigDecimal("0.00"));
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setNpaClassification(npaClassification);
//                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
//                        rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    rbiSossPojo.setRefIndex(refIndex);
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setgNo(preGNo);
                            rbiSossPojo.setsGNo(preSGNo);
                            rbiSossPojo.setSsGNo(preSsGNo);
                            rbiSossPojo.setSssGNo(preSssGNo);
                            rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                            
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
                    params.setAcType(acType.contains(".") ? acType.substring(acType.indexOf(".") + 1) : acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
//                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
//                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
//                    Long noOfAc = 0l;
//                    BigDecimal bal = new BigDecimal("0");
                    AcctBalPojo acctBal = new AcctBalPojo();
                    List<GlHeadPojo> glPojoList = new ArrayList<GlHeadPojo>();
                    
                    if (!((acNature == null) || acNature.equalsIgnoreCase("")) || !((acType == null) || acType.equalsIgnoreCase(""))) {
                        List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
//                        if ((rangeBaseOn == null) || rangeBaseOn.equalsIgnoreCase("")) {
                        List natureList = null;
                        if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                            natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                    + "acctnature in ('" + params.getNature() + "')").getResultList();
                        } else if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                            natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                    + "acctcode in ('" + params.getAcType() + "')").getResultList();
                        }
                        
                        for (int z = 0; z < (acType.equalsIgnoreCase("LIEN.ALL") ? 1 : natureList.size()); z++) {
                            rbiSossPojo = new RbiSossPojo();
                            Vector vector = null;
                            params.setNature("");
                            if (acType.equalsIgnoreCase("LIEN.ALL")) {
                                params.setAcType("ALL");
                            } else {
                                vector = (Vector) natureList.get(z);
                                params.setAcType(vector.get(0).toString());
                            }
                            if (acType.contains("LIEN")) {
                                for (int m = 0; m < dates.size(); m++) {
                                    BigDecimal accbal = new BigDecimal("0");
                                    BigDecimal accbal1 = new BigDecimal("0");
                                    String dt = dates.get(m);
                                    BigDecimal acctBal1 = quarterlyRemote.getAcCodeAmount(acType, dt);
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, (acctBal1.divide(repOpt)).abs());
                                }
                            } else if (acType.contains("SSI")) {
                                for (int m = 0; m < dates.size(); m++) {
                                    BigDecimal accbal = new BigDecimal("0");
                                    BigDecimal accbal1 = new BigDecimal("0");
                                    String dt = dates.get(m);
                                    accbal1 = accbal1.add(quarterlyRemote.getAcCodeAmount(acType, dt).abs());
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, (accbal1.divide(repOpt)).abs());
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
                                    tableName = commonRemote.getTableName(acNature);
                                } else if (!acType.equalsIgnoreCase("")) {
                                    List acnature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
                                    if (!acnature.isEmpty()) {
                                        Vector vect = (Vector) acnature.get(0);
                                        acNature = vect.get(0).toString();
                                        tableName = commonRemote.getTableName(acNature);
                                    }
                                }
                                String cls = "";
                                if (params.getClassification().equalsIgnoreCase("L")) {
                                    cls = "1";
                                } else if (params.getClassification().equalsIgnoreCase("A")) {
                                    cls = "-1";
                                }
                                for (int m = 0; m < dates.size(); m++) {
                                    if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                        List dataList = em.createNativeQuery("select count(distinct a.acno), ifnull(sum(a.amt),0) from ( "
                                                + " select  a.acno, cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) as amt from " + tableName + " a,accountmaster b "
                                                + " where  a.acno in (select acno from " + tableName + " WHERE DT <= '" + dates.get(m) + "' group by acno   having sign(sum(cramt-dramt)) = " + cls + " )  "
                                                + " and (b.closingdate is null or b.closingdate = '' or b.closingdate > '" + dates.get(m) + "' )   "
                                                + " and a.acno = b.acno and a.dt <='" + dates.get(m) + "' " + acctCatQuery + " "
                                                + " and b.accttype in (select acctcode from accounttypemaster where acctcode = '" + vector.get(0).toString() + "') group by b.acno  ) a").getResultList();
                                        if (!dataList.isEmpty()) {
                                            Vector vect = (Vector) dataList.get(0);
                                            amtAsPerClass = new BigDecimal(vect.get(1).toString());
                                        }
                                    } else {
                                        List dataList = em.createNativeQuery("select substring(a.acno,3,2) as acno, cast(ifnull(sum(a.cramt-a.dramt),0)as decimal(25,2)) as amt from " + tableName + " a ,td_accountmaster b where a.dt <='" + dates.get(m) + "'  and a.auth ='Y' and a.closeflag is null  and a.acno =b.acno  and substring(a.acno,3,2) = " + vector.get(0).toString() + " " + acctCatQuery + " group by substring(a.acno,3,2) ;").getResultList();
                                        if (!dataList.isEmpty()) {
                                            Vector vect = (Vector) dataList.get(0);
                                            amtAsPerClass = new BigDecimal(vect.get(1).toString());
                                        }
                                    }
                                    if ((amtAsPerClass.compareTo(new BigDecimal("0"))) == 0) {
                                        rbiSossPojo = setReportAmount(rbiSossPojo, m, amtAsPerClass);
                                    } else {
                                        rbiSossPojo = setReportAmount(rbiSossPojo, m, amtAsPerClass.divide(repOpt));
                                    }
                                }
                            } else {
                                for (int m = 0; m < dates.size(); m++) {
                                    String dt = dates.get(m);
                                    params.setDate(dt);
                                    params.setToDate(dt);
                                    GlHeadPojo newBalPojo = getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dt);
                                    if (newBalPojo == null) {
                                        rbiSossPojo = setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                    } else {
                                        rbiSossPojo = setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().abs().divide(repOpt));
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
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector == null ? "ALL" : vector.get(1).toString()));
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
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
                                    rbiSossPojo.setsGNo(sGNo);
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    rbiSossPojo.setSssGNo(sssGNo);
                                    rbiSossPojo.setSsssGNo(ssssGNo);
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
                                                    rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(vector.get(1).toString()));
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
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;
                        }
//                        }
                    } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
//                        GlHeadPojo glHeadPojo = new GlHeadPojo();
//                        glPojoList = getGLHeadAndBal(params);
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8),acname").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
                            GlHeadPojo glPojo = new GlHeadPojo();
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
//                            GlHeadPojo newBalPojo = hoRemote.getOSBalance(osBlancelist, vector.get(0).toString(), classification);
//                            if (newBalPojo == null) {
//                                glPojo.setBalance(new BigDecimal(0.00));
//                            } else {
//                                glPojo.setBalance(newBalPojo.getBalance().divide(repOpt));
//                            }
                            rbiSossPojo = new RbiSossPojo();
                            for (int m = 0; m < dates.size(); m++) {
                                String dt = dates.get(m);
                                params.setDate(dt);
                                params.setToDate(dt);
                                GlHeadPojo newBalPojo = getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, dt);
                                if (newBalPojo == null) {
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, new BigDecimal(0.00));
                                } else {
                                    rbiSossPojo = setReportAmount(rbiSossPojo, m, newBalPojo.getBalance().abs().divide(repOpt));
                                }
//                                BigDecimal acctBal1 = new BigDecimal(getForm1GLHeadBal(orgnCode, glPojo.getGlHead(), dt, classification, fSGNo));
//                                glPojo = setGlReportAmount(glPojo, m, (acctBal1.divide(repOpt)).abs());                                
                            }
//                            glPojoList.add(glPojo);
//                        }
//                        if (glPojoList.size() > 0) {
//                            rbiSossPojo.setAmt(new BigDecimal("0.00"));
//                            rbiSossPojo.setSecondAmount(new BigDecimal("0.00"));
//                            rbiSossPojo.setThirdAmount(new BigDecimal("0.00"));
//                            rbiSossPojo.setFourthAmount(new BigDecimal("0.00"));
//                            rbiSossPojo.setFifthAmount(new BigDecimal("0.00"));
//                            rbiSossPojo.setSixthAmount(new BigDecimal("0.00"));
//                            rbiSossPojo.setSeventhAmount(new BigDecimal("0.00"));
//
//
//
//                            rbiSossPojo.setsNo(sNo);
//                            rbiSossPojo.setAcNature(acNature);
//                            rbiSossPojo.setAcType(acType);
//                            rbiSossPojo.setClassification(classification);
//                            rbiSossPojo.setCountApplicable(countApplicable);
//                            rbiSossPojo.setDescription(description);
//                            rbiSossPojo.setFormula(formula);
//                            rbiSossPojo.setGlHeadTo(glHeadTo);
//                            rbiSossPojo.setGlheadFrom(glHeadFrom);
//                            rbiSossPojo.setNpaClassification(npaClassification);
////                            rbiSossPojo.setRangeBaseOn(rangeBaseOn);
////                            rbiSossPojo.setRangeFrom(rangeFrom);
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
//                            if (reportFormat.equalsIgnoreCase("N")) {
//                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
//                                    rbiSossPojo.setgNo(preGNo);
//                                    rbiSossPojo.setsGNo(preSGNo);
//                                    rbiSossPojo.setSsGNo(preSsGNo);
//                                    rbiSossPojo.setSssGNo(preSssGNo);
//                                    rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
//
//                                } else {
//                                    rbiSossPojo.setgNo(gNo);
//                                    rbiSossPojo.setsGNo(sGNo);
//                                    rbiSossPojo.setSsGNo(ssGNo);
//                                    rbiSossPojo.setSssGNo(sssGNo);
//                                    rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
//
//                                    rbiPojoTable.add(rbiSossPojo);
//                                    preGNo = gNo;
//                                    preSGNo = sGNo;
//                                    preSsGNo = ssGNo;
//                                    preSssGNo = sssGNo;
//                                    preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
//                                    preFormula = formula;
//                                }
//                            } else {
//                                rbiSossPojo.setgNo(gNo);
//                                rbiSossPojo.setsGNo(sGNo);
//                                rbiSossPojo.setSsGNo(ssGNo);
//                                rbiSossPojo.setSssGNo(sssGNo);
//                                rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
//                                rbiSossPojo.setDescription(description);
//
//                                rbiPojoTable.add(rbiSossPojo);
//                            }


                            /* If GL Head Series have multiple GL Head */
//                            for (int j = 0; j < glPojoList.size(); j++) {
//                                rbiSossPojo = new RbiSossPojo();
//                                GlHeadPojo glHeadPo = glPojoList.get(j);

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
//                                rbiSossPojo.setAmt(glHeadPo.getFirstAmt());
//                                rbiSossPojo.setSecondAmount(glHeadPo.getSecondAmt());
//                                rbiSossPojo.setThirdAmount(glHeadPo.getThirsAmt());
//                                rbiSossPojo.setFourthAmount(glHeadPo.getFourthAmt());
//                                rbiSossPojo.setFifthAmount(glHeadPo.getFifthAmt());
//                                rbiSossPojo.setSixthAmount(glHeadPo.getSixthAmt());
//                                rbiSossPojo.setSeventhAmount(glHeadPo.getSeventhAmt());
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
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
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo == null ? 0 : preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
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
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo == null ? 0 : ssssGNo);
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
                            preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                            preFormula = formula;

//                            }
                        }
                    }
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo == null ? 0 : ssssGNo;
                    preFormula = formula;
                }
            }
//            List<CrrSlrPojo> crrNDTLList1 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList2 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList3 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList4 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList5 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList6 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList7 = new ArrayList<CrrSlrPojo>();
//            List<CrrSlrPojo> crrNDTLList8 = new ArrayList<CrrSlrPojo>();
//            for (int l =0;l<dates.size();l++){
//                String dt= dates.get(l);
//                for(int m=0; m<crrNDTLListNew.size();m++){
//                    if(ymd.parse(crrNDTLListNew.get(m).getDate()).equals(ymd.parse(dt))){
//                        
//                    }                    
//                }
//                if(l==0){
//                    crrNDTLList1 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));                    
//                } else if(l==1){
//                    crrNDTLList2 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                } else if(l==2){
//                    crrNDTLList3 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                } else if(l==3){
//                    crrNDTLList4 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                } else if(l==4){
//                    crrNDTLList5 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                } else if(l==5){
//                    crrNDTLList6 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                } else if(l==6){
//                    crrNDTLList7 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                } else if(l==7){
//                    crrNDTLList8 = hoRemote.getCrrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
//                }
//            }
            /*Formula Processing*/
            for (int i = 0; i < rbiPojoTable.size(); i++) {
                RbiSossPojo pojo = rbiPojoTable.get(i);
                String refIndex = pojo.getRefIndex();
//                System.out.println("hello;" + pojo.getgNo() + ";" + pojo.getsGNo() + ";" + pojo.getSsGNo() + ";" + pojo.getSssGNo() + ";" + pojo.getSsssGNo() + ";" + pojo.getDescription() + "; formula:" + pojo.getFormula());
                if (!(pojo.getFormula() == null || pojo.getFormula().equals(""))) {
                    if (pojo.getFormula().equalsIgnoreCase("CRR") || pojo.getFormula().equalsIgnoreCase("SLR") || pojo.getFormula().equalsIgnoreCase("NDTL-P")) {
                        for (int k = 0; k < dates.size(); k++) {
                            String dt = dates.get(k);
                            List<CrrSlrPojo> crrList = new ArrayList<CrrSlrPojo>();
                            if (pojo.getFormula().equalsIgnoreCase("CRR") || pojo.getFormula().equalsIgnoreCase("NDTL-P")) {
                                for (int m = 0; m < crrNDTLListNew.size(); m++) {
                                    if (dmy.parse(crrNDTLListNew.get(m).getDate()).equals(ymd.parse(dt))) {
                                        CrrSlrPojo pojoCrr = new CrrSlrPojo();
                                        pojoCrr.setDate(crrNDTLListNew.get(m).getDate());
                                        pojoCrr.setNtdl(crrNDTLListNew.get(m).getNtdl());
                                        pojoCrr.setReqToBeMaint(crrNDTLListNew.get(m).getReqToBeMaint());
                                        crrList.add(pojoCrr);
                                    }
                                }
//                                if(k==0){
//                                    crrList = crrNDTLList1;
////                                    crrList = crrNDTLListNew.get(0);
//                                } else if(k==1){
//                                    crrList = crrNDTLList2;
//                                } else if(k==2){
//                                    crrList = crrNDTLList3;
//                                } else if(k==3){
//                                    crrList = crrNDTLList4;
//                                }  else if(k==4){
//                                    crrList = crrNDTLList5;
//                                }  else if(k==5){
//                                    crrList = crrNDTLList6;
//                                }  else if(k==6){
//                                    crrList = crrNDTLList7;
//                                }  else if(k==7){
//                                    crrList = crrNDTLList8;
//                                }
                            } else if (pojo.getFormula().equalsIgnoreCase("SLR")) {
                                for (int m = 0; m < slrNDTLListNew.size(); m++) {
                                    if (dmy.parse(slrNDTLListNew.get(m).getDate()).equals(ymd.parse(dt))) {
                                        CrrSlrPojo pojoCrr = new CrrSlrPojo();
                                        pojoCrr.setDate(slrNDTLListNew.get(m).getDate());
                                        pojoCrr.setNtdl(slrNDTLListNew.get(m).getNtdl());
                                        pojoCrr.setReqToBeMaint(slrNDTLListNew.get(m).getReqToBeMaint());
                                        crrList.add(pojoCrr);
                                    }
                                }
//                                crrList = hoRemote.getSlrDetails(orgnCode, reportIn, dmy.format(ymd.parse(dt)), dmy.format(ymd.parse(dt)));
                            }
                            if (pojo.getFormula().equalsIgnoreCase("NDTL-P")) {
                                pojo = setReportAmount(pojo, k, new BigDecimal(crrList.get(0).getNtdl()));
                            } else {
                                pojo = setReportAmount(pojo, k, new BigDecimal(crrList.get(0).getReqToBeMaint()));
                            }
                        }
                    } else {
                        BigDecimal[] arr = getValueFromFormulaForFormOne(rbiPojoTable, pojo.getFormula(), dates);
                        pojo.setAmt((refIndex.equalsIgnoreCase("+ive") && arr[0].doubleValue() < 0) ? new BigDecimal("0") : arr[0]);
                        pojo.setSecondAmount((refIndex.equalsIgnoreCase("+ive") && arr[1].doubleValue() < 0) ? new BigDecimal("0") : arr[1]);
                        pojo.setThirdAmount((refIndex.equalsIgnoreCase("+ive") && arr[2].doubleValue() < 0) ? new BigDecimal("0") : arr[2]);
                        pojo.setFourthAmount((refIndex.equalsIgnoreCase("+ive") && arr[3].doubleValue() < 0) ? new BigDecimal("0") : arr[3]);
                        pojo.setFifthAmount((refIndex.equalsIgnoreCase("+ive") && arr[4].doubleValue() < 0) ? new BigDecimal("0") : arr[4]);
                        pojo.setSixthAmount((refIndex.equalsIgnoreCase("+ive") && arr[5].doubleValue() < 0) ? new BigDecimal("0") : arr[5]);
                        pojo.setSeventhAmount((refIndex.equalsIgnoreCase("+ive") && arr[6].doubleValue() < 0) ? new BigDecimal("0") : arr[6]);

//                        pojo.setSecondAmount(arr[0]);                        
//                        pojo.setSecondAmount(arr[1]);
//                        pojo.setThirdAmount(arr[2]);
//                        pojo.setFourthAmount(arr[3]);
//                        pojo.setFifthAmount(arr[4]);
//                        pojo.setSixthAmount(arr[5]);
//                        pojo.setSeventhAmount(arr[6]);
                    }
                }
            }
            /*NDTL Processing-(I-III)+II*/
            RbiSossPojo nopF = new RbiSossPojo();//It is (I-III) Part.
            RbiSossPojo nopS = new RbiSossPojo();//It is (II) Part.
            for (int i = 0; i < rbiPojoTable.size(); i++) {
                RbiSossPojo pojo = rbiPojoTable.get(i);
                if (pojo.getfSssGNo().equalsIgnoreCase("NOP-F")) {
                    nopF = pojo;
                } else if (pojo.getfSssGNo().equalsIgnoreCase("NOP-S")) {
                    nopS = pojo;
                }
            }
            
            if (nopF.getfSssGNo().equals("NOP-F") && nopS.getfSssGNo().equals("NOP-S")) {
                RbiSossPojo ntdlPojo = new RbiSossPojo();
                ntdlPojo.setsNo(nopF.getsNo());
                ntdlPojo.setReportName(nopF.getReportName());
                ntdlPojo.setDescription(nopF.getDescription());
                ntdlPojo.setgNo(nopF.getgNo());
                ntdlPojo.setsGNo(nopF.getsGNo());
                ntdlPojo.setSsGNo(nopF.getSsGNo());
                ntdlPojo.setSssGNo(nopF.getSssGNo());
                ntdlPojo.setSsssGNo(nopF.getSsssGNo());
                ntdlPojo.setClassification(nopF.getClassification());
                ntdlPojo.setAcNature(nopF.getAcNature());
                ntdlPojo.setGlheadFrom(nopF.getGlheadFrom());
                ntdlPojo.setGlHeadTo(nopF.getGlHeadTo());
                ntdlPojo.setFormula(nopF.getFormula());
                ntdlPojo.setCountApplicable(nopF.getCountApplicable());
                ntdlPojo.setfSssGNo(nopF.getfSssGNo());
                ntdlPojo.setAcType("");

                /*First Date Amount*/
                if (nopF.getAmt().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setAmt(nopS.getAmt());
                } else {
                    ntdlPojo.setAmt(nopF.getAmt().add(nopS.getAmt()));
                }
                /*Second Date Amount*/
                if (nopF.getSecondAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setSecondAmount(nopS.getSecondAmount());
                } else {
                    ntdlPojo.setSecondAmount(nopF.getSecondAmount().add(nopS.getSecondAmount()));
                }
                /*Third Date Amount*/
                if (nopF.getThirdAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setThirdAmount(nopS.getThirdAmount());
                } else {
                    ntdlPojo.setThirdAmount(nopF.getThirdAmount().add(nopS.getThirdAmount()));
                }
                /*Fourth Date Amount*/
                if (nopF.getFourthAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setFourthAmount(nopS.getFourthAmount());
                } else {
                    ntdlPojo.setFourthAmount(nopF.getFourthAmount().add(nopS.getFourthAmount()));
                }
                /*Fifth Date Amount*/
                if (nopF.getFifthAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setFifthAmount(nopS.getFifthAmount());
                } else {
                    ntdlPojo.setFifthAmount(nopF.getFifthAmount().add(nopS.getFifthAmount()));
                }
                /*Sixth Date Amount*/
                if (nopF.getSixthAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setSixthAmount(nopS.getSixthAmount());
                } else {
                    ntdlPojo.setSixthAmount(nopF.getSixthAmount().add(nopS.getSixthAmount()));
                }
                /*Seventh Date Amount*/
                if (nopF.getSeventhAmount().compareTo(new BigDecimal("0")) <= 0) {
                    ntdlPojo.setSeventhAmount(nopS.getSeventhAmount());
                } else {
                    ntdlPojo.setSeventhAmount(nopF.getSeventhAmount().add(nopS.getSeventhAmount()));
                }
                
                rbiPojoTable.remove(nopF);
                rbiPojoTable.remove(nopS);
                rbiPojoTable.add(ntdlPojo);
            }
            /*Sorting of list*/
            ComparatorChain chain = new ComparatorChain();
            chain.addComparator(new ComparatorByReportName());
            chain.addComparator(new ComparatorByGno());
            chain.addComparator(new ComparatorBySgno());
            chain.addComparator(new ComparatorBySSgno());
            chain.addComparator(new ComparatorBySSSgno());
            chain.addComparator(new ComparatorBySSSSgno());
            Collections.sort(rbiPojoTable, chain);
            /*Retuning List*/
            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public GlHeadPojo setGlReportAmount(GlHeadPojo pojo, int i, BigDecimal bal) throws ApplicationException {
        try {
            if (i == 0) {
                pojo.setFirstAmt(bal);
            } else if (i == 1) {
                pojo.setSecondAmt(bal);
            } else if (i == 2) {
                pojo.setThirsAmt(bal);
            } else if (i == 3) {
                pojo.setFourthAmt(bal);
            } else if (i == 4) {
                pojo.setFifthAmt(bal);
            } else if (i == 5) {
                pojo.setSixthAmt(bal);
            } else if (i == 6) {
                pojo.setSeventhAmt(bal);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }
    
    public RbiSossPojo setReportAmount(RbiSossPojo pojo, int i, BigDecimal bal) throws ApplicationException {
        try {
            if (i == 0) {
                pojo.setAmt(bal);
            } else if (i == 1) {
                pojo.setSecondAmount(bal);
            } else if (i == 2) {
                pojo.setThirdAmount(bal);
            } else if (i == 3) {
                pojo.setFourthAmount(bal);
            } else if (i == 4) {
                pojo.setFifthAmount(bal);
            } else if (i == 5) {
                pojo.setSixthAmount(bal);
            } else if (i == 6) {
                pojo.setSeventhAmount(bal);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
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
    
    public BigDecimal getOperandBalanceForFormOne(List<RbiSossPojo> dataList, String csvExp,
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
            balance = balance.add(new BigDecimal(pojo.getAmt() == null ? "0" : pojo.getAmt().toString()));
        } else if (k == 1) {
            balance = balance.add(new BigDecimal(pojo.getSecondAmount() == null ? "0" : pojo.getSecondAmount().toString()));
        } else if (k == 2) {
            balance = balance.add(new BigDecimal(pojo.getThirdAmount() == null ? "0" : pojo.getThirdAmount().toString()));
        } else if (k == 3) {
            balance = balance.add(new BigDecimal(pojo.getFourthAmount() == null ? "0" : pojo.getFourthAmount().toString()));
        } else if (k == 4) {
            balance = balance.add(new BigDecimal(pojo.getFifthAmount() == null ? "0" : pojo.getFifthAmount().toString()));
        } else if (k == 5) {
            balance = balance.add(new BigDecimal(pojo.getSixthAmount() == null ? "0" : pojo.getSixthAmount().toString()));
        } else if (k == 6) {
            balance = balance.add(new BigDecimal(pojo.getSeventhAmount() == null ? "0" : pojo.getSeventhAmount().toString()));
        }
        return balance;
    }
    
    public List<RbiSossPojo> getMonetaryReportDetails(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
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
                    /*As it is insertion of heading in the RBI MASTER*/
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setAmt(new BigDecimal("0"));
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
                    
                    rbiPojoTable.add(rbiSossPojo);
                    /*Report Processing*/
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
                    
                    List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                    AcctBalPojo acctBal = new AcctBalPojo();
                    BigDecimal bal = new BigDecimal("0");
                    if (!(acNature == null || acNature.equalsIgnoreCase("")) || !(acType == null || acType.equalsIgnoreCase(""))) {
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
                            params.setNpaAcList(resultList);
                            
                            GlHeadPojo glPojo = new GlHeadPojo();
                            glPojo.setGlHead(vector.get(0).toString());
                            glPojo.setGlName(vector.get(1).toString());
                            if (rangeBaseOn.equalsIgnoreCase("Y")
                                    || rangeBaseOn.equalsIgnoreCase("M")
                                    || rangeBaseOn.equalsIgnoreCase("D")) {
                                glPojo.setBalance(getMonetaryAggregatesFdValue("", glPojo.getGlHead(), dt,
                                        rangeBaseOn, rangeFrom, rangeTo));
                            } else {
                                acctBal = rbiReportRemote.getAcctsAndBal(params);
                                glPojo.setBalance(acctBal.getBalance());
                            }
                            glHeadList.add(glPojo);
                        }
                    } else if (!(glHeadFrom == null
                            || glHeadFrom.equalsIgnoreCase(""))
                            && !(glHeadTo == null
                            || glHeadTo.equalsIgnoreCase(""))) {
                        glHeadList = rbiReportRemote.getGLHeadAndBal(params);
                    }
                    /*Setting into main list*/
                    for (int z = 0; z < glHeadList.size(); z++) {
                        rbiSossPojo = new RbiSossPojo();
                        GlHeadPojo glHeadPo = glHeadList.get(z);
                        bal = glHeadPo.getBalance().divide(repOpt);
                        rbiSossPojo.setAmt(bal);
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
                        if (ssGNo == 0) {
                            rbiSossPojo.setSsGNo(z + 1);
                        } else {
                            rbiSossPojo.setSsGNo(ssGNo);
                        }
                        if (sssGNo == 0 && ssGNo != 0) {
                            rbiSossPojo.setSssGNo(z + 1);
                        } else {
                            rbiSossPojo.setSssGNo(sssGNo);
                        }
                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
                            rbiSossPojo.setSsssGNo(z + 1);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                        } else {
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                        }
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        rbiPojoTable.add(rbiSossPojo);
                    }
                }
            }
            return rbiPojoTable;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /*
     * Formula implementation of RBI Report (
     */
    public Double getValueFromFormula(List<RbiAlmPojo> dataList, String formula) throws ApplicationException {
        try {
            Double balance;
            String exp = "";
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(",")) {
                    balance = getOperandBalanceDouble(dataList, arr[i]);
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
            return PostfixEvaluator.evaluate(exp).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public Double getOperandBalanceDouble(List<RbiAlmPojo> dataList, String csvExp) throws ApplicationException {
        try {
            Double balance = 0d;
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (RbiAlmPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        balance = balance + pojo.getAmt();
                    }
                }
            } else if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0) {
                for (RbiAlmPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        balance = balance + pojo.getAmt();
                    }
                }
            } else if (arr[1] != 0 && arr[2] != 0) {
                for (RbiAlmPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        balance = balance + pojo.getAmt();
                    }
                }
            } else if (arr[1] != 0) {
                for (RbiAlmPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        balance = balance + pojo.getAmt();
                    }
                }
            } else {
                for (RbiAlmPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0])) {
                        balance = balance + pojo.getAmt();
                    }
                }
            }
            return balance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
}
